package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Footer;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;

public class ShutdownEmbedMessage {

    public static void sendDiscordMessage(String shutdownMessage) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook("https://discordapp.com/api/webhooks/639305351564623882/5y_4r4uy6emG1VptJ6lf7fHO77nE8UYC7R3EhzwOyjkB2sHM_77E_ydgo9lBoTBIWT0z");
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle("Shutdown Announcement");
            embedMessage.setDescription(shutdownMessage);
            embedMessage.setColor(8917522);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://static.runelite.net/cache/item/icon/" + 13307 + ".png");
            embedMessage.setThumbnail(thumbnail);

            /*
             * Footer
             */
            Footer footer = new Footer();
            footer.setText(World.type.getWorldName() + " - Waste Your Life One Skill At A Time!");
            embedMessage.setFooter(footer);

            /*
             * Fire the message
             */
            message.setEmbeds(embedMessage);
            webhook.sendMessage(message.toJson());
        } catch (Exception e) {
            ServerWrapper.logError("Failed to send discord embed", e);
        }
    }

}
