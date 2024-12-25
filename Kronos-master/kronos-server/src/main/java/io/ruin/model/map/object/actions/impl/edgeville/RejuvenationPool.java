package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class RejuvenationPool {

    private static void drink(Player player, GameObject obj) {
        boolean delayCheck = obj.id != 40004;
        if(delayCheck && System.currentTimeMillis() - player.rejuvenationPool < 1000 * 60) {
            player.dialogue(new MessageDialogue("You can only drink from the " + ObjectDef.get(obj.id).name + " once every minute."));
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(833);
            Nurse.heal(player, null);
            event.delay(1);
            if(delayCheck)
                player.rejuvenationPool = System.currentTimeMillis();
            player.unlock();
        });
    }

    static {
        ObjectAction.register(31380, "drink", RejuvenationPool::drink);
        ObjectAction.register(29241, "drink", RejuvenationPool::drink);
        ObjectAction.register(2654, "drink", RejuvenationPool::drink);
    }

}
