package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.Bounds;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public enum RingOfWealth {

    /**
     * Regular rings
     */
    FIVE(11980, 5, 11982, 20786),
    FOUR(11982, 4, 11984, 20787),
    THREE(11984, 3, 11986, 20788),
    TWO(11986, 2, 11988, 20789),
    ONE(11988, 1, 2572, 20790),
    UNCHARGED(2572, 0, -1, 12785),

    /**
     * Imbued rings
     */
    FIVE_I(20786, 5, 20787, -1),
    FOUR_I(20787, 4, 20788, -1),
    THREE_I(20788, 3, 20789, -1),
    TWO_I(20789, 2, 20790, -1),
    ONE_I(20790, 1, 12785, -1),
    UNCHARGED_I(12785, 0, -1, -1);

    private final int id, charges, replacementId, imbueId;

    RingOfWealth(int id, int charges, int replacementId, int imbueId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
        this.imbueId = imbueId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("ring", true,
                new JeweleryTeleports.Teleport("Miscellania", new Bounds(2563, 3846, 2566, 3849, 0)),
                new JeweleryTeleports.Teleport("Grand Exchange", new Bounds(3163, 3463, 3166, 3466, 0)),
                new JeweleryTeleports.Teleport("Falador", new Bounds(3022, 3375, 3024, 3377, 0)),
                new JeweleryTeleports.Teleport("Dondakan", new Bounds(2837, 10127, 2839, 10129, 0))
        );
        for(RingOfWealth ring : values()) {
            /**
             * Regular ring actions
             */
            teleports.register(ring.id, ring.charges, ring.replacementId);
            ItemAction.registerInventory(ring.id, "features", RingOfWealth::displayFeatures);
            ItemAction.registerEquipment(ring.id, "boss log", (player, item) -> KillCounter.openOwnBoss(player));
            ItemAction.registerEquipment(ring.id, "coin collection", (player, item) -> toggleBMCollect(player));
            /**
             * Imbued ring actions
             */
            if(ring.imbueId != -1) {
                ItemItemAction.register(ring.id, 12783, (player, ringItem, scrollItem) -> player.dialogue(
                        new YesNoDialogue("Are you sure you want to do this?", "You will imbue your ring, making it untradable.", ringItem, () -> {
                            ringItem.setId(ring.imbueId);
                            scrollItem.remove();
                            new ItemDialogue().one(ringItem.getId(), "You have imbued your Ring of Wealth.");
                        })
                ));
                teleports.register(ring.imbueId, ring.charges, ring.replacementId);
            }
        }
    }

    private static void displayFeatures(Player player, Item item) {
        player.dialogue(new OptionsDialogue("What features would you like to toggle?",
                        new Option("Auto Collect Coins: " + (player.ROWAutoCollectGold ? Color.DARK_GREEN.wrap("Enabled") : Color.DARK_RED.wrap("Disabled")), () -> toggleCoinCollect(player)),
                        new Option("Auto Collect Revenant Ether: " + (player.ROWAutoCollectEther ? Color.DARK_GREEN.wrap("Enabled") : Color.DARK_RED.wrap("Disabled")), () -> toggleEtherCollect(player))
                )
        );
    }

    private static void toggleBMCollect(Player player) {
        player.ROWAutoCollectBloodMoney = !player.ROWAutoCollectBloodMoney;
        if(player.ROWAutoCollectBloodMoney)
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of wealth will now collect all blood money drops from monsters."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of wealth will no longer automatically collect blood money drops from monsters."));
    }

    private static void toggleCoinCollect(Player player) {
        player.ROWAutoCollectGold = !player.ROWAutoCollectGold;
        if(player.ROWAutoCollectGold)
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of wealth will now collect all coin drops from monsters."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of wealth will no longer automatically collect coin drops from monsters."));
    }

    private static void toggleEtherCollect(Player player) {
        player.ROWAutoCollectEther = !player.ROWAutoCollectEther;
        if(player.ROWAutoCollectEther)
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of wealth will now collect all revenant ether drops from monsters."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of wealth will no longer automatically collect revenant ether drops from monsters."));
    }

    public static boolean check(Player pKiller, Item item) {
        if(!RingOfWealth.wearingRingOfWealth(pKiller))
            return false;
        if(item.getId() == BLOOD_MONEY && pKiller.ROWAutoCollectBloodMoney)
            return true;
        if(item.getId() == 21820 && pKiller.ROWAutoCollectEther)
            return true;
        return item.getId() == COINS_995 && pKiller.ROWAutoCollectGold;
    }

    public static boolean wearingRingOfWealth(Player player) {
        for (RingOfWealth ring : RingOfWealth.values()) {
            if(player.getEquipment().hasId(ring.id))
                return true;
        }
        return false;
    }

}
