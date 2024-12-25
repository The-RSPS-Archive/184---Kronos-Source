package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

public class Guardian extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(856, 150, 0, 0, 50, 0, 0, 0);

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    @Override
    public void startDeath(Hit killHit) {
        setDead(true);
        npc.faceNone(false);
        npc.animate(info.death_animation);
        npc.addEvent(event -> {
            event.delay(3);
            npc.transform(npc.getId() + 2);
        });
        npc.attackNpcListener = (player, npc1, message) -> {
            if (message) player.sendMessage("It's already destroyed.");
            return false;
        };
        if (getKiller() != null) {
            super.dropItems(getKiller());
        }
    }

    private void preDefend(Hit hit) {
        if ((hit.attackStyle != null && !hit.attackStyle.isMelee())
                || hit.attacker != null && hit.attacker.player != null && Config.WEAPON_TYPE.get(hit.attacker.player) != 11) {
            hit.block();
            if (hit.attacker.player != null)
                hit.attacker.player.sendMessage("The Guardian resists your attack.");
        }
    }

    @Override
    public void follow() {
        //DONT move
    }

    @Override
    public boolean attack() {
        if (Misc.getEffectiveDistance(npc, target) > 1)
            return false;
        if (Random.rollDie(6,1))
            dropBoulder();
        else
            meleeAttack();
        return true;
    }

    private void meleeAttack() {
        int maxDamage = info.max_damage;
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE))
            maxDamage /= 2;
        npc.animate(info.attack_animation);
        target.hit(new Hit(npc, info.attack_style).randDamage(maxDamage).ignorePrayer());
    }

    private void dropBoulder() {
        npc.animate(4278);
        Position pos = target.getPosition().copy();
        World.sendGraphics(305, 35, PROJECTILE.send(npc, pos.getX(), pos.getY()), pos);
        npc.addEvent(event -> {
            event.delay(1);
            npc.localPlayers().forEach(p -> {
                if (p.getPosition().equals(pos)) {
                    p.hit(new Hit().randDamage(40).delay(0));
                }
            });
        });
    }
}
