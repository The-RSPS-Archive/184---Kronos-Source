package io.ruin.model.activities.inferno.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.inferno.Inferno;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.LinkedList;
import java.util.List;

public class JalTokJad extends NPCCombat { // Inferno jad

    private static final int MAX_DISTANCE = 16;

    private static final Projectile[] MAGIC_PROJECTILES = {
            new Projectile(448, 128, 31, 2, 10, 8, 16, 32),
            new Projectile(449, 128, 31, 6, 14, 8, 16, 32),
            new Projectile(450, 128, 31, 10, 20, 8, 16, 32)
    };

    private boolean spawnedHealers = false;
    private List<NPC> healers = new LinkedList<>();

    private Inferno getInferno() {
        return Inferno.getInstance(npc);
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(this::postDamage);
        npc.deathStartListener = (entity, killer, killHit) -> healers.forEach(healer -> {
            getInferno().getMap().removeNpc(healer);
            healer.remove();
        });
    }

    private void postDamage(Hit hit) {
        if (getInferno() == null || getWave() == -1)
            return;
        if (!spawnedHealers && npc.getHp() < (npc.getMaxHp() / 2)) {
            spawnHealers(getHealerCount());
            spawnedHealers = true;
        }
    }

    private void spawnHealers(int healerCount) {
        if (isDead())
            return;
        if (getInferno() == null)
            return;
        List<Position> spawnPoints = getHealerSpawnPoints();
        for (int i = 0; i < healerCount; i++) {
            Position position = Random.get(spawnPoints);
            NPC healer = new NPC(7701).spawn(position.getX(), position.getY(), 0);
            healers.add(healer);
            healer.deathEndListener = (DeathListener.Simple) () -> getInferno().getMap().removeNpc(healer);
            healer.targetPlayer(getInferno().getPlayer(), false); //target but don't attack (needed so they don't check bounds when attacking!)
            healer.face(npc);
            healer.startEvent(e -> { //when attacked, this event will stop.
                int healTicks = 4;
                while (!npc.getCombat().isDead()) {
                    DumbRoute.step(healer, npc, 1);
                    if (++healTicks >= 4 && DumbRoute.withinDistance(healer, npc, 1)) {
                        healTicks = 0;
                        healer.animate(2639);
                        npc.graphics(444, 250, 0);
                        npc.incrementHp(Random.get(1, 10));
                        if (npc.getHp() == npc.getMaxHp()) {
                            int deadHealers = 4 - (getInferno().getMap().getNpcs().size() - 1);
                            if (deadHealers > 0)
                                spawnHealers(deadHealers);
                        }
                    }
                    e.delay(1);
                }
            });
            getInferno().getMap().addNpc(healer);
        }
    }

    private List<Position> getHealerSpawnPoints() {
        Position base = npc.getPosition().relative(2, 2);
        if (getWave() == 69) { // always spawn healers to the north
            base.translate(0, 5, 0);
            return base.area(1, p -> p.getTile().clipping == 0);
        } else {
            return Random.get(base.area(6, p -> !p.isWithinDistance(base, 3) && p.getTile().clipping == 0)).area(1);
        }
    }

    private int getHealerCount() {
        if (getWave() == 67)
            return 5;
        else
            return 3;
    }

    @Override
    public void follow() {
        follow(MAX_DISTANCE);
    }

    @Override
    public boolean attack() {
        if (withinDistance(1)) {
            if (Random.rollDie(3)) {
                basicAttack(info.attack_animation, info.attack_style, info.max_damage);
                return true;
            }
        } else if (!withinDistance(MAX_DISTANCE)) {
            /**
             * Not in ranged distance
             */
            return false;
        }
        if (Random.rollPercent(50)) {
            /**
             * Magic attack
             */
            npc.animate(7592);
            npc.addEvent(e -> {
                e.delay(2);
                if (target == null)
                    return;
                npc.graphics(447, 500, 0);
                int delay = MAGIC_PROJECTILES[0].send(npc, target);
                for (int i = 1; i < MAGIC_PROJECTILES.length; i++)
                    MAGIC_PROJECTILES[i].send(npc, target);
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(info.max_damage)
                        .clientDelay(delay);
                hit.postDamage(t -> {
                    t.graphics(157);
                    t.privateSound(163);
                });
                target.hit(hit);
            });
        } else {
            /**
             * Ranged attack
             */
            npc.animate(7593);
            npc.addEvent(e -> {
                e.delay(2);
                if (target == null)
                    return;
                World.sendGraphics(451, 0, 0, target.getPosition());
                Hit hit = new Hit(npc, AttackStyle.RANGED)
                        .randDamage(info.max_damage)
                        .delay(2);
                hit.postDamage(t -> {
                    t.graphics(157);
                    t.privateSound(163);
                });
                target.hit(hit);
            });
        }
        return true;
    }

    private int getWave() {
        return getInferno() == null ? -1 : getInferno().getWave();
    }
}