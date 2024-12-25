package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.Server;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.utility.Broadcast;

import java.util.*;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public class VoteGambler {

    private static boolean enabled = true;

    private static final int VOTE_TICKET_ID = 1464;

    private static final int NPC_ID = 1815;

    private static final int REWARD_ID = 6199;

    private static Set<Integer> entries = new HashSet<>();

    private static List<Winner> unclaimedRewards = new ArrayList<>();

    static {
        ItemNPCAction.register(VOTE_TICKET_ID, NPC_ID, VoteGambler::handleEntry);
        NPCAction.register(NPC_ID, "Lottery-info", VoteGambler::lotteryInfo);

        /**
         * Starts event every 12 hours
         * 432 * 100 = 43200 seconds
         * Once it starts, it will...
         * check the entries -> Find a random entry -> Award the player associated -> Reset
         */
        World.startEvent(event -> {
            while (true) {
                event.delay(Server.toTicks(432 * 100));
                handleWinnerEntry();
            }
        });
        LoginListener.register(player -> {
            if (!unclaimedRewards.isEmpty()) {
                unclaimedRewards.stream()
                        .filter(win -> win.getUserId() == player.getUserId())
                        .findAny()
                        .ifPresent(winner -> {
                            player.getBank().add(REWARD_ID, 2);
                            sendMessage(player, "You have won the last vote lottery, your prizes have been deposited in your bank.");
                            unclaimedRewards.remove(winner);
                        });
            }
        });
    }


    private static void lotteryInfo(Player player, NPC npc) {
        int totalEntries = entries.size();
        String type = totalEntries == 1 ? "entry" : "entries";
        String message = totalEntries == 0 ? "There are no entries" : "There is currently " + Color.RED.wrap(totalEntries + " ") + type;
        player.dialogue(new NPCDialogue(npc, message));
    }

    private static void handleEntry(Player player, Item item, NPC npc) {
        if (!enabled) {
            sendMessage(player, "Vote lottery is currently disabled");
            return;
        }

        if (alreadyEntered(player)) {
            sendMessage(player, "You are already in the raffle draw.");
            return;
        }
        item.remove();
        addEntry(player);
        sendMessage(player, "Goodluck, you have entered the Vote lottery");
    }

    private static void handleWinnerEntry() {
        int totalEntries = entries.size();

        if (entries.isEmpty()) {
            sendBroadcast("No winners this raffle since there were no entries");
            return;
        }

        Optional<Integer> entryWinnerOptional = entries.stream().skip(new Random().nextInt(totalEntries)).findFirst();

        if (!entryWinnerOptional.isPresent()) {
            System.err.println("This should not be empty...");
            return;
        }

        int winnerId = entryWinnerOptional.get();

        Optional<Player> playerOptional = Optional.ofNullable(World.getPlayer(winnerId));
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            rewardAndReset(player);
        } else {
            unclaimedRewards.add(new Winner(winnerId));
            sendBroadcast("The winner has been picked for Vote lottery, vote now for a chance to win 2x Mystery boxes");
            clearAllEntries();
        }
    }


    private static void rewardAndReset(Player player) {
        player.getInventory().add(REWARD_ID, 2);
        sendMessage(player, "You have won the vote raffle draw");
        sendBroadcast(StringUtils.capitalizeFirst(player.getName()) + " has won the Vote Lottery");
        sendBroadcast("Vote now for a chance to win 2x Mystery boxes.");
        clearAllEntries();
    }

    private static void addEntry(Player player) {
        entries.add(player.getUserId());
    }

    private static boolean alreadyEntered(Player player) {
        return entries.contains(player.getUserId());
    }

    private static void clearAllEntries() {
        entries.clear();
    }


    private static void sendBroadcast(String message) {
        Broadcast.WORLD.sendNews("[Vote Lottery] " + message);
    }

    private static void sendMessage(Player player, String message) {
        player.sendFilteredMessage(Color.DARK_RED.wrap("[Vote Lottery]") + " " + message);
    }


    static class Winner {
        int userId;

        Winner(int userId) {
            this.userId = userId;
        }

        public int getUserId() {
            return userId;
        }
    }
}
