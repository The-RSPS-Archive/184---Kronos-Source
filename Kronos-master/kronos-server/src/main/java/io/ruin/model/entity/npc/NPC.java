package io.ruin.model.entity.npc;

import io.ruin.Server;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.activities.miscpvm.BasicCombat;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.entity.shared.listeners.RespawnListener;
import io.ruin.model.entity.shared.masks.*;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class NPC extends NPCAttributes {

    private int id;

    public NPC(int id) {
        this.id = id;
        setSize(getDef().size);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public NPCDef getDef() {
        return NPCDef.cached.get(id);
    }

    /**
     * Actions
     */

    public NPCAction[] actions;

    public HashMap<Integer, ItemNPCAction> itemActions;

    public ItemNPCAction defaultItemAction;

    public Position walkTo;
    public Predicate<Position> skipReachCheck;
    public boolean skipMovementCheck;
    public Bounds spawnBounds;

    /**
     * Local
     */

    //todo - rethink all of these methods >.>

    private List<Player> localPlayers = new ArrayList<>();

    public void addPlayer(Player player) {
        localPlayers.add(player);
    }

    public void removePlayer(Player player) {
        localPlayers.remove(player);
    }

    @Override
    public boolean isLocal(Player player) {
        return localPlayers.contains(player);
    }

    @Override
    public List<Player> localPlayers() {
        return localPlayers;
    }

    @Override
    public Iterable<NPC> localNpcs() {
        //ehh
        HashSet<NPC> set = new HashSet<>();
        for(Player player : localPlayers)
            set.addAll(player.localNpcs());
        return set;
    }

    /**
     * Listeners
     */

    public RespawnListener respawnListener;

    /**
     * Masks
     */

    private UpdateMask[] masks;

    public UpdateMask[] getMasks() {
        return masks;
    }

    /**
     * Movement
     */

    private NPCMovement movement;

    public NPCMovement getMovement() {
        return movement;
    }

    public boolean isRandomWalkAllowed() {
        return walkBounds != null && !isHidden() && getMovement().isAtDestination() && !isLocked() && localPlayers().size() != 0 && !isMovementBlocked(false, false);
    }

    /**
     * Combat
     */

    private NPCCombat combat;

    private boolean setCombat() {
        NPCDef def = getDef();
        if(def.combatInfo == null) {
            /* not a combat npc */
            return false;
        }
        if(def.combatHandlerClass != null) {
            try {
                combat = def.combatHandlerClass.newInstance().init(this, def.combatInfo);
                return true;
            } catch(Exception e) {
                Server.logError("", e);
            }
        }
        combat = new BasicCombat().init(this, def.combatInfo);
        return true;
    }

    public NPCCombat getCombat() {
        return combat;
    }

    /**
     * Hitpoints
     */

    @Override
    public int setHp(int newHp) {
        return combat == null ? 0 : combat.stats[StatType.Hitpoints.ordinal()].alter(newHp);
    }

    @Override
    public int setMaxHp(int newHp) {
        return combat == null ? 0 : combat.stats[StatType.Hitpoints.ordinal()].set(newHp);
    }

    @Override
    public int getHp() {
        return combat == null ? 0 : combat.stats[StatType.Hitpoints.ordinal()].currentLevel;
    }

    @Override
    public int getMaxHp() {
        return combat == null ? 0 : combat.stats[StatType.Hitpoints.ordinal()].fixedLevel;
    }

    /**
     * Poison
     */

    @Override
    public boolean isPoisonImmune() {
        if (getCombat() != null && getCombat().getInfo().poison_immunity)
            return true;
        return super.isPoisonImmune();
    }

    @Override
    public boolean isVenomImmune() {
        if (getCombat() != null && getCombat().getInfo().venom_immunity)
            return true;
        return super.isVenomImmune();
    }

    @Override
    public boolean envenom(int damage) {
        if (isVenomImmune() && !isPoisonImmune()) { // npcs that are immune to venom but not to poison are poisoned instead of envenomed
            return poison(6);
        } else {
            return super.envenom(damage);
        }
    }

    /**
     * Transform
     */

    private TransformUpdate transformUpdate;

    public void transform(int id) {
        transformUpdate.set(this.id = id);
        NPCDef def = getDef();
        setSize(def.size);
        if(combat != null && def.combatInfo != null)
            combat.updateInfo(def.combatInfo);
    }

    /**
     * Init
     */

    private void init() {
        this.movement = new NPCMovement(this);
        this.masks = new UpdateMask[]{
                animationUpdate = new AnimationUpdate(),
                hitsUpdate = new HitsUpdate(),
                entityDirectionUpdate = new EntityDirectionUpdate(),
                transformUpdate = new TransformUpdate(),
                mapDirectionUpdate = new MapDirectionUpdate(),
                graphicsUpdate = new GraphicsUpdate(),
                forceTextUpdate = new ForceTextUpdate(),
        };
    }

    /**
     * Spawn
     */

    public Position spawnPosition;

    public Direction spawnDirection;

    public Bounds walkBounds, attackBounds;

    public boolean defaultSpawn;

    public int walkRange;

    public NPC spawn(Position position) {
        return spawn(position.getX(), position.getY(), position.getZ(), Direction.SOUTH, 0);
    }

    public NPC spawn(int x, int y, int z) {
        return spawn(x, y, z, Direction.SOUTH, 0);
    }

    public NPC spawn(int x, int y, int z, int walkRange) {
        return spawn(x, y, z, Direction.SOUTH, walkRange);
    }

    public NPC spawn(int x, int y, int z, Direction spawnDirection, int walkRange) {
        init();
        this.position = new Position(x, y, z);
        this.lastPosition = position.copy();
        this.spawnPosition = new Position(x, y, z);
        this.spawnDirection = spawnDirection;
        this.walkRange = walkRange;
        if(walkRange > 0)
            walkBounds = new Bounds(spawnPosition, walkRange);
        if(setCombat()) {
            attackBounds = new Bounds(spawnPosition, getCombat().getAttackBoundsRange());
        }
        setIndex(World.npcs.add(this, 0));
        this.wildernessSpawnLevel = Wilderness.getLevel(spawnPosition);
        checkMulti();
        Tile.occupy(this);
        if (combat != null && combat.info != null && combat.info.spawn_animation != -1)
            animate(combat.info.spawn_animation);
        return this;
    }

    /**
     * Spawning - For a Player
     */

    protected Player targetPlayer;

    private boolean targetIcon;

    private Runnable targetRemovalAction;

    public NPC targetPlayer(Player player, boolean showIcon) {
        this.targetPlayer = player;
        this.targetIcon = showIcon;
        player.npcTarget = true;
        if(showIcon)
            player.getPacketSender().sendHintIcon(this);
        return this;
    }

    private void removeTarget() {
        if(targetPlayer != null) {
            targetPlayer.npcTarget = false;
            if(targetIcon)
                targetPlayer.getPacketSender().resetHintIcon(true);
            targetPlayer = null;
        }
        if(targetRemovalAction != null) {
            targetRemovalAction.run();
            targetRemovalAction = null;
        }
    }

    public void attackTargetPlayer() {
        attackTargetPlayer(null, null);
    }

    public void attackTargetPlayer(Supplier<Boolean> removalCheck) {
        attackTargetPlayer(removalCheck, null);
    }

    public void attackTargetPlayer(Supplier<Boolean> removalCheck, Runnable removalAction) {
        if(combat == null) {
            //todo remove after we find the broken npc lol
            Server.logWarning("NPC " + id + " HAS NO COMBAT SCRIPT SET!");
            return;
        }
        addEvent(e -> {
            int attackAttempts = 0;
            while(true) {
                if(combat.isDead()) {
                    /**
                     * Death will automatically remove this npc.
                     * THIS MEANS ANY NPC THAT TARGETS A PLAYER SHOULD HAVE "respawn_ticks": -1 !
                     */
                    e.delay(1);
                    continue;
                }
                if(removalCheck == null || !removalCheck.get()) {
                    if(combat.getTarget() != null) {
                        /**
                         * Npc is already attacking target.
                         */
                        attackAttempts = 0;
                        e.delay(1);
                        continue;
                    }
                    if(++attackAttempts < 5) {
                        /**
                         * We're not attacking? Lets try again.
                         * If attack fails 5 times in a row, npc will remove itself!
                         */
                        face(targetPlayer);
                        combat.setTarget(targetPlayer);
                        e.delay(1);
                        continue;
                    }
                }
                remove();
                return;
            }
        });
        this.targetRemovalAction = removalAction;
    }

    public boolean isTargeting(Player player) {
        return targetPlayer == player;
    }

    public boolean hasTarget() {
        return targetPlayer != null;
    }


    /**
     * Remove
     */

    private boolean removed;

    public void remove() {
        if(removed)
            return;
        removed = true;
        setHidden(true);
        removeTarget();
        World.npcs.remove(getIndex());
    }

    public boolean isRemoved() {
        return removed;
    }

    /**
     * Reset Actions
     */

    public void resetActions(boolean resetMovement, boolean resetCombat) {
        if(!isLocked())
            stopEvent(resetCombat);
        if(resetMovement) {
            movement.reset();
            faceNone(false);
            TargetRoute.reset(this);
        }
        if(resetCombat && combat.getTarget() != null)
            combat.reset();
    }

    /**
     * Processing
     */

    public void process() {
        if(removed)
            return;
        processHits();
        processEvent();
        if(combat != null)
            combat.follow0();
        movement.process();
        if(combat != null)
            combat.attack0();
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    @Override
    public boolean isNpc() {
        return true;
    }
}