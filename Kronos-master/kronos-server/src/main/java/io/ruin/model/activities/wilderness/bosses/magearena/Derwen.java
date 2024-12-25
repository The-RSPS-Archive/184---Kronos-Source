package io.ruin.model.activities.wilderness.bosses.magearena;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;

public class Derwen extends NPCCombat {

    private static final Projectile SPECIAL_PROJECTILE = new Projectile(1512, 45, 0, 25, 70, 0, 16, 32);
    private static final Projectile HEAL_PROJECTILE = new Projectile(1513, 0, 45, 25, 56, 8, 16, 32);

    private TickDelay specialCooldown = new TickDelay();

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle != null && !hit.attackStyle.isMagic()) {
            hit.block();
            if (hit.attacker != null && hit.attacker.player != null)
                hit.attacker.player.sendMessage("Derwen's magic protects him against your attacks. He is only vulnerable to magic spells!");
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
        if (!withinDistance(1) && !specialCooldown.isDelayed() && Random.rollDie(5, 1)) {
            specialAttack();
        } else {
            magicAttack();
        }
        return true;
    }

    private void magicAttack() {
        npc.animate(7849);
        target.graphics(1511, 128, 20);
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
        npc.animate(7849);
        Bounds npcBounds = npc.getBounds();
        Position ballPos = Random.get(npc.getPosition().relative(1, 1).area(6, pos -> pos.getTile().clipping == 0 && !pos.inBounds(npcBounds) && ProjectileRoute.allow(npc, pos)));
        SPECIAL_PROJECTILE.send(npc, ballPos);
        npc.addEvent(event -> {
            event.delay(2);
            NPC ball = new NPC(7514).spawn(ballPos);
            ball.addEvent(e -> {
                e.delay(3);
                while (!ball.getCombat().isDead() && !isDead() && target != null) {
                    int delay = HEAL_PROJECTILE.send(ball, npc);
                    npc.hit(new Hit(HitType.HEAL).fixedDamage(5).clientDelay(delay));
                    e.delay(3);
                }
                ball.remove();
            });
        });
        specialCooldown.delay(50);
    }

}
