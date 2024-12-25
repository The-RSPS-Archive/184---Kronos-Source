package io.ruin.content.activities.tournament

import io.ruin.model.entity.player.Player
import io.ruin.model.map.Position

class TournamentFightPair {
    /**
     * The fight uid.
     */
    var uid = 0

    /**
     * The location within the tournament that the fight is taking place.
     */
    var fightArea: Position? = null

    /**
     * The first player of the fight pair.
     */
    var first: Player? = null

    /**
     * The second player of the fight pair.
     */
    var second: Player? = null

    /**
     * A flag determining if the fight can commence.
     */
    fun valid(): Boolean {
        return (first != null && second != null)
    }

    /**
     * A flag to check if the fight has commenced.
     */
    var commenced = false

    /**
     * Assigns the two fight participants as each others targets.
     */
    fun assign() {
        if (valid()) {
            val a = first()
            val b = second()
            a.tournamentFight = this
            b.tournamentFight = this
        }
    }

    /**
     * Supplies specified player's opponent reference.
     */
    fun opponent(player: Player): Player? {
        if (!valid()) {
            return null
        }

        return if (player == first) second else first
    }

    fun fightArea(tile: Position) {
        fightArea = tile
    }

    fun uid(index: Int) {
        this.uid = index
    }

    fun first(): Player {
        return first!!
    }

    fun second(): Player {
        return second!!
    }

    /**
     * A flag checking if the player is immune to damage under certain conditions such as fog etc.
     * This is to combat against the winner taking few ticks of damage while their opponent is in the dying sequence.
     * This is to prevent the winner dying / taking damage and potentially dying when ideally they shouldn't.
     */
    fun immune(player: Player): Boolean {
        return (opponent(player) != null && opponent(player)!!.dead())
    }
}