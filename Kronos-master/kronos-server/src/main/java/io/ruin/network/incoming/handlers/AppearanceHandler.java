package io.ruin.network.incoming.handlers;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.IdentityKit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.masks.Appearance;
import io.ruin.model.inter.InterfaceType;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {44})
public class AppearanceHandler implements Incoming {

    private static final short[][] COLORS = {{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574, 17050, 0, 127}};

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        boolean female = (in.readByte() == 1);
        Appearance app = player.getAppearance();
        app.setGender(female ? 1 : 0);
        for(int i = 0; i < 7; i++) {
            int style = in.readUnsignedByte();
            if(i == 1 && female) {
                /**
                 * Female jaw
                 */
                style = 255;
            } else {
                IdentityKit identitykit = IdentityKit.get(style);
                if(identitykit == null || identitykit.aBool53 || identitykit.anInt405 != (female ? 7 : 0) + i) {
                    Server.logWarning("Invalid Appearance Style: [" + i + "]=" + style + " player=" + player.getName());
                    continue;
                }
            }
            app.styles[i] = style;
        }
        for(int i = 0; i < 5; i++) {
            int color = in.readUnsignedByte();
            if(app.colors[i] == color) { //allows custom skins
                /* already this color, no checks required */
                continue;
            }
            if(color < 0 || color >= COLORS[i].length) {
                Server.logWarning("Invalid Appearance Color: [" + i + "]=" + color + " player=" + player.getName());
                color = 0;
            }
            app.colors[i] = color;
        }
        player.closeInterface(InterfaceType.MAIN);
        app.update();
    }

}