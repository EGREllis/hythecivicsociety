package net.hythe.servlets.listener;

import net.hythe.projects.database.mapping.DataMapper;
import net.hythe.projects.database.mapping.DataMapperFactory;
import net.hythe.projects.database.mapping.DataMapperImpl;
import net.hythe.projects.database.Database;
import net.hythe.projects.database.mapping.PlanningApplicationDataMapperFactory;
import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.projects.database.source.JarFileSqlSource;
import net.hythe.servlets.Keys;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationStartListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        final String jarFilePath = sce.getServletContext().getInitParameter("database_jar_path");
        System.out.println("Servlet Context param: database_jar_path = "+jarFilePath);

        Database database = new Database(new JarFileSqlSource(jarFilePath));
        if (database.isDatabaseCreated()) {
            database.dropDatabase();
        }
        database.createDatabase();
        sce.getServletContext().setAttribute(Keys.DATABASE_KEY, database);

        DataMapperFactory<PlanningApplication> planningApplicationDataMapperFactory = new PlanningApplicationDataMapperFactory();
        DataMapper<PlanningApplication> planningApplicationDataMapper = planningApplicationDataMapperFactory.newDataMapper(database);
        sce.getServletContext().setAttribute(Keys.PLANNING_DATA_MAPPER, planningApplicationDataMapper);
    }
}