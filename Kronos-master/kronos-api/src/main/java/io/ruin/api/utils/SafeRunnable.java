package io.ruin.api.utils;

public class SafeRunnable implements Runnable {

    private final Runnable toRun;

    public SafeRunnable(Runnable toRun) {
        this.toRun = toRun;
    }

    @Override
    public void run() {
        try {
            toRun.run();
        } catch(Throwable t) {
            ServerWrapper.logError("", t);
        }
    }

}
