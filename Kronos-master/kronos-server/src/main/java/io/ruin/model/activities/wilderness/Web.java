package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Web {

    private static final int KNIFE = 946;

    private static void slashWeb(Player player, GameObject web) {
        boolean knife, wildernessSword;
        ItemDef wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if(wepDef != null && wepDef.sharpWeapon) {
            knife = false;
            wildernessSword = wepDef.id == 13111;
        } else {
            if(!player.getInventory().hasId(KNIFE)) {
                player.sendMessage("Only a sharp blade can cut through this sticky web.");
                return;
            }
            knife = true;
            wildernessSword = false;
        }
        player.startEvent(event -> {
            player.animate(knife ? 911 : player.getCombat().weaponType.attackAnimation);
            if(wildernessSword || Random.rollDie(2, 1)) {
                player.lock();
                player.sendMessage("You slash the web apart.");
                event.delay(1);
                World.startEvent(e -> {
                    web.setId(734);
                    e.delay(100);
                    web.setId(733);
                });
                player.unlock();
                return;
            }
            player.sendMessage("You fail to cut through it.");
        });
    }

    static {
        ObjectAction.register(733, "slash", Web::slashWeb);
        ItemDef.forEach(def -> {
            if(def.equipSlot != Equipment.SLOT_WEAPON)
                return;
            String name = def.name.toLowerCase();
            if(name.contains("axe") || name.contains("claws") || name.contains("dagger") || name.contains("sword")
                    || name.contains("scimitar") || name.contains("halberd") || name.contains("whip") || name.contains("tentacle")
                    || name.contains("blade") || name.contains("machete") || name.contains("scythe") || name.contains("staff of the dead")
                    || name.contains("xil-ek") || name.contains("excalibur") || name.contains("spear") || name.contains("hasta")
                    || name.contains("rapier"))
                def.sharpWeapon = true;
        });
    }
}
