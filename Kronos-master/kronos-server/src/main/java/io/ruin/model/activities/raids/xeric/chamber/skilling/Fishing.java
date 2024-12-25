package io.ruin.model.activities.raids.xeric.chamber.skilling;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public enum Fishing {

    KYREN(20867, 90, 130.0),
    ROQED(20865, 75, 110.0),
    MYCIL(20863, 60, 105.0),
    BRAWK(20861, 45, 95.0),
    LECKISH(20859, 30, 70.0),
    SUPHI(20857, 15, 40.0),
    PYSK(20855, 1, 10.0);

    public final int itemId, levelReq;
    public final double exp;

    Fishing(int itemId, int levelReq, double exp) {
        this.itemId = itemId;
        this.levelReq = levelReq;
        this.exp = exp;
    }

    private static final int FISHING_ROD = 307;
    private static final int CAVE_WORMS = 20853;

    static {
        ObjectAction.register(29889, "fish", (player, obj) -> {
            int fishingLevel = player.getStats().get(StatType.Fishing).currentLevel;
            for (Fishing fish : values()) {
                if (fishingLevel >= fish.levelReq) {
                    fish(player, fish);
                    break;
                }
            }
        });
    }

    private static void fish(Player player, Fishing fish) {
        if(player.getInventory().isFull()) {
            player.dialogue(new MessageDialogue("Your inventory is too full to hold any more."));
            return;
        }

        if(!player.getInventory().hasId(FISHING_ROD)) {
            player.sendMessage("You need a fishing rod to fish from this spot.");
            return;
        }

        if(!player.getInventory().hasId(CAVE_WORMS)) {
            player.sendMessage("You need at least one cave worm to fish from this spot.");
            return;
        }

        player.animate(622);
        player.startEvent(event -> {
            int animTicks = 2;
            while(true) {
                if(animTicks > 0) {
                    event.delay(1);
                    animTicks--;
                }
                if(success(player, fish.levelReq)) {

                    player.collectResource(fish.itemId, 1);
                    player.getInventory().add(fish.itemId, 1);
                    player.getStats().addXp(StatType.Fishing, fish.exp * anglerBonus(player), false);
                    player.getInventory().remove(CAVE_WORMS, 1);

                    if(!player.getInventory().hasId(CAVE_WORMS)) {
                        player.sendMessage("You've ran out of bait!");
                        player.resetAnimation();
                        return;
                    }

                    if(player.getInventory().isFull()) {
                        player.dialogue(new MessageDialogue("Your inventory is too full to hold any more."));
                        player.resetAnimation();
                        return;
                    }

                }
                if(animTicks == 0) {
                    player.animate(623);
                    animTicks = 3;
                }
            }
        });

    }

    private static boolean success(Player player, int levelReq) {
        double chance = 0.18;
        double roll = Random.get();
        int levelDifference = player.getStats().get(StatType.Fishing).currentLevel - levelReq;
        chance += (double) levelDifference * 0.01;
        return !(roll > Math.min(chance, 0.90));
    }

    private static double anglerBonus(Player player) {
        double bonus = 1.0;
        Item hat = player.getEquipment().get(Equipment.SLOT_HAT);
        Item top = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item waders = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (hat != null && hat.getId() == 13258)
            bonus += 0.4;
        if (top != null && top.getId() == 13259)
            bonus += 0.8;
        if (waders != null && waders.getId() == 13260)
            bonus += 0.6;
        if (boots != null && boots.getId() == 13261)
            bonus += 0.2;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.5;

        return bonus;
    }

}
