package io.ruin.services.discord.util;

import org.json.JSONObject;

public class Image {

    private String url;
    private String proxyUrl;
    private int height;
    private int width;

    public Image setUrl(String url) {
        this.url = url;
        return this;
    }

    public Image setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
        return this;
    }

    public Image setHeight(int height) {
        this.height = height;
        return this;
    }

    public Image setWidth(int width) {
        this.width = width;
        return this;
    }

    public JSONObject toJson() {
        if (url == null)
            return null;
        JSONObject result = new JSONObject();
        result.put("url", url);
        if (proxyUrl != null)
            result.put("proxy_url", proxyUrl);
        if (height != 0)
            result.put("height", height);
        if (width != 0)
            result.put("width", width);
        return result;
    }
}