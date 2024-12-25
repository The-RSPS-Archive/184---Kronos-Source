package io.ruin.process.task;

import io.ruin.Server;
import kilim.Pausable;
import kilim.Task;

public class Task0 {

    private Task task;

    protected Task0(TaskConsumer consumer) {
        task = new Task() {
            @Override
            public void execute() throws Pausable {
                try {
                    consumer.accept(Task0.this);
                } catch(Throwable t) {
                    Server.logError("", t);
                }
            }
        };
    }

    protected Task0 start() {
        task.start();
        return this;
    }

    public final void sleep(long ms) throws Pausable {
        Task.sleep(ms);
    }

    public final void sync(Runnable runnable) {
        Server.worker.execute(runnable);
    }

}
