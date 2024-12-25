package io.ruin.model.activities.pvminstances;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.services.Loggers;

import java.util.HashMap;
import java.util.Map;

public class PVMInstance {

    private static final Map<Integer, PVMInstance> activeInstances = new HashMap<>();

    public static void destroyAll() {
        activeInstances.forEach((owner, instance) -> instance.destroy(false));
        activeInstances.clear();
    }

    public static PVMInstance getByUserId(int id) {
        return activeInstances.get(id);
    }

    public static PVMInstance getByPlayer(Player player) {
        return player == null ? null : getByUserId(player.getUserId());
    }

    public static PVMInstance getByUsername(String name) {
        return getByPlayer(World.getPlayer(name));
    }
    private DynamicMap map;
    private InstanceType type;

    private int ownerId;
    private int timeLeft;
    private int idleTime;

    private MapListener mapListener;

    private boolean destroyed;

    private int playersInside;
    private final String password;

    private final InstancePrivacy privacy;

    private long timeCreated, timeDestroyed;

    public PVMInstance(Player owner, InstanceType type, InstancePrivacy privacy, String password) {
        this.ownerId = owner.getUserId();
        this.type = type;
        this.timeLeft = type.getDuration();
        this.privacy = privacy;
        this.password = password;
        build();
        activeInstances.put(ownerId, this);
    }

    public PVMInstance(Player owner, InstanceType type, InstancePrivacy privacy) {
        this(owner, type, privacy, null);
    }

    private void build() {
        //Create the map
        map = new DynamicMap();
        map.build(type.getBounds());
        mapListener = map.toListener().onExit(this::onExit);
        //Spawn npcs
        for (InstanceType.Spawn spawn : type.getSpawns()) {
            int x = map.swRegion.baseX + spawn.x;
            int y = map.swRegion.baseY + spawn.y;
            NPC npc = new NPC(spawn.id).spawn(x, y, spawn.z, spawn.direction, spawn.walkRange);
            map.addNpc(npc);
        }
        if (map.getNpcs() != null) {
            map.getNpcs().forEach(n -> {
                if (n.getCombat() != null) {
                    DeathListener old = n.deathEndListener;
                    n.deathEndListener = (entity, killer, killHit) -> {
                        if (old != null) old.handle(entity, killer, killHit);
                        if (timeLeft <= 0)
                            n.remove(); // remove npc if instance expired
                    };
                }
            });
        }
        //Add event
        addEvent();
        timeCreated = System.currentTimeMillis();
    }

    private void addEvent() {
        World.startEvent(event -> {
            while (!destroyed) {
                if (timeLeft > 0) {
                    timeLeft--;
                    if (timeLeft == 3000) {
                        map.forPlayers(p -> p.sendMessage(Color.RED.wrap("The instance will expire in 30 minutes.")));
                    } else if (timeLeft == 1000) {
                        map.forPlayers(p -> p.sendMessage(Color.RED.wrap("The instance will expire in 10 minutes.")));
                    } else if (timeLeft == 500) {
                        map.forPlayers(p -> p.sendMessage(Color.RED.wrap("The instance will expire in 5 minutes.")));
                    } else if (timeLeft == 100) {
                        map.forPlayers(p -> p.sendMessage(Color.RED.wrap("The instance will expire in 1 minute.")));
                    } else if (timeLeft == 50) {
                        map.forPlayers(p -> p.sendMessage(Color.RED.wrap("The instance will expire in 30 seconds.")));
                    } else if (timeLeft == 0) {
                        map.forPlayers(p -> p.sendMessage(Color.RED.wrap("The instance has expired. Monsters will no longer respawn.")));
                    }
                }
                if (playersInside == 0) {
                    idleTime++;
                    if (idleTime >= 1000) {
                        destroy();
                        Player owner = World.getPlayer(ownerId, true);
                        if (owner != null)
                            owner.sendMessage(Color.RED.wrap("Your " + type.getName() + " instance has expired, as it was left empty for over 10 minutes."));
                    }
                } else {
                    idleTime = 0;
                }
                event.delay(1);
            }
        });
    }

    public void enter(Player player) {
        playersInside++;
        player.currentInstance = this;
        player.getMovement().teleport(convertPosition(type.getEntryPosition()));
        player.addActiveMapListener(mapListener);
    }

    private void onExit(Player player, boolean logout) {
        if (logout)
            player.getMovement().teleport(type.getExitPosition());
        player.deathEndListener = null;
        player.currentInstance = null;
        playersInside--;
        if (playersInside == 0 && timeLeft <= 0) {
            destroy();
        } else if (playersInside == 0) {
            Player owner = World.getPlayer(ownerId);
            if (owner != null) {
                player.sendMessage(Color.RED.wrap("Your " + type.getName() + " instance is now empty. If it remains empty for 10 consecutive minutes, it will be destroyed."));
            }
        }
    }

    void destroy() {
        destroy(true);
    }

    private void destroy(boolean remove) {
        map.destroy();
        destroyed = true;
        if (remove)
            activeInstances.remove(ownerId);
        timeDestroyed = System.currentTimeMillis();
        Loggers.logPvMInstance(ownerId, type.getName(), type.getCost(), timeCreated, timeDestroyed);
    }

    private Position convertPosition(Position pos) {
        if (!pos.inBounds(type.getBounds())) {
            throw new IllegalArgumentException("Position " + pos + " not in source bounds");
        }
        int localX = pos.getX() - type.getBounds().swX;
        int localY = pos.getY() - type.getBounds().swY;
        return new Position(localX + map.swRegion.baseX, localY + map.swRegion.baseY, pos.getZ());
    }

    public InstancePrivacy getPrivacy() {
        return privacy;
    }

    public String getPassword() {
        return password;
    }

    public void kickAllPlayers() {
        map.forPlayers(p -> {
            p.getMovement().teleport(type.getExitPosition());
            p.sendMessage("The instance owner has destroyed the instance.");
        });
    }

    public InstanceType getType() {
        return type;
    }

    public long getTimeDestroyed() {
        return timeDestroyed;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
