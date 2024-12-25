package io.ruin.model.entity.player;

import io.ruin.model.achievements.Achievement;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum PlayerCounter {

    /**
     * Runecrafting
     */
    CRAFTED_AIR((p, amt) -> p.craftedAir = amt, p -> p.craftedAir, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_MIND((p, amt) -> p.craftedMind = amt, p -> p.craftedMind, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_WATER((p, amt) -> p.craftedWater = amt, p -> p.craftedWater, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_EARTH((p, amt) -> p.craftedEarth = amt, p -> p.craftedEarth, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_FIRE((p, amt) -> p.craftedFire = amt, p -> p.craftedFire, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_BODY((p, amt) -> p.craftedBody = amt, p -> p.craftedBody, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_COSMIC((p, amt) -> p.craftedCosmic = amt, p -> p.craftedCosmic, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_LAW((p, amt) -> p.craftedLaw = amt, p -> p.craftedLaw, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_NATURE((p, amt) -> p.craftedNature = amt, p -> p.craftedNature, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_CHAOS((p, amt) -> p.craftedChaos = amt, p -> p.craftedChaos, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_DEATH((p, amt) -> p.craftedDeath = amt, p -> p.craftedDeath, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_ASTRAL((p, amt) -> p.craftedAstral = amt, p -> p.craftedAstral, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_BLOOD((p, amt) -> p.craftedBlood = amt, p -> p.craftedBlood, Achievement.EXPERT_RUNECRAFTER),
    CRAFTED_SOUL((p, amt) -> p.craftedSoul = amt, p -> p.craftedSoul, Achievement.EXPERT_RUNECRAFTER),

    /**
     * Agility
     */
    GNOME_STRONGHOLD_COURSE((p, amt) -> p.gnomeStrongholdLaps = amt, p -> p.gnomeStrongholdLaps),
    BARBARIAN_COURSE((p, amt) -> p.barbarianCrouseLaps = amt, p -> p.barbarianCrouseLaps),
    WILDERNESS_COURSE((p, amt) -> p.wildernessCourseLaps = amt, p -> p.wildernessCourseLaps),
    DRAYNOR_ROOFTOP((p, amt) -> p.draynorRooftopLaps = amt, p -> p.draynorRooftopLaps),
    ALKHARID_ROOFTOP((p, amt) -> p.alKharidRooftopLaps = amt, p -> p.alKharidRooftopLaps),
    VARROCK_ROOFTOP((p, amt) -> p.varrockRooftopLaps = amt, p -> p.varrockRooftopLaps),
    CANIFIS_ROOFTOP((p, amt) -> p.canifisRooftopLaps = amt, p -> p.canifisRooftopLaps),
    FALADOR_ROOFTOP((p, amt) -> p.faladorRooftopLaps = amt, p -> p.faladorRooftopLaps),
    SEERS_ROOFTOP((p, amt) -> p.seersRooftopLaps = amt, p -> p.seersRooftopLaps),
    RELLEKKA_ROOFTOP((p, amt) -> p.rellekkaRooftopLaps = amt, p -> p.rellekkaRooftopLaps),
    ARDOUGNE_ROOFTOP((p, amt) -> p.ardougneRooftopLaps = amt, p -> p.ardougneRooftopLaps),

    /**
     * Woodcutting
     */
    CHOPPED_REGULAR((p, amt) -> p.choppedRegular = amt, p -> p.choppedRegular),
    CHOPPED_SAPLING((p, amt) -> p.choppedSapling = amt, p -> p.choppedSapling),
    CHOPPED_ACHEY((p, amt) -> p.choppedAchey = amt, p -> p.choppedAchey),
    CHOPPED_OAK((p, amt) -> p.choppedOak = amt, p -> p.choppedOak),
    CHOPPED_WILLOW((p, amt) -> p.choppedWillow = amt, p -> p.choppedWillow),
    CHOPPED_TEAK((p, amt) -> p.choppedTeak = amt, p -> p.choppedTeak),
    CHOPPED_JUNIPER((p, amt) -> p.choppedJuniper = amt, p -> p.choppedJuniper),
    CHOPPED_MAPLE((p, amt) -> p.choppedMaple = amt, p -> p.choppedMaple),
    CHOPPED_YEW((p, amt) -> p.choppedYew = amt, p -> p.choppedYew),
    CHOPPED_MAGIC((p, amt) -> p.choppedMagic = amt, p -> p.choppedMagic),
    CHOPPED_REDWOOD((p, amt) -> p.choppedRedwood = amt, p -> p.choppedRedwood),
    CHOPPED_MAHOGANY((p, amt) -> p.choppedMahogany = amt, p -> p.choppedMahogany),
    CHOPPED_CORRUPT((p, amt) -> p.choppedCorrupt = amt, p -> p.choppedCorrupt),
    OPENED_BIRDS_NESTS((p, amt) -> p.openedBirdsNests = amt, p -> p.openedBirdsNests),
    ACQUIRED_BIRDS_NESTS((p, amt) -> p.acquiredBirdsNests = amt, p -> p.acquiredBirdsNests),

    /**
     * Mining
     */
    MINED_CLAY((p, amt) -> p.minedClay = amt, p -> p.minedClay),
    MINED_COPPER((p, amt) -> p.minedCopper = amt, p -> p.minedCopper),
    MINED_TIN((p, amt) -> p.minedTin = amt, p -> p.minedTin),
    MINED_IRON((p, amt) -> p.minedIron = amt, p -> p.minedIron),
    MINED_SILVER((p, amt) -> p.minedSilver = amt, p -> p.minedSilver),
    MINED_COAL((p, amt) -> p.minedCoal = amt, p -> p.minedCoal),
    MINED_GOLD((p, amt) -> p.minedGold = amt, p -> p.minedGold, Achievement.GOLDEN_TOUCH),
    MINED_MITHRIL((p, amt) -> p.minedMithril = amt, p -> p.minedMithril),
    MINED_LOVAKITE((p, amt) -> p.minedLovakite = amt, p -> p.minedLovakite),
    MINED_ADAMANT((p, amt) -> p.minedAdamant = amt, p -> p.minedAdamant),
    MINED_RUNITE((p, amt) -> p.minedRunite = amt, p -> p.minedRunite),
    MINED_AMETHYST((p, amt) -> p.minedAmethyst = amt, p -> p.minedAmethyst),
    MINED_RUNE_ESSENCE((p, amt) -> p.minedRuneEssence = amt, p -> p.minedRuneEssence, Achievement.ESSENCE_EXTRACTOR),
    MINED_PURE_ESSENCE((p, amt) -> p.minedPureEssence = amt, p -> p.minedPureEssence, Achievement.ESSENCE_EXTRACTOR),
    MINED_DARK_ESSENCE((p, amt) -> p.minedDarkEssence = amt, p -> p.minedDarkEssence),
    MINED_GEODE((p, amt) -> p.minedGeode = amt, p -> p.minedGeode),
    MINED_SANDSTONE((p, amt) -> p.minedSandstone = amt, p -> p.minedSandstone),
    MINED_GRANITE((p, amt) -> p.minedGranite = amt, p -> p.minedGranite),
    MINED_GEM_ROCK((p, amt) -> p.minedGemRock = amt, p -> p.minedGemRock),
    MINED_CORRUPT((p, amt) -> p.minedCorrupt = amt, p -> p.minedCorrupt),

    /**
     * Smithing
     */
    SMELTED_BRONZE_BARS((p, amt) -> p.smeltedBronzeBars = amt, p -> p.smeltedBronzeBars),
    SMELTED_BLURITE_BARS((p, amt) -> p.smeltedBluriteBars = amt, p -> p.smeltedBluriteBars),
    SMELTED_IRON_BARS((p, amt) -> p.smeltedIronBars = amt, p -> p.smeltedIronBars),
    SMELTED_SILVER_BARS((p, amt) -> p.smeltedSilverBars = amt, p -> p.smeltedSilverBars),
    SMELTED_STEEL_BARS((p, amt) -> p.smeltedSteelBars = amt, p -> p.smeltedSteelBars),
    SMELTED_GOLD_BARS((p, amt) -> p.smeltedGoldBars = amt, p -> p.smeltedGoldBars, Achievement.GOLDEN_TOUCH),
    SMELTED_MITHRIL_BARS((p, amt) -> p.smeltedMithrilBars = amt, p -> p.smeltedMithrilBars),
    SMELTED_ADAMANT_BARS((p, amt) -> p.smeltedAdamantBars = amt, p -> p.smeltedAdamantBars),
    SMELTED_RUNITE_BARS((p, amt) -> p.smeltedRuniteBars = amt, p -> p.smeltedRuniteBars),
    SMELTED_CORRUPT_BARS((p, amt) -> p.smeltedCorruptBars = amt, p -> p.smeltedCorruptBars),

    /**
     * Thieving
     */
    VEGETABLE_STALL_THIEVES((p, amt) -> p.vegetableStallThieves = amt, p -> p.vegetableStallThieves),
    BAKER_STALL_THIEVES((p, amt) -> p.bakerStallThieves = amt, p -> p.bakerStallThieves),
    CRAFTING_STALL_THIEVES((p, amt) -> p.craftingStallThieves = amt, p -> p.craftingStallThieves),
    MONKEY_FOOD_STALL_THIEVES((p, amt) -> p.monkeyFoodStallThieves = amt, p -> p.monkeyFoodStallThieves),
    MONKEY_GENERAL_STALL_THIEVES((p, amt) -> p.monkeyGeneralStallThieves = amt, p -> p.monkeyGeneralStallThieves),
    TEA_STALL_THIEVES((p, amt) -> p.teaStallThieves = amt, p -> p.teaStallThieves),
    SILK_STALL_THIEVES((p, amt) -> p.silkStallThieves = amt, p -> p.silkStallThieves),
    WINE_STALL_THIEVES((p, amt) -> p.wineStallThieves = amt, p -> p.wineStallThieves),
    SEED_STALL_THIEVES((p, amt) -> p.seedStallThieves = amt, p -> p.seedStallThieves),
    FUR_STALL_THIEVES((p, amt) -> p.furStallThieves = amt, p -> p.furStallThieves),
    FISH_STALL_THIEVES((p, amt) -> p.fishStallThieves = amt, p -> p.fishStallThieves),
    CROSSBOW_STALL_THIEVES((p, amt) -> p.crossbowStallThieves = amt, p -> p.crossbowStallThieves),
    SILVER_STALL_THIEVES((p, amt) -> p.silverStallThieves = amt, p -> p.silverStallThieves),
    SPICE_STALL_THIEVES((p, amt) -> p.spiceStallThieves = amt, p -> p.spiceStallThieves),
    MAGIC_STALL_THIEVES((p, amt) -> p.magicStallThieves = amt, p -> p.magicStallThieves),
    SCIMITAR_STALL_THIEVES((p, amt) -> p.scimitarStallThieves = amt, p -> p.scimitarStallThieves),
    GEM_STALL_THIEVES((p, amt) -> p.gemStallThieves = amt, p -> p.gemStallThieves),
    MOR_GEM_STALL_THIEVES((p, amt) -> p.gemMorStallThieves = amt, p -> p.gemMorStallThieves),
    ORE_STALL_THIEVES((p, amt) -> p.oreStallThieves = amt, p -> p.oreStallThieves),
    PICKPOCKETED_MAN((p, amt) -> p.pickpocketMan = amt, p -> p.pickpocketMan),
    PICKPOCKETED_FARMER((p, amt) -> p.pickpocketFarmer = amt, p -> p.pickpocketFarmer),
    PICKPOCKETED_WARRIOR((p, amt) -> p.pickpocketWarrior = amt, p -> p.pickpocketWarrior),
    PICKPOCKETED_HAM_MEMBER((p, amt) -> p.pickpocketHamMember = amt, p -> p.pickpocketHamMember),
    PICKPOCKETED_ROGUE((p, amt) -> p.pickpocketRogue = amt, p -> p.pickpocketRogue),
    PICKPOCKETED_MASTER_FARMER((p, amt) -> p.pickpocketMasterFarmer = amt, p -> p.pickpocketMasterFarmer),
    PICKPOCKETED_GUARD((p, amt) -> p.pickpocketGuard = amt, p -> p.pickpocketGuard),
    PICKPOCKETED_BANDIT((p, amt) -> p.pickpocketBandit = amt, p -> p.pickpocketBandit),
    PICKPOCKETED_KNIGHT((p, amt) -> p.pickpocketKnight = amt, p -> p.pickpocketKnight),
    PICKPOCKETED_PALADIN((p, amt) -> p.pickpocketPaladin = amt, p -> p.pickpocketPaladin),
    PICKPOCKETED_GNOME((p, amt) -> p.pickpocketGnome = amt, p -> p.pickpocketGnome),
    PICKPOCKETED_HERO((p, amt) -> p.pickpocketHero = amt, p -> p.pickpocketHero),
    PICKPOCKETED_ELF((p, amt) -> p.pickpocketElf = amt, p -> p.pickpocketElf),
    PICKPOCKETED_TZHAAR_HUR((p, amt) -> p.pickpocketTzhaarHur = amt, p -> p.pickpocketTzhaarHur),
    WALL_SAFES_CRACKED((p, amt) -> p.wallSafesCracked = amt, p -> p.wallSafesCracked, Achievement.QUICK_HANDS),
    ROGUES_CASTLE_CHESTS((p, amt) -> p.rougesCastleChests = amt, p -> p.rougesCastleChests),
    PICKED_LOCKS((p, amt) -> p.pickedLocks = amt, p -> p.pickedLocks),

    /**
     * Firemaking
     */
    NORMAL_LOGS_BURNT((p, amt) -> p.normalLogsBurnt = amt, p -> p.normalLogsBurnt),
    ACHEY_LOGS_BURNT((p, amt) -> p.acheyLogsBurnt = amt, p -> p.acheyLogsBurnt),
    OAK_LOGS_BURNT((p, amt) -> p.oakLogsBurnt = amt, p -> p.oakLogsBurnt),
    WILLOW_LOGS_BURNT((p, amt) -> p.willowLogsBurnt = amt, p -> p.willowLogsBurnt),
    TEAK_LOGS_BURNT((p, amt) -> p.teakLogsBurnt = amt, p -> p.teakLogsBurnt),
    ARCTIC_PINE_LOGS_BURNT((p, amt) -> p.arcticPineLogsBurnt = amt, p -> p.arcticPineLogsBurnt),
    MAPLE_LOGS_BURNT((p, amt) -> p.mapleLogsBurnt = amt, p -> p.mapleLogsBurnt),
    MAHOGANY_LOGS_BURNT((p, amt) -> p.mahoganyLogsBurnt = amt, p -> p.mahoganyLogsBurnt),
    YEW_LOGS_BURNT((p, amt) -> p.yewLogsBurnt = amt, p -> p.yewLogsBurnt),
    MAGIC_LOGS_BURNT((p, amt) -> p.magicLogsBurnt = amt, p -> p.magicLogsBurnt),
    REDWOOD_LOGS_BURNT((p, amt) -> p.redwoodLogsBurnt = amt, p -> p.redwoodLogsBurnt),
    RED_LOGS_BURNT((p, amt) -> p.redLogsBurnt = amt, p -> p.redLogsBurnt),
    GREEN_LOGS_BURNT((p, amt) -> p.greenLogsBurnt = amt, p -> p.greenLogsBurnt),
    BLUE_LOGS_BURNT((p, amt) -> p.blueLogsBurnt = amt, p -> p.blueLogsBurnt),
    WHITE_LOGS_BURNT((p, amt) -> p.whiteLogsBurnt = amt, p -> p.whiteLogsBurnt),
    PURPLE_LOGS_BURNT((p, amt) -> p.purpleLogsBurnt = amt, p -> p.purpleLogsBurnt),
    KINDLING_BURNT((p, amt) -> p.kindlingBurnt = amt, p -> p.kindlingBurnt),

    /**
     * Cooking
     */
    JUGS_OF_WINE_MADE((p, amt) -> p.jugsOfWineMade = amt, p -> p.jugsOfWineMade),
    COOKED_FOOD((p, amt) -> p.cookedFood = amt, p -> p.cookedFood, Achievement.PRACTICE_MAKES_PERFECT),
    BURNT_FOOD((p, amt) -> p.burntFood = amt, p -> p.burntFood),
    COOKED_ON_FIRE((p, amt) -> p.cookedOnFire = amt, p -> p.cookedOnFire),

    /**
     * Prayer
     */
    REGULAR_BONES_BURIED((p, amt) -> p.regularBonesBuried = amt, p -> p.regularBonesBuried),
    BURNT_BONES_BURIED((p, amt) -> p.burntBonesBuried = amt, p -> p.burntBonesBuried),
    BAT_BONES_BURIED((p, amt) -> p.batBonesBuried = amt, p -> p.batBonesBuried),
    WOLF_BONES_BURIED((p, amt) -> p.wolfBonesBuried = amt, p -> p.wolfBonesBuried),
    BIG_BONES_BURIED((p, amt) -> p.bigBonesBuried = amt, p -> p.bigBonesBuried),
    LONG_BONES_BURIED((p, amt) -> p.longBonesBuried = amt, p -> p.longBonesBuried),
    CURVED_BONES_BURIED((p, amt) -> p.curvedBonesBuried = amt, p -> p.curvedBonesBuried),
    JOGRE_BONES_BURIED((p, amt) -> p.jogreBonesBuried = amt, p -> p.jogreBonesBuried),
    BABYDRAGON_BONES_BURIED((p, amt) -> p.babydragonBonesBuried = amt, p -> p.babydragonBonesBuried),
    DRAGON_BONES_BURIED((p, amt) -> p.dragonBonesBuried = amt, p -> p.dragonBonesBuried),
    ZOGRE_BONES_BURIED((p, amt) -> p.zogreBonesBuried = amt, p -> p.zogreBonesBuried),
    OURG_BONES_BURIED((p, amt) -> p.ourgBonesBuried = amt, p -> p.ourgBonesBuried),
    MONKEY_BONES_BURIED((p, amt) -> p.monkeyBonesBuried = amt, p -> p.monkeyBonesBuried),
    WYVERN_BONES_BURIED((p, amt) -> p.wyvernBonesBuried = amt, p -> p.wyvernBonesBuried),
    DAGANNOTH_BONES_BURIED((p, amt) -> p.dagannothBonesBuried = amt, p -> p.dagannothBonesBuried),
    LAVA_DRAGON_BONES_BURIED((p, amt) -> p.lavaDragonBonesBuried = amt, p -> p.lavaDragonBonesBuried),
    SUPERIOR_DRAGON_BONES_BURIED((p, amt) -> p.superiorDragonBonesBuried = amt, p -> p.superiorDragonBonesBuried),
    WYRM_BONES_BURIED((p, amt) -> p.wyrmBonesBuried = amt, p -> p.wyrmBonesBuried),
    DRAKE_BONES_BURIED((p, amt) -> p.drakeBonesBuried = amt, p -> p.drakeBonesBuried),
    HYDRA_BONES_BURIED((p, amt) -> p.hydraBonesBuried = amt, p -> p.hydraBonesBuried),
    REGULAR_BONES_ALTAR((p, amt) -> p.regularBonesAltar = amt, p -> p.regularBonesAltar),
    BURNT_BONES_ALTAR((p, amt) -> p.burntBonesAltar = amt, p -> p.burntBonesAltar),
    BAT_BONES_ALTAR((p, amt) -> p.batBonesAltar = amt, p -> p.batBonesAltar),
    WOLF_BONES_ALTAR((p, amt) -> p.wolfBonesAltar = amt, p -> p.wolfBonesAltar),
    BIG_BONES_ALTAR((p, amt) -> p.bigBonesAltar = amt, p -> p.bigBonesAltar),
    LONG_BONES_ALTAR((p, amt) -> p.longBonesAltar = amt, p -> p.longBonesAltar),
    CURVED_BONES_ALTAR((p, amt) -> p.curvedBonesAltar = amt, p -> p.curvedBonesAltar),
    JOGRE_BONES_ALTAR((p, amt) -> p.jogreBonesAltar = amt, p -> p.jogreBonesAltar),
    BABYDRAGON_BONES_ALTAR((p, amt) -> p.babydragonBonesAltar = amt, p -> p.babydragonBonesAltar),
    DRAGON_BONES_ALTAR((p, amt) -> p.dragonBonesAltar = amt, p -> p.dragonBonesAltar),
    ZOGRE_BONES_ALTAR((p, amt) -> p.zogreBonesAltar = amt, p -> p.zogreBonesAltar),
    OURG_BONES_ALTAR((p, amt) -> p.ourgBonesAltar = amt, p -> p.ourgBonesAltar),
    MONKEY_BONES_ALTAR((p, amt) -> p.monkeyBonesAltar = amt, p -> p.monkeyBonesAltar),
    WYVERN_BONES_ALTAR((p, amt) -> p.wyvernBonesAltar = amt, p -> p.wyvernBonesAltar),
    DAGANNOTH_BONES_ALTAR((p, amt) -> p.dagannothBonesAltar = amt, p -> p.dagannothBonesAltar),
    LAVA_DRAGON_BONES_ALTAR((p, amt) -> p.lavaDragonBonesAltar = amt, p -> p.lavaDragonBonesAltar),
    SUPERIOR_DRAGON_BONES_ALTAR((p, amt) -> p.superiorDragonBonesAltar = amt, p -> p.superiorDragonBonesAltar),
    WYRM_BONES_ALTAR((p, amt) -> p.wyrmBonesAltar = amt, p -> p.wyrmBonesAltar),
    DRAKE_BONES_ALTAR((p, amt) -> p.drakeBonesAltar = amt, p -> p.drakeBonesAltar),
    HYDRA_BONES_ALTAR((p, amt) -> p.hydraBonesAltar = amt, p -> p.hydraBonesAltar),

    /**
     * Farming
     */
    HARVESTED_POTATO((p, amt) -> p.harvestedPotato = amt, p -> p.harvestedPotato),
    HARVESTED_ONION((p, amt) -> p.harvestedOnion = amt, p -> p.harvestedOnion),
    HARVESTED_CABBAGE((p, amt) -> p.harvestedCabbage = amt, p -> p.harvestedCabbage),
    HARVESTED_TOMATO((p, amt) -> p.harvestedTomato = amt, p -> p.harvestedTomato),
    HARVESTED_SWEETCORN((p, amt) -> p.harvestedSweetcorn = amt, p -> p.harvestedSweetcorn),
    HARVESTED_STRAWBERRY((p, amt) -> p.harvestedStrawberry = amt, p -> p.harvestedStrawberry),
    HARVESTED_WATERMELON((p, amt) -> p.harvestedWatermelon = amt, p -> p.harvestedWatermelon),
    HARVESTED_MARIGOLDS((p, amt) -> p.harvestedMarigolds = amt, p -> p.harvestedMarigolds),
    HARVESTED_ROSEMARY((p, amt) -> p.harvestedRosemary = amt, p -> p.harvestedRosemary),
    HARVESTED_NASTURTIUM((p, amt) -> p.harvestedNasturtium = amt, p -> p.harvestedNasturtium),
    HARVESTED_WOAD((p, amt) -> p.harvestedWoad = amt, p -> p.harvestedWoad),
    HARVESTED_LIMPWURT((p, amt) -> p.harvestedLimpwurt = amt, p -> p.harvestedLimpwurt),

    HARVESTED_GUAM((p, amt) -> p.harvestedGuam = amt, p -> p.harvestedGuam, Achievement.MY_ARMS_PATCH),
    HARVESTED_MARRENTILL((p, amt) -> p.harvestedMarrentill = amt, p -> p.harvestedMarrentill, Achievement.MY_ARMS_PATCH),
    HARVESTED_TARROMIN((p, amt) -> p.harvestedTarromin = amt, p -> p.harvestedTarromin, Achievement.MY_ARMS_PATCH),
    HARVESTED_HARRALANDER((p, amt) -> p.harvestedHarralander = amt, p -> p.harvestedHarralander, Achievement.MY_ARMS_PATCH),
    HARVESTED_RANARR((p, amt) -> p.harvestedRanarr = amt, p -> p.harvestedRanarr, Achievement.MY_ARMS_PATCH),
    HARVESTED_TOADFLAX((p, amt) -> p.harvestedToadflax = amt, p -> p.harvestedToadflax, Achievement.MY_ARMS_PATCH),
    HARVESTED_IRIT((p, amt) -> p.harvestedIrit = amt, p -> p.harvestedIrit, Achievement.MY_ARMS_PATCH),
    HARVESTED_AVANTOE((p, amt) -> p.harvestedAvantoe = amt, p -> p.harvestedAvantoe, Achievement.MY_ARMS_PATCH),
    HARVESTED_KWUARM((p, amt) -> p.harvestedKwuarm = amt, p -> p.harvestedKwuarm, Achievement.MY_ARMS_PATCH),
    HARVESTED_SNAPDRAGON((p, amt) -> p.harvestedSnapdragon = amt, p -> p.harvestedSnapdragon, Achievement.MY_ARMS_PATCH),
    HARVESTED_CADANTINE((p, amt) -> p.harvestedCadantine = amt, p -> p.harvestedCadantine, Achievement.MY_ARMS_PATCH),
    HARVESTED_LANTADYME((p, amt) -> p.harvestedLantadyme = amt, p -> p.harvestedLantadyme, Achievement.MY_ARMS_PATCH),
    HARVESTED_DWARF_WEED((p, amt) -> p.harvestedDwarfWeed = amt, p -> p.harvestedDwarfWeed, Achievement.MY_ARMS_PATCH),
    HARVESTED_TORSTOL((p, amt) -> p.harvestedTorstol = amt, p -> p.harvestedTorstol, Achievement.MY_ARMS_PATCH),

    HARVESTED_BITTERCAP((p, amt) -> p.harvestedBittercap = amt, p -> p.harvestedBittercap),
    GROWN_APPLE((p, amt) -> p.grownApple = amt, p -> p.grownApple),
    GROWN_BANANA((p, amt) -> p.grownBanana = amt, p -> p.grownBanana),
    GROWN_ORANGE((p, amt) -> p.grownOrange = amt, p -> p.grownOrange),
    GROWN_CURRY((p, amt) -> p.grownCurry = amt, p -> p.grownCurry),
    GROWN_PINEAPPLE((p, amt) -> p.grownPineapple = amt, p -> p.grownPineapple),
    GROWN_PAPAYA((p, amt) -> p.grownPapaya = amt, p -> p.grownPapaya),
    GROWN_PALM((p, amt) -> p.grownPalm = amt, p -> p.grownPalm),
    GROWN_OAK((p, amt) -> p.grownOak = amt, p -> p.grownOak),
    GROWN_WILLOW((p, amt) -> p.grownWillow = amt, p -> p.grownWillow),
    GROWN_MAPLE((p, amt) -> p.grownMaple = amt, p -> p.grownMaple),
    GROWN_YEW((p, amt) -> p.grownYew = amt, p -> p.grownYew),
    GROWN_MAGIC((p, amt) -> p.grownMagic = amt, p -> p.grownMagic),
    GROWN_REDBERRY((p, amt) -> p.grownRedberry = amt, p -> p.grownRedberry),
    GROWN_CADAVABERRY((p, amt) -> p.grownCadavaberry = amt, p -> p.grownCadavaberry),
    GROWN_DWELLBERRY((p, amt) -> p.grownDwellberry = amt, p -> p.grownDwellberry),
    GROWN_JANGERBERRY((p, amt) -> p.grownJangerberry = amt, p -> p.grownJangerberry),
    GROWN_WHITEBERRY((p, amt) -> p.grownWhiteberry = amt, p -> p.grownWhiteberry),
    GROWN_POISON_IVY((p, amt) -> p.grownPoisonIvy = amt, p -> p.grownPoisonIvy),
    HARVESTED_BARLEY((p, amt) -> p.harvestedBarley = amt, p -> p.harvestedBarley),
    HARVESTED_HAMMERSTONE((p, amt) -> p.harvestedHammerstone = amt, p -> p.harvestedHammerstone),
    HARVESTED_ASGARNIAN((p, amt) -> p.harvestedAsgarnian = amt, p -> p.harvestedAsgarnian),
    HARVESTED_JUTE((p, amt) -> p.harvestedJute = amt, p -> p.harvestedJute),
    HARVESTED_YANILLIAN((p, amt) -> p.harvestedYanillian = amt, p -> p.harvestedYanillian),
    HARVESTED_KRANDORIAN((p, amt) -> p.harvestedKrandorian = amt, p -> p.harvestedKrandorian),
    HARVESTED_WILDBLOOD((p, amt) -> p.harvestedWildblood = amt, p -> p.harvestedWildblood),
    GROWN_CALQUAT((p, amt) -> p.grownCalquat = amt, p -> p.grownCalquat),
    GROWN_CACTUS((p, amt) -> p.grownCactus = amt, p -> p.grownCactus),
    GROWN_SPIRIT_TREE((p, amt) -> p.grownSpiritTree = amt, p -> p.grownSpiritTree),

    /**
     * Fishing
     */
    TOTAL_FISH((p, amt) -> p.totalFishCaught = amt, p -> p.totalFishCaught),

    /**
     * Motherlode mine
     */
    MINED_PAYDIRT((p, amt) -> p.minedPaydirt = amt, p -> p.minedPaydirt),
    CLEANED_PAYDIRT((p, amt) -> p.cleanedPaydirt = amt, p -> p.cleanedPaydirt, Achievement.DOWN_IN_THE_DIRT),

    /**
     * Wintertodt
     */
    LIFETIME_WINTERTODT_POINTS((p, amt) -> p.lifetimeWintertodtPoints = amt, p -> p.lifetimeWintertodtPoints),
    WINTERTODT_SUBDUED((p, amt) -> p.wintertodtSubdued = amt, p -> p.wintertodtSubdued),

    /**
     * Hunter
     */
    CAUGHT_SWIFT((p, amt) -> p.caughtSwift = amt, p -> p.caughtSwift),
    CAUGHT_WARBLER((p, amt) -> p.caughtWarbler = amt, p -> p.caughtWarbler),
    CAUGHT_LONGTAIL((p, amt) -> p.caughtLongtail = amt, p -> p.caughtLongtail),
    CAUGHT_TWITCH((p, amt) -> p.caughtTwitch = amt, p -> p.caughtTwitch),
    CAUGHT_WAGTAIL((p, amt) -> p.caughtWagtail = amt, p -> p.caughtWagtail),

    CAUGHT_GREY_CHINCHOMPA((p, amt) -> p.caughtGreyChinchompa = amt, p -> p.caughtGreyChinchompa),
    CAUGHT_RED_CHINCHOMPA((p, amt) -> p.caughtRedChinchompa = amt, p -> p.caughtRedChinchompa),
    CAUGHT_BLACK_CHINCHOMPA((p, amt) -> p.caughtBlackChinchompa = amt, p -> p.caughtBlackChinchompa),

    CAUGHT_SWAMP_LIZARD((p, amt) -> p.caughtSwampLizard = amt, p -> p.caughtSwampLizard),
    CAUGHT_ORANGE_SALAMANDER((p, amt) -> p.caughtOrangeSalamander = amt, p -> p.caughtOrangeSalamander),
    CAUGHT_RED_SALAMANDER((p, amt) -> p.caughtRedSalamander = amt, p -> p.caughtRedSalamander),
    CAUGHT_BLACK_SALAMANDER((p, amt) -> p.caughtBlackSalamander = amt, p -> p.caughtBlackSalamander),

    /**
     * PVM
     */
    ABYSSAL_CREATURES_KC((p, amt) -> p.abyssCreaturesKilled = amt, p -> p.abyssCreaturesKilled, Achievement.ABYSSAL_DISTURBANCE),

    /**
     * Loyalty chest
     */
    LOYALTY_CHEST_OPENED((p, amt) -> p.loyaltyChestCount = amt, p -> p.loyaltyChestCount),

    /**
     * TP Portal
     */
    TELEPORT_PORTAL_USES((p, amt) -> p.teleportPortalUses = amt, p -> p.teleportPortalUses),

    /**
     * Slayer tasks
     */
    SLAYER_TASKS_COMPLETED((p, amt) -> p.slayerTasksCompleted = amt, p -> p.slayerTasksCompleted, Achievement.COMMENCE_SLAUGHTER),
    WILDERNESS_SLAYER_TASKS_COMPLETED((p, amt) -> p.wildernessTasksCompleted = amt, p -> p.wildernessTasksCompleted, Achievement.COMMENCE_SLAUGHTER),

    /**
     * Presets
     */
    PRESETS_LOADED((p, amt) -> p.presetsLoaded = amt, p -> p.presetsLoaded),

    /**
     * Daily tasks
     */
    DAILY_TASKS_COMPLETED((p, amt) -> p.dailyTasksCompleted = amt, p -> p.dailyTasksCompleted),

    DEMON_KILLS((p, amt) -> p.demonKills = amt, p -> p.demonKills, Achievement.DEMON_SLAYER),

    IMPLINGS_CAUGHT((p, amt) -> p.implingCaught = amt, p -> p.implingCaught, Achievement.IMPLING_HUNTER),
    ;

    private final BiConsumer<Player, Integer> setAction;

    private final Function<Player, Integer> getAction;

    private final Achievement[] achievements;

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    PlayerCounter(BiConsumer<Player, Integer> setAction, Function<Player, Integer> getAction) {
        this(setAction, getAction, null);
    }

    PlayerCounter(BiConsumer<Player, Integer> setAction, Function<Player, Integer> getAction, Achievement... achievements) {
        this.setAction = setAction;
        this.getAction = getAction;
        this.achievements = achievements;
    }

    public final void increment(Player player, int amount) {
        set(player, get(player) + amount);
    }

    public final void decrement(Player player, int amount) {
        set(player, get(player) - amount);
    }

    public final void set(Player player, int amount) {
        setAction.accept(player, amount);
        if(achievements != null) {
            for(Achievement achievement : achievements)
                achievement.update(player);
        }
    }

    public final int get(Player player) {
        return getAction.apply(player);
    }

}