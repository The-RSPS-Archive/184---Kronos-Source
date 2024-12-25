package io.ruin.process.event;

import kilim.Pausable;

@FunctionalInterface
public interface EventConsumer {

    void accept(Event event) throws Pausable;

}