package io.ruin.content.activities.tournament.presets

import io.ruin.api.utils.Tuple
import io.ruin.content.activities.tournament.TournamentAttributes
import io.ruin.content.activities.tournament.TournamentPlaylist
import io.ruin.model.inter.journal.presets.Preset
import io.ruin.model.item.Item
import io.ruin.model.item.containers.Equipment
import io.ruin.model.skills.prayer.Prayer
import io.ruin.model.stat.StatType

object PureRangedMeleeTournamentAttributes : TournamentAttributes {

    override fun playlist(): TournamentPlaylist {
        return TournamentPlaylist.PURE_RANGED_MELEE
    }

    override fun equipmentLoadout(): Array<Tuple<Int, Item>> {
        return arrayOf(
                Tuple(Equipment.SLOT_HAT, Item(2581)),
                Tuple(Equipment.SLOT_CAPE, Item(21295)),
                Tuple(Equipment.SLOT_AMULET, Item(Preset.AMULET_OF_FURY)),
                Tuple(Equipment.SLOT_WEAPON, Item(12788)),
                Tuple(Equipment.SLOT_CHEST, Item(12596)),
                Tuple(Equipment.SLOT_LEGS, Item(12383)),
                Tuple(Equipment.SLOT_HANDS, Item(11133)),
                Tuple(Equipment.SLOT_FEET, Item(3105)),
                Tuple(Equipment.SLOT_RING, Item(11773)),
                Tuple(Equipment.SLOT_AMMO, Item(21326, 100))
        )
    }

    override fun inventoryLoadout(): Array<Tuple<Int, Item>> {
        var index = 0
        return arrayOf(
                Tuple(index++, Item(12695)),
                Tuple(index++, Item(2444)),
                Tuple(index++, Item(10925)),
                Tuple(index++, Item(10925)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(13441)),
                Tuple(index++, Item(3144)),
                Tuple(index++, Item(6685)),
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
                Tuple(index++, Item(11804)),
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
                Tuple(StatType.Attack, 75),
                Tuple(StatType.Defence, 1),
                Tuple(StatType.Strength, 99),
                Tuple(StatType.Hitpoints, 99),
                Tuple(StatType.Ranged, 99),
                Tuple(StatType.Prayer, 52),
                Tuple(StatType.Magic, 99)
        )
    }

    override fun restrictedPrayers(): Array<Prayer> {
        return arrayOf(Prayer.PROTECT_ITEM, Prayer.REDEMPTION, Prayer.SMITE, Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MISSILES, Prayer.PROTECT_FROM_MELEE)
    }
}