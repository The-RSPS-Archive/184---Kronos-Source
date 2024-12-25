package io.ruin.services.discord.util;

import org.json.simple.JSONObject;

public class Field {

    private String name;
    private String value;
    private boolean inline;

    public Field(String title, String value, boolean inline) {
        this.name = title;
        this.value = value;
        this.inline = inline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setInline(boolean inline) {
        this.inline = inline;
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("name", this.name);
        result.put("value", this.value);
        result.put("inline", this.inline);
        return (result);
    }

}