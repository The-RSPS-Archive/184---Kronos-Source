package io.ruin.model.activities.pvminstances;

import io.ruin.Server;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;

import java.util.LinkedList;
import java.util.List;


public enum InstanceType {
    GIANT_MOLE("Giant Mole", new Bounds(1728, 5120, 1791, 5247, 1), 300_000, 2000, 60, new Position(1752, 5236, 0), new Position(2985, 3316, 0)),
    DAGANNOTH_KINGS("Dagannoth Kings", 11589, 400_000, 2_000, 60, new Position(2899, 4449, 0), new Position(1912, 4367, 0)),
    KRAKEN("Kraken", 9116, 400_000, 1_500, 60, new Position(2280, 10022, 0), new Position(2280, 10016, 0)),
    KBD("King Black Dragon", 9033, 500_000, 2_500, 60, new Position(2271, 4680, 0),  new Position(3067, 10253, 0)),
    THERMONUCLEAR_SMOKE_DEVIL("Thermonuclear Smoke Devil", new Bounds(2344, 9432, 2376, 9456, 0), 400_000, 1_500, 60, new Position(2376, 9452, 0), new Position(2379, 9452, 0)),
    CERBERUS("Cerberus", 5140, 600_000, 3000, 60, new Position(1304, 1290, 0), new Position(1310, 1274, 0)),
    CORP("Corporeal Beast", 11844, 1_000_000, 5_000, 60, new Position(2974, 4384, 2), new Position(2970, 4384, 2)),
    KALPHITE_QUEEN("Kalphite Queen", 13972, 500_000, 2_500, 60, new Position(3507, 9494, 0), new Position(3509, 9496, 2)),

    BANDOS_GWD("General Graardor", new Bounds(2856, 5344, 2877, 5374, 2), 500_000, 3_000, 60, new Position(2864, 5354, 2), new Position(2862, 5354, 2)),
    ZAMORAK_GWD("K'ril Tsutsaroth", new Bounds(2912, 5312, 2944, 5336, 2), 500_000, 3_000, 60, new Position(2925, 5331, 2), new Position(2925, 5333, 2)),
    ARMADYL_GWD("Kree'arra", new Bounds(2816, 5288, 2840, 5304, 2), 500_000, 3_000, 60, new Position(2839, 5296, 2), new Position(2839, 5294, 2)),
    SARADOMIN_GWD("Commander Zilyana", new Bounds(2880, 5248, 2911, 5278, -1), 500_000, 3_000, 60, new Position(2907, 5265, 0), new Position(2909, 5265, 0))
    //todo - add support for gwd kc requirements (for eco worlds, not used in pvp world)

    ;

    InstanceType(String name, Bounds bounds, int coinCost, int bmCost, int duration, Position entryPosition, Position exitPosition) {
        this.name = name;
        this.bounds = bounds;
        this.coinCost = coinCost;
        this.bmCost = bmCost;
        this.duration = duration * 100;
        this.entryPosition = entryPosition;
        this.exitPosition = exitPosition;
        loadSpawns();
    }

    private void loadSpawns() {
        int baseX = bounds.swX;
        int baseY = bounds.swY;
        if (baseX % 8 != 0 || baseY % 8 != 0) {
            Server.logWarning("InstanceType " + name + " has imprecise coordinates!");
        }
        spawns = new LinkedList<>();
        SpawnListener.forEach(npc -> {
            if (npc == null) return;
            if (!npc.defaultSpawn)
                return;
            if (npc.spawnPosition.inBounds(bounds)) {
                spawns.add(new Spawn(npc.getId(), npc.spawnPosition.getX() - baseX, npc.spawnPosition.getY() -  baseY, npc.getSpawnPosition().getZ(), npc.spawnDirection, npc.walkRange));
            }
        });
    }

    InstanceType(String name, int regionId, int coinCost, int bmCost, int duration, Position entryPosition, Position exitPosition) {
        this(name, Bounds.fromRegion(regionId), coinCost, bmCost, duration, entryPosition, exitPosition);
    }

    /**
     * Name of type
     */
    private String name;

    /**
     * The position the player will be teleported to upon leaving
     */
    private Position exitPosition;

    /**
     * Entry position
     */
    private Position entryPosition;

    /**
     * How much blood money it costs to create this instance (only used if world is set to PvP)
     */
    private int bmCost;

    /**
     * How many coins it costs to create this instance (only used if world is set to Eco)
     */
    private int coinCost;


    /**
     * Instance duration (in ticks)
     */
    private int duration;

    /**
     * Bounds. Always use whole chunks to make sure coordinate conversion works properly
     */
    private Bounds bounds;

    /**
   ,  * Spawns (In Local coords)
     */
    private List<Spawn> spawns;

    public Position getExitPosition() {
        return exitPosition;
    }

    public Position getEntryPosition() {
        return entryPosition;
    }

    public int getCost() {
        return coinCost;
    }

    public int getDuration() {
        return duration;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public String getName() {
        return name;
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }


    static class Spawn {
        int id;
        int x;
        int y;
        int z;
        Direction direction;
        int walkRange;


        Spawn(int id, int x, int y, int z, Direction direction, int walkRange) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
            this.direction = direction;
            this.walkRange = walkRange;
        }
    }
}

