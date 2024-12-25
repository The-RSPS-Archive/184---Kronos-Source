package io.ruin.model.entity;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.cache.AnimDef;
import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.Combat;
import io.ruin.model.combat.CombatUtils;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Movement;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.entity.shared.listeners.*;
import io.ruin.model.entity.shared.masks.*;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.*;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.process.event.Event;
import io.ruin.process.event.EventConsumer;
import io.ruin.process.event.EventType;
import io.ruin.process.event.EventWorker;
import io.ruin.utility.TickDelay;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

// TODO replace entity.player != null checks with isPlayer (and one for npc)
public abstract class Entity {

    public Player player;

    public NPC npc;

    public final HashMap<Object, Object> temp = new HashMap<>();

    public boolean processed;

    public Entity() {
        if(this instanceof Player) {
            player = (Player) this;
            npc = null;
        } else {
            npc = (NPC) this;
            player = null;
        }
    }

    /**
     * Index
     */

    private int index = -1;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getClientIndex() {
        if(player != null)
            return index + 32768;
        return index;
    }

    /**
     * Size
     */

    private int size = 1;

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    /**
     * Hidden
     */

    private boolean hidden;

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        Tile.occupy(this);
    }

    public boolean isHidden() {
        return hidden;
    }

    /*
     * God Mode/Str Bonuses
     */

    private boolean invincible;
    private int strAdder;

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
    public boolean isInvincible() {
        return invincible;
    }

    public void setStrAdder(int strAdder) {
        this.strAdder = strAdder;
    }
    public int getStrAdder() {
        return this.strAdder;
    }
    public boolean hasStrAdder() {
        return(this.strAdder != 0);
    }

    /**
     * Position
     */

    @Expose
    protected Position position;

    public boolean isAt(Position pos) {
        return isAt(pos.getX(), pos.getY());
    }

    public boolean isAt(int x, int y) {
        return position.getX() == x && position.getY() == y;
    }

    public int getAbsX() {
        return position.getX();
    }

    public int getAbsY() {
        return position.getY();
    }

    public int getHeight() {
        return position.getZ();
    }

    public Position getPosition() {
        return position;
    }

    public Bounds getBounds() {
        return new Bounds(getPosition().getX(), getPosition().getY(), getPosition().getX() + getSize() - 1, getPosition().getY() + getSize() - 1, getPosition().getZ());
    }

    /**
     * Last Position
     */

    protected Position lastPosition;

    public void updateLastPosition() {
        lastPosition.copy(getPosition());
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    /**
     * Tile stuff
     */

    public boolean occupyingTiles;

    public boolean isNearBank() {
        Tile tile = Tile.get(getAbsX(), getAbsY(), getHeight());
        return tile != null && tile.nearBank;
    }

    /**
     * Multi
     */

    private boolean multi;
    private boolean ignoreMulti;

    public void setIgnoreMulti(boolean ignoreMulti) {
        this.ignoreMulti = ignoreMulti;
        checkMulti();
    }

    public void checkMulti() {
        Tile tile = Tile.get(getAbsX(), getAbsY(), getHeight());
        boolean inMulti = ignoreMulti || (tile != null && tile.multi);
        if(multi == inMulti)
            return;
        multi = inMulti;
        if(player != null)
            Config.MULTI_ZONE.set(player, multi ? 1 : 0);
    }

    public boolean inMulti() {
        return multi;
    }

    /**
     * Route finder
     */

    private RouteFinder routeFinder;

    public RouteFinder getRouteFinder() {
        if(routeFinder == null)
            routeFinder = new RouteFinder(this);
        return routeFinder;
    }

    /**
     * Step - todo - honestly move all this to Movement class lol..
     */

    public void step(int diffX, int diffY, StepType stepType) {
        stepAbs(getAbsX() + diffX, getAbsY() + diffY, stepType);
    }

    public void stepAbs(int absX, int absY, StepType stepType) {
        /* forces a step without route finding */
        Movement movement = getMovement();
        movement.readOffset = 0;
        movement.getStepsX()[0] = absX;
        movement.getStepsY()[0] = absY;
        movement.writeOffset = 1;
        movement.stepType = stepType;
    }

    public void resetSteps() {
        Movement movement = getMovement();
        movement.readOffset = 0;
        movement.writeOffset = 0;
        movement.stepType = StepType.NORMAL;
    }

    public boolean addStep(int absX, int absY) {
        Movement movement = getMovement();
        if(movement.writeOffset < 50) {
            movement.getStepsX()[movement.writeOffset] = absX;
            movement.getStepsY()[movement.writeOffset] = absY;
            movement.writeOffset++;
            return true;
        }
        return false;
    }

    /**
     * Listeners
     */

    public AttackNpcListener attackNpcListener;

    public AttackPlayerListener attackPlayerListener;

    public DeathListener deathStartListener, deathEndListener;

    public DropListener dropListener;

    public HitListener hitListener;

    /**
     * Masks
     */

    public abstract UpdateMask[] getMasks();

    public int getUpdateMaskData(boolean playerUpdate, boolean justAdded) {
        int maskData = 0;
        for(UpdateMask updateMask : getMasks()) {
            if(!updateMask.hasUpdate(justAdded))
                continue;
            maskData |= updateMask.get(playerUpdate);
        }
        return maskData;
    }

    public void resetUpdates() {
        for(UpdateMask updateMask : getMasks()) {
            if(updateMask.isSent()) {
                updateMask.setSent(false);
                updateMask.reset();
            }
        }
    }

    /**
     * Masks - Animations
     */

    protected AnimationUpdate animationUpdate;

    private long animTick = 0;

    public int animate(int id) {
        return animate(id, 0);
    }

    /*
    Whether we are currently animating or not
     */
    public boolean isAnimating() {
        return Server.currentTick() < animTick;
    }

    public int resetAnimation() {
        return animate(-1, 0);
    }

    public int animate(int id, int delay) {
        AnimDef def = AnimDef.get(id);
        if (def != null) {
            animTick = Server.currentTick() + def.getDuration();
        }
        animationUpdate.set(id, delay);
        if(id == -1) {
            //if(animationDelay != null)
            //    animationDelay.reset();
            return 0;
        }
        return 0;
        //int ticks = AnimationDefinition.get(id).ticks;
        //if(animationDelay == null)
        //    animationDelay = new WorldDelay();
        //animationDelay.delay(ticks);
        //return ticks;
    }

    public boolean hasAnimationUpdate() {
        return animationUpdate.hasUpdate(false);
    }

    /**
     * Masks - Graphics
     */

    protected GraphicsUpdate graphicsUpdate;

    public void graphics(int id) {
        graphics(id, 0, 0);
    }

    public void graphics(int id, int height, int delay) {
        graphicsUpdate.set(id, height, delay);
    }

    public void graphics(Graphic graphic) {
        graphicsUpdate.set(graphic.getId(), graphic.getHeight(), graphic.getDelay());
    }

    /**
     * Masks - Facing
     */

    protected MapDirectionUpdate mapDirectionUpdate;

    protected EntityDirectionUpdate entityDirectionUpdate;

    public void face(Direction direction) {
        if(npc != null) {
            int faceX = getAbsX() + direction.deltaX;
            int faceY = getAbsY() + direction.deltaY;
            face(faceX, faceY);
            return;
        }
        mapDirectionUpdate.set(direction.clientValue);
    }

    public void face(int absX, int absY) {
        face(absX, absY, getSize(), getSize());
    }

    public void face(GameObject gameObject) {
        if(gameObject.id == -1) {
            /* object was removed */
            return;
        }
        ObjectDef def = gameObject.getDef();
        int x = gameObject.x, y = gameObject.y;
        if(isAt(x, y) && gameObject.type <= 9) {//(gameObject.type == 0 || gameObject.type == 5)) {
            if(gameObject.direction == 0)
                x--;
            else if(gameObject.direction == 1)
                y++;
            else if(gameObject.direction == 2)
                x++;
            else if(gameObject.direction == 3)
                y--;
        }
        int xLength, yLength;
        if(gameObject.direction == 1 || gameObject.direction == 3) {
            xLength = def.yLength;
            yLength = def.xLength;
        } else {
            xLength = def.xLength;
            yLength = def.yLength;
        }
        face(x, y, xLength, yLength);
    }

    private void face(int absX, int absY, int xLength, int yLength) {
        int faceX = (absX * 2) + xLength;
        int faceY = (absY * 2) + yLength;
        if(player != null) {
            int anInt1783 = (position.getBaseLocalX() << 7) | (size << 6);
            int anInt1770 = (position.getBaseLocalY() << 7) | (size << 6);
            int baseRegionX = (position.getFirstChunkX() - 6) * 8;
            int baseRegionY = (position.getFirstChunkY() - 6) * 8;
            int i_35_ = (anInt1783 - (faceX - baseRegionX - baseRegionX) * 64);
            int i_36_ = (anInt1770 - (faceY - baseRegionY - baseRegionY) * 64);
            if(i_35_ != 0 || i_36_ != 0)
                mapDirectionUpdate.set((int) (Math.atan2((double) i_35_, (double) i_36_) * 325.949) & 0x7ff);
            return;
        }
        mapDirectionUpdate.set(faceX, faceY);
    }

    public void face(Entity target) {
        face(target, false);
    }

    public void faceTemp(Entity target) {
        face(target, true);
    }

    private void face(Entity target, boolean temp) {
        int direction = entityDirectionUpdate.direction;
        int newDirection = target == null ? -1 : target.getClientIndex();
        if(direction == newDirection && !temp)
            return;
        entityDirectionUpdate.set(newDirection, temp);
    }

    public void faceNone(boolean delay) {
        int direction = entityDirectionUpdate.direction;
        if(direction == -1)
            return;
        entityDirectionUpdate.remove(delay);
    }

    /**
     * Masks - Force text
     */

    protected ForceTextUpdate forceTextUpdate;

    public void forceText(String text) {
        forceTextUpdate.set(text);
    }

    /**
     * Event
     */

    private Event activeEvent, nextEvent;

    private ArrayList<Event> backgroundEvents;

    public final Event startEvent(EventConsumer eventConsumer) {
        stopEvent(true);
        return nextEvent = EventWorker.createEvent(eventConsumer);
    }

   public final Event startPersistableEvent(EventConsumer consumer) {
        stopEvent(true);
        Event event = EventWorker.createEvent(consumer);
        event.eventType = EventType.PERSISTENT;
        return nextEvent = event;
   }

    public final void addEvent(EventConsumer eventConsumer) {
        if(backgroundEvents == null)
            backgroundEvents = new ArrayList<>();
        backgroundEvents.add(EventWorker.createEvent(eventConsumer));
    }

    protected final void processEvent() {
        if(nextEvent != null) {
            activeEvent = nextEvent;
            nextEvent = null;
        }
        if(activeEvent != null && !activeEvent.tick())
            activeEvent = null;
        if(backgroundEvents != null && !backgroundEvents.isEmpty())
            backgroundEvents.removeIf(event -> !event.tick());
    }

    public final void stopEvent(boolean resetCombat) {
        if (activeEvent != null && activeEvent.persistent()) {
            return;
        }

        if(activeEvent != null) {
            if(resetCombat || !activeEvent.isIgnoreCombatReset())
                activeEvent = null;
        }
        if(nextEvent != null) {
            if(resetCombat || !nextEvent.isIgnoreCombatReset())
                nextEvent = null;
        }
    }

    /**
     * Locking
     */

    private LockType lock = LockType.NONE;

    public void unlock() {
        lock = LockType.NONE;
    }

    public void lock() {
        lock = LockType.FULL;
    }

    public void lock(LockType type) {
        lock = type;
    }

    public boolean isLocked() {
        return lock != LockType.NONE || getMovement().teleporting();
    }

    public boolean isLocked(LockType type) {
        return lock == type;
    }

    public boolean isLockedExclude(LockType type) {
        return lock != LockType.NONE && lock != type;
    }

    /**
     * Poison
     */

    @Expose
    public int poisonTicks;

    @Expose
    public int poisonDamage;

    @Expose
    public int poisonImmunity;

    @Expose
    public int poisonLevel; // 1 = poison, 2 = venom

    @Expose
    public int poisonImmunityLevel; // ^

    public void curePoison(int immuneFor) {
        if(poisonLevel > 1) { // envenomed, downgrade to poison
            poisonLevel--;
            poisonDamage = 6;
            Config.POISONED.set(player, 1);
        } else { // regular poison, cure
            this.poisonTicks = 0;
            this.poisonDamage = 0;
            if(poisonImmunityLevel < 2) { // if we have venom immunity already, we should keep that instead
                if(poisonImmunity < immuneFor)
                    this.poisonImmunity = immuneFor;
                if(immuneFor > 0)
                    poisonImmunityLevel = 2;
            }
            poisonLevel = 0;
            if(player != null)
                Config.POISONED.set(player, 0);
        }
    }

    public boolean poison(int damage) {
        if(poisonLevel > 0 || isPoisonImmune())
            return false;
        poisonTicks = 120;
        poisonDamage = damage;
        poisonLevel = 1;
        if(player != null) {
            player.sendMessage("You have been poisoned!");
            Config.POISONED.set(player, 1);
        }
        return true;
    }

    private void checkPoison() {
        if(poisonImmunity > 0 && --poisonImmunity == 0) {
            poisonImmunityLevel = 0;
            return;
        }
        if(poisonDamage == 0) {
            /* not poisoned */
            return;
        }
        if(poisonTicks == 0 || --poisonTicks % 30 != 0) {
            /* no hit required */
            return;
        }
        hit(new Hit(poisonLevel == 2 ? HitType.VENOM : HitType.POISON).fixedDamage(poisonDamage));
        if(poisonLevel == 1) { // regular poison, check decay
            if(poisonTicks > 0) {
            /* maintain same damage */
                return;
            }
            if(--poisonDamage > 0)
                poisonTicks = 120;
            else if(player != null) {
                poisonLevel = 0;
                Config.POISONED.set(player, 0);
            }
        } else { // venom, increase damage
            poisonDamage = Math.min(poisonDamage + 2, 20);
        }
    }

    public boolean envenom(int damage) {
        if(poisonLevel >= 2 || isVenomImmune())
            return false;
        poisonTicks = 120;
        poisonDamage = damage;
        poisonLevel = 2;
        if(player != null) {
            player.sendMessage("You have been envenomed!");
            Config.POISONED.set(player, 1000000);
        }
        return true;
    }

    /**
     * Note: curing venom also cures poison
     */
    public void cureVenom(int immuneFor) {
        if(poisonLevel == 0)
            return;
        this.poisonTicks = 0;
        this.poisonDamage = 0;
        if(poisonImmunity < immuneFor)
            this.poisonImmunity = immuneFor;
        if(immuneFor > 0)
            poisonImmunityLevel = 2;
        poisonLevel = 0;
        if(player != null)
            Config.POISONED.set(player, 0);
    }

    public boolean isPoisoned() {
        return poisonDamage > 0;
    }

    public boolean isPoisonImmune() {
        return poisonImmunity > 0;
    }

    public boolean isVenomImmune() {
        return poisonImmunityLevel > 1 && poisonImmunity > 0;
    }

    /**
     * Hits
     */

    private ArrayList<Hit> queuedHits;

    public HitsUpdate hitsUpdate;

    public int hit(Hit... hits) {
        if(queuedHits == null)
            queuedHits = new ArrayList<>();
        int damage = 0;
        for(Hit hit : hits) {
            if(hit.defend(this)) {
                if (!isLocked(LockType.FULL_NULLIFY_DAMAGE))
                    queuedHits.add(hit);
                damage += hit.damage;
            }
        }
        Hit baseHit = hits[0];
        if(baseHit.type.resetActions && baseHit.resetActions) {
            if(player != null)
                player.resetActions(true, false, false);
            else
                npc.resetActions(false, false);
        }
        if(baseHit.attacker != null) {
            if(baseHit.attacker.player != null && baseHit.attackStyle != null) {
                if(player != null) //important that this happens here for things that hit multiple targets
                    baseHit.attacker.player.getCombat().skull(player);
                if(baseHit.attackSpell == null)
                    CombatUtils.addXp(baseHit.attacker.player, this, baseHit.attackStyle, baseHit.attackType, damage);
            }
            getCombat().updateLastDefend(baseHit.attacker);
        }

        return damage;
    }

    protected void processHits() {
        if(isStunned())
            return;
        checkPoison();
        if(queuedHits != null && !queuedHits.isEmpty()) {
            boolean defendAnim = false;
            //noinspection ForLoopReplaceableByForEach (foreach will cause concurrentmodification exceptions!)
            for(int i = 0; i < queuedHits.size(); i++) {
                Hit hit = queuedHits.get(i);
                if(hit.isNullified() || getCombat().isDead() || isLocked(LockType.FULL_NULLIFY_DAMAGE)) {
                    hit.removed = true;
                    continue;
                }
                if(hit.finish(this)) {
                    hit.removed = true;
                    if(!hit.isHidden())
                        hitsUpdate.add(hit, getHp(), getMaxHp());
                    if(hit.attacker != null && hit.attackStyle != null) {
                        if(!defendAnim && !hit.attackStyle.isMagic()) {
                            defendAnim = true;
                            int animId = getCombat().getDefendAnimation();
                            if(animId != -1)
                                animate(animId);
                        }
                        //todo - honestly this retaliate system is so bad...
                        if(!isLocked() && !getCombat().retaliating && getCombat().allowRetaliate(hit.attacker)) {
                            getCombat().retaliating = true;
                            addEvent(e -> {
                                e.delay(1);
                                if(!getCombat().isDead() && !hit.attacker.getCombat().isDead()) {
                                    getCombat().setTarget(hit.attacker);
                                    getCombat().faceTarget();
                                }
                                getCombat().retaliating = false;
                            });
                        }
                    }
                }
            }
            queuedHits.removeIf(hit -> hit.removed);
        }
    }

    public void clearHits() {
        if(queuedHits != null)
            queuedHits.clear();
    }

    /**
     * Hp
     */

    public abstract int getHp();

    public abstract int getMaxHp();

    public abstract int setHp(int newHp);

    public abstract int setMaxHp(int newHp);

    public int incrementHp(int increment) {
        int hp = getHp();
        int maxHp = getMaxHp();
        if(hp > maxHp) {
            if(increment >= 0)
                return hp;
            hp += increment;
            if(hp < 0)
                hp = 0;
        } else {
            hp += increment;
            if(hp < 0)
                hp = 0;
            else if(hp > maxHp)
                hp = maxHp;
        }
        return setHp(hp);
    }

    /**
     * Sounds
     */

    public void privateSound(int id) {
        privateSound(id, 1, 0);
    }

    public void privateSound(int id, int type, int delay) {
        if(player != null)
            player.getPacketSender().sendSoundEffect(id, type, delay);
    }

    public void publicSound(int id) {
        publicSound(id, 1, 0);
    }

    public void publicSound(int id, int type, int delay) {
        int x = getAbsX();
        int y = getAbsY();
        int distance = 15; //idk how to calc
        for(Player p : localPlayers())
            p.getPacketSender().sendAreaSound(id, type, delay, x, y, distance);
    }

    /**
     * Freezing
     */

    private Entity freezer;

    private TickDelay freezeDelay = new TickDelay();

    public void freeze(int seconds, Entity entity) {
        freezeDelay.delaySeconds(seconds);
        freezer = entity;
        if(player != null && !player.isLocked())
            getMovement().reset();
    }

    public void resetFreeze() {
        freezeDelay.reset();
        freezer = null;
        if(player != null)
            player.getPacketSender().sendWidget(Widget.BARRAGE, 0);
    }

    public boolean isFrozen() {
        return freezeDelay.isDelayed();
    }

    public boolean hasFreezeImmunity() {
        return freezeDelay.isDelayed(5);
    }

    /**
     * Stunning
     */

    private TickDelay stunDelay = new TickDelay();

    public void stun(int seconds, boolean resetMovement) {
        stunDelay.delaySeconds(seconds);
        if(player != null) {
            player.privateSound(2727);
            player.sendMessage("You have been stunned!");
        }
        graphics(245, 124, 0);
        if(resetMovement)
            getMovement().reset();
    }

    public void resetStun() {
        stunDelay.reset();
    }

    public boolean isStunned() {
        return stunDelay.isDelayed();
    }

    public boolean isStunned(int gracePeriod) {
        return stunDelay.isDelayed(gracePeriod);
    }

    /**
     * Root - the entity cannot move but can still perform other actions
     */

    private TickDelay rootDelay = new TickDelay();

    public void root(int ticks, boolean resetMovement) {
        rootDelay.delay(ticks);
        if(resetMovement)
            getMovement().reset();
    }

    public void resetRoot() {
        rootDelay.reset();
    }

    public boolean isRooted() {
        return rootDelay.isDelayed();
    }

    /**
     * Movement check
     */

    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        if(!ignoreFreeze && isFrozen()) {
            if(freezer != null) {
                if(freezer.player != null && World.getPlayer(freezer.player.getUserId(), true) == null) {
                    resetFreeze();
                    return false;
                }
                if(!freezer.getPosition().isWithinDistance(getPosition(), false, 12)) {
                    resetFreeze();
                    return false;
                }
                if(message && player != null)
                    player.sendMessage("A magical force stops you from moving.");
                return true;
            }
        }
        if(isStunned() || isRooted()) {
            if(message && player != null)
                player.sendMessage("You're stunned!");
            return true;
        }

        if(player != null && DuelRule.NO_MOVEMENT.isToggled(player)) {
            if(message)
                player.sendMessage("Movement has been disabled for this duel!");
            return true;
        }
        return false;
    }

    /**
     * Abstract
     */

    public void forLocalEntity(Consumer<Entity> entityConsumer) {
        for(Player player : localPlayers()) {
            if(player != this)
                entityConsumer.accept(player);
        }
        for(NPC npc : localNpcs()) {
            if(npc != this)
                entityConsumer.accept(npc);
        }
    }

    public abstract Iterable<Player> localPlayers();

    public abstract boolean isLocal(Player player);

    public abstract Iterable<NPC> localNpcs();

    public abstract Movement getMovement();

    public abstract Combat getCombat();

    public void set(String key, Object value) {
        temp.put(key, value);
    }

    public <T> T get(Object key) {
        Object value = temp.get(key);
        return value == null ? null : (T) value;
    }

    public <T> T get(Object key, T nullValue) {
        T attribute = get(key);
        return attribute == null ? nullValue : attribute;
    }

    public <T> T remove(Object key) {
        Object value = temp.remove(key);
        return value == null ? null : (T) value;
    }

    public boolean isPlayer() {
        return false;
    }

    public boolean isNpc() {
        return false;
    }

    public boolean dead() {
        return getCombat().isDead();
    }

    public boolean alive() {
        return !dead();
    }
}