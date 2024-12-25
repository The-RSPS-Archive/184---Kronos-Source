package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;

public class RingOfRecoil {

    public static final int RING_OF_RECOIL = 2550;

    static {
        ItemAction.registerInventory(RING_OF_RECOIL, "break", (player, item) -> {
            if(player.recoilDamageRemaining == 40) {
                player.dialogue(new ItemDialogue().one(RING_OF_RECOIL, "The ring is fully charged.<br>There would be no point in breaking it."));
                return;
            }
            player.dialogue(
                    new OptionsDialogue(
                            "Status: " + player.recoilDamageRemaining + " damage points left.",
                            new Option("Break the ring.", () -> {
                                item.remove();
                                rechargeRing(player);
                                player.dialogue(new ItemDialogue().one(RING_OF_RECOIL, "The ring shatters. Your next ring of recoil will start<br>afresh from 40 damage points."));
                            }),
                            new Option("Cancel")
                    )
            );
        });
        ItemAction.registerEquipment(RING_OF_RECOIL, "check", (player, item) -> {
            if(player.recoilDamageRemaining == 1)
                player.sendMessage("You can inflict 1 more point of damage before a ring will shatter.");
            else
                player.sendMessage("You can inflict " + player.recoilDamageRemaining + " more points of damage before a ring will shatter.");
        });
    }

    public static void rechargeRing(Player player) {
        player.recoilDamageRemaining = 40;
    }

    public static void check(Player player, Hit hit) {
        if(hit.attacker == null || hit.attackStyle == null)
            return;
        Item ring = player.getEquipment().get(Equipment.SLOT_RING);
        if(ring == null || ring.getId() != RING_OF_RECOIL)
            return;
        int damage = (int) Math.ceil(hit.damage * 0.10);
        if(damage == 0)
            return;
        if(damage >= player.recoilDamageRemaining) {
            damage = player.recoilDamageRemaining;
            rechargeRing(player);
            ring.remove();
            player.sendMessage("<col=7f007f>Your Ring of Recoil has shattered.");
        } else {
            player.recoilDamageRemaining -= damage;
        }
        hit.attacker.hit(new Hit().fixedDamage(damage));
    }

}
