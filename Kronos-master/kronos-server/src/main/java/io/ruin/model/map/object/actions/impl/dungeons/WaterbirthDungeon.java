package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.skills.slayer.Slayer;

public class WaterbirthDungeon {
    static {
        SpawnListener.register(new String[]{"dagannoth rex", "dagannoth prime", "dagannoth supreme"}, npc -> {
            if (npc.getPosition().getRegion().id == 11588) {
                npc.attackNpcListener = (player, npc1, message) -> {
                    if (!Slayer.isTask(player, npc1)) {
                        if (message)
                            player.sendMessage(Color.TOMATO.wrap("You may only attack the Kings in this lair if you are on relevant slayer task."));
                        return false;
                    }
                    return true;
                };
            }
        });


        Tile.get(2543, 10143, 0, false).clipping = 0;
        Tile.get(2545, 10141, 0, false).clipping = 0;
        Tile.get(2545, 10145, 0, false).clipping = 0;

        ObjectAction.register(10230, 1, (player, object) ->  {
            climb(player, 2900, 4449, 0, false);
        }); // ladder to kings

        ObjectAction.register(10230, 2, (player, object) ->  {
            climb(player, 2900, 4385, 0, false);
        }); // ladder to kings, slayer-only lair

        ObjectAction peekAction = (player, obj) -> {
            int regularCount = Region.get(11589).players.size();
            int slayerCount = Region.get(11588).players.size();
            if (regularCount == 0)
                player.sendMessage("It doesn't look like there's anyone down in the regular lair.");
            else
                player.sendMessage("It looks like there " + (regularCount > 1 ? "are" : "is") + " " + regularCount + " adventurer" + (regularCount > 1 ? "s" : "") + " in the regular lair.");

            if (slayerCount == 0)
                player.sendMessage("It doesn't look like there's anyone down in the slayer-only lair.");
            else
                player.sendMessage("It looks like there " + (slayerCount > 1 ? "are" : "is") + " " + slayerCount + " adventurer" + (slayerCount > 1 ? "s" : "") + " in the regular lair.");
        };
        ObjectAction.register(10230, 4, peekAction);
        ObjectAction.register(30169, 1, (player, obj) -> InstanceDialogue.open(player, InstanceType.DAGANNOTH_KINGS));
        ObjectAction.register(30169, 2, peekAction);
        ObjectAction.register(30170, 1, (player, obj) -> {
            player.step(0, player.getAbsY() > obj.y ? -2 : 2, StepType.FORCE_WALK);
        });


        ObjectAction.register(10194, 1, (player, object) -> { // ladder at the very end of the dungeon
            climb(player, 2523, 3739, 0, true);
        });
        ObjectAction.register(8966, 1, (player, object) -> { // stairs at the entrance
            player.getMovement().teleport(2523,  3739, 0);
        });
        ObjectAction.register(8929, 1, (player, object) -> { // entrance to the dungeon
            player.getMovement().teleport(2442, 10146, 0);
        });
        ObjectAction.register(8930, 1, (player, object) -> { // little cave entrance at the top of the hill
            player.getMovement().teleport(2545, 10143, 0);
        });
        ObjectAction twoPersonDoorAction = (player, object) -> { // doors that would usually require 2 ppl
            object.setId(8963);
            World.startEvent(event -> {
                event.delay(50);
                object.setId(object.originalId);
            });
        };
        ObjectAction.register(8958, 1, twoPersonDoorAction);
        ObjectAction.register(8959, 1, twoPersonDoorAction);
        ObjectAction.register(8960, 1, twoPersonDoorAction);
        ObjectAction.register(10177, 1, (player, object) -> { // ladder down to sublevel 2
            climb(player, 1799, 4407, 3, false);
        });
        ObjectAction.register(10177, 2, (player, object) -> { // ladder up to top of hill
            climb(player, 2544, 3741, 0,true);
        });
        ObjectAction.register(10177, 3, (player, object) -> { // ladder down to sublevel 2
            climb(player, 1799, 4407, 3, false);
        });
        ObjectAction.register(10193, 1, (player, object) -> { // ladder back to sublevel 1
            climb(player, 2545, 10143, 0, true);
        });
        ObjectAction.register(10195, 1, (player, object) -> climb(player, 1810, 4405, 2, false));
        ObjectAction.register(10196, 1, (player, object) -> climb(player, 1807, 4405, 3, true));
        ObjectAction.register(10197, 1, (player, object) -> climb(player, 1822, 4404, 2, false));
        ObjectAction.register(10198, 1, (player, object) -> climb(player, 1825, 4404, 3, true));
        ObjectAction.register(10199, 1, (player, object) -> climb(player, 1834, 4388, 2, false));
        ObjectAction.register(10200, 1, (player, object) -> climb(player, 1834, 4390, 3, true));
        ObjectAction.register(10201, 1, (player, object) -> climb(player, 1810, 4394, 1, false));
        ObjectAction.register(10202, 1, (player, object) -> climb(player, 1812, 4394, 2, true));
        ObjectAction.register(10203, 1, (player, object) -> climb(player, 1799, 4386, 2, true));
        ObjectAction.register(10204, 1, (player, object) -> climb(player, 1799, 4389, 1, false));
        ObjectAction.register(10205, 1, (player, object) -> climb(player, 1798, 4382, 1, false));
        ObjectAction.register(10206, 1, (player, object) -> climb(player, 1796, 4382, 2, true));
        ObjectAction.register(10207, 1, (player, object) -> climb(player, 1800, 4369, 2, true));
        ObjectAction.register(10208, 1, (player, object) -> climb(player, 1802, 4369, 1, false));
        ObjectAction.register(10209, 1, (player, object) -> climb(player, 1827, 4362, 1, false));
        ObjectAction.register(10210, 1, (player, object) -> climb(player, 1825, 4362, 2, true));
        ObjectAction.register(10211, 1, (player, object) -> climb(player, 1863, 4373, 2, true));
        ObjectAction.register(10212, 1, (player, object) -> climb(player, 1863, 4371, 1, false));
        ObjectAction.register(10213, 1, (player, object) -> climb(player, 1864, 4389, 1, false));
        ObjectAction.register(10214, 1, (player, object) -> climb(player, 1864, 4387, 2, true));
        ObjectAction.register(10215, 1, (player, object) -> climb(player, 1890, 4408, 0, false));
        ObjectAction.register(10216, 1, (player, object) -> climb(player, 1890, 4406, 1, true));
        ObjectAction.register(10217, 1, (player, object) -> climb(player, 1957, 4373, 1, true));
        ObjectAction.register(10218, 1, (player, object) -> climb(player, 1957, 4371, 0, false));
        ObjectAction.register(10219, 1, (player, object) -> climb(player, 1824, 4379, 3, true));
        ObjectAction.register(10220, 1, (player, object) -> climb(player, 1824, 4381, 2, false));
        ObjectAction.register(10221, 1, (player, object) -> climb(player, 1838, 4375, 2, false));
        ObjectAction.register(10222, 1, (player, object) -> climb(player, 1838, 4377, 3, false));
        ObjectAction.register(10223, 1, (player, object) -> climb(player, 1850, 4386, 1, false));
        ObjectAction.register(10224, 1, (player, object) -> climb(player, 1850, 4387, 2, true));
        ObjectAction.register(10225, 1, (player, object) -> climb(player, 1932, 4378, 1, false));
        ObjectAction.register(10226, 1, (player, object) -> climb(player, 1932, 4380, 2, true));
        ObjectAction.register(10227, 1, (player, object) -> climb(player, 1961, 4391, 2, false));
        ObjectAction.register(10228, 1, (player, object) -> climb(player, 1961, 4393, 3, true));
        ObjectAction.register(10229, 1, (player, object) -> climb(player, 1912, 4367, 0, true));
    }


    private static void climb(Player player, int x, int y, int z, boolean up) {
        Ladder.climb(player, x, y, z, up, true, false);
    }
}
