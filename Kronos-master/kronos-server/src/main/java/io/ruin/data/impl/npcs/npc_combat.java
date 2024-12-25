package io.ruin.data.impl.npcs;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.*;
import io.ruin.cache.AnimDef;
import io.ruin.cache.NPCDef;
import io.ruin.data.DataFile;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.skills.slayer.SlayerTask;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class npc_combat extends DataFile {

    /*
    public static void main(String[] args) throws Exception {
        Server.fileStore = new FileStore("%HOME%/jagexcache/oldschool/LIVE");
        Server.dataFolder = FileUtils.get("%HOME%/Dropbox/Runite/Server/data");
        ItemDef.load();
        NPCDef.load();
        AnimDef.load();
        DataFile.load(new npc_combat()); //needed for parsing
        DataFile.load(new slayer_tasks()); //needed for parsing
        //dumpAll();
        //toJson();
    }
    */

    @Override
    public String path() {
        return "npcs/combat/*.json";
    }

    @Override
    public int priority() {
        return 5;
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<Info> list = JsonUtils.fromJson(json, List.class, Info.class);

        if (!Server.dataOnlyMode) {
            list.forEach(info -> {
                for (int id : info.ids) {
                    NPCDef def = NPCDef.get(id);
//                    if (def.combatInfo != null) {
//                        System.err.println(def.id + ": " + def.name + " already has combat info set!");
//                    }
                    def.combatInfo = info;
                    if (info.handler != null && !info.handler.isEmpty()) {
                        try {
                            //noinspection unchecked
                            def.combatHandlerClass = (Class<? extends NPCCombat>) Class.forName(info.handler);
                        } catch (ClassNotFoundException e) {
                            //just in case someone hasn't commit, we'll print a little warning and move on!
                            System.err.println("Warning, combat handler class not found: " + info.handler + " for npc #" + id + " \"" + def.name + "\"!");
                        } catch (Exception e) {
                            ServerWrapper.logError("Failed to combat info: " + def.id, e);
                        }
                    }
                    //if(info.slayer_task != null && info.slayer_task.equalsIgnoreCase("NONE"))
                    //    info.slayer_task = null;
                }
            });
        }

        return list;
    }

    public static final class Info {

        @Expose public int[] ids;

        @Expose public String handler;

        @Expose public Pet pet;

        /**
         * Combat Info
         */

        @Expose public int hitpoints;

        @Expose public int aggressive_level;

        @Expose public int max_damage;

        @Expose public AttackStyle attack_style;

        /**
         * Slayer Info
         */

        @Expose public int slayer_level;

        @Expose public double slayer_xp;

        @Expose public String[] slayer_tasks;

        public SlayerTask[] slayerTasks;

        /**
         * Combat Stats
         */

        @Expose public int attack, strength, defence, ranged, magic;

        /**
         * Aggressive Stats
         */

        @Expose public int stab_attack, slash_attack, crush_attack, magic_attack, ranged_attack;

        /**
         * Defensive Stats
         */

        @Expose public int stab_defence, slash_defence, crush_defence, magic_defence, ranged_defence;

        /**
         * Immunities
         */

        @Expose public boolean poison_immunity, venom_immunity;

        /**
         * Speeds
         */

        @Expose public int attack_ticks, death_ticks, respawn_ticks;

        /**
         * Anims
         */

        @Expose public int spawn_animation, attack_animation, defend_animation, death_animation;

        /**
         * Sounds
         */

        @Expose public Integer attack_sound, defend_sound, death_sound;

        /**
         * Broadcast loot to all nearby players
         */
        @Expose public boolean local_loot = false;

        /**
         * Combat XP modifier.
         * Combat XP earned through hitting this monster will be multiplied by this value
         */

        @Expose public double combat_xp_modifier = 1.0;

        @Expose public int random_drop_count = 1;

    }

    /**
     * Dump combat info from wiki..
     */

    public static void dump(String wikiName) {
        new WikiDumper(wikiName).run();
    }

    public static void dumpAll() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY); //will this help speed this along??

        Set<String> dumped = new HashSet<>();
        AtomicLong done = new AtomicLong(0);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FileUtils.get("%HOME%/Dropbox/Runite/Server/data/wiki_dumps/combat/temp/~FAILED.txt")))) {
            bw.write("# id     name     combat");
            bw.newLine();
            NPCDef.forEach(def -> {
                if(def.combatLevel == 0)
                    return;
                String name = def.name.trim();
                if(name.isEmpty() || name.equalsIgnoreCase("null"))
                    return;
                /**
                 * Try to dump if an npc with the given name/combat hasn't already been dumped..
                 */
                String dumpKey = name.toLowerCase() + "-" + def.combatLevel;
                if(!dumped.contains(dumpKey)) {
                    //tried to think of every potential wiki url name..
                    //which makes this entire method execute VERY slow because it uses connections..
                    String[] names = {
                            name + def.combatLevel,
                            name + "(" + def.combatLevel + ")",

                            name + " " + def.combatLevel,
                            name + " " + "(" + def.combatLevel + ")",

                            name.replace(" ", "_") + def.combatLevel,
                            name.replace(" ", "_") + "(" + def.combatLevel + ")",

                            name.replace(" ", "_") + "_" + def.combatLevel,
                            name.replace(" ", "_") + "_" + "(" + def.combatLevel + ")",

                            name + " " + "(monster)",
                            name + "_" + "(monster)",

                            name + " " + "(Monster)",
                            name + "_" + "(Monster)",

                            name + " " + "(common)",
                            name + "_" + "(common)",

                            name + " " + "(Common)",
                            name + "_" + "(Common)",

                            name.replace(" ", "_"),
                            name
                    };
                    for(String n : names) {
                        WikiDumper d = new WikiDumper(n).run();
                        if(!d.success)
                            d = new WikiDumper(n.toLowerCase()).run();
                        if(!d.success)
                            d = new WikiDumper(StringUtils.fixCaps(n.toUpperCase())).run();
                        if(!d.success && n.contains("(")) {
                            String s = n.replace("(", "[").replace(")", "]");
                            d = new WikiDumper(s).run();
                            if(!d.success)
                                d = new WikiDumper(s.toLowerCase()).run();
                            if(!d.success)
                                d = new WikiDumper(StringUtils.fixCaps(s.toUpperCase())).run();
                        }
                        if(d.success) {
                            dumped.add(dumpKey);
                            break;
                        }
                    }
                }
                /**
                 * Every attempt to dump failed, let's log it..
                 */
                if(!dumped.contains(dumpKey)) {
                    try {
                        bw.write(def.id + "     " + def.name + "     " + def.combatLevel);
                        bw.newLine();
                    } catch(IOException e) {
                        /* ignored */
                    }
                }
                /**
                 * This is to follow progress, lol!
                 */
                System.err.println(done.incrementAndGet());
            });
        } catch(Exception e) {
            //This really shouldn't ever even happen..
            ServerWrapper.logError("Failed to dump", e);
        }
        System.exit(1);
    }

    public static final class WikiDumper {

        private String wikiName;

        private boolean success;

        public WikiDumper(String wikiName) {
            this.wikiName = wikiName;
        }

        public WikiDumper run() {
            File file = null;
            try {
                Connection.Response response = Jsoup.connect("http://oldschoolrunescape.wikia.com/wiki/" + URLEncoder.encode(wikiName, "UTF-8"))
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .execute();
                String url = response.url().toString();
                wikiName = URLDecoder.decode(url.substring(url.lastIndexOf("/") + 1), "UTF-8");
                //^^ prevents multiple files for redirects ^^
                Document doc = response.parse();
                if(doc == null)
                    throw new IOException("Failed to connect to wiki page!");
                Elements infos = doc.getElementsByClass("wikitable infobox");
                try(BufferedWriter bw = new BufferedWriter(new FileWriter(file = FileUtils.get("D:/Dropbox/Vorkath/Server/data/wiki_dumps/combat/temp/" + wikiName + ".txt")))) {
                    int count = 0;
                    for(Element info : infos) {
                        String name = info.child(0).text();
                        Element body = info.child(1);

                        int bodyOffset = 0;

                        if(body.child(1).text().toLowerCase().startsWith("also called"))
                            bodyOffset++;

                        Element combatLevel = body.child(3 + bodyOffset);
                        Elements combatInfo = body.child(7 + bodyOffset).select("td > table > tbody > tr");

                        Element hitpoints = combatInfo.get(1);
                        Element aggressive = combatInfo.get(2);
                        Element poisonous = combatInfo.get(3);
                        Element maxHit = combatInfo.get(4);
                        Element attackStyles = combatInfo.get(7);

                        Element slayerLevel = combatInfo.get(9);
                        Element slayerXp = null, slayerCat = null;
                        int skip = 4;
                        if(!slayerLevel.text().equalsIgnoreCase("not assigned")) {
                            slayerXp = combatInfo.get(10);
                            slayerCat = combatInfo.get(11);
                            skip = 0;
                        }

                        Element combatStats = combatInfo.get(16 - skip);
                        Element aggressiveStats = combatInfo.get(19 - skip);
                        Element defensiveStats = combatInfo.get(22 - skip);
                        Element other = combatInfo.get(25 - skip);
                        Element atkSpeed = combatInfo.get(27 - skip);

                        if(count != 0) {
                            bw.newLine();
                            bw.newLine();
                        }
                        bw.write(name); bw.newLine();
                        bw.write(combatLevel.text()); bw.newLine();
                        bw.write(hitpoints.text()); bw.newLine();
                        bw.write(aggressive.text()); bw.newLine();
                        bw.write(poisonous.text()); bw.newLine();
                        bw.write(maxHit.text()); bw.newLine();
                        bw.write(attackStyles.text()); bw.newLine();
                        bw.write(slayerLevel.text()); bw.newLine();
                        bw.write(slayerXp == null ? "null" : slayerXp.text()); bw.newLine();
                        bw.write(slayerCat == null ? "null" : slayerCat.text()); bw.newLine();
                        bw.write(combatStats.text()); bw.newLine();
                        bw.write(aggressiveStats.text()); bw.newLine();
                        bw.write(defensiveStats.text()); bw.newLine();
                        bw.write(other.text()); bw.newLine();
                        try {
                            Element img = atkSpeed.select("img").get(1);
                            String src = img.absUrl("src");
                            if(src == null || src.isEmpty())
                                src = img.attr("src");
                            if(src == null || src.isEmpty())
                                throw new IOException("Failed to find img src!");
                            bw.write(src);
                        } catch(Exception e) {
                            String text = atkSpeed.text();
                            if(text == null || text.isEmpty())
                                text = "Unknown";
                            bw.write(text);
                        }
                        String[] check = {
                                name,
                                combatLevel.text(),
                                hitpoints.text(),
                                aggressive.text(),
                                poisonous.text(),
                                maxHit.text(),
                                slayerLevel.text(),
                                slayerXp == null ? "" : slayerXp.text(),
                                slayerCat == null ? "" : slayerCat.text(),
                        };
                        for(String s : check) {
                            if(s.contains(",")) {
                                System.err.println("Multiple legacy entries for: \"" + wikiName + "\"");
                                break;
                            }
                        }
                        count++;
                    }
                }
                success = true;
                return this;
            } catch(Exception e) {
                success = false;
                if(file != null && file.exists()) {
                    //failed so if there's a file delete it!
                    if(!file.delete())
                        file.deleteOnExit();
                }
                if(e.getMessage().contains("HTTP error fetching URL")) {
                    //uhh, page does not exist??
                    return this;
                }
                if(e.getMessage().contains("timed out")) {
                    //timed out, try again!
                    ThreadUtils.sleep(10L);
                    return run();
                }
                ServerWrapper.logError("Error while parsing: " + wikiName, e);
                return this;
            }
        }
    }

    /**
     * Convert dumped files to json
     */

    private static void toJson() throws IOException {
        File temp = FileUtils.get("%HOME%/Dropbox/Runite/Server/data/wiki_dumps/combat/temp");
        if(!temp.exists())
            throw new IOException("Failed to find temp folder!");
        File[] files = temp.listFiles();
        if(files == null || files.length == 0)
            throw new IOException("No dump files found!");
        for(File file : files) {
            if(!file.getName().startsWith("~"))
                toJson(file);
        }
        System.exit(1);
    }

    private static void toJson(File dumpFile) throws IOException {
        List<String[]> npcInfo = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(dumpFile))) {
            String line;
            while((line = br.readLine()) != null) {
                if((line = line.trim()).isEmpty())
                    continue;
                String[] lines = new String[15];
                for(int i = 1; i < lines.length; i++)
                    lines[i] = br.readLine().trim();
                lines[0] = line;
                npcInfo.add(lines);
            }
        }
        List<Info> infos = new ArrayList<>();
        npcInfo.forEach(lines -> {
            Info baseInfo = new Info();
            /**
             * Wiki info
             */
            String name = lines[0];
            String combatLine = lines[1];
            String hitpointsLine = lines[2];
            String aggressiveLine = lines[3];
            String poisonousLine = lines[4];
            String maxHitLine = lines[5];
            String attackStylesLine = lines[6];
            String slayerLevelLine = lines[7];
            String slayerXpLine = lines[8];
            String slayerCategoryLine = lines[9];
            String combatStatsLine = lines[10];
            String aggressiveStatsLine = lines[11];
            String defensiveStatsLine = lines[12];
            String otherLine = lines[13];
            String attackSpeedLine = lines[14];
            /**
             * Parse combat levels (Sometimes multiples.. Lame!)
             */
            combatLine = combatLine.toLowerCase()
                    .replace("combat level", "")
                    .replace("combat", "")
                    .replace("n/a", "")
                    .replace("varies", "")
                    .replace("/", ",")
                    .trim();
            String[] split = combatLine.contains(",") ? combatLine.split(",") : new String[]{combatLine};
            int[] combatLevels = new int[split.length];
            for(int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                if(s.isEmpty() || s.equals("unknown (edit)") || s.equals("?")) {
                    // -1 for unknown combat levels because if we don't know
                    // the combat level then we can't identify the specific npc..
                    s = "-1";
                }
                combatLevels[i] = Integer.valueOf(s);
            }
            /**
             * Parse hitpoints
             */
            hitpointsLine = hitpointsLine.toLowerCase()
                    .replace("hitpoints", "")
                    .replace("n/a", "")
                    .replace("varies", "")
                    .replace("/", ",")
                    .trim();
            split = hitpointsLine.contains(",") ? hitpointsLine.split(",") : new String[]{hitpointsLine};
            int[] hitpoints = new int[split.length];
            for(int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                if(s.isEmpty() || s.equals("unknown (edit)") || s.equals("?")) {
                    // welp if the hp is unknown let's use 1 for now..
                    // we'll have to correct these ourselves later.. :(
                    s = "1";
                }
                hitpoints[i] = Integer.valueOf(s);
            }
            /**
             * Parse aggressiveness
             */
            aggressiveLine = aggressiveLine.toLowerCase()
                    .replace("aggressive", "")
                    .replace("unknown (edit)", "")
                    .trim();
            //uhh let's do this ourselves..
            /**
             * Parse poisonous (Skipping..)
             */
            poisonousLine = poisonousLine.toLowerCase()
                    .replace("poisonous", "")
                    .replace("unknown (edit)", "")
                    .trim();
            //uhh let's make poison through npc combat handlers instead..
            /**
             * Parse max hits
             */
            maxHitLine = maxHitLine.toLowerCase().replace("max hit", "")
                    .replace("n/a", "")
                    .replace("varies", "")
                    .replace("(approx)", "")
                    .replace("(approx.)", "")
                    .replace("~", "")
                    .replace("/", ",")
                    .trim();
            split = maxHitLine.contains(",") ? maxHitLine.split(",") : new String[]{maxHitLine};
            int[] maxHits = new int[split.length];
            for(int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                // welp if the max hit is unknown let's use 1 for now..
                // we'll have to correct these ourselves later.. :(
                if(s.isEmpty() || s.equals("unknown (edit)") || s.equals("?"))
                    s = "1";
                maxHits[i] = Integer.valueOf(s);
            }
            /**
             * Parse attack styles (Really simple.. only checking for melee)
             */
            attackStylesLine = attackStylesLine.toLowerCase().trim();
            if(attackStylesLine.contains("stab"))
                baseInfo.attack_style = AttackStyle.STAB;
            else if(attackStylesLine.contains("slash"))
                baseInfo.attack_style = AttackStyle.SLASH;
            else
                baseInfo.attack_style = AttackStyle.CRUSH;
            /**
             * Parse slayer level
             */
            slayerLevelLine = slayerLevelLine.toLowerCase()
                    .replace("slayer level", "")
                    .replace("n/a", "")
                    .replace("none", "")
                    .replace("null", "")
                    .replace("not assigned", "")
                    .replace("unknown (edit)", "")
                    .replace("unknown", "")
                    .trim();
            if(!slayerLevelLine.isEmpty())
                baseInfo.slayer_level = Integer.valueOf(slayerLevelLine);
            /**
             * Parse slayer xp
             */
            slayerXpLine = slayerXpLine.toLowerCase()
                    .replace("slayer xp", "")
                    .replace("n/a", "")
                    .replace("none", "")
                    .replace("null", "")
                    .replace("not assigned", "")
                    .replace("unknown (edit)", "")
                    .replace("unknown", "")
                    .trim();
            split = slayerXpLine.contains(",") ? slayerXpLine.split(",") : new String[]{slayerXpLine};
            double[] slayerXps = new double[split.length];
            for(int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                if(s.isEmpty() || s.equals("?")) {
                    // If this happens and a task exists, we need to
                    // make sure we manually correct this down below..
                    s = "0";
                }
                slayerXps[i] = Double.valueOf(s);
            }
            /**
             * Parse slayer category (And validate!)
             */
            slayerCategoryLine = slayerCategoryLine.toLowerCase()
                    .replace("category", "")
                    .replace("n/a", "")
                    .replace("none", "")
                    .replace("null", "")
                    .replace("not assigned", "")
                    .replace("unknown (edit)", "")
                    .replace("unknown", "")
                    .replace("/", ",")
                    .trim();
            split = slayerCategoryLine.contains(",") ? slayerCategoryLine.split(",") : new String[]{slayerCategoryLine};
            List<String> slayerCategoryNames = new ArrayList<>(split.length);
            if(!slayerCategoryLine.isEmpty()) {
                for(String catName : split) {
                    catName = catName.replace("elf", "elves")
                            .replace("wolf", "wolves")
                            .replace("dwarf", "dwarves")
                            .replace("jelly", "jellies")
                            .replace("barrow brother", "barrows brothers")
                            .replace("kalphites", "kalphite")
                            .replace("scabarites", "Minions of Scabaras")
                            .replace("spiritual mage", "Spiritual Creatures")
                            .replace("tzhaar", "TzHaar")
                            .trim();
                    if(catName.equals("boss")) {
                        catName = name;
                        catName = catName.replace("Vet'ion Reborn", "Vet'ion");
                    }
                    String[] possibleNames = {
                            StringUtils.capitalizeFirst(catName),
                            StringUtils.capitalizeFirst(catName) + "s",

                            StringUtils.capitalizeFirst(catName.replace(" ", "")),
                            StringUtils.capitalizeFirst(catName.replace(" ", "")) + "s",

                            catName.replace(" ", ""),
                            catName.replace(" ", "") + "s",

                            StringUtils.fixCaps(catName.toUpperCase()),
                            StringUtils.fixCaps(catName.toUpperCase()) + "s",

                            "The " + StringUtils.fixCaps(catName.toUpperCase()),
                            "The " + StringUtils.fixCaps(catName.toUpperCase()) + "s",

                            catName,
                            catName + "s",

                            "The " + catName,
                            "The " + catName + "s"
                    };
                    boolean found = false;
                    for(String s : possibleNames) {
                        if(SlayerTask.TASKS.get(s) != null) {
                            found = true;
                            slayerCategoryNames.add(s);
                            break;
                        }
                    }
                    if(!found)
                        System.err.println(dumpFile.getName() + ": \"" + slayerCategoryLine + "\" task is invalid!");
                }
            }
            /**
             * Parse combat stats (No idea why trim isn't working for split strings..)
             */
            combatStatsLine = combatStatsLine.toLowerCase()
                    .trim()
                    .replace("?", "1")
                    .replace("+", "");
            split = combatStatsLine.split("\\s+");
            baseInfo.attack = Integer.valueOf(split[0].replace(" ", ""));
            baseInfo.strength = Integer.valueOf(split[1].replace(" ", ""));
            baseInfo.defence = Integer.valueOf(split[2].replace(" ", ""));
            baseInfo.ranged = Integer.valueOf(split[3].replace(" ", ""));
            baseInfo.magic = Integer.valueOf(split[4].replace(" ", ""));
            /**
             * Parse aggressive stats (No idea why trim isn't working for split strings..)
             */
            aggressiveStatsLine = aggressiveStatsLine.toLowerCase()
                    .trim()
                    .replace("?", "0")
                    .replace("+", "");
            split = aggressiveStatsLine.split("\\s+");
            baseInfo.stab_attack = Integer.valueOf(split[0].replace(" ", ""));
            baseInfo.slash_attack = Integer.valueOf(split[1].replace(" ", ""));
            baseInfo.crush_attack = Integer.valueOf(split[2].replace(" ", ""));
            baseInfo.magic_attack = Integer.valueOf(split[3].replace(" ", ""));
            baseInfo.ranged_attack = Integer.valueOf(split[4].replace(" ", ""));
            /**
             * Parse defensive stats (No idea why trim isn't working for split strings..)
             */
            defensiveStatsLine = defensiveStatsLine.toLowerCase()
                    .trim()
                    .replace("?", "0")
                    .replace("+", "");
            split = defensiveStatsLine.split("\\s+");
            baseInfo.stab_defence = Integer.valueOf(split[0].replace(" ", ""));
            baseInfo.slash_defence = Integer.valueOf(split[1].replace(" ", ""));
            baseInfo.crush_defence = Integer.valueOf(split[2].replace(" ", ""));
            baseInfo.magic_defence = Integer.valueOf(split[3].replace(" ", ""));
            baseInfo.ranged_defence = Integer.valueOf(split[4].replace(" ", ""));
            /**
             * Parse other
             */
            otherLine = otherLine.toLowerCase()
                    .trim()
                    .replace("?", "0")
                    .replace("+", "");
            split = otherLine.split("\\s+");
            /**
             * Parse other - attack bonus
             */
            int attackBonus = Integer.valueOf(split[2].replace(" ", ""));
            if(attackBonus != 0) {
                if(baseInfo.stab_attack == 0)
                    baseInfo.stab_attack = attackBonus;
                if(baseInfo.slash_attack == 0)
                    baseInfo.slash_attack = attackBonus;
                if(baseInfo.crush_attack == 0)
                    baseInfo.crush_attack = attackBonus;
                if(baseInfo.magic_attack == 0)
                    baseInfo.magic_attack = attackBonus;
                if(baseInfo.ranged_attack == 0)
                    baseInfo.ranged_attack = attackBonus;
            }
            /**
             * Parse other - poison immunity
             */
            String poisonImmunity = split[3].replace(" ", "");
            baseInfo.poison_immunity = poisonImmunity.equals("yes") || poisonImmunity.equals("immune");
            /**
             * Parse other - venom immunity
             */
            String venomImmunity = split[4].replace(" ", "");
            baseInfo.venom_immunity = venomImmunity.equals("yes") || venomImmunity.equals("immune") || venomImmunity.equals("poisons")
                    || venomImmunity.equals("spells"); //this is bugged from Mithril dragons.. it's supposed to be "immune" lol
            /**
             * Parse attack speed
             */
            if(attackSpeedLine.contains("Monster_attack_speed_")) {
                int i = Integer.valueOf(attackSpeedLine.split("Monster_attack_speed_")[1].split(".gif")[0]);
                baseInfo.attack_ticks = Math.max(1, (10 - i));
            } else {
                //Unknown.. guess!
                baseInfo.attack_ticks = 5;
            }
            /**
             * Okay.. all web data parsing is done..
             * Now we need to actually put that data to use..
             */
            for(int i = 0; i < combatLevels.length; i++) {
                int combatLevel = combatLevels[i];
                List<Integer> npcIds = new ArrayList<>();
                NPCDef.forEach(def -> {
                    if(def.combatLevel == combatLevel && def.name.equalsIgnoreCase(name))
                        npcIds.add(def.id);
                });
                if(!npcIds.isEmpty()) {
                    /**
                     * Let's check if any old info was loaded..
                     */
                    Info oldInfo = null;
                    for(int id : npcIds) {
                        NPCDef def = NPCDef.get(id);
                        if(def != null && def.combatInfo != null) {
                            oldInfo = def.combatInfo;
                            break;
                        }
                    }
                    /**
                     * Let's start creating our new info entry..
                     */
                    Info newInfo = new Info();
                    newInfo.ids = new int[npcIds.size()];
                    for(int x = 0; x < npcIds.size(); x++)
                        newInfo.ids[x] = npcIds.get(x);
                    if(oldInfo == null || oldInfo.handler == null)
                        newInfo.handler = "";
                    else
                        newInfo.handler = oldInfo.handler;

                    if(i >= hitpoints.length)
                        newInfo.hitpoints = hitpoints[hitpoints.length - 1];
                    else
                        newInfo.hitpoints = hitpoints[i];

                    newInfo.aggressive_level = combatLevel * 2; //we'll have to unset non-aggro npcs lol xD..

                    if(i >= maxHits.length)
                        newInfo.max_damage = maxHits[maxHits.length - 1];
                    else
                        newInfo.max_damage = maxHits[i];

                    newInfo.attack_style = baseInfo.attack_style;

                    newInfo.slayer_level = Math.max(1, baseInfo.slayer_level);
                    if(i >= slayerXps.length)
                        newInfo.slayer_xp = slayerXps[slayerXps.length - 1];
                    else
                        newInfo.slayer_xp = slayerXps[i];
                    newInfo.slayer_tasks = slayerCategoryNames.isEmpty() ? new String[0] : slayerCategoryNames.toArray(new String[0]);

                    if(newInfo.slayer_xp <= 0 && newInfo.slayer_tasks.length > 0) {
                        //We don't want slayer npcs not giving xp!
                        newInfo.slayer_xp = newInfo.hitpoints;
                    }

                    newInfo.attack = baseInfo.attack;
                    newInfo.strength = baseInfo.strength;
                    newInfo.defence = baseInfo.defence;
                    newInfo.ranged = baseInfo.ranged;
                    newInfo.magic = baseInfo.magic;

                    newInfo.stab_attack = baseInfo.stab_attack;
                    newInfo.slash_attack = baseInfo.slash_attack;
                    newInfo.crush_attack = baseInfo.crush_attack;
                    newInfo.magic_attack = baseInfo.magic_attack;
                    newInfo.ranged_attack = baseInfo.ranged_attack;

                    newInfo.stab_defence = baseInfo.stab_defence;
                    newInfo.slash_defence = baseInfo.slash_defence;
                    newInfo.crush_defence = baseInfo.crush_defence;
                    newInfo.magic_defence = baseInfo.magic_defence;
                    newInfo.ranged_defence = baseInfo.ranged_defence;

                    newInfo.poison_immunity = baseInfo.poison_immunity;
                    newInfo.venom_immunity = baseInfo.venom_immunity;

                    newInfo.attack_ticks = baseInfo.attack_ticks;
                    newInfo.death_ticks = oldInfo == null ? 2 : oldInfo.death_ticks;
                    newInfo.respawn_ticks = oldInfo == null ? 50 : oldInfo.respawn_ticks;

                    newInfo.spawn_animation = oldInfo == null ? -1 : oldInfo.spawn_animation;
                    newInfo.attack_animation = oldInfo == null ? -1 : oldInfo.attack_animation;
                    newInfo.defend_animation = oldInfo == null ? -1 : oldInfo.defend_animation;
                    newInfo.death_animation = oldInfo == null ? -1 : oldInfo.death_animation;

                    NPCDef def = NPCDef.get(npcIds.get(0));
                    SortedSet<Integer> anims = AnimDef.findAnimationsWithSameRigging(def.walkAnimation, def.standAnimation, def.walkBackAnimation, def.walkLeftAnimation, def.walkRightAnimation);
                    if(anims != null && anims.contains(836)) { //human like npc
                        if(newInfo.attack_animation == -1)
                            newInfo.attack_animation = 422;
                        if(newInfo.defend_animation == -1)
                            newInfo.defend_animation = 424;
                        if(newInfo.death_animation == -1) {
                            newInfo.death_animation = 836;
                            newInfo.death_ticks = 2;
                        }
                    }

                    //newInfo.temp = def.name + " (" + combatLevel + ")!@!@"; //custom bit used for json file.. can remove later..
                    infos.add(newInfo);
                }
            }
        });
        if(infos.isEmpty()) {
            System.err.println(dumpFile.getName() + " failed to parse any npc combat defs!");
            return;
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FileUtils.get("%HOME%/Dropbox/Vorkath/Server/data/wiki_dumps/combat/json/" + dumpFile.getName().replace(".txt", ".json"))))) {
            bw.write(JsonUtils.toPrettyJson(infos)
                    .replace(": [\n", ": [ ")
                    .replace("\n" + "    ],", " ],")
                    .replace("\": [       ", "\": [ ")
                    .replace(",\n" + "      ", ", ")

                    .replace("\n" + "    \"temp\": \"", " #")
                    .replace("!@!@\",", "")

                    .replace("\n" + "    \"hitpoints\":", "\n    //Combat Info\n    \"hitpoints\":")
                    .replace("\n" + "    \"slayer_level\":", "\n    //Slayer Info\n    \"slayer_level\":")
                    .replace("\n" + "    \"attack\":", "\n    //Combat Stats\n    \"attack\":")
                    .replace("\n" + "    \"stab_attack\":", "\n    //Aggressive Stats\n    \"stab_attack\":")
                    .replace("\n" + "    \"stab_defence\":", "\n    //Defensive Stats\n    \"stab_defence\":")
                    .replace("\n" + "    \"poison_immunity\":", "\n    //Immunites\n    \"poison_immunity\":")
                    .replace("\n" + "    \"attack_ticks\":", "\n    //Speeds\n    \"attack_ticks\":")
                    .replace("\n" + "    \"spawn_animation\":", "\n    //Animations\n    \"spawn_animation\":")
            );
        } catch(Exception e) {
            ServerWrapper.logError("Failed to replace", e);
        }
/*
        HashSet<Integer> ids = new LinkedHashSet<>(); //hash set to prevent user error (multiple ids entered, lol)
        infos.forEach(info -> {
            for(int id : info.ids)
                ids.add(id);
        });
        npc_drops.dump(dumpFile.getName().replace(".txt", ""), ids.toArray(new Integer[0]));
*/
    }

}