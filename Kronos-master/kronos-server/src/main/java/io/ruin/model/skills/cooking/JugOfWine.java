package io.ruin.model.skills.cooking;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public class JugOfWine {

    private static final int GRAPES = 1987;
    private static final int JUG_OF_WINE = 1993;
    private static final int JUG_OF_WATER = 1937;

    private static void makeWine(Player player, Item primary, Item secondary) {
        player.startEvent(event -> {
            if (player.getStats().get(StatType.Cooking).fixedLevel < 35) {
                player.sendMessage("You do not have the required level to make wine.");
                return;
            }
            int amount = Math.min(primary.count(), secondary.count());
            while(amount --> 0) {
                player.getInventory().remove(primary.getId(), 1);
                player.getInventory().remove(secondary.getId(), 1);
                player.getInventory().add(JUG_OF_WINE, 1);
                player.sendMessage("You squeeze the grapes into the jug. The wine begins to ferment.");
                player.getStats().addXp(StatType.Cooking, 200.0, true);
                PlayerCounter.JUGS_OF_WINE_MADE.increment(player, 1);
                event.delay(2);
            }
        });
    }

    static {
        ItemItemAction.register(JUG_OF_WATER, GRAPES, JugOfWine::makeWine);
    }
}
