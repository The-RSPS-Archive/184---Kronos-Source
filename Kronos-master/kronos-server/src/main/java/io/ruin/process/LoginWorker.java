package io.ruin.process;

import io.ruin.Server;
import io.ruin.api.process.ProcessWorker;
import io.ruin.api.protocol.login.LoginRequest;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class LoginWorker {

    private static ConcurrentHashMap<String, LoginRequest> requests;

    public static void start() {
        if(requests != null)
            throw new RuntimeException("Login Worker already started!");
        requests = new ConcurrentHashMap<>();

        ProcessWorker worker = Server.newWorker("login-worker", 1000L, Thread.MIN_PRIORITY);
        worker.queue(() -> {
            Collection<LoginRequest> values = requests.values();
            if(!values.isEmpty())
                values.removeIf(r -> !r.info.channel.isActive());
            return false;
        });
    }

    public static void add(LoginRequest request) {
        requests.put(request.info.name.toLowerCase(), request);
    }

    public static LoginRequest get(String name) {
        return requests.get(name.toLowerCase());
    }

}