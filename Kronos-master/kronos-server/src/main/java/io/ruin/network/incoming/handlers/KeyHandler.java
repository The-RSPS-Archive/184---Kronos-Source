package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.incoming.Incoming;

//Ignoring for now.. @IdHolder(ids = {})
public class KeyHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        while(in.remaining() > 0) {
            in.skip(3); //time diff
            handleKey(player, in.readUnsignedByteC());
        }
    }

    private void handleKey(Player player, int key) {
        if(key >= 1 && key <= 13) {
            int tab = Config.KEYBINDS[key].get(player);
            player.sendMessage("  tab=" + tab);
            /* keybind keys */
        }
    }

}