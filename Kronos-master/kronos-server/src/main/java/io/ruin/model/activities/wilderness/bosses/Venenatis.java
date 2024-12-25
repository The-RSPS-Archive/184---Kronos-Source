package io.ruin.model.activities.wilderness.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.stat.StatType;

public class Venenatis extends NPCCombat {

    public static final Projectile WEB_PROJECTILE = new Projectile(1254, 45, 0, 75, 46, 10, 16, 64);
    public static final Projectile MAGIC_PROJECTILE = new Projectile(165, 23, 21, 40, 46, 10, 0, 64);
    public static final Projectile DRAIN_PROJECTILE = new Projectile(171, 36, 31, 48, 56, 6, 16, 64);


    @Override
    public void init() {
        npc.addEvent(event -> {
            while (true) {
                drain();
                event.delay(1);
            }
        });
        npc.hitListener = new HitListener().postDefend(h -> {
            if (!npc.inMulti())
                h.damage *= 0.15; // NOTE: Fighting Venenatis while she is in a single-way area will only deal 1-6 damage REGARDLESS of the player's equipment and stats. Full damage will only be dealt if she is in a multi-way area.
        });
    }

    private void drain() {
        if (target == null || target.player == null || !Random.rollDie(30, 1))
            return;
        target.graphics(172, 92, 0);
        DRAIN_PROJECTILE.send(target, npc);
        int current = target.player.getStats().get(StatType.Prayer).currentLevel;
        target.player.getPrayer().drain((int) (10 + ((current + 1) / 5.0)));
        if (current > 0)
            target.player.sendMessage("Your prayer was drained!");
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(2) && Random.rollDie(3, 1)) { // melee has "halberd" range
            basicAttack();
        } else {
            magicAttack();
        }
        if (Random.rollDie(14, 1)) { // can stack with melee/mage attack
            webAttack();
        }
        if (Random.rollDie(8, 1)) {
            target.poison(8);
        }
        return true;
    }

    private void magicAttack() {
        npc.graphics(164);
        if (npc.inMulti()) {
            npc.animate(5322);
            for (Player p : npc.localPlayers()) {
                if ((p == target || p.inMulti()) && DumbRoute.withinDistance(npc, p, 8)) {
                    int delay = MAGIC_PROJECTILE.send(npc, target);
                    target.hit(new Hit(npc, AttackStyle.MAGIC, null).randDamage(50).clientDelay(delay));
                }
            }
        } else {
            projectileAttack(MAGIC_PROJECTILE, 5322, AttackStyle.MAGIC, 50);
        }
    }

    private void webAttack() {
        npc.animate(5322);
        int delay = WEB_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, null, null).ignoreDefence().ignorePrayer().randDamage(50).clientDelay(delay));
        target.stun(2, true);
        if (target.player != null) {
            target.player.sendMessage("Venenatis hurls her web at you, sticking you to the ground.");
        }
    }

/*    @Override
    public void dropItems(Killer killer) {
        super.dropItems(killer);
        Wilderness.rewardBossDamage(npc, 1.1);
    }*/

    @Override
    public int getAttackBoundsRange() {
        return 17;
    }
}
