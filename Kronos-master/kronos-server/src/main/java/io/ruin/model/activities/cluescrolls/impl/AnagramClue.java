package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.cache.NPCDef;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;

import java.util.Collections;
import java.util.List;

public class AnagramClue extends Clue {

    private final String clue;

    private AnagramClue(String clue, ClueType type) {
        super(type);
        this.clue = clue.toUpperCase();
    }

    @Override
    public void open(Player player) {
        player.getPacketSender().sendString(203, 2, "The anagram reveals<br>who to speak to next:<br>" + clue);
        player.openInterface(InterfaceType.MAIN, 203);
    }

    /**
     * Register
     */

    private static void register(int npcId, String clue, ClueType type) {
        register(npcId, new AnagramClue(clue, type));
    }

    private static void register(String npcName, String clue, ClueType type) {
        register(Collections.singletonList(npcName), clue, type);
    }

    private static void register(List<String> npcNames, String clue, ClueType type) {
        AnagramClue anagram = new AnagramClue(clue, type);
        for(String npcName : npcNames) {
            NPCDef.forEach(def -> {
                if(def.name.equalsIgnoreCase(npcName)) {
                    register(def.id, anagram);
                }
            });
        }
    }

    private static void register(int npcId, AnagramClue anagram) {
        NPCDef def = NPCDef.get(npcId);
        if(def.anagram != null)
            System.err.println(def.name + " has duplicate anagram clues set!");
        def.anagram = anagram;
    }

    static {
        register("Baraek", "A Baker", ClueType.MEDIUM);
        register("Captain Tobias", "A Basic Anti Pot", ClueType.MEDIUM);
        register("Zenesha", "A Zen She", ClueType.HARD);
        register("Jaraah", "Aha Jar", ClueType.MEDIUM);
        register("Caroline", "Arc O Line", ClueType.MEDIUM);
        register("Oracle", "Are Col", ClueType.MEDIUM);
        register("Ramara du Croissant", "Arr! So I am a crust, and?", ClueType.HARD);
        register("Saba", "A Bas", ClueType.MEDIUM);
        register("Oneiromancer", "Career In Moon", ClueType.ELITE);
        register("Gnome Coach", "C On Game Hoc", ClueType.HARD);
        register("Old Crone", "Cool Nerd", ClueType.ELITE); //Check after restart
        register("Prospector Percy", "Copper Ore Crypts", ClueType.HARD);
        register("Doomsayer", "Do Say More", ClueType.HARD);
        register("Mandrith", "Dr Hitman", ClueType.ELITE);
        register("Strange Old Man", "Dragons Lament", ClueType.HARD);
        register("Brundt the Chieftain", "Dt Run B", ClueType.MEDIUM);
        register("Zoo keeper", "Eek Zero Op", ClueType.MEDIUM);
        register("Lowe", "El Ow", ClueType.EASY);
        register("Recruiter", "Err Cure It", ClueType.MEDIUM);
        register("Gabooty", "Got A Boy", ClueType.MEDIUM);
        register("Otto Godblessed", "Goblets Odd Toes", ClueType.MEDIUM);
        register("Luthas", "Halt Us", ClueType.MEDIUM);
        register(490, "I Even", ClueType.MEDIUM);
        register("Kaylee", "Leakey", ClueType.MEDIUM);
        register("Odd Old Man", "Land Doomd", ClueType.HARD);
        register("Cam the Camel", "Machete Clam", ClueType.ELITE);
        register("Femi", "Me if", ClueType.MEDIUM);
        register("Brother Omad", "Motherboard", ClueType.HARD);
        register("Cap'n Izzy No-Beard", "O Birdz A Zany En Pc", ClueType.HARD);
        register(4626, "Ok Co", ClueType.MEDIUM);//Cook
        register("Party Pete", "Peaty Pert", ClueType.EASY);
        register("Professor Onglewip", "Profs Lose Wrong Pie", ClueType.HARD);
        register("Karim", "R Ak Mi", ClueType.MEDIUM);
        register("Martin Thwait", "Rat Mat Within", ClueType.HARD);
        register("Trader Stan", "Red Art Tans", ClueType.HARD);
        register("Taria", "Ratai", ClueType.MEDIUM);
        register("Hans", "Snah", ClueType.EASY);
        register("Hickton", "Thickno", ClueType.MEDIUM);
        register("Sigli the Huntsman", "Unleash Night Mist", ClueType.MEDIUM);
    }

}
