package io.ruin.services.livedata.handler.impl;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.services.livedata.handler.LiveDataUpdateHandler;
import io.ruin.services.livedata.handler.Parameters;

public class GameModeUpdateHandler extends LiveDataUpdateHandler {
//    @Override
//    public String path() {
//        return "/players/gamemode/";
//    }
//
//    @Parameters
//    class Params {
//        int user_id;
//        int game_mode;
//    }
//
//    public String handle(Params params) {
//        Player player = World.getPlayer(params.user_id, true);
//        if (player == null) {
//                return "Error: Player not online";
//        }
//        if (params.game_mode < 0 || params.game_mode >= GameMode.values().length) {
//            return "Error: Invalid game mode";
//        }
//        Config.IRONMAN_MODE.set(player, params.game_mode);
//        if(params.game_mode == 0)
//             GameMode.changeForumsGroup(player, GameMode.STANDARD.groupId);
//        else if(params.game_mode == 1)
//            GameMode.changeForumsGroup(player, GameMode.IRONMAN.groupId);
//        else if(params.game_mode == 2)
//            GameMode.changeForumsGroup(player, GameMode.ULTIMATE_IRONMAN.groupId);
//        else if(params.game_mode == 3)
//            GameMode.changeForumsGroup(player, GameMode.HARDCORE_IRONMAN.groupId);
//
//        player.sendMessage(Color.RED.wrap("An administrator has changed your game mode to: " + player.getGameMode() + "."));
//        return "OK";
//    }
}
