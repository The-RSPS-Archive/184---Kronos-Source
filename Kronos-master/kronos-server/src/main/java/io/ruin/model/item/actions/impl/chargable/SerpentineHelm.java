package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public enum SerpentineHelm {

    SERPENTINE(12929, 12931, -1),
    TANZANITE(13196, 13197, 13200),
    MAGMA(13198, 13199, 13201);

    private static final int ZULRAH_SCALES = 12934;
    private static final int MAX_AMOUNT = 11000;

    SerpentineHelm(int uncharged, int charged, int mutagen) {
        this.uncharged = uncharged;
        this.charged = charged;
        this.mutagen = mutagen;
    }

    public int getUncharged() {
        return uncharged;
    }

    public int getChargedId() {
        return charged;
    }

    public int getMutagen() {
        return mutagen;
    }

    private int uncharged, charged;
    private int mutagen;

    public static SerpentineHelm get(int id) {
        for (SerpentineHelm h : values())
            if (h.charged == id || h.uncharged == id)
                return h;
        return null;
    }

    public static SerpentineHelm getMutated(int id) {
        for (SerpentineHelm h : values())
            if (h.mutagen == id)
                return h;
        return null;
    }

    static {
        ItemItemAction.register(Tool.CHISEL, 12927, (player, primary, secondary) -> {
            if (!player.getStats().check(StatType.Crafting, 53, "create a serpentine helm")) {
                return;
            }
            secondary.setId(SERPENTINE.uncharged);
            player.sendMessage("You create a serpentine helm.");
        });
        ItemAction.registerInventory(12927, "dismantle", SerpentineHelm::dismantle);
        for (SerpentineHelm helm : SerpentineHelm.values()) {
            ItemAction.registerInventory(helm.uncharged, "dismantle", SerpentineHelm::dismantle);
            ItemAction.registerInventory(helm.charged, "check", SerpentineHelm::check);
            ItemAction.registerEquipment(helm.charged, "check", SerpentineHelm::check);
            ItemAction.registerInventory(helm.charged, "uncharge", SerpentineHelm::uncharge);
            ItemItemAction.register(helm.uncharged, ZULRAH_SCALES, SerpentineHelm::charge);
            ItemItemAction.register(helm.charged, ZULRAH_SCALES, SerpentineHelm::charge);

            ItemDef.get(helm.charged).addOnTickEvent(SerpentineHelm::consumeCharge);
            ItemDef.get(helm.charged).addPreTargetDefendListener((player, item, hit, target) -> {
                if (hit.attackStyle != null && hit.attackStyle.isMelee() && target.npc != null && !target.isPoisonImmune() && Random.rollDie(6, 1))
                    target.envenom(6);
            });
            ItemDef.get(helm.charged).breakId = SERPENTINE.uncharged;
        }
        ItemItemAction.register(SERPENTINE.uncharged, MAGMA.mutagen, SerpentineHelm::color);
        ItemItemAction.register(SERPENTINE.charged, MAGMA.mutagen, SerpentineHelm::color);
        ItemItemAction.register(SERPENTINE.uncharged, TANZANITE.mutagen, SerpentineHelm::color);
        ItemItemAction.register(SERPENTINE.charged, TANZANITE.mutagen, SerpentineHelm::color);
    }

    private static void consumeCharge(Player player, Item item) {
        if (player.getCombat().isDefending(10) && Random.get() < 11.0 / 90) { //TODO do this properly instead of in a probabilistic way
            AttributeExtensions.deincrementCharges(item, 1);
            int charges = AttributeExtensions.getCharges(item);
            if (charges <= 0) {
                player.sendMessage(Color.RED.wrap("Your helm has ran out of charges!"));
                SerpentineHelm h = get(item.getId());
                item.setId(h.uncharged);
            }
        }
    }

    private static void check(Player player, Item helm) {
        String scales;
        int scalesAmount = AttributeExtensions.getCharges(helm);
        if (scalesAmount == 0)
            scales = "0.0%, 0 scales";
        else
            scales = NumberUtils.formatOnePlace(((double) scalesAmount / MAX_AMOUNT) * 100D) + "%, " + scalesAmount + " scales";
        player.sendMessage("Scales: <col=007f00>" + scales + "</col>");
    }

    private static void charge(Player player, Item helm, Item scalesItem) {
        int scalesAmount = AttributeExtensions.getCharges(helm);
        int allowedAmount = MAX_AMOUNT - scalesAmount;
        if (allowedAmount == 0) {
            player.sendMessage("The serpentine helm can't hold any more scales.");
            return;
        }
        int addAmount = Math.min(allowedAmount, scalesItem.getAmount());
        scalesItem.incrementAmount(-addAmount);
        AttributeExtensions.addCharges(helm, addAmount);
        helm.setId(get(helm.getId()).charged);
        check(player, helm);
    }

    private static void uncharge(Player player, Item helm) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge the helmet, all scales will fall out.", helm, () -> {
            int scalesAmount = AttributeExtensions.getCharges(helm);
            player.getInventory().add(ZULRAH_SCALES, scalesAmount);
            AttributeExtensions.clearCharges(helm);
            helm.setId(get(helm.getId()).uncharged);
        }));
    }

    public static void dismantle(Player player, Item helm) {
        player.dialogue(
                new YesNoDialogue("Are you sure you want to dismantle it?", "The item will be destroyed and you will receive 20,000 scales.",
                        helm,
                        () -> {
                            SerpentineHelm h = get(helm.getId());
                            helm.remove();
                            if (h != null && h.mutagen != -1) {
                                if (!player.getInventory().hasRoomFor(h.mutagen)) {
                                    player.sendMessage("Not enough space in your inventory.");
                                    player.closeDialogue();
                                    return;
                                } else {
                                    player.getInventory().add(h.mutagen, 1);
                                }
                            }
                            player.getInventory().add(ZULRAH_SCALES, 20000);
                            player.closeDialogue();
                        })
        );
    }

    private static void color(Player player, Item helm, Item mutagen) {
        SerpentineHelm oldType = get(helm.getId());
        SerpentineHelm newType = getMutated(mutagen.getId());
        mutagen.remove();
        if (helm.getId() == oldType.charged)
            helm.setId(newType.charged);
        else
            helm.setId(newType.uncharged);
        player.sendMessage("You apply the mutagen to your serpentine helm.");
    }

}
