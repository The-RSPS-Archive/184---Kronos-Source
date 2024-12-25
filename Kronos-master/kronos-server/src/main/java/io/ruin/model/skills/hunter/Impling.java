package io.ruin.model.skills.hunter;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.ButterflyNet;
import io.ruin.model.item.actions.impl.ImplingJar;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.Arrays;

public enum Impling {

    BABY(1635, 17, 27, 18, 20, 11238, 0, 10),
    YOUNG(1636, 22, 32, 20, 22, 11240, 0, 10),
    GOURMET(1637, 28, 38, 22, 24, 11242, 0, 10),
    EARTH(1638, 36, 46, 25, 27, 11244, 0, 10),
    ESSENCE(1639, 42, 52, 27, 29, 11246, 100, 10),
    ECLECTIC(1640, 50, 60, 30, 32, 11248, 0, 10),
    NATURE(1641, 58, 68, 34, 36, 11250, 100, 10),
    MAGPIE(1642, 65, 75, 44, 54, 11252, 50, 10),
    NINJA(1643, 74, 85, 50, 60, 11254, 40, 10),
    DRAGON(1644, 83, 93, 65, 75, 11256, 10, 10),
    LUCKY(7233, 89, 99, 0, 0, 19732, 1, 1);

    public final int npcId, levelReq, bareHandLevelReq, jarId, puroPuroSpawnWeight, overworldSpawnWeight;
    public final double puroExp, worldExp;

    Impling(int npcId, int levelReq, int bareHandLevelReq, double puroExp, double worldExp, int jarId, int puroPuroSpawnWeight, int overworldSpawnWeight) {
        this.npcId = npcId;
        this.levelReq = levelReq;
        this.bareHandLevelReq = bareHandLevelReq;
        this.puroExp = puroExp;
        this.worldExp = worldExp;
        this.jarId = jarId;
        this.puroPuroSpawnWeight = puroPuroSpawnWeight;
        this.overworldSpawnWeight = overworldSpawnWeight;
    }

    private static Bounds PURO_PURO = new Bounds(2562, 4290, 2621, 4349, 0);

    private static void attemptCatch(Player player, NPC npc, Impling impling) {
        boolean barehands = !hasButterflyNet(player) && !hasMagicButterflyNet(player);

        /* check for impling jar */
        Item impJar = player.getInventory().findItem(ImplingJar.IMPLING_JAR);
        if(!barehands && impJar == null) {
            player.sendFilteredMessage("You don't have an empty impling jar in which to keep an impling.");
            return;
        }

        /* check for level req */
        int hunterLevel = player.getStats().get(StatType.Hunter).currentLevel;
        int levelReq = barehands ? impling.bareHandLevelReq : impling.levelReq;
        if(hunterLevel < levelReq) {
            player.sendFilteredMessage("You need a Hunter level of at least " + levelReq + " to catch this impling" + (barehands ? " barehanded." : "."));
            return;
        }

        if(!player.getPosition().isWithinDistance(npc.getPosition(), 1))
            return;

        player.startEvent(event -> {
            player.lock();
            if(barehands) {
                player.animate(7171);
            } else {
                player.animate(hasMagicButterflyNet(player) ? 6605 : 6606);
            }
            event.delay(2);
            if(Random.rollDie(2, 1)) { //TODO: proper chance

                if (barehands) {
                    ImplingJar jar = ImplingJar.forJarId(impling.jarId);
                    Item loot;
                    if (jar != null) {
                        if (jar.equals(ImplingJar.LUCKY_IMPLING)) {
                            ClueType randomClue = Random.get(ClueType.values());
                            loot = new LootTable().addTable(1, randomClue.loots).rollItem();
                        } else {
                            loot = jar.getLootTable().rollItem();
                        }
                        player.getInventory().add(loot);
                    } else {
                        player.getInventory().add(impling.jarId);
                    }
                } else {
                    impJar.setId(impling.jarId);
                }

                despawnImpling(npc);
                player.getStats().addXp(StatType.Hunter, player.getPosition().inBounds(PURO_PURO) ? impling.puroExp : impling.worldExp, true);
                PlayerCounter.IMPLINGS_CAUGHT.increment(player, 1);
            }
            player.unlock();
        });
    }

    private static boolean hasButterflyNet(Player player) {
        return player.getEquipment().hasId(ButterflyNet.BUTTERFLY_NET);
    }

    private static boolean hasMagicButterflyNet(Player player) {
        return player.getEquipment().hasId(ButterflyNet.MAGIC_BUTTERFLY_NET);
    }

    /**
     * Impling spawning
     */

    private static final int PURO_PURO_STATIC_RESPAWN_DELAY = 50; // respawn time for static spawns in puro puro (baby, young gourmet, earth, eclectic)
    private static final int PURO_PURO_RANDOM_SPAWN_DELAY = 20; // respawn time for random spawns in puro puro (other implings)
    private static final int PURO_PURO_MAX_RANDOM_IMPLINGS = 6; // maximum number of random spawns that can be active at a time
    private static final int PURO_PURO_TOTAL_SPAWN_WEIGHT = Arrays.stream(values()).mapToInt(imp -> imp.puroPuroSpawnWeight).sum();

    private static final int OVERWORLD_SPAWN_DELAY = 200; // spawn time for random spawns in the overworld (implings will attempt to spawn at this interval, if the active number is below maximum)
    private static final int OVERWORLD_MAX_IMPLINGS = 30; // maximum number of overworld spawns that can be active at one time
    private static final int OVERWORLD_TOTAL_SPAWN_WEIGHT = Arrays.stream(values()).mapToInt(imp -> imp.overworldSpawnWeight).sum();
    @Getter
    private static int ACTIVE_PURO_PURO_IMPLINGS = 0;
    @Getter
    private static int ACTIVE_OVERWORLD_IMPLINGS = 0;

    private static void despawnImpling(NPC npc) {
        Impling type = get(npc.getId());
        if (type == null)
            return;
        if (isInPuroPuro(npc)) {
            if (type == BABY || type == YOUNG || type == GOURMET || type == EARTH || type == ECLECTIC) { // these have static spawns
                npc.addEvent(event -> {
                    npc.setHidden(true);
                    event.delay(PURO_PURO_STATIC_RESPAWN_DELAY);
                    npc.getMovement().teleport(npc.getSpawnPosition());
                    npc.setHidden(false);
                });
            } else {
                ACTIVE_PURO_PURO_IMPLINGS--;
                npc.remove();
            }
        } else {
            ACTIVE_OVERWORLD_IMPLINGS--;
            npc.remove();
        }
    }

    public static Impling get(int id) {
        for (Impling value : values()) {
            if (value.npcId == id)
                return value;
        }
        return null;
    }

    private static boolean isInPuroPuro(Entity entity) {
        return entity.getPosition().getRegion().id == 10307; // puro puro region
    }

    private static Impling getRandomPuroPuroSpawn() {
        int roll = Random.get(PURO_PURO_TOTAL_SPAWN_WEIGHT);
        for (Impling impling : values()) {
            if (impling.puroPuroSpawnWeight == 0)
                continue;
            roll -= impling.puroPuroSpawnWeight;
            if (roll <= 0) {
                return impling;
            }
        }
        return Random.get(values()); // should be unreachable
    }

    private static void spawnRandomImplingPuroPuro() {
        Impling type = getRandomPuroPuroSpawn();
        Position spawnPosition = Random.get(PURO_PURO_RANDOM_SPAWN_POSITIONS);
        NPC impling = new NPC(type.npcId).spawn(spawnPosition.getX(), spawnPosition.getY(), spawnPosition.getZ(), 12);
        impling.getRouteFinder().routeAbsolute(impling.walkBounds.randomX(), impling.walkBounds.randomY());
        ACTIVE_PURO_PURO_IMPLINGS++;
    }

    private static Impling getRandomOverworldSpawn() {
        int roll = Random.get(OVERWORLD_TOTAL_SPAWN_WEIGHT);
        for (Impling impling : values()) {
            if (impling.overworldSpawnWeight == 0)
                continue;
            roll -= impling.overworldSpawnWeight;
            if (roll <= 0) {
                return impling;
            }
        }
        return Random.get(values()); // should be unreachable
    }

    private static void spawnRandomImplingOverworld() {
        Impling type = getRandomOverworldSpawn();
        Position spawnPosition = Random.get(OVERWORLD_RANDOM_SPAWN_POSITIONS);
        NPC impling = new NPC(type.npcId).spawn(spawnPosition.getX(), spawnPosition.getY(), spawnPosition.getZ(), 16);
        impling.getRouteFinder().routeAbsolute(impling.walkBounds.randomX(), impling.walkBounds.randomY());
        ACTIVE_OVERWORLD_IMPLINGS++;
    }

    /**
     * Spawn locations
     */

    private static final Position[] PURO_PURO_RANDOM_SPAWN_POSITIONS = {
            new Position(2569, 4342, 0), //nw
            new Position(2614, 4297, 0), //se
    };

    private static final Position[] OVERWORLD_RANDOM_SPAWN_POSITIONS = {

            new Position(3079, 3503, 0), //edgeville spawns
            new Position(3104, 3506, 0),
            new Position(3087, 3485, 0),
            new Position(3126, 3498, 0),

            new Position(2032, 3591, 0), //home spawns
            new Position(2030, 3551, 0),
            new Position(2001, 3571, 0),
            new Position(2064, 3586, 0),
            new Position(2076, 3569, 0),
            new Position(2068, 3621, 0),



            new Position(3170, 3454, 0), // varrock spawns
            new Position(3208, 3434, 0),
            new Position(3283, 3453, 0),

            new Position(3086, 3235, 0), // draynor village spawn

            new Position(3223, 3223, 0), // lumbridge spawns
            new Position(3197, 3232, 0),
            new Position(3234, 3226, 0),

            new Position(3276, 3166, 0), // al kharid spawn
            new Position(3367, 3268, 0), // duel arena

            new Position(3035, 3256, 0), // port sarim spawn

            new Position(2957, 3224, 0), // rimmington spawn

            new Position(2965, 3401, 0), // falador spawns
            new Position(3010, 3381, 0),
            new Position(3005, 3327, 0),
            new Position(3050, 3317, 0),

            new Position(2930, 3449, 0), // taverley spawn

            new Position(2720, 3484, 0), // seers village spawns
            new Position(2719, 3461, 0),
            new Position(2722, 3504, 0),

            new Position(2851, 3432, 0), // catherby spawns
            new Position(2815, 3466, 0),

            new Position(2596, 3411, 0), // fishing guild spawn

            new Position(2508, 3524, 0), // barbarian outpost spawns
            new Position(2497, 3506, 0),

            new Position(2463, 3481, 0), // gnome stronghold spawns
            new Position(2479, 3448, 0),
            new Position(2475, 3427, 0),
            new Position(2437, 3427, 0),

            new Position(2658, 3311, 0), // ardougne spawns
            new Position(2664, 3301, 0),
            new Position(2668, 3376, 0),

            new Position(2542, 3090, 0), // yanille spawn

            new Position(2454, 3087, 0), // castle wars spawns
            new Position(2455, 3096, 0),

            new Position(2344, 3167, 0), // lletya spawn

            new Position(2340, 3698, 0), // piscatoris colony spawn
            new Position(2348, 3586, 0), // piscators hunter area spawn

            new Position(2765, 3207, 0), // brimhaven/karamja spawns
            new Position(2760, 3171, 0),
            new Position(2797, 3097, 0),
            new Position(2852, 2960, 0),

            new Position(2611, 2905, 0), // feldip hills hunter area spawns
            new Position(2558, 2894, 0),
            new Position(2545, 2914, 0),

            new Position(3495, 3493, 0), // canifis spawn
            new Position(3604, 3533, 0), // morytania farming patch spawn

            new Position(3111, 3542, 0), // wilderness spawns
            new Position(3237, 3622, 0),
            new Position(3029, 3724, 0),
            new Position(3203, 3868, 0),
            new Position(3006, 3865, 0),
            new Position(2998, 3932, 0),
            new Position(3187, 3936, 0),
            new Position(3180, 3934, 0),

            new Position(1821, 3774, 0), // Zeah spawns
            new Position(1712, 3880, 0),
            new Position(1249, 3731, 0),
            new Position(1553, 3445, 0),
            new Position(1646, 3503, 0),
            new Position(1586, 3487, 0),
            new Position(1788, 3473, 0),
            new Position(1825, 3487, 0),
            new Position(1632, 3666, 0),

    };

    static {
        for (Impling impling : values()) {
            NPCAction.register(impling.npcId, "catch", (player, npc) -> attemptCatch(player, npc, impling));
            NPCDef.get(impling.npcId).flightClipping = true;
        }

        //puro-puro random spawns
        World.startEvent(event -> {
            //spawn a few on startup
            for (int i = 0; i < 4; i++)
                spawnRandomImplingPuroPuro();
            while (true) {
                if (ACTIVE_PURO_PURO_IMPLINGS < PURO_PURO_MAX_RANDOM_IMPLINGS)
                    spawnRandomImplingPuroPuro();
                event.delay(PURO_PURO_RANDOM_SPAWN_DELAY);
            }
        });
        //overworld spawns
        World.startEvent(event -> {
            //spawn a few on startup
            for (int i = 0; i < 8; i++)
                spawnRandomImplingOverworld();
            while (true) {
                if (ACTIVE_OVERWORLD_IMPLINGS < OVERWORLD_MAX_IMPLINGS)
                    spawnRandomImplingOverworld();
                event.delay(OVERWORLD_SPAWN_DELAY);
            }
        });
    }



}
