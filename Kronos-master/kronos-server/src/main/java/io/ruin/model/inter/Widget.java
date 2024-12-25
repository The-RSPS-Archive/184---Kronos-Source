package io.ruin.model.inter;

public enum Widget {

    BARRAGE(111, "Frozen", "You are frozen and cannot move"),
    TELEBLOCK(112, "Teleblocked", "You cannot teleport"),
    VENGEANCE(113, "Vengeance cooldown", "Vengeance spell is on cooldown and cannot be casted again"),
    STAMINA(114, "Stamina potion", "Run energy consumption reduced by 70%"),
    OVERLOAD(115, "Overload", "Your stats are boosted every 15 seconds"),
    ANTIFIRE(110, "Antifire", "Partial protection against dragon fire"),
    EXTENDED_ANTIFIRE(117, "Extended antifire", "Partial protection against dragon fire"),
    ANTI_VENOM(116, "Anti-venom", "You are immune to venom");

    private final int spriteId;
    private final String name;
    private final String description;

    Widget(int spriteId, String name, String description) {
        this.spriteId = spriteId;
        this.name = name;
        this.description = description;
    }

    public int getSpriteId() {
        return spriteId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
