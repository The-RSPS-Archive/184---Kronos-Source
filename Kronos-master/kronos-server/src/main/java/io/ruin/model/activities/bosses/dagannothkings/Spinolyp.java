package io.ruin.model.activities.bosses.dagannothkings;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;

public class Spinolyp extends NPCCombat {


    private static final Projectile RANGED_PROJECTILE = new Projectile(476, 10, 31, 15, 25, 10, 15, 0);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(124, 10, 31, 15, 25, 10, 15, 0);


    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(this::postDamage);
    }

    private void postDamage(Hit hit) {
        if (!npc.isLocked() && !hit.isBlocked() && Random.rollDie(3))
            dive();
    }

    @Override
    public void follow() {
        //???
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10)) {
            reset();
            return false;
        }
        if (Random.rollDie(10, 6)) {
            if (projectileAttack(RANGED_PROJECTILE, info.attack_animation, AttackStyle.RANGED, info.max_damage).damage > 0 && Random.rollDie(3)) {
                target.poison(6);
            }
        } else {
            if (projectileAttack(MAGIC_PROJECTILE, info.attack_animation, AttackStyle.MAGIC, info.max_damage).damage > 0 && target.player  != null) {
                target.player.getPrayer().drain(1);
            }
        }
        if (Random.rollDie(7))
            dive();
        return true;
    }

    private void dive() {
        npc.startEvent(event -> {
            npc.lock(LockType.FULL_NULLIFY_DAMAGE);
            event.delay(1);
            int x = npc.getAbsX() + Random.get(-2, 2);
            int y = npc.getAbsY() + Random.get(-2, 2);
            Tile dest = Tile.get(x, y, npc.getHeight(), false);
            if (dest == null || dest.clipping != (1 << 21)) {
                npc.unlock();
                return;
            }
            npc.animate(2866);
            event.delay(1);
            npc.transform(5946);
            event.delay(5);
            npc.getMovement().teleport(x, y, npc.getHeight());
            npc.transform(5947);
            npc.animate(2864);
            event.delay(1);
            npc.unlock();
        });
    }

    @Override
    public int getAggressionRange() {
        return 10;
    }

    @Override
    public int getAttackBoundsRange() {
        return 20;
    }
}
