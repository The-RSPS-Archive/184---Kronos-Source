package io.ruin.api.process;

import io.ruin.api.utils.SafeRunnable;
import io.ruin.api.utils.ServerWrapper;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProcessWorker {

    private final long period;

    private final ScheduledExecutorService executor;

    private final ArrayList<Supplier<Boolean>> queuedTasks;

    private long executorThreadId = -1;

    private long executions, slowExecutions;

    private long lastExecutionMs, slowestExecutionMs;

    public ProcessWorker(long periodMs, ProcessFactory factory) {
        this(periodMs, factory, null);
    }

    public ProcessWorker(long periodMs, ProcessFactory factory, Consumer<Throwable> errorAction) {
        this.period = periodMs;
        this.executor = Executors.newSingleThreadScheduledExecutor(factory);
        this.executor.scheduleAtFixedRate(() -> {
            try {
                process();
            } catch (Exception e) {
                ServerWrapper.logError("ProcessWorker failed to process", e);
            }
        }, 0L, periodMs, TimeUnit.MILLISECONDS);
        this.queuedTasks = new ArrayList<>();
        executeAwait(() -> this.executorThreadId = Thread.currentThread().getId());
    }

    public final void queue(Supplier<Boolean> task) {
        executor.execute(() -> queuedTasks.add(task));
    }

    /**
     * Processing
     */

    private void process() {
        long startTime = System.nanoTime();
        if(!queuedTasks.isEmpty())
            queuedTasks.removeIf(this::finish);
        lastExecutionMs = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        if(lastExecutionMs > period)
            slowExecutions++;
        if(lastExecutionMs > slowestExecutionMs)
            slowestExecutionMs = lastExecutionMs;
        executions++;
    }

    private boolean finish(Supplier<Boolean> task) {
        try {
            return task.get();
        } catch(Throwable t) {
            ServerWrapper.logError("", t);
            return false;
        }
    }

    /**
     * Executing
     */

    public final void execute(Runnable runnable) {
        executor.execute(new SafeRunnable(runnable));
    }

    public final boolean executeAwait(Runnable runnable) {
        try {
            executor.submit(new SafeRunnable(runnable)).get();
            return true;
        } catch(Throwable t) {
            ServerWrapper.logError("", t);
            return false;
        }
    }

    public final void executeLast(Runnable runnable) {
        queue(() -> {
            new SafeRunnable(runnable).run();
            return true;
        });
    }

    public final <T> void execute(Supplier<T> asyncTask, Consumer<T> syncTask) {
        CompletableFuture.supplyAsync(asyncTask)
                .exceptionally(t -> {
                    ServerWrapper.logError("", t);
                    return null;
                })
                .thenAccept(t -> sync(() -> syncTask.accept(t)));
    }

    public final void sync(Runnable runnable) {
        if(Thread.currentThread().getId() == executorThreadId)
            new SafeRunnable(runnable).run();
        else
            execute(runnable);
    }

    /**
     * Getters
     */

    public final long getPeriod() {
        return period;
    }

    public final ScheduledExecutorService getExecutor() {
        return executor;
    }

    public final long getExecutions() {
        return executions;
    }

    public final long getSlowExecutions() {
        return slowExecutions;
    }

    public final long getLastExecutionTime() {
        return lastExecutionMs;
    }

    public final long getSlowestExecutionTime() {
        return slowestExecutionMs;
    }

    public final long getQueuedTaskCount() {
        return queuedTasks.size();
    }

}