package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

public class Scorpia {

    private static void teleport(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(x, y);
            player.unlock();
        });
    }

    static {
        ObjectAction.register(26762, 3231, 3936, 0, "enter", (player, obj) -> teleport(player, 3233, 10332));
        ObjectAction.register(26762, 3231, 3951, 0, "enter", (player, obj) -> teleport(player, 3232, 10351));
        ObjectAction.register(26762, 3241, 3949, 0, "enter", (player, obj) -> teleport(player, 3243, 10351));

        ObjectAction.register(26763, 3233, 10331, 0, "use", (player, obj) -> teleport(player, 3233, 3938));
        ObjectAction.register(26763, 3232, 10352, 0, "use", (player, obj) -> teleport(player, 3233, 3950));
        ObjectAction.register(26763, 3243, 10352, 0, "use", (player, obj) -> teleport(player, 3242, 3948));
    }

}
