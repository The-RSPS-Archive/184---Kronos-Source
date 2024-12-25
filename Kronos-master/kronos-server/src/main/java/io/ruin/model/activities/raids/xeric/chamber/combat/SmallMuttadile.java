package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.activities.raids.xeric.chamber.impl.MuttadilesChamber;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

public class SmallMuttadile extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(1291, 20, 31, 20, 15, 12, 15, 10);

    static {
        NPCDef.get(7562).ignoreOccupiedTiles = true;
    }

    public MuttadilesChamber chamber;
    private int triedHealCount = 0;
    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(hit -> {
            if (npc.getHp() < npc.getMaxHp() / 2 && Random.rollPercent(30) && triedHealCount < 3 && chamber.getTreeHealth() > 0)
                tryHeal();
        });
    }

    @Override
    public void follow() {
        follow(1);
    }

    private void tryHeal() {
        reset();
        triedHealCount++;
        npc.addEvent(event -> {
            npc.lock(LockType.FULL);
            npc.faceNone(false);
            int failedTicks = 0;
            while (true) {
                npc.getRouteFinder().routeEntity(chamber.getTree());
                npc.face(chamber.getTree());
                if (chamber.getTreeHealth() <= 0) {
                    npc.faceNone(false);
                    TargetRoute.reset(npc);
                    npc.unlock();
                    return;
                }
                if (Misc.getEffectiveDistance(npc, chamber.getTree()) <= 1) {
                    npc.animate(7420);
                    event.delay(1);
                    chamber.damageTree(chamber.getTreeHealth());
                    npc.setHp(npc.getMaxHp());
                    npc.hitsUpdate.forceSend(npc.getHp(), npc.getMaxHp());
                    event.delay(3);
                } else {
                    if (++failedTicks >= 25) { // give up
                        npc.faceNone(false);
                        TargetRoute.reset(npc);
                        npc.unlock();
                        return;
                    }
                    event.delay(1);
                }
            }
        });
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8) || npc.isLocked())
            return false;
        npc.face(target);
        if (withinDistance(1) && Random.rollDie(3, 2))
            basicAttack();
        else
            rangedAttack();
        return true;
    }

    private void rangedAttack() {
        int maxDamage = 35;
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES))
            maxDamage *= 0.6;
        npc.animate(7421);
        int delay = RANGED_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(maxDamage).clientDelay(delay).ignorePrayer();
        target.hit(hit);
    }

    @Override
    public int getAggressionRange() {
        return 20;
    }
}
