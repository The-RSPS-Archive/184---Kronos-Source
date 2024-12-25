package io.ruin.model.skills.woodcutting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.BirdNest;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class WoodcuttingGuild {

    private enum Egg {

        RED(5076, 1308),
        BLUE(5077, 1307),
        GREEN(5078, 1309);

        private final int itemID;

        private final Projectile projectile;

        Egg(int itemID, int graphic) {
            this.itemID = itemID;
            this.projectile = new Projectile(graphic, 25, 112, 0, 140, 0, 50, 64);
        }

    }

    private static Bounds BOOST_AREA_1 = new Bounds(1563, 3477, 1600, 3503, -1);
    private static Bounds BOOST_AREA_2 = new Bounds(1607, 3487, 1654, 3516, -1);

    public static boolean hasBoost(Player player) {
        return player.getPosition().inBounds(BOOST_AREA_1) || player.getPosition().inBounds(BOOST_AREA_2);
    }

    private static void ropeLadder(Player player, GameObject ladder, int offsetX, int anim, int height) {
        player.startEvent(event -> {
            player.lock();
            player.animate(anim);
            event.delay(1);
            player.getMovement().teleport(ladder.x + offsetX, ladder.y, height);
            player.unlock();
        });
    }

    private static void openGate(Player player, GameObject northGate, GameObject southGate, int replacementX, int replacementY) {
        player.startEvent(event -> {
            player.lock();

            GameObject northGateReplacement = GameObject.spawn(23552, replacementX, replacementY, 0, 0, 1);
            GameObject southGateReplacement = GameObject.spawn(23554, replacementX, replacementY - 1, 0, 0, 3);
            northGate.skipClipping(true).remove();
            southGate.skipClipping(true).remove();
            if(player.getAbsX() == 1657)
                player.dialogue(new NPCDialogue(7235, "Welcome to the woodcutting guild, adventurer."));
            if(player.getAbsX() >= replacementX)
                player.stepAbs(replacementX - 1, player.getAbsY(), StepType.FORCE_WALK);
            else
                player.stepAbs(replacementX, player.getAbsY(), StepType.FORCE_WALK);
            event.delay(1);
            northGate.restore().skipClipping(false);
            southGate.restore().skipClipping(false);
            northGateReplacement.remove();
            southGateReplacement.remove();

            player.unlock();
        });
    }

    static {
        /**
         * Guild entrance
         */
        int[] gate = {28851, 28852};
        for(int id : gate) {
            ObjectAction.register(id, "open", (player, obj) -> {
                if(!player.getStats().check(StatType.Woodcutting, 60, "access this guild"))
                    return;
                /* West entrance */
                if(obj.x == 1562) {
                    GameObject northGate = Tile.getObject(28851, 1562, 3488, 0);
                    GameObject southGate = Tile.getObject(28852, 1562, 3487, 0);
                    openGate(player, northGate, southGate, 1563, 3488);
                } else {
                    /* East entrance */
                    GameObject northGate = Tile.getObject(28851, 1657, 3505, 0);
                    GameObject southGate = Tile.getObject(28852, 1657, 3504, 0);
                    openGate(player, northGate, southGate, 1658, 3505);
                }
            });
        }

        /**
         * Rope ladders
         */
        ObjectAction.register(28857, "climb-up", (player, obj) -> ropeLadder(player, obj, player.getAbsX() >= 1571 ? -1 : 1, 828, 1));
        ObjectAction.register(28858, "climb-down", (player, obj) -> ropeLadder(player, obj, 0, 827, 0));

        /**
         * Dungeon entrance/exit
         */
        ObjectAction.register(28855, "enter", (player, obj) -> player.getMovement().teleport(1596, 9900));
        ObjectAction.register(28856, "climb", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(828);
            event.delay(1);
            player.getMovement().teleport(1606, 3508);
            player.unlock();
        }));

        /**
         * Bird eggs on shrine
         */
        for(Egg egg : Egg.values()) {
            ItemObjectAction.register(egg.itemID, 29088, (player, item, obj) -> {
                item.setId(BirdNest.SEED_ONE.itemID);
                player.getStats().addXp(StatType.Prayer, 100.0, true);
                player.sendFilteredMessage("You offer your bird's egg to the shrine and receive a reward.");
                player.animate(3705);
                egg.projectile.send(obj.x + 1, obj.y + 1, obj.x + 2, obj.y + 1);
            });
        }

        /**
         * Redwood tree entrance to upper level
         */
        ObjectAction.register(29681, "enter", (player, obj) -> player.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight() + 1));

        /**
         * Redwood tree exit back to lower level
         */
        ObjectAction.register(29682, "enter", (player, obj) -> player.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight() - 1));
    }
}
