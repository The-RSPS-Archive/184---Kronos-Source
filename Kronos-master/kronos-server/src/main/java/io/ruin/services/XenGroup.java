package io.ruin.services;

import io.ruin.model.entity.npc.actions.edgeville.CreditManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;

public class XenGroup {

    public static void update(Player player) {
        PlayerGroup donationGroup = CreditManager.getGroup(player);
        if(donationGroup != null)
            donationGroup.sync(player, "donator");
        //todo ironman checks
    }

}
