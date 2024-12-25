package io.ruin.model.item;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class EquipmentCheck implements BiPredicate<Entity, Entity> {

    private Map<Integer, List<Integer>> equipment = new HashMap<>();
    private String message;

    public EquipmentCheck(int... ids) {
        this("You do not have the required equipment to cast this spell.", ids);
    }

    public EquipmentCheck(String message, int... itemIds) {
        this.message = message;
        for (int id: itemIds) {
            ItemDef def = ItemDef.get(id);
            if (def == null || def.equipSlot == -1)
                throw new IllegalArgumentException("invalid item given for equipment check: " + id);
            int slot = def.equipSlot;
            List<Integer> items = equipment.computeIfAbsent(slot, i -> new LinkedList<>());
            items.add(id);
        }
    }

    @Override
    public boolean test(Entity caster, Entity target) {
        if (caster.player == null)
            return true;
        return hasItems(caster.player);
    }

    public boolean hasItems(Player player) {
        for (int slot : equipment.keySet()) {
            List<Integer> acceptableItems = equipment.get(slot);
            int id = player.getEquipment().getId(slot);
            if (!acceptableItems.contains(id)) {
                if (message != null)
                    player.sendMessage(message);
                return false;
            }
        }
        return true;
    }
}
