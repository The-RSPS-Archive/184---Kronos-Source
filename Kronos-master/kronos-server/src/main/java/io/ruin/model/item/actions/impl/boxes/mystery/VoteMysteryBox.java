package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Icon;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class VoteMysteryBox {

    private static final LootTable PVP_VOTING_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(BLOOD_MONEY, 500, 2000, 2000),                  // 2000 Blood money
            new LootItem(6914, 1, 40),                             // Masters wand
            new LootItem(4151, 1, 40),                             // Abyssal whip
            new LootItem(20128, 1, 40),                            // Hood of darkness
            new LootItem(20131, 1, 40),                            // Robe top of darkness
            new LootItem(20137, 1, 40),                            // Robe bottom of darkness
            new LootItem(4153, 1, 40),                             // Granite maul
            new LootItem(6528, 1, 40),                             // Obsidian maul
            new LootItem(10887, 1, 40),                            // Barrelchest anchor
            new LootItem(1249, 1, 40),                             // Dragon spear
            new LootItem(11128, 1, 40),                            // Berserker necklace
            new LootItem(4716, 1, 40),                             // Dharok's helm
            new LootItem(4718, 1, 40),                             // Dharok's greataxe
            new LootItem(4720, 1, 40),                             // Dharok's platebody
            new LootItem(4722, 1, 40),                             // Dharok's platelegs
            new LootItem(4708, 1, 40),                             // Ahrim's hood
            new LootItem(4712, 1, 40),                             // Ahrim's robetop
            new LootItem(4714, 1, 40),                             // Ahrim's robeskirt
            new LootItem(6585, 1, 40),                             // Dragon boots
            new LootItem(12831, 1, 40),                            // Blessed Spirit shield
            new LootItem(6733, 1, 40),                             // Archer ring
            new LootItem(6735, 1, 40),                             // Warrior ring
            new LootItem(6920, 1, 40),                             // Infinity boots
            new LootItem(6585, 1, 40),                              // Amulet of fury
            new LootItem(12397, 1, 40),                            // Royal Crown
            new LootItem(12791, 1, 5).broadcast(Broadcast.GLOBAL), // Rune Pouch
            new LootItem(11941, 1, 5).broadcast(Broadcast.GLOBAL), // Royal Sceptre
            new LootItem(12846, 1, 5).broadcast(Broadcast.GLOBAL), // Bounty hunter teleport
            new LootItem(1505, 1, 5).broadcast(Broadcast.GLOBAL),  // Obelisk Teleport
            new LootItem(1038, 1, 1).broadcast(Broadcast.GLOBAL),  // Red party hat
            new LootItem(1040, 1, 1).broadcast(Broadcast.GLOBAL),  // Yellow party hat
            new LootItem(1042, 1, 1).broadcast(Broadcast.GLOBAL),  // Blue party hat
            new LootItem(1044, 1, 1).broadcast(Broadcast.GLOBAL),  // Green party hat
            new LootItem(1046, 1, 1).broadcast(Broadcast.GLOBAL),  // Purple party hat
            new LootItem(1048, 1, 1).broadcast(Broadcast.GLOBAL),  // White  party hat
            new LootItem(11862, 1, 1).broadcast(Broadcast.GLOBAL), // Black party hat
            new LootItem(11863, 1, 1).broadcast(Broadcast.GLOBAL), // Rainbow party hat
            new LootItem(12399, 1, 1).broadcast(Broadcast.GLOBAL)  // Partyhat & specs
    );

    private static final LootTable ECO_VOTING_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(COINS_995, 50_000, 300_000, 500),                  // Gold
            new LootItem(6914, 1, 80),      // Masters wand
            new LootItem(4151, 1, 80),      // Abyssal whip
            new LootItem(20128, 1, 80),     // Hood of darkness
            new LootItem(20131, 1, 80),     // Robe top of darkness
            new LootItem(20137, 1, 80),     // Robe bottom of darkness
            new LootItem(4153, 1, 80),      // Granite maul
            new LootItem(6528, 1, 80),      // Obsidian maul
            new LootItem(10887, 1, 80),     // Barrelchest anchor
            new LootItem(1249, 1, 80),      // Dragon spear
            new LootItem(11128, 1, 80),     // Berserker necklace
            new LootItem(4716, 1, 80),      // Dharok's helm
            new LootItem(4718, 1, 80),      // Dharok's greataxe
            new LootItem(4720, 1, 80),      // Dharok's platebody
            new LootItem(4722, 1, 80),      // Dharok's platelegs
            new LootItem(4708, 1, 80),      // Ahrim's hood
            new LootItem(4712, 1, 80),      // Ahrim's robetop
            new LootItem(4714, 1, 80),      // Ahrim's robeskirt
            new LootItem(6585, 1, 80),      // Dragon boots
            new LootItem(12831, 1, 80),     // Blessed Spirit shield
            new LootItem(6733, 1, 80),      // Archer ring
            new LootItem(6735, 1, 80),      // Warrior ring
            new LootItem(6920, 1, 80),      // Infinity boots
            new LootItem(6585, 1, 80),      // Amulet of fury
            new LootItem(21295, 1, 1).broadcast(Broadcast.GLOBAL), //Infernal Cape
            new LootItem(12397, 1, 1).broadcast(Broadcast.GLOBAL),   // Royal Crown
            new LootItem(21902, 1, 4).broadcast(Broadcast.GLOBAL), //Dragon crossbow
            new LootItem(12929, 1, 5).broadcast(Broadcast.GLOBAL), //Serpentine helm
            new LootItem(11791, 1, 6).broadcast(Broadcast.GLOBAL), //Staff of the dead
            new LootItem(6889, 1, 6).broadcast(Broadcast.GLOBAL), //Mage's Book
            new LootItem(22109, 1, 7).broadcast(Broadcast.GLOBAL), //Ava's Assembler
            new LootItem(12791, 1, 7).broadcast(Broadcast.GLOBAL), //Rune Pouch
            new LootItem(12954, 1, 20).broadcast(Broadcast.GLOBAL), //Dragon Defender
            new LootItem(21301, 1, 20).broadcast(Broadcast.GLOBAL), //Obsidian Platebody
            new LootItem(21304, 1, 20).broadcast(Broadcast.GLOBAL), //Obsidian Platelegs
            new LootItem(21298, 1, 20).broadcast(Broadcast.GLOBAL) //Obsidian Helm
    );

    public static void open(Player player, Item item) {
        InterfaceHandler.register(Interface.SUPER_MYSTERY_BOX, h -> {
            h.actions[7] = (SimpleAction) VoteMysteryBox::spin;
            h.actions[19] = (SimpleAction) VoteMysteryBox::claimReward;
            h.actions[21] = (SimpleAction) VoteMysteryBox::discardReward;
            h.actions[23] = (SimpleAction) VoteMysteryBox::openRewards;
        });

        if(player.isVisibleInterface(Interface.SUPER_MYSTERY_BOX)) {
            player.sendMessage("You need to claim or discard your reward before doing this!");
            return;
        }

        if(player.claimedBox) {
            player.getBox().clear();
            generateReward(player);
            player.getBox().sendUpdates();
            player.openInterface(InterfaceType.MAIN, 702);
            player.getPacketSender().sendClientScript(10034, "ssii", "Spins", "Get ready to test your luck with our wheel of fortune! Click the \"spin\" button when you're ready. There's all sorts of items to be won within this mystery spinning box!<br><br>If you wish to skip the rolling, you can click \"Spin\" again.<br><br><col=ffff00>Will you walk away with riches... or with rubbish? Good luck!", 15, 0);
        } else {
            player.getBox().sendUpdates();
            player.getPacketSender().sendClientScript(10034, "ssii", "Spins", "Get ready to test your luck with our wheel of fortune! Click the \"spin\" button when you're ready. There's all sorts of items to be won within this mystery spinning box!<br><br>If you wish to skip the rolling, you can click \"Spin\" again.<br><br><col=ffff00>Will you walk away with riches... or with rubbish? Good luck!", 15, 1);
        }
    }

    private static void generateReward(Player player) {
        LootTable table = ECO_VOTING_BOX_TABLE;
        for(int i = 0; i < 24; i ++)
            player.getBox().add(table.rollItem());
        Item reward = player.getBox().get(15);
        if(reward == null)
            generateReward(player);
    }

    private static void spin(Player player) {
        Item voteMysteryBox = player.getInventory().findItem(6829);
        if(voteMysteryBox == null) {
            player.closeInterface(InterfaceType.MAIN);
            return;
        }
        player.claimedBox = false;
        voteMysteryBox.remove();
    }

    private static void claimReward(Player player) {
        Item reward = player.getBox().get(15);
        player.getInventory().add(reward);
        player.claimedBox = true;
        player.sendMessage("You get " + reward.getDef().descriptiveName + " from the Vote Mystery Box.");
        if (reward.lootBroadcast != null)
            Broadcast.GLOBAL.sendNews(Icon.MYSTERY_BOX, "Voting Mystery Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
        player.getBox().clear();
        if(player.isVisibleInterface(Interface.SUPER_MYSTERY_BOX))
            player.closeInterface(InterfaceType.MAIN);
    }

    private static void openRewards(Player player) {
        player.openInterface(InterfaceType.MAIN, VIEW_REWARDS_WIDGET);
        player.closeInterface(InterfaceType.INVENTORY);
        updateRewards(player, ECO_VOTING_BOX_TABLE);
    }

    private static void discardReward(Player player) {
        player.closeInterface(InterfaceType.MAIN);
        player.claimedBox = true;
        player.sendMessage("You discard your Vote Mystery Box reward.");
        player.getBox().clear();
    }


    static final int VIEW_REWARDS_WIDGET = 713;
    private static final int MAX_VIEW_OFFERS = 50;

    static {
        ItemAction.registerInventory(6829, "open", VoteMysteryBox::open);

        InterfaceHandler.register(VIEW_REWARDS_WIDGET, h -> {
            h.actions[96] = (SimpleAction) VoteMysteryBox::openRewards;
            h.actions[100] = (SimpleAction) SuperMysteryBox::openRewards;
            h.actions[412] = (SimpleAction) SummerMysteryBox::openRewards;
        });
    }

    static void updateRewards(Player player, LootTable rewardsTable) {
        List<LootItem> rewards = findRewards(rewardsTable);

        for (int index = 0; index < MAX_VIEW_OFFERS; index++) {
            hideReward(player, index, true);
        }

        for (int index = 0; index < rewards.size(); index++) {
            updateReward(player, index, rewards.get(index));
            hideReward(player, index, false);
        }

        int scrollBarWidgetId = 104;
        int scrollContainerWidgetId = 105;
        player.getPacketSender().sendClientScript(
                30, "ii",
                VIEW_REWARDS_WIDGET << 16 | scrollBarWidgetId,
                VIEW_REWARDS_WIDGET << 16 | scrollContainerWidgetId
        );

    }

    private static void hideReward(Player player, int index, boolean hidden) {
        player.getPacketSender().sendClientScript(69, "ii", hidden ? 1 : 0, VIEW_REWARDS_WIDGET << 16 | (106 + (6 * index)));
    }

    private static void updateReward(Player player, int index, LootItem offer) {
        double tableProbability = ECO_VOTING_BOX_TABLE.tables.get(0).weight / ECO_VOTING_BOX_TABLE.totalWeight;
        double probabilityInTable = offer.weight / ECO_VOTING_BOX_TABLE.tables.get(0).totalWeight;
        String rarity = "1/" + (int) (1 / (probabilityInTable * tableProbability));
        int containerWidgetId = 108 + (index * 6);
        int itemContainerId = 1100 + index;
        int ItemNameId = 109 + (index * 6);
        int amountToGetId = 110 + (index * 6);
        int rarityId = 111 + (index * 6);

        player.getPacketSender().sendClientScript(
                149, "IviiiIsssss",
                VIEW_REWARDS_WIDGET << 16 | containerWidgetId, itemContainerId,
                4, 7, 1, -1, "", "", "", "", ""
        );
        player.getPacketSender().sendItems(VIEW_REWARDS_WIDGET, containerWidgetId, itemContainerId, offer.toItem());
        player.getPacketSender().sendString(VIEW_REWARDS_WIDGET, ItemNameId, ItemDef.get(offer.id).name);
        player.getPacketSender().sendString(VIEW_REWARDS_WIDGET, amountToGetId, offer.max == 1 ? "" : (formatPrice(offer.min) + " - " + formatPrice(offer.max)));
        player.getPacketSender().sendString(VIEW_REWARDS_WIDGET, rarityId, "");
    }

    private static List<LootItem> findRewards(LootTable table) {
        int viewable = MAX_VIEW_OFFERS;
        if(table.equals(SummerMysteryBox.summerMboxLoot))
            viewable = 36;
        return Arrays.stream(table.tables.get(0).items)
                .sorted(Comparator.comparingInt(rewardA -> rewardA.weight)).limit(viewable)
                .collect(Collectors.toList());
    }

    private static String formatPrice(long price) {
        if (price > 9_999_999) {
            return NumberUtils.formatNumber(price / 1000_000) + "M";
        } else if (price > 99_999) {
            return NumberUtils.formatNumber(price / 1000) + "K";
        }

        return NumberUtils.formatNumber(price);
    }
}
