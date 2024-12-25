package io.ruin.util

import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

/**
 *
 * @project Kronos
 * @author ReverendDread on 7/7/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
fun <K, V> listenedMap(): ListenedMap<K, V> = ListenedMap()

class ListenedMap<K, V> : ConcurrentHashMap<K, V>() {

    val listeners = mutableMapOf<ListenerType, Consumer<V>>()

    @Override
    override fun put(key: K, value: V): V? {
        listeners[ListenerType.PRE_ADD]?.accept(value)
        val result = super.put(key, value)
        listeners[ListenerType.POST_ADD]?.accept(value)
        return result
    }

    @Override
    override fun remove(key: K, value: V): Boolean {
        listeners[ListenerType.PRE_REMOVE]?.accept(value)
        val result = super.remove(key, value)
        listeners[ListenerType.POST_REMOVE]?.accept(value);
        return result
    }

    enum class ListenerType {
        PRE_ADD, PRE_REMOVE, POST_ADD, POST_REMOVE
    }

    fun preAdd(listener: Consumer<V>) = listeners.put(ListenerType.PRE_ADD, listener)
    fun preRemove(listener: Consumer<V>) = listeners.put(ListenerType.PRE_REMOVE, listener)
    fun postAdd(listener: Consumer<V>) = listeners.put(ListenerType.POST_ADD, listener)
    fun postRemove(listener: Consumer<V>) = listeners.put(ListenerType.POST_REMOVE, listener)

}