package io.ruin.api

import io.ruin.event.GameEventContext
import io.ruin.model.combat.Hit
import io.ruin.model.combat.Killer
import io.ruin.model.entity.Entity
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.player.Player
import io.ruin.model.entity.shared.listeners.*
import io.ruin.model.inter.Interface
import io.ruin.model.inter.InterfaceType
import io.ruin.model.inter.dialogue.NPCDialogue
import io.ruin.model.inter.dialogue.PlayerDialogue
import io.ruin.model.inter.utils.Unlock
import io.ruin.model.map.Position
import io.ruin.model.skills.prayer.Prayer
import kotlin.coroutines.coroutineContext
import kotlin.math.min

typealias DeathEndEvent = (Entity, Killer, Hit) -> Unit

fun Player.openMainInterface(interId: Int) = openInterface(InterfaceType.MAIN, interId)

fun Player.openPrimaryOverlay(interId: Int) = openInterface(InterfaceType.PRIMARY_OVERLAY, interId)

fun Player.closePrimaryOverlay() = closeInterface(InterfaceType.PRIMARY_OVERLAY)

fun Player.message(msg: String) = sendMessage(msg)

fun Player.teleport(x: Int, y: Int, z: Int = 0) = movement.teleport(x, y, z)

fun Player.teleport(tile: Position) = movement.teleport(tile.x, tile.y, tile.z)

fun Player.onDeathFinish(fn: DeathEndEvent) {
    deathEndListener = DeathListener(fn)
}

fun Player.onTeleportAction(fn: (Player) -> Boolean) {
    teleportListener = TeleportListener(fn)
}

fun Player.onPrayerActivate(fn: (Player, Prayer) -> Boolean) {
    activatePrayerListener = ActivatePrayerListener(fn)
}

fun Player.onPlayerAttack(fn: (Player, Player, Boolean) -> Boolean) {
    attackPlayerListener = AttackPlayerListener(fn)
}

fun Player.onLogoutAction(fn: (Player) -> Unit) {
    logoutListener = LogoutListener().onLogout(fn)
}

fun Player.onLogoutAttempt(fn: (Player) -> Boolean) {
    logoutListener = LogoutListener().onAttempt(fn)
}

fun Player.clearListeners() {
    logoutListener = null
    teleportListener = null
}

suspend fun Player.chat(msg: String, hideContinue: Boolean = false) {
    val event = coroutineContext[GameEventContext.Key]?.event!!
    val dialogue = PlayerDialogue(msg)
    if (hideContinue) {
        dialogue.hideContinue()
    }

    dialogue.open(player)
    event.yield()
    closeInterface(InterfaceType.CHATBOX)
}

suspend fun Player.chat(msg: String, npcId: Int, hideContinue: Boolean = false) {
    val event = coroutineContext[GameEventContext.Key]?.event!!
    val dialogue = NPCDialogue(npcId, msg)
    if (hideContinue) {
        dialogue.hideContinue()
    }

    dialogue.open(player)
    event.yield()
    closeInterface(InterfaceType.CHATBOX)
}

suspend fun NPC.chat(msg: String, hideContinue: Boolean = false) {
    val event = coroutineContext[GameEventContext.Key]?.event!!
    val player = event.ctx as Player
    val dialogue = NPCDialogue(this, msg)
    if (hideContinue) {
        dialogue.hideContinue()
    }

    dialogue.open(player)
    event.yield()
    player.closeInterface(InterfaceType.CHATBOX)
}

suspend fun Player.options(vararg options: String): Int {
    val event = coroutineContext[GameEventContext.Key]?.event!!
    val params = mutableListOf("Select an Option", "")
    for (i in 0 until options.size) {
        params[1] += options[i]
        if (i != options.size - 1)
            params[1] += "|"
    }

    openInterface(InterfaceType.CHATBOX, Interface.OPTIONS_DIALOGUE)
    packetSender.setAlignment(Interface.OPTIONS_DIALOGUE, 1, 0, -10)
    packetSender.sendClientScript(58, "ss", *params.toTypedArray())
    Unlock(Interface.OPTIONS_DIALOGUE, 1).children(1, 5).unlockSingle(this)
    val option = event.yield() as Int
    player.closeInterface(InterfaceType.CHATBOX)
    return option
}

suspend fun Player.messageBox(msg: String, hideContinue: Boolean = false, lineHeight: Int = 24) {
    val event = coroutineContext[GameEventContext.Key]!!.event
    player.openInterface(InterfaceType.CHATBOX, Interface.MESSAGE_DIALOGUE)
    player.packetSender.sendString(Interface.MESSAGE_DIALOGUE, 1, msg)
    player.packetSender.setTextStyle(Interface.MESSAGE_DIALOGUE, 1, 1, 1, lineHeight)
    if (hideContinue)
        player.packetSender.setHidden(Interface.MESSAGE_DIALOGUE, 2, true)
    else
        player.packetSender.sendString(Interface.MESSAGE_DIALOGUE, 2, "Click here to continue")

    event.yield()
    player.closeInterface(InterfaceType.CHATBOX)
}

fun Player.heal(amount: Int = 0) {
    hp = if (amount == 0) {
        maxHp
    } else {
        min(amount, maxHp)
    }
}