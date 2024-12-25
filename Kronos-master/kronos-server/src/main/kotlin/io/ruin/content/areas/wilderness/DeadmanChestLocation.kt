package io.ruin.content.areas.wilderness

import io.ruin.model.map.Position

/**
 * Location definitions that a [DeadmanChest] may appear.
 * @author Heaven
 */
enum class DeadmanChestLocation(val hint: String, val tile: Position) {
    GDZ("near Greater Demons (multi)", Position(3276, 3886, 0)),
    REVS_MULTI("east of Revenants lair (multi)", Position(3160, 3848, 0)),
    REVS("near Revenants lair", Position(3136, 3837, 0)),
    KBD_LAIR("near KBD lair", Position(3028, 3840, 0))
}