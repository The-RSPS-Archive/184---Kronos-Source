package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public enum Crossbow {

    BRONZE_CROSSBOW(9420, 9440, 9454, 9, 12.0, 4436),
    BLURITE_CROSSBOW(9422, 9442, 9456, 24, 32.0, 4437),
    IRON_CROSSBOW(9423, 9444, 9457, 39, 44.0, 4438),
    STEEL_CROSSBOW(9425, 9446, 9459, 46, 54.0, 4439),
    MITHRIL_CROSSBOW(9427, 9448, 9461, 54, 64.0, 4440),
    ADAMANT_CROSSBOW(9429, 9450, 9463, 61, 82.0, 4441),
    RUNITE_CROSSBOW(9431, 9452, 9465, 69, 100.0, 4442),
    DRAGON_CROSSBOW(21918, 21952, 21921, 78, 135.0, 7860);

    public final int limbs, stock, unfCrossbow, levelReq, anim;
    public final double exp;

    Crossbow(int limbs, int stock, int unfCrossbow, int levelReq, double exp, int anim) {
        this.limbs = limbs;
        this.stock = stock;
        this.unfCrossbow = unfCrossbow;
        this.levelReq = levelReq;
        this.exp = exp;
        this.anim = anim;
    }

    private void make(Player player, Item limbs, Item stock) {
        limbs.remove();
        stock.remove();
        player.getInventory().add(unfCrossbow, 1);
        player.sendFilteredMessage("You attach the stock to the limbs and create an unstrung crossbow.");
        player.animate(anim);
        player.getStats().addXp(StatType.Fletching, exp, true);
    }

    static {
        for(Crossbow crossbow : values()) {
            SkillItem item = new SkillItem(crossbow.unfCrossbow).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item limbs = player.getInventory().findItem(crossbow.limbs);
                    if(limbs == null)
                        return;
                    Item stock = player.getInventory().findItem(crossbow.stock);
                    if(stock == null)
                        return;
                    crossbow.make(player, limbs, stock);
                    event.delay(2);
                }
            });
            ItemItemAction.register(crossbow.limbs, crossbow.stock, (player, limbs, stock) -> {
                if(!player.getStats().check(StatType.Fletching, crossbow.levelReq, crossbow.unfCrossbow, "do that"))
                    return;
                Item hammer = player.getInventory().findItem(Tool.HAMMER);
                if(hammer == null) {
                    player.sendFilteredMessage("You'll need a hammer to do that.");
                    return;
                }
                boolean multiple = player.getInventory().hasMultiple(limbs.getId(), stock.getId());
                if(multiple) {
                    SkillDialogue.make(player, item);
                    return;
                }
                crossbow.make(player, limbs, stock);
            });
        }
    }
}