package io.ruin.utility;

import io.ruin.model.entity.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ReverendDread on 7/18/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@RequiredArgsConstructor @Getter
public enum CS2Script {

    // party leader ? 2 : 1, name|combatLevel|att|str|ranged|magic|def|hp|prayer|completedTheatres
    TOB_PARTYDETAILS_ADDMEMBER(2317, "is"),
    //name|combatLevel|att|str|ranged|magic|def|hp|prayer|completedTheatres
    TOB_PARTYDETAILS_ADDAPPLICANT(2321, "s"),
    //slot, name|memberSize|preferredSize|preferredCombatLevel
    TOB_PARTYLIST_ADDLINE(2340, "is"),
    //leader ? 2 : isMember ? 1 : isApplicant ? 3 : 4
    TOB_PARTYDETAILS_REFRESH(2323, "iii"),
    //name|name|name|name|name
    TOB_HUD_STATUSNAMES(2301, "sssss"),
    //
    TOB_HUD_FADE(2306, "iis"),
    //
    TOB_HUD_PORTAL(2307, "s"),
    //color transparency
    TOPLEVEL_MAINMODAL_OPEN(2524, "ii"),

    ;

    private final int scriptId;
    private final String argTypes;

    public void sendScript(Player player, Object... values) {
        player.getPacketSender().sendClientScript(getScriptId(), getArgTypes(), values);
    }

    @Override
    public String toString() {
        return "CS2Script{" +
                "scriptId=" + scriptId +
                ", argTypes='" + argTypes + '\'' +
                '}';
    }

}
