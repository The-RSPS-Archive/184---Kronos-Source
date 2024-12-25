package io.ruin.event

import io.ruin.model.entity.Entity
import io.ruin.model.entity.player.Player

/**
 * The dedicated processor for our collection of [GameEvent]s.
 * @author Heaven
 */
object GameEventProcessor {

    private val activeEvents = mutableListOf<GameEvent>()

    @JvmStatic fun pulse() {
        val copy = activeEvents.toList()
        copy.forEach {
            if (it.waiting) {
                if (it.ticks > 0 && --it.ticks <= 0) {
                    it.resume()
                }
            }

            if (it.finished) {
                activeEvents -= it
            }
        }
    }

    /**
     * Finds any/all active events related to the [Entity] specified and kills off the job(s) concerning.
     */
    @JvmStatic fun killFor(entity: Entity) {
        val relevant  = activeEvents.filter { it.ctx == entity }
        relevant.forEach(GameEvent::kill)
        activeEvents.removeAll(relevant)
    }

    @JvmStatic fun resumeWith(ctx: Player, value: Any?): Boolean {
        val event = activeEvents.find { it.ctx == ctx } ?: return false
        return event.resume(value)
    }

    operator fun GameEventProcessor.plusAssign(event: GameEvent) {
        activeEvents += event
    }
}