package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.woodcutting.Tree;

public enum BirdNest {

    /* Egg */
    RED_EGG(5070, 5076, "search"),
    GREEN_EGG(5071, 5078, "search"),
    BLUE_EGG(5072, 5077, "search"),

    /* Ring */
    RING(5074, -1, "search"),

    /* Seeds */
    SEED_ONE(5073, -1, "search"),
    SEED_TWO(7413, -1, "search"),
    SEED_THREE(13653, -1, "search"),

    /* Clue scrolls */
    EASY_CLUE(19712, ClueType.EASY.clueId, "open"),
    MEDIUM_CLUE(19714, ClueType.MEDIUM.clueId, "open"),
    HARD_CLUE(19716, ClueType.HARD.clueId, "open"),
    ELITE_CLUE(19718, ClueType.ELITE.clueId, "open");

    public final int itemID, result;
    public final String optionName;

    BirdNest(int itemID, int result, String optionName) {
        this.result = result;
        this.itemID = itemID;
        this.optionName = optionName;
    }

    public static final LootTable ringNest = new LootTable().addTable(1,
            new LootItem(1635, 1, 15), //Gold ring
            new LootItem(1637, 1, 10), //Sapphire ring
            new LootItem(1639, 1, 5),  //Emerald ring
            new LootItem(1641, 1, 3),  //Ruby ring
            new LootItem(1643, 1, 1)   //Diamond ring
    );

    public static final LootTable seedNest = new LootTable().addTable(1,
            new LootItem(5312, 1, 50), //Acorn
            new LootItem(5283, 1, 50), //Apple tree seed
            new LootItem(5284, 1, 50), //Banana tree seed
            new LootItem(5285, 1, 30), //Orange tree seed
            new LootItem(5313, 1, 30), //Willow seed
            new LootItem(5286, 1, 30), //Curry tree seed
            new LootItem(5314, 1, 5),  //Maple seed
            new LootItem(5287, 1, 5),  //Pineapple seed
            new LootItem(5288, 1, 5),  //Papaya tree seed
            new LootItem(5289, 1, 5),  //Palm tree seed
            new LootItem(5290, 1, 5),  //Calquat tree seed
            new LootItem(5315, 1, 5),  //Yew seed
            new LootItem(5316, 1, 1),  //Magic seed
            new LootItem(5317, 1, 1)   //Spirit seed
    );

    private static void openNest(Player player, Item item, Item reward, String descriptiveName) {
        if (player.getInventory().isFull()) {
            player.sendMessage("You don't have enough inventory space to do that.");
            return;
        }

        item.setId(5075);
        player.getInventory().add(reward);
        PlayerCounter.OPENED_BIRDS_NESTS.increment(player, 1);
        player.sendMessage("You take " + descriptiveName + " out of the bird's nest.");
    }

    public static int getRandomNest(Tree tree) {
        /* Redwood trees only give clue nests */
        if (tree == Tree.REDWOOD) {
            int[] clueNests = {19712, 19714, 19716, 19718};
            return clueNests[Random.get(3)];
        }

        int randomNest;
        randomNest = Random.get(BirdNest.values().length - 1);
        return BirdNest.values()[randomNest].itemID;
    }

    static {
        for (BirdNest nest : values()) {
            if (nest.result != -1) {
                ItemAction.registerInventory(nest.itemID, nest.optionName, (player, item) -> {
                    Item reward = new Item(nest.result, 1);
                    openNest(player, item, reward, reward.getDef().descriptiveName.toLowerCase());
                });
            } else {
                ItemAction.registerInventory(nest.itemID, nest.optionName, (player, item) -> {
                    Item reward = nest == RING ? ringNest.rollItem() : seedNest.rollItem();
                    openNest(player, item, reward, reward.getDef().descriptiveName.toLowerCase());
                });
            }
        }
    }
}
