package io.ruin.model.content.upgrade.effects;

import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

/**
 * @author ReverendDread on 6/18/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class ExperienceBoost extends ItemUpgrade {

    @Override
    public double giveExperienceBoost(Player player, StatType statType) {
        return 1.01;
    }

}
