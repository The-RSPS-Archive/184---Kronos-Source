package io.ruin.content.activities.event.impl.eventboss.tabel

import io.ruin.model.item.loot.LootItem
import io.ruin.model.item.loot.LootTable
import io.ruin.utility.Broadcast


/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/2/2020
 */
class BrutalLavaDragonLoot : LootTable() {

    init {
        guaranteedItems(
                LootItem(995, 100_000, 200_000, 100), //coins
                LootItem(11944, 5, 5, 100), //lava dragon bones
                LootItem(1748, 4, 8, 100) // black dhide
        )
        addTable(14,
                LootItem(380, 70, 120, 100), //lobster
                LootItem(454, 50, 100, 100), //coal
                LootItem(562, 200, 300, 100), //chaos rune
                LootItem(560, 200, 300, 100), //death rune
                LootItem(7937, 1500, 2500, 100), //ess
                LootItem(30083, 1, 1, 100).broadcast(Broadcast.WORLD) //Hides
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
                LootItem(30083, 2, 2, 100).broadcast(Broadcast.WORLD), //Hides
                LootItem(11230, 20, 50, 90)
        )
        addTable(1,
                LootItem(30145, 1, 1, 2).broadcast(Broadcast.WORLD), //leaf
                LootItem(30129, 1, 1, 2).broadcast(Broadcast.WORLD), //Augment
                LootItem(30083, 5, 5, 3).broadcast(Broadcast.WORLD) //Hides
        )
    }
}