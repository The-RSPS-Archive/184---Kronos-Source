package io.ruin.process.event;

/**
 * Introduces event types to allow for defining and handling more case-specific events/mechanics.
 * @author Heaven
 */
public enum EventType {
	// Ensures the event finishes until the end. You cannot cancel this event type.
	PERSISTENT,

	// Attempt to finish the event however, may be cancelled at any point in time.
	DEFAULT
}
