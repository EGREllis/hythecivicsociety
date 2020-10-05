package net.hythe.projects.database.mapping;

import net.hythe.projects.database.Database;
import net.hythe.projects.database.Keys;
import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.projects.database.rows.PlanningApplicationRowReader;
import net.hythe.projects.database.rows.PlanningApplicationRowWriter;

import java.util.Properties;

public class PlanningApplicationDataMapperFactory implements DataMapperFactory<PlanningApplication> {
    @Override
    public DataMapper<PlanningApplication> newDataMapper(Database database) {
        Properties databaseProperties = database.getDatabaseProperties();
        String select = databaseProperties.getProperty(Keys.SELECT_PLANNING_APPLICATION);
        String insert = databaseProperties.getProperty(Keys.INSERT_PLANNING_APPLICATION);
        RowReader<PlanningApplication> rowReader = new PlanningApplicationRowReader();
        RowWriter<PlanningApplication> rowWriter = new PlanningApplicationRowWriter(insert);
        return new DataMapperImpl<>(select, rowReader, rowWriter);
    }
}
