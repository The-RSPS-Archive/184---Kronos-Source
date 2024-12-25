package io.ruin.util

/**
 *
 * @project Kronos
 * @author ReverendDread on 3/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
interface EventListener<T> {
    fun run(consumer: () -> Unit)
}