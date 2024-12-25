package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public class RefundManager {

    static {
        //NPCAction.register(2108, "reclaim-credits", RefundManager::claim);
    }

    private static void claim(Player player, NPC npc) {
        if (!World.isLive() && !player.isAdmin()) {
            player.dialogue(new NPCDialogue(npc, "Sorry, you can't claim store purchases on this world."));
            return;
        }

        player.lock();

        player.dialogue(new NPCDialogue(npc, "Attempting to claim past purchases, please wait..."));

        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM refunds WHERE user_id = ? AND claimed = 0", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setInt(1, player.getUserId());

                resultSet = statement.executeQuery();
                int totalCredits = 0;
                while (resultSet.next()) {
                    int creditsToReclaim = resultSet.getInt("credits");
                    totalCredits += creditsToReclaim;
                    updateCredits(player, resultSet);
                    resultSet.updateRow();
                }
                completeClaim(player, npc, totalCredits);
            } finally {
                DatabaseUtils.close(statement, resultSet);
            }
        });
        player.unlock();
    }

    private static void updateCredits(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateInt("user_id", player.getUserId());
        resultSet.updateInt("claimed", 1);
    }

    private static void completeClaim(Player player, NPC npc, int credits) {
        Server.worker.execute(() -> {
            if (credits == 0) {
                player.dialogue(new NPCDialogue(npc.getId(), "You have nothing to reclaim"));
            } else {
                player.refundedCredits += credits;
                player.dialogue(new NPCDialogue(npc.getId(), "You have claimed back " + credits + " credits"));
            }
        });
    }
}
