package io.ruin.content.activities.lms

import io.ruin.api.*
import io.ruin.model.entity.player.Player
import io.ruin.model.entity.shared.LockType
import io.ruin.model.map.Position
import io.ruin.model.map.`object`.GameObject

/**
 * The manager class for Last Man Standing. Any relevant action listeners or other fields pertaining to LMS activity
 * can be defined and accessed here.
 * @author Heaven
 */
object LastManStanding {

    /**
     * The id of the bloody key item.
     */
    const val BLOODY_KEY = 20526

    /**
     * The id of the bloodier key item.
     */
    const val BLOODIER_KEY = 20608

    /**
     * The id of the loot crate object.
     */
    const val LOOT_CRATE_ID = 29081

    init {
        whenRegionEntered(13617) {
            it.openPrimaryOverlay(333)
        }
        whenRegionExit(13617) { player, _ ->
            if (player.lmsSession == null) {
                if (player.lmsQueue != null) {
                    player.lmsQueue.remove(player)
                }
                player.closePrimaryOverlay()
            }
        }
        whenObjClick(29067, 1) { player, obj ->
            if (obj.x == 3410 && obj.y == 3184 && player.absX > 3410) {
                if (player.lmsQueue != null) {
                    player.event {
                        if (player.options("Leave queue.", "Stay in queue.") == 1) {
                            if (player.lmsQueue != null) {
                                player.lmsQueue.remove(player)
                                player.teleport(3410, 3184, 0)
                            }
                        }
                    }
                } else {
                    if (player.lmsSession == null) {
                        player.teleport(3410, 3184, 0)
                    }
                }
            }
        }
        whenObjClick(29069, 1) { player, _ ->
            if (player.lmsSession != null) {
                player.lmsSession.exchangeKey(player)
            }
        }
        whenObjClick(LOOT_CRATE_ID, "search") { player, _ ->
            if (player.lmsSession != null && player.lmsSession.crate != null) {
                player.lmsSession.crate?.loot(player)
            }
        }
        whenObjClick(29092,"climb") { player, gameObject ->
            if (player.lmsSession != null) {
                val down = when(gameObject.direction) {
                    0 -> player.absY < gameObject.y
                    2 -> player.absY > gameObject.y
                    3 -> player.absX > gameObject.x
                    else -> true

                }
                player.climbLadder(gameObject, down)
            }
        }
    }

    val ARENA_SPAWNS = arrayOf(
            Position(3459, 5836, 0),
            Position(3454, 5849, 0),
            Position(3462, 5844, 0),
            Position(3465, 5878, 0),
            Position(3469, 5870, 0),
            Position(3463, 5863, 0),
            Position(3437, 5868, 0),
            Position(3432, 5873, 0),
            Position(3427, 5878, 0),
            Position(3410, 5863, 0),
            Position(3399, 5864, 0),
            Position(3398, 5883, 0),
            Position(3407, 5883, 0),
            Position(3407, 5845, 0),
            Position(3402, 5839, 0),
            Position(3403, 5831, 0),
            Position(3410, 5834, 0),
            Position(3412, 5839, 0),
            Position(3421, 5827, 0),
            Position(3415, 5822, 0),
            Position(3405, 5825, 0),
            Position(3397, 5821, 0),
            Position(3423, 5821, 0),
            Position(3430, 5822, 0),
            Position(3440, 5819, 0),
            Position(3439, 5809, 0),
            Position(3434, 5801, 0),
            Position(3448, 5800, 0),
            Position(3454, 5803, 0),
            Position(3454, 5809, 0),
            Position(3451, 5821, 0),
            Position(3426, 5791, 0),
            Position(3420, 5791, 0),
            Position(3416, 5784, 0),
            Position(3426, 5782, 0),
            Position(3416, 5776, 0),
            Position(3407, 5781, 0),
            Position(3399, 5780, 0),
            Position(3439, 5820, 0),
            Position(3497, 5832, 0),
            Position(3501, 5823, 0),
            Position(3504, 5838, 0),
            Position(3497, 5850, 0),
            Position(3506, 5856, 0),
            Position(3501, 5866, 0),
            Position(3491, 5873, 0),
            Position(3487, 5870, 0),
            Position(3508, 5885, 0),
            Position(3499, 5872, 0),
            Position(3499, 5872, 0),
            Position(3493, 5870, 0)
    )

    val WEAPON_UPGRADES = arrayOf(
            13652, 22324, 4153, 21006, 11785, 11802, 21003, 22296
    )

    val CAPE_UPGRADES = arrayOf(
            21295
    )

    val CHEST_UPGRADES = arrayOf(
            10551, 11832
    )

    val LEG_UPGRADES = arrayOf(
         11834
    )

    val BOOT_UPGRADES = arrayOf(
            13235, 13239, 13237, 6920
    )

    val COMMON_LOOT_TABLE = arrayOf(
            4708, 4712, 4714, 4716, 4718, 4720, 4722, 4736, 4738, 4153
    )

    val POTIONS = arrayOf(
            6685, 3024, 3024, 2444, 12695, 12625
    )

    fun randomLobbyTile() = Position(3402 + random(3), 3172 + random(3), 0)

    private fun Player.climbLadder(ladder: GameObject, down: Boolean) = event {
        var targetX = ladder.x
        var targetY = ladder.y
        lock(LockType.FULL_DELAY_DAMAGE)
        animate(if (down) 827 else 828)
        pause(1)
        if (down) {
           when {
               ladder.direction == 0 -> {
                   targetY += 1
               }
               ladder.direction == 2 -> {
                   targetY += -1
               }
               ladder.direction == 3 -> {
                   targetX += -1
               }
           }
        } else {
            when {
                ladder.direction == 0 -> {
                    targetY += -1
                }
                ladder.direction == 2 -> {
                    targetY += 1
                }
                ladder.direction == 3 -> {
                    targetX += 1
                }
            }
        }

        movement.teleport(targetX, targetY)
        unlock()
    }
}