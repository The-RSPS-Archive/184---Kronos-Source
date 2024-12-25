package io.ruin.model.item.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.ground.GroundItem;

public class MaxCape {

    public static void check(Player player) {
        if(unlocked(player))
            return;
        Item hood = player.getEquipment().get(Equipment.SLOT_HAT);
        if(hood != null && hood.getDef().maxType)
            remove(player, hood);
        Item cape = player.getEquipment().get(Equipment.SLOT_CAPE);
        if(cape != null && cape.getDef().maxType)
            remove(player, cape);
    }

    public static boolean wearing(Player player) {
        Item capeId = player.getEquipment().get(Equipment.SLOT_CAPE);
        return capeId != null && capeId.getDef().maxType;
    }

    private static void remove(Player player, Item item) {
        int id = item.getId();
        item.remove();
        if(player.getInventory().add(id, 1) == 1) {
            player.sendMessage("<col=aa0000>Your " + item.getDef().name + " has been unequipped and added to your inventory because you no longer meet the requirements to wear it.");
            return;
        }
        if(player.getBank().add(id, 1) == 1) {
            player.sendMessage("<col=aa0000>Your " + item.getDef().name + " has been unequipped and added to your bank because you no longer meet the requirements to wear it.");
            return;
        }
        new GroundItem(id, 1).spawn();
        player.sendMessage("<col=aa0000>Your " + item.getDef().name + " has been unequipped and dropped to the floor because you no longer meet the requirements to wear it and have no space in your inventory or bank to store it.");
    }

    public static boolean unlocked(Player player) {
        return player.getStats().total99s >= 22;
    }

    public enum MaxCapes {

        FIRE(6570, 13330, 13329),
        SARADOMIN(2412, 13332, 13331),
        SARADOMIN_IMBUED(21791, 21778, 21776),
        ZAMORAK(2414, 13334, 13333),
        ZAMORAK_IMBUED(21795, 21782, 21780),
        GUTHIX(2413, 13336, 13335),
        GUTHIX_IMBUED(21793, 21786, 21784),
        AVA(10499, 13338, 13337),
        ASSEMBLER(22109, 21900, 21898),
        ARDOUGNE(13124, 20764, 20760),
        INFERNAL(21295, 21282, 21285);

        public final int secondaryId, newHoodId, newCapeId;

        MaxCapes(int secondaryId, int newHoodId, int newCapeId) {
            this.secondaryId = secondaryId;
            this.newHoodId = newHoodId;
            this.newCapeId = newCapeId;
        }
    }

    static {
        for(MaxCapes cape : MaxCapes.values()) {
            ItemItemAction.register(13280, cape.secondaryId, (player, primary, secondary) -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Combine these capes to create " + ItemDef.get(cape.newCapeId).descriptiveName+ "?", primary, () -> {
                    Item hoodItem = player.getInventory().findItem(13281);
                    if(hoodItem == null) {
                        player.dialogue(new ItemDialogue().one(13281, "In order for your max cape to absorb this item, you will also need a max hood in your inventory."));
                        return;
                    }
                    primary.remove();
                    hoodItem.setId(cape.newHoodId);
                    secondary.setId(cape.newCapeId);
                    player.dialogue(new ItemDialogue().one(cape.newCapeId, "You infuse the items together to produce " + ItemDef.get(cape.newCapeId).descriptiveName + "."));
                }));
            });
            ItemAction.registerInventory(cape.newCapeId, "revert", (player, item) -> {
                if(player.getInventory().getFreeSlots() < 3) {
                    player.dialogue(new MessageDialogue("You need at least 2 inventory slots to do this."));
                    return;
                }
                player.dialogue(new OptionsDialogue("Are you use you want to revert your max cape?",
                        new Option("Yes", () -> player.startEvent(event -> {
                            player.lock();
                            item.setId(cape.secondaryId);
                            player.getInventory().add(13280, 1);
                            player.getInventory().add(13281, 1);
                            player.dialogue(new ItemDialogue().two(cape.secondaryId, 13280, "You rip the " + ItemDef.get(cape.secondaryId).name + " from your max cape."));
                            player.unlock();
                        })),
                        new Option("No", player::closeDialogue)
                ));
            });
        }
        LoginListener.register(p -> p.addEvent(e -> {
            e.delay(1);
            check(p);
        }));
        ItemDef.forEach(def -> def.maxType = def.name.toLowerCase().contains("max cape") || def.name.toLowerCase().contains("max hood"));
    }

}
