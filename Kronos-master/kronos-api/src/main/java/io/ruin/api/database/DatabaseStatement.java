package io.ruin.api.database;

import io.ruin.api.utils.ServerWrapper;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseStatement {

    void execute(Connection connection) throws SQLException;

    default void failed(Throwable t) {
        //todo - don't log timeouts?
        ServerWrapper.logError("", t);
    }

}