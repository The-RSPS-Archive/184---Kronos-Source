package io.ruin.data.impl.objects;

import com.google.gson.annotations.Expose;
import io.ruin.api.protocol.world.WorldType;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.map.object.GameObject;

import java.util.List;

public class object_spawns extends DataFile {

    @Override
    public String path() {
        return "objects/spawns/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<Spawn> spawns = JsonUtils.fromJson(json, List.class, Spawn.class);
        spawns.forEach(spawn -> {
            if(spawn.world != null && spawn.world != World.type)
                return;
            GameObject.spawn(spawn.id, spawn.x, spawn.y, spawn.z, spawn.type, spawn.direction);
        });
        return spawns;
    }

    private static final class Spawn {
        @Expose public int id;
        @Expose public int x, y, z;
        @Expose public int type, direction;
        @Expose public WorldType world;
    }
    
}