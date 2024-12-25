package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.*;
import io.ruin.model.skills.construction.room.Room;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.skills.construction.Construction.forCurrentHouse;
import static io.ruin.model.skills.construction.Construction.forHouseOwnerOnly;

public class Garden {

    static {
        ObjectAction.register(Buildable.EXIT_PORTAL.getBuiltObjects()[0], "enter",
                forCurrentHouse((p, house) -> house.leave(p)));
        ObjectAction.register(Buildable.EXIT_PORTAL.getBuiltObjects()[0], "lock",
                forHouseOwnerOnly((p, house) -> {
                    house.setLocked(!house.isLocked());
                    p.dialogue(new MessageDialogue("Your house is now " + (house.isLocked() ? "locked" : "unlocked") + "."));
                }));

        ObjectAction.register(Buildable.TIP_JAR.getBuiltObjects()[0], "use", forCurrentHouse((player, house) -> {
            if (player.isInOwnHouse()) {
                if (house.getTips() > 0) {
                    player.dialogue(new MessageDialogue("Your tip jar currently has " + NumberUtils.formatNumber(house.getTips()) + " coins.<br>Would you like to withdraw them?"),
                            new OptionsDialogue("Withdraw coins?",
                                    new Option("Yes", () -> {
                                        int added = player.getInventory().add(COINS_995, house.getTips());
                                        if (added > 0) {
                                            house.setTips(house.getTips() - added);
                                            player.sendMessage("Withdrew " + NumberUtils.formatNumber(added) + " coins.");
                                        } else {
                                            player.sendMessage("Not enough space in your inventory.");
                                        }
                                    }),
                                    new Option("No")));
                } else {
                    player.dialogue(new MessageDialogue("Your tip jar is empty."));
                }
            } else {
                if (house.getOwner().getGameMode().isIronMan()) {
                    player.sendMessage("This house belongs to an Iron man; they cannot receive tips.");
                    return;
                }

                player.integerInput("Enter how many coins you'd like to tip the house owner:", amt -> house.addTip(player, amt));
            }
        }));
        ObjectAction.register(Buildable.TIP_JAR.getBuiltObjects()[0], "setup", forHouseOwnerOnly(Garden::sendJarDialogue));
        ObjectAction.register(Buildable.DUNGEON_ENTRANCE.getBuiltObjects()[0], "enter", Construction.forCurrentRoom((player, room) -> {
            Room below = room.getRoomBelow();
            if (below != null && below.getDefinition() == RoomDefinition.DUNGEON_STAIRS && below.getBuilt(Hotspot.DUNGEON_STAIRS) != null && Buildable.STAIRS.contains(below.getBuilt(Hotspot.DUNGEON_STAIRS))) {
                if (room.getHouse().getChallengeMode() == ChallengeMode.OFF && !player.isInOwnHouse()) {
                    player.sendMessage("You can't use this entrance until challenge mode is turned on.");
                } else {
                    player.getMovement().teleport(below.getAbsolutePosition(3,2));
                }
            } else {
                player.sendMessage("The entrance doesn't actually lead anywhere.");
            }
        }));
    }

    public static void sendJarDialogue(Player player, House house) {
        player.dialogue(new OptionsDialogue("Tip Jar options",
                new Option("Tip jar notifications: <shad=000000>" + (house.isTipJarNotifications() ? "<col=009900>Enabled" : "<col=ff0000>Disabled"), () -> {
                    house.setTipJarNotifications(!house.isTipJarNotifications());
                    sendJarDialogue(player, house);
                }),
                new Option("Close")
        ));
    }

}
