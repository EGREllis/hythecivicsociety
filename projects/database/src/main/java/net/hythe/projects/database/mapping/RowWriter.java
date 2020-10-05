package net.hythe.projects.database.mapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface RowWriter<T> {
    PreparedStatement insertRow(Connection connection, T item) throws SQLException;
}
