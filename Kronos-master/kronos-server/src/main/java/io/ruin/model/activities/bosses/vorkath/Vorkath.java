package io.ruin.model.activities.bosses.vorkath;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Misc;

import java.util.LinkedList;
import java.util.List;

public class Vorkath extends NPCCombat {

    private static final int BREATH_START_HEIGHT = 25;
    private static final int BREATH_END_HEIGHT = 31;
    private static final int BREATH_DELAY = 30;
    private static final int BREATH_DURATION_START = 46;
    private static final int BREATH_DURATION_INCREMENT = 8;
    private static final int BREATH_CURVE = 15;
    private static final int BREATH_OFFSET = 255;
    private static final int TILE_OFFSET = 1;

    private static final Projectile VENOM_PROJECTILE = new Projectile(1470, BREATH_START_HEIGHT, BREATH_END_HEIGHT, BREATH_DELAY, BREATH_DURATION_START, BREATH_DURATION_INCREMENT, BREATH_CURVE, BREATH_OFFSET).tileOffset(TILE_OFFSET);
    private static final Projectile PURPLE_PROJECTILE = new Projectile(1471, BREATH_START_HEIGHT, BREATH_END_HEIGHT, BREATH_DELAY, BREATH_DURATION_START, BREATH_DURATION_INCREMENT, BREATH_CURVE, BREATH_OFFSET).tileOffset(TILE_OFFSET);
    private static final Projectile ICE_PROJECTILE = new Projectile(395, BREATH_START_HEIGHT, BREATH_END_HEIGHT, BREATH_DELAY, BREATH_DURATION_START, BREATH_DURATION_INCREMENT, BREATH_CURVE, BREATH_OFFSET).tileOffset(TILE_OFFSET);
    private static final Projectile FIRE_PROJECTILE = new Projectile(393, BREATH_START_HEIGHT, BREATH_END_HEIGHT, BREATH_DELAY, BREATH_DURATION_START, BREATH_DURATION_INCREMENT, BREATH_CURVE, BREATH_OFFSET).tileOffset(TILE_OFFSET);

    private static final Projectile RANGED_PROJECTILE = new Projectile(1477, BREATH_START_HEIGHT, BREATH_END_HEIGHT, BREATH_DELAY, BREATH_DURATION_START, BREATH_DURATION_INCREMENT, BREATH_CURVE, BREATH_OFFSET).tileOffset(TILE_OFFSET);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(1479, BREATH_START_HEIGHT, BREATH_END_HEIGHT, BREATH_DELAY, BREATH_DURATION_START, BREATH_DURATION_INCREMENT, BREATH_CURVE, BREATH_OFFSET).tileOffset(TILE_OFFSET);

    private static final Projectile UP_FIREBALL_PROJECTILE = new Projectile(1481, BREATH_START_HEIGHT + 10, 0, BREATH_DELAY + 20, 180 - BREATH_DELAY, 0, 55, BREATH_OFFSET);
    private static final Projectile ZOMBIE_SPAWN_PROJECTILE = new Projectile(1484, BREATH_START_HEIGHT + 10, 0, BREATH_DELAY + 20, 180 - BREATH_DELAY, 0, 40, 0);
    private static final Projectile POISON_PROJECTILE = new Projectile(1483, BREATH_START_HEIGHT + 15, 0, BREATH_DELAY - 10, 105 - BREATH_DELAY, 0, 45, 0);

    private static final Projectile FIREBALL_PROJECTILE = new Projectile(1482, BREATH_START_HEIGHT, 0, 0, 30, 0, BREATH_CURVE, BREATH_OFFSET);
    public static final int BREATH_ANIM = 7952;

    private static final Bounds ZOMBIE_SPAWN = new Bounds(5077, 10839, 5098, 10860, -1);
    private static final Position SPAWN_POSITION = new Position(2269, 4062, 0);

    static {
        NPCAction.register(8059, "poke", (player, npc) -> {
            if (npc.isLocked() || npc.getId() != 8059)
                return;
            player.animate(827);
            npc.lock();
            npc.addEvent(event -> {
                npc.animate(7950);
                event.delay(6);
                npc.transform(8061);
                event.delay(2);
                npc.unlock();
                npc.getCombat().setTarget(player);
                npc.face(player);
            });
        });
        ObjectAction.register(31990, 1, (player, obj) -> createAndEnter(player));
    }

    public static void createAndEnter(Player player) {
        DynamicMap map = new DynamicMap().build(9023, 1);
        NPC vorkath = new NPC(8059).spawn(map.convertX(SPAWN_POSITION.getX()), map.convertY(SPAWN_POSITION.getY()), 0, Direction.SOUTH, 0);
        map.addNpc(vorkath);
        player.startEvent(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.getMovement().teleport(map.convertPosition(player.getPosition()));
            event.delay(1);
            player.animate(839);
            player.getMovement().force(0, 2, 0, 0, 0, 60, Direction.NORTH);
            event.delay(2);
            map.assignListener(player).onExit((p, logout) -> {
                if (logout)
                    p.getMovement().teleport(2272, 4052, 0);
                p.deathEndListener = null;
                map.destroy();
            });
            player.unlock();
        });
        ObjectAction exitAction = (p, obj) -> {
            p.startEvent(event -> {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(839);
                p.getMovement().force(0, -2, 0, 0, 0, 60, Direction.SOUTH);
                event.delay(2);
                p.getMovement().teleport(map.revertPosition(p.getPosition()));
                p.unlock();
            });
        };
        ObjectAction.register(31990, map.convertX(2272), map.convertY(4053), 0, "climb-over", exitAction);
        ObjectAction.register(31990, map.convertX(2272), map.convertY(4053), 0, "climb-over", exitAction);
    }

    /**
     * Info
     *
     * NPC Ids:
     * 8058 - Sleeping, no options (doesn't fight, just there for scenery)
     * 8059 - Sleeping, poke option (initial form)
     * 8060 - Active form, level 392 (quest)
     * 8061 - Active form, level 732 (post-quest, this is what we use)
     *
     * 8062 - Zombified spawn, weaker form (quest)
     * 8063 Zombified spawn, stronger form (post-quest)
     *
     *
     * Animations:
     *  7946 - Sleeping idle
     *  7947 - Walk
     *  7948 - Awaken, idle
     *  7949 - Death
     *  7950 - Waking up
     *  7951 - melee attack
     *  7952 - Breath attack, forward
     *  7953 - Raises front legs? not sure
     *  7954 - Entering a block state
     *  7955 - Block state loop
     *  7956 - Exiting block state
     *  7957 - Upwards breath attack, followed by a channeled breath forward (acid pool attack)
     *
     * GFX:
     *  1470 - Venom breath projectile
     *  1471 - Purple breath projectile (disables prayers)
     *  1472 - Venom breath explosion
     *  1473 - Purple breath explosion
     *  1477 - Ranged attack projectile
     *  1478 - Ranged attack explosion
     *  1479 - Magic attack projectile
     *  1480 - Magic attack explosion
     *  1481 - Fireball attack projectile (the one he shoots upwards)
     *  1482 - A smaller fireball (fired repeatedly during poison pool phase)
     *  1483 - Acid pool projectile
     *  1484 - Zombified spawn projectile
     *  1485 - Some grey thing idk, looks like a projectile
     *
     * Objects:
     *  32000 - Acid pool
     *
     */

    private enum Resistance {
        PARTIAL, FULL
    }

    private Resistance resistance = null;

    private int attackCounter = 0;
    private boolean specialType = Random.rollDie(2, 1);

    @Override
    public void init() {
        npc.deathEndListener = (entity, killer, killHit) ->  {
            npc.transform(8059);
            attackCounter = 0;
            specialType = Random.rollDie(2, 1);
        };
        npc.hitListener = new HitListener().postDefend(this::postDefend);
    }


    private void postDefend(Hit hit) {
        if (resistance == Resistance.FULL) {
            hit.block();
            if (hit.attacker.player != null)
                hit.attacker.player.sendMessage("Vorkath is currently immune to your attacks!");
        } else if (resistance == Resistance.PARTIAL) {
            hit.damage /= 2;
        }
    }


    @Override
    public void follow() {
        follow(30);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(16))
            return false;
        if (resistance != null) { // doing a special attack, dont use regular attacks
            return true;
        }
        if (attackCounter > 1 && attackCounter % 6 == 0) {
            if (specialType)
                poisonAttack();
            else
                zombieSpawn();
            specialType = !specialType;
            attackCounter = 0;
            return true;
        }
        if (withinDistance(1) && Random.rollDie(3, 1)) {
            basicAttack();
        } else {
            int roll = Random.get(100);
            if (roll > 90)
                fireballAttack();
            else if (roll > 80)
                venomBreath();
            else if (roll > 70)
                purpleBreath();
            else if (roll > 60)
                fireBreath();
            else if (roll > 30)
                magicAttack();
            else
                rangedAttack();
        }
        attackCounter++;
        return true;
    }

    private void fireballAttack() {
        npc.animate(7960);
        Position targetPos = target.getPosition().copy();
        int delay = UP_FIREBALL_PROJECTILE.send(npc, targetPos.getX(), targetPos.getY());
        World.sendGraphics(157, 20, delay, targetPos);
        npc.addEvent(event -> {
            event.delay(3);
            if (isDead()) // lets be nice
                return;
            if (target != null && target.getPosition().isWithinDistance(targetPos, 1))
                target.hit(new Hit(npc).randDamage(20, 60).delay(0));
        });

    }

    @Override
    public boolean isAggressive() {
        return npc.getId() != 8059; // not sleeping
    }

    private void venomBreath() {
        npc.animate(BREATH_ANIM);
        int delay = VENOM_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(60).penetrateDragonfireResistance(0.2).clientDelay(delay));
        target.graphics(1472, 92, delay);
        if (Random.rollDie(4, 3))
            target.envenom(6);
    }

    private void fireBreath() {
        npc.animate(BREATH_ANIM);
        int delay = FIRE_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(80).penetrateDragonfireResistance(0.3).clientDelay(delay));
        target.graphics(157, 92, delay);
    }

    private void purpleBreath() {
        npc.animate(BREATH_ANIM);
        int delay = PURPLE_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(70).penetrateDragonfireResistance(0.2).clientDelay(delay).postDamage(entity -> {
            if (entity.player != null) {
                entity.player.getPrayer().deactivateAll();
                entity.player.sendMessage(Color.RED.wrap("Your prayers have been disabled!"));
            }
        }));
        target.graphics(1473, 92, delay);
    }

    private void magicAttack() {
        npc.animate(BREATH_ANIM);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(32).clientDelay(delay));
        target.graphics(1480, 92, delay);
    }

    private void rangedAttack() {
        npc.animate(BREATH_ANIM);
        int delay = RANGED_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(32).clientDelay(delay));
        target.graphics(1478, 92, delay);
    }

    private void zombieSpawn() {
        resistance = Resistance.FULL;
        npc.animate(7960);
        int delay = ICE_PROJECTILE.send(npc, target);
        target.graphics(369, 0, delay);
        target.freeze(30, npc);
        Position pos = Random.get(npc.getPosition().area(7, position ->
                position.getTile().clipping == 0
                        && position.isWithinDistance(target.getPosition(), 10)
                        && !position.isWithinDistance(target.getPosition(), 2)
                        && npc.getSpawnPosition().getY() - position.getY() <= 8));
        ZOMBIE_SPAWN_PROJECTILE.send(npc, pos.getX(), pos.getY());
        npc.addEvent(event -> {
            final Entity entity = target;
            event.delay(3);
            NPC spawn = new NPC(8063).spawn(pos);
            spawn.getCombat().setTarget(entity);
            spawn.face(entity);
            while (!spawn.getCombat().isDead() && !spawn.isRemoved() && !isDead() && !npc.isRemoved()) {
                event.delay(1);
            }
            resistance = null;
            entity.resetFreeze();
        });
    }

    private void poisonAttack() {
        npc.animate(7957);
        resistance = Resistance.PARTIAL;
        target.addEvent(event -> { // may seem odd that this event is on the target but it's because players are processed before npcs, if the event is on the npc then it will not look right
            final Entity entity = target;
            List<Position> positions = npc.getSpawnPosition().relative(3, 3).area(11, p -> p.getTile().clipping == 0);
            List<GameObject> pools = new LinkedList<>();
            pools.add(new GameObject(32000, target.getPosition().copy(), 10, Random.get(3)));
            Position srcPos = Misc.getClosestPosition(npc, target);
            POISON_PROJECTILE.send(srcPos, target.getPosition());
            for (int i = 0; i < 39; i++) {
                Position pos = positions.remove(Random.get(positions.size() - 1));
                pools.add(new GameObject(32000, pos, 10, Random.get(3)));
                POISON_PROJECTILE.send(srcPos, pos);
            }
            event.delay(2);
            pools.forEach(GameObject::spawn);
            npc.addEvent(dmgEvent -> {
                while (resistance == Resistance.PARTIAL && !isDead()) {
                    if (entity.getPosition().getTile().getObject(32000, 10, -1) != null) {
                        int dmg = entity.hit(new Hit().randDamage(1, 10).delay(0));
                        npc.incrementHp(dmg);
                        dmgEvent.delay(1);
                    }
                    dmgEvent.delay(1);
                }
            });
            for (int i = 0; i < 25; i++) {
                if (isDead() || npc.isRemoved() || target != entity)
                    break;
                Position pos = target.getPosition().copy();
                int delay = FIREBALL_PROJECTILE.send(npc, pos);
                World.sendGraphics(131, 0, delay, pos);
                Hit hit = new Hit();
                    entity.hit(hit.randDamage(25).delay(2).preDamage(e -> {
                        if (!e.getPosition().equals(pos)) {
                            hit.damage = 0;
                            hit.hide();
                        }
                    }));
                event.delay(1);
            }
            pools.forEach(GameObject::remove);
            resistance = null;
        });
    }

    @Override
    public Position getDropPosition() {
        return npc.getSpawnPosition().relative(3, -2);
    }

    @Override
    public int getRandomDropCount() {
        return 2;
    }
}
