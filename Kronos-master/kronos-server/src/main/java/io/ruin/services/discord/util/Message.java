package io.ruin.services.discord.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class Message {

    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private Embed[] embeds;

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Message setUsername(String username) {
        this.username = username;
        return this;
    }

    public Message setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public Message setTts(boolean tts) {
        this.tts = tts;
        return this;
    }

    public Message setEmbeds(Embed... embeds) {
        this.embeds = embeds;
        return this;
    }

    public JSONObject toJson() {
        if (content == null && embeds == null)
            return null;
        JSONObject result = new JSONObject();
        if (content != null)
            result.put("content", content);
        if (username != null)
            result.put("username", username);
        if (avatarUrl != null)
            result.put("avatarUrl", avatarUrl);
        result.put("tts", tts);
        JSONArray embedArray = new JSONArray();
        if (embeds != null) {
            Arrays.stream(embeds).forEachOrdered(embed -> embedArray.put(embed.toJson()));
            result.put("embeds", embedArray);
        }
        return result;
    }
}