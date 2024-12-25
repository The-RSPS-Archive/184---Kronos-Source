package io.ruin.model.activities.summerevent;

import io.ruin.cache.Icon;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/20/2020
 */
public class SummerShrine {
    public static boolean EVENT_ENABLED = true;

    public static int SHRINE = 50010;
    public static int TOKENS = 30219;

    private static int getTokens(Player player) {
        return player.getInventory().count(TOKENS);
    }
    private static boolean checkTime(Player player) {
        if (player.lastSacrifice < System.currentTimeMillis() - 86400000) {
            return true;
        }
        return false;
    }

    private static void sacrificeTokens(Player player) {
        if (!checkTime(player)) {
            long remaining = player.lastSacrifice + 86400000 - System.currentTimeMillis();
            int hours = (int) ((remaining % 86400000) / 3600000);
            int minutes = (int) ((remaining % 3600000) / 60000);
            int seconds = (int) ((remaining % 60000) / 1000);
            player.sendMessage("You can use the Shrine again in: "+ hours + " hours, " + minutes + " and " + seconds + " seconds.");
            player.dialogue(new PlayerDialogue("It's not working.. Maybe I should<br>" +
                    "try again tomorrow").animate(567));
        } else {
            Item reward;
            int maxTokensUsable = 1000;
            int tokensAvailable = getTokens(player) > maxTokensUsable ? 1000 : getTokens(player);
            int tokensRemaining = getTokens(player) > maxTokensUsable ? getTokens(player) - 1000 : 0;
            if (tokensAvailable < 1) {
                player.dialogue(new PlayerDialogue("It looks like I don't have any Summer Tokens...").animate(567));
                return;
            }
            player.dialogue(new ItemDialogue().one(TOKENS, "You have sacrificed "+tokensAvailable+" tokens"));
            player.lastSacrifice = System.currentTimeMillis();
            player.animate(791);
            player.getInventory().remove(TOKENS, tokensAvailable);
            int rolls = 0;
            System.out.println(tokensAvailable);
            if (tokensAvailable > 1)
                rolls++;
            if (tokensAvailable >= 250)
                rolls++;
            if (tokensAvailable >= 500)
                rolls++;
            if (tokensAvailable >= 750)
                rolls++;
            if (tokensAvailable == 1000)
                rolls++;
            System.out.println("Rolls: "+rolls);
            for (int i = 0; i < rolls; i++) {
                reward = ShrineLoot.shrineLoot.rollItem();
                player.getInventory().addOrDrop(reward);
                if (ShrineLoot.shrineLoot.getWeight(reward) == 1) {
                    Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Summer Shrine Loot", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
                }
            }
        }
    }



    static {
        ObjectAction.register(SHRINE, "Sacrifice", (player, obj) -> {
            if (!EVENT_ENABLED) {
                player.dialogue(new PlayerDialogue("It looks like the event has ended...").animate(567));
                return;
            }
            player.dialogue(new ItemDialogue().one(TOKENS, "You can use the tokens you have gathered from skilling<br>" +
                    "or killing npcs here for a chance at some limited-time<br>" +
                    "rewards. You can only do this once every<br>" +
                    "24 hours."),
                    new OptionsDialogue(
                            new Option("Yes!", () -> {
                                sacrificeTokens(player);
                            }),
                            new Option("No thanks.", player::closeDialogue)
                    )
            );

        });
    }
}
