package io.ruin.content.activities.tournament.presets

import io.ruin.api.utils.Tuple
import io.ruin.content.activities.tournament.TournamentAttributes
import io.ruin.content.activities.tournament.TournamentPlaylist
import io.ruin.model.entity.player.Player
import io.ruin.model.inter.journal.presets.Preset
import io.ruin.model.inter.utils.Config
import io.ruin.model.item.Item
import io.ruin.model.item.containers.Equipment
import io.ruin.model.skills.magic.SpellBook
import io.ruin.model.skills.prayer.Prayer
import io.ruin.model.stat.StatType

object ZerkNhTournamentAttributes : TournamentAttributes {

    override fun playlist(): TournamentPlaylist {
        return TournamentPlaylist.ZERK_HYBRID
    }

    override fun spellbookLoadout(): SpellBook {
        return SpellBook.ANCIENT
    }

    override fun equipmentLoadout(): Array<Tuple<Int, Item>> {
        return arrayOf(
                Tuple(Equipment.SLOT_HAT, Item(3751)),
                Tuple(Equipment.SLOT_CAPE, Item(21791)),
                Tuple(Equipment.SLOT_AMULET, Item(Preset.AMULET_OF_FURY)),
                Tuple(Equipment.SLOT_WEAPON, Item(21006)),
                Tuple(Equipment.SLOT_CHEST, Item(10338)),
                Tuple(Equipment.SLOT_SHIELD, Item(12829)),
                Tuple(Equipment.SLOT_LEGS, Item(10340)),
                Tuple(Equipment.SLOT_HANDS, Item(7462)),
                Tuple(Equipment.SLOT_FEET, Item(6920)),
                Tuple(Equipment.SLOT_RING, Item(11773)),
                Tuple(Equipment.SLOT_AMMO, Item(21932, 500))
        )
    }

    override fun inventoryLoadout(): Array<Tuple<Int, Item>> {
        var index = 0
        return arrayOf(
                Tuple(index++, Item(4587)),
                Tuple(index++, Item(10330)),
                Tuple(index++, Item(11785)),
                Tuple(index++, Item(12791)),
                Tuple(index++, Item(8850)),
                Tuple(index++, Item(10332)),
                Tuple(index++, Item(22109)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13652)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(10925)),
                Tuple(index++, Item(12695)),
                Tuple(index++, Item(2444)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(10925)),
                Tuple(index++, Item(6685)),
                Tuple(index++, Item(6685)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(10925)),
                Tuple(index++, Item(6685)),
                Tuple(index++, Item(6685)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index, Item(13441))
        )
    }

    override fun skillLoadout(): Array<Tuple<StatType, Int>> {
        return arrayOf(
                Tuple(StatType.Attack, 60),
                Tuple(StatType.Defence, 45),
                Tuple(StatType.Strength, 99),
                Tuple(StatType.Hitpoints, 99),
                Tuple(StatType.Ranged, 99),
                Tuple(StatType.Prayer, 55),
                Tuple(StatType.Magic, 99)
        )
    }

    override fun applyExtras(player: Player) {
        if (!player.runePouch.isEmpty) {
            player.cachedRunePouchTypes = Config.RUNE_POUCH_TYPES.get(player)
            player.cachedRunePouchAmounts = Config.RUNE_POUCH_AMOUNTS.get(player)
        }

        player.runePouch.set(0, Item(Preset.WATER_RUNE, 10000))
        player.runePouch.set(1, Item(Preset.DEATH_RUNE, 8000))
        player.runePouch.set(2, Item(Preset.BLOOD_RUNE, 4000))
        player.tournamentPouch = true
    }

    override fun restrictedPrayers(): Array<Prayer> {
        return arrayOf(Prayer.PROTECT_ITEM, Prayer.REDEMPTION, Prayer.SMITE)
    }
}