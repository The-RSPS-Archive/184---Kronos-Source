package io.ruin.model.activities.raids.tob.dungeon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@RequiredArgsConstructor @Getter
public enum RoomType {

    MAIDEN("The Maiden of Sanguinesti"),
    BLOAT("Pestulent Bloat"),
    VASILIAS("Nylocas Vasilias"),
    SOTETSEG("Sotetseg"),
    XARPUS("Xarpus"),
    VERZIK("Verzik Vitur"),
    TREASURE("Verzik's Treasure Room");

    private final String portalText;

}
