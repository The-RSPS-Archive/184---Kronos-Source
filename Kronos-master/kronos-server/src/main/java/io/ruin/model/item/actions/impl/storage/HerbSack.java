package io.ruin.model.item.actions.impl.storage;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

public class HerbSack {

    private static final int[] GRIMY_HERBS = new int[] {
            199, 201, 203, 205, 207, 3049, 209,
            211, 213, 3051, 215, 2485, 217, 219 };

    private static int amountStored(Player player, int herbId) {
        if (herbId == 199) return player.herbSackGuamLeaf;
        if (herbId == 201) return player.herbSackMarrentill;
        if (herbId == 203) return player.herbSackTarromin;
        if (herbId == 205) return player.herbSackHarralander;
        if (herbId == 207) return player.herbSackRanarrWeed;
        if (herbId == 3049) return player.herbSackToadflax;
        if (herbId == 209) return player.herbSackIritLeaf;
        if (herbId == 211) return player.herbSackAvantoe;
        if (herbId == 213) return player.herbSackKwuarm;
        if (herbId == 3051) return player.herbSackSnapdragon;
        if (herbId == 215) return player.herbSackCadantine;
        if (herbId == 2485) return player.herbSackLantadyme;
        if (herbId == 217) return player.herbSackDwarfWeed;
        if (herbId == 219) return player.herbSackTorstol;
        return -1;
    }

    private static void incrementSackStored(Player player, int herbId, int amount) {
        if (herbId == 199) player.herbSackGuamLeaf += amount;
        if (herbId == 201) player.herbSackMarrentill += amount;
        if (herbId == 203) player.herbSackTarromin += amount;
        if (herbId == 205) player.herbSackHarralander += amount;
        if (herbId == 207) player.herbSackRanarrWeed += amount;
        if (herbId == 3049) player.herbSackToadflax += amount;
        if (herbId == 209) player.herbSackIritLeaf += amount;
        if (herbId == 211) player.herbSackAvantoe += amount;
        if (herbId == 213) player.herbSackKwuarm += amount;
        if (herbId == 3051) player.herbSackSnapdragon += amount;
        if (herbId == 215) player.herbSackCadantine += amount;
        if (herbId == 2485) player.herbSackLantadyme += amount;
        if (herbId == 217) player.herbSackDwarfWeed += amount;
        if (herbId == 219) player.herbSackTorstol += amount;
    }

    private static void decrementSackStored(Player player, int herbId, int amount) {
        if (herbId == 199) player.herbSackGuamLeaf -= amount;
        if (herbId == 201) player.herbSackMarrentill -= amount;
        if (herbId == 203) player.herbSackTarromin -= amount;
        if (herbId == 205) player.herbSackHarralander -= amount;
        if (herbId == 207) player.herbSackRanarrWeed -= amount;
        if (herbId == 3049) player.herbSackToadflax -= amount;
        if (herbId == 209) player.herbSackIritLeaf -= amount;
        if (herbId == 211) player.herbSackAvantoe -= amount;
        if (herbId == 213) player.herbSackKwuarm -= amount;
        if (herbId == 3051) player.herbSackSnapdragon -= amount;
        if (herbId == 215) player.herbSackCadantine -= amount;
        if (herbId == 2485) player.herbSackLantadyme -= amount;
        if (herbId == 217) player.herbSackDwarfWeed -= amount;
        if (herbId == 219) player.herbSackTorstol -= amount;
    }

    private static void clearSack(Player player) {
        player.herbSackGuamLeaf = 0;
        player.herbSackMarrentill = 0;
        player.herbSackHarralander = 0;
        player.herbSackRanarrWeed = 0;
        player.herbSackToadflax = 0;
        player.herbSackIritLeaf = 0;
        player.herbSackAvantoe = 0;
        player.herbSackKwuarm = 0;
        player.herbSackSnapdragon = 0;
        player.herbSackCadantine = 0;
        player.herbSackLantadyme = 0;
        player.herbSackDwarfWeed = 0;
        player.herbSackTorstol = 0;
    }

    private static void addToSack(Player player, int herbId, int amount) {
        int herbSize = amountStored(player, herbId);
        if (herbSize >= 60) {
            player.sendFilteredMessage("Your herb sack cannot carry anymore " + ItemDef.get(herbId).name.toLowerCase() + "s.");
            return;
        }
        int minToAdd = Math.min(amount, 30 - herbSize);
        int amountToAdd = Math.min(minToAdd, player.getInventory().count(herbId));
        if (amountToAdd == 0)
            return;
        player.getInventory().remove(herbId, amountToAdd);
        incrementSackStored(player, herbId, amountToAdd);
        player.sendFilteredMessage("You add " + amountToAdd + " grimy herb" + (amount == 1 ? "" : "s") + " to the sack.");
    }

    static {
        /**
         * Fill
         */
        ItemAction.registerInventory(13226, "fill", (player, item) -> {
            int added = 0;
            for (int herbId : GRIMY_HERBS) {
                Item herb = player.getInventory().findItem(herbId);
                if (herb == null)
                    continue;
                int herbSize = amountStored(player, herbId);
                if (herbSize >= 30) {
                    player.sendFilteredMessage("Your herb sack cannot carry anymore " + herb.getDef().name.toLowerCase() + "s.");
                    continue;
                }
                int amountToAdd = Math.min(herb.count(), 30 - herbSize);
                player.getInventory().remove(herb.getId(), amountToAdd);
                incrementSackStored(player, herbId, amountToAdd);
                added += amountToAdd;
            }
            if (added > 0)
                player.sendFilteredMessage("You add " + added + " grimy herb" + (added == 1 ? "" : "s") + " to the sack.");
            else
                player.sendFilteredMessage("You have no grimy herbs to add to the sack.");
        });
        /**
         * Check
         */
        ItemAction.registerInventory(13226, "check", (player, item) -> {
            player.sendFilteredMessage("The herb sack contains:");
            for (int herbs : GRIMY_HERBS) {
                int herbSize = amountStored(player, herbs);
                player.sendFilteredMessage(herbSize + " " + ItemDef.get(herbs).name.toLowerCase() + "s");
            }
        });
        /**
         * Empty
         */
        ItemAction.registerInventory(13226, "empty", (player, item) -> {
            for (int herbId : GRIMY_HERBS) {
                int freeSlots = player.getInventory().getFreeSlots();
                if (freeSlots == 0)
                    return;
                int herbSize = amountStored(player, herbId);
                int amountToRemove = Math.min(herbSize, freeSlots);
                if (amountToRemove == 0)
                    continue;
                decrementSackStored(player, herbId, amountToRemove);
                player.getInventory().add(herbId, amountToRemove);
            }
        });
        /**
         * Destroy
         */
        ItemAction.registerInventory(13226, "destroy", (player, item) ->
                player.dialogue(new YesNoDialogue("Are you sure you want to destroy the item?",
                                "The contents of the sack will be destroyed with it.", item, () -> {
                            item.remove();
                            clearSack(player);
                        })
                )
        );
        /**
         * Item on bag
         */
        for (int herbId : GRIMY_HERBS) {
            ItemItemAction.register(herbId, 13226, (player, primary, secondary) -> player.dialogue(new OptionsDialogue(
                    "How many would you like to deposit?",
                    new Option("One", () -> addToSack(player, herbId, 1)),
                    new Option("Five", () -> addToSack(player, herbId, 5)),
                    new Option("X", () -> player.integerInput("How many would you like to deposit?", amt -> addToSack(player, herbId, amt))),
                    new Option("All", () -> addToSack(player, herbId, Integer.MAX_VALUE))
            )));
        }
    }
}
