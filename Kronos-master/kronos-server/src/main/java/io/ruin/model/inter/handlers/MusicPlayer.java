package io.ruin.model.inter.handlers;

import io.ruin.Server;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.EnumMap;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.MapListener;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private static Track[] TRACKS;

    static {
        Object[][] trackData = {
                //displayName, musicArchive (name or id), ...regionIds
                {"7th Realm", "7th realm", 10645, 10644},
                {"Adventure", "adventure", 12854},
                {"Al Kharid", "al kharid", 13105},
                {"All's Fairy in Love & War", "alls fairy in love n war"}, //todo
                {"Alone", "alone", 12086, 10134},
                {"Altar Ego", "altar_ego", 13131},
                {"Alternative Root", "alternative root"}, //todo
                {"Ambient Jungle", "ambient jungle", 11310},
                {"Anywhere", "anywhere", 10795},
                {"Arabian", "arabian", 13617, 13106},
                {"Arabian 2", "arabian2", 13107},
                {"Arabian 3", "arabian3", 12848},
                {"Arabique", "arabique", 11417},
                {"Arcane", "unknown"}, //todo
                {"Armadyl Alliance", "armadyl alliance"}, //todo
                {"Armageddon", "armageddon"}, //todo
                {"Army Of Darkness", "army of darkness", 12088},
                {"Arrival", "arrival", 11572},
                {"Artistry", "artistry", 8010},
                {"Ascent", "ascent"}, //todo
                {"Assault and Battery", "assault and battery"}, //todo
                {"Attack 1", "attack1", 10034},
                {"Attack 2", "attack2", 11414},
                {"Attack 3", "attack3", 12192},
                {"Attack 4", "attack4", 10289, 10389},
                {"Attack 5", "attack5", 9033},
                {"Attack 6", "attack6", 10387},
                {"Attention", "attention", 11825},
                {"Autumn Voyage", "autumn voyage", 12851},
                {"Aye Car Rum Ba", "aye car rum ba", 8527},
                {"Aztec", "aztec", 11157},
                {"Back to Life", "back to life"}, //todo
                {"Background", "background", 11316, 11060},
                {"Ballad Of Enchantment", "ballad of enchantment", 10290},
                {"Bandit Camp", "bandit camp", 12590},
                {"Bandos Battalion", "bandos battalion"}, //todo
                {"Bane", "corporealbeast"}, //todo
                {"Barb Wire", "barb wire"}, //todo
                {"Barbarianism", "barbarianism", 12341, 12441},
                {"Barking Mad", "barking mad", 14234},
                {"Baroque", "baroque", 10547},
                {"Beetle Juice", "beetle juice"}, //todo
                {"Beneath the Stronghold", "beneath_the_stronghold"}, //todo
                {"Beyond", "beyond", 11418, 11419},
                {"Big Chords", "big chords", 10032},
                {"Blistering Barnacles", "blistering barnacles", 8528},
                {"Body Parts", "body parts", 13979},
                {"Bone Dance", "bone dance", 13619},
                {"Bone Dry", "bone dry", 12946},
                {"Book Of Spells", "book of spells", 12593},
                {"Borderland", "borderland", 10809},
                {"Breeze", "breeze", 9010},
                {"Brew Hoo Hoo!", "brew hoo hoo", 14747},
                {"Brimstail's Scales", "brimstail's scales"}, //todo
                {"Bubble and Squeak", "bubble and squeak", 7753},
                {"Bunny's Sugar Rush", "bunny_sugar_rush"}, //todo
                {"Cabin Fever", "cabin fever"}, //todo
                {"Camelot", "camelot", 11602},
                {"Castlewars", "castlewars", 9520},
                {"Catch me if you can", "catch me if you can", 10646},
                {"Cave Background", "cave background", 12184, 11929},
                {"Cave of Beasts", "cave of beasts", 11165},
                {"Cave of the Goblins", "cave of the goblins", 12693},
                {"Cavern", "cavern", 12193, 10388},
                {"Cellar Song", "cellar song", 12697},
                {"Chain Of Command", "chain of command", 10648, 10905},
                {"Chamber", "chamber", 10821, 11078}, //todo
                {"Chef Surprise", "chef surprize", 7507},
                {"Chickened Out", "chickened out", 9796},
                {"Chompy Hunt", "chompy hunt", 10542, 10642},
                {"City of the Dead", "city of the dead", 12843, 13099},
                {"Clan Wars", "clanwars"}, //todo
                {"Claustrophobia", "claustrophobia", 9293},
                {"Close Quarters", "close quarters", 12602, 6234},
                {"Coil", "coil", 9007},
                {"Competition", "competition", 8781},
                {"Complication", "complication", 9035},
                {"Contest", "contest", 11576},
                {"Corporal Punishment", "corporal punishment", 12619},
                {"Corridors of Power", "corridors of power"}, //todo
                {"Country Jig", "unknown", 6454},
                {"Courage", "courage", 11826},
                {"Creature Cruelty", "creature cruelty", 9011},
                {"Crystal Castle", "crystal castle", 9797},
                {"Crystal Cave", "crystal cave"}, //todo
                {"Crystal Sword", "crystal sword", 12855, 10647},
                {"Cursed", "cursed", 9623},
                {"Dagannoth Dawn", "dagannoth dawn", 7236, 7748},
                {"Dance of Death", "dance of death", 14131},
                {"Dance of the Undead", "dance of the undead", 14131},
                {"Dangerous", "dangerous", 12343},
                {"Dangerous Road", "dangerous road", 11413},
                {"Dangerous Way", "dangerous way", 14231},
                {"Dark", "dark", 13369},
                {"Darkly Altared", "darkly_altared"}, //todo
                {"Darkness in the Depths", "darkness_in_the_depths"}, //todo
                {"Davy Jones' Locker", "davy jones locker", 11924},
                {"Dead Can Dance", "dead can dance", 12601},
                {"Dead Quiet", "dead quiet", 13621, 9294},
                {"Deadlands", "deadlands", 14390, 14134},
                {"Deep Down", "deep down", 10823, 10822},
                {"Deep Wildy", "deep wildy", 11835},
                {"Desert Heat", "desert heat", 13614},
                {"Desert Voyage", "desert voyage", 13102, 13359},
                {"Devils May Care", "devils_may_care"}, //todo
                {"Diango's Little Helpers", "diango's little helpers", 8005},
                {"Dies Irae", "dies_irae"}, //todo
                {"Dimension X", "dimension x"}, //todo
                {"Distant Land", "distant land", 13873},
                {"Distillery Hilarity", "distillery hilarity"}, //todo
                {"Dogs of War", "dogs of war"}, //todo
                {"Doorways", "doorways", 12599, 13110},
                {"Dorgeshuun City", "dorgeshun city"}, //todo
                {"Dorgeshuun Deep", "dorgeshun deep"}, //todo
                {"Down Below", "down below", 12438},
                {"Down To Earth", "down to earth", 10571},
                {"Down and Out", "down and out"}, //todo
                {"Down by the Docks", "unknown"}, //todo
                {"Dragontooth Island", "dragontooth island", 15159},
                {"Dream", "dream", 12594},
                {"Dreamstate", "dreamstate"}, //todo
                {"Duel Arena", "duel arena", 13362},
                {"Dunjun", "dunjun", 11672},
                {"Dwarf Theme", "dwarf theme", 12085},
                {"Dwarven Domain", "unknown"}, //todo
                {"Dynasty", "dynasty", 13358},
                {"Eagles' Peak", "eagle peak"}, //todo
                {"Easter Jig", "easter jig"}, //todo
                {"Egypt", "egypt", 13104},
                {"Elven Mist", "elven mist", 9266},
                {"Emotion", "emotion", 10033, 10309, 10133},
                {"Emperor", "emperor", 11570, 11670},
                {"Escape", "escape", 10903},
                {"Espionage", "espionage"}, //todo
                {"Etceteria", "etcetera", 10300},
                {"Everlasting", "everlasting"}, //todo
                {"Everlasting Fire", "everlasting fire", 13373},
                {"Everywhere", "everywhere", 8499},
                {"Evil Bob's Island", "evil bobs island", 10058},
                {"Expanse", "expanse", 12852, 12952},
                {"Expecting", "expecting", 9778, 9878},
                {"Expedition", "expedition", 11676},
                {"Exposed", "exposed", 8752},
                {"Faerie", "faerie", 9540},
                {"Faithless", "faithless", 12856},
                {"Fanfare", "fanfare", 11828},
                {"Fanfare 2", "fanfare2", 11823},
                {"Fanfare 3", "fanfare3", 10545},
                {"Fangs for the Memory", "fangs for the memory"}, //todo
                {"Far Away", "far away", 9265},
                {"Fe Fi Fo Fum", "fe fi fo fum"}, //todo
                {"Fear and Loathing", "fear and loathing"}, //todo
                {"Fenkenstrain's Refrain", "fenkenstrain's refrain", 13879, 14135},
                {"Fight or Flight", "fight or flight", 7752},
                {"Find My Way", "find my way", 10894},
                {"Fire and Brimstone", "fire and brimstone", 9552},
                {"Fire in the Deep", "unknown"}, //todo
                {"Fishing", "fishing", 11317},
                {"Floating Free", "floating free"}, //todo
                {"Flute Salad", "flute salad", 12595},
                {"Food for Thought", "food for thought"}, //todo
                {"Forbidden", "forbidden", 13111},
                {"Forest", "forest", 9009},
                {"Forever", "forever", 12342},
                {"Forgettable Melody", "forgettable melody"}, //todo
                {"Forgotten", "forgotten"}, //todo
                {"Frogland", "frogland", 9802},
                {"Frostbite", "frostbite", 11323},
                {"Fruits de Mer", "fruits de mer", 11059},
                {"Funny Bunnies", "funny bunnies", 9810},
                {"Gaol", "gaol", 12090, 10031, 10131},
                {"Garden", "garden", 12853},
                {"Garden of Autumn", "garden of autumn"}, //todo
                {"Garden of Spring", "garden of spring"}, //todo
                {"Garden of Summer", "garden of summer"}, //todo
                {"Garden of Winter", "garden of winter"}, //todo
                {"Gnome King", "gnome king", 9782},
                {"Gnome Village", "gnome village", 9781},
                {"Gnome Village 2", "gnome village2", 9269},
                {"Gnome Village Party", "gnome_village_party"}, //todo
                {"Gnomeball", "gnomeball", 9270},
                {"Goblin Game", "goblin game", 10393},
                {"Goblin Village", "goblin village"}, //todo
                {"Golden Touch", "golden touch"}, //todo
                {"Greatness", "greatness", 12596},
                {"Grimly Fiendish", "grimly_fiendish", 6475, 6731},
                {"Grip of the Talon", "grip of the talon"}, //todo
                {"Grotto", "grotto", 13720},
                {"Ground Scape", "ground scape"}, //todo
                {"Grumpy", "grumpy", 10286},
                {"H.A.M. Attack", "ham attack"}, //todo
                {"H.A.M. Fisted", "ham fisted"}, //todo
                {"H.A.M. and Seek", "ham and seek"}, //todo
                {"Harmony", "harmony", 12850},
                {"Harmony 2", "harmony2", 12950},
                {"Haunted Mine", "haunted mine", 11077},
                {"Have a Blast", "have a blast", 7757},
                {"Have an ice day", "have an ice day"}, //todo
                {"Head to Head", "head to head", 7504},
                {"Heart and Mind", "heart and mind", 10059},
                {"Hells Bells", "hells bells", 11066},
                {"Hermit", "hermit", 9034},
                {"High Seas", "high seas", 11057},
                {"High Spirits", "high spirits"}, //todo
                {"Home Sweet Home", "home sweet home"}, //todo
                {"HomeScape", "homescape"},
                {"Horizon", "horizon", 11573},
                {"Hypnotized", "hypnotized"}, //todo
                {"Iban", "iban", 8519},
                {"Ice Melody", "ice melody", 11318},
                {"Ice and Fire", 483, 6462},
                {"Illusive", "illusive"}, //todo
                {"Impetuous", "impetuous"}, //todo
                {"In Between", "in between", 10061},
                {"In The Manor", "in the manor", 10287},
                {"In The Pits", "in the pits", 9808},
                {"In the Brine", "in the brine", 14638},
                {"In the Clink", "in the clink", 8261},
                {"Inadequacy", "inadequacy"}, //todo
                {"Incantation", "incantation"}, //todo
                {"Inferno", "inferno"}, //todo
                {"Insect Queen", "insect queen", 13972},
                {"Inspiration", "inspiration", 12087},
                {"Into the Abyss", "into the abyss", 12107},
                {"Intrepid", "intrepid", 9369}, //todo
                {"Invader", "sire"}, //todo
                {"Island Life", "island life", 10794},
                {"Island of the Trolls", "island of the trolls"}, //todo
                {"Isle of Everywhere", "isle of everywhere"}, //todo
                {"Jester Minute", "jester minute"}, //todo
                {"Jolly R", "jollyr", 11058},
                {"Joy of the Hunt", "joy of the hunt"}, //todo
                {"Jungle Bells", "jungle bells"}, //todo
                {"Jungle Hunt", "jungle hunt"}, //todo
                {"Jungle Island", "jungle island", 11313, 11309},
                {"Jungle Island Xmas", "jungle island xmas"}, //todo
                {"Jungle Troubles", "jungle troubles", 11568},
                {"Jungly 1", "jungly1", 11054, 1154},
                {"Jungly 2", "jungly2", 10802},
                {"Jungly 3", "jungly3", 11055},
                {"Karamja Jam", "karamja jam", 10900, 10899},
                {"Kingdom", "kingdom", 11319},
                {"Knightly", "knightly", 10291},
                {"Knightmare", "knightmare"}, //todo
                {"Kourend the Magnificent", "kourend_the_magnificent"}, //todo
                {"La Mort", "la mort", 8779},
                {"Labyrinth", "labyrinth"}, //todo
                {"Lair", "lair", 13975},
                {"Lament", "lament", 12433},
                {"Lament of Meiyerditch", "lament of meiyerditch"}, //todo
                {"Land Down Under", "land down under"}, //todo
                {"Land of the Dwarves", "land of the dwarves", 11423},
                {"Landlubber", "landlubber", 10801},
                {"Last Man Standing", "last_man_standing"}, //todo
                {"Last Stand", "last stand"}, //todo
                {"Lasting", "lasting", 10549},
                {"Legend", "legend", 10808},
                {"Legion", "legion", 12089, 10039},
                {"Life's a Beach!", "life's a beach!"}, //todo
                {"Lighthouse", "lighthouse", 10040},
                {"Lightness", "lightness", 12599, 12343},
                {"Lightwalk", "lightwalk", 11061},
                {"Little Cave of Horrors", "little cave of horrors"}, //todo
                {"Lonesome", "lonesome", 13203},
                {"Long Ago", "long ago", 10544},
                {"Long Way Home", "long way home", 11826},
                {"Looking Back", "looking back"}, //todo
                {"Lore and Order", "lore and order"}, //todo
                {"Lost Soul", "lost soul", 9008},
                {"Lower Depths", "lower_depths"}, //todo
                {"Lullaby", "lullaby", 13365, 10551},
                {"Mad Eadgar", "mad eadgar", 11677},
                {"Mage Arena", "mage arena", 12349, 10057},
                {"Magic Dance", "magic dance", 10288}, //todo
                {"Magic, Magic, Magic", "magic magic magic"}, //todo
                {"Magical Journey", "magical journey", 10805},
                {"Major Miner", "major miner"}, //todo
                {"Making Waves", "making waves", 9273, 9272}, //todo
                {"Malady", "malady"}, //todo
                {"March", "march", 10036},
                {"March of the Shayzien", "shayzien_march"}, //todo
                {"Marooned", "marooned", 11562, 12117},
                {"Marzipan", "marzipan", 11166, 11421},
                {"Masquerade", "masquerade", 10908},
                {"Mastermindless", "mastermindless"}, //todo
                {"Mausoleum", "mausoleum", 13722},
                {"Maws, Jaws & Claws", "maws_jaws_claws", 4883, 5395, 5140},
                {"Meddling Kids", "meddling kids"}, //todo
                {"Medieval", "medieval", 13109},
                {"Mellow", "mellow", 10293},
                {"Melodrama", "melodrama", 9776},
                {"Melzar's Maze", "melzars maze"}, //todo
                {"Meridian", "meridian", 8497},
                {"Method of Madness", "method of madness"}, //todo
                {"Miles Away", "miles away", 11571, 10569},
                {"Mind over Matter", "mind over matter"}, //todo
                {"Miracle Dance", "miracle dance", 11083},
                {"Mirage", "mirage", 13199},
                {"Miscellania", "miscellania", 10044},
                {"Monarch Waltz", "monarch waltz", 10807},
                {"Monkey Badness", "monkey badness", 11051},
                {"Monkey Business", "monkey business"}, //todo
                {"Monkey Madness", "monkey madness"}, //todo
                {"Monkey Sadness", "monkey sadness"}, //todo
                {"Monkey Trouble", "monkey trouble"}, //todo
                {"Monster Melee", "monster melee", 12694},
                {"Moody", "moody", 12600, 9523},
                {"Mor Ul Rek", "mor-ul-rek"}, //todo
                {"Morytania", "morytania", 13622},
                {"Mouse Trap", "mouse trap"}, //todo
                {"Mudskipper Melody", "mudskipper melody", 11824},
                {"Mutant Medley", "mutant medley"}, //todo
                {"My Arm's Journey", "my arms journey"}, //todo
                {"Narnode's Theme", "narnode's theme", 9882},
                {"Natural", "natural", 13620, 9038},
                {"Nether Realm", "nether_realm"}, //todo
                {"Neverland", "neverland", 9780},
                {"Newbie Melody", "newbie melody"},
                {"Night of the Vampyre", "unknown"}, //todo
                {"Nightfall", "nightfall", 12861, 11827},
                {"No Way Out", "no way out", 13209, 12369, 12113},
                {"Nomad", "nomad", 11056},
                {"Norse Code", "norse code"}, //todo
                {"Nox Irae", "nox_irae"}, //todo
                {"Null and Void", "null and void", 10537},
                {"Ogre the top", "ogre the top"}, //todo
                {"On the Up", "on the up"}, //todo
                {"On the Wing", "on the wing"}, //todo
                {"Oriental", "oriental", 11666},
                {"Out of the Deep", "out of the deep", 10140},
                {"Over to Nardah", "over to nardah", 13613},
                {"Overpass", "overpass", 9267},
                {"Overture", "overture", 10806},
                {"Parade", "parade", 13366},
                {"Path of Peril", "path of peril", 10575},
                {"Pathways", "pathways", 10901},
                {"Pest Control", "pest control", 10536},
                {"Pharaoh's Tomb", "pharoah's tomb", 13356, 12105},
                {"Phasmatys", "phasmatys", 14746},
                {"Pheasant Peasant", "pheasant peasant", 10314},
                {"Pick & Shovel", "pick_and_shovel"}, //todo
                {"Pinball Wizard", "pinball wizard"}, //todo
                {"Pirates of Penance", "pirates of penance"}, //todo
                {"Pirates of Peril", "pirates of peril", 12093},
                {"Poles Apart", "poles apart"}, //todo
                {"Prime Time", "prime time"}, //todo
                {"Principality", "principality", 11575},
                {"Quest", "quest", 10315},
                {"Rat Hunt", "rat hunt", 11343},
                {"Rat a Tat Tat", "rat a tat tat", 11599},
                {"Ready for Battle", "ready for battle", 9620},
                {"Regal", "regal", 13117},
                {"Reggae", "reggae", 11565},
                {"Reggae 2", "reggae2", 11567},
                {"Rellekka", "rellekka", 10297},
                {"Right on Track", "right on track"}, //todo
                {"Righteousness", "righteousness", 9803},
                {"Rising Damp", "rising damp"}, //todo
                {"Riverside", "riverside", 10803, 8396},
                {"Roc and Roll", "roc and roll"}, //todo
                {"Roll the Bones", "roll the bones"}, //todo
                {"Romancing the Crone", "romancing the crone", 11068},
                {"Romper Chomper", "romper chomper", 9263},
                {"Royale", "royale", 11671},
                {"Rugged Terrain", "rugged_terrain"}, //todo
                {"Rune Essence", "rune essence", 11595},
                {"Sad Meadow", "sad meadow", 10035, 11081},
                {"Saga", "saga", 10296},
                {"Sarcophagus", "sarcophagus", 12945},
                {"Sarim's Vermin", "sarim's vermin", 11926},
                {"Scape Ape", "scape_ape", 12698, 12437},
                {"Scape Cave", "scape cave"}, //todo why does this break the server? 6298
                {"Scape Hunter", "scape hunter"},
                {"Scape Main", "scape main"},
                {"Scape Original", "scape original"},
                {"Scape Sad", "scape sad", 13116},
                {"Scape Santa", "scape santa"},
                {"Scape Scared", "scape scared"},
                {"Scape Soft", "scape soft", 11829},
                {"Scape Wild", "scape wild", 12857, 12604},
                {"Scarab", "scarab", 12589},
                {"School's Out", "schools out"}, //todo
                {"Scorpia Dances", "scorpia_dances"}, //todo
                {"Sea Shanty", "sea shanty", 11569},
                {"Sea Shanty 2", "sea shanty2", 12082},
                {"Sea Shanty Xmas", "sea shanty xmas"}, //todo
                {"Serenade", "serenade", 9521},
                {"Serene", "serene", 11837, 11936, 11339},
                {"Settlement", "settlement", 11065},
                {"Shadowland", "shadowland", 13618, 13875, 8526},
                {"Shine", "shine", 13363},
                {"Shining", "shining", 12858},
                {"Shining Spirit", "shining_spirit"}, //todo
                {"Shipwrecked", "shipwrecked", 14391},
                {"Showdown", "showdown", 10895},
                {"Sigmund's Showdown", "sigmunds showdown"}, //todo
                {"Slice of Silent Movie", "slice of silent movie"}, //todo
                {"Slice of Station", "slice of station"}, //todo
                {"Slither and Thither", "slither and thither"}, //todo
                {"Slug a bug Ball", "slug a bug ball"}, //todo
                {"Sojourn", "sojourn", 11321},
                {"Soul Fall", "soulfall"}, //todo
                {"Soundscape", "soundscape", 9774},
                {"Sphinx", "sphinx", 13100},
                {"Spirit", "spirit", 12597},
                {"Spirits of the Elid", "spirits of elid", 13461},
                {"Splendour", "splendour", 11574},
                {"Spooky", "spooky", 12340},
                {"Spooky 2", "spooky2", 13718},
                {"Spooky Jungle", "spookyjungle", 11053, 11668},
                {"Stagnant", "stagnant", 13876, 8782},
                {"Starlight", "starlight", 11925, 12949},
                {"Start", "start", 12339},
                {"Still Night", "still night", 13108},
                {"Stillness", "stillness", 13977},
                {"Storm Brew", "storm brew"}, //todo
                {"Stranded", "stranded", 11322, 11578},
                {"Strange Place", "strange place"}, //todo
                {"Stratosphere", "stratosphere", 8523},
                {"Strength of Saradomin", "strength of saradomin"}, //todo
                {"Subterranea", "subterranea", 10142},
                {"Sunburn", "sunburn", 12846, 13357},
                {"Superstition", "superstition", 11153},
                {"Suspicious", "suspicious"}, //todo
                {"Tale of Keldagrim", "tale of keldagrim", 11678},
                {"Talking Forest", "talking forest", 10550},
                {"Tears of Guthix", "tears of guthix", 12948},
                {"Technology", "technology", 10310},
                {"Temple", "temple", 11151},
                {"Temple of Light", "temple of light", 7496},
                {"That Sullen Hall", "that_sullen_hall", 5139},
                {"The Cellar Dwellers", "the cellar dwellers", 10135},
                {"The Chosen", "the chosen", 9805},
                {"The Depths", "the depths"}, //todo
                {"The Desert", "the desert", 12591},
                {"The Desolate Isle", "the desolate isle", 10042},
                {"The Desolate Mage", "desolate_mage"}, //todo
                {"The Doors of Dinh", 477, 6461},
                {"The Enchanter", "the enchanter"}, //todo
                {"The Far Side", "the far side", 12111},
                {"The Forlorn Homestead", "forlorn_homestead"}, //todo
                {"The Galleon", "the galleon"}, //todo
                {"The Genie", "the genie", 13457},
                {"The Golem", "the golem", 13616, 13872},
                {"The Last Shanty", "the last shanty"}, //todo
                {"The Lost Melody", "the lost melody", 13206},
                {"The Lost Tribe", "the lost tribe"}, //todo
                {"The Lunar Isle", "the lunar isle"}, //todo
                {"The Mad Mole", "the mad mole", 6992},
                {"The Militia", "unknown"}, //todo
                {"The Mollusc Menace", "the mollusc menace"}, //todo
                {"The Monsters Below", "the monsters below", 9886},
                {"The Navigator", "the navigator", 10652},
                {"The Noble Rodent", "the noble rodent"}, //todo
                {"The Other Side", "the other side", 14646, 14647},
                {"The Power of Tears", "the power of tears"}, //todo
                {"The Quizmaster", "the quizmaster", 7754},
                {"The Rogues' Den", "the rogues den", 11853, 12109},
                {"The Shadow", "the shadow", 11314},
                {"The Slayer", "the slayer", 11164},
                {"The Terrible Tower", "the terrible tower", 13623},
                {"The Tower", "the tower", 10292, 10136},
                {"The Trade Parade", "the trade parade", 12598},
                {"Theme", "theme", 10294, 10138},
                {"Thrall of the Serpent", "thrall_of_the_serpent", 8751},
                {"Throne of the Demon", "throne of the demon"}, //todo
                {"Time Out", "time out", 11591},
                {"Time to Mine", "time to mine", 11422},
                {"Tiptoe", "tiptoe", 12440},
                {"Title Fight", "title fight"}, //todo
                {"Tomb Raider", "tomb raider"}, //todo
                {"Tomorrow", "tomorrow", 12081},
                {"Too Many Cooks...", "too many cooks", 11930},
                {"Trawler", "trawler", 7499},
                {"Trawler Minor", "trawler minor", 7755},
                {"Tree Spirits", "tree spirits", 9268},
                {"Tremble", "tremble", 11320},
                {"Tribal", "tribal", 11311},
                {"Tribal 2", "tribal2", 11566},
                {"Tribal Background", "tribal background", 11312, 11412},
                {"Trinity", "trinity", 10804, 10904},
                {"Trouble Brewing", "trouble brewing"}, //todo
                {"Troubled", "troubled", 11833},
                {"Troubled Waters", "troubled_waters"},
                {"Twilight", "twilight", 10906},
                {"TzHaar!", "tzhaar", 9551},
                {"Undead Dungeon", "undead dungeon"}, //todo
                {"Undercurrent", "undercurrent", 12345},
                {"Underground", "underground", 13368, 11416},
                {"Underground Pass", "upass", 9621},
                {"Understanding", "understanding", 9547},
                {"Unknown Land", "unknown land", 12338},
                {"Untouchable", "untouchable"}, //todo
                {"Upcoming", "upcoming", 10546},
                {"Upper Depths", "upper_depths"}, //todo
                {"Venture", "venture", 13364},
                {"Venture 2", "venture2", 13464},
                {"Victory is Mine", "victory is mine", 12696},
                {"Village", "village", 13878},
                {"Vision", "vision", 12337, 12436},
                {"Volcanic Vikings", "volcanic vikings", 9275},
                {"Voodoo Cult", "voodoo cult", 9545, 11665},
                {"Voyage", "voyage", 10038}, //todo
                {"Waking Dream", "waking dream"}, //todo
                {"Wander", "wander", 12083},
                {"Warpath", "warpath"}, //todo
                {"Warrior", "warrior", 10653},
                {"Warriors' Guild", "warriors guild", 11319},
                {"Waterfall", "waterfall", 10037, 10137},
                {"Waterlogged", "waterlogged", 13877, 8014},
                {"Way of the Enchanter", "way of the enchanter"}, //todo
                {"Wayward", "wayward", 9875},
                {"We are the Fairies", "we are the fairies"}, //todo
                {"Well Of Voyage", "well of voyage", 9366},
                {"Where Eagles Lair", "where eagles lair"}, //todo
                {"Wild Side", "wild side", 12092},
                {"Wilderness", "wilderness", 11832, 12346},
                {"Wilderness 2", "wilderness2", 12091},
                {"Wilderness 3", "wilderness3", 11834},
                {"Wildwood", "wildwood", 12344},
                {"Witching", "witching", 13113},
                {"Woe of the Wyvern", "woe of the wyvern", 12181},
                {"Wolf Mountain", "wolf mountain"}, //todo
                {"Wonder", "wonder", 11831},
                {"Wonderous", "wonderous", 10548},
                {"Woodland", "woodland", 8498},
                {"Work, Work, Work", "work work work"}, //todo
                {"Workshop", "workshop", 12084},
                {"Wrath and Ruin", "wrath and ruin"}, //todo
                {"Xenophobe", "xenophobe", 7492, 11589},
                {"Yesteryear", "yesteryear", 12849},
                {"Zamorak Zoo", "zamorak zoo"}, //todo
                {"Zealot", "zealot", 10827},
                {"Zogre Dance", "zogre dance", 9775},
                {"Zombiism", "zombiism"}, //todo
        };
        /**
         * Create tracks
         */
        EnumMap names = EnumMap.get(812);
        EnumMap hashes = EnumMap.get(819);
        TRACKS = new Track[names.length];
        for(int i = 0; i < names.length; i++) {
            int id = names.keys[i];
            String name = names.stringValues[i];
            int hash = hashes.intValues[i];
            Object[] data = null;
            for(Object[] d : trackData) {
                if(d[0].equals(name)) {
                    data = d;
                    break;
                }
            }
            TRACKS[id - 1] = new Track(name, hash, data);
        }
        /**
         * Select tracks
         */
        InterfaceHandler.register(Interface.MUSIC_PLAYER, h -> {
            h.actions[1] = (SlotAction) (p, slot) -> {
                int i = slot - 1;
                if(i < 0 || i >= TRACKS.length)
                    return;
                TRACKS[i].select(p);
            };
            h.actions[7] = (SimpleAction) p -> {
                Config.MUSIC_PREFERENCE.set(p, 1);
                p.getPacketSender().sendString(Interface.MUSIC_PLAYER, 5, "AUTO");
            };
            h.actions[9] = (SimpleAction) p -> {
                Config.MUSIC_PREFERENCE.set(p, 0);
                p.getPacketSender().sendString(Interface.MUSIC_PLAYER, 5, "MANUAL");
            };
            h.actions[11] = (SimpleAction) p -> {
                if(Config.MUSIC_LOOP.toggle(p) == 1)
                    p.sendMessage("Music looping is now enabled.");
                else
                    p.sendMessage("Music looping now disabled.");
            };
        });
    }

    public static final class Track {

        private String name;

        private int varpPos = -1, varpShift;

        private int archiveId = -1;

        private Track(String name, int hash, Object[] data) {
            this.name = name;
            if(hash != -1) {
                this.varpPos = hash >> 14;
                this.varpShift = hash & 0x3fff;
            }
            if(data != null) {
                Object d = data[1];
                if(d instanceof Integer)
                    archiveId = (int) d;
//                else if((archiveId = Server.fileStore.get(6).getArchiveId((String) d)) == -1)
//                    System.err.println("Warning, archive id not set for music track: \"" + name + "\"");
                if(data.length > 2) {
                    List<Integer> regionIds = new ArrayList<>();
                    for(int i = 2; i < data.length; i++)
                        regionIds.add((int) data[i]);
                    MapListener.registerRegions(regionIds)
                            .onEnter(this::autoPlay);
                }
                return;
            }
            //ServerWrapper.logWarning("Warning, unhandled music track: \"" + name + "\"");
        }

        public void autoPlay(Player player) {
            if (varpPos < 1) {
                Server.logError("Error playing track for region "+ player.getPosition().regionId() +". (varpPos="+ varpPos +", varpShift="+ varpShift +")");
                return;
            }

            try {
                Config unlock = Config.MUSIC_UNLOCKS[varpPos - 1];
                int value = unlock.get(player);
                if((unlock.get(player) & (1 << varpShift)) == 0) {
                /* unlock track */
                    unlock.set(player, value | (1 << varpShift));
                    player.sendFilteredMessage("<col=ff0000>You have unlocked a new music track: " + name);
                }
                if(Config.MUSIC_PREFERENCE.get(player) == 1) {
                /* auto-play track */
                    player.getPacketSender().sendString(Interface.MUSIC_PLAYER, 5, name);
                    player.getPacketSender().sendMusic(archiveId);
                }
            } catch(Throwable t) {
                Server.logError("Error playing music track for region "+ player.getPosition().regionId() +" (varp pos:"+ varpPos +"): "+ t.getMessage()); //todo remove after find bug
            }
        }

        private void select(Player player) {
            try {
                if(varpPos != -1) {
                    Config config = Config.MUSIC_UNLOCKS[varpPos - 1];
                    int value = config.get(player);
                    if((value & (1 << varpShift)) == 0) {
                        player.sendMessage("You have not unlocked this music track yet.");
                        return;
                    }
                }
                Config.MUSIC_PREFERENCE.set(player, 0);
                player.getPacketSender().sendString(Interface.MUSIC_PLAYER, 5, name);
                player.getPacketSender().sendMusic(archiveId);
            } catch(Throwable t) {
                Server.logError("", t); //todo remove after find bug
            }
        }

    }

}
