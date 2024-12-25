package io.ruin.data.impl.items;

import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.combat.ShieldType;

import java.util.Map;

public class shield_types extends DataFile {

    public static Map<String, ShieldType> MAP;

    @Override
    public String path() {
        return "items/shield_types.json";
    }

    @Override
    public int priority() {
        return DataFile.priority("items", 1);
    }

    @Override
    public Object fromJson(String fileName, String json) {
        return MAP = JsonUtils.fromJson(json, Map.class, String.class, ShieldType.class);
    }

    public static void unload() {
        MAP.clear();
        MAP = null;
    }

}