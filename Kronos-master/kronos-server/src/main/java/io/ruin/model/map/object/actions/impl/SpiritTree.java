package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.skills.farming.patch.impl.SpiritTreePatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiritTree {

    static {
        for (int id : Arrays.asList(1293, 1294, 1295)) {
            ObjectAction.register(id, 1, (player, obj) -> open(player));
            ObjectAction.register(id, 2, (player, obj) -> open(player));
        }

        ObjectAction.register(2186, 1, (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.getPacketSender().fadeOut();
                event.delay(2);
                player.getMovement().teleport(2503, 3192, 0);
                event.delay(2);
                player.getPacketSender().fadeIn();
                player.unlock();
                player.dialogue(new MessageDialogue("You make your way through the maze."));
            });
        });

        NPCAction toMaze = (player, npc) -> player.startEvent(event -> {
            player.lock();
            player.sendMessage("Elkoy leads you through the maze.");
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(2515, 3161, 0);
            event.delay(2);
            player.getPacketSender().fadeIn();
            player.unlock();
            player.dialogue(new NPCDialogue(npc,"Here we are."));
        });

        NPCAction.register(4968, "follow", toMaze);
        NPCAction.register(4968, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "I can lead you through the maze, if you'd like."),
                    new OptionsDialogue(
                            new Option("Yes, please.", () -> {
                                toMaze.handle(player, npc);
                            }),
                            new Option("No thanks.", () -> {})
                    ));
        });
    }

    private static List<PatchData> SPIRIT_TREE_PATCHES = Arrays.asList(PatchData.PORT_SARIM_SPIRIT_TREE, PatchData.BRIMHAVEN_SPIRIT_TREE, PatchData.ETCETERIA_SPIRIT_TREE, PatchData.ZEAH_SPIRIT_TREE);
    private static String[] PATCH_NAMES = {"Port Sarim", "Brimhaven", "Etceteria", "Zeah"};

    private static List<Option> getOptions(Player player) {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Tree Gnome Village", () -> teleport(player, 2542, 3170, 0)));
        options.add(new Option("Gnome Stronghold", () -> teleport(player, 2462, 3444, 0)));
        options.add(new Option("Battlefield of Khazard", () -> teleport(player, 2555, 3259, 0)));
        options.add(new Option("Grand Exchange", () -> teleport(player, 3184, 3508, 0)));
        int i = 0;
        for (PatchData farmTree : SPIRIT_TREE_PATCHES) {
            SpiritTreePatch patch = (SpiritTreePatch) player.getFarming().getPatch(farmTree.getObjectId());
            String name = PATCH_NAMES[i++];
            Runnable action;
            if (patch.canTeleport()) {
                action = () -> teleport(player, patch.getTeleportPosition().getX(), patch.getTeleportPosition().getY(), patch.getTeleportPosition().getZ());
            } else {
                name = "<str>" + name;
                action = () -> player.dialogue(new MessageDialogue("You do not have a fully grown spirit tree at that location."));
            }
            options.add(new Option(name, action));

        }
        options.add(getHouseOption(player));
        return options;
    }

    private static Option getHouseOption(Player player) {
        String houseText = "Played-owned House";
        Runnable houseAction;
        if (player.house == null) {
            houseText = "<str>" + houseText;
            houseAction = () -> player.sendMessage("You don't have a house to teleport to.");
        } else if (player.house.getSpiritTreePosition() == null) {
            houseText = "<str>" + houseText;
            houseAction = () -> player.sendMessage("Your house doesn't have a spirit tree planted.");
        } else {
            houseAction = () -> houseTeleport(player);
        }
        return new Option(houseText, houseAction);
    }

    public static void open(Player player) {
        OptionScroll.open(player, "Spirit Tree Locations", getOptions(player));
    }

    private static void teleport(Player player, int x, int y, int z) {
        player.getMovement().startTeleport(event -> {
            player.animate(828);
            player.dialogue(new ItemDialogue().one(6063, "You place your hands on the dry tough bark of the<br>" +
                    "spirit tree, and feel a surge of energy run through<br>" +
                    "your veins.").hideContinue());
            event.delay(2);
            player.closeDialogue();
            player.getMovement().teleport(x,y,z);
        });
    }

    private static void houseTeleport(Player player) {
        player.lock();
        player.startEvent(event -> {
            player.animate(828);
            player.dialogue(new ItemDialogue().one(6063, "You place your hands on the dry tough bark of the<br>" +
                    "spirit tree, and feel a surge of energy run through<br>" +
                    "your veins.").hideContinue());
            event.delay(1);
            player.house.buildAndEnter(player, player.house.getSpiritTreePosition(), false);
        });
    }

}
