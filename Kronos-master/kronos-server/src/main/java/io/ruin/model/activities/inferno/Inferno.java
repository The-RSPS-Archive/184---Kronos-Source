package io.ruin.model.activities.inferno;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.World;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.activities.miscpvm.PassiveCombat;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;

import java.util.*;

public class Inferno {

    private static final int HOST = 7690,
            NIBBLER = 7691,
            BAT = 7692,
            BLOB = 7693,
            MELEE = 7697,
            RANGE = 7698,
            MAGE = 7699,
            JAD = 7700,
            HEALER = 7701;

    private static final Position EXIT = new Position(2495, 5112, 0);

    private static final Position[] SPAWN_POSITIONS;
    public static final int PILLAR_ALIVE = 7709;
    public static final int PILLAR_DEAD = 7710;
    public static final int SHIELD = 7707;

    static {
        List<Position> spawns = Arrays.asList(
                new Position(2259, 5353, 0),
                new Position(2279, 5352, 0),
                new Position(2260, 5346, 0),
                new Position(2279, 5346, 0),
                new Position(2272, 5340, 0),
                new Position(2261, 5339, 0),
                new Position(2260, 5332, 0),
                new Position(2271, 5331, 0),
                new Position(2279, 5332, 0)
        );
        Collections.shuffle(spawns); //let's not get predictable ;)
        SPAWN_POSITIONS = spawns.toArray(new Position[0]);
    }

    /**
     * Separator
     */

    private Player player;

    @Expose private int wave;

    @Expose private boolean practice;

    @Expose private boolean[] deadPillars = new boolean[3];

    private DynamicMap map;

    private List<Pillar> pillars;

    private ActivityTimer timer;

    private int spawnOffset;

    private boolean logoutRequest;

    private boolean killedZuk;

    private boolean preparingWave;

    public Inferno(Player player, int wave, boolean practice) {
        this.player = player;
        this.wave = wave;
        this.practice = practice;
    }

    public void start(boolean login) {
        if (deadPillars == null)
            deadPillars = new boolean[3];
        player.startEvent(e -> {
            player.lock();

            player.inferno = this;
            player.deathEndListener = (DeathListener.Simple) this::handleDeath;
            player.logoutListener = new LogoutListener().onAttempt(this::allowLogout);
            spawnOffset = Random.get(SPAWN_POSITIONS.length - 1);

            map = new DynamicMap().build(9043, 1);
            pillars = new ArrayList<>(3);
            if (wave < 67) {
                if (!deadPillars[0])
                    pillars.add(new Pillar(30353, map.convertX(2257), map.convertY(5349), Config.INFERNO_PILLAR_DAMAGE_WEST));
                if (!deadPillars[1])
                    pillars.add(new Pillar(30354, map.convertX(2267), map.convertY(5335), Config.INFERNO_PILLAR_DAMAGE_SOUTH));
                if (!deadPillars[2])
                    pillars.add(new Pillar(30355, map.convertX(2274), map.convertY(5351), Config.INFERNO_PILLAR_DAMAGE_EAST));
            }

            if(login) {
                player.getMovement().teleport(map.convertX(2270), map.convertY(5344), 0);
            } else {
                player.animate(6723);
                player.getMovement().force(0, 11, 0, 0, 15, 255, Direction.NORTH);
                e.delay(5);
                player.dialogue(new MessageDialogue("You jump into the fiery cauldron of The Inferno; your heart is pulsating.").lineHeight(24).hideContinue());
                e.delay(2);
                player.getPacketSender().fadeOut();
                e.delay(4);
                player.dialogue(new MessageDialogue("You fall and fall and feel the temperature rising.").hideContinue());
                e.delay(2);
                player.dialogue(new MessageDialogue("Your heart is in your throat...").hideContinue());
                e.delay(2);
                player.dialogue(new MessageDialogue("You hit the ground in the centre of The Inferno.").hideContinue());
                player.getPacketSender().fadeIn();
                player.getMovement().teleport(map.convertX(2270), map.convertY(5344), 0);
                player.animate(4367);
                e.delay(10);
                player.closeDialogue();
            }

            map.assignListener(player).onExit((p, logout) -> stop(logout));
            timer = new ActivityTimer();
            player.unlock();
            if (login) {
                player.sendMessage(Color.RED.wrap("The next wave will start in 10 seconds."));
                prepareWave(true);
            } else {
                prepareWave(false);
            }

        });
    }

    private void stop(boolean logout) {
        if(logout) {
            if(practice) {
                player.getMovement().teleport(EXIT);
                player.inferno = null;
            }
        } else {
            int lastWave = wave - 1;
            if(lastWave == 0) {
                player.dialogue(new NPCDialogue(HOST, "Well I suppose you tried... better luck next time.").animate(610));
            } else if(!practice) {
                int tokkul = 2 + (lastWave * (3 + lastWave));
                if(!killedZuk) {
                    if(!player.isSapphire()) {
                        player.dialogue(new NPCDialogue(HOST, "Well done in the cave, here take TokKul as reward.").animate(588));
                        player.getInventory().addOrDrop(6529, tokkul);
                    } else {
                        player.dialogue(new NPCDialogue(HOST, "Well I suppose you tried... better luck next time."));
                    }
                } else {
                    player.dialogue(new NPCDialogue(HOST, "You are very impressive for a JalYt. You managed to defeat TzKal-Zuk! Please accept this cape as a token of appreciation.").animate(615));
                    player.zukKills.increment(player);
                    player.infernoBestTime = timer.stop(player, player.infernoBestTime);
                    player.getInventory().addOrDrop(21295, 1);
                    player.getInventory().addOrDrop(6529, 16440);
                    Broadcast.GLOBAL.sendNews(player, player.getName() + " has defeated the Inferno.");
                }
            }
            player.inferno = null;
            player.deathEndListener = null;
            player.logoutListener = null;
            player.getNpcUpdater().setMaxDistance(14);
        }
        player = null;
        map.destroy();
        map = null;
    }

    /**
     * Spawning
     */

    private void prepareWave(boolean login) {
        if (preparingWave)
            return;
        preparingWave = true;
        if (!login)
            wave++;
        map.addEvent(event -> {
            if (!practice)
                player.sendMessage(Color.RED.wrap("Wave: " + wave));
            else
                player.sendMessage(Color.RED.wrap("Wave: " + wave + " (Practice mode)"));
            event.delay(login ? 16 : 10);
            beginWave();
            preparingWave = false;
        });
    }

    private void beginWave() {
        if (wave > 69)
            return;
        if (map.getNpcs() != null)
            map.getNpcs().removeIf(n -> n.getCombat().isDead()); // clear dead npcs

        /**
         * Boss waves
         */
        if(wave == 67) { // Single jad
            //Collapse pillars
            pillars.forEach(Pillar::collapse);
            pillars.clear();
            spawn(JAD);
            return;
        }
        if(wave == 68) { // Triple jad wave
            spawn(JAD, new Position(2265, 5347, 0));
            NPC jad2 = spawn(JAD, new Position(2267, 5336, 0));
            jad2.getCombat().updateLastAttack(4);
            NPC jad3 = spawn(JAD, new Position(2274, 5346, 0));
            jad3.getCombat().updateLastAttack(7);
            return;
        }
        if(wave == 69) { // Zuk
            player.getNpcUpdater().setMaxDistance(32);
            spawnZuk();
            return;
        }
        /**
         * Nibbler only waves
         */
        if(wave == 3) {
            spawnNibblers(6);
            return;
        }
        if(wave == 8 || wave == 17 || wave == 34) {
            spawnNibblers(5);
            return;
        }
        /**
         * Regular waves
         */
        int budget = this.wave;
        if(wave > 3)
            budget--;
        if(wave > 8)
            budget--;
        if(wave > 17)
            budget--;
        if(wave > 34)
            budget--;
        while(budget >= 31) {
            budget -= 31;
            spawn(MAGE);
        }
        while(budget >= 15) {
            budget -= 15;
            spawn(RANGE);
        }
        while(budget >= 7) {
            budget -= 7;
            spawn(MELEE);
        }
        while(budget >= 3) {
            budget -= 3;
            spawn(BLOB);
        }
        while(budget > 0) {
            budget--;
            spawn(BAT);
        }
        spawnNibblers(3);
    }

    private void spawnZuk() {
        player.addEvent(event -> {
            event.delay(6);
            player.getPacketSender().fadeOut();
            player.getPacketSender().sendMapState(2);
            player.dialogue(new MessageDialogue("A great power is starting to shake the cavern...").hideContinue());
            event.delay(2);
            player.lock();
            //Need to teleport the player to z 1 because we have to change a few objects there
            player.getMovement().teleport(map.convertX(2271), map.convertY(5356), 1);
            GameObject.forObj(30356, map.convertX(2267), map.convertY(5368), 1, GameObject::remove);
            GameObject.spawn(30346, map.convertX(2268), map.convertY(5364), 1, 10, 3);
            GameObject.spawn(30345, map.convertX(2273), map.convertY(5364), 1, 10, 3);
            event.delay(1);
            //Back to z 0
            player.getMovement().teleport(player.getPosition().copy().translate(0,0,-1));
            event.delay(2);
            int wallX = map.convertX(2270);
            int wallY = map.convertY(5363);
            GameObject.forObj(30338, wallX, wallY, 0, GameObject::remove);
            NPC shield = spawn(SHIELD, new Position(2270, 5363, 0));
            GameObject.forObj(30337, map.convertX(2268), map.convertY(5364), 0, obj -> {
                obj.setId(30344);
                World.startEvent(e -> {
                    e.delay(2);
                    player.getPacketSender().sendObjectAnimation(map.convertX(2268), map.convertY(5364), 0, 10, 3, 7561);
                    e.delay(2);
                    obj.remove();
                });
            });
            GameObject.forObj(30336, map.convertX(2273), map.convertY(5364), 0, obj -> {
                obj.setId(30343);
                World.startEvent(e -> {
                    e.delay(2);
                    player.getPacketSender().sendObjectAnimation(map.convertX(2273), map.convertY(5364), 0, 10, 3, 7561);
                    e.delay(2);
                    obj.remove();
                });
            });
            GameObject.forObj(30332, map.convertX(2275), map.convertY(5364), 0, obj -> obj.setId(30339));
            GameObject.spawn(30340, map.convertX(2267), map.convertY(5364), 0, 10, 1);
            GameObject.spawn(30342, map.convertX(2267), map.convertY(5366), 0, 10, 1);
            GameObject.spawn(30341, map.convertX(2275), map.convertY(5366), 0, 10, 3);
            event.delay(1);
            player.getPacketSender().fadeIn();
            player.getPacketSender().sendMapState(0);
            player.getPacketSender().moveCameraToLocation(map.convertX(2276), map.convertY(5349), 1000, 10, 100);
            player.getPacketSender().turnCameraToLocation(map.convertX(2271), map.convertY(5365), 1000, 10, 100);
            player.getPacketSender().shakeCamera(0, 10);
            player.getPacketSender().shakeCamera(1, 10);
            player.getPacketSender().shakeCamera(2, 10);
            player.addEvent(camEvent -> {
                int tickToDelay = 14;
                camEvent.delay(tickToDelay);
                player.getPacketSender().resetCamera();
            });
            NPC zuk = spawn(7706, new Position(2268, 5364, 0));
            zuk.lock();
            shield.addEvent(e -> {
                e.delay(3);
                shield.step(0, -1, StepType.FORCE_WALK);
                e.delay(1);
                shield.step(0, -1, StepType.FORCE_WALK);
            });
            player.dialogue(new NPCDialogue(7690, "Oh no! TzKal-Zuk's prison is breaking down. This not meant to have happened. There's nothing I can do for you now JalYt!").animate(615).hideContinue());
            event.delay(9);
            player.closeDialogue();
            player.getPacketSender().resetCamera();
            player.unlock();
            zuk.unlock();
        });
    }


    private void spawn(int id) {
        Position pos = SPAWN_POSITIONS[spawnOffset];
        if(++spawnOffset >= SPAWN_POSITIONS.length)
            spawnOffset = 0;
        NPC npc = new NPC(id).spawn(map.convertX(pos.getX()), map.convertY(pos.getY()), 0);
        registerNPC(npc);
    }

    public NPC spawn(int id, Position pos) {
        return spawn(id, pos, true);
    }

    public NPC spawn(int id, Position pos, boolean attackTarget) {
        NPC npc = new NPC(id).spawn(map.convertX(pos.getX()), map.convertY(pos.getY()), 0);
        registerNPC(npc, attackTarget);
        return npc;
    }

    private void registerNPC(NPC npc) {
        registerNPC(npc, true);
    }

    private void registerNPC(NPC npc, boolean attackTarget) {
        npc.set("INFERNO", this);
        npc.getCombat().setAllowRespawn(false);
        npc.deathEndListener = (DeathListener.Simple) () -> handleDeath(npc);
        npc.targetPlayer(player, false);
        if (attackTarget)
            npc.attackTargetPlayer();
        map.addNpc(npc);
    }

    private void spawnNibblers(int amount) {
        int skips = 9 - amount;
        int point = 0;
        int baseX = map.convertX(2265);
        int baseY = map.convertY(5345);
        Entity target = pillars.isEmpty() ? player : Random.get(pillars).npc;
        for(int i = 0; i < amount; i++) {
            /**
             * Calculate point inside of 3x3 square to spawn.
             */
            if(skips > 0 && Random.rollDie(2, 1)) {
                skips--;
                point++;
            }
            int pointX = point / 3;
            int pointY = point;
            if(point >= 6)
                pointY -= 6;
            else if(point >= 3)
                pointY -= 3;
            point++;
            /**
             * Spawn the nibblers!
             */
            NPC npc = new NPC(NIBBLER).spawn(baseX + pointX, baseY + pointY, 0);
            npc.face(target);
            npc.getCombat().setTarget(target);
            npc.targetPlayer(player, false);
            npc.deathEndListener = (DeathListener.Simple) () -> handleDeath(npc);
            npc.getCombat().setAllowRespawn(false);
            map.addNpc(npc);
        }
    }

    public static Inferno getInstance(NPC npc) {
        return npc.get("INFERNO");
    }

    /**
     * Death handlers
     */

    private void handleDeath() {
        player.getMovement().teleport(EXIT);
        player.sendMessage("You have been defeated!");
    }

    private void handleDeath(NPC npc) {
        npc.setHidden(true); // we don't remove the npc here so that the magers can find dead ones and revive them
        if(player.getCombat().isDead()) {
            //nothing should happen cause you failed
            return;
        }
        if (npc.getId() == 7706) {
            killedZuk = true;
            player.getMovement().teleport(EXIT);
            return;
        }
        if (npc.getId() == BLOB) {
            spawnBloblets(npc);
            return;
        }
        if (map.getNpcs().stream().allMatch(n -> n.getId() == PILLAR_ALIVE || n.getId() == PILLAR_DEAD || n.getCombat().isDead())) {
            if (!finishPractice()) {
                if(logoutRequest) {
                    player.sendMessage("<col=ef1020>The Inferno has been paused. You may now log out.");
                    return;
                }
                prepareWave(false);
            }
        }
    }

    public DynamicMap getMap() {
        return map;
    }

    private void spawnBloblets(NPC npc) {
        LinkedList<Position> spawnPoints = new LinkedList<>();
        Bounds b = new Bounds(npc.getPosition(), npc.getPosition().relative(npc.getSize(), npc.getSize()), npc.getPosition().getZ());
        b.forEachPos(pos -> {
            if (Tile.get(pos).clipping == 0)
                spawnPoints.add(pos);
        });
        Collections.shuffle(spawnPoints);
        for (int bloblet = 7694; bloblet <= 7696; bloblet++) {
            Position pos = spawnPoints.pop();
            NPC n = new NPC(bloblet).spawn(pos);
            registerNPC(n);
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

    public int getWave() {
        return wave;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Pillar
     */
    private final class Pillar {

        public final GameObject obj;

        public final NPC npc;

        public final int pillarIndex;

        public Pillar(int objId, int x, int y, Config config) {
            pillarIndex = objId - 30353;
            obj = GameObject.spawn(objId, x, y, 0, 10, 0);
            map.addNpc(npc = new NPC(PILLAR_ALIVE).spawn(x, y, 0));
            npc.hitListener = new HitListener().postDamage(hit -> {
                int damageDealt = npc.getMaxHp() - npc.getHp();
                config.set(player, damageDealt);
            });
            npc.deathStartListener = (DeathListener.Simple) () -> {
                deadPillars[pillarIndex] = true;
                obj.remove();
                npc.transform(PILLAR_DEAD);
                npc.animate(7561);
                if(withinDistance(player))
                    player.hit(wave < 67 ? new Hit().randDamage(10, 25) : new Hit().fixedDamage(49));
                for(NPC n : map.getNpcs()) {
                    if(n.getCombat() != null && !n.getCombat().isDead() && withinDistance(n))
                        n.hit(new Hit().randDamage(10, 25));
                }
                pillars.remove(this);
            };
            npc.deathEndListener = (DeathListener.Simple) () -> map.removeNpc(npc);
            NPCDef.get(PILLAR_ALIVE).ignoreMultiCheck = true;
            NPCDef.get(PILLAR_DEAD).ignoreMultiCheck = true;
        }

        private boolean withinDistance(Entity entity) {
            int centerX = npc.getAbsX() + 1;
            int centerY = npc.getAbsY() + 1;
            return Misc.getDistance(entity.getPosition(), centerX, centerY) <= 2;
        }

        private void collapse() { // forced collapse, wave 67
            npc.addEvent(event -> {
                obj.remove();
                npc.transform(PILLAR_DEAD);
                npc.animate(7561);
                if(withinDistance(player))
                    player.hit(new Hit().fixedDamage(49));
                for(NPC n : map.getNpcs()) {
                    if(n.getCombat() != null && !n.getCombat().isDead() && withinDistance(n))
                        n.hit(new Hit().randDamage(10, 25));
                }
                event.delay(npc.getCombat().getInfo().death_ticks);
                npc.remove();
            });
        }

    }

    static {
        NPCDef def = NPCDef.get(PILLAR_ALIVE);
        def.combatHandlerClass = PassiveCombat.class;
        def.combatInfo = new npc_combat.Info();
        def.combatInfo.hitpoints = 254;
        def.combatInfo.defend_animation = -1;
        def.combatInfo.death_animation = -1;

        NPCDef.get(NIBBLER).ignoreOccupiedTiles = true;

        def = NPCDef.get(SHIELD);
        def.combatHandlerClass = PassiveCombat.class;
        def.combatInfo = new npc_combat.Info();
        def.combatInfo.hitpoints = 600;
        def.combatInfo.defend_animation = 7568;
        def.combatInfo.death_animation = 7569;
        def.combatInfo.spawn_animation = -1;
    }

    /**
     * Actions
     */

    public static void join(Player player, int wave, boolean practice) {
        if(player.inferno == null) //probably will always be true, but just to be safe?
            new Inferno(player, wave - 1, practice).start(false);
    }

    private static void exit(Player player) {
        //map listener will stop your session the second you leave the map.
        player.getMovement().teleport(EXIT);
    }

    private static int getStartingWave(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return 60;
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return 55;
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            return 50;
        } else if (player.isGroup(PlayerGroup.DIAMOND)) {
            return 45;
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return 40;
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            return 35;
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            return 30;
        } else {
            return 25;
        }
    }

    static {
        GameObject.forObj(30352, 2493, 5124, 0, obj -> obj.walkTo = new Position(2496, 5115, 0));
        ObjectAction.register(30352, actions -> {
            actions[1] = (player, obj) -> {
                player.dialogue(new OptionsDialogue(
                        new Option("Enter the Inferno", () -> join(player, getStartingWave(player), false)),
                        new Option("Practice a specific wave", () -> player.integerInput("Enter the wave you'd like to practice: (1-69)", wave -> {
                            if(wave < 0 || wave > 69) {
                                player.retryIntegerInput("Invalid wave, enter the wave you'd like to practice: (1-69)");
                                return;
                            }
                            join(player, wave, true);
                        })),
                        new Option("Cancel")));
            };
        });

        LoginListener.register(p -> {
            if(p.inferno != null) {
                p.inferno.player = p;
                p.inferno.start(true);
            }
        });

        ObjectAction.register(30283, "exit", (player, obj) -> player.dialogue(
                new OptionsDialogue("Really leave?",
                        new Option("Yes - really leave.", () -> exit(player)),
                        new Option("No, I'll stay.", player::closeDialogue)
                )
        ));

        ObjectAction.register(30283, "quick-exit", (player, obj) -> exit(player));

    }

}
