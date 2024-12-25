package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public enum Javelin {

    BRONZE_JAVELIN(19584, 19570, 825, 1.0, 3),
    IRON_JAVELIN(19584, 19572, 826, 2.0, 17),
    STEEL_JAVELIN(19584, 19574, 827, 5.0, 32),
    MITHRIL_JAVELIN(19584, 19576, 828, 8.0, 47),
    ADAMANT_JAVELIN(19584, 19578, 829, 10.0, 62),
    RUNE_JAVELIN(19584, 19580, 830, 12.4, 77),
    DRAGON_JAVELIN(19584, 19582, 19484, 15.0, 92),
    AMETHYST_JAVELIN(19584, 21352, 21318, 13.5, 84);

    public final int shaft, head, outcome, levelReq;
    public final double exp;

    Javelin(int shaft, int head, int outcome, double exp, int levelReq) {
        this.shaft = shaft;
        this.head = head;
        this.outcome = outcome;
        this.exp = exp;
        this.levelReq = levelReq;
    }

    private void make(Player player, Item shaft, Item head, int amount) {
        shaft.remove(amount);
        head.remove(amount);
        player.getInventory().add(outcome, amount);
        player.getStats().addXp(StatType.Fletching, exp * amount, true);
        if(amount == 1)
            player.sendFilteredMessage("You attach javelin head a to your javelin shaft.");
        else
            player.sendFilteredMessage("You attach javelin heads to " + amount + " javelin shafts.");

    }

    static {
        for (Javelin javelin : values()) {
            SkillItem item = new SkillItem(javelin.outcome).addAction((player, amount, event) -> {
                while (amount-- > 0) {
                    Item shaft = player.getInventory().findItem(javelin.shaft);
                    if (shaft == null)
                        return;
                    Item headItem = player.getInventory().findItem(javelin.head);
                    if (headItem == null)
                        return;
                    int maxAmount = Math.min(shaft.getAmount(), headItem.getAmount());
                    if (maxAmount > 15) {
                        javelin.make(player, shaft, headItem, 15);
                        event.delay(2);
                        continue;
                    }
                    javelin.make(player, shaft, headItem, maxAmount);
                    break;
                }
            });
            ItemItemAction.register(javelin.shaft, javelin.head, (player, shaft, headItem) -> {
                if (!player.getStats().check(StatType.Fletching, javelin.levelReq, javelin.outcome, "make that javelin"))
                    return;
                int maxAmount = Math.min(shaft.getAmount(), headItem.getAmount());
                if (maxAmount > 15) {
                    SkillDialogue.make(player, item);
                    return;
                }
                javelin.make(player, shaft, headItem, maxAmount);
            });
        }
    }
}