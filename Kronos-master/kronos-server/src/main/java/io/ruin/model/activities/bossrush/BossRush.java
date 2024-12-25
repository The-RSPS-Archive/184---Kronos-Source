package io.ruin.model.activities.bossrush;

import com.google.api.client.util.Lists;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.utility.TickDelay;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BossRush {

    private static final int game_delay = 200;
    private static final int game_cost = 100;
    private static BossRush instance;

    private final List<Player> players = Lists.newArrayList();
    private final TickDelay delay = new TickDelay();
    private boolean available;
    private boolean started;
    private int pointsPool;

    private void start() {
        setStarted(true);
    }

    private boolean depositPoints(Player player, int points) {
        player.PvmPoints -= points;
        pointsPool += points;
        if (canStart()) {
            pointsPool -= game_cost;
            start();
        }
        return true;
    }

    private boolean canStart() {
        return pointsPool >= game_cost;
    }

    static {
        MapListener.registerBounds(new Bounds(3311, 3240, 3316, 3244))
        .onEnter(player -> BossRush.getInstance().getPlayers().add(player))
        .onExit((player, logout) -> {
            if (logout) {} //TODO teleport out if they logout.
            BossRush.getInstance().getPlayers().remove(player);
        });
        World.startEvent(e -> {
            while (true) {
                if (BossRush.getInstance().isAvailable()) {
                    e.delay(game_delay);
                } else {
                    e.delay(1);
                }
            }
        });
    }

    //hate having to do it this way
    public static BossRush getInstance() {
        if (instance == null)
            instance = new BossRush();
        return instance;
    }

}
