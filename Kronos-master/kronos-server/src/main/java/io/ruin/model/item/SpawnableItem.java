package io.ruin.model.item;
/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 1/28/2020
 */

import io.ruin.cache.ItemID;
import io.ruin.model.World;
import lombok.AllArgsConstructor;
import lombok.Getter;
import static io.ruin.cache.ItemID.*;

import java.util.stream.Stream;


@Getter @AllArgsConstructor
public enum SpawnableItem {
    /*
     * Food
     */
    SHARK(World.spawnableOffset + ItemID.SHARK, 0),
    LOBSTER(World.spawnableOffset + ItemID.LOBSTER, 0),
    MONKFISH(World.spawnableOffset + ItemID.MONKFISH, 0),
    /*
     * Potions
     */
    ATTACK_POTION(World.spawnableOffset + SUPER_ATTACK4, 0),
    STRENGTH_POTION(World.spawnableOffset + SUPER_STRENGTH4, 0),
    DEFENCE_POTION(World.spawnableOffset + SUPER_DEFENCE4, 0),
    RANGE_POTION(World.spawnableOffset + RANGING_POTION4, 0),
    PRAYER_POTION(World.spawnableOffset + PRAYER_POTION4, 0),
    SANFEW_SERUM(World.spawnableOffset + SANFEW_SERUM4, 0),
    SUPER_RESTORE(World.spawnableOffset + SUPER_RESTORE4, 0),
    SARA_BREW(World.spawnableOffset + SARADOMIN_BREW4, 0),
    /*
     * Armor
     */
    BRONZEPLATELEGS(World.spawnableOffset + BRONZE_PLATELEGS, 0),
    BRONZEPLATESKIRT(World.spawnableOffset + BRONZE_PLATESKIRT, 0),
    BRONZEPLATEBODY(World.spawnableOffset + BRONZE_PLATEBODY, 0),
    BRONZEFULLHELM(World.spawnableOffset + BRONZE_FULL_HELM, 0),
    BRONZEKITESHIELD(World.spawnableOffset + BRONZE_KITESHIELD, 0),
    IRONPLATELEGS(World.spawnableOffset + IRON_PLATELEGS, 0),
    IRONPLATESKIRT(World.spawnableOffset + IRON_PLATESKIRT, 0),
    IRONPLATEBODY(World.spawnableOffset + IRON_PLATEBODY, 0),
    IRONFULLHELM(World.spawnableOffset + IRON_FULL_HELM, 0),
    IRONKITSHIELD(World.spawnableOffset + IRON_KITESHIELD, 0),
    STEELPLATELEGS(World.spawnableOffset + STEEL_PLATELEGS, 0),
    STEELPLATESKIRT(World.spawnableOffset + STEEL_PLATESKIRT, 0),
    STEELPLATEBODY(World.spawnableOffset + STEEL_PLATEBODY, 0),
    STEELFULLHELM(World.spawnableOffset + STEEL_FULL_HELM, 0),
    STEELKITSHIELD(World.spawnableOffset + STEEL_KITESHIELD, 0),
    BLACKPLATELEGS(World.spawnableOffset + BLACK_PLATELEGS, 0),
    BLACKPLATESKIRT(World.spawnableOffset + BLACK_PLATESKIRT, 0),
    BLACKPLATEBODY(World.spawnableOffset + BLACK_PLATEBODY, 0),
    BLACKFULLHELM(World.spawnableOffset + BLACK_FULL_HELM, 0),
    BLACKKITESHIELD(World.spawnableOffset + BLACK_KITESHIELD, 0),
    MITHRILPLATELEGS(World.spawnableOffset + MITHRIL_PLATELEGS, 0),
    MITHRILPLATESKIRT(World.spawnableOffset + MITHRIL_PLATESKIRT,0 ),
    MITHRILPLATEBODY(World.spawnableOffset + MITHRIL_PLATEBODY, 0),
    MITHRILFULLHELM(World.spawnableOffset + MITHRIL_FULL_HELM, 0),
    MITHRILKITESHIELD(World.spawnableOffset + MITHRIL_KITESHIELD, 0),
    ADAMANTPLATELEGS(World.spawnableOffset + ADAMANT_PLATELEGS, 0),
    ADAMANTPLATESKIRT(World.spawnableOffset + ADAMANT_PLATESKIRT, 0),
    ADAMANTPLATEBODY(World.spawnableOffset + ADAMANT_PLATEBODY, 0),
    ADAMANTFULLHELM(World.spawnableOffset + ADAMANT_FULL_HELM, 0),
    ADAMANTKITESHIELD(World.spawnableOffset + ADAMANT_KITESHIELD, 0),
    RUNEPLATELEGS(World.spawnableOffset + RUNE_PLATELEGS, 0),
    RUNEPLATESKIRT(World.spawnableOffset + RUNE_PLATESKIRT, 0),
    RUNEPLATEBODY(World.spawnableOffset + RUNE_PLATEBODY, 0),
    RUNEFULLHELM(World.spawnableOffset + RUNE_FULL_HELM, 0),
    RUNEKITESHIELD(World.spawnableOffset + RUNE_KITESHIELD, 0),
    GREEN_CHAPS(World.spawnableOffset + GREEN_DHIDE_CHAPS, 0),
    GREEN_VAMBRACES(World.spawnableOffset + GREEN_DHIDE_VAMB, 0),
    GREEN_BODY(World.spawnableOffset + GREEN_DHIDE_BODY, 0),
    BLUE_CHAPS(World.spawnableOffset + BLUE_DHIDE_CHAPS, 0),
    BLUE_VAMBRACES(World.spawnableOffset + BLUE_DHIDE_VAMB, 0),
    BLUE_BODY(World.spawnableOffset + BLUE_DHIDE_BODY, 0),
    RED_CHAPS(World.spawnableOffset + RED_DHIDE_CHAPS, 0),
    RED_VAMBRACES(World.spawnableOffset + RED_DHIDE_VAMB, 0),
    RED_BODY(World.spawnableOffset + RED_DHIDE_BODY, 0),
    BLACK_CHAPS(World.spawnableOffset + BLACK_DHIDE_CHAPS, 0),
    BLACK_VAMBRACES(World.spawnableOffset + BLACK_DHIDE_VAMB, 0),
    BLACK_BODY(World.spawnableOffset + BLACK_DHIDE_BODY, 0),
    ANTI_DRAGON_SHIELD(World.spawnableOffset + ANTIDRAGON_SHIELD, 0),
    /*
     * Weapons
     */
    BRONZE_SCIMITAR(World.spawnableOffset + ItemID.BRONZE_SCIMITAR, 0),
    IRON_SCIMITAR(World.spawnableOffset + ItemID.IRON_SCIMITAR, 0),
    STEEL_SCIMITAR(World.spawnableOffset + ItemID.STEEL_SCIMITAR, 0),
    BLACK_SCIMITAR(World.spawnableOffset + ItemID.BLACK_SCIMITAR, 0),
    MITHRIL_SCIMITAR(World.spawnableOffset + ItemID.MITHRIL_SCIMITAR,0),
    ADAMANT_SCIMITAR(World.spawnableOffset + ItemID.ADAMANT_SCIMITAR,0),
    RUNE_SCIMITAR(World.spawnableOffset + ItemID.RUNE_SCIMITAR, 0),
    DRAGON_SCIMITAR(World.spawnableOffset + ItemID.DRAGON_SCIMITAR, 0),
    DRAGON_LONGSWORD(World.spawnableOffset + ItemID.DRAGON_LONGSWORD, 0),
    DRAGON_DAGGER(World.spawnableOffset + ItemID.DRAGON_DAGGER, 0),
    DRAGON_DAGGER_P(World.spawnableOffset + ItemID.DRAGON_DAGGERP_5698, 0),
    DRAGON_BATTLEAXE(World.spawnableOffset + ItemID.DRAGON_BATTLEAXE, 0),
    DRAGON_MACE(World.spawnableOffset + ItemID.DRAGON_MACE, 0),
    DRAGON_2H(World.spawnableOffset + ItemID.DRAGON_2H_SWORD, 0),
    GRANITE_MAUL(World.spawnableOffset + ItemID.GRANITE_MAUL, 0),

    SHORTBOW(World.spawnableOffset + ItemID.SHORTBOW, 0),
    OAK_SHORTBOW(World.spawnableOffset + ItemID.OAK_SHORTBOW, 0),
    WILLOW_SHORTBOW(World.spawnableOffset + ItemID.WILLOW_SHORTBOW, 0),
    MAPLE_SHORTBOW(World.spawnableOffset + ItemID.MAPLE_SHORTBOW, 0),
    YEW_SHORTBOW(World.spawnableOffset + ItemID.YEW_SHORTBOW, 0),
    MAGIC_SHORTBOW(World.spawnableOffset + ItemID.MAGIC_SHORTBOW, 0),
    RUNE_CBOW(World.spawnableOffset + ItemID.RUNE_CROSSBOW, 0),
    BRONZE_ARROW(World.spawnableOffset + ItemID.BRONZE_ARROW, 0),
    IRON_ARROW(World.spawnableOffset + ItemID.IRON_ARROW, 0),
    STEEL_ARROW(World.spawnableOffset + ItemID.STEEL_ARROW, 0),
    MITHRIL_ARROW(World.spawnableOffset + ItemID.MITHRIL_ARROW, 0),
    ADAMANT_ARROW(World.spawnableOffset + ItemID.ADAMANT_ARROW, 0),
    RUNE_ARROW(World.spawnableOffset + ItemID.RUNE_ARROW, 0),

    AIR_STAFF(World.spawnableOffset + STAFF_OF_AIR, 0),
    AIR_BSTAFF(World.spawnableOffset + AIR_BATTLESTAFF, 0),
    WATER_STAFF(World.spawnableOffset + STAFF_OF_WATER, 0),
    WATER_BSTAFF(World.spawnableOffset + WATER_BATTLESTAFF, 0),
    EARTH_STAFF(World.spawnableOffset + STAFF_OF_EARTH, 0),
    EARTH_BSTAFF(World.spawnableOffset + EARTH_BATTLESTAFF, 0),
    FIRE_STAFF(World.spawnableOffset + STAFF_OF_FIRE, 0),
    FIRE_BSTAFF(World.spawnableOffset + FIRE_BATTLESTAFF, 0),
    ANCIENT_STAFF(World.spawnableOffset + ItemID.ANCIENT_STAFF, 0),
    /*
     * Runes
     */
    AIR_RUNE(World.spawnableOffset + ItemID.AIR_RUNE, 0),
    WATER_RUNE(World.spawnableOffset + ItemID.WATER_RUNE, 0),
    EARTH_RUNE(World.spawnableOffset + ItemID.EARTH_RUNE, 0),
    FIRE_RUNE(World.spawnableOffset + ItemID.FIRE_RUNE, 0),
    MIND_RUNE(World.spawnableOffset + ItemID.MIND_RUNE, 0),
    CHAOS_RUNE(World.spawnableOffset + ItemID.CHAOS_RUNE, 0),
    DEATH_RUNE(World.spawnableOffset + ItemID.DEATH_RUNE, 0),
    NATURE_RUNE(World.spawnableOffset + ItemID.NATURE_RUNE, 0),
    ASTRAL_RUNE(World.spawnableOffset + ItemID.ASTRAL_RUNE, 0),
    /*
     * Jewelry
     */
    RING_OF_RECOIL(World.spawnableOffset + ItemID.RING_OF_RECOIL, 0),
    RING_OF_LIFE(World.spawnableOffset + ItemID.RING_OF_LIFE, 0),
    AMULET_OF_ACCURACY(World.spawnableOffset + ItemID.AMULET_OF_ACCURACY, 0),
    AMULET_OF_STRENGTH(World.spawnableOffset + ItemID.AMULET_OF_STRENGTH, 0),
    AMULET_OF_GLORY(World.spawnableOffset + ItemID.AMULET_OF_GLORY4, 0),

    ZAMORAK_CAPE(World.spawnableOffset + ItemID.ZAMORAK_CAPE, 0),
    GUTHIX_CAPE(World.spawnableOffset + ItemID.GUTHIX_CAPE, 0),
    SARADOMIN_CAPE(World.spawnableOffset + ItemID.SARADOMIN_CAPE, 0),

    MITHRIL_GLOVES(World.spawnableOffset + ItemID.MITHRIL_GLOVES, 0),
    RUNE_GLOVES(World.spawnableOffset + ItemID.RUNE_GLOVES, 0),

    CLIMBING_BOOTS(World.spawnableOffset + ItemID.CLIMBING_BOOTS, 0),
    RUNE_BOOTS(World.spawnableOffset + ItemID.RUNE_BOOTS, 0)
    ;

    private int id, cost;

}

