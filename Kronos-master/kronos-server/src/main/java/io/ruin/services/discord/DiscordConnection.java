package io.ruin.services.discord;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.process.task.TaskWorker;
import io.ruin.services.discord.impl.AdminCommands;
import io.ruin.services.discord.impl.PrivateMessageReceived;
import io.ruin.services.discord.impl.UserCommands;
import io.ruin.utility.OfflineMode;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import javax.security.auth.login.LoginException;

public class DiscordConnection {

	private static JDA jda;
	private static DiscordConnection instance = new DiscordConnection();
	private static long myId;

	public static final long CHANNEL_PUNISHMENTS = 642470243998105652L;

	public static void setup(String token) throws LoginException {
		jda = JDABuilder.createDefault(token)
				.addEventListeners(new UserCommands())
				.addEventListeners(new AdminCommands())
				.addEventListeners(new PrivateMessageReceived())
				.build();

		TaskWorker.startTask(t -> {
			while (true) {
				t.sleep(60000L);
				jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.watching(World.players.count() + " players!"));
			}
		});
	}


	public static void post(long channel, String title, String text) {
        if (!World.isLive() || OfflineMode.enabled) {
            return;
        }
		MessageEmbed built = new EmbedBuilder().setTitle(title).setDescription(text).build();
		post(channel, built);
	}

	public static void post(long channel, MessageEmbed built) {
	    if (!World.isLive() || OfflineMode.enabled) {
            return;
        }
		try {
			jda.getTextChannelById(channel).sendMessage(built).queue();
		} catch (Exception e) {
            ServerWrapper.logError("Failed to send discord message in : " + channel, e);
        }
	}
}
