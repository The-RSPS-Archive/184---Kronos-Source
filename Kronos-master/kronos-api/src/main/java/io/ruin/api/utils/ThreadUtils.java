package io.ruin.api.utils;

public class ThreadUtils {

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException e) {
            ServerWrapper.logError("Issue sleeping thread for: " + ms, e);
        }
    }

}
