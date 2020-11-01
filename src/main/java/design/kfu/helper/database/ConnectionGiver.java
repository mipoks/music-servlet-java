package design.kfu.helper.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionGiver {
    Connection getConnection() throws SQLException;
}
