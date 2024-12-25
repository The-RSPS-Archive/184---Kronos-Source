package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public class SpiritShield {

    private static final int HOLY_ELIXIR = 12833;
    private static final int SPIRIT_SHIELD = 12829;
    private static final int BLESSED_SPIRIT_SHIELD = 12831;

    private static final int ARCANE_SIGIL = 12827;
    private static final int SPECTRAL_SIGIL = 12823;
    private static final int ELYSIAN_SIGIL = 12819;
    private static final int DIVINE_SIGIL = 30187;

    static {
        /**
         * Holy spirit shield
         */
        ItemItemAction.register(HOLY_ELIXIR, SPIRIT_SHIELD, (player, primary, secondary) -> {
            primary.remove();
            secondary.remove();
            player.getInventory().add(BLESSED_SPIRIT_SHIELD, 1);
            player.dialogue(new ItemDialogue().one(BLESSED_SPIRIT_SHIELD, "The spirit shield glows an eerie holy glow."));
        });

        /*
         * Sigils attaching to spirit shield
         */
        int[] sigils = {ARCANE_SIGIL, SPECTRAL_SIGIL, ELYSIAN_SIGIL, DIVINE_SIGIL};
        for (int itemID : sigils) {
            ItemObjectAction.register(itemID, "anvil", (player, item, obj) -> {
                if(player.getStats().get(StatType.Prayer).currentLevel < 90 || player.getStats().get(StatType.Smithing).currentLevel < 85) {
                    player.sendMessage("You don't have the skills required to make this. You need 85 Smithing and 90 Prayer.");
                    return;
                }
                if(!player.getInventory().hasId(BLESSED_SPIRIT_SHIELD)) {
                    player.sendMessage("You don't have a spirit shield to attach your " + item.getDef().name + " to.");
                    return;
                }

                Item hammer = player.getInventory().findItem(Tool.HAMMER);
                if(hammer == null) {
                    player.sendMessage("You need a hammer to do this.");
                    return;
                }

                player.startEvent(event -> {
                    player.lock();
                    player.animate(898);
                    event.delay(6);
                    player.animate(898);
                    event.delay(6);
                    player.getInventory().remove(BLESSED_SPIRIT_SHIELD, 1);
                    player.dialogue(new ItemDialogue().one(itemID == DIVINE_SIGIL ? 30191 : item.getId() - 2, "You successfully combine the " + item.getDef().name + " with the shield."));
                    item.setId(itemID == DIVINE_SIGIL ? 30191 : item.getId() - 2);
                    player.unlock();
                });
            });
        }
    }
}
