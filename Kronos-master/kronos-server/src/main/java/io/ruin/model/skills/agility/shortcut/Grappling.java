package io.ruin.model.skills.agility.shortcut;

import io.ruin.cache.AnimDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

import java.util.stream.IntStream;

/**
 * @author ReverendDread on 3/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Grappling {

    private static final int[] CROSSBOWS = { 767, 837, 9174, 9176, 9177, 9179, 9181, 9183, 9185, 11165, 11785, 21012, 21902, 23601, 23611 };
    private static final int GRAPPLING_HOOK = 9419;

    public static void grapple(Player player, GameObject object, int agilityLevel, int rangedLevel, int strengthLevel, int emoteId, int gfxId, int delay, Position position, Position destination) {
        if (!player.getStats().check(StatType.Agility, agilityLevel, "grapple") ||
            !player.getStats().check(StatType.Strength, strengthLevel, "grapple") ||
            !player.getStats().check(StatType.Ranged, rangedLevel, "grapple")) {
            return;
        }
        if (!player.getEquipment().hasId(GRAPPLING_HOOK)) {//grappling hook
            player.sendMessage("You need a mithril grapple tipped bolt with a rope to do that.");
            return;
        }
        if (!IntStream.of(CROSSBOWS).anyMatch(player.getEquipment()::hasId)) {
            player.sendMessage("You need a crossbow equipped to do that.");
            return;
        }
        player.startEvent(e -> { //4455, 760
            e.path(player, position);
            player.animate(emoteId);
            player.graphics(gfxId, 100, 0);
            e.delay(delay);
            player.getPacketSender().fadeOut();
            e.delay(3);
            player.getPacketSender().fadeIn();
            player.getMovement().teleport(destination);
        });
    }

}
