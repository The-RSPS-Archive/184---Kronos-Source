package io.ruin.content.areas.ver_sinhaza

import io.ruin.api.chat
import io.ruin.api.event
import io.ruin.api.whenNpcClick
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.player.Player

/**
 * The interaction script for Mercenary.
 * @author Heaven
 */
object Mercenary {

    init {
        for (id in intArrayOf(8213, 8214, 8215)) {
            whenNpcClick(id, 1) { player, npc ->
                player.talkTo(npc)
            }
        }
    }

    private fun Player.talkTo(npc: NPC) = event {
        chat("What are you doing here?")
        npc.chat("We're getting ready to test out our mettle in the Theatre. Most people go in alone but if we do it as a team, we should have no problem.")
        chat("Well good luck with that.")
    }
}