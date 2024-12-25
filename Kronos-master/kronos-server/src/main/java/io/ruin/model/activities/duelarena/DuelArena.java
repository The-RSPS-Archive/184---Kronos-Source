package io.ruin.model.activities.duelarena;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;

public class DuelArena {

    public static final Bounds BOUNDS = new Bounds(3325, 3200, 3391, 3286, 0);
    public static final Bounds CUSTOM_EDGE = new Bounds(3074, 3462, 3087, 3470, 0);

    private static void entered(Player player) {
        player.attackPlayerListener = DuelArena::allowAttack;
        player.allowPrayerListener = DuelArena::allowPrayer;
        player.teleportListener = DuelArena::allowTeleport;
        player.deathStartListener = DuelArena::deathStart;
        player.deathEndListener = DuelArena::deathEnd;
        player.setAction(1, PlayerAction.CHALLENGE);
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.DUEL_ARENA_CHALLENGE);
    }

    private static void exited(Player player, boolean logout) {
        if(!logout) {
            player.attackPlayerListener = null;
            player.allowPrayerListener = null;
            player.teleportListener = null;
            player.deathStartListener = null;
            player.deathEndListener = null;
            player.setAction(1, null);
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        }
        player.getDuel().lose(true);
    }

    private static void deathStart(Entity entity, Killer killer, Hit hit) {
        Duel targetDuel = entity.player.getDuel().targetDuel;
        if(targetDuel != null)
            targetDuel.player.cureVenom(0);
    }

    private static void deathEnd(Entity entity, Killer killer, Hit hit) {
        if(entity.player.getCombat().isDead() && (killer.player != null && killer.player.getCombat().isDead())) {
            entity.player.getDuel().draw();
        } else {
            entity.player.getDuel().lose(false);
        }
    }

    private static boolean allowAttack(Player player, Player pTarget, boolean message) {
        Duel duel = player.getDuel();
        AttackStyle style = player.getCombat().getAttackStyle();
        if(duel.stage < 3) {
            if(message)
                player.sendMessage("You can not attack players in the challenge area.");
            return false;
        }
        if(duel.targetDuel == null || duel.targetDuel.player != pTarget) {
            if(message)
                player.sendMessage("You can only attack your opponent.");
            return false;
        }
        if(duel.stage != 5) {
            if(message)
                player.sendMessage("The duel has not started yet!");
            return false;
        }
        if(style != null) {
            if(style.isMelee()) {
                if(duel.isToggled(DuelRule.NO_MELEE)) {
                    if(message)
                        player.sendMessage("Melee has been disabled for this duel!");
                    return false;
                }
            } else if(style.isRanged()) {
                if(duel.isToggled(DuelRule.NO_RANGED)) {
                    if(message)
                        player.sendMessage("Ranged has been disabled for this duel!");
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean allowPrayer(Player player) {
        if(DuelRule.NO_PRAYER.isToggled(player)) {
            player.sendMessage("Prayer has been turned off for this duel.");
            return false;
        }
        return true;
    }

    private static boolean allowTeleport(Player player) {
        if(player.getDuel().stage >= 4) {
            player.dialogue(new MessageDialogue("Coward! You can't teleport from a duel."));
            return false;
        }
        return true;
    }

    public static boolean allowMagic(Player player) {
        if(DuelRule.NO_MAGIC.isToggled(player)) {
            player.sendMessage("Magic has been disabled for this duel!");
            return false;
        }
        return true;
    }

    static {
        MapListener.registerBounds(BOUNDS, CUSTOM_EDGE)
                .onEnter(DuelArena::entered)
                .onExit(DuelArena::exited);
    }

}