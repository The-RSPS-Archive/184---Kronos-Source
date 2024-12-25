package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public enum StringBow {

    SHORT_BOW(50, 841, 5, 5.0, 6678),
    LONG_BOW(48, 839, 10, 10.0, 6684),
    OAK_SHORT_BOW(54, 843, 20, 16.5, 6679),
    OAK_LONG_BOW(56, 845, 25, 25.0, 6685),
    COMPOSITE_BOW(4825, 4827, 30, 45.0, 6686),
    WILLOW_SHORT_BOW(60, 849, 35, 33.3, 6680),
    WILLOW_LONG_BOW(58, 847, 40, 41.5, 6686),
    MAPLE_SHORT_BOW(64, 853, 50, 50.0, 6681),
    MAPLE_LONG_BOW(62, 851, 55, 58.3, 6687),
    YEW_SHORT_BOW(68, 857, 65, 68.5, 6682),
    YEW_LONG_BOW(66, 855, 70, 75.0, 6688),
    MAGIC_SHORT_BOW(72, 861, 80, 83.3, 6683),
    MAGIC_LONG_BOW(70, 859, 85, 91.5, 6689),
    CORRUPT_SHORT_BOW(30151, 30148, 90, 98.5, 8525),
    CORRUPT_LONG_BOW(30157, 30154, 95, 105.5, 8526),

    ;

    public final int unstrung, strung, levelReq, animation;
    public final double exp;

    StringBow(int unstrung, int strung, int levelReq, double exp, int animation) {
        this.unstrung = unstrung;
        this.strung = strung;
        this.levelReq = levelReq;
        this.exp = exp;
        this.animation = animation;
    }

    private void make(Player player, Item unstrung, Item bowString) {
        unstrung.remove();
        bowString.remove();
        player.getInventory().add(strung, 1);
        player.sendFilteredMessage("You add a string to the bow.");
        player.animate(animation);
        player.getStats().addXp(StatType.Fletching, exp, true);
    }

    private static final int BOW_STRING = 1777;

    static {
        for(StringBow bow : values()) {
            SkillItem item = new SkillItem(bow.strung).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item unstrung = player.getInventory().findItem(bow.unstrung);
                    if(unstrung == null)
                        return;
                    Item bowString = player.getInventory().findItem(BOW_STRING);
                    if(bowString == null)
                        return;
                    bow.make(player, unstrung, bowString);
                    event.delay(2);
                }
            });
            ItemItemAction.register(bow.unstrung, BOW_STRING, (player, unstrung, bowString) -> {
                if(!player.getStats().check(StatType.Fletching, bow.levelReq, bow.strung, "do that"))
                    return;
                boolean multiple = player.getInventory().hasMultiple(unstrung.getId(), bowString.getId());
                if(multiple) {
                    SkillDialogue.make(player, item);
                    return;
                }
                bow.make(player, unstrung, bowString);
            });
        }
    }
}