package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.ItemUpgrading;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.NpcID.HORVIK;

public class Horvik {

    static {
        /*
         * Upgrading
         */
        List<String> upgradeInfo = new ArrayList<>();
        upgradeInfo.add("To upgrade/imbue an item, simply use the required item on");
        upgradeInfo.add("Horvik. These are the items he's able to assist you with:");
        upgradeInfo.add("");
        for(ItemUpgrading upgrade : ItemUpgrading.values()) {
            String currencyName = "Coins";
            int cost = upgrade.coinUpgradeCost;
            int currencyId = COINS_995;
            upgradeInfo.add(ItemDef.get(upgrade.regularId).name + " -> " + ItemDef.get(upgrade.upgradeId).name + "  <col=800000> " + NumberUtils.formatNumber(cost));
            ItemAction.registerInventory(upgrade.upgradeId, "uncharge", (player, item) -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "You will NOT get your " + currencyName + " back.", item, () -> {
                    item.setId(upgrade.regularId);
                    player.dialogue(new ItemDialogue().one(upgrade.regularId, "You release the energy from the item."));
                }));
            });
            ItemNPCAction.register(upgrade.regularId, HORVIK, (player, item, npc) -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Upgrade your " + item.getDef().name + " for " + cost + " " + currencyName + "?", new Item(upgrade.upgradeId, 1), () -> {
                    Item bloodMoney = player.getInventory().findItem(currencyId);
                    if(bloodMoney == null || bloodMoney.getAmount() < cost) {
                        player.dialogue(new NPCDialogue(npc, "You don't have enough " + currencyName + " for me to upgrade that."));
                        return;
                    }
                    bloodMoney.incrementAmount(-cost);
                    item.setId(upgrade.upgradeId);
                    player.dialogue(new ItemDialogue().one(upgrade.upgradeId, "Your " + item.getDef().name + " has been upgraded."));
                }));
            });
        }
        String[] upgradeInfoArray = upgradeInfo.toArray(new String[0]);
        NPCAction.register(HORVIK, "upgrade-items", (player, item) -> player.sendScroll("<col=800000>Item Upgrades", upgradeInfoArray));
        /*
         * Repairing
         */
        NPCAction.register(HORVIK, "repair-items", Horvik::repairAll);
        for(ItemBreaking brokenItem : ItemBreaking.values()) {
            ItemAction.registerInventory(brokenItem.brokenId, "fix", (player, item) -> repairItem(player, item, brokenItem));
            ItemItemAction.register(COINS_995, brokenItem.brokenId, (player, primary, secondary) -> repairItem(player, secondary, brokenItem));
            ItemNPCAction.register(brokenItem.brokenId, HORVIK, (player, item, npc) -> repairItem(player, item, brokenItem));
        }
        /*
         * Zamorakian spear/hasta conversion
         */
        for(int id : new int[]{11824, 11889})
            ItemNPCAction.register(id, HORVIK, Horvik::convertZamorakianWeapon);
    }

    private static void repairAll(Player player, NPC npc) {
        int totalPrice = 0;
        ArrayList<Item> brokenItems = new ArrayList<>();
        for(Item item : player.getInventory().getItems()) {
            if(item == null)
                continue;
            ItemBreaking brokenItem = item.getDef().brokenFrom;
            if(brokenItem == null)
                continue;
            totalPrice += brokenItem.coinRepairCost;
            brokenItems.add(item);
        }
        if(brokenItems.isEmpty()) {
            player.dialogue(new NPCDialogue(npc, "You don't appear to have any broken items, " + player.getName() + ". But, I'll be happy to repair them once you do!"));
            return;
        }
        int currencyId;
        String currencyName;
        int price;
        currencyId = COINS_995;
        currencyName = "coins";
        price = coinPrice(player, totalPrice);
        
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Repair all your items for " + NumberUtils.formatNumber(price) + " " + currencyName + "?", 2347, 1, () -> {
            Item currency = player.getInventory().findItem(currencyId);
            if(currency == null || currency.getAmount() < price) {
                currency = player.getBank().findItem(currencyId);
                if (currency == null || currency.getAmount() < price) {
                    player.dialogue(new NPCDialogue(npc, "You don't have enough " + currencyName + " for me to repair all your broken items. Use an item on me to repair them separately."));
                    return;
                }
            }
            currency.remove(price);
            for(Item item : brokenItems)
                item.setId(item.getDef().brokenFrom.fixedId);
            player.dialogue(new NPCDialogue(npc, "I've repaired all your items for you."));
        }));
    }

    private static void repairItem(Player player, Item item, ItemBreaking brokenItem) {
        int currencyId;
        String currencyName;
        int price;
        currencyId = COINS_995;
        currencyName = "coins";
        price = coinPrice(player, brokenItem.coinRepairCost);

        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Fix your " + item.getDef().name + " for " + NumberUtils.formatNumber(price) + " " + currencyName + "?", item, () -> {
            Item currency = player.getInventory().findItem(currencyId);
            if(currency == null || currency.getAmount() < price) {
                currency = player.getBank().findItem(currencyId);
                if (currency == null || currency.getAmount() < price) {
                    player.dialogue(new MessageDialogue("You don't have enough " + currencyName + " to repair that."));
                    return;
                }
            }
            currency.remove(price);
            item.setId(brokenItem.fixedId);
            player.dialogue(new MessageDialogue("You have repaired your " + item.getDef().name + "."));
        }));
    }

    private static void convertZamorakianWeapon(Player player, Item item, NPC npc) {
        int currencyId;
        String currencyName;
        int price;
        currencyId = COINS_995;
        currencyName = "coins";
        price = coinPrice(player, 1500000);
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Convert your " + item.getDef().name + " for " + NumberUtils.formatNumber(price) + " " + currencyName + "?", item, () -> {
            Item currency = player.getInventory().findItem(currencyId);
            if(currency == null || currency.getAmount() < price) {
                player.dialogue(new NPCDialogue(npc, "You don't have enough " + currencyName + " for me to upgrade that."));
                return;
            }
            currency.remove(price);
            item.setId(item.getId() == 11824 ? 11889 : 11824);
            player.dialogue(new NPCDialogue(npc, "I've converted your item for you."));
        }));
    }

    private static int coinPrice(Player player, int basePrice) {
        double smithingLevel = player.getStats().get(StatType.Smithing).fixedLevel;
        double smithingMultiplier = 1D - (smithingLevel / 200D);
        return (int) (smithingMultiplier * basePrice);
    }

}