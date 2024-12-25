package io.ruin.content.activities.event.impl.eventboss.tabel

import io.ruin.model.item.attributes.AttributeExtensions
import io.ruin.model.item.attributes.AttributeTypes
import io.ruin.model.item.loot.LootItem
import io.ruin.model.item.loot.LootTable
import io.ruin.utility.AttributePair
import io.ruin.utility.Broadcast

/**
 *
 * @project Kronos
 * @author ReverendDread on 5/12/2020
 * https://www.rune-server.ee/members/reverenddread/
 */

class CorruptedNechryarchLoot : LootTable() {

    init {
        guaranteedItems(
                LootItem(995, 100_000, 200_000, 100), //coins
                LootItem(532, 1, 1, 100) //coins
        )
        addTable(25,
                LootItem(380, 50, 100, 100), //lobster
                LootItem(454, 50, 100, 100), //coal
                LootItem(562, 200, 300, 100), //chaos rune
                LootItem(560, 200, 300, 100), //death rune
                LootItem(7937, 1500, 2500, 100) //ess
        )
        addTable(10,
                LootItem(995, 100_000, 500_000, 100), //coins
                LootItem(561, 200, 300, 100), //Nature rune
                LootItem(565, 200, 300, 100), //Blood rune
                LootItem(21880, 50, 100, 90), //Wrath rune
                LootItem(386, 50, 100, 100), //Shark
                LootItem(392, 50, 100, 90), //manta
                LootItem(454, 200, 500, 100), //coal
                LootItem(441, 50, 100, 100), //iron
                LootItem(11230, 20, 50, 90)
        )
        addTable(1,
                LootItem(22610, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta spear
                LootItem(22613, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta sword
                LootItem(22616, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta chest
                LootItem(22619, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta skirt
                LootItem(22622, 1, 1, 1).broadcast(Broadcast.GLOBAL), //statius warhammer
                LootItem(22625, 1, 1, 1).broadcast(Broadcast.GLOBAL), //statius helm
                LootItem(22628, 1, 1, 1).broadcast(Broadcast.GLOBAL), //statius plate
                LootItem(22631, 1, 1, 1).broadcast(Broadcast.GLOBAL), //statius legs
                LootItem(22634, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Morrigans axe
                LootItem(22636, 1, 1, 1).broadcast(Broadcast.GLOBAL), //morrigans javelin
                LootItem(22638, 1, 1, 1).broadcast(Broadcast.GLOBAL), //morrigans coif
                LootItem(22641, 1, 1, 1).broadcast(Broadcast.GLOBAL), //morrigans body
                LootItem(22644, 1, 1, 1).broadcast(Broadcast.GLOBAL), //morrigans chaps
                LootItem(22647, 1, 1, 1).broadcast(Broadcast.GLOBAL), //zuriels staff
                LootItem(22650, 1, 1, 1).broadcast(Broadcast.GLOBAL), //zuriels hood
                LootItem(22653, 1, 1, 1).broadcast(Broadcast.GLOBAL), //zuriels top
                LootItem(22656, 1, 1, 1).broadcast(Broadcast.GLOBAL), //zuriels bottom
                LootItem(22656, 1, 1, 1).broadcast(Broadcast.GLOBAL) //custom javelin
        )
    }

}