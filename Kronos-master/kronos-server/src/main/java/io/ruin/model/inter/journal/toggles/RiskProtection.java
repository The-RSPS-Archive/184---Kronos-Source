package io.ruin.model.inter.journal.toggles;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.IKOD;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

import java.util.ArrayList;
import java.util.Iterator;

import static io.ruin.cache.ItemID.COINS_995;

public class RiskProtection extends JournalEntry {

    private static final int TIER_ONE = 10_000;
    private static final int TIER_TWO = 25_000;
    private static final int TIER_THREE = 50_000;
    public static final RiskProtection INSTANCE = new RiskProtection();


    @Override
    public void send(Player player) {
        int tier = player.riskProtectionTier;
        send(player, "Risk Protection", protectionState(player), tier == 0 ? Color.RED : Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.dialogue(new OptionsDialogue(
                new Option("Toggle Risk Protection", () -> {
                    if(player.wildernessLevel > 0) {
                        player.sendMessage("You can't toggle this feature inside the wilderness.");
                        return;
                    }
                    int delay = player.riskProtectionExpirationDelay.remaining();
                    if(player.riskProtectionExpirationDelay.isDelayed()) {
                        if (delay >= 100) {
                            int minutes = delay / 100;
                            player.dialogue(new ItemDialogue().one(8792, "You can toggle your risk protection in " + minutes + " minute" + (minutes == 1 ? "" : "s") + "."));
                        } else {
                            int seconds = delay / 10 * 6;
                            player.dialogue(new ItemDialogue().one(8792, "You can toggle your risk protection in " + seconds + " second" + (seconds == 1 ? "" : "s") + "."));
                        }
                        return;
                    }
                    player.dialogue(new OptionsDialogue("What level of risk protection would you like?",
                            new Option("10,000+ Coins", () -> toggleRiskProtection(player, 1)),
                            new Option("25,000+ Coins", () -> toggleRiskProtection(player, 2)),
                            new Option("50,000+ Coins", () -> toggleRiskProtection(player, 3))
                    ));
                }),
                new Option("Forfeit Risk Protection", () -> {
                    if(player.riskProtectionTier == 0) {
                        player.sendMessage("You need to set your risk protection before attempting to forfeit it!");
                        return;
                    }
                    player.dialogue(new OptionsDialogue("Forfeit your Risk Protection?",
                            new Option("Yes, let anyone attack me!", () -> {
                                player.riskProtectionTier = 0;
                                player.riskProtectionExpirationDelay.delay(10 * 100); // 10 minutes
                                player.sendMessage(Color.DARK_RED.wrap("You've forfeit your risk protection for 10 minutes."));
                                send(player);
                            }),
                            new Option("No, I don't want to be ragged.", player::closeDialogue)
                    ));
                }),
                new Option("What is Risk Protection?", () -> player.sendScroll("Risk Protection",
                        "Risk protection is a toggleable feature to prevent raggers",
                        "or welfare PVPers from attacking players who are looking for",
                        "fights of equal risked wealth. You're able to toggle between",
                        "three different protection types:",
                        "",
                        Color.DARK_RED.wrap("Tier 1 -> Greater than 10,000 coins"),
                        Color.DARK_RED.wrap("Tier 2 -> Greater than 25,000 coins"),
                        Color.DARK_RED.wrap("Tier 3 -> Greater than 50,000 coins"),
                        "",
                        "You're unable to attack a player who has higher risk protection",
                        "than you have in risked wealth. If you attack a player who has",
                        "less wealth than your risk prevention, your risk prevention will",
                        "be forfeited for 10 minutes. During this period anybody is able",
                        "to attack you regardless of their risked wealth."))
        ));
    }

    public static void monitorRiskProtection(Player player) {
        updateRiskedBloodMoney(player);

        int riskProtectionValue = protectionValue(player);
        long risked = player.riskedBloodMoney;
        if(risked < riskProtectionValue) {
            if(risked < TIER_ONE)
                toggleRiskProtection(player, 10); // 10 will work as 0 blood money risk protection
            else if(risked < TIER_TWO)
                toggleRiskProtection(player, 1);
            else if(risked < TIER_THREE)
                toggleRiskProtection(player, 2);
        }
    }

    private static void toggleRiskProtection(Player player, int tier) {
        updateRiskedBloodMoney(player);

        long risked = player.riskedBloodMoney;
        if(risked < valueForTier(tier)) {
            player.sendMessage(Color.DARK_RED.wrap("Your risk protection value can't be set higher than your current risk."));
            monitorRiskProtection(player);
            return;
        }
        player.riskProtectionTier = tier;
        player.sendMessage(Color.DARK_RED.wrap("You now have a risk protection value up to " + NumberUtils.formatNumber(protectionValue(player)) + " Coins."));
//        if(player.journal == Journal.TOGGLES)
//            INSTANCE.send(player);
    }

    private String protectionState(Player player) {
        int tier = player.riskProtectionTier;
        if(tier == 0) return "Forfeited";
        else if(tier == 10) return "Unset";
        else return NumberUtils.formatNumber(protectionValue(player)) + "+ Coins";
    }

    public static int protectionValue(Player player) {
        int tier = player.riskProtectionTier;
        switch(tier) {
            case 1: return TIER_ONE;
            case 2: return TIER_TWO;
            case 3: return TIER_THREE;
            default: return 0;
        }
    }

    public static int valueForTier(int tier) {
        switch(tier) {
            case 1: return TIER_ONE;
            case 2: return TIER_TWO;
            case 3: return TIER_THREE;
            default: return 0;
        }
    }

    public static void updateRiskedBloodMoney(Player player) {
        boolean skulled = player.getCombat().isSkulled();
        boolean ultimateIronMan = player.getGameMode().isUltimateIronman();
        int keepCount = IKOD.getKeepCount(skulled, ultimateIronMan, !player.getCombat().highRiskSkull);

        ArrayList<Item> items = IKOD.getItems(player);
        ArrayList<Item> keepItems = new ArrayList<>(keepCount);
        int keepCountRemaining = keepCount;
        long value = 0;
        for(Iterator<Item> it = items.iterator(); it.hasNext(); ) {
            Item item = it.next();
            ItemDef def = item.getDef();
            if(!def.tradeable && item.getAttributeHash() <= 0)
                continue;
            if(!def.neverProtect && keepCountRemaining > 0) {
                int keepAmount = Math.min(item.getAmount(), keepCountRemaining);
                keepItems.add(new Item(item.getId(), keepAmount, item.copyOfAttributes()));
                keepCountRemaining -= keepAmount;
                item.incrementAmount(-keepAmount);
                if(item.getAmount() == 0) {
                    it.remove();
                    continue;
                }
            }
            value += valueOfItem(item);
        }
        player.riskedBloodMoney = value;
    }

    private static long valueOfItem(Item item) {
        if (item.getId() == COINS_995)
            return item.getAmount();
        long price = item.getDef().sigmundBuyPrice;
        return item.getAmount() * price;
    }

}
