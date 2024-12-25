package io.ruin.util

import java.util.concurrent.ConcurrentLinkedDeque
import java.util.function.Predicate
import java.util.stream.Collectors

/**
 *
 * @project Kronos
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
class CheckedConcurrentLinkedDeque<E>(val predicate: Predicate<E>) : ConcurrentLinkedDeque<E>() {

    override fun addFirst(e: E) {
        if (predicate.test(e))
        super.addFirst(e)
    }

    override fun offer(e: E): Boolean {
        if (predicate.test(e))
        return super.offer(e)
        else return false
    }

    override fun offerLast(e: E): Boolean {
        if (predicate.test(e))
        return super.offerLast(e)
        return false
    }

    override fun offerFirst(e: E): Boolean {
        if (predicate.test(e))
        return super.offerFirst(e)
        return false
    }

    override fun push(e: E) {
        if (predicate.test(e))
        super.push(e)
    }

    override fun addLast(e: E) {
        if (predicate.test(e))
        super.addLast(e)
    }

    override fun add(element: E): Boolean {
        if (predicate.test(element))
        return super.add(element)
        return false
    }

    override fun remove(element: E): Boolean {
        if (predicate.test(element))
        return super.remove(element)
        return false
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return super.addAll(elements.stream().filter(predicate).collect(Collectors.toList()))
    }
}