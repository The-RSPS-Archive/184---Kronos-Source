package io.ruin.model.entity.npc.actions.wilderness;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemNPCAction;

import static io.ruin.cache.ItemID.COINS_995;

public enum Piles {
    /**
     * Ore
     */
    IRON_ORE(440),
    COAL(453),
    GOLD_ORE(444),
    MITHRIL_ORE(447),
    ADAMANT_ORE(449),
    RUNITE_ORE(451),

    /**
     * Logs
     */
    YEW_LOGS(1515),
    MAGIC_LOGS(1513),

    /**
     * Fish
     */
    DARK_CRAB(11936),
    RAW_DARK_CRAB(11934),

    /**
     * Bars
     */

    IRON_BAR(2351),
    STEEL_BAR(2353),
    GOLD_BAR(2355),
    MITHRIL_BAR(2357),
    ADAMANT_BAR(2359),
    RUNITE_BAR(2363),

    /*
     * Wilderness Resources
     */

    RAW_MOLTEN_EEL(30086),
    COOKED_MOLTEN_EEL(30089),
    CORRUPTED_ORE(30109),
    CORRUPTED_BAR(30112)
    ;


    public final int itemID;

    Piles(int itemID) {
        this.itemID = itemID;
    }

    public static final int EXCHANGE_RATE = 50;

    private static void talk(Player player, NPC npc) {
        String currencyName = "50 coins";
        player.dialogue(
                new NPCDialogue(npc, "Hello. I can convert items to banknotes, for " + currencyName +"<br>per item. Just hand me the items you'd like me to<br>convert.").animate(590),
                new OptionsDialogue(
                        new Option("Who are you?", () -> player.dialogue(
                                new PlayerDialogue("Who are you?"),
                                new NPCDialogue(npc, "I'm Piles. I lived in Draynor Village when I was<br>young, where I saw three men working in the market,<br>converting items to banknotes.").animate(569),
                                new NPCDialogue(npc, "Their names were Niles, Miles and Giles. I'm trying to<br>be like them, so I've changed my name and started this<br>business here.").animate(569),
                                new PlayerDialogue("Thanks").animate(575)
                        )),
                        new Option("Thanks", () -> player.dialogue(new PlayerDialogue("Thanks")))
                )
        );
    }

    static {
        NPCAction.register(13, "talk-to", Piles::talk);
        for (Piles exchangeableItem : values())
            ItemDef.get(exchangeableItem.itemID).allowPilesToNote = true;
        ItemNPCAction.register("piles", (player, item, npc) -> {
            if(item != null && item.getDef().allowPilesToNote) {
                int amountOfItems = item.count();
                int cost = amountOfItems * EXCHANGE_RATE;
                String currencyName = "gold coins";
                int currencyId = COINS_995;
                player.dialogue(
                        new YesNoDialogue("Are you sure you want to do this?", "Pay " + NumberUtils.formatNumber(cost) + " " + currencyName + " to note " + amountOfItems + " " + item.getDef().name + "?", currencyId, cost, () -> {
                            if (!player.getInventory().contains(currencyId, cost)) {
                                player.dialogue(new NPCDialogue(npc, "Unfortunately, you don't have enough " + currencyName + " right now to do that.").animate(611));
                                return;
                            }
                            player.getInventory().remove(currencyId, cost);
                            player.getInventory().remove(item.getId(), amountOfItems);
                            player.getInventory().add(item.getDef().notedId, amountOfItems);
                            player.dialogue(new ItemDialogue().one(item.getId(), "Piles converts your items to banknotes."));
                        })
                );
            } else {
                player.dialogue(new NPCDialogue(npc, "Sorry, I wasn't expecting anyone to want to convert<br>that sort of item, so I haven't any banknotes for it.").animate(611));
            }
        });
    }
}