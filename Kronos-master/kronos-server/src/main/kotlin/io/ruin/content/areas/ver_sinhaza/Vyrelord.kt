package io.ruin.content.areas.ver_sinhaza

import io.ruin.api.chat
import io.ruin.api.event
import io.ruin.api.random
import io.ruin.api.whenNpcClick
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.player.Player

/**
 * The interaction script for vyrelords & vyreladies.
 * @author Heaven
 */
object Vyrelord {

    init {
        for (id in intArrayOf(8332, 8333, 8334, 8335)) {
            whenNpcClick(id, 1) { player, npc ->
                player.talkTo(npc)
            }
        }
        whenNpcClick(8251, 1) { player, npc ->
            player.event {
                npc.chat("Clear off food sack.")
            }
        }
    }

    private fun Player.talkTo(npc: NPC) = event {
        when(random(4)) {
            1 -> npc.chat("Will you be entering the Theatre? It will be fun to watch you die.")
            2 -> npc.chat("Get lost!")
            3 -> npc.chat("Leave me alone food sack!")
            4 -> npc.chat("I do enjoy a day out at the Theatre.")
        }
    }
}