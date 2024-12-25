package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.item.EquipmentCheck;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class Charge extends Spell {

    public static EquipmentCheck capeCheck = new EquipmentCheck("You must have a mage arena cape equipped to cast this spell.", 2412, 2413, 2414, 21791, 21793, 21795);

    public Charge() {
        Item[] runes = {
                Rune.FIRE.toItem(3),
                Rune.BLOOD.toItem(3),
                Rune.AIR.toItem(3)
        };
        registerClick(80, 180, false, runes, (p, i) -> p.getCombat().charge());
    }

}