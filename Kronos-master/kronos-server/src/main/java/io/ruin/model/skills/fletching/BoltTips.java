package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public enum BoltTips {

    OPAL(11, 1609, 45, 890, 12, 1.5),
    JADE(26, 1611, 9187, 891, 12, 2.0),
    PEARL(41, 411, 46, 886, 6, 3.2),
    RED_TOPAZ(48, 1613, 9188, 892, 12, 3.9),
    SAPPHIRE(56, 1607, 9189, 888, 12, 4.0),
    EMERALD(58, 1605, 9190, 889, 12, 5.5),
    RUBY(63, 1603, 9191, 887, 12, 6.0),
    DIAMOND(65, 1601, 9192, 886, 12, 7.0),
    DRAGONSTONE(71, 1615, 9193, 885, 12, 8.2),
    ONYX(73, 6573, 9194, 2717, 24, 9.4);

    public final int levelReq, gem, boltTips, anim, amount;
    public final double exp;

    BoltTips(int levelReq, int gem, int boltTips, int anim, int amount, double exp) {
        this.levelReq = levelReq;
        this.gem = gem;
        this.boltTips = boltTips;
        this.anim = anim;
        this.amount = amount;
        this.exp = exp;
    }

    private void make(Player player, Item gem) {
        gem.remove();
        player.getInventory().add(boltTips, amount);
        player.getStats().addXp(StatType.Fletching, exp * amount, true);
        player.animate(anim);
        player.sendFilteredMessage("You use your chisel to fletch small bolt tips.");
    }

    static {
        for(BoltTips boltTips : values()) {
            SkillItem item = new SkillItem(boltTips.gem).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item chisel = player.getInventory().findItem(Tool.CHISEL);
                    if(chisel == null)
                        return;
                    Item gem = player.getInventory().findItem(boltTips.gem);
                    if(gem == null) {
                        player.sendMessage("You have run out of gems.");
                        break;
                    }
                    boltTips.make(player, gem);
                    event.delay(4);
                }
            });
            ItemItemAction.register(boltTips.gem, Tool.CHISEL, (player, gem, chisel) -> {
                if (!player.getStats().check(StatType.Fletching, boltTips.levelReq, "do that"))
                    return;
                if(player.getInventory().hasMultiple(gem.getId())) {
                    SkillDialogue.make(player, item);
                    return;
                }
                boltTips.make(player, gem);
            });
        }
    }

}