package io.ruin.model.content.upgrade.effects;

import io.ruin.model.content.upgrade.ItemUpgrade;

/**
 * @author ReverendDread on 6/18/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Looting extends ItemUpgrade {

    @Override
    public int addDoubleDropRolls() {
        return 1;
    }

}
