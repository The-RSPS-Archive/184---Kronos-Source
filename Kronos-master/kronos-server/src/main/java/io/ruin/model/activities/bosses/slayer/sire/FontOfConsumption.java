package io.ruin.model.activities.bosses.slayer.sire;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.List;

public class FontOfConsumption {

    static {
        ItemObjectAction.register(13273, 27029, (player, item, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.face(Direction.NORTH);
                event.delay(1);
                player.dialogue(new ItemDialogue().one(13273, "You place the Unsired into the Font of Consumption...").hideContinue());
                player.animate(827);
                World.sendGraphics(1276, 0, 0, player.getPosition().relative(0, 1));
                event.delay(2);
                int reward = roll(player);
                item.setId(reward);
                player.dialogue(new ItemDialogue().one(reward, "The Font consumes the Unsired and returns you a<br>reward."));
                player.unlock();
            });
        });

        ObjectAction.register(27057, 1, (player, obj) -> { // TODO find chathead and full dialogue... @Nick :)
            List<Item> pieces = player.getInventory().collectOneOfEach(13274, 13275, 13276);
            if (pieces == null) {
                player.dialogue(new MessageDialogue("You'll need a bludgeon claw, axon and spine<br> to have the Overseer create a weapon for you."));
            } else {
                player.startEvent(event -> {
                    player.lock();
                    pieces.forEach(Item::remove);
                    player.dialogue(new ItemDialogue().two(13274, 13275, "You hand over the components to the Overseer.").hideContinue());
                    event.delay(2);
                    player.getInventory().add(13263, 1);
                    player.dialogue(new ItemDialogue().one(13263, "The Overseer presents you with an Abyssal Bludgeon."));
                    player.unlock();
                });
            }
        });
    }

    private static int roll(Player player) {
        int roll = Random.get(127);
        if (roll < 5)
            return 13262; // abyssal orphan (pet)
        if (roll < 15)
            return 7979; // abyssal head
        if (roll < 41)
            return 13265; // abyssal dagger
        if (roll < 53)
            return 4151; // whip
        if (roll < 66)
            return 13277; // jar of miasma
        return getNextBludgeonPiece(player); // 66-127 -> bludgeon piece
    }

    private static int getNextBludgeonPiece(Player player) {
        int claws = 0, spines = 0, axons = 0;
        for (Item item : player.getBank().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == 13274)
                spines += item.getAmount();
            else if (item.getId() == 13275)
                claws += item.getAmount();
            else if (item.getId() == 13276)
                axons += item.getAmount();
        }
        for (Item item : player.getInventory().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == 13274)
                spines++;
            else if (item.getId() == 13275)
                claws++;
            else if (item.getId() == 13276)
                axons++;
        }
        int lowest = Math.min(Math.min(claws, axons), spines);
        List<Integer> possible = new ArrayList<>();
        if (lowest == spines)
            possible.add(13274);
        if (lowest == claws)
            possible.add(13275);
        if (lowest == axons)
            possible.add(13276);
        return Random.get(possible);
    }

}
