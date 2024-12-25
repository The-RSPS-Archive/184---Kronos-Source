package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.cache.Color;
import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.combine.SlayerHelm;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.actions.ObjectAction;

public class SmokeDevilDungeon {

    private static boolean hasFaceMask(Player player) {
        Item faceMask = player.getEquipment().findFirst(SlayerHelm.FACEMASK, SlayerHelm.SLAYER_HELM, SlayerHelm.SLAYER_HELM_IMBUE, SlayerHelm.BLACK_SLAYER_HELM,
                SlayerHelm.BLACK_HELM_IMBUE, SlayerHelm.RED_HELM_IMBUE, SlayerHelm.RED_SLAYER_HELM, SlayerHelm.GREEN_HELM_IMBUE, SlayerHelm.GREEN_SLAYER_HELM,
                SlayerHelm.PURPLE_HELM_IMBUE, SlayerHelm.PURPLE_SLAYER_HELM, SlayerHelm.HYDRA_SLAYER_HELM, SlayerHelm.HYDRA_HELM_IMBUE,
                SlayerHelm.VORKATH_SLAYER_HELM, SlayerHelm.VORKATH_SLAYER_HELM_IMBUE);
        return faceMask != null;
    }

    private static final Bounds DUNGEON_BOUNDS = new Bounds(2317, 9418, 2431, 9471, -1);
    private static final Bounds DUNGEON_BOSS = new Bounds(2350, 9436, 2378, 9460, -1);

    private static int playerCount;

    static {
        /**
         * Entrance/exit
         */
        ObjectAction.register(30176, 2411, 3061, 0, "enter", (player, obj) -> {
            if (hasFaceMask(player)) {
                player.getMovement().teleport(2404, 9415);
            } else {
                player.dialogue(new NPCDialogue(7654, "Hey you don't " + Color.COOL_BLUE.wrap("*cough*") + " wanna go in there without " + Color.COOL_BLUE.wrap("*cough*") +
                                " some kind of protection from the smoke. Your lungs aren't as tough as " + Color.COOL_BLUE.wrap("*wheeze*") + " mine."),
                        new OptionsDialogue(
                                new Option("Enter anyway.", () -> player.getMovement().teleport(2404, 9415)),
                                new Option("Stay outside.", player::closeDialogue)
                        ));
            }
        });
        ObjectAction.register(534, 2404, 9414, 0, "use", (player, obj) -> player.getMovement().teleport(2412, 3060));

        /**
         * Boss room
         */
        ObjectAction.register(535, 2378, 9452, 0, "use", (player, obj) -> {
            if(player.smokeBossWarning) {
                player.dialogue(
                        new MessageDialogue(Color.DARK_RED.wrap("WARNING!") +
                                "<br> This is the lair of the Smoke Devil boss. <br> Are you sure you want to enter?").lineHeight(24),
                        new OptionsDialogue("Enter the boss area?",
                                new Option("Yes.", () -> player.getMovement().teleport(2376, 9452)),
                                new Option("Yes, and don't warn me again.", () -> {
                                    player.getMovement().teleport(2376, 9452);
                                    player.smokeBossWarning = false;
                                }),
                                new Option("No.", player::closeDialogue)
                        )
                );
            } else {
                player.getMovement().teleport(2376, 9452);
            }
        });
        ObjectAction.register(535, 2378, 9452, 0, "peek", (player, obj) -> {
            if(playerCount == 0)
                player.sendFilteredMessage("You look inside the crevice and see no adventurers inside the cave.");
            else
                player.sendFilteredMessage("You look inside the crevice and see " + playerCount + " adventurer" + (playerCount == 1 ? "" : "s") + " inside the cave.");
        });
        ObjectAction.register(535, 2378, 9452, 0, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.THERMONUCLEAR_SMOKE_DEVIL));
        ObjectAction.register(536, 2377, 9452, 0, "use", (player, obj) -> player.getMovement().teleport(2379, 9452));

        MapListener.registerBounds(DUNGEON_BOSS).onEnter(player -> playerCount++).onExit((player, logout) -> playerCount--);

        MapListener.registerBounds(DUNGEON_BOUNDS).onEnter(player -> player.addEvent(e -> {
            while(player.getPosition().inBounds(DUNGEON_BOUNDS)) {
                if(hasFaceMask(player))
                    return;
                player.hit(new Hit().fixedDamage(20));
                e.delay(50);
            }
        }));
    }
}
