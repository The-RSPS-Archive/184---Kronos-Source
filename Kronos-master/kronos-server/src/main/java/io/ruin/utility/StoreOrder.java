package io.ruin.utility;

import io.ruin.model.item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ReverendDread on 2/29/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project ZarosOSRS
 */
@Getter @Setter
public class StoreOrder extends Item {

    private int itemId, itemAmount;
    private int price;

    public StoreOrder(int id, int amount) {
        super(id, amount);
    }
}
