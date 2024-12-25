package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Option;

public class DragSetting extends JournalEntry {

    public static void update(Player player) {
        player.getPacketSender().sendVarp(20000, player.dragSetting);
    }

    @Override
    public void send(Player player) {
        if(player.dragSetting == 5)
            send(player, "Drag Setting", "5 (OSRS)", Color.GREEN);
        else if(player.dragSetting == 10)
            send(player, "Drag Setting", "10 (Pre-EoC)", Color.GREEN);
        else
            send(player, "Drag Setting", player.dragSetting + " (Custom)", Color.GREEN);
        update(player);
    }

    @Override
    public void select(Player player) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("5 (OSRS)", () -> set(player, 5)),
                        new Option("10 (Pre-EoC)", () -> set(player, 10)),
                        new Option("Custom", () -> {
                            player.integerInput("Enter desired drag setting: (0-50)", drag -> {
                                if(drag < 0 || drag > 50) {
                                    player.retryIntegerInput("Invalid drag setting, please try again: (0-50)");
                                    return;
                                }
                                set(player, drag);
                            });
                        })
                )
        );
    }

    private void set(Player player, int drag) {
        player.dragSetting = drag;
        send(player);
    }

}