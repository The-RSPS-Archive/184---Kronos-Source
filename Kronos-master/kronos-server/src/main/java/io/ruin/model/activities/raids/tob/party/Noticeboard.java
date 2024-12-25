package io.ruin.model.activities.raids.tob.party;

import io.ruin.content.areas.ver_sinhaza.MysteriousStranger;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.model.activities.raids.tob.party.TheatrePartyManager.*;

/**
 * @author ReverendDread on 7/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Noticeboard {

    static {

        ObjectAction.register(32655, 1, (player, obj) -> {
            if (!MysteriousStranger.INSTANCE.talkedAboutVerzik(player)) { //talked about verzik
                player.dialogue(new NPCDialogue(8325, "Hey, Over here."));
                return;
            }
            TheatrePartyManager.instance().openPartyList(player);
        });

        InterfaceHandler.register(Interface.TOB_PARTY_LIST, (handler) -> {

            handler.actions[3] = (SlotAction) (player, slot) -> {
              switch (slot) {
                  case 0:
                      TheatrePartyManager.instance().openPartyList(player);
                      break;
                  case 1:
                      boolean create = Config.TOB_PARTY_LEADER.get(player) == -1;
                      if (create) TheatrePartyManager.instance().register(player);
                      TheatrePartyManager.instance().openPartyDetails(player);
                      break;
              }
            };

            handler.actions[16] = (SlotAction) (player, slot) -> {
                player.viewingTheatreSlot = slot;
                TheatrePartyManager.instance().openPartyDetailsViaSlot(player);
            };

        });

        InterfaceHandler.register(Interface.TOB_PARTY_DETAILS, (handler) -> {

            handler.actions[0] = (SlotAction) (player, slot) -> {
                switch (slot) {
                    case 0: //Back
                        TheatrePartyManager.instance().openPartyList(player);
                        break;
                    case 1: //Refresh
                        TheatrePartyManager.instance().openPartyDetailsViaSlot(player);
                        break;
                    case 2: //Unblock
                        TheatrePartyManager.instance().getPartyForSlot(player.viewingTheatreSlot).ifPresent(party -> {
                            if (party.isLeader(player)) {
                                player.dialogue(new MessageDialogue(
                                        "All players rejected from this party have been unblocked, and may apply again."
                                ), new ActionDialogue(() -> TheatrePartyManager.instance().openPartyDetails(player)));
                            }
                        });
                        break;
                    case 3: //Preferred party size
                        TheatrePartyManager.instance().getPartyForSlot(player.viewingTheatreSlot).ifPresent(party -> {
                            if (party.isLeader(player)) {
                                player.integerInput("Enter your prefered party size: ", (amount) -> {
                                    if (amount <= 0 || amount > 5) {
                                        player.dialogue(new MessageDialogue("Invalid number entered."));
                                    } else {
                                        party.setPreferedSize(amount);
                                        TheatrePartyManager.instance().refreshPartyDetails(player, party);
                                        player.sendMessage("You've set the party prefered size to " + amount + ".");
                                    }
                                });

                            } else {
                                player.sendMessage("You can't change that.");
                            }
                        });
                        break;
                    case 4: //Preferred combat level
                        TheatrePartyManager.instance().getPartyForSlot(player.viewingTheatreSlot).ifPresent(party -> {
                            if (party.isLeader(player)) {
                                player.integerInput("Enter your prefered combat level: ", (amount) -> {
                                    if (amount < 3 || amount > 126) {
                                        player.dialogue(new MessageDialogue("Invalid number entered."));
                                    } else {
                                        party.setPreferedLevel(amount);
                                        TheatrePartyManager.instance().refreshPartyDetails(player, party);
                                        player.sendMessage("You've set the party prefered combat level to " + amount + ".");
                                    }
                                });
                            } else {
                                player.sendMessage("You can't change that.");
                            }
                        });
                        break;
                    case 5: //Bottom buttons
                        TheatrePartyManager.instance().getPartyForSlot(player.viewingTheatreSlot).ifPresent(party -> {
                            switch (TheatrePartyManager.instance().getPartyStatus(player, party)) {
                                case IS_LEADER:
                                    TheatrePartyManager.instance().deregister(party);
                                    TheatrePartyManager.instance().openPartyList(player);
                                    break;
                                case IN_PARTY:
                                    party.leave(player.getUserId(), false);
                                    TheatrePartyManager.instance().openPartyList(player);
                                    break;
                                case WITHDRAW_APP:
                                    party.getApplicants().remove((Integer) player.getUserId());
                                    TheatrePartyManager.instance().refreshPartyDetails(player, party);
                                    break;
                                case APPLY_APP:
                                    if (!party.getBlocked().contains(player.getUserId())) {
                                        party.getApplicants().add(player.getUserId());
                                        TheatrePartyManager.instance().refreshPartyDetails(player, party);
                                    } else {
                                        player.sendMessage("You can't apply for this party at the moment.");
                                    }
                                    break;
                            }
                        });
                        break;
                    case 6: //Quit for leader
                        TheatrePartyManager.instance().getPartyForSlot(player.viewingTheatreSlot).ifPresent(party -> {
                            if (party.isLeader(player)) { //check if player is the leader
                                TheatrePartyManager.instance().deregister(party);
                                TheatrePartyManager.instance().openPartyList(player);
                            }
                        });
                        break;
                    case 7:
                    case 8:
                    case 9:
                    case 10: //Kick user
                        TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party -> {
                            int target = party.getUsers().get(slot - 6);
                            TheatrePartyManager.instance().forUserId(target).ifPresent(t -> {
                                if (party.isLeader(player))
                                    party.leave(target, false);
                                else
                                    party.leave(player.getUserId(), false);
                            });
                        });
                        break;
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15: //Accept app
                        TheatrePartyManager.instance().getPartyForSlot(player.viewingTheatreSlot).ifPresent(party -> {
                            if (party.isLeader(player)) {
                                int target = party.getApplicants().get(slot - 11);
                                party.getUsers().add(party.getApplicants().get(slot - 11));
                                party.getApplicants().remove(slot - 11);
                                TheatrePartyManager.instance().openPartyDetails(player);
                                TheatrePartyManager.instance().forUserId(target).ifPresent(p -> {
                                   if (p.isVisibleInterface(Interface.TOB_PARTY_DETAILS))
                                       TheatrePartyManager.instance().openPartyDetails(p);
                                });
                            }
                        });
                        break;
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25: //Reject app
                        TheatrePartyManager.instance().getPartyForSlot(player.viewingTheatreSlot).ifPresent(party -> {
                            if (party.isLeader(player)) {
                                int target = party.getApplicants().get(slot - 11);
                                party.getApplicants().remove((Integer) target);
                                party.getBlocked().add(target);
                                TheatrePartyManager.instance().openPartyDetails(player);
                                TheatrePartyManager.instance().forUserId(target).ifPresent(p -> {
                                    if (p.isVisibleInterface(Interface.TOB_PARTY_DETAILS))
                                        TheatrePartyManager.instance().openPartyDetails(p);
                                });
                            }
                        });
                        break;
                }
            };

        });

    }

}
