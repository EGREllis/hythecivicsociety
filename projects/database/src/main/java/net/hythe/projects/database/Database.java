package net.hythe.projects.database;

import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.projects.database.reader.PlanningApplicationRowReader;
import net.hythe.projects.database.reader.RowReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.List;

public class Database {
    private static final String JDBC_CONNECTION_STRING = "jdbc:derby:testdb;create=true";
    private static final String SQL_STATEMENT_TERMINATOR = ";";
    private static final String TEST_DATABASE_QUERY = "SELECT 1 FROM planning_application";
    private static final String DROP_TABLE_SQL = "DROP TABLE planning_application";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Driver driver = (Driver)(Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance());
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(JDBC_CONNECTION_STRING);
        } catch (Exception e) {
            logException(e);
        }
        return connection;
    }

    private void closeSafely(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    private boolean isDatabaseCreated() {
        Connection connection = null;
        Statement statement = null;
        boolean result;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeQuery(TEST_DATABASE_QUERY);
            result = true;
        } catch (SQLException sqle) {
            result = false;
        } finally {
            closeSafely(statement);
            closeSafely(connection);
        }
        return result;
    }

    private void dropDatabase() {
        System.out.println("Dropping database...");
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_SQL);
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        System.out.println("Dropped database.");
    }

    //TODO: Known bug - Does not correctly process multiple SQL statements on the same line.
    private void createDatabase() {
        System.out.println(String.format("Creating database..."));
        executeSqlFromClasspathS("ddl/create_table_planning.sql");
        System.out.println("Created database.");
        System.out.println(String.format("Adding stock data...."));
        executeSqlFromClasspathS("data/planning_test_data.sql");
        System.out.println(String.format("Added stock data."));
    }

    private void executeSqlFromClasspathS(String path) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(path)))) {
            StringBuilder sqlStatement = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(SQL_STATEMENT_TERMINATOR);
                sqlStatement.append(split[0]);
                if (line.contains(SQL_STATEMENT_TERMINATOR)) {
                    try {
                        Connection connection = getConnection();
                        Statement statement = connection.createStatement();
                        System.out.println(String.format("Executing: %1$s", sqlStatement));
                        if (statement.execute(sqlStatement.toString())) {
                            ResultSet resultSet = statement.getResultSet();
                            int rowCount = 0;
                            while (resultSet.next()) {
                                rowCount++;
                            }
                            System.out.println(String.format("Result set %1$d rows", rowCount));
                            resultSet.close();
                        } else {
                            System.out.println(String.format("Updated %1$d rows", statement.getUpdateCount()));
                        }
                        closeSafely(statement);
                        closeSafely(connection);
                        sqlStatement = new StringBuilder();
                        if (split.length > 1) {
                            sqlStatement.append(split[1]);
                        }
                    } catch (SQLException sqle) {
                        throw new RuntimeException(sqle);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) {
        URL url = ClassLoader.getSystemResource("data/planning_stock_data.sql");
        System.out.println("Resource: "+url+" "+url.toExternalForm());
        Database database = new Database();
        boolean created = database.isDatabaseCreated();
        if (!created) {
            database.createDatabase();
        }
        try {
            DataMapper dataMapper = new DataMapper();
            RowReader<PlanningApplication> planningApplicationRowReader = new PlanningApplicationRowReader();
            List<PlanningApplication> results = dataMapper.loadPlanningApplications(database, planningApplicationRowReader);
            for (PlanningApplication result : results) {
                System.out.println(result.toString());
            }
        } catch (SQLException e) {
            logException(e);
        }
        database.dropDatabase();
    }

    private static void logException(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace(System.err);
    }
}
