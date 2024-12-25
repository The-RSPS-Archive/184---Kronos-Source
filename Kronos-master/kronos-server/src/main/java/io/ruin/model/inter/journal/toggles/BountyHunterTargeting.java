package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.activities.wilderness.BountyHunter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Option;

public class BountyHunterTargeting extends JournalEntry {

    @Override
    public void send(Player player) {
        BountyHunter.Targeting targeting = player.getBountyHunter().targeting;
        send(player, "Targeting", targeting.name, targeting == BountyHunter.Targeting.DISABLED ? Color.RED : Color.GREEN);
    }

    @Override
    public void select(Player player) {
        if(player.wildernessLevel > 0) {
            player.sendMessage("You can't toggle bounty hunter targeting inside the wilderness.");
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        new Option("All Wilderness", () -> set(player, BountyHunter.Targeting.ALL)),
                        new Option("Disable Targeting", () -> set(player, BountyHunter.Targeting.DISABLED)),
                        new Option("Edgeville Only", () -> set(player, BountyHunter.Targeting.EDGEVILLE))
                )
        );
    }

    private void set(Player player, BountyHunter.Targeting targeting) {
        player.getBountyHunter().targeting = targeting;
        send(player);
    }

}