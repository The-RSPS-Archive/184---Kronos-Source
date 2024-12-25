package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Containers;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class Humidify extends Spell {

    private static final int CLAY = 434;
    private static final int SOFT_CLAY = 1761;

    public Humidify() {
        Item[] runes = {
                Rune.ASTRAL.toItem(1),
                Rune.WATER.toItem(3),
                Rune.FIRE.toItem(1)
        };
        registerClick(68, 68, true, runes, (p, i) -> {
            int count = 0;
            for(Item item : p.getInventory().getItems()) {
                if (item != null) {
                    /* Fill up our water containers */
                    for(Containers containers : Containers.values()) {
                        if(item.getId() == containers.empty) {
                            item.setId(containers.full);
                            count++;
                        }
                    }

                    /* Turn any clay into soft clay */
                    if(item.getId() == CLAY) {
                        item.setId(SOFT_CLAY);
                        count++;
                    }
                }
            }
            if(count > 0) {
                p.startEvent(e -> {
                    p.lock();
                    p.animate(6294);
                    p.graphics(1061, 72, 0);
                    e.delay(4);
                    p.unlock();
                });
                return true;
            }

            p.sendMessage("You have nothing in your inventory that this spell can humidify.");
            return false;
        });
    }

}