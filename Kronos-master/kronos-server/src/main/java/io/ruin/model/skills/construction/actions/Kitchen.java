package io.ruin.model.skills.construction.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.Consumable;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.stat.StatType;

import java.util.function.Consumer;

public class Kitchen {

    static {
        //Shelves
        ItemDispenser.register(Buildable.WOODEN_SHELVES_1,7688, 7702, 7728);
        ItemDispenser.register(Buildable.WOODEN_SHELVES_2,7688, 7702, 7728, 7742);
        ItemDispenser.register(Buildable.WOODEN_SHELVES_3,7688, 7714, 7732, 7742, 1887);
        ItemDispenser.register(Buildable.OAK_SHELVES_1,7688, 7702, 7728, 7742, 1923);
        ItemDispenser.register(Buildable.OAK_SHELVES_2,7688, 7714, 7732, 7742, 1887, 1923);
        ItemDispenser.register(Buildable.TEAK_SHELVES_1,7688, 7714, 7732, 7742, 2313, 1923, 1931);
        ItemDispenser.register(Buildable.TEAK_SHELVES_2,7688, 7726, 7735, 7742, 2313, 1923, 1931, 1949);

        //Larders
        ItemDispenser.register(Buildable.WOODEN_LARDER, 7738, 1927);
        ItemDispenser.register(Buildable.OAK_LARDER, 7738, 1927, 1944, 1933);
        ItemDispenser.register(Buildable.TEAK_LARDER, 7738, 1927, 1944, 1933, 1942, 1550, 1957, 1985);


    }

    enum Tea {
        CLAY(7728, 7702, 7700, 7692, 7730, 7731, 1),
        PORCELAIN(7732, 7714, 7712, 7704, 7733, 7734, 2),
        GILDED(7735, 7726, 7724, 7716, 7736, 7737, 3),
        ;
        int cup, teapot, teapotWithLeaves,
                potOfTea, // (4)
                cupOfTea,
                milkyTea,
                boost;

        Tea(int cup, int teapot, int teapotWithLeaves, int potOfTea, int cupOfTea, int milkyTea, int boost) {
            this.cup = cup;
            this.teapot = teapot;
            this.teapotWithLeaves = teapotWithLeaves;
            this.potOfTea = potOfTea;
            this.cupOfTea = cupOfTea;
            this.milkyTea = milkyTea;
            this.boost = boost;
        }
    }

    static { // making tea stuff
        //boiling kettle
        for (int i = 1; i < Hotspot.STOVE.getBuildables().length; i++) { // start at 1 because basic stove cant support kettle
            int stove = Hotspot.STOVE.getBuildables()[i].getBuiltObjects()[0];
            ItemObjectAction.register(7690, stove, (player, item, obj) -> {
                player.startEvent(event -> {
                    player.lock();
                    item.remove();
                    player.animate(832);
                    obj.setId(stove + 1);
                    event.delay(2);
                    player.sendMessage("The kettle boils.");
                    event.delay(1);
                    player.animate(832);
                    obj.setId(stove);
                    player.getInventory().add(7691, 1);
                    player.unlock();
                });
            });
        }
        for (Tea tea : Tea.values()) {
            // teapot + leaves
            ItemItemAction.register(7738, tea.teapot, (player, primary, secondary) -> {
                primary.remove();
                secondary.setId(tea.teapotWithLeaves);
            });
            // hot kettle + teapot with leaves
            ItemItemAction.register(7691, tea.teapotWithLeaves, (player, primary, secondary) -> {
                if (!player.getStats().check(StatType.Cooking, 20, "make tea")) {
                    return;
                }
                primary.setId(7688);
                secondary.setId(tea.potOfTea);
                player.getStats().addXp(StatType.Cooking, 25, true);
            });
            // pot of tea + cup
            for (int id = tea.potOfTea; id <= tea.potOfTea + 6; id += 2) {
                int potOfTea = id;
                ItemItemAction.register(id, tea.cup, (player, primary, secondary) -> {
                    primary.setId(potOfTea == tea.potOfTea +  6 ? tea.teapot : potOfTea + 2); // dose down or empty
                    secondary.setId(tea.cupOfTea);
                });
            }
            // cup of tea + milk
            ItemItemAction.register(tea.cupOfTea, 1927, (player, primary, secondary) -> {
                primary.setId(tea.milkyTea);
                if (player.discardBuckets)
                    secondary.remove();
                else
                    secondary.setId(1925);
            });
            // drinking
            ItemAction drink = (player, item) -> {
                item.setId(tea.cup);
                Consumable.animEat(player);
                player.getStats().get(StatType.Construction).boost(tea.boost, 0);
            };
            ItemAction.registerInventory(tea.cupOfTea, "drink", drink);
            ItemAction.registerInventory(tea.milkyTea, "drink", drink);
            //Emptying
            ItemAction empty = (player, item) -> item.setId(tea.cup);
            ItemAction.registerInventory(tea.cupOfTea, "empty", empty);
            ItemAction.registerInventory(tea.milkyTea, "empty", empty);
        }
    }

    enum Barrel {
        BEER(Buildable.BEER_BARREL, 7740),
        CIDER(Buildable.CIDER_BARREL, 7752),
        ASGARNIAN_ALE(Buildable.ASGARNIAN_ALE_BARREL, 7744),
        GREENMANS_ALE(Buildable.GREENMANS_ALE_BARREL, 7746),
        DRAGON_BITTER(Buildable.DRAGON_BITTER_BARREL, 7748),
        CHEFS_DELIGHT(Buildable.CHEFS_DELIGHT_BARREL, 7754),
        ;

        Buildable buildable;
        int ale;

        Barrel(Buildable buildable, int ale) {
            this.buildable = buildable;
            this.ale = ale;
        }
    }

    static { // Beer things
        for (Barrel barrel : Barrel.values()) {
            int barrelId = barrel.buildable.getBuiltObjects()[0];
            ItemObjectAction.register(7742, barrelId, (player, item, obj) -> {
                player.startEvent(event -> {
                   player.lock();
                   player.animate(3660);
                   event.delay(1);
                   item.setId(barrel.ale);
                   player.unlock();
                });
            });
        }
        ItemAction.registerInventory(Barrel.BEER.ale, "drink", (player, item) -> drinkAle(player, item, p -> {
            p.incrementHp(1);
            p.getStats().get(StatType.Strength).boost(0, 0.04);
            p.getStats().get(StatType.Attack).drain(0.07);
        }));
        ItemAction.registerInventory(Barrel.CIDER.ale, "drink", (player, item) -> drinkAle(player, item, p -> {
            p.incrementHp(2);
            p.getStats().get(StatType.Strength).drain(2);
            p.getStats().get(StatType.Attack).drain(2);
            p.getStats().get(StatType.Farming).boost(1, 0);
        }));
        ItemAction.registerInventory(Barrel.ASGARNIAN_ALE.ale, "drink", (player, item) -> drinkAle(player, item, p -> {
            p.incrementHp(2);
            p.getStats().get(StatType.Strength).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(4);
        }));
        ItemAction.registerInventory(Barrel.GREENMANS_ALE.ale, "drink", (player, item) -> drinkAle(player, item, p -> {
            p.incrementHp(2);
            p.getStats().get(StatType.Strength).drain(9);
            p.getStats().get(StatType.Attack).drain(9);
            p.getStats().get(StatType.Defence).drain(9);
            p.getStats().get(StatType.Herblore).boost(1, 0);
        }));
        ItemAction.registerInventory(Barrel.DRAGON_BITTER.ale, "drink", (player, item) -> drinkAle(player, item, p -> {
            p.incrementHp(1);
            p.getStats().get(StatType.Strength).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(4);
        }));
        ItemAction.registerInventory(Barrel.CHEFS_DELIGHT.ale, "drink", (player, item) -> drinkAle(player, item, p -> {
            p.incrementHp(1);
            p.getStats().get(StatType.Strength).drain(2);
            p.getStats().get(StatType.Attack).drain(4);
            p.getStats().get(StatType.Cooking).boost(1, 0.05);
        }));

    }
    private static void drinkAle(Player player, Item ale, Consumer<Player> effect) {
        if(player.isLocked() || player.isStunned())
            return;
        if(player.potDelay.isDelayed() || player.karamDelay.isDelayed())
            return;
        player.animate(829);
        player.privateSound(2401);
        player.resetActions(true, player.getMovement().following != null, true);
        ale.setId(7742);
        effect.accept(player);
        player.potDelay.delay(3);
    }
}
