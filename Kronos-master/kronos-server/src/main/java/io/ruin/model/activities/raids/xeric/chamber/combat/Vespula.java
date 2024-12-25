package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;

public class Vespula extends NPCCombat {

    private static final int GROUNDED = 7532;
    private static final int FLYING = 7530;

    private static final Projectile TILE_PROJECTILE_FLYING = new Projectile(1486, 70, 0, 40, 80, 0, 30, 0);
    private static final Projectile TILE_PROJECTILE_GROUNDED = new Projectile(1486, 40, 0, 40, 80, 0, 30, 0);
    private static final Projectile PROJECTILE_FLYING = new Projectile(1486, 70, 43, 30, 30, 5, 30, 0);
    private static final Projectile PROJECTILE_GROUNDED = new Projectile(1486, 40, 43, 30, 30, 5, 30, 0);

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDefend(this::postDefend).preDefend(this::preDefend).postTargetDefend(this::postTargetDefend);
        npc.attackNpcListener = (player, npc1, message) -> {
            if (npc.getId() == FLYING && player.getCombat().getAttackStyle().isMelee()) {
                if (message) player.sendMessage("Vespula is flying too high for you to hit with melee!");
                return false;
            }
            return true;
        };
    }

    private void postTargetDefend(Hit hit, Entity entity) {
        if (!hit.isBlocked() && Random.rollDie(5))
            entity.poison(20);
    }

    private void preDefend(Hit hit) {
        if (npc.getId() == GROUNDED)
            hit.block();
    }

    private void postDefend(Hit hit) {
        if (npc.getHp() > npc.getMaxHp() / 5 && (npc.getHp() - hit.damage) <= npc.getMaxHp() / 5) {
            // land
            npc.transform(GROUNDED);
            npc.animate(7457);
            npc.localPlayers().forEach(p -> p.sendMessage(Color.DARK_RED.wrap("The portal is now vulnerable!")));
            npc.addEvent(event -> {
                event.delay(50);
                if (!isDead()) {
                    npc.transform(FLYING);
                    npc.animate(7452);
                    npc.setHp(npc.getMaxHp());
                }
            });
        }
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (npc.getId() != FLYING || !withinDistance(1) || Random.get(5) > 3)
            rangedAttack();
        else
            meleeAttack();
        return true;
    }

    private void rangedAttack() {
        npc.animate(getInfo().attack_animation);
        //targeted attack
        int delay;
        if (npc.getId() == FLYING)
            delay = PROJECTILE_FLYING.send(npc, target);
        else
            delay = PROJECTILE_GROUNDED.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? getInfo().max_damage / 2 : getInfo().max_damage).ignorePrayer().clientDelay(delay));

        //echo projectile
        Direction echoDir = Random.get(Direction.values());
        Position echoPosition = target.getPosition().copy().translate(echoDir.deltaX, echoDir.deltaY, 0);
        if (npc.getId() == FLYING)
            TILE_PROJECTILE_FLYING.send(npc, echoPosition);
        else
            TILE_PROJECTILE_GROUNDED.send(npc, echoPosition);
        Entity immune = target;
        npc.addEvent(event -> {
            event.delay(4);
            npc.localPlayers().forEach(p -> {
                if (p != immune && p.isAt(echoPosition)) {
                    int maxDamage = getInfo().max_damage;
                    if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES))
                        maxDamage /= 2;
                    p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(maxDamage).ignorePrayer());
                }
            });
        });
    }

    private void meleeAttack() {
        npc.animate(7454);
        target.hit(new Hit(npc, AttackStyle.STAB).randDamage(info.max_damage).ignorePrayer().ignoreDefence());
    }

}