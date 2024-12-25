package net.runelite.standalone;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum CustomWorldType {

    PVP("KronosPK", "https://kronos.rip", "144.217.10.42", "144.217.10.42"),
    ECO("Kronos", "https://kronos.rip", "144.217.10.42", "144.217.10.42"),
    BETA("BETA", "167.114.217.217", "167.114.217.217", "144.217.10.42"),
    DEV("Development", "127.0.0.1", "127.0.0.1", "127.0.0.1");

    private final String name;
    private final String url;
    private final String gameServerAddress;
    private final String fileServerAddress;

}
