package io.ruin.content.activities.event

/**
 *
 * Represents a Timed event implementation.
 *
 * @project Kronos
 * @author ReverendDread on 5/12/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
interface TimedEventImpl {

    fun onEventStart()

    fun onEventStopped()

    fun tick()

}