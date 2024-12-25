package io.ruin.model.combat;

import io.ruin.model.map.Projectile;

import java.util.Arrays;

public enum RangedWeapon {
    /**
     * Knifes
     */
    BRONZE_KNIFE(new RangedData(219, Projectile.thrown(212, 11))),
    IRON_KNIFE(new RangedData(220, Projectile.thrown(213, 11))),
    STEEL_KNIFE(new RangedData(221, Projectile.thrown(214, 11))),
    BLACK_KNIFE(new RangedData(222, Projectile.thrown(215, 11))),
    MITHRIL_KNIFE(new RangedData(223, Projectile.thrown(216, 11))),
    ADAMANT_KNIFE(new RangedData(224, Projectile.thrown(217, 11))),
    RUNE_KNIFE(new RangedData(225, Projectile.thrown(218, 11))),
    DRAGON_KNIFE(new RangedData(-1, Projectile.thrown(28, 11))),
    /**
     * Darts
     */
    BRONZE_DART(new RangedData(232, Projectile.thrown(226, 11))),
    IRON_DART(new RangedData(233, Projectile.thrown(227, 11))),
    STEEL_DART(new RangedData(234, Projectile.thrown(228, 11))),
    BLACK_DART(new RangedData(273, Projectile.thrown(34, 11))), //todo - test projectile gfx
    MITHRIL_DART(new RangedData(235, Projectile.thrown(229, 11))),
    ADAMANT_DART(new RangedData(236, Projectile.thrown(230, 11))),
    RUNE_DART(new RangedData(237, Projectile.thrown(231, 11))),
    DRAGON_DART(new RangedData(1123, Projectile.thrown(1122, 105))),
    /**
     * Throwing axes
     */
    BRONZE_THROWING_AXE(new RangedData(43, Projectile.thrown(36, 11))),
    IRON_THROWING_AXE(new RangedData(42, Projectile.thrown(35, 11))),
    STEEL_THROWING_AXE(new RangedData(44, Projectile.thrown(37, 11))),
    BLACK_THROWING_AXE(new RangedData(47, Projectile.thrown(40, 11))),
    MITHRIL_THROWING_AXE(new RangedData(45, Projectile.thrown(38, 11))),
    ADAMANT_THROWING_AXE(new RangedData(46, Projectile.thrown(39, 11))),
    RUNE_THROWING_AXE(new RangedData(48, Projectile.thrown(41, 11))),
    DRAGON_THROWING_AXE(new RangedData(1320, Projectile.thrown(1319, 11))),
    MORRIGANS_THROWING_AXE(new RangedData(1624, Projectile.thrown(1623, 11))),
    /**
     * Thrown (misc)
     */
    OBBY_RING(new RangedData(Projectile.thrown(442, 11))),
    CHINCHOMPA(new RangedData(Projectile.thrown(908, 11))),
    RED_CHINCHOMPA(new RangedData(Projectile.thrown(909, 11))),
    BLACK_CHINCHOMPA(new RangedData(Projectile.thrown(1272, 11))),
    MORRIGANS_JAVELIN(new RangedData(1619, Projectile.thrown(1620, 11))),
    /**
     * Generated
     */
    CRYSTAL_BOW(new RangedData(250, Projectile.arrow(249))),
    CRAWS_BOW(new RangedData(1611, Projectile.arrow(1574))),
    /**
     * Fired (ammo)
     */
    NORMAL_BOW(
            RangedAmmo.BRONZE_ARROW,
            RangedAmmo.IRON_ARROW,
            RangedAmmo.STEEL_ARROW,
            RangedAmmo.BROAD_ARROW,
            RangedAmmo.MITHRIL_ARROW,
            RangedAmmo.ADAMANT_ARROW,
            RangedAmmo.RUNE_ARROW,
            RangedAmmo.AMETHYST_ARROW,
            RangedAmmo.DRAGON_ARROW
    ),
    DARK_BOW(
            RangedAmmo.BRONZE_ARROW,
            RangedAmmo.IRON_ARROW,
            RangedAmmo.STEEL_ARROW,
            RangedAmmo.BROAD_ARROW,
            RangedAmmo.MITHRIL_ARROW,
            RangedAmmo.ADAMANT_ARROW,
            RangedAmmo.RUNE_ARROW,
            RangedAmmo.AMETHYST_ARROW,
            RangedAmmo.DRAGON_ARROW
    ),
    BALLISTA(
            RangedAmmo.BRONZE_JAVELIN,
            RangedAmmo.IRON_JAVELIN,
            RangedAmmo.STEEL_JAVELIN,
            RangedAmmo.MITHRIL_JAVELIN,
            RangedAmmo.ADAMANT_JAVELIN,
            RangedAmmo.RUNE_JAVELIN,
            RangedAmmo.AMETHYST_JAVELIN,
            RangedAmmo.DRAGON_JAVELIN
    ),
    CROSSBOW(
            Arrays.stream(RangedAmmo.values()).filter(a -> a.name().toLowerCase().contains("bolt")).toArray(RangedAmmo[]::new)
    ),
    TWISTED_BOW(
            RangedAmmo.BRONZE_ARROW,
            RangedAmmo.IRON_ARROW,
            RangedAmmo.STEEL_ARROW,
            RangedAmmo.BROAD_ARROW,
            RangedAmmo.MITHRIL_ARROW,
            RangedAmmo.ADAMANT_ARROW,
            RangedAmmo.RUNE_ARROW,
            RangedAmmo.AMETHYST_ARROW,
            RangedAmmo.DRAGON_ARROW,
            RangedAmmo.CORRUPT_ARROW
    ),
    KARILS_CROSSBOW(RangedAmmo.BOLT_RACK),
    HUNTERS_CROSSBOW(RangedAmmo.KEBBIT_BOLTS, RangedAmmo.LONG_KEBBIT_BOLTS),
    TOXIC_BLOWPIPE,
    CORRUPTED_JAVELIN;

    public final RangedData data;

    public final boolean[] allowedAmmo;

    RangedWeapon() {
        this.data = null;
        this.allowedAmmo = null;
    }

    RangedWeapon(RangedData data) {
        this.data = data;
        this.allowedAmmo = null;
    }

    RangedWeapon(RangedAmmo... ammo) {
        this.data = null;
        this.allowedAmmo = new boolean[RangedAmmo.values().length];
        for(RangedAmmo a : ammo)
            this.allowedAmmo[a.ordinal()] = true;
    }

    public boolean allowAmmo(RangedAmmo ammo) {
        return allowedAmmo[ammo.ordinal()];
    }

}