package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;
import io.ruin.utility.OfflineMode;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Hiscores {

    public static void save(Player player) {
        if (player.isAdmin())
            return;
        saveHiscores(player);
    }

    /**
     * ECO
     */

    private static void saveHiscores(Player player) {
        if (OfflineMode.enabled)
            return;
        Server.siteDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM highscores WHERE username = ? LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, player.getName());
                    resultSet = statement.executeQuery();
                    if (!resultSet.next()) {
                        resultSet.moveToInsertRow();
                        updateECO(player, resultSet);
                        updatePVP(player, resultSet);
                        resultSet.insertRow();
                    } else {
                        updateECO(player, resultSet);
                        updatePVP(player, resultSet);
                        resultSet.updateRow();
                    }
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw, true);
                t.printStackTrace(pw);
                System.out.println("FAILED TO UPDATE HISCORES FOR: "+player.getName());
                System.out.println(sw.getBuffer().toString());
                /* do nothing */
            }
        });
    }

    private static void updateECO(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateString("username", player.getName());
        resultSet.updateString("gamemode", getGameMode(player));
        resultSet.updateString("xpmode", player.xpMode.getName());
        resultSet.updateLong("overall_exp", player.getStats().totalXp);
        resultSet.updateInt("overall_level", player.getStats().totalLevel);
        resultSet.updateInt("attack_exp", (int) player.getStats().get(StatType.Attack).experience);
        resultSet.updateInt("attack_level", player.getStats().get(StatType.Attack).fixedLevel);
        resultSet.updateInt("defence_exp", (int) player.getStats().get(StatType.Defence).experience);
        resultSet.updateInt("defence_level", player.getStats().get(StatType.Defence).fixedLevel);
        resultSet.updateInt("strength_exp", (int) player.getStats().get(StatType.Strength).experience);
        resultSet.updateInt("strength_level", player.getStats().get(StatType.Strength).fixedLevel);
        resultSet.updateInt("hitpoints_exp", (int) player.getStats().get(StatType.Hitpoints).experience);
        resultSet.updateInt("hitpoints_level", player.getStats().get(StatType.Hitpoints).fixedLevel);
        resultSet.updateInt("ranged_exp", (int) player.getStats().get(StatType.Ranged).experience);
        resultSet.updateInt("ranged_level", player.getStats().get(StatType.Ranged).fixedLevel);
        resultSet.updateInt("prayer_exp", (int) player.getStats().get(StatType.Prayer).experience);
        resultSet.updateInt("prayer_level", player.getStats().get(StatType.Prayer).fixedLevel);
        resultSet.updateInt("magic_exp", (int) player.getStats().get(StatType.Magic).experience);
        resultSet.updateInt("magic_level", player.getStats().get(StatType.Magic).fixedLevel);
        resultSet.updateInt("cooking_exp", (int) player.getStats().get(StatType.Cooking).experience);
        resultSet.updateInt("cooking_level", player.getStats().get(StatType.Cooking).fixedLevel);
        resultSet.updateInt("woodcutting_exp", (int) player.getStats().get(StatType.Woodcutting).experience);
        resultSet.updateInt("woodcutting_level", player.getStats().get(StatType.Woodcutting).fixedLevel);
        resultSet.updateInt("fletching_exp", (int) player.getStats().get(StatType.Fletching).experience);
        resultSet.updateInt("fletching_level", player.getStats().get(StatType.Fletching).fixedLevel);
        resultSet.updateInt("fishing_exp", (int) player.getStats().get(StatType.Fishing).experience);
        resultSet.updateInt("fishing_level", player.getStats().get(StatType.Fishing).fixedLevel);
        resultSet.updateInt("firemaking_exp", (int) player.getStats().get(StatType.Firemaking).experience);
        resultSet.updateInt("firemaking_level", player.getStats().get(StatType.Firemaking).fixedLevel);
        resultSet.updateInt("crafting_exp", (int) player.getStats().get(StatType.Crafting).experience);
        resultSet.updateInt("crafting_level", player.getStats().get(StatType.Crafting).fixedLevel);
        resultSet.updateInt("smithing_exp", (int) player.getStats().get(StatType.Smithing).experience);
        resultSet.updateInt("smithing_level", player.getStats().get(StatType.Smithing).fixedLevel);
        resultSet.updateInt("mining_exp", (int) player.getStats().get(StatType.Mining).experience);
        resultSet.updateInt("mining_level", player.getStats().get(StatType.Mining).fixedLevel);
        resultSet.updateInt("herblore_exp", (int) player.getStats().get(StatType.Herblore).experience);
        resultSet.updateInt("herblore_level", player.getStats().get(StatType.Herblore).fixedLevel);
        resultSet.updateInt("agility_exp", (int) player.getStats().get(StatType.Agility).experience);
        resultSet.updateInt("agility_level", player.getStats().get(StatType.Agility).fixedLevel);
        resultSet.updateInt("thieving_exp", (int) player.getStats().get(StatType.Thieving).experience);
        resultSet.updateInt("thieving_level", player.getStats().get(StatType.Thieving).fixedLevel);
        resultSet.updateInt("slayer_exp", (int) player.getStats().get(StatType.Slayer).experience);
        resultSet.updateInt("slayer_level", player.getStats().get(StatType.Slayer).fixedLevel);
        resultSet.updateInt("farming_exp", (int) player.getStats().get(StatType.Farming).experience);
        resultSet.updateInt("farming_level", player.getStats().get(StatType.Farming).fixedLevel);
        resultSet.updateInt("runecrafting_exp", (int) player.getStats().get(StatType.Runecrafting).experience);
        resultSet.updateInt("runecrafting_level", player.getStats().get(StatType.Runecrafting).fixedLevel);
        resultSet.updateInt("hunter_exp", (int) player.getStats().get(StatType.Hunter).experience);
        resultSet.updateInt("hunter_level", player.getStats().get(StatType.Hunter).fixedLevel);
        resultSet.updateInt("construction_exp", (int) player.getStats().get(StatType.Construction).experience);
        resultSet.updateInt("construction_level", player.getStats().get(StatType.Construction).fixedLevel);
        resultSet.updateInt("kills", Config.PVP_KILLS.get(player));
        resultSet.updateInt("deaths", Config.PVP_DEATHS.get(player));
        resultSet.updateInt("highest_shutdown", player.highestShutdown);
        resultSet.updateInt("highest_killspress", player.highestKillSpree);
        resultSet.updateInt("pk_rating", player.pkRating);
    }

    private static void updatePVP(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateInt("kills", Config.PVP_KILLS.get(player));
        resultSet.updateInt("deaths", Config.PVP_DEATHS.get(player));
        resultSet.updateInt("highest_shutdown", player.highestShutdown);
        resultSet.updateInt("highest_killspress", player.highestKillSpree);
        resultSet.updateInt("pk_rating", player.pkRating);
    }


    private static int getTotalSkillLevel(Player player) {
        int totalLevel = player.getStats().totalLevel;
        for (int i = 0; i < 7; i++)
            totalLevel -= player.getStats().get(i).fixedLevel;
        return totalLevel;
    }

    private static long getTotalSkillXp(Player player) {
        long totalExp = player.getStats().totalXp;
        for (int i = 0; i < 7; i++)
            totalExp -= player.getStats().get(i).experience;
        return totalExp;
    }

    private static String getGameMode(Player player) {
        if(player.getGameMode().isUltimateIronman()) {
            return "Ultimate Iron Man";
        } else if (player.getGameMode().isHardcoreIronman()) {
            return "Hardcore Iron Man";
        } else if (player.getGameMode().isIronMan()) {
            return "Iron Man";
        } else {
            return "Regular";
        }
    }
}