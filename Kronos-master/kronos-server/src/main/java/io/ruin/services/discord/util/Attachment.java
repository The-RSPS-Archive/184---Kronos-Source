package io.ruin.services.discord.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Attachment {

    private String authorName;
    private String authorIcon;
    private String color;
    private ArrayList<Field> fields;

    public Attachment() {
        this(null, null, null);
    }

    public Attachment(String authorName) {
        this(authorName, null, null);
    }

    public Attachment(String authorName, String authorIcon) {
        this(authorName, authorIcon, null);
    }

    public Attachment(String authorName, String authorIcon, String color) {
        this.authorName = authorName;
        this.authorIcon = authorIcon;
        this.color = color;
        this.fields = new ArrayList<>();
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void pushField(Field field) {
        this.fields.add(field);
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("author_icon", this.authorIcon);
        result.put("author_name", this.authorName);
        result.put("color", this.color);
        if (!this.fields.isEmpty()) {
            JSONArray array = new JSONArray();
            for (Field field : this.fields) {
                array.add(field.toJson());
            }
            result.put("fields", array);
        }
        return (result);
    }

}