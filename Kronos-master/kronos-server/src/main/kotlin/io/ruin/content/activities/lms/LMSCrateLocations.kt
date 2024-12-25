package io.ruin.content.activities.lms

import io.ruin.model.map.Position

enum class LMSCrateLocations(val tile: Position, val hint: String) {
    DEBTOR_HIDEOUT_NORTH(Position(3409, 5819, 0), "near the Debtor hideout"),
    DEBTOR_HIDEOUT_EAST(Position(3421, 5801, 0), "near the Debtor hideout"),
    MOUNTAIN(Position(3450, 5852, 0), "near the mountain"),
    MOSER_SETTLEMENT_NORTH(Position(3479, 5809, 0), "near the Moser settlement"),
    MOSER_SETTLEMENT_EAST(Position(3494, 5791, 0), "near the Moser settlement"),
    MOSER_SETTLEMENT_WEST(Position(3459, 5789, 0), "near the Moser settlement"),
    ROCKY_OUTPOST(Position(3459, 5772, 0), "near the Rocky outcrop"),
    TRINITY_OUTPOST(Position(3480, 5876, 0), "near the Trinity outpost")
}