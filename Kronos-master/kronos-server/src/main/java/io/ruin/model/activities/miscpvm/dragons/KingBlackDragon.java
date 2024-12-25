package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

import java.util.Arrays;

public class KingBlackDragon extends NPCCombat {

    private static final Projectile FIRE_PROJECTILE = new Projectile(393, 43, 31, 51, 56, 10, 15, 250);
    private static final Projectile POISON_PROJECTILE = new Projectile(394, 43, 31, 51, 56, 10, 15, 250);
    private static final Projectile SHOCK_PROJECTILE = new Projectile(395, 43, 31, 51, 56, 10, 15, 250);
    private static final Projectile FREEZE_PROJECTILE = new Projectile(396, 43, 31, 51, 56, 10, 15, 250);

    private static final StatType[] SHOCK_STATS = {
            StatType.Attack, StatType.Strength, StatType.Defence,
            StatType.Ranged, StatType.Magic
    };


    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1) && Random.rollDie(4, 1))
            basicAttack();
        else {
            if (Random.rollDie(2, 1))
                fire(FIRE_PROJECTILE, 0);
            else switch (Random.get(3)) {
                case 1:
                    fire(FREEZE_PROJECTILE, 10);
                    if (Random.rollDie(3, 1))
                        target.freeze(3, npc);
                    break;
                case 2:
                    fire(SHOCK_PROJECTILE, 12);
                    if (target.player != null && Random.rollDie(3, 1))
                        Arrays.stream(SHOCK_STATS).forEach(s -> target.player.getStats().get(s).drain(2));
                    break;
                case 3:
                    fire(POISON_PROJECTILE, 10);
                    if (Random.rollDie(3, 1))
                        target.poison(8);
                    break;
            }
        }
        return true;
    }

    private void fire(Projectile proj, int minMaxDamage) {
        npc.animate(81);
        minMaxDamage = (int) Math.max(65 * (1 - target.getCombat().getDragonfireResistance()), minMaxDamage);
        int delay = proj.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(minMaxDamage).ignoreDefence().clientDelay(delay);
        if (minMaxDamage == 0)
            hit.block();
        target.hit(hit);
    }
}
