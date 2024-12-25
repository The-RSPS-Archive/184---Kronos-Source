package io.ruin.api.protocol.world;

public enum WorldType {
    ECO("Kronos", "https://kronos.rip"),
    BETA("Kronos BETA", "https://kronos.rip"),
    PVP("KronosPK", "https://kronos.rip"),
    DEV("Development", "http://127.0.0.1");

    WorldType(String worldName, String websiteUrl) {
        this.worldName = worldName;
        this.websiteUrl = websiteUrl;
    }

    private String worldName, websiteUrl;

    public String getWorldName() {
        return worldName;
    }

    public String getWebsiteUrl() { return websiteUrl; }
}