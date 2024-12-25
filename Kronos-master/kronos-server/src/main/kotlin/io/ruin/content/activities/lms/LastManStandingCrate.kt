package io.ruin.content.activities.lms

import io.ruin.api.globalEvent
import io.ruin.api.message
import io.ruin.model.entity.player.Player
import io.ruin.model.map.Position
import io.ruin.model.map.`object`.GameObject

/**
 * Represents a loot crate within the LMS activity.
 * @author Heaven
 */
class LastManStandingCrate(val session: LastManStandingSession, val location: LMSCrateLocations) {

    /**
     * The backing game object for this crate.
     */
    private val OBJ = GameObject(29081, Position(session.map.convertX(location.tile.x), session.map.convertY(location.tile.y), 0), 10, 0)

    /**
     * The lifespan of this crate,
     */
    private var lifespan = 150

    /**
     * Spawns the crate and starts event processing.
     */
    fun spawn() {
        OBJ.spawn()
        globalEvent {
            while (lifespan-- > 0) {
                pause(1)
            }
            destroy()
        }

        session.participants.forEach {
            it.sendHintArray(tile())
            it.packetSender.sendMessage("A magical loot crate has appeared ${location.hint}", "", 14)
        }
    }

    /**
     * Loots the crate and flag it to be destroyed gracefully.
     */
    fun loot(player: Player) {
        player.lock()
        player.animate(832)
        OBJ.graphics(86, 40, 0)
        player.inventory.addOrDrop(LastManStanding.WEAPON_UPGRADES.random(), 1)
        player.message("You find some loot!")
        destroy()
        player.unlock()
    }

    /**
     * Cleans up any resources associated with this crate reference.
     */
    fun destroy() {
        lifespan = 0
        if (!OBJ.isRemoved) {
            OBJ.remove()
        }
        session.participants.forEach { it.clearHintArrow() }
    }

    /**
     * The tiled location of this loot crate.
     */
    fun tile() = Position(OBJ.x, OBJ.y, 0)
}