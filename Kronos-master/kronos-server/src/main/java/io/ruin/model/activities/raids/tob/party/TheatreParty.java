package io.ruin.model.activities.raids.tob.party;

import io.ruin.Server;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.dungeon.TheatreDungeon;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.utils.Config;
import io.ruin.util.ListenedList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * Represents a party in the Theatre of Blood
 *
 * @author ReverendDread on 7/15/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Getter
public class TheatreParty {

    private static final int NO_PARTY = 0, IN_PARTY = 1, PARTY_INSIDE = 2, DEAD_SPECTATING = 3;

    private int leaderId; //The leader userId
    private int slot; //The party slot
    private ListenedList<Integer> users; //Userids in the party
    private ListenedList<Integer> applicants; //Applicants applying for party
    private List<Integer> blocked; //Players blocked from applying
    private long tickCreatedOn; //The server tick the party was created on.
    @Setter private int preferedSize = 5; //default to full party size
    @Setter private int preferedLevel = 126;
    @Getter private TheatreDungeon dungeon;

    /**
     * Creates a new theatre party with the desired userId as leader.
     * @param userId
     */
    public TheatreParty(int userId, int slot) {
        this.leaderId = userId;
        this.slot = slot;
        this.users = new ListenedList<>();
        this.applicants = new ListenedList<>();
        this.blocked = new ArrayList<>();
        this.tickCreatedOn = Server.currentTick();
        this.dungeon = new TheatreDungeon(this);
        registerListeners();
        this.users.add(userId);
        TheatrePartyManager.instance().forUserId(userId).ifPresent(this::displayMembers);
    }

    /**
     * Notifies players in the party that the party is disbanded.
     */
    public void disbandNotifiy() {
        for (int user : getUsers()) {
            TheatrePartyManager.instance().forUserId(user).ifPresent(player -> {
                if (player.isVisibleInterface(Interface.TOB_PARTY_DETAILS)) {
                    TheatrePartyManager.instance().openPartyList(player);
                }
                getUsers().remove((Integer) user);
                TheatrePartyManager.instance().sendBlankPartyMembers(user);
            });
        }
    }

    /**
     * Performs the leave action on a user.
     * @param userId
     * @param resign
     */
    public void leave(int userId, boolean resign) {
        TheatrePartyManager.instance().forUserId(userId).ifPresent(player -> {
            getUsers().remove((Integer) player.getUserId());
            if (resign) {
                player.getMovement().teleport(TheatrePartyManager.OUTSIDE);
            }
            getUsers().forEach(user -> {
                TheatrePartyManager.instance().forUserId(user).ifPresent(p -> {
                    displayMembers(player);
                    p.sendMessage(Color.RED.wrap(StringUtils.capitalizeFirst(player.getName()) + " has left the party."));
                });
            });
            if (getUsers().size() <= 0) {
                TheatrePartyManager.instance().deregister(this); //collapse party if its empty.
            }
        });
    }

    /**
     *
     * @param player
     */
    public void displayMembers(Player player) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < 5; index++) {
            if (index < getUsers().size()) {
                int userId = getUsers().get(index);
                TheatrePartyManager.instance().forUserId(userId).ifPresent(user -> sb.append(StringUtils.capitalizeFirst(user.getName())).append("<br>"));
            } else {
                sb.append("-");
                if (index < 4)
                    sb.append("<br>");
            }
        }
        player.getPacketSender().sendString(Interface.TOB_PARTY_MEMBERS_OVERLAY, 9, sb.toString());
    }

    /**
     * Performs a consumer on every user in the party.
     * @param consumer
     */
    public void forPlayers(Consumer<Player> consumer) {
        getUsers().stream().map(userId -> TheatrePartyManager.instance().forUserId(userId).get()).forEach(consumer);
    }

    /**
     * Updates the party status for a player. (Indicated on the party hud)
     * @param player
     * @param status
     */
    public static void updatePartyStatus(Player player, PartyStatus status) {
        Config.THEATRE_OF_BLOOD.set(player, status.ordinal());
    }

    /**
     * Checks if the player is the leader of this party.
     * @param player
     * @return
     */
    public boolean isLeader(Player player) {
        return player.getUserId() == getLeaderId();
    }

    /**
     * Gets the leader for this party.
     * @return
     */
    public Optional<Player> getLeader() {
        return World.getPlayerByUid(getLeaderId());
    }

    /**
     * Registers listeners for list of users.
     */
    private void registerListeners() {
        users.postAdd((i) -> TheatrePartyManager.instance().forUserId(i).ifPresent(player -> {
            TheatreParty.updatePartyStatus(player, PartyStatus.IN_PARTY);
            Config.TOB_PARTY_LEADER.set(player, TheatrePartyManager.instance().forUserId(getLeaderId()).orElse(player).getIndex());
            forPlayers(p -> {
                TheatrePartyManager.instance().refreshPartyDetails(p, this);
                displayMembers(p);
            });
        }));
        users.postRemove((i) -> TheatrePartyManager.instance().forUserId(i).ifPresent(player -> {
            TheatreParty.updatePartyStatus(player, PartyStatus.NO_PARTY);
            Config.TOB_PARTY_LEADER.set(player, -1);
            TheatrePartyManager.instance().sendBlankPartyMembers(i);
            player.logoutListener = null;
            forPlayers(p -> {
                TheatrePartyManager.instance().refreshPartyDetails(p, this);
                displayMembers(p);
            });
        }));
        applicants.postAdd((i) -> {
            //refresh the player who sent the app
            TheatrePartyManager.instance().forUserId(i).ifPresent(player -> {
                if (player.isVisibleInterface(Interface.TOB_PARTY_DETAILS))
                    TheatrePartyManager.instance().refreshPartyDetails(player, this);
            });
            //refresh users in the party
            forPlayers(p -> {
                TheatrePartyManager.instance().refreshPartyDetails(p, this);
            });
        });
        applicants.postRemove((i) -> {
            //refresh the player who sent the app
            TheatrePartyManager.instance().forUserId(i).ifPresent(player -> {
                if (player.isVisibleInterface(Interface.TOB_PARTY_DETAILS))
                    TheatrePartyManager.instance().refreshPartyDetails(player, this);
            });
            //refresh users in the party
            forPlayers(p -> {
                TheatrePartyManager.instance().refreshPartyDetails(p, this);
            });
        });
        getLeader().ifPresent(leader -> {
            leader.logoutListener = new LogoutListener().onLogout(player -> TheatrePartyManager.instance().deregister(this));
        });
    }

}
