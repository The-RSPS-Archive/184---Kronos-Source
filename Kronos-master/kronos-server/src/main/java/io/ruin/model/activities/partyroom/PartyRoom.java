package io.ruin.model.activities.partyroom;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;

import java.util.Collections;
import java.util.List;

public class PartyRoom {

    //Balloons = 115 -> 122

    public static void localDrop(Player player, int balloonCount) {
        List<Position> positions = player.getPosition().area(3, pos -> !pos.equals(player.getPosition()) && (pos.getTile() == null || pos.getTile().clipping == 0));
        World.startEvent(event -> {
            while(!positions.isEmpty()) {
                Collections.shuffle(positions);
                Position position = positions.get(0);
                positions.remove(position);
                if(position == null)
                    continue;
                if(Random.rollDie(3, 1))
                    continue;
                int balloonId = Random.get(115, 122);
                GameObject balloon = GameObject.spawn(balloonId, position.getX(), position.getY(), position.getZ(), 10, 0);
                player.sendMessage("SPAWNING!");
                event.delay(Random.get(2));
            }
        });


    }

}
