package io.ruin.api.utils;

import io.ruin.api.process.ProcessFactory;
import io.ruin.api.process.ProcessWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintStream;

public class ServerWrapper {

    private static String modulePath;

    private static final Logger rollingWarnFileLogger = LoggerFactory.getLogger("rollingWarnFileLogger");
    private static final Logger rollingErrorFileLogger = LoggerFactory.getLogger("rollingErrorFileLogger");
    private static final Logger logger = LoggerFactory.getLogger("logger");

    public static void init(Class<?> c) {
        modulePath = new File(c.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath()
                .replace("%20", " ")
                .replace("target", "");
    }

    public static ProcessWorker newWorker(String name, long period, int threadPriority) {
        return new ProcessWorker(period, new ProcessFactory(name, threadPriority), t -> logError(name, t));
    }

    public static void log(String message) {
        logger.info(message);
    }

    public static void logError(String message) {
        rollingErrorFileLogger.error(message);
    }

    public static void logError(String message, Throwable t) {
        rollingErrorFileLogger.error(message, t);
    }

    public static void logWarning(String warning) {
        rollingWarnFileLogger.warn(warning);
    }

    public static String getPath() {
        if(modulePath == null)
            throw new RuntimeException("function init never called!");
        return modulePath;
    }

    public static File getFile(String path) {
        if(!path.startsWith(File.separator))
            path = File.separator + path;
        return FileUtils.get(modulePath + path);
    }

    public static void println(String message) {
        PrintStream out = System.out;
        out.println(TimeUtils.addTimestamp(message));
        out.flush();
        ThreadUtils.sleep(100L);
    }
}
