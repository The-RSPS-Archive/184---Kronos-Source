package io.ruin.model.skills.construction.servants;

import io.ruin.cache.ItemDef;

public enum ServantAction { // for saving
    DEPOSIT,
    WITHDRAW,
    UNNOTE,
    TAKE_TO_SAWMILL;

    public String toString(int itemId, int itemAmount) {
        String item = itemAmount + " x " + ItemDef.get(itemId).name;
        switch (this) {
            case DEPOSIT:
                return "Deposit in bank - " + item;
            case WITHDRAW:
                return "Withdraw from bank - " + item;
            case UNNOTE:
                return "Un-note - " + item;
            case TAKE_TO_SAWMILL:
                return "Take to sawmill - " + item;
        }
        return "";
    }


}
