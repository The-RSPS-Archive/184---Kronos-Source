package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;

import java.util.Arrays;

@IdHolder(ids={51, 6, 42, 95, 50, 36 })
public class ObjectActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if(player.isLocked())
            return;
        player.resetActions(true, true, true);
        int option = OPTIONS[opcode];
        if(option == 1) {
            int ctrlRun = in.readByteC();
            int id = in.readUnsignedShort();
            int y = in.readShortA();
            int x = in.readLEShortA();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 2) {
            int id = in.readUnsignedLEShortA();
            int x = in.readShort();
            int ctrlRun = in.readByteS();
            int y = in.readShortA();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 3) {
            int id = in.readUnsignedShort();
            int x = in.readShortA();
            int y = in.readLEShortA();
            int ctrlRun = in.readByteA();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 4) {
            int x = in.readShort();
            int ctrlRun = in.readByteC();
            int id = in.readUnsignedShortA();
            int y = in.readShortA();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 5) {
            int y = in.readShort();
            int x = in.readShort();
            int id = in.readUnsignedShort();
            int ctrlRun = in.readByteA();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 6) {
            int id = in.readLEShort();
            ObjectDef def = ObjectDef.get(id);
            if(def != null) {
                if(player.debug) {
                    DebugMessage debug = new DebugMessage()
                            .add("id", id)
                            .add("name", def.name)
                            .add("options", Arrays.toString(def.options))
                            .add("varpbitId", def.varpBitId)
                            .add("varpId", def.varpId);
                    player.sendFilteredMessage("[ObjectAction] " + debug.toString());
                }
            }
            return;
        }
        player.sendFilteredMessage("Unhandled object action: option=" + option + " opcode=" + opcode);
    }

    public static void handleAction(Player player, int option, int objectId, int objectX, int objectY, int ctrlRun) {
        if(objectId == -1)
            return;
        GameObject gameObject = Tile.getObject(objectId, objectX, objectY, player.getPosition().getZ());
        if(gameObject == null)
            return;
        if(player.debug) {
            DebugMessage debug = new DebugMessage()
                    .add("option", option)
                    .add("id", gameObject.id)
                    .add("name", gameObject.getDef().name)
                    .add("x", gameObject.x)
                    .add("y", gameObject.y)
                    .add("z", gameObject.z)
                    .add("type", gameObject.type)
                    .add("direction", gameObject.direction)
                    .add("options", Arrays.toString(gameObject.getDef().options))
                    .add("varpbitId", gameObject.getDef().varpBitId)
                    .add("varpId", gameObject.getDef().varpId);
            player.sendFilteredMessage("[ObjectAction] " + debug.toString());
        }
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeObject(gameObject, () -> {
            player.getPacketSender().resetMapFlag();
            int i = option - 1;
            if(i < 0 || i >= 5)
                return;
            ObjectAction action = null;
            ObjectAction[] actions = gameObject.actions;
            if(actions != null)
                action = actions[i];
            if(action == null && (actions = gameObject.getDef().defaultActions) != null)
                action = actions[i];
            if(action != null) {
                action.handle(player, gameObject);
                return;
            }
            player.sendMessage("Nothing interesting happens.");
        });
    }

}