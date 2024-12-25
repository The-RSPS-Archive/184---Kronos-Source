package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;

public class PlayerPrayer {

    private Player player;

    private boolean[] active = new boolean[Prayer.values().length];

    @Expose private boolean[] quickPrayers = new boolean[active.length];

    private int drainTotal, drainSkip;

    @Expose private int drainCounter;

    public double attackBoost, strengthBoost, defenceBoost;

    public double rangedAttackBoost, rangedStrengthBoost;

    public double magicBoost;

    public void init(Player player) {
        this.player = player;
        sendQuickPrayers();
    }

    /**
     * Regular prayers
     */

    private boolean delayRequired() {
        return player.isLocked() || player.isStunned();
    }

    private boolean checkPoints() {
        if(player.getStats().get(StatType.Prayer).currentLevel == 0) {
            player.sendMessage("You need to recharge your Prayer at an altar.");
            return false;
        }
        return true;
    }

    private boolean checkReq(Prayer prayer) {
        if(player.getStats().get(StatType.Prayer).fixedLevel < prayer.level) {
            player.dialogue(new MessageDialogue("You need a <col=000080>Prayer</col> level of " + prayer.level + " to use <col=000080>" + prayer.name + "</col>."));
            return false;
        }
        return prayer.activationCheck == null || prayer.activationCheck.test(player);
    }

    private boolean allowPrayers() {
        return player.allowPrayerListener == null || player.allowPrayerListener.allow(player);
    }

    private boolean allowPrayer(Prayer prayer) {
        return player.activatePrayerListener == null || player.activatePrayerListener.allow(player, prayer);
    }

    public void toggle(Prayer prayer) {
        if(delayRequired()) {
            //So on rs it delays but Idk a good way to do that... Idk if people will even notice...
            prayer.config.update(player);
            return;
        }
        if(isActive(prayer)) {
            deactivate(prayer);
            player.privateSound(2663);
            return;
        }
        if(!checkPoints()) {
            /* client sends sound & doesn't light up prayer */
            return;
        }
        if(!allowPrayers()) {
            prayer.config.set(player, 0);
            return;
        }
        activate(prayer);
    }

    private void activate(Prayer prayer) {
        if(!allowPrayer(prayer) || !checkReq(prayer)) {
            prayer.config.set(player, 0);
            return;
        }
        if(prayer.disallowed != null) {
            for(Prayer p : prayer.disallowed) {
                if(isActive(p))
                    deactivate(p);
            }
        }
        active[prayer.ordinal()] = true;
        drainTotal += prayer.drain;
        attackBoost += prayer.attackBoost;
        strengthBoost += prayer.strengthBoost;
        defenceBoost += prayer.defenceBoost;
        rangedAttackBoost += prayer.rangedAttackBoost;
        rangedStrengthBoost += prayer.rangedStrengthBoost;
        magicBoost += prayer.magicBoost;
        if(prayer.headIcon != -1)
            player.getAppearance().setPrayerIcon(prayer.headIcon);
        prayer.config.set(player, 1);
        player.privateSound(prayer.soundId);
    }

    public void deactivate(Prayer prayer) {
        active[prayer.ordinal()] = false;
        drainTotal -= prayer.drain;
        attackBoost -= prayer.attackBoost;
        strengthBoost -= prayer.strengthBoost;
        defenceBoost -= prayer.defenceBoost;
        rangedAttackBoost -= prayer.rangedAttackBoost;
        rangedStrengthBoost -= prayer.rangedStrengthBoost;
        magicBoost -= prayer.magicBoost;
        if(prayer.headIcon != -1)
            player.getAppearance().setPrayerIcon(-1);
        if(drainTotal == 0) {
            drainSkip = 0;
            Config.QUICK_PRAYING.set(player, 0);
        }
        prayer.config.set(player, 0);
    }

    public void deactivateAll() {
        for(Prayer prayer : Prayer.values()) {
            if(isActive(prayer))
                deactivate(prayer);
        }
    }

    public void deactivateProtectionPrayer() {
            deactivate(Prayer.PROTECT_FROM_MAGIC);
            deactivate(Prayer.PROTECT_FROM_MISSILES);
            deactivate(Prayer.PROTECT_FROM_MELEE);
    }

    public boolean isActive(Prayer prayer) {
        return active[prayer.ordinal()];
    }

    /**
     * Quick prayers
     */

    public void toggleQuickPrayers() {
        if(delayRequired()) {
            Config.QUICK_PRAYING.update(player);
            return;
        }
        if(Config.QUICK_PRAYING.get(player) == 1) {
            deactivateAll();
            player.privateSound(2663);
            return;
        }
        if(!checkPoints() || !allowPrayers()) {
            Config.QUICK_PRAYING.set(player, 0); //orb needs to be refreshed
            return;
        }
        for(int i = 0; i < quickPrayers.length; i++) {
            if(quickPrayers[i]) {
                Prayer prayer = Prayer.values()[i];
                if(!isActive(prayer))
                    activate(prayer);
            }
        }
        Config.QUICK_PRAYING.set(player, 1);
    }

    public void toggleQuickPrayer(Prayer prayer) {
        if(quickPrayers[prayer.ordinal()]) {
            quickPrayers[prayer.ordinal()] = false;
            return;
        }
        if(!checkReq(prayer)) {
            sendQuickPrayers();
            return;
        }
        if(prayer.disallowed != null) {
            for(Prayer p : prayer.disallowed) {
                if(quickPrayers[p.ordinal()])
                    quickPrayers[p.ordinal()] = false;
            }
        }
        quickPrayers[prayer.ordinal()] = true;
        sendQuickPrayers();
    }

    private void sendQuickPrayers() {
        int value = 0;
        for(int i = 0; i < Prayer.QUICK_PRAYER_ORDER.length; i++) {
            Prayer prayer = Prayer.QUICK_PRAYER_ORDER[i];
            if(quickPrayers[prayer.ordinal()])
                value |= (1 << i);
        }
        Config.QUICK_PRAYERS_ACTIVE.set(player, value);
    }

    /**
     * Draining
     */

    public int drain(int points) {
        if(player.getCombat().isDead())
            return 0;
        Stat prayer = player.getStats().get(StatType.Prayer);
        int currentLevel = prayer.currentLevel;
        if(currentLevel == 0)
            return 0;
        if(currentLevel > points) {
            prayer.alter(prayer.currentLevel - points);
            return points;
        }
        prayer.alter(0);
        if(drainTotal > 0) {
            player.sendMessage("You have run out of prayer points, you must recharge at an altar.");
            deactivateAll();
        }
        drainCounter = 0;
        return currentLevel;
    }

    public void process() {
        //If a player is in a LMS session, don't drain at all.
        if (player.lmsSession != null) {
            return;
        }
        if(drainTotal == 0)
            return;
        if(drainSkip == 0) {
            drainSkip = drainTotal;
            return;
        }
        if(drainSkip != -1) {
            drainCounter += drainSkip;
            drainSkip = -1;
        }
        drainCounter += drainTotal;
        int resistance = 60 + (player.getEquipment().bonuses[EquipmentStats.PRAYER] * 2);
        if(drainCounter > resistance) {
            drain(drainCounter / resistance);
            drainCounter %= resistance;
        }
        if(drainCounter < 0) //this should never be possible. must be a bug somewhere else.
            drainCounter = 0;
    }

    /**
     * Slashed
     */

    public TickDelay slashDelay = new TickDelay();

    public void slashPrayers() {
        slashDelay.delay(9);
        if(isActive(Prayer.PROTECT_FROM_MAGIC))
            deactivate(Prayer.PROTECT_FROM_MAGIC);
        else if(isActive(Prayer.PROTECT_FROM_MISSILES))
            deactivate(Prayer.PROTECT_FROM_MISSILES);
        else if(isActive(Prayer.PROTECT_FROM_MELEE))
            deactivate(Prayer.PROTECT_FROM_MELEE);
    }

}