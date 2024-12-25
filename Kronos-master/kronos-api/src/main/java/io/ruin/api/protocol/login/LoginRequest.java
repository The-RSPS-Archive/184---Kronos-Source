package io.ruin.api.protocol.login;

import io.ruin.api.protocol.Response;

public abstract class LoginRequest {

    public final LoginInfo info;

    public LoginRequest(LoginInfo info) {
        this.info = info;
    }

    public void deny(Response response) {
        if(info.channel != null)
            response.send(info.channel);
    }

    public abstract void success();

}