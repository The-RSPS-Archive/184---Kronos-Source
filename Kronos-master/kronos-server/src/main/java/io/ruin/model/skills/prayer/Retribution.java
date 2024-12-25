package io.ruin.model.skills.prayer;

import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

public class Retribution {

    public static void check(Player player, Killer killer) {
        if(killer == null)
            return;
        if (player.getPrayer().isActive(Prayer.RETRIBUTION)) {
            player.graphics(437, 0, 0);
            int maxDamage = (int) (player.getStats().get(StatType.Prayer).fixedLevel * 0.25);
            if(player.inMulti()) {
                for (Player p2 : player.localPlayers()) {
                    if (p2 == player || Misc.getDistance(p2.getPosition(), player.getPosition()) > 1)
                        continue;
                    if(p2.wildernessLevel == 0 && !p2.pvpAttackZone)
                        continue;
                    p2.hit(new Hit().randDamage(maxDamage));
                }
            } else {
                if(killer.player != null)
                    killer.player.hit(new Hit().randDamage(maxDamage));
            }
        }
    }

}
