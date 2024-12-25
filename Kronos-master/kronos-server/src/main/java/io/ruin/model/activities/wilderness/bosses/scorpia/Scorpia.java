package io.ruin.model.activities.wilderness.bosses.scorpia;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.utility.TickDelay;

public class Scorpia extends NPCCombat {


    static {
        NPCDef.get(6615).ignoreOccupiedTiles = true; // so she doesnt get stuck on her babies
        NPCDef.get(6617).ignoreOccupiedTiles = true;
    }

    private boolean spawnedGuardians = false;
    private NPC[] guardians = new NPC[2];
    public static final Projectile HEAL_PROJECTILE = new Projectile(109, 43, 31, 51, 56, 10, 16, 64);;

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(this::spawnGuardians);
        npc.deathStartListener = (entity, killer, killHit) -> spawnedGuardians = false;
    }

    @Override
    public void startDeath(Hit killHit) {
        for (NPC guardian : guardians) {
            if (guardian != null && !guardian.isRemoved() && !guardian.getCombat().isDead())
                guardian.getCombat().startDeath(killHit);
        }
        super.startDeath(killHit);
    }

    private void spawnGuardians(Hit hit) {
        if (!spawnedGuardians && npc.getHp() < 100) {
            spawnedGuardians = true;
            Bounds bounds = new Bounds(npc.getAbsX(), npc.getAbsY(), npc.getAbsX() + npc.getSize() - 1, npc.getAbsY() + npc.getSize()
                     - 1, 0);
            for (int i = 0; i < guardians.length; i++) {
                guardians[i] = new NPC(6617).spawn(bounds.randomX(), bounds.randomY(), bounds.z);
                NPC guardian = guardians[i];
                guardian.face(npc);
                guardian.graphics(144, 20, 0);
                guardian.addEvent(event -> {
                    TickDelay timeout = new TickDelay();
                    timeout.delay(25);
                    TickDelay nextHeal = new TickDelay();
                    while (true) {
                        if (guardian.getCombat().isDead())
                            return;
                        if (!timeout.isDelayed()) {
                            guardian.remove();
                            return;
                        }
                        DumbRoute.step(guardian, npc, 3);
                        if (!DumbRoute.withinDistance(guardian, npc, 3)) {
                            event.delay(1);
                            continue;
                        }
                        timeout.delay(25);
                        if (!nextHeal.isDelayed()) {
                            nextHeal.delay(4);
                            guardian.animate(info.attack_animation);
                            HEAL_PROJECTILE.send(guardian, npc);
                            npc.incrementHp(Random.get(1, 10));
                        }
                        event.delay(1);
                    }
                });
            }
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        if (Random.rollDie(6, 1))
            target.poison(20);
        return true;
    }

    @Override
    public int getAttackBoundsRange() {
        return 20;
    }

  /*  @Override
    public void dropItems(Killer killer) {
        super.dropItems(killer);
        Wilderness.rewardBossDamage(npc, 1);
    }*/
}
