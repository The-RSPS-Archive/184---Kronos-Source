package io.ruin.model.activities.donatorzone;

import io.ruin.model.entity.npc.actions.edgeville.CreditManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DonatorBossPortal {

    private static final int BOSS_ENTRANCE = 6282;

    public static boolean available(long lastUsed) {
        if (lastUsed <= 0) {
            return true;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTimeInMillis(lastUsed);
        int lastUsedDay = cal.get(Calendar.DAY_OF_MONTH);

        return System.currentTimeMillis() > lastUsed && lastUsedDay != currentDay;
    }

    public static boolean hasKillsLeft(Player player) {
        PlayerGroup donationGroup = CreditManager.getGroup(player);
        if (donationGroup != null) {
            switch (donationGroup) {
                case ZENYTE:
                    if (player.timesKilledDonatorBoss < 7) {
                        return true;
                    }
                    break;
                case ONYX:
                    if (player.timesKilledDonatorBoss < 5) {
                        return true;
                    }
                    break;
                case DRAGONSTONE:
                    if (player.timesKilledDonatorBoss < 3) {
                        return true;
                    }
                    break;
                case DIAMOND:
                    if (player.timesKilledDonatorBoss < 2) {
                        return true;
                    }
                    break;
                case RUBY:
                    if (player.timesKilledDonatorBoss < 1) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    static {
        ObjectAction.register(BOSS_ENTRANCE, 1, (player, obj) -> player.getMovement().teleport(2425, 9531, 0));
    }
}
