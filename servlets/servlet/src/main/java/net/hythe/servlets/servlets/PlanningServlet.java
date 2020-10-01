package net.hythe.servlets.servlets;

import net.hythe.projects.database.DataMapper;
import net.hythe.projects.database.Database;
import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.projects.database.reader.PlanningApplicationRowReader;
import net.hythe.projects.database.reader.RowReader;
import net.hythe.servlets.Keys;
import net.hythe.servlets.Util;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PlanningServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Database database = (Database)context.getAttribute(Keys.DATABASE_KEY);
        if (database == null) {
            System.out.println("Database was null");
        } else {
            System.out.println(String.format("Database is %1$s", database));
        }
        RowReader<PlanningApplication> planningApplicationRowReader = new PlanningApplicationRowReader();
        DataMapper dataMapper = new DataMapper();
        List<PlanningApplication> planningApplications = dataMapper.loadPlanningApplications(database, planningApplicationRowReader);
        System.out.println(String.format("Loaded %1$d planning applications", planningApplications.size()));
        request.setAttribute(Keys.DATABASE_KEY, database);
        request.setAttribute(Keys.PLANNING_APPLICATION_KEY, planningApplications);
        request.setAttribute(Keys.PLANNING_APPLICATION_JSON, Util.toJSONArray(planningApplications));
        request.getRequestDispatcher("planning.jsp").forward(request, response);
    }
}
