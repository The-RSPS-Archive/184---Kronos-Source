package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.map.object.GameObject;

public class Arclight {

    private static final int ARCLIGHT = 19675;
    private static final int DARKLIGHT = 6746;
    private static final int SHARD = 19677;

    static {
        ItemObjectAction.register(DARKLIGHT, 28900, Arclight::createArclight);
        ItemItemAction.register(ARCLIGHT, SHARD, Arclight::charge);
        ItemItemAction.register(DARKLIGHT, SHARD, (player, primary, secondary) -> player.sendMessage("The power of the altar in the Catacombs is required to do this."));
        ItemAction.registerInventory(ARCLIGHT, "check", Arclight::check);
        ItemAction.registerEquipment(ARCLIGHT, "check", Arclight::check);
        ItemDef.get(ARCLIGHT).addPreTargetDefendListener(Arclight::onArclightHit);
        ItemDef.get(DARKLIGHT).addPreTargetDefendListener(Arclight::onDarklightHit);
    }

    private static void onDarklightHit(Player player, Item item, Hit hit, Entity entity) {
        if (entity == null || entity.player != null || !entity.npc.getDef().demon || hit.attackStyle == null || !hit.attackStyle.isMelee())
            return;
        hit.boostDamage(0.75);
        hit.boostAttack(0.5);
    }

    private static void onArclightHit(Player player, Item item, Hit hit, Entity entity) {
        if (entity == null || entity.player != null || !entity.npc.getDef().demon || hit.attackStyle == null || !hit.attackStyle.isMelee())
            return;
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges <= 1) {
            item.setId(DARKLIGHT);
            player.sendMessage(Color.RED.wrap("Your Arclight has ran out of charges!"));
            player.getCombat().updateWeapon(false);
        }
        item.putAttribute(AttributeTypes.CHARGES, currentCharges - 1);
        hit.boostDamage(0.70);
        hit.boostAttack(0.70);
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your arclight currently has " + NumberUtils.formatNumber(item.getAttributeInt(AttributeTypes.CHARGES)) + " charges.");
    }

    private static void charge(Player player, Item arclight, Item shards) {
        int currentCharges = AttributeExtensions.getCharges(arclight);
        int shardsToUse = Math.min(3, shards.getAmount());
        int chargesToAdd = shardsToUse == 3 ? 1000 : (333 * shardsToUse);
        arclight.putAttribute(AttributeTypes.CHARGES, Math.min(10000, currentCharges + chargesToAdd));
        shards.remove(shardsToUse);
        player.dialogue(new ItemDialogue().one(ARCLIGHT, "Your Arclight now holds " + NumberUtils.formatNumber(currentCharges) + " charges."));

    }

    private static void createArclight(Player player, Item item, GameObject gameObject) {
        if (!player.getInventory().contains(SHARD, 3)) {
            player.dialogue(new MessageDialogue("At least 3 ancient shards are required to empower Darklight."));
            return;
        }
        player.getInventory().remove(SHARD, 3);
        item.setId(ARCLIGHT);
        item.putAttribute(AttributeTypes.CHARGES, 1000);
        player.dialogue(new ItemDialogue().two(ARCLIGHT, SHARD, "You empower the sword, transforming it. It now has 1,000 charges."));
    }

}
