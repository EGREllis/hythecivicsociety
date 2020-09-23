package net.hythe.projects.database;

import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.projects.database.reader.RowReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {
    private static final String SELECT_ALL_PLANNING_APPILCATIONS = "SELECT * FROM planning_application";

    public List<PlanningApplication> loadPlanningApplications(Database database, RowReader<PlanningApplication> rowReader) throws SQLException {
        List<PlanningApplication> results = new ArrayList<PlanningApplication>();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        if (statement.execute(SELECT_ALL_PLANNING_APPILCATIONS)) {
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                PlanningApplication planning = rowReader.readFromRow(resultSet);
                results.add(planning);
            }
        }
        return results;
    }
}
