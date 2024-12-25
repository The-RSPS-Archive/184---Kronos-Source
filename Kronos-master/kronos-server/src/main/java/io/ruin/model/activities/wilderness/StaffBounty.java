package io.ruin.model.activities.wilderness;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Icon;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.Broadcast;
import io.ruin.utility.OfflineMode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StaffBounty {

    public static boolean EVENT_ACTIVE;

    public static int SUPPORT_KILL_VALUE = 100;
    public static int MODERATOR_KILL_VALUE = 200;
    public static int ADMINISTRATOR_KILL_VALUE = 300;

    static {
        checkActive();
    }

    public static void checkActive() {
        if (OfflineMode.enabled)
            return;
        Server.gameDb.execute(con -> {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT active from staff_bounty LIMIT 1");
            while (resultSet.next())
                EVENT_ACTIVE = resultSet.getBoolean("active");
        });
    }

    public static void startEvent(Player player) {
        if (EVENT_ACTIVE) {
            player.sendMessage("The staff bounty event is already toggled on.");
            return;
        }
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement("UPDATE staff_bounty " +
                    "SET active = ?, support_deaths = ?, moderator_deaths = ?, administrator_deaths = ?")) {
                statement.setBoolean(1, true);
                statement.setInt(2, 0);
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.executeUpdate();
            }
        });
        EVENT_ACTIVE = true;
        player.sendMessage("The staff bounty event has been toggled on and all support, moderator and administrator deaths have been reset.");
        Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", "The hunt has begun! Staff members will now be worth additional bounties. Good luck, everyone!");
    }

    private static int supportDeaths;
    private static int moderatorDeaths;
    private static int administratorDeaths;

    public static void stopEvent(Player player) {
        if (!EVENT_ACTIVE) {
            player.sendMessage("The staff bounty event is already toggled off.");
            return;
        }
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement("UPDATE staff_bounty SET active = ?")) {
                statement.setBoolean(1, false);
                statement.executeUpdate();
            }
        });
        EVENT_ACTIVE = false;
        player.sendMessage("The staff bounty event has been toggled off.");
        updateDeathCounters();
        int staffBounty = (supportDeaths *= SUPPORT_KILL_VALUE) + (moderatorDeaths *= MODERATOR_KILL_VALUE) + (administratorDeaths *= ADMINISTRATOR_KILL_VALUE);
        Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", "The staff hunt has ended. There was a total of " + NumberUtils.formatNumber(staffBounty) + " blood money made collectively. Well done, everyone!");
    }

    private static void updateDeathCounters() {
        getSupportDeaths();
        getModeratorDeaths();
        getAdministratorDeaths();
    }

    /**
     * Support kills
     */
    private static void getSupportDeaths() {
        Server.gameDb.executeAwait(con -> {
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM staff_bounty");
                while(resultSet.next()) {
                    supportDeaths = resultSet.getInt("support_deaths");
                }
            } finally {
                DatabaseUtils.close(statement, resultSet);
            }
        });
    }

    /**
     * Moderator kills
     */
    private static void getModeratorDeaths() {
        Server.gameDb.executeAwait(con -> {
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM staff_bounty");
                while(resultSet.next()) {
                    moderatorDeaths = resultSet.getInt("moderator_deaths");
                }
            } finally {
                DatabaseUtils.close(statement, resultSet);
            }
        });
    }

    /**
     * Administrator kills
     */
    private static void getAdministratorDeaths() {
        Server.gameDb.executeAwait(con -> {
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM staff_bounty");
                while(resultSet.next()) {
                    administratorDeaths = resultSet.getInt("administrator_deaths");
                }
            } finally {
                DatabaseUtils.close(statement, resultSet);
            }
        });
    }

    public static void rewardBounty(Player player) {

    }

    public static void incrementStaffBounty(String row) {
        Server.gameDb.execute(con -> {
            try(PreparedStatement statement = con.prepareStatement("UPDATE staff_bounty SET " + row + " = " + row + " + 1")) {
                statement.executeUpdate();
            }
        });
    }
}
