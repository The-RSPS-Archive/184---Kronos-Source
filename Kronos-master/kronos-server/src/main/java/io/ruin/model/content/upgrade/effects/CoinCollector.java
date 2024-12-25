package io.ruin.model.content.upgrade.effects;

import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

/**
 * @author ReverendDread on 6/18/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class CoinCollector extends ItemUpgrade {

    @Override
    public boolean modifyDroppedItem(Player player, Item item) {
        if (item.getId() == 995) {
            player.getInventory().add(item.getId(), item.getAmount(), item.copyOfAttributes());
            return false;
        }
        return true;
    }

}
