package io.ruin.content.items

import io.ruin.api.event
import io.ruin.api.messageBox
import io.ruin.api.options
import io.ruin.api.whenInvClick
import io.ruin.model.entity.player.Player
import io.ruin.model.item.Item

/**
 * @author Leviticus
 */
object SkinScrolls {
    private const val GREEN_SKIN_SCROLL = 6806
    private const val BLUE_SKIN_SCROLL = 6807
    private const val PURPLE_SKIN_SCROLL = 6808
    private val scrolls = intArrayOf(GREEN_SKIN_SCROLL, BLUE_SKIN_SCROLL, PURPLE_SKIN_SCROLL)

    init {
        scrolls.forEach {
            whenInvClick(it, 1) { player, item ->
                player.redeem(item)
            }
        }
    }

    private fun Player.redeem(item: Item) = event {
        if (options("Redeem Scroll", "No, I don't want to do that") == 1) {
            if (item.id == GREEN_SKIN_SCROLL && unlockedGreenSkin ||
                    item.id == BLUE_SKIN_SCROLL && unlockedBlueSkin ||
                    item.id == PURPLE_SKIN_SCROLL && unlockedPurpleSkin) {
                player.messageBox("You already have this skin unlocked.")
                return@event
            }
            when(item.id) {
                GREEN_SKIN_SCROLL -> unlockedGreenSkin = true
                BLUE_SKIN_SCROLL -> unlockedBlueSkin = true
                PURPLE_SKIN_SCROLL -> unlockedPurpleSkin = true
            }
            item.remove()
        }
    }
}