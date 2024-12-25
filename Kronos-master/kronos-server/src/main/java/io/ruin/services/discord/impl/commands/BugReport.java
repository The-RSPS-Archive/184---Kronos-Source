package io.ruin.services.discord.impl.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/30/2020
 */
public class BugReport {

    private static String COLUMN_ID = "5f1ca3eec9ef2b0011333ef7";
    private static String ACCESS_TOKEN = "p0553fff230f5645db0fa228ad5f1fddb63459a4e";
    private static String BOARD_ID = "5e5da93fb0320f0012e539e9";

    public static void handle(GuildMessageReceivedEvent e) throws IOException {
        //::bugreport-Title-Description
        String command[] = e.getMessage().getContentRaw().split("-");
        if (command.length != 3) {
            e.getChannel().sendMessage("Please use the proper format! `::bugreport-title-description`").queue();
            return;
        }
        String title = command[1];
        String descriptionText = command[2] + " - User: "+e.getAuthor().getAsTag();

        JSONObject description = new JSONObject();
        description.put("text", descriptionText);


        OkHttpClient http = new OkHttpClient();

        JSONObject body = new JSONObject();
                body.put("name", title);
                body.put("description", description);
                body.put("column_id", COLUMN_ID);

        Request req = new Request.Builder()
                .url("https://gloapi.gitkraken.com/v1/glo/boards/" + BOARD_ID + "/cards")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString()))
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        try (Response response = http.newCall(req).execute()) {
            if(!response.isSuccessful()){
                System.err.println("Failed to add bug report to GloBoard");
                System.err.print(response.body().string());
                e.getChannel().sendMessage("Failed to post to GloBoard! Please report this to an admin!").queue();
                return;
            }
        }
        e.getChannel().sendMessage("Bug Report posted! Thank you <@"+e.getAuthor().getId()+">").queue();
        e.getMessage().delete().queue();


    }
}
