package io.ruin.model.inter.journal.main;

import io.ruin.Server;
import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.TimeUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Uptime extends JournalEntry {

    public static final Uptime INSTANCE = new Uptime();

    @Override
    public void send(Player player) {
        send(player, "Uptime", TimeUtils.fromMs(Server.currentTick() * Server.tickMs(), false), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        if(player.isAdmin()) {
            player.sendMessage("Loading server information...");
            CompletableFuture.runAsync(() -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("Players", realPlayers());
                map.put("skip1", "");
                map.put("Cycles", NumberUtils.formatNumber(Server.worker.getExecutions()));
                map.put("Slow (Lag) Cycles", NumberUtils.formatNumber(Server.worker.getSlowExecutions()));
                map.put("Last Cycle", NumberUtils.formatNumber(Server.worker.getLastExecutionTime()) + " ms");
                map.put("Slowest Cycle", NumberUtils.formatNumber(Server.worker.getSlowestExecutionTime()) + " ms");
                map.put("skip2", "");

                Runtime runtime = Runtime.getRuntime();
                long memory = runtime.totalMemory() - runtime.freeMemory();
                map.put("Memory Usage", NumberUtils.formatNumber(memory) + " bytes");
                map.put("skip3", "");

                OperatingSystemMXBean mxBean = ManagementFactory.getOperatingSystemMXBean();

                //Returns the amount of virtual memory that is guaranteed to be available to the running process in bytes, or -1 if this operation is not supported.
                toMap(map, mxBean, "getCommittedVirtualMemorySize");

                //Returns the amount of free physical memory in bytes.
                toMap(map, mxBean, "getFreePhysicalMemorySize");

                //Returns the amount of free swap space in bytes.
                toMap(map, mxBean, "getFreeSwapSpaceSize");

                //Returns the "recent cpu usage" for the Java Virtual Machine process.
                toMap(map, mxBean, "getProcessCpuLoad");

                //Returns the CPU time used by the process on which the Java virtual machine is running in nanoseconds.
                toMap(map, mxBean, "getProcessCpuTime");

                //Returns the "recent cpu usage" for the whole system.
                toMap(map, mxBean, "getSystemCpuLoad");

                //Returns the total amount of physical memory in bytes.
                toMap(map, mxBean, "getTotalPhysicalMemorySize");

                //Returns the total amount of swap space in bytes.
                toMap(map, mxBean, "getTotalSwapSpaceSize");

                List<String> lines = new ArrayList<>();
                map.forEach((s, o) -> {
                    if(s.startsWith("skip"))
                        lines.add("");
                    else
                        lines.add("<col=000080>" + s + ": <col=800000>" + o);
                });
                String[] linesArray = lines.toArray(new String[lines.size()]);
                Server.worker.execute(() -> player.sendScroll("<col=800000>" + World.type.getWorldName() + " Process Information", linesArray));
            });
        }
    }

    private static int realPlayers() {
        int players = 0;
        for(Player p : World.players) {
            if (p.getChannel().id() != null)
                players++;
        }
        return players;
    }


    private static void toMap(Map<String, Object> map, OperatingSystemMXBean mxBean, String methodName) {
        for(Method method : mxBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if(method.getName().equals(methodName)) {
                try {
                    Object value = method.invoke(mxBean);
                    try {
                        long l = Long.valueOf(value+"");
                        value = NumberUtils.formatNumber(l);
                    } catch(NumberFormatException e) {
                        /* ignore */
                    }
                    map.put(methodName, value);
                } catch(Exception e) {
                    ServerWrapper.logError("Failed mapping: " + methodName, e);
                }
            }
        }
    }

}