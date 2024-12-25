package io.ruin.model.skills.herblore;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public enum HerbTar {

    GUAM(249, 10142, 30.0, 19),
    MARRENTILL(251, 10143, 42.5, 31),
    TARROMIN(253, 10144, 55.0, 39),
    HARRALANDER(255, 10145, 67.5, 44);

    public final int herb, result, levelReq;
    public final double exp;
    public final String herbName;

    HerbTar(int herb, int result, double exp, int levelReq) {
        this.herb = herb;
        this.result = result;
        this.exp = exp;
        this.levelReq = levelReq;
        this.herbName = new Item(herb, 1).getDef().name.toLowerCase();
    }

    private void mix(Player player, Item primary, Item secondary) {
        primary.remove(1);
        secondary.remove(1);
        player.getInventory().add(result, 15);
        player.sendMessage("You mix the " + herbName + " into the swamp tar.");
        player.getStats().addXp(StatType.Herblore, exp, true);
    }

    private static final int SWAMP_TAR = 1939;

    static {
        for(HerbTar herbTar : values()) {
            SkillItem item = new SkillItem(herbTar.herb).addAction((player, amount, event) -> {
                while (amount-- > 0) {
                    Item herb = player.getInventory().findItem(herbTar.herb);
                    if (herb == null)
                        return;
                    Item swampTar = player.getInventory().findItem(SWAMP_TAR);
                    if (swampTar == null)
                        return;
                    int maxAmount = Math.min(swampTar.getAmount(), herb.count());
                    if (maxAmount > 1) {
                        player.animate(5249);
                        event.delay(3);
                        herbTar.mix(player, swampTar, herb);
                        continue;
                    }
                    player.animate(5249);
                    event.delay(3);
                    herbTar.mix(player, swampTar, herb);
                    break;
                }
            });
            ItemItemAction.register(SWAMP_TAR, herbTar.herb, (player, primary, secondary) -> {
                Item pestleAndMortar = player.getInventory().findItem(Tool.PESTLE_AND_MORTAR);
                if(pestleAndMortar == null) {
                    player.sendMessage("You need a pestle and mortar to mix " + herbTar.herbName + " with swamp tar.");
                    return;
                }
                if (!player.getStats().check(StatType.Herblore, herbTar.levelReq, "make " + herbTar.herbName + " tar."))
                    return;
                int amount = Math.min(primary.count(), secondary.count());
                if(amount > 1) {
                    SkillDialogue.make(player, item);
                    return;
                }
                player.startEvent(event -> {
                    player.animate(5249);
                    event.delay(3);
                    herbTar.mix(player, primary, secondary);
                });
            });
        }
    }

}
