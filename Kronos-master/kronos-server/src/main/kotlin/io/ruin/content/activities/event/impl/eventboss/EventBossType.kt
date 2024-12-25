package io.ruin.content.activities.event.impl.eventboss

import io.ruin.content.activities.event.impl.eventboss.tabel.BrutalLavaDragonLoot
import io.ruin.content.activities.event.impl.eventboss.tabel.CorruptedNechryarchLoot
import io.ruin.model.item.actions.impl.Pet
import io.ruin.model.item.loot.LootTable
import io.ruin.model.map.Position

enum class EventBossType(val id: Int, val positions: MutableList<Position>, val hitpoints: Int, val message: MutableList<String>, val rolls: Int, val lootTable: LootTable, val embedUrl: String, val pet: Pet, val petChance: Int) {

    CORRUPTED_NECHRYARCH(15001, mutableListOf(
            Position.of(1609, 3886, 0),
            Position.of(3545, 3268, 0),
            Position.of(1235, 3526, 0)
    ), 5000, mutableListOf(
            "The Corrupted Nechryarch has spawned south of Wintertodt.",
            "The Corrupted Nechryarch has spawned south of Barrows.",
            "The Corrupted Nechryarch has spawned south of Mount Quidamortem."), 1, CorruptedNechryarchLoot(),
    "https://i.imgur.com/hnjb4vw.png", Pet.Nech, 150),
    BRUTAL_LAVA_DRAGON(15016, mutableListOf(
            Position.of(3225, 3884, 0)
    ), 5000, mutableListOf("The Brutal Lava Dragon has appeared North of Lava Dragon Isle!"), 1, BrutalLavaDragonLoot(),
    "https://i.imgur.com/uZWY3ap.png", Pet.LAVADRAGON, 150)
}
