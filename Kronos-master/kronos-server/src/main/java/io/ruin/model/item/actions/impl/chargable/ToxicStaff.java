package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeTypes;

public class ToxicStaff {

    private static final int ZULRAH_SCALES = 12934;
    private static final int MAX_AMOUNT = 10000;
    private static final int UNCHARGED = 12902;
    private static final int CHARGED = 12904;

    static {
        ItemAction.registerInventory(12932, "dismantle", SerpentineHelm::dismantle);
        ItemDef.get(CHARGED).breakId = UNCHARGED;
        ItemItemAction.register(11791, 12932, (player, primary, secondary) -> {
            player.dialogue(new YesNoDialogue("Are you sure you want to do this?",
                    "The staff will consume the magic fang.", secondary, () -> {
                primary.setId(UNCHARGED);
                secondary.remove();
                player.sendFilteredMessage("You attach the fang to the staff, creating a toxic staff.");
            }));
        });
        ItemAction.registerInventory(UNCHARGED, "dismantle", (player, item) -> {
            if(player.getInventory().isFull()) {
                player.sendFilteredMessage("You need at least 1 inventory space to do this.");
                return;
            }
            player.dialogue(new YesNoDialogue("Are you sure you want to do this?",
                    "Remove the magical fang from your staff?", item, () -> {
                item.setId(11791);
                player.getInventory().add(12932, 1);
                player.sendFilteredMessage("You attach the fang to the staff, creating a toxic staff.");
            }));
        });
        ItemItemAction.register(UNCHARGED, ZULRAH_SCALES, ToxicStaff::charge);
        ItemItemAction.register(CHARGED, ZULRAH_SCALES, ToxicStaff::charge);
        ItemAction.registerInventory(CHARGED, "check", ToxicStaff::check);
        ItemAction.registerEquipment(CHARGED, "check", ToxicStaff::check);
        ItemAction.registerInventory(CHARGED, "uncharge", ToxicStaff::uncharge);
        ItemDef.get(CHARGED).addPostTargetDefendListener((player, item, hit, target) -> {
            if(hit.attackSpell != null && Random.rollDie(4, 1))
                target.envenom(6);
            int charges = getScalesAmount(item);
            if(--charges <= 0)
                item.setId(UNCHARGED);
            item.putAttribute(AttributeTypes.CHARGES, charges);
        });
    }

    private static void charge(Player player, Item staff, Item scalesItem) {
        int scalesAmount = getScalesAmount(staff);
        int allowedAmount = MAX_AMOUNT - scalesAmount;
        if(allowedAmount == 0) {
            player.sendMessage("Your staff is already fully charged.");
            return;
        }
        int addAmount = Math.min(allowedAmount, scalesItem.getAmount());
        scalesItem.incrementAmount(-addAmount);
        staff.putAttribute(AttributeTypes.CHARGES, scalesAmount + (addAmount));
        staff.setId(CHARGED);
        check(player, staff);
    }

    private static void check(Player player, Item helm) {
        String scales;
        int scalesAmount = getScalesAmount(helm);
        if(scalesAmount == 0)
            scales = "0.0%, 0 scales";
        else
            scales = NumberUtils.formatOnePlace(((double)scalesAmount / MAX_AMOUNT) * 100D) + "%, " + scalesAmount + " scales";
        player.sendMessage("Scales: <col=007f00>" + scales + "</col>");
    }

    private static void uncharge(Player player, Item helm) {
        int reqSlots = 0;
        int scalesAmount = getScalesAmount(helm);
        if(scalesAmount > 0) {
            if(!player.getInventory().hasId(ZULRAH_SCALES))
                reqSlots++;
        }
        if(player.getInventory().getFreeSlots() < reqSlots) {
            player.sendMessage("You don't have enough inventory space to uncharge the staff.");
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge the staff, all scales will fall out.", helm, () -> {
            if(scalesAmount > 0)
                player.getInventory().add(ZULRAH_SCALES, scalesAmount);
            helm.putAttribute(AttributeTypes.CHARGES, 0);
            helm.setId(UNCHARGED);
        }));
    }

    private static int getScalesAmount(Item staff) {
        return staff.getAttributeInt(AttributeTypes.CHARGES);
    }

}
