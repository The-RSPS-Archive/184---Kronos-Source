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

object DhMeleeTournamentAttributes : TournamentAttributes {

    override fun playlist(): TournamentPlaylist {
        return TournamentPlaylist.DH
    }

    override fun spellbookLoadout(): SpellBook {
        return SpellBook.LUNAR
    }

    override fun equipmentLoadout(): Array<Tuple<Int, Item>> {
        return arrayOf(
                Tuple(Equipment.SLOT_HAT, Item(4716)),
                Tuple(Equipment.SLOT_CAPE, Item(21295)),
                Tuple(Equipment.SLOT_AMULET, Item(Preset.AMULET_OF_FURY)),
                Tuple(Equipment.SLOT_WEAPON, Item(12006)),
                Tuple(Equipment.SLOT_CHEST, Item(4720)),
                Tuple(Equipment.SLOT_SHIELD, Item(22322)),
                Tuple(Equipment.SLOT_LEGS, Item(4722)),
                Tuple(Equipment.SLOT_HANDS, Item(7462)),
                Tuple(Equipment.SLOT_FEET, Item(11840)),
                Tuple(Equipment.SLOT_RING, Item(11773))
        )
    }

    override fun inventoryLoadout(): Array<Tuple<Int, Item>> {
        var index = 0
        return arrayOf(
                Tuple(index++, Item(12695)),
                Tuple(index++, Item(10925)),
                Tuple(index++, Item(10925)),
                Tuple(index++, Item(6685)),
                Tuple(index++, Item(11802)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(3144)),
                Tuple(index++, Item(12791)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(3144)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(3144)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(3144)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(4718)),
                Tuple(index++, Item(4153)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index, Item(13441))
        )
    }

    override fun skillLoadout(): Array<Tuple<StatType, Int>> {
        return arrayOf(
                Tuple(StatType.Attack, 99),
                Tuple(StatType.Defence, 99),
                Tuple(StatType.Strength, 99),
                Tuple(StatType.Hitpoints, 99),
                Tuple(StatType.Ranged, 99),
                Tuple(StatType.Prayer, 99),
                Tuple(StatType.Magic, 99)
        )
    }

    override fun applyExtras(player: Player) {
        if (!player.runePouch.isEmpty) {
            player.cachedRunePouchTypes = Config.RUNE_POUCH_TYPES.get(player)
            player.cachedRunePouchAmounts = Config.RUNE_POUCH_AMOUNTS.get(player)
        }

        player.tournamentRunePouch.set(0, Item(Preset.EARTH_RUNE, 16000))
        player.tournamentRunePouch.set(1, Item(Preset.DEATH_RUNE, 16000))
        player.tournamentRunePouch.set(2, Item(Preset.ASTRAL_RUNE, 16000))
        player.tournamentPouch = true
    }

    override fun restrictedPrayers(): Array<Prayer> {
        return arrayOf(Prayer.PROTECT_ITEM, Prayer.REDEMPTION, Prayer.SMITE, Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MISSILES, Prayer.PROTECT_FROM_MELEE)
    }
}