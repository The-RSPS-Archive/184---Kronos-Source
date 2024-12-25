package io.ruin.model.map;

import lombok.Builder;
import lombok.Getter;

/**
 * @author ReverendDread on 6/15/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Builder @Getter
public class Graphic {

    private int id = -1;
    private int height;
    private int delay;

    private int soundId = -1, soundType = 1, soundDelay;

}
