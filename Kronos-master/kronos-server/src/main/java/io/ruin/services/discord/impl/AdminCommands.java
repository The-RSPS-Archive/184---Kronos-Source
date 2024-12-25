package io.ruin.services.discord.impl;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 3/24/2020
 */

import com.google.common.collect.Lists;
import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.World;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AdminCommands extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        if (e.getAuthor().isBot())
            return;

        if(World.isBeta())
            return;

        if (!e.getMember().hasPermission(Permission.ADMINISTRATOR))
            return;

        String message = e.getMessage().getContentRaw().toLowerCase();
        String[] splitMessage = message.split(" ");
        String commandArg = "0";
        if (splitMessage.length != 1) {
            commandArg = splitMessage[1];
        }

        if (message.equals("::checkorders")) {
            new Thread(() -> {
                try {
                    List<String> orders = checkOrders();
                String combinedOrders = String.join(" \n", orders);
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Current Pending OSRS GP Orders");
                eb.setColor(new Color(0xB00D03));
                eb.setDescription("Below is a list of pending orders. Please contact the player to complete the orders.");
                eb.addField("ID  -  Name  -  Status  -  Amount", combinedOrders, false);
                eb.setFooter("use ::convert [amount] to convert the donated amount to OSRS GP");
                e.getChannel().sendMessage(eb.build()).queue();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            }).start();
        }

        if(message.startsWith("::convert")){
            try {
                int usd = Integer.parseInt(commandArg);
                int osrs = (int) Math.ceil(usd / 0.6);
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("OSRS GP Conversion");
                eb.setColor(new Color(0xB00D03));
                eb.addField("Converted:", "$" + usd + " to OSRS: " + osrs + "M", false);
                eb.setFooter("Remember to mark the order as approved once OSRS GP has been claimed with ::approve [id]");
                e.getChannel().sendMessage(eb.build()).queue();
            } catch (NumberFormatException ex) {
                e.getChannel().sendMessage("Invalid number input! \n" +
                        "Ex: $25 -> `::convert 25`").queue();
            }
        }

        if(message.startsWith("::approve")) {
            try {
                int id = Integer.parseInt(commandArg);
                approveOrder(id);
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Order Approval");
                eb.setColor(new Color(0xB00D03));
                eb.addField("Order #" + id + " was approved!", "Please notify this user to claim the donation now.", false);
                e.getChannel().sendMessage(eb.build()).queue();

            } catch (NumberFormatException ex) {
                e.getChannel().sendMessage("Approval failed! \n" +
                        "Ex: Order ID 25 -> `::approve 25`");
            }
        }

        if(message.startsWith("::bonusxp")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Bonus XP!");
            eb.setColor(new Color(0xB00D03));
            int amount = Integer.parseInt(commandArg);
            if (amount < 1) {
                eb.addField("Error!", "XP Multiplier cannot be less than 1!", false);
                amount = 1;
            } else if (amount > 4) {
                eb.addField("Error!", "XP Multiplier cannot be greater than 4!", false);
                amount = 4;
            }
            World.boostXp(amount);
            eb.addField("Bonus XP!", "The bonus XP is now set to "+amount+"x!", false);
            e.getChannel().sendMessage(eb.build()).queue();

        }
        if (message.equalsIgnoreCase("::doubledrops")) {
            World.toggleDoubleDrops();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Bonus XP!");
            eb.setColor(new Color(0xB00D03));
            eb.addField("Double Drops!", "Double drops are now " + (World.doubleDrops ? "**ENABLED**" : "**DISABLED**")+"!", false);
            e.getChannel().sendMessage(eb.build()).queue();
        }
        if (message.equalsIgnoreCase("::doublepc")) {
            World.toggleDoublePest();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("double PC Points!");
            eb.setColor(new Color(0xB00D03));
            eb.addField("Double PC Points!", "Double PC Points are now " + (World.doubleDrops ? "**ENABLED**" : "**DISABLED**")+"!", false);
            e.getChannel().sendMessage(eb.build()).queue();
        }
        if (message.equalsIgnoreCase("::doubleslayer")) {
            World.toggleDoubleSlayer();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Double Slayer Points!");
            eb.setColor(new Color(0xB00D03));
            eb.addField("Double Slayer Points!", "Double Slayer Points are now " + (World.doubleDrops ? "**ENABLED**" : "**DISABLED**")+"!", false);
            e.getChannel().sendMessage(eb.build()).queue();
        }

    }


    private List<String> checkOrders() throws SQLException {
        List<String> orders = Lists.newArrayList();
        Server.siteDb.executeAwait(connection -> {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = connection.prepareStatement("SELECT id, player_name, status, total_payment_amount FROM orders WHERE status=? AND payment_method=?", ResultSet.TYPE_SCROLL_INSENSITIVE);
                statement.setString(1, "Pending");
                statement.setString(2, "OSGP");
                resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("player_name");
                    String status = resultSet.getString("status");
                    String amount = "$" + resultSet.getString("total_payment_amount");
                    orders.add(id + "  -  " + name + " - " + status + " - " + amount);
                }
            } finally {
                DatabaseUtils.close(statement, resultSet);
            }
        });
        return orders;
    }

    private void approveOrder(int id) {
        Server.siteDb.executeAwait(connection -> {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("UPDATE orders SET status=? WHERE id=?", ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, "Approved");
                statement.setInt(2, id);
                statement.executeUpdate();
            } finally {
                DatabaseUtils.close(statement);
            }
        });
    }
}
