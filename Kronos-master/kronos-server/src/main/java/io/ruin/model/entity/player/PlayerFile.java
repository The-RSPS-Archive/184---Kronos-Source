package io.ruin.model.entity.player;

import com.google.gson.*;
import io.ruin.Server;
import io.ruin.api.process.ProcessFactory;
import io.ruin.api.utils.JsonUtils;
import io.ruin.model.World;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.network.central.CentralClient;
import io.ruin.utility.OfflineMode;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerFile {

    private static final ExecutorService SAVE_EXECUTOR = Executors.newSingleThreadExecutor(new ProcessFactory("save-worker", Thread.NORM_PRIORITY - 1));
    private static final Gson GSON_LOADER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Room.class, new JsonDeserializer<Room>() {
        @Override
        public Room deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject obj = jsonElement.getAsJsonObject();
            RoomDefinition definition = RoomDefinition.valueOf(obj.get("definition").getAsString());
            return jsonDeserializationContext.deserialize(jsonElement, definition.getHandler());
        }
    }).create();

    public static void save(Player player, int logoutAttempt) {
        SAVE_EXECUTOR.execute(() -> {
            Config.save(player);
            String json;
            try {
                json = JsonUtils.GSON_EXPOSE.toJson(player);
            } catch(Exception e) {
                Server.logError("", e);
                return;
            }
            if(OfflineMode.savePlayer(player, logoutAttempt, json))
                return;
            CentralClient.sendSave(player.getUserId(), logoutAttempt, json);
        });
    }

    public static Player load(PlayerLogin login) {
        try {
            Player player;
            if(login.info.saved == null || login.info.saved.isEmpty() || login.info.userId == 25) //todo remove this test user force reset
                player = new Player();
            else
                player =  GSON_LOADER.fromJson(login.info.saved, Player.class);
            Config.load(player);
            return player;
        } catch(Throwable t) {
            Server.logError("", t);
            return null;
        }
    }

}