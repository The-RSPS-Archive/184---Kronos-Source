package io.ruin.process.task;

public class TaskWorker {

    public static void startTask(TaskConsumer taskConsumer) {
        new Task0(taskConsumer).start();
    }

}
