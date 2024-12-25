package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public enum StringCrossbow {

    BRONZE_CROSSBOW(9454, 9174, 9, 6.0, 6671),
    BLURITE_CROSSBOW(9456, 9176, 24, 16.0, 6672),
    IRON_CROSSBOW(9457, 9177, 39, 22.0, 6673),
    STEEL_CROSSBOW(9459, 9179, 46, 24.0, 6674),
    MITHRIL_CROSSBOW(9461, 9181, 54, 32.0, 6675),
    ADAMANT_CROSSBOW(9463, 9183, 61, 41.0, 6676),
    RUNITE_CROSSBOW(9465, 9185, 69, 50.0, 6677),
    DRAGON_CROSSBOW(21921, 21902, 78, 70.0, 7961);

    public final int unstrung, strung, levelReq, animation;
    public final double exp;

    StringCrossbow(int unstrung, int strung, int levelReq, double exp, int animation) {
        this.unstrung = unstrung;
        this.strung = strung;
        this.levelReq = levelReq;
        this.exp = exp;
        this.animation = animation;
    }

    private void make(Player player, Item unstrung, Item crossbowString) {
        unstrung.remove();
        crossbowString.remove();
        player.getInventory().add(strung, 1);
        player.sendMessage("You add a string to the crossbow");
        player.animate(animation);
        player.getStats().addXp(StatType.Fletching, exp, true);
    }

    private static final int CROSSBOW_STRING = 9438;

    static {
        for(StringCrossbow crossbow : values()) {
            SkillItem item = new SkillItem(crossbow.strung).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item unstrung = player.getInventory().findItem(crossbow.unstrung);
                    if(unstrung == null)
                        return;
                    Item crossbowString = player.getInventory().findItem(CROSSBOW_STRING);
                    if(crossbowString == null)
                        return;
                    crossbow.make(player, unstrung, crossbowString);
                    event.delay(2);
                }
            });
            ItemItemAction.register(crossbow.unstrung, CROSSBOW_STRING, (player, unstrung, crossbowString) -> {
                if(!player.getStats().check(StatType.Fletching, crossbow.levelReq, crossbow.strung, "do that"))
                    return;
                boolean multiple = player.getInventory().hasMultiple(unstrung.getId(), crossbowString.getId());
                if(multiple) {
                    SkillDialogue.make(player, item);
                    return;
                }
                crossbow.make(player, unstrung, crossbowString);
            });
        }
    }
}