package io.ruin.event

import io.ruin.Server
import io.ruin.event.GameEventProcessor.plusAssign
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Creates a new event using the worker executor service. Optionally, you may provide a context to run this event with.
 * @author Heaven
 */
class GameEvent(val ctx: Any?, val fn: suspend GameEvent.() -> Unit) : CoroutineScope by CoroutineScope(Server.worker.executor.asCoroutineDispatcher()) {

    /**
     * The amount of ticks, optionally, that this event is suspended for.
     */
    var ticks = -1

    /**
     * The [Continuation] reference for this [GameEvent].
     */
    private var continuation: Continuation<Any?>? = null

    /**
     * The [Job] that is dispatched with this [GameEvent].
     */
    private var job: Job? = null

    /**
     * A flag indicating if this [GameEvent] is actively waiting.
     */
    val waiting get() = continuation != null && job?.isActive == true

    /**
     * A flag indicating if this [GameEvent] is completed.
     */
    val finished get() = continuation == null && job?.isCompleted == true

    /**
     * Ticks this [GameEvent] delay until zero.
     */
    fun tick(): Boolean {
        if (ticks > 0) {
            ticks--
            return true
        }

        return false
    }

    /**
     * Ends this [GameEvent] by killing the parent job and all children recursively.
     */
    fun kill() {
        cancel()
    }

    /**
     * Pauses this [GameEvent] until told to resume.
     */
    suspend fun yield(): Any? {
        return suspendCoroutine { continuation = it }
    }

    /**
     * Pauses this [GameEvent] for the specified tick duration.
     */
    suspend fun pause(ticks: Int): Any? {
        this.ticks = ticks
        return suspendCoroutine { continuation = it }
    }

    /**
     * Resumes this [GameEvent] from its current suspension point.
     */
    fun resume(value: Any? = null): Boolean {
        if (continuation == null) return false

        val cont = continuation!!
        cont.resume(value)
        continuation = null
        return true
    }

    /**
     * Starts this [GameEvent].
     */
    fun start() {
        job = launch(GameEventContext(this)) { fn() }
        GameEventProcessor += this
    }
}