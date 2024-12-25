package io.ruin.model.entity.shared.listeners;

import io.ruin.model.combat.Killer;
import io.ruin.model.item.Item;

public interface DropListener {

    void dropping(Killer killer, Item item);

}