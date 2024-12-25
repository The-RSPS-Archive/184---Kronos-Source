package io.ruin.model.item.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemObjectAction;

import java.util.ArrayList;

public enum Containers {

    /**
     * Misc
     */
    VIAL(229, 227),
    BUCKET(1925, 1929),
    BOWL(1923, 1921),
    JUG(1935, 1937),
    KETTLE(7688, 7690),

    /**
     * Watering cans
     */
    WATERING_CAN_EMPTY(5331, 5340),
    WATERING_CAN_ONE(5333, 5340),
    WATERING_CAN_TWO(5334, 5340),
    WATERING_CAN_THREE(5335, 5340),
    WATERING_CAN_FOUR(5336, 5340),
    WATERING_CAN_FIVE(5337, 5340),
    WATERING_CAN_SIX(5338, 5340),
    WATERING_CAN_SEVEN(5331, 5340);

    public final int empty, full;
    public final String name;

    Containers(int empty, int full) {
        this.empty = empty;
        this.full = full;
        this.name = ItemDef.get(empty).name.toLowerCase();
    }

    private enum WaterSource {
        SINK, WATERPUMP, FOUNTAIN, WELL, TAP
    }

    private static void fillContainer(Player player, Item item, Containers waterContainer, WaterSource source) {
        /* Only buckets are able to be filled from a well */
        if (source == WaterSource.WELL && waterContainer != BUCKET) {
            player.sendMessage("If I drop my " + waterContainer.name + " down there, I don't think I'm likely to get it back.");
            return;
        }

        ArrayList<Item> containers = player.getInventory().collectItems(item.getId());
        containers.forEach(container -> container.setId(waterContainer.full));
        player.animate(832);
        player.privateSound(2609);
        player.sendMessage("You fill the " + waterContainer.name + " from the " + source.name().toLowerCase() + ".");
    }

    private static final int[] FULL_BUCKETS = {1783, 1927, 1929, 4286, 4687, 4693, 7622, 7624, 7626, 9659};

    static {
        /**
         * Water sources
         */
        ObjectDef.forEach(objDef -> {
            if (objDef.name.equalsIgnoreCase("sink"))
                for (Containers container : values())
                    ItemObjectAction.register(container.empty, objDef.id, (player, item, obj) -> fillContainer(player, item, container, WaterSource.SINK));
            if (objDef.name.equalsIgnoreCase("waterpump"))
                for (Containers container : values())
                    ItemObjectAction.register(container.empty, objDef.id, (player, item, obj) -> fillContainer(player, item, container, WaterSource.WATERPUMP));
            if (objDef.name.equalsIgnoreCase("fountain"))
                for (Containers container : values())
                    ItemObjectAction.register(container.empty, objDef.id, (player, item, obj) -> fillContainer(player, item, container, WaterSource.FOUNTAIN));
            if (objDef.name.equalsIgnoreCase("well"))
                for (Containers container : values())
                    ItemObjectAction.register(container.empty, objDef.id, (player, item, obj) -> fillContainer(player, item, container, WaterSource.WELL));
            if (objDef.name.equalsIgnoreCase("tap"))
                for (Containers container : values())
                    ItemObjectAction.register(container.empty, objDef.id, (player, item, obj) -> fillContainer(player, item, container, WaterSource.TAP));
        });

        /**
         * Emptying water containers
         */
        for (Containers container : values()) {
            ItemAction.registerInventory(container.full, "empty", (player, item) -> {
                item.setId(container.empty);
                player.sendMessage("You empty the contents of the " + container.name + " on the floor.");
            });
        }

        /**
         * Emptying buckets
         */
        for(int id : FULL_BUCKETS) {
            ItemAction.registerInventory(id, "empty", (player, item) -> {
                item.setId(BUCKET.empty);
                player.sendMessage("You empty the contents of the bucket on the floor.");
            });
        }
    }
}
