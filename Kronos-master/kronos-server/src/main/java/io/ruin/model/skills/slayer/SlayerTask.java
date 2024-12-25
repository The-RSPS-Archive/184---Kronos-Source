package io.ruin.model.skills.slayer;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.teleports;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;

import java.util.*;
import java.util.function.Predicate;

public class SlayerTask {

    public static Map<String, SlayerTask> TASKS;

    public static SlayerTask JAD_TASK, ZUK_TASK;

    public static int BOSS_KEY;

    public int key;

    public String name;

    @Expose public Type[] type;

    @Expose public int level;

    @Expose public int min, max;

    @Expose public teleports.Teleport[] teleports;

    @Expose public boolean disable;

    @Expose public String extension;

    public Config extensionConfig;

    @Expose public String unlock;

    public Config unlockConfig;

    @Expose public int weight = 1;



    public Predicate<Player> additionalRequirement;

    public int mainSpawns, wildernessSpawns;

    private int highestCombatLevel;


    static {
        Server.afterData.add(() -> {
            SpawnListener.forEach(npc -> {
                if(npc.getCombat() == null)
                    return;
                String[] slayerTasks = npc.getCombat().getInfo().slayer_tasks;
                if(slayerTasks == null)
                    return;
                for(String s : slayerTasks) {
                    SlayerTask task = TASKS.get(s);
                    if(task == null) {
                        System.err.println("Npc (" + npc.getId() + ") has invalid slayer task: \"" + s + "\"");
                        continue;
                    }
                    if(Wilderness.getLevel(npc.getPosition()) > 0)
                        task.wildernessSpawns++;
                    else
                        task.mainSpawns++;
                    task.level = npc.getCombat().getInfo().slayer_level;
                    task.highestCombatLevel = Math.max(task.highestCombatLevel, npc.getDef().combatLevel);
                }
            });
            TASKS.values().forEach(task -> {
                if(task.name.equals("TzTok-Jad")) {
                    JAD_TASK = task;
                    return;
                }
                if(task.name.equals("TzKal-Zuk")) {
                    ZUK_TASK = task;
                    return;
                }
                if (task.name.equals("Basilisks") || task.name.equals("Cockatrice")) {
                    task.additionalRequirement = p -> p.getStats().get(StatType.Defence).fixedLevel >= 20; // so can equip mirror shield
                }
                if (task.extension != null && task.extension.length() > 0) {
                    try {
                        task.extensionConfig = (Config) Config.class.getField(task.extension).get(null);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        ServerWrapper.logError("Extension config does not exist for: " + task.name, e);
                    }
                }
                if (task.unlock != null && task.unlock.length() > 0) {
                    try {
                        task.unlockConfig = (Config) Config.class.getField(task.unlock).get(null);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        ServerWrapper.logError("Unlock config does not exist for: " + task.name, e);
                    }
                }
                if(task.mainSpawns == 0 && task.wildernessSpawns == 0 && !task.name.equals("Barrows Brothers")) { // barrows brothers arent spawned in the world by default...
                    //ServerWrapper.logWarning("Slayer task \"" + task.name + "\" has 0 npcs!");
                    return;
                }
                int defaultMin, defaultMax;
                Type highest = Arrays.stream(task.type).min(Comparator.comparing(Type::ordinal)).orElse(null);
                if(highest == Type.EASY) {
                    defaultMin = 10;
                    defaultMax = 25;
                } else if(highest == Type.MEDIUM) {
                    defaultMin = 20;
                    defaultMax = 50;
                } else if(highest == Type.HARD) {
                    defaultMin = 50;
                    defaultMax = 115;
                } else {
                    defaultMin = 1;
                    defaultMax = 35;
                }
                if(task.min == 0)
                    task.min = defaultMin;
                if(task.max == 0)
                    task.max = defaultMax;
                for (Type t : task.type)
                    t.addTask(task);
            });
            NPCDef.cached.values().stream()
                    .filter(Objects::nonNull)
                    .map(def -> def.combatInfo)
                    .filter(Objects::nonNull)
                    .forEach(info -> {
                        if (info.slayer_tasks != null && info.slayer_tasks.length > 0) {
                            info.slayerTasks = new SlayerTask[info.slayer_tasks.length];
                            for (int i = 0; i < info.slayer_tasks.length; i++) {
                                info.slayerTasks[i] = SlayerTask.find(info.slayer_tasks[i]);
                            }
                        }
                    });
        });
    }

    public Type getHighestType() {
        Type highest = type[0];
        for (int i = 1; i < type.length; i++) {
            if (type[i].ordinal() > highest.ordinal())
                highest = type[i];
        }
        return highest;
    }

    public static SlayerTask find(String taskName) {
        for (Type type : Type.values()) {
            for (SlayerTask task : type.tasks) {
                if (task.name.equalsIgnoreCase(taskName))
                    return task;
            }
        }
        return null;
    }

    public enum Type {

        EASY(5, 1.0/3, 3),
        MEDIUM(15, 2.0/3, 60),
        HARD(25, 1.0, 80),
        BOSS(100, 1.0, 100);

        private final int basePoints;

        public final double modifier;

        public final int minimumCombatLevel;

        protected SlayerTask[] tasks;

        Type(int basePoints, double modifier, int minimumCombatLevel) {
            this.basePoints = basePoints;
            this.modifier = modifier;
            this.minimumCombatLevel = minimumCombatLevel;
        }

        private void addTask(SlayerTask task) {
            List<SlayerTask> newTasks = new ArrayList<>();
            if(tasks != null)
                Collections.addAll(newTasks, tasks);
            newTasks.add(task);
            tasks = newTasks.toArray(new SlayerTask[0]);
        }

        public int getPoints(int killed) {
            return basePoints + (int) (killed * modifier);
        }

    }

}
