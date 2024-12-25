package io.ruin.process.event;

import io.ruin.Server;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import kilim.Continuation;
import kilim.NotPausable;
import kilim.Pausable;
import kilim.Task;

import java.util.function.Supplier;

public class Event {

    private int delayTicks;

    protected Continuation continuation;

    private boolean ignoreCombatReset;

    private Supplier<Boolean> cancelCondition;

    public EventType eventType = EventType.DEFAULT;

    public Event(EventConsumer consumer) {
        continuation = new Continuation() {
            @Override
            public void execute() throws Pausable {
                try {
                    consumer.accept(Event.this);
                } catch(Pausable p) {
                    throw p;
                } catch(Throwable t) {
                    Server.logError("", t);
                }
            }
        };
    }

    public final boolean tick() {
        if(delayTicks > 0) {
            if (--delayTicks > 0)
                return true;
            if (cancelCondition != null && cancelCondition.get()) {
                return false;
            }
        }
        try {
            return !continuation.run();
        } catch(NotPausable e) {
            Server.logError("", e);
            return false;
        }
    }

    public final void delay(int ticks) throws Pausable {
        delayTicks = ticks;
        Task.yield();
    }

    public final void waitForMovement(Entity entity) throws Pausable {
        while(!entity.getMovement().isAtDestination())
            delay(1);
    }

    public final void waitForTile(Entity entity, Position position) throws Pausable {
        waitForTile(entity, position.getX(), position.getY());
    }

    public final void waitForTile(Entity entity, int x, int y) throws Pausable {
        while(!entity.isAt(x, y))
            delay(1);
    }

    public final Event ignoreCombatReset() {
        this.ignoreCombatReset = true;
        return this;
    }

    public boolean isIgnoreCombatReset() {
        return ignoreCombatReset;
    }

    public final void waitForDialogue(Player player) throws Pausable {
        while (player.hasDialogue())
            delay(1);
    }

    public final void path(Entity entity, Position... waypoints) throws Pausable {
        for (Position pos : waypoints) {
            entity.getRouteFinder().routeAbsolute(pos.getX(), pos.getY());
            waitForMovement(entity);
        }
    }

    public final void waitForCondition(Supplier<Boolean> condition, int timeout) throws Pausable {
        int time = 0;
        while (!condition.get() && time < timeout) {
            time++;
            delay(1);
        }
    }

    public final boolean persistent() {
        return eventType == EventType.PERSISTENT;
    }

    /**
     * When returning from a pause (delay), the given condition will be checked, and if met, the event will be stopped
     */
    public final void setCancelCondition(Supplier<Boolean> cancelCondition) {
        this.cancelCondition = cancelCondition;
    }
}
