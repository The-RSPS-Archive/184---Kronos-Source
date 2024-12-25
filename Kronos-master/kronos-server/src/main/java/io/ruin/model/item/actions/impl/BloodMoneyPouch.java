package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.actions.ItemAction;

import static io.ruin.cache.ItemID.BLOOD_MONEY;

public enum BloodMoneyPouch {

    SMALL(22521, 6, 12),
    MEDIUM(22522, 12, 16),
    LARGE(22523, 20, 28),
    GIANT(22524, 32, 40);

    private int itemId, minAmount, maxAmount;

    BloodMoneyPouch(int itemId, int minAmt, int maxAmt) {
        this.itemId = itemId;
        this.minAmount = minAmt;
        this.maxAmount = maxAmt;
    }

    public static final int MAX_ALLOWED = 28;

    static {
        for(BloodMoneyPouch pouch : BloodMoneyPouch.values()) {
            ItemAction.registerInventory(pouch.itemId, "open-all", (player, item) -> player.dialogue(new YesNoDialogue("Are you sure you want to do this?",
                    "Open all your bloody pouches for their contents?", item, () -> {
                int amount = player.getInventory().count(item.getId());
                if(amount <= 0)
                    return;
                int bmReward = Random.get(pouch.minAmount, pouch.maxAmount) * amount;
                player.lock();
                item.remove(amount);
                player.sendMessage("You open the pouch to find " + bmReward + " blood money.");
                player.getInventory().add(BLOOD_MONEY, bmReward);
                player.unlock();
            })));

            ItemAction.registerInventory(pouch.itemId, "open", (player, item) -> {
                int bmReward = Random.get(pouch.minAmount, pouch.maxAmount);
                player.lock();
                item.remove(1);
                player.sendMessage("You open the pouch to find " + bmReward + " blood money.");
                player.getInventory().add(BLOOD_MONEY, bmReward);
                player.unlock();
            });
        }
    }

}
