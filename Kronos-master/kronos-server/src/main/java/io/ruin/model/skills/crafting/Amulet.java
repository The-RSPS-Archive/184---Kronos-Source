package io.ruin.model.skills.crafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.BALL_OF_WOOL;

public enum Amulet {

    HOLY_SYMBOL(1714, 1718, 1, 4.0, 0),
    UNHOLY_SYMBOL(1720, 1722, 1, 4.0, 0),
    GOLD(1673, 1692, 8, 4.0, -20),
    SAPPHIRE(1675, 1694, 24, 4.0, -20),
    OPAL(21099, 21108, 27, 55.0, -20),
    EMERALD(1677, 1696, 31, 4.0, -20),
    JADE(21102, 21111, 34, 70.0, -20),
    TOPAZ(21105, 21114, 45, 80.0, -20),
    RUBY(1679, 1698, 50, 4.0, -20),
    DIAMOND(1681, 1700, 70, 4.0, -20),
    DRAGONSTONE(1683, 1702, 80, 4.0, -20),
    ONYX(6579, 6581, 90, 4.0, -20),
    ZENYTE(19501, 19541, 98, 4.0, -20);

    private final int unstrung, strung, lvlReq, itemOffset;
    private final double xp;

    Amulet(int unstrung, int strung, int lvlReq, double xp, int itemOffset) {
        this.unstrung = unstrung;
        this.strung = strung;
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.itemOffset = itemOffset;
    }

    private void string(Player player, Item unstrung, Item wool) {
        unstrung.setId(strung);
        wool.remove();
        player.sendFilteredMessage("You put some string on your amulet.");
        player.getStats().addXp(StatType.Crafting, xp, true);
    }

    static {
        for(Amulet amulet : values()) {
            SkillItem item = new SkillItem(amulet.strung).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item unstrung = player.getInventory().findItem(amulet.unstrung);
                    if(unstrung == null)
                        break;
                    Item wool = player.getInventory().findItem(BALL_OF_WOOL);
                    if(wool == null)
                        break;
                    amulet.string(player, unstrung, wool);
                    event.delay(2);
                }
                player.resetAnimation();
            });
            ItemItemAction.register(amulet.unstrung, BALL_OF_WOOL, (player, unstrungAmulet, wool) -> {
                if(!player.getStats().check(StatType.Crafting, amulet.lvlReq, BALL_OF_WOOL, amulet.unstrung, "string this amulet"))
                    return;
                if(player.getInventory().hasMultiple(amulet.unstrung))
                    SkillDialogue.make(player, item);
                else
                    amulet.string(player, unstrungAmulet, wool);
            });
        }
    }
}