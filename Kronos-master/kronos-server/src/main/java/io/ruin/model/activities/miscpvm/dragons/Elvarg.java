package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Elvarg extends ChromaticDragon {

    private static final Position SPAWN_POSITION = new Position(2854, 9637, 0);
    private static final Projectile LAVA_PROJECTILE = new Projectile(660, 0, 0, 0, 100, 30, 45, 0);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(130, 10, 31, 40, 56, 10, 16, 127);
    private static final Projectile RANGED_DRAGONFIRE = new Projectile(393, 37, 32, 50, 60, 5, 24, 200);
    private Position ripTile = null;
    private int ripDir = -1;

    private List<Position> lavaSources;
    private List<GameObject> fires;

    @Override
    public void init() {
        super.init();
        fires = new ArrayList<>(9);
        lavaSources = Arrays.asList(
                npc.spawnPosition.relative(-5, 10),
                npc.spawnPosition.relative(8, 9),
                npc.spawnPosition.relative(10, 5),
                npc.spawnPosition.relative(10, 0),
                npc.spawnPosition.relative(11, -4),
                npc.spawnPosition.relative(6, -7),
                npc.spawnPosition.relative(1, -11)
                );
        npc.deathStartListener = (entity, killer, killHit) -> {
            if (killer.player != null) {
                if(killer.player.isLocked())
                    return;
                killer.player.lock(LockType.FULL_NULLIFY_DAMAGE);
            }
            ripTile = findFacing();
            npc.addEvent(event -> {
                event.delay(3);
                npc.face(ripTile.getX(), ripTile.getY());
            });
        };
        npc.deathEndListener = (entity, killer, killHit) -> {
            if (killer != null) {
                GameObject existing = Tile.getObject(-1, npc.getAbsX(), npc.getAbsY(), npc.getHeight(), 10, -1);
                if (existing != null && !existing.isRemoved())
                    existing.remove();
                GameObject corpse = GameObject.spawn(25202, npc.getAbsX(), npc.getAbsY(), npc.getHeight(), 10, ripDir);
                killer.player.startEvent(event -> {
                    killer.player.getRouteFinder().routeAbsolute(ripTile.getX(), ripTile.getY());
                    event.waitForMovement(killer.player);
                    event.delay(1);
                    killer.player.face(corpse);
                    killer.player.animate(6654);
                    event.delay(4);
                    corpse.setId(25203);
                    killer.player.animate(6655);
                    killer.player.getInventory().add(11279, 1);
                    killer.player.sendMessage("You take Elvarg's head!");
                    ObjectAction.register(corpse, 1, (player, obj) -> loot(player, npc, obj));
                    event.delay(4);
                    killer.player.unlock();
                });
            }
            fires.forEach(obj -> {
                if (!obj.isRemoved())
                    obj.remove();
            });
            npc.remove();
        };
        npc.addEvent(event -> {
            while (!isDead() && !npc.isRemoved()) {
                doFireDamage();
                event.delay(1);
            }
        });
        npc.hitListener = new HitListener().postDamage(this::postDamage);
    }

    private void postDamage(Hit hit) {
        if (isDead())
            return;
        double chance = 0.1 + ((npc.getMaxHp() - npc.getHp()) * 0.005);
        if (hit.damage > 5 && fires.size() < 90 && Random.get() < chance) {
            shootLava();
        }
    }

    private void doFireDamage() {
        if (target == null || fires.size() == 0)
            return;
        if (fires.stream().anyMatch(obj -> target.getPosition().equals(obj.x, obj.y))) {
            target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(10, 25).penetrateDragonfireResistance(0.2));
        }
    }

    private Position closestLavaSource() {
        if (target == null)
            return null;
        return lavaSources.stream()
                .reduce((position, position2) -> Misc.getDistance(target.getPosition(), position2) < Misc.getDistance(target.getPosition(), position) ? position2 : position)
                .orElse(Random.get(lavaSources));
    }

    private void shootLava() {
        Position source = closestLavaSource();
        if (target == null || source == null)
            return;
        npc.addEvent(event -> {
            final Entity entity = target;
            Position pos = entity.getPosition().copy();
            int delay = LAVA_PROJECTILE.send(source.getX(), source.getY(), pos.getX(), pos.getY());
            World.sendGraphics(157, 45, delay, pos);
            event.delay((delay * 25) / 600);
            if (isDead() || npc.isRemoved() || target == null)
                return;
            if (target.getPosition().equals(pos))
                target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(30, 60).penetrateDragonfireResistance(0.3));
        });
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1) && Random.rollDie(3, 1))
            basicAttack();
        else if (withinDistance(1) && Random.rollDie(3, 1))
            meleeDragonfire();
        else if (Random.rollDie(2, 1))
            magicAttack();
        else
            rangedDragonfire();
        return true;
    }

    @Override
    void meleeDragonfire() {
        npc.animate(81);
        npc.graphics(1, 100, 0);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(20, 50).penetrateDragonfireResistance(0.2).postDamage(this::burnGround));
    }

    @Override
    public Hit basicAttack() {
        Hit mainHit = super.basicAttack();
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage((int) (info.max_damage * 1.5)).penetrateDragonfireResistance(0.2));
        return mainHit;
    }

    private void magicAttack() {
        npc.animate(6722);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay);
        int damage = target.hit(hit);
        if (damage > 0)
            target.graphics(346, 124, delay);
        else {
            target.graphics(85, 124, delay);
            hit.hide();
        }
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage((int) (info.max_damage * 1.5)).penetrateDragonfireResistance(0.2));
    }


    private void rangedDragonfire() {
        npc.animate(81);
        int delay = RANGED_DRAGONFIRE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(50).penetrateDragonfireResistance(0.2).clientDelay(delay)
                .postDamage(this::burnGround));
    }

    private void burnGround(Entity entity) {
        if (Random.get() > 0.6)
            return;
        entity.getPosition().area(1).forEach(pos -> {
            GameObject obj = Tile.getObject(-1, pos.getX(), pos.getY(), pos.getZ(), 10, -1);
            if (Tile.allowObjectPlacement(pos.getX(), pos.getY(), npc.getHeight()) || (obj != null && (obj.id == 25065 || (obj.id >= 25087 && obj.id <= 25089)))) {
                fires.add(GameObject.spawn(732, pos.getX(), pos.getY(), npc.getHeight(), 10, 0));
            }
        });
    }

    @Override
    public void dropItems(Killer killer) {
        // don't drop items, wait to loot corpse :)
    }

    public static void loot(Player player, NPC npc, GameObject corpse) {
        List<Item> items = NPCDef.get(6118).lootTable.rollItems(true);
        player.startEvent(event -> {
            player.lock();
            player.animate(827);
            event.delay(1);
            for (Item item: items) {
                player.getInventory().addOrDrop(item.getId(), item.getAmount());
            }
            if (player.getInventory().isFull()) {
                player.sendMessage(Color.ORANGE_RED.wrap("WARNING: Items left on the ground will be lost when you leave the instance."));
            }
            if (!corpse.isRemoved())
                corpse.remove();
            player.sendMessage("You loot Elvarg's corpse. She will not respawn until you leave her lair.");
            player.unlock();
        });
    }

    private Position findFacing() {

        Position position = new Position(npc.getAbsX() - 1, npc.getAbsY() + 1, npc.getHeight());
        Tile tile = position.getTile();
        if (tile == null || tile.clipping == 0) {
            ripDir = 1;
            return position; // west
        }
        position = new Position(npc.getAbsX() + 2, npc.getAbsY() - 1, npc.getHeight());
        tile = position.getTile();
        if (tile == null || tile.clipping == 0) {
            ripDir = 0;
            return position; // south
        }
        position = new Position(npc.getAbsX() + 1, npc.getAbsY() + 4, npc.getHeight());
        tile = position.getTile();
        if (tile == null || tile.clipping == 0) {
            ripDir = 2;
            return position; // north
        }
        ripDir = 3;
        return new Position(npc.getAbsX() + 4, npc.getAbsY() + 2, npc.getHeight()); // east

    }



    public static void createAndEnter(Player player) {
        DynamicMap map = new DynamicMap().build(11414, 1);
        NPC elvarg = new NPC(6118).spawn(map.convertX(SPAWN_POSITION.getX()), map.convertY(SPAWN_POSITION.getY()), 0, Direction.SOUTH, 10);
        map.addNpc(elvarg);
        player.startEvent(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.getMovement().teleport(map.convertPosition(player.getPosition()));
            event.delay(1);
            player.animate(839);
            player.getMovement().force(2, 0, 0, 0, 0, 60, Direction.EAST);
            event.delay(2);
            player.sendFilteredMessage("You enter the dragon's lair. Good luck.");
            player.sendMessage("<col=ff0000>WARNING: This is an instanced area. Items not protected on death WILL be lost.");
            map.assignListener(player).onExit((p, logout) -> {
                if (logout)
                    p.getMovement().teleport(2845, 9636, 0);
                map.destroy();
            });
            player.unlock();
        });
        ObjectAction exitAction = (p, obj) -> {
            p.startEvent(event -> {
                        p.lock(LockType.FULL_DELAY_DAMAGE);
                        p.animate(839);
                        p.getMovement().force(-2, 0, 0, 0, 0, 60, Direction.WEST);
                        event.delay(2);
                        p.getMovement().teleport(map.revertPosition(p.getPosition()));
                        p.unlock();
                    });
        };
        ObjectAction.register(25161, map.convertX(2846), map.convertY(9636), 0, "climb-over", exitAction);
        ObjectAction.register(25161, map.convertX(2846), map.convertY(9635), 0, "climb-over", exitAction);
    }

    private static int getBones(Player player) {
        int level = player.getStats().get(StatType.Crafting).currentLevel;
        int bones = 2;
        if (player.getStats().get(StatType.Crafting).fixedLevel == 99)
            bones++;
        if (level >= 70) {
            bones += (level - 60) / 10;
        }
        return bones;
    }

    private static int getHides(Player player) {
        return getBones(player) * 2;
    }

    private static void lootHead(Player player, Item primary, Item secondary) {
        int bones = getBones(player);
        int hides = getHides(player);
        if (player.getInventory().getFreeSlots() < bones + hides - 1) {
            player.sendMessage("You will need at least " + (bones + hides - 1) + " free inventory spaces to do this.");
            return;
        }
        primary.remove();
        player.getInventory().add(536, bones);
        player.getInventory().add(1753, hides);
        player.getStats().addXp(StatType.Crafting, 1500, false);
    }

    static {
        ObjectAction.register(25161, "climb-over", (player, obj) -> {
            createAndEnter(player);
        });
        ItemItemAction.register(11279, Tool.CHISEL, Elvarg::lootHead);
    }
}
