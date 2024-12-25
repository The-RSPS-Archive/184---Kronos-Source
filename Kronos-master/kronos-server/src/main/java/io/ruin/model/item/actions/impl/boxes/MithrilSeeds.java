package io.ruin.model.item.actions.impl.boxes;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;

import java.util.Arrays;

public class MithrilSeeds {

    public enum Flowers {
        BLACK(2476, 2988, 23),       // black
        WHITE(2474, 2987, 6),        // white
        ORANGE(2470, 2985, 1539),    // orange
        PURPLE(2468, 2984, 1485),    // purple
        YELLOW(2466, 2983, 1465),    // yellow
        BLUE(2464, 2982, 1530),      // blue
        RED(2462, 2981, 1408),       // red
        MIXED(2460, 2980, 1466),     // mixed
        ASSORTED(2472, 2986, 1078);  // assorted

        private int flowerId;
        private int objId;
        private int weight;

        Flowers(int flowerId, int objId, int weight) {
            this.flowerId = flowerId;
            this.objId = objId;
            this.weight = weight;
        }
    }

    private static int TOTAL_WEIGHT = Arrays.stream(Flowers.values()).mapToInt(f -> f.weight).sum();

    private static Flowers roll() {
        int roll = Random.get(TOTAL_WEIGHT);
        for (Flowers flower : Flowers.values()) {
            roll -= flower.weight;
            if (roll <= 0)
                return flower;
        }
        return null;
    }

    public static void plant(Player player, Flowers flower) {
        if (!Tile.allowObjectPlacement(player.getPosition())) {
            player.sendMessage("You can't plant a seed here.");
            return;
        }
        if (flower == null)
            return;
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You open the small mithril case.");
            player.sendMessage("You drop a seed by your feet.");
            GameObject obj = GameObject.spawn(flower.objId, player.getAbsX(), player.getAbsY(), player.getHeight(), 10, 0);
            player.getRouteFinder().routeSelf(true);
            event.waitForMovement(player);
            player.face(obj);
            player.unlock();
            World.startEvent(worldEvent -> {
                worldEvent.delay(60);
                if (!obj.isRemoved())
                    obj.remove();
            });
            player.dialogue(new OptionsDialogue(
                    new Option("Pick the flowers.", () -> {
                        player.animate(827);
                        obj.remove();
                        player.getInventory().addOrDrop(flower.flowerId, 1);
                    }),
                    new Option("Leave the flowers.", () -> {})
            ));
        });
    }

    private static void plant(Player player, Item item) {
        if (!Tile.allowObjectPlacement(player.getPosition())) {
            player.sendMessage("You can't plant a seed here.");
            return;
        }
        Flowers flowers = roll();
        if (flowers == null)
            return;
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You open the small mithril case.");
            player.sendMessage("You drop a seed by your feet.");
            item.remove(1);
            GameObject obj = GameObject.spawn(flowers.objId, player.getAbsX(), player.getAbsY(), player.getHeight(), 10, 0);
            player.getRouteFinder().routeSelf(true);
            event.waitForMovement(player);
            player.face(obj);
            player.unlock();
            World.startEvent(worldEvent -> {
                worldEvent.delay(60);
                if (!obj.isRemoved())
                    obj.remove();
            });
            player.dialogue(new OptionsDialogue(
                    new Option("Pick the flowers.", () -> {
                        player.animate(827);
                        obj.remove();
                        player.getInventory().addOrDrop(flowers.flowerId, 1);
                    }),
                    new Option("Leave the flowers.", () -> {})
            ));
        });
    }

    private static final int MITHRIL_SEED = 299;

    static {
        ItemAction.registerInventory(MITHRIL_SEED, "plant", MithrilSeeds::plant);
    }
}
