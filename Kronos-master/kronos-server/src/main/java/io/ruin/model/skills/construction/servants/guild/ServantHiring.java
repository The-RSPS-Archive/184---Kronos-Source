package io.ruin.model.skills.construction.servants.guild;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.skills.construction.servants.ServantDefinition;
import io.ruin.model.stat.StatType;

import java.util.function.Function;

public class ServantHiring {

    static {
        for (ServantDefinition servant : ServantDefinition.values()) {
            NPCAction.register(servant.getNpcId(), 1, (player, npc) -> { // use option number instead of text since these are varp based npcs
                sendOptions(player, servant);
            });
        }
    }

    private static void sendOptions(Player player, ServantDefinition servant) {
        Function<String, String> format = servant == ServantDefinition.DEMON_BUTLER ? ServantDefinition::demonify : s -> s;
        ActionDialogue returnDialogue = new ActionDialogue(() -> sendOptions(player, servant));
        player.dialogue(new OptionsDialogue(
                new Option("What services do you offer?", () -> {
                    if (servant.isSawmill())
                        player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("I can take items to the bank, retrieve items from the bank, un-note items, and take logs to the sawmill.")), returnDialogue);
                    else
                        player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("I can take items to the bank, retrieve items from the bank, and un-note items.")), returnDialogue);
                }),
                new Option("How many items can you carry at once?", () -> {
                    player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("I may carry up to " + servant.getItemCapacity() + " items at once.")), returnDialogue);
                }),
                new Option("How much is your fee?", () -> {
                    player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("I will charge " + NumberUtils.formatNumber(servant.getPayment()) + " coins for every 8 tasks I perform.")), returnDialogue);
                }),
                new Option("You're hired!", () -> {
                    if (player.house == null) {
                        player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("You don't have a house! Come back when you do.")),
                                returnDialogue);
                        return;
                    }
                    if (player.house.getServantSave().getHiredServant() != null) {
                        player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("You already have another member of the guild working for you. Speak to the Chief if you want to fire them.")),
                                returnDialogue);
                        return;
                    }
                    if (!player.house.canHaveServant()) {
                        player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("Sorry, but I can't work for you unless you have 2 bedrooms in 2 beds in your house.")),
                                returnDialogue);
                        return;
                    }
                    if (player.getStats().get(StatType.Construction).fixedLevel < servant.getLevelReq()) {
                        player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("Sorry, but I only work for more experienced house owners.")),
                                new MessageDialogue("A Construction level of at least " + servant.getLevelReq() + " is required to hire that servant."),
                                returnDialogue);
                        return;
                    }
                    player.dialogue(new NPCDialogue(servant.getNpcId(), format.apply("Perfect. I'll make my way to your house.")));
                    player.house.getServantSave().hire(servant);
                    Config.HIRED_SERVANT.set(player, servant.getVarpbitValue());
                }),
                new Option("That's all, thanks.")
        ));
    }

}
