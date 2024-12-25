package io.ruin.model.activities.godwars;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public enum GodwarsAltars {

    BANDOS(26366, new Position(2862, 5354, 2)),
    ZAMORAK(26363, new Position(2925, 5333, 2)),
    SARADOMIN(26364, new Position(2909, 5265, 0)),
    ARMADYL(26365, new Position(2839, 5294, 2));

    public final int altarId;
    public final Position teleportPosition;

    GodwarsAltars(int altarId, Position altarPosition) {
        this.altarId = altarId;
        this.teleportPosition = altarPosition;
    }

    static {
        for(GodwarsAltars altar : values()) {
            ObjectAction.register(altar.altarId, "pray", (player, obj) -> {
                if(player.getCombat().isDefending(5) | player.getCombat().isAttacking(5)) {
                    player.sendFilteredMessage("You cannot use this altar while under combat.");
                    return;
                }

                if(player.godwarsAltarCooldown.isDelayed()) {
                    player.sendFilteredMessage("You cannot use this altar yet!");
                    return;
                }

                player.animate(645);
                player.sendFilteredMessage("You pray to the gods...");
                player.godwarsAltarCooldown.delay(1000);
                player.getStats().get(StatType.Prayer).restore();
            });
            ObjectAction.register(altar.altarId, "teleport", (player, obj) -> {
                player.getMovement().teleport(altar.teleportPosition);
            });
        }
    }
}
