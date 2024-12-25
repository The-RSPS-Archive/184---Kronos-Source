package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Option;

public class SwitchGrading extends JournalEntry {

    public static void update(Player player) {
        player.getPacketSender().sendVarp(20001, player.switchGrading);
    }

    @Override
    public void send(Player player) {
        if(player.switchGrading == 0)
            send(player, "Switch Grading", "Disabled", Color.RED);
        else
            send(player, "Switch Grading", player.switchGrading + " Items", Color.GREEN);
        update(player);
    }

    @Override
    public void select(Player player) {
        if(player.switchGrading == 0) {
            player.dialogue(
                    new OptionsDialogue(
                            new Option("Enable Switch Grading", () -> set(player, -1)),
                            new Option("Keep Switch Grading Disabled", player::closeDialogue)
                    )
            );
        } else {
            player.dialogue(
                    new OptionsDialogue(
                            new Option("Change Switch Grading", () -> set(player, -1)),
                            new Option("Disable Switch Grading", () -> set(player, 0))
                    )
            );
        }
    }

    private void set(Player player, int items) {
        if(items == -1) {
            player.integerInput("How many items would you like to grade? (4-12)", amt -> {
                if(amt < 4 || amt > 12) {
                    player.retryIntegerInput("How many items would you like to grade? (4-12)");
                    return;
                }
                set(player, amt);
            });
            return;
        }
        player.switchGrading = items;
        send(player);
    }

}