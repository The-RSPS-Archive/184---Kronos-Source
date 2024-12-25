package io.ruin.api.protocol.world;

public enum WorldSetting {

    MEMBERS(0x1),
    UNKNOWN1(0x2),
    PVP(0x4),
    HIGH_RISK(0x400),
    UNKNOWN2(0x8),
    BOUNTY(0x20),
    BETA(0x2000000),
    DEADMAN(0x20000000);

    public final int mask;

    WorldSetting(int mask) {
        this.mask = mask;
    }

}