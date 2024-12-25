package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class KillCounter {

    private static List<Function<Player, KillCounter>> bossList, slayerList;

    static {
        /* Setting kill counters on npc defs */
        /* bosses that are killed through normal means */
        set(p -> p.kreeArraKills, "kree'Arra");
        set(p -> p.commanderZilyanaKills, "commander Zilyana");
        set(p -> p.generalGraardorKills, "general Graardor");
        set(p -> p.krilTsutsarothKills, "k'ril Tsutsaroth");
        set(p -> p.dagannothRexKills, "dagannoth Rex");
        set(p -> p.dagannothPrimeKills, "dagannoth Prime");
        set(p -> p.dagannothSupremeKills, "dagannoth Supreme");
        set(p -> p.giantMoleKills, "giant Mole");
        set(p -> p.kalphiteQueenKills, "kalphite Queen");
        set(p -> p.kingBlackDragonKills, "king black Dragon");
        set(p -> p.callistoKills, "callisto");
        set(p -> p.venenatisKills, "venenatis");
        set(p -> p.vetionKills, "vet'ion");
        set(p -> p.chaosElementalKills, "chaos Elemental");
        set(p -> p.chaosFanaticKills, "chaos Fanatic");
        set(p -> p.crazyArchaeologistKills, "crazy Archaeologist");
        set(p -> p.scorpiaKills, 6615);
        set(p -> p.corporealBeastKills, "corporeal Beast");
        set(p -> p.zulrahKills, "zulrah");
        set(p -> p.krakenKills, 494);
        set(p -> p.thermonuclearSmokeDevilKills, 499);
        set(p -> p.cerberusKills, "cerberus");
        set(p -> p.abyssalSireKills, "abyssal Sire");
        set(p -> p.skotizoKills, "skotizo");
        set(p -> p.oborKills, "obor");
        set(p -> p.derangedArchaeologistKills, "deranged Archaeologist");
        set(p -> p.elvargKills, 6118);
        set(p -> p.vorkathKills, "vorkath");

        /* Slayer monsters */
        set(p -> p.crawlingHandKills, "crawling Hand");
        set(p -> p.caveBugKills, "cave Bug");
        set(p -> p.caveCrawlerKills, "cave Crawler");
        set(p -> p.bansheeKills, "banshee");
        set(p -> p.caveSlimeKills, "cave Slime");
        set(p -> p.rockslugKills, "rockslug");
        set(p -> p.desertLizardKills, "desert Lizard");
        set(p -> p.cockatriceKills, "cockatrice");
        set(p -> p.pyrefiendKills, "pyrefiend");
        set(p -> p.mogreKills, "mogre");
        set(p -> p.harpieBugSwarmKills, "harpie Bug Swarm");
        set(p -> p.wallBeastKills, "wall Beast");
        set(p -> p.killerwattKills, "killerwatt");
        set(p -> p.molaniskKills, "molanisk");
        set(p -> p.basiliskKills, "basilisk");
        set(p -> p.seaSnakeKills, "sea Snake");
        set(p -> p.terrorDogKills, "terror Dog");
        set(p -> p.feverSpiderKills, "fever Spider");
        set(p -> p.infernalMageKills, "infernal Mage");
        set(p -> p.brineRatKills, "brine Rat");
        set(p -> p.bloodveldKills, "bloodveld");
        set(p -> p.jellyKills, "jelly");
        set(p -> p.turothKills, "turoth");
        set(p -> p.mutatedZygomiteKills, "mutated Zygomite");
        set(p -> p.caveHorrorKills, "cave Horror");
        set(p -> p.aberrantSpectreKills, "aberrant Spectre");
        set(p -> p.spiritualRangerKills, "spiritual Ranger");
        set(p -> p.dustDevilKills, "dust Devil");
        set(p -> p.spiritualWarriorKills, "spiritual Warrior");
        set(p -> p.kuraskKills, "kurask");
        set(p -> p.skeletalWyvernKills, "skeletal Wyvern");
        set(p -> p.gargoyleKills, "gargoyle");
        set(p -> p.nechryaelKills, "nechryael");
        set(p -> p.spiritualMageKills, "spiritual Mage");
        set(p -> p.abyssalDemonKills, "abyssal Demon");
        set(p -> p.caveKrakenKills, "cave Kraken");
        set(p -> p.darkBeastKills, "dark Beast");
        set(p -> p.smokeDevilKills, "smoke Devil");
//        set(p -> p.superiorCreatureKills, "superior Creature"); // need a proper way to add this
        set(p -> p.brutalBlackDragonKills, "brutal Black Dragon");
        set(p -> p.fossilIslandWyvernsKills, 7792, 7793, 7794, 7795);

        /* Other monsters */
        set(p -> p.adamantDragonKills, "adamant dragon");
        set(p -> p.runeDragonKills, "rune dragon");
        set(p -> p.demonicGorillaKills, "demonic gorilla");
        set(p -> p.jungleDemonKills, "jungle demon");

        /* Setting counter properties */
        LoginListener.register(p -> {
            p.kreeArraKills.setName("Kree'Arra").messageOnKill();
            p.commanderZilyanaKills.setName("Commander Zilyana").messageOnKill();
            p.generalGraardorKills.setName("General Graardor").messageOnKill();
            p.krilTsutsarothKills.setName("K'ril Tsutsaroth").messageOnKill();
            p.dagannothRexKills.setName("Dagannoth Rex").messageOnKill();
            p.dagannothPrimeKills.setName("Dagannoth Prime").messageOnKill();
            p.dagannothSupremeKills.setName("Dagannoth Supreme").messageOnKill();
            p.giantMoleKills.setName("Giant Mole").messageOnKill();
            p.kalphiteQueenKills.setName("Kalphite Queen").messageOnKill();
            p.kingBlackDragonKills.setName("King Dragon").messageOnKill();
            p.callistoKills.setName("Callisto").messageOnKill();
            p.venenatisKills.setName("Venenatis").messageOnKill();
            p.vetionKills.setName("Vet'ion").messageOnKill();
            p.chaosElementalKills.setName("Chaos Elemental").messageOnKill();
            p.chaosFanaticKills.setName("Chaos Fanatic").messageOnKill();
            p.crazyArchaeologistKills.setName("Crazy Archaeologist").messageOnKill();
            p.scorpiaKills.setName("Scorpia").messageOnKill();
            p.corporealBeastKills.setName("Corporeal Beast").messageOnKill();
            p.zulrahKills.setName("Zulrah").messageOnKill();
            p.jadCounter.setName("TzTok-Jad").messageOnKill();
            p.zukKills.setName("TzKal-Zuk").messageOnKill();
            p.krakenKills.setName("Kraken").messageOnKill();
            p.thermonuclearSmokeDevilKills.setName("Thermonuclear Smoke Devil").messageOnKill();
            p.cerberusKills.setName("Cerberus").messageOnKill();
            p.abyssalSireKills.setName("Abyssal Sire").messageOnKill();
            p.skotizoKills.setName("Skotizo").messageOnKill();
            p.wintertodtKills.setName("Wintertodt").messageOnKill();
            p.oborKills.setName("Obor").messageOnKill();
            p.chambersofXericKills.setName("Chambers of Xeric").messageOnKill();
            p.derangedArchaeologistKills.setName("Deranged Archaeologist").messageOnKill();
            p.barrowsChestsLooted.setName("Barrows Chests").messageOnKill();
            p.elvargKills.setName("Elvarg").messageOnKill();
            p.vorkathKills.setName("Vorkath").messageOnKill();

            p.crawlingHandKills.setName("Crawling Hands");
            p.caveBugKills.setName("Cave Bugs");
            p.caveCrawlerKills.setName("Cave Crawlers");
            p.bansheeKills.setName("Banshees");
            p.caveSlimeKills.setName("Cave Slimes");
            p.rockslugKills.setName("Rockslugs");
            p.desertLizardKills.setName("Desert Lizards");
            p.cockatriceKills.setName("Cockatrices");
            p.pyrefiendKills.setName("Pyrefiends");
            p.mogreKills.setName("Mogres");
            p.harpieBugSwarmKills.setName("Harpie Bug Swarms");
            p.wallBeastKills.setName("Wall Beasts");
            p.killerwattKills.setName("Killerwatts");
            p.molaniskKills.setName("Molanisks");
            p.basiliskKills.setName("Basilisks");
            p.seaSnakeKills.setName("Sea Snakes");
            p.terrorDogKills.setName("Terror Dogs");
            p.feverSpiderKills.setName("Fever Spiders");
            p.infernalMageKills.setName("Infernal Mages");
            p.brineRatKills.setName("Brine Rats");
            p.bloodveldKills.setName("Bloodvelds");
            p.jellyKills.setName("Jellies");
            p.turothKills.setName("Turoth");
            p.mutatedZygomiteKills.setName("Mutated Zygomites");
            p.caveHorrorKills.setName("Cave Horrors");
            p.aberrantSpectreKills.setName("Aberrant Spectres");
            p.spiritualRangerKills.setName("Spiritual Rangers");
            p.dustDevilKills.setName("Dust Devils");
            p.spiritualWarriorKills.setName("Spiritual Warriors");
            p.kuraskKills.setName("Kurask");
            p.skeletalWyvernKills.setName("Skeletal Wyverns");
            p.gargoyleKills.setName("Gargoyles");
            p.nechryaelKills.setName("Nechryael");
            p.spiritualMageKills.setName("Spiritual Mages");
            p.abyssalDemonKills.setName("Abyssal Demons");
            p.caveKrakenKills.setName("Cave Krakens");
            p.darkBeastKills.setName("Dark Beasts");
            p.smokeDevilKills.setName("Smoke Devils");
            p.superiorCreatureKills.setName("Superior Creatures");
            p.brutalBlackDragonKills.setName("Brutal Black Dragons");
            p.fossilIslandWyvernsKills.setName("Fossil Island Wyvernss");

            p.jungleDemonKills.setAchievement(Achievement.WELCOME_TO_THE_JUNGLE);
        });

        /* Building list that will be used in the boss interface  - Bosses not ingame are commented out*/
        bossList = Arrays.asList(
                p -> p.kreeArraKills,
                p -> p.commanderZilyanaKills,
                p -> p.generalGraardorKills,
                p -> p.krilTsutsarothKills,
                p -> p.dagannothRexKills,
                p -> p.dagannothPrimeKills,
                p -> p.dagannothSupremeKills,
                p -> p.giantMoleKills,
                p -> p.kalphiteQueenKills,
                p -> p.kingBlackDragonKills,
                p -> p.callistoKills,
                p -> p.venenatisKills,
                p -> p.vetionKills,
                p -> p.chaosElementalKills,
                p -> p.chaosFanaticKills,
                p -> p.crazyArchaeologistKills,
                p -> p.scorpiaKills,
                p -> p.barrowsChestsLooted,
                p -> p.corporealBeastKills,
                p -> p.zulrahKills,
                p -> p.jadCounter,
                p -> p.zukKills,
                p -> p.krakenKills,
                p -> p.thermonuclearSmokeDevilKills,
                p -> p.cerberusKills,
                p -> p.abyssalSireKills,
                p -> p.skotizoKills,
                p -> p.wintertodtKills,
                p -> p.elvargKills,
//                p -> p.oborKills,
                p -> p.chambersofXericKills,
//                p -> p.derangedArchaeologistKills
                p -> p.vorkathKills
        );

        /* Slayer list */
        slayerList = Arrays.asList(
                p -> p.crawlingHandKills,
                p -> p.caveBugKills,
                p -> p.caveCrawlerKills,
                p -> p.bansheeKills,
                p -> p.caveSlimeKills,
                p -> p.rockslugKills,
                p -> p.desertLizardKills,
                p -> p.cockatriceKills,
                p -> p.pyrefiendKills,
//                p -> p.mogreKills,
//                p -> p.harpieBugSwarmKills,
//                p -> p.wallBeastKills,
//                p -> p.killerwattKills,
//                p -> p.molaniskKills,
                p -> p.basiliskKills,
//                p -> p.seaSnakeKills,
//                p -> p.terrorDogKills,
//                p -> p.feverSpiderKills,
                p -> p.infernalMageKills,
//                p -> p.brineRatKills,
                p -> p.bloodveldKills,
                p -> p.jellyKills,
                p -> p.turothKills,
//                p -> p.mutatedZygomiteKills,
                p -> p.caveHorrorKills,
                p -> p.aberrantSpectreKills,
                p -> p.spiritualRangerKills,
                p -> p.dustDevilKills,
                p -> p.spiritualWarriorKills,
                p -> p.kuraskKills,
                p -> p.skeletalWyvernKills,
                p -> p.gargoyleKills,
                p -> p.nechryaelKills,
                p -> p.spiritualMageKills,
                p -> p.abyssalDemonKills,
                p -> p.caveKrakenKills,
                p -> p.darkBeastKills,
                p -> p.smokeDevilKills,
//                p -> p.superiorCreatureKills,
                p -> p.brutalBlackDragonKills
//                p -> p.fossilIslandWyvernsKills
        );

        /* Interface handler */
        InterfaceHandler.register(Interface.KILL_COUNTER, h -> {
            h.actions[16] = (SlotAction) (p, slot) -> p.activeKillLogSlot = slot;
            h.actions[25] = (SimpleAction) KillCounter::resetStreak;
            h.closedAction = (player, integer) -> {
              player.activeKillLogList = null;
              player.activeKillLogSlot = -1;
            };
        });
    }

    private static void resetStreak(Player p) {
        if (p.activeKillLogList == null || p.activeKillLogSlot < 0 || p.activeKillLogSlot >= p.activeKillLogList.size()) {
            return;
        }
        KillCounter counter = p.activeKillLogList.get(p.activeKillLogSlot).apply(p);
        counter.resetStreak();
        p.sendMessage("Your " + counter.getName() + " streak has been reset.");
        p.getPacketSender().sendClientScript(1588, "i", p.activeKillLogSlot);
        p.activeKillLogSlot = -1;
    }

    public static void openOwnBoss(Player player) {
        open(player, player, bossList, "Boss Kill Log");
    }

    public static void openOwnSlayer(Player player) {
        open(player, player, slayerList, "Slayer Kill Log");
    }

    public static void openBoss(Player player, Player killer) {
        open(player, killer, bossList, killer.getName() + "'s Boss Kill Log");
    }

    public static void openSlayer(Player player, Player killer) {
        open(player, killer, slayerList, killer.getName() + "'s Slayer Kill Log");
    }

    private static void open(Player player, Player killer, List<Function<Player, KillCounter>> list, String title) {
        StringBuilder names = new StringBuilder();
        StringBuilder totalCounts = new StringBuilder();
        StringBuilder streaks = new StringBuilder();
        for (Function<Player, KillCounter> f : list) {
            KillCounter kc = f.apply(player);
            if (kc.getName() == null) {
                names.append("null");
            } else {
                names.append(kc.getName());
            }
            names.append("|");
            totalCounts.append(NumberUtils.formatNumber(kc.getKills()));
            totalCounts.append("|");
            streaks.append(NumberUtils.formatNumber(kc.getStreak()));
            streaks.append("|");
        }
        player.openInterface(InterfaceType.MAIN, Interface.KILL_COUNTER);
        player.getPacketSender().sendClientScript(1584,
                "sssis",
                names.toString(),
                totalCounts.toString(),
                streaks.toString(),
                list.size(),
                title
        );
        if (player == killer) {
            player.getPacketSender().sendAccessMask(Interface.KILL_COUNTER, 16, 0, list.size(), 2);
            player.activeKillLogList = list;
        } else {
            player.getPacketSender().setHidden(Interface.KILL_COUNTER, 16, true);
        }
    }

    private static void set(Function<Player, KillCounter> get, String... names) {
        List<String> search = Arrays.asList(names);
        NPCDef.cached.values().stream()
                .filter(Objects::nonNull)
                .filter(def -> {
                    for (String name : search)
                        if (def.name.toLowerCase().contains(name.toLowerCase())) {
                            return true;
                        }
                    return false;
                }).forEach(def -> def.killCounter = get);
    }

    private static void set(Function<Player, KillCounter> get, Integer... ids) {
        List<Integer> search = Arrays.asList(ids);
        NPCDef.cached.values().stream()
                .filter(Objects::nonNull)
                .filter(def -> search.contains(def.id))
                .forEach(def -> def.killCounter = get);
    }

    @Expose private int kills;

    @Expose private int streak;

    private String name;
    private boolean messageOnKill;

    private Achievement achievement;

    public void increment(Player player) {
        kills++;
        streak++;
        if (messageOnKill)
            player.sendMessage("Your " + name +" kill count is: " + Color.RED.wrap(NumberUtils.formatNumber(kills)));
        if (achievement != null)
            achievement.update(player);
    }

    public void resetStreak() {
        streak = 0;
    }

    public int getKills() {
        return kills;
    }

    public int getStreak() {
        return streak;
    }

    public String getName() {
        return name;
    }

    public KillCounter setName(String name) {
        this.name = name;
        return this;
    }

    public KillCounter messageOnKill() {
        messageOnKill = true;
        return this;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
}
