package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;

public class JeweleryTeleports {

    public final String type;

    public final boolean dragonstone;

    public final Teleport[] teleports;

    public JeweleryTeleports(String type, boolean dragonstone, Teleport... teleports) {
        this.type = type;
        this.dragonstone = dragonstone;
        this.teleports = teleports;
    }

    public void register(int id, int charges, int replacementId) {
        ItemAction.registerInventory(id, "rub", (player, item) -> {
            if(charges == 0) {
                player.sendFilteredMessage("This " + type + " doesn't have any remaining charges.");
                return;
            }
            player.sendFilteredMessage("You rub the " + type + "...");
            Option[] options = new Option[teleports.length];
            for(int i = 0; i < teleports.length; i++) {
                Teleport teleport = teleports[i];
                options[i] = new Option(teleport.name, () -> teleport.select(player, item, charges, replacementId, this));
            }
            player.dialogue(new OptionsDialogue("Where would you like to teleport to?", options));
        });
        for(Teleport teleport : teleports) {
            if(charges == 0)
                ItemAction.registerEquipment(id, "rub", (player, item) -> player.sendFilteredMessage("This " + type + " doesn't have any remaining charges."));
            else
                ItemAction.registerEquipment(id, teleport.name, (player, item) -> teleport.select(player, item, charges, replacementId, this));
        }
    }

    protected static final class Teleport {

        public final String name;

        public final int x, y, z;

        public final Bounds bounds;

        public Teleport(String name, int x, int y, int z) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
            this.bounds = null;
        }

        public Teleport(String name, Bounds bounds) {
            this.name = name;
            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.bounds = bounds;
        }

        public void select(Player player, Item item, int charges, int replacementId, JeweleryTeleports teleports) {
            int x, y, z;
            if(bounds == null) {
                x = this.x;
                y = this.y;
                z = this.z;
            } else {
                x = bounds.randomX();
                y = bounds.randomY();
                z = bounds.z;
            }
            player.getMovement().startTeleport(teleports.dragonstone ? 30 : 20, event -> {
                player.animate(714);
                player.graphics(111, 92, 0);
                player.publicSound(200);
                event.delay(3);
                player.getMovement().teleport(x, y, z);
                if(charges != -1) { //eternal glory = -1
                    if(replacementId == -1) { //things like games necklace, ring of dueling, etc
                        item.remove();
                        player.sendMessage("<col=7F00FF>Your " + teleports.type + " crumbles to dust.");
                    } else if(charges == 1) {
                        item.setId(replacementId);
                        player.sendMessage("<col=7F00FF>You use your " + teleports.type + "'s last charge.");
                    } else {
                        item.setId(replacementId);
                        player.sendMessage("<col=7F00FF>Your " + teleports.type + " has " + CHARGES[charges - 2] + " left.");
                    }
                }
            });
        }

    }

    private static final String[] CHARGES = {
            "one charge",
            "two charges",
            "three charges",
            "four charges",
            "five charges",
            "six charges",
            "seven charges",
            "eight charges",
            "nine charges",
            "ten charges"
    };

}
