package io.ruin.model.inter.journal.presets.pure;

import io.ruin.model.inter.journal.presets.Preset;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.SpellBook;

public class PureMelee extends Preset {

    public PureMelee() {
        free = true;
        name = "<img=49> Melee";
        attack = level(75);
        strength = level(99);
        defence = level(1);
        ranged = level(99);
        prayer = level(52);
        magic = level(99);
        hitpoints = level(99);
        spellBook = SpellBook.MODERN;
        invItems[0] = new Item(SUPER_ATTACK_POTION_4, 1);
        invItems[1] = new Item(SARADOMIN_BREW_4, 1);
        invItems[2] = new Item(SUPER_RESTORE_POTION_4, 1);
        invItems[3] = new Item(TELEPORT_HOME, 5);
        invItems[4] = new Item(SUPER_STRENGTH_POTION_4, 1);
        invItems[5] = new Item(COOKED_KARAMBWAN, 1);
        invItems[6] = new Item(SUPER_RESTORE_POTION_4, 1);
        invItems[7] = new Item(SHARK, 1);
        invItems[8] = new Item(COOKED_KARAMBWAN, 1);
        invItems[9] = new Item(SHARK, 1);
        invItems[10] = new Item(COOKED_KARAMBWAN, 1);
        invItems[11] = new Item(SHARK, 1);
        invItems[12] = new Item(SHARK, 1);
        invItems[13] = new Item(COOKED_KARAMBWAN, 1);
        invItems[14] = new Item(SHARK, 1);
        invItems[15] = new Item(COOKED_KARAMBWAN, 1);
        invItems[16] = new Item(SHARK, 1);
        invItems[17] = new Item(SHARK, 1);
        invItems[18] = new Item(SHARK, 1);
        invItems[19] = new Item(SHARK, 1);
        invItems[20] = new Item(SHARK, 1);
        invItems[21] = new Item(DRAGON_DAGGERP, 1);
        invItems[22] = new Item(SHARK, 1);
        invItems[23] = new Item(SHARK, 1);
        invItems[24] = new Item(SHARK, 1);
        invItems[25] = new Item(SHARK, 1);
        invItems[26] = new Item(SHARK, 1);
        invItems[27] = new Item(DRAGON_2H_SWORD, 1);
        equipItems[Equipment.SLOT_HAT] = new Item(IRON_FULL_HELM, 1);
        equipItems[Equipment.SLOT_WEAPON] = new Item(DRAGON_SCIMITAR, 1);
        equipItems[Equipment.SLOT_AMULET] = new Item(STRENGTH_AMULET, 1);
        equipItems[Equipment.SLOT_SHIELD] = new Item(UNHOLY_BOOK, 1);
        equipItems[Equipment.SLOT_FEET] = new Item(CLIMBING_BOOTS, 1);
        equipItems[Equipment.SLOT_HANDS] = new Item(MITHRIL_GLOVES, 1);
        equipItems[Equipment.SLOT_LEGS] = new Item(IRON_PLATELEGS, 1);
        equipItems[Equipment.SLOT_CAPE] = new Item(ZAMORAK_CAPE, 1);
        equipItems[Equipment.SLOT_CHEST] = new Item(IRON_PLATEBODY, 1);
        equipItems[Equipment.SLOT_RING] = new Item(RING_OF_RECOIL, 1);
    }

}