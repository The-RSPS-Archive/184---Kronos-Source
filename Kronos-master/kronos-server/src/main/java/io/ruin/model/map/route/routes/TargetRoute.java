package io.ruin.model.map.route.routes;

import io.ruin.model.entity.Entity;
import io.ruin.model.map.route.RouteType;

//I'm not 100% sure I like this class...
public class TargetRoute {

    public static void set(Entity entity, Entity target, int distance) {
        set(entity, target, distance, null);
    }

    public static void set(Entity entity, Entity target, Runnable finishAction) {
        set(entity, target, 1, finishAction);
    }

    public static void set(Entity entity, Entity target, int distance, Runnable finishAction) {
        TargetRoute r = entity.getRouteFinder().targetRoute;
        if(r == null) {
            r = entity.getRouteFinder().targetRoute = new TargetRoute();
        }
        r.target = target;
        r.distance = distance;
        r.finishAction = finishAction;
    }

    public static void reset(Entity entity) {
        TargetRoute r = entity.getRouteFinder().targetRoute;
        if(r != null)
            r.reset();
    }

    public static void beforeMovement(Entity entity) {
        TargetRoute r = entity.getRouteFinder().targetRoute;
        if(r != null && r.target != null)
            r.beforeMovement0(entity);
    }

    public static void afterMovement(Entity entity) {
        TargetRoute r = entity.getRouteFinder().targetRoute;
        if(r != null && r.target != null && entity.getMovement().isAtDestination())
            r.afterMovement0(entity);
    }

    /**
     * Separator
     */

    public Entity target;

    private int distance;

    private Runnable finishAction;

    private boolean abs;

    private RouteType route;

    public boolean withinDistance;

    public void reset() {
        target = null;
        finishAction = null;
        route = null;
    }

    private void beforeMovement0(Entity entity) {
        if(target.npc != null && target.npc.walkTo != null) {
            abs = true;
            route = entity.getRouteFinder().routeAbsolute(target.npc.walkTo.getX(), target.npc.walkTo.getY());
            return;
        }
        abs = false;
        route = entity.getRouteFinder().routeEntity(target);
        withinDistance = false;
        if (entity.getHeight() != target.getHeight())
            return;
        if(distance == 1) {
            if(route.reachable && route.finished(entity.getPosition()))
                withinDistance = true;
        } else {
            int absX = entity.getAbsX();
            int absY = entity.getAbsY();
            int size = entity.getSize();
            int targetX = target.getAbsX();
            int targetY = target.getAbsY();
            int targetSize = target.getSize();
            if(!inTarget(absX, absY, size, targetX, targetY, targetSize)
                    && inRange(absX, absY, size, targetX, targetY, targetSize, distance)
                    && ((target.npc != null && target.npc.getId() == 7706)|| ProjectileRoute.allow(absX, absY, entity.getHeight(), size, targetX, targetY, targetSize))) { // TODO: look into projectile clipping so the zuk(inferno boss) exception is not required?
                withinDistance = true;
                entity.getMovement().reset();
            }
        }
    }

    public boolean allowStep(Entity entity, int stepX, int stepY) {
        if(target == null)
            return true;
        TargetRoute r = target.getRouteFinder().targetRoute;
        if(r != null && (target.getMovement().getWalkDirection() != -1 || target.getMovement().getRunDirection() != -1)
                && target.getMovement().isAtDestination()
                && r.distance <= distance
                && r.route != null
                && entity.isAt(r.route.absX, r.route.absY)
                && r.route.xLength == entity.getSize()) {
            withinDistance = r.withinDistance;
            return false;
        }
        if(route.reachable) {
            int size = entity.getSize();
            int targetX = target.getAbsX();
            int targetY = target.getAbsY();
            int targetSize = target.getSize();
            if(inTarget(stepX, stepY, size, targetX, targetY, targetSize)) {
                withinDistance = false;
                return target.getMovement().isAtDestination(); //todo - test
            }
            if(distance == 1) {
                withinDistance = (stepX == route.finishX && stepY == route.finishY);
                return true;
            }
            if(inRange(stepX, stepY, size, targetX, targetY, targetSize, distance)
                    && ProjectileRoute.allow(stepX, stepY, entity.getHeight(), size, targetX, targetY, targetSize)) {
                withinDistance = true;
                entity.getMovement().reset();
                return true;
            }
        }
        return true;
    }

    private void afterMovement0(Entity entity) {
        if(finishAction != null) {
            /**
             * Interactions
             */
            entity.faceNone(true);
            if(abs)
                withinDistance = route.finished(entity.getPosition());
            if(withinDistance || (target.npc != null && target.npc.skipReachCheck != null && target.npc.skipReachCheck.test(entity.getPosition())))
                finishAction.run();
            else if(entity.player != null)
                entity.player.getMovement().outOfReach();
            reset();
        } else if(!withinDistance) {
            /**
             * Combat
             */
            if(entity.player != null)
                entity.player.getMovement().outOfReach();
            entity.getCombat().reset();
            reset();
        }
    }

    /**
     * Misc checks
     */

    protected static boolean inTarget(int absX, int absY, int size, int targetX, int targetY, int targetSize) {
        if(absX > (targetX + (targetSize - 1)) || absY > (targetY + (targetSize - 1)))
            return false;
        if(targetX > (absX + (size - 1)) || targetY > (absY + (size - 1)))
            return false;
        return true;
    }

    public static boolean inRange(int absX, int absY, int size, int targetX, int targetY, int targetSize, int distance) {
        if(absX < targetX) {
            /**
             * West of target
             */
            int closestX = absX + (size - 1);
            int diffX = targetX - closestX;
            if(diffX > distance)
                return false;
        } else if(absX > targetX) {
            /**
             * East of target
             */
            int closestTargetX = targetX + (targetSize - 1);
            int diffX = absX - closestTargetX;
            if(diffX > distance)
                return false;
        }
        if(absY < targetY) {
            /**
             * South of target
             */
            int closestY = absY + (size - 1);
            int diffY = targetY - closestY;
            if(diffY > distance)
                return false;
        } else if(absY > targetY) {
            /**
             * North of target
             */
            int closestTargetY = targetY + (targetSize - 1);
            int diffY = absY - closestTargetY;
            if(diffY > distance)
                return false;
        }
        return true;
    }

}