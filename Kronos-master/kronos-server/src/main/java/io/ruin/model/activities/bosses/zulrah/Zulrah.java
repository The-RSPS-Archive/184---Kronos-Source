package io.ruin.model.activities.bosses.zulrah;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.*;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.List;

public class Zulrah extends NPCCombat { // this is a mess

    /**
     * Anims
     * 5068 - attack, slow (possibly summoning snakelings/fumes?)
     * 5069 - attack, fast
     * 5070 - stand
     * 5071 - emerge, slow (spawn)
     * 5072 - submerge
     * 5073 - emerge, fast
     * 5804 - death
     * 5806 - melee, attack from left side
     * 5807 - melee, attack from right side
     * 5808 - defend
     *
     * GFX
     * 1044 - range projectile
     * 1045 - toxic fume spawn projectile
     * 1046 - magic projectile
     * 1047 - snakeling spawn projectile
     *
     * Objects
     * 11700 - Toxic fume
     *
     */

    private static final Projectile RANGED_PROJECTILE = new Projectile(1044, 65, 20, 20, 15, 12, 15, 10);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(1046, 65, 20, 20, 15, 12, 15, 10);
    private static final Projectile FUMES_PROJECTILE = new Projectile(1045, 65, 0, 20, 72, 0, 18, 10);
    private static final Projectile SNAKELING_PROJECTILE = new Projectile(1047, 65, 0, 20, 108, 0, 18, 10);
    public static final Bounds SHRINE_BOUNDS = new Bounds(new Position(2268, 3074, 0), 16);

    private static final int[][] fumeOffsets = { // from spawn pos! starts from top left then goes to top right (U shape)
            {-4,3},{-4, 0},{-4, -3}, //west
            {-1,-4},{2, -4},{5,-4}, // center
            {6,-1},{6,2},{6,5}, // east
    };

    static {
        ObjectAction.register(10068, "board", (player, obj) -> {
            player.dialogue(new OptionsDialogue("Go to Zulrah's shrine?",
                    new Option("Yes", () -> enter(player)),
                    new Option("No", () -> {})));
        });

        ObjectAction.register(11701, "read", (player, obj) -> {
            player.dialogue(new OptionsDialogue("Return to Zul-Andra?",
                    new Option("Yes", () -> {
                        player.getMovement().startTeleport(event -> {
                            player.animate(3864);
                            player.graphics(1039, 0, 0);
                            event.delay(3);
                            player.getMovement().teleport(2199, 3056, 0);
                        });
                    }),
                    new Option("No", () -> {})));
        });
    }


    public static void enter(Player player) {
        player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(1);
            player.dialogue(new MessageDialogue("The priestess rows you to Zulrah's shrine,<br>then hurriedly paddles away.").hideContinue());
            event.delay(2);
            player.getPacketSender().fadeIn();
            DynamicMap map = new DynamicMap();
            map.build(SHRINE_BOUNDS);
            NPC zulrah = new NPC(2042).spawn(map.swRegion.baseX + 18, map.swRegion.baseY + 16, 0, Direction.SOUTH, 0);
            map.addNpc(zulrah);
            player.getMovement().teleport(map.swRegion.baseX + 20, map.swRegion.baseY + 12, 0);
            map.assignListener(player).onExit((p, logout) -> {
                if (logout)
                    p.getMovement().teleport(2210, 3057, 0);
                p.deathEndListener = null;
                map.destroy();
            });
            event.delay(2);
            player.unlock();
            player.zulrahTimer = new ActivityTimer();
            player.closeInterfaces();
        });
    }

    private enum Form {
        NORMAL(2042), // ranged form
        MAGMA(2043), // melee form
        TANZANITE(2044), // magic/ranged form
        JAD(2042) // alternating ranged/magic form
        ;
        Form(int npcId) {
            this.npcId = npcId;
        }
        int npcId;
    }

    private enum Location {
        CENTER(0, 0, 2, -2),
        SOUTH(0, -9, 1, -4),
        EAST(10, -1, 8, 1),
        WEST(-10, -1, -4, 1);

        Location(int xOffset, int yOffset, int dropOffsetX, int dropOffsetY) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.dropOffsetX = dropOffsetX;
            this.dropOffsetY = dropOffsetY;
        }

        public Position get(NPC npc) {
            return npc.getSpawnPosition().copy().translate(xOffset, yOffset, 0);
        }

        public Position getDrop(NPC npc) {
            return npc.getSpawnPosition().copy().translate(dropOffsetX, dropOffsetY, 0);
        }

        int xOffset, yOffset;
        int dropOffsetX, dropOffsetY;
    }

    private static class Phase {
        Form form;
        Location location;
        boolean[] safeFumes = new boolean[9];


        Phase(Form form, Location location) {
            this.form = form;
            this.location = location;
        }

        Phase westSafe() {
            safeFumes[0] = safeFumes[1] = safeFumes[2] = true;
            return this;
        }

        Phase centerSafe() {
            safeFumes[3] = safeFumes[4] = safeFumes[5] = true;
            return this;
        }

        Phase eastSafe() {
            safeFumes[6] = safeFumes[7] = safeFumes[8] = true;
            return this;
        }

        Phase setSafe(int index) {
           safeFumes[index] = true;
            return this;
        }

        Phase setUnsafe(int index) {
            safeFumes[index] = false;
            return this;
        }


    }


    private static final Phase[][] ROTATIONS = {
            {new Phase(Form.NORMAL, Location.CENTER).setSafe(8), new Phase(Form.MAGMA, Location.CENTER).setSafe(8), new Phase(Form.TANZANITE, Location.CENTER).setSafe(8), new Phase(Form.NORMAL, Location.SOUTH).westSafe(), new Phase(Form.MAGMA, Location.CENTER).setSafe(1), new Phase(Form.TANZANITE, Location.WEST).setSafe(1), new Phase(Form.NORMAL, Location.SOUTH).eastSafe(), new Phase(Form.TANZANITE, Location.SOUTH).westSafe(), new Phase(Form.JAD, Location.WEST).setSafe(0), new Phase(Form.MAGMA, Location.CENTER).setSafe(0).setSafe(1)},
            {new Phase(Form.NORMAL, Location.CENTER).setSafe(8), new Phase(Form.MAGMA, Location.CENTER).setSafe(8), new Phase(Form.TANZANITE, Location.CENTER).setSafe(8), new Phase(Form.NORMAL, Location.WEST).westSafe(), new Phase(Form.TANZANITE, Location.SOUTH).setSafe(1), new Phase(Form.MAGMA, Location.CENTER).setSafe(1), new Phase(Form.NORMAL, Location.EAST).setSafe(4), new Phase(Form.TANZANITE, Location.SOUTH).westSafe(), new Phase(Form.JAD, Location.WEST).setSafe(0), new Phase(Form.MAGMA, Location.CENTER).setSafe(0).setSafe(1)},
            {new Phase(Form.NORMAL, Location.CENTER).setSafe(8), new Phase(Form.NORMAL, Location.EAST).setSafe(8), new Phase(Form.MAGMA, Location.CENTER).eastSafe(), new Phase(Form.TANZANITE, Location.WEST).centerSafe(), new Phase(Form.NORMAL, Location.SOUTH).centerSafe(), new Phase(Form.TANZANITE, Location.EAST).setSafe(4).setSafe(5), new Phase(Form.NORMAL, Location.CENTER).westSafe(), new Phase(Form.NORMAL, Location.WEST).westSafe(), new Phase(Form.TANZANITE, Location.CENTER).eastSafe(), new Phase(Form.JAD, Location.EAST).eastSafe(), new Phase(Form.TANZANITE, Location.CENTER).eastSafe()},
            {new Phase(Form.NORMAL, Location.CENTER).setSafe(8), new Phase(Form.TANZANITE, Location.EAST).setSafe(8), new Phase(Form.NORMAL, Location.SOUTH).setSafe(1), new Phase(Form.TANZANITE, Location.WEST).setSafe(1), new Phase(Form.MAGMA, Location.CENTER).eastSafe(), new Phase(Form.NORMAL, Location.EAST).eastSafe(), new Phase(Form.NORMAL, Location.SOUTH).centerSafe(), new Phase(Form.TANZANITE, Location.WEST).westSafe(), new Phase(Form.NORMAL, Location.CENTER).eastSafe(), new Phase(Form.TANZANITE, Location.CENTER).eastSafe(), new Phase(Form.JAD, Location.EAST).eastSafe(), new Phase(Form.TANZANITE, Location.CENTER).eastSafe()},
    };
    private static final boolean[] JAD_RANGE_FIRST = {true,true,false,false};

    /**
     * instance variables
     */
    private Phase[] rotation;
    private int rotationId;
    private int currentPhase;
    private int attacks;
    private TickDelay transitioning = new TickDelay();
    private boolean spawningFumes;
    private boolean useRange; // for jad phase only
    private Position meleeTarget;
    private NPC[] snakelings = new NPC[3];


    private Phase getPhase() {
        return rotation[currentPhase % rotation.length];
    }

    @Override
    public boolean allowRetaliate(Entity attacker) {
        if (transitioning.isDelayed())
            return false;
        else
            return super.allowRetaliate(attacker);
    }

    private void nextPhase() {
        npc.faceNone(false);
        final Phase next = rotation[(currentPhase + 1) % (rotation.length)]; // loops back around
        npc.animate(5072);
        if (target.player.debug)
            target.player.sendMessage("Rotation " + rotationId + ", Phase [" + Color.COOL_BLUE.wrap(String.valueOf(currentPhase + 1 % rotation.length)) + "]: " + Color.RED.wrap(next.form.toString()) + ", " + Color.RED.wrap(next.location.toString()));
        transitioning.delay(6);
        npc.addEvent(event -> {
            event.delay(2);
            npc.getMovement().teleport(next.location.get(npc));
            if (next.form.npcId != npc.getId())
                npc.transform(next.form.npcId);
            npc.animate(5073);
            currentPhase++;
        });
        if (next.form == Form.JAD)
            useRange = JAD_RANGE_FIRST[rotationId];
    }

    private boolean fumes() {
        List<Integer> possible = getEmptyFumesSpots();
        if (possible.size() == 0) {
            spawningFumes = false;
            return false;
        }
        Integer spot = Random.get(possible); // pick a spot
        if (spawnFume(fumeOffsets[spot][0], fumeOffsets[spot][1])) { // spawn it
            possible.remove(spot);
        }
        if (possible.size() == 0) { // if no other spots left, return
            spawningFumes = false;
            return true;
        }
        if (possible.contains(spot + 1)) { // if the spot next to the one we just spawned clouds on is free, spawn a second one there
            spot = spot + 1;
            spawnFume(fumeOffsets[spot][0], fumeOffsets[spot][1]);
        }
        return true;
    }

    private boolean checkFumes() {
        if ((spawningFumes || ((currentPhase == 0 || Random.rollPercent(35))) && hasEmptyFumesSpot())) {
            spawningFumes = true;
            return fumes();
        }
        return false;
    }

    private boolean hasEmptyFumesSpot() {
        for (int i = 0; i < getPhase().safeFumes.length; i++) {
            if (!getPhase().safeFumes[i] // our current form allows this spawn
                    && Tile.getObject(11700, npc.getSpawnPosition().getX() + fumeOffsets[i][0], npc.getSpawnPosition().getY() + fumeOffsets[i][1], npc.getHeight(), 10, -1) == null) { // and it's not already spawned
                return true;
            }
        }
        return false;
    }

    private List<Integer> getEmptyFumesSpots() {
        List<Integer> list = new ArrayList<>(9);
        for (int i = 0; i < getPhase().safeFumes.length; i++) {
            if (!getPhase().safeFumes[i] // our current form allows this spawn
                    && Tile.getObject(11700, npc.getSpawnPosition().getX() + fumeOffsets[i][0], npc.getSpawnPosition().getY() + fumeOffsets[i][1], npc.getHeight(), 10, -1) == null) { // and it's not already spawned
                list.add(i);
            }
        }
        return list;
    }

    private boolean checkSnakelings() {
        for (int i = 0; i < snakelings.length; i++) {
            if ((snakelings[i] == null || snakelings[i].isRemoved()) && Random.rollPercent(20)) {
                if (spawnSnakeling(i))
                    return true;
            }
        }
        return false;
    }

    private ArrayList<Position> getPossibleSpawns(Position src) {
        ArrayList<Position> list = new ArrayList<>();
        for (int x = -2; x <= 2; x++)
            for (int y = -2; y <= 2; y++) {
                Tile tile = Tile.get(src.getX() + x, src.getY() + y, src.getZ(), false);
                if (tile!= null && tile.clipping == 0)
                    list.add(new Position(src.getX() + x, src.getY() + y, src.getZ()));
            }
        return list;
    }

    private boolean spawnSnakeling(int index) {
        if (target == null)
            return false;
        ArrayList<Position> possible = getPossibleSpawns(target.getPosition());
        if (possible.isEmpty())
            return false;
        Position pos = Random.get(possible);
        int x = pos.getX();
        int y = pos.getY();
        npc.animate(info.attack_animation);
        npc.faceNone(false);
        npc.face(x, y);
        int duration = SNAKELING_PROJECTILE.send(npc, x, y);
        npc.addEvent(event -> {
            event.delay(((duration * 25) / 600) - 1);
            if (isDead() || npc.isRemoved() || target == null)
                return;
            snakelings[index] = new NPC(2045).spawn(x, y, npc.getHeight(), 0);
            snakelings[index].targetPlayer(target.player, false);
            snakelings[index].attackTargetPlayer();
        });
        return true;
    }

    private void magicAttack() {
        npc.face(target);
        npc.animate(info.attack_animation);
        int duration = MAGIC_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(duration);
        target.hit(hit);
    }

    private void rangedAttack() {
        npc.face(target);
        npc.animate(info.attack_animation);
        int duration = RANGED_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(duration);
        target.hit(hit);
    }


    private void meleeAttack() {
        meleeTarget = getMeleeTargetPosition(target);
        npc.faceTemp(target);
        npc.animate(5806);
        transitioning.delay(6); // EHHHHH
        attacks += 2;
        npc.addEvent(event -> {
            event.delay(5);
            if (target == null || meleeTarget == null)
                return;
            if (Misc.getDistance(meleeTarget, target.getPosition()) <= 1) {
                target.hit(new Hit(npc, AttackStyle.STAB).randDamage(20, 41).ignorePrayer().ignoreDefence());
                target.stun(3, true);
            }
            meleeTarget = null;
        });
    }

    private Position getMeleeTargetPosition(Entity target) {
        Position pos = target.getPosition().copy();
        if (pos.getY() >= npc.getSpawnPosition().getY() - 1) { // sides
            pos.set(pos.getX(), Math.max(npc.getSpawnPosition().getY() + 2, pos.getY()), pos.getZ());
        } else { // center/bottom
            pos.set(npc.getSpawnPosition().getX() + 2, pos.getY(), pos.getZ());
        }
        return pos;
    }

    private boolean spawnFume(int offsetX, int offsetY) {
        final int x = npc.getSpawnPosition().getX() + offsetX;
        final int y = npc.getSpawnPosition().getY() + offsetY;
        if (Tile.getObject(11700, x, y, 0) != null)
            return false;
        npc.animate(info.attack_animation);
        npc.faceNone(false);
		npc.face(x, y);
        int duration = FUMES_PROJECTILE.send(npc, x+1, y+1);
        final Phase phase = getPhase();
        npc.addEvent(event -> {
            event.delay(((duration * 25) / 600) - 1);
            if (target == null)
                return;
            GameObject obj = GameObject.spawn(11700, x, y, 0, 10, 0);
            for (int i = 0; i < 25; i++) {
                if (obj.isRemoved() || isDead() || npc.isRemoved()) {
                    if (!obj.isRemoved())
                        obj.remove();
                    return;
                }
                if (phase != getPhase()) { // TODO - temp fix! come back and do it better later
                    if (!obj.isRemoved())
                        obj.remove();
                    return;
                }
                if (target != null && target.getAbsX() >= obj.x && target.getAbsX() <= obj.x + 2
                        && target.getAbsY() >= obj.y && target.getAbsY() <= obj.y + 2)
                    target.hit(new Hit(HitType.VENOM).randDamage(1, 4).ignoreDefence().ignorePrayer());
                event.delay(1);
            }
            if (!obj.isRemoved())
                obj.remove();
        });
        return true;
    }

    @Override
    public int getAggressionRange() {
        return 20;
    }

    @Override
    public void init() {
        transitioning.delay(6); // let spawn animation finish
        rotationId = Random.get(ROTATIONS.length - 1);
        rotation = ROTATIONS[rotationId];
        currentPhase = 0;
        npc.deathStartListener = (entity, killer, killHit) -> {
          for (NPC n : snakelings) {
              if (n != null && !n.isRemoved()) {
                  n.getCombat().startDeath(killHit);
              }
          }
        };
        npc.deathEndListener = (entity, killer, killHit) -> onDeath(killer.player);
        npc.hitListener = new HitListener();
        npc.hitListener.preTargetDamage(this::preTargetDamage);
        npc.hitListener.postDamage(this::onDamaged);
    }

    @Override
    public int getAttackBoundsRange() {
        return 32;
    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        if (transitioning.isDelayed())
            return false;
        if (++attacks >= (getPhase().form == Form.JAD ? 12 : 8) && !(getPhase().form == Form.MAGMA && meleeTarget != null)) {
            nextPhase();
            attacks = 0;
            return false;
        }
        if (getPhase().form == Form.NORMAL || getPhase().form == Form.TANZANITE || (getPhase().form == Form.MAGMA && meleeTarget == null)) {
            if (checkSnakelings() || checkFumes())
                return true;
        }
        switch (getPhase().form) {
            case NORMAL:
                rangedAttack();
                break;
            case TANZANITE:
                if (Random.get() < 0.5)
                    rangedAttack();
                else
                    magicAttack();
                break;
            case MAGMA:
                meleeAttack();
                break;
            case JAD:
                if (useRange)
                    rangedAttack();
                else
                    magicAttack();
                useRange = !useRange;
                break;
        }
        return true;
    }

    private void preTargetDamage(Hit hit, Entity target) {
        if (target != null && Random.get() < 0.2)
            target.envenom(6);
    }

    private void onDamaged(Hit hit) {
    }


    @Override
    public Position getDropPosition() {
        return getPhase().location.getDrop(npc);
    }

    @Override
    public void dropItems(Killer killer) {
        super.dropItems(killer);
        super.dropItems(killer);
    }


    private void onDeath(Player player) {
        GameObject.spawn(11701, npc.getSpawnPosition().getX() + 2, npc.getSpawnPosition().getY() - 3, npc.getSpawnPosition().getZ(), 10, 0); // exit
        onRemove();
        npc.remove();
        if (player != null && player.zulrahTimer != null)
            player.zulrahBestTime = player.zulrahTimer.stop(player, player.zulrahBestTime);
    }

    public void onRemove() {
        for (NPC npc : snakelings) {
            if (npc != null && !npc.isRemoved())
                npc.remove();
        }
    }
}