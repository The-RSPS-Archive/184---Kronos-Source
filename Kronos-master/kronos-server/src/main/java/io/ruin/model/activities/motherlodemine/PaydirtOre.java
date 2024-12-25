package io.ruin.model.activities.motherlodemine;

import io.ruin.api.utils.Random;
import io.ruin.model.skills.mining.Rock;

public enum PaydirtOre {
    NUGGET(12012, 0, 0),
    COAL(Rock.COAL.ore, 30, 0),
    GOLD(Rock.GOLD.ore, Rock.GOLD.levelReq, 15),
    MITHRIL(Rock.MITHRIL.ore, Rock.MITHRIL.levelReq, 30),
    ADAMANT(Rock.ADAMANT.ore, Rock.ADAMANT.levelReq, 45),
    RUNITE(Rock.RUNE.ore, Rock.RUNE.levelReq, 75);

    PaydirtOre(int itemId, int levelReq, double xp) {
        this.itemId = itemId;
        this.levelReq = levelReq;
        this.xp = xp;
    }

    private int itemId, levelReq;
    private double xp;

    public int getItemId() {
        return itemId;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public double getXp() {
        return xp;
    }

    public static PaydirtOre get(int level) {
        if (Random.rollDie(25, 4))
            return NUGGET;
        if (level >= RUNITE.levelReq && Random.rollDie(70,1))
            return RUNITE;
        if (level >= ADAMANT.levelReq && Random.rollDie(8, 1))
            return ADAMANT;
        if (level >= MITHRIL.levelReq && Random.rollDie(4, 1))
            return MITHRIL;
        if (level >= GOLD.levelReq && Random.rollDie(3, 1))
            return GOLD;
        return COAL;
    }

}