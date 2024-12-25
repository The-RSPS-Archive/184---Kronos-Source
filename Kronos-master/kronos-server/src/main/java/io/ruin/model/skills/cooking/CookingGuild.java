package io.ruin.model.skills.cooking;

import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.MaxCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class CookingGuild {

    private static final int HEAD_CHEF = 2658;
    private static final int GOLDEN_CHEF_HAT = 20205;
    private static final int CHEFS_HAT = 1949;
    private static final int COOKING_CAPE = StatType.Cooking.regularCapeId;
    private static final int MASTER_COOKING_CAPE = StatType.Cooking.masterCapeId;
    private static final int COOKING_CAPE_T = StatType.Cooking.trimmedCapeId;
    private static final int COOKING_HOOD = StatType.Cooking.hoodId;

    static {
        /**
         * Entrance
         */
        ObjectAction.register(24958, 3143, 3443, 0, "open", (player, obj) -> {
            if(player.getAbsY() <= 3443) {
                if(player.getStats().get(StatType.Cooking).currentLevel < 32) {
                    player.dialogue(new NPCDialogue(HEAD_CHEF, "Sorry. Only the finest chefs are allowed in here. Get your cooking level up " +
                            "to 32 and come back wearing a chef's hat."));
                    return;
                }

                int hat = player.getEquipment().getId(Equipment.SLOT_HAT);
                int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);

                boolean hasChefsHat = hat == CHEFS_HAT || hat == GOLDEN_CHEF_HAT || hat == COOKING_HOOD;
                boolean hasCookingCape = cape == COOKING_CAPE || cape == COOKING_CAPE_T || cape == MASTER_COOKING_CAPE;

                if (!hasChefsHat && !hasCookingCape && !MaxCape.wearing(player)) {
                    player.dialogue(new NPCDialogue(HEAD_CHEF, "You can't come in here unless you're wearing a chef's hat, or something like that."));
                    return;
                }
            }

            player.startEvent(event -> {
                player.lock();

                if(!player.isAt(obj.x, player.getAbsY())) {
                    player.stepAbs(obj.x, player.getAbsY(), StepType.FORCE_WALK);
                    event.delay(1);
                }
                GameObject opened = GameObject.spawn(24959, 3143, 3444, 0, obj.type, 2);
                obj.skipClipping(true).remove();
                player.step(0, player.getAbsY() <= 3443 ? 1 : -1, StepType.FORCE_WALK);
                event.delay(2);
                obj.restore().skipClipping(false);
                opened.remove();

                player.unlock();
            });
        });

        /**
         * Staircase
         */
        ObjectAction.register(2608, 3144, 3447, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, true);
        });
        ObjectAction.register(2609, 3144, 3447, 1, "climb", (player, obj) -> player.dialogue(
                new OptionsDialogue("Climb up or down the ladder?",
                        new Option("Climb Up.", () -> Ladder.climb(player, 3144, 3446, player.getHeight() + 1, true, true, true)),
                        new Option("Climb Down.", () -> Ladder.climb(player, 3144, 3449, player.getHeight() - 1, false, true, true))
                )));
        ObjectAction.register(2609, 3144, 3447, 1, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3144, 3446, player.getHeight() + 1, true, true, true);
        });
        ObjectAction.register(2609, 3144, 3447, 1, "climb-down", (player, obj) -> {
            Ladder.climb(player, 3144, 3449, player.getHeight() - 1, false, true, true);
        });
        ObjectAction.register(2610, 3144, 3447, 2, "climb-down", (player, obj) -> {
            Ladder.climb(player, 3144, 3449, player.getHeight() - 1, false, true, true);
        });
    }
}
