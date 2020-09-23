package net.hythe.projects.database.reader;

import net.hythe.projects.database.model.PlanningApplication;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanningApplicationRowReader implements RowReader<PlanningApplication> {
    public PlanningApplication readFromRow(ResultSet resultSet) throws SQLException {
        return new PlanningApplication(
                resultSet.getString("name"),
                resultSet.getDate("valid_date"),
                resultSet.getString("address"),
                resultSet.getString("proposal"),
                resultSet.getString("type"),
                resultSet.getString("status"),
                resultSet.getString("ward"),
                resultSet.getString("parish")

        );
    }
}
