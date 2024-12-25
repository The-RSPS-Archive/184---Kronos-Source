package io.ruin.model.item.attributes;

/**
 * Represents the name of a key in the attribute map.
 * Attribute keys aren't limited to just the ones defined here
 *
 * @author ReverendDread on 4/10/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public enum AttributeTypes {

    CHARGES, //Used for items that have charges.
    SPAWNED, //Used for items that have been spawned and can't be traded, sold or alched.
    FROM_SHOP,
    AMMO_ID,
    AMMO_AMOUNT,
    UPGRADE_1,
    UPGRADE_2,
    UPGRADE_3,
    AUGMENTED //Used for staff of the dead for autocasting ancients.

}
