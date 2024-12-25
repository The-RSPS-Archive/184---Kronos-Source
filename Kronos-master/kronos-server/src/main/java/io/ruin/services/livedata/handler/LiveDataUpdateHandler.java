package io.ruin.services.livedata.handler;

import com.google.gson.Gson;
import io.ruin.Server;
import io.ruin.services.livedata.LiveData;
import io.undertow.server.HttpHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Handles requests from the ACP to UPDATE data in the server.
 * If the request only looks up data and doesn't modify it, there is no need to use this class!
 */
public abstract class LiveDataUpdateHandler {


//    public abstract String path();
//
//    public LiveDataUpdateHandler() {
//        paramClass = getDataClass();
//    }
//
//    private Class<?> paramClass;
//
//    private Class<?> getDataClass() {
//        return Arrays.
//                stream(this.getClass().getDeclaredClasses())
//                .filter(c -> c.isAnnotationPresent(Parameters.class))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Handler has no Parameters class"));
//    }
//
//    public HttpHandler getHttpHandler() {
//        return LiveData.authHandler(httpServerExchange -> {
//            httpServerExchange.getRequestReceiver().receiveFullString((exchange, s) -> {
//                Object params = new Gson().fromJson(s, paramClass);
//                exchange.dispatch(Server.worker.getExecutor(), () -> {
//                    try {
//                        String response = (String) this.getClass().getMethod("handle", paramClass).invoke(this, params);
//                        exchange.setStatusCode(200);
//                        exchange.getResponseSender().send(response);
//                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                        exchange.setStatusCode(500);
//                        exchange.getResponseSender().send("Error: " + e.getMessage());
//                        Server.logError("", e);
//                    }
//                });
//            });
//        });
//    }

}
