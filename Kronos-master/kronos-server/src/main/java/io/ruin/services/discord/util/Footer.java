package io.ruin.services.discord.util;

import org.json.JSONObject;

public class Footer {

    private String text;
    private String iconUrl;
    private String proxyIconUrl;

    public Footer setText(String text) {
        this.text = text;
        return this;
    }

    public Footer setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public Footer setProxyIconUrl(String proxyIconUrl) {
        this.proxyIconUrl = proxyIconUrl;
        return this;
    }

    public JSONObject toJson() {
        if (text == null)
            return null;
        JSONObject result = new JSONObject();
        result.put("text", text);
        if (iconUrl != null)
            result.put("icon_url", iconUrl);
        if (proxyIconUrl != null)
            result.put("proxy_icon_url", proxyIconUrl);
        return result;
    }
}