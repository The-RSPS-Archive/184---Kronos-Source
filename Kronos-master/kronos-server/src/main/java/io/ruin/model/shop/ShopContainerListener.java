package io.ruin.model.shop;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.WidgetInfo;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.containers.ShopItemContainer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ShopContainerListener {

    private final Player player;
    private ShopItemContainer shopContainer;


    public void open(){
        shopContainer.send(player, 300 << 16 | 16, 2);
    }


    public void update(){
        shopContainer.sendUpdates(player, 300 << 16 | 16, 2);
    }


}
