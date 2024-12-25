package io.ruin.model.combat;

public enum AttackStyle {

    STAB,
    SLASH,
    CRUSH,
    RANGED,
    MAGIC,
    MAGICAL_RANGED,
    MAGICAL_MELEE,
    DRAGONFIRE,
    CANNON;

    public boolean isMelee() {
        return this == STAB || this == SLASH || this == CRUSH;
    }

    public boolean isRanged() {
        return this == RANGED;
    }

    public boolean isMagic() {
        return this == MAGIC;
    }

    public boolean isMagicalRanged() {
        return this == MAGICAL_RANGED;
    }

    public boolean isMagicalMelee() {
        return this == MAGICAL_MELEE;
    }

    public boolean isDragonfire() {
        return this == DRAGONFIRE;
    }

    public boolean isCannon() {
        return this == CANNON;
    }

}