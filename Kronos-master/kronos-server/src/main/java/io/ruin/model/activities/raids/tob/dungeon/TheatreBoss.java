package io.ruin.model.activities.raids.tob.dungeon;

import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author ReverendDread on 7/28/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class TheatreBoss extends NPC {

    /**
     * The party associated with this boss.
     */
    protected TheatreParty party;

    /**
     * Creates a new theatre boss object.
     * @param id
     *          the npc id.
     * @param party
     *          the party fighting the boss.
     */
    public TheatreBoss(int id, TheatreParty party) {
        super(id);
        this.party = party;
    }

    /**
     * Returns a filtered nonNull stream of players in the party.
     * @return
     */
    public Stream<Player> getTargets() {
        return party.getUsers().stream().map(userId -> {
            Optional<Player> player = TheatrePartyManager.instance().forUserId(userId);
            return player.orElse(null);
        }).filter(Objects::nonNull);
    }

    public void postEffects() {
        npc.setMaxHp((int) ((double) npc.getMaxHp() * getHealthMultiplier()));
    }

    public double getHealthMultiplier() {
        int size = party.getUsers().size();
        return (size < 3 ? .75 : size == 4 ? .875 : 1);
    }

}
