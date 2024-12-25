package io.ruin.data.impl.items;

import com.google.gson.annotations.Expose;
import io.ruin.api.protocol.world.WorldType;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.map.ground.GroundItem;

import java.util.List;

public class item_spawns extends DataFile {

    @Override
    public String path() {
        return "items/spawns/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<Spawn> spawns = JsonUtils.fromJson(json, List.class, Spawn.class);
        spawns.forEach(spawn -> {
            if(spawn.world != null && spawn.world != World.type)
                return;
            new GroundItem(spawn.id, spawn.amount).position(spawn.x, spawn.y, spawn.z).spawnWithRespawn(1);
        });
        return spawns;
    }

    private static final class Spawn {
        @Expose public int id;
        @Expose public int amount = 1;
        @Expose public int x, y, z;
        @Expose public WorldType world;
    }

}