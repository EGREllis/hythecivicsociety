package net.hythe.projects.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class Database {
    private static final String JDBC_CONNECTION_STRING = "jdbc:derby:testdb;create=true";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Driver driver = (Driver)(Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance());
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(JDBC_CONNECTION_STRING);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
        return connection;
    }

    public String getDatabase() {
        return "database";
    }

    public static void main(String[] args) {
    }
}
