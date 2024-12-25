package io.ruin.model.activities.appreciationpoints;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.map.Bounds;

public class Caretaker {

    private static final int CARETAKER = 6002;
    private static Bounds EDGEVILLE = new Bounds(3083, 3486, 3098, 3506, -1);

    static {
        SpawnListener.register(CARETAKER, npc -> npc.startEvent(event -> {
            while (true) {
                for (Player player : World.players) {
                    if (player.getPosition().inBounds(EDGEVILLE)) {
                        npc.forceText("Thank you for playing, " + player.getName() + "!");
                        npc.animate(862);
                        break;
                    }
                }
                event.delay(100);
            }
        }));
    }

}
