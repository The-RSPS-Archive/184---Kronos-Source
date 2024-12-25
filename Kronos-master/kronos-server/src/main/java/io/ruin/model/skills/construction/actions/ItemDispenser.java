package io.ruin.model.skills.construction.actions;

import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemDispenser { // used for house objects that allow players to take certain items
                             // osrs uses an option dialogue for this but I decided to use the option scroll instead so all the items fit in one screen, should be more convenient for players

    public static void register(Buildable buildable, int... items) {
        register(buildable, Arrays.stream(items).mapToObj(id -> new Item(id, 1)).toArray(Item[]::new));
    }

    public static void register(int obj, int... items) {
        register(obj, Arrays.stream(items).mapToObj(id -> new Item(id, 1)).toArray(Item[]::new));
    }

    public static void register(Buildable buildable, Item... items) {
        for (int id : buildable.getBuiltObjects()) {
            register(id, items);
        }
    }

    public static void register(int objId, Item[] items) {
        ObjectAction.register(objId, 1, (player, obj) -> {
            OptionScroll.open(player, "Choose an Item", true, Arrays.stream(items)
                    .map(item -> new Option(item.getDef().name, () -> player.getInventory().add(item)))
                    .collect(Collectors.toList()));
        });
    }

}
