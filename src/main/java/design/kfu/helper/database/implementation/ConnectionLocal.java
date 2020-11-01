package design.kfu.helper.database.implementation;

import design.kfu.helper.database.ConnectionGiver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionLocal implements ConnectionGiver {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/kfumusic";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "postgres";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}
