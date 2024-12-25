package io.ruin.services.discord.util;

import org.json.JSONObject;

public class Thumbnail {

    private String url;
    private String proxyUrl;
    private int height;
    private int width;

    public Thumbnail setUrl(String url) {
        this.url = url;
        return this;
    }

    public Thumbnail setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
        return this;
    }

    public Thumbnail setHeight(int height) {
        this.height = height;
        return this;
    }

    public Thumbnail setWidth(int width) {
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