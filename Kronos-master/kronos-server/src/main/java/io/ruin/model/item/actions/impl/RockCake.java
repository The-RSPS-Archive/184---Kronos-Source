package io.ruin.model.item.actions.impl;

import io.ruin.model.combat.Hit;
import io.ruin.model.item.actions.ItemAction;

public class RockCake {

    private static final int ROCK_CAKE = 7510;

    static {
        ItemAction.registerInventory(ROCK_CAKE, "eat", (player, item) -> {
            if(player.getHp() >= 2 && !player.rockCakeDelay.isDelayed()) {
                player.rockCakeDelay.delay(1);
                player.privateSound(1018);
                player.hit(new Hit().fixedDamage(1));
                player.animate(829);
            }
        });
        ItemAction.registerInventory(ROCK_CAKE, "guzzle", (player, item) -> {
            if(player.getHp() >= 2 && !player.rockCakeDelay.isDelayed()) {
                int damage = (player.getHp() + 10) / 10;
                player.rockCakeDelay.delay(1);
                player.privateSound(1018);
                player.hit(new Hit().fixedDamage(damage));
                player.animate(829);
            }
        });
    }
}
