package io.ruin.model.map.object.actions.impl;

import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;

public class Sandpit {

    private static final int SANDPIT = 10814;

    static {
        ItemObjectAction.register(Tool.EMPTY_BUCKET, SANDPIT, (player, item, obj) -> player.startEvent(event -> {
            int amt = player.getInventory().count(Tool.EMPTY_BUCKET);
            while(amt-- > 0) {
                Item bucket = player.getInventory().findItem(Tool.EMPTY_BUCKET);
                if(bucket == null)
                    break;
                bucket.setId(1783);
                player.animate(895);
                player.sendMessage("You fill the bucket with sand.");
                event.delay(2);
            }
        }));
    }

}
