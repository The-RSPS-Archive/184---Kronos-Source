package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

public class EnchantCrossbowBolt extends Spell {

    //todo@@ bolt enchanting has changed.. uses new skill interface for each available option
    //https://i.imgur.com/pk8NUZm.png

    public EnchantCrossbowBolt() {
        clickAction = (player, i) -> {
            List<Bolts> enchantableBolts = new ArrayList<>();
            for(Bolts bolts : Bolts.values()) {
                if(player.getInventory().hasId(bolts.bolt))
                        enchantableBolts.add(bolts);
            }
            if(enchantableBolts.isEmpty()) {
                player.dialogue(new MessageDialogue("You don't have any bolts you can enchant."));
                return;
            }
            SkillItem[] bolts = new SkillItem[enchantableBolts.size()];
            enchantableBolts.forEach(bolt -> {
                Item boltItem = player.getInventory().findItem(bolt.bolt);
                if(boltItem != null) {
                    bolts[enchantableBolts.indexOf(bolt)] = new SkillItem(bolt.enchantedBolt).addAction((p, amount, event) -> EnchantCrossbowBolt.Bolts.enchant(p, bolt, amount));
                }
            });
            SkillDialogue.make(player, bolts);
        };
    }

    private enum Bolts {

        /**
         * Regular
         */
        OPAL(4, 4462, 759, 879, 9236, 10, 9.0, new Item(564, 1), new Item(556, 2)),
        SAPPHIRE(7, 4462, 759, 9337, 9240, 10, 17.0, new Item(564, 1), new Item(555, 1), new Item(558, 1)),
        JADE(14, 4462, 759, 9335, 9237, 10, 19.0, new Item(564, 1), new Item(557, 2)),
        PEARL(24, 4462, 759, 880, 9238, 10, 29.0, new Item(564, 1), new Item(556, 2)),
        EMERALD(27, 4462, 759, 9338, 9241, 10, 27.0, new Item(564, 1), new Item(556, 3), new Item(561, 1)),
        RED_TOPAZ(29, 4462, 759, 9336, 9239, 10, 33.0, new Item(564, 1), new Item(554, 2)),
        RUBY(49, 4462, 759, 9339, 9242, 10, 59.9, new Item(564, 1), new Item(554, 5), new Item(565, 1)),
        DIAMOND(57, 4462, 759, 9340, 9243, 10, 67.0, new Item(564, 1), new Item(557, 10), new Item(563, 2)),
        DRAGONSTONE(68, 4462, 759, 9341, 9244, 10, 78.0, new Item(564, 1), new Item(557, 15), new Item(566, 1)),
        ONYX(87, 4462, 759, 9342, 9245, 10, 97.0, new Item(564, 1), new Item(554, 20), new Item(560, 1)),

        /**
         * Dragon
         */
        DRAGON_OPAL(4, 4462, 759, 21955, 21932, 10, 9.0, new Item(564, 1), new Item(556, 2)),
        DRAGON_SAPPHIRE(7, 4462, 759, 21963, 21940, 10, 17.0, new Item(564, 1), new Item(555, 1), new Item(558, 1)),
        DRAGON_JADE(14, 4462, 759, 21957, 21934, 10, 19.0, new Item(564, 1), new Item(557, 2)),
        DRAGON_PEARL(24, 4462, 759, 21959, 21936, 10, 29.0, new Item(564, 1), new Item(556, 2)),
        DRAGON_EMERALD(27, 4462, 759, 21965, 21942, 10, 27.0, new Item(564, 1), new Item(556, 3), new Item(561, 1)),
        DRAGON_RED_TOPAZ(29, 4462, 759, 21961, 21938, 10, 33.0, new Item(564, 1), new Item(554, 2)),
        DRAGON_RUBY(49, 4462, 759, 21967, 21944, 10, 59.9, new Item(564, 1), new Item(554, 5), new Item(565, 1)),
        DRAGON_DIAMOND(57, 4462, 759, 21969, 21946, 10, 67.0, new Item(564, 1), new Item(557, 10), new Item(563, 2)),
        DRAGON_DRAGONSTONE(68, 4462, 759, 21971, 21948, 10, 78.0, new Item(564, 1), new Item(557, 15), new Item(566, 1)),
        DRAGON_ONYX(87, 4462, 759, 21973, 21950, 10, 97.0, new Item(564, 1), new Item(554, 20), new Item(560, 1));

        public final int levelReq, animation, graphic, amt, bolt, enchantedBolt;
        public final double exp;
        public final Item[] runes;

        Bolts(int levelReq, int animation, int graphic, int bolt, int enchantedBolt, int amt, double exp, Item... runes) {
            this.levelReq = levelReq;
            this.animation = animation;
            this.graphic = graphic;
            this.bolt = bolt;
            this.enchantedBolt = enchantedBolt;
            this.amt = amt;
            this.exp = exp;
            this.runes = runes;
        }

        private static void enchant(Player player, Bolts bolt, int amt) {
            if (!player.getStats().check(StatType.Magic, bolt.levelReq, "cast this spell"))
                return;

            player.startEvent(e -> {
                int amount = amt;
                while (amount-- > 0) {
                    RuneRemoval r = null;
                    if ((r = RuneRemoval.get(player, bolt.runes)) == null) {
                        int size = bolt.runes.length;
                        int offset = 0;
                        StringBuilder runeRequirement = new StringBuilder();
                        for(Item rune : bolt.runes) {
                            offset++;
                            runeRequirement.append((offset == 1 ? "" : (size == offset ? "and " : ", ")) +  "x" + rune.getAmount() + " " +rune.getDef().name + (rune.getAmount() == 1 ? "" : "s "));
                        }
                        player.sendMessage("You need " + runeRequirement.toString() + " to enchant this bolt.");
                        break;
                    }
                    Item bolts = player.getInventory().findFirst(bolt.bolt);
                    if (bolts == null)
                        break;
                    r.remove();
                    if (bolts.getAmount() >= 10) {
                        bolts.incrementAmount(-10);
                        player.getInventory().add(bolt.enchantedBolt, 10);
                    } else {
                        int inventoryAmount = bolts.getAmount();
                        bolts.incrementAmount(-inventoryAmount);
                        player.getInventory().add(bolt.enchantedBolt, inventoryAmount);
                    }

                    player.animate(bolt.animation);
                    player.graphics(bolt.graphic);
                    player.sendFilteredMessage("The magic of the runes coaxes out the true nature of the gem tips.");
                    player.getStats().addXp(StatType.Magic, bolt.exp, true);
                    e.delay(3);
                }
            });
        }
    }

}
