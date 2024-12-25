package io.ruin.model.skills.crafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

public enum Glass {

    BEER_GLASS(1, 17.5, 1919, "a beer glass"),
    EMPTY_CANDLE_LANTERN(4, 19.0, 4527, "a candle lantern"),
    OIL_LAMP(12, 25.0, 4522, "an oil lamp"),
    VIAL( 33, 35.0, 229, "a vial"),
    FISH_BOWL(42, 42.5, 6667, "a fish bowl"),
    ORB(46, 52.5, 567, "an orb"),
    LANTERN_LENS(49, 55.0, 4542, "a lantern lens"),
    LIGHT_ORB(87, 70.0, 10973, "a light orb");

    public final int levelReq, itemID;
    public final double exp;
    public String itemName;

    Glass(int levelReq, double exp, int itemID, String itemName) {
        this.levelReq = levelReq;
        this.exp = exp;
        this.itemID = itemID;
        this.itemName = itemName;
    }

    private static final int MOLTEN_GLASS = 1775;
    private static final int GLASSBLOWING_PIPE = 1785;

    private static void craft(Player player, Glass glass, int amount) {
        player.closeInterfaces();
        if(!player.getStats().check(StatType.Crafting, glass.levelReq, "make " + glass.itemName))
            return;

        if(!player.getInventory().hasId(MOLTEN_GLASS)) {
            player.sendMessage("You need a molten glass to craft this item!");
            return;
        }

        player.startEvent(event -> {
            int amt = amount;
            while(amt --> 0) {
                if (!player.getInventory().hasId(MOLTEN_GLASS))
                    return;
                player.animate(884);
                player.getInventory().remove(MOLTEN_GLASS, 1);
                player.getInventory().add(glass.itemID, 1);
                player.getStats().addXp(StatType.Crafting, glass.exp, true);
                event.delay(2);
            }
        });
    }

    static {
        ItemItemAction.register(GLASSBLOWING_PIPE, MOLTEN_GLASS, (player, glassBlowingPipe, moltenGlass) -> {
            List<Glass> blowableGlass = new ArrayList<>();
            for(Glass glass : Glass.values()) {
                blowableGlass.add(glass);
            }
            if(blowableGlass.isEmpty()) {
                player.dialogue(new MessageDialogue("You don't have any molten glass!"));
                return;
            }
            SkillItem[] glass = new SkillItem[blowableGlass.size()];
            blowableGlass.forEach(b -> glass[blowableGlass.indexOf(b)] = new SkillItem(b.itemID).addAction((p, amount, event) -> craft(p, b, amount)));
            SkillDialogue.make(player, glass);
        });

    }

}
