package net.hythe.projects.database.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowReader<T> {
    T readFromRow(ResultSet resultSet) throws SQLException;
}
