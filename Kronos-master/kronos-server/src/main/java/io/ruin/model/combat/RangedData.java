package io.ruin.model.combat;

import io.ruin.model.map.Projectile;

public class RangedData {

    public final int drawbackId, doubleDrawbackId;

    public final Projectile[] projectiles;

    public final boolean alwaysBreak;

    public RangedData(Projectile... projectiles) {
        this(-1, -1, false, projectiles);
    }

    public RangedData(boolean alwaysBreak, Projectile... projectiles) {
        this(-1, -1, alwaysBreak, projectiles);
    }

    public RangedData(int drawbackId, Projectile... projectiles) {
        this(drawbackId, -1, false, projectiles);
    }

    public RangedData(int drawbackId, int doubleDrawbackId, Projectile... projectiles) {
        this(drawbackId, doubleDrawbackId, false, projectiles);
    }

    public RangedData(int drawbackId, int doubleDrawbackId, boolean alwaysBreak, Projectile... projectiles) {
        this.drawbackId = drawbackId;
        this.doubleDrawbackId = doubleDrawbackId;
        this.alwaysBreak = alwaysBreak;
        this.projectiles = projectiles;
    }

}