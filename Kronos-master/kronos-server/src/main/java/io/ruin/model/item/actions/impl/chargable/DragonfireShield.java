package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

import static io.ruin.cache.ItemID.BLOOD_MONEY;

public enum DragonfireShield {

    REGULAR(11284, 11283, 6700),
    ANCIENT_WYVERN(21634, 21633, 7700),
    WARD(22003, 22002, 7873);

    private final int unchargedId, chargedId;
    private final int unchargeAnimation;

    DragonfireShield(int unchargedId, int chargedId, int unchargeAnimation) {
        this.unchargedId = unchargedId;
        this.chargedId = chargedId;
        this.unchargeAnimation = unchargeAnimation;
    }

    public static DragonfireShield get (int id) {
        for(DragonfireShield shield : values())
            if(shield.chargedId == id || shield.unchargedId == id)
                return shield;
        return null;
    }

    private static final int MAX_CHARGES = 50;

    static {
        for(DragonfireShield shield : DragonfireShield.values()) {
            /**
             * Check
             */
            ItemAction.registerInventory(shield.chargedId, "inspect", DragonfireShield::check);
            ItemAction.registerEquipment(shield.chargedId, "check", DragonfireShield::check);
            ItemAction.registerInventory(shield.unchargedId, "inspect", DragonfireShield::check);
            ItemAction.registerEquipment(shield.unchargedId, "check", DragonfireShield::check);

            /**
             * Empty
             */
            ItemAction.registerInventory(shield.chargedId, "empty", DragonfireShield::empty);

            /**
             * Dragonfire charging
             */
            ItemDef.get(shield.chargedId).addPreDefendListener(DragonfireShield::chargeCheck);
            ItemDef.get(shield.unchargedId).addPostDamageListener(DragonfireShield::chargeCheck);

            /**
             * Activate
             */
            ItemAction.registerEquipment(shield.chargedId, "activate", (player, item) -> player.dragonfireShieldSpecial = true);
            ItemAction.registerEquipment(shield.chargedId, "operate", (player, item) -> player.dragonfireShieldSpecial = true);
        }
    }

    private static void chargeCheck(Player player, Item item, Hit hit) {
        if(hit.attackStyle == null)
            return;
        if(hit.attackStyle == AttackStyle.DRAGONFIRE) {
            if(wearingDragonfireShield(player))
                charge(player, player.getEquipment().get(Equipment.SLOT_SHIELD));
        }
    }

    private static boolean wearingDragonfireShield(Player player) {
        int shieldId = player.getEquipment().getId(Equipment.SLOT_SHIELD);
        return shieldId == 11283 || shieldId == 11284 || shieldId == 21634 || shieldId == 21633 || shieldId == 22003 || shieldId == 22002;
    }

    private static void check(Player player, Item shield) {
        int charges = AttributeExtensions.getCharges(shield);
        if(charges == 0)
            player.sendMessage("<col=007f00>Your shield doesn't have any charges.");
        else if(charges == 1)
            player.sendMessage("<col=007f00>Your shield has one charge remaining.");
        else if(charges == MAX_CHARGES)
            player.sendMessage("<col=007f00>Your shield has 50 charges and is currently full!");
        else
            player.sendMessage("<col=007f00>Your shield has " + charges + " charges remaining.");
    }

    private static void charge(Player player, Item shield) {
        int charges = AttributeExtensions.getCharges(shield);
        if(charges >= MAX_CHARGES)
            return;
        AttributeExtensions.setCharges(shield, charges + 1);
        shield.setId(get(shield.getId()).chargedId);
        player.animate(6695);
        if(shield.getId() == DragonfireShield.ANCIENT_WYVERN.chargedId || shield.getId() == DragonfireShield.ANCIENT_WYVERN.unchargedId)
            player.graphics(1399);
        else
            player.graphics(1164);
        player.sendMessage("You shield absorbs most of the dragon fire!");
        player.sendMessage("Your dragonfire shield glows more brightly.");
    }

    private static void payToCharge(Player player, Item shield, Item bloodMoney) {
        int charges = AttributeExtensions.getCharges(shield);
        if(charges == 50) {
            player.dialogue(new ItemDialogue().one(shield.getId(), "Your shield already has the maximum amount of charges!"));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Pay 2,500 blood money to set the shields charges to 50?", shield, () -> {
            if(bloodMoney.getAmount() < 2500) {
                player.dialogue(new ItemDialogue().one(BLOOD_MONEY, "You need at least " + Color.DARK_RED.wrap("2,500 blood money") + " to do this!"));
                return;
            }
            DragonfireShield dfs = get(shield.getId());
            if(dfs == null)
                return;
            AttributeExtensions.setCharges(shield, MAX_CHARGES);
            shield.setId(dfs.chargedId);
            bloodMoney.remove(2500);
        }));
    }

    private static void empty(Player player, Item shield) {
        player.dialogue(new YesNoDialogue("Are you sure you want to empty it?", "Emptying the shield will remove all the dragonfire charges!", shield, () -> {
            if(!player.getInventory().contains(shield))
                return;
            DragonfireShield dfs = get(shield.getId());
            if(dfs == null)
                return;
            AttributeExtensions.clearCharges(shield);
            shield.setId(dfs.unchargedId);
            player.animate(dfs.unchargeAnimation);
            player.sendMessage("<col=007f00> You vent the shield's remaining charges harmlessly into the air.");
        }));
    }

    public static final Projectile PROJECTILE = new Projectile(1166, 31, 16, 0, 30, 2, 0, 11);

    public static void specialAttack(Player player, Entity target) {
        if(player.dragonfireShieldCooldown.isDelayed()) {
            player.dragonfireShieldSpecial = false;
            player.sendMessage("<col=007f00>Your shield is still on cooldown from its last use!");
            return;
        }
        if (!wearingDragonfireShield(player)) {
            player.dragonfireShieldSpecial = false;
            return;
        }
        if(player.isLocked()) {
            player.dragonfireShieldSpecial = false;
            return;
        }
        if(player.getDuel().stage >= 4) {
            player.sendMessage("You can't use the dragonfire special inside the duel arena!");
            return;
        }
        if(player.joinedTournament) {
            player.sendMessage("You can't use the dragonfire special inside the tournament.");
            return;
        }
        if(target == null) {
            player.dragonfireShieldSpecial = false;
            return;
        }
        int shieldId = player.getEquipment().getId(Equipment.SLOT_SHIELD);
        if(!consumeCharge(player, shieldId))
            return;

        if(shieldId == 21633 || shieldId == 21634)
            ancientWyvernSpecial(player, target);
        else
            dragonfireShieldSpecial(player, target);
    }

    private static boolean consumeCharge(Player player, int shieldId) {
        Item shield = player.getEquipment().findItem(shieldId);
        if(shield == null)
            return false;
        int charges = AttributeExtensions.getCharges(shield);
        if(charges <= 0) {
            setShieldToRegular(shield);
            return false;
        } else if(charges == 1) {
            player.sendMessage("<col=007f00>Your shield has degraded upon using its final charge.");
            setShieldToRegular(shield);
        } else {
            AttributeExtensions.deincrementCharges(shield, 1);
        }
        return true;
    }

    private static void setShieldToRegular(Item shield) {
        DragonfireShield dfs = get(shield.getId());
        if(dfs == null)
            return;
        AttributeExtensions.setCharges(shield, 0);
        shield.setId(dfs.unchargedId);
    }

    private static void ancientWyvernSpecial(Player player, Entity target) {
        player.startEvent(event -> {
            player.lock();
            int damage = Random.get(25);
            player.animate(7700);
            player.getCombat().updateLastAttack(4);
            if(damage > 0)
                target.freeze(15, target);
            player.dragonfireShieldCooldown.delaySeconds(120);
            player.dragonfireShieldSpecial = false;
            event.delay(3);
            target.hit(new Hit(player, AttackStyle.MAGIC, AttackType.ACCURATE).fixedDamage(damage));
            target.graphics(367);
            player.unlock();
        });
    }

    private static void dragonfireShieldSpecial(Player player, Entity target) {
        player.startEvent(event -> {
            player.lock();
            player.animate(6696);
            player.graphics(1165);
            player.dragonfireShieldCooldown.delaySeconds(120);
            player.dragonfireShieldSpecial = false;
            player.getCombat().updateLastAttack(4);
            event.delay(3);
            int delay = PROJECTILE.send(player, target);
            target.hit(new Hit(player, AttackStyle.MAGIC, AttackType.ACCURATE).randDamage(Random.get(25)).clientDelay(delay));
            player.unlock();
        });
    }

}
