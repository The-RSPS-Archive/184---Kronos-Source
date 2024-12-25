package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

import static io.ruin.cache.ItemID.BLOOD_MONEY;

public class MysteriousEmblem {

    static {
        int[][] emblems = {
                {12746, 150},    //Tier 1
                {12748, 250},    //Tier 2
                {12749, 375},    //Tier 3
                {12750, 500},   //Tier 4
                {12751, 750},   //Tier 5
                {12752, 1000},   //Tier 6
                {12753, 1500},   //Tier 7
                {12754, 2500},   //Tier 8
                {12755, 3750},   //Tier 9
                {12756, 5000},  //Tier 10
        };
        for(int[] emblem : emblems) {
            ItemDef def = ItemDef.get(emblem[0]);
            def.sigmundBuyPrice = emblem[1];
            ItemAction.registerInventory(def.id, "info", (player, item) -> player.sendScroll("<col=800000>About Mysterious Emblems",
                    "Mysterious Emblems are items dropped from killing",
                    "Bounty Hunter targets. If you kill a target with an emblem",
                    "in your inventory, your emblem will be upgraded. If you kill",
                    " a target with an emblem in their inventory, their emblem",
                    "will be downgraded one tier and dropped.",
                    "",
                    "You can right click and redeem emblems in exchange",
                    "for wilderness points. Wilderness points can be spent",
                    "by trading Sigmund. Here are the emblem values:",
                    "",
                    Color.DARK_RED.wrap("Tier 1 ") + "           150 blood money",
                    Color.DARK_RED.wrap("Tier 2 ") + "           250 blood money",
                    Color.DARK_RED.wrap("Tier 3 ") + "           375 blood money",
                    Color.DARK_RED.wrap("   Tier 4 ") + "         500 blood money",
                    Color.DARK_RED.wrap("   Tier 5 ") + "         750 blood money",
                    Color.DARK_RED.wrap("   Tier 6 ") + "         1,000 blood money",
                    Color.DARK_RED.wrap("   Tier 7 ") + "         1,500 blood money",
                    Color.DARK_RED.wrap("   Tier 8 ") + "         2,500 blood money",
                    Color.DARK_RED.wrap("   Tier 9 ") + "         3,750 blood money",
                    Color.DARK_RED.wrap("   Tier 10 ") + "        5,000 blood money"
            ));

            ItemAction.registerInventory(def.id, "redeem", (player, item) -> {
                int emblemValue = emblem[1];
                String emblemName = item.getDef().name;
                if(player.wildernessLevel > 0 || player.pvpAttackZone) {
                    player.sendMessage("You can't redeem a mysterious emblem inside the wilderness.");
                    return;
                }
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Redeem your " + emblemName +
                        " for " + NumberUtils.formatNumber(emblemValue) + " blood money?", item, () -> {
                    item.remove();
                    player.getInventory().add(new Item(BLOOD_MONEY, emblemValue));
                    String format = String.format("Emblem:[Player:[%s] IPAddress:[%s] Value:[%d]]", player.getName(), player.getIp(), emblemValue);
                    ServerWrapper.log(format);
                    player.dialogue(new ItemDialogue().one(BLOOD_MONEY, "You redeem your " + emblemName + " for " +
                            Color.DARK_RED.wrap(NumberUtils.formatNumber(emblemValue)) + " blood money."));
                }));
            });
        }
    }

}