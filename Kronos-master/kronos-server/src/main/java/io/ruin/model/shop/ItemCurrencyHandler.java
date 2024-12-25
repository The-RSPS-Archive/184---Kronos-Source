package io.ruin.model.shop;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ItemCurrencyHandler extends CurrencyHandler {

    @Getter
    private int currencyItemId;

    public ItemCurrencyHandler(int currencyItemId){
        super("" + currencyItemId);
        ItemDef itemDef = ItemDef.get(currencyItemId);
        if(itemDef != null) {
            this.name = itemDef.name;
            this.pluralName = this.name.endsWith("s") ? this.name : this.name + "s";
        }
        this.currencyItemId = currencyItemId;
    }

    @Override
    public int getCurrencyCount(Player player) {
        return player.getInventory().count(currencyItemId);
    }

    @Override
    public int removeCurrency(Player player, int amount) {
        if(amount > player.getInventory().count(currencyItemId)){
            return 0;
        }
        return player.getInventory().remove(currencyItemId, amount);
    }

    @Override
    public int addCurrency(Player player, int amount) {
        return player.getInventory().add(currencyItemId, amount);
    }


}