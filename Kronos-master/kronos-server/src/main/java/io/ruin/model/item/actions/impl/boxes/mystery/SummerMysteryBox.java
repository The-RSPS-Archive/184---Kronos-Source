package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.cache.Icon;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.journal.toggles.BroadcastActiveVolcano;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.item.actions.impl.boxes.mystery.VoteMysteryBox.VIEW_REWARDS_WIDGET;
import static io.ruin.model.item.actions.impl.boxes.mystery.VoteMysteryBox.updateRewards;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/8/2020
 */
public class SummerMysteryBox {

    private static final int SUMMER_MYSTERY_BOX = 30185;


    public static final LootTable summerMboxLoot = new LootTable().addTable(1,
            new LootItem(4151, 1, 30), //Abyssal whip
            new LootItem(11128, 1, 20), //Berserker necklace
            new LootItem(11840, 1, 30), //Dragon boots
            new LootItem(12002, 1, 10), //Occult necklace
            new LootItem(6585, 1, 10), //Amulet of fury
            new LootItem(12902, 1, 10), //Toxic staff of the dead
            new LootItem(11791, 1, 10), //Staff of the dead
            new LootItem(11785, 1, 10), //Armadyl crossbow
            new LootItem(4224, 1, 10), //New crystal shield
            new LootItem(12831, 1, 10), //Blessed spirit shield
            new LootItem(11926, 1, 10), //Odium ward
            new LootItem(11924, 1, 10), //Malediction ward
            new LootItem(12379, 1, 20), //Rune cane
            new LootItem(12373, 1, 15), //Dragon cane
            new LootItem(6889, 1, 10), //Master's book
            new LootItem(12900, 1, 10), //Uncharged toxic trident
            new LootItem(20724, 1, 5), //Imbued heart
            new LootItem(11908, 1, 10), //Uncharged trident
            new LootItem(12371, 1, 10), //Lava dragon mask
            new LootItem(21634, 1, 10), //Ancient wyvern shield
            new LootItem(22003, 1, 10), //Dragonfire ward
            new LootItem(11284, 1, 10), //Dragonfire shield
            new LootItem(22545, 1, 10), //Viggora's chainmace
            new LootItem(22550, 1, 10), //Craw's bow
            new LootItem(13235, 1, 5).broadcast(Broadcast.GLOBAL), //Eternal boots
            new LootItem(13237, 1, 5).broadcast(Broadcast.GLOBAL), //Pegasian boots
            new LootItem(13239, 1, 5).broadcast(Broadcast.GLOBAL), //Primordial boots
            new LootItem(11806, 1, 5).broadcast(Broadcast.GLOBAL), //Saradomin godsword
            new LootItem(11804, 1, 5).broadcast(Broadcast.GLOBAL), //Bandos godsword
            new LootItem(11773, 1, 5).broadcast(Broadcast.GLOBAL), //Berserker ring (i)
            new LootItem(11772, 1, 5).broadcast(Broadcast.GLOBAL), //Warriors ring (i)
            new LootItem(11771, 1, 5).broadcast(Broadcast.GLOBAL), //Archers ring (i)
            new LootItem(11770, 1, 5).broadcast(Broadcast.GLOBAL), //Seers ring (i))
            new LootItem(13652, 1, 3).broadcast(Broadcast.GLOBAL), //Dragon claws
            new LootItem(19553, 1, 3).broadcast(Broadcast.GLOBAL), //Amulet of torture
            new LootItem(19547, 1, 3).broadcast(Broadcast.GLOBAL), //Necklace of anguish
            new LootItem(19544, 1, 3).broadcast(Broadcast.GLOBAL), //Tormented bracelet
            new LootItem(19550, 1, 3).broadcast(Broadcast.GLOBAL), //Ring of suffering
            new LootItem(20017, 1, 2).broadcast(Broadcast.GLOBAL), //Ring of coins
            new LootItem(6583, 1, 2).broadcast(Broadcast.GLOBAL), //Ring of stone
            new LootItem(20005, 1, 2).broadcast(Broadcast.GLOBAL), //Ring of nature
            new LootItem(COINS_995, 5000000, 10000000, 2).broadcast(Broadcast.GLOBAL), //Coins
            new LootItem(COINS_995, 8000000, 12000000, 2).broadcast(Broadcast.GLOBAL), //Coins
            new LootItem(11806, 1, 2).broadcast(Broadcast.GLOBAL), //Armadyl godsword
            new LootItem(12821, 1, 2).broadcast(Broadcast.GLOBAL), //Spectral spirit shield
            new LootItem(11785, 1, 2).broadcast(Broadcast.GLOBAL), //Armadyl crossbow
            new LootItem(13576, 1, 2).broadcast(Broadcast.GLOBAL), //Dragon warhammer
            new LootItem(1038, 1, 1).broadcast(Broadcast.GLOBAL), //Red party hat
            new LootItem(1040, 1, 1).broadcast(Broadcast.GLOBAL), //Yellow party hat
            new LootItem(1042, 1, 1).broadcast(Broadcast.GLOBAL), //Blue party hat
            new LootItem(1044, 1, 1).broadcast(Broadcast.GLOBAL), //Green party hat
            new LootItem(1046, 1, 1).broadcast(Broadcast.GLOBAL), //Purple party hat
            new LootItem(1048, 1, 1).broadcast(Broadcast.GLOBAL), //White  party hat
            new LootItem(11862, 1, 1).broadcast(Broadcast.GLOBAL), //Black party hat
            new LootItem(11863, 1, 1).broadcast(Broadcast.GLOBAL), //Rainbow party hat
            new LootItem(12399, 1, 1).broadcast(Broadcast.GLOBAL), //Partyhat & specs
            new LootItem(962, 1, 1).broadcast(Broadcast.GLOBAL), // Xmas cracker
            new LootItem(1050, 1, 1).broadcast(Broadcast.GLOBAL), // Santa hat
            new LootItem(21003, 1, 4).broadcast(Broadcast.GLOBAL), //Elder maul
            new LootItem(1419, 1, 1).broadcast(Broadcast.GLOBAL), // Scythe
            new LootItem(1037, 1, 1).broadcast(Broadcast.GLOBAL), // Bunny ears
            new LootItem(21006, 1, 3).broadcast(Broadcast.GLOBAL), //Kodai wand
            new LootItem(21006, 1, 4).broadcast(Broadcast.GLOBAL), //Vesta's longsword
            new LootItem(21006, 1, 4).broadcast(Broadcast.GLOBAL), //Statius's warhammer
            new LootItem(12437, 1, 1).broadcast(Broadcast.GLOBAL), // 3rd age cloak
            new LootItem(10344, 1, 1).broadcast(Broadcast.GLOBAL), // 3rd age amulet
            new LootItem(10346, 1, 1).broadcast(Broadcast.GLOBAL), // 3rd age platelegs
            new LootItem(10348, 1, 1).broadcast(Broadcast.GLOBAL), // 3rd age platebody
            new LootItem(10350, 1, 1).broadcast(Broadcast.GLOBAL), // 3rd age fullhelm
            new LootItem(10352, 1, 1).broadcast(Broadcast.GLOBAL), // 3rd age kiteshield

            //Exclusives
            new LootItem(30194, 1, 3).broadcast(Broadcast.GLOBAL), //kronos minion
            new LootItem(30172, 1, 3).broadcast(Broadcast.GLOBAL), //tbow limited
            new LootItem(30169, 1, 3).broadcast(Broadcast.GLOBAL), //kodai limited
            new LootItem(30166, 1, 3).broadcast(Broadcast.GLOBAL), //Ancest bot Limited
            new LootItem(30163, 1, 3).broadcast(Broadcast.GLOBAL), //Ancest top Limited
            new LootItem(30160, 1, 3).broadcast(Broadcast.GLOBAL), //Ancest hat Limited
            new LootItem(20997, 1, 1).broadcast(Broadcast.GLOBAL), //tbow
            new LootItem(30185, 1, 3, 1).broadcast(Broadcast.GLOBAL), //summer mbox
            new LootItem(12817, 3, 1).broadcast(Broadcast.GLOBAL) //elysian

    );

    public static void open(Player player, Item item) {
        InterfaceHandler.register(Interface.SUMMER_MYSTER_BOX, h -> {
            h.actions[7] = (SimpleAction) SummerMysteryBox::spin;
            h.actions[19] = (SimpleAction) SummerMysteryBox::claimReward;
            h.actions[21] = (SimpleAction) SummerMysteryBox::discardReward;
            h.actions[23] = (SimpleAction) SummerMysteryBox::openRewards;
        });

        if(player.isVisibleInterface(Interface.SUMMER_MYSTER_BOX)) {
            player.sendMessage("You need to claim or discard your reward before doing this!");
            return;
        }

        if(player.claimedBox) {
            player.getBox().clear();
            generateReward(player);
            player.getBox().sendUpdates();
            player.openInterface(InterfaceType.MAIN, 702);
            player.getPacketSender().sendClientScript(10034, "ssii", "Summer Mystery Box", "Thank you for checking out our LIMITED Summer Mystery Box!<br><br>" +
                    "Click the \"Spin\" button when you're ready to test your luck!<br><br><br>" +
                    "Good Luck!", 15, 0);
        } else {
            player.getBox().sendUpdates();
            player.openInterface(InterfaceType.MAIN, 702);
            player.getPacketSender().sendClientScript(10034, "ssii", "Summer Mystery Box", "Thank you for checking out our LIMITED Summer Mystery Box!<br><br>" +
                    "Click the \"Spin\" button when you're ready to test your luck!<br><br><br>" +
                    "Good Luck!", 15, 0);
        }
    }

    private static void generateReward(Player player) {
        LootTable table = summerMboxLoot;
        for(int i = 0; i < 24; i ++)
            player.getBox().add(table.rollItem());
        Item reward = player.getBox().get(15);
        if(reward == null)
            generateReward(player);
    }

    private static void spin(Player player) {

        Item superMysteryBox = player.getInventory().findItem(SUMMER_MYSTERY_BOX);
        if (superMysteryBox == null) {
            player.closeInterface(InterfaceType.MAIN);
            return;
        }
        player.claimedBox = false;
        superMysteryBox.remove(1);

    }

    private static void claimReward(Player player) {
        Item reward = player.getBox().get(15);
        player.getInventory().add(reward);
        player.claimedBox = true;
        player.sendMessage("You get " + reward.getDef().descriptiveName + " from the Summer Mystery Box.");
        if (summerMboxLoot.getWeight(reward) < 5) {
            Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Summer Mystery Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
        }
        player.getBox().clear();
        if(player.isVisibleInterface(Interface.SUPER_MYSTERY_BOX))
            player.closeInterface(InterfaceType.MAIN);
    }

    private static void discardReward(Player player) {
        player.closeInterface(InterfaceType.MAIN);
        player.claimedBox = true;
        player.sendMessage("You discard your Super Mystery Box reward.");
        player.getBox().clear();
    }

    public static void openRewards(Player player) {
        player.openInterface(InterfaceType.MAIN, VIEW_REWARDS_WIDGET);
        player.closeInterface(InterfaceType.INVENTORY);
        updateRewards(player, summerMboxLoot);
    }

    static {
        ItemAction.registerInventory(SUMMER_MYSTERY_BOX, "open", SummerMysteryBox::open);
    }
}
