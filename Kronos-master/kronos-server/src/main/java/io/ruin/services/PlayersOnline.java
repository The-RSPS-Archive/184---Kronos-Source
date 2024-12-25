package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.utils.IPAddress;
import io.ruin.model.World;
import io.ruin.model.activities.pvp.PVPInstance;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.utility.OfflineMode;

import java.io.IOException;
import java.sql.PreparedStatement;

public class PlayersOnline {

    static {
        if (!OfflineMode.enabled && !World.isDev() && !World.isBeta()) {
            String[] split = World.address.split(":");
            try {
                split[0] = IPAddress.get();
            } catch (IOException e) {
                /* ignored */
            }
            String ip = split[0];
            int port = Integer.valueOf(split[1]);
            World.startTask(t -> {
                while (true) {
                    t.sleep(10000L); //10 seconds
                    Server.siteDb.execute(con -> {
                        try (PreparedStatement statement = con.prepareStatement("UPDATE players_online SET players_online = ?, wilderness_count=?, instance_count =?")) {
                            statement.setInt(1, World.players.count());
                            statement.setInt(2, Wilderness.players.size());
                            statement.setInt(3, PVPInstance.players.size());
                            statement.executeUpdate();
                        }
                    });
                }
            });
        }

        if (!OfflineMode.enabled && !World.isDev()) {
            World.startTask(t -> {
                while (true) {
                    t.sleep(1800000); //30 minutes
                    Server.gameDb.execute(con -> {
                        try (PreparedStatement statement = con.prepareStatement("INSERT INTO online_statistics (world, players) VALUES (?, ?)")) {
                            statement.setInt(1, World.id);
                            statement.setInt(2,World.players.count());
                            statement.executeUpdate();
                        }
                    });
                }
            });
        }
    }

}