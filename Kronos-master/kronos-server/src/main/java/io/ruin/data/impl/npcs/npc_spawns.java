package io.ruin.data.impl.npcs;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.protocol.world.WorldType;
import io.ruin.api.utils.ArrayUtils;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.shop.ShopManager;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
public class npc_spawns extends DataFile {

    @Override
    public String path() {
        return "npcs/spawns/*.json";
    }

    public static Map<String, List<Spawn>> allSpawns = Maps.newConcurrentMap();

    @Override
    public Object fromJson(File originalFile, String fileName, String json) {
        List<Spawn> spawns = JsonUtils.fromJson(json, List.class, Spawn.class);
        File baseFolder = new File(Server.dataFolder, "npcs/spawns/");
        String spawnKey = baseFolder.toPath().resolve(originalFile.toPath()).toString();
        //log.info("Attempting to set spawns key as {}", spawnKey);
        allSpawns.put(spawnKey, spawns);

        if (!Server.dataOnlyMode) {
            spawns.forEach(spawn -> {
                if (spawn.world != null && spawn.world != World.type) return;
                if (spawn.walkRange == 0) Tile.get(spawn.x, spawn.y, spawn.z, true).flagUnmovable();
                NPC n = new NPC(spawn.id).spawn(spawn.x, spawn.y, spawn.z, Direction.get(spawn.direction), spawn.walkRange);
                n.defaultSpawn = true;
                spawn.name = n.getDef().name;
                if(spawn.shopOptions != null && n.getDef().options != null){
                    spawn.shopOptions.forEach((rightClickOption, shopUUID) -> {
                        int indexOfValue = ArrayUtils.indexOfIgnoreCase(rightClickOption, n.getDef().options);
                        //log.info("Attempting to find {} on npc {}", rightClickOption, n.getDef().options);

                        if(indexOfValue >= 0){
                            if(n.actions == null)
                                n.actions = new NPCAction[n.getDef().options.length];
                            //log.info("Found option at index {}", indexOfValue);
                            n.actions[indexOfValue] = ((player, npc) -> {
                               ShopManager.openIfExists(player, shopUUID);
                            });
                        }
                    });
                }
            });
        }

        return spawns;
    }

    @Override
    public int priority() {
        return 15;
    }
    public static final class Spawn {
        @Expose public String name;
        @Expose public int id;
        @Expose public int x, y, z;
        @Expose public String direction = "S";
        @Expose public int walkRange;
        @Expose public WorldType world;
        @Expose public Map<String, String> shopOptions;
    }

}