package io.ruin.model.map.dynamic;

import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.activities.pvp.PVPInstance;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.*;
import io.ruin.model.map.object.GameObject;
import io.ruin.process.event.Event;
import io.ruin.process.event.EventConsumer;
import io.ruin.process.event.EventWorker;
import io.ruin.util.CheckedConcurrentLinkedDeque;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DynamicMap {

    private static final CheckedConcurrentLinkedDeque<Region> FREE_REGIONS = new CheckedConcurrentLinkedDeque<>(region -> region.baseX >= 6400);

    public Region swRegion, nwRegion, seRegion, neRegion;

    public DynamicMap() {
        swRegion = FREE_REGIONS.poll();
    }

    public DynamicMap build(List<DynamicChunk> chunks) {
        List<DynamicChunk> swChunks = chunks.stream().filter(c -> c.pointX <= 7 && c.pointY <= 7).collect(Collectors.toList());
        if (!swChunks.isEmpty())
            build(swRegion, swChunks);

        List<DynamicChunk> seChunks = chunks.stream().filter(c -> c.pointX > 7 && c.pointY <= 7).collect(Collectors.toList());
        seChunks.forEach(c -> c.pointX -= 8);
        if (!seChunks.isEmpty())
            buildSe(seChunks);

        List<DynamicChunk> nwChunks = chunks.stream().filter(c -> c.pointX <= 7 && c.pointY > 7).collect(Collectors.toList());
        nwChunks.forEach(c -> c.pointY -= 8);
        if (!nwChunks.isEmpty())
            buildNw(nwChunks);

        List<DynamicChunk> neChunks = chunks.stream().filter(c -> c.pointX > 7 && c.pointY > 7).collect(Collectors.toList());
        neChunks.forEach(c -> {
            c.pointX -= 8;
            c.pointY -= 8;
        });
        if (!neChunks.isEmpty())
            buildNe(chunks);
        return this;
    }

    public DynamicMap buildSe(List<DynamicChunk> chunks) {
        build(seRegion = Region.LOADED[swRegion.id + 256], chunks);
        return this;
    }

    public DynamicMap buildNw(List<DynamicChunk> chunks) {
        build(seRegion = Region.LOADED[swRegion.id + 1], chunks);
        return this;
    }

    public DynamicMap buildNe(List<DynamicChunk> chunks) {
        build(seRegion = Region.LOADED[swRegion.id + 257], chunks);
        return this;
    }

    /**
     * Build by region
     */

    public DynamicMap build(int regionId, int maxHeight) {
        return buildSw(regionId, maxHeight);
    }

    public DynamicMap buildSw(int regionId, int maxHeight) {
        build(swRegion, regionId, maxHeight);
        return this;
    }

    public DynamicMap buildNw(int regionId, int maxHeight) {
        build(nwRegion = Region.LOADED[swRegion.id + 1], regionId, maxHeight);
        return this;
    }

    public DynamicMap buildSe(int regionId, int maxHeight) {
        build(seRegion = Region.LOADED[swRegion.id + 256], regionId, maxHeight);
        return this;
    }

    public DynamicMap buildNe(int regionId, int maxHeight) {
        build(neRegion = Region.LOADED[swRegion.id + 257], regionId, maxHeight);
        return this;
    }

    private void build(Region targetRegion, int regionId, int maxHeight) {
        Region sourceRegion = Region.LOADED[regionId];
        List<DynamicChunk> chunks = new ArrayList<>(64 * (maxHeight + 1));
        for(int pointZ = 0; pointZ <= maxHeight; pointZ++) {
            for(int pointX = 0; pointX < 8; pointX++) {
                int chunkX = (sourceRegion.baseX + (pointX * 8)) >> 3;
                for(int pointY = 0; pointY < 8; pointY++) {
                    int chunkY = (sourceRegion.baseY + (pointY * 8)) >> 3;
                    chunks.add(new DynamicChunk(chunkX, chunkY, pointZ).pos(pointX, pointY, pointZ));
                }
            }
        }
        targetRegion.dynamicRegionBaseX = sourceRegion.baseX;
        targetRegion.dynamicRegionBaseY = sourceRegion.baseY;
        build(targetRegion, chunks);
    }

    private void build(Region targetRegion, List<DynamicChunk> chunks) {
        targetRegion.dynamicData = new int[4][8][8][2]; // TODO look into this! Causing nullpointers
        List<GameObject> objects = new ArrayList<>();
        for(DynamicChunk chunk : chunks) {
            int chunkAbsX = chunk.x << 3;
            int chunkAbsY = chunk.y << 3;
            for(int localX = 0; localX < 8; localX++) {
                for(int localY = 0; localY < 8; localY++) {
                    Tile localTargetTile = Tile.get(chunkAbsX + localX, chunkAbsY + localY, chunk.z, false);
                    if(localTargetTile == null)
                        continue;
                    int newChunkAbsX = targetRegion.baseX + (chunk.pointX * 8);
                    int newChunkAbsY = targetRegion.baseY + (chunk.pointY * 8);
                    int newTileX = newChunkAbsX + DynamicChunk.rotatedX(localX, localY, chunk.rotation);
                    int newTileY = newChunkAbsY + DynamicChunk.rotatedY(localX, localY, chunk.rotation);
                    Tile newTile = Tile.get(newTileX, newTileY, chunk.pointZ, true);
                    newTile.clipping = localTargetTile.defaultClipping;
                    newTile.multi = localTargetTile.multi;
                    newTile.roofExists = localTargetTile.roofExists;
                    newTile.nearBank = localTargetTile.nearBank;
                    if(localTargetTile.gameObjects != null) {
                        for(GameObject obj : localTargetTile.gameObjects) {
                            if(obj.isSpawned()) //we don't want to copy spawned objects
                                continue;
                            ObjectDef def = ObjectDef.get(obj.originalId);
                            int newX = newChunkAbsX + DynamicChunk.rotatedX(localX, localY, chunk.rotation, def.xLength, def.yLength, obj.direction);
                            int newY = newChunkAbsY + DynamicChunk.rotatedY(localX, localY, chunk.rotation, def.xLength, def.yLength, obj.direction);
                            int newDirection = (obj.direction + chunk.rotation) & 0x3;
                            objects.add(new GameObject(obj.originalId, newX, newY, chunk.pointZ, obj.type, newDirection));
                        }
                    }
                }
            }
            targetRegion.dynamicData[chunk.pointZ][chunk.pointX][chunk.pointY][0] = (chunk.rotation << 1) | (chunk.z << 24) | (chunk.x << 14) | (chunk.y << 3);
            targetRegion.dynamicData[chunk.pointZ][chunk.pointX][chunk.pointY][1] = chunk.regionId;
        }
        for(GameObject obj : objects) { //objects need to be "added" to tiles last to clip properly
            if (objectConsumer != null)
                objectConsumer.accept(obj);
            if (obj.id != -1)
                Tile.get(obj.x, obj.y, obj.z, true).addObject(obj);
        }
        targetRegion.empty = false;
    }

    /**
     * Build by bounds (May not be precise if bounds coordinates don't match absolute chunk coordinates)
     */

    public DynamicMap build(Bounds bounds) {
        int xChunks = (int) (Math.ceil((bounds.neX - bounds.swX) / 8) + 1);
        int yChunks = (int) Math.ceil((bounds.neY - bounds.swY) / 8) + 1;
        if (xChunks > 16 || yChunks > 16) {
            throw new IllegalArgumentException("Dynamic regions cannot exceed 2x2 region dimensions");
        }
        List<DynamicChunk> chunks = new ArrayList<>();
        for(int pointZ = 0; pointZ < 4; pointZ++) {
            if(bounds.z != -1 && pointZ != bounds.z) //ignore this height
                continue;
            for(int pointX = 0; pointX < xChunks; pointX++) {
                int chunkX = (bounds.swX + (pointX * 8)) >> 3;
                for(int pointY = 0; pointY < yChunks; pointY++) {
                    int chunkY = (bounds.swY + (pointY * 8)) >> 3;
                    chunks.add(new DynamicChunk(chunkX, chunkY, pointZ).pos(pointX, pointY, pointZ));
                }
            }
        }
        return build(chunks);
    }

    /**
     * Events
     */

    private List<Event> events;

    public void addEvent(EventConsumer eventConsumer) {
        if(events == null) {
            events = new ArrayList<>();
            World.startEvent(event -> {
                while(swRegion != null) {
                    if(!events.isEmpty())
                        events.removeIf(e -> !e.tick());
                    event.delay(1);
                }
            });
        }
        events.add(EventWorker.createEvent(eventConsumer));
    }

    /**
     * Npcs
     */

    private List<NPC> npcs;

    public void addNpc(NPC npc) {
        if(npcs == null)
            npcs = new ArrayList<>();
        npcs.add(npc);
    }

    public void removeNpc(NPC npc) {
        npc.remove();
        npcs.remove(npc);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public void forPlayers(Consumer<Player> action) {
        for (Region r: getRegions()) {
            if (r != null) {
                r.players.forEach(action);
            }
        }
    }

    /**
     * Destroy
     */

    public void destroy() {
        swRegion.destroy();
        FREE_REGIONS.offer(swRegion);
        swRegion = null;
        if(nwRegion != null) {
            nwRegion.destroy();
            nwRegion = null;
        }
        if(seRegion != null) {
            seRegion.destroy();
            seRegion = null;
        }
        if(neRegion != null) {
            neRegion.destroy();
            neRegion = null;
        }
        if(events != null) {
            events.clear();
            events = null;
        }
        if(npcs != null) {
            npcs.removeIf(npc -> {
                npc.remove();
                return true;
            });
            npcs = null;
        }
    }

    /**
     * Convert - Returns the equivalent abs coordinate from the new "dynamic" region.
     */

    public int convertX(int absX) {
        return convertX(swRegion, absX);
    }

    public int convertY(int absY) {
        return convertY(swRegion, absY);
    }

    public int convertX(Region region, int absX) {
        int localX = absX - region.dynamicRegionBaseX;
        return region.baseX + localX;
    }

    public int convertY(Region region, int absY) {
        int localY = absY - region.dynamicRegionBaseY;
        return region.baseY + localY;
    }

    public Position convertPosition(Position pos) {
        return new Position(convertX(pos.getX()), convertY(pos.getY()), pos.getZ());
    }

    /**
     * Revert - Returns the equivalent abs coordinate from the src "original" region.
     */

    public int revertX(int absX) {
        return revertX(swRegion, absX);
    }

    public int revertY(int absY) {
        return revertY(swRegion, absY);
    }

    public int revertX(Region region, int absX) {
        int localX = absX - region.baseX;
        return region.dynamicRegionBaseX + localX;
    }

    public int revertY(Region region, int absY) {
        int localY = absY - region.baseY;
        return region.dynamicRegionBaseY + localY;
    }

    public Position revertPosition(Position pos) {
        return new Position(revertX(pos.getX()), revertY(pos.getY()), pos.getZ());
    }

    /**
     * Misc
     */

    public boolean isIn(Player player) {
        int regionId = player.lastRegion.id;
        if(swRegion != null && swRegion.id == regionId)
            return true;
        if(nwRegion != null && nwRegion.id == regionId)
            return true;
        if(seRegion != null && seRegion.id == regionId)
            return true;
        if(neRegion != null && neRegion.id == regionId)
            return true;
        return false;
    }

    public MapListener assignListener(Player player) {
        MapListener listener = toListener();
        player.addActiveMapListener(listener);
        return listener;
    }

    public MapListener toListener() {
        return new MapListener(this::isIn);
    }

    public Region[] getRegions() {
        return new Region[]{swRegion, seRegion, nwRegion, neRegion};
    }

    private Consumer<GameObject> objectConsumer;

    public void setObjectConsumer(Consumer<GameObject> objectConsumer) {
        this.objectConsumer = objectConsumer;
    }

    /**
     * Load
     */

    public static void load() {
        List<Region> emptyRegions = new ArrayList<>();
        int dynamicIndex = 1;
        for(int x = 1; x < 127; x++) {
            yLoop: for(int y = 1; y < 255; y++) {
                Region swRegion = Region.get((x * 256) + y);
                if(Wilderness.getLevel(new Position(swRegion.baseX, swRegion.baseY, 0)) != 0 ||
                        Wilderness.getLevel(new Position(swRegion.baseX + 63, swRegion.baseY + 63, 0)) != 0)
                    continue;
                int[] regionIds = {swRegion.id, swRegion.id + 1, swRegion.id + 256, swRegion.id + 257};
                /**
                 * Validate regions..
                 */
                for(int regionId : regionIds) {
                    Region region = Region.get(regionId);
                    if(!region.empty || region.tiles != null) {
                        /* this region isn't blank! */
                        continue yLoop;
                    }
                    if(region.dynamicIndex != -1) {
                        /* this region is already being used */
                        continue yLoop;
                    }
                }
                /**
                 * Check passed, queue base (sw) region!
                 */
                for(int regionId : regionIds)
                    Region.get(regionId).dynamicIndex = dynamicIndex;
                emptyRegions.add(swRegion);
                dynamicIndex++;
            }
        }

        PVPInstance.init(emptyRegions);
        emptyRegions.removeIf(r -> r.dynamicData != null);

        Collections.shuffle(emptyRegions);
        FREE_REGIONS.addAll(emptyRegions);
    }
}