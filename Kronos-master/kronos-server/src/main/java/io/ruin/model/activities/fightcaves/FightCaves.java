package io.ruin.model.activities.fightcaves;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.routes.DumbRoute;

public class FightCaves {

    private static final int HOST = 2180,
            TZ_KIH = 3116,
            TZ_KEK = 3118,
            TZ_KEK_MINION = 3120,
            TOK_XIL = 3121,
            YT_MEJ_KOT = 3123,
            KET_ZEK = 3125,
            TZ_TOK_JAD = 3127,
            YT_HUR_KOT = 3128;

    private static final Position EXIT = new Position(2439, 5172, 0);

    private static final Position C = new Position(2400, 5088, 0);

    private static final Position S = new Position(2400, 5070, 0);

    private static final Position NW = new Position(2382, 5106, 0);

    private static final Position SW = new Position(2380, 5071, 0);

    private static final Position SE = new Position(2418, 5082, 0);

    private static final Position[] ROTATIONS = {SE, SW, C, NW, SW, SE, S, NW, C, SE, SW, S, NW, C, S}; //https://vgy.me/ItO1Db.png

    /**
     * Separator
     */

    private Player player;

    @Expose private int wave;

    @Expose private boolean practice;

    private int waveRotationOffset, spawnRotationOffset;

    private ActivityTimer timer;

    private DynamicMap map;

    private boolean jadHealers, killedJad;

    private boolean logoutRequest;

    private FightCaves(Player player, int wave, boolean practice) {
        this.player = player;
        this.wave = wave;
        this.practice = practice;
    }

    private void start(boolean login) {
        player.fightCaves = this;
        player.deathEndListener = (DeathListener.Simple) this::handleDeath;
        player.logoutListener = new LogoutListener().onAttempt(this::allowLogout);
        waveRotationOffset = spawnRotationOffset = Random.get(ROTATIONS.length - 1);
        timer = new ActivityTimer();
        map = new DynamicMap().build(9551, 1);
        int x, y;
        if(login) {
            x = map.convertX(C.getX());
            y = map.convertY(C.getY());
        } else {
            x = map.convertX(2412);
            y = map.convertY(5115);
        }
        player.getMovement().teleport(x, y, 0);
        map.assignListener(player).onExit((p, logout) -> stop(logout));
        map.addEvent(e -> {
            if(wave == 63) {
                if(practice)
                    DumbRoute.route(player, map.convertX(C.getX()), map.convertY(C.getY()));
                //lets be a little nice if a player logs in on jad..
                e.delay(5);
            } else {
                if(!login)
                    DumbRoute.route(player, map.convertX(C.getX()), map.convertY(C.getY()));
                player.dialogue(new NPCDialogue(HOST, "You're on your own now JalYt, prepare to fight for your life!").animate(615));
                e.delay(login ? 10 : 5);
                while(player.isVisibleInterface(Interface.NPC_DIALOGUE))
                    e.delay(1);
            }
            beginWave();
        });
    }

    private void stop(boolean logout) {
        if(logout) {
            if(!practice) {
                player.getMovement().teleport(C);
            } else {
                player.getMovement().teleport(EXIT);
                player.fightCaves = null;
            }
        } else {
            int lastWave = wave - 1;
            if(lastWave == 0) {
                player.dialogue(new NPCDialogue(HOST, "Well I suppose you tried... better luck next time.").animate(610));
            } else if(!practice) {
                int tokkul = 2 + ((lastWave - 50) * (3 + lastWave));
                if(!killedJad) {
                    if(!player.isSapphire()) {
                        player.dialogue(new NPCDialogue(HOST, "Well done in the cave, here take TokKul as reward.").animate(588));
                        player.getInventory().addOrDrop(6529, tokkul);
                    } else {
                        player.dialogue(new NPCDialogue(HOST, "Well I suppose you tried... better luck next time."));
                    }
                } else {
                    player.dialogue(new NPCDialogue(HOST, "You have defeated TzTok-Jad, I am most impressed! Please accept this gift. Give cape back to me if you not want it.").animate(615));
                    player.getInventory().addOrDrop(6570, 1);
                    player.getInventory().addOrDrop(6529, tokkul + 4000);
                }
            }
            player.fightCaves = null;
            player.deathEndListener = null;
            player.logoutListener = null;
        }
        player = null;
        map.destroy();
        map = null;
    }

    /**
     * Spawning
     */

    private void beginWave() {
        int wave = this.wave;
        if(practice)
            player.sendMessage("<col=ef1020>Practice Wave: " + wave);
        else
            player.sendMessage("<col=ef1020>Wave: " + wave);
        /**
         * "Unique" waves
         */
        if(wave == 63) {
            NPC jad = spawn(TZ_TOK_JAD);
            jad.hitListener = new HitListener().postDamage(h -> spawnHealers(jad));
            if(!practice) {
                player.sendMessage("<col=ef1020>Final Challenge!");
                jad.deathStartListener = (DeathListener.Simple) () -> {
                    player.jadCounter.increment(player);
                    player.fightCavesBestTime = timer.stop(player, player.fightCavesBestTime);
                };
            }
            player.dialogue(new NPCDialogue(HOST, "Look out, here comes TzTok-Jad!").animate(615));
            return;
        }
        if(wave == 62) {
            spawn(KET_ZEK);
            spawn(KET_ZEK + 1);
            return;
        }
        if(wave == 30) {
            spawn(YT_MEJ_KOT);
            spawn(YT_MEJ_KOT + 1);
            return;
        }
        if(wave == 14) {
            spawn(TOK_XIL);
            spawn(TOK_XIL + 1);
            return;
        }
        /**
         * "Basic" waves
         */
        while(wave >= 31) {
            wave -= 31;
            spawn(KET_ZEK);
        }
        while(wave >= 15) {
            wave -= 15;
            spawn(YT_MEJ_KOT);
        }
        while(wave >= 7) {
            wave -= 7;
            spawn(TOK_XIL);
        }
        while(wave >= 3) {
            wave -= 3;
            spawn(TZ_KEK);
        }
        while(wave > 0) {
            wave--;
            spawn(TZ_KIH);
        }
    }

    private NPC spawn(int id) {
        Position position = nextSpawnPosition();
        return spawn(id, map.convertX(position.getX()), map.convertY(position.getY()));
    }

    private NPC spawn(int id, int x, int y) {
        NPC npc = new NPC(id).spawn(x, y, 0);
        npc.deathEndListener = (DeathListener.Simple) () -> handleDeath(npc);
        npc.targetPlayer(player, false).attackTargetPlayer();
        map.addNpc(npc);
        return npc;
    }

    private void spawnHealers(NPC jad) {
        if(jadHealers || jad.getHp() > (jad.getMaxHp() / 2))
            return;
        jadHealers = true;
        spawnHealers(jad, 4);
    }

    private void spawnHealers(NPC jad, int count) {
        for(int i = 0; i < count; i++) {
            Position position = nextSpawnPosition();
            NPC healer = new NPC(YT_HUR_KOT).spawn(map.convertX(position.getX()), map.convertY(position.getY()), 0);
            healer.deathEndListener = (DeathListener.Simple) () -> map.removeNpc(healer);
            healer.targetPlayer(player, false); //target but don't attack (needed so they don't check bounds when attacking!)
            healer.face(jad);
            healer.startEvent(e -> { //when attacked, this event will stop.
                int healTicks = 4;
                while(!jad.getCombat().isDead()) {
                    DumbRoute.step(healer, jad, 1);
                    if(++healTicks >= 4 && DumbRoute.withinDistance(healer, jad, 1)) {
                        healTicks = 0;
                        healer.animate(2639);
                        jad.graphics(444, 250, 0);
                        jad.incrementHp(Random.get(1, 10));
                        if(jad.getHp() == jad.getMaxHp()) {
                            int deadHealers = 4 - (map.getNpcs().size() - 1);
                            if(deadHealers > 0)
                                spawnHealers(jad, deadHealers);
                        }
                    }
                    e.delay(1);
                }
            });
            map.addNpc(healer);
        }
    }

    private Position nextSpawnPosition() {
        Position pos = ROTATIONS[spawnRotationOffset];
        if(++spawnRotationOffset >= ROTATIONS.length)
            spawnRotationOffset = 0;
        return pos;
    }

    /**
     * Death handlers
     */

    private void handleDeath() {
        killedJad = false; //sorry m8
        player.getMovement().teleport(EXIT);
        player.sendMessage("You have been defeated!");
    }

    private void handleDeath(NPC npc) {
        map.removeNpc(npc);
        if(player.getCombat().isDead()) {
            //nothing should happen cause you failed
            return;
        }
        if(npc.getId() == TZ_TOK_JAD) {
            if(!finishPractice()) {
                killedJad = true;
                player.getMovement().teleport(EXIT);
            }
            return;
        }
        if(npc.getId() == TZ_KEK) {
            int x = npc.getAbsX();
            int y = npc.getAbsY();
            spawn(TZ_KEK_MINION, Random.get(x, x + 1), Random.get(y, y + 1));
            spawn(TZ_KEK_MINION, Random.get(x, x + 1), Random.get(y, y + 1));
            return;
        }
        if(map.getNpcs().isEmpty()) {
            if(!finishPractice()) {
                wave++;
                if(++waveRotationOffset >= ROTATIONS.length)
                    waveRotationOffset = 0;
                spawnRotationOffset = waveRotationOffset;
                if(logoutRequest) {
                    player.sendMessage("<col=ef1020>The Fight Cave has been paused. You may now log out.");
                    return;
                }
                beginWave();
            }
        }
    }

    /**
     * Misc
     */

    private boolean finishPractice() {
        if(!practice)
            return false;
        player.getMovement().teleport(EXIT);
        timer.stop(player, -1);
        return true;
    }

    private boolean allowLogout() {
        if(practice)
            return true;
        if(!logoutRequest) {
            player.sendMessage("<col=ef1020>Your logout request has been received. The minigame will be paused at the end of this wave.<br><col=ef1020>If you try to log out before that, you will have to repeat this wave.");
            logoutRequest = true;
            return false;
        }
        return true;
    }

    /**
     * Actions
     */

    private static void join(Player player, int wave, boolean practice) {
        if(player.fightCaves == null) //probably will always be true, but just to be safe?
            new FightCaves(player, wave, practice).start(false);
    }

    private static void exit(Player player) {
        //map listener will stop your session the second you leave the map.
        player.getMovement().teleport(EXIT);
    }

    private static int getStartingWave(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return 63;
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return 60;
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            return 56;
        } else if (player.isGroup(PlayerGroup.DIAMOND)) {
            return 54;
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return 52;
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            return 50;
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            return 50;
        } else {
            return 50;
        }
    }

    static {
        /**
         * Join
         */
        ObjectAction.register(11833, actions -> {
            actions[1] = (player, obj) -> join(player, getStartingWave(player), false);
            actions[2] = (player, obj) -> player.integerInput("Enter the wave you'd like to practice: (1-63)", wave -> {
                if(wave < 0 || wave > 63) {
                    player.retryIntegerInput("Invalid wave, enter the wave you'd like to practice: (1-63)");
                    return;
                }
                join(player, wave, true);
            });
        });
        /**
         * Leave
         */
        ObjectAction.register(11834, "leave", (player, obj) -> player.dialogue(
                new OptionsDialogue("Really leave?",
                        new Option("Yes - really leave.", () -> exit(player)),
                        new Option("No, I'll stay.", player::closeDialogue)
                )
        ));
        /**
         * Escape
         */
        ObjectAction.register(11834, "escape", (player, obj) -> exit(player));
        /**
         * Login
         */
        LoginListener.register(p -> {
            if(p.fightCaves != null) {
                p.fightCaves.player = p;
                p.fightCaves.start(true);
            }
        });

    }

}