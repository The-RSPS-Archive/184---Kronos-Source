package io.ruin.model.skills.construction.seat.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.construction.seat.Seat;

public enum Chair implements Seat {

    CRUDE_WOODEN_CHAIR(6752, 4073, 4141, 4074, 4142),
    WOODEN_CHAIR(6753, 4075, 4143, 4076, 4144),
    ROCKING_CHAIR(6754, 4079, 4145, 4080, 4146),
    OAK_CHAIR(6755, 4081, 4147, 4082, 4148),
    OAK_ARMCHAIR(6756, 4083, 4149, 4084, 4150),
    TEAK_ARMCHAIR(6757, 4085, 4151, 4086, 4152),
    MAHOGANY_ARMCHAIR(6758, 4087, 4154, 4088, 4153),

    WOODEN_STOOL(6806, 4107, 7059), // stools cant be type 11
    OAK_STOOL(6807, 4108, 7087),

    CARVED_TEAK_BENCH_2(13694, 4097, 4098), // Throne room ones
    MAHOGANY_BENCH_2(13695, 4099, 4100),
    GILDED_BENCH_2(13696, 4101, 4102),

    OAK_THRONE(13665, 4111, 7088),
    TEAK_THRONE(13666, 4112, 7089),
    MAHOGANY_THRONE(13667, 4113, 7090),
    GILDED_THRONE(13668, 4114, 7091),
    SKELETON_THRONE(13669, 4115, 7092),
    CRYSTAL_THRONE(13670, 4116, 7093),
    DEMONIC_THRONE(13671, 4117, 7094),

    TEAK_GARDEN_BENCH_RIGHT(29270, 7285, 7286),
    TEAK_GARDEN_BENCH_LEFT(29271, 7287, 7288),
    GNOME_GARDEN_BENCH_RIGHT(29272, 7289, 7290),
    GNOME_GARDEN_BENCH_LEFT(29273, 7291, 7292),
    //marble & obsidian garden benches can't be sat on. "Unlike the teak garden bench and gnome bench, this bench cannot be sat on due to rendering issues caused by the game engine. Attempting to do so results in the chatbox stating "It looks like you'd get a sore back if you sat on that bench."
    ;

    Chair(int chairId, int idle, int eat, int idleDiagonal, int eatDiagonal) {
        this.chairId = chairId;
        this.idle = idle;
        this.eat = eat;
        this.idleDiagonal = idleDiagonal;
        this.eatDiagonal = eatDiagonal;
    }

    Chair(int chairId, int idle, int eat) {
        this(chairId, idle, eat, -1, -1);
    }

    private int chairId;
    private int idle;
    private int eat;
    private int idleDiagonal;
    private int eatDiagonal;

    public void sit(Player player, GameObject obj) {
        boolean diagonal = obj.type == 11;
        Direction dir = Direction.getFromObjectDirection(obj.direction);
        player.startEvent(event -> {
            player.lock();
            player.seat = this;
            player.animate(diagonal ? Construction.SIT_DIAGONAL : Construction.SIT);
            player.getMovement().force(0, 0, -dir.deltaX, -dir.deltaY, 25, 5, dir);
            event.delay(1);
            obj.setId(Construction.OCCUPIED_SEAT);
            player.animate(diagonal ? idleDiagonal : idle);
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
        boolean diagonal = obj.type == 11;
        Direction dir = Direction.getFromObjectDirection(obj.direction);
        player.seat = null;
        player.animate(diagonal ? Construction.STAND_UP_DIAGONAL : Construction.STAND_UP);
        player.getMovement().force(0, 0, dir.deltaX, dir.deltaY, 25, 5, dir);
        obj.setId(chairId);
    }

    @Override
    public int getEatAnimation(Player player) {
        GameObject obj = Tile.getObject(Construction.OCCUPIED_SEAT, player.getAbsX(), player.getAbsY(),player.getHeight(), -1, -1);
        boolean diagonal = obj != null && obj.type == 11;
        return diagonal ? eatDiagonal : eat;
    }

    @Override
    public void restore(Player player) {
        GameObject seat = Tile.getObject(Construction.OCCUPIED_SEAT, player.getAbsX(), player.getAbsY(),player.getHeight(), -1, -1);
        if (seat != null) {
            seat.setId(chairId);
        }
    }

    static {
        for (Chair c : Chair.values()) {
            ObjectAction.register(c.chairId, 1, c::sit);
        }
    }
}
