package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

import java.util.List;

public class SuperglassMake extends Spell {

    public SuperglassMake() {
        Item[] runes = {
                Rune.ASTRAL.toItem(2),
                Rune.FIRE.toItem(6),
                Rune.AIR.toItem(10)
        };
        registerClick(77, 78, true, runes, (p, i) -> {
            List<Item> items = p.getInventory().collectItems(1783);
            if (items == null || items.size() == 0) {
                p.sendMessage("You don't have any sand to turn into glass.");
                return false;
            }
            p.startEvent(event -> {
                p.lock();
                p.animate(4413);
                p.graphics(729, 96, 0);
                items.forEach(item -> item.setId(1775));
                p.getStats().addXp(StatType.Crafting, 8 * items.size(), true);
                event.delay(1);
                p.unlock();
            });
            return true;
        });
    }
}
