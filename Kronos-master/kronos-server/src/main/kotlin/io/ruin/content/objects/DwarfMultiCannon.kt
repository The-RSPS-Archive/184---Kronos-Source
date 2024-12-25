package io.ruin.content.objects

import io.ruin.api.*
import io.ruin.model.combat.AttackStyle
import io.ruin.model.combat.AttackType
import io.ruin.model.combat.Hit
import io.ruin.model.entity.player.Player
import io.ruin.model.entity.shared.listeners.LoginListener
import io.ruin.model.item.Item
import io.ruin.model.map.Bounds
import io.ruin.model.map.Position
import io.ruin.model.map.Projectile
import io.ruin.model.map.Tile
import io.ruin.model.map.`object`.GameObject

object DwarfMultiCannon {
    private val BASE = Item(6, 1)
    private val STAND = Item(8, 1)
    private val BARREL = Item(10, 1)
    private val FURNACE = Item(12, 1)

    init {
//        whenInvClick(BASE.id, 1) { player, _ ->
//            if (player.preRequirements())
//                player.setup()
//        }
//
//        whenObjClick(6, "fire") { player, obj ->
//            val cannon = obj as Cannon
//            if (player.userId != cannon.userId) {
//                player.message("This is cannon does not belong to you.")
//                return@whenObjClick
//            }
//            if (player.cannon == null) {
//                obj.remove()
//                player.message("Your cannon has been placed in your inventory or dropped.")
//                return@whenObjClick
//            }
//            player.fire()
//        }
//
//        whenObjClick(6, "pick-up") { player, obj ->
//            val cannon = obj as Cannon
//            if (player.userId != cannon.userId) {
//                player.message("This is cannon does not belong to you.")
//                return@whenObjClick
//            }
//            player.pickup()
//        }
//
//        whenObjClick(6, "empty") { player, obj ->
//            val cannon = obj as Cannon
//            if (player.userId != cannon.userId) {
//                player.message("This is cannon does not belong to you.")
//                return@whenObjClick
//            }
//            player.empty()
//        }
//
//
//        LoginListener.register { player ->
//            val cannon = player.cannon
//            if (cannon != null && !cannon.isSpawned) {
//                if (player.cannonPosition != null) {
//                    Tile.getObject(6, player.cannonPosition.x, player.cannonPosition.y, player.cannonPosition.z)?.remove()
//                    if (player.cannonBallsLoaded > 0)
//                        player.inventory.addOrDrop(Item(2, player.cannonBallsLoaded))
//                    player.inventory.addOrDrop(BASE)
//                    player.inventory.addOrDrop(STAND)
//                    player.inventory.addOrDrop(BARREL)
//                    player.inventory.addOrDrop(FURNACE)
//                }
//                player.cannon = null
//            }
//        }
    }

    fun Player.fire() {
        if (!cannon.firing) {
            cannon.firing = true
            reload()
            beginFiring(this)
        } else {
            reload()
        }

    }

    private var direction = Direction.NORTH
    private const val CANNON_BALL_GFX = 53


    private fun beginFiring(player: Player) {
        val cannon = player.cannon
        globalEvent {
            while (cannon != null && cannon.loaded > 0 && cannon.firing) {
                if (!player.isOnline) {
                    cannon.remove()
                    return@globalEvent
                }
                direction = if (direction.ordinal == Direction.values.size - 1)
                    Direction.values[0]
                else
                    Direction.values[direction.ordinal + 1]
                cannon.animate(direction.animation)

                shoot(player)
                pause(1)
            }
            player.message("Your cannon has ran out of cannonballs.")
            cannon.firing = false
        }
    }

    private fun shoot(player: Player) {
        player.localNpcs().forEach { npc ->

            if (npc == null || npc.combat == null || npc.combat.isDead)
                return@forEach

            if (player.cannon.loaded < 1)
                return@forEach

            if (!player.combat.multiCheck(npc, false) && (player.combat.isDefending(6) || player.combat.isAttacking(6)))
                return@forEach

            val distance = player.cannon.pos.distance(npc.position)
            if (distance in 1..10) {
                val myX = player.cannon.pos.x
                val myY = player.cannon.pos.y
                val theirX = npc.position.x
                val theirY = npc.position.y
                var canAttack = false
                when (direction) {
                    Direction.NORTH -> if (theirY > myY && theirX >= myX - 1 && theirX <= myX + 1)
                        canAttack = true
                    Direction.NORTH_EAST -> if (theirX >= myX + 1 && theirY >= myY + 1)
                        canAttack = true
                    Direction.EAST -> if (theirX > myX && theirY >= myY - 1 && theirY <= myY + 1)
                        canAttack = true
                    Direction.SOUTH_EAST -> if (theirY <= myY - 1 && theirX >= myX + 1)
                        canAttack = true
                    Direction.SOUTH -> if (theirY < myY && theirX >= myX - 1 && theirX <= myX + 1)
                        canAttack = true
                    Direction.SOUTH_WEST -> if (theirX <= myX - 1 && theirY <= myY - 1)
                        canAttack = true
                    Direction.WEST -> if (theirX < myX && theirY >= myY - 1 && theirY <= myY + 1)
                        canAttack = true
                    Direction.NORTH_WEST -> if (theirX <= myX - 1 && theirY >= myY + 1)
                        canAttack = true
                }

                if (canAttack) {
                    player.cannon.loaded--
                    player.cannonBallsLoaded--
                    val delay = 2
                    val projectile = Projectile(CANNON_BALL_GFX, 37, 37, 15 + (delay * 10), 50, 10, 0, 96)
                    val projectileDelay = projectile.send(player.cannonPosition, npc.position)
                    val damage = random(30)
                    npc.hit(Hit(player, AttackStyle.RANGED, AttackType.ACCURATE).randDamage(damage).clientDelay(projectileDelay))
                }
            }
        }
    }

    private fun Player.reload() {
        val loaded = cannon.loaded
        val balls = inventory.findItem(2)
        if (balls != null) {
            if (loaded < 30) {
                if (balls.amount + loaded > 30) {
                    val remainder = 30 - loaded
                    cannon.loaded += remainder
                    cannonBallsLoaded = cannon.loaded
                    balls.remove(remainder)
                    message("You load the cannon with $remainder cannonballs.")
                } else {
                    message("You load the cannon with ${balls.amount} cannonballs.")
                    cannon.loaded += balls.amount
                    cannonBallsLoaded = cannon.loaded
                    balls.remove(balls.amount)
                }
            } else {
                message("Your cannon is already full!")
            }
        }
    }

    private fun Player.empty() {
        if (!inventory.hasId(2) && !inventory.hasFreeSlots(1)) {
            player.message("You don't have enough space in your inventory.")
            return
        }

        if (cannon.loaded == 0) {
            player.message("Your cannon is already empty.")
            return
        }

        inventory.add(Item(2, cannon.loaded))
        cannonBallsLoaded = 0
        cannon.loaded = 0
    }

    private fun Player.pickup() {
        if (!inventory.hasFreeSlots(4)) {
            player.message("You don't have enough space in your inventory.")
            return
        }

        if (cannon.loaded > 0 && !inventory.hasFreeSlots(5)) {
            player.message("You don't have enough space in your inventory.")
            return
        }

        player.message("You pick up the cannon")

        cannon.firing = false
        if (cannon.loaded > 0)
            inventory.add(Item(2, cannon.loaded))
        inventory.add(BASE)
        inventory.add(STAND)
        inventory.add(BARREL)
        inventory.add(FURNACE)
        cannon.remove()
        cannonBallsLoaded = 0
        cannonPosition = null
        cannon = null

    }

    fun Player.setup() {
        combat.reset()
        event {
            lock()

            val pos = position.copy().translate(0, 1)
            animate(827)
            inventory.remove(BASE)
            message("You place the cannon base on the ground.")
            pause(2)
            val base = GameObject(7, pos, 10, 1).spawn()
            face(base)

            animate(827)
            inventory.remove(STAND)
            message("You add the stand.")
            pause(2)
            base.remove()
            val stand = GameObject(8, pos, 10, 1).spawn()

            animate(827)
            inventory.remove(BARREL)
            message("You add the barrels.")
            pause(2)
            stand.remove()
            val barrel = GameObject(9, pos, 10, 1).spawn()

            animate(827)
            inventory.remove(FURNACE)
            message("You add the furnace.")
            pause(2)

            barrel.remove()
            val newCannon = Cannon(6, pos, 10, 1)
            newCannon.spawn()
            cannonPosition = pos
            cannon = newCannon
            cannon.userId = userId

            val balls = inventory.findItem(2)
            if (balls != null) {
                if (balls.amount >= 30) {
                    balls.remove(30)
                    message("You load the cannon with 30 cannonballs.")
                    cannon.loaded += 30
                    cannonBallsLoaded = cannon.loaded
                } else {
                    message("You load the cannon with ${balls.amount} cannonballs.")
                    cannon.loaded += balls.amount
                    cannonBallsLoaded = cannon.loaded
                    balls.remove(balls.amount)
                }
            }
            player.onLogoutAttempt {
                if (cannon != null) {
                    message("Please pick up your cannon before doing this!")
                    false
                } else {
                    true
                }
            }
            unlock()
        }
    }
    private val EDGEVILLE = Bounds(3036, 3478, 3144, 3524, -1)
    private val HOME = Bounds(1986, 3545, 2047, 3641, -1)

    private fun Player.preRequirements(): Boolean {
        if (!inventory.containsAll(false, BASE, STAND, BARREL, FURNACE)) {
            message("You don't have all the cannon components!")
            return false
        }
        if(combat.isDefending(6) || combat.isAttacking(6)) {
            message("You cannot place your cannon while in combat")
        }
        if (cannon != null) {
            message("You cannot construct more than one cannon at a time.")
            return false
        }
        if (!Tile.allowObjectPlacement(player.position) || player.position.inBounds(EDGEVILLE) || player.position.inBounds(HOME)) {
            message("You can't place a cannon here.")
            return false
        }
        return true
    }

}

class Cannon(val id: Int, val pos: Position, val type: Int, private val rot: Int) : GameObject(id, pos, type, rot) {
    var loaded = 0
    var firing = false
    var userId = -1
}

private enum class Direction(internal val animation: Int) {
    NORTH(515),

    NORTH_EAST(516),

    EAST(517),

    SOUTH_EAST(518),

    SOUTH(519),

    SOUTH_WEST(520),

    WEST(521),

    NORTH_WEST(514);


    companion object {
        var values = values()
    }
}