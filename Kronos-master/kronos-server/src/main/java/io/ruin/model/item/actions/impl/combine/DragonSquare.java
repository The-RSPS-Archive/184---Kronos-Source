package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;

public class DragonSquare {

    private static final int LEFT = 2366;
    private static final int RIGHT = 2368;
    private static final int FULL = 1187;

    private static void make(Player player) {
        Item leftHalf = player.getInventory().findItem(LEFT);
        Item rightHalf = player.getInventory().findItem(RIGHT);

        if(leftHalf == null || rightHalf == null) {
            player.dialogue(new ItemDialogue().two(LEFT, RIGHT, "You need both the left and right shield halves to forge a square shield."));
            return;
        }

        Item hammer = player.getInventory().findItem(Tool.HAMMER);
        if(hammer == null) {
            player.sendMessage("You need a hammer to forge the shields.");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You start to hammer the metal...");
            player.animate(898);
            event.delay(6);
            if(player.getInventory().hasId(RIGHT) && player.getInventory().hasId(LEFT)) {
                player.getInventory().remove(RIGHT, 1);
                player.getInventory().remove(LEFT, 1);
                player.getInventory().add(FULL, 1);
                player.sendMessage("You forge the shield halves together to complete it.");
            }
            player.unlock();
        });
    }

    static {
        ItemObjectAction.register(LEFT, "anvil", (player, item, obj) -> make(player));
        ItemObjectAction.register(RIGHT, "anvil", (player, item, obj) -> make(player));
    }
}
