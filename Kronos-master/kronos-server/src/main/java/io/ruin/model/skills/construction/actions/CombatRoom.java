package io.ruin.model.skills.construction.actions;

import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MultiZone;
import io.ruin.model.map.dynamic.DynamicChunk;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.*;
import io.ruin.model.skills.construction.room.Room;

import java.util.Arrays;

public class CombatRoom {

    static { // mark room template as multi
        int baseX = RoomDefinition.COMBAT_ROOM.getChunkX();
        int baseY = RoomDefinition.COMBAT_ROOM.getChunkY();
        for (HouseStyle style : HouseStyle.values()) {
            int x = (baseX + style.changeX) * 8;
            int y = baseY * 8;
            int z = style.changeZ;
            MultiZone.add(new Bounds(x,y,x+7,y+7, z));
        }
    }

    static {
        //Enter
        for (Buildable b : Arrays.asList(Buildable.BOXING_RING, Buildable.FENCING_RING, Buildable.COMBAT_RING)) { // climb over
            int id = b.getBuiltObjects()[0];
            ObjectAction.register(id, 1, (player, obj) -> {
                int dir = obj.direction;
                boolean entering = true;
                if (inArena(player, Buildable.COMBAT_RING)) {
                    dir = (dir + 2) & 3;
                    entering = false;
                }
                if (entering && !canEnter(player, b))
                    return;
                Direction direction = Direction.fromDoorDirection(dir);
                boolean finalEntering = entering;
                player.startEvent(event -> {
                    player.lock();
                    if (finalEntering)
                        player.getRouteFinder().routeAbsolute(obj.x, obj.y);
                    else
                        player.getRouteFinder().routeAbsolute(obj.x + direction.deltaX, obj.y + direction.deltaY);
                    event.waitForMovement(player);
                    event.delay(1);
                    player.getMovement().force(0, 0, direction.deltaX, direction.deltaY, 40, 10, direction);
                    player.animate(3688);
                    event.delay(1);
                    if (finalEntering)
                        player.setAction(1, PlayerAction.ATTACK);
                    else
                        player.setAction(1, null);
                    player.unlock();
                });
            });
        }
    }

    private static boolean canEnter(Player player, Buildable arena) {
        if (arena == Buildable.BOXING_RING || arena == Buildable.FENCING_RING) {
            for (int slot = 0; slot < 14; slot++) {
                if (slot == 3) continue;
                if (player.getEquipment().getId(slot) != -1) {
                    player.sendMessage("Armour and jewellery are not allowed in this ring!");
                    return false;
                }
            }
        }
        if (arena == Buildable.BOXING_RING) {
            int weapon = player.getEquipment().getId(Equipment.SLOT_WEAPON);
            if (weapon != -1 && weapon != 7671 && weapon != 7673){
                player.sendMessage("You can only fight in this ring while unarmed or using boxing gloves.");
                return false;
            }
        }
        return true;
    }

    public static boolean canAttack(Player p, Player pTarget, boolean message) {
        House house = p.getCurrentHouse();
        Room room = p.getCurrentRoom();
        if (house == null || room == null) // ?
            return false;
        Buildable arena = room.getBuilt(Hotspot.COMBAT_RING);
        return inArena(p, arena) && inArena(pTarget, arena) || house.canAttackChallengeMode(p, pTarget, message);
    }

    private static boolean inArena(Player player, Buildable arena) {
        switch (arena) {
            case BOXING_RING:
            case FENCING_RING:
            case COMBAT_RING:
                return inRing(player);
            case RANGING_PEDESTALS:
                return inRangingPedestals(player);
            default:
                return false;
        }
    }

    public static boolean handleDeath(Player player) {
        if (player.getCurrentRoom() != null && player.getCurrentRoom().getDefinition() == RoomDefinition.COMBAT_ROOM && inArena(player, player.getCurrentRoom().getBuilt(Hotspot.COMBAT_RING))) {
            player.getMovement().teleport((player.getAbsX() & ~7) + DynamicChunk.rotatedX(2, 1, player.getCurrentRoom().getRotation()),
                    (player.getAbsY() & ~7) + DynamicChunk.rotatedY(2, 1, player.getCurrentRoom().getRotation()), player.getHeight());
            player.setAction(1, null);
            return true;
        }
        return false;
    }

    private static boolean inRing(Player player) {
        int x = player.getAbsX() & 7;
        int y = player.getAbsY() & 7;
        return x >= 2 && x <= 5 && y >= 2 && y <= 5;
    }

    private static boolean inRangingPedestals(Player player) {
        if (player.getCurrentRoom() == null)
            return false;
        int x = player.getAbsX() & 7;
        int y = player.getAbsY() & 7;
        return (x == DynamicChunk.rotatedX(5, 2, player.getCurrentRoom().getRotation()) && y == DynamicChunk.rotatedY(5, 2, player.getCurrentRoom().getRotation()))
                || (x == DynamicChunk.rotatedX(2, 5, player.getCurrentRoom().getRotation()) && y == DynamicChunk.rotatedY(2, 5, player.getCurrentRoom().getRotation()));
    }

    public static boolean allowEquip(Player player, ItemDef selectedDef) {
        if (player.getCurrentRoom() == null || player.getCurrentRoom().getDefinition() != RoomDefinition.COMBAT_ROOM || player.getCurrentRoom().getBuilt(Hotspot.COMBAT_RING) == null)
            return true;
        Buildable arena = player.getCurrentRoom().getBuilt(Hotspot.COMBAT_RING);
        if (arena == null || !inArena(player, arena))
            return true;
        if (arena == Buildable.BOXING_RING) {
            if (selectedDef.equipSlot != 3 || (selectedDef.id != 7671 && selectedDef.id != 7673)) {
                player.sendMessage("You cannot use weapons or armour in the boxing ring, except for boxing gloves.");
                return false;
            }
        }
        if (arena == Buildable.BOXING_RING || arena == Buildable.FENCING_RING) {
            if (selectedDef.equipSlot != 3) {
                player.sendMessage("You cannot equip armour in this ring!");
                return false;
            }
        }
        return true;
    }

    //static dummies
    enum Dummy {
        REGULAR(Buildable.COMBAT_DUMMY, 2668),
        UNDEAD(Buildable.UNDEAD_COMBAT_DUMMY, 7413);

        Dummy(Buildable b, int npcId) {
            objId = b.getBuiltObjects()[0];
            this.npcId = npcId;
        }

        int objId;
        int npcId;

        static {
            for (Dummy dummy : values()) {
                ObjectAction.register(dummy.objId, "attach", (player, obj) -> {
                    House house = player.getCurrentHouse();
                    if (house == null || obj.get("DUMMY_REMOVED") != null) {
                        return;
                    }
                    obj.set("DUMMY_REMOVED", true);
                    NPC npc = new NPC(dummy.npcId);
                    house.addNPC(npc.spawn(obj.x, obj.y, obj.z, Direction.getFromObjectDirection(obj.direction), 0));
                    World.startEvent(event -> { // since the npc will only become visible to the player in the next tick, wait 1 tick to remove the object too
                        event.delay(1);
                        obj.remove();
                        obj.remove("DUMMY_REMOVED");
                    });
                    NPCAction.register(npc, "detach", (p, n) -> {
                        n.remove();
                        obj.setId(dummy.objId);
                    });
                    NPCAction.register(npc, "upgrade", (p, n) -> player.sendMessage("You must detach the dummy before upgrading it."));
                    NPCAction.register(npc, "remove", (p, n) -> player.sendMessage("You must detach the dummy before removing it."));
                });
            }
        }
    }

    static { // rack
        ItemDispenser.register(Buildable.GLOVE_RACK, 7671, 7673);
        ItemDispenser.register(Buildable.WEAPONS_RACK, 7671, 7673, 7675, 7676);
        ItemDispenser.register(Buildable.EXTRA_WEAPONS_RACK, 7671, 7673, 7675, 7676, 7679);

    }

}
