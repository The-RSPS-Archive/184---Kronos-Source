package io.ruin.model.skills.slayer;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.combat.RangedAmmo;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Slayer {

    static {
        ItemDef.cached.values().stream().filter(def -> def.name.toLowerCase().contains("slayer helm")).forEach(def -> def.slayerHelm = true);
    }

    public static void reset(Player player) {
        player.slayerTask = null;
        player.slayerTaskName = null;
        player.slayerTaskRemaining = 0;
        player.slayerTaskDangerous = false;
        player.slayerTaskAmountAssigned = 0;
    }

    public static void test(Player player) {
        List<SlayerTask> tasks = getPossibleTasks(player, SlayerTask.Type.HARD, false);
        tasks.forEach(it -> {
            player.sendMessage(it.name + " " + it.level);
        });
    }

    private static List<SlayerTask> getPossibleTasks(Player player, SlayerTask.Type type, Boolean preferWilderness) {
        return Arrays.stream(type.tasks)
                .filter(task -> player.getStats().get(StatType.Slayer).fixedLevel >= task.level) // has level
                .filter(task -> !task.disable) // task not disabled
                .filter(task -> task.additionalRequirement == null || task.additionalRequirement.test(player)) // has additional req
                .filter(task -> task.unlockConfig == null || task.unlockConfig.get(player) == 1) // unlocked (if required)
                .filter(task -> preferWilderness ? task.wildernessSpawns > 0 : task.mainSpawns > 0) // has relevant spawns
                .filter(task -> !isBlockedTask(player, task))
                .collect(Collectors.toList());
    }

    public static void set(Player player, SlayerTask.Type type, Boolean preferWilderness) {
        List<SlayerTask> tasks = getPossibleTasks(player, type, preferWilderness);
        int totalWeight = tasks.stream().mapToInt(task -> task.weight).sum();
        SlayerTask task = null;
        int roll = Random.get(totalWeight);
        for (SlayerTask t : tasks) {
            roll -= t.weight;
            if (roll <= 0) {
                task = t;
                break;
            }
        }
        if (task == null) { // should never happen, but just in case..
            task = Random.get(tasks);
        }
        player.slayerTask = task;
        player.slayerTaskName = task.name;
        int taskAmount = task.type[0] == SlayerTask.Type.BOSS ? -1 : Random.get(task.min, task.max);
        player.slayerTaskRemaining = taskAmount;
        player.slayerTaskAmountAssigned = taskAmount;
        if (task.extensionConfig != null && task.extensionConfig.get(player) == 1)
            player.slayerTaskRemaining *= 1.35;
        if(preferWilderness)
            player.slayerTaskDangerous = true;
    }

    public static SlayerTask getTask(Player player) {
        if(player.slayerTask != null)
            return player.slayerTask;
        if(player.slayerTaskName == null)
            return null;
        SlayerTask task = SlayerTask.TASKS.get(player.slayerTaskName);
        if(task == null) {
            reset(player);
            return null;
        }
        return player.slayerTask = task;
    }

    public static boolean hasSlayerHelmEquipped(Player player) {
        ItemDef def = player.getEquipment().getDef(Equipment.SLOT_HAT);
        return def != null && def.slayerHelm;
    }

    private static double combatLevelDropRate(NPC npc) {
        NPCDef def = npc.getDef();
        if (def.combatLevel >= 500) {
            return 0.80;
        } else if (def.combatLevel >= 300) {
            return 0.85;
        } else if (def.combatLevel >= 200) {
            return 0.90;
        } else if (def.combatLevel >= 100) {
            return 0.95;
        }
        return 1;
    }

    public static void onNPCKill(Player player, NPC npc) {
        if (isTask(player, npc)) {
            player.slayerTaskRemaining--;
            player.getStats().addXp(StatType.Slayer, npc.getCombat().getInfo().slayer_xp, true);
            if (Wilderness.getLevel(npc.getPosition()) > 0) {
                npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
                    if (isTask(player, npc)) {
                        if (Random.get(1, 40) == 1) {
                            new GroundItem(23490, 1).owner(killer.player).position(npc.getPosition()).spawn();
                        }
                        if (Random.rollDie((int) Math.ceil(combatLevelDropRate(npc) * 500), 1)) {
                            new GroundItem(12746, 1).owner(killer.player).position(npc.getPosition()).spawn(); //Drops the first emblem
                        }
                        if (Random.rollDie((int) Math.ceil(combatLevelDropRate(npc) * 40), 1)) { //40-47
                            new GroundItem(30104, 1).owner(killer.player).position(npc.getPosition()).spawn();
                        }
                    }
                };
            }
            if (player.slayerTaskRemaining <= 0) {
                finishTask(player);
            }
        }
    }

    public static boolean isTask(Player player, NPC npc) {
        SlayerTask playerTask = getTask(player);
        if (playerTask == null || npc.getCombat().getInfo().slayerTasks == null) {
            return false;
        }
        for (SlayerTask task : npc.getCombat().getInfo().slayerTasks) {
            if (task == playerTask)
                return true;
        }
        return false;
    }

    private static void finishTask(Player player) {
        SlayerTask task = getTask(player);
        if (player.slayerTaskDangerous)
            PlayerCounter.WILDERNESS_SLAYER_TASKS_COMPLETED.increment(player, 1);
        else
            PlayerCounter.SLAYER_TASKS_COMPLETED.increment(player, 1);
        int reward = getPointsReward(task, player.slayerTaskDangerous ? player.wildernessTasksCompleted : player.slayerTasksCompleted);
        reward *= task.getHighestType().modifier; // TODO fix this
        if (World.doubleSlayer)
            reward *= 2;
        player.sendMessage("Your slayer task is now complete.");
        Config.SLAYER_POINTS.set(player, reward + Config.SLAYER_POINTS.get(player));
        if (player.slayerTaskDangerous) {
            player.wildernessSlayerPoints += reward;
            player.wildernessTasksCompleted++;
            player.sendMessage("You've completed a total of " + PlayerCounter.WILDERNESS_SLAYER_TASKS_COMPLETED.get(player) + " wilderness tasks, earning " + reward + " wilderness points," +
                    " You now have a total of " + player.wildernessSlayerPoints + " points.");
        } else {
            player.sendMessage("You've completed a total of " + PlayerCounter.SLAYER_TASKS_COMPLETED.get(player) + " tasks, earning " + reward + " points" +
                    ". You now have a total of " + Config.SLAYER_POINTS.get(player) + " points.");
        }
        reset(player);
    }

    public static int getPointsReward(SlayerTask task, int tasks) {
        int base = 30;
        if (tasks % 1000 == 0)
            return base * 50;
        else if (tasks % 250 == 0)
            return base * 35;
        else if (tasks % 100 == 0)
            return base * 25;
        else if (tasks % 50 == 0)
            return base * 15;
        else if (tasks % 10 == 0)
            return base * 5;
        return base;
    }


    static void sendTaskInfo(Player player) {
        SlayerTask task = getTask(player);
        if(task != null) {
            if(task.type[0] == SlayerTask.Type.BOSS) {
                Config.SLAYER_TASK_1.set(player, 98);
                Config.SLAYER_TASK_2.set(player, task.key);
            } else {
                Config.SLAYER_TASK_1.set(player, task.key);
            }
        }
        Config.SLAYER_TASK_AMOUNT.set(player, player.slayerTaskRemaining);
    }

    public static void sendRewardInfo(Player player) {
        Config.UNLOCK_BLOCK_TASK_SIX.set(player, 1);
        if(player.slayerBlockedTasks != null) {
            for(int i = 0; i < player.slayerBlockedTasks.size(); i++) {
                SlayerTask blocked = SlayerTask.TASKS.get(player.slayerBlockedTasks.get(i));
                Config.BLOCKED_TASKS[i].set(player, blocked != null ? blocked.key : 0);
            }
        }
    }

    public static boolean isBlockedTask(Player player, SlayerTask task) {
        if(player.slayerBlockedTasks != null) {
            for(int i = 0; i < player.slayerBlockedTasks.size(); i++) {
                SlayerTask blocked = SlayerTask.TASKS.get(player.slayerBlockedTasks.get(i));
                if(blocked == task) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void check(Player player) {
        SlayerTask task = Slayer.getTask(player);
        if (task == null) {
            player.sendMessage("You do not currently have a slayer assignment. Talk to Krystilia in Edgeville to receive one.");
        } else {
            player.sendMessage("Your current slayer assignment is " + task.name + ". Only " + player.slayerTaskRemaining + " left to go.");
        }
    }
}
