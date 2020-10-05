package net.hythe.projects.database.rows;

import net.hythe.projects.database.mapping.RowWriter;
import net.hythe.projects.database.model.PlanningApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanningApplicationRowWriter implements RowWriter<PlanningApplication> {
    private final String preparedInsert;

    public PlanningApplicationRowWriter(final String preparedInsert) {
        this.preparedInsert = preparedInsert;
    }

    @Override
    public PreparedStatement insertRow(Connection connection, PlanningApplication item) throws SQLException {
        PreparedStatement preped = connection.prepareStatement(preparedInsert);
        preped.setString(1, item.getName());
        preped.setDate(2, new java.sql.Date(item.getValidDate().getTime()));
        preped.setString(3, item.getAddress());
        preped.setString(4, item.getProposal());
        preped.setString(5, item.getType());
        preped.setString(6, item.getStatus());
        preped.setString(7, item.getWard());
        preped.setString(8, item.getParish());
        return preped;
    }
}
