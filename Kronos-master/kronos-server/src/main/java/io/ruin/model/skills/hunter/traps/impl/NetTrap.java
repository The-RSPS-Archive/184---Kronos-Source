package io.ruin.model.skills.hunter.traps.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.creature.impl.Salamander;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.TrapType;

import java.util.Arrays;
import java.util.Optional;

import static io.ruin.model.skills.hunter.Hunter.addTimeoutEvent;
import static io.ruin.model.skills.hunter.Hunter.canPlaceTrap;

public class NetTrap implements TrapType { // TODO net trap behavior is likely VERY buggy due to how the replacing works, needs thorough testing

    public static NetTrap SWAMP = new NetTrap(29,9341, 9257, 9005,9158, 9003, 9004);
    public static NetTrap DESERT = new NetTrap(47,8732,8730, 8974,8973, 8972, 8734);
    public static NetTrap RED = new NetTrap(59,8990, 8989, 8987,8988, 8985, 8986);
    public static NetTrap BLACK = new NetTrap(67,9000, 8999, 8997,8998, 8993, 8996);
    //TODO orange, red, and black sallies
    static {
        for (NetTrap trap : Arrays.asList(SWAMP, DESERT, RED, BLACK)) {
            ObjectAction.register(trap.defaultTreeId, "set-trap", trap::layTrap);
            Hunter.registerTrap(trap, false);
            ObjectAction.register(trap.getBentTreeId(), "dismantle", (player, obj) -> Salamander.dismantleTrap(player, getNetObject(obj)));
            ObjectAction.register(trap.getFailedObjectId(), "dismantle", Salamander::dismantleTrap);
        }
    }

    public NetTrap(int levelReq, int defaultTreeId, int bentTreeId, int failingId, int failedId, int succeedingId, int successId) {
        this.levelReq = levelReq;
        this.failedId = failedId;
        this.succeedingId = succeedingId;
        this.successId = new int[]{successId};
        this.defaultTreeId = defaultTreeId;
        this.bentTreeId = bentTreeId;
        this.failingId = failingId;
    }

    private int levelReq;
    private int failedId;
    private int defaultTreeId;
    private int bentTreeId;
    private int succeedingId;
    private int failingId;

    private int[] successId;

    public int getDefaultTreeId() {
        return defaultTreeId;
    }

    public int getSucceedingId() {
        return succeedingId;
    }

    public int[] getSuccessId() {
        return successId;
    }

    public int getFailingId() {
        return failingId;
    }

    @Override
    public int getItemId() {
        return 303;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public int getActiveObjectId() {
        return 9343;
    }

    @Override
    public int getFailedObjectId() {
        return failedId;
    }

    @Override
    public int getPlaceAnimation() {
        return 5215;
    }

    @Override
    public int getDismantleAnimation() {
        return 5207;
    }

    @Override
    public int[] getSuccessObjects() {
        return successId;
    }

    @Override
    public void onPlace(Player player, GameObject object) {

    }

    @Override
    public void onRemove(Player player, GameObject object) {
        player.getInventory().addOrDrop(954, 1);
        Optional.ofNullable(getTreeObject(object)).ifPresent(tree -> tree.setId(getDefaultTreeId()));
        if (object.direction < 2 && (object.id == getSuccessId()[0] || object.id == getFailedObjectId())) {
            if (!object.isRemoved())
                object.setId(getDefaultTreeId());
        } else {
            if (!object.isRemoved())
                object.remove();
        }
    }

    @Override
    public void collapse(Player player, Trap trap, boolean remove) {
        if (trap.isRemoved() || trap.getObject().id == -1 || trap.getOwner() == null) {
            return;
        }
        GameObject object = trap.getObject();
        Position dropPos = new Position(object.x, object.y, object.z);
        Optional.ofNullable(getTreeObject(object)).ifPresent(tree -> tree.setId(getDefaultTreeId()));
        if (object.direction < 2 && (object.id == getSuccessId()[0] || object.id == getFailedObjectId() || object.id == getFailingId() || object.id == getSucceedingId())) {
            int x = object.x;
            int y = object.y;
            if (object.direction == 0)
                y++;
            else
                x++;
            dropPos = new Position(x, y, object.z);
            object.setId(getDefaultTreeId());
        } else {
            if (!object.isRemoved())
            object.remove();
        }
        new GroundItem(trap.getTrapType().getItemId(), 1)
                .owner(player)
                .position(dropPos)
                .spawn();
        new GroundItem(954, 1)
                .owner(player)
                .position(dropPos)
                .spawn();
        trap.setRemoved(true);
        if (remove) {
            trap.getOwner().traps.remove(trap);
        }
    }

    protected int getBentTreeId() {
        return bentTreeId;
    }

    public static GameObject getNetObject(GameObject tree) {
        int x = tree.x;
        int y = tree.y;
        if (tree.direction == 0)
            y++;
        else if (tree.direction == 2)
            y--;
        else if (tree.direction == 1)
            x++;
        else
            x--;
        return Tile.getObject(-1, x, y, tree.z, 10, -1);
    }

    public static GameObject getTreeObject(GameObject net) {
        int x = net.x;
        int y = net.y;
        if (net.direction == 0)
            y--;
        else if (net.direction == 2)
            y++;
        else if (net.direction == 1)
            x--;
        else
            x++;
        return Tile.getObject(-1, x, y, net.z, 10, -1);
    }

    public void layTrap(Player player, GameObject obj) {
        if (!canPlaceTrap(player, this))
            return;
        if (!player.getInventory().contains(getItemId(), 1) || !player.getInventory().contains(954, 1)) {
            player.sendMessage("You'll need a small fishing net and a rope to place a trap here.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(getPlaceAnimation());
            event.delay(1);
            player.getInventory().remove(getItemId(), 1);
            player.getInventory().remove(954, 1);
            obj.setId(getBentTreeId());
            Trap trap = new Trap(player, this, GameObject.spawn(getActiveObjectId(), player.getAbsX(), player.getAbsY(), player.getHeight(), 10, obj.direction));
            player.traps.add(trap);
            addTimeoutEvent(player, trap);
            player.unlock();
        });
    }
}
