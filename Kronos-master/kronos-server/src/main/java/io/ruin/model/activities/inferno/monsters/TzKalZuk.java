package io.ruin.model.activities.inferno.monsters;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.model.activities.inferno.Inferno;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

public class TzKalZuk extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(1375, 80, 31, 56, 36, 8, 16, 192).regionBased();

    private static final Position[] HEALER_SPAWNS = {
            new Position(2280, 5363, 0),
            new Position(2276, 5363, 0),
            new Position(2266, 5363, 0),
            new Position(2262, 5363, 0)
    };
    private static final Projectile HEALER_PROJECTILE = new Projectile(660, 15, 80, 0, 50, 0, 16, 32);

    private static final Position MAGER_SPAWN = new Position(2265, 5350, 0);
    private static final Position RANGER_SPAWN = new Position(2275, 5350, 0);


    private NPC shield;
    private boolean ready = false;
    private Direction shieldDirection = Random.get() < 0.5 ? Direction.WEST : Direction.EAST;
    private Position westShieldPoint, eastShieldPoint;

    private boolean spawnedJad, spawnedHealers;
    private int addsTimer = 0;
    private int waveSpawnTime = 60;

    @Override
    public void init() {
        npc.hitsUpdate.hpBarType = 2;
        npc.addEvent(event -> {
            while (npc.isLocked()) // wait for cutscene to end
                event.delay(1);
            reset();
            npc.face(Direction.SOUTH);
            for (NPC n : npc.localNpcs()) { // find reference to shield
                if (n.getId() == 7707) {
                    shield = n;
                    break;
                }
            }
            if (shield == null) { // this should never happen
                npc.remove();
                Server.logWarning("No shield found for Zuk!");
                return;
            }
            startShieldLogic();
        });
        startAddsSpawns();
        npc.hitListener = new HitListener().postDamage(this::postDamage);
    }

    private void startAddsSpawns() {
        npc.addEvent(event -> { // adds timer/spawner
            while (!ready)
                event.delay(1);
            while (!isDead()) {
                if (npc.getHp() >= 479 && npc.getHp() <= 599) {
                    event.delay(1);
                } else {
                    addsTimer++;
                    if (addsTimer >= waveSpawnTime) {
                        spawnWave();
                    }
                    event.delay(1);
                }
            }
        });
    }

    private void spawnWave() {
        if (waveSpawnTime <= 350)
            waveSpawnTime = 350;
        addsTimer = 0;
        NPC mager = getInferno().spawn(7703, MAGER_SPAWN, false);
        NPC ranger = getInferno().spawn(7702, RANGER_SPAWN, false);
        mager.face(shield);
        ranger.face(shield);
        mager.getCombat().setTarget(shield);
        ranger.getCombat().setTarget(shield);
        mager.getCombat().updateLastAttack(10);
        ranger.getCombat().updateLastAttack(10);
    }

    private void postDamage(Hit hit) { // check damage-based spawns
        if (!spawnedJad && npc.getHp() <= 480) {
            spawnJad();
        } else if (!spawnedHealers && npc.getHp() <= 240) {
            spawnHealers();
        }
    }

    private void spawnHealers() {
        if (spawnedHealers)
            return;
        spawnedHealers = true;
        for (int i = 0; i < HEALER_SPAWNS.length; i++) {
            NPC healer = getInferno().spawn(7708, HEALER_SPAWNS[i], false);
            healer.getCombat().reset();
            healer.startEvent(event -> {
                healer.lock();
                event.delay(3);
                healer.face(npc);
                healer.unlock();
                while (true) {
                    if (npc.getHp() < npc.getMaxHp()) {
                        HEALER_PROJECTILE.send(healer, npc);
                        npc.hit(new Hit(HitType.HEAL).randDamage(10, 25).delay(2));
                        event.delay(4);
                    }
                    event.delay(1);
                }
            });
        }
    }

    private void spawnJad() {
        if (spawnedJad)
            return;
        spawnedJad = true;
        waveSpawnTime += 175;
        NPC jad = getInferno().spawn(7700, new Position(2270, 5347, 0), false);
        jad.getCombat().setTarget(shield);
        jad.face(shield);
        jad.getCombat().updateLastAttack(9);
    }

    private void startShieldLogic() {
        westShieldPoint = getInferno().getMap().convertPosition(new Position(2257,5361,0));
        eastShieldPoint = getInferno().getMap().convertPosition(new Position(2283,5361,0));
        shield.addEvent(event -> {
            int stoppedTime = 0;
            while (!shield.getCombat().isDead()) {
                Position destination = shieldDirection == Direction.WEST ? westShieldPoint : eastShieldPoint;
                if (shield.isAt(destination) && !ready)
                    ready = true;
                if (!shield.isAt(destination)) {
                    shield.step(shieldDirection == Direction.WEST ? -1 : 1, 0, StepType.FORCE_WALK);
                } else if (++stoppedTime == 4) {
                    stoppedTime = 0;
                    shieldDirection = shieldDirection == Direction.WEST ? Direction.EAST : Direction.WEST;
                }
                event.delay(1);
            }
        });
    }

    private boolean isTargetSafe() {
        if (shield.getCombat().isDead())
            return false;
        int minX = shield.getAbsX();
        int maxX = shield.getAbsX() + shield.getSize() - 1;
        if (!shield.isAt(eastShieldPoint) && !shield.isAt(westShieldPoint)) {
            if (shieldDirection == Direction.WEST) {
                maxX += 2;
                minX -= 3;
            } else {
                minX -= 2;
                maxX += 3;
            }
        } else {
            if (shield.isAt(westShieldPoint))
                maxX++;
            else
                minX--;
        }
        return target.getAbsX() >= minX && target.getAbsX() <= maxX;
    }

    private Inferno getInferno() {
        return Inferno.getInstance(npc);
    }

    @Override
    public void follow() {
        //dont move
    }

    @Override
    public boolean attack() {
        if (!ready)
            return false;
        Entity projectileTarget = isTargetSafe() ? shield : target;
        int delay = PROJECTILE.send(npc, projectileTarget);
        Hit hit = new Hit(npc, Random.get() < 0.5 ? AttackStyle.RANGED : AttackStyle.MAGIC).randDamage(info.max_damage).ignorePrayer().ignoreDefence().clientDelay(delay);
        if (projectileTarget == shield) {
            hit.nullify();
            projectileTarget.animate(projectileTarget.getCombat().getDefendAnimation(), delay - 25);
        }
        target.hit(hit);
        npc.animate(info.attack_animation);
        return true;
    }

    @Override
    public boolean allowRetaliate(Entity attacker) {
        return target == null;
    }
}
