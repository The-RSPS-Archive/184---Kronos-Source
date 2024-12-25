package io.ruin.model.activities.wilderness.bosses.magearena;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

public class Porazdir extends NPCCombat {

    private static final Projectile SPECIAL_PROJECTILE = new Projectile(1514, 45, 31, 25, 50, 8, 16, 32);

    private TickDelay specialCooldown = new TickDelay();

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle != null && !hit.attackStyle.isMagic()) {
            hit.block();
            if (hit.attacker != null && hit.attacker.player != null)
                hit.attacker.player.sendMessage("Porazdir's magic protects him against your attacks. He is only vulnerable to magic spells!");
        }
    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (withinDistance(1) && Random.rollDie(2, 1)) {
            basicAttack();
            return true;
        }
        if (!specialCooldown.isDelayed() && Random.rollDie(3, 1)) {
             specialAttack();
        } else {
            magicAttack();
        }
        return true;
    }

    private void magicAttack() {
        npc.animate(7841);
        target.graphics(78, 0, 20);
        boolean prayer = target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(prayer ? 21 : 43).delay(1);
        hit.postDamage(entity -> {
            if (entity.player != null && hit.damage > 0 && Random.rollDie(5, 1)) {
                Stat magic = entity.player.getStats().get(StatType.Magic);
                int minLevel = (int) (magic.fixedLevel * 0.95);
                int drain = magic.currentLevel - minLevel;
                if (drain > 0) {
                    magic.drain(drain);
                    entity.player.sendMessage("You feel your magical powers weaken.");
                }
            }
        });
        target.hit(hit);

    }

    private void specialAttack() {
        npc.animate(7841);
        int delay = SPECIAL_PROJECTILE.send(npc, target);
        if (target.player != null)
            target.player.sendMessage(Color.RED.wrap("Porazdir fires a ball of energy directly linked to his power!"));
        npc.addEvent(event -> {
            event.delay(delay * 20 / 600);
            if (target == null)
                return;
            int damage = 30;
            int distance = Misc.getEffectiveDistance(npc, target);
            if (distance < 8)
                damage -= distance * 3;
            else {
                damage = 0;
                if (target.player != null)
                    target.player.sendMessage("Porazdir was too far away to put any power into the energy ball!");
            }
            target.hit(new Hit(npc).fixedDamage(damage));
        });
        specialCooldown.delay(50);
    }
}
