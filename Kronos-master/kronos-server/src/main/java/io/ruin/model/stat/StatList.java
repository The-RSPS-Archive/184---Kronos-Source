package io.ruin.model.stat;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.summerevent.SummerTokens;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.skillcapes.HitpointsSkillCape;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.skills.CapePerks;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Broadcast;

import java.util.List;

public class StatList {

    @Expose private Stat[] stats;

    @Expose private StatCounter[] counters;

    public int total99s;

    public int totalLevel;

    public long totalXp;

    private Player player;

    public void init(Player player) {
        this.player = player;
        if(stats == null) {
            StatType[] types = StatType.values();
            stats = new Stat[types.length];
            for(int id = 0; id < types.length; id++) {
                StatType type = types[id];
                if(type == StatType.Hitpoints)
                    stats[id] = new Stat(10, 1154);
                else
                    stats[id] = new Stat(1, 0);
                stats[id].updated = true;
            }
            return;
        }
        if(counters == null) {
            counters = new StatCounter[stats.length + 1];
            for(int i = 0; i < counters.length; i++)
                counters[i] = new StatCounter(i);
        } else {
            for(int i = 0; i < counters.length; i++) {
                StatCounter counter = counters[i];
                counter.index = i;
                counter.send(player); //i don't think we need to send if we have configs save ????
            }
        }
        for(Stat stat : stats) {
            stat.fixedLevel = Stat.levelForXp(stat.experience);
            stat.updated = true;
        }
    }

    public void set(StatType type, int level) {
        set(type, level, Stat.xpForLevel(level));
    }

    public void set(StatType type, int level, int experience) {
        Stat stat = get(type);
        stat.currentLevel = stat.fixedLevel = level;
        stat.experience = experience;
        stat.updated = true;
    }

    public boolean check(StatType type, int lvlReq) {
        return get(type).currentLevel >= lvlReq;
    }

    /**
     * Checks the fixed (not boosted) level.
     */
    public boolean checkFixed(StatType type, int levelReq, String action) {
        if (get(type).fixedLevel < levelReq) {
            player.sendMessage("You need " + type.descriptiveName + " level of " + levelReq + " or higher to " + action + ".");
            return false;
        }
        return true;
    }

    public boolean check(StatType type, int levelReq, String action) {
        if(!check(type, levelReq)) {
            player.sendMessage("You need " + type.descriptiveName + " level of " + levelReq + " or higher to " + action + ".");
            return false;
        }
        return true;
    }

    public boolean check(StatType type, int lvlReq, int itemId, String action) {
        if(!check(type, lvlReq)) {
            player.dialogue(new ItemDialogue().one(itemId, "You need " + type.descriptiveName + " level of " + lvlReq + " or higher to " + action + "."));
            return false;
        }
        return true;
    }

    public boolean check(StatType type, int lvlReq, int itemId1, int itemId2, String action) {
        if(!check(type, lvlReq)) {
            player.dialogue(new ItemDialogue().two(itemId1, itemId2, "You need " + type.descriptiveName + " level of " + lvlReq + " or higher to " + action + "."));
            return false;
        }
        return true;
    }

    public void restore(boolean restoreBoosted) {
        for(Stat stat : stats) {
            if(!restoreBoosted && stat.currentLevel > stat.fixedLevel)
                continue;
            stat.alter(stat.fixedLevel);
        }
    }

    public void addXp(StatType type, double amount, boolean useMultiplier) {

        int statId = type.ordinal();
        Stat stat = stats[statId];
        double baseAmount = amount;

        SummerTokens.xpDrop(player);

        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                boolean hasEffect = upgrades != null;
                if (hasEffect) {
                    for (String s : upgrades) {
                        try {
                            if (s.equalsIgnoreCase("empty"))
                                continue;
                            ItemEffect effect = ItemEffect.valueOf(s);
                            amount *= effect.getUpgrade().giveExperienceBoost(player, type);
                        } catch (Exception ex) {
                            System.err.println("Unknown upgrade { " + s + " } found!");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

        //do pre exp drop here

        if (player.experienceLock) {
            return;
        }

        if(useMultiplier) {
            if(World.xpMultiplier > 0)
                amount += baseAmount * (World.xpMultiplier - 1);
            amount += baseAmount * (Wilderness.getXPModifier(player, type));
        }
        /*
         * XP Modes
         */
        if (useMultiplier) {
            if (stat.fixedLevel >= 99) {
                amount *= player.xpMode.getAfter99Rate();
            } else {
                if (type.isCombat()) {
                    amount *= player.xpMode.getCombatRate();
                } else {
                    amount *= player.xpMode.getSkillRate();
                }
            }
        }


        /**
         * 50% experience boost from scroll
         */
        if(player.expBonus.isDelayed())
            amount *= 2.00;
        /**
         * 10% experience boost from first 3 days
         */
        if (player.first3.isDelayed())
            amount *= 1.10;
        /**
         * 25% experience boost inside the wilderness
         */
        if(player.wildernessLevel > 1)
            amount *= 1.25;

        if (player.getEquipment().contains(new Item(773)))
            amount *= 10.0;
        /**
         * 25% weekend experience boost
         */
        if(World.weekendExpBoost)
            amount *= 1.25;
        double newXp = stat.experience + amount;
        if(newXp > Stat.MAX_XP)
            newXp = Stat.MAX_XP;
        if(newXp == Stat.MAX_XP && stat.experience < Stat.MAX_XP) {
            Broadcast.GLOBAL.sendNews(player.getName() + " has just reached 200 million experience in " + type.name() + "!");
            player.sendMessage("Congratulations, you have reached max experience in " + type.name() + "!");
        }

        stat.experience = newXp;
        stat.updated = true;

        int oldLevel = stat.fixedLevel;
        int newLevel = stat.fixedLevel = Stat.levelForXp(stat.experience);
        int gain = newLevel - oldLevel;
        if(gain == 0) {
            /* level did not change */
            return;
        }
        if(stat.currentLevel < stat.fixedLevel) {
            if(type == StatType.Hitpoints || type == StatType.Prayer) {
                if(stat.currentLevel == oldLevel)
                    stat.currentLevel += gain;
            } else {
                stat.currentLevel += gain;
            }
        }
        player.graphics(199, 124, 0); //todo add the new gfx !!
        player.sendMessage("You've just advanced " + type.descriptiveName + " level. You have reached level " + newLevel + ".");
        if(newLevel == 99) {
            player.sendMessage(Color.ORANGE_RED.tag() + "Congratulations on achieving level 99 in " + type.name() + "!");
            player.sendMessage(Color.ORANGE_RED.tag() + "You may now purchase a skillcape from Mac who can be found at home.");
            Broadcast broadcast = type.defaultXpMultiplier == -1 ? Broadcast.FRIENDS : Broadcast.GLOBAL;
            broadcast.sendNews(player, player.getName() + " has just achieved level 99 in " + type.name() + "!");
        }
        if(statId <= 6)
            player.getCombat().updateLevel();
    }

    public void process() {
        boolean rapidRestore = player.getPrayer().isActive(Prayer.RAPID_RESTORE);
        boolean rapidHeal = player.getPrayer().isActive(Prayer.RAPID_HEAL) || HitpointsSkillCape.wearsHitpointsCape(player);
        boolean preserve = player.getPrayer().isActive(Prayer.PRESERVE);
        StatType[] types = StatType.values();
        totalLevel = 0;
        totalXp = 0;
        total99s = 0;
        for(int statId = 0; statId < types.length; statId++) {
            Stat stat = stats[statId];
            StatType type = types[statId];
            if(type != StatType.Prayer)
                stat.process(type == StatType.Hitpoints, rapidRestore, rapidHeal, preserve);
            if(stat.updated) {
                stat.updated = false;
                player.getPacketSender().sendStat(statId, stat.currentLevel, (int) stat.experience);
            }
            if(stat.fixedLevel == 99)
                total99s++;
            totalLevel += stat.fixedLevel;
            totalXp += stat.experience;
        }
        player.getCombat().checkLevel();
    }

    public StatCounter getCounter(int slot) {
        return counters[slot];
    }

    public Stat get(int statId) {
        return stats[statId];
    }

    public Stat get(StatType type) {
        return stats[type.ordinal()];
    }

    public Stat[] get() {
        return stats;
    }

    public boolean check(StatRequirement statRequirement) {
        return statRequirement.hasRequirement(player);
    }
}