package io.ruin.model.skills.crafting;

import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.MaxCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class CraftingGuild {

    private static final int BROWN_APRON = 1757;
    private static final int GOLDEN_APRON = 20208;
    private static final int CRAFTING_CAPE = 9780;
    private static final int CRAFTING_CAPE_T = 9781;

    static {
        /**
         * Entrance
         */
        ObjectAction.register(14910, 2933, 3289, 0, "open", (player, obj) -> {
            if(player.getAbsY() >= 3289) {
                if(player.getStats().get(StatType.Crafting).currentLevel < 40) {
                    player.dialogue(new NPCDialogue(5810, "Sorry, but you need level 40 Crafting to get in there."));
                    return;
                }

                Item apron = player.getEquipment().get(Equipment.SLOT_CHEST);
                boolean hasApron = apron != null && (apron.getId() == BROWN_APRON || apron.getId() == GOLDEN_APRON);
                boolean hasCraftingCape = player.getEquipment().getId(Equipment.SLOT_CAPE) == CRAFTING_CAPE || player.getEquipment().getId(Equipment.SLOT_CAPE) == CRAFTING_CAPE_T;

                if (!MaxCape.wearing(player) && !hasApron && !hasCraftingCape) {
                    player.dialogue(new NPCDialogue(5810, "Where's your brown apron? You can't come in here unless you're wearing one."));
                    return;
                }
            }

            player.startEvent(event -> {
                player.lock();
                GameObject opened = GameObject.spawn(1539, 2933, 3288, 0, obj.type, 0);
                obj.skipClipping(true).remove();
                if(player.getAbsY() >= 3289)
                    player.dialogue(new NPCDialogue(5810, "Welcome to the Guild of Master Craftsmen."));
                player.step(0, player.getAbsY() >= 3289 ? -1 : 1, StepType.FORCE_WALK);
                event.delay(2);
                obj.restore().skipClipping(false);
                opened.remove();
                player.unlock();
            });
        });

        /**
         * Staircase
         */
        ObjectAction.register(9582, 2931, 3282, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2933, 3282, 1));
        ObjectAction.register(9584, 2932, 3282, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2932, 3281, 0));
    }
}
