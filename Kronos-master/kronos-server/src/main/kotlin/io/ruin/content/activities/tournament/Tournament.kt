package io.ruin.content.activities.tournament

import io.ruin.api.*
import io.ruin.cache.Color
import io.ruin.cache.ItemID.BLOOD_MONEY
import io.ruin.cache.ItemID.COINS_995
import io.ruin.model.World
import io.ruin.model.combat.Hit
import io.ruin.model.combat.HitType
import io.ruin.model.combat.Killer
import io.ruin.model.entity.Entity
import io.ruin.model.entity.player.Player
import io.ruin.model.entity.player.PlayerAction
import io.ruin.model.inter.utils.Config
import io.ruin.model.item.Item
import io.ruin.model.skills.magic.rune.Rune
import io.ruin.model.skills.prayer.Prayer
import io.ruin.model.stat.StatType
import io.ruin.utility.Broadcast

/**
 * Represents a single tournament session.
 * @author Heaven
 */
class Tournament(var attributes: TournamentAttributes) {

    val participants = mutableListOf<Player>()
    private val fights = mutableListOf<TournamentFightPair>()
    var round = 0
    var name = attributes.playlist().typeName
    var inProgress = false
    var finalRound = false
    private var fogActive = false

    /**
     * A countdown used to time, based on the tournament cycle rate,
     * until the next group of fights can start.
     */
    private var roundCountdown = 0

    /**
     * The amount of time, in seconds, until the fog appears in the tournament.
     */
    private var fogCountdown = -1

    /**
     * Supplies the amount of participants left in this session.
     */
    val contestants: Int get() = participants.size

    /**
     * The current stage for this session.
     */
    private var stage = TournamentStage.NONE

    /**
     * Starts the session for this tournament.
     * @param players
     */
    fun start(players: MutableList<Player>) {
        inProgress = true
        participants.addAll(players)
        participants.forEach { player ->
            player.lock()
            player.tournament = this
            player.onTeleportAction {
                it.message("The use of these spells is not permitted within the battlegrounds.")
                false
            }
            player.onPrayerActivate(::canActivatePrayer)
            player.onLogoutAction(::onLogout)
            player.onPlayerAttack(::canAttack)
            player.onDeathFinish(::onDeath)
            if (Config.RIGOUR_UNLOCK.get(player) == 0) {
                player.tournamentRigour = true
                Config.RIGOUR_UNLOCK.set(player, 1)
            }
            if (Config.AUGURY_UNLOCK.get(player) == 0) {
                player.tournamentAugury = true
                Config.AUGURY_UNLOCK.set(player, 1)
            }
            if (Config.PRESERVE_UNLOCK.get(player) == 0) {
                player.tournamentPreserve = true
                Config.PRESERVE_UNLOCK.set(player, 1)
            }
            player.unlock()
        }

        generateFights()
        pulse()
    }

    fun end(forceEnd: Boolean = false) {
        if (participants.isNotEmpty()) {
            participants.forEach {
                it.closePrimaryOverlay()
                it.teleport(3113 + random(3), 3513 + random(3), 0)
                it.normalize()
            }
        }

        stage = TournamentStage.NONE
        participants.clear()
        fights.clear()
        inProgress = false
        if (TournamentManager.generateTournament()) {
            TournamentManager.activityTimer = 3.times(60)
            TournamentManager.pulse()
        } else {
            TournamentManager.activeTournament = null
            TournamentManager.enabled = false
        }
    }

    /**
     * An event used to handle the progression and update the activity when expected.
     */
    private fun pulse() = globalEvent {
        while(inProgress) {
            if (roundCountdown > 0) {
                roundCountdown--
                if (stage == TournamentStage.COMMENCING) {
                    handleRoundCountdown()
                }
            } else {
                if (stage == TournamentStage.ENDED) {
                    end()
                } else if (stage == TournamentStage.ROUND_ENDED) {
                    generateFights()
                } else if (stage != TournamentStage.ENDED && contestants == 1) {
                    val finalPlayer = participants[0]
                    if (World.getPlayer(finalPlayer.index) != null) {
                        finalPlayer.awardWinner()
                    }
                } else if (stage == TournamentStage.FIGHTING) {
                    if (fights.isEmpty()) {
                        stage = TournamentStage.ROUND_ENDED
                        roundCountdown = 6
                        fogCountdown = -1
                        fogActive = false
                        announce("All fights in round #$round have finished. The next round will begin shortly.")
                    } else {
                        if (!fogActive) {
                            if (fogCountdown > 0) {
                                fogCountdown -= 2
                                updateOverlay()
                            } else {
                                fogActive = true
                                updateOverlay()
                            }
                        } else {
                            sendFogDamage()
                        }
                    }
                }
            }
            pause(4)
        }
    }

    /**
     * Generates fight pairs for the remaining participants within the collection. If a fight pair is incomplete, such as one of the
     * references within the pair being null, then that player is given a free round and automatically advances to the next.
     */
    private fun generateFights() {
        for (index in 0 until participants.size step 2) {
            val fightPair = TournamentFightPair()
            fightPair.first = participants[index]
            if (index + 1 < contestants) {
                fightPair.second = participants[index + 1]
            }
            fights.add(fightPair)
            fightPair.assign()
        }

        roundCountdown = 20
        startRound(contestants == 2)
    }

    private fun startRound(lastRound: Boolean) {
        round++
        finalRound = lastRound
        val copy = fights.toList()
        for (index in 0 until copy.size) {
            val pair = fights[index]
            val arenaTile = if (finalRound) TournamentMap.FINALE_ARENA_CENTRE_TILE else TournamentMap.BATTLE_ARENA_CENTRE_TILES[index]
            if (pair.first != null) {
                val plr = pair.first!!
                plr.refresh()
                plr.teleport(arenaTile.relative(random(2), random(2)))
                if (pair.second == null) {
                    plr.message(Color.PURPLE.wrap("Due to lack of participants you have been given a free win this round."))
                    plr.message(Color.PURPLE.wrap("Please wait while the current round finishes."))
                    fights.remove(pair)
                }
            }
            if (pair.second != null) {
                val plr = pair.second!!
                plr.refresh()
                plr.teleport(arenaTile.relative(random(2), random(2)))
            }
            if (pair.valid()) {
                val first = pair.first()
                val second = pair.second()
                first.sendHintArrow(second)
                second.sendHintArrow(first)
                pair.fightArea(arenaTile)
            }
        }

        if (finalRound) {
            val fight = fights[0]
            if (fight.valid()) {
                val first = fight.first()
                val second = fight.second()
                Broadcast.WORLD.sendNews("The final round of the tournament, ${first.name} Vs ${second.name}, is about to begin!")
            }
        } else {
            announce("Round $round will start in 15 seconds.")
        }
        stage = TournamentStage.COMMENCING
    }

    private fun handleRoundCountdown() {
        if (roundCountdown < 11) {
            fights.forEach { fight ->
                when {
                    roundCountdown > 0 -> {
                        fight.first().shoutCountdown(roundCountdown)
                        fight.second().shoutCountdown(roundCountdown)
                    }
                    else -> {
                        fight.first().shoutCountdown(0)
                        fight.second().shoutCountdown(0)
                        fights.filter(TournamentFightPair::valid).forEach { it.commenced = true }
                        stage = TournamentStage.FIGHTING
                        fogCountdown = 360
                        roundCountdown = 0
                        updateOverlay()
                    }
                }
            }
        }
    }

    /**
     * Sends the fog status effect to all participants applicable.
     */
    private fun sendFogDamage() {
        val damage = IntRange(3, 5).random()
        fights.filter(TournamentFightPair::valid).forEach { fight ->
            if (fight.first != null && fight.first().alive() && !fight.immune(fight.first())) {
                fight.second().hit(Hit(HitType.DISEASE1).fixedDamage(damage))
                fight.first().message("The noxious fog causes you to take some damage.")
            }
            if (fight.second != null && fight.second().alive() && !fight.immune(fight.second())) {
                fight.second().hit(Hit(HitType.DISEASE1).fixedDamage(damage))
                fight.second().message("The noxious fog causes you to take some damage.")
            }
        }
    }

    private fun Player.awardWinner() {
        stage = TournamentStage.ENDED
        tournamentWins++
        Broadcast.WORLD.sendNews("$name has won the tournament!")
        World.sendGraphics(1389, 0, 0, 3644, 9074, 0)
        World.sendGraphics(1389, 0, 0, 3644, 9081, 0)
        World.sendGraphics(1389, 0, 0, 3651, 9081, 0)
        World.sendGraphics(1389, 0, 0, 3651, 9074, 0)
        roundCountdown = 3
    }

    /**
     * Eliminates the player from this session.
     */
    private fun Player.eliminate(loggedOut: Boolean) {
        val fight = tournamentFight
        val validFight = fight != null
        var opp: Player? = null
        normalize()
        participants -= this
        if (validFight) {
            opp = fight.opponent(player)
            fights.remove(fight)
        }

        if (opp != null) {
            opp.resetCombat()
            if (finalRound || contestants == 1) {
                opp.awardWinner()
            } else {
                advanceRoundFor(this, fight, loggedOut)
                updateOverlay()
            }
        }
    }

    private fun Player.normalize() {
        tournamentFight = null
        tournament = null
        logoutListener = null
        teleportListener = null
        attackPlayerListener = null
        deathEndListener = null
        activatePrayerListener = null
        combat.resetKillers()
        clearHintArrow()
        inventory.clear()
        equipment.clear()
        setAction(1, null)
        if (player.preTournyAttack != null)
            player.stats.set(StatType.Attack, player.preTournyAttack[0], player.preTournyAttack[1])
        if (player.preTournyStrength != null)
            player.stats.set(StatType.Strength, player.preTournyStrength[0], player.preTournyStrength[1])
        if (player.preTournyDefence != null)
            player.stats.set(StatType.Defence, player.preTournyDefence[0], player.preTournyDefence[1])
        if (player.preTournyRanged != null)
            player.stats.set(StatType.Ranged, player.preTournyRanged[0], player.preTournyRanged[1])
        if (player.preTournyPrayer != null)
            player.stats.set(StatType.Prayer, player.preTournyPrayer[0], player.preTournyPrayer[1])
        if (player.preTournyMagic != null)
            player.stats.set(StatType.Magic, player.preTournyMagic[0], player.preTournyMagic[1])
        if (player.preTournyHitpoints != null)
            player.stats.set(StatType.Hitpoints, player.preTournyHitpoints[0], player.preTournyHitpoints[1])

        combat.updateLevel()
        if (player.tournamentRigour) {
            Config.RIGOUR_UNLOCK.reset(player)
            Config.RIGOUR_UNLOCK.update(player)
            player.tournamentRigour = false
        }
        if (player.tournamentAugury) {
            Config.AUGURY_UNLOCK.reset(player)
            Config.AUGURY_UNLOCK.update(player)
            player.tournamentAugury = false
        }
        if (player.tournamentPreserve) {
            Config.PRESERVE_UNLOCK.reset(player)
            Config.PRESERVE_UNLOCK.update(player)
            player.tournamentPreserve = false
        }
        if (player.tournamentPouch) {
            player.tournamentRunePouch.set(0, null)
            player.tournamentRunePouch.set(1, null)
            player.tournamentRunePouch.set(2, null)
            player.tournamentPouch = false
        }
        if (player.cachedRunePouchTypes != 0) {
            player.tournamentRunePouch.empty(false)
            Config.RUNE_POUCH_TYPES.set(player, player.cachedRunePouchTypes)
            Config.RUNE_POUCH_AMOUNTS.set(player, player.cachedRunePouchAmounts)

            val firstRuneType = Config.RUNE_POUCH_LEFT_TYPE.get(player)
            if (firstRuneType != 0) {
                val rune = Rune.VALUES[firstRuneType - 1]
                player.tournamentRunePouch.set(0, Item(rune.id, Config.RUNE_POUCH_LEFT_AMOUNT.get(player)))
            }

            val secondRuneType = Config.RUNE_POUCH_MIDDLE_TYPE.get(player)
            if (secondRuneType != 0) {
                val rune = Rune.VALUES[secondRuneType - 1]
                player.tournamentRunePouch.set(1, Item(rune.id, Config.RUNE_POUCH_MIDDLE_AMOUNT.get(player)))
            }

            val thirdRuneType = Config.RUNE_POUCH_RIGHT_TYPE.get(player)
            if (thirdRuneType != 0) {
                val rune = Rune.VALUES[thirdRuneType - 1]
                player.tournamentRunePouch.set(2, Item(rune.id, Config.RUNE_POUCH_RIGHT_AMOUNT.get(player)))
            }

            player.cachedRunePouchTypes = 0
            player.cachedRunePouchAmounts = 0
        }

        resolvePrize()
    }

    private fun Player.resetCombat() {
        clearHintArrow()
        combat.restore()
        combat.resetKillers()
        combat.restoreSpecial(100)
    }

    /**
     * Advances a round for the winning player, if available.
     */
    private fun advanceRoundFor(loser: Player, fight: TournamentFightPair, logout: Boolean) {
        val opponent = fight.opponent(loser)!!
        if (logout) {
            opponent.message("Your opponent has logged out therefore you win by default.")
        } else {
            opponent.message("You have defeated ${loser.name}. You will advance to the next round.")
        }

        opponent.message("You will be regeared upon your next fight.")
    }

    fun canAttack(attacker: Player, target: Player, message: Boolean = false): Boolean {
        val fight = attacker.tournamentFight ?: return false
        if (!fight.commenced) {
            attacker.message("The fight has not started yet.")
            return false
        }

        if (fight.opponent(attacker) != target) {
            attacker.message("That player is not your target.")
            return false
        }

        return true
    }

    private fun canActivatePrayer(plr: Player, prayer: Prayer): Boolean {
        if (prayer in attributes.restrictedPrayers()) {
            plr.message("The use of this prayer is not permitted within this tournament.")
            return false
        }
        return true
    }

    private fun onDeath(dead: Entity, killer: Killer, hit: Hit) {
        if (dead is Player) {
            dead.eliminate(false)
            dead.teleport(3113 + random(3), 3513 + random(3), 0)
        }
    }

    private fun onLogout(player: Player) {
        player.eliminate(true);
        player.teleport(3113 + random(3), 3513 + random(3), 0)
    }

    private fun Player.refresh() {
        combat.resetKillers()
        combat.restore()
        setAction(1, PlayerAction.ATTACK)
        if (!attributes.spellbookLoadout().isActive(this)) {
            attributes.spellbookLoadout().setActive(this)
        }

        attributes.equipmentLoadout().forEach {
            equipment[it.first()] = it.second()
        }
        attributes.inventoryLoadout().forEach {
            inventory[it.first()] = it.second()
        }
        attributes.skillLoadout().forEach {
            stats[it.first()] = it.second()
            combat.updateLevel()
        }
        attributes.applyExtras(this)
    }

    private fun updateOverlay() {
        //TODO
    }

    private fun Player.resolvePrize() {
        when (contestants) {
            3 -> {
                inventory.add(COINS_995, 1_250_000)
                inventory.add(BLOOD_MONEY,250)
                message(Color.RED.wrap("Congratulations! You have placed third within the tournament. You have won 1.25M GP and 250 BM!"))
            }
            2 -> {
                inventory.add(COINS_995, 2_500_000)
                inventory.add(BLOOD_MONEY, 500)
                message(Color.RED.wrap("Congratulations! You have placed second within the tournament. You have won 2.5M GP, and 500 BM!"))
            }
            1 -> {
                inventory.add(COINS_995, 5_000_000)
                inventory.add(6199, 1)
                message(Color.RED.wrap("Congratulations! You have placed first within the tournament. You have won 5M GP and a mystery box!"))
            }
            else -> {
                message(Color.RED.wrap("You have placed #$contestants within the tournament."))
            }
        }
    }

    private fun Player.shoutCountdown(counter: Int) {
        if (counter > 0) {
            forceText("$counter...")
            message("$counter...")
        } else {
            forceText("FIGHT!")
            message("FIGHT!")
        }
    }

    private fun announce(msg: String) {
        participants.forEach {
            it.message(msg)
        }
    }
}