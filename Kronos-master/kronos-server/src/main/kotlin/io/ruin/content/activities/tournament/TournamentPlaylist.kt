package io.ruin.content.activities.tournament

import io.ruin.content.activities.tournament.presets.*

/**
 * Different playlist types that a tournament session may use.
 * @author Heaven
 */
enum class TournamentPlaylist(val typeName: String, val attributes: TournamentAttributes) {
    DMM_TRIBRID("DMM Tribrid", DmmTribridTournamentAttributes),
    ZERK_HYBRID("Hybrid (45 Def)", ZerkNhTournamentAttributes),
    PURE_RANGED_MELEE("Ranged/Melee (1 Def)", PureRangedMeleeTournamentAttributes),
    DH("Dharok", DhMeleeTournamentAttributes),
    DH_RANGED("Dharok/Ranged", DhRangedTournamentAttributes);

    companion object {
        val VALUES = values()
    }
}