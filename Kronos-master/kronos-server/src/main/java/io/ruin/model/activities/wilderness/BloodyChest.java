package io.ruin.model.activities.wilderness;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.ObjectAction;

public class BloodyChest {

    // Objects
    private static final int CLOSED_CHEST = 32572;
    private static final int OPEN_CHEST = 32573;

    // Items
    private static final int BLOODY_KEY_EASY = 3606;
    private static final int BLOODY_KEY_MEDIUM = 3608;
    private static final int BLOODY_KEY_HARD = 7297;

    // Bounds
    public static final Bounds BLOODY_DUNGEON = new Bounds(3004, 10230, 3061, 10293, -1);

    public static void attemptPickupBloodyKey(Player player) {
        if (player.bloodyKeyWarning) {
            player.dialogue(
                    new MessageDialogue("<col=ff0000>Warning:</col> Picking up this key will high-risk skull you, teleblock you, and all players will be able to attack you!"),
                    new OptionsDialogue("Are you sure you wish to pickup this key?",
                            new Option("Yes, I'm brave.", () -> pickupBloodyKey(player)),
                            new Option("Eep! No thank you.", () -> player.sendFilteredMessage("You decide to not to pickup the key.")),
                            new Option("Yes please, don't show this message again.", () -> {
                                player.bloodyKeyWarning = false;
                                pickupBloodyKey(player);
                            }))
            );
        } else {
            pickupBloodyKey(player);
        }
    }

    public static void pickupBloodyKey(Player player) {

    }

    public static boolean hasBloodyKey(Player player) {
        return player.getInventory().hasId(BLOODY_KEY_EASY) ||
                player.getInventory().hasId(BLOODY_KEY_MEDIUM) ||
                player.getInventory().hasId(BLOODY_KEY_HARD);
    }

    static {
        ObjectAction.register(CLOSED_CHEST, "open", (player, obj) -> {
            Item bloodyKey = player.getInventory().findFirst(BLOODY_KEY_EASY, BLOODY_KEY_MEDIUM, BLOODY_KEY_HARD);
            if (bloodyKey == null) {
                player.dialogue(new ItemDialogue().one(BLOODY_KEY_EASY, "You need a " + Color.DARK_RED.wrap("Bloody Key") + " to open this chest. Right click and select "
                        + Color.DARK_RED.wrap("Information") + " on the " + Color.DARK_RED.wrap("Bloody Chest") + " for more details on how to obtain a key."));
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the Bloody Chest with your key.");
                player.privateSound(51);
                player.animate(536);
                World.startEvent(e -> {
                    obj.setId(OPEN_CHEST);
                    e.delay(2);
                    obj.setId(obj.originalId);
                });
                event.delay(1);
                player.unlock();
            });
        });
    }

}
