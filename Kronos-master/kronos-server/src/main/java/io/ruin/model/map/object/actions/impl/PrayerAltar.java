package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class PrayerAltar {

    public static void pray(Player player) {
        Stat prayer = player.getStats().get(StatType.Prayer);
        if(prayer.currentLevel == prayer.fixedLevel) {
            player.sendMessage("You already have full prayer points.");
            return;
        }
        prayer.restore();
        player.animate(645);
        player.privateSound(2674);
    }

    public static void switchBook(Player player, SpellBook book, boolean altar) {
        if(book.isActive(player) && altar) {
            player.dialogue(new MessageDialogue("You're already using this spellbook."));
            return;
        }
        book.setActive(player);
        if(altar) {
            player.animate(645);
            player.sendMessage("You are now using the " + book.name + " spellbook.");
        }
    }

    private static void bonesOnAltar(Player player, Item item, Bone bone) {
        item.remove();
        player.animate(3705);
        player.sendFilteredMessage("The gods are pleased with your offerings.");
        player.getStats().addXp(StatType.Prayer, bone.exp * 1.5, true);
        World.sendGraphics(624, 0, 0, 3091, 3510, 0);
        bone.altarCounter.increment(player, 1);
    }

    static {
        /**
         * Registering all prayer altars
         */
        ObjectDef.forEach(def -> {
            if(def.hasOption("pray-at"))
                ObjectAction.register(def.id, "pray-at", (player, obj) -> pray(player));
        });
        /**
         * Custom Edgeville altar
         */
        final int[] ALTARS = new int[] { 18258, 31858, 33524, 50001};
        for(int altar : ALTARS){
            ObjectAction.register(altar, actions -> {
                actions[1] = (player, obj) -> pray(player);
                actions[2] = (player, obj) -> player.dialogue(
                        new OptionsDialogue("Select which prayer book you'd like to switch to:",
                                new Option("Modern", () -> switchBook(player, SpellBook.MODERN, true)),
                                new Option("Ancient", () -> switchBook(player, SpellBook.ANCIENT, true)),
                                new Option("Lunar", () -> switchBook(player, SpellBook.LUNAR, true)),
                                new Option("Arceuus", () -> switchBook(player, SpellBook.ARCEUUS, true))
                        )
                );
            });
        }
        /**
         * Bones on our home location altar
         */
        for(Bone bone : Bone.values()) {
            SkillItem item = new SkillItem(bone.id).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item boneItem = player.getInventory().findItem(bone.id);
                    if(boneItem == null)
                        return;
                    if(player.getInventory().hasMultiple(boneItem.getId())) {
                        bonesOnAltar(player, boneItem, bone);
                        event.delay(3);
                        continue;
                    }
                    bonesOnAltar(player, boneItem, bone);
                    break;
                }
            });
            ItemObjectAction.register(bone.id, 18258, (player, boneItem, obj) -> {
                if (player.getInventory().hasMultiple(boneItem.getId())) {
                    SkillDialogue.make(player, item);
                    return;
                }
                bonesOnAltar(player, boneItem, bone);
            });
            ItemObjectAction.register(bone.id, 50001, (player, boneItem, obj) -> {
                if (player.getInventory().hasMultiple(boneItem.getId())) {
                    SkillDialogue.make(player, item);
                    return;
                }
                bonesOnAltar(player, boneItem, bone);
            });
        }
    }

}
