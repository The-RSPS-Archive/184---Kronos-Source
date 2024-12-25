package io.ruin.content.activities.event.impl.eventboss

import io.ruin.api.globalEvent
import io.ruin.api.utils.Random
import io.ruin.cache.Icon
import io.ruin.content.activities.event.TimedEventImpl
import io.ruin.model.combat.Hit
import io.ruin.model.combat.HitType
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.shared.listeners.DeathListener
import io.ruin.model.map.ground.GroundItem
import io.ruin.services.discord.impl.EventBossEmbedMessage
import io.ruin.utility.Broadcast
import io.ruin.utility.OfflineMode

/**
 *
 * @project Kronos
 * @author ReverendDread on 5/12/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
class EventBoss(val boss: EventBossType) : TimedEventImpl {

    val npc = NPC(boss.id)

    override fun onEventStart() {
        npc.hp = boss.hitpoints
        val random = Random.get(boss.positions.size - 1);
        npc.spawn(boss.positions[random])
        npc.setIgnoreMulti(true)
        npc.combat.setAllowRespawn(false)
        globalEvent { Broadcast.WORLD_NOTIFICATION.sendNews(Icon.BLUE_INFO_BADGE, boss.message[random]) }
        if (!OfflineMode.enabled) {
            EventBossEmbedMessage.sendDiscordMessage(boss, boss.message[random])
        }
        npc.deathEndListener = DeathListener { entity, _, killHit ->
            entity.combat.killers.forEach { (_, killer) ->
                if (killer.damage >= 100) {
                    boss.lootTable.guaranteed.forEach { GroundItem(it.id, Random.get(it.min, it.max), it.attributes).position(npc.position).owner(killer.player).spawn() }
                    repeat(boss.rolls) {
                        val rolled = boss.lootTable.rollItem()
                        GroundItem(rolled.id, rolled.amount, rolled.copyOfAttributes()).position(npc.position).owner(killer.player).spawn()
                    }
                    if (Random.rollDie(boss.petChance)) {
                        boss.pet.unlock(killer.player)
                    }
                }
            }
            globalEvent { Broadcast.WORLD_NOTIFICATION.sendNews(Icon.BLUE_INFO_BADGE, "${npc.def.name} has been defeated!") }
        }
    }

    override fun onEventStopped() {
        npc.remove()
        globalEvent { Broadcast.WORLD_NOTIFICATION.sendNews(Icon.BLUE_INFO_BADGE, "${npc.def.name} has retreated...") }
    }

    override fun tick() {
        if (!npc.isRemoved && !npc.dead()) {
            if (npc.localPlayers().isEmpty()) {
                npc.hit(Hit(HitType.HEAL).fixedDamage(50))
            }
        }
    }

}