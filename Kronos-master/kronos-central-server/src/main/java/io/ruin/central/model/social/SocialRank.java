package io.ruin.central.model.social;

public enum SocialRank {
    FRIEND,
    RECRUIT,
    CORPORAL,
    SERGEANT,
    LIEUTENANT,
    CAPTAIN,
    GENERAL,
    OWNER,
    ADMIN(127);
    
    public final int id;

    private SocialRank() {
        this.id = this.ordinal();
    }

    private SocialRank(int id) {
        this.id = id;
    }

    public static SocialRank get(int ordinal, SocialRank defaultRank) {
        SocialRank[] ranks = SocialRank.values();
        if (ordinal < 0 || ordinal >= ranks.length) {
            return defaultRank;
        }
        return ranks[ordinal];
    }
}

