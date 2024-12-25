package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

import static io.ruin.api.database.DatabaseUtils.insertQuery;

public class Referral {

    public static void claim(Player player, String referralCode, Consumer<Boolean> consumer) {
        if(player.getInventory().isFull()) {
            player.dialogue(new MessageDialogue("You must have at least 1 inventory space to do this."));
            return;
        }
        player.lock();
        player.dialogue(new MessageDialogue("Attempting to claim referral reward for: " + referralCode + ", please wait...").hideContinue());
        Server.gameDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM referral WHERE referral_code = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, referralCode);
                    resultSet = statement.executeQuery();
                    if(!resultSet.next()) {
                      finish(false);
                    } else {
                        int rewardId = resultSet.getInt("reward_id");
                        int rewardAmount = resultSet.getInt("reward_amt");
                        int claimed = resultSet.getInt("total_claimed");
                        int maxAmount = resultSet.getInt("max_amount");
                        if(claimed >= maxAmount) {
                            finish(false);
                            return;
                        }
                        resultSet.updateInt("total_claimed", claimed + 1);
                        resultSet.updateRow();
                        insertClaimed(player, referralCode);
                        player.getInventory().add(rewardId, rewardAmount);
                        finish(true);
                    }
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                finish(false);
                Server.logError("", t);
            }

            private void finish(boolean success) {
                Server.worker.execute(() -> {
                    consumer.accept(success);
                    player.unlock();
                });
            }
        });
    }

    public static void checkClaimed(Player player, String referralCode, Consumer<Boolean> consumer) {
        player.lock();
        player.dialogue(new MessageDialogue("Checking if you've already claimed this referral..").hideContinue());
        Server.gameDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM referral_claimed WHERE referral_code = ? AND user_ip = ? OR referral_code = ? AND username = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, referralCode);
                    statement.setString(2, player.getIp());
                    statement.setString(3, referralCode);
                    statement.setString(4, player.getName());
                    resultSet = statement.executeQuery();
                    finish(resultSet.next());
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                finish(true);
                Server.logError("", t);
            }

            private void finish(boolean alreadyClaimed) {
                Server.worker.execute(() -> {
                    consumer.accept(alreadyClaimed);
                    player.unlock();
                });
            }
        });
    }

    public static void insertClaimed(Player player, String referralCode) {
        Server.gameDb.executeAwait(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery("referral_claimed", "referral_code", "username", "user_ip"))) {
                statement.setString(1, referralCode);
                statement.setString(2, player.getName());
                statement.setString(3, player.getIp());
                statement.execute();
            }
        });
    }

}
