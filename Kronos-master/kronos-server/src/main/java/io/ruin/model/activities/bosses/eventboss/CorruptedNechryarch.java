package io.ruin.model.activities.bosses.eventboss;

import com.google.common.collect.Lists;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.utility.TickDelay;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ReverendDread on 6/8/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class CorruptedNechryarch extends NPCCombat {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(5000, 120, 43, 60, 90, 0, 16, 64);
    private static final Projectile ACID_PROJECTILE = new Projectile(5005, 35, 35, 10, 10, 10, 16, 64);

    private TickDelay acidAttackCooldown = new TickDelay();
    private List<Position> acidPools = Lists.newArrayList();

    @Override
    public void init() {
        npc.setIgnoreMulti(true);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(16))
            return false;
        if (!acidAttackCooldown.isDelayed()) {
            acid_attack();
        }
        boolean close = npc.localPlayers().stream().anyMatch(p -> p.getPosition().isWithinDistance(npc.getPosition(), 2));
        if (close && Random.rollDie(3))
            melee_attack();
        else
            magic_attack();
        return true;
    }

    private void acid_attack() {
        acidAttackCooldown.delay(50);
        npc.startEvent(event -> {
            List<Player> targets = npc.localPlayers();
            Position lastAcidPos = npc.getPosition();
            for (int cycle = 0; cycle < (target.getSize() > 1 ? 2 : 1); cycle++) {
                Player random = Random.get(targets);
                int duration = ACID_PROJECTILE.send(lastAcidPos, random.getPosition());
                lastAcidPos = random.getPosition().copy();
                int delay = ((duration * 25) / 600) - 1;
                event.delay(delay);
                World.sendGraphics(5001, 0, 0, lastAcidPos);
                World.sendGraphics(5004, 0, 0, lastAcidPos);
                acidPools.add(lastAcidPos);
                for (Player player : targets) {
                    if (player.getPosition().equals(lastAcidPos)) {
                        int damage = Random.get(1, 30);
                        player.hit(new Hit().fixedDamage(damage));
                        npc.hit(new Hit(HitType.HEAL).fixedDamage(damage));
                    }
                }
            }
            for (int duration = 0; duration < 8; duration++) {
                targets.forEach(p -> {
                    for (Position pos : acidPools) {
                        if (pos.equals(p.getPosition())) {
                            int damage = Random.get(4, 8);
                            p.hit(new Hit().fixedDamage(damage));
                            npc.hit(new Hit(HitType.HEAL).fixedDamage(damage));
                        }
                    }
                });
                event.delay(2);
            }
            event.delay(20);
            acidPools.clear();
        });
    }

    private void melee_attack() {
        List<Player> targets = npc.localPlayers();
        npc.animate(4672);
        targets.forEach(p -> {
            if (p.getPosition().isWithinDistance(npc.getPosition(), 2)) {
                p.hit(new Hit(npc, AttackStyle.CRUSH, null).randDamage(41).ignorePrayer());
            }
        });
    }

    private void magic_attack() {
        npc.animate(7550);
        List<Player> targets = npc.localPlayers().stream().filter(p -> ProjectileRoute.allow(npc, p)).collect(Collectors.toList());
        if (targets.size() == 0)
            return;
        targets.forEach(p -> {
            int duration = MAGIC_PROJECTILE.send(npc, p);
            Hit hit = new Hit(npc, AttackStyle.MAGIC, null).randDamage(30).clientDelay(duration);
            p.hit(hit);
            if (Random.rollDie(10)) {
                p.startEvent(e -> {
                    e.delay(hit.getTicks());
                    //after hit effects
                    for (int i = 0; i < 5; i++) {
                        p.hit(new Hit().fixedDamage(8));
                        p.graphics(5002);
                        e.delay(2);
                    }
                });
            }
        });
    }

}
