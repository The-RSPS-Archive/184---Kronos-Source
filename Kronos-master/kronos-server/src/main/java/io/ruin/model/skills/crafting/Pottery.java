package io.ruin.model.skills.crafting;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.Containers;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public enum Pottery {

    POT(1787, 1931, 1, 6.0, 3.0),
    PIE_DISH(1789, 2313, 7, 15.0, 10),
    BOWL(1791, 1923, 8, 18.0, 14.5),
    PLANT_POT(5352, 5356, 19, 20.0, 17.5);

    public final int unfired, fired, levelReq;
    public final double unfiredExp, firedExp;
    public String name;

    Pottery(int unfired, int fired, int levelReq, double unfiredExp, double firedExp) {
        this.unfired = unfired;
        this.fired = fired;
        this.levelReq = levelReq;
        this.unfiredExp = unfiredExp;
        this.firedExp = firedExp;
        this.name = ItemDef.get(fired).name.toLowerCase();
    }

    /* Items */
    private static final int CLAY = 434;
    private static final int SOFT_CLAY = 1761;

    /* Objects */
    private static final int POTTERS_WHEEL = 14887;
    private static final int[] POTTERY_OVENS = { 14888, 11601 };

    private static void makeSoftClay(Player player, Item primary, Item secondary, int emptyContainer) {
        if (secondary.getId() == Containers.WATERING_CAN_EMPTY.full) {
            player.sendMessage("You can't water that.");
            return;
        }
        player.sendMessage("You mix the clay and water.");
        primary.setId(SOFT_CLAY);
        secondary.setId(emptyContainer);
        player.sendMessage("You now have some soft workable clay.");
    }

    private static void spinClay(Player player, Pottery pottery, int amount) {
        if (!player.getStats().check(StatType.Crafting, pottery.levelReq, "spin this"))
            return;
        player.startEvent(event -> {
            int made = 0;
            while (made++ < amount) {
                Item softClay = player.getInventory().findItem(SOFT_CLAY);
                if (softClay == null) {
                    player.sendMessage("You have run out of clay.");
                    return;
                }

                softClay.setId(pottery.unfired);
                player.getStats().addXp(StatType.Crafting, pottery.unfiredExp, true);
                player.sendMessage("You make the clay into a " + pottery.name);
                player.animate(883);
                player.privateSound(2588);
                event.delay(5);
            }
        });
    }

    private static void firePottery(Player player, Pottery pottery, int amount) {
        player.startEvent(event -> {
            int made = amount;
            while (made-- > 0) {
                Item unfiredPottery = player.getInventory().findItem(pottery.unfired);
                if (unfiredPottery == null) {
                    player.sendMessage("You don't have " + (made == (amount - 1) ? "any " : "anymore ") + pottery.name +
                            (pottery.unfired == Pottery.PIE_DISH.unfired ? "es" : "s") + " which need firing.");
                    return;
                }

                player.sendMessage("You put a " + pottery.name + " in the oven.");
                player.privateSound(2725);
                player.animate(1317);
                event.delay(6);
                unfiredPottery.setId(pottery.fired);
                player.getStats().addXp(StatType.Crafting, pottery.firedExp, true);
                player.sendMessage("You remove the " + pottery.name + " from the oven.");
                event.delay(1);
            }
        });
    }

    static {
        /**
         * Pottery wheel
         */
        ItemObjectAction.register(CLAY, POTTERS_WHEEL, (player, item, obj) -> {
            player.dialogue(new MessageDialogue("This clay is too hard to craft. You'll need to soften it with some water."));
        });
        ItemObjectAction.register(SOFT_CLAY, POTTERS_WHEEL, (player, item, obj) -> SkillDialogue.make(player,
                new SkillItem(POT.unfired).name(ItemDef.get(POT.fired).name).addAction((p, amount, event) -> spinClay(p, POT, amount)),
                new SkillItem(PIE_DISH.unfired).name(ItemDef.get(PIE_DISH.fired).name).addAction((p, amount, event) -> spinClay(p, PIE_DISH, amount)),
                new SkillItem(BOWL.unfired).name(ItemDef.get(BOWL.fired).name).addAction((p, amount, event) -> spinClay(p, BOWL, amount)),
                new SkillItem(PLANT_POT.unfired).name(ItemDef.get(PLANT_POT.fired).name).addAction((p, amount, event) -> spinClay(p, PLANT_POT, amount))));

        /**
         * Pottery oven
         */
        for(int oven : POTTERY_OVENS) {
            ObjectAction.register(oven, "fire", (player, obj) -> SkillDialogue.make(player,
                    new SkillItem(POT.unfired).name(ItemDef.get(POT.fired).name).addAction((p, amount, event) -> firePottery(p, POT, amount)),
                    new SkillItem(PIE_DISH.unfired).name(ItemDef.get(PIE_DISH.fired).name).addAction((p, amount, event) -> firePottery(p, PIE_DISH, amount)),
                    new SkillItem(BOWL.unfired).name(ItemDef.get(BOWL.fired).name).addAction((p, amount, event) -> firePottery(p, BOWL, amount)),
                    new SkillItem(PLANT_POT.unfired).name(ItemDef.get(PLANT_POT.fired).name).addAction((p, amount, event) -> firePottery(p, PLANT_POT, amount))));
            for (Pottery pottery : values())
                ItemObjectAction.register(pottery.unfired, oven, (player, item, obj) -> firePottery(player, pottery, 1));
        }
        /**
         * Water on clay
         */
        for (Containers waterContainers : Containers.values()) {
            SkillItem item = new SkillItem(SOFT_CLAY).addAction((player, amount, event) -> {
                while (amount-- > 0) {
                    Item softClay = player.getInventory().findItem(CLAY);
                    if (softClay == null)
                        return;
                    Item wateringContainer = player.getInventory().findItem(waterContainers.full);
                    if (wateringContainer == null)
                        return;
                    int maxAmount = Math.min(softClay.count(), wateringContainer.count());
                    if (maxAmount > 1) {
                        Pottery.makeSoftClay(player, softClay, wateringContainer, waterContainers.empty);
                        event.delay(2);
                        continue;
                    }
                    Pottery.makeSoftClay(player, softClay, wateringContainer, waterContainers.empty);
                    break;
                }
            });
            ItemItemAction.register(CLAY, waterContainers.full, (player, primary, secondary) -> {
                int maxAmount = Math.min(primary.count(), secondary.count());
                if (maxAmount > 1) {
                    SkillDialogue.make(player, item);
                    return;
                }
                Pottery.makeSoftClay(player, primary, secondary, waterContainers.empty);
            });
        }

    }
}
