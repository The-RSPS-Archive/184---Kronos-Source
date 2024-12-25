package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.wilderness.WildernessObelisk;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.FairyRing;
import io.ruin.model.map.object.actions.impl.SpiritTree;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

import static io.ruin.model.map.object.actions.impl.FairyRing.ZANARIS;
import static io.ruin.model.skills.construction.Buildable.*;

public class SuperiorGarden {

    static { // pools
        List<Buildable> pools = Arrays.asList(RESTORATION_POOL, REVITALISATION_POOL, REJUVENATION_POOL, FANCY_REJUVENATION_POOL, ORNATE_REJUVENATION_POOL);
        for (int i = 0; i < pools.size(); i++) {
            int poolLevel = i;
            ObjectAction.register(pools.get(i).getBuiltObjects()[0], "drink", (player, obj) -> drinkFromPool(player, poolLevel));
        }
    }

    private static void drinkFromPool(Player player, int poolLevel) {
        player.animate(833);
        Config.SPECIAL_ENERGY.set(player, 1000);
        if (poolLevel >= 1) {
            player.getMovement().restoreEnergy(100);
        }
        if (poolLevel >= 2) {
            player.getStats().get(StatType.Prayer).restore();
        }
        if (poolLevel >= 3) {
            for (Stat stat: player.getStats().get()) {
                if (stat != player.getStats().get(StatType.Hitpoints) && stat.currentLevel < stat.fixedLevel)
                    stat.alter(stat.fixedLevel);
            }
        }
        if (poolLevel >= 4) {
            player.setHp(player.getMaxHp());
        }
        player.sendFilteredMessage("You drink from the pool. It makes you feel replenished.");
    }

    static { // fairy rings and spirit trees. only the tree + fairy ring object is handled here, the one thats just a fairy ring is handled in FairyRing class because it works the same way as the non-construction ones
        ObjectAction.register(SPIRIT_TREE.getBuiltObjects()[0], 1, (player, obj) -> SpiritTree.open(player));
        int ringAndTree = SPIRIT_TREE_AND_FAIRY_RING.getBuiltObjects()[0];
        ObjectAction.register(ringAndTree, 1, (player, obj) -> SpiritTree.open(player));
        ObjectAction.register(ringAndTree, 2, (player, obj) -> FairyRing.teleport(player, ZANARIS, obj));
        ObjectAction.register(ringAndTree, 3, FairyRing::openCombinationPanel);
        ObjectAction.register(ringAndTree, 4, (player, obj) -> {
            for (FairyRing fairyRing : FairyRing.values()) {
                if(fairyRing.lastDestination == Config.FAIRY_RING_LAST_DESTINATION.get(player))
                    FairyRing.teleport(player, fairyRing, obj);
            }
        });
    }

    static { // obelisk
        int obelisk = OBELISK.getBuiltObjects()[0];
        ObjectAction.register(obelisk, "activate", (player, obj) -> {
            player.dialogue(new MessageDialogue("<col=ff0000>WARNING:</col> Activating the obelisk will teleport you deep into the wilderness. Are you sure you want to do this?"),
                    new OptionsDialogue("Teleport to Wilderness",
                            new Option("Yes - I am fearless!", () -> WildernessObelisk.teleport(player, Random.get(WildernessObelisk.values()))),
                            new Option("No")));
        });
        ObjectAction.register(obelisk, "set destination", (player, obj) -> WildernessObelisk.chooseDestination(player));
        ObjectAction.register(obelisk, "teleport to destination", (player, obj) -> {
            if(player.obeliskRedirectionScroll || player.getEquipment().getId(Equipment.SLOT_WEAPON) == 13111) {
                if(player.obeliskDestination == null)
                    player.sendFilteredMessage("You need to set a destination before attempting to do this.");
                else
                    WildernessObelisk.teleport(player, player.obeliskDestination);
                return;
            }
            player.sendFilteredMessage("You need learn how to set an obelisk destination before attempting this.");
        });

    }

}
