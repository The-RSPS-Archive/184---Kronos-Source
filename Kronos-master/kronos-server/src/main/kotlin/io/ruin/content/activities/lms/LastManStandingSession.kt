package io.ruin.content.activities.lms

import io.ruin.api.*
import io.ruin.cache.Color
import io.ruin.model.World
import io.ruin.model.combat.Hit
import io.ruin.model.combat.Killer
import io.ruin.model.entity.Entity
import io.ruin.model.entity.player.Player
import io.ruin.model.entity.player.PlayerAction
import io.ruin.model.inter.Interface.LAST_MAN_STANDING_GAME
import io.ruin.model.inter.InterfaceType
import io.ruin.model.inter.utils.Config
import io.ruin.model.item.Item
import io.ruin.model.item.actions.impl.storage.RunePouch
import io.ruin.model.item.containers.Equipment
import io.ruin.model.map.dynamic.DynamicMap
import io.ruin.model.skills.magic.SpellBook
import io.ruin.model.skills.magic.rune.Rune
import io.ruin.model.stat.Stat
import io.ruin.model.stat.StatType
import java.util.*

/**
 * Represents a session of the Last Man Standing activity.
 * @author Heaven
 */
class LastManStandingSession(private val queue: LastManStandingQueue) {

    /**
     * The stats we boost during this activity.
     */
    private val BOOSTED_STATS = arrayOf(StatType.Attack, StatType.Defence, StatType.Strength, StatType.Hitpoints, StatType.Ranged, StatType.Prayer, StatType.Magic)

    /**
     * A flag indicating if this is a competitive session. If true, we modify players rankings respectively.
     */
    private val competitive = queue.mode == LastManStandingMode.COMPETITIVE

    /**
     * The collection of [Player] participants in this activity.
     */
    internal val participants = mutableListOf<Player>()

    /**
     * The map chunks encapsulating the activity region.
     */
    var map: DynamicMap = DynamicMap()

    /**
     * The total amount of players that participated in this session.
     */
    private val totalParticipants = queue.players.size

    /**
     * Gets the remaining players within this session.
     */
    private val remainingPlayers get() = participants.size

    /**
     * A tick timer used to monitor the frequency at which events occur within this activity.
     */
    private var cycles = 0

    /**
     * A flag indicating if this activity is in progress.
     */
    private var inProgress = false

    /**
     * The grace period before the fights begin.
     */
    private var roundCountdown = 15

    /**
     * The designated fog sequence for this activity.
     */
    private val FOG_SEQ = LMSFogSequence.DEBTO_HIDEOUT

    /**
     * How often the fog within this activity will be updated.
     */
    private var fogState = -1

    /**
     * The loot crate within this activity.
     */
    @JvmField var crate: LastManStandingCrate? = null

    /**
     * Set up the environment and start the activity.
     */
    fun start() {
        inProgress = true
        map.buildSw(13658, 2).buildSe(13914, 2).buildNw(13659, 2).buildNe(13915, 2)
        participants.addAll(queue.players)
        participants.shuffle()
        for (plr in participants.withIndex()) {
            plr.value.lmsQueue = null
            plr.value.closeInterface(InterfaceType.CHATBOX)
            plr.value.lock()
            plr.value.lmsSessionKills = 0
            val tile = LastManStanding.ARENA_SPAWNS[plr.index]
            plr.value.teleport(map.convertX(tile.x), map.convertY(tile.y))
            plr.value.lmsSession = this
            plr.value.combat.resetKillers()
            plr.value.combat.restore()
            plr.value.setAction(1, PlayerAction.ATTACK)
            plr.value.onLogoutAction(::onLogout)
            plr.value.onPlayerAttack(::onPlayerAttack)
            plr.value.onTeleportAction(::onTeleport)
            plr.value.onDeathFinish(::onDeath)
            plr.value.setAttributes()
            plr.value.setStats()
            plr.value.setGear()
            plr.value.message("The grace period has begun!")
            plr.value.updateOverlay(true)
            plr.value.unlock()
        }

        pulse()
    }

    private fun Player.resetCombatState() {
        combat.resetKillers()
        combat.restoreSpecial(100)
        movement.restoreEnergy(100)
    }

    private fun Player.eliminate() {
        remove()
        if (remainingPlayers == 1) {
            val player = participants[0]
            if (World.getPlayer(player.index) != null) {
                player.setWinner()
            }
        }
        closePrimaryOverlay()
    }

    /**
     * Removes the player from this activity and removes any attributes or listeners that were set within this scope.
     */
    private fun Player.remove() {
        lmsKills += lmsSessionKills
        lmsGamesPlayed++
        normalize()
        equipment.clear()
        inventory.clear()
        giveReward()
        participants -= this
        triggerOverlayUpdate()
    }

    fun eliminatePlayer(player: Player) {
        player.eliminate()
    }

    fun exchangeKey(player: Player) {
        val key = player.inventory.items.filter(Objects::nonNull).find { it.id == LastManStanding.BLOODY_KEY || it.id == LastManStanding.BLOODIER_KEY }
        if (key != null) {
            player.lock()
            player.rollLoot(key)
            player.heal()
            player.unlock()
        } else {
            player.message("You require a bloody key to loot this chest.")
        }
    }

    /**
     * The main activity event.
     */
    private fun pulse() = globalEvent {
        while(inProgress) {
            ++cycles
            if (roundCountdown > 0) {
                roundCountdown--
                if (roundCountdown in 0..10) {
                    participants.forEach {
                        it.shoutCountdown(roundCountdown)
                    }
                }
            }
            if (cycles % 20 == 0) {
                fogState++
                when(fogState) {
                    0 -> broadcastSafeSpot()
                }
            }
            if (cycles % 30 == 0) {
                triggerOverlayUpdate()
            }
            if (cycles % 80 == 0) {
                generateLootCrate()
            }
            pause(3)
        }
    }

    /**
     * Actions to be invoked upon a players death.
     * @param killer The cause of death for the specified player.
     */
    private fun Player.onDeath(killer: Killer) {
        if (killer.player != null && killer.player.lmsSession != null) {
            killer.player.lmsSessionKills++
            if (killer.player.awardedKey()) {
                message("A mysterious looking key has appeared in your inventory.")
            }
            killer.player.refillSupplies()
            killer.player.resetCombatState()
        }

        teleport(LastManStanding.randomLobbyTile())
    }

    /**
     * A flag that returns if the key was successfully added to the players inventory.
     */
    private fun Player.awardedKey(): Boolean {
        val keyId = if (participants.size <= 5) LastManStanding.BLOODIER_KEY else LastManStanding.BLOODY_KEY
        return inventory.add(Item(keyId)) > 0
    }

    /**
     * Flag the winner.
     */
    private fun Player.setWinner() {
        remove()
        teleport(LastManStanding.randomLobbyTile())
        message("You are victorious!")
        lmsWins++
        end()
    }

    /**
     * Ends this activity and disposes of all resources.
     */
    private fun end() {
        inProgress = false
        crate?.destroy()
        crate = null
        participants.clear()
        map.destroy()
    }

    private fun Player.shoutCountdown(timer: Int) {
        if (timer > 0) {
            forceText("$timer!")
        } else {
            forceText("FIGHT!")
            message("The grace period has ended!")
        }
    }

    private fun onDeath(entity: Entity, killer: Killer, hit: Hit) {
        if (entity is Player) {
            entity.onDeath(killer)
            entity.eliminate()
        }
    }

    private fun onTeleport(player: Player): Boolean {
        player.message("You can't use spells of this kind within the arena.")
        return false
    }

    private fun onPlayerAttack(player: Player, target: Player, message: Boolean = false): Boolean {
        if (roundCountdown != 0) {
            player.message("The grace period has not ended yet.")
            return false
        }
        val last = target.combat.lastAttacker
        if (last != null && last != player && target.combat.isDefending(20)) {
            player.message(target.name + " is fighting another player.")
            return false
        }
        return true
    }

    private fun onLogout(player: Player) {
        player.eliminate()
        player.teleport(LastManStanding.randomLobbyTile())
        if (player.combat.isDefending(10) && player.combat.lastAttacker in participants) {
            val attacker = player.combat.lastAttacker
            if (attacker is Player && attacker.lmsSession != null) {
                if (attacker.awardedKey()) {
                    attacker.message("A mysterious looking key has appeared in your inventory.")
                }
                attacker.refillSupplies()
                attacker.resetCombatState()
            }
        }
    }

    /**
     * Awards the player their reward respective to their placement within this activity.
     */
    private fun Player.giveReward() {
        val rewardAmt = when(remainingPlayers) {
            1 -> 25
            2 -> 15
            3 -> 5
            else -> 2
        }

        inventory.add(20527, rewardAmt)
        message(Color.RED.wrap("You have been awarded $rewardAmt Survival tokens for placing #$remainingPlayers in the competition."))
    }

    /**
     * Sends an overlay update to all active participants in the activity.
     */
    private fun triggerOverlayUpdate() {
        participants.forEach {
            it.updateOverlay(false)
        }
    }

    /**
     * Handles the displaying & updating of the overlay involved with this session.
     * @param display Flags if we want to send the displayInterface packet.
     */
    private fun Player.updateOverlay(display: Boolean = false) {
        packetSender.sendString(LAST_MAN_STANDING_GAME, 9, "$remainingPlayers / $totalParticipants")
        packetSender.sendString(LAST_MAN_STANDING_GAME, 11, lmsSessionKills.toString())
        packetSender.sendString(LAST_MAN_STANDING_GAME, 13, "Safe")
        packetSender.sendString(LAST_MAN_STANDING_GAME, 14, "${cycles / 20} mins")
        if (display) {
            openPrimaryOverlay(LAST_MAN_STANDING_GAME)
        }
    }

    /**
     * Attempts to find a key within the players inventory then rolls for loot.
     */
    private fun Player.rollLoot(key: Item) {
        inventory.remove(key.id, 1)
        val keyName = if (key.id == LastManStanding.BLOODIER_KEY) "bloodier" else "bloody"
        if (key.id == LastManStanding.BLOODY_KEY) {
            val rollCount = random(3)
            for (i in 0 until rollCount) {
                val rand = random(6)
                when(rand) {
                    1 -> player.randLootOf(LastManStanding.CAPE_UPGRADES)
                    2 -> player.randLootOf(LastManStanding.BOOT_UPGRADES)
                    3 -> player.randLootOf(LastManStanding.WEAPON_UPGRADES)
                    4 -> player.randLootOf(LastManStanding.CHEST_UPGRADES)
                    5 -> player.randLootOf(LastManStanding.LEG_UPGRADES)
                    else -> player.randLootOf(LastManStanding.COMMON_LOOT_TABLE)
                }
            }
        } else {
            inventory.addOrDrop(LastManStanding.WEAPON_UPGRADES.random(), 1)
        }
        message("You feel invigorated as the $keyName key is consumed and you find some loot!")
    }

    /**
     * Refills the players consumables in their inventory after every key has been used.
     */
    private fun Player.refillSupplies() {
        if (inventory.isFull) return
        inventory.items.filter(Objects::nonNull).forEach {
            if (it.id == 229) {
                inventory.remove(it.id, 1)
            }

            if (it.def.consumable) {
                inventory.remove(it.id, 1)
            }
        }

        LastManStanding.POTIONS.forEach {
            inventory.add(Item(it))
        }

        val sharks = inventory.freeSlots
        if (sharks > 0) {
            for (i in 0 until sharks) {
                inventory.add(Item(385))
            }
        }
    }

    private fun Player.randLootOf(potential: Array<Int>) = inventory.addOrDrop(potential.random(), 1)

    /**
     * Detaches the player from this session & removes any attributes relevant to this activity.
     */
    private fun Player.normalize() {
        lmsSession = null
        attackPlayerListener = null
        teleportListener = null
        logoutListener = null
        deathEndListener = null
        setAction(1, null)
        combat.restore()
        combat.resetKillers()
        stats.set(StatType.Attack, preTournyAttack[0], preTournyAttack[1])
        stats.set(StatType.Strength, preTournyStrength[0], preTournyStrength[1])
        stats.set(StatType.Defence, preTournyDefence[0], preTournyDefence[1])
        stats.set(StatType.Hitpoints, preTournyHitpoints[0], preTournyHitpoints[1])
        stats.set(StatType.Ranged, preTournyRanged[0], preTournyRanged[1])
        stats.set(StatType.Prayer, preTournyPrayer[0], preTournyPrayer[1])
        stats.set(StatType.Magic, preTournyMagic[0], preTournyMagic[1])
        combat.updateCombatLevel()
        if (tournamentRigour) {
            Config.RIGOUR_UNLOCK.reset(this)
            Config.RIGOUR_UNLOCK.update(this)
            tournamentRigour = false
        }
        if (tournamentAugury) {
            Config.AUGURY_UNLOCK.reset(this)
            Config.AUGURY_UNLOCK.update(this)
            tournamentAugury = false
        }
        if (tournamentPreserve) {
            Config.PRESERVE_UNLOCK.reset(this)
            Config.PRESERVE_UNLOCK.update(this)
            tournamentPreserve = false
        }
        if (tournamentPouch) {
            tournamentRunePouch.set(0, null)
            tournamentRunePouch.set(1, null)
            tournamentRunePouch.set(2, null)
            tournamentPouch = false
        }
        if (cachedRunePouchTypes != 0) {
//            runePouch.empty(false)
            Config.RUNE_POUCH_TYPES.set(this, cachedRunePouchTypes)
            Config.RUNE_POUCH_AMOUNTS.set(this, cachedRunePouchAmounts)

            val firstRuneType = Config.RUNE_POUCH_LEFT_TYPE.get(this)
            if (firstRuneType != 0) {
                val rune = Rune.VALUES[firstRuneType - 1]
                tournamentRunePouch.set(0, Item(rune.id, Config.RUNE_POUCH_LEFT_AMOUNT.get(this)))
            }

            val secondRuneType = Config.RUNE_POUCH_MIDDLE_TYPE.get(this)
            if (secondRuneType != 0) {
                val rune = Rune.VALUES[secondRuneType - 1]
                tournamentRunePouch.set(1, Item(rune.id, Config.RUNE_POUCH_MIDDLE_AMOUNT.get(this)))
            }

            val thirdRuneType = Config.RUNE_POUCH_RIGHT_TYPE.get(this)
            if (thirdRuneType != 0) {
                val rune = Rune.VALUES[thirdRuneType - 1]
                tournamentRunePouch.set(2, Item(rune.id, Config.RUNE_POUCH_RIGHT_AMOUNT.get(this)))
            }

            cachedRunePouchTypes = 0
            cachedRunePouchAmounts = 0
        }
    }

    /**
     * Sets any attributes to the player, if needed, for this activity.
     */
    private fun Player.setAttributes() {
        SpellBook.ANCIENT.setActive(this)
        preTournyAttack = intArrayOf(stats.get(StatType.Attack).fixedLevel, stats.get(StatType.Attack).experience.toInt())
        preTournyStrength = intArrayOf(stats.get(StatType.Strength).fixedLevel, stats.get(StatType.Strength).experience.toInt())
        preTournyDefence = intArrayOf(stats.get(StatType.Defence).fixedLevel, stats.get(StatType.Defence).experience.toInt())
        preTournyRanged = intArrayOf(stats.get(StatType.Ranged).fixedLevel, stats.get(StatType.Ranged).experience.toInt())
        preTournyPrayer = intArrayOf(stats.get(StatType.Prayer).fixedLevel, stats.get(StatType.Prayer).experience.toInt())
        preTournyMagic = intArrayOf(stats.get(StatType.Magic).fixedLevel, stats.get(StatType.Magic).experience.toInt())
        preTournyHitpoints = intArrayOf(stats.get(StatType.Hitpoints).fixedLevel, stats.get(StatType.Hitpoints).experience.toInt())
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
        if (!player.runePouch.isEmpty) {
            player.cachedRunePouchTypes = Config.RUNE_POUCH_TYPES.get(player)
            player.cachedRunePouchAmounts = Config.RUNE_POUCH_AMOUNTS.get(player)
        }
    }

    /**
     * Sets the players initial inventory & equipment setup.
     */
    private fun Player.setGear() {
        equipment[Equipment.SLOT_HAT] = Item(10828)
        equipment[Equipment.SLOT_CAPE] = Item(2412)
        equipment[Equipment.SLOT_SHIELD] = Item(12829)
        equipment[Equipment.SLOT_WEAPON] = Item(4675)
        equipment[Equipment.SLOT_AMULET] = Item(1712)
        equipment[Equipment.SLOT_FEET] = Item(3105)
        equipment[Equipment.SLOT_HANDS] = Item(7462)
        equipment[Equipment.SLOT_CHEST] = Item(4091)
        equipment[Equipment.SLOT_LEGS] = Item(4093)
        equipment[Equipment.SLOT_AMMO] = Item(9244, 5000)
        equipment[Equipment.SLOT_RING] = Item(6737)
        var index = 0
        inventory[index++] = Item(2503)
        inventory[index++] = Item(1079)
        inventory[index++] = Item(12954)
        inventory[index++] = Item(4151)
        inventory[index++] = Item(9185)
        inventory[index++] = Item(1215)
        inventory[index++] = Item(10499)
        inventory[index++] = Item(6685)
        inventory[index++] = Item(3024)
        inventory[index++] = Item(3024)
        inventory[index++] = Item(2444)
        inventory[index++] = Item(12695)
        inventory[index++] = Item(12625)
        for (i in index until inventory.items.size - 3) {
            inventory[index++] = Item(385)
        }

        inventory[index++] = Item(3144)
        inventory[index++] = Item(3144)
        inventory[index] = Item(RunePouch.RUNE_POUCH)

        tournamentRunePouch.set(0, Item(Rune.WATER.id, 16000))
        tournamentRunePouch.set(1, Item(Rune.DEATH.id, 16000))
        tournamentRunePouch.set(2, Item(Rune.BLOOD.id, 16000))
        player.tournamentPouch = true
    }

    /**
     * Boosts the whitelisted stats of this player while they're within this activity.
     */
    private fun Player.setStats() {
        val xp = Stat.xpForLevel(99)
        for (boosted in BOOSTED_STATS) {
            val stat = stats[boosted]
            stat.fixedLevel = 99
            stat.currentLevel = stat.fixedLevel
            stat.experience = xp.toDouble()
            stat.updated = true
        }

        combat.updateCombatLevel()
    }

    /**
     * Broadcasts the safespot to the players within this activity.
     */
    private fun broadcastSafeSpot() {
        participants.forEach {
            it.packetSender.sendMessage("Lethal fog will soon fill the island! The safezone is the ${FOG_SEQ.title}.", "", 14)
        }
    }

    /**
     * Generates and broadcasts the new location for the loot crate.
     */
    private fun generateLootCrate() {
        crate = LastManStandingCrate(this, LMSCrateLocations.values().random())
        crate!!.spawn()
    }
}