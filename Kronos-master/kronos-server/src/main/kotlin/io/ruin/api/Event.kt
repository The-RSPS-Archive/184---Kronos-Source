package io.ruin.api

import io.ruin.event.GameEvent
import io.ruin.model.entity.npc.NPC
import io.ruin.model.entity.npc.NPCAction
import io.ruin.model.entity.player.Player
import io.ruin.model.inter.InterfaceHandler
import io.ruin.model.inter.actions.DefaultAction
import io.ruin.model.inter.actions.SimpleAction
import io.ruin.model.inter.actions.SlotAction
import io.ruin.model.item.Item
import io.ruin.model.item.actions.ItemAction
import io.ruin.model.item.actions.ItemItemAction
import io.ruin.model.item.actions.ItemNPCAction
import io.ruin.model.map.MapListener
import io.ruin.model.map.`object`.GameObject
import io.ruin.model.map.`object`.actions.ObjectAction
import io.ruin.model.map.dynamic.DynamicMap

typealias PlayerObjectEvent = (Player, GameObject) -> Unit
typealias PlayerNpcEvent = (Player, NPC) -> Unit
typealias PlayerItemEvent = (Player, Item) -> Unit
typealias ItemOnNpcEvent = (Player, Item, NPC) -> Unit

fun whenObjClick(id: Int, option: Int, action: PlayerObjectEvent) = ObjectAction.register(id, option, action)

fun whenObjClick(id: Int, option: String, action: PlayerObjectEvent) = ObjectAction.register(id, option, action)

fun whenObjClick(id: Int, x: Int, y: Int, height: Int, option: String, action: PlayerObjectEvent) = ObjectAction.register(id, x, y, height, option, action)

fun whenNpcClick(id: Int, option: Int, action: PlayerNpcEvent) = NPCAction.register(id, option, action)

fun whenButtonClick(parentId: Int, childId: Int, action: SimpleAction) {
    InterfaceHandler.register(parentId) {
        it.actions[childId] = action
    }
}

fun whenButtonClick(parentId: Int, childId: Int, action: DefaultAction) {
    InterfaceHandler.register(parentId) {
        it.actions[childId] = action
    }
}

fun whenButtonClick(parentId: Int, childId: Int, action: SlotAction) {
    InterfaceHandler.register(parentId) {
        it.actions[childId] = action
    }
}

fun whenRegionEntered(regionId: Int, action: (Player) -> Unit) = MapListener.registerRegion(regionId).onEnter(action)

fun whenRegionExit(regionId: Int, action: (Player, Boolean) -> Unit) = MapListener.registerRegion(regionId).onExit(action)

fun whenMapExit(map: DynamicMap, action: (Player, Boolean) -> Unit) = MapListener.registerMap(map).onExit(action)

fun globalEvent(fn: suspend GameEvent.() -> Unit) {
    val event = GameEvent(null, fn)
    event.start()
}

fun Player.event(fn: suspend GameEvent.() -> Unit) {
    resetActions(true, true, true)
    val event = GameEvent(this, fn)
    event.start()
}

fun NPC.event(fn: suspend GameEvent.() -> Unit) {
    val event = GameEvent(this, fn)
    event.start()
}

fun whenItemOnItem(id: Int, id2: Int, fn: ItemItemAction) = ItemItemAction.register(id, id2, fn)

fun whenItemOnNpc(id: Int, id2: Int, fn: ItemOnNpcEvent) = ItemNPCAction.register(id, id2, fn)

fun whenInvClick(id: Int, option: Int, fn: PlayerItemEvent) = ItemAction.registerInventory(id, option, fn)