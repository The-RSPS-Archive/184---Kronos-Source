package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;

import java.sql.*;
import java.util.List;
import java.util.function.BiConsumer;

public class Votes {

    public static void claim(Player player, NPC npc, BiConsumer<Integer, Integer> consumer) {
        if (player.getInventory().isFull()) {
            player.dialogue(new NPCDialogue(npc, "You need at least 1 inventory slot to claim your voting rewards."));
            return;
        }
        player.lock();
        player.dialogue(new NPCDialogue(npc, "Attempting to claim vote tickets, please wait...").hideContinue());
        Server.siteDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet rs = null;
                int count = 0;
                try {
                    statement = connection.prepareStatement("SELECT * FROM votes WHERE username = ? AND date_claimed IS NULL AND completed = 1", ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, player.getName());
                    rs = statement.executeQuery();
                    while (rs.next()) {
                        count++;
                        //rs.updateTimestamp("date_claimed", new Timestamp(System.currentTimeMillis()));
                    }
                } finally {
                    finish(count, 0);
                    markClaimed(player);
                }
            }

            @Override
            public void failed(Throwable t) {
                finish(-1, -1);
                Server.logError("", t); //todo exclude timeouts
            }

            private void finish(int claimed, int runelocusCount) {
                Server.worker.execute(() -> {
                    consumer.accept(claimed, runelocusCount);
                    player.unlock();
                });
            }

            private void markClaimed(Player player) {
                Server.siteDb.execute(connection -> {
                    PreparedStatement statement = null;
                    try {
                        statement = connection.prepareStatement("UPDATE votes SET date_claimed=? WHERE username=? AND date_claimed IS NULL AND completed = 1", ResultSet.CONCUR_UPDATABLE);
                        statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                        statement.setString(2, player.getName());
                        statement.executeUpdate();
                    } finally {
                        DatabaseUtils.close(statement);
                    }

                });
            }
        });
    }

}
