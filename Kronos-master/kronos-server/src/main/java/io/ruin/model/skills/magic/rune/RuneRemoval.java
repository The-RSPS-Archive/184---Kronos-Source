package io.ruin.model.skills.magic.rune;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.storage.RunePouch;
import io.ruin.model.item.containers.Equipment;

import java.util.ArrayList;

public class RuneRemoval {

    private ArrayList<Removal> toRemove;

    private boolean keep;

    private RuneRemoval(ArrayList<Removal> toRemove) {
        this.toRemove = toRemove;
    }

    public void remove() {
        if(!keep) {
            for(Removal r : toRemove)
                r.remove();
        }
        toRemove.clear();
    }

    /**
     * Separator
     */

    public static RuneRemoval get(Player player, Item... runeItems) {
        ItemDef wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        Rune staffRune = wepDef == null ? null : wepDef.staffRune;

        ArrayList<Item> pItems = new ArrayList<>();
        for(Item item : player.getInventory().getItems()) {
            if(item != null)
                pItems.add(item);
        }
        if(player.getInventory().hasId(RunePouch.RUNE_POUCH)) {
            for(Item item : player.getRunePouch().getItems()) {
                if(item != null)
                    pItems.add(item);
            }
            for(Item item : player.getTournamentRunePouch().getItems()) {
                if(item != null)
                    pItems.add(item);
            }
        }
        ArrayList<Removal> toRemove = new ArrayList<>();
        for(Item reqItem : runeItems) {
            int reqId = reqItem.getId();
            int reqAmount = reqItem.getAmount();
            Rune reqRune = reqItem.getDef().rune;
            if(reqRune != null && reqRune.accept(staffRune)) {
                /**
                 * Use staff
                 */
                continue;
            }
            if(reqRune == Rune.FIRE && player.getEquipment().hasId(20714)) {
                /**
                 * Use tome of fire
                 */
                continue;
            }
            if(reqRune == Rune.WATER && (player.getEquipment().hasId(21006) || player.getEquipment().hasId(30181))) {
                /**
                 * Kodai wand
                 */
                continue;
            }
            for(Item item : pItems) {
                ItemDef def = item.getDef();
                if(reqRune != null) {
                    if(!reqRune.accept(def.rune))
                        continue;
                } else {
                    if(item.getId() != reqId)
                        continue;
                }
                int amount = item.getAmount();
                if(amount >= reqAmount) {
                    toRemove.add(new Removal(item, reqAmount));
                    reqAmount = 0;
                    break;
                }
                toRemove.add(new Removal(item, amount));
                reqAmount -= amount;
            }
            if(reqAmount > 0) {
                /**
                 * Not enough runes
                 */
                return null;
            }
        }
        RuneRemoval removal = new RuneRemoval(toRemove);
        if(wepDef != null && (wepDef.id == 11791 || wepDef.id == 12902 || wepDef.id == 12904 || wepDef.id == 21006 || wepDef.id == 30181))
            removal.keep = Random.get() <= 0.125;
        return removal;
    }

    private static final class Removal {

        private final Item item;

        private final int amount;

        public Removal(Item item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        private void remove() {
            item.incrementAmount(-amount);
        }

    }

}