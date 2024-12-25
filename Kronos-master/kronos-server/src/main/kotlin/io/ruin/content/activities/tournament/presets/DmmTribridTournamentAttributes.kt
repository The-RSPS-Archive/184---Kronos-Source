package io.ruin.content.activities.tournament.presets

import io.ruin.api.utils.Tuple
import io.ruin.content.activities.tournament.TournamentAttributes
import io.ruin.content.activities.tournament.TournamentPlaylist
import io.ruin.model.entity.player.Player
import io.ruin.model.inter.journal.presets.Preset
import io.ruin.model.inter.utils.Config
import io.ruin.model.item.Item
import io.ruin.model.item.actions.impl.storage.RunePouch
import io.ruin.model.item.containers.Equipment
import io.ruin.model.skills.magic.SpellBook
import io.ruin.model.skills.prayer.Prayer
import io.ruin.model.stat.StatType

object DmmTribridTournamentAttributes: TournamentAttributes {

    override fun playlist(): TournamentPlaylist {
        return TournamentPlaylist.DMM_TRIBRID
    }

    override fun spellbookLoadout(): SpellBook {
        return SpellBook.ANCIENT
    }

    override fun equipmentLoadout(): Array<Tuple<Int, Item>> {
        return arrayOf(
                Tuple(Equipment.SLOT_HAT, Item(4724)),
                Tuple(Equipment.SLOT_CAPE, Item(Preset.IMBUED_SARADOMIN_CAPE)),
                Tuple(Equipment.SLOT_AMULET, Item(Preset.AMULET_OF_FURY)),
                Tuple(Equipment.SLOT_WEAPON, Item(22647)),
                Tuple(Equipment.SLOT_CHEST, Item(Preset.AHRIM_ROBETOP)),
                Tuple(Equipment.SLOT_SHIELD, Item(Preset.MAGES_BOOK)),
                Tuple(Equipment.SLOT_LEGS, Item(Preset.AHRIM_ROBESKIRT)),
                Tuple(Equipment.SLOT_HANDS, Item(7462)),
                Tuple(Equipment.SLOT_FEET, Item(6920)),
                Tuple(Equipment.SLOT_RING, Item(Preset.SEERS_RING_IMBUE)),
                Tuple(Equipment.SLOT_AMMO, Item(21948, 100))
        )
    }

    override fun inventoryLoadout(): Array<Tuple<Int, Item>> {
        var index = 0
        return arrayOf(
                Tuple(index++, Item(Preset.ARMADYL_CROSSBOW)),
                Tuple(index++, Item(11828)),
                Tuple(index++, Item(22613)),
                Tuple(index++, Item(Preset.COOKED_KARAMBWAN)),
                Tuple(index++, Item(11283)),
                Tuple(index++, Item(Preset.VERAC_PLATESKIRT)),
                Tuple(index++, Item(Preset.DRAGON_DEFENDER)),
                Tuple(index++, Item(Preset.COOKED_KARAMBWAN)),
                Tuple(index++, Item(Preset.DHAROK_BODY)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SARADOMIN_BREW_4)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.RANGING_POTION_4)),
                Tuple(index++, Item(Preset.SANFEW_SERUM)),
                Tuple(index++, Item(Preset.SUPER_RESTORE_POTION_4)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SUPER_COMBAT_POTION)),
                Tuple(index++, Item(566, 1000)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index++, Item(Preset.SHARK)),
                Tuple(index, Item(RunePouch.RUNE_POUCH))
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

        player.tournamentRunePouch.set(0, Item(Preset.WATER_RUNE, 10000))
        player.tournamentRunePouch.set(1, Item(Preset.DEATH_RUNE, 8000))
        player.tournamentRunePouch.set(2, Item(Preset.BLOOD_RUNE, 4000))
        player.tournamentPouch = true
    }

    override fun restrictedPrayers(): Array<Prayer> {
        return arrayOf(Prayer.PROTECT_ITEM, Prayer.REDEMPTION, Prayer.SMITE)
    }
}