package io.ruin.model.skills.crafting;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.NEEDLE;
import static io.ruin.model.skills.Tool.THREAD;

public enum Armour {
    //Leather items
    LEATHER_GLOVES(1, ArmourType.LEATHER, 1059, "a pair of leather gloves", 10.0, 1),
    LEATHER_BOOTS(7, ArmourType.LEATHER, 1061, "a pair of leather boots", 11.25, 1),
    LEATHER_COWL(9, ArmourType.LEATHER, 1167, "a leather cowl", 12.5, 1),
    LEATHER_VAMBRACES(11, ArmourType.LEATHER, 1063, "a pair of leather vambraces", 13.0, 1),
    LEATHER_BODY(14, ArmourType.LEATHER, 1129, "a leather body", 15.0, 1),
    LEATHER_CHAPS(18, ArmourType.LEATHER, 1095, "a pair of leather chaps", 17.0, 1),
    HARD_LEATHER_BODY(28, ArmourType.HARD_LEATHER, 1131, "a hard leather body", 35.0, 1),
    SPIKY_VAMBRACES(32, ArmourType.SPIKY_VAMBRANCES, 10077, "a pair of spiky vambraces", 6.0, 1),
    COIF(38, ArmourType.LEATHER, 1169, "a coif", 18.0, 1),
    STUDDED_BODY(41, ArmourType.LEATHER, 1133, "a studded body", 40.0, 1),
    STUDDED_CHAPS(44, ArmourType.LEATHER, 1097, "a pair of studded chaps", 42.0, 1),

    //Green dragon leather items
    GREEN_VAMBRACES(57, ArmourType.GREEN_DRAGONHIDE, 1065, "a pair of green dragonhide vambraces", 62.0, 1),
    GREEN_CHAPS(60, ArmourType.GREEN_DRAGONHIDE, 1099, "a pair of green dragonhide chaps", 124.0, 2),
    GREEN_BODY(63, ArmourType.GREEN_DRAGONHIDE, 1135, "a green dragonhide body", 186.0, 3),

    //Blue dragon leather items
    BLUE_VAMBRACES(66, ArmourType.BLUE_DRAGONHIDE, 2487, "a pair of blue dragonhide vambraces", 70.0, 1),
    BLUE_CHAPS(68, ArmourType.BLUE_DRAGONHIDE, 2493, "a pair of blue dragonhide chaps", 140.0, 2),
    BLUE_BODY(71, ArmourType.BLUE_DRAGONHIDE, 2499, "a blue dragonhide body", 210.0, 3),

    //Red dragon leather items
    RED_VAMBRACES(73, ArmourType.RED_DRAGONHIDE, 2489, "a pair of red dragonhide vambraces", 78.0, 1),
    RED_CHAPS(75, ArmourType.RED_DRAGONHIDE, 2495, "a pair of red dragonhide chaps", 156.0, 2),
    RED_BODY(77, ArmourType.RED_DRAGONHIDE, 2501, "a red dragonhide body", 234.0, 3),

    //Black dragon leather items
    BLACK_VAMBRACES(79, ArmourType.BLACK_DRAGONHIDE, 2491, "a pair of black dragonhide vambraces", 86.0, 1),
    BLACK_CHAPS(82, ArmourType.BLACK_DRAGONHIDE, 2497, "a pair of black dragonhide chaps", 172.0, 2),
    BLACK_BODY(84, ArmourType.BLACK_DRAGONHIDE, 2503, "a black dragonhide body", 258.0, 3),

    //Snakeskin items
    SNAKESKIN_BOOTS(45, ArmourType.SNAKESKIN, 6328, "a pair of snakeskin boots", 30.0, 6),
    SNAKESKIN_VAMBRACES(47, ArmourType.SNAKESKIN, 6330, "a pair of snakeskin vambraces", 35.0, 8),
    SNAKESKIN_BANDANA(48, ArmourType.SNAKESKIN, 6326, "a snakeskin bandana", 45.0, 5),
    SNAKESKIN_CHAPS(51, ArmourType.SNAKESKIN, 6324, "a pair of snakeskin chaps", 50.0, 12),
    SNAKESKIN_BODY(53, ArmourType.SNAKESKIN, 6322, "a snakeskin body", 55.0, 15),

    //Yak hide items
    YAKHIDE_LEGS(43, ArmourType.YAK_HIDE, 10822, "a pair of yak-hide legs", 32.0, 1),
    YAKHIDE_BODY(46, ArmourType.YAK_HIDE, 10824, "a yak-hide body", 32.0, 2),

    //Crab armour
    CRAB_HELMET(15, ArmourType.CRAB_HELMET, 7539, "a crab helmet", 32.5, 1),
    CRAB_CLAW(15, ArmourType.CRAB_CLAW, 7537, "a crab claw", 32.5, 1),

    LAVA_COIF(92, ArmourType.LAVA_DRAGONHIDE, 30074, "a lava dragonhide coif", 774.0, 8),
    LAVA_CHAPS(95, ArmourType.LAVA_DRAGONHIDE, 30080, "a pair of lava dragonhide chaps", 774.0, 8),
    LAVA_BODY(99, ArmourType.LAVA_DRAGONHIDE, 30077, "a lava dragonhide body", 774.0, 8),

    ;

    public final int levelRequirement, cutID, required;
    public final ArmourType leatherType;
    public final double exp;
    public final String leatherName;

    Armour(int levelRequirement, ArmourType leatherType, int cutID, String leatherName, double exp, int required) {
        this.levelRequirement = levelRequirement;
        this.leatherType = leatherType;
        this.cutID = cutID;
        this.leatherName = leatherName;
        this.exp = exp;
        this.required = required;
    }

    private static void craft(Player player, Armour armourType, int amount) {
        player.closeInterfaces();
        if(!player.getStats().check(StatType.Crafting, armourType.levelRequirement, "make " + armourType.leatherType.groupName))
            return;
        Item leatherToCut = player.getInventory().findItem(armourType.leatherType.leather);
        if(leatherToCut == null)
            return;
        int maxAmount = player.getInventory().getAmount(armourType.leatherType.leather) / armourType.required;
        if (maxAmount < amount)
            amount = maxAmount;
        if (amount < 1) {
            player.dialogue(new MessageDialogue("You need at least " + armourType.required + " pieces of "
                    + armourType.leatherType.leatherName + " to make " + armourType.leatherName + "."));
            return;
        }

        final int amt = amount;
        player.startEvent(event -> {
            int made = 0;
            while (made++ < amt) {
                Item thread = player.getInventory().findItem(THREAD);
                if (thread == null) {
                    player.dialogue(new MessageDialogue("You need some thread to make anything out of "
                            + armourType.leatherType.leatherName + "."));
                    return;
                }
                Item needle = player.getInventory().findItem(NEEDLE);
                if (needle == null) {
                    player.dialogue(new MessageDialogue("You need a needle to work with "
                            + armourType.leatherType.leatherName + "."));
                    return;
                }
                if ((made + 1) % 5 == 0) {
                    player.sendFilteredMessage("You use up a reel of your thread.");
                    thread.remove(1);
                }
                if (Random.get(30) == 0) {
                    player.sendFilteredMessage("Your needle broke.");
                    needle.remove(1);
                }

                player.getInventory().remove(armourType.leatherType.leather, armourType.required);
                player.getInventory().add(armourType.cutID, 1);
                player.animate(1249);
                player.sendFilteredMessage("You make " + armourType.leatherName + ".");
                player.getStats().addXp(StatType.Crafting, armourType.exp, true);
                event.delay(2);
            }
        });
    }

    private static final int SOFT_LEATHER = 1741;
    private static final int HARD_LEATHER = 1743;
    private static final int GREEN_DRAGON_LEATHER = 1745;
    private static final int BLUE_DRAGON_LEATHER = 2505;
    private static final int RED_DRAGON_LEATHER = 2507;
    private static final int BLACK_DRAGON_LEATHER = 2509;
    private static final int LAVA_DRAGON_LEATHER = 30083;

    private static final int SNAKESKIN = 6289;
    private static final int YAK_HIDE = 10818;
    private static final int KEBBIT_CLAWS = 10113;
    private static final int FRESH_CRAB_CLAW = 7536;
    private static final int FRESH_CRAB_SHELL = 7538;


    static {
        ItemItemAction.register(NEEDLE, THREAD, (player, needle, softLeather) -> {
            player.sendMessage("Perhaps I should use the needle with a piece of leather instead.");
        });
        /**
         * Leather
         */
        ItemItemAction.register(NEEDLE, SOFT_LEATHER, (player, needle, greenDragonLeather) -> SkillDialogue.make(player,
                new SkillItem(LEATHER_BODY.cutID).addAction((p, amount, event) -> craft(p, LEATHER_BODY, amount)),
                new SkillItem(LEATHER_GLOVES.cutID).addAction((p, amount, event) -> craft(p, LEATHER_GLOVES, amount)),
                new SkillItem(LEATHER_BOOTS.cutID).addAction((p, amount, event) -> craft(p, LEATHER_BOOTS, amount)),
                new SkillItem(LEATHER_VAMBRACES.cutID).addAction((p, amount, event) -> craft(p, LEATHER_VAMBRACES, amount)),
                new SkillItem(LEATHER_CHAPS.cutID).addAction((p, amount, event) -> craft(p, LEATHER_CHAPS, amount)),
                new SkillItem(COIF.cutID).addAction((p, amount, event) -> craft(p, COIF, amount)),
                new SkillItem(LEATHER_COWL.cutID).addAction((p, amount, event) -> craft(p, LEATHER_COWL, amount))
        ));
        /**
         * Green dragon hide
         */
        ItemItemAction.register(NEEDLE, GREEN_DRAGON_LEATHER, (player, needle, greenDragonLeather) -> SkillDialogue.make(player,
                new SkillItem(GREEN_BODY.cutID).addAction((p, amount, event) -> craft(p, GREEN_BODY, amount)),
                new SkillItem(GREEN_VAMBRACES.cutID).addAction((p, amount, event) -> craft(p, GREEN_VAMBRACES, amount)),
                new SkillItem(GREEN_CHAPS.cutID).addAction((p, amount, event) -> craft(p, GREEN_CHAPS, amount))));

        /**
         * Blue dragon hide
         */
        ItemItemAction.register(NEEDLE, BLUE_DRAGON_LEATHER, (player, needle, blueDragonLeather) -> SkillDialogue.make(player,
                new SkillItem(BLUE_BODY.cutID).addAction((p, amount, event) -> craft(p, BLUE_BODY, amount)),
                new SkillItem(BLUE_VAMBRACES.cutID).addAction((p, amount, event) -> craft(p, BLUE_VAMBRACES, amount)),
                new SkillItem(BLUE_CHAPS.cutID).addAction((p, amount, event) -> craft(p, BLUE_CHAPS, amount))));

        /**
         * Red dragon hide
         */
        ItemItemAction.register(NEEDLE, RED_DRAGON_LEATHER, (player, needle, redDragonLeather) -> SkillDialogue.make(player,
                new SkillItem(RED_BODY.cutID).addAction((p, amount, event) -> craft(p, RED_BODY, amount)),
                new SkillItem(RED_VAMBRACES.cutID).addAction((p, amount, event) -> craft(p, RED_VAMBRACES, amount)),
                new SkillItem(RED_CHAPS.cutID).addAction((p, amount, event) -> craft(p, RED_CHAPS, amount))));

        /**
         * Black dragon hide
         */
        ItemItemAction.register(NEEDLE, BLACK_DRAGON_LEATHER, (player, needle, blackDragonLeather) -> SkillDialogue.make(player,
                new SkillItem(BLACK_BODY.cutID).addAction((p, amount, event) -> craft(p, BLACK_BODY, amount)),
                new SkillItem(BLACK_VAMBRACES.cutID).addAction((p, amount, event) -> craft(p, BLACK_VAMBRACES, amount)),
                new SkillItem(BLACK_CHAPS.cutID).addAction((p, amount, event) -> craft(p, BLACK_CHAPS, amount))));

        /**
         * Snakeskin
         */
        ItemItemAction.register(NEEDLE, SNAKESKIN, (player, needle, snakeSkin) -> SkillDialogue.make(player,
                new SkillItem(SNAKESKIN_BODY.cutID).addAction((p, amount, event) -> craft(p, SNAKESKIN_BODY, amount)),
                new SkillItem(SNAKESKIN_CHAPS.cutID).addAction((p, amount, event) -> craft(p, SNAKESKIN_CHAPS, amount)),
                new SkillItem(SNAKESKIN_VAMBRACES.cutID).addAction((p, amount, event) -> craft(p, SNAKESKIN_VAMBRACES, amount)),
                new SkillItem(SNAKESKIN_BANDANA.cutID).addAction((p, amount, event) -> craft(p, SNAKESKIN_BANDANA, amount)),
                new SkillItem(SNAKESKIN_BOOTS.cutID).addAction((p, amount, event) -> craft(p, SNAKESKIN_BOOTS, amount))));

        /**
         * Yak-hide
         */
        ItemItemAction.register(NEEDLE, YAK_HIDE, (player, needle, yakHide) -> SkillDialogue.make(player,
                new SkillItem(YAKHIDE_BODY.cutID).addAction((p, amount, event) -> craft(p, YAKHIDE_BODY, amount)),
                new SkillItem(YAKHIDE_LEGS.cutID).addAction((p, amount, event) -> craft(p, YAKHIDE_LEGS, amount))));

        /**
         * Hard leather
         */
        ItemItemAction.register(NEEDLE, HARD_LEATHER, (player, needle, hardLeather) -> SkillDialogue.make(player,
                new SkillItem(HARD_LEATHER_BODY.cutID).addAction((p, amount, event) -> craft(p, HARD_LEATHER_BODY, amount))));

        /**
         * Spiky Vambraces
         */
        ItemItemAction.register(NEEDLE, KEBBIT_CLAWS, (player, needle, kebbitClaw) -> SkillDialogue.make(player,
                new SkillItem(SPIKY_VAMBRACES.cutID).addAction((p, amount, event) -> craft(p, SPIKY_VAMBRACES, amount))));

        /**
         * Crab helmet
         */
        ItemItemAction.register(NEEDLE, FRESH_CRAB_SHELL, (player, needle, freshCrabShell) -> SkillDialogue.make(player,
                new SkillItem(CRAB_HELMET.cutID).addAction((p, amount, event) -> craft(p, CRAB_HELMET, amount))));

        /**
         * Crab claw
         */
        ItemItemAction.register(NEEDLE, FRESH_CRAB_CLAW, (player, needle, freshCrabClaw) -> SkillDialogue.make(player,
                new SkillItem(CRAB_CLAW.cutID).addAction((p, amount, event) -> craft(p, CRAB_CLAW, amount))));

        /**
         * Lava dragon hide
         */
        ItemItemAction.register(NEEDLE, LAVA_DRAGON_LEATHER, (player, needle, blackDragonLeather) -> SkillDialogue.make(player,
                new SkillItem(LAVA_COIF.cutID).addAction((p, amount, event) -> craft(p, LAVA_COIF, amount)),
                new SkillItem(LAVA_CHAPS.cutID).addAction((p, amount, event) -> craft(p, LAVA_CHAPS, amount)),
                new SkillItem(LAVA_BODY.cutID).addAction((p, amount, event) -> craft(p, LAVA_BODY, amount))));
    }
}
