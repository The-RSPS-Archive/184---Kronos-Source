package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.ArrayUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class Benny {

    private static final String[] SHOUTS = {
            "Get your " + World.type.getWorldName() + " Herald here!",
            World.type.getWorldName() + " Herald, on sale here!",
            "Read all about it!",
            "Extra! Extra! Read all about it!",
            "Buy your " + World.type.getWorldName() + " Herald now!"
    };

    static {
        NPCAction.register(5216, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Would you like a " + World.type.getWorldName() + " Herald?"),
                    new OptionsDialogue(
                            new Option("Yes please.", () -> player.dialogue(new PlayerDialogue("Yes please."),
                                    new ActionDialogue(() -> {
                                        npc.faceTemp(player);
                                        player.dialogue(new ItemDialogue().one(11169, "Benny gives you a " + World.type.getWorldName() + " Herald."));
                                        player.getInventory().addOrDrop(11169, 1); })
                            )),
                            new Option("Not right now.", () -> player.dialogue(new PlayerDialogue("Not right now.")))
                    )
            );
        });

        ItemAction.registerInventory(11169, "view-updates", (player, item) -> player.dialogue(new OptionsDialogue("View the updates section on our forums?",
                new Option("Yes", () -> player.openUrl(World.type.getWorldName() + " Updates", "https://community.kronos.rip/index.php?forums/news-updates.2/")),
                new Option("No", player::closeDialogue))));

        SpawnListener.register(ArrayUtils.of("benny"), npc -> npc.addEvent(e -> {
            while (true) {
                e.delay(20);
                npc.forceText(SHOUTS[Random.get(4)]);
            }
        }));
    }
}
