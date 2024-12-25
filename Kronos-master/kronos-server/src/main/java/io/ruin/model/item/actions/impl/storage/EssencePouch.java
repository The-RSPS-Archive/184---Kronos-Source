package io.ruin.model.item.actions.impl.storage;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.runecrafting.Essence;
import io.ruin.model.stat.StatType;

import java.util.Map;

public enum EssencePouch {

    SMALL_POUCH(5509, 1, 3),
    MEDIUM_POUCH(5510, 25, 6),
    LARGE_POUCH(5512, 50, 9),
    GIANT_POUCH(5514, 75, 12);

    private final int itemId;
    private final int levelReq;
    private final int capacity;

    EssencePouch(int itemId, int levelReq, int capacity) {
        this.itemId = itemId;
        this.levelReq = levelReq;
        this.capacity = capacity;
    }

    public int getItemId() {
        return itemId;
    }

    private static void fill(Player player, EssencePouch pouch) {
        if (!player.getStats().check(StatType.Runecrafting, pouch.levelReq, "use this pouch"))
            return;
        int amount = player.getInventory().count(Essence.PURE.id);
        if (amount == 0) {
            player.sendFilteredMessage("You do not have any essence to fill your pouch with.");
            return;
        }
        amount = amount > pouch.capacity ? pouch.capacity : amount;
        Map<EssencePouch, Integer> pouchMap = player.runeEssencePouches;
        if (pouchFull(player, pouch)) {
            player.getInventory().remove(Essence.PURE.id, amount);
            pouchMap.put(pouch, amount);
        }
    }

    private static void empty(Player player, EssencePouch pouch) {
        if (!player.getStats().check(StatType.Runecrafting, pouch.levelReq, "use this pouch"))
            return;
        if (player.getInventory().isFull()) {
            player.sendFilteredMessage("You do not have any more free space in your inventory.");
            return;
        }
        Map<EssencePouch, Integer> pouchMap = player.runeEssencePouches;
        if (pouchMap == null) {
            player.sendFilteredMessage("There are no essence in this pouch.");
            return;
        }
        int freeSlots = player.getInventory().getFreeSlots();
        int available = pouchMap.get(pouch);
        if (freeSlots < available)
            available = freeSlots;
        if (!pouchEmpty(player, pouch)) {
            player.getInventory().add(Essence.PURE.id, available);
            pouchMap.put(pouch, pouchMap.get(pouch) - available);
        }
    }

    private static void check(Player player, EssencePouch pouch) {
        Map<EssencePouch, Integer> pouchMap = player.runeEssencePouches;
        if (pouchMap == null) {
            player.sendFilteredMessage("There are no essence in this pouch.");
            return;
        }
        if (!pouchEmpty(player, pouch)) {
            int amount = pouchMap.get(pouch);
            player.sendFilteredMessage("There " + (amount == 1 ? "is " : "are ") + amount + " essence in this pouch.");
        }
    }

    private static boolean pouchFull(Player player, EssencePouch pouch) {
        Map<EssencePouch, Integer> pouchMap = player.runeEssencePouches;
        if (pouchMap.containsKey(pouch) && pouchMap.get(pouch) >= pouch.capacity) {
            player.sendFilteredMessage("You cannot add any more essence to the pouch.");
            return false;
        }
        return true;
    }

    private static boolean pouchEmpty(Player player, EssencePouch pouch) {
        Map<EssencePouch, Integer> pouchMap = player.runeEssencePouches;
        if (!pouchMap.containsKey(pouch) || (pouchMap.containsKey(pouch) && pouchMap.get(pouch) <= 0)) {
            player.sendFilteredMessage("There are no essence in this pouch.");
            return true;
        }
        return false;
    }

    static {
        for (EssencePouch pouch : values()) {
            ItemAction.registerInventory(pouch.itemId, "fill", (player, item) -> fill(player, pouch));
            ItemAction.registerInventory(pouch.itemId, "empty", (player, item) -> empty(player, pouch));
            ItemAction.registerInventory(pouch.itemId, "check", (player, item) -> check(player, pouch));
        }

        ItemDef.get(Essence.PURE.id).bankWithdrawListener = EssencePouch::withdrawToPouch;
    }

    private static int withdrawToPouch(Player player, Item item, int amount) {
        int intercepted = 0;
        for (Map.Entry<EssencePouch, Integer> entry : player.runeEssencePouches.entrySet()) {
            EssencePouch pouch = entry.getKey();
            if (!player.getInventory().contains(pouch.getItemId(), 1))
                continue;
            int spaceInPouch = pouch.capacity - entry.getValue();
            int withdraw = Math.min(Math.min(item.getAmount(), amount), spaceInPouch);
            if (withdraw > 0) {
                intercepted += withdraw;
                entry.setValue(entry.getValue() + withdraw);
                player.sendFilteredMessage("You withdraw " + withdraw + " essence directly into your " + pouch.toString().toLowerCase().replace("_", " ") + ".");
                if (intercepted == amount)
                    return intercepted;
            }
        }
        return intercepted;
    }
}
