package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

public class Godsword {

    /**
     * Shards
     */
    private static final int SHARD_1 = 11818;
    private static final int SHARD_2 = 11820;
    private static final int SHARD_3 = 11822;

    /**
     * Combined shards
     */
    private static final int SHARD_1_AND_2 = 11794;
    private static final int SHARD_1_AND_3 = 11796;
    private static final int SHARD_2_AND_3 = 11800;

    /**
     * Blade
     */
    private static final int BLADE = 11798;

    /**
     * Hilts
     */
    private static final int SARADOMIN_HILT = 11814;
    private static final int ARMADYL_HILT = 11810;
    private static final int ZAMORAK_HILT = 11816;
    private static final int BANDOS_HILT = 11812;

    /**
     * Godswords
     */
    private static final int ARMADYL_GODSWORD = 11802;
    private static final int BANDOS_GODSWORD = 11804;
    private static final int SARADOMIN_GODSWORD = 11806;
    private static final int ZAMORAK_GODSWORD = 11808;

    private static void combine(Player player, Item itemOne, Item itemTwo, int result) {
        itemOne.remove();
        itemTwo.remove();
        player.getInventory().add(result, 1);
    }

    private static void dismantle(Player player, Item item, int resultOne, int resultTwo) {
        if(player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough free space to do this.");
            return;
        }

        item.remove();
        player.getInventory().add(resultOne, 1);
        player.getInventory().add(resultTwo, 1);
    }

    static {
        /**
         * Combining
         */
        ItemItemAction.register(SHARD_1, SHARD_2, (player, primary, secondary) -> combine(player, primary, secondary, SHARD_1_AND_2));
        ItemItemAction.register(SHARD_1, SHARD_3, (player, primary, secondary) -> combine(player, primary, secondary, SHARD_1_AND_3));
        ItemItemAction.register(SHARD_2, SHARD_3, (player, primary, secondary) -> combine(player, primary, secondary, SHARD_2_AND_3));

        ItemItemAction.register(SHARD_1, SHARD_2_AND_3, (player, primary, secondary) -> combine(player, primary, secondary, BLADE));
        ItemItemAction.register(SHARD_2, SHARD_1_AND_3, (player, primary, secondary) -> combine(player, primary, secondary, BLADE));
        ItemItemAction.register(SHARD_3, SHARD_1_AND_2, (player, primary, secondary) -> combine(player, primary, secondary, BLADE));

        ItemItemAction.register(BLADE, SARADOMIN_HILT, (player, primary, secondary) -> combine(player, primary, secondary, SARADOMIN_GODSWORD));
        ItemItemAction.register(BLADE, ARMADYL_HILT, (player, primary, secondary) -> combine(player, primary, secondary, ARMADYL_GODSWORD));
        ItemItemAction.register(BLADE, BANDOS_HILT, (player, primary, secondary) -> combine(player, primary, secondary, BANDOS_GODSWORD));
        ItemItemAction.register(BLADE, ZAMORAK_HILT, (player, primary, secondary) -> combine(player, primary, secondary, ZAMORAK_GODSWORD));

        /**
         * Dismantle
         */
        ItemAction.registerInventory(SARADOMIN_GODSWORD, "dismantle", (player, item) -> dismantle(player, item, SARADOMIN_HILT, BLADE));
        ItemAction.registerInventory(ARMADYL_GODSWORD, "dismantle", (player, item) -> dismantle(player, item, ARMADYL_HILT, BLADE));
        ItemAction.registerInventory(BANDOS_GODSWORD, "dismantle", (player, item) -> dismantle(player, item, BANDOS_HILT, BLADE));
        ItemAction.registerInventory(ZAMORAK_GODSWORD, "dismantle", (player, item) -> dismantle(player, item, ZAMORAK_HILT, BLADE));

    }
}
