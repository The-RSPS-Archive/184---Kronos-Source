package io.ruin.model.skills.runecrafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;

public class DenseEssence {

    private static final int DARK_ESSENCE_FRAGMENTS = 7938;
    private static final int DARK_ESSENCE_BLOCK = 13446;

    static {
        /**
         * Crafting dark essence block into fragments
         */
        ItemItemAction.register(DARK_ESSENCE_BLOCK, Tool.CHISEL, (player, darkEssenceBlock, chisel) -> {
            boolean addFragments = false;
            if (!player.getInventory().hasId(DARK_ESSENCE_FRAGMENTS)) {
                player.darkEssFragments = 0;
                addFragments = true;
            } else if (player.darkEssFragments >= 111) {
                player.dialogue(new ItemDialogue().one(DARK_ESSENCE_FRAGMENTS, "Your pile of fragments cannot grow any larger."));
                return;
            }
            player.animate(7202);
            player.getInventory().remove(13446, 1);
            if (addFragments)
                player.getInventory().add(DARK_ESSENCE_FRAGMENTS, 1);
            player.getStats().addXp(StatType.Crafting, 8.0, true);
            player.darkEssFragments += 4;
        });

        /**
         * Dark essence fragments item options
         */
        ItemAction.registerInventory(DARK_ESSENCE_FRAGMENTS, actions -> {
            actions[1] = (player, item) -> player.sendMessage("There are " + player.darkEssFragments + " essence fragements in this pile.");
            actions[5] = (player, item) -> {
                player.dialogue(
                        new YesNoDialogue("Are you sure you want to do this?","If you select yes, your dark essence fragments will be destroyed.", item, () -> {
                            item.remove();
                            player.darkEssFragments = 0;
                        })
                );
            };
        });

        /**
         * Dark altar
         */
        ObjectAction.register(27979, "venerate", (player, obj) -> {
            ArrayList<Item> denseBlocks = player.getInventory().collectItems(13445);
            if (denseBlocks == null) {
                player.sendMessage("You don't have any dense essence blocks to venerate.");
                return;
            }
            for (Item denseBlock : denseBlocks)
                denseBlock.setId(13446);
            int blockCount = denseBlocks.size();
            player.getStats().addXp(StatType.Runecrafting, blockCount * 2.5, true);
            player.getPrayer().drain(blockCount);
            player.animate(645);
        });

        /**
         * North & East shortcuts
         */
        ObjectAction.register(27984, "climb", (player, obj) -> {
            Position westShortcut = new Position(1742, 3854, 0);
            if(player.getPosition().equals(westShortcut))
                climb(player, 52, 2, 0, 6, 6, Direction.EAST);
            else
                climb(player, 49, 0, -3, Direction.SOUTH);
        });
        ObjectAction.register(27985, "climb", (player, obj) -> {
            Position westShortcut = new Position(1752, 3854, 0);
            if(player.getPosition().equals(westShortcut))
                climb(player, 52, -2, 0, -6, 6, Direction.WEST);
            else
                climb(player, 49, 0, 3, Direction.NORTH);
        });
        ObjectAction.register(34741, "climb", (player, obj) -> {
            Position northShortcut = new Position(1761, 3874, 0);
            if(player.getPosition().equals(northShortcut))
                climb(player, 69, 0, -2, Direction.SOUTH);
            else
                climb(player, 69, 0, 2, Direction.NORTH);
        });

        /**
         * West shortcuts
         */
        ObjectAction.register(27988, "climb", (player, obj) -> {
            climb(player, 52, 2, 0, 1, 2, Direction.EAST);
        });
        ObjectAction.register(27987, "climb", (player, obj) -> {
            climb(player, 52, -2, 0, -1, 2, Direction.WEST);
        });
    }

    private static void climb(Player player, int levelReq, int diffX, int diffY, Direction direction) {
        if (!player.getStats().check(StatType.Agility, levelReq, "use this shortcut"))
            return;
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839, 26);
            player.getMovement().force(diffX, diffY, 0, 0, 26, 90, direction);
            event.delay(2);
            player.unlock();
        });
    }

    private static void climb(Player player, int levelReq, int diffX, int diffY, int length, int delay, Direction direction) {
        if (!player.getStats().check(StatType.Agility, levelReq, "use this shortcut"))
            return;
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839, 26);
            player.getMovement().force(diffX, diffY, 0, 0, 26, 90, direction);
            event.delay(2);
            player.step(length, 0, StepType.FORCE_WALK);
            event.delay(delay);
            player.animate(839);
            player.getMovement().force(diffX, diffY, 0, 0, 26, 90, direction);
            event.delay(2);
            player.unlock();
        });
    }

}
