package io.ruin.model.entity.shared;

public enum LockType {

    // Default value
    NONE,

    // You can't move but can do other stuff
    MOVEMENT,

    // You can't do anything
    FULL,

    // when you teleport. any incoming damage is not applied at all.
    FULL_NULLIFY_DAMAGE,

    // when walking over a plank, or doing an agility obstacle. hits stack up and the last 2 are executed when the event finished
    FULL_DELAY_DAMAGE,

    // same as full, except player can logout
    FULL_ALLOW_LOGOUT,

    // Full, but hits are shown as normal
    FULL_REGULAR_DAMAGE,

    // Will stop entity from attacking if this lock is active
    FULL_CANT_ATTACK

}