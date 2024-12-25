package io.ruin.model.item.containers;

import io.ruin.model.entity.shared.Movement;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.ground.GroundItem;

import java.util.Map;

public class Inventory extends ItemContainer {

    public double weight;

    public void addOrDrop(Item item) {
        addOrDrop(item.getId(), item.getAmount(), item.copyOfAttributes());
    }

    public void addOrDrop(int id, int amount) {
        addOrDrop(id, amount, null);
    }

    public void addOrDrop(int id, int amount, Map<String, String> attributes) {
        if(add(id, amount) > 0) {
            /* added normally */
            return;
        }
        if(player.isAdmin()) {
            player.sendMessage("Not enough space to spawn item (" + id + ", " + amount + ")");
//            return;
        }
        if(player.getDuel().stage >= 4) {
            //player.sendMessage("You can't drop items in a duel.");
            return;
        }
        if(player.joinedTournament) {
            //player.sendMessage("You can't drop items while you're signed up for a tournament.");
            return;
        }
        Movement movement = player.getMovement();
        int x, y, z;
        if(movement.isTeleportQueued()) {
            x = movement.teleportX;
            y = movement.teleportY;
            z = movement.teleportZ;
        } else {
            x = player.getAbsX();
            y = player.getAbsY();
            z = player.getHeight();
        }
        new GroundItem(id, amount, attributes).owner(player).position(x, y, z).spawn();
    }



    @Override
    public boolean sendUpdates() {
        if(!super.sendUpdates())
            return false;
        weight = 0;
        for(Item item : items) {
            if(item != null)
                weight += item.getDef().weightInventory;
        }
        return true;
    }

}