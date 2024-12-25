package io.ruin.model.stat;

import com.google.gson.annotations.Expose;
import io.ruin.Server;

public class Stat {

    public static final double MAX_XP = 200000000D;

    public static final int[] LEVEL_EXPERIENCES = new int[127];

    /**
     * Separator
     */

    public boolean updated;

    @Expose protected int boostedFor, depletedFor;

    @Expose public int currentLevel;

    public int fixedLevel;

    @Expose public double experience;

    public Stat(int level) {
        /* only used for npcs! */
        this.currentLevel = level;
        this.fixedLevel = level;
    }

    public Stat(int level, double experience) {
        this.currentLevel = level;
        this.fixedLevel = level;
        this.experience = experience;
    }

    public void reset() {
        experience = 0;
        currentLevel = fixedLevel = 1;
        boostedFor = depletedFor = 0;
        updated = true;
    }

    public void resetTo1() {
        this.currentLevel = 1;
        this.fixedLevel = 1;
        this.experience = 0;
        this.boostedFor = 0;
        this.depletedFor = 0;
        this.updated = true;
    }

    public int set(int lv) {
        // Clamp 1..99
        lv = Math.min(99, Math.max(1, lv));

        experience = xpForLevel(lv);
        fixedLevel = lv;
        currentLevel = lv;
        updated = true;
        return fixedLevel;
    }

    public void setExperience(int experience) {
        this.experience = experience;
        set(levelForXp(experience));
    }

    public int alter(int newLevel) {
        if(newLevel < 0)
            Server.logError("", new Throwable("Not a real error, just want to see where newLevel (" + newLevel + ") is set to less than 0!"));
        if(!updated && currentLevel != newLevel)
            updated = true;
        currentLevel = Math.max(0, newLevel);
        boostedFor = depletedFor = 0;
        return currentLevel;
    }

    public void restore() {
        alter(fixedLevel);
    }

    public void restore(int flatBoost) {
        int restoredLevel = Math.min(fixedLevel, currentLevel + flatBoost);
        alter(restoredLevel);
    }

    public void restore(int flatBoost, double percentBoost) {
        int restore = flatBoost + (int) (fixedLevel * percentBoost);
        int restoredLevel = Math.min(fixedLevel, currentLevel + restore);
        alter(restoredLevel);
    }

    public void boost(int flatBoost, double percentBoost) {
        int boost = flatBoost + (int) (fixedLevel * percentBoost);
        int boostedLevel = Math.min(currentLevel + boost, fixedLevel + boost);
        if(currentLevel <= boostedLevel)
            alter(boostedLevel);
    }

    public int drain(double percent) {
        return drain((int) (fixedLevel * percent));
    }

    public int drain(int amount) {
        int oldLevel = currentLevel;
        alter(Math.max(0, currentLevel - amount));
        return oldLevel - currentLevel;
    }

    public void process(boolean hitpoints, boolean rapidRestore, boolean rapidHeal, boolean preserve) {
        if(currentLevel > fixedLevel) {
            int boostTime = 100; //60 seconds
            if(preserve)
                boostTime *= 1.2;
            if(rapidRestore && !hitpoints)
                boostTime /= 2;
            if(++boostedFor >= boostTime) {
                currentLevel--;
                boostedFor = 0;
                updated = true;
            }
        } else if(currentLevel < fixedLevel) {
            int depleteTime = 50; //30 seconds
            if(hitpoints ? rapidHeal : rapidRestore)
                depleteTime /= 2;
            if(++depletedFor >= depleteTime) {
                currentLevel++;
                depletedFor = 0;
                updated = true;
            }
        }
    }

    /**
     * Misc
     */

    static {
        int i = 0;
        for(int i_0_ = 0; i_0_ < 127; i_0_++) {
            int i_1_ = i_0_ + 1;
            int i_2_ = (int) ((double) i_1_ + Math.pow(2.0, (double) i_1_ / 7.0) * 300.0);
            i += i_2_;
            LEVEL_EXPERIENCES[i_0_] = i / 4;
        }
    }

    public static int levelForXp(double xp) {
        int level = 1;
        for(int i = 0; i < 98; i++) {
            if(xp >= LEVEL_EXPERIENCES[i])
                level = i + 2;
        }
        return level;
    }

    public static int xpForLevel(int level) {
        return level <= 1 ? 0 : LEVEL_EXPERIENCES[level - 2];
    }

}