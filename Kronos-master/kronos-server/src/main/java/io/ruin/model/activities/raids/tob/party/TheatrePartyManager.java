package io.ruin.model.activities.raids.tob.party;

import com.google.api.client.util.Lists;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.dungeon.RoomType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.util.ListenedList;
import io.ruin.utility.CS2Script;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 *
 * Handles everything to do with managing tob parties.
 *
 * applicant cs2 2321 "s" name | splitter
 * member cs2 2317 "is" leaderidx name | splitter
 *
 * @author ReverendDread on 7/15/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public final class TheatrePartyManager {

    private static TheatrePartyManager singleton;
    private static final String SEPERATOR = "|";
    @Getter
    public static final int[] slots = {
            9, 8, 7, 6, 5, 44, 43, 42, 41, 40, 4, 39, 38, 37, 36, 35, 34, 33, 32,
            31, 30, 3, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 2, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 1, 0
    };
    public static final int IN_PARTY = 1, IS_LEADER = 2, WITHDRAW_APP = 3, APPLY_APP = 4;
    public static final Position OUTSIDE = Position.of(3677, 3219, 0);

    /**
     * List of active parties.
     */
    private ListenedList<TheatreParty> parties = new ListenedList<>();

    public TheatrePartyManager() {
        parties.postRemove(TheatreParty::disbandNotifiy);
    }

    /**
     * Registers a party to the active parties.
     * @param leader
     * @return
     */
    public Optional<TheatreParty> register(Player leader) {
        boolean exists = getPartyForPlayer(leader.getUserId()).isPresent();
        int slot = findSlot();
        if (exists) {
            leader.sendMessage("Your already in a party.");
            return Optional.empty();
        }
        if (slot == -1) {
            leader.sendMessage("You can't create a party right now.");
            return Optional.empty();
        }
        TheatreParty party = new TheatreParty(leader.getUserId(), slot);
        parties.add(party);
        leader.sendMessage("You've created a Theatre of Blood party.");
        Config.TOB_PARTY_LEADER.set(leader, leader.getIndex());
        return Optional.of(party);
    }

    public void deregister(TheatreParty party) {
        getPartyForSlot(party.getSlot()).ifPresent((found) -> parties.remove(found));
    }

    /**
     * Called when the party leader leaves.
     * @param party
     */
    public void leaderLeave(TheatreParty party) {
        getPartyForSlot(party.getSlot()).ifPresent((found) -> {

        });
    }

    /**
     * Shows the active parties list.
     * @param player
     */
    public void openPartyList(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.TOB_PARTY_LIST);
        for (int index = 0; index < getSlots().length; index++) {
            int slot = getSlots()[index];
            if (index < parties.size()) {
                TheatreParty party = parties.get(index);
                Player leader = forUserId(party.getLeaderId()).get();
                StringBuilder sb = new StringBuilder().
                append(StringUtils.capitalizeFirst(leader.getName())).append(SEPERATOR).
                append(party.getUsers().size()).append(SEPERATOR).
                append(party.getPreferedSize()).append(SEPERATOR).
                append(party.getPreferedLevel()).append(SEPERATOR);
                CS2Script.TOB_PARTYLIST_ADDLINE.sendScript(player, slot, sb.toString());
            } else {
                CS2Script.TOB_PARTYLIST_ADDLINE.sendScript(player, slot, "");
            }
        }
    }

    /**
     * Opens the party details info for the desired slot.
     * @param player
     */
    public void openPartyDetailsViaSlot(Player player) {
        getPartyForSlot(player.viewingTheatreSlot).ifPresent((party) -> {
            player.openInterface(InterfaceType.MAIN, Interface.TOB_PARTY_DETAILS);
            for (int index = 0; index < 5; index++) {
                if (index < party.getUsers().size()) {
                    int userId = party.getUsers().get(index);
                    forUserId(userId).ifPresent(member ->
                            CS2Script.TOB_PARTYDETAILS_ADDMEMBER.sendScript(player, party.isLeader(player) ? IS_LEADER : IN_PARTY, buildMember(member)));
                } else {
                    CS2Script.TOB_PARTYDETAILS_ADDMEMBER.sendScript(player, party.isLeader(player) ? IS_LEADER : IN_PARTY, buildBlank());
                }
            }
            for (int userId : party.getApplicants()) {
                forUserId(userId).ifPresent(applicant ->
                        CS2Script.TOB_PARTYDETAILS_ADDAPPLICANT.sendScript(player, buildApplicant(applicant)));
            }
            refreshPartyDetails(player, party);
        });
    }

    /**
     * Opens the party details info for the players party.
     * @param player
     */
    public void openPartyDetails(Player player) {
        getPartyForPlayer(player.getUserId()).ifPresent((party) -> {
            player.openInterface(InterfaceType.MAIN, Interface.TOB_PARTY_DETAILS);
            player.viewingTheatreSlot = party.getSlot();
            for (int index = 0; index < 5; index++) {
                if (index < party.getUsers().size()) {
                    int userId = party.getUsers().get(index);
                    forUserId(userId).ifPresent(member ->
                            CS2Script.TOB_PARTYDETAILS_ADDMEMBER.sendScript(player, party.isLeader(player) ? IS_LEADER : IN_PARTY, buildMember(member)));
                } else {
                    CS2Script.TOB_PARTYDETAILS_ADDMEMBER.sendScript(player, party.isLeader(player) ? IS_LEADER : IN_PARTY, buildBlank());
                }
            }
            for (int userId : party.getApplicants()) {
                forUserId(userId).ifPresent(applicant ->
                        CS2Script.TOB_PARTYDETAILS_ADDAPPLICANT.sendScript(player, buildApplicant(applicant)));
            }
            refreshPartyDetails(player, party);
        });
    }

    public void refreshPartyDetails(Player player, TheatreParty party) {
        CS2Script.TOB_PARTYDETAILS_REFRESH.sendScript(player, getPartyStatus(player, party), party.getPreferedSize(), party.getPreferedLevel());
    }

    public int getPartyStatus(Player player, TheatreParty party) {
        return party.isLeader(player) ? IS_LEADER :
                party.getUsers().contains(player.getUserId()) ? IN_PARTY :
                        party.getApplicants().contains(player.getUserId()) ? WITHDRAW_APP : APPLY_APP;
    }

    /**
     * Builds a formatted string for a member.
     * @param member The member.
     * @return
     */
    private String buildMember(Player member) {
        return new StringBuilder()
        .append(StringUtils.capitalizeFirst(member.getName())).append(SEPERATOR)
        .append(member.getCombat().getLevel()).append(SEPERATOR)
        .append(member.getStats().get(StatType.Attack).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Strength).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Ranged).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Magic).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Defence).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Hitpoints).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Prayer).fixedLevel).append(SEPERATOR)
        .append(member.theatreOfBloodCompleted).append(SEPERATOR)
        .toString();
    }

    /**
     * Builds a formatted string for a member or applicant.
     * @param member The member.
     * @return
     */
    private String buildApplicant(Player member) {
        return new StringBuilder()
        .append(StringUtils.capitalizeFirst(member.getName())).append(SEPERATOR)
        .append(member.getCombat().getLevel()).append(SEPERATOR)
        .append(member.getStats().get(StatType.Attack).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Strength).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Ranged).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Magic).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Defence).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Hitpoints).fixedLevel).append(SEPERATOR)
        .append(member.getStats().get(StatType.Prayer).fixedLevel).append(SEPERATOR)
        .append(member.theatreOfBloodCompleted).append(SEPERATOR)
        .toString();
    }

    /**
     * Builds a blank party member for the party details.
     * @return
     */
    private String buildBlank() {
        return new StringBuilder()
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .append("-").append(SEPERATOR)
        .toString();
    }

    /**
     * Gets a party optional for the desired userId.
     * @param userId
     * @return
     */
    public Optional<TheatreParty> getPartyForPlayer(int userId) {
        return parties.stream().filter(party -> forUserId(userId).isPresent()).findFirst();
    }

    /**
     * Gets a party optional for the desired slot.
     * @param slot
     * @return
     */
    public Optional<TheatreParty> getPartyForSlot(int slot) {
        return parties.stream().filter(party -> party.getSlot() == slot).findFirst();
    }

    /**
     * Finds an empty slot for a party.
     * @return
     */
    private int findSlot() {
        for (int slot : getSlots()) {
            boolean isFree = parties.stream().noneMatch(theatreParty -> theatreParty.getSlot() == slot);
            if (isFree)
                return slot;
        }
        return -1;
    }

    /**
     * Sends blank party members on the party overlay.
     * @param userId
     */
    public void sendBlankPartyMembers(int userId) {
        forUserId(userId).ifPresent((player) -> {
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < 5; index++) {
                sb.append("-");
                if (index < 4)
                    sb.append("<br>");
            }
            player.getPacketSender().sendString(Interface.TOB_PARTY_MEMBERS_OVERLAY, 9, sb.toString());
        });
    }

    /**
     * Gets the player assosiated with the desired userId.
     * @param userId
     * @return
     */
    public Optional<Player> forUserId(int userId) {
        return World.getPlayerByUid(userId);
    }

    /**
     * Returns a singleton of the theatre party manager.
     * @return
     */
    public static TheatrePartyManager instance() {
        if (singleton == null)
            singleton = new TheatrePartyManager();
        return singleton;
    }

    static {

        MapListener.registerBounds(
                new Bounds(
                new Position(3642, 3204, 0),
                new Position(3683, 3234, 0), 0)
        )
        .onEnter(player -> {
            player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.TOB_PARTY_MEMBERS_OVERLAY);
            TheatrePartyManager.instance().sendBlankPartyMembers(player.getUserId());
        });

        ObjectAction.register(32653, "enter", (player, obj) -> {
            boolean dialogue = TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).isPresent();
            if (dialogue) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party -> {
                    if (!player.isAcceptedTheatreRisk()) {
                        player.dialogue(
                            new MessageDialogue("Warning: The Theatre of Blood is dangerous. Once you enter, " +
                                    "you are at risk of death. The only method of escape is to resign or defeat the Theatre." +
                                    "Teleporting is restricted and logging out is considered a death. Your items will be lost if the whole party dies."),
                            new MessageDialogue("You will not see the warning again should you accept."),
                            new OptionsDialogue(
                                    new Option("Yes - proceed.", () -> {
                                        player.setAcceptedTheatreRisk(true);
                                        crystals(player, party);
                                    }),
                                    new Option("No - stay out.")
                            )
                        );
                    } else {
                        crystals(player, party);
                    }
                });
            } else {
                player.dialogue(new OptionsDialogue(Color.MAROON.wrap("You are not in a party..."),
                    new Option("Form or join a party", () -> {
                        player.startEvent(e -> {
                           player.getRouteFinder().routeAbsolute(3664, 3219);
                           e.waitForMovement(player);
                            TheatrePartyManager.instance().openPartyList(player);
                        });
                    }),
                    new Option("<str>Observe a specific party</str>", () -> { /* TODO */ }),
                    new Option("<str>Observe recent party</str>", () -> { /* TODO */ }),
                    new Option("Cancel")
                ));
            }
        });

        ObjectAction.register(32756, "talk-to", ((player, obj) -> {
            player.dialogue(new NPCDialogue(8323, "Lady Verzik Litur lets people in here to perform, not to chat."),
                new OptionsDialogue(
                        new Option("What am I supposed to do in here?", () -> {
                            player.dialogue(
                                new NPCDialogue(8323, "Pass through the barrier and face your challenge. If you survive, and your struggle entertains Verzik, she will grant you freedom from the blood tithes."),
                                new PlayerDialogue("Okay.")
                            );
                        }),
                        new Option("I want to resign from the party.", () ->
                            player.dialogue(
                                new PlayerDialogue("I want to resign from the party."),
                                new OptionsDialogue("There is no penalty for resigning now.",
                                    new Option("Resign and leave the Theatre.", () -> {
                                        TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party -> {
                                            party.leave(player.getUserId(), false);
                                        });
                                    }),
                                    new Option("Do not resign.", () ->
                                        player.dialogue(
                                            new PlayerDialogue("Actually, I'll stay in for now."),
                                            new NPCDialogue(8323, "As you wish.")
                                        )
                                    )
                                )
                            )
                        ),
                        new Option("Sorry, I'll get on with it.", () -> player.dialogue(new PlayerDialogue("Sorry I'll get on with it.")))
                )
            );
        }));
//        ObjectAction.register(32756, "resign", ((player, obj) -> {
//
//        }));

    }

    /**
     * Starts the buying crystals dialogue
     * @param player
     * @param party
     */
    private static void crystals(Player player, TheatreParty party) {
        if (!player.isAcceptedTheatreCrystals()) {
            player.dialogue(new OptionsDialogue(Color.MAROON.wrap("Only Verzik's crystals can teleport out of the Theatre."),
                    new Option("Go and buy teleport crystals.", () -> {
                        player.startEvent(e -> {
                            player.getRouteFinder().routeAbsolute(3673, 3222);
                            e.waitForMovement(player);
                            //TODO open shop
                        });
                    }),
                    new Option("Enter the Theatre without any teleport crystals.", () -> enterTheatre(player, party)),
                    new Option("Enter the Theatre, and don't ask this again.", () -> {
                        player.setAcceptedTheatreCrystals(true);
                        enterTheatre(player, party);
                    })
            ));
        } else {
            enterTheatre(player, party);
        }
    }

    /**
     * Starts the enter tob dialogue
     * @param player
     * @param party
     */
    private static void enterTheatre(Player player, TheatreParty party) {
        if (party.isLeader(player)) {
            player.dialogue(new OptionsDialogue("Is your party ready? (Members: " + party.getUsers().size() + ")",
                    new Option("Yes, let's go!", () -> {
                        party.forPlayers(p -> {
                            TheatreParty.updatePartyStatus(p, PartyStatus.PARTY_INSIDE);
                        });
                        party.getDungeon().build();
                        startDungeon(player, party);
                    }),
                    new Option("Cancel.")
            ));
        } else {
            if (!party.getDungeon().isBuilt()) {
                player.sendMessage(Color.RED.wrap("The party leader has to start the dungeon first."));
                return;
            }
            startDungeon(player, party);
        }
    }

    /**
     * Starts the dungeon for the desired player, taking in their party.
     * @param player
     * @param party
     */
    private static void startDungeon(Player player, TheatreParty party) {
        party.getDungeon().enterRoom(player, RoomType.MAIDEN);
        Config.THEATRE_HUD_STATE.set(player, 2);
        List<String> names = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            if (i < party.getUsers().size()) {
                if (i == 0)
                    names.add("Me");
                else
                    names.add(TheatrePartyManager.instance().forUserId(party.getUsers().get(i)).get().getName());
            } else {
                names.add("");
            }
        }
        CS2Script.TOB_HUD_STATUSNAMES.sendScript(player, names.toArray());
        player.hitListener = new HitListener().postDamage(hit -> {
            for (int i = 0; i < party.getUsers().size(); i++) {
                int userId = party.getUsers().get(i);
                int finalI = i;
                TheatrePartyManager.instance().forUserId(userId).ifPresent(plr -> Config.varpbit(6442 + finalI, false).set(player, (int) ((double) plr.getHp() / plr.getMaxHp() * 30)));
            }
        });
        //6448 health bar overlay
    }

}
