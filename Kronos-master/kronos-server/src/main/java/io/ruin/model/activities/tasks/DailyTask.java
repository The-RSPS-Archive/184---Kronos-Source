package io.ruin.model.activities.tasks;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.listeners.DailyResetListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.model.activities.tasks.DailyTaskDifficulty.*;
import static io.ruin.model.activities.tasks.NPCKillTaskListener.*;
import static io.ruin.model.activities.tasks.PlayerKillTaskListener.*;

public enum DailyTask {
    //PvM tasks
    KILL_CALLISTO(CALLISTO, HARD, "Kill Callisto", "Kill Callisto once.", 1),
    KILL_VETION(VETION, HARD, "Kill Vet'ion", "Kill Vet'ion once.", 1),
    KILL_VENENATIS(VENENATIS, HARD, "Kill Venenatis", "Kill Venenatis once.", 1),
    KILL_SCORPIA(SCORPIA, HARD, "Kill Scorpia", "Kill Scorpia once.", 1),
    KILL_ZULRAH(ZULRAH, HARD, "Kill Zulrah", "Kill Zulrah 5 times.", 5),
    KILL_ABYSSAL_SIRE(ABYSSAL_SIRE, HARD, "Kill Abyssal Sire", "Kill the Abyssal sire 3 times.", 3),
    KILL_DAGANNOTH_KINGS(DAGANNOTH_KINGS, HARD, "Kill Dag. Kings", "Kill any of the Dagannoth Kings 10 times", 10),
    KILL_GWD_GENERALS(GODWARS_GENERALS, HARD, "Kill GWD Generals", "Kill any of the Godwars Dungeon generals 4 times", 4),
    KILL_CERBERUS(CERBERUS, HARD, "Kill Cerberus", "Kill Cerberus twice.", 2),
    KILL_KREE_ARRA(KREE_ARRA, MEDIUM, "Kill Kree'Arra", "Kill Kree'Arra (Armadyl General) once.", 1),
    KILL_GRAARDOR(GENERAL_GRAARDOR, MEDIUM, "Kill Graardor", "Kill General Graardor (Bandos General) once.", 1),
    KILL_ZILYANA(COMMANDER_ZILYANA, MEDIUM, "Kill Cdr. Zilyana", "Kill Commander Zilyana (Saradomin General) once.", 1),
    KILL_KRIL_TSUTSAROTH(KRIL_TSUTSAROTH, MEDIUM, "Kill K'ril", "Kill K'ril Tsutsaroth (Zamorak General) once.", 1),
    KILL_BARROWS_BROTHERS(BARROWS_BROTHERS, MEDIUM, "Kill Barrows Brothers", "Kill any of the barrows brothers 12 times.", 12),
    KILL_KALPHITE_QUEEN(KALPHITE_QUEEN, MEDIUM, "Kill Kalphite Queen", "Kill the Kalphite Queen once.", 1),
    KILL_LIZARDMAN_SHAMAN(LIZARDMAN_SHAMAN, EASY, "Kill Lizardmen Shaman", "Kill 2 Lizardmen Shaman", 2),
    KILL_THERMONUCLEAR_SMOKE_DEVIL(THERMONUCLEAR_SMOKE_DEVIL, EASY, "Kill Thermo Smoke Devil", "Kill the Thermonuclear Smoke Devil 3 times.", 3),
    KILL_BARROWS_BROTHERS_EASY(BARROWS_BROTHERS, EASY, "Kill Barrows Brothers", "Kill any of the barrows brothers 6 times.", 6),
    KILL_KRAKEN(KRAKEN, EASY, "Kill Kraken", "Kill the Kraken 5 times.", 5),
    KILL_REVENANTS_MEDIUM(REVENANTS, MEDIUM, "Kill Revenants", "Kill 15 of any revenant monster.", 15),
    KILL_REVENANTS_HARD(REVENANTS, HARD, "Kill Revenants", "Kill 30 of any revenant monster.", 15),

    //PvP Tasks
    EDGEVILLE_EASY(EDGEVILLE, EASY, "Edgeville Kill", "Kill a player in the Edgeville wilderness.", 1),
    EDGEVILLE_MEDIUM(EDGEVILLE, MEDIUM, "Edgeville Kills", "Get 3 kills in the Edgeville wilderness.", 3),
    EDGEVILLE_HARD(EDGEVILLE, HARD, "Edgeville Kills", "Get 10 kills in the Edgeville wilderness.", 10),

    MAIN_EASY(MAIN, EASY, "Main Kill", "Get a kill as a main (126 combat).", 1),
    MAIN_MEDIUM(MAIN, MEDIUM, "Main Kills", "Get 3 kills as a main (126 combat).", 3),
    MAIN_HARD(MAIN, HARD, "Main Kills", "Get 10 kills as a main (126 combat).", 10),

    PURE_EASY(PURE, EASY, "Pure Kill", "Get a kill as a 1 defence pure.", 1),
    PURE_MEDIUM(PURE, MEDIUM, "Pure Kills", "Get 3 kills as a 1 defence pure.", 3),
    PURE_HARD(PURE, HARD, "Pure Kills", "Get 10 kills as a 1 defence pure.", 10),

    HOTSPOT_EASY(HOTSPOT, EASY, "Hotspot Kill", "Get a kill in the active hotspot.<br>Check the journal tab to see where the active hotspot is.", 1),
    HOTSPOT_MEDIUM(HOTSPOT, MEDIUM, "Hotspot Kills", "Get 3 kills in the active hotspot.<br>Check the journal tab to see where the active hotspot is.", 3),
    HOTSPOT_HARD(HOTSPOT, HARD, "Hotspot Kills", "Get 10 kills in the active hotspot.<br>Check the journal tab to see where the active hotspot is.", 10),

    DEEP_MEDIUM(DEEP_WILD, MEDIUM, "Deep Wild Kill", "Get a kill in level 50+ wilderness.", 1),
    DEEP_HARD(DEEP_WILD, HARD, "Deep Wild Kills", "Get 5 kills in level 50+ wilderness.", 5),

    REVENANT_CAVE_EASY(REV_CAVE, EASY, "Rev Cave Kill", "Kill a player in the revenant cave.", 1),
    REVENANT_CAVE_MEDIUM(REV_CAVE, EASY, "Rev Cave Kills", "Kill 2 players in the revenant cave.", 2),
    REVENANT_CAVE_HARD(REV_CAVE, EASY, "Rev Cave Kills", "Kill 5 players in the revenant cave.", 5),

    ;

    DailyTask(TaskListener listener, DailyTaskDifficulty difficulty, String shortDescription, String longDescription, int amountToKill) {
        this.listener = listener;
        this.difficulty = difficulty;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.amountToKill = amountToKill;
    }

    private final TaskListener listener;
    private final DailyTaskDifficulty difficulty;
    private final String shortDescription;
    private final String longDescription;
    private final int amountToKill;

    public DailyTaskDifficulty difficulty() {
        return difficulty;
    }

    public String shortDescription() {
        return shortDescription;
    }

    public String longDescription() {
        return longDescription;
    }

    public boolean isComplete(int currentProgress) {
        return currentProgress >= amountToKill;
    }


    public TaskListener getListener() {
        return listener;
    }

    public static void checkNPCKill(Player player, NPC npc) {
        for (int i = 0; i < player.dailyTasks.length; i++) {
            DailyTask dailyTask = player.dailyTasks[i];
            int progress = dailyTask.getListener().onNPCKill(player, npc);
            if (progress <= 0)
                continue;
            boolean wasComplete = dailyTask.isComplete(player.dailyTaskProgress[i]);
            player.dailyTaskProgress[i] += progress;
            if (!wasComplete && dailyTask.isComplete(player.dailyTaskProgress[i])) {
                dailyTask.complete(player);
            }
            //update(player, i);
        }
    }

    public static void checkPlayerKill(Player player, Player killed) {
        for (int i = 0; i < player.dailyTasks.length; i++) {
            DailyTask dailyTask = player.dailyTasks[i];
            int progress = dailyTask.getListener().onPlayerKill(player, killed);
            if (progress <= 0)
                continue;
            boolean wasComplete = dailyTask.isComplete(player.dailyTaskProgress[i]);
            player.dailyTaskProgress[i] += progress;
            if (!wasComplete && dailyTask.isComplete(player.dailyTaskProgress[i])) {
                dailyTask.complete(player);
            }
            //update(player, i);
        }
    }

    private void complete(Player player) {
        PlayerCounter.DAILY_TASKS_COMPLETED.increment(player, 1);
        player.getInventory().addOrDrop(BLOOD_MONEY, difficulty().getBmReward());
        player.dailyTaskPoints += difficulty().getPointsReward();
        player.sendMessage(Color.DARK_GREEN.wrap("Congratulations, you have completed the task \"" + shortDescription() + "\"! You receive " + NumberUtils.formatNumber(difficulty().getBmReward()) + " blood money and " + difficulty().getPointsReward() + " task points. You now have a total of " + NumberUtils.formatNumber(player.dailyTaskPoints) + " task points."));
        if (isComplete(player, 0) && isComplete(player, 1) && isComplete(player, 2)) {
            player.sendMessage("You have completed all of today's tasks and receive 6 bonus task points. Visit the guide in Edgeville to spend them!");
            player.dailyTaskPoints += 6;
        } else {
            player.sendMessage("Complete the remaining tasks for the day to receive 6 bonus task points.");
        }
    }

    public static boolean isComplete(Player player, int taskIndex) {
        return player.dailyTasks[taskIndex].isComplete(player.dailyTaskProgress[taskIndex]);
    }

    public static void assignTasks(Player player) {
        LinkedList<DailyTask> pvmTasks = getPvMTasks();
        LinkedList<DailyTask> pvpTasks = getPvPTasks();
        Collections.shuffle(pvmTasks);
        Collections.shuffle(pvpTasks);
        player.dailyTasks = new DailyTask[3];
        DailyTask selected;
        if (!player.getGameMode().isIronMan()) {
            selected = pvpTasks.pop();
        } else {
            selected = pvmTasks.pop();
        }
        player.dailyTasks[0] = selected;
        pvpTasks.removeIf(dt -> dt.getListener() == selected.getListener()); // so we don't get 2 of the same task just different difficulty
        if (!player.getGameMode().isIronMan()) {
            player.dailyTasks[1] = pvpTasks.pop();
        } else {
            player.dailyTasks[1] = pvmTasks.pop();
        }
        if (!player.getGameMode().isIronMan()) {
            player.dailyTasks[2] = pvpTasks.pop();
        } else {
            player.dailyTasks[2] = pvmTasks.pop();
        }
        for (int i = 0; i < 3; i++) {
            player.dailyTaskProgress[i] = 0;
            player.sendMessage(Color.RED.wrap("You have a new daily task: " + player.dailyTasks[i].shortDescription() + " (" + player.dailyTasks[i].amountToKill +")"));
            //update(player, i);
        }
        player.sendMessage(Color.RED.wrap("Click on a task in the Journal tab for more details."));
    }

    private static LinkedList<DailyTask> getPvMTasks() {
        LinkedList<DailyTask> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof PlayerKillTaskListener);
        return list;
    }

    private static LinkedList<DailyTask> getPvPTasks() {
        LinkedList<DailyTask> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof NPCKillTaskListener);
        return list;
    }

//    private static void update(Player player, int taskIndex) {
//        if (player.journal == Journal.MAIN)
//            TaskEntry.ENTRIES[taskIndex].send(player);
//    }

    static {
        DailyResetListener.register(DailyTask::assignTasks);
    }

    public static class TaskEntry extends JournalEntry {

        public static final JournalEntry[] ENTRIES = {
                new TaskEntry(0),
                new TaskEntry(1),
                new TaskEntry(2),
        };
        private int taskIndex;

        public TaskEntry(int taskIndex) {
            this.taskIndex = taskIndex;
        }

        @Override
        public void send(Player player) {
            DailyTask t = player.dailyTasks[taskIndex];
            int progress = player.dailyTaskProgress[taskIndex];
            if (t == null) {
                send(player, "N/A");
            } else {
                send(player, t.shortDescription(), progress + " / " + t.amountToKill, progress == 0 ? Color.RED : (progress < t.amountToKill ? Color.YELLOW : Color.GREEN));
            }
        }

        @Override
        public void select(Player player) {
            DailyTask t = player.dailyTasks[taskIndex];
            if (t == null) {
                player.sendMessage("No task");
            } else {
                t.open(player);
            }
        }
    }

    public static class PointsEntry extends JournalEntry {

        public static final PointsEntry ENTRY = new PointsEntry();

        @Override
        public void send(Player player) {
            send(player, "Task Points", player.dailyTaskPoints, player.dailyTaskPoints < 1 ? Color.RED : Color.GREEN);
        }

        @Override
        public void select(Player player) {
            player.dialogue(new MessageDialogue("Visit the " + Color.COOL_BLUE.wrap(World.type.getWorldName() + " Expert") + " who's located outside the " + Color.COOL_BLUE.wrap("Edgeville bank") + " to spend your task points."));
        }
    }

    private void open(Player player) {
        player.sendScroll("Daily task",
                Color.DARK_RED.wrap("Challenge: ") + longDescription(),
                "",
                Color.GOLD.wrap("Reward: ") + NumberUtils.formatNumber(difficulty().getBmReward()) + " blood money and " + difficulty().getPointsReward() + " task points",
                "",
                "Complete all daily tasks for the day to receive an extra 6 task points!"
        );
    }

}
