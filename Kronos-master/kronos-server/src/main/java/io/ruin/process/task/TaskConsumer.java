package io.ruin.process.task;

import kilim.Pausable;

@FunctionalInterface
public interface TaskConsumer {
    void accept(Task0 task) throws Pausable;
}