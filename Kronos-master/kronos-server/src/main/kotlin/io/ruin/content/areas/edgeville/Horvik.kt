package io.ruin.content.areas.edgeville

import io.ruin.api.*
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.player.Player

/**
 * @author Leviticus
 */
object Horvik {

    private const val HORVIK = 2882
    private const val CRYSTAL_SEED = 4207
    private const val PRICE = 10_000_000
    private const val CRYSTAL_BOW = 4212
    private const val CRYSTAL_SHIELD = 4224

    init {
        whenItemOnNpc(CRYSTAL_SEED, HORVIK) { player, _, npc ->
            player.talk(npc)
        }
    }

    private fun Player.talk(man: NPC) = event {
        man.chat("Hello there, I see you have a crystal seed, would you like to make this into a Crystal Shield or Bow?")
        if (options("Yes", "No") == 1) {
            if (!inventory.contains(CRYSTAL_SEED, 1)) {
                player.messageBox("You do not have a crystal seed.")
                return@event
            }

            if (!inventory.contains(995, PRICE)) {
                player.messageBox("You do not have enough coins to purchase this.")
                return@event
            }
            if (options("Crystal Shield", "Crystal Bow") == 1) {
                purchaseCrystalShield()
            } else {
                purchaseCrystalBow()
            }
        } else {
            man.chat("Very well.")
        }
    }

    private fun Player.purchaseCrystalShield() {
        inventory.remove(995, PRICE)
        inventory.remove(CRYSTAL_SEED, 1)
        inventory.addOrDrop(CRYSTAL_SHIELD, 1)
    }

    private fun Player.purchaseCrystalBow() {
        inventory.remove(995, PRICE)
        inventory.remove(CRYSTAL_SEED, 1)
        inventory.addOrDrop(CRYSTAL_BOW, 1)
    }
}