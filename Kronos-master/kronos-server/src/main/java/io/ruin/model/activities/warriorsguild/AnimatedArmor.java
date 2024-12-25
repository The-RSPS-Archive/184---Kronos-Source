package io.ruin.model.activities.warriorsguild;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.List;

public enum AnimatedArmor {

    BRONZE(5, 2450, 1075, 1117, 1155),
    IRON(10, 2451, 1067, 1115, 1153),
    STEEL(15, 2452, 1069, 1119, 1157),
    BLACK(20, 2453, 1077, 1125, 1165),
    MITHRIL(25, 2454, 1071, 1121, 1159),
    ADDY(30, 2455, 1073, 1123, 1161),
    RUNE(40, 2456, 1079, 1127, 1163);

    public final int tokenCount, npcId;
    public final int[] armorIds;

    AnimatedArmor(int tokenCount, int npcId, int... armorIds) {
        this.tokenCount = tokenCount;
        this.npcId = npcId;
        this.armorIds = armorIds;
    }

    private void spawn(Player player, GameObject gameObject) {
        if(player.npcTarget) {
            player.sendMessage("You are already under attack!");
            return;
        }
        ArrayList<Item> items = player.getInventory().collectOneOfEach(armorIds);
        if(items == null) {
            player.sendMessage("To animate this armor, you'll need a full helm, platebody, and pair of platelegs.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.dialogue(new MessageDialogue("You place your armour on the platform where it disappears...").hideContinue());
            event.delay(1);
            player.animate(827);
            event.delay(3);
            for(Item armor : items)
                armor.remove();
            event.delay(2);
            player.dialogue(new MessageDialogue("The animator hums, something appears to be working. You stand back...").hideContinue().lineHeight(31));
            event.delay(2);
            player.getMovement().force(0, 2, 0, 0, 45, 126, Direction.SOUTH);
            event.delay(1);
            player.animate(820, 5);
            event.delay(3);
            NPC npc = new NPC(npcId)
                    .spawn(gameObject.x, gameObject.y, 0, Direction.SOUTH, 0)
                    .targetPlayer(player, true);
            npc.forceText("I'M ALIVE!");
            npc.animate(4166, 1);
            event.delay(2);
            npc.attackTargetPlayer(() -> !player.getPosition().inBounds(ATTACK_BOUNDS), () -> {
                if(npc.getCombat().isDead())
                    new GroundItem(8851, tokenCount).owner(player).position(npc.getPosition()).spawn();
                for(int id : armorIds)
                    new GroundItem(id, 1).owner(player).position(npc.getPosition()).spawn();
            });
            player.closeInterface(InterfaceType.CHATBOX);
            player.unlock();
        });
    }

    static {
        for(AnimatedArmor armour : values()) {
            for(int id : armour.armorIds)
                ItemObjectAction.register(id, 23955, (p, item, obj) -> armour.spawn(p, obj));
            NPCDef.get(armour.npcId).ignoreOccupiedTiles = true;
        }
        ObjectAction.register(23955, "animate", (player, obj) -> {
            for(AnimatedArmor armour : values()) {
                List<Item> armourPieces = player.getInventory().collectOneOfEach(armour.armorIds);
                if(armourPieces != null) {
                    armour.spawn(player, obj);
                    break;
                }
            }
        });
        ItemObjectAction.register(23955, (p, item, obj) -> p.sendMessage("This item cannot be animated. Try using a full helm, platebody, or pair of platelegs instead."));
    }

    private static final Bounds ATTACK_BOUNDS = new Bounds(2849, 3534, 2861, 3545, 0);

}
