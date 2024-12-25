package io.ruin.data.impl.items;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.ItemDef;
import io.ruin.data.DataFile;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.combat.RangedAmmo;
import io.ruin.model.combat.RangedWeapon;
import io.ruin.model.stat.StatType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class item_info extends DataFile {

    @Override
    public String path() {
        return "items/item_info.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<Temp> temps = JsonUtils.fromJson(json, List.class, Temp.class);
        temps.forEach(temp -> {
            ItemDef def = ItemDef.get(temp.id);
            def.tradeable = temp.tradeable;
            def.examine = temp.examine;
            def.dropAnnounce = temp.dropAnnounce;
            def.weightInventory = temp.weight;
            def.weightEquipment = temp.weight_equipped == null ? temp.weight : temp.weight_equipped;
            def.protectValue = temp.protect_value;
            if (def.protectValue > 500_000) {
                def.dropAnnounce = true;
            }
            def.wilderness = temp.wilderness;

            /*
             * Reset (Necessary when reloading)
             */
            def.equipReqs = null;
            def.rangedLevel = 1;
            def.equipBonuses = null;
            /*
             * Equip info
             */
            def.equipSlot = temp.equip_slot == null ? -1 : temp.equip_slot;
            def.twoHanded = temp.two_handed == Boolean.TRUE;
            def.hideHair = temp.hide_hair == Boolean.TRUE;
            def.hideBeard = temp.hide_beard == Boolean.TRUE;
            def.hideArms = temp.hide_arms == Boolean.TRUE;
            /*
             * Levels
             */
            ArrayList<Integer> reqs = new ArrayList<>();
            for(StatType stat : StatType.values()) {
                try {
                    Field field = temp.getClass().getField(stat.name().toLowerCase() + "_level");
                    Integer level = (Integer) field.get(temp);
                    if(level != null) {
                        if(stat == StatType.Ranged)
                            def.rangedLevel = level;
                        reqs.add(stat.ordinal() << 8 | level);
                    }
                } catch(Exception e) {
                    ServerWrapper.logError("Failed to load item level: " + def.id, e);
                }
            }
            if(!reqs.isEmpty()) {
                def.equipReqs = new int[reqs.size()];
                for(int i = 0; i < def.equipReqs.length; i++)
                    def.equipReqs[i] = reqs.get(i);
            }
            /*
             * Bonuses
             */
            String[] bonusOrder = {
                    "stab_attack_bonus",
                    "slash_attack_bonus",
                    "crush_attack_bonus",
                    "magic_attack_bonus",
                    "range_attack_bonus",
                    "stab_defence_bonus",
                    "slash_defence_bonus",
                    "crush_defence_bonus",
                    "magic_defence_bonus",
                    "range_defence_bonus",
                    "melee_strength_bonus",
                    "ranged_strength_bonus",
                    "magic_damage_bonus",
                    "prayer_bonus",
                    "undead_bonus",
                    "slayer_bonus"
            };
            for(int i = 0; i < bonusOrder.length; i++) {
                try {
                    Field field = temp.getClass().getField(bonusOrder[i]);
                    Integer bonus = (Integer) field.get(temp);
                    if(bonus != null) {
                        if(def.equipBonuses == null)
                            def.equipBonuses = new int[16];
                        def.equipBonuses[i] = bonus;
                    }
                } catch(Exception e) {
                    ServerWrapper.logError("Failed to load item bonuses: " + def.id, e);
                }
            }
            /*
             * Misc
             */
            def.shieldType = temp.shield_type == null ? null : shield_types.MAP.get(temp.shield_type);
            def.weaponType = temp.weapon_type == null ? null : weapon_types.MAP.get(temp.weapon_type);
            def.rangedWeapon = temp.ranged_weapon == null ? null : RangedWeapon.valueOf(temp.ranged_weapon);
            def.rangedAmmo = temp.ranged_ammo == null ? null : RangedAmmo.valueOf(temp.ranged_ammo);

            def.achievement = temp.achievement == null ? null : Achievement.valueOf(temp.achievement);
            def.achievementReqIsIronmanOnly = temp.achievement_req_is_ironman_only != null && temp.achievement_req_is_ironman_only;

        });
        ItemDef.forEach(this::loadMisc);
        shield_types.unload();
        weapon_types.unload();
        return temps;
    }

    private void loadMisc(ItemDef def) {
        def.dropOption = def.getOption("drop");
        def.equipOption = def.getOption("wield", "equip", "wear", "ride", "hold");
        def.pickupOption = def.getGroundOption("take", "pickup");
        if(def.value > 0) {
            def.highAlchValue = (def.value * 3) / 5;
            def.lowAlchValue = (def.highAlchValue / 3) * 2;
        }
        /*
         * Keep last
         */
        if(def.isNote()) {
            ItemDef reg = ItemDef.get(def.notedId);
            def.name = reg.name;
            def.tradeable = reg.tradeable;
            def.examine = "Swap this note at any bank for the equivalent item.";
            def.stackable = true;
        }
        if(def.isPlaceholder()) {
            ItemDef reg = ItemDef.get(def.placeholderMainId);
            def.name = reg.name;
            def.examine = reg.examine;
            /*ItemDef.forEach(def2 -> {
                if(def2.inventoryModel != reg.inventoryModel || def2.hasPlaceholder())
                    return;
                if(!stripName(def2.name).startsWith(stripName(reg.name)))
                    return;
                def2.placeholderMainId = reg.placeholderMainId;
                def2.placeholderTemplateId = reg.placeholderTemplateId;
            });*/
        }
    }

    private static String stripName(String name) {
        int i = name.lastIndexOf("(");
        if(i != -1)
            name = name.substring(0, i).trim();
        return name.toLowerCase()
                .replace("new crystal shield", "crystal shield")
                .replace("crystal shield full", "crystal shield");
    }

    public static final class Temp {
        @Expose public int id;
        @Expose public boolean tradeable;
        @Expose public String examine;
        @Expose public double weight;
        @Expose public Double weight_equipped;
        @Expose public int protect_value;
        @Expose public boolean wilderness;
        /**
         * Attributes that don't have to be set.
         */
        @Expose public Integer equip_slot;
        @Expose public Boolean two_handed;
        @Expose public Boolean hide_hair;
        @Expose public Boolean hide_beard;
        @Expose public Boolean hide_arms;
        //okay this isn't the prettiest design...
        @Expose public Integer attack_level, defence_level, strength_level, hitpoints_level, ranged_level, prayer_level, magic_level,
                cooking_level, woodcutting_level, fletching_level, fishing_level, firemaking_level, crafting_level, smithing_level, mining_level,
                herblore_level, agility_level, thieving_level, slayer_level, farming_level, runecrafting_level, hunter_level, construction_level;
        //neither is this...
        @Expose public Integer stab_attack_bonus, slash_attack_bonus, crush_attack_bonus, magic_attack_bonus, range_attack_bonus,
                stab_defence_bonus, slash_defence_bonus, crush_defence_bonus, magic_defence_bonus, range_defence_bonus,
                melee_strength_bonus, ranged_strength_bonus, magic_damage_bonus,
                prayer_bonus, undead_bonus, slayer_bonus;
        @Expose public String shield_type;
        @Expose public String weapon_type;
        @Expose public String ranged_weapon;
        @Expose public String ranged_ammo;

        @Expose public String achievement;
        @Expose public Boolean achievement_req_is_ironman_only; // indicates the achievement req should only be applied to ironman characters
        @Expose public boolean dropAnnounce;
    }

}