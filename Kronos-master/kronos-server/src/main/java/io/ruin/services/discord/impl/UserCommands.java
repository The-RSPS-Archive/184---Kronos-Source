package io.ruin.services.discord.impl;
/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 1/14/2020
 */

import io.ruin.Server;
import io.ruin.api.utils.TimeUtils;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Hotspot;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.services.discord.impl.commands.BugReport;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;

import java.awt.*;

public class UserCommands extends ListenerAdapter {

    private String[] onlineAliases = {"::online", "::players", "::info", "::server"};

    @SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        if (e.getAuthor().isBot())
            return;
        if(World.isBeta())
            return;


        String message = e.getMessage().getContentRaw().toLowerCase();

        /*
         * Regular User Commands
         */

        for (int x = 0; x < onlineAliases.length; x++) {
            if (e.getMessage().getContentRaw().equalsIgnoreCase(onlineAliases[x])) {
                if (!e.getChannel().getId().equalsIgnoreCase("720814346774511646")) {
                    e.getChannel().sendMessage("Please only use this command in #bot-spam!").queue();
                    return;
                }
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Kronos Server Status", "https://kronos.rip")
                        .setColor(new Color(0xB00D03))
                        .setImage("https://kronos.rip/frontend/img/logo.png")
                        .addField("Players Online:", String.valueOf(World.players.count()), true)
                        .addField("Uptime:", TimeUtils.fromMs(Server.currentTick() * Server.tickMs(), false), true)
                        .addField("Players in Wild:", String.valueOf(Wilderness.players.size()), true)
                        .addField("**Active Bonuses:**", "", false)
                        .addField("XP Boost:", World.xpMultiplier+"x", true)
                        .addField("Weekend XP:", World.weekendExpBoost ? "Enabled" : "Disabled", true)
                        .addField("Hotspot:", Hotspot.ACTIVE.name, true);

                e.getChannel().sendMessage(embed.build()).queue();
            }
        }
        if (e.getChannel().getId().equalsIgnoreCase("665271176729198604")) {
            if (e.getMessage().getContentRaw().startsWith("::bugreport")) {
                BugReport.handle(e);
            }
        }
    }
}
