package io.ruin.model.skills.construction.seat.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.construction.seat.Seat;

public enum DiningBench implements Seat {
    WOODEN_BENCH(13300, 26210, 4089, 4090),
    OAK_BENCH(13301, 26211, 4091, 4092),
    CARVED_OAK_BENCH(13302, 26212, 4093, 4094),
    TEAK_BENCH(13303, 26213, 4095, 4096),
    CARVED_TEAK_BENCH(13304, 26214, 4097, 4098),
    MAHOGANY_BENCH(13305, 26215, 4099, 4100),
    GILDED_BENCH(13306, 26216, 4101, 4102),

    ;

    DiningBench(int benchId, int tempId, int idle, int eat) {
        this.benchId = benchId;
        this.tempId = tempId;
        this.idle = idle;
        this.eat = eat;
    }

    private int benchId;

    private int tempId;
    private int idle;
    private int eat;

    private void sit(Player player, GameObject object) {
        Position pullTo = player.getPosition().copy();
        player.startEvent(event -> {
            player.lock();
            Direction step = object.direction == 1 || object.direction == 3 ? Direction.NORTH : Direction.WEST;
            player.step(step.deltaX, step.deltaY, StepType.FORCE_WALK);
            event.delay(1);
            if (object.id != benchId) { // someone else sat on it maybe
                player.unlock();
                return;
            }
            player.seat = this;
            GameObject temp = GameObject.spawn(tempId, pullTo.getX(), pullTo.getY(), pullTo.getZ(), 10, object.direction);
            Direction dir = Direction.getDirection(player.getPosition(), new Position(object.x, object.y, object.z));
            player.animate(Construction.SIT);
            player.getMovement().force(0, 0, dir.deltaX, dir.deltaY, 25, 5, Direction.getFromObjectDirection(object.direction));
            object.setId(Construction.OCCUPIED_SEAT);
            event.delay(1);
            player.animate(idle);
            temp.remove();
            event.delay(1);
            player.unlock();
        });
    }


    @Override
    public void stand(Player player) {
        GameObject obj = Tile.getObject(Construction.OCCUPIED_SEAT, player.getAbsX(), player.getAbsY(),player.getHeight(), -1, -1);
        if (obj == null) { // ??
            player.animate(-1);
            player.seat = null;
            return;
        }
        Direction step;
        if (obj.direction == 0)
            step = Direction.NORTH_WEST;
        else if (obj.direction == 1)
            step = Direction.NORTH_EAST;
        else if (obj.direction == 2)
            step = Direction.SOUTH_WEST;
        else
            step = Direction.NORTH_WEST;
        player.seat = null;
        player.animate(Construction.STAND_UP);
        player.getMovement().force(0, 0, step.deltaX, step.deltaY, 25, 5, Direction.getFromObjectDirection(obj.direction));
        obj.setId(benchId);
    }

    @Override
    public int getEatAnimation(Player player) {
        return eat;
    }

    @Override
    public void restore(Player player) {
        GameObject seat = Tile.getObject(Construction.OCCUPIED_SEAT, player.getAbsX(), player.getAbsY(),player.getHeight(), -1, -1);
        if (seat != null) {
            seat.setId(benchId);
        }
    }

    static {
        for (DiningBench bench : DiningBench.values()) {
            ObjectAction.register(bench.benchId, 1, bench::sit);
        }
    }
}
