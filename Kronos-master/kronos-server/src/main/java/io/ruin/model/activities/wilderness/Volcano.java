package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Direction;

public enum Volcano {

    MALEDICTION_WARD(new Item(11931, 1), new Item(11932, 1), new Item(11933, 1), new Item(11924, 1), "Malediction"),
    ODIUM_WARD(new Item(11928, 1), new Item(11929, 1), new Item(11930, 1), new Item(11926, 1), "Odium");

    public final Item firstShard, secondShard, thirdShard, shield;
    public final String shieldName;

    Volcano(Item firstShard, Item secondShard, Item thirdShard, Item shield, String shieldName) {
        this.firstShard = firstShard;
        this.secondShard = secondShard;
        this.thirdShard = thirdShard;
        this.shield = shield;
        this.shieldName = shieldName;
    }

    private static void forgeShield(Player player, Volcano shield) {
        Item first = player.getInventory().findItem(shield.firstShard.getId());
        Item second = player.getInventory().findItem(shield.secondShard.getId());
        Item third = player.getInventory().findFirst(shield.thirdShard.getId());

        if (first == null || second == null || third == null) {
            player.sendFilteredMessage("You need all " + shield.shieldName + " shards to forge a shield.");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.dialogue(new MessageDialogue("You drop the three shield shards into the mouth of the volcanic<br>chamber of fire.").hideContinue());
            player.animate(4411);
            event.delay(1);
            player.getMovement().force(0, -1, 0, 0, 45, 126, Direction.NORTH);
            event.delay(1);
            player.animate(734, 5);
            event.delay(3);
            first.remove();
            second.remove();
            third.remove();
            player.getInventory().add(shield.shield);
            player.sendFilteredMessage("You forge the shield pieces together in the chambers of fire and are blown back by");
            player.sendFilteredMessage("the intense heat.");
            player.closeDialogue();
            player.unlock();
        });
    }

    static {
        /*
         * Shield shards
         */
        for (Volcano shield : Volcano.values()) {
            ItemObjectAction.register(shield.firstShard.getId(), 26755, (player, item, obj) -> forgeShield(player, shield));
            ItemObjectAction.register(shield.secondShard.getId(), 26755, (player, item, obj) -> forgeShield(player, shield));
            ItemObjectAction.register(shield.thirdShard.getId(), 26755, (player, item, obj) -> forgeShield(player, shield));
        }

        /*
         * Gold ring
         */
        ItemObjectAction.register(1635, 26755, (player, item, obj) -> player.startEvent(event -> {
            player.lock();
            player.getPacketSender().shakeCamera(0,5);
            item.remove();
            event.delay(2);
            NPC goblin = new NPC(3028).spawn(player.getAbsX() - 1, player.getAbsY(), 0);
            goblin.face(player);
            goblin.forceText("My Precious!!! NOOOOO!!!");
            event.delay(1);
            goblin.animate(6184);
            player.getPacketSender().resetCamera();
            event.delay(3);
            goblin.remove();
            player.unlock();
        }));
    }
}
