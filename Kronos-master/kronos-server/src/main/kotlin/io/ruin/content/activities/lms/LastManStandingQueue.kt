package io.ruin.content.activities.lms

import io.ruin.api.globalEvent
import io.ruin.api.onLogoutAction
import io.ruin.api.onTeleportAction
import io.ruin.api.teleport
import io.ruin.model.entity.player.Player
import io.ruin.model.inter.Interface.LAST_MAN_STANDING_LOBBY

/**
 * Represents an active queue within the lobby.
 * @author Heaven
 */
class LastManStandingQueue(val mode: LastManStandingMode) {

    /**
     * The amount of players within this queue.
     */
    val players = mutableListOf<Player>()

    /**
     * The lifespan of this queue until we attempt to start the queue by force.
     */
    private var lifespan = 50

    /**
     * A flag indicating if this queue is active.
     */
    private var queueing = false

    init {
        queueing = true
        pulse()
    }

    /**
     * Adds the player to this queue.
     * @param player The player who is joining this queue.
     */
    fun add(player: Player) {
        val added = !players.contains(player) && players.add(player)
        if (added) {
            val queueSize = players.size
            val threshold = mode.threshold
            player.lmsQueue = this
            player.teleport(3411, 3184, 0)
            player.onTeleportAction { remove(player); true }
            player.onLogoutAction { remove(player); player.teleport(LastManStanding.randomLobbyTile()) }
            players.forEach {
                it.packetSender.sendString(LAST_MAN_STANDING_LOBBY, 11, "${mode.title} ($queueSize/$threshold)")
            }
        }
    }

    private fun pulse() = globalEvent {
        val queue = this@LastManStandingQueue
        while (queueing) {
            --lifespan
            if (players.size == 0) {
                remove()
                return@globalEvent
            }

            if (lifespan > 0) {
                if (players.size >= mode.threshold) {
                    val session = LastManStandingSession(queue)
                    session.start()
                    remove()
                    queueing = false
                }
            } else {
                if (players.size < 4) {
                    lifespan = 1
                } else {
                    val session = LastManStandingSession(queue)
                    session.start()
                    remove()
                    queueing = false
                }
            }
            pause(10)
        }
    }

    /**
     * Removes the player from this queue.
     * @param player The player who is leaving this queue.
     */
    fun remove(player: Player) {
        val removed = players.remove(player)
        if (removed) {
            val queueSize = players.size
            val threshold = mode.threshold
            player.teleportListener = null
            player.logoutListener = null
            player.lmsQueue = null
            player.packetSender.sendString(LAST_MAN_STANDING_LOBBY, 11, "None")
            players.forEach {
                it.packetSender.sendString(LAST_MAN_STANDING_LOBBY, 11, "${mode.title} ($queueSize/$threshold)")
            }

            if (players.isEmpty()) {
                remove()
            }
        }
    }

    /**
     * Removes the this element from the collection of active queues within the lobby.
     */
    private fun remove() {
        LastManStandingLobby.removeQueue(this)
    }

    /**
     * The current occupied size of this queue.
     */
    fun size() = players.size
}