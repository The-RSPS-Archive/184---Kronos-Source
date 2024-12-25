package io.ruin.model.item.actions.impl.tradepost;

import com.google.gson.annotations.Expose;
import io.ruin.model.item.Item;

/**
 * @author <a href="https://github.com/kLeptO">Augustinas R.</a>
 */
public class TradePostOffer {

    @Expose private final String username;
    @Expose private final Item item;
    @Expose private final int pricePerItem;
    @Expose private final long timestamp;

    public TradePostOffer(String username, Item item, int pricePerItem, long timestamp) {
        this.username = username;
        this.item = item;
        this.pricePerItem = pricePerItem;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public Item getItem() {
        return item;
    }

    public int getPricePerItem() {
        return pricePerItem;
    }

    public long getTimestamp() {
        return timestamp;
    }

}