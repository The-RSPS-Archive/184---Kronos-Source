package io.ruin.model.skills.crafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public enum SpinningWheel {

    BALL_OF_WOOL(1737, 1759, 1, 2.5),
    BOW_STRING(1779, 1777, 10, 15.0),
    ROPE(10814, 954, 30, 25.0),
    CROSSBOW_STRING_SINEW(9436, 9438, 10, 15.0),
    CROSSBOW_STRING_TREE_ROOTS(6049, 9438, 19, 15.0),
    MAGIC_STRING(6051, 6038, 19, 30.0);

    public final int before, after, levelReq;
    public final double exp;

    SpinningWheel(int before, int after, int levelReq, double exp) {
        this.before = before;
        this.after = after;
        this.levelReq = levelReq;
        this.exp = exp;
    }

    private static void spin(Player player, SpinningWheel item, int amount) {
        player.closeInterfaces();
        if(!player.getStats().check(StatType.Crafting, item.levelReq, "make this"))
            return;
        Item before = new Item(item.before, 1);
        if(!player.getInventory().hasId(item.before)) {
            player.sendMessage("You'll need " + before.getDef().name.toLowerCase() + " to make that.");
            return;
        }

        player.startEvent(event -> {
            int amt = amount;
            while(amt --> 0) {
                player.lock();
                if(!player.getInventory().hasId(item.before)) {
                    player.unlock();
                    return;
                }
                spin(player, item);
                event.delay(2);
                player.unlock();
            }
        });
        player.unlock();
    }

    private static void spin(Player player, SpinningWheel item) {
        if(!player.getInventory().hasId(item.before))
            return;
        player.animate(894);
        player.getInventory().remove(item.before, 1);
        player.getInventory().add(item.after, 1);
        player.getStats().addXp(StatType.Crafting, item.exp, true);
    }

    private static void spinningOptions(Player player) {
        SkillDialogue.make(player,
                new SkillItem(BALL_OF_WOOL.after).addAction((p, amount, event) -> spin(p, BALL_OF_WOOL, amount)),
                new SkillItem(BOW_STRING.after).addAction((p, amount, event) -> spin(p, BOW_STRING, amount)),
                new SkillItem(ROPE.after).addAction((p, amount, event) -> spin(p, ROPE, amount)),
                new SkillItem(CROSSBOW_STRING_SINEW.after).addAction((p, amount, event) -> spin(p, CROSSBOW_STRING_SINEW, amount)),
                new SkillItem(CROSSBOW_STRING_TREE_ROOTS.after).addAction((p, amount, event) -> spin(p, CROSSBOW_STRING_TREE_ROOTS, amount)),
                new SkillItem(MAGIC_STRING.after).addAction((p, amount, event) -> spin(p, MAGIC_STRING, amount)));
    }

    static final int[] SPINNING_WHEEL = {25824, 14889, 4309};

    static {
        for(int WHEEL : SPINNING_WHEEL) {
            /**
             * Object interaction
             */
            ObjectAction.register(WHEEL, "spin", (player, obj) -> spinningOptions(player));

            /**
             * Using the materials on the spinning wheel
             */
            ItemObjectAction.register(BALL_OF_WOOL.before, WHEEL, (player, item, obj) -> spin(player, BALL_OF_WOOL));
            ItemObjectAction.register(BOW_STRING.before, WHEEL, (player, item, obj) -> spin(player, BOW_STRING));
            ItemObjectAction.register(MAGIC_STRING.before, WHEEL, (player, item, obj) -> spin(player, MAGIC_STRING));
            ItemObjectAction.register(CROSSBOW_STRING_TREE_ROOTS.before, WHEEL, (player, item, obj) -> spin(player, CROSSBOW_STRING_TREE_ROOTS));
            ItemObjectAction.register(CROSSBOW_STRING_SINEW.before, WHEEL, (player, item, obj) -> spin(player, CROSSBOW_STRING_SINEW));
            ItemObjectAction.register(ROPE.before, WHEEL, (player, item, obj) -> spin(player, ROPE));
            ItemObjectAction.register(MAGIC_STRING.before, WHEEL, (player, item, obj) -> spin(player, ROPE));
        }
    }
}
