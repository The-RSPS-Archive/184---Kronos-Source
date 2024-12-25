package io.ruin.model.activities;

import io.ruin.model.entity.player.Player;

import java.util.concurrent.TimeUnit;

public class ActivityTimer {

    private long time;

    public ActivityTimer() {
        this.time = System.currentTimeMillis();
    }

    public long stop(Player player, long previousBestTime) {
        time = System.currentTimeMillis() - time;
        if(previousBestTime == -1) {
            player.sendMessage("Duration: <col=ef1020>" + format(time));
            return time;
        } else if(previousBestTime == 0 || time < previousBestTime) {
            player.sendMessage("Duration: <col=ef1020>" + format(time) + "</col> (new personal best)");
            return time;
        } else {
            player.sendMessage("Duration: <col=ef1020>" + format(time) + "</col>. Personal best: " + format(previousBestTime));
            return previousBestTime;
        }
    }

    private static String format(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }

}
