package io.ruin.content.activities.lms

import io.ruin.api.event
import io.ruin.api.messageBox
import io.ruin.model.entity.player.Player

/**
 * @author Heaven
 */
object LastManStandingLobby {

    /**
     * The collection of [LastManStandingQueue]s in this lobby.
     */
    private val queues = mutableListOf<LastManStandingQueue>()

    /**
     * Attempts to add a [Player] to the specified LMS queue.
     */
    fun Player.add(mode: LastManStandingMode) = event {
        if (player.inventory.isNotEmpty || player.equipment.isNotEmpty) {
            messageBox("You can't bring your own items into the arena.")
            return@event
        }

        val queue = findQueueBy(mode)
        if (queue == null) {
            val created = LastManStandingQueue(mode)
            created.add(player)
            queues += created
        } else {
            queue.add(this@add)
        }
    }

    /**
     * Remove the queue from the stack.
     */
    fun removeQueue(queue: LastManStandingQueue) {
        queues -= queue
    }

    /**
     * Returns the queue size for the existing queue given the provided mode or returns 0 if null.
     */
    fun queueSizeOf(mode: LastManStandingMode) = findQueueBy(mode)?.size() ?: 0

    /**
     * Attempts to find an existing queue based on the arguments provided.
     */
    private fun findQueueBy(mode: LastManStandingMode): LastManStandingQueue? {
        return queues.find { it.mode == mode }
    }
}