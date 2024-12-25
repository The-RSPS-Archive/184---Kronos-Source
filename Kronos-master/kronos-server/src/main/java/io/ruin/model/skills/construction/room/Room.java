package io.ruin.model.skills.construction.room;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.dynamic.DynamicChunk;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Door;
import io.ruin.model.skills.construction.*;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.ruin.cache.ItemID.COINS_995;

public abstract class Room {

    public Room() {

    }

    public Room(RoomDefinition def) {
        this();
        init(def);
    }

    public Room init(RoomDefinition def) {
        definition = def;
        built = new Buildable[def.getHotspots().length];
        return this;
    }


    @Expose private RoomDefinition definition;

    @Expose private Buildable[] built;

    @Expose
    private int rotation;

    private List<GameObject> objects;

    private DynamicChunk chunk;
    protected House house;

    private Position basePosition;

    //puke

    public int viewerCost, viewerRotation;
    public int[] viewerMovedPoints;
    public int pointX, pointY, pointZ;
    public RoomDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(RoomDefinition definition) {
        this.definition = definition;
    }

    public void setBuilt(int index, Buildable buildable) {
        this.built[index] = buildable;
    }

    public Buildable[] getBuiltArray() {
        return built;
    }

    public Buildable getBuilt(int index) {
        if (built == null)
            throw new IllegalArgumentException("Not initialized properly");
        if (index < 0 || index >= built.length) {
            return null;
        }
        return built[index];
    }

    public Buildable getBuilt(Hotspot hotspot) {
        return getBuilt(getHotspotIndex(hotspot));
    }

    public List<GameObject> getHotspotObjects(Hotspot hotspot) {
        return objects.stream().filter(obj -> { // cache this maybe
            for (int id : hotspot.getHotspotIds()) {
                if (id == obj.originalId)
                    return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    public int getHotspotIndex(Hotspot type) {
        for (int i = 0; i < getDefinition().getHotspots().length; i++) {
            if (getDefinition().getHotspots()[i] == type)
                return i;
        }
        return -1;
    }

    public boolean hasBuildable(Buildable b) {
        for (Buildable buildable: built) {
            if (b == buildable)
                return true;
        }
        return false;
    }

    protected void onBuildableChanged(Player player, Hotspot hotspot, Buildable newBuildable) { // for subclasses

    }

    public void build(House house) {
        checkBuilt();
        setHouse(house);
        basePosition = new Position(getHouse().getMap().swRegion.baseX + (getChunkX() * 8), getHouse().getMap().swRegion.baseY + (getChunkY() * 8), getChunkZ());
        loadDefaultObjects(house.getMap());
        render();
        registerActions();
        onBuild();
    }

    /**
     * Called as the last step in building this room for the current house
     */
    protected void onBuild() { // for sub-classes

    }

    private void checkBuilt() {
        if (built.length < getDefinition().getHotspots().length) { // should only happen during development
            Buildable[] newBuilt = new Buildable[getDefinition().getHotspots().length];
            System.arraycopy(built, 0, newBuilt, 0, built.length);
            built = newBuilt;
        }
    }

    private void registerActions() {
        objects.forEach(obj -> { // door hotspots [the ones that lead into other rooms]
            if (obj.id == -1 || obj.getDef().name == null || !obj.getDef().name.equalsIgnoreCase("door hotspot")) {
                return;
            }
            ObjectAction.register(obj, 5, (player, unused) -> {
                Room adjacent = getAdjacentRoom(Direction.fromDoorDirection(obj.direction));
                if (adjacent == null) {
                    if (isAdjacentInBounds(Direction.fromDoorDirection(obj.direction)))
                        RoomDefinition.openRoomSelection(player, def -> previewBuild(player, def, Direction.fromDoorDirection(obj.direction)));
                    else
                        player.dialogue(new MessageDialogue("There's no space to build a room there."));
                } else {
                    player.house.confirmRemoveRoom(player, adjacent);
                }
            });
        });
        for (int i = 0; i < getDefinition().getHotspots().length; i++) { // actions for hotspot/buildable objects
            int hotspotIndex = i;
            Hotspot hotspot = getDefinition().getHotspots()[hotspotIndex];
            objects.forEach(obj -> {
                for (int id : hotspot.getHotspotIds()) {
                    if (id == obj.originalId) {
                        ObjectAction.register(obj, 5, (player, unused) -> hotspotInteract(player, hotspotIndex));
                        if (Arrays.stream(hotspot.getBuildables()).anyMatch(b -> b.getUpgradeFrom() != null)) // not sure if there are objects where this will cause issues
                            ObjectAction.register(obj, 4, (player, unused) -> openFurnitureCreation(player, hotspotIndex));
                    }
                }
            });
        }
    }

    public int indexOfBuilt(int hotspotIndex) {
        Hotspot hotspot = definition.getHotspots()[hotspotIndex];
        for (int i = 0; i < hotspot.getBuildables().length; i++) {
            if (hotspot.getBuildables()[i] == built[hotspotIndex])
                return i;
        }
        return -1;
    }

    public int indexOfBuildable(int hotspotIndex, Buildable b) {
        Hotspot hotspot = definition.getHotspots()[hotspotIndex];
        for (int i = 0; i < hotspot.getBuildables().length; i++) {
            if (hotspot.getBuildables()[i] == b)
                return i;
        }
        return -1;
    }

    public Room getAdjacentRoom(Direction direction) {
        if (direction == null)
            throw new IllegalArgumentException("invalid dir");
        if (!isAdjacentInBounds(direction))
            return null;
        int x = getChunkX() + direction.deltaX;
        int y = getChunkY() + direction.deltaY;
        return house.getRoom(x, y, getChunkZ());
    }


    public boolean isAdjacentInBounds(Direction dir) {
        if (dir == null)
            return false;
        int x = getChunkX() + dir.deltaX;
        int y = getChunkY() + dir.deltaY;
        return x >= 0 && x < House.MAX_DIMENSION && y >= 0 && y < House.MAX_DIMENSION;
    }

    public Room getRoomBelow() {
        if (getChunkZ() == 0)
            return null;
        return house.getRoom(getChunkX(), getChunkY(), getChunkZ() - 1);
    }

    public Room getRoomAbove() {
        if (getChunkZ() == 2)
            return null;
        return house.getRoom(getChunkX(), getChunkY(), getChunkZ() + 1);
    }

    private void hotspotInteract(Player player, int hotspotIndex) {
        if (player != house.getOwner()) {
            player.dialogue(new MessageDialogue("Only the house owner can do that."));
            return;
        }
        Buildable buildable = getBuilt(hotspotIndex);
        if (buildable != null) { // we have something built here, open remove dialogue
            if (!house.isBuildingMode()) {
                player.dialogue(new MessageDialogue("You can only do that in building mode."));
            } else if (!player.isInOwnHouse()) { // this will never happen under normal circumstances are you cannot be in another player's house while they're in building mode but better safe than sorry...
                player.dialogue(new MessageDialogue("Only the house owner can do that."));
            } else {
                player.dialogue(new OptionsDialogue("Really remove it?",
                        new Option("Yes", () -> {
                            if (!getBuilt(hotspotIndex).canRemove(player, this)) {
                                return;
                            }
                            clearBuilt(hotspotIndex);
                            player.animate(Construction.REMOVE_OBJECT);
                            if (Buildable.isStairs(buildable) && (definition == RoomDefinition.SKILL_HALL_DOWN || definition == RoomDefinition.QUEST_HALL_DOWN)) { // if we just removed stairs going down, we need to change the room type back to the "up" one because the "down" version has a hole...
                                definition = definition.getAlternateForm(true);
                                house.buildAndEnter(player, player.getPosition().localPosition(), true);
                            } else
                                onBuildableChanged(player, definition.getHotspots()[hotspotIndex], null);
                        }),
                        new Option("No")));
            }
        } else { // nothing built, show options
            openFurnitureCreation(player, hotspotIndex);
        }
    }

    public void openFurnitureCreation(Player player, int hotspotIndex) {
        Hotspot hotspot = getDefinition().getHotspots()[hotspotIndex];
        Buildable.sendFurnitureCreation(player, this, hotspotIndex, hotspot.getBuildables());
    }

    private void render() {
        objects.stream()
                .filter(obj -> obj.id == 13830 || (getDefinition() == RoomDefinition.CHAPEL && IntStream.of(Hotspot.WINDOW.getHotspotIds()).anyMatch(i -> obj.id == i))).forEach(obj -> { // :(
            Room adjacent = getAdjacentRoom(Direction.fromDoorDirection(obj.direction));
            //something to think about: should curtains stay when the windows are replaced by walls? probably not..
            if ((adjacent == null || adjacent.getDefinition().isOutdoors()) && getChunkZ() > 0) {
                if (getDefinition() != RoomDefinition.CHAPEL || (getBuilt(Hotspot.WINDOW) == null && !house.isBuildingMode())) // if the room IS a chapel and has a custom window built, we need to leave the window alone here so that the hotspot rendering will work correctly
                    obj.setId(house.getStyle().windowId);
            }
            else
                obj.setId(house.getStyle().wallId); // this section of the wall has another indoors room on the other side so we place a wall here instead of a window
        });
        if (getDefinition().isBasement()) {
            //turns out osrs doesn't do what i did below even though (in my opinion) it looks better
//            objects.stream().filter(obj -> obj.id == 13066 || obj.id == 13067)
//                    .filter(obj -> (obj.x & 7) == 0 || (obj.x & 7) == 7 || (obj.y & 7) == 0 || (obj.y & 7) == 0)
//                    .forEach(obj -> { // dungeon rooms: these objects are adjacent to doors, if there is no "door" (passage to another room) we need to replace them with a version that doesnt have rounded edges
//                Room adjacent = getAdjacentRoom(Direction.fromDoorDirection(obj.direction));
//                if (adjacent == null)
//                    obj.setId(getHouse().getStyle().dungeonWallId);
//            });
        }
        if (!house.isBuildingMode()) // doors
            objects.stream().filter(obj ->
                    obj.type == 0
                            && onDoorLocation(obj)
                            && obj.id != -1
                            && obj.id == obj.originalId
                            && obj.id != house.getStyle().wallId
                            && obj.getDef().options[4] != null
                            && obj.id != house.getStyle().windowId)
                    .forEach(obj -> {
                        Room adjacent = getAdjacentRoom(Direction.fromDoorDirection(obj.direction));
                        if (getDefinition().isOutdoors()) // this is an outdoors 'room'... just delete the door
                            obj.remove();
                        else if (getChunkZ() == 0 && adjacent == null) // no adjacent in basement -> place wall instead of door
                            obj.setId(getDefinition().isBasement() ? getHouse().getStyle().dungeonWallId : house.getStyle().wallId); // instead of removing the door in dungeon rooms we could also replace it with a dungeon-type wall. i think it looks better
                        else if (getChunkZ() > 1 && adjacent == null) // this room is upstairs and there is no adjacent one in this direction... place a wall instead of a door
                            obj.setId(house.getStyle().wallId);
                        else if (!isAdjacentInBounds(Direction.fromDoorDirection(obj.direction))) // out of bounds so instead of having a door we just place a wall so it doesn't look weird having a door to the void
                            obj.setId(house.getStyle().wallId);
                        else if (adjacent != null && !adjacent.getDefinition().isOutdoors()) {
                            Direction dir = Direction.fromDoorDirection(obj.direction);
                            GameObject other = Tile.getObject(-1, obj.x + dir.deltaX, obj.y + dir.deltaY, obj.z, 0, -1);
                            if (other != null && (other.id == house.getStyle().wallId || other.id == house.getStyle().dungeonWallId)) // if the object adjacent to this door is a wall, that means the adjacent room isn't connected to this one on this direction,
                                // so we place a wall instead of removing the door otherwise we'd have a small section of this wall looking thinner than the rest!
                                obj.setId(getDefinition().isBasement() ? house.getStyle().dungeonWallId : house.getStyle().wallId);
                            else
                                obj.remove();
                        } else { // if we reach this point then we actually need to place a door here
                            int newId = obj.getDef().verticalFlip ? house.getStyle().doorId2 : house.getStyle().doorId1;
                            if (Config.RENDER_DOORS_MODE.get(house.getOwner()) == 2) // do not render doors
                                newId = -1;
                            obj.setId(newId);
                            if (Config.RENDER_DOORS_MODE.get(house.getOwner()) == 1) { // render door open
                                Door.changeState(obj, house.getStyle() == HouseStyle.DEATHLY_MANSION);
                            }
                        }
                    });
        for (int i = 0; i < definition.getHotspots().length; i++) { // hotspots
            renderHotspot(i);
        }
    }

    public void buildObject(Player player, int hotspotIndex, int buildableIndex) {
        Hotspot hotspot = getDefinition().getHotspots()[hotspotIndex];
        Buildable[] buildables = hotspot.getBuildables();
        if (buildableIndex < 0 || buildableIndex >= buildables.length)
            return;
        Buildable selected = buildables[buildableIndex];
        buildObject(player, hotspotIndex, selected);
    }

    public void buildObject(Player player, int hotspotIndex, Buildable selected) {
        if (Construction.getEffectiveLevel(player, selected) < selected.getLevelReq()) {
            player.dialogue(new MessageDialogue("You need a Construction level of at least " + selected.getLevelReq() + " to build that."));
            return;
        }
        if (!selected.hasTools(player)) {
            player.dialogue(new MessageDialogue("You will need a hammer and a saw to build that."));
            return;
        }
        if (!selected.hasAllMaterials(player) && !player.isAdmin()) {
            player.sendMessage("You do not have all the required materials to build that.");
            return;
        }
        if (!selected.canBuild(player)) {
            return;
        }
        Buildable currentlyBuilt = getBuilt(hotspotIndex);
        if (selected.getUpgradeFrom() != null) {
            if (currentlyBuilt != selected.getUpgradeFrom()) {
                player.dialogue(new MessageDialogue("You must have " + StringUtils.withArticle(StringUtils.capitalizeFirst(selected.getUpgradeFrom().name().replace("_", " ").toLowerCase())) + " built in that hotspot before you can upgrade."));
                return;
            }
        } else if (currentlyBuilt != null) {
            player.dialogue(new MessageDialogue("You already have something built in this hotspot."));
            return;
        }
        player.closeInterfaces();
        //awkward..
        if (Buildable.isStairs(selected)) {
            Consumer<Boolean> action = (up) -> {
                RoomDefinition newDef = definition.getAlternateForm(up);
                definition = newDef;
                selected.removeMaterials(player);
                player.getStats().addXp(StatType.Construction, selected.getXP(), true);
                setBuilt(hotspotIndex, newDef.getHotspots()[hotspotIndex].getBuildables()[indexOfBuildable(hotspotIndex, selected)]);
                house.buildAndEnter(player, player.getPosition().localPosition(), true);
            };
            if (getChunkZ() == 0) {
                action.accept(true);
            } else if (getChunkZ() == 2) {
                action.accept(false);
            } else {
                player.dialogue(new OptionsDialogue("Where should the stairs lead?",
                        new Option("Up", () -> action.accept(true)),
                        new Option("Down", () -> action.accept(false))
                ));
            }
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(selected.getAnimation());
            event.delay(1);
            setBuilt(hotspotIndex, selected);
            renderHotspot(hotspotIndex);
            selected.removeMaterials(player);
            house.calculate();
            player.getStats().addXp(StatType.Construction, selected.getXP(), true);
            event.delay(1);
            player.unlock();
            onBuildableChanged(player, definition.getHotspots()[hotspotIndex], selected);
        });
    }

    public void renderHotspot(int hotspotIndex) {
        Buildable built = getBuilt(hotspotIndex);
        Hotspot hotspot = definition.getHotspots()[hotspotIndex];
        if (hotspot == Hotspot.EMPTY)
            return;
        if (built == null && !house.isBuildingMode()) { // nothing built but also not in building mode -> hide hotspot object
            objects.stream().filter(obj -> {
                for (int id : hotspot.getHotspotIds())
                    if (id == obj.id) {
                        return true;
                    }
                return false;
            }).forEach(obj -> removeHotspotObject(obj));
        } else if (built != null) { // something built! replace hotspot objects
            for (int i = 0; i < hotspot.getHotspotIds().length; i++) {
                final int index = i;
                objects.stream().
                        filter(obj -> obj.id == hotspot.getHotspotIds()[index] || obj.originalId == hotspot.getHotspotIds()[index])
                        .forEach(obj -> obj.setId(getHotspotReplacementId(hotspot, built, index)));
            }
        }
    }

    public void removeHotspotObject(GameObject obj) {
        obj.remove();
    }

    protected int getHotspotReplacementId(Hotspot hotspot, Buildable built, int index) {
        return built.getBuiltObjects()[index];
    }

    protected void clearBuilt(int hotspotIndex) {
        Hotspot spot = getDefinition().getHotspots()[hotspotIndex];
        objects.forEach(obj -> {
            for (int i = 0; i < spot.getHotspotIds().length; i++) {
                if (obj.originalId == spot.getHotspotIds()[i])
                    obj.restore();
            }
        });
        built[hotspotIndex] = null;
    }

    private void loadDefaultObjects(DynamicMap map) {
        objects = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int absX = map.swRegion.baseX + (chunk.pointX * 8) + x;
                int absY = map.swRegion.baseY + (chunk.pointY * 8) + y;
                List<GameObject> tileObjs = Tile.get(absX, absY, chunk.pointZ, true).gameObjects;
                if (tileObjs != null)
                    objects.addAll(tileObjs);
            }
        }
    }

    private void previewBuild(Player player, RoomDefinition def, Direction direction) {
        if (!house.canAddRoom(player, def, getChunkX() + direction.deltaX, getChunkY() + direction.deltaY, getChunkZ())) {
            return;
        }
        TempRoom tempRoom = new TempRoom(def, house.getMap().swRegion.baseX + ((getChunkX() + direction.deltaX) * 8), house.getMap().swRegion.baseY + ((getChunkY() + direction.deltaY) * 8));
        tempRoom.render(player);
        player.dialogue(new OptionsDialogue(
                new Option("Rotate clockwise", () -> {
                    tempRoom.rotate(player, true);
                    player.optionsDialogue.resend();
                }),
                new Option("Rotate anticlockwise", () -> {
                    tempRoom.rotate(player, false);
                    player.optionsDialogue.resend();
                }),
                new Option("Build", () -> {
                    int x = getChunkX() + direction.deltaX;
                    int y = getChunkY() + direction.deltaY;
                    if (!player.getInventory().contains(COINS_995, def.getCost())
                            || !house.canAddRoom(player, def, x, y, getChunkZ())) {
                        tempRoom.clear(player);
                        return;
                    }
                    player.getInventory().remove(COINS_995, def.getCost());
                    Room newRoom = def.create();
                    newRoom.rotation = tempRoom.rotation;
                    house.setRoom(x, y, chunk.pointZ, newRoom);
                    house.buildAndEnter(player, player.getPosition().localPosition(), true);
                }),
                new Option("Cancel", player::closeDialogue)
        ) {
            @Override
            public void closed(Player player) {
                tempRoom.clear(player);
            }
        });
    }


    public DynamicChunk makeChunk(HouseStyle style) {
        return chunk = definition.getChunk(style).rotate(rotation);
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public int getChunkX() {
        if (chunk == null)
            throw new IllegalStateException("chunk not set");
        return pointX = chunk.pointX;
    }

    public int getChunkY() {
        if (chunk == null)
            throw new IllegalStateException("chunk not set");
        return pointY = chunk.pointY;
    }

    public int getChunkZ() {
        if (chunk == null)
            throw new IllegalStateException("chunk not set");
        return pointZ = chunk.pointZ;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public Position getAbsolutePosition(int xInChunk, int yInChunk) {
        int rotatedX = DynamicChunk.rotatedX(xInChunk, yInChunk, rotation);
        int rotatedY = DynamicChunk.rotatedY(xInChunk, yInChunk, rotation);
        return new Position(house.getMap().swRegion.baseX + (getChunkX() * 8) + rotatedX,
                house.getMap().swRegion.baseY + (getChunkY() * 8) + rotatedY,
                getChunkZ());
    }

    public Position getLocalPosition(int x, int y, int roomX, int roomY, int roomZ) { // local to region
        return new Position((roomX * 8) + DynamicChunk.rotatedX(x, y, rotation), (roomY * 8) + DynamicChunk.rotatedY(x, y, rotation), roomZ);
    }

    public Position getBaseAbsolutePosition() {
        return basePosition;
    }

    public House getHouse() {
        return house;
    }

    public void assembleFlatpack(Player player, Flatpack f, Item item, Hotspot hotspot) {
        player.animate(f.getBuildable().getAnimation());
        item.remove();
        int index = player.getCurrentRoom().getHotspotIndex(hotspot);
        setBuilt(index, f.getBuildable());
        renderHotspot(index);
        player.house.calculate();
        player.sendMessage("You assemble the flatpack.");
    }

    private static final class TempRoom {

        private int rotation;

        private RoomDefinition type;

        private int baseX; // abs

        private int baseY;
        private TempRoom(RoomDefinition type, int baseX, int baseY) {
            this.baseX = baseX;
            this.baseY = baseY;
            this.type = type;
        }
        private void render(Player player) {
            for(RoomDefinition.VisualObject obj : type.getVisualObjects()) {
                int x = baseX + obj.x;
                int y = baseY + obj.y;
                player.getPacketSender().sendCreateObject(obj.id, x, y, player.getHeight(), obj.type, obj.direction);
            }
        }

        private void rotate(Player player, boolean clockwise) {
            if(clockwise)
                rotation++;
            else
                rotation--;
            rotation &= 0x3;
            clear(player);
            for(RoomDefinition.VisualObject obj : type.getVisualObjects()) {
                ObjectDef def = ObjectDef.get(obj.id);
                int x = baseX + DynamicChunk.rotatedX(obj.x, obj.y, rotation, def.xLength, def.yLength, obj.direction);
                int y = baseY + DynamicChunk.rotatedY(obj.x, obj.y, rotation, def.xLength, def.yLength, obj.direction);
                player.getPacketSender().sendCreateObject(obj.id, x, y, player.getHeight(), obj.type, (obj.direction + rotation) & 0x3);
            }
        }

        private void clear(Player player) {
            player.getPacketSender().clearChunk(baseX, baseY);
        }



    }
    public static boolean onDoorLocation(GameObject obj) {
        int x = obj.x & 7;
        int y = obj.y & 7;
        return ((x == 0 || x == 7) && (y == 3 || y == 4))
                || ((y == 0 || y == 7) && (x == 3 || x == 4));
    }

    public void enableChallengeMode() {

    }

    public void disableChallengeMode() {

    }

}
