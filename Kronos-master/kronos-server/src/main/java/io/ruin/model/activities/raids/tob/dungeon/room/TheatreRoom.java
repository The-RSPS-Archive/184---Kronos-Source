package io.ruin.model.activities.raids.tob.dungeon.room;

import io.ruin.model.activities.raids.tob.dungeon.TheatreBoss;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.*;
import io.ruin.model.map.dynamic.DynamicMap;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public abstract class TheatreRoom extends DynamicMap {

    protected int lastSpectatorSpot;
    protected final TheatreParty party;
    protected TheatreBoss boss;
    protected TheatreRoom(TheatreParty party) {
        this.party = party;
    }

    //Returns the entrance spot for the room.
    public abstract Position getEntrance();
    //Called upon a room being loaded for the first time.
    public abstract void onLoad();
    //Used to register object listeners.
    public abstract void registerObjects();
    //Returns a list of spectator spots.
    public abstract List<Position> getSpectatorSpots();

    /**
     * Assigns the map listener for this area.
     * @param player
     */
    public void assignMapListener(Player player) {
        assignListener(player)
        .onEnter(p -> p.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.TOB_PARTY_MEMBERS_OVERLAY))
        .onExit((p, logout) ->  player.closeInterface(InterfaceType.PRIMARY_OVERLAY));
    }

    /**
     * Adds multi to this area.
     */
    public void addMultiArea() {
        Stream.of(getRegions()).filter(Objects::nonNull).forEach(region -> MultiZone.add(region.bounds));
    }

    /**
     * Removes multi to this area.
     */
    public void removeMultiArea() {
        Stream.of(getRegions()).filter(Objects::nonNull).forEach(region -> MultiZone.remove(region.bounds));
    }

    /**
     * Handles a player's death while in this area.
     * @param player
     */
    public void handlePlayerDeath(Player player) {
        player.getMovement().teleport(getSpectatorSpots().get(lastSpectatorSpot));
        lastSpectatorSpot++;
    }

}
