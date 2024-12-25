package io.ruin.content

import io.ruin.api.globalEvent
import io.ruin.cache.Icon
import io.ruin.utility.Broadcast

/**
 * @author Leviticus
 */
object GlobalMessages {

    init {
        globalEvent {
            while (true) {
                pause(1400) // Every 15 minutes.
                announceVote()
            }
        }
    }

    private fun announceVote() {
        Broadcast.WORLD.sendNews(Icon.ANNOUNCEMENT, "You can now ::Vote for 1M GP 1hour Double XP and a Vote Mystery Box!")
    }
}