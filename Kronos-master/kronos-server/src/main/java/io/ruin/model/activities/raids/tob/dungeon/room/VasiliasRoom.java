package io.ruin.model.activities.raids.tob.dungeon.room;

import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Position;

import java.util.List;

/**
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class VasiliasRoom extends TheatreRoom {

    public VasiliasRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(13122, 1);
    }

    @Override
    public void registerObjects() {

    }

    @Override
    public List<Position> getSpectatorSpots() {
        return null;
    }

    @Override
    public Position getEntrance() {
        return Position.of(3295, 4283, 0);
    }

}
