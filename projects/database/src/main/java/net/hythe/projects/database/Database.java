package net.hythe.projects.database;

import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.projects.database.reader.PlanningApplicationRowReader;
import net.hythe.projects.database.reader.RowReader;
import net.hythe.projects.database.source.ClasspathSqlSource;
import net.hythe.projects.database.source.JarFileSqlSource;
import net.hythe.projects.database.source.SqlSource;

import java.io.File;
import java.sql.*;
import java.util.List;

import static net.hythe.projects.database.Util.logException;

public class Database {
    private static final String JDBC_CONNECTION_STRING = "jdbc:derby:testdb;create=true";
    private static final String SQL_STATEMENT_TERMINATOR = ";";
    private static final String CLASSPATH_PROPERTY_PATH = "config/planning.properties";
    private static final String TEST_DATABASE_QUERY = "SELECT 1 FROM planning_application";

    private SqlSource sqlSource;

    public Database(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    private String getDropTableDatabaseSQL() {
        return sqlSource.getSqlFromSource(CLASSPATH_PROPERTY_PATH).getProperty("drop");
    }

    private String getCreateTableSQL() {
        return sqlSource.getSqlFromSource(CLASSPATH_PROPERTY_PATH).getProperty("create");
    }

    private String getStockDataSQL() {
        return sqlSource.getSqlFromSource(CLASSPATH_PROPERTY_PATH).getProperty("stock");
    }

    private void runSQL(String sql) {
        String[] sqlStatements = sql.split(SQL_STATEMENT_TERMINATOR);
        for (String sqlStatement : sqlStatements) {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                System.out.println(String.format("Executing: %1$s", sqlStatement));
                if (statement.execute(sqlStatement)) {
                    ResultSet resultSet = statement.getResultSet();
                    long rowCount = 0L;
                    while (resultSet.next()) {
                        rowCount++;
                    }
                    System.out.println(String.format("Returned %1$d rows", rowCount));
                } else {
                    System.out.println(String.format("Updated %1$d rows", statement.getUpdateCount()));
                }
            } catch (SQLException sqle) {
                logException(sqle);
            }
        }
    }

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

    public boolean isDatabaseCreated() {
        boolean result;
        try (   Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeQuery(TEST_DATABASE_QUERY);
            result = true;
        } catch (SQLException sqle) {
            result = false;
        }
        return result;
    }

    public void dropDatabase() {
        runSQL(getDropTableDatabaseSQL());
    }

    //TODO: Known bug - Does not correctly process multiple SQL statements on the same line.
    public void createDatabase() {
        runSQL(getCreateTableSQL());
        runSQL(getStockDataSQL());
    }

    public static void main(String args[]) {
        File file = new File("./target/database-1.0-SNAPSHOT.jar");
        System.out.println(file.getAbsolutePath());

        Database database;
        if (args.length == 0) {
            database = new Database(new ClasspathSqlSource());
        } else {
            database = new Database(new JarFileSqlSource(args[0]));
        }

        boolean created = database.isDatabaseCreated();
        if (!created) {
            database.createDatabase();
        }

        DataMapper dataMapper = new DataMapper();
        RowReader<PlanningApplication> planningApplicationRowReader = new PlanningApplicationRowReader();
        List<PlanningApplication> results = dataMapper.loadPlanningApplications(database, planningApplicationRowReader);
        for (PlanningApplication result : results) {
            System.out.println(result.toString());
        }

        database.dropDatabase();
    }

    @Override
    public String toString() {
        return JDBC_CONNECTION_STRING;
    }
}
