package io.ruin.model.content.upgrade;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.stat.StatType;

/**
 * @author ReverendDread on 5/29/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class ItemUpgrade {

    /**
     * called before hitting an enemy in combat, usually used for altering a hit's properties.
     */
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {

    }

    public void postTargetDefend(Player player, Entity target, Item item, Hit hit) {

    }

    /**
     * called before the player takes damage.
     */
    public void preDefend(Player player, Entity target, Item item, Hit hit) {

    }

    /**
     * called after the player takes damage.
     */
    public void postPlayerDamage(Player player, Entity target, Item item, Hit hit) {

    }

    /**
     * Called when rolling a drop.
     * @return
     */
    public int addDoubleDropRolls() {
        return 0;
    }

    /**
     * Called when collecting a resource.
     * @param player
     * @param resource
     */
    public void collectResource(Player player, Item resource) {

    }

    /**
     * Called when an item is received from a monster drop.
     * @param player
     * @param item
     * @return if the item should be dropped
     */
    public boolean modifyDroppedItem(Player player, Item item) {
        return true;
    }

    public double giveExperienceBoost(Player player, StatType statType) {
        return 1.0;
    }

}
