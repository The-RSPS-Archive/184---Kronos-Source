package io.ruin.model.activities.inferno.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;

public class JalAk extends NPCCombat { //Blob

    private static final Projectile RANGED_PROJECTILE = new Projectile(1380, 50, 31, 10, 40, 8, 16, 128);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(1378, 50, 31, 10, 40, 8, 16, 128);


    private AttackStyle nextAttack;

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (nextAttack == null) {
            read(); // we don't actually attack here, just check what prayer the player is using so we can prepare the next attack
        } else {
            if (withinDistance(1) && Random.rollPercent(33)) {
                basicAttack();
            } else if (nextAttack == AttackStyle.MAGIC) {
                projectileAttack(MAGIC_PROJECTILE, 7583, AttackStyle.MAGIC, info.max_damage);
            } else if (nextAttack == AttackStyle.RANGED) {
                projectileAttack(RANGED_PROJECTILE, 7583, AttackStyle.RANGED, info.max_damage);
            }
            nextAttack = null;
        }
        return true;
    }

    private void read() {
        if (target.player == null) {
            if (Random.rollPercent(50))
                nextAttack = AttackStyle.MAGIC;
            else
                nextAttack = AttackStyle.RANGED;
        } else if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
            nextAttack = AttackStyle.RANGED;
        } else if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
            nextAttack = AttackStyle.MAGIC;
        } else {
            if (Random.rollPercent(50))
                nextAttack = AttackStyle.MAGIC;
            else
                nextAttack = AttackStyle.RANGED;
        }
    }

}
