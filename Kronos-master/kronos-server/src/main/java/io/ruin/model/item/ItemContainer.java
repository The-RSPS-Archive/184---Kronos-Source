package io.ruin.model.item;

import java.util.Map;

public class ItemContainer extends ItemContainerG<Item> {

    @Override
    protected Item newItem(int id, int amount, Map<String, String> attributes) {
        return new Item(id, amount, attributes);
    }

    @Override
    protected Item[] newArray(int size) {
        return new Item[size];
    }

}
