package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public class Ballista {

    private static final int MONKEY_TAIL = 19610;
    private static final int SPRING = 19601;
    private static final int LIMBS = 19592;

    private static final int LIGHT_FRAME = 19586;
    private static final int HEAVY_FRAME = 19589;

    private static final int INCOMPLETE_LIGHT = 19595;
    private static final int INCOMPLETE_HEAVY = 19598;

    private static final int UNSTRUNG_LIGHT = 19604;
    private static final int UNSTRUNG_HEAVY = 19607;

    private static final int LIGHT_BALLISTA = 19478;
    private static final int HEAVY_BALLISTA = 19481;

    private static void craft(Player player, Double exp, int levelReq, Item itemOne, Item itemTwo, int result) {
        if(!player.getStats().check(StatType.Fletching, levelReq, "do this"))
            return;
        player.startEvent(event -> {
            itemOne.remove();
            itemTwo.remove();
            player.getInventory().add(result, 1);
            player.animate(7172);
            player.getStats().addXp(StatType.Fletching, exp, true);
            event.delay(4);
            player.resetAnimation();
        });
    }

    static {
        /**
         * Wrong actions
         */
        ItemItemAction.register(MONKEY_TAIL, SPRING, (player, primary, secondary) ->
                player.sendMessage("You should add a ballista spring to an incomplete ballista before attaching the tail."));

        ItemItemAction.register(MONKEY_TAIL, LIGHT_FRAME, (player, primary, secondary) ->
                player.sendMessage("The frame must first be combined with a pair of ballista limbs."));

        ItemItemAction.register(MONKEY_TAIL, SPRING, (player, primary, secondary) ->
                player.sendMessage("The frame must first be combined with a pair of ballista limbs."));

        ItemItemAction.register(MONKEY_TAIL, LIMBS, (player, primary, secondary) ->
                player.sendMessage("The limbs must first be combined with a ballista frame."));

        ItemItemAction.register(SPRING, LIMBS, (player, primary, secondary) ->
                player.sendMessage("The limbs must first be combined with a ballista frame."));

        ItemItemAction.register(MONKEY_TAIL, INCOMPLETE_LIGHT, (player, primary, secondary) ->
                player.sendMessage("You should add a ballista spring before attaching the tail."));

        /**
         * Attaching the limbs to a frame
         */
        ItemItemAction.register(LIMBS, LIGHT_FRAME, (player, primary, secondary) ->
                craft(player, 15.0, 30, primary, secondary, INCOMPLETE_LIGHT));
        ItemItemAction.register(LIMBS, HEAVY_FRAME, (player, primary, secondary) ->
                craft(player, 30.0, 72, primary, secondary, INCOMPLETE_HEAVY));

        /**
         * Adding the spring to an unfinished frame
         */
        ItemItemAction.register(SPRING, INCOMPLETE_LIGHT, (player, primary, secondary) ->
                craft(player, 15.0, 30, primary, secondary, UNSTRUNG_LIGHT));
        ItemItemAction.register(SPRING, INCOMPLETE_HEAVY, (player, primary, secondary) ->
                craft(player, 30.0, 72, primary, secondary, UNSTRUNG_HEAVY));

        /**
         * Finishing the ballista
         */
        ItemItemAction.register(MONKEY_TAIL, UNSTRUNG_LIGHT, (player, primary, secondary) ->
                craft(player, 300.0, 30, primary, secondary, LIGHT_BALLISTA));
        ItemItemAction.register(MONKEY_TAIL, UNSTRUNG_HEAVY, (player, primary, secondary) ->
                craft(player, 600.0, 72, primary, secondary, HEAVY_BALLISTA));
    }
}
