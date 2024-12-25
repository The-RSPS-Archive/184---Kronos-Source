package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;

public enum RingOfSuffering {

    REGULAR(19550, 20655),
    IMBUED(19710, 20657);

    public int unchargedId, chargedId;

    RingOfSuffering(int unchargedId, int chargedId) {
        this.unchargedId = unchargedId;
        this.chargedId = chargedId;
    }

    public static RingOfSuffering get(int id) {
        for (RingOfSuffering r : values())
            if (r.chargedId == id || r.unchargedId == id)
                return r;
        return null;
    }

    private static final int MAXIMUM_CHARGES = 100_000;
    private static final int[] RING_OF_RECOIL = new int[]{2550, 2551};

    static {
        for (RingOfSuffering ring : values()) {
            /**
             * Charging
             */
            for (int ringOfRecoil : RING_OF_RECOIL) {
                ItemItemAction.register(ring.chargedId, ringOfRecoil, (player, primary, secondary) -> charge(player, primary, -1));
                ItemItemAction.register(ring.unchargedId, ringOfRecoil, (player, primary, secondary) -> charge(player, primary, ring.chargedId));
            }

            /**
             * Check
             */
            ItemAction.registerEquipment(ring.chargedId, "check", RingOfSuffering::check);
            ItemAction.registerInventory(ring.chargedId, "check", RingOfSuffering::check);

            /**
             * Recoil settings
             */
            ItemAction.registerEquipment(ring.chargedId, "recoil settings", (player, item) -> recoilSettings(player, item, ring.unchargedId));
            ItemAction.registerInventory(ring.chargedId, "recoil settings", (player, item) -> recoilSettings(player, item, ring.unchargedId));
        }
    }

    private static void check(Player player, Item ring) {
        int charges = AttributeExtensions.getCharges(ring);
        player.sendFilteredMessage("<col=007f00>Your ring currently has "
                + NumberUtils.formatNumber(charges) + " recoil charge" + (charges > 1 ? "s" : "")
                + " remaining. The recoil effect is currently "
                + (player.ringOfSufferingEffect ? "enabled" : "disabled") + ".</col>");
    }

    private static void charge(Player player, Item ringOfSuffering, int chargedId) {
        double totalCharges = 0;
        Item recoils = player.getInventory().findItem(2550);
        Item notedRecoils = player.getInventory().findItem(2551);

        /* find how many total charges we can offer the ring of suffering */
        if (recoils != null)
            totalCharges += recoils.count() * 40D;
        if (notedRecoils != null)
            totalCharges += notedRecoils.count() * 40D;
        totalCharges = Math.min(Integer.MAX_VALUE, totalCharges);
        /* if the ring is full, return */
        int sufferingCharges = AttributeExtensions.getCharges(ringOfSuffering);
        if (sufferingCharges == MAXIMUM_CHARGES) {
            player.sendFilteredMessage("<col=007f00>Your ring of suffering can't hold any more charges.");
            return;
        }

        int amountToCharge = (int) Math.min(MAXIMUM_CHARGES - sufferingCharges, totalCharges);

        /* if we've only got 1 ring, remove and add charges */
        if (amountToCharge <= 40) {
            if (recoils != null)
                recoils.remove();
            else if (notedRecoils != null)
                notedRecoils.remove(1);
            if (chargedId != -1)
                ringOfSuffering.setId(chargedId);
            AttributeExtensions.setCharges(ringOfSuffering, sufferingCharges + amountToCharge);
            player.sendFilteredMessage("<col=007f00>You load your ring with 1 ring of recoil. It now has "
                    + NumberUtils.formatNumber(sufferingCharges) + " recoil charges.");
        } else {
            /* if the player has more than one ring we.. */
            player.integerInput("How many rings do you want to use?", amt -> {
                if (amt <= 0)
                    return;
                int amountRequested = (int) Math.min(amountToCharge, Math.min(Integer.MAX_VALUE, amt * 40D));
                int ringsToRemove = amountRequested / 40;
                Item noted = player.getInventory().findItem(2551);
                if (noted != null) {
                    int removed = noted.remove(ringsToRemove);
                    if (removed > 0) {
                        ringsToRemove -= removed;
                        AttributeExtensions.addCharges(ringOfSuffering, removed * 40);
                    }
                }
                if (chargedId != -1)
                    ringOfSuffering.setId(chargedId);
                while (ringsToRemove-- > 0) {
                    Item ring = player.getInventory().findFirst(2550);
                    if (ring == null)
                        break;
                    ring.remove(1);
                    AttributeExtensions.addCharges(ringOfSuffering, 40);
                }
                player.sendFilteredMessage("<col=007f00>You load your ring with " + amountRequested / 40 + " ring"
                        + (amountRequested / 40 > 1 ? "s" : "") + " of recoil. It now has "
                        + NumberUtils.formatNumber(AttributeExtensions.getCharges(ringOfSuffering)) + " recoil charges.");
            });
        }
    }

    private static void recoilSettings(Player player, Item ring, int unchargedId) {
        player.dialogue(new OptionsDialogue(
                new Option("Toggle recoil effect", () -> {
                    player.ringOfSufferingEffect = !player.ringOfSufferingEffect;
                    player.sendFilteredMessage("You " + (player.ringOfSufferingEffect ? "enable" : "disable") + " the recoil effect of your ring.");
                }),
                new Option("Discard recoil changes", () -> {
                    player.dialogue(new OptionsDialogue("Are you sure you wish to discard your recoil charges?",
                            new Option("Yes - release them", () -> {
                                int chargeAmount = AttributeExtensions.getCharges(ring);
                                if (chargeAmount > 0) {
                                    AttributeExtensions.setCharges(ring, 0);
                                    ring.setId(unchargedId);
                                    player.sendFilteredMessage("<col=007f00>You discard the remaining recoil charges in your ring.");
                                }
                            }),
                            new Option("No - keep them", player::closeDialogue)
                    ));
                }),
                new Option("Cancel", player::closeDialogue)
        ));
    }

    public static void check(Player player, Hit hit) {
        if(!player.ringOfSufferingEffect)
            return;
        if(hit.attacker == null || hit.attackStyle == null)
            return;
        Item ring = player.getEquipment().get(Equipment.SLOT_RING);
        if(ring == null || ring.getId() != 20655 && ring.getId() != 20657)
            return;
        int damage = (int) Math.ceil(hit.damage * 0.10);
        if(damage == 0)
            return;
        int charges = AttributeExtensions.getCharges(ring);
        if(damage >= charges) {
            damage = charges;
            ring.setId(get(ring.getId()).unchargedId);
            player.sendMessage("<col=7f007f>Your ring of suffering has run out of charges.");
        } else {
            AttributeExtensions.deincrementCharges(ring, damage);
        }
        hit.attacker.hit(new Hit().fixedDamage(damage));
    }

}