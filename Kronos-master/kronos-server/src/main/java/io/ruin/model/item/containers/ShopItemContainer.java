package io.ruin.model.item.containers;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.shop.ShopItem;
import lombok.var;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ShopItemContainer extends ItemContainerG<ShopItem> {

    @Override
    protected ShopItem newItem(int id, int amount, Map<String, String> attributes) {
        throw new UnsupportedOperationException("Cannot create ShopItem using default constructor");
    }


    protected ShopItem newItem(int id, int amount, int price, Map<String, String> attributes) {
        return new ShopItem(id, amount, price);
    }

    @Override
    public void send(Player player, int interfaceHash, int containerId) {
        player.getPacketSender().sendShopItems(interfaceHash, containerId, items, items.length);
    }

    public void sendUpdates(Player player, int interfaceHash, int containerId) {
        player.getPacketSender().updateItems(interfaceHash, containerId, items, updatedSlots, items.length);
    }

    @Override
    protected ShopItem[] newArray(int size) {
        return new ShopItem[size];
    }

    public int add(int id, int amount, int price, Map<String, String> attributes) {

        if (amount <= 0) {
            System.err.println("Invalid add amount: " + amount + " | " + new Throwable().getStackTrace()[1].toString());
            return 0;
        }
        ItemDef def = ItemDef.get(id);
        if (def == null) {
            System.err.println("Failed to add non-existing item: " + id + " | " + new Throwable().getStackTrace()[1].toString());
            return 0;
        }

        boolean stack = forceStack;
        int attributeHash = AttributeExtensions.hashAttributes(attributes);
        boolean hasAttributes = attributeHash != 0 && !def.stackable;

        if (stack) {
            Item stackItem = findItem(id, attributeHash);
            if (stackItem != null) {
                stackItem.incrementAmount(amount);
                return amount;
            }
        }

        if (def.stackable) {
            /**
             * Regular stackable item
             */
            Item stackItem = findItem(id, attributeHash);
            if (stackItem != null) {
                stackItem.incrementAmount(amount);
                return amount;
            }
            stack = true;
        }

        if (amount == 1 || stack) {
            int freeSlot = freeSlot();
            if(freeSlot == -1) {
                /**
                 * No free slot
                 */
                return 0;
            }
            set(freeSlot, newItem(id, amount, price, attributes));
            return amount;
        }

        int added = 0;
        while (amount-- > 0) { //move one at a time
            int freeSlot = freeSlot();
            if(freeSlot == -1) {
                /**
                 * No free slot
                 */
                break;
            }
            set(freeSlot, newItem(id, 1, price, attributes));
            added++;
        }
        return added;
    }

    public void forEach(Consumer<ShopItem> shopItemConsumer){
        Stream.of(items).filter(Objects::nonNull).forEach(shopItemConsumer);
    }

    @Override
    public int add(ShopItem item) {

        int id = item.getId();
        int amount = item.getAmount();
        var attributes = item.copyOfAttributes();
        int price = item.getPrice();



        if (amount <= 0) {
            System.err.println("Invalid add amount: " + amount + " | " + new Throwable().getStackTrace()[1].toString());
            return 0;
        }
        ItemDef def = ItemDef.get(id);
        if (def == null) {
            System.err.println("Failed to add non-existing item: " + id + " | " + new Throwable().getStackTrace()[1].toString());
            return 0;
        }

        boolean stack = forceStack;
        int attributeHash = AttributeExtensions.hashAttributes(attributes);
        boolean hasAttributes = attributeHash != 0 && !def.stackable;

        if (stack) {
            Item stackItem = findItem(id, attributeHash);
            if (stackItem != null) {
                stackItem.incrementAmount(amount);
                return amount;
            }
        }

        if (def.stackable) {
            /**
             * Regular stackable item
             */
            Item stackItem = findItem(id, attributeHash);
            if (stackItem != null) {
                stackItem.incrementAmount(amount);
                return amount;
            }
            stack = true;
        }

        if (amount == 1 || stack) {
            int freeSlot = freeSlot();
            if(freeSlot == -1) {
                /**
                 * No free slot
                 */
                return 0;
            }
            set(freeSlot, item.copy());
            return amount;
        }

        int added = 0;
        while (amount-- > 0) { //move one at a time
            int freeSlot = freeSlot();
            if(freeSlot == -1) {
                /**
                 * No free slot
                 */
                break;
            }
            set(freeSlot, newItem(id, 1, price, attributes));
            added++;
        }
        return added;
    }
}
