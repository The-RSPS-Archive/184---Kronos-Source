package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.*;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.skills.magic.spells.ancient.IceBarrage;
import io.ruin.model.skills.magic.spells.ancient.IceBlitz;
import io.ruin.model.skills.magic.spells.ancient.IceBurst;
import io.ruin.model.skills.magic.spells.ancient.IceRush;
import io.ruin.model.skills.magic.spells.modern.*;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.Event;
import io.ruin.utility.TickDelay;
import kilim.Pausable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.ruin.model.activities.raids.xeric.chamber.combat.GreatOlm.Facing.*;

public class GreatOlm extends NPCCombat {

    private static final TargetSpell[] WATER_SPELLS = {
            new WaterStrike(), new WaterBolt(), new WaterBlast(), new WaterWave(), new WaterSurge(),
            new IceRush(), new IceBlitz(), new IceBurst(), new IceBarrage(),
    };

    static {
        ObjectDef.get(32297).clipType = 1; // force flame wall fire to clip tiles
        ObjectAction.register(32297, "douse", (player, obj) -> { // fire wall douse
            for (int i = 0; i < WATER_SPELLS.length; i++) { // combat spells
                TargetSpell spell = WATER_SPELLS[i];
                if (player.getStats().get(StatType.Magic).currentLevel < spell.getLvlReq())
                    continue;
                SpellBook book = i > 4 ? SpellBook.ANCIENT : SpellBook.MODERN;
                if (!book.isActive(player))
                    continue;
                RuneRemoval r = RuneRemoval.get(player, spell.getRuneItems());
                if (r == null)
                    continue;
                //can cast
                r.remove();
                player.getStats().addXp(StatType.Magic, spell.baseXp, true);
                player.animate(spell.getAnimationId());
                if (spell.getCastGfx() != null)
                    player.graphics(spell.getCastGfx()[0], spell.getCastGfx()[1], spell.getCastGfx()[2]);
                if (spell.getProjectile() != null)
                    spell.getProjectile().send(player, obj.x, obj.y);
                player.lock();
                player.addEvent(event -> {
                    event.delay(1);
                    player.unlock();
                    if (!obj.isRemoved())
                        obj.remove();
                });
                return;
            }
            //humidify
            RuneRemoval rune = RuneRemoval.get(player, Rune.ASTRAL.toItem(1),Rune.WATER.toItem(3),Rune.FIRE.toItem(1));
            if (rune == null || !SpellBook.LUNAR.isActive(player) || !player.getStats().check(StatType.Magic, 68)) {
                player.sendMessage("You do not have the required runes to cast a water spell from your current spellbook at your Magic level.");
                return;
            }
            rune.remove();
            player.animate(6294);
            player.graphics(1061, 72, 0);
            player.getStats().addXp(StatType.Magic, 68, true);
            player.lock();
            player.addEvent(event -> {
                event.delay(1);
                player.unlock();
                if (!obj.isRemoved())
                    obj.remove();
            });
        });
    }

    private static final Projectile CRYSTAL_DROP_PROJECTILE = new Projectile(1357, 150, 0, 0, 135, 0, 0, 0);
    private static final Projectile CRYSTAL_BOMB_PROJECTILE = new Projectile(1357, 90, 0, 30, 100, 0, 16, 0);
    private static final Projectile CRYSTAL_SPIKE_PROJECTILE = new Projectile(1352, 200, 0, 0, 30, 0, 0, 0);

    private static final Projectile ACID_POOL_PROJECTILE = new Projectile(1354, 90, 0, 30, 100, 0, 16, 0);private static final Projectile ACID_DRIP_PROJECTILE = new Projectile(1354, 90, 43, 30, 25, 6, 16, 0);

    private static final Projectile BURN_PROJECTILE = new Projectile(1350, 90, 43, 35, 20, 6, 16, 192);
    private static final Projectile FLAME_WALL_PROJECTILE_1 = new Projectile(1347, 90, 0, 15, 55, 0, 16, 127);
    private static final Projectile FLAME_WALL_PROJECTILE_2 = new Projectile(1348, 0, 0, 0, 30, 0, 16, 0);

    private static final Projectile SIPHON_PROJECTILE = new Projectile(1355, 90, 0, 30, 100, 0, 16, 0);

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1339, 90, 43, 35, 40, 6, 16, 192);
    private static final Projectile RANGED_PROJECTILE = new Projectile(1340, 90, 43, 35, 40, 6, 16, 192);

    private static final Projectile MAGIC_SPHERE = new Projectile(1341, 90, 43, 30, 150, 0, 16, 192);
    private static final Projectile RANGED_SPHERE = new Projectile(1343, 90, 43, 30, 150, 0, 16, 192);
    private static final Projectile MELEE_SPHERE = new Projectile(1345, 90, 43, 30, 150, 0, 16, 192);

    //head anims
    //7335 - rise
    //7336 - facing center
    //7337 - facing right
    //7338 - facing left
    //7339 - mid to face right
    //7340 - right to face mid
    //7341 - mid to face left
    //7342 - left to face mid
    //7343 - left to face right
    //7344 - right to face left
    //7345 - attack (mid)
    //7346 - attack (right)
    //7347 - attack (left)
    //7348 - go down
    //7371 - mid attack (empowered)
    //7372 - left attack (empowered)
    //7373 - right attack (empowered)
    //7374 - idle mid (empowered)
    //7375 - idle left (empowered)
    //7376 - idle right (empowered)
    //7377 - mid to face left (empowered)
    //7378 - left to face mid(empowered)
    //7379 - left to face right(empowered)
    //7380 - right to face left (empowered)
    //7381 - mid to face right (empowered)
    //7382 - right to face mid (empowered)
    //7383 - rise (empowered)

    //left hand anims
    //7354 - rise
    //7355 - idle
    //7356 - crystal
    //7357 - infinity (healing)
    //7358 - lightning
    //7359 - swap
    //7360 - into clench
    //7361 - clench idle
    //7362 - unclench


    enum Facing {
        RIGHT(7337, 7376, 7346, 7373,
                7339, 7343, 7381, 7379,
                28, 44,
                37, 52),
        CENTER(7336, 7374, 7345, 7371,
                7340, 7342, 7382, 7378,
                28, 39,
                37, 49),
        LEFT(7338, 7375, 7347, 7372,
                7341, 7344, 7377, 7380,
                28, 34,
                37, 45),
        ;

        Facing(int idleAnim, int empoweredIdleAnim, int attackAnim, int empoweredAttackAnim, int closeTransitionAnim, int farTransitionAnim, int empoweredCloseTransitionAnim, int empoweredFarTransitionAnim, int swX, int swY, int neX, int neY) {
            this.idleAnim = idleAnim;
            this.empoweredIdleAnim = empoweredIdleAnim;
            this.attackAnim = attackAnim;
            this.empoweredAttackAnim = empoweredAttackAnim;
            this.closeTransitionAnim = closeTransitionAnim;
            this.farTransitionAnim = farTransitionAnim;
            this.empoweredCloseTransitionAnim = empoweredCloseTransitionAnim;
            this.empoweredFarTransitionAnim = empoweredFarTransitionAnim;
            this.swX = swX;
            this.swY = swY;
            this.neX = neX;
            this.neY = neY;
        }

        int idleAnim, empoweredIdleAnim;
        int attackAnim, empoweredAttackAnim;
        int closeTransitionAnim, farTransitionAnim;
        int empoweredCloseTransitionAnim, empoweredFarTransitionAnim;

        int getIdleAnim(boolean empowered) {
            return empowered ? empoweredIdleAnim : idleAnim;
        }

        int getAttackAnim(boolean empowered) {
            return empowered ? empoweredAttackAnim : attackAnim;
        }

        int getCloseTransitionAnim(boolean empowered) {
            return empowered ? empoweredCloseTransitionAnim : closeTransitionAnim;
        }

        int getFarTransitionAnim(boolean empowered) {
            return empowered ? empoweredFarTransitionAnim : farTransitionAnim;
        }

        int swX, swY;
        int neX, neY; // local targeting area, assuming olm is on the east side. must be flipped if on west

    }

    enum PhasePower {
        ACID(Color.DARK_GREEN.wrap("acid")),
        FLAME(Color.RED.wrap("flame")),
        CRYSTAL(Color.RAID_PURPLE.wrap("crystal")),
        ;

        PhasePower(String name) {
            this.name = name;
        }

        String name;
    }
    private Chamber chamber;

    private NPC leftClaw;
    private NPC rightClaw;
    private int clenchDamageCounter = 0;
    private boolean clenched;
    private boolean clawHealing;

    private int currentPhase = 0;
    private int lastPhase;

    private Facing facing = CENTER;
    private Bounds northTargetBounds, centerTargetBounds, southTargetBounds, arenaBounds;

    private int attackCounter = 0;
    private int specialCounter = 0;
    private AttackStyle lastBasicAttackStyle = Random.rollPercent(50) ? AttackStyle.MAGIC : AttackStyle.RANGED;

    private PhasePower phasePower = null;

    private boolean finalStand = false;
    private TickDelay siphonDelay = new TickDelay();
    @Override
    public void init() {
        chamber = npc.get("RAID_CHAMBER");
        if (chamber == null) {
            npc.remove();
            return;
        }
        northTargetBounds = new Bounds(chamber.getPosition(RIGHT.swX, RIGHT.swY), chamber.getPosition(RIGHT.neX, RIGHT.neY), 0);
        centerTargetBounds = new Bounds(chamber.getPosition(CENTER.swX, CENTER.swY), chamber.getPosition(CENTER.neX, CENTER.neY), 0);
        southTargetBounds = new Bounds(chamber.getPosition(LEFT.swX, LEFT.swY), chamber.getPosition(LEFT.neX, LEFT.neY), 0);
        arenaBounds = new Bounds(chamber.getPosition(28, 35), chamber.getPosition(37, 51), 0);
        lastPhase = 2; //0,1,2  = 3 phases default
        if (chamber.getRaid() != null)
            lastPhase += chamber.getRaid().getPartySize() / 8; // 1 extra phase for every 8 party members
        rightClaw = chamber.spawnNPC(7553, 38, 47, Direction.WEST, 0);
        leftClaw = chamber.spawnNPC(7555, 38, 37, Direction.WEST, 0);
        rightClaw.deathEndListener = leftClaw.deathEndListener = (entity, killer, killHit) -> clawDeathEnd(entity.npc);
        rightClaw.deathStartListener = leftClaw.deathStartListener = (entity, killer, killHit) -> clawDeathStart(entity.npc);
        rightClaw.hitListener = new HitListener().preDefend(this::preRightClawDefend);
        rightClaw.getCombat().setAllowRespawn(false);
        leftClaw.getCombat().setAllowRespawn(false);
        leftClaw.hitListener = new HitListener().postDamage(this::postLeftClawDamage).preDefend(this::preLeftClawDefend);
        npc.deathStartListener = (entity, killer, killHit) -> olmDeathStart();
        npc.deathEndListener = (entity, killer, killHit) -> olmDeathEnd();
        npc.hitListener = new HitListener().postDamage(this::postDamage);
        npc.lock();
        rightClaw.lock();
        leftClaw.lock();
        npc.startEvent(event -> {
            while (getAllTargets().size() == 0)
                event.delay(5);
            rise(event);
        });
        startAcidPoolEvent();
        npc.set("RAID_NO_POINTS", true);
    }

    private void preLeftClawDefend(Hit hit) {
        if (clenched)
            hit.block();
        else if (clawHealing)
            hit.type = HitType.HEAL;
        else if (!hit.attackStyle.isMelee()) {
            if (hit.attacker.player != null)
                hit.attacker.player.sendMessage("The claw resists your non-melee attack!");
            hit.block();
        }
    }

    private void preRightClawDefend(Hit hit) {
        if (!hit.attackStyle.isMagic()) {
            if (hit.attacker.player != null)
                hit.attacker.player.sendMessage("The claw resists your non-magic attack!");
            hit.block();
        }
    }

    private void postLeftClawDamage(Hit hit) {
        if (rightClaw.getCombat().isDead() || leftClaw.getCombat().isDead() || clenched || currentPhase == lastPhase)
            return;
        clenchDamageCounter += hit.damage;
        if (clenchDamageCounter >= leftClaw.getMaxHp() / 5) {
            forAllTargets(p -> p.sendMessage("The Great Olm's left claw clenches to protect itself temporarily."));
            clenchDamageCounter = 0;
            clenched = true;
            animate(leftClaw, 7360);
            delayedAnimation(leftClaw, 7361, 1);
            npc.addEvent(event -> {
                event.delay(20);
                forAllTargets(p -> p.sendMessage("The Great Olm regains control of its left claw!"));
                clenched = false;
                animate(leftClaw, 7362);
                delayedAnimation(leftClaw, 7355, 2);
            });
        }
    }

    private void olmDeathStart() {
        animate(npc, 7348);
        forAllTargets(p -> p.getPacketSender().resetCamera());
        if (!fires.isEmpty()) {
            fires.forEach(gameObject -> {
                if (!gameObject.isRemoved())
                    gameObject.remove();
            });
        }
    }

    private void olmDeathEnd() {
        getObject(npc).setId(29882);
        GameObject crystal = chamber.getObject(30018, 32, 53, 0);
        chamber.getObject(30027, 33, 55, 0).setId(30028); // reward chest
        crystal.animate(7506);
        chamber.getRaid().completeRaid();
        World.startEvent(event -> {
            event.delay(2);
            crystal.remove();
        });
        npc.remove();
        leftClaw.remove();
        rightClaw.remove();
    }

    private void clawDeathStart(NPC claw) {
        animate(claw, claw == leftClaw ? 7370 : 7352);
        claw.addEvent(event -> {
            event.delay(2);
            getObject(claw).setId(claw == leftClaw ? 29885 : 29888);
        });
    }

    private void clawDeathEnd(NPC claw) {
        claw.setHidden(true);
        if (leftClaw.getCombat().isDead() && rightClaw.getCombat().isDead()) {
            nextPhase();
        } else if (currentPhase == lastPhase) {
            startClawReviveTimer(claw);
        }
    }

    private void startClawReviveTimer(NPC claw) {
        NPC otherClaw = claw == leftClaw ? rightClaw : leftClaw;
        claw.lock();
        claw.addEvent(event -> {
            event.delay(2);
            claw.setHidden(false);
            claw.hitsUpdate.hpBarType = 8;
            int progress = 1;
            while (progress <= 25 && !otherClaw.getCombat().isDead()) {
                claw.hitsUpdate.forceSend(progress++, 25);
                event.delay(1);
            }
            if (!otherClaw.getCombat().isDead() && progress >= 25) { // failed, revive claw
                claw.set("RAID_NO_POINTS", true);
                restore(claw);
                getObject(claw).setId(claw == rightClaw ? 29886 : 29883);
                event.delay(1);

                animate(claw, claw == rightClaw ? 7350 : 7354);
                event.delay(5);

                getObject(claw).setId(claw == rightClaw ? 29887 : 29884);
                claw.hitsUpdate.hpBarType = 1;
            } else {
                animate(npc, facing.getIdleAnim(isEmpowered()));
                claw.setHidden(true);
            }
            claw.unlock();
        });
    }

    private void ceilingCrystals(int delay, int duration) {
        npc.addEvent(event -> {
            forAllTargets(p -> {
                p.getPacketSender().shakeCamera(0, 6);
                p.getPacketSender().shakeCamera(1, 6);
            });
            event.setCancelCondition(this::isDead);
            event.delay(delay);
            int ticks = 0;
            while (ticks < duration) {
                for (int i = 0; i < 2; i ++) {
                    Position position;
                    if (i == 0 && ticks % 4 == 0 && target != null)
                        position = target.getPosition().copy();
                    else
                        position = arenaBounds.randomPosition();
                    Position src = Random.get() < 0.5 ? position.relative(1, 0) : position.relative(0, 1);
                    int projDelay = CRYSTAL_DROP_PROJECTILE.send(src, position);
                    World.sendGraphics(1358, 0, projDelay, position);
                    chamber.getRaid().getMap().addEvent(e -> {
                        e.delay(projDelay / 30);
                        forAllTargets(p -> {
                            int distance = p.getPosition().distance(position);
                            if (distance <= 1) {
                                p.hit(new Hit(npc).randDamage(distance == 0 ? 30 : 15));
                            }
                        });
                    });
                }
                ticks += 2;
                event.delay(2);
            }
            forAllTargets(p -> p.getPacketSender().resetCamera());
        });
    }

    private void nextPhase() {
        if (npc.isLocked())
            return;
        if (currentPhase >= lastPhase) {
            if (!finalStand) {
                finalStand = true;
                ceilingCrystals(0, 10000);
                forAllTargets(p -> p.sendMessage("The Great Olm is giving its all. This is its final stand."));
                npc.remove("RAID_NO_POINTS");
            }
            return;
        }
        npc.lock();
        leftClaw.lock();
        rightClaw.lock();
        ceilingCrystals(3, 30);
        facing = CENTER;
        npc.addEvent(event -> {
            //go down
            animate(npc, 7348);
            animate(leftClaw, 7370);
            animate(rightClaw, 7352);
            event.delay(2);
            if (npc.isRemoved())
                return;
            getObject(npc).setId(29882);
            getObject(leftClaw).setId(29885);
            getObject(rightClaw).setId(29888);
            if (isOnEastSide()) { // to west
                npc.getMovement().teleport(chamber.getPosition(23, 42));
                leftClaw.getMovement().teleport(chamber.getPosition(23, 47));
                rightClaw.getMovement().teleport(chamber.getPosition(23, 37));
            } else {
                npc.getMovement().teleport(npc.getSpawnPosition());
                leftClaw.getMovement().teleport(leftClaw.getSpawnPosition());
                rightClaw.getMovement().teleport(rightClaw.getSpawnPosition());
            }
            event.delay(30);
            if (currentPhase + 1 != lastPhase) {
                phasePower = Random.get(PhasePower.values());
                forAllTargets(p -> p.sendMessage("The Great Olm rises with the power of " + phasePower.name + "."));
            } else {
                phasePower = null;
            }
            rise(event);
            restore(leftClaw);
            restore(rightClaw);
            currentPhase++;
        });
    }

    private void restore(NPC claw) {
        claw.getCombat().restore();
        claw.getCombat().setDead(false);
        claw.setHidden(false);
    }

    private void rise(Event event) throws Pausable {
        npc.lock();
        rightClaw.lock();
        leftClaw.lock();
        getObject(rightClaw).setId(29886);
        getObject(npc).setId(29880);
        getObject(leftClaw).setId(29883);
        event.delay(1);

        animate(rightClaw, 7350);
        animate(npc, isEmpowered() ? 7383 : 7335);
        animate(leftClaw, 7354);
        event.delay(5);

        getObject(rightClaw).setId(29887);
        getObject(npc).setId(29881);
        getObject(leftClaw).setId(29884);
        npc.unlock();
        leftClaw.unlock();
        rightClaw.unlock();
    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        attackCounter++;
        if ((attackCounter % 10) == 1) {
            if (++specialCounter == (isEmpowered() ? 10 : 9))
                specialCounter = 1;
        }
        List<Player> targets = getFacingTargets();
        if (targets.size() == 0 || !targets.contains(target.player)) {
            turn();
            return true;
        }
        int attackType = (attackCounter - 1) % 4;
        if (attackType == 0 || attackType == 2) {
            if (finalStand && !siphonDelay.isDelayed() && Random.rollPercent(15)) {
                siphonAttack();
                siphonDelay.delay(20);
            } else {
                PhasePower power = isEmpowered() ? Random.get(PhasePower.values()) : phasePower;
                if (power == PhasePower.ACID && Random.rollPercent(15)) {
                    if (Random.rollPercent(30) && Random.rollPercent(50))
                        acidPoolsAttack();
                    else
                        acidDrip();
                } else if (power == PhasePower.FLAME && Random.rollPercent(20)) {
                    if (Random.rollPercent(30) && Random.rollPercent(50))
                        flameWall();
                    else
                        burnAttack();
                } else if (power == PhasePower.CRYSTAL && Random.rollPercent(15)) {
                    if (Random.rollPercent(30) && Random.rollPercent(50))
                        crystalBomb();
                    else
                        crystalMark();
                } else {
                    if (Random.get() < 0.93)
                        basicAttack(targets);
                    else
                        sphereAttack(targets);
                }
            }
        } else if (attackType == 1 && !clenched) {
            int specialType = (specialCounter - 1) % (isEmpowered() ? 9 : 8);
            if (specialType == 4)
                crystalBurst();
            else if (specialType == 5)
                lightningAttack();
            else if (specialType == 6)
                swapAttack();
            else if (currentPhase > 1 && (specialType == 2 || specialType == 8))
                clawHealing();
            else
                basicAttack();
        }
        if (attackType == 2) {
            attackCounter = 0;
        }
        return true;
    }

    private void basicAttack(List<Player> targets) {
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        lastBasicAttackStyle = Random.rollPercent(75) ? lastBasicAttackStyle : (lastBasicAttackStyle == AttackStyle.RANGED ? AttackStyle.MAGIC : AttackStyle.RANGED);
        targets.forEach(p -> {
            int delay = (lastBasicAttackStyle == AttackStyle.RANGED ? RANGED_PROJECTILE : MAGIC_PROJECTILE).send(npc, p);
            int maxDamage = info.max_damage;
            if (p.getPrayer().isActive(lastBasicAttackStyle == AttackStyle.RANGED ? Prayer.PROTECT_FROM_MISSILES : Prayer.PROTECT_FROM_MAGIC))
                maxDamage /= 4;
            p.hit(new Hit(npc, lastBasicAttackStyle).randDamage(maxDamage).ignorePrayer().clientDelay(delay));
        });
    }

    private void sphereAttack(List<Player> targets) {
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        for (int i = 0; i < 3 && targets.size() > 0; i++) {
            Player target = targets.remove(Random.get(targets.size() - 1));
            AttackStyle style = Random.get() < 1d/3 ? AttackStyle.MAGIC : (Random.get() < 1d/2 ? AttackStyle.RANGED : AttackStyle.STAB);
            String message;
            Projectile projectile;
            Prayer prayer;
            int hitGfx;
            switch (style) {
                case MAGIC:
                    message = Color.PURPLE.wrap("The Great Olm fires a sphere of magical power your way.");
                    projectile = MAGIC_SPHERE;
                    hitGfx = 1342;
                    prayer = Prayer.PROTECT_FROM_MAGIC;
                    break;
                case RANGED:
                    message = Color.DARK_GREEN.wrap("The Great Olm fires a sphere of accuracy and dexterity your way.");
                    projectile = RANGED_SPHERE;
                    hitGfx = 1344;
                    prayer = Prayer.PROTECT_FROM_MISSILES;
                    break;
                case STAB:
                    message = Color.RED.wrap("The Great Olm fires a sphere of aggression your way.");
                    projectile = MELEE_SPHERE;
                    hitGfx = 1346;
                    prayer = Prayer.PROTECT_FROM_MELEE;
                    break;
                default:
                    return;
            }
            if (target.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) || target.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) || target.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                target.getPrayer().drain(target.getStats().get(StatType.Prayer).currentLevel / 3);
                target.getPrayer().deactivateProtectionPrayer();
                message += " Your prayers have been sapped.";
            }
            int delay = projectile.send(npc, target);
            target.graphics(hitGfx, 100, delay);
            target.sendMessage(message);
            target.addEvent(event -> {
                event.delay(4);
                if (!target.getPrayer().isActive(prayer)) {
                    target.hit(new Hit(npc).fixedDamage(Math.min(200, target.getHp() / 2)));
                }
            });

        }
    }

    private void crystalBurst() {
        if (leftClaw.getCombat().isDead() || clenched)
            return;
        animate(leftClaw, 7356);
        delayedAnimation(leftClaw, 7355, 2);
        getAllTargets().forEach(p -> {
            Position pos = p.getPosition().copy();
            GameObject crystal = GameObject.spawn(30033, pos, 10, 0);
            p.addEvent(event -> {
                event.delay(2);
                crystal.setId(30034);
                if (p.getPosition().equals(pos))
                    p.hit(new Hit(npc).randDamage(getMaxDamage()));
                event.delay(1);
                crystal.remove();
            });

        });
    }

    private void lightningAttack() {
        if (leftClaw.getCombat().isDead() || clenched)
            return;
        animate(leftClaw, 7358);
        delayedAnimation(leftClaw, 7355, 2);
        for (int x = 30; x <= 36; x++) {
            if (!Random.rollPercent(45))
                continue;
            int yStep = Random.rollPercent(50) ? -1 : 1;
            Position lightningPos = chamber.getPosition(x, yStep == -1 ? 52 : 35);
            npc.addEvent(event -> {
                event.setCancelCondition(this::isDead);
                for (int i = 0; i < 17; i++) {
                    World.sendGraphics(1356, 0, 0, lightningPos);
                    forAllTargets(player -> {
                        if (player.isAt(lightningPos)) {
                            player.hit(new Hit(npc).randDamage(getMaxDamage()/2));
                            player.stun(2, true);
                            player.getPrayer().slashPrayers();
                            player.sendMessage(Color.RED.wrap("You've been electrocuted to the spot!"));
                        }
                    });
                    event.delay(1);
                    lightningPos.translate(0, yStep, 0);
                }
            });
        }
    }

    private void swapAttack() {
        if (leftClaw.getCombat().isDead() || clenched)
            return;
        animate(leftClaw, 7359);
        delayedAnimation(leftClaw, 7355, 2);
        LinkedList<Player> targets = new LinkedList<>(getAllTargets());
        for (int i = 0; i < 4; i++) {
            if (targets.isEmpty())
                return;
            Player player = targets.pop();
            Player other = null;
            Position tile = null;
            if (!targets.isEmpty())
                other = targets.pop();
            if (other != null) {
                player.sendMessage("The Great Olm has paired you with " + Color.RED.wrap(other.getName()) + "! The magical power will enact soon...");
                other.sendMessage("The Great Olm has paired you with " + Color.RED.wrap(player.getName()) + "! The magical power will enact soon...");
            } else {
                player.sendMessage("The Great Olm had no one to pair you with! The magical power will enact soon...");
                tile = centerTargetBounds.randomPosition();
            }
            int gfxId = 1359 + i;
            Player finalOther = other;
            Position finalTile = tile;
            npc.addEvent(event -> {
                int ticks = 0;
                while (ticks++ < 4) {
                    player.graphics(gfxId, 0, 0);
                    if (finalOther != null)
                        finalOther.graphics(gfxId, 0, 0);
                    else
                        World.sendGraphics(gfxId, 0, 0, finalTile);
                    event.delay(2);
                }
                int distance = player.getPosition().distance(finalOther != null ? finalOther.getPosition() : finalTile);
                if (distance > 20)
                    return;
                if (distance == 0) {
                    player.sendMessage("The teleport attack has no effect!");
                    if (finalOther != null)
                        finalOther.sendMessage("The teleport attack has no effect!");
                } else {
                    player.getMovement().teleport(finalOther != null ? finalOther.getPosition() : finalTile);
                    player.hit(new Hit(npc).fixedDamage(distance * 4));
                    if (finalOther != null) {
                        finalOther.getMovement().teleport(player.getPosition());
                        finalOther.hit(new Hit(npc).fixedDamage(distance * 4));
                    }
                }
            });
        }
    }

    private void clawHealing() {
        if (leftClaw.getCombat().isDead() || clenched || clawHealing)
            return;
        animate(leftClaw, 7357);
        clawHealing = true;
        npc.addEvent(event -> {
            event.delay(15);
            clawHealing = false;
            animate(leftClaw, 7355);
        });
    }

    //phase specific attacks
    private void crystalBomb() {
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        int bombCount = 1;
        if (chamber.getRaid().getPartySize() >= 30)
            bombCount = 3;
        else if (chamber.getRaid().getPartySize() >= 15)
            bombCount = 2;
        for (int i = 0; i < bombCount; i++) {
            npc.addEvent(event -> {
                Position bombPos = arenaBounds.randomPosition();
                CRYSTAL_BOMB_PROJECTILE.send(npc, bombPos);
                event.delay(5);
                GameObject bomb = GameObject.spawn(29766, bombPos, 10, 0);
                event.delay(8);
                bomb.remove();
                World.sendGraphics(40, 0, 0, bombPos);
                forAllTargets(p -> {
                    int distance = p.getPosition().distance(bombPos);
                    if (distance > 3)
                        return;
                    p.hit(new Hit(npc).fixedDamage(60 - (distance * 10)));
                });
            });
        }
    }

    private void crystalMark() {
        forAllTargets(p -> p.sendMessage("The Great Olm sounds a cry..."));
        List<Player> potentialTargets = getFacingTargets();
        if (potentialTargets.size() == 0)
            return;
        Player target = Random.get(potentialTargets);
        target.sendMessage(Color.RED.wrap("The Great Olm has chosen you as its target - watch out!"));
        target.graphics(246);
        npc.addEvent(event -> {
            int crystals = 0;
            while (crystals++ < 10) {
                target.graphics(246);
                Position pos = target.getPosition().copy();
                event.delay(3);
                target.graphics(246);
                getAllTargets().forEach(p -> {
                    if (p.getPosition().equals(pos))
                        p.hit(new Hit(npc).randDamage(10, 15));
                });
                int delay = CRYSTAL_SPIKE_PROJECTILE.send(pos.relative(0, 1), pos);
                World.sendGraphics(1353, 0, delay, pos);
                event.delay(3);
                target.graphics(246);
                getAllTargets().forEach(p -> {
                    if (p.getPosition().equals(pos))
                        p.hit(new Hit(npc).randDamage(5, 10));
                });
            }
        });
    }

    private static final StatType[] BURN_STAT_DRAIN = {StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};

    private void burnPlayer(Player player, boolean wasSpread) {
        if (player.get("OLM_BURN_EFFECT") != null)
            return;
        if (wasSpread)
            player.forceText("I will burn with you!");
        player.addEvent(event -> {
            player.set("OLM_BURN_EFFECT", true);
            for (int i = 0; i < 5; i++) {
                if (!player.getPosition().inBounds(arenaBounds))
                    return;
                if (!wasSpread || i > 0)
                    player.forceText("Burn with me!");
                player.hit(new Hit(npc).fixedDamage(5));
                for (StatType type : BURN_STAT_DRAIN)
                    player.getStats().get(type).drain(2);
                player.localPlayers().forEach(other -> {
                    if (other.getPosition().isWithinDistance(player.getPosition(), 1))
                        burnPlayer(other, true);
                });
                event.delay(8);
            }
            player.remove("OLM_BURN_EFFECT");
        });
    }

    private void burnAttack() {
        List<Player> potentialTargets = getFacingTargets();
        if (potentialTargets.size() == 0)
            return;
        Player target = Random.get(potentialTargets);
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        BURN_PROJECTILE.send(npc, target);
        npc.addEvent(event -> {
            event.delay(2);
            burnPlayer(target, false);
        });
    }

    private List<GameObject> fires = new ArrayList<>(20);
    private void flameWall() {
        List<Player> potentialTargets = getFacingTargets();
        if (potentialTargets.size() == 0)
            return;
        Player target = Random.get(potentialTargets);
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        int targetY = target.getPosition().getY();
        int localY = targetY & 63;
        if (localY <= 36 || localY >= 51) // fail
            return;
        int projectileX = isOnEastSide() ? npc.getSpawnPosition().getX() - 10 : npc.getSpawnPosition().getX() - 1;
        Position src1 = new Position(projectileX, targetY + 1, 0);
        FLAME_WALL_PROJECTILE_1.send(npc, src1);
        Position src2 = new Position(projectileX, targetY - 1, 0);
        FLAME_WALL_PROJECTILE_1.send(npc, src2);
        npc.addEvent(event -> {
            event.delay(1);
            if (isOnEastSide()) {
                for (int x = projectileX; x < projectileX + 10; x++) {
                    FLAME_WALL_PROJECTILE_2.send(src1.getX(), src1.getY(), x, targetY + 1);
                    FLAME_WALL_PROJECTILE_2.send(src2.getX(), src2.getY(), x, targetY - 1);
                }
            } else {
                for (int x = projectileX; x > projectileX - 10; x--) {
                    FLAME_WALL_PROJECTILE_2.send(src1.getX(), src1.getY(), x, targetY + 1);
                    FLAME_WALL_PROJECTILE_2.send(src2.getX(), src2.getY(), x, targetY - 1);
                }
            }
            event.delay(1);
            if (isOnEastSide()) {
                for (int x = projectileX; x < projectileX + 10; x++) {
                    fires.add(GameObject.spawn(32297, x, targetY + 1, 0, 10, 0));
                    fires.add(GameObject.spawn(32297, x, targetY - 1, 0, 10, 0));
                }
            } else {
                for (int x = projectileX; x > projectileX - 10; x--) {
                    fires.add(GameObject.spawn(32297, x, targetY + 1, 0, 10, 0));
                    fires.add(GameObject.spawn(32297, x, targetY - 1, 0, 10, 0));
                }
            }
            event.delay(10);
            getAllTargets().forEach(p -> {
                if (p.getAbsY() == targetY)
                    p.hit(new Hit(npc).randDamage(35, 60));
            });
            event.delay(1);
            fires.forEach(gameObject -> {
                if (!gameObject.isRemoved())
                    gameObject.remove();
            });
        });
    }

    private void startAcidPoolEvent() {
        npc.addEvent(event -> {
            while (!isDead()) {
                forAllTargets(p -> {
                    if (Tile.getObject(30032, p.getAbsX(), p.getAbsY(), p.getHeight(), 10, -1) != null) {
                        p.hit(new Hit(HitType.POISON).randDamage(3,6));
                        p.poison(4);
                    }
                });
                event.delay(1);
            }
        });
    }

    private void spawnAcidPool(Position position, int duration) {
        GameObject pool = GameObject.spawn(30032, position, 10, 0);
        npc.addEvent(event -> {
            event.delay(duration);
            pool.remove();
        });
    }

    private void acidPoolsAttack() {
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        int poisonPools = 6 + (chamber.getRaid().getPartySize() / 3);
        for (int i = 0; i < poisonPools; i++) {
            Position pos = arenaBounds.randomPosition();
            ACID_POOL_PROJECTILE.send(npc, pos);
            npc.addEvent(event -> {
                event.delay(3);
                GameObject pool = GameObject.spawn(30032, pos, 10, 0);
                event.delay(6);
                pool.remove();
            });
        }
    }

    private void acidDrip() {
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        List<Player> potentialTargets = getFacingTargets();
        if (potentialTargets.size() == 0)
            return;
        Player target = Random.get(potentialTargets);
        ACID_DRIP_PROJECTILE.send(npc, target);
        target.sendMessage(Color.RED.wrap("The Great Olm has smothered you in acid. It starts to drip off slowly."));
        target.addEvent(event -> {
            event.setCancelCondition(this::isDead);
            event.delay(3);
            int ticks = 0;
            while (ticks++ < 20) {
                if (Tile.getObject(30032, target.getAbsX(), target.getAbsY(), 0, 10, -1) != null) {
                    event.delay(1);
                    continue;
                }
                Position position = target.getLastPosition().copy();
                if (position.distance(target.getPosition()) > 16) {
                    //teleported or something
                    return;
                }
                if (position.equals(target.getPosition())) {
                    if (Tile.getObject(30032, position.getX(), position.getY(), position.getZ(), 10, -1) == null) {
                        spawnAcidPool(position, 10);
                    }
                    event.delay(1);
                    continue;
                }
                int iterations = 0;
                while (iterations++ < 2) {
                    if (Tile.getObject(30032, position.getX(), position.getY(), position.getZ(), 10, -1) == null) {
                        spawnAcidPool(position, 10);
                    }
                    if (position.equals(target.getPosition())) {
                        break;
                    }
                    Direction dir = Direction.getDirection(position, target.getPosition());
                    position.translate(dir.deltaX, dir.deltaY, 0);
                }
                event.delay(1);
            }
        });
    }


    private void siphonAttack() {
        animate(npc, facing.getAttackAnim(isEmpowered()));
        delayedAnimation(npc, facing.getIdleAnim(isEmpowered()), 1);
        npc.addEvent(event -> {
            event.setCancelCondition(this::isDead);
            Position[] siphons = new Position[2];
            for (int i = 0; i < siphons.length; i++) {
                siphons[i] = centerTargetBounds.randomPosition();
                SIPHON_PROJECTILE.send(npc, siphons[i]);
            }
            event.delay(4);
            for (Position siphon : siphons) {
                World.sendGraphics(1363, 0, 0, siphon);
            }
            event.delay(5);
            int damageDealt = 0;
            for (Player player : getAllTargets()) {
                boolean safe = false;
                for (Position siphon : siphons) {
                    if (player.getPosition().equals(siphon)) {
                        safe = true;
                        break;
                    }
                }
                if (!safe) {
                    damageDealt += player.hit(new Hit(npc).randDamage(5, 10));
                }
            }
            npc.hit(new Hit(HitType.HEAL).fixedDamage(damageDealt * 3));
        });
    }


    private void turn() {
        Facing dest = getTurnDestination();
        if (dest == null || dest == facing)
            return;
        if ((facing == LEFT && dest == RIGHT) || (facing == RIGHT && dest == LEFT) || (facing == LEFT && dest == CENTER)) { // 'far' transition
            animate(npc, dest.getFarTransitionAnim(isEmpowered()));
            delayedAnimation(npc, dest.getIdleAnim(isEmpowered()), 1);
        } else { // 'close' transition
            animate(npc, dest.getCloseTransitionAnim(isEmpowered()));
            delayedAnimation(npc, dest.getIdleAnim(isEmpowered()), 1);
        }
        facing = dest;
    }

    private Facing getTurnDestination() {
        if (target == null)
            target = Random.get(getAllTargets());
        if (target.getPosition().inBounds(centerTargetBounds))
            return CENTER;
        else if (target.getPosition().inBounds(northTargetBounds))
            return isOnEastSide() ? RIGHT : LEFT;
        else
            return isOnEastSide() ? LEFT : RIGHT;
    }

    private void animate(NPC npc, int animationId) {
        GameObject obj = getObject(npc);
        if (obj != null)
            obj.animate(animationId);
    }

    private void delayedAnimation(NPC npc, int animationId, int delay) {
        npc.addEvent(event -> {
            event.delay(delay);
            animate(npc, animationId);
        });
    }

    private GameObject getObject(NPC npc) {
        if (isOnEastSide())
            return Tile.getObject(-1, npc.getAbsX(), npc.getAbsY(), npc.getHeight(), 10, -1);
        else
            return Tile.getObject(-1, npc.getAbsX() - 3, npc.getAbsY(), npc.getHeight(), 10, -1);

    }

    private void forAllTargets(Consumer<Player> action) {
        npc.getPosition().getRegion().players.stream()
                .filter(p -> p.getHeight() == 0 && (p.getPosition().getY() & 63) >= 34) // on olm floor, past the barrier
                .forEach(action);
    }

    private List<Player> getAllTargets() {
        return npc.getPosition().getRegion().players.stream()
                .filter(p -> p.getHeight() == 0 && (p.getPosition().getY() & 63) >= 34) // on olm floor, past the barrier
                .collect(Collectors.toList());
    }

    private List<Player> getFacingTargets() {
        Bounds bounds;
        if (facing == CENTER)
            bounds = centerTargetBounds;
        else if (facing == RIGHT)
            bounds = isOnEastSide() ? northTargetBounds : southTargetBounds;
        else
            bounds = isOnEastSide() ? southTargetBounds : northTargetBounds;
        return npc.getPosition().getRegion().players.stream()
                .filter(p -> p.getPosition().inBounds(bounds))
                .collect(Collectors.toList());
    }

    private void forFacingTargets(Consumer<Player> action) {
        Bounds bounds;
        if (facing == CENTER)
            bounds = centerTargetBounds;
        else if (facing == RIGHT)
            bounds = isOnEastSide() ? northTargetBounds : southTargetBounds;
        else
            bounds = isOnEastSide() ? southTargetBounds : northTargetBounds;
        npc.getPosition().getRegion().players.stream()
                .filter(p -> p.getPosition().inBounds(bounds))
                .forEach(action);
    }

    @Override
    public void faceTarget() {
        //no face
    }

    private boolean isOnEastSide() {
        return npc.getPosition().equals(npc.spawnPosition);
    }


    private void postDamage(Hit hit) {
        if (hit.damage == 0 || hit.type == HitType.HEAL)
            return;
        if (currentPhase != lastPhase || !leftClaw.getCombat().isDead() || !rightClaw.getCombat().isDead()) {
            npc.hit(new Hit(HitType.HEAL).fixedDamage(hit.damage).delay(3));
        }
    }

    private boolean isEmpowered() {
        return currentPhase == lastPhase;
    }



}
