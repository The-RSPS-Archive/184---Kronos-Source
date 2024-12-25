package io.ruin.model.activities.pvminstances;

public enum InstancePrivacy {
    /**
     *  Anyone can join
     */
    PUBLIC,

    /**
     * Must input correct password to join
     */
    PASSWORD,

    /**
     * Only the owner can join
     */
    PRIVATE
}
