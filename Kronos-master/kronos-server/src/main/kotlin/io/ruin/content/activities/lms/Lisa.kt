package io.ruin.content.activities.lms

import io.ruin.api.*
import io.ruin.content.activities.lms.LastManStandingLobby.add
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.player.Player
import io.ruin.model.entity.shared.listeners.SpawnListener
import io.ruin.model.shop.ShopManager

/**
 * @author Heaven
 */
object Lisa {

    private const val ID = 7317
    private val NPC = SpawnListener.find(ID)[0]

    init {
        whenNpcClick(ID, 1) { player, lisa ->
            player.talkTo(lisa)
        }
        whenNpcClick(ID, 3) { player, lisa ->
            player.selectOptions(lisa)
        }
        whenNpcClick(ID, 4) { player, lisa ->
           ShopManager.getByUUID("093f2aef-46b4-4b40-943b-0934954ee124")?.open(player);//TODO Add lisa UUID
        }
        pulse()
    }

    private fun pulse() = globalEvent {
        while(true) {
            pause(15)
            val currentQueueSize = LastManStandingLobby.queueSizeOf(LastManStandingMode.CASUAL)
            if (currentQueueSize < LastManStandingMode.CASUAL.threshold) {
                NPC.forceText("Come on, we need ${LastManStandingMode.CASUAL.threshold - currentQueueSize} more casual participants!")
            } else {
                NPC.forceText("A casual match is starting shortly!")
            }
        }
    }

    private fun Player.talkTo(lisa: NPC) = event {
        lisa.chat("Welcome, are you looking to take part in a game of<br>Last Man Standing?")
        when(options("Join Last Man Standing.", "Tell me more.", "Not right now.")) {
            1 -> {
                chat("I'd like to join Last Man Standing.")
                lisa.chat("Excellent, which mode would you like to participate in?")
                lisa.chat("Remember, you'll need to bank all of your items before stepping foot in Last Man Standing.")
            }
            2 -> player.tellMeMore(lisa)
            3 -> chat("Not right now.")
        }
    }

    private fun Player.tellMeMore(lisa: NPC) = event {
        chat("Tell me more.")
        lisa.chat("It's simple. A group of competitors are taken to the<br>island, only one comes out alive.")
        lisa.chat("You can't take any of your belongings to the island,<br>you can only use what you find there.")
        lisa.chat("The fight for survival will boost your physical and<br>mental abilities, ensuring you will make a worthy<br>contender as the Last Man Standing.")
        lisa.chat("Be warned, however, lethal fog will approach the island after a set amount of<br>time, forcing contenders to the designated safezone.")
        lisa.chat("Remember, this is a solo competition, anyone attempting<br>to form teams for an unfair advantage will be banished<br>from further participation.")
        lisa.chat("Now that you know more, would you like to take part<br>in a game of Last Man Standing?")
        if (options("Join Last Man Standing.", "Not right now.") == 1) {
            chat("I'd like to join Last Man Standing.")
            lisa.chat("Excellent, which mode would you like to participate in?")
            lisa.chat("Remember, you'll need to bank all of your items before stepping foot in Last Man Standing.")
            selectOptions(lisa)
        } else {
            chat("Not right now.")
        }
    }

    private fun Player.selectOptions(lisa: NPC) = event {
        when(options("<str>Competitive.</str>", "Casual.", "Cancel.")) {
            1 -> messageBox("Competitive mode is currently disabled.")
            2 -> add(LastManStandingMode.CASUAL)
        }
    }
}