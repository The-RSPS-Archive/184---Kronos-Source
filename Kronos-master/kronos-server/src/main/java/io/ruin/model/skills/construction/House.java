package io.ruin.model.skills.construction;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MultiZone;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.dynamic.DynamicChunk;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.skills.construction.actions.CombatRoom;
import io.ruin.model.skills.construction.actions.Costume;
import io.ruin.model.skills.construction.actions.CostumeStorage;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.construction.room.impl.MenagerieRoom;
import io.ruin.model.skills.construction.room.impl.SimpleRoom;
import io.ruin.model.skills.construction.servants.Servant;
import io.ruin.model.skills.construction.servants.ServantSave;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.ruin.cache.ItemID.COINS_995;

public class House {

    static {
        LoginListener.register(player -> {
            if (player.house != null) {
                player.house.calculate();
                player.house.petContainer.init(player, 40, -1, 63785,517, false);
                if (player.house.servantSave.getHiredServant() != null) {
                    Config.HIRED_SERVANT.set(player, player.house.servantSave.getHiredServant().getVarpbitValue());
                }
                player.house.servantSave.init();
            }
        });
    }

    public static final int MAX_DIMENSION = 8;

    @Expose private HouseStyle style = HouseStyle.BASIC_WOOD;

    @Expose private Room[][][] rooms = new Room[3][8][8];

    @Expose private HouseLocation location = HouseLocation.EDGEVILLE;

    @Expose private boolean locked;

    @Expose private int tips; // gp left in tip jar by other players for the house owner
    @Expose private boolean tipJarNotifications = true;

    @Expose private ItemContainer petContainer = new ItemContainer(); // where pets are stored
    public ServantSave getServantSave() {
        return servantSave;
    }

    @Expose private int moneyInMoneybag = 0;

    @Expose private ServantSave servantSave = new ServantSave();

    @Expose private Map<Costume, int[]> fancyDressStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> armourCaseStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> magicWardrobeStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> capeRackStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> easyTreasureTrailsStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> mediumTreasureTrailsStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> hardTreasureTrailsStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> eliteTreasureTrailsStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> masterTreasureTrailsStorage = new HashMap<>();
    @Expose private Map<Costume, int[]> toyBoxStorage = new HashMap<>();

    private Servant servant;

    private boolean hasServantsMoneybag = false;

    private boolean hasBellPull;
    private MenagerieRoom menagerie = null; // reference to the menagerie

    private boolean buildingMode = true;

    private DynamicMap map;

    private Player owner;
    private Map<RoomDefinition, Integer> roomTypeCounts = new HashMap<>();
    private Position entryPosition = null;

    private Position fairyRingPosition = null;

    private Position spiritTreePosition = null;

    private int portalCount;

    private int roomCount;

    private ChallengeMode challengeMode = ChallengeMode.OFF;

    public House() {
        rooms[1][3][4] = new SimpleRoom().init(RoomDefinition.PARLOUR);
        rooms[1][3][3] = new SimpleRoom().init(RoomDefinition.GARDEN);
        rooms[1][3][3].setBuilt(0, Buildable.EXIT_PORTAL);
        calculate();
    }

    public void guestEnter(Player player, Position localToEnter) {
        if (!isBuilt())
            return;
        player.startEvent(event -> {
            player.lock();
            player.setHidden(true);
            player.openInterface(InterfaceType.SECONDARY_OVERLAY, Interface.CONSTRUCTION_LOADING);
            player.getPacketSender().sendMapState(2);
            for (int z = 0; z < 3; z++) { // this is actually really hilarious but it's required
                player.getMovement().teleport(map.swRegion.baseX + 31, map.swRegion.baseY + 31, z);
                event.delay(1);
            }
            event.delay(1);
            player.getPacketSender().sendMapState(0);
            teleportInside(player, localToEnter);
            player.closeInterface(InterfaceType.SECONDARY_OVERLAY);
            player.closeInterfaces();
            player.setHidden(false);
            assignListener(player);
            entered(player);
            player.unlock();
        });
    }

    public void enterOrPortal(Player player, Position returnPosition) {
        if (!isBuilt()) {
            player.getMovement().teleport(getLocation().getPosition());
        } else {
            guestEnter(player, returnPosition);
        }
    }

    public void buildAndEnter(Player player) {
        buildAndEnter(player, null, false);
    }

    public void buildAndEnter(Player player, boolean buildingMode) {
        buildAndEnter(player, null, buildingMode);
    }

    public void buildAndEnter(Player player, Position localToEnter, boolean buildingMode) {
        buildAndEnter(player, localToEnter, buildingMode, null);
    }

    public void buildAndEnter(Player player, Position localToEnter, boolean buildingMode, Runnable onEnter) {
        if (player.house == this)
            this.owner = player;
        if (map != null)
            map.destroy();
        this.buildingMode = buildingMode;
        Config.BUILDING_MODE.set(player, buildingMode ? 1 : 0);
        player.lock();
        player.startEvent(event -> {
            player.openInterface(InterfaceType.POH_LOADING, Interface.CONSTRUCTION_LOADING);
            player.getPacketSender().sendMapState(2);
            build(); // may be worth investigating doing this off the main thread in the future
            for (int z = 0; z < 3; z++) { // this is actually really hilarious but it's required
                player.getMovement().teleport(map.swRegion.baseX + 31, map.swRegion.baseY + 31, z);
                event.delay(1);
            }
            teleportInside(player, localToEnter);
            event.delay(1);
            player.getPacketSender().sendMapState(0);
            player.closeInterface(InterfaceType.POH_LOADING);
            player.closeInterfaces();
            player.unlock();
            assignListener(player);
            entered(player);
            if (onEnter != null)
                onEnter.run();
        });
    }

    public void teleportInside(Player player, Position localToEnter) {
        if (localToEnter != null && (localToEnter.getZ() == 1 || getRoom(localToEnter.getX() / 8, localToEnter.getY() / 8, localToEnter.getZ()) != null)) {
            player.getMovement().teleport(map.swRegion.baseX + localToEnter.getX(), map.swRegion.baseY + localToEnter.getY(), localToEnter.getZ());
        } else if (entryPosition == null) {
            player.getMovement().teleport(map.swRegion.baseX + 31, map.swRegion.baseY + 31, 1);
        } else {
            player.getMovement().teleport(map.swRegion.baseX + entryPosition.getX(), map.swRegion.baseY + entryPosition.getY(), entryPosition.getZ());
        }
    }

    public Position localToAbs(Position pos) {
        return new Position(map.swRegion.baseX + pos.getX(), map.swRegion.baseY + pos.getY(), pos.getZ());
    }

    public void assignListener(Player player) {
        if (map != null) {
            map.assignListener(player)
                    .onEnter(this::entered)
                    .onExit(this::exited);
        }
    }

    private void destroy() {

    }

    public void test(Player player) {
        if (rooms[1][4][4] == null) {
            Room room = new SimpleRoom().init(RoomDefinition.PARLOUR);
            room.setBuilt(0, Buildable.CRUDE_WOODEN_CHAIR);
            room.setBuilt(2, Buildable.CRUDE_WOODEN_CHAIR);
            rooms[1][4][4] = room;
        }
        build();
        player.getMovement().teleport(map.swRegion.baseX, map.swRegion.baseY, 1);
    }

    public void build() {
        build(new DynamicMap());
    }

    public void build(DynamicMap map) {
        this.map = map;
        long start = System.nanoTime();
        challengeMode = ChallengeMode.OFF;
        List<DynamicChunk> chunks = new ArrayList<>();
        for (int z = 0; z < 4; z++) { //up to height 3 here because even though the player can't build at 3, they can build at 2 and so there might be roofs at 3
            for (int x = 0; x < MAX_DIMENSION; x++) {
                for (int y = 0; y < MAX_DIMENSION; y++) {
                    Room room = z == 3 ? null : rooms[z][x][y];
                    if (room != null) {
                        chunks.add(room.makeChunk(style).pos(x, y, z));
                    } else {
                        if (z == 0) {
                            chunks.add(getEmptyBasement(x, y).pos(x,y,z));
                        } else if (z == 1) {
                            chunks.add(RoomDefinition.GROUND_FLOORS.getChunk(style).pos(x, y, z));
                        } else if (z > 1) {
                            if (!buildingMode) {
                                Room below = rooms[z - 1][x][y];
                                if (below != null && !below.getDefinition().isOutdoors())
                                    chunks.add(getRoof(x, y).pos(x, y, z));
                            } else {
                                chunks.add(RoomDefinition.BLANK.getChunk(style).pos(x,y,z)); // blank chunk to level things correctly
                            }
                        }
                    }
                }
            }
        }
        map.build(chunks);
        map.swRegion.setHouse(this);
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < rooms[z].length; x++) {
                for (int y = 0; y < rooms[z][x].length; y++) {
                    Room room = rooms[z][x][y];
                    if (room != null) {
                        room.build(this);
                    }
                }
            }
        }
        MultiZone.add(Bounds.fromRegion(map.swRegion.id, 0)); // set our basement as multi
        spawnServant();
        calculate();
        long end = System.nanoTime();
        //System.out.println("House built in " + Duration.ofNanos(end - start).toMillis() + "ms, " + roomCount + " rooms!");
    }

    private void spawnServant() {
        if (servantSave.getHiredServant() != null) {
            if (!canHaveServant()) {
                owner.sendMessage(Color.RED.wrap("You currently employ a servant, but do not have 2 beds in your house. They will not appear until you do."));
                return;
            }
            servant = new Servant(servantSave, this);
            servant.spawn(localToAbs(entryPosition));
            addNPC(servant);
            servant.walkBounds = new Bounds(servant.getSpawnPosition(), 10);
            servant.getRouteFinder().routeSelf();
        }
    }

    public void calculate() {
        roomTypeCounts.clear();
        menagerie = null;
        spiritTreePosition = null;
        fairyRingPosition = null;
        entryPosition = null;
        portalCount = 0;
        hasServantsMoneybag = false;
        hasBellPull = false;
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < rooms[z].length; x++) {
                for (int y = 0; y < rooms[z][x].length; y++) {
                    Room room = rooms[z][x][y];
                    if (room != null) {
                        roomTypeCounts.compute(room.getDefinition(), (def, count) -> count == null ? 1 : count + 1);
                        if (room.getDefinition() == RoomDefinition.GARDEN || room.getDefinition() == RoomDefinition.FORMAL_GARDEN) {
                            if (room.getBuilt(0) == Buildable.EXIT_PORTAL) {
                                portalCount++;
                                entryPosition = room.getLocalPosition(3, 2, x,y,z);
                            }
                        }
                        if (room.getDefinition() == RoomDefinition.SUPERIOR_GARDEN) {
                            if (room.hasBuildable(Buildable.FAIRY_RING) || room.hasBuildable(Buildable.SPIRIT_TREE_AND_FAIRY_RING)) {
                                fairyRingPosition = room.getLocalPosition(3, room.hasBuildable(Buildable.SPIRIT_TREE_AND_FAIRY_RING) ? 2 : 3, x,y,z);
                            }
                            if (room.hasBuildable(Buildable.SPIRIT_TREE) || room.hasBuildable(Buildable.SPIRIT_TREE_AND_FAIRY_RING)) {
                                spiritTreePosition = room.getLocalPosition(3, 2, x,y,z);
                            }
                        }
                        if (menagerie == null && room instanceof MenagerieRoom) {
                            menagerie = (MenagerieRoom) room;
                        }
                        if (room.getDefinition() == RoomDefinition.BEDROOM) {
                            if (room.hasBuildable(Buildable.SERVANTS_MONEYBAG))
                                hasServantsMoneybag = true;
                        }
                        if (room.getDefinition() == RoomDefinition.DINING_ROOM && room.getBuilt(Hotspot.BELL_PULL) != null) {
                            hasBellPull = true;
                        }
                    }
                }
            }
        }
        roomCount = roomTypeCounts.entrySet().stream().mapToInt(Map.Entry::getValue).sum();
    }

    public boolean canHaveServant() { // requires 2 bedrooms and 2 beds (though 2 beds does imply 2 bedrooms)
        int bedCount = 0;
        int bedroomCount = 0;
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < rooms[z].length; x++) {
                for (int y = 0; y < rooms[z][x].length; y++) {
                    Room room = rooms[z][x][y];
                    if (room != null && room.getDefinition() == RoomDefinition.BEDROOM) {
                        bedroomCount++;
                        if (room.getBuilt(Hotspot.BED) != null)
                            bedCount++;
                    }
                }
            }
        }
        return bedCount >= 2 && bedroomCount >= 2;
    }

    public boolean canAddRoom(Player player, RoomDefinition def, int x, int y, int z) {
        if (def.isBasement() && z != 0) {
            player.dialogue(new MessageDialogue("This room can only be created in the basement."));
            return false;
        }
        if (def.isOutdoors() && z != 1) {
            player.dialogue(new MessageDialogue("You can only add that room on surface level."));
            return false;
        }
        if ((def == RoomDefinition.MENAGERIE_INDOORS || def == RoomDefinition.MENAGERIE_OUTDOORS)
                && roomTypeCounts.getOrDefault(RoomDefinition.MENAGERIE_INDOORS, 0) + roomTypeCounts.getOrDefault(RoomDefinition.MENAGERIE_OUTDOORS, 0) > 0) {
            player.dialogue(new MessageDialogue("You may only have one menagerie in your house.<br><br>Use the house viewer if you'd like to move it here."));
            return false;
        }
        if (def == RoomDefinition.COSTUME_ROOM
                && roomTypeCounts.getOrDefault(RoomDefinition.COSTUME_ROOM, 0) > 0) {
            player.dialogue(new MessageDialogue("You may only have one costume room in your house.<br><br>Use the house viewer if you'd like to move it here."));
            return false;
        }
        if (roomCount >= Construction.getMaxRooms(player.getStats().get(StatType.Construction).fixedLevel)
                && !player.isAdmin()) { // admins can bypass room limit but it probably breaks the house viewer. need to test
            player.dialogue(new MessageDialogue("You already have the maximum number of rooms allowed for your Construction level."));
            return false;
        }
        if (z > 1) {
            Room below = getRoom(x, y, z - 1);
            if (below == null || below.getDefinition().isOutdoors()) {
                player.dialogue(new MessageDialogue("You can't add a room with nothing below to support it."));
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRoom(Player player, Room room) {
        if (room.getChunkZ() == 1 && getRoom(room.getChunkX(), room.getChunkY(), room.getChunkZ() + 1) != null) {
            player.dialogue(new MessageDialogue("You can't move that room as it is supporting a room above it."));
            return false;
        }
        return true;
    }

    public boolean canRemoveRoom(Player player, Room room) {
        if (room.getChunkZ() == 1 && getRoom(room.getChunkX(), room.getChunkY(), room.getChunkZ() + 1) != null) {
            player.dialogue(new MessageDialogue("You can't remove that room as it is supporting a room above it."));
            return false;
        }
        if ((room.getDefinition() == RoomDefinition.GARDEN || room.getDefinition() == RoomDefinition.FORMAL_GARDEN)
                && room.hasBuildable(Buildable.EXIT_PORTAL)
                && portalCount == 1) {
            player.dialogue(new MessageDialogue("Your house must have at least one exit portal."));
            return false;
        }
        if (room.getDefinition() == RoomDefinition.MENAGERIE_OUTDOORS || room.getDefinition() == RoomDefinition.MENAGERIE_INDOORS) {
            if (!getPetContainer().isEmpty()) {
                player.dialogue(new MessageDialogue("You must remove the pets from the pet house before you can remove the menagerie."));
                return false;
            }
        }
        if (room.getDefinition() == RoomDefinition.COSTUME_ROOM) {
            for (CostumeStorage storage : CostumeStorage.values()) {
                if (storage.getSets(player).size() != 0) {
                    player.dialogue(new MessageDialogue("You must empty out all the containers in the room before you can remove the room."));
                    return false;
                }
            }
        }
        return true;
    }

    public void rebuild() {
        build(map);
    }

    public boolean isBuildingMode() {
        return buildingMode;
    }

    public DynamicMap getMap() {
        return map;
    }

    public HouseStyle getStyle() {
        return style;
    }

    public Room getRoom(int x, int y, int z) {
        if (x < 0 || x >= MAX_DIMENSION || y < 0 || y >= MAX_DIMENSION || z < 0 || z >= 3) {
            return null;
        }
        return rooms[z][x][y];
    }

    public void setRoom(int x, int y, int z, Room room) {
        rooms[z][x][y] = room;
        calculate();
    }

    private DynamicChunk getEmptyBasement(int x, int y) {
        if (isBuildingMode())
            return RoomDefinition.BLANK.getChunk(style);
        else
            return RoomDefinition.BASEMENT_FLOORS.getChunk(style);
//        int adjacents = 0;
//        boolean north = false, south = false, west = false, east = false;
//        if (getRoom(x, y + 1, 0) != null) {
//            adjacents++;
//            north = true;
//        }
//        if (getRoom(x, y - 1, 0) != null) {
//            adjacents++;
//            south = true;
//        }
//        if (getRoom(x - 1, y, 0) != null) {
//            adjacents++;
//            west = true;
//        }
//        if (getRoom(x + 1, y, 0) != null) {
//            adjacents++;
//            east = true;
//        }
//        return adjacents > 0 ? RoomDefinition.BASEMENT_FLOORS.getChunk(style) : RoomDefinition.BLANK.getChunk(style);
    }

    private DynamicChunk getRoof(int x, int y) { // kinda weird looking code but whatever
        int adjacents = 0;
        boolean north = false, south = false, west = false, east = false;
        if (getRoom(x, y + 1, 1) != null && getRoom(x, y + 1, 2) == null) {
            adjacents++;
            north = true;
        }
        if (getRoom(x, y - 1, 1) != null && getRoom(x, y - 1, 2) == null) {
            adjacents++;
            south = true;
        }
        if (getRoom(x - 1, y, 1) != null && getRoom(x - 1, y, 2) == null) {
            adjacents++;
            west = true;
        }
        if (getRoom(x + 1, y, 1) != null && getRoom(x + 1, y, 2) == null) {
            adjacents++;
            east = true;
        }
        if (adjacents == 0) {
            return RoomDefinition.ROOFTOP_STRAIGHT.getChunk(style);
        } else if (adjacents == 1) {
            DynamicChunk chunk = RoomDefinition.ROOFTOP_STRAIGHT.getChunk(style);
            if (west || east)
                chunk.rotate(1);
            return chunk;
        } else if (adjacents == 2) {
            if (west && east)
                return RoomDefinition.ROOFTOP_STRAIGHT.getChunk(style).rotate(1);
            else if (north && south)
                return RoomDefinition.ROOFTOP_STRAIGHT.getChunk(style);
            else if (east)
                return RoomDefinition.ROOFTOP_THREE_WAY.getChunk(style);
            else
                return RoomDefinition.ROOFTOP_THREE_WAY.getChunk(style).rotate(2);
        } else if (adjacents == 3) {
            DynamicChunk chunk = RoomDefinition.ROOFTOP_THREE_WAY.getChunk(style);
            if (!west)
                return chunk;
            else if (!north)
                return chunk.rotate(1);
            else if (!east)
                return chunk.rotate(2);
            else
                return chunk.rotate(3);
        } else {
            return RoomDefinition.ROOFTOP_FOUR_WAY.getChunk(style);
        }

    }

    public Player getOwner() {
        return owner;
    }

    public int getPortalCount() {
        return portalCount;
    }

    public void decrementPortalCount() {
        portalCount--;
    }

    public Position getEntryPosition() {
        return entryPosition;
    }

    public boolean isPlayerInside(Player player) {
        return map != null && map.isIn(player);
    }

    public Room getCurrentRoom(Player player) {
        int x = (player.getAbsX() >> 3) & 7;
        int y = (player.getAbsY() >> 3) & 7;
        //this only works because i locked houses to a single region... it would be much more complicated otherwise
        return getRoom(x, y, player.getHeight());
    }

    public HouseLocation getLocation() {
        return location;
    }

    public void setLocation(HouseLocation location) {
        this.location = location;
    }

    public boolean isBuilt() {
        return map != null && map.swRegion != null && map.swRegion.getHouse() != null && map.swRegion.getHouse() == this;
    }

    public boolean isLocked() {
        return locked;
    }

    public void expelGuests() {
        if (!isBuilt())
            return;
        for (Region region : map.getRegions()) {
            if (region == null || region.players == null)
                continue;
            region.players.forEach(p -> {
                if (p == owner)
                    return;
                if (p.seat != null)
                    p.seat.restore(p);
                p.getMovement().teleport(getLocation().getPosition());
                p.resetActions(true, true, true);
                p.dialogue(new MessageDialogue("The host has expelled all guests."));
                p.deathEndListener = null;
                p.attackPlayerListener = null;
            });
        }
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setStyle(HouseStyle style) {
        this.style = style;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Position getFairyRingPosition() {
        return fairyRingPosition;
    }

    public Position getSpiritTreePosition() {
        return spiritTreePosition;
    }

    public int getTips() {
        return tips;
    }

    public void addTip(Player tipper, int amount) {
        if (tips == Integer.MAX_VALUE) {
            tipper.sendMessage(owner.getName() + "'s tip jar is full right now.");
            return;
        }
        amount = Math.min(amount, tipper.getInventory().getAmount(COINS_995));
        if (amount == 0) {
            tipper.sendMessage("You don't have any coins in your inventory.");
            return;
        }
        tipper.getInventory().remove(COINS_995, amount);
        long newAmount = tips + amount;
        tips = newAmount > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) newAmount;
        if (owner != null && owner.isOnline() && isTipJarNotifications()) {
            owner.sendMessage(tipper.getName() + " has left you a tip: Coins x " + NumberUtils.formatNumber(amount) + ".");
        }
    }

    public void setTips(int tips) {
        this.tips = tips;
    }

    public boolean isTipJarNotifications() {
        return tipJarNotifications;
    }

    public void setTipJarNotifications(boolean tipJarNotifications) {
        this.tipJarNotifications = tipJarNotifications;
    }

    public NPC addNPC(NPC npc) {
        if (map != null)
            map.addNpc(npc);
        return npc;
    }

    public ItemContainer getPetContainer() {
        return petContainer;
    }

    public boolean hasServantsMoneybag() {
        return hasServantsMoneybag;
    }

    public int getMoneyInMoneybag() {
        return moneyInMoneybag;
    }

    public void setMoneyInMoneybag(int moneyInMoneybag) {
        this.moneyInMoneybag = moneyInMoneybag;
    }

    public void callServant() {
        if (owner == null || !owner.isInOwnHouse() || servant == null || servant.isHidden())
            return;
        servant.getMovement().teleport(owner.getPosition());
        servant.startFollowing();
    }

    public boolean isHasBellPull() {
        return hasBellPull;
    }

    public void confirmRemoveRoom(Player player, Room adjacent) {
        player.dialogue(new OptionsDialogue("Remove the " + adjacent.getDefinition().getName() + "?",
                new Option("Yes.", () -> {
                    if (canRemoveRoom(player, adjacent)) {
                        setRoom(adjacent.getChunkX(), adjacent.getChunkY(), adjacent.getChunkZ(), null);
                        buildAndEnter(player, player.getPosition().localPosition(), true);
                    }
                }),
                new Option("No.")));
    }

    public void leave(Player p) {
        p.unlock();
        p.cureVenom(0);
        if (p.getCurrentHouse() != null)
            p.getMovement().teleport(p.getCurrentHouse().getLocation().getPosition());
    }

    public ChallengeMode getChallengeMode() {
        return challengeMode;
    }

    public void setChallengeMode(ChallengeMode mode) {
        ChallengeMode oldMode = this.challengeMode;
        this.challengeMode = mode;
        switch(challengeMode) {
            case OFF:
                getMap().swRegion.players.forEach(p -> p.sendMessage(Color.RED.wrap(owner.getName() + " has disabled Challenge Mode.")));
                getMap().swRegion.players.forEach(p -> p.setAction(1, null));
                break;
            case ON:
                getMap().swRegion.players.forEach(p -> p.sendMessage(Color.RED.wrap(owner.getName() + " has enabled Challenge Mode!")));
                getMap().swRegion.players.forEach(p -> p.setAction(1, null));
                break;
            case PVP:
                getMap().swRegion.players.forEach(p -> p.sendMessage(Color.RED.wrap(owner.getName() + " has enabled PvP Challenge Mode!")));
                getMap().swRegion.players.forEach(p -> p.setAction(1, PlayerAction.ATTACK));
                break;
        }
        for (Room[] rooms1 : rooms[0]) { // basement only, other rooms dont interact with challenge mode
            for (Room room : rooms1) {
                if (room != null) {
                    if (oldMode == ChallengeMode.OFF)
                        room.enableChallengeMode();
                    else
                        room.disableChallengeMode();
                }
            }
        }

    }

    private void entered(Player player) {
        if (getChallengeMode() == ChallengeMode.PVP) {
            player.setAction(1, PlayerAction.ATTACK);
        }
        player.attackPlayerListener = (p, pTarget, message) -> {
            if (p.getCurrentRoom() != null && p.getCurrentRoom().getDefinition() == RoomDefinition.COMBAT_ROOM && pTarget.getCurrentRoom() != null && pTarget.getCurrentRoom().getDefinition() == RoomDefinition.COMBAT_ROOM) {
                return CombatRoom.canAttack(p, pTarget, message);
            }
            return canAttackChallengeMode(p, pTarget, message);
        };
        player.deathEndListener = (entity, killer, killHit) -> {
            player.getCombat().restore();
            if (!CombatRoom.handleDeath(player)) {
                leave(player);
            }
        };
    }

    public boolean canAttackChallengeMode(Player p, Player pTarget, boolean message) {
        if (getChallengeMode() != ChallengeMode.PVP)
            return false;
        if (p.getHeight() == 0 && pTarget.getHeight() == 0) {
            return true;
        } else {
            if (message)
                p.sendMessage("You can only attack other players in the dungeon.");
            return false;
        }
    }

    private void exited(Player p, boolean logout) {
        if (logout) {
            p.getMovement().teleport(location.getPosition());
        }
        p.setAction(1, null); // remove attack option lol
        p.deathEndListener = null;
        p.attackPlayerListener = null;
        Construction.confiscate(p);
        if (p == owner) {
            expelGuests();
            destroy();
        }
    }

    public Map<Costume, int[]> getFancyDressStorage() {
        return fancyDressStorage;
    }

    public Map<Costume, int[]> getArmourCaseStorage() {
        return armourCaseStorage;
    }

    public Map<Costume, int[]> getMagicWardrobeStorage() {
        return magicWardrobeStorage;
    }

    public Map<Costume, int[]> getCapeRackStorage() {
        return capeRackStorage;
    }

    public Map<Costume, int[]> getEasyTreasureTrailsStorage() {
        return easyTreasureTrailsStorage;
    }

    public Map<Costume, int[]> getMediumTreasureTrailsStorage() {
        return mediumTreasureTrailsStorage;
    }

    public Map<Costume, int[]> getHardTreasureTrailsStorage() {
        return hardTreasureTrailsStorage;
    }

    public Map<Costume, int[]> getEliteTreasureTrailsStorage() {
        return eliteTreasureTrailsStorage;
    }

    public Map<Costume, int[]> getMasterTreasureTrailsStorage() {
        return masterTreasureTrailsStorage;
    }

    public Map<Costume, int[]> getToyBoxStorage() {
        return toyBoxStorage;
    }
}
