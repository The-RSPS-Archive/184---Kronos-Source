package io.ruin.model.skills.construction.servants;

import com.google.gson.annotations.Expose;
import io.ruin.model.item.ItemContainer;

public class ServantSave {

    public void init() {
        if (hiredServant != null)
            inventory.init(hiredServant.getItemCapacity(), false);
    }

    @Expose private ServantDefinition hiredServant = null;

    @Expose private ItemContainer inventory = new ItemContainer();

    @Expose private int actions = 0;

    public ServantAction getLastAction() {
        return lastAction;
    }

    public void setLastAction(ServantAction lastAction) {
        this.lastAction = lastAction;
    }

    public int getLastActionItemId() {
        return lastActionItemId;
    }

    public void setLastActionItemId(int lastActionItemId) {
        this.lastActionItemId = lastActionItemId;
    }

    public int getLastActionItemAmount() {
        return lastActionItemAmount;
    }

    public void setLastActionItemAmount(int lastActionItemAmount) {
        this.lastActionItemAmount = lastActionItemAmount;
    }

    @Expose private ServantAction lastAction;

    @Expose private int lastActionItemId = -1;

    @Expose private int lastActionItemAmount = -1;

    public ServantDefinition getHiredServant() {
        return hiredServant;
    }

    public ItemContainer getInventory() {
        return inventory;
    }

    public int getActions() {
        return actions;
    }

    public int incrementActions() {
        return ++actions;
    }

    public void resetActions() {
        actions = 0;
    }

    public void fire() {
        hiredServant = null;
        resetActions();
        inventory.clear();
    }

    public void hire(ServantDefinition servant) {
        hiredServant = servant;
        resetActions();
        inventory.clear();
    }
}
