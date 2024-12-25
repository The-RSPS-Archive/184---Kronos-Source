package io.ruin.model.map;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.bank.BankActions;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.model.skills.construction.House;

import java.util.ArrayList;
import java.util.Arrays;

public class Region {

    public static final int CLIENT_SIZE = 104;

    public static final Region[] LOADED = new Region[Short.MAX_VALUE + 1];

    /**
     * Separator
     */

    public final int id;

    public final int baseX, baseY;

    public final Bounds bounds;

    public final ArrayList<Player> players;

    public final ArrayList<Tile> activeTiles;

    private House house; // house instance that this region currently belongs to

    public Region(int id) {
        this.id = id;
        this.baseX = (id >> 8) * 64;
        this.baseY = (id & 0xff) * 64;
        this.bounds = Bounds.fromRegion(id);
        this.players = new ArrayList<>();
        this.activeTiles = new ArrayList<>();
    }

    /**
     * Data
     */

    public int[] keys;

    public Tile[][][] tiles;

    public int[][][][] dynamicData;

    public int dynamicIndex = -1;

    public int dynamicRegionBaseX, dynamicRegionBaseY;

    public boolean empty;

    public void init() { //not my favorite design, let's come back to this one day..
        byte[][][] tileData = new byte[4][64][64];
        byte[] mapData = getMapData();
        if(mapData != null) {
            InBuffer mapIn = new InBuffer(mapData);
            for(int z = 0; z < 4; z++) {
                for(int x = 0; x < 64; x++) {
                    for(int y = 0; y < 64; y++) {
                        for(; ; ) {
                            int i = mapIn.readByteUnsafe() & 0xFF;
                            if(i == 0)
                                break;
                            if(i == 1) {
                                mapIn.skipByte();
                                break;
                            }
                            if(i <= 49)
                                mapIn.skipByte();
                            else if(i <= 81)
                                tileData[z][x][y] = (byte) (i - 49);
                        }
                    }
                }
            }
            for(int z = 0; z < 4; z++) {
                for(int x = 0; x < 64; x++) {
                    for(int y = 0; y < 64; y++) {
                        if((tileData[z][x][y] & 0x1) == 1) {
                            int height = z;
                            if((tileData[1][x][y] & 0x2) == 2)
                                height--;
                            if(height >= 0) {
                                int absX = baseX + x;
                                int absY = baseY + y;
                                Tile tile = getTile(absX, absY, z, true);
                                tile.flagUnmovable();
                                tile.defaultClipping = tile.clipping;
                            }
                        }
                        if((tileData[z][x][y] & 0x4) != 0) {
                            int absX = baseX + x;
                            int absY = baseY + y;
                            Tile tile = getTile(absX, absY, z, true);
                            tile.roofExists = true;
                        }
                    }
                }
            }
        }
        byte[] landscapeData = null;
        boolean invalidKeys = false;
        try {
            landscapeData = getLandscapeData();
        } catch(Throwable t) {
            //System.err.println("Invalid Map Keys for Region (" + id + "): base=(" + baseX + ", " + baseY + ") keys=" + Arrays.toString(keys));
            invalidKeys = true;
        }
        if(landscapeData != null) {
            InBuffer landIn = new InBuffer(landscapeData);
            int objectId = -1;
            for(; ; ) {
                int increment = landIn.readSmart();
                if(increment == 0)
                    break;
                objectId += increment;
                int positionHash = 0;
                for(; ; ) {
                    int increment2 = landIn.readSmart();
                    if(increment2 == 0)
                        break;
                    positionHash += increment2 - 1;
                    int localX = (positionHash >> 6) & 0x3f;
                    int localY = positionHash & 0x3f;
                    int height = positionHash >> 12;
                    int objectHash = landIn.readByteUnsafe() & 0xFF;
                    int type = objectHash >> 2;
                    int direction = objectHash & 0x3;
                    if(localX < 0 || localX >= 64 || localY < 0 || localY >= 64)
                        continue;
                    if((tileData[1][localX][localY] & 0x2) == 2)
                        height--;
                    if(height >= 0) {
                        int absX = baseX + localX;
                        int absY = baseY + localY;
                        GameObject obj = new GameObject(objectId, absX, absY, height, type, direction);
                        getTile(absX, absY, height, true).addObject(obj);
                        BankActions.markTiles(obj);
                    }
                }
            }
        }
        empty = !invalidKeys && mapData == null && landscapeData == null;
    }

    public Tile getTile(int x, int y, int z, boolean create) {
        int localX = x - baseX;
        int localY = y - baseY;
        if(tiles == null) {
            if(!create)
                return null;
            tiles = new Tile[4][64][64];
        }
        Tile tile = tiles[z][localX][localY];
        if(tile == null && create)
            tile = tiles[z][localX][localY] = new Tile(this);
        return tile;
    }

    private byte[] getMapData() {
        IndexFile index = Server.fileStore.get(5);
        int mapArchiveId = index.getArchiveId("m" + ((baseX >> 3) / 8) + "_" + ((baseY >> 3) / 8));
        return mapArchiveId == -1 ? null : index.getFile(mapArchiveId, 0);
    }

    public byte[] getLandscapeData() {
        if(keys == null)
            return null;
        IndexFile index = Server.fileStore.get(5);
        int landArchiveId = index.getArchiveId("l" + ((baseX >> 3) / 8) + "_" + ((baseY >> 3) / 8));
        return landArchiveId == -1 ? null : index.getFile(landArchiveId, 0, keys[0] == 0 && keys[1] == 0 && keys[2] == 0 && keys[3] == 0 ? null : keys);
    }

    public static Region get(int regionId) {
        return LOADED[regionId];
    }

    public static Region get(int absX, int absY) {
        return get(getId(absX, absY));
    }

    public static int getId(int absX, int absY) {
        return ((absX >> 6) << 8) | absY >> 6;
    }

    public static int getClipping(int x, int y, int z) {
        Region region = Region.get(x, y);
        if(region.empty)
            return RouteFinder.UNMOVABLE_MASK;
        Tile tile = region.getTile(x, y, z, false);
        return tile == null ? 0 : tile.clipping;
    }

    /**
     * Updating
     */

    public static void update(Player player) {
        player.getPacketSender().clearChunks();
        for(Region region : player.getRegions()) {
            for(Tile tile : region.activeTiles)
                tile.update(player);
        }
    }

    /**
     * Destroy
     */

    public void destroy() {
        players.clear();
        if(!activeTiles.isEmpty()) {
            for(Tile tile : activeTiles)
                tile.destroy();
            activeTiles.clear();
        }
        tiles = null;
        dynamicIndex = -1;
        dynamicData = null;
        empty = true;
        house = null;
    }


    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}