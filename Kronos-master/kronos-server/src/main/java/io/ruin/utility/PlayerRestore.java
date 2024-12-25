package io.ruin.utility;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.World;
import io.ruin.model.entity.npc.actions.edgeville.StarterGuide;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author ReverendDread on 6/27/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class PlayerRestore {

    private static final String[] skill_names = { "attack", "defence", "strength", "hitpoints",
            "ranged", "prayer", "magic", "cooking", "woodcutting", "fletching", "fishing",
            "firemaking", "crafting", "smithing", "mining", "herblore", "agility", "thieving",
            "slayer", "farming", "runecrafting", "hunter", "construction"
    };

    private static final String IPS_PATH = System.getProperty("user.home") + "/Desktop/Kronos/";
    private static final String CLAIMED_RESTORES = "claimed_restores.txt";

    public static void reconstructPlayer(Player player) {
        if (player.getUserId() <= 1660 && !player.restored) {
            log.info("Reconstructing player data for {}, {}", player.getName(), player.getUserId());
            player.inTutorial = false;
            player.newPlayer = false;
            player.setTutorialStage(0);
            if (player.edgeHome) {
                player.getMovement().teleport(World.EDGEHOME);
            } else {
                player.getMovement().teleport(World.HOME);
            }
            //reconstructItemContainers(player); //forget about items
            reconstructStats(player);
            giveRestoreStarter(player);
            player.beforeFuckup = true;
        }
        player.restored = true;
    }

    private static boolean reconstructStats(Player player) {
        if (OfflineMode.enabled)
            return false;
        Server.siteDb.execute((connection) -> {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM highscores WHERE username='" + player.getName() + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String mode = rs.getString("gamemode");
                switch (mode) {
                    case "Ultimate Iron Man":
                        Config.IRONMAN_MODE.set(player, 2);
                        break;
                    case "Hardcore Iron Man":
                        Config.IRONMAN_MODE.set(player, 3);
                        break;
                    case "Iron Man":
                        Config.IRONMAN_MODE.set(player, 1);
                        break;
                    case "Regular":
                        Config.IRONMAN_MODE.set(player, 0);
                        break;
                }

                Config.PVP_DEATHS.set(player, rs.getInt("deaths"));
                Config.PVP_KILLS.set(player, rs.getInt("kills"));
                player.highestShutdown = rs.getInt("highest_shutdown");
                player.highestKillSpree = rs.getInt("highest_killspress");
                player.pkRating = rs.getInt("pk_rating");

                for (int skill = 0; skill < skill_names.length; skill++) {
                    int level = rs.getInt(skill_names[skill] + "_level");
                    int experience = rs.getInt(skill_names[skill] + "_exp");
                    player.getStats().get(skill).setExperience(experience);
                }

            }

        });
        return true;
    }

    private static boolean reconstructItemContainers(Player player) {
        Server.gameDb.execute((connection) -> {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("SELECT * FROM items WHERE user_id = ?");
                ps.setInt(1, player.getUserId());
                rs = ps.executeQuery();
                while (rs.next()) {

                    int id = rs.getInt("id");
                    int amount = rs.getInt("amount");
                    String container = rs.getString("container");
                    int slot = rs.getInt("slot");

                    switch (container) {
                        case "inventory":
                        case "bank":
                        case "equipment":
                            player.getBank().add(id, amount);
                            break;
                    }

                    log.info("id {}, amount {}, container {}, slot {}", id, amount, container, slot);
                }
            } finally {
                DatabaseUtils.close(ps, rs);
            }
        });
        return true;
    }

    public static void giveRestoreStarter(Player player) {
        player.getInventory().add(COINS_995, 10000); // gp
        player.getInventory().add(558, 500); // Mind Rune
        player.getInventory().add(556, 1500); // Air Rune
        player.getInventory().add(554, 1000); // Fire Rune
        player.getInventory().add(555, 1000); // Water Rune
        player.getInventory().add(557, 1000); // Earth Rune
        player.getInventory().add(562, 1000); // Chaos Rune
        player.getInventory().add(560, 500); // Death Rune
        player.getInventory().add(1381, 1); // Air Staff
        player.getInventory().add(362, 50); // Tuna
        player.getInventory().add(863, 300); // Iron Knives
        player.getInventory().add(867, 150); // Adamant Knives
        player.getInventory().add(1169, 1); // Coif
        player.getInventory().add(1129, 1); // Leather body
        player.getInventory().add(1095, 1); // Leather Chaps
        player.getInventory().add(13385, 1); // Xeric Hat
        player.getInventory().add(12867, 1); // Blue d hide set
        player.getInventory().add(13024, 1); // Rune set
        player.getInventory().add(11978, 1); // Glory 6
        player.getInventory().add(13387, 1); // Xerican Top
        player.getInventory().add(1323, 1); // Iron scim
        player.getInventory().add(1333, 1); // Rune scim
        player.getInventory().add(4587, 1); // Dragon Scim
        switch (player.getGameMode()) {
            case IRONMAN:
                player.getInventory().add(12810, 1);
                player.getInventory().add(12811, 1);
                player.getInventory().add(12812, 1);
                break;
            case ULTIMATE_IRONMAN:
                player.getInventory().add(12813, 1);
                player.getInventory().add(12814, 1);
                player.getInventory().add(12815, 1);
                break;
            case HARDCORE_IRONMAN:
                player.getInventory().add(20792, 1);
                player.getInventory().add(20794, 1);
                player.getInventory().add(20796, 1);
                break;
            case STANDARD:
                player.getInventory().add(COINS_995, 115000);
                break;
        }
        if (canClaim(player)) {
            player.getBank().add(290, 2); //super mystery box
            player.getBank().add(6199, 5); //normal mystery box
            writeIP(player);
        }
    }

    private static boolean canClaim(Player player) {
        try {
            File file = new File(IPS_PATH, CLAIMED_RESTORES);
            if (!file.exists())
                file.createNewFile();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String holder;
            while ((holder = br.readLine()) != null) {
                if (holder.equals(player.getMACAddress())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void writeIP(Player player) {
        try {
            File file = new File(IPS_PATH, CLAIMED_RESTORES);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(player.getMACAddress());
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
