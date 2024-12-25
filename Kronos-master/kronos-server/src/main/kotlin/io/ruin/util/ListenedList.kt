package io.ruin.util

import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Consumer

fun <T> listenedList(): ListenedList<T> = ListenedList()

/**
 * A CopyOnWriteArrayList backed list, that has optional consumers for when elements are added or removed from the list.
 *
 * @project Kronos
 * @author ReverendDread on 3/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
class ListenedList<T> : CopyOnWriteArrayList<T>() {

    val listeners = mutableMapOf<ListenerType, Consumer<T>>()

    @Override
    override fun add(element: T) : Boolean {
        if (contains(element)) {
            println("${element?.toString()} already exists")
            return false
        }
        listeners[ListenerType.PRE_ADD]?.accept(element)
        val result = super.add(element);
        listeners[ListenerType.POST_ADD]?.accept(element)
        return result;
    }

    @Override
    override fun remove(element: T): Boolean {
        if (!contains(element)) {
            println("${element?.toString()} doesn't exist")
            return false
        }
        listeners[ListenerType.PRE_REMOVE]?.accept(element)
        val result = super.remove(element)
        listeners[ListenerType.POST_REMOVE]?.accept(element)
        return result
    }

    enum class ListenerType {
        PRE_ADD, POST_ADD, PRE_REMOVE, POST_REMOVE
    }

    fun preAdd(listener: Consumer<T>) = listeners.put(ListenerType.PRE_ADD, listener)
    fun preRemove(listener: Consumer<T>) = listeners.put(ListenerType.PRE_REMOVE, listener)
    fun postAdd(listener: Consumer<T>) = listeners.put(ListenerType.POST_ADD, listener)
    fun postRemove(listener: Consumer<T>) = listeners.put(ListenerType.POST_REMOVE, listener)

}