package io.ruin.model.inter.journal;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementCategory;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.activities.wilderness.ActiveVolcano;
import io.ruin.model.activities.wilderness.BloodyMerchant;
import io.ruin.model.activities.wilderness.BossEvent;
import io.ruin.model.activities.wilderness.Hotspot;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.handlers.TabJournal;
import io.ruin.model.inter.journal.bestiary.BestiarySearchDrop;
import io.ruin.model.inter.journal.bestiary.BestiarySearchMonster;
import io.ruin.model.inter.journal.bestiary.BestiarySearchResult;
import io.ruin.model.inter.journal.main.*;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.journal.presets.main.Hybrid;
import io.ruin.model.inter.journal.presets.main.MainNoHonorTribrid;
import io.ruin.model.inter.journal.presets.main.Melee;
import io.ruin.model.inter.journal.presets.pure.PureMelee;
import io.ruin.model.inter.journal.presets.pure.PureNoHonorTribrid;
import io.ruin.model.inter.journal.presets.pure.RangeDDS;
import io.ruin.model.inter.journal.toggles.*;

import java.util.ArrayList;
import java.util.Arrays;

public enum Journal {

    MAIN,
    ACHIEVEMENTS,
    PRESETS,
    TOGGLES,
    BESTIARY;

    public Journal next() {
        int index = ordinal() + 1;
        if (index > values().length - 1) {
            index = 1;
        }
        return values()[index];
    }

    public Journal previous() {
        int index = ordinal() - 1;
        if (index <= 0) {
            index = 4;
        }
        return values()[index];
    }

    private void addCategory(String name) {
        addEntry(new Category(name));
    }

    /**
     * Entries
     */

    private ArrayList<JournalEntry> entries = new ArrayList<>();

    private void addEntry(JournalEntry entry) {
        entry.childId = 8 + entries.size();
        entries.add(entry);
        entries.trimToSize();
    }

    /**
     * Sending
     */
    public void send(Player player) {
        player.journal = this;
        TabJournal.swap(player, Interface.SERVER_TAB);
        if(this == BESTIARY && player.bestiarySearchResults != null) {
            for(int childId = 9; childId <= 47; childId++) {
                player.getPacketSender().sendClientScript(135, "ii", Interface.SERVER_TAB << 16 | childId, 494);
                player.getPacketSender().sendString(Interface.SERVER_TAB, childId, "");
            }
            for(JournalEntry entry : player.bestiarySearchResults)
                entry.send(player);
        } else {
            player.bestiarySearchResults = null;
            for(JournalEntry entry : entries) {
                entry.send(player);
            }
        }
    }

    /**
     * Selecting
     */
    public void select(Player player, int childId) {
        if(this == BESTIARY && player.bestiarySearchResults != null) {
            if (childId < 0 || childId >= player.bestiarySearchResults.size() + 2)
                return;
            player.bestiarySearchResults.get(childId - 1).select(player);
        } else {
            if (childId < 0 || childId >= entries.size())
                return;
            entries.get(childId).select(player);
        }
    }

    static {
//        /*
//         * Main
//         */
//        MAIN.addCategory("<img=85> Server");
//        MAIN.addEntry(Uptime.INSTANCE);
//        MAIN.addEntry(PlayersOnline.INSTANCE);
//        MAIN.addEntry(WildernessCount.INSTANCE);
//        MAIN.addEntry(PVPInstanceCount.INSTANCE);
//
//        MAIN.addCategory("");
//        MAIN.addCategory("<img=67> Player Statistics");
//        MAIN.addEntry(TotalSpent.INSTANCE);
//        MAIN.addEntry(PlayTime.INSTANCE);
//        MAIN.addEntry(ExpBonusTime.INSTANCE);
//        MAIN.addEntry(DropBoostTime.INSTANCE);
//        MAIN.addEntry(PetDropBoostTime.INSTANCE);
//
//        MAIN.addCategory("");
//        MAIN.addCategory("<img=65> PVP Statistics");
//        MAIN.addEntry(PKRating.INSTANCE);
//        MAIN.addEntry(new TotalKills());
//        MAIN.addEntry(new TotalDeaths());
//        MAIN.addEntry(new KillDeathRatio());
//        MAIN.addEntry(new KillingSpree());
//        MAIN.addEntry(new HighestKillingSpree());
//        MAIN.addEntry(new HighestShutdown());
//
//        MAIN.addCategory("");
//        MAIN.addCategory("<img=123> Events");
//        BossEvent[] bossEvents = new BossEvent[BossEvent.BOSSES.length];
//        for (int i = 0; i < bossEvents.length; i++)
//                MAIN.addEntry(bossEvents[i] = new BossEvent(i));
//        MAIN.addEntry(Hotspot.Entry.INSTANCE);
//        MAIN.addEntry(DeadmanChestEntry.INSTANCE);
//        MAIN.addEntry(ActiveVolcano.Entry.INSTANCE);
//        //MAIN.addEntry(SupplyChest.Entry.INSTANCE);
//        MAIN.addEntry(BloodyMerchant.Entry.INSTANCE);
//
//        MAIN.addCategory("");
//        MAIN.addCategory("<img=53> Daily Tasks");
//        MAIN.addEntry(DailyTask.PointsEntry.ENTRY);
//        for (int i = 0; i < 3; i++)
//            MAIN.addEntry(DailyTask.TaskEntry.ENTRIES[i]);
//
//
//
//        /*
//         * Achievements
//         */
        for (AchievementCategory cat : AchievementCategory.values()) {
            ACHIEVEMENTS.addCategory(cat.name());
            Arrays.stream(Achievement.values())
                    .filter(ach -> ach.getCategory() == cat)
                    .sorted((a1, a2) -> a1.getListener().name().compareToIgnoreCase(a2.getListener().name()))
                    .map(Achievement::toEntry)
                    .forEachOrdered(ACHIEVEMENTS::addEntry);
        }
//
//        /*
//         * Presets
//         */
//        PRESETS.addCategory("Main");
//        PRESETS.addEntry(new Melee());
//        PRESETS.addEntry(new Hybrid());
//        PRESETS.addEntry(new MainNoHonorTribrid());
//
//        PRESETS.addCategory("");
//        PRESETS.addCategory("Pure");
//        PRESETS.addEntry(new PureMelee());
//        PRESETS.addEntry(new RangeDDS());
//        PRESETS.addEntry(new PureNoHonorTribrid());
//
//        PRESETS.addCategory("");
//        PRESETS.addCategory("Custom");
//        for(PresetCustom preset : PresetCustom.ENTRIES)
//            PRESETS.addEntry(preset);
//
//        /*
//         * Toggles
//         */
        TOGGLES.addCategory("Combat");
        TOGGLES.addEntry(new TargetOverlay());
        TOGGLES.addEntry(new DragSetting());
        TOGGLES.addEntry(new ShowWidgets());
        TOGGLES.addEntry(new BountyOverlay());
        TOGGLES.addEntry(new KDOverlay());

        TOGGLES.addCategory("");
        TOGGLES.addCategory("Edgeville Blacklist");
        for (EdgevilleBlacklist blacklistedUsers : EdgevilleBlacklist.ENTRIES)
            TOGGLES.addEntry(blacklistedUsers);

        TOGGLES.addCategory("");
        TOGGLES.addCategory("Bounty Hunter");
        TOGGLES.addEntry(new BountyHunterTargeting());
        TOGGLES.addEntry(new BountyHunterStreaks());

        TOGGLES.addCategory("");
        TOGGLES.addCategory("Broadcasts");
        TOGGLES.addEntry(new BroadcastBossEvent());
        TOGGLES.addEntry(new BroadcastActiveVolcano());
        TOGGLES.addEntry(new BroadcastHotspot());
        TOGGLES.addEntry(new BroadcastSupplyChest());
        TOGGLES.addEntry(new BroadcastBloodyMerchant());
        TOGGLES.addEntry(new BroadcastHotspot());
        TOGGLES.addEntry(new BroadcastAnnouncements());
        TOGGLES.addEntry(new BroadcastTournaments());

        TOGGLES.addCategory("");
        TOGGLES.addCategory("Miscellaneous");
        TOGGLES.addEntry(new BreakVials());
        TOGGLES.addEntry(new DiscardBuckets());
        TOGGLES.addEntry(new HideIcon());
        TOGGLES.addEntry(RiskProtection.INSTANCE);
        TOGGLES.addEntry(new HideYells());
        TOGGLES.addEntry(new ExperienceLock());

        /*
         * Bestiary
         */
        BESTIARY.addCategory("Search");
        BESTIARY.addEntry(BestiarySearchDrop.INSTANCE);
        BESTIARY.addEntry(BestiarySearchMonster.INSTANCE);

        BESTIARY.addCategory("");
        BESTIARY.addCategory("Popular");
        BESTIARY.addEntry(new BestiarySearchResult(415)); //Abyssal demon
        BESTIARY.addEntry(new BestiarySearchResult(5862)); //Cerberus
        BESTIARY.addEntry(new BestiarySearchResult(3162)); //Kree'arra
        BESTIARY.addEntry(new BestiarySearchResult(2042)); //Zulrah
        BESTIARY.addEntry(new BestiarySearchResult(2215)); //General Graardor
        BESTIARY.addEntry(new BestiarySearchResult(319)); //Corporeal Beast
        BESTIARY.addEntry(new BestiarySearchResult(6619)); //Chaos Fanatic
        BESTIARY.addEntry(new BestiarySearchResult(3129)); //K'ril Tsutsaroth
        BESTIARY.addEntry(new BestiarySearchResult(6503)); //Callisto
        BESTIARY.addEntry(new BestiarySearchResult(2265)); //Dagannoth Supreme

        /*
         * Updating
         */
        LoginListener.register(player -> {
            TOGGLES.entries.forEach(entry -> entry.send(player)); // Sending these on login forces the custom varp based settings to be updated
//            player.journal.send(player);
//            player.addEvent(event -> {
//                while(true) {
//                    if(player.journal == MAIN) {
//                        Uptime.INSTANCE.send(player);
//                        PlayersOnline.INSTANCE.send(player);
//                        WildernessCount.INSTANCE.send(player);
//                        TotalSpent.INSTANCE.send(player);
//                        PlayTime.INSTANCE.send(player);
//                        DeadmanChestEntry.INSTANCE.send(player);
//                        PVPInstanceCount.INSTANCE.send(player);
//                        ActiveVolcano.Entry.INSTANCE.send(player);
//                        Hotspot.Entry.INSTANCE.send(player);
//                        //SupplyChest.Entry.INSTANCE.send(player);
//                        BloodyMerchant.Entry.INSTANCE.send(player);
//                        ExpBonusTime.INSTANCE.send(player);
//                        DropBoostTime.INSTANCE.send(player);
//                        PetDropBoostTime.INSTANCE.send(player);
//                        AppreciationPoints.INSTANCE.send(player);
//                        WildernessPoints.INSTANCE.send(player);
//                        for (BossEvent bossEvent : bossEvents) bossEvent.send(player);
//                    }
//                    event.delay(10);
//                }
//            });
        });
    }

    public ArrayList<JournalEntry> getEntries() {
        return entries;
    }
}