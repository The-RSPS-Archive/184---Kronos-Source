package io.ruin.model.activities.raids.xeric.chamber.skilling;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class Herblore {

    /**
     * Potion brewing
     */
    enum Potions {

        /* combat potions */
        ELDER(20905, 20910, new int[]{70, 59, 47}, new int[]{20924, 20920, 20916}),
        TWISTED(20905, 20912, new int[]{70, 59, 47}, new int[]{20936, 20932, 20928}),
        KODAI(20905, 20911, new int[]{70, 59, 47}, new int[]{20948, 20944, 20940}),

        /* restore potions */
        REVITALISATION(20908, 20910, new int[]{78, 65, 52}, new int[]{20960, 20956, 20952}),
        PRAYER_ENHANCE(20908, 20912, new int[]{78, 65, 52}, new int[]{20972, 20968, 20964}),
        XERIC_ACID(20908, 20911, new int[]{78, 65, 52}, new int[]{20984, 20980, 20976}),

        /* overload */
        OVERLOAD(new int[][]{
                {20924, 20936, 20948},
                {20920, 20932, 20944},
                {20916, 20928, 20940}}, 20902, new int[]{90, 75, 60}, new int[]{20996, 20992, 20988});

        public int herbId, secondaryId;
        public int[] levelReqs, potionIds;
        public int[][] secondaryPotions;

        Potions(int herbId, int secondaryId, int[] levelReqs, int[] potionIds) {
            this.herbId = herbId;
            this.secondaryId = secondaryId;
            this.levelReqs = levelReqs;
            this.potionIds = potionIds;
        }

        Potions(int[][] secondaryPotions, int herbId, int[] levelReqs, int[] potionIds) {
            this.secondaryPotions = secondaryPotions;
            this.herbId = herbId;
            this.levelReqs = levelReqs;
            this.potionIds = potionIds;;
        }
    }

    static {
        for (Potions potion : Potions.values()) {
            if (potion != Potions.OVERLOAD) {
                ItemItemAction.register(potion.herbId, 20801, (player, herbId, gourdVial) -> onGourdVial(player, potion, -1));
                //ItemItemAction.register(potion.secondaryId, 20801, (player, herbId, gourdVial) -> onGourdVial(player, potion, -1)); shit fix for now lul
            } else {
                for (int i = 0; i < 3; i++) {
                    final int potionTier = i;
                    for (int secondaryPotion : potion.secondaryPotions[i]) {
                        ItemItemAction.register(secondaryPotion, 20902, (player, primary, secondary) -> onGourdVial(player, potion, potionTier));
                    }
                }
            }
        }
    }

    private static void onGourdVial(Player player, Potions potion, int tier) {
        if (!player.getStats().check(StatType.Herblore, potion.levelReqs[2], "make this potion"))
            return;
        int potionTier = -1;
        int herbloreLevel = player.getStats().get(StatType.Herblore).currentLevel;
        /* determine which potion tier the player can make */
        for (int i = 0; i < 3; i++) {
            if (herbloreLevel >= potion.levelReqs[i]) {
                potionTier = i;
                break;
            }
        }
        if (potion == Potions.OVERLOAD) {
            if (player.getInventory().hasMultiple(potion.herbId, potion.secondaryPotions[tier][0], potion.secondaryPotions[tier][1], potion.secondaryPotions[tier][2]))
                SkillDialogue.make(player, new SkillItem(potion.potionIds[tier]).addAction((p, amount, event) -> createOverload(p, potion, tier, amount)));
            else
                createOverload(player, potion, tier, 1);
            return;
        }

        selectPotion(player, potion, potionTier, potion.herbId == 20905);
    }

    private static void selectPotion(Player player, Potions potion, int potionTier, boolean combatPotion) {
        /**
         * Combat potions
         */
        if (combatPotion) {
            Item strinkhornMushroom = player.getInventory().findItem(20910);
            Item endarkenedJuice = player.getInventory().findItem(20911);
            Item cicely = player.getInventory().findItem(20912);

            if (strinkhornMushroom != null && endarkenedJuice != null && cicely != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.ELDER.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.ELDER, potionTier, amount)),
                        new SkillItem(Potions.KODAI.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.KODAI, potionTier, amount)),
                        new SkillItem(Potions.TWISTED.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.TWISTED, potionTier, amount))
                );
            } else if (strinkhornMushroom != null && endarkenedJuice != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.ELDER.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.ELDER, potionTier, amount)),
                        new SkillItem(Potions.KODAI.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.KODAI, potionTier, amount))
                );
            } else if (strinkhornMushroom != null && cicely != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.ELDER.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.ELDER, potionTier, amount)),
                        new SkillItem(Potions.TWISTED.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.TWISTED, potionTier, amount))
                );
            } else if (endarkenedJuice != null && cicely != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.KODAI.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.KODAI, potionTier, amount)),
                        new SkillItem(Potions.TWISTED.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.TWISTED, potionTier, amount))
                );
            } else {
                if (player.getInventory().hasMultiple(potion.herbId, potion.secondaryId))
                    SkillDialogue.make(player, new SkillItem(potion.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, potion, potionTier, amount)));
                else
                    createPotion(player, potion, potionTier, 1);
            }
        } else {
            /**
             * Restore potions
             */
            Item strinkhornMushroom = player.getInventory().findItem(20910);
            Item endarkenedJuice = player.getInventory().findItem(20911);
            Item cicely = player.getInventory().findItem(20912);

            if (strinkhornMushroom != null && endarkenedJuice != null && cicely != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.REVITALISATION.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.REVITALISATION, potionTier, amount)),
                        new SkillItem(Potions.XERIC_ACID.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.XERIC_ACID, potionTier, amount)),
                        new SkillItem(Potions.PRAYER_ENHANCE.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.PRAYER_ENHANCE, potionTier, amount))
                );
            } else if (strinkhornMushroom != null && endarkenedJuice != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.REVITALISATION.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.REVITALISATION, potionTier, amount)),
                        new SkillItem(Potions.XERIC_ACID.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.XERIC_ACID, potionTier, amount))
                );
            } else if (strinkhornMushroom != null && cicely != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.REVITALISATION.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.REVITALISATION, potionTier, amount)),
                        new SkillItem(Potions.PRAYER_ENHANCE.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.PRAYER_ENHANCE, potionTier, amount))
                );
            } else if (endarkenedJuice != null && cicely != null) {
                SkillDialogue.make(player,
                        new SkillItem(Potions.XERIC_ACID.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.XERIC_ACID, potionTier, amount)),
                        new SkillItem(Potions.PRAYER_ENHANCE.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, Potions.PRAYER_ENHANCE, potionTier, amount))
                );
            } else {
                if (player.getInventory().hasMultiple(potion.herbId, potion.secondaryId))
                    SkillDialogue.make(player, new SkillItem(potion.potionIds[potionTier]).addAction((p, amount, event) -> createPotion(p, potion, potionTier, amount)));
                else
                    createPotion(player, potion, potionTier, 1);
            }
        }
    }

    private static void createPotion(Player player, Potions potion, int potionTier, int amount) {
        Item herb = player.getInventory().findItem(potion.herbId);
        Item secondary = player.getInventory().findItem(potion.secondaryId);
        if (herb != null && secondary != null) {
            final int maxAmount = Math.min(amount, Math.min(herb.count(), secondary.count()));
            player.startEvent(event -> {
                int made = 0;
                while (made++ < maxAmount) {
                    if (!player.getInventory().hasId(20801))
                        return;
                    player.getInventory().remove(potion.herbId, 1);
                    player.getInventory().remove(potion.secondaryId, 1);
                    player.getInventory().remove(20801, 1);
                    player.getInventory().add(potion.potionIds[potionTier], 1);
                    player.animate(363);
                    player.sendFilteredMessage("You mix your " + herb.getDef().name + " together with your "
                            + secondary.getDef().name + " into a potion perfectly!");
                    player.getStats().addXp(StatType.Herblore, 26, false);
                    event.delay(2);
                }
            });
        }
    }

    private static void createOverload(Player player, Potions potion, int potionTier, int amount) {
        Item herb = player.getInventory().findItem(potion.herbId);
        Item elder = player.getInventory().findItem(potion.secondaryPotions[potionTier][0]);
        Item kodai = player.getInventory().findItem(potion.secondaryPotions[potionTier][1]);
        Item twisted = player.getInventory().findItem(potion.secondaryPotions[potionTier][2]);

        if (herb != null && elder != null && kodai != null && twisted != null) {
            int herbAmount = herb.count();
            int secondaryAmount = Math.min(elder.count(), Math.min(kodai.count(), twisted.count()));
            final int maxAmount = Math.min(amount, Math.min(herbAmount, secondaryAmount));
            player.startEvent(event -> {
                int made = 0;
                while (made++ < maxAmount) {
                    player.getInventory().remove(potion.secondaryPotions[potionTier][0], 1);
                    player.getInventory().remove(potion.secondaryPotions[potionTier][1], 1);
                    player.getInventory().remove(potion.secondaryPotions[potionTier][2], 1);
                    player.getInventory().remove(20902, 1);
                    player.getInventory().add(potion.potionIds[potionTier], 1);
                    player.getStats().addXp(StatType.Herblore, 66, false);
                    player.animate(363);
                    player.sendFilteredMessage("You mix your noxifer herb together with an elder, kodai and twisted potion into an overload.");
                    event.delay(2);
                }
            });
        } else {
            player.sendFilteredMessage("You need an Elder(4), Kodai(4) and Twisted(4) to create an overload. ");
        }
    }

    /**
     * Potion decanting
     */

    /**
     * Herbs
     */
    enum Herbs {

        NOXIFER(20901, 20902),
        GOLPAR(20904, 20905),
        BUCHU_LEAF(20907, 20908);

        private int grimyId, cleanId;

        Herbs(int grimyId, int cleanId) {
            this.grimyId = grimyId;
            this.cleanId = cleanId;
        }
    }

    static {
        for (Herbs herb : Herbs.values())
            ItemAction.registerInventory(herb.grimyId, "clean", (player, item) -> {
                item.setId(herb.cleanId);
                player.getStats().addXp(StatType.Herblore, 2, false);
                player.sendFilteredMessage("You clean the " + ItemDef.get(herb.cleanId).name + ".");
            });
    }

    /**
     * Geyser
     */
    private static final int EMPTY_GOURD_VIAL = 20800;
    private static final int WATER_FILLED_GOURD_VIAL = 20801;

    static {
        ItemObjectAction.register(EMPTY_GOURD_VIAL, 29878, (player, item, obj) -> {
            player.startEvent(event -> {
                player.sendFilteredMessage("You fill the gourd vial.");
                while (true) {
                    if (!player.getInventory().hasId(EMPTY_GOURD_VIAL))
                        return;
                    player.animate(832);
                    player.privateSound(2609);
                    for (int i = 0; i < 2; i++) {
                        Item gourd = player.getInventory().findItem(EMPTY_GOURD_VIAL);
                        if (gourd != null) {
                            event.delay(1);
                            gourd.setId(WATER_FILLED_GOURD_VIAL);
                        }
                    }
                }
            });
        });
    }

    /**
     * Gourd tree
     */
    static {
        ObjectAction.register(29772, "pick", (player, obj) -> pickGourdTree(player, false));
        ObjectAction.register(29772, "pick-lots", (player, obj) -> pickGourdTree(player, true));
    }

    private static void pickGourdTree(Player player, boolean lots) {
        if (player.getInventory().isFull()) {
            player.dialogue(new MessageDialogue("Your inventory is too full to do this."));
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(2280);
            event.delay(2);
            player.getInventory().add(EMPTY_GOURD_VIAL, lots ? player.getInventory().getFreeSlots() : 1);
            player.sendFilteredMessage("You pick " + (lots ? "some" : "a")
                    + " gourd fruit" + (lots ? "s" : "") + " from the tree, tearing the top"
                    + (lots ? "s" : "") + " off in the process.");
            event.delay(1);
            player.unlock();
        });
    }
}
