package io.ruin.model.map.ground;

import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.storage.LootingBag;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.services.Loggers;

import java.util.Map;

public class GroundItem {

    public int originalOwner = -1;

    public int activeOwner = -1;

    public int diedToIron = -1;

    public int id;

    public int amount;

    public int x, y, z;

    public Tile tile;

    private int respawnMinutes;

    private long timeDropped;

    private String dropperName, dropperIp;

    private Map<String, String> attributes;

    public GroundItem(Item item) {
        this(item.getId(), item.getAmount(), item.copyOfAttributes());
    }

    public GroundItem(int id, int amount) {
        this(id, amount, null);
    }

    public GroundItem(int id, int amount, Map<String, String> attributes) {
        this.id = id;
        this.amount = amount;
        this.attributes = attributes;
    }

    public GroundItem owner(Player player) {
        return owner(player.getUserId());
    }

    public GroundItem owner(int ownerId) {
        this.originalOwner = ownerId;
        this.activeOwner = ownerId;
        return this;
    }

    public GroundItem diedToIron(Player player) {
        return diedToIron(player.getUserId());
    }

    public GroundItem diedToIron(int ownerId) {
        this.diedToIron = ownerId;
        return this;
    }

    public GroundItem position(Position pos) {
        return position(pos.getX(), pos.getY(), pos.getZ());
    }

    public GroundItem position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Spawning
     */

    public GroundItem spawn() {
        return spawn(1);
    }

    public GroundItem spawnPrivate() {
        return spawn(-1);
    }

    public GroundItem spawn(int appearMinutes) {
        Tile.get(x, y, z, true).addItem(this);
        if(appearMinutes != 0) {
            boolean allowAppear = appearMinutes > 0 && activeOwner != -1 && ItemDef.get(id).tradeable;
            World.startTask(t -> {
                t.sleep(Math.abs(appearMinutes) * 60000L);
                if(allowAppear)
                    t.sync(this::appear);
                t.sleep(getDespawnTime() * 60000L);
                t.sync(this::disappear);
            });
        }
        return this;
    }

    public GroundItem spawnPublic() {
        Tile.get(x, y, z, true).addItem(this);
        World.startTask(t -> {
            t.sync(this::appear);
            t.sleep(getDespawnTime() * 60000L);
            t.sync(this::disappear);
        });
        return this;
    }

    public GroundItem spawnWithRespawn(int respawnMinutes) {
        this.respawnMinutes = respawnMinutes;
        return spawn(0);
    }

    /**
     * Appear
     */

    private void appear() {
        if(isRemoved()) {
            /* this is possible because the task never gets stopped! */
            return;
        }
        sendRemove();
        activeOwner = -1;
        sendAdd();
    }

    /**
     * Disappear
     */

    private void disappear() {
        if(isRemoved()) {
            /* this is possible because the task never gets stopped! */
            return;
        }
        remove();
    }

    private int getDespawnTime() {
        return Tile.get(x, y, z, true).region.dynamicData != null ? 60 : 2;
    }

    /**
     * Remove
     */

    public void remove() {
        //Warning: This MAY null if isRemoved isn't checked first!
        tile.removeItem(this);
    }

    public boolean isRemoved() {
        return tile == null;
    }

    /**
     * Pickup
     */

    public void pickup(Player player, int distance) {
        if(isRemoved()) {
            System.out.println("Can't pick up ground item is removed.");
            return;
        }
        if(activeOwner != -1 && activeOwner != player.getUserId()) {
            System.out.println("Can't pick up item not spawned for you.");
            return;
        }
        if(diedToIron != -1 && diedToIron != player.getUserId()) {
            return;
        }
        if(player.getGameMode().isIronMan() && originalOwner != -1 && originalOwner != player.getUserId()) {
            player.sendMessage("Ironmen cannot pick up items dropped by or for other players.");
            return;
        }
        if(player.getDuel().stage >= 4) {
            player.sendMessage("You can't pickup items in a duel.");
            return;
        }
        if(player.joinedTournament) {
            player.sendMessage("You can't pickup items while you're signed up for a tournament.");
            return;
        }
        ItemDef def = ItemDef.get(id);
        if(def.clueType != null && player.getInventory().hasId(def.clueType.clueId)) {
            player.sendMessage("You already have one of those in your inventory!");
            return;
        }
        if(!player.getLootingBag().isFull() && player.getInventory().hasId(LootingBag.OPENED_LOOTING_BAG) && player.wildernessLevel > 0) {
            if(player.getLootingBag().add(id, amount, attributes) == 0) {
                player.sendMessage("Not enough space in your looting bag.");
                return;
            }
        } else if(player.getInventory().add(id, amount, attributes) == 0) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        remove();
        if(distance > 0)
            player.animate(832);
        player.privateSound(2582);
        if(respawnMinutes > 0) {
            World.startTask(t -> {
                t.sleep(respawnMinutes * 60000L);
                t.sync(() -> this.spawnWithRespawn(respawnMinutes));
            });
        }
        Loggers.logPickup(player.getUserId(), player.getName(), player.getIp(), id, amount, x, y, z);
        if (getTimeDropped() > 0) { // this item was manually dropped by someone, log as trade
            Loggers.logDropTrade(player.getUserId(), originalOwner, player.getIp(), getDropperIp(), player.getName(), getDropperName(), id, amount, x, y, z, getTimeDropped());
        }
        if (id == 88 && !player.bootsOfLightnessTaken) {
            player.bootsOfLightnessTaken = true;
            Achievement.LIGHTNESS.update(player);
        }
    }

    /**
     * Sending
     */

    public void sendAdd() {
        for(Player player : tile.region.players) {
            if(activeOwner == -1) {
                player.getPacketSender().sendGroundItem(this);
                continue;
            }
            if(activeOwner == player.getUserId()) {
                player.getPacketSender().sendGroundItem(this);
                return;
            }
        }
    }

    public void sendAdd(Player player) {
        player.getPacketSender().sendGroundItem(this);
    }

    public void sendRemove(Player player) {
        player.getPacketSender().sendRemoveGroundItem(this);
    }

    public void sendRemove() {
        for(Player player : tile.region.players) {
            if(activeOwner == -1) {
                player.getPacketSender().sendRemoveGroundItem(this);
                continue;
            }
            if(activeOwner == player.getUserId()) {
                player.getPacketSender().sendRemoveGroundItem(this);
                return;
            }
        }
    }

    public void sendUpdate() {
        for(Player player : tile.region.players) {
            if(activeOwner == -1) {
                player.getPacketSender().sendRemoveGroundItem(this);
                player.getPacketSender().sendGroundItem(this);
                continue;
            }
            if(activeOwner == player.getUserId()) {
                player.getPacketSender().sendRemoveGroundItem(this);
                player.getPacketSender().sendGroundItem(this);
                return;
            }
        }
    }

    /**
     * For logging
     */
    public GroundItem droppedBy(Player player) {
        timeDropped = System.currentTimeMillis();
        dropperName = player.getName();
        dropperIp = player.getIp();
        return this;
    }

    public String getDropperName() {
        return dropperName;
    }

    public String getDropperIp() {
        return dropperIp;
    }

    public long getTimeDropped() {
        return timeDropped;
    }

}
