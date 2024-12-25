package io.ruin.content.activities.tournament

import io.ruin.api.*
import io.ruin.cache.Color
import io.ruin.model.World
import io.ruin.model.entity.player.Player
import io.ruin.model.stat.StatType
import io.ruin.services.discord.impl.TournamentEmbedMessage
import io.ruin.utility.Broadcast
import java.util.*

/**
 * A manager for the [Tournament] activities. TODO old cringe code. Come back to this to reduce garbage.
 * @author Heaven
 */
object TournamentManager {

    /**
     * A flag indicating if this activity is enabled.
     */
    @JvmField var enabled = true

    /**
     * A flag indicating if the players are required to pay some sort of fee prior to entering the tournament.
     */
    @JvmField var requireFee = false

    /**
     * A reference to the active [Tournament] session, if available.
     */
    @JvmField var activeTournament: Tournament? = null

    /**
     * The total amount of time, in minutes, until a new [Tournament] session begins.
     */
    @JvmField var activityTimer = 3.times(60)

    /**
     * Initializer block for listeners.
     */
    init {
        /*
         * Custom home statue to handle tournament entrance.
         */
        whenObjClick(31626, 1) { player, _ ->
            if (activeTournament != null) {
                player.join()
            }
        }
        /*
         * Custom home ensure people can't go up the ladder.
         */

        /*whenObjClick(24082, 3109, 3504, 2, "climb-up") { player, _ ->
            player.event {
                player.messageBox("Sorry you can't go up there.")
            }
        }*/
        /*
         * Custom home ensure people can't just leave tournament with accidental click.
         */
        whenObjClick(7325, 1) { player, _ ->
            if (Lobby.players.contains(player)) {
                player.leave()
            }
        }
        whenNpcClick(7316, 1) { player, npc ->
            player.event {
                if (activeTournament != null) {
                    if (activeTournament!!.inProgress) {
                        npc.chat("There is currently a tournament underway. ${activeTournament!!.contestants} contestants remain.")
                    } else {
                        npc.chat("The next tournament will begin in $activityTimer mins. To enter, simply go in the tournament entrance near me. You must bank all of your items beforehand.")
                    }
                } else {
                    npc.chat("Sorry, there is no scheduled tournament at this time. Try checking again at a later time.")
                }
            }
        }

        if (generateTournament()) {
            pulse()
        }
    }

    /**
     * Attempts to start a new [Tournament] session and sets up the environment.
     */
    fun generateTournament(): Boolean {
        if (activeTournament != null && activeTournament!!.inProgress) {
            return false
        }

        val attributes = if (activeTournament == null) {
            TournamentPlaylist.VALUES.random().attributes
        } else {
            TournamentPlaylist.VALUES.filter { it != activeTournament?.attributes }.random().attributes
        }

        val created = Tournament(attributes)
        activeTournament = created
        return true
    }

    /**
     * The main method of our manager that is processed every cycle of our dedicated event processor.
     */
    fun pulse() = globalEvent {
        while(activityTimer != -1) {
            pause(100)
            when(activityTimer--) {
                30 -> {
                    TournamentEmbedMessage.sendDiscordMessage(activeTournament?.name, "30")
                    Broadcast.WORLD.sendNews("In 30 minutes a ${activeTournament?.name} Tournament is starting! Type ::tournament to sign up now!")
                }
                15 -> {
                    TournamentEmbedMessage.sendDiscordMessage(activeTournament?.name, "15")
                    Broadcast.WORLD.sendNews("In 15 minutes a ${activeTournament?.name} Tournament is starting! Type ::tournament to sign up now!")
                }
                10 -> {
                    TournamentEmbedMessage.sendDiscordMessage(activeTournament?.name, "10")
                    Broadcast.WORLD.sendNews("In 10 minutes a ${activeTournament?.name} Tournament is starting! Type ::tournament to sign up now!")
                }
                5 -> {
                    TournamentEmbedMessage.sendDiscordMessage(activeTournament?.name, "5")
                    Broadcast.WORLD.sendNews("In five minutes a ${activeTournament?.name} Tournament is starting! Type ::tournament to sign up now!")
                    broadcast("In five minutes a ${activeTournament?.name} Tournament is starting! Type ::tournament to sign up now!")
                }
                1 -> {
                    if (Lobby.players.size >= 4) {
                        TournamentEmbedMessage.sendDiscordMessage(activeTournament?.name, "1")
                        Broadcast.WORLD.sendNews("In one minute a ${activeTournament?.name} Tournament is starting! Type ::tournament to sign up now!")
                        broadcast("In one minute a ${activeTournament?.name} Tournament is starting! Type ::tournament to sign up now!")
                    }
                }
                0 -> {
                    activityTimer = if (!begin(activeTournament)) {
                        5
                    } else {
                        -1
                    }
                }
            }
        }
    }
    private fun broadcast(message: String) {
        for (p in World.players) {
            //If the player has toggle off tournament broadcasts don't send.
            if (!p.broadcastTournaments) {
                return
            }
            p.packetSender.sendMessage(message, "", 14)
        }
    }

    private fun begin(tournament: Tournament?): Boolean {
        if (tournament == null) {
            generateTournament()
            return false
        }

        if (World.updating) {
            Broadcast.WORLD.sendNews("The tournament has been put on hold due to a system update.")
            return false
        }

        if (Lobby.players.size < 4) {
            return false
        }

        tournament.start(Lobby.players)
        Lobby.players.clear()
        return true
    }

    /**
     * Attempts to add a new player to the activity.
     */
    fun Player.join() {
        event {
            if (!enabled) {
                messageBox("The Tournament is not taking any participants at this time. Please<br>check back at a later time.")
                return@event
            }

            if (activeTournament?.contestants == 100) {
                messageBox("The lobby for this tournament is currently full. Try joining again at a later time.")
                return@event
            }

            Lobby.players?.forEach { player ->
                println(player.ipInt)
                if (ipInt == player.ipInt) {
                    messageBox("You already have an account in the tournament.")
                    return@event
                }
            }

            if (pet != null) {
                messageBox("Pets are not permitted within the arena.")
                return@event
            }

            if (bountyHunter.target != null) {
                messageBox("You must lose your bounty target before joining the tournament.")
                return@event
            }

            if (!passItemCheck()) {
                messageBox("You are not permitted to bring outside items into the Tournament. All<br>gear will be provided for you upon starting.")
                return@event
            }

            if (requireFee && inventory.remove(621, 1) == 0) {
                messageBox("You must have a Tournament Voucher to enter this tournament. Receive one by voting at ::vote!")
                return@event
            }

            val safelyAdd = this@join !in Lobby
            if (safelyAdd) {
                lock()
                pause(1)
                Lobby.players += this@join
                onLogoutAction(TournamentManager::onLogout)
                onTeleportAction(TournamentManager::onTeleport)
                preTournyAttack = intArrayOf(player.stats.get(StatType.Attack).fixedLevel, player.stats.get(StatType.Attack).experience.toInt())
                preTournyStrength = intArrayOf(player.stats.get(StatType.Strength).fixedLevel, player.stats.get(StatType.Strength).experience.toInt())
                preTournyDefence = intArrayOf(player.stats.get(StatType.Defence).fixedLevel, player.stats.get(StatType.Defence).experience.toInt())
                preTournyRanged = intArrayOf(player.stats.get(StatType.Ranged).fixedLevel, player.stats.get(StatType.Ranged).experience.toInt())
                preTournyPrayer = intArrayOf(player.stats.get(StatType.Prayer).fixedLevel, player.stats.get(StatType.Prayer).experience.toInt())
                preTournyMagic = intArrayOf(player.stats.get(StatType.Magic).fixedLevel, player.stats.get(StatType.Magic).experience.toInt())
                preTournyHitpoints = intArrayOf(player.stats.get(StatType.Hitpoints).fixedLevel, player.stats.get(StatType.Hitpoints).experience.toInt())
                teleport(3109, 3514, 2)
                message("You have successfully joined the lobby. There is a total of ${Lobby.players.size} contestants inside the lobby.")
                unlock()
            }
        }
    }

    /**
     * Attempts to remove the existing player from the activity.
     */
    fun Player.leave() {
        lock()
        remove()
        teleport(3086, 3491, 0)
        unlock()
    }

    private fun onTeleport(player: Player): Boolean {
        player.remove()
        return true
    }

    private fun onLogout(player: Player) {
        player.remove()
        player.teleport(3078, 3496, 0)
    }

    private fun Player.remove() {
        if (this in Lobby)  {
            Lobby.players -= this
            clearListeners()
            if (requireFee) {
                bank.add(621, 1)
                message("You have successfully left the lobby.")
                message(Color.RED.wrap("Your tournament fee has been refunded to your bank."))
            }
        }
    }

    private fun Player.passItemCheck(): Boolean {
        if (equipment.isNotEmpty) {
            return false
        }

        return if (requireFee) {
            (inventory.items.filter(Objects::nonNull).none { it.id != 621 })
        } else {
            inventory.isEmpty
        }
    }

    object Lobby {

        /**
         * The collection of players waiting for the next [Tournament] session.
         */
        val players = mutableListOf<Player>()

        /**
         * A flag indicating if the player exists within the lobby.
         * @param player
         * @return
         */
        @JvmStatic
        operator fun contains(player: Player) = players.contains(player)
    }
}