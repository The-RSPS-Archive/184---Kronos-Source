package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.cache.NPCDef;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CrypticClue extends Clue {

    private final String clue;

    public CrypticClue(String clue, ClueType type) {
        super(type);
        this.clue = clue;
    }

    @Override
    public void open(Player player) {
        player.getPacketSender().sendString(203, 2, clue);
        player.openInterface(InterfaceType.MAIN, 203);
    }

    /**
     * Digging
     */
    private static void registerDig(String clue, int x, int y, int z, ClueType type) {
        CrypticClue cryptic = new CrypticClue(clue, type);
        Tile.get(x, y, z, true).digAction = cryptic::advance;
    }

    static {
        registerDig("46 is my number. My body is the colour of burnt orange and crawls among those with eight. Three mouths I have, yet I cannot eat. My blinking blue eye hides my grave.", 3170, 3885, 0, ClueType.HARD);
        registerDig("Aggie I see, Lonely and southern I feel I am neither inside nor outside the house yet no house would be complete without me. Your treasure waits beneath me.", 3085, 3255, 0, ClueType.HARD);
        registerDig("Come to the evil ledge, Yew know yew want to. Try not to get stung.", 3089, 3468, 0, ClueType.HARD);
        registerDig("Dig between some ominous stones in Falador.", 3040, 3398, 0, ClueType.EASY);
        registerDig("Dig near some giant mushrooms behind the Grand Tree.", 2458, 3504, 0, ClueType.EASY);
        registerDig("Dig where the forces of Zamorak and Saradomin collide.", 3049, 4839, 0, ClueType.ELITE);
        registerDig("I lie lonely and forgotten in mid wilderness, where the dead rise from their beds. Feel free to quarrel and wind me up and dig while you shoot their heads.", 3174, 3663, 0, ClueType.HARD);
        registerDig("My giant guardians below the market streets would be fans of rock and roll, if only they could grab hold of it. Dig near my green bubbles!", 3161, 9904, 0, ClueType.HARD);
        registerDig("The beasts retreat, for their Queen is gone; the song of this town still plays on. Dig near the birthplace of a blade, be careful not to melt your spade.", 2342, 3677, 0, ClueType.ELITE);
        registerDig("The beasts to my east snap claws and tails, The rest to my west can slide and eat fish. The force to my north will jump and they'll wail, Come dig by my fire and make a wish.", 2598, 3267, 0, ClueType.HARD);
        registerDig("The treasure is buried in a small building full of bones. Here is a hint: it's not near a graveyard.", 3356, 3507, 0, ClueType.EASY);
        registerDig("THEY'RE EVERYWHERE!!! But they were here first. Dig for treasure where the ground is rich with ore.", 3081, 3421, 0, ClueType.HARD);
        registerDig("W marks the spot.", 2867, 3546, 0, ClueType.ELITE);
    }

    /**
     * Searching
     */
    private static void registerSearch(String clue, int objectId, int x, int y, int z, ClueType type) {
        GameObject object = Tile.getObject(objectId, x, y, z);
        if (Objects.nonNull(object))
            object.crypticClue = new CrypticClue(clue, type);
    }

    static {
        /*
         * Easy
         */
        registerSearch("A crate found in the tower of a church is your next location.", 357, 2612, 3304, 1, ClueType.EASY);
        registerSearch("Look in the ground floor crates of houses in Falador.", 24088, 3029, 3355, 0, ClueType.EASY);
        registerSearch("Search a bookcase in the Wizards tower.", 12539, 3113, 3158, 0, ClueType.EASY);
        registerSearch("Search the bookcase in the monastery.", 380, 3054, 3483, 0, ClueType.EASY);
        registerSearch("Search the boxes in one of the tents in Al Kharid.", 361, 3308, 3206, 0, ClueType.EASY);
        registerSearch("Search the boxes in the goblin house near Lumbridge.", 359, 3245, 3245, 0, ClueType.EASY);

        /*
         * Medium
         */
        registerSearch("A town with a different sort of night-life is your destination. Search for some crates in one of the houses.", 24344, 3498, 3507, 0, ClueType.MEDIUM);
        registerSearch("Find a crate close to the monks that like to paaarty!", 354, 2614, 3204, 0, ClueType.MEDIUM);
        registerSearch("In a village made of bamboo, look for some crates under one of the houses.", 356, 2800, 3074, 0, ClueType.MEDIUM);

        /*
         * Hard
         */
        registerSearch("A great view - watch the rapidly drying hides get splashed. Check the box you are sitting on.", 359, 2523, 3493, 1, ClueType.HARD);
        registerSearch("Four blades I have, yet draw no blood; Still I turn my prey to powder. If you are brave, come search my roof; It is there my blades are louder.", 12963, 3166, 3309, 2, ClueType.HARD);
        registerSearch("The cheapest water for miles around, but they react badly to religious icons.", 354, 3178, 2987, 0, ClueType.HARD);
        registerSearch("This village has a problem with cartloads of the undead. Try checking the bookcase to find an answer.", 394, 2833, 2991, 0, ClueType.HARD);

        /*
         * Elite
         */

        registerSearch("A Guthixian ring lies between two peaks. Search the stones and you'll find what you seek.", 26633, 2922, 3484, 0, ClueType.ELITE);

        registerSearch("Even the seers say this clue goes right over their heads.", 14934, 2707, 3488, 2, ClueType.ELITE);


        /*
         * Unverfied/unused
         */
        /*
        registerSearch("My home is grey, and made of stone; A castle with a search for a meal. Hidden in some drawers I am, across from a wooden wheel.", 5618, 3213, 3216, 1, ClueType.HARD);
        registerSearch("Search the boxes in the house near the south entrance to Varrock.", 5111, 3203, 3384, 0, ClueType.EASY);
        registerSearch("Search the boxes just outside the Armour shop in East Ardounge.", 361, 2654, 3299, 0, ClueType.EASY);
        registerSearch("Search the boxes of Falador's general store.", 24088, 2955, 3390, 0, ClueType.EASY);
        registerSearch("Search the bush at the digsite centre.", 2357, 3345, 3378, 0, ClueType.EASY);
        registerSearch("It seems to have reached the end of the line, and it's still empty.", 6045, 3041, 9820, 0, ClueType.HARD);
        registerSearch("Search for a crate in a building in Hemenster.", 357, 2636, 3453, 0, ClueType.EASY);
         registerSearch("Read 'How to breed scorpions.' By O.W.Thathurt.", 380, 2702, 3409, 1, ClueType.HARD);
        registerSearch("Search the chests in the Dwarven Mine.", 375, 3000, 9798, 0, ClueType.EASY);
        registerSearch("Search the chests upstairs in the Al Kharid Palace.", 375, 3301, 3169, 1, ClueType.EASY);
        registerSearch("Search the crate in the left-hand tower of Lumbridge Castle.", 357, 3228, 3212, 1, ClueType.EASY);
        registerSearch("Search for a crate in Varrock Castle.", 5113, 3224, 3492, 0, ClueType.EASY);
        registerSearch("Search for a crate on the ground floor of a house in Seers' Village.", 25775, 2699, 3470, 0, ClueType.EASY);
        registerSearch("Search the crate near a cart in Port Khazard.", 366, 2660, 3149, 0, ClueType.EASY);
        registerSearch("Search the crates near a cart in Varrock.", 5107, 3226, 3452, 0, ClueType.EASY);
        registerSearch("Search the crates in a house in Yanille that has a piano.", 357, 2598, 3105, 0, ClueType.EASY);
        registerSearch("Search the crates in Canifis.", 24344, 3509, 3497, 0, ClueType.EASY);
        registerSearch("Search the crates in Horvik's armoury.", 5106, 3228, 3433, 0, ClueType.EASY);
        registerSearch("Search the crates in the Dwarven mine.", 357, 3035, 9849, 0, ClueType.EASY);
        registerSearch("Search the crates in the guard house of the northern gate of East Ardougne.", 356, 2645, 3338, 0, ClueType.EASY);
        registerSearch("Search the crates in the outhouse of the long building in Taverley.", 357, 2914, 3433, 0, ClueType.EASY);
        registerSearch("Search the crates in the shed just north of East Ardougne.", 355, 2617, 3347, 0, ClueType.EASY);
        registerSearch("Search the crates in most north-western house in Al Kharid.", 358, 3289, 3202, 0, ClueType.EASY);
        registerSearch("Search the crates in the Port Sarim Fishing shop.", 9534, 3012, 3222, 0, ClueType.EASY);
        registerSearch("Search the crate in the Toad and Chicken pub.", 354, 2913, 3536, 0, ClueType.EASY);
        registerSearch("Search the drawers above Varrock's shops.", 7194, 3206, 3419, 1, ClueType.EASY);
        registerSearch("Search the drawers found upstairs in East Ardougne's houses.", 348, 2574, 3326, 1, ClueType.EASY);
        registerSearch("Search the drawers in a house in Draynor Village.", 350, 3097, 3277, 0, ClueType.EASY);
        registerSearch("Search the drawers in Catherby's Archery shop.", 350, 2825, 3442, 0, ClueType.EASY);
        registerSearch("Search the drawers in Falador's chain mail shop.", 348, 2969, 3311, 0, ClueType.EASY);
        registerSearch("Search the drawers in the ground floor of a shop in Yanille.", 350, 2570, 3085, 0, ClueType.EASY);
        registerSearch("Search the drawers in the house next to the Port Sarim mage shop.", 348, 3024, 3259, 0, ClueType.EASY);
        registerSearch("Search the drawers in the upstairs of a house in Catherby.", 350, 2809, 3451, 1, ClueType.EASY);
        registerSearch("Search the drawers in one of Gertrude's bedrooms.", 7194, 3156, 3406, 0, ClueType.EASY);
        registerSearch("Search the drawers of houses in Burthorpe.", 348, 2929, 3570, 0, ClueType.EASY);
        registerSearch("Search the drawers on the first floor of a building overlooking Ardougne's Market.", 352, 2657, 3322, 1, ClueType.EASY);
        registerSearch("Search the drawers upstairs in Falador's shield shop.", 348, 2971, 3386, 1, ClueType.EASY);
        registerSearch("Search the drawers upstairs of houses in eastern part of Falador.", 350, 3035, 3347, 1, ClueType.EASY);
        registerSearch("Search the drawers, upstairs in the bank to the East of Varrock.", 7194, 3250, 3420, 1, ClueType.EASY);
        registerSearch("Search through some drawers in the upstairs of a house in Rimmington.", 352, 2970, 3214, 1, ClueType.EASY);
        registerSearch("Search through some drawers found in Taverley's houses.", 350, 2894, 3418, 0, ClueType.EASY);
        registerSearch("Search the crates in East Ardougne's general store.", 357, 2615, 3291, 0, ClueType.EASY);
        registerSearch("Search the tents in the Imperial Guard camp in Burthorpe for some boxes.", 3686, 2885, 3540, 0, ClueType.EASY);
        registerSearch("Search the wheelbarrow in Rimmington mine.", 9625, 2978, 3239, 0, ClueType.EASY);
        registerSearch("Search through chests found in the upstairs of houses in eastern Falador.", 375, 3041, 3364, 1, ClueType.EASY);
        registerSearch("Search upstairs in the houses of Seers' Village for some drawers.", 25766, 2716, 3471, 1, ClueType.EASY);*/
    }


    /**
     * Talking
     */
    private static void registerTalk(int npcId, String clue, ClueType type) {
        registerTalk(npcId, new CrypticClue(clue, type));
    }

    private static void registerTalk(String npcName, String clue, ClueType type) {
        registerTalk(Collections.singletonList(npcName), clue, type);
    }

    private static void registerTalk(List<String> npcNames, String clue, ClueType type) {
        CrypticClue cryptic = new CrypticClue(clue, type);
        for(String npcName : npcNames) {
            NPCDef.forEach(def -> {
                if(def.name.equalsIgnoreCase(npcName))
                    registerTalk(def.id, cryptic);
            });
        }
    }

    private static void registerTalk(int npcId, CrypticClue cryptic) {
        NPCDef def = NPCDef.get(npcId);
        if(def.cryptic != null)
            System.err.println(def.name + " has duplicate cryptic clues set!");
        def.cryptic = cryptic;
    }

    static {
        registerTalk("Father Aereck", "A reck you say Let's pray there aren't any ghosts.", ClueType.ELITE);
        registerTalk("Abbot Langley", "A bag belt only?', he asked his balding brothers.", ClueType.HARD);
        registerTalk("Horacio", "Dobson is my last name, and with gardening I seek fame.", ClueType.ELITE);
        registerTalk("General Bentnoze", "Generally speaking, his nose was very bent.", ClueType.HARD);
        registerTalk("Saniboch", "Gold I see, yet gold I require. Give me 875 if death you desire.", ClueType.HARD);
        registerTalk("Barker", "His bark is worse than his bite.", ClueType.ELITE);
        registerTalk("Candle maker", "I burn between heroes and legends.", ClueType.ELITE);
        registerTalk("Gerrant", "If a man carried my burden, he would break his back. I am not rich, but leave silver in my track. Speak to the keeper of my trail.", ClueType.HARD);
        registerTalk("Ellena", "I watch the sea. I watch you fish. I watch your tree.", ClueType.HARD);
        registerTalk("Kamfreena", "I am the one who watches the giants. The giants in turn watch me. I watch with two while they watch with one. Come seek where I may be.", ClueType.ELITE);
        registerTalk("Hamid", "Identify the back of this over-acting brother. (He's a long way from home.)", ClueType.HARD);
        registerTalk("Wilough", "My name is like a tree, yet it is spelt with a 'g'. Come see the fur which is right near me.", ClueType.HARD);
        registerTalk("Captain Tobias", "One of the sailors in Port Sarim is your next destination.", ClueType.EASY);
        registerTalk("Examiner", "Often sought out by scholars of histories past, find me where words of wisdom speak volumes.", ClueType.HARD);
        registerTalk("Dominic Onion", "'See you in your dreams' said the vegetable man.", ClueType.ELITE);
        registerTalk("Jeed", "Someone watching the fights in the Duel Arena is your next destination.", ClueType.EASY);
        registerTalk("Arhein", "Speak to Arhein in Catherby.", ClueType.EASY);
        registerTalk("Donovan the Family Handyman", "Speak to Donovan, the Family Handyman.", ClueType.MEDIUM);
        registerTalk("Doric", "Speak to Doric, who lives north of Falador.", ClueType.EASY);
        registerTalk("Gaius", "Speak to Gaius in Taverley.", ClueType.EASY);
        registerTalk("Hajedy", "Speak to Hajedy.", ClueType.MEDIUM);
        registerTalk("Jatix", "Speak to Jatix in Taverley.", ClueType.EASY);
        registerTalk("Kangai Mau", "Speak to Kangai Mau.", ClueType.MEDIUM);
        registerTalk("Ned", "Speak to Ned in Draynor Village.", ClueType.EASY);
        registerTalk("Rusty", "Speak to Rusty north of Falador", ClueType.EASY);
        registerTalk(501, "Speak to Sarah at Falador farm.", ClueType.EASY);
        registerTalk(1312, "Speak to the bartender of the Blue Moon Inn in Varrock.", ClueType.EASY);
        registerTalk("The Lady of the Lake", "Speak to the Lady of the Lake.", ClueType.EASY);
        registerTalk("Ulizius", "Speak to Ulizius.", ClueType.MEDIUM);
        registerTalk("Sir Prysin", "Surprising? I bet he is...", ClueType.HARD);
        registerTalk("Hans", "Snah? I feel all confused, like one of those cakes...", ClueType.HARD);
        registerTalk("Sir Vyvin", "Surviving.", ClueType.HARD);
        registerTalk("Gnome trainer", "'Small shoe.' Often found with rod on mushroom.", ClueType.HARD);
        registerTalk("Wyson the gardener", "Speak to a Wyse man.", ClueType.ELITE);
        registerTalk("Roavar", "Speak to Roavar.", ClueType.MEDIUM);
        registerTalk("Hairdresser", "Talk to the barber in the Falador barber shop.", ClueType.EASY);
        registerTalk(1313, "Talk to the bartender of the Rusty Anchor in Port Sarim.", ClueType.EASY);
        registerTalk("Lucy", "Talk to a party-goer in Falador.", ClueType.EASY);
        registerTalk("Gypsy Aris", "Varrock is where I reside not the land of the dead, but I am so old, I should be there instead. Let's hope your reward is as good as it says, just 1 gold one and you can have it read.", ClueType.ELITE);
        registerTalk("Vannaka", "You were 3 and I was the 6th. Come speak to me.", ClueType.ELITE);
    }

}

