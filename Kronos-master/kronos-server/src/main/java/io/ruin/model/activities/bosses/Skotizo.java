package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;

import java.util.function.Function;

public class Skotizo extends NPCCombat { // Kourend catacombs boss
    private static int[] SKOTIZO_SPAWN = {29, 29};

    private static int[][] ALTAR_POSITIONS = {
            {32, 15},//s
            {14, 32},//w
            {30, 48},//n
            {50, 32},//e
    };

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1242, 120, 31, 25, 56, 10, 15, 220);


    private static final int DORMANT_ALTAR = 7289;
    private static final int AWAKENED_ALTAR = 7288;

    private static final int OBJ_DORMANT_ALTAR = 28924;
    private static final int OBJ_AWAKENED_ALTAR = 28923;

    private NPC[] altars;
    private GameObject[] altarObjects;

    private static void preAltarDamage(Hit hit) {
        if (hit.attacker != null && hit.attackWeapon != null && hit.attackWeapon.id == 19675) {
            hit.damage = 100;
        }
    }

    public static void startFight(Player player, GameObject altar) {
        player.lock();
        World.startEvent(event -> {
            altar.animate(1477);
            event.delay(6);
            altar.animate(1471);
        });
        player.startEvent(event -> {
            player.animate(3865);
            player.graphics(1296);
            event.delay(3);
            DynamicMap map = new DynamicMap().build(6810, 0);
            NPC skotizo = new NPC(7297).spawn(map.swRegion.baseX + SKOTIZO_SPAWN[0], map.swRegion.baseY + SKOTIZO_SPAWN[1], 0, Direction.SOUTH, 8);
            map.addNpc(skotizo);
            player.getMovement().teleport(map.convertX(1695), map.convertY(9878));
            player.openInterface(InterfaceType.PRIMARY_OVERLAY, 308);
            map.assignListener(player).onExit((p, logout) -> {
                if (logout) {
                    p.getMovement().teleport(1665, 10048, 0);
                } else {
                    p.closeInterface(InterfaceType.PRIMARY_OVERLAY);
                }
                map.destroy();
            });
            skotizo.targetPlayer(player, false);
            event.delay(1);
            player.animate(-1);
            player.unlock();
        });
    }

    @Override
    public void init() {
        altars = new NPC[4];
        altarObjects = new GameObject[altars.length];
        for (int i = 0; i < altars.length; i++) {
            final int index = i;
            altars[i] = new NPC(DORMANT_ALTAR).spawn(getAbsolute(ALTAR_POSITIONS[i][0], ALTAR_POSITIONS[i][1]));
            altarObjects[i] = GameObject.spawn(OBJ_DORMANT_ALTAR, altars[i].getAbsX(), altars[i].getAbsY(), altars[i].getHeight(), 10, i);
            altars[i].deathStartListener = (DeathListener.Simple) () -> deactivateAltar(index);
            altars[i].attackNpcListener = (player, npc1, message) -> npc1.getId() == AWAKENED_ALTAR;
            altars[i].hitListener = new HitListener().preDamage(Skotizo::preAltarDamage);
            npc.addEvent(event -> {
                event.delay(10);
                while (!isDead()) {
                    while (altars[index].getId() == AWAKENED_ALTAR) {
                        event.delay(1);
                    }
                    event.delay(Random.get(30, 90));
                    awakenAltar(index);
                }
            });

            altars[index].addEvent(event -> {
                while (!npc.isRemoved())
                    event.delay(2);
                altars[index].remove();
            });
        }

        npc.hitListener = new HitListener().preDefend(hit -> hit.boostDefence(0.25 * activeAltarCount()));
        npc.attackBounds = new Bounds(npc.getSpawnPosition(), 32);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        updateAltarStatus();
        if (!withinDistance(8))
            return false;
        if (withinDistance(1) && Random.rollDie(3, 2))
            basicAttack();
        else
            magicAttack();
        return true;
    }

    private void magicAttack() {
        npc.animate(69);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
        target.graphics(197, 0, delay);
    }

    private Position getAbsolute(int localX, int localY) {
        return new Position(npc.getPosition().getRegion().baseX + localX, npc.getPosition().getRegion().baseY + localY, npc.getPosition().getZ());
    }

    private void awakenAltar(int index) {
        if (altars[index].getId() == AWAKENED_ALTAR)
            return;
        altars[index].transform(AWAKENED_ALTAR);
        altarObjects[index].setId(OBJ_AWAKENED_ALTAR);
        altarObjects[index].animate(1472);
        updateAltarStatus();
    }

    private void deactivateAltar(int index) {
        if (altars[index].getId() == DORMANT_ALTAR)
            return;
        altars[index].transform(DORMANT_ALTAR);
        altarObjects[index].setId(OBJ_DORMANT_ALTAR);
        updateAltarStatus();
    }

    private void updateAltarStatus() {
        if (target == null || target.player == null)
            return;
        Function<NPC, Integer> status = altar -> altar.getId() == AWAKENED_ALTAR ? 1 : 0;
        target.player.getPacketSender().sendClientScript(1313, "iiii",
                status.apply(altars[1]),
                status.apply(altars[3]),
                status.apply(altars[0]),
                status.apply(altars[2])
        );
    }

    private int activeAltarCount() {
        int count = 0;
        for (NPC altar : altars)
            if (altar.getId() == AWAKENED_ALTAR) count++;
        return count;
    }
}