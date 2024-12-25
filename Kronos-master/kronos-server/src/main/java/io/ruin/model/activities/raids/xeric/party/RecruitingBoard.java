package io.ruin.model.activities.raids.xeric.party;

import io.ruin.Server;
import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.model.stat.StatType.CLIENT_ORDER;

public class RecruitingBoard {

    private static final Bounds BOUNDS = new Bounds(1215, 3541, 1277, 3581, 0);
    public static List<Player> ADVERTISED_PARTIES = new ArrayList<>(50);

    static {
        ObjectAction.register(29776, "read", (player, obj) -> displayRecruitingBoard(player));
        InterfaceHandler.register(Interface.RAIDING_RECRUITING_BOARD, h -> {
            h.actions[3] = (DefaultAction) (player, option, slot, itemId) -> {
                if (slot == 0)
                    displayRecruitingBoard(player);
                if (slot == 1)
                    if (Config.RAIDS_PARTY.get(player) == -1)
                        createParty(player, true);
                    else
                        displayParty(player, player.raidsParty);
            };
            h.actions[14] = (DefaultAction) (player, option, slot, itemId) -> {
                if (slot < ADVERTISED_PARTIES.size() && ADVERTISED_PARTIES.get(slot) != null) {
                    Player partyLeader = ADVERTISED_PARTIES.get(slot);
                    displayParty(player, partyLeader.raidsParty);
                }
            };
        });
        InterfaceHandler.register(Interface.RAIDING_PARTY, h -> {
            h.actions[3] = (DefaultAction) (player, option, slot, itemId) -> {
                if (slot == 0)
                    if (player.raidsParty == null)
                        joinParty(player, player.viewingParty);
                    else
                        leaveParty(player);
                if (slot == 1)
                    advertiseParty(player, player.advertisingParty);
                if (slot == 2)
                    disbandParty(player);
                if (slot == 3) {
                    displayParty(player, player.raidsParty);
                }
                if (slot == 4)
                    displayRecruitingBoard(player);
                if (slot == 5)
                    setPreferredSetting(player, "party size", Config.RAIDS_PREFERRED_PARTY_SIZE, 1, 100);
                if (slot == 6)
                    setPreferredSetting(player, "combat level", Config.RAIDS_PREFERRED_COMBAT_LEVEL, 4, 126);
                if (slot == 7)
                    setPreferredSetting(player, "skill total", Config.RAIDS_PREFERRED_SKILL_TOTAL, 33, 2277);
                if (slot == 8)
                    player.sendMessage("Challenge mode is not currently available.");
            };
        });
    }

    private static void displayRecruitingBoard(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RAIDING_RECRUITING_BOARD);
        for (int i = 0; i < 40; i++)
            if (i < ADVERTISED_PARTIES.size() && ADVERTISED_PARTIES.get(i) != null) {
                Player partyHost = ADVERTISED_PARTIES.get(i);
                String partyInformation = "<col=ffffff>" + partyHost.getName() + "</col>" + "|" +
                        (partyHost.raidsParty.getMembers().size() + 1) + "|" +
                        Config.RAIDS_PREFERRED_PARTY_SIZE.get(partyHost) + "|" +
                        Config.RAIDS_PREFERRED_COMBAT_LEVEL.get(partyHost) + "|" +
                        Config.RAIDS_PREFERRED_SKILL_TOTAL.get(partyHost) + "|" +
                        (Server.currentTick() - partyHost.advertisementStartTick) + "|";
                player.getPacketSender().sendClientScript(1566, "is", i, partyInformation);
            } else {
                player.getPacketSender().sendClientScript(1566, "is", i, "");
            }
    }

    public static void createParty(Player player, boolean displayInterface) {
        Config.RAIDS_PARTY.set(player, player.getIndex());
        player.raidsParty = new Party(player);
        resetPartyPreferences(player);
        if (displayInterface)
            displayParty(player, player.raidsParty);
    }

    public static void joinParty(Player player, Party party) {
        if (!ADVERTISED_PARTIES.contains(party.getLeader())) {
            player.dialogue(new MessageDialogue("This party is no longer recruiting."));
            return;
        }
        player.closeInterface(InterfaceType.MAIN);
        player.dialogue(new MessageDialogue("Requesting..").hideContinue());
        party.getLeader().dialogue(new OptionsDialogue(player.getName() + " (" + player.getCombat().getLevel() + " combat, " + NumberUtils.formatNumber(player.getStats().totalLevel) + " total level) wishes to join your party.",
                new Option("Accept", () -> {
                    Player leader = party.getLeader();
                    if (player.raidsParty != null) {
                        leader.dialogue(new MessageDialogue(player.getName() + " is already in a party."));
                        return;
                    }
                    party.addMember(player);
                    player.raidsParty = party;
                    copyPartyPreferences(player, party);
                    if (leader.isVisibleInterface(Interface.RAIDING_PARTY))
                        displayParty(leader, party);
                    displayParty(player, player.raidsParty);
                    Config.RAIDS_PARTY.set(player, leader.getIndex());
                    player.sendMessage("You've joined " + leader.getName() + "'s raid party.");
                    party.getLeader().sendMessage(player.getName() + " has joined your raid party.");
                }),
                new Option("Decline", () -> {
                    player.dialogue(new MessageDialogue(party.getLeader().getName() + " has declined your request to join his raid party."));
                    party.getLeader().sendMessage("You decline " + player.getName() + "'s request to join your party.");
                })
        ));
    }

    public static void invite(Player player, Player target) {
        if (player.raidsParty == null) {
            player.sendMessage("You need to create a party before requesting " + target.getName() + " to join it.");
            return;
        }
        if (target.raidsParty != null) {
            player.sendMessage(target.getName() + " is already in a party.");
            return;
        }
        if (player.raidsParty.getLeader() != player) {
            player.sendMessage("You need to be the leader of your party in order to invite a player to join it.");
            return;
        }

        player.dialogue(new MessageDialogue("Requesting..").hideContinue());
        target.dialogue(new OptionsDialogue(player.getName() + " has invited you to join their party.",
                new Option("Accept", () -> {
                    if (target.raidsParty != null) {
                        player.dialogue(new MessageDialogue(target.getName() + " is already in a party."));
                        return;
                    }
                    player.raidsParty.addMember(target);
                    target.raidsParty = player.raidsParty;
                    copyPartyPreferences(target, player.raidsParty);
                    Config.RAIDS_PARTY.set(target, player.getIndex());
                    target.sendMessage("You've joined " + player.getName() + "'s raid party.");
                    player.dialogue(new MessageDialogue(target.getName() + " has joined your raid party."));
                }),
                new Option("Decline", () -> {
                    player.dialogue(new MessageDialogue(target.getName() + " has declined your request to join your raid party."));
                    target.sendMessage("Your decline " + player.getName() + "'s request to join their party.");
                })
        ));
    }

    public static void disbandParty(Player player) {
        Config.RAIDS_PARTY.set(player, -1);
        player.dialogue(
                new MessageDialogue("Your party has been disbanded."),
                new ActionDialogue(() -> {
                    if (player.getAbsX() == 1246 && player.getAbsY() == 3562)
                        displayRecruitingBoard(player);
                }));

        /* reset the player's party preferences */
        resetPartyPreferences(player);

        /* terminate the party */
        if (player.raidsParty.getLeader() == player) {

            /* remove all the players from the party */
            player.raidsParty.getMembers().forEach(member -> {
                player.raidsParty.removeMember(member);
                member.sendMessage("<col=ef20ff>" + player.getName() + " has debanded the party.");
                member.raidsParty = null;
                Config.RAIDS_PARTY.set(member, -1);
                if (member.isVisibleInterface(Interface.RAIDING_PARTY))
                    displayRecruitingBoard(member);
            });

            /* if the party is advertised, remove it from the list */
            player.advertisingParty = false;
            player.advertisementStartTick = 0L;
            player.raidsParty = null;
            if (ADVERTISED_PARTIES.contains(player))
                ADVERTISED_PARTIES.remove(player);
        }
    }

    public static void leaveParty(Player player) {
        Player partyLeader = player.raidsParty.getLeader();
        if (partyLeader == player) {
            disbandParty(player);
            return;
        }
        player.sendMessage("<col=ef20ff> You leave " + player.raidsParty.getLeader().getName() + "'s party.");
        player.raidsParty.removeMember(player);
        player.raidsParty = null;
        resetPartyPreferences(player);
        Config.RAIDS_PARTY.set(player, -1);
        if (player.isVisibleInterface(Interface.RAIDING_PARTY))
            displayRecruitingBoard(player);
        if (partyLeader.isVisibleInterface(Interface.RAIDING_PARTY))
            displayParty(partyLeader, partyLeader.raidsParty);
    }

    private static void advertiseParty(Player player, boolean delist) {
        if (delist)
            ADVERTISED_PARTIES.remove(player);
        else
            ADVERTISED_PARTIES.add(player);
        player.advertisingParty = !player.advertisingParty;
        player.advertisementStartTick = delist ? 0L : Server.currentTick();
        displayParty(player, player.raidsParty);
    }

    private static void displayParty(Player player, Party party) {
        if (party == null) {
            if (Config.RAIDS_PARTY.get(player) != -1) {
                Config.RAIDS_PARTY.set(player, -1);
            }
            return;
        }

        /* open the interface and fire the scripts! :^ ) */
        player.openInterface(InterfaceType.MAIN, 507);

        /* build the party members statistics */
        StringBuilder leaderInformation = new StringBuilder();

        /* team leader information */
        Player partyLeader = party.getLeader();
        leaderInformation.append("<col=ffffff>").append(partyLeader.getName()).append("</col>").append("|");
        leaderInformation.append(partyLeader.getCombat().getLevel()).append("|");
        leaderInformation.append(partyLeader.getStats().totalLevel).append("|");
        for (StatType stat : CLIENT_ORDER)
            leaderInformation.append(partyLeader.getStats().get(stat).fixedLevel).append("|");
        player.getPacketSender().sendClientScript(1517, "is", 0, leaderInformation.toString());

        /* party members information */
        if (party.getMembers().size() != 0) {
            for (int i = 0; i < party.getMembers().size(); i++) {
                if (party.getMembers().get(i) == party.getLeader())
                    continue;
                StringBuilder memberInformation = new StringBuilder();
                memberInformation.append("<col=9f9f9f>").append(party.getMembers().get(i).getName()).append("</col>").append("|");
                memberInformation.append(party.getMembers().get(i).getCombat().getLevel()).append("|");
                memberInformation.append(party.getMembers().get(i).getStats().totalLevel).append("|");
                for (StatType stat : CLIENT_ORDER)
                    memberInformation.append(party.getMembers().get(i).getStats().get(stat).fixedLevel).append("|");
                player.getPacketSender().sendClientScript(1517, "is", i + 1, memberInformation.toString());
            }
        }

        player.getPacketSender().sendClientScript(1524, "siii",
                "Raiding Party of " + partyLeader.getName() + " (" + (party.getMembers().size()) + ")",
                player == partyLeader ? 2 : player.raidsParty == party ? 1 : 0,
                player.advertisingParty ? 1 : 0,
                player.partyAdvertisementsRemaining);

        /* if we're not on a party, set the party we're viewing */
        if (player.raidsParty == null)
            player.viewingParty = party;
    }

    private static void copyPartyPreferences(Player player, Party party) {
        Config.RAIDS_PREFERRED_PARTY_SIZE.set(player, Config.RAIDS_PREFERRED_PARTY_SIZE.get(party.getLeader()));
        Config.RAIDS_PREFERRED_COMBAT_LEVEL.set(player, Config.RAIDS_PREFERRED_COMBAT_LEVEL.get(party.getLeader()));
        Config.RAIDS_PREFERRED_SKILL_TOTAL.set(player, Config.RAIDS_PREFERRED_SKILL_TOTAL.get(party.getLeader()));
    }

    public static void resetPartyPreferences(Player player) {
        Config.RAIDS_PREFERRED_PARTY_SIZE.set(player, 0);
        Config.RAIDS_PREFERRED_COMBAT_LEVEL.set(player, 0);
        Config.RAIDS_PREFERRED_SKILL_TOTAL.set(player, 0);
    }

    private static void setPreferredSetting(Player player, String setting, Config config, int minValue, int maxValue) {
        player.closeInterface(InterfaceType.MAIN);
        player.integerInput("Set a preferred " + setting + " (or 0 to clear it):", amt -> {
            config.set(player, minValue > amt ? 0 : Math.min(maxValue, amt));
            player.raidsParty.getMembers().forEach(member -> config.set(member, minValue > amt ? 0 : Math.min(maxValue, amt)));
            displayParty(player, player.raidsParty);
        });
    }

    static {
        MapListener.registerBounds(BOUNDS)
                .onEnter(player -> player.setAction(1, PlayerAction.INVITE))
                .onExit((player, logout) -> {
                    if (logout && player.raidsParty != null)
                        leaveParty(player);
                    else
                        player.setAction(1, null);
                });
    }
}
