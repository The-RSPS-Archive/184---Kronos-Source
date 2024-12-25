package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

public class BakePie extends Spell {

    private enum Pie {

        REDBERRY_PIE(10, 78.0, 2321, 2325),
        PIE_MEAT(20, 104.0, 2317, 2327),
        MUD_PIE(29, 128.0, 2319, 7170),
        APPLE_PIE(30, 130.0, 7168, 2323),
        GARDEN_PIE(34, 128.0, 7186, 7188),
        FISH_PIE(47, 164.0, 7186, 7188),
        ADMIRAL_PIE(70, 210.0, 7196, 7198),
        WILD_PIE(85, 240.0, 7206, 7208),
        SUMMER_PIE(95, 260.0, 7216, 7218);

        private int levelReq, raw, baked;
        private double exp;

        Pie(int levelReq, double exp, int raw, int baked) {
            this.levelReq = levelReq;
            this.exp = exp;
            this.raw = raw;
            this.baked = baked;
        }
    }

    public BakePie() {
        Item[] runes = {
                Rune.ASTRAL.toItem(4),
                Rune.WATER.toItem(5),
                Rune.FIRE.toItem(1)
        };
        clickAction = (p, i) -> {
            if (!p.getStats().check(StatType.Magic, 65, "cast this spell"))
                return;
            p.startEvent(e -> {
                int count = 0;
                for (Item item : p.getInventory().getItems()) {
                    for (Pie pie : Pie.values()) {
                        if (item != null && item.getId() == pie.raw) {
                            if (p.getStats().get(StatType.Cooking).currentLevel < pie.levelReq) {
                                continue;
                            }
                            RuneRemoval r = null;
                            if (runes != null && (r = RuneRemoval.get(p, runes)) == null) {
                                p.sendMessage("You don't have enough runes to cast this spell.");
                                break;
                            }
                            item.setId(pie.baked);
                            p.animate(4413);
                            p.graphics(746, 96, 0);
                            p.publicSound(2879);
                            r.remove();
                            p.getStats().addXp(StatType.Cooking, pie.exp, true);
                            p.getStats().addXp(StatType.Magic, 60, true);
                            e.delay(3);
                            count++;
                        }
                    }
                }
                if (count == 0)
                    p.sendMessage("You don't have any pies to bake.");
            });
            return;
        };
    }

}