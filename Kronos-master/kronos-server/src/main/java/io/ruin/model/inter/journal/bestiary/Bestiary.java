package io.ruin.model.inter.journal.bestiary;

import io.ruin.Server;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.BlankEntry;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.process.task.TaskWorker;

import java.util.*;

public class Bestiary {

    private static final HashMap<Integer, LinkedHashSet<NPCDef>> drops = new HashMap<>();

    public static void search(Player player, String name, boolean monster) {
        String imgTag;
        if(monster)
            player.sendMessage((imgTag = "<img=108>") + Color.DARK_GREEN.tag() + " Bestiary: " + Color.OLIVE.tag() + "Searching for monster \"" + name + "\"...");
        else
            player.sendMessage((imgTag = "<img=33>") + Color.DARK_GREEN.tag() + " Bestiary: " + Color.OLIVE.tag() + "Searching for monsters that drop \"" + name + "\"...");
        TaskWorker.startTask(t -> {
            List<JournalEntry> results = new ArrayList<>();
            results.add(BestiarySearchDrop.INSTANCE);
            results.add(BestiarySearchMonster.INSTANCE);
            results.add(new BlankEntry());
            String search = formatForSearch(name);
            if(!search.isEmpty()) {
                LinkedHashMap<String, TreeMap<Integer, List<NPCDef>>> map = new LinkedHashMap<>();
                if(monster) {
                    NPCDef.forEach(npcDef -> {
                        String searchName = formatForSearch(npcDef.name);
                        if(!searchName.contains(search))
                            return;
                        map.computeIfAbsent(searchName, k -> new TreeMap<>()).computeIfAbsent(npcDef.combatLevel, k -> new ArrayList<>()).add(npcDef);
                    });
                } else {
                    ItemDef.forEach(itemDef -> {
                        if(!formatForSearch(itemDef.name).contains(search))
                            return;
                        HashSet<NPCDef> npcDefs = drops.get(itemDef.id);
                        if(npcDefs == null)
                            return;
                        npcDefs.forEach(npcDef -> map.computeIfAbsent(formatForSearch(npcDef.name), k -> new TreeMap<>()).computeIfAbsent(npcDef.combatLevel, k -> new ArrayList<>()).add(npcDef));
                    });
                }
                int[] childId = {13};
                map.values().forEach(levelsMap -> {
                    List<NPCDef> matched = new ArrayList<>();
                    levelsMap.values().forEach(defs -> {
                        defs.sort(new Comparator<NPCDef>() {
                            @Override
                            public int compare(NPCDef d1, NPCDef d2) {
                                return Integer.compare(priority(d2), priority(d1));
                            }
                            private int priority(NPCDef def) {
                                int priority = 0;
                                if(def.combatInfo != null)
                                    priority++;
                                if(def.lootTable != null)
                                    priority++;
                                if(def.combatLevel > 0)
                                    priority++;
                                if(def.hasOption("attack"))
                                    priority++;
                                return priority;
                            }
                        });
                        NPCDef def = defs.get(0);
                        if(def.combatInfo != null || def.lootTable != null)
                            matched.add(def);
                        //^ only match the highest priority npc with this combat level.
                    });
                    for(NPCDef def : matched) {
                        BestiarySearchResult result = new BestiarySearchResult(def.id);
                        result.childId = childId[0] - 1; //wish java somehow allowed non-final modification inside of lambdas!
                        if(matched.size() > 1) //multiple same levels
                            result.name += " (" + def.combatLevel + ")";
                        /* some custom name resizing! */
                        result.name = result.name.replace("Greater Skeleton Hellhound", "Grtr. Skeleton Hellhound");
                        /* end of custom name resizing! */
                        results.add(result);
                        childId[0]++;
                    }
                });
                if (results.size() > 15) {
                    results.clear();
                    player.sendMessage("Too many results..");
                    return;
                }
            }
            Server.worker.execute(() -> {
                int found = results.size() - 2; //minus two because of the search entries
                if(found == 0) {
                    player.bestiarySearchResults = null;
                    player.sendMessage(imgTag + Color.DARK_GREEN.tag() + " Bestiary: " + Color.OLIVE.tag() + "No results found.");
                } else {
                    player.bestiarySearchResults = results;
                    if(found == 1)
                        player.sendMessage(imgTag + Color.DARK_GREEN.tag() + " Bestiary: " + Color.OLIVE.tag() + "1 result found.");
                    else
                        player.sendMessage(imgTag + Color.DARK_GREEN.tag() + " Bestiary: " + Color.OLIVE.tag() + found + " results found.");
                }
                if(player.journal == Journal.BESTIARY)
                    Journal.BESTIARY.send(player);
            });
        });
    }

    private static String formatForSearch(String string) {
        return string.replace("'", "")
                .toLowerCase()
                .trim();
    }

    static {
        NPCDef.forEach(def -> {
            if(def != null && def.lootTable != null)
                def.lootTable.allItems().forEach(item -> drops.computeIfAbsent(item.getId(), k -> new LinkedHashSet<>()).add(def));
            if(def != null && def.combatInfo != null && def.combatInfo.pet != null)
                drops.computeIfAbsent(def.combatInfo.pet.itemId, k -> new LinkedHashSet<>()).add(def);
        });
    }

}
