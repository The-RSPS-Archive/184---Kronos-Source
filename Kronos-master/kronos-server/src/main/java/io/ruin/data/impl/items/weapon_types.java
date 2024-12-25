package io.ruin.data.impl.items;

import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.combat.AttackSet;
import io.ruin.model.combat.WeaponType;

import java.util.Map;

public class weapon_types extends DataFile {

    public static Map<String, WeaponType> MAP;

    @Override
    public String path() {
        return "items/weapon_types.json";
    }

    @Override
    public int priority() {
        return DataFile.priority("items", 1);
    }

    @Override
    public Object fromJson(String fileName, String json) {
        MAP = JsonUtils.fromJson(json, Map.class, String.class, WeaponType.class);
        MAP.values().forEach(weaponType -> {
            AttackSet[] orderedSets = new AttackSet[4];
            for(AttackSet set : weaponType.attackSets) {
                if(set != null)
                    orderedSets[set.child / 4] = set;
            }
            weaponType.attackSets = orderedSets;
        });
        WeaponType.UNARMED = MAP.get("UNARMED");
        return MAP;
    }

    public static void unload() {
        MAP.clear();
        MAP = null;
    }

}