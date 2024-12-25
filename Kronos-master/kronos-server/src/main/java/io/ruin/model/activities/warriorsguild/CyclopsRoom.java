package io.ruin.model.activities.warriorsguild;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.skillcapes.AttackSkillCape;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.skills.CapePerks;

public class CyclopsRoom {

    private static final Bounds TOP_BOUNDS = new Bounds(2837, 3533, 2876, 3556, 2);

    private static final Bounds TOP_LOBBY = new Bounds(2838, 3537, 2846, 3542, 2);

    private static final Bounds UNDER_BOUNDS = new Bounds(2905, 9957, 2940, 9973, 0);

    private static final Bounds UNDER_LOBBY = new Bounds(2905, 9966, 2911, 9973, 0);

    private static void entered(Player player) {
        player.nextDefenderId = Cyclops.getNext(player, player.getHeight() == 0);
        player.sendMessage(ItemDef.get(player.nextDefenderId).name + "s are now being dropped.");
    }

    private static void exited(Player player, boolean logout) {
        if(logout)
            kick(player);
        if(player.tokenEvent.isDelayed())
            player.tokenEvent.reset();
    }

    public static boolean checkActive(Player player) {
        Position position = player.getPosition();
        if(position.inBounds(TOP_BOUNDS)) {
            if(position.inBounds(TOP_LOBBY) || (position.getX() == 2847 && position.getY() == 3537))
                return false;
            checkTokenEvent(player);
            return true;
        }
        if(position.inBounds(UNDER_BOUNDS)) {
            if(position.inBounds(UNDER_LOBBY))
                return false;
            checkTokenEvent(player);
            return true;
        }
        return false;
    }

    private static void checkTokenEvent(Player player) {
        if(player.tokenEvent.isDelayed())
            return;
        if (AttackSkillCape.wearsAttackCape(player))
            return;
        Item tokens = player.getInventory().findItem(8851);
        if(tokens != null) {
            tokens.incrementAmount(-10);
            player.sendFilteredMessage("<col=804080>10 of your tokens crumble away.");
        }
        if(tokens == null || tokens.getAmount() == 0) {
            kick(player);
            player.sendFilteredMessage("<col=804080>Next time, please leave as soon as your time is up.");
        }
        player.tokenEvent.delay(100);
    }

    private static void kick(Player player) {
        if(player.getHeight() == 2)
            player.getMovement().teleport(2846, 3540, 2);
        else
            player.getMovement().teleport(2911, 9968, 0);
    }

    static {
        MapListener.register(CyclopsRoom::checkActive).onEnter(CyclopsRoom::entered).onExit(CyclopsRoom::exited);
    }

}
