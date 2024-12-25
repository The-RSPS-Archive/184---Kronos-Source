package io.ruin.model.shop;


import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public abstract class CurrencyHandler {

    public static final int NOT_ENOUGH_SPACE = -2;

    @Accessors(fluent = true)
    @Getter
    protected String name, pluralName;

    public CurrencyHandler(String name, String pluralName) {
        this.name = name;
        this.pluralName = pluralName;
    }

    public CurrencyHandler(String name) {
        this.name = name;
        this.pluralName = name;
    }

    public abstract int getCurrencyCount(Player player);

    public int getPossibleBuyAmount(Player player, ShopItem shopItem) {
            int pricePer = shopItem.getPrice();
            int amount = shopItem.getAmount();
            int currencyAmount = getCurrencyCount(player);
            if(currencyAmount <= 0){
                return 0;
            }
            long shopTotalCost = (long) pricePer * (long) shopItem.getAmount();
            if(shopTotalCost > Integer.MAX_VALUE){
                amount = Integer.MAX_VALUE / pricePer;
                log.debug("total was over max int, set to {}", amount);
            }
            log.debug("buy amt request for {} {} {}", shopTotalCost, pricePer * amount, currencyAmount);
            if(pricePer * amount > currencyAmount){
                int newAmount = currencyAmount / pricePer;
                int remaining = currencyAmount - newAmount;

                log.debug("{} {}", newAmount, remaining);
                amount = newAmount;
            }
            if(shopItem.getAdditionalItems() != null && !shopItem.getAdditionalItems().isEmpty()){
                if(player.getInventory().getFreeSlots() <= 1){
                    return NOT_ENOUGH_SPACE;
                }
                int slotsPerPurchase = 1;
                for(Item additional : shopItem.getAdditionalItems()){
                    ItemDef def = additional.getDef();
                    if(def.stackable){
                        slotsPerPurchase++;
                    } else {
                        slotsPerPurchase += additional.getAmount();
                    }
                }

                log.debug("{} {}", player.getInventory().getFreeSlots(), slotsPerPurchase);




                int possibleAmount = Math.floorDiv(player.getInventory().getFreeSlots(), slotsPerPurchase);

                log.debug("{} {} {}", player.getInventory().getFreeSlots(), slotsPerPurchase, possibleAmount);
                if(possibleAmount <= 0){
                    return NOT_ENOUGH_SPACE;
                }

                amount = Math.min(amount, possibleAmount);
            }
            if(!player.getInventory().hasRoomFor(shopItem.getId(), amount)){
                if(currencyAmount == pricePer){
                    return 1;
                }
                int capacity = player.getInventory().getCapacityFor(shopItem.getId());
                return capacity == 0 ? NOT_ENOUGH_SPACE : capacity;
            }
            return amount;
    }

    public abstract int removeCurrency(Player player, int amount);

    public abstract int addCurrency(Player player, int amount);

}
