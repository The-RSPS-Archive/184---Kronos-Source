package io.ruin.model.activities.pvp.leaderboard;

import io.ruin.Server;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.*;
import java.util.Map.Entry;


public class Leaderboard {

    /**
     * Edgeville Pkers
     */
    public static HashMap<Integer, EdgePker> edgePkers = new HashMap<>();

    /**
     * Deep Wilderness Pkers
     */
    public static HashMap<Integer, DeepWildernessPker> deepWildernesPkers = new HashMap<>();

    /**
     * Unclaimed Leaderboard Rewards
     */
    private static List<Reward> unclaimedRewards = new ArrayList<>();

    static {
        /*
         * Register the leaderboard event
         */
        World.startEvent(e -> {
            /*
             * Start event every 24 hours
             */
            while (true) {
                e.delay(Server.toTicks(864 * 100));
                pullWinners();
            }
        });

        /*
          Check if player has unclaimed rewards
         */
        LoginListener.register(player -> {
            if (!unclaimedRewards.isEmpty()) {
                unclaimedRewards.stream().filter(reward -> player.getUserId() == reward.userId).findAny().ifPresent(reward -> {
                    Item item = reward.reward;
                    player.getBank().add(item.getId(), item.getAmount());
                    unclaimedRewards.remove(reward);
                });
            }

        });

        ObjectAction.register(3192, "Edge PKing", ((player, obj) -> openEdgeLeaderBoard(player)));
        ObjectAction.register(3192, "Deep Wild PKing", ((player, obj) -> openDeepWildernessLeaderBoard(player)));
    }

    private static void openEdgeLeaderBoard(Player player) {
        player.openInterface(InterfaceType.MAIN, 108);
        for (int i = 1; i < 62; i++) {
            player.getPacketSender().sendString(108, i, "");
        }
        player.getPacketSender().sendString(108, 19, "<col=FF0000>Rewards: 1st: 1x SMbox - 2nd: 2x Mbox - 3rd: 1x Mbox</col>");
        player.getPacketSender().sendString(108, 16, "");
        player.getPacketSender().sendString(108, 17, "");
        player.getPacketSender().sendString(108, 20, "Edgeville Pking Leaderboard");
        if (edgePkers != null && !edgePkers.isEmpty()) {
            Set<Entry<Integer, EdgePker>> map = sortByValuesEdge(edgePkers);
            Iterator iterator2 = map.iterator();
            int index = 1;
            while (iterator2.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator2.next();
                EdgePker pker = ((EdgePker) entry.getValue());
                player.getPacketSender().sendString(108, 21 + index, index + ": " + pker);
                index++;
                if (index + 21 == 32) {
                    break;
                }
            }
        }
    }

    private static void openDeepWildernessLeaderBoard(Player player) {
        player.openInterface(InterfaceType.MAIN, 108);

        for (int i = 1; i < 62; i++) {
            player.getPacketSender().sendString(108, i, "");
        }
        player.getPacketSender().sendString(108, 19, "<col=FF0000>Rewards: 1st: 1x SMbox - 2nd: 2x Mbox - 3rd: 1x Mbox</col>");
        player.getPacketSender().sendString(108, 16, "");
        player.getPacketSender().sendString(108, 17, "");
        player.getPacketSender().sendString(108, 20, "Deep Wilderness Pking Leaderboard");

        if (deepWildernesPkers != null && !deepWildernesPkers.isEmpty()) {
            Set<Entry<Integer, DeepWildernessPker>> map = sortByValuesDeep(deepWildernesPkers);
            Iterator iterator2 = map.iterator();
            int index = 1;
            while (iterator2.hasNext()) {
                Entry entry = (Entry) iterator2.next();
                DeepWildernessPker pker = ((DeepWildernessPker) entry.getValue());
                player.getPacketSender().sendString(108, 21 + index, index + ": " + pker);
                index++;
                if (index + 21 == 32) {
                    break;
                }
            }
        }
    }

    public static void pullWinners() {
        World.startEvent(event -> {
            List<EdgePker> topThreeEdge = getTopThreeEdgevillePkers();
            List<DeepWildernessPker> topThreeDeep = getTopThreeDeepWildernessPkers();
            if (topThreeEdge != null) {
                Player firstPlace = topThreeEdge.get(0).getPlayer();
                Player secondPlace = topThreeEdge.get(1).getPlayer();
                Player thirdPlace = topThreeEdge.get(2).getPlayer();
                Player firstPlacePlayer = World.getPlayer(firstPlace.getName());
                Player secondPlacePlayer = World.getPlayer(secondPlace.getName());
                Player thirdPlacePlayer = World.getPlayer(thirdPlace.getName());
                Item firstPlaceReward = new Item(6199, 1);
                Item secondPlaceReward = new Item(13307, 250);
                Item thirdPlaceReward = new Item(13307, 100);
                if (firstPlacePlayer != null) {
                    firstPlacePlayer.getBank().add(firstPlaceReward.getId(), firstPlaceReward.getAmount());
                    firstPlacePlayer.sendMessage("Your reward for winning first place is in your bank!");
                } else {
                    unclaimedRewards.add(new Reward(firstPlace.getUserId(), firstPlaceReward));
                }

                if (secondPlacePlayer != null) {
                    secondPlacePlayer.getBank().add(secondPlaceReward.getId(), secondPlaceReward.getAmount());
                    secondPlacePlayer.sendMessage("Your reward for winning second place is in your bank!");
                } else {
                    unclaimedRewards.add(new Reward(secondPlace.getUserId(), secondPlaceReward));
                }

                if (thirdPlacePlayer != null) {
                    thirdPlacePlayer.getBank().add(thirdPlaceReward.getId(), thirdPlaceReward.getAmount());
                    thirdPlacePlayer.sendMessage("Your reward for winning third place is in your bank!");
                } else {
                    unclaimedRewards.add(new Reward(thirdPlace.getUserId(), thirdPlaceReward));
                }
                String message = Icon.WILDERNESS.tag() + "<col=006600> " +
                        "Pk Scoreboard:" + " <col=1e44b3> The winners of the edgeville leaderboard have been announced! " + "1st: [" + firstPlace.getName() + "] " + "2nd: [" + secondPlace.getName() + "] " + "3rd: [" + thirdPlace.getName() + "]";
                broadcast(message);
            } else {
                String message = Icon.WILDERNESS.tag() + "<col=006600> " +
                        "Pk Scoreboard:" + " <col=1e44b3> Not enough participants for the edgeville leaderboard! Maybe next time folks!";
                broadcast(message);
            }

            event.delay(10);

            if (topThreeDeep != null) {
                Player firstPlace = topThreeDeep.get(0).getPlayer();
                Player secondPlace = topThreeDeep.get(1).getPlayer();
                Player thirdPlace = topThreeDeep.get(2).getPlayer();
                Player firstPlacePlayer = World.getPlayer(firstPlace.getName());
                Player secondPlacePlayer = World.getPlayer(secondPlace.getName());
                Player thirdPlacePlayer = World.getPlayer(thirdPlace.getName());
                Item firstPlaceReward = new Item(6199, 1);
                Item secondPlaceReward = new Item(13307, 250);
                Item thirdPlaceReward = new Item(13307, 100);
                if (firstPlacePlayer != null) {
                    firstPlacePlayer.getBank().add(firstPlaceReward.getId(), firstPlaceReward.getAmount());
                    firstPlacePlayer.sendMessage("Your reward for winning first place is in your bank!");
                } else {
                    unclaimedRewards.add(new Reward(firstPlace.getUserId(), firstPlaceReward));
                }

                if (secondPlacePlayer != null) {
                    secondPlacePlayer.getBank().add(secondPlaceReward.getId(), secondPlaceReward.getAmount());
                    secondPlacePlayer.sendMessage("Your reward for winning second place is in your bank!");
                } else {
                    unclaimedRewards.add(new Reward(secondPlace.getUserId(), secondPlaceReward));
                }

                if (thirdPlacePlayer != null) {
                    thirdPlacePlayer.getBank().add(thirdPlaceReward.getId(), thirdPlaceReward.getAmount());
                    thirdPlacePlayer.sendMessage("Your reward for winning third place is in your bank!");
                } else {
                    unclaimedRewards.add(new Reward(thirdPlace.getUserId(), thirdPlaceReward));
                }

                String message = Icon.WILDERNESS.tag() + "<col=006600> " +
                        "Pk Scoreboard:" + " <col=1e44b3> The winners of the deep wilderness leaderboard have been announced! " + "1st: [" + firstPlace.getName() + "] " + "2nd: [" + secondPlace.getName() + "] " + "3rd: [" + thirdPlace.getName() + "]";
                broadcast(message);
            } else {
                String message = Icon.WILDERNESS.tag() + "<col=006600> " +
                        "Pk Scoreboard:" + " <col=1e44b3> Not enough participants for the deep wilderness leaderboard! Maybe next time folks!";
                broadcast(message);
            }

            edgePkers = new HashMap<>();
            deepWildernesPkers = new HashMap<>();
        });
    }

    public static void addKill(Player player) {
        if (player == null) {
            return;
        }

        if (edgePkers == null)
            edgePkers = new HashMap<>();

        if (deepWildernesPkers == null)
            deepWildernesPkers = new HashMap<>();

        int userId = player.getUserId();
        if (player.wildernessLevel <= 6) {
            EdgePker k = edgePkers.get(userId);
            if (k == null)
                k = new EdgePker(player);
            k.addKill();
            edgePkers.put(userId, k);
        } else if (player.wildernessLevel >= 30) {
            DeepWildernessPker k = deepWildernesPkers.get(userId);
            if (k == null)
                k = new DeepWildernessPker(player);
            k.addKill();
            deepWildernesPkers.put(userId, k);
        }
    }

    public static void addDeath(Player player) {
        if (player == null) {
            return;
        }

        if (edgePkers == null)
            edgePkers = new HashMap<>();

        if (deepWildernesPkers == null)
            deepWildernesPkers = new HashMap<>();

        int userId = player.getUserId();
        if (player.wildernessLevel > 0 && player.wildernessLevel <= 6) {
            EdgePker k = edgePkers.get(userId);
            if (k == null)
                k = new EdgePker(player);
            k.addDeath();
            edgePkers.put(userId, k);
        } else if (player.wildernessLevel >= 30) {
            DeepWildernessPker k = deepWildernesPkers.get(userId);
            if (k == null)
                k = new DeepWildernessPker(player);
            k.addDeath();
            deepWildernesPkers.put(userId, k);
        }
    }

    private static List<EdgePker> getTopThreeEdgevillePkers() {
        List<EdgePker> winners = new ArrayList<>();
        EdgePker firstPlaceKiller = null; //TOP 1st
        EdgePker secondPlaceKiller = null; //TOP 2nd
        EdgePker thirdPlaceKiller = null; //TOP 3rd

        if (edgePkers == null) {
            return null;
        }

        for (EdgePker killas : edgePkers.values()) {
            if (killas == null || killas.getPlayer() == null) {
                continue;
            }

            if (firstPlaceKiller == null || (killas.getKills() > firstPlaceKiller.getKills() || (killas.getKills() == firstPlaceKiller.getKills() && killas.getKills() / Math.max(1D, killas.getDeaths()) > firstPlaceKiller.getKills() / Math.max(1D, firstPlaceKiller.getDeaths())))) {
                thirdPlaceKiller = secondPlaceKiller;
                secondPlaceKiller = firstPlaceKiller;
                firstPlaceKiller = killas;
            } else if (secondPlaceKiller == null || killas.getKills() > secondPlaceKiller.getKills()) {
                thirdPlaceKiller = secondPlaceKiller;
                secondPlaceKiller = killas;
            } else if (thirdPlaceKiller == null || killas.getKills() > thirdPlaceKiller.getKills()) {
                thirdPlaceKiller = killas;
            }
        }

        if (firstPlaceKiller != null) {
            winners.add(firstPlaceKiller);
        }

        if (secondPlaceKiller != null) {
            winners.add(secondPlaceKiller);
        }

        if (thirdPlaceKiller != null) {
            winners.add(thirdPlaceKiller);
        }

        if (winners.size() == 3) {
            return winners;
        } else {
            return null;
        }
    }

    private static List<DeepWildernessPker> getTopThreeDeepWildernessPkers() {
        List<DeepWildernessPker> winners = new ArrayList<>();
        DeepWildernessPker firstPlaceKiller = null; //TOP 1st
        DeepWildernessPker secondPlaceKiller = null; //TOP 2nd
        DeepWildernessPker thirdPlaceKiller = null; //TOP 3rd

        if (edgePkers == null) {
            return null;
        }

        for (DeepWildernessPker killas : deepWildernesPkers.values()) {
            if (killas == null || killas.getPlayer() == null) {
                continue;
            }

            if (firstPlaceKiller == null || (killas.getKills() > firstPlaceKiller.getKills() || (killas.getKills() == firstPlaceKiller.getKills() && killas.getKills() / Math.max(1D, killas.getDeaths()) > firstPlaceKiller.getKills() / Math.max(1D, firstPlaceKiller.getDeaths())))) {
                thirdPlaceKiller = secondPlaceKiller;
                secondPlaceKiller = firstPlaceKiller;
                firstPlaceKiller = killas;
            } else if (secondPlaceKiller == null || killas.getKills() > secondPlaceKiller.getKills()) {
                thirdPlaceKiller = secondPlaceKiller;
                secondPlaceKiller = killas;
            } else if (thirdPlaceKiller == null || killas.getKills() > thirdPlaceKiller.getKills()) {
                thirdPlaceKiller = killas;
            }
        }

        if (firstPlaceKiller != null) {
            winners.add(firstPlaceKiller);
        }

        if (secondPlaceKiller != null) {
            winners.add(secondPlaceKiller);
        }

        if (thirdPlaceKiller != null) {
            winners.add(thirdPlaceKiller);
        }

        if (winners.size() == 3) {
            return winners;
        } else {
            return null;
        }
    }

    private static Set<Entry<Integer, EdgePker>> sortByValuesEdge(HashMap map) {
        Set<Entry<Integer, EdgePker>> entries = map.entrySet();

        Comparator<Entry<Integer, EdgePker>> valueComparator = (e1, e2) -> {
            EdgePker v1 = e1.getValue();
            EdgePker v2 = e2.getValue();
            return v1.compareTo(v2);
        };

        // Sort method needs a List, so let's first convert Set to List in Java
        List<Entry<Integer, EdgePker>> listOfEntries = new ArrayList<Entry<Integer, EdgePker>>(entries);

        // sorting HashMap by values using comparator
        listOfEntries.sort(valueComparator);

        LinkedHashMap<Integer, EdgePker> sortedByValue = new LinkedHashMap<Integer, EdgePker>(listOfEntries.size());

        // copying entries from List to Map
        for (Entry<Integer, EdgePker> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        return sortedByValue.entrySet();
    }

    private static Set<Entry<Integer, DeepWildernessPker>> sortByValuesDeep(HashMap map) {
        Set<Entry<Integer, DeepWildernessPker>> entries = map.entrySet();

        Comparator<Entry<Integer, DeepWildernessPker>> valueComparator = (e1, e2) -> {
            DeepWildernessPker v1 = e1.getValue();
            DeepWildernessPker v2 = e2.getValue();
            return v1.compareTo(v2);
        };
        // Sort method needs a List, so let's first convert Set to List in Java
        List<Entry<Integer, DeepWildernessPker>> listOfEntries = new ArrayList<Entry<Integer, DeepWildernessPker>>(entries);

        // sorting HashMap by values using comparator
        listOfEntries.sort(valueComparator);

        LinkedHashMap<Integer, DeepWildernessPker> sortedByValue = new LinkedHashMap<Integer, DeepWildernessPker>(listOfEntries.size());

        // copying entries from List to Map
        for (Entry<Integer, DeepWildernessPker> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        return sortedByValue.entrySet();
    }

    static class Reward {
        int userId;
        Item reward;

        Reward(int userId, Item reward) {
            this.userId = userId;
            this.reward = reward;
        }
    }

    private static void broadcast(String eventMessage) {
        for (Player p : World.players) {
            p.getPacketSender().sendMessage(eventMessage, "", 14);
        }
    }
}
