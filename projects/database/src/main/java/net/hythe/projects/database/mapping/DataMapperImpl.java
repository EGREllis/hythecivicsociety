package net.hythe.projects.database.mapping;

import net.hythe.projects.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataMapperImpl<T> implements DataMapper<T> {
    private final String select;
    private final RowReader<T> rowReader;
    private final RowWriter<T> rowWriter;

    public DataMapperImpl(String select, RowReader<T> rowReader, RowWriter<T> rowWriter) {
        this.select = select;
        this.rowReader = rowReader;
        this.rowWriter = rowWriter;
    }

    public List<T> loadPlanningApplications(Database database) {
        List<T> results = new ArrayList<T>();
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            if (statement.execute(select)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    T planning = rowReader.readFromRow(resultSet);
                    results.add(planning);
                }
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return results;
    }

    public int insertPlanningApplication(Database database, T item) {
        int rows = 0;
        try (   Connection connection = database.getConnection();
                PreparedStatement statement = rowWriter.insertRow(connection, item)) {
            if (!statement.execute()) {
                rows = statement.getUpdateCount();
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return rows;
    }
}
