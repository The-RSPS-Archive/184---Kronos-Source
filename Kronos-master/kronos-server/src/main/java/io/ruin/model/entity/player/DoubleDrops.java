package io.ruin.model.entity.player;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.item.Item;
import io.ruin.model.item.attributes.AttributeExtensions;

import java.util.List;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/11/2020
 */
public class DoubleDrops {

    /*
     * Math to retrieve loot rolls after a kill
     */
    public static int getRolls(Player player) {
        int doubleDropChance = getDoubleDropChance(player);
        int rolls = 1;
        if (World.doubleDrops)
            rolls++;
        if (player.storeAmountSpent > 0) {
            if (Random.get(1, 100) <= doubleDropChance) {
                rolls++;
            }
        }
//        if (player.pet.npcId == 20000) { //20% chance to double roll with founders pet
//            if(Random.get(1, 100) <= 20) {
//                rolls++;
//            }
//        }
        if (player.getEquipment().contains(new Item(12785))) { //20% chance to double roll with ROW i
            if (Random.get(1, 100) <= 20) {
                rolls++;
            }
        }

        if (player.getEquipment().contains(new Item(773))) { //5 rolls with pring
            rolls += 5;
        }


        if (Random.get(1, 100) <= gearCount(player) * 2) {
            rolls++;
        }

        return rolls;
    }

    /*
     * Method to display a visual chance at rolling multiple drops
     */
    public static int getChance(Player player) {
        int chance = 0;

        if (World.doubleDrops)
            chance += 100;

        chance += getDoubleDropChance(player);;

//        if (player.pet.npcId == 20000)  //20% chance to double roll with founders pet
//            chance += 20;

        if (player.getEquipment().contains(new Item(12785)))  //20% chance to double roll with ROW i
            chance += 20;


        chance += (gearCount(player) * 2);

        return chance;
    }

    private static int gearCount(Player player) {
        int gearRolls = 0;
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                boolean hasEffect = upgrades != null;
                if (hasEffect) {
                    for (String s : upgrades) {
                        try {
                            if (s.equalsIgnoreCase("empty"))
                                continue;
                            ItemEffect effect = ItemEffect.valueOf(s);
                            gearRolls += effect.getUpgrade().addDoubleDropRolls();
                        } catch (Exception ex) {
                            System.err.println("Unknown upgrade { " + s + " } found!");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        return gearRolls;
    }

    private static int getDoubleDropChance(Player player) {
        int doubleDropChance = player.getPrimaryGroup().getDoubleDropChance();

        if (player.isGroup(PlayerGroup.ZENYTE)) {
            doubleDropChance = PlayerGroup.ZENYTE.getDoubleDropChance();
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            doubleDropChance = PlayerGroup.ONYX.getDoubleDropChance();
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            doubleDropChance = PlayerGroup.DRAGONSTONE.getDoubleDropChance();
        } else if(player.isGroup(PlayerGroup.DIAMOND)) {
            doubleDropChance =PlayerGroup.DIAMOND.getDoubleDropChance();
        }else if (player.isGroup(PlayerGroup.RUBY)) {
            doubleDropChance = PlayerGroup.RUBY.getDoubleDropChance();
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            doubleDropChance = PlayerGroup.EMERALD.getDoubleDropChance();
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            doubleDropChance = PlayerGroup.SAPPHIRE.getDoubleDropChance();
        }
        return doubleDropChance;
    }
}
