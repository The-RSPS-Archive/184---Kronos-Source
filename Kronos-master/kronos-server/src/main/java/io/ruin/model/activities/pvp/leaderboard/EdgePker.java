package io.ruin.model.activities.pvp.leaderboard;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;

public class EdgePker implements Pker {
    private Player player;
    private int kills;
    private int deaths;

    public EdgePker(Player player) {
        this.player = player;
    }

    @Override
    public void addKill() {
        kills++;
    }

    @Override
    public void addDeath() {
        deaths++;
    }

    public Player getPlayer() {
        return player;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int compareTo(EdgePker v2) {
        double kdr = kills / Math.max(1D, deaths);
        double kdr2 = v2.kills / Math.max(1D, v2.deaths);
        if (getKills() > v2.getKills() || (getKills() == v2.getKills() && kdr > kdr2)) {
            return -1;
        } else if (getKills() < v2.getKills()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        PlayerGroup group = player.getClientGroup();
        return group.tag() + StringUtils.capitalizeFirst(getPlayer().getName()) + "     <img=49>Kills: " + getKills() + "       <img=46>Deaths: " + + getDeaths() + "       <img=93>KDR: " + NumberUtils.formatTwoPlaces((getDeaths() == 0 ? (double) getKills() : (getKills() / Math.max(1D, getDeaths()))));
    }
}
