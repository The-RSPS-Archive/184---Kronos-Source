package io.ruin.model.activities.bosses.slayer.sire;

import io.ruin.api.protocol.world.WorldType;
import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.AttackNpcListener;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.spells.ancient.ShadowBarrage;
import io.ruin.model.skills.magic.spells.ancient.ShadowBlitz;
import io.ruin.model.skills.magic.spells.ancient.ShadowBurst;
import io.ruin.model.skills.magic.spells.ancient.ShadowRush;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.process.event.Event;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;
import kilim.Pausable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AbyssalSire extends NPCCombat {

    //animations
    private static final int TENTACLE_WAKE_UP_ANIM = 7108;
    private static final int TENTACLE_ATTACK_ANIM = 7109;
    private static final int TENTACLE_WAKE_UP_ANIM_2 = 7111;
    private static final int TENTACLE_SLEEP_ANIM = 7112;
    private static final int TENTACLE_WAKE_UP_ANIM_3 = 7114;

    private static final int SIRE_WAKE_ANIM = 4528;
    private static final int SIRE_PHASE_1_SPAWN = 4530;
    private static final int SIRE_PHASE_1_POISON = 4531;
    private static final int SIRE_PHASE_1_TRANSITION = 4532;

    private static final int SIRE_PHASE_2_MELEE = 5366;
    private static final int SIRE_PHASE_2_POISON = 5367;
    private static final int SIRE_PHASE_2_SPAWN = 7095;
    private static final int SIRE_PHASE_2_STRONG_MELEE = 5369;
    private static final int SIRE_PHASE_2_STRONGER_MELEE = 5755;
    private static final int SIRE_PHASE_2_TRANSITION = 7096;

    private static final int SIRE_PHASE_3_EXPLODE = 7098;

    //gfx
    private static final Projectile SPAWN_PROJECTILE = new Projectile(1274, 70, 0, 50, 70, 10, 12, 127);
    private static final int POISON_EFFECT = 1275;

    //npc ids
    private static final int STUNNED_TENTACLE = 5911;
    private static final int ACTIVE_TENTACLE = 5912;
    private static final int SPAWN = 5916;

    private List<NPC> tentacles = new ArrayList<>(6);
    private List<NPC> respiratorySystemNPCs = new ArrayList<>(4);
    private List<GameObject> respiratorySystemObjects = new ArrayList<>(4);
    private List<NPC> spawns = new LinkedList<>();
    private NPC nwTentacle, wTentacle, swTentacle, neTentacle, eTentacle, seTentacle;

    private int phase = 0;
    private int activatedBy = -1;
    private TickDelay resetDelay = new TickDelay();
    private int attempts = 0;


    static {
        for (int id : Arrays.asList(5886, 5887, 5888, 5889, 5890, 5891, 5908, 5916, 5917, 5918))
            NPCDef.get(id).ignoreOccupiedTiles = true;
    }

    @Override
    public void init() {
        for (int[] set : new int[][] { // this is some quality code right here
                                        // clipping all around the arena is messed up, possibly something wrong with map loading? so need to block the tiles individually...
                {0, -5},
                {1, -5},
                {2, -5},
                {3, -5},
                {4, -5},
                {5, -6},
                {0, -6},
                {-1, -6},
                {-2, -6},
                {-3, -6},
                {-4, -6},
                {-4, -7},
                {-4, -8},
                {-4, -9},
                {-5, -9},
                {-6, -9},
                {-7, -9},
                {-8, -9},
                {-9, -9},
                {-10, -9},
                {-11, -9},
                {-11, -10},
                {-11, -11},
                {-11, -12},
                {-10, -12},
                {-9, -12},
                {-8, -12},
                {-7, -12},
                {-6, -12},
                {-5, -12},
                {-4, -12},
                {-3, -12},
                {-3, -13},
                {-3, -14},
                {-2, -14},
                {-1, -14},
                {-1, -15},
                {0, -15},
                {0, -16},
                {0, -17},
                {-1, -17},
                {-1, -18},
                {-2, -18},
                {-3, -18},
                {-3, -19},
                {-5, -23},
                {-4, -23},
                {-3, -23},
                {-3, -24},
                {-3, -25},
                {-2, -25},
                {-2, -26},
                {-1, -26},
                {0, -26},
                {0, -27},
                {1, -27},
                {2, -27},
                {2, -28},
                {2, -29},
                {2, -30},
                {2, -31},
                {13, -31},
                {12, -31},
                {11, -31},
                {10, -31},
                {10, -30},
                {11, -29},
                {11, -28},
                {11, -27},
                {10, -27},
                {10, -26},
                {10, -25},
                {10, -24},
                {10, -23},
                {11, -23},
                {12, -23},
                {13, -23},
                {14, -23},
                {14, -20},
                {14, -24},
                {14, -25},
                {15, -25},
                {16, -25},
                {17, -25},
                {17, -24},
                {17, -23},
                {17, -22},
                {17, -21},
                {17, -20},
                {16, -20},
                {15, -20},
                {13, -20},
                {12, -20},
                {11, -20},
                {10, -20},
                {9, -19},
                {9, -20},
                {8, -19},
                {7, -19},
                {7, -18},
                {7, -17},
                {7, -16},
                {7, -15},
                {8, -14},
                {9, -14},
                {9, -13},
                {10, -13},
                {11, -13},
                {12, -13},
                {13, -13},
                {13, -9},
                {14, -9},
                {14, -10},
                {14, -11},
                {14, -12},
                {14, -13},
                {12, -9},
                {11, -9},
                {10, -9},
                {9, -9},
                {9, -8},
                {9, -7},
                {8, -6},
                {7, -6},
                {6, -6},
                {5, -6},
                {5, -5},
                {4, -5},
                {-4, -19},

                {-5, -19},
                {-6, -19},
                {-7, -19},
                {-8, -19},
                {-8, -20},
                {-8, -21},
                {-8, -22},
                {-8, -23},
                {-7, -23},
                {-6, -23},

                {1, -31},
                {0, -31},
                {-2, -31},
                {-1, -31},
                {-3, -31},
                {-4, -31},
                {-5, -31},
                {-6, -31},
                {-7, -31},
                {-8, -31},
                {-9, -31},
                {-10, -31},
                {-11, -31},
                {14, -31},
                {15, -31},
                {16, -31},
                {17, -31},
                {18, -31},
                {19, -31},
                {20, -31},
                {22, -31},

        }) {
            Tile.get(npc.getSpawnPosition().getX() + set[0], npc.getSpawnPosition().getY() + set[1], npc.getSpawnPosition().getZ(), true).flagUnmovable();
//            GameObject.spawn(6084,npc.getSpawnPosition().getX() + set[0], npc.getSpawnPosition().getY() + set[1], npc.getSpawnPosition().getZ(), 10, 0); // enable for debugging
        }
        npc.hitsUpdate.hpBarType = 2;
        //spawn tentacles
        tentacles.add(nwTentacle = new NPC(5910).spawn(npc.getSpawnPosition().getX() - 10, npc.getSpawnPosition().getY() - 11, npc.getSpawnPosition().getZ(), Direction.SOUTH, 0)); //nw tentacle
        tentacles.add(wTentacle = new NPC(STUNNED_TENTACLE).spawn(npc.getSpawnPosition().getX() - 7, npc.getSpawnPosition().getY() - 20, npc.getSpawnPosition().getZ(), Direction.SOUTH, 0)); //w tentacle
        tentacles.add(swTentacle = new NPC(5909).spawn(npc.getSpawnPosition().getX() - 9, npc.getSpawnPosition().getY() - 29, npc.getSpawnPosition().getZ(), Direction.SOUTH, 0)); //sw tentacle
        tentacles.add(neTentacle = new NPC(5910).spawn(npc.getSpawnPosition().getX() + 7, npc.getSpawnPosition().getY() - 11, npc.getSpawnPosition().getZ(), Direction.SOUTH, 0)); //ne tentacle
        tentacles.add(eTentacle = new NPC(STUNNED_TENTACLE).spawn(npc.getSpawnPosition().getX() + 5, npc.getSpawnPosition().getY() - 20, npc.getSpawnPosition().getZ(), Direction.SOUTH, 0)); //e tentacle
        tentacles.add(seTentacle = new NPC(5909).spawn(npc.getSpawnPosition().getX() + 8, npc.getSpawnPosition().getY() - 29, npc.getSpawnPosition().getZ(), Direction.SOUTH, 0)); //se tentacle
        tentacles.forEach(n -> n.addEvent(event -> tentacleLogic(n, event)));
        //spawn resp systems
        Position nwResp = npc.getSpawnPosition().relative(-13, -11);
        Position neResp = npc.getSpawnPosition().relative(15, -12);
        Position seResp = npc.getSpawnPosition().relative(18, -22);
        Position swResp = npc.getSpawnPosition().relative(-10, -21);
        respiratorySystemObjects.add(Tile.getObject(26954, nwResp.getX(), nwResp.getY(), nwResp.getZ(), -1, -1));
        respiratorySystemObjects.add(Tile.getObject(26954, swResp.getX(), swResp.getY(), swResp.getZ(), -1, -1));
        respiratorySystemObjects.add(Tile.getObject(26954, neResp.getX(), neResp.getY(), neResp.getZ(), -1, -1));
        respiratorySystemObjects.add(Tile.getObject(26954, seResp.getX(), seResp.getY(), seResp.getZ(), -1, -1));
        respiratorySystemObjects.forEach(o -> o.setId(26953));

        respiratorySystemNPCs.add(new NPC(5914).spawn(nwResp));
        respiratorySystemNPCs.add(new NPC(5914).spawn(swResp));
        respiratorySystemNPCs.add(new NPC(5914).spawn(neResp));
        respiratorySystemNPCs.add(new NPC(5914).spawn(seResp));

        AttackNpcListener attackListener = (player, npc1, message) -> {
            if (World.type == WorldType.ECO && !Slayer.isTask(player, npc)) { // slayer-only, single boss despite being in multi
                if (message)
                    player.sendMessage("The Sire takes no interest in you.");
                return false;
            }
            if (activatedBy != -1 && player.getUserId() != activatedBy) {
                if (message)
                    player.sendMessage("The Sire takes no interest in you.");
                return false;
            }

            if (npc.isLocked())
                return false;
            return true;
        };
        respiratorySystemNPCs.forEach(resp -> {
            resp.deathEndListener = (entity, killer, killHit) -> respiratoryDeath(resp);
            resp.getCombat().setAllowRespawn(false);
            resp.hitListener = new HitListener().postDefend(hit -> { // reduced damage if tentacles are awake
               if (tentacles.get(0).getId() == ACTIVE_TENTACLE && !hit.isBlocked())
                   hit.damage /= 10;
            });
            resp.attackNpcListener = attackListener;
        });

        npc.attackNpcListener = attackListener;
        npc.deathEndListener = (entity, killer, killHit) -> resetStates();
        npc.deathStartListener = (entity, killer, killHit) -> spawns.forEach(n -> {
            killer.player.deathStartListener = null;
            killer.player.teleportListener = null;

           if (!n.getCombat().isDead() && !n.isRemoved())
               n.getCombat().startDeath(killHit);
        });
        npc.hitListener = new HitListener().postDefend(this::postDefend);
        npc.addEvent(event -> {
            event.delay(100);
            while (true) {
                if (phase > 0 && !resetDelay.isDelayed()) {
                    Player owner = World.getPlayer(activatedBy, true);
                    if (owner == null || !owner.getPosition().isWithinDistance(npc.getPosition(), 30)){
                        npc.setHidden(true);
                        event.delay(1);
                        respawn();
                        resetStates();
                    }
                }
                event.delay(5);
            }
        });
    }

    @Override
    public int getAttackBoundsRange() {
        return 40;
    }

    private void postDefend(Hit hit) {
        resetDelay.delay(100);
        if (phase == 0 && hit.attacker != null && hit.attacker.player != null) {
            startFight(hit.attacker.player.getUserId());
            hit.attacker.player.deathStartListener = (DeathListener.SimpleKiller) killer -> activatedBy = -1;
            hit.attacker.player.teleportListener = killer -> {
                activatedBy = -1;
                return true;
            };
        } else if (phase == 1 && npc.getId() != 5888) { // look for stun
            if (npc.getHp() - hit.damage <= npc.getMaxHp() - 75) {
                stun();
                hit.attacker.player.sendMessage("The Sire has been disorientated.");
            } else if (Random.get() <= getStunChance(hit.attackSpell)) {
                stun();
                hit.attacker.player.sendMessage("Your Shadow spell disorientates the Sire.");
            }
        } else if (phase == 2 && npc.getHp() < npc.getMaxHp() / 2) {
            startPhase3();
        } else if (phase == 3 && npc.getHp() < 140) {
            hit.nullify();
            startPhase4();
        }
    }

    private void startPhase4() {
        phase = 4;
        npc.transform(5908);
        npc.animate(SIRE_PHASE_3_EXPLODE);
        npc.faceNone(false);
        npc.face(Direction.SOUTH);
        npc.lock(LockType.FULL_DELAY_DAMAGE);
        npc.addEvent(event -> {
            teleportPlayer();
            event.delay(6);
            delayAttack(7);
            npc.localPlayers().forEach(p -> {
                if (Misc.getEffectiveDistance(npc, p) <= 1) {
                    p.hit(new Hit(npc, null).randDamage(72).ignoreDefence().ignorePrayer().delay(0));
                }
            });
            int spawns = Random.get(3, 6);
            for (int i = 0; i < spawns; i++) {
                spawn(Random.get(npc.localPlayers()), npc.getPosition().relative(Random.get(1, 4), Random.get(1, 4)));
            }
            npc.unlock();
            npc.face(Direction.SOUTH);
        });
    }

    private void teleportPlayer() {
        if (target == null) target = Random.get(npc.localPlayers());
        final Player player = target.player;
        player.getMovement().startTeleport(e -> {
            player.animate(1816);
            player.graphics(342);
            e.delay(3);
            player.getMovement().teleport(npc.getAbsX() + 2, npc.getAbsY() - 1, npc.getHeight());
        });
    }

    private void startPhase3() {
        phase = 3;
        npc.lock(LockType.FULL_NULLIFY_DAMAGE);
        npc.faceNone(false);
        npc.addEvent(event -> {
            Position dest = npc.getSpawnPosition().relative(0, -18);
            while (!npc.isAt(dest)) {
                npc.step(dest.getX() - npc.getAbsX(), dest.getY() - npc.getAbsY(), StepType.FORCE_WALK);
                event.delay(1);
            }
            npc.animate(SIRE_PHASE_2_TRANSITION);
            event.delay(2);
            npc.transform(5891);
            event.delay(6);
            recoverTentacles();
            npc.unlock();
        });
    }

    private void stun() {
        npc.transform(5888);
        stunTentacles();
        delayAttack(30);
        npc.addEvent(event -> {
            event.delay(50);
            if (phase != 1) { // player killed all resp systems while we were stunned
                return;
            }
            npc.transform(5887);
            npc.animate(SIRE_WAKE_ANIM);
            npc.setHp(npc.getMaxHp());
            recoverTentacles();
        });
    }

    private void stunTentacles() {
        tentacles.forEach(n -> {
            if (n.getId() == STUNNED_TENTACLE) // already sleepin
                return;
            n.transform(STUNNED_TENTACLE);
            n.animate(TENTACLE_SLEEP_ANIM);
            n.faceNone(false);
            n.face(Direction.SOUTH);
        });
    }

    private void recoverTentacles() {
        if (phase == 2)
            return;
        tentacles.forEach(n -> {
            n.animate(TENTACLE_WAKE_UP_ANIM_3);
            n.addEvent(event -> {
                event.delay(2);
                if (npc.getId() == 5888)
                    return;
                n.transform(ACTIVE_TENTACLE);
            });
        });
    }


    private double getStunChance(Spell spell) {
        if (spell == null)
            return 0;
        else if (spell == ShadowRush.INSTANCE)
            return 0.25;
        else if (spell == ShadowBurst.INSTANCE)
            return 0.5;
        else if (spell == ShadowBlitz.INSTANCE)
            return 0.75;
        else if (spell == ShadowBarrage.INSTANCE)
            return 1;
        else
            return 0;

    }

    private void startFight(int uid) {
        activatedBy = uid;
        npc.animate(SIRE_WAKE_ANIM);
        phase = 1;
        npc.transform(5887);
        delayAttack(8);
        wakeTentacles();
    }

    private void wakeTentacles() { // from INITIAL state, not stunned
        nwTentacle.animate(7108);
        neTentacle.animate(7108);
        wTentacle.animate(7114);
        eTentacle.animate(7114);
        swTentacle.animate(7111);
        seTentacle.animate(7111);
        tentacles.forEach(n -> {
            n.addEvent(event -> {
                event.delay(2);
                n.transform(5912);
            });
        });
    }

    @Override
    public boolean canAttack(Entity target) {
        if (target.player == null)
            return false;
        return activatedBy != -1 && activatedBy == target.player.getUserId() && super.canAttack(target);
    }

    private void tentacleLogic(NPC tentacle, Event event) throws Pausable {
        while (true) {
            if (tentacle.localPlayers().size() == 0) { // thought this wasnt necessary but apparently it is
                event.delay(3);
                continue;
            }
            Player player = Random.get(tentacle.localPlayers());
            if (isDead() || player == null || tentacle.getId() != ACTIVE_TENTACLE || phase == 2) {
                event.delay(5);
            } else {
                tentacle.faceTemp(player);
                if (player.getPosition().getX() >= tentacle.getAbsX() && player.getPosition().getX() <= tentacle.getAbsX() + 8
                        && player.getPosition().getY() >= tentacle.getAbsY() && player.getPosition().getY() <= tentacle.getAbsY() + 8) {
                    tentacle.animate(TENTACLE_ATTACK_ANIM);
                    player.hit(new Hit().randDamage(30).ignoreDefence().ignorePrayer()); // no attacker, tentacles have no stats
                    event.delay(4);
                } else {
                    event.delay(2);
                }
            }
        }
    }

    private void resetStates() {
        respiratorySystemNPCs.forEach(n -> n.getCombat().respawn());
        respiratorySystemObjects.forEach(o -> o.setId(26953));
        phase = 0;
        npc.transform(5886);
        nwTentacle.transform(5910);
        wTentacle.transform(STUNNED_TENTACLE);
        swTentacle.transform(5909);
        neTentacle.transform(5910);
        eTentacle.transform(STUNNED_TENTACLE);
        seTentacle.transform(5909);
        tentacles.forEach(n -> n.face(Direction.SOUTH));
        spawns.forEach(n -> n.remove());
        spawns.clear();
    }

    private void respiratoryDeath(NPC respSystem) {
        GameObject obj = Tile.getObject(-1, respSystem.getAbsX(), respSystem.getAbsY(), respSystem.getHeight());
        obj.animate(7102);
        World.startEvent(event -> {
            event.delay(5);
            obj.setId(26954);
        });
        if (respiratorySystemNPCs.stream().allMatch(n -> n.getCombat().isDead()) && phase == 1) { // all systems dead, onwards to phase 2!
            stunTentacles();
            phase = 2;
            npc.addEvent(event -> {
                npc.lock(LockType.FULL_NULLIFY_DAMAGE);
                npc.setHp(npc.getMaxHp());
                npc.transform(5889);
                npc.animate(SIRE_PHASE_1_TRANSITION);
                event.delay(5);
                Position dest = npc.getSpawnPosition().relative(0, -7);
                while (!npc.isAt(dest)) {
                    npc.step(dest.getX() - npc.getAbsX(), dest.getY() - npc.getAbsY(), StepType.FORCE_WALK);
                    event.delay(1);
                }
                event.waitForMovement(npc);
                event.delay(1);
                npc.transform(5890);
                npc.unlock();
            });
        }
    }

    @Override
    public void follow() {
        if (phase != 2)
            return;
        follow(1);
    }

    @Override
    public Position getDropPosition() {
        return npc.getPosition().relative(2, 2); // just cause
    }

    @Override
    public boolean attack() {
        if (!withinDistance(12))
            return false;
        switch (phase) {
            case 1:
                npc.face(Direction.SOUTH);
                if (Random.rollDie(3, 1)) { // doesnt actually attack every turn
                    if (Random.rollDie(4, 1))
                        projectileSpawn();
                    else
                        poisonPool(target.getPosition().copy());
                }
                break;
            case 2:
                if (!withinDistance(1)) {
                    if (npc.getMovement().isAtDestination()) {
                        if (++attempts == 5) {
                            teleportPlayer();
                            attempts = 0;
                        }
                        return false;
                    } else {
                        return false;
                    }
                } else {
                    if (Random.rollDie(6, 4))
                        meleeAttack();
                    else if (Random.rollDie(2, 1))
                        poisonPool(target.getPosition().copy());
                    else
                        projectileSpawn();
                }
                break;
            case 3:
                npc.faceNone(false);
                npc.face(Direction.SOUTH);
                poisonPool(target.getPosition().copy());
                if (Random.rollDie(3, 1))
                    spawn();
                break;
            case 4:
                npc.faceNone(false);
                npc.face(Direction.SOUTH);
                spawn();
                if (Random.rollDie(2, 1)) // anotha one
                    spawn();
                break;
        }
        return true;
    }

    private void meleeAttack() { // phase 2 ONLY
        if (phase != 2)
            throw new IllegalStateException();
        double roll = Random.get();
        int minDamage, maxDamage;
        if (roll < 0.25) { // double back swipe
            minDamage = 8;
            maxDamage = 26;
            npc.animate(SIRE_PHASE_2_STRONGER_MELEE);
        } else if (roll < 0.65) { // single back swipe
            minDamage = 4;
            maxDamage = 12;
            npc.animate(SIRE_PHASE_2_STRONG_MELEE);
        } else { // arm swipe
            minDamage = 2;
            maxDamage = 6;
            npc.animate(SIRE_PHASE_2_MELEE);
        }
        if (target.player != null) {
            if (!target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
                minDamage *= 2;
                maxDamage *= 2;
            }
        }
        target.hit(new Hit(npc, AttackStyle.SLASH).randDamage(minDamage, maxDamage).ignorePrayer());
    }

    private void projectileSpawn() {
        spawns.removeIf(n -> n.getCombat().isDead());
        if (spawns.size() >= 14)
            return;
        if (phase == 1)
            npc.animate(SIRE_PHASE_1_SPAWN);
        else
            npc.animate(SIRE_PHASE_2_SPAWN); // phases 3 and 4 don't do projectile-based spawns!
        Position dest = Random.get(target.getPosition().area(2, pos -> pos.getTile().clipping == 0));
        int delay = SPAWN_PROJECTILE.send(npc, dest.getX(), dest.getY());
        final Entity entity = target;
        npc.addEvent(event -> {
            event.delay((delay * 25) / 600 - 1);
            if (isDead())
                return;
            spawn(entity, dest);
        });

    }

    private void spawn() {
        spawn(target, Random.get(target.getPosition().area(2, pos -> pos.getTile().clipping == 0)));
    }

    private void spawn(Entity entity, Position dest) {
        spawns.removeIf(n -> n.getCombat().isDead());
        if (spawns.size() >= 14)
            return;
        if (target == null) {
            return;
        }
        NPC spawn = new NPC(SPAWN);
        spawn.spawn(dest).attackBounds = npc.attackBounds;
        spawn.targetPlayer(entity.player, false);
        spawn.attackTargetPlayer();
        spawns.add(spawn);
    }

    private void poisonPool(Position pos) {
        if (phase == 1)
            npc.animate(SIRE_PHASE_1_POISON);
        else if (phase == 2)
            npc.animate(SIRE_PHASE_2_POISON);
        // phase 3 and 4 don't do an animation on spawning a pool
        World.startEvent(event -> {
            World.sendGraphics(POISON_EFFECT, 0, 0, pos);
            event.delay(2);
            for (int i = 0; i < 4; i++) {
                pos.getRegion().players.forEach(p -> { // IMO it would make more sense to just check for the sire's target, but OSRS does an AOE check so i guess i'll do that too... whatever
                    int distance = Misc.getDistance(p.getPosition(), pos);
                    if (distance > 1)
                        return;
                    int maxDamage = 30;
                    if (p.isPoisonImmune())
                        maxDamage *= 0.7;
                    maxDamage /= distance + 1;
                    p.hit(new Hit(HitType.POISON).randDamage(1, maxDamage).delay(0));
                    if (Random.rollDie(5, 1))
                        p.poison(8);
                });
                event.delay(1);
            }
        });
    }
}
