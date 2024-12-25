package io.ruin.api.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ReverendDread on 6/14/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public interface DatabaseStatement {

    void execute(Connection connection) throws SQLException;

}
