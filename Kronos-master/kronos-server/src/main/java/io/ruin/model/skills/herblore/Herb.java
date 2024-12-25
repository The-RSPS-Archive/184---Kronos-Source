package io.ruin.model.skills.herblore;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.VIAL_OF_WATER;

public enum Herb {

    GUAM_LEAF(1, 2.5, 199, 249, 91),
    MARRENTILL(5, 3.75, 201, 251, 93),
    TARROMIN(11, 5.0, 203, 253, 95),
    HARRALANDER(20, 6.25, 205, 255, 97),
    RANARR_WEED(25, 7.5, 207, 257, 99),
    TOADFLAX(30, 8.0, 3049, 2998, 3002),
    IRIT_LEAF(40, 8.75, 209, 259, 101),
    AVANTOE(48, 10.0, 211, 261, 103),
    KWUARM(54, 11.25, 213, 263, 105),
    SNAPDRAGON(59, 11.75, 3051, 3000, 3004),
    CADANTINE(65, 12.5, 215, 265, 107),
    LANTADYME(67, 13.125, 2485, 2481, 2483),
    DWARF_WEED(70, 13.75, 217, 267, 109),
    TORSTOL(75, 15.0, 219, 269, 111);

    public final int lvlReq;

    public final double xp;

    public final int grimyId, cleanId, unfId;

    Herb(int lvlReq, double xp, int grimyId, int cleanId, int unfId) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.grimyId = grimyId;
        this.cleanId = cleanId;
        this.unfId = unfId;
    }

    private void mix(Player player, Item herbItem, Item vialItem) {
        herbItem.remove();
        vialItem.remove();
        player.getInventory().add(unfId, 1);
        player.animate(363);
    }

    static {
        for(Herb herb : values()) {
            String herbName = ItemDef.get(herb.cleanId).name.toLowerCase();
            ItemAction.registerInventory(herb.grimyId, "clean", (player, item) -> {
                if(!player.getStats().check(StatType.Herblore, herb.lvlReq, item.getId(), "clean this"))
                    return;
                item.setId(herb.cleanId);
                player.getStats().addXp(StatType.Herblore, herb.xp, true);
                player.sendFilteredMessage("You clean the " + herbName + ".");
            });
            SkillItem skillItem = new SkillItem(herb.unfId).addAction((player, amount, event) -> {
                while(amount-- > 0) {
                    Item herbItem = player.getInventory().findItem(herb.cleanId);
                    if(herbItem == null)
                        return;
                    Item vialItem = player.getInventory().findItem(VIAL_OF_WATER);
                    if(vialItem == null)
                        return;
                    herb.mix(player, herbItem, vialItem);
                    event.delay(2);
                }
            });
            ItemItemAction.register(herb.cleanId, VIAL_OF_WATER, (player, herbItem, vialItem) -> {
                if(!player.getStats().check(StatType.Herblore, herb.lvlReq, VIAL_OF_WATER, herb.cleanId, "mix this"))
                    return;
                if(player.getInventory().hasMultiple(herb.cleanId, VIAL_OF_WATER)) {
                    SkillDialogue.make(player, skillItem);
                    return;
                }
                herb.mix(player, herbItem, vialItem);
            });
        }
    }

    public static Herb get(int id) {
        for (Herb herb: values()) {
            if (herb.cleanId == id || herb.grimyId == id)
                return herb;
        }
        return null;
    }

}
