package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public enum Arrow {

    HEADLESS(52, 314, 53, 1.0, 1, "headless arrows", ""),
    HEADLESS_CORRUPTED(30115, 314, 30117, 1.0, 90, "headless corrupted arrows", ""),
    BRONZE_ARROW(53, 39, 882, 1.3, 1, "bronze arrows", "bronze"),
    IRON_ARROW(53, 40, 884, 2.5, 15, "iron arrows", "iron"),
    STEEL_ARROW(53, 41, 886, 5.0, 30, "steel arrows", "steel"),
    MITHRIL_ARROW(53, 42, 888, 7.5, 45, "mithril arrows", "mithril"),
    BROAD_ARROW(53, 11874, 4160, 10.0, 52, "broad arrows", "broad"),
    ADAMANT_ARROW(53, 43, 890, 10.0, 60, "adamant arrows", "adamant"),
    RUNE_ARROW(53, 44, 892, 12.5, 75, "rune arrows", "rune"),
    DRAGON_ARROW(53, 11237, 11212, 15.0, 90, "dragon arrows", "dragon"),
    AMETHYST(53, 21350, 21326, 13.5, 82, "amethyst arrow", "amethyst"),
    CORRUPTED_ARROW(30117, 30119, 30121, 15.5, 90, "corrupted arrow", "corrupted")
    ;

    public final int shaft, tip, outcome, levelReq;
    public final Double exp;
    public final String itemName, shortName;

    Arrow(int shaft, int tip, int outcome, double exp, int levelReq, String itemName, String shortName) {
        this.shaft = shaft;
        this.tip = tip;
        this.outcome = outcome;
        this.exp = exp;
        this.levelReq = levelReq;
        this.itemName = itemName;
        this.shortName = shortName;
    }

    private void make(Player player, Item shaft, Item tipItem, int amount) {
        shaft.remove(amount);
        tipItem.remove(amount);
        player.getInventory().add(outcome, amount);
        player.getStats().addXp(StatType.Fletching, exp * amount, true);
        boolean headless = shaft.getId() == 52 && tipItem.getId() == 314;
        if(headless) {
            player.sendFilteredMessage("You attach feathers to " + amount + " arrow shafts.");
        } else {
            player.sendFilteredMessage("You attach arrow heads to " + amount + " arrow shafts.");
            player.sendFilteredMessage("You attach " + shortName + " heads to some of your arrows.");
        }
    }

    static {
        for(Arrow arrow : values()) {
            SkillItem item = new SkillItem(arrow.outcome).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item shaft = player.getInventory().findItem(arrow.shaft);
                    if(shaft == null)
                        return;
                    Item tipItem = player.getInventory().findItem(arrow.tip);
                    if(tipItem == null)
                        return;
                    int maxAmount = Math.min(shaft.getAmount(), tipItem.getAmount());
                    if(maxAmount > 15) {
                        arrow.make(player, shaft, tipItem, 15);
                        event.delay(2);
                        continue;
                    }
                    arrow.make(player, shaft, tipItem, maxAmount);
                    break;
                }
            });
            ItemItemAction.register(arrow.shaft, arrow.tip, (player, shaft, tipItem) -> {
                if(!player.getStats().check(StatType.Fletching, arrow.levelReq, arrow.outcome, "make " + arrow.itemName))
                    return;
                if (arrow == BROAD_ARROW && Config.BROADER_FLETCHING.get(player) == 0) {
                    player.sendMessage("You haven't unlocked the ability to fletch broad arrows.");
                    return;
                }
                int maxAmount = Math.min(shaft.getAmount(), tipItem.getAmount());
                if(maxAmount > 15) {
                    SkillDialogue.make(player, item);
                    return;
                }
                arrow.make(player, shaft, tipItem, maxAmount);
            });
        }
    }

}