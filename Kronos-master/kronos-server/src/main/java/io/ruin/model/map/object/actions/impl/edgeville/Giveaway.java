package io.ruin.model.map.object.actions.impl.edgeville;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import io.ruin.Server;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import static io.ruin.cache.ItemID.BLOOD_MONEY;

public class Giveaway {

    private enum GiveawayType {
        CHRISTMAS_2018(World.type.getWorldName() + " Christmas Giveaway",
            "Logitech G502 Gaming mouse<br>x 1<br>(3,750,000)|Logitech G502 Gaming mouse (3,750,000 Blood money)|https://www.amazon.com/dp/B019OB663A",
            "Curved Gaming LED Monitor<br>x 1<br>(7,500,000)|Sceptre 24\" Curved 75Hz Gaming LED Monitor (7,500,000 Blood money)|https://www.amazon.com/dp/B07CY7W3VC",
            "GTRacing Gaming Office Chair<br>x 1<br>(11,250,000)|GTRacing Gaming Office Chair (11,250,000 Blood money)|https://www.amazon.com/dp/B01N2RJ0HI",
            "PS4 Slim 1TB Console<br>x 1<br>(15,000,000)|PS4 Slim 1TB Console (15,000,000 Blood money)|https://www.amazon.com/dp/B071CV8CG2",
            2019, Calendar.JANUARY, 1,
            0x640000, 0x006400, 0xB3B3B3,
            5_000, 20, 3000
        );

        private final String title;
        private final String rewardDetails1;
        private final String rewardDetails2;
        private final String rewardDetails3;
        private final String rewardDetails4;
        private final int deadlineYear;
        private final int deadlineMonth;
        private final int deadlineDate;
        private final int unfilledColor;
        private final int filledColor;
        private final int lineColor;
        private final int costPerEntry;
        private final int entriesPerPerson;
        private final int totalEntries;

        GiveawayType(String title, String rewardDetails1, String rewardDetails2, String rewardDetails3, String rewardDetails4, int deadlineYear, int deadlineMonth, int deadlineDate, int unfilledColor, int filledColor, int lineColor, int costPerEntry, int entriesPerPerson, int totalEntries) {
            this.title = title;
            this.rewardDetails1 = rewardDetails1;
            this.rewardDetails2 = rewardDetails2;
            this.rewardDetails3 = rewardDetails3;
            this.rewardDetails4 = rewardDetails4;
            this.deadlineYear = deadlineYear;
            this.deadlineMonth = deadlineMonth;
            this.deadlineDate = deadlineDate;
            this.unfilledColor = unfilledColor;
            this.filledColor = filledColor;
            this.lineColor = lineColor;
            this.costPerEntry = costPerEntry;
            this.entriesPerPerson = entriesPerPerson;
            this.totalEntries = totalEntries;
        }

        private static final GiveawayType[] VALUES = values();
    }

    private static final GiveawayType CURRENT_GIVEAWAY = GiveawayType.CHRISTMAS_2018;
    private static final Calendar DEADLINE = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    private static final SetMultimap<String, Integer> ENTRIES = HashMultimap.create();
    private static int totalAmount = -1;

    static {

        if (CURRENT_GIVEAWAY != null) {
            DEADLINE.set(CURRENT_GIVEAWAY.deadlineYear, CURRENT_GIVEAWAY.deadlineMonth, CURRENT_GIVEAWAY.deadlineDate);
        }

        //ObjectAction.register(40000, "Use", (player, obj) -> openGiveawayInterface(player));

        InterfaceHandler.register(Interface.GIVEAWAY, h -> h.actions[6] = (SimpleAction) Giveaway::buyEntryOpen);

    }

    private static boolean isBeforeDeadline() {
        return !Calendar.getInstance(TimeZone.getTimeZone("GMT")).after(DEADLINE);
    }

    public static void openGiveawayInterface(Player player) {
        if (CURRENT_GIVEAWAY == null) {
            player.dialogue(new MessageDialogue("There isn't a giveaway to participate in right now!"));
            return;
        }

        if (player.giveawayId == -1 || GiveawayType.VALUES[player.giveawayId] != CURRENT_GIVEAWAY) {
            if (player.debug) {
                player.sendMessage("Reset giveaway id and entry count due to different giveaway.");
            }
            player.giveawayId = CURRENT_GIVEAWAY.ordinal();
            player.giveawayEntries = 0;
        }

        player.openInterface(InterfaceType.MAIN, 709);
        player.getPacketSender().sendClientScript(10069,
            "ssssssiiiiiiiiiii",
            CURRENT_GIVEAWAY.title, "Welcome to the first ever " + World.type.getWorldName() + " community giveaway! Each player has the opportunity to buy themselves 20 individual entries for the chance at winning free stuff. Throughout the duration of this competition, collective member entries will increase the giveaway tiers which will in turn increase the amount of draws; more draws means more winners! Enter now for your chance, or chances, to win!",
            CURRENT_GIVEAWAY.rewardDetails1,
            CURRENT_GIVEAWAY.rewardDetails2,
            CURRENT_GIVEAWAY.rewardDetails3,
            CURRENT_GIVEAWAY.rewardDetails4,
            totalAmount, CURRENT_GIVEAWAY.costPerEntry * CURRENT_GIVEAWAY.totalEntries,
            CURRENT_GIVEAWAY.costPerEntry, player.giveawayEntries, CURRENT_GIVEAWAY.entriesPerPerson,
            CURRENT_GIVEAWAY.deadlineDate, CURRENT_GIVEAWAY.deadlineMonth, CURRENT_GIVEAWAY.deadlineYear,
            CURRENT_GIVEAWAY.unfilledColor, CURRENT_GIVEAWAY.filledColor, CURRENT_GIVEAWAY.lineColor
        );
    }

    private static void buyEntryOpen(Player player) {
        if (!isBeforeDeadline()) {
            player.dialogue(new MessageDialogue("The giveaway has ended."));
            return;
        }

        if (!World.isLive() && !player.isAdmin()) {
            System.out.println("You can not buy entries on this world.");
            return;
        }

        if (player.giveawayEntries >= CURRENT_GIVEAWAY.entriesPerPerson) {
            player.dialogue(new MessageDialogue("You are already at the maximum amount of entries for this giveaway."));
            return;
        }

        if (CURRENT_GIVEAWAY.totalEntries - (totalAmount / CURRENT_GIVEAWAY.costPerEntry) <= 0) {
            player.dialogue(new MessageDialogue("There are no more entries available."));
            return;
        }

        player.integerInput("How many entries would you like to purchase? (1-" + (CURRENT_GIVEAWAY.entriesPerPerson - player.giveawayEntries) + ")", amount -> {
            int entriesLeft = CURRENT_GIVEAWAY.entriesPerPerson - player.giveawayEntries;
            int totalEntriesLeft = CURRENT_GIVEAWAY.totalEntries - (totalAmount / CURRENT_GIVEAWAY.costPerEntry);

            Set<Integer> userIds = ENTRIES.get(player.getIp());
            if (!userIds.contains(player.getUserId()) && userIds.size() >= 3) {
                player.dialogue(new MessageDialogue("You are already at the maximum amount of entries for this giveaway."));
                return;
            }

            if (amount > entriesLeft) {
                player.dialogue(new MessageDialogue("You only have " + entriesLeft + " available entries left."));
                return;
            }

            // don't allow going over
            amount = Math.min(amount, totalEntriesLeft);

            int requiredBloodMoney = amount * CURRENT_GIVEAWAY.costPerEntry;

            if (player.getInventory().count(BLOOD_MONEY) < requiredBloodMoney) {
                player.dialogue(new MessageDialogue("You don't have enough Blood money to buy that many entries."));
                return;
            }

            player.getInventory().remove(BLOOD_MONEY, requiredBloodMoney);
            totalAmount += amount * CURRENT_GIVEAWAY.costPerEntry;
            player.giveawayEntries += amount;
            ENTRIES.put(player.getIp(), player.getUserId());

            player.getPacketSender().sendClientScript(10076,
                "iiii",
                player.giveawayEntries, CURRENT_GIVEAWAY.entriesPerPerson,
                totalAmount, CURRENT_GIVEAWAY.costPerEntry * CURRENT_GIVEAWAY.totalEntries
            );

            // update other players viewing the interface
            player.getRegions().forEach(r -> {
                for (Player other : r.players) {
                    if (other == null || !other.isVisibleInterface(Interface.GIVEAWAY)) {
                        continue;
                    }

                    other.getPacketSender().sendClientScript(10076,
                        "iiii",
                        other.giveawayEntries, CURRENT_GIVEAWAY.entriesPerPerson,
                        totalAmount, CURRENT_GIVEAWAY.costPerEntry * CURRENT_GIVEAWAY.totalEntries
                    );
                }
            });

            final int finalAmount = amount;

            Server.gameDb.execute(con -> {
                try (PreparedStatement statement = con.prepareStatement("INSERT INTO giveaway_entries (gid, uid, ip_address) VALUES (?, ?, ?)")) {

                    for (int i = 0; i < finalAmount; i++) {
                        statement.setInt(1, CURRENT_GIVEAWAY.ordinal());
                        statement.setInt(2, player.getUserId());
                        statement.setString(3, player.getIp());
                        statement.addBatch();
                    }

                    statement.executeBatch();
                }
            });
        });
    }

    public static void updateTotalAmount() {
        Server.gameDb.execute(con -> {
            int totalEntries = 0;

            try (PreparedStatement statement = con.prepareStatement("SELECT uid, ip_address FROM giveaway_entries WHERE gid = ?")) {
                statement.setInt(1, CURRENT_GIVEAWAY.ordinal());

                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        int uid = results.getInt("uid");
                        String ipAddress = results.getString("ip_address");

                        if (ipAddress != null) {
                            ENTRIES.put(ipAddress, uid);
                        }

                        totalEntries++;
                    }
                }
            }

            totalAmount = CURRENT_GIVEAWAY.costPerEntry * totalEntries;
        });
    }

}
