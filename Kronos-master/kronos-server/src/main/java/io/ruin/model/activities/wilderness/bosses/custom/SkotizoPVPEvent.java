package io.ruin.model.activities.wilderness.bosses.custom;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.List;

public class SkotizoPVPEvent extends NPCCombat {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1242, 120, 31, 25, 56, 10, 15, 220);
    private static final Projectile SPLIT_PROJECTILE = new Projectile(195, 31, 31, 0, 66, 0, 110, 0);
    private static final Projectile MINION_SPAWN_PROJECTILE = new Projectile(195, 120, 0, 25, 75, 0, 15, 220);
    private static final int MAGIC_ATTACK_ANIM = 69;


    private List<NPC> minions = new ArrayList<>(3);
    private TickDelay minionCooldown = new TickDelay();

    @Override
    public void init() {
        npc.hitsUpdate.hpBarType = 2;
        npc.hitListener = new HitListener().postDefend(this::postDefend);
        npc.deathStartListener = (entity, killer, killHit) -> killMinions();
    }

    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        if(attacker.player != null && !attacker.player.getCombat().isSkulled()) {
            attacker.player.getCombat().skullNormal();
            attacker.player.sendMessage("<col=6f0000>You've been marked with a skull for attacking Skotizo!");
        }
    }

    private void postDefend(Hit hit) {
        if (!minions.isEmpty() && minions.stream().anyMatch(n -> !n.isRemoved() && !n.getCombat().isDead())) {
            hit.damage *= 0.20;
            if (hit.attacker != null && hit.attacker.player != null)
                hit.attacker.player.sendMessage(Color.RED.wrap("Skotizo's resists your damage through his minions' energy!"));
        }
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (withinDistance(1) && Random.rollPercent(60)) {
            basicAttack();
        } else if (npc.getHp() <= npc.getMaxHp() / 2
                && !minionCooldown.isDelayed()
                && Random.rollPercent(5)
                && (minions.isEmpty() || minions.stream().allMatch(n -> n.isRemoved() || n.getCombat().isDead()))) {
            spawnMinions();
        } else {
            magicAttack();
        }
        return true;
    }

    private void magicAttack() {
        npc.animate(MAGIC_ATTACK_ANIM);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        int maxDamage = 38;
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
            maxDamage /= 2;
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).ignorePrayer().clientDelay(delay);
        hit.postDamage(entity -> { // "split" attack
            entity.localPlayers().forEach(p -> {
                if (p == entity)
                    return;
                if (Misc.getDistance(p.getPosition(), entity.getPosition()) <= 2) {
                    int d = SPLIT_PROJECTILE.send(entity, p);
                    p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(10, 26).ignorePrayer().clientDelay(d));
                    p.graphics(197, 0, d);
                }
            });
        });
        target.hit(hit);
        target.graphics(197, 0, delay);
    }

    private void spawnMinions() {
        minionCooldown.delay(30);
        minions.clear();
        npc.animate(MAGIC_ATTACK_ANIM);
        for (int i = 0; i < 3; i++) {
            Player player = Random.get(npc.localPlayers());
            if (player == null)
                return;
            Position spawnPos = Random.get(player.getPosition().area(1, pos -> Tile.get(pos) == null || Tile.get(pos).clipping == 0));
            int delay = MINION_SPAWN_PROJECTILE.send(npc, spawnPos.getX(), spawnPos.getY());
            npc.addEvent(event -> {
                event.delay(1);
                if (target == null || isDead() || npc.isRemoved())
                    return;
                NPC minion = new NPC(7287).spawn(spawnPos);
                minions.add(minion);
                minion.getCombat().setTarget(player);
                minion.face(player);
                minion.graphics(197);
                minion.deathStartListener = (entity, killer, killHit) -> {
                    final Position explosionTile = minion.getPosition().copy();
                    minion.addEvent(boomEvent -> {
                        boomEvent.delay(4);
                        World.sendGraphics(1328, 0, 0, explosionTile);
                        boomEvent.delay(1);
                        explosionTile.getRegion().players.forEach(p -> {
                            if (Misc.getDistance(p.getPosition(), explosionTile) <= 1) {
                                p.hit(new Hit(null, null).randDamage(7, 18).ignorePrayer().ignoreDefence());
                            }
                        });
                    });
                };
            });
        }
    }

    private void killMinions() {
        minions.forEach(n -> {
            if (n != null && !n.isRemoved() && !n.getCombat().isDead()) {
                n.getCombat().startDeath(null);
            }
        });
    }

}
