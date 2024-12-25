package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

public class EquipmentStats {

    public static final int STAB_ATTACK = 0, SLASH_ATTACK = 1, CRUSH_ATTACK = 2, MAGIC_ATTACK = 3, RANGE_ATTACK = 4;

    public static final int STAB_DEFENCE = 5, SLASH_DEFENCE = 6, CRUSH_DEFENCE = 7, MAGIC_DEFENCE = 8, RANGE_DEFENCE = 9;

    public static final int MELEE_STRENGTH = 10, RANGED_STRENGTH = 11, MAGIC_DAMAGE = 12, PRAYER = 13;

    public static final int UNDEAD = 14, SLAYER = 15;

    public static final String[] STAT_NAMES = { "Stab Attack", "Slash Attack", "Crush Attack", "Magic Attack", "Range Attack",
            "Stab Defence", "Slash Defence", "Crush Defence", "Magic Defence", "Range Defence",
            "Melee Strength", "Ranged Strength", "Magic Damage", "Prayer", "Undead", "Slayer"};

    public static String getNameForIndex(int index) {
        return STAT_NAMES[index];
    }

    public static void open(Player player) {
        player.getMovement().reset();
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, Interface.EQUIPMENT_STATS);
        player.openInterface(InterfaceType.INVENTORY, Interface.EQUIPMENT_STATS_INVENTORY);
        player.getPacketSender().sendClientScript(149, "IviiiIsssss", 5570560, 93, 4, 7, 1, -1, "Equip", "", "", "", "");
        player.getPacketSender().sendAccessMask(Interface.EQUIPMENT_STATS_INVENTORY, 0, 0, 27, 1180674);
        update(player);
    }

    public static void update(Player player) {
        int i = 0;
        int[] bonuses = player.getEquipment().bonuses;
        player.getPacketSender().sendString(84, 23, "Stab: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 24, "Slash: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 25, "Crush: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 26, "Magic: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 27, "Range: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 29, "Stab: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 30, "Slash: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 31, "Crush: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 32, "Magic: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 33, "Range: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 35, "Melee strength: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 36, "Ranged strength: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 37, "Magic damage: " + asBonus(bonuses[i++], true));
        player.getPacketSender().sendString(84, 38, "Prayer: " + asBonus(bonuses[i++], false));
        player.getPacketSender().sendString(84, 40, "Undead: " + asBonus(bonuses[i++], true));
        player.getPacketSender().sendString(84, 41, "Slayer: " + asBonus(bonuses[i], true));
        player.getPacketSender().sendWeight((int) (player.getInventory().weight + player.getEquipment().weight));
    }

    public static String asBonus(int value, boolean percent) {
        String s = (value >= 0 ? "+" : "") + value;
        if(percent)
            s += "%";
        return s;
    }

    /**
     * Handler
     */

    static {
        InterfaceHandler.register(Interface.EQUIPMENT_STATS, h -> {
            h.actions[11] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_HAT);
            h.actions[12] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_CAPE);
            h.actions[13] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_AMULET);
            h.actions[14] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_WEAPON);
            h.actions[15] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_CHEST);
            h.actions[16] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_SHIELD);
            h.actions[17] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_LEGS);
            h.actions[18] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_HANDS);
            h.actions[19] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_FEET);
            h.actions[20] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_RING);
            h.actions[21] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_AMMO);
        });
        InterfaceHandler.register(Interface.EQUIPMENT_STATS_INVENTORY, h -> {
            h.actions[0] = (DefaultAction) (player, option, slot, itemId) -> {
                Item item = player.getInventory().get(slot, itemId);
                if(item == null)
                    return;
                if(option == 1)
                    player.getEquipment().equip(item);
                else
                    item.examine(player);
            };
        });
    }

}
