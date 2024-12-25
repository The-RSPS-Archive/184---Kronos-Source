package io.ruin.model.content.upgrade;

import io.ruin.api.utils.Random;
import io.ruin.model.content.upgrade.effects.*;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author ReverendDread on 5/29/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Getter
public enum ItemEffect {

    /*
     * Weapons
     */
    BANE_OF_DRAGONS("Bane of Dragons", "Does more damage against dragons.", new BaneOfDragons(), 30.0, true, EquipSlot.WEAPON),
    LOOTING("Looting", "Increases your chances of getting a rare drop.", new Looting(), 5.0, true, EquipSlot.WEAPON, EquipSlot.RING),
    FIRE_ASPECT("Fire Aspect", "Does fire damage to your target over time.", new FireAspect(), 15.0, true, EquipSlot.WEAPON),
    RECOIL("Recoil", "Reflects some damage back at your target.", new Recoil(), 20.0, true, EquipSlot.WEAPON),
    BANE_OF_SPIDERS("Bane of Spiders", "Does more damage against spiders.", new BaneOfSpiders(), 30.0, true, EquipSlot.WEAPON),
    HOLY("Holy", "Randomly heals you when you deal damage.", new Holy(), 12.0, true, EquipSlot.WEAPON),
    SPITEFUL("Spiteful", "You will deal more damage the lower your health is.", new Spiteful(), 5.0, true, EquipSlot.WEAPON),
    DAMAGED("Damaged", "Reduced damage dealt", new Damaged(), 20.0, false, EquipSlot.WEAPON),
    WRECKLESS("Wreckless", "Deals additional damage, but also damages the user.", new Wreckless(), 10.0, false, EquipSlot.WEAPON),
    ZULRAHS_KISS("Zulrah's Kiss", "10% chance to inflict poison", new ZulrahsKiss(), 20.0, true, EquipSlot.WEAPON),

    /*
     * Jewelery
     */
    BONE_COLLECTOR("Bone Collector", "Chance to send a bone to your bank based on what has dropped.", new BoneCollector(), 10.0, true, EquipSlot.AMULET),
    COIN_COLLECTOR("Coin Collector", "Automatically picks up coins.", new CoinCollector(), 10.0, true, EquipSlot.RING),
    HOLY_BONES("Holy Bones", "Chance to bury a bone and restore your prayer some.", new HolyBones(), 10.0, true, EquipSlot.AMULET),
    ZULRAHS_BLESSING("Zulrah's Blessing", "25% chance to cure poison", new ZulrahsBlessing(), 10.0, true, EquipSlot.AMULET, EquipSlot.RING),
    SARADOMINS_GRACE("Saradomin's Grace", "On opponent death, 10% chance to restore 5 prayer points", new SaradominsGrace(), 10.0, true, EquipSlot.RING, EquipSlot.AMULET),
    ZAMORAKS_CURSE("Zamorak's Curse", "On opponent death, 10% chance to suffer 10hp", new ZamoraksCurse(), 10.0, false, EquipSlot.AMULET, EquipSlot.RING),
    GUTHIXS_BALANCE("Guthix's Balance", "5% chance to heal you and your opponent for 10hp", new GuthixsBalance(), 100.0, false, EquipSlot.RING, EquipSlot.AMULET),
    /*
     * Armor
     */
    XP_BOOST("Experience Boost", "Gives a 1% experience boost.", new ExperienceBoost(), 10.0, true, EquipSlot.HAT, EquipSlot.CHEST, EquipSlot.LEGS, EquipSlot.HANDS, EquipSlot.FEET),
    PROTECTOR("Protector", "5% Chance to reduce damage by 20%", new Protector(), 10.0, true, EquipSlot.CHEST, EquipSlot.HAT, EquipSlot.LEGS),
    AGILE("Agile", "5% Chance to gain 5% run energy on damage", new Agile(), 10, true, EquipSlot.CHEST, EquipSlot.HAT, EquipSlot.LEGS)
    ;

    private String name, description;
    private ItemUpgrade upgrade;
    private EquipSlot[] slots;
    private double weight;
    private boolean positive;

    ItemEffect(String name, String description, ItemUpgrade upgrade, double weight, boolean positive, EquipSlot... slots) {
        this.name = name;
        this.description = description;
        this.upgrade = upgrade;
        this.weight = weight;
        this.positive = positive;
        this.slots = slots;
    }

    public static ItemEffect rollFrom(ItemEffect[] effects) {
        double totalWeight = Stream.of(values()).mapToDouble(ItemEffect::getWeight).sum();
        double random = Random.get() * totalWeight;
        int index = -1;
        for (int i = 0; i < effects.length; i++) {
            random -= effects[i].getWeight();
            if (random <= 0.0D) {
                index = i;
                break;
            }
        }
        return index == -1 ? rollFrom(effects) : effects[index]; //keep rolling if didnt find anything
    }

    public static ItemUpgrade getUpgrade(ItemEffect upgrade) {
        return valueOf(upgrade.name()).getUpgrade();
    }

    public int[] getSlots() {
        return Stream.of(slots).mapToInt(EquipSlot::ordinal).toArray();
    }

    enum EquipSlot {
        HAT, CAPE, AMULET, WEAPON, CHEST, SHIELD, BLANK_1, LEGS, BLANK_2, HANDS, FEET, BLANK_3, RING, AMMO
    }

}
