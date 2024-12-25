package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

import static io.ruin.cache.ItemID.BLOOD_MONEY;

public class BloodyTokens {


    public static final int BLOODY_TOKENS = 13215;

    public static boolean exchange(Player player, Item item) {
        if(item.getId() == BLOOD_MONEY) {
            fromBloodMoney(player, item);
            return true;
        }
        if(item.getId() == BLOODY_TOKENS) {
            toBloodMoney(player, item);
            return true;
        }
        return false;
    }

    private static void fromBloodMoney(Player player, Item bloodMoney) {
        if(bloodMoney.getAmount() < 1000) {
            player.dialogue(new MessageDialogue("You need at least 1,000 blood money to exchange them for bloody tokens."));
            return;
        }
        player.dialogue(
                new OptionsDialogue("Exchange your blood money for bloody tokens?",
                        new Option("Yes", () -> {
                            int tokensAmount = bloodMoney.getAmount() / 1000;
                            int removeAmount = tokensAmount * 1000;
                            Item tokens = player.getInventory().findItem(BLOODY_TOKENS);
                            if(tokens != null) {
                                bloodMoney.incrementAmount(-removeAmount);
                                tokens.incrementAmount(tokensAmount);
                            } else {
                                int freeSlots = player.getInventory().getFreeSlots();
                                if(removeAmount == bloodMoney.getAmount())
                                    freeSlots++;
                                if(freeSlots == 0) {
                                    player.dialogue(new MessageDialogue("You don't have enough inventory space."));
                                    return;
                                }
                                bloodMoney.incrementAmount(-removeAmount);
                                player.getInventory().add(BLOODY_TOKENS, tokensAmount);
                            }
                            player.dialogue(new ItemDialogue().two(13315, 13218, "The bank exchanges your blood money for bloody tokens."));
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

    private static void toBloodMoney(Player player, Item tokens) {
        player.dialogue(
                new OptionsDialogue("Exchange your bloody tokens for blood money?",
                        new Option("Yes", () -> {
                            Item bloodMoney = player.getInventory().findItem(BLOOD_MONEY);
                            if(bloodMoney != null) {
                                int removeAmount = (Integer.MAX_VALUE - bloodMoney.getAmount()) / 1000;
                                if(removeAmount == 0) {
                                    player.dialogue(new MessageDialogue("Your blood money stack is too large!"));
                                    return;
                                }
                                removeAmount = Math.min(tokens.getAmount(), removeAmount);
                                tokens.incrementAmount(-removeAmount);
                                bloodMoney.incrementAmount(removeAmount * 1000);
                            } else {
                                int removeAmount = Integer.MAX_VALUE / 1000;
                                removeAmount = Math.min(tokens.getAmount(), removeAmount);
                                tokens.incrementAmount(-removeAmount);
                                player.getInventory().add(BLOOD_MONEY, removeAmount * 1000);
                            }
                            player.dialogue(new ItemDialogue().two(13315, 13218, "The bank exchanges your bloody tokens for blood money."));
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

}
