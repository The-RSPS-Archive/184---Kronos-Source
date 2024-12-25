package io.ruin.model.activities.raids.xeric.party;

import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.TabJournal;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Party {

    public static final int WAITING = 2;
    public static final int EXPLORING_UPPER_LEVEL = 258;
    public static final int REACHED_LOWER_LEVEL = 514;
    public static final int REACHED_BOTTOM_LEVEL = 770;
    public static final int GET_OUT = 1026;

    private Player leader;
    private LinkedList<Player> members;
    private ChambersOfXeric raid;
    private int points;

    public Party(Player leader) {
        this.leader = leader;
        members = new LinkedList<>();
        members.add(leader);
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public List<Player> getMembers() {
        return members;
    }

    public int getSize() {
        return members.size();
    }

    public boolean addMember(Player player) {
        return members.add(player);
    }

    public void removeMember(Player player) {
        members.remove(player);
        if (members.size() > 0 && player == leader) {
            leader = members.getFirst();
        }
    }

    public ChambersOfXeric getRaid() {
        return raid;
    }

    public void setRaid(ChambersOfXeric raid) {
        this.raid = raid;
    }

    static {
        InterfaceHandler.register(Interface.RAIDING_JOURNAL, h -> {
            h.actions[2] = (SimpleAction) player -> sendRaidJournal(player, player.raidsParty);
            h.actions[14] = (SimpleAction) player -> {
                if (player.raidsParty == null)
                    return;
                if (player.raidsParty.getLeader() != player) {
                    player.dialogue(new MessageDialogue("Only the party leader may start the raid."));
                    return;
                }
                player.dialogue(new OptionsDialogue("No-one may join the party after the raid begins.",
                        new Option("Begin the raid.", () -> {
                            player.raidsParty.getMembers().forEach(member -> {
                                if(Config.RAIDS_STAGE.get(member) <= 0) {
                                    member.sendMessage("<col=ef20ff>" + player.getName() + " has started the raid without you!");
                                    member.raidsParty.removeMember(member);
                                    member.raidsParty = null;
                                    RecruitingBoard.resetPartyPreferences(member);
                                    Config.RAIDS_PARTY.set(member, -1);
                                    updatePartyStage(member, -1);
                                    return;
                                }
                                sendRaidJournal(member, member.raidsParty);
                                member.sendMessage("<col=ef20ff>The raid has begun!");
                                updatePartyStage(member, EXPLORING_UPPER_LEVEL);
                            });
                            RecruitingBoard.ADVERTISED_PARTIES.remove(player);

                            player.raidsParty.getRaid().startRaid();
                            updatePartyStage(player, EXPLORING_UPPER_LEVEL);
                            sendRaidJournal(player, player.raidsParty);
                        }),
                        new Option("Don't begin the raid yet.", player::closeDialogue)
                ));
            };
        });
    }

    public static void sendRaidJournal(Player player, Party party) {
        /* set the journal interface */
        TabJournal.swap(player, Interface.RAIDING_JOURNAL);

        player.getPacketSender().sendClientScript(1547, "i", party.getMembers().size());
        /* party members information */
        int index = 0;
        if (party.getMembers().size() != 0) {
            for (Player member : party.getMembers()) {
                String memberInformation = "<col=" + (member == party.getLeader() ? "ffffff" : "9f9f9f") + ">" + member.getName() + "</col>" + "|" +
                        member.getCombat().getLevel() + "|" +
                        member.getStats().totalLevel;
                player.getPacketSender().sendClientScript(1548, "is", index++, memberInformation);
            }
        }
        player.getPacketSender().sendClientScript(1553, "i", index - 1);
  }

    public static void updatePartyStage(Player player, int stage) {
        if(player.raidsParty == null)
            return;
        if(player.raidsParty.getSize() > 1)
            stage += player.raidsParty.getMembers().size() * 2;
        Player partyLeader = player.raidsParty.getLeader();
        if (partyLeader == player)
            stage += 1;
        Config.RAIDS_STAGE.set(player, stage);
    }

    public void forPlayers(Consumer<Player> action) {
        members.forEach(action);
    }

    public void refreshAll() {
        forPlayers(p -> sendRaidJournal(p, this));
    }

    public void destroy() {
        raid = null;
        members = null;
        leader = null;
    }

    public int getPoints() {
        return points;
    }

    public void sendTotalPoints() {
        forPlayers(p -> Config.RAIDS_PARTY_POINTS.set(p, points));
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPartyCombatLevel() {
        //used to use average for this but was changed in one of the raid updates to just use highest combat instead
        return members.stream().mapToInt(p -> p.getCombat().getLevel()).max().orElse(126);
    }

}