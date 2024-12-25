package io.ruin.api.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.ruin.api.process.ProcessFactory;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.api.utils.TimeUtils;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLTransientConnectionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Database {

    private static final ExecutorService SHARED_EXECUTOR = Executors.newFixedThreadPool(100, new ProcessFactory("database-worker", Thread.MIN_PRIORITY + 1));

    public final String host;

    public final String database;

    private final String username, password;

    private HikariDataSource dataSource;

    public Database(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public final void connect() {
        if(dataSource != null)
            throw new RuntimeException("Database " + host + "/" + database + " is already connected!");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + "/" + database + "?useSSL=false&serverTimezone=UTC");
        config.setUsername(username);
        config.setPassword(password);

        config.setPoolName(database + "-hikari-pool");
        config.setMinimumIdle(0); //has to be 0 so idle connections timeout since Hikari keeps them alive after shutdown!!!
        config.setMaximumPoolSize(100); //the maximum number of connections that can be in the pool at once.
        config.setConnectionTimeout(5000L); //how long the connection will attempt to connect for before timing out.
        config.setIdleTimeout(TimeUtils.getMinutesToMillis(5L)); //how long a connection can sit idle in the pool
        config.setLeakDetectionThreshold(TimeUtils.getMinutesToMillis(5L)); //how long a connection can be outside of a pool before being considered a "leak"
        config.addDataSourceProperty("cachePrepStmts", "true"); //enables the following two settings
        config.addDataSourceProperty("prepStmtCacheSize", "250"); //recommended minimum value
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); //recommended value
        config.addDataSourceProperty("useServerPrepStmts", "true"); //"this can provide a substantial performance boost"

        dataSource = new HikariDataSource(config);
    }

    public void execute(DatabaseStatement statement) {
        execute(statement, false);
    }

    public void executeAwait(DatabaseStatement statement) {
        execute(statement, true);
    }

    @SneakyThrows
    private void execute(DatabaseStatement statement, boolean wait) {
        Runnable task = () -> {
            while (true) {
                try (Connection con = dataSource.getConnection()) {
                    if (con.isValid(5)) {
                        statement.execute(con);
                        return;
                    }
                } catch (SQLTransientConnectionException e) {
                    /* no connections available in the pool at this time */
                } catch (Throwable t) {
                    statement.failed(t);
                    return;
                }
                ThreadUtils.sleep(100L);
            }
        };
        if (wait) {
            try {
                SHARED_EXECUTOR.submit(task).get();
            } catch (Throwable t) {
                ServerWrapper.logError("", t);
            }
            return;
        }
        SHARED_EXECUTOR.execute(task);
    }

}