package io.ruin.model.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;

public class KillingSpree {

    private static final String[] SHUTDOWN_MESSAGES = new String[]{
            "%1 has slain the mighty %2 and has ended his killing spree of %3!",
            "%1 proved a worthy foe for %2 and ended his killing spree of %3.",
            "%1 has ended %2's killing spree of %3.",
            "%1 has just slayed %2 and ruined his killing spree of %3."
    };

    public static String shutdownMessage(String killer, String killed, int killedStreak) {
        String message = Random.get(SHUTDOWN_MESSAGES);
        return message.replaceAll("%1", killer).replaceAll("%2", killed).replaceAll("%3", "" + killedStreak);
    }

    public static int overheadId(Player player) {
        int spree = player.currentKillSpree;
        if(spree >= 10)
            return player.getCombat().highRiskSkull ? 23 : 18;
        if(spree >= 8)
            return player.getCombat().highRiskSkull ? 22 : 17;
        if(spree >= 6)
            return player.getCombat().highRiskSkull ? 21 : 16;
        if(spree >= 4)
            return player.getCombat().highRiskSkull ? 20 : 15;
        if(spree >= 2)
            return player.getCombat().highRiskSkull ? 19 : 14;
        return player.getCombat().highRiskSkull ? 1 : 0;
    }

    public static int imgId(Player player) {
        int spree = player.currentKillSpree;
        if(spree >= 10)
            return player.getCombat().highRiskSkull ? 107 : 98;
        if(spree >= 8)
            return player.getCombat().highRiskSkull ? 106 : 97;
        if(spree >= 6)
            return player.getCombat().highRiskSkull ? 105 : 96;
        if(spree >= 4)
            return player.getCombat().highRiskSkull ? 104 : 95;
        if(spree >= 2)
            return player.getCombat().highRiskSkull ? 103 : 94;
        return player.getCombat().highRiskSkull ? 93 : 46;
    }

    public static String imgTag(int spree) {
        if(spree >= 10)
            return "<img=98>";
        if(spree >= 8)
            return "<img=97>";
        if(spree >= 6)
            return "<img=96";
        if(spree >= 4)
            return "<img=95>";
        return "<img=94>";
    }

}