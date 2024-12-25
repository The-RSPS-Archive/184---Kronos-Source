package io.ruin.model.skills.magic.spells;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import java.util.List;

public class BountyTeleport extends Spell {

    public BountyTeleport() {
        Item[] runes = {Rune.LAW.toItem(1), Rune.DEATH.toItem(1), Rune.CHAOS.toItem(1)};
        registerClick(85, 45.0, false, runes, BountyTeleport::cast);
    }

    public static boolean cast(Player player, Integer i) {
        if(Config.BOUNTY_HUNTER_TELEPORT.get(player) == 0) {
            player.sendFilteredMessage("You must first learn this spell using a bounty teleport scroll before casting it.");
            return false;
        }
        Player target = player.getBountyHunter().target;
        if(target == null) {
            player.sendFilteredMessage("You don't have a bounty target to teleport to.");
            return false;
        }
        if(player.getPosition().isWithinDistance(target.getPosition(), 15)) {
            player.sendFilteredMessage("You are already near your bounty hunter target!");
            return false;
        }
        if(target.wildernessLevel <= 0) {
            player.sendFilteredMessage("Your target is currently not inside the wilderness.");
            return false;
        }

        player.getMovement().startTeleport(e -> {
            List<Position> positions = target.getPosition().area(3, pos -> !pos.equals(target.getPosition()) && (pos.getTile() == null || pos.getTile().clipping == 0));
            if(positions.isEmpty()) {
                Server.logWarning(player.getName() + " (" + player.getAbsX() + ", " + player.getAbsY() + ", " + player.getHeight() + ") can't teleport to " + target.getName() + " (" + target.getAbsX() + ", " + target.getAbsY() + ", " + target.getHeight() + ")");
                player.sendFilteredMessage("You can't teleport to your target at the moment.");
                return;
            }
            Position destination = Random.get(positions);
            if(destination == null) {
                player.sendFilteredMessage("You can't teleport to your target at the moment.");
                return;
            }
            player.mageArena = target.mageArena;
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(destination.getX(), destination.getY(), destination.getZ());
        });

        return true;
    }

}
