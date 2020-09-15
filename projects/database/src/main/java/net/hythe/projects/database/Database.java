package net.hythe.projects.database;

import java.sql.Driver;

public class Database {
    public static void main(String[] args) {
        // Start a derby database
        Driver driver = null;
        try {
            driver = (Driver)(Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance());
        } catch (Exception e) {
            System.err.println(String.format("%1$s", e.getMessage()));
            e.printStackTrace(System.err);
        }
    }
}
