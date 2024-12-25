package io.ruin.event

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * A key & element pair that is used to retrieve the [GameEvent] reference from the [CoroutineContext] set.
 * @author Heaven
 */
class GameEventContext(val event: GameEvent) : AbstractCoroutineContextElement(GameEventContext) {
    companion object Key : CoroutineContext.Key<GameEventContext>
}