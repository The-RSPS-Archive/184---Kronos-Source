package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;

public class SuperheatItem extends Spell {

    public SuperheatItem() {
        Item[] runes = {
                Rune.FIRE.toItem(4),
                Rune.NATURE.toItem(1),
        };
        registerItem(43, 53, true, runes, (player, item) -> {
            if (player.superheatDelay.isDelayed()) {
                player.startEvent(event -> {
                    event.delay(player.superheatDelay.remaining());
                    itemAction.accept(player, item);
                });
                return false;
            }

            if (item.getId() == 453) {
                player.sendMessage("You must cast this spell on ore, not coal.");
                return false;
            }

            SmithBar bar = null;
            l1:
            for (SmithBar b : SmithBar.values()) {
                for (Item i : b.smeltItems)
                    if (i.getId() == item.getId()) {
                        bar = b;
                        break l1;
                    }
            }

            if (bar == null) {
                player.animate(722);
                player.graphics(85, 10, 100);
                player.sendMessage("You must cast this spell on ore.");
                return false;
            }

            if (bar == SmithBar.IRON && player.getStats().get(StatType.Smithing).currentLevel >= SmithBar.STEEL.smeltLevel && player.getInventory().contains(453, 1))
                bar = SmithBar.STEEL;

            if (!bar.smeltItems.stream().allMatch(i -> player.getInventory().contains(i))) {
                player.sendMessage("You don't have enough coal to superheat that ore.");
                return false;
            }

            if (!player.getStats().check(StatType.Smithing, bar.smeltLevel, "superheat that ore")) {
                return false;
            }

            bar.smeltItems.forEach(i -> player.getInventory().remove(i.getId(), i.getAmount()));

            double smeltXp = bar.smeltXp;
            if (bar == SmithBar.GOLD && player.getEquipment().hasId(776)) {
                smeltXp *= 2.5;
            }

            player.getInventory().add(bar.itemId, 1);
            player.getStats().addXp(StatType.Smithing, smeltXp, true);
            player.sendMessage("You superheat the ore, smelting it.");
            player.superheatDelay.delay(3);
            player.animate(722);
            player.graphics(148, 0, 0);
            player.getPacketSender().sendClientScript(915, "i", 6);
            return true;
        });
    }

}
