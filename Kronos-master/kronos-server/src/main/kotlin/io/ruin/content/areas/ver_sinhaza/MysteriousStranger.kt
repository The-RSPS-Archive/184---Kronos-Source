package io.ruin.content.areas.ver_sinhaza

import io.ruin.api.chat
import io.ruin.api.event
import io.ruin.api.options
import io.ruin.api.whenNpcClick
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.player.Player

/**
 * The interaction script for the Mysterious stranger.
 * @author Heaven
 */
object MysteriousStranger {

    const val ID  = 8325

    init {
        whenNpcClick(ID, 1) { player, npc ->
            if (player.mysteriousStrangerVarp.and(1.shl(1)) == 0) {
                player.talkAboutVerzik(npc)
            } else {
                player.talkTo(npc)
            }
        }
        whenNpcClick(ID, 2) { player, npc ->
            player.trade(npc)
        }
    }

    private fun Player.talkAboutVerzik(npc: NPC) = event {
        npc.chat("You look like someone who can handle themselves. Are you intending to partake in the Theatre?")
        chat("Maybe?")
        npc.chat("You're wondering what's in it for you I suppose. The citizens of Meiyerditch come here looking for freedom, but I can tell you're no citizen.")
        npc.chat("Don't worry, you're not the first to sneak in. The vampyres here turn a blind eye to that sort of thing. Outsiders generally perform better in the Theatre so make for more interesting entertainment.")
        npc.chat("Even the outsiders don't survive though, no one does. You, however, might just have what it takes.")
        chat("Where are you going with this?")
        npc.chat("You're strong enough to survive the Theatre but you need a reason to try. I have a little proposition for you.")
        chat("Go on...")
        npc.chat("The Theatre is owned by Lady Verzik Vitur. I represent a party that has certain... interests in Verzik.")
        npc.chat("Enter the Theatre and beat the challenges within. Doing so would be quite the embarassment for Verzik, something that my associates would very much appreciate.")
        chat("So you just need me to embarass her? Doesn't sound too bad.")
        npc.chat("Be aware, this will be no easy challenge. I doubt you'll succeed alone. However, the vampyres will let you enter in a group of up to five. I suggest you take advantage of this.")
        npc.chat("You can use the notice board to find suitable allies with whom to enter the Theatre.")
        mysteriousStrangerVarp = mysteriousStrangerVarp.or(1.shl(1))
        when(options("Have you any advice to help me?", "Very well, I'll do my best.")) {
            1 -> talkCrystal(npc)
            2 -> chat("Very well, I'll do my best.")
        }
    }

    private fun Player.talkTo(npc: NPC) = event {
        npc.chat("Any luck in the Theatre?")
        val opts = mutableListOf<String>()
        opts += "What am I meant to be doing again?"
        opts += if (talkedAboutCrystal()) {
            "Can I get those teleport crystals you mentioned"
        } else {
            "Have you any advice to help me?"
        }
        opts += "I've managed to defeat her!"
        when(options(*opts.toTypedArray())) {
            1 -> talkObjective(npc)
            2 -> {
                if (!talkedAboutCrystal()) {
                    talkCrystal(npc)
                } else {
                    //TODO open shop
                }
            }
        }
    }

    private fun Player.talkObjective(npc: NPC) = event {
        chat("What am I meant to be doing again?")
        npc.chat("Enter the Theatre and beat the challenges within. Doing so would cause great embarrassment to Verzik and my associated would very much appreciate it.")
    }

    private fun Player.talkCrystal(npc: NPC) = event {
        chat("Have you any advice to help me?")
        npc.chat("Actually, I do. Lady Verzik's magic prevents most forms of teleportation. However, there is a special crystal whose power can overcome the restriction.")
        npc.chat("My associates have... acquired some shards of the crystal; do not ask me how. If you take one into the Theatre, you can use it to teleport out to safety in an emergency.")
        mysteriousStrangerVarp = mysteriousStrangerVarp.or(1.shl(2))
        //TODO open shop
    }

    private fun Player.trade(npc: NPC) = event {
        npc.chat("Perhaps we should discuss some matters before we trade.")
        if (!talkedAboutVerzik()) {
            talkAboutVerzik(npc)
        } else if (!talkedAboutCrystal()) {
            talkCrystal(npc)
        } else {
            //TODO open shop
        }
    }

    fun Player.talkedAboutVerzik() = mysteriousStrangerVarp.and(1.shl(1)) != 0

    private fun Player.talkedAboutCrystal() = mysteriousStrangerVarp.and(1.shl(2)) != 0
}