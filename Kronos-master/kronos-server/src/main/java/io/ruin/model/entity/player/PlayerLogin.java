package io.ruin.model.entity.player;

import io.ruin.Server;
import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.protocol.login.LoginRequest;
import io.ruin.data.impl.login_set;
import io.ruin.model.World;
import io.ruin.network.central.CentralClient;
import io.ruin.utility.OfflineMode;
import io.ruin.utility.PlayerRestore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerLogin extends LoginRequest {

    private static final boolean[] LOADING = new boolean[World.players.entityList.length];

    public PlayerLogin(LoginInfo info) {
        super(info);
        send();
    }

    private void send() {
        if(OfflineMode.loadPlayer(this))
            return;
        CentralClient.sendLogin(info.ipAddress, info.name, info.password, info.email, info.macAddress, info.uuid, info.tfaCode, info.tfaTrust, info.tfaTrustValue & 0xff);
    }

    @Override
    public void success() {
        if(login_set.deny(this))
            return;
        Server.worker.execute(this::accept);
    }

    private void accept() {
        if(World.updating) {
            deny(Response.UPDATING);
            return;
        }
        int index = -1;
        for(int i = 1; i < World.players.entityList.length; i++) {
            Player player = World.players.entityList[i];
            if(player == null) {
                if(index == -1 && !LOADING[i])
                    index = i;
                continue;
            }
            if(player.getUserId() == info.userId) {
                super.deny(Response.ALREADY_LOGGED_IN);
                Server.logWarning(player.getName() + " is already online!");
                return;
            }
        }
        if(index == -1) {
            deny(Response.WORLD_FULL);
            return;
        }
        load(index);
    }

    private void load(int index) {
        LOADING[index] = true;
        Server.worker.execute(() -> PlayerFile.load(this), player -> {

            if (player == null) {
                deny(Response.ERROR_LOADING_ACCOUNT);
                return;
            }

            player.setIndex(index);
            player.init(info);

            //reconstruct players if their before the char fuck up
            //PlayerRestore.reconstructPlayer(player);

            World.players.set(index, player);
            LOADING[index] = false;

            player.getPacketSender().sendLogin(info);
            player.getChannel().pipeline().replace("decoder", "decoder", player.getDecoder());
        });
    }

    @Override
    public void deny(Response response) {
        super.deny(response);
        CentralClient.sendLogout(info.userId);
    }

}