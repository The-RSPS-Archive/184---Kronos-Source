package io.ruin.content.areas.edgeville

import io.ruin.api.message
import io.ruin.api.whenObjClick
import io.ruin.cache.ObjectID
import io.ruin.data.impl.Help
import io.ruin.model.entity.player.Player
import io.ruin.model.item.actions.impl.tradepost.TradePost

/**
 * @author Leviticus
 */
object TradingPostHandle {

    init {
        whenObjClick(ObjectID.GRAND_EXCHANGE_BOOTH, "open") { player, _ ->
            openTradingPost(player)
        }
        whenObjClick(ObjectID.GRAND_EXCHANGE_BOOTH, "coffer") { player, _ ->
            TradePost.openCoffer(player)
        }
        whenObjClick(ObjectID.GRAND_EXCHANGE_BOOTH, "guide") { player, _ ->
            Help.open(player, "trading_post")
        }
    }

    private fun openTradingPost(player: Player) {
        if (player.gameMode.isIronMan) {
            player.message("Your gamemode prevents you from accessing the trading post!")
            return
        }
        player.tradePost.openViewOffers()
    }
}