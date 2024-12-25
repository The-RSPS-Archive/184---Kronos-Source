package io.ruin.model.skills.construction.actions;


import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;

import java.util.Arrays;
import java.util.Map;

public class CostumeRoom {

    static {
        for (Buildable b : Hotspot.FANCY_DRESS_BOX.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.FANCY_DRESS_BOX.open(player));
            ItemObjectAction.register(open, (player, item, obj) -> depositCostume(player, item, b, CostumeStorage.FANCY_DRESS_BOX));
            b.setRemoveTest((player, room) -> checkStorageEmpty(player, CostumeStorage.FANCY_DRESS_BOX));
        }

        for (Buildable b : Hotspot.TOY_BOX.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.TOY_BOX_1.open(player));
            ItemObjectAction.register(open, (player, item, obj) -> depositCostume(player, item, b, CostumeStorage.TOY_BOX_1, CostumeStorage.TOY_BOX_2));
            b.setRemoveTest((player, room) -> checkStorageEmpty(player, CostumeStorage.TOY_BOX_1));
        }

        for (Buildable b :Hotspot.ARMOUR_CASE.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.ARMOUR_CASE.open(player));
            ItemObjectAction.register(open, (player, item, obj) -> depositCostume(player, item, b, CostumeStorage.ARMOUR_CASE));
            b.setRemoveTest((player, room) -> checkStorageEmpty(player, CostumeStorage.ARMOUR_CASE));

        }

        for (Buildable b :Hotspot.MAGIC_WARDROBE.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.MAGIC_WARDROBE.open(player));
            ItemObjectAction.register(open, (player, item, obj) -> depositCostume(player, item, b, CostumeStorage.MAGIC_WARDROBE));
            b.setRemoveTest((player, room) -> checkStorageEmpty(player, CostumeStorage.MAGIC_WARDROBE));

        }

        for (Buildable b : Hotspot.CAPE_RACK.getBuildables()) {
            int open = b.getBuiltObjects()[0];
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.CAPE_RACK.open(player));
            ItemObjectAction.register(open, (player, item, obj) -> depositCostume(player, item, b, CostumeStorage.CAPE_RACK));
            b.setRemoveTest((player, room) -> checkStorageEmpty(player, CostumeStorage.CAPE_RACK));

        }

        for (Buildable b : Hotspot.TREASURE_CHEST.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            b.setRemoveTest((player, room) -> checkStorageEmpty(player, CostumeStorage.EASY_TREASURE_TRAILS, CostumeStorage.MEDIUM_TREASURE_TRAILS, CostumeStorage.HARD_TREASURE_TRAILS_1, CostumeStorage.ELITE_TREASURE_TRAILS, CostumeStorage.MASTER_TREASURE_TRAILS));

        }
        ObjectAction.register(18805, "search", (player, obj) -> CostumeStorage.EASY_TREASURE_TRAILS.open(player));
        ItemObjectAction.register(18805, (player, item, obj) -> depositCostume(player, item, Buildable.OAK_TREASURE_CHEST, CostumeStorage.EASY_TREASURE_TRAILS));

        ObjectAction.register(18807, "search", (player, obj) -> player.dialogue(new OptionsDialogue(
                new Option("Easy treasure trails", () -> CostumeStorage.EASY_TREASURE_TRAILS.open(player)),
                new Option("Medium treasure trails", () -> CostumeStorage.MEDIUM_TREASURE_TRAILS.open(player))
        )));
        ItemObjectAction.register(18807, (player, item, obj) -> depositCostume(player, item, Buildable.TEAK_TREASURE_CHEST, CostumeStorage.EASY_TREASURE_TRAILS, CostumeStorage.MEDIUM_TREASURE_TRAILS));

        ObjectAction.register(18809, "search", (player, obj) -> player.dialogue(new OptionsDialogue(
                new Option("Easy treasure trails", () -> CostumeStorage.EASY_TREASURE_TRAILS.open(player)),
                new Option("Medium treasure trails", () -> CostumeStorage.MEDIUM_TREASURE_TRAILS.open(player)),
                new Option("Hard treasure trails", () -> CostumeStorage.HARD_TREASURE_TRAILS_1.open(player)),
                new Option("Elite treasure trails", () -> CostumeStorage.ELITE_TREASURE_TRAILS.open(player)),
                new Option("Master treasure trails", () -> CostumeStorage.MASTER_TREASURE_TRAILS.open(player))
        )));
        ItemObjectAction.register(18809, (player, item, obj) -> depositCostume(player, item, Buildable.MAHOGANY_TREASURE_CHEST, CostumeStorage.EASY_TREASURE_TRAILS, CostumeStorage.MEDIUM_TREASURE_TRAILS, CostumeStorage.HARD_TREASURE_TRAILS_1, CostumeStorage.HARD_TREASURE_TRAILS_2, CostumeStorage.ELITE_TREASURE_TRAILS, CostumeStorage.MASTER_TREASURE_TRAILS));

    }

    private static void open(Player player, GameObject obj, int open) {
        player.animate(536);
        obj.setId(open);
    }

    private static void close(Player player, GameObject obj, int closed) {
        player.animate(535);
        obj.setId(closed);
    }

    private static boolean checkStorageEmpty(Player player, CostumeStorage... types) {
        for (CostumeStorage type : types) {
            Map<Costume, int[]> stored = type.getSets(player);
            if (stored.size() > 0) {
                player.sendMessage("You must take all the items from inside before you can remove it.");
                return false;
            }
        }
        return true;
    }

    static {
        InterfaceHandler.register(Interface.CONSTRUCTION_COSTUME_STORAGE, h -> {
            h.actions[2] = (SlotAction) CostumeRoom::withdrawCostume;
        });
    }

    private static void withdrawCostume(Player player, int slot) {
        CostumeStorage type = player.get("COSTUME_STORAGE");
        if (type == null)
            return;
        slot /= 4;
        if (slot < 0 || slot > type.getCostumes().length) {
            throw new IllegalArgumentException(""+slot);
        }
        if (type.display[slot].getId() == 10165) { // more...
            if (type == CostumeStorage.HARD_TREASURE_TRAILS_1) {
                CostumeStorage.HARD_TREASURE_TRAILS_2.open(player);
            } else if (type == CostumeStorage.TOY_BOX_1) {
                CostumeStorage.TOY_BOX_2.open(player);
            }
            return;
        } else if (type.display[slot].getId() == 10166) {
            if (type == CostumeStorage.HARD_TREASURE_TRAILS_2) {
                CostumeStorage.HARD_TREASURE_TRAILS_1.open(player);
            } else if (type == CostumeStorage.TOY_BOX_2) {
                CostumeStorage.TOY_BOX_1.open(player);
            }
            return;
        }
        if (type.display[0].getId() == 10166) // back...
            slot--;
        Map<Costume, int[]> storedSets = type.getSets(player);
        if (storedSets == null)
            throw new IllegalArgumentException();
        Costume costume = type.getCostumes()[slot];
        int[] stored = storedSets.get(costume);
        if (stored == null) {
            return;
        }
        if (!player.getInventory().hasFreeSlots(stored.length)) {
            player.sendMessage("You'll need at least " + stored.length + " free inventory slots to withdraw that costume.");
            return;
        }
        for (int id : stored) player.getInventory().add(id, 1);
        storedSets.remove(costume);
        player.closeInterfaces();
    }

    private static void depositCostume(Player player, Item item, Buildable b, CostumeStorage... validTypes) {
        CostumeStorage type = null;
        Costume costume = null;
        for (CostumeStorage cs : CostumeStorage.values()) {
            costume = cs.getByItem(item.getId());
            if (costume != null) {
                type = cs;
                break;
            }
        }
        if (costume == null || !Arrays.asList(validTypes).contains(type)) {
            player.sendMessage("You can't store that item there.");
            return;
        }
        for (int[] piece : costume.pieces) {
            if (Arrays.stream(piece).anyMatch(option -> player.getInventory().contains(option, 1))) {
                costume.sendRequiredItems(player);
                return;
            }
        }
        int maxStored = getMaxStorage(b);
        int currentStored = type.countSpaceUsed(player);
        if (currentStored >= maxStored) {
            if (type == CostumeStorage.CAPE_RACK) {
                player.sendMessage("That cape rack can only hold up to " + maxStored + " capes of accomplishment.");
            } else {
                player.sendMessage("There's no more space in there.");
            }
            return;
        }
        int[] pieces = new int[costume.pieces.length];
        piece_loop:
        for (int i = 0; i < pieces.length; i++) {
            int[] piece = costume.pieces[i];
            for (int option : piece) {
                if (player.getInventory().remove(option, 1) > 0) {
                    pieces[i] = option;
                    continue piece_loop;
                }
            }
        }
        type.getSets(player).put(costume, pieces);
        player.sendMessage("You place the costume in the treasure chest.");

    }

    public static int getMaxStorage(Buildable b) {
        switch (b) {
            case OAK_ARMOUR_CASE:
                return 2;
            case TEAK_ARMOUR_CASE:
                return 4;

            case OAK_MAGIC_WARDROBE:
                return 1;
            case CARVED_OAK_MAGIC_WARDROBE:
                return 2;
            case TEAK_MAGIC_WARDROBE:
                return 3;
            case CARVED_TEAK_MAGIC_WARDROBE:
                return 4;
            case MAHOGANY_MAGIC_WARDROBE:
                return 5;
            case GILDED_MAGIC_WARDROBE:
                return 6;

            case OAK_CAPE_RACK:
                return 0;
            case TEAK_CAPE_RACK:
                return 1;
            case MAHOGANY_CAPE_RACK:
                return 5;
            case GILDED_CAPE_RACK:
                return 10;

            case OAK_FANCY_DRESS_BOX:
                return 2;
            case TEAK_FANCY_DRESS_BOX:
                return 4;

            default:
                return Integer.MAX_VALUE;
        }
    }

}
