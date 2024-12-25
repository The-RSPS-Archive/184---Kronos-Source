package io.ruin.model.map;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.dynamic.DynamicMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class MapListener {

    private Predicate<Player> activeCheck;

    public EnteredAction enterAction;

    public ExitAction exitAction;

    public int staticId = -1;

    public MapListener(Predicate<Player> activeCheck) {
        this.activeCheck = activeCheck;
    }

    public MapListener onEnter(EnteredAction enterAction) {
        this.enterAction = enterAction;
        return this;
    }

    public MapListener onExit(ExitAction exitAction) {
        this.exitAction = exitAction;
        return this;
    }

    public boolean isActive(Player player) {
        return activeCheck.test(player);
    }

    /**
     * Static
     */

    public static MapListener[] LISTENERS;

    public static MapListener register(Predicate<Player> predicate) {
        return register(new MapListener(predicate));
    }

    public static MapListener registerPosition(Position position) {
        return register(new MapListener(p -> p.getPosition().equals(position)));
    }

    public static MapListener registerBounds(Bounds bounds) {
        return register(new MapListener(p -> p.getPosition().inBounds(bounds)));
    }

    public static MapListener registerBounds(Bounds... bounds) {
        return register(new MapListener(p -> {
            for(Bounds b : bounds) {
                if(p.getPosition().inBounds(b))
                    return true;
            }
            return false;
        }));
    }

    public static MapListener registerRegion(int regionId) {
        return register(new MapListener(p -> p.lastRegion != null && p.lastRegion.id == regionId));
    }

    public static MapListener registerRegions(int... regionIds) {
        return register(new MapListener(p -> {
            if(p.lastRegion != null) {
                for(int id : regionIds) {
                    if(p.lastRegion.id == id)
                        return true;
                }
            }
            return false;
        }));
    }

    public static MapListener registerRegions(List<Integer> regionIds) {
        return register(new MapListener(p -> p.lastRegion != null && regionIds.contains(p.lastRegion.id)));
    }

    public static MapListener registerMap(DynamicMap map) {
        return register(map.toListener());
    }

    private static MapListener register(MapListener listener) {
        List<MapListener> list = new ArrayList<>();
        if(LISTENERS != null)
            Collections.addAll(list, LISTENERS);
        listener.staticId = list.size();
        list.add(listener);
        LISTENERS = list.toArray(new MapListener[0]);
        return listener;
    }

    /**
     * Action types (Yes I know I can use consumers, but this is a little bit more noob friendly for koders.)
     */

    public interface EnteredAction {
        void entered(Player player);
    }

    public interface ExitAction {
        void exited(Player player, boolean logout);
    }

}