package io.ruin.model.activities.duelarena;

import io.ruin.api.utils.ListUtils;
import io.ruin.model.entity.player.Player;

import java.util.List;

public enum DuelRule {

    NO_RANGED("No Ranged", 4, 41, 54),
    NO_MELEE("No Melee", 5, 42, 55),
    NO_MAGIC("No Magic", 6, 43, 56),
    NO_SPECIALS("No Special Attack", 13, 49, 62),
    FUN_WEAPONS("Fun weapons", 12, 48, 61),
    NO_FORFEIT("Forfeit", 0, 37, 50),
    NO_PRAYER("No Prayer", 9, 46, 59),
    NO_DRINKS("No Drinks", 7, 44, 57),
    NO_FOOD("No Food", 8, 45, 58),
    NO_MOVEMENT("No Movement", 1, 38, 51),
    OBSTACLES("Obstacles", 10, 47, 60),
    NO_WEAPON_SWITCH("No Weapon Switching", 2, 52, 39),
    SHOW_INVENTORIES("Show Inventories", 3, 40, 53),

    NO_HELMS("Disable Head Slot", 14, 69),
    NO_CAPES("Disable Back Slot", 15, 70),
    NO_AMULETS("Disable Neck Slot", 16, 71),
    NO_AMMO("Disable Ammo Slot", 27, 79),
    NO_WEAPON("Disable Right Hand Slot", 17, 72),
    NO_BODY("Disable Torso Slot", 18, 73),
    NO_SHIELD("Disable Left Hand Slot", 19, 74),
    NO_LEGS("Disable Leg Slot", 21, 75),
    NO_RING("Disable Ring Slot", 26, 78),
    NO_BOOTS("Disable Feet Slot", 24, 77),
    NO_GLOVES("Disable Hand Slot", 23, 76);

    public final String message;

    public final int bitValue, bitPos;

    private final List<Integer> childIds;

    DuelRule(String message, int bitPos, Integer... childIds) {
        this.message = message;
        this.bitPos = bitPos;
        this.bitValue = 1 << bitPos;
        this.childIds = ListUtils.toList(childIds);
    }

    public boolean isToggled(Player player) {
        Duel duel = player.getDuel();
        return duel.stage >= 3 && duel.isToggled(this);
    }

    public static DuelRule get(int childId) {
        for(DuelRule rule : values()) {
            if(rule.childIds.contains(childId))
                return rule;
        }
        return null;
    }

}
