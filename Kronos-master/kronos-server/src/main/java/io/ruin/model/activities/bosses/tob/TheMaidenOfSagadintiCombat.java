package io.ruin.model.activities.bosses.tob;

import io.ruin.model.activities.raids.tob.dungeon.boss.TheMaidenOfSugadinti;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;

import java.util.List;

/**
 *
 * //8093
 * //8092 tornado
 * //8091 melee?
 *
 * @author ReverendDread on 7/28/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class TheMaidenOfSagadintiCombat extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(32);
    }

    @Override
    public boolean attack() {
        return false;
    }

    private TheMaidenOfSugadinti asMaiden() {
        return (TheMaidenOfSugadinti) npc;
    }

}
