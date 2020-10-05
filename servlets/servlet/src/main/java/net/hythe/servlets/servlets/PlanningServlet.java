package net.hythe.servlets.servlets;

import net.hythe.projects.database.mapping.DataMapper;
import net.hythe.projects.database.Database;
import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.servlets.Keys;
import net.hythe.servlets.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PlanningServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PlanningApplication> planningApplications = loadPlanningApplications();
        packRequest(request, planningApplications);
        request.getRequestDispatcher("planning.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanningApplication planningApplication = getPlanningApplicationFrom(request);
        Database database = getDatabase();
        DataMapper<PlanningApplication> dataMapper = getDataMapper();

        //TODO: Check that a row has been updated?
        int rowsUpdated = dataMapper.insertPlanningApplication(database, planningApplication);

        List<PlanningApplication> planningApplications = loadPlanningApplications();
        packRequest(request, planningApplications);
        request.getRequestDispatcher("planning.jsp").forward(request, response);
    }

    private List<PlanningApplication> loadPlanningApplications() {
        Database database = getDatabase();
        DataMapper<PlanningApplication> dataMapper = getDataMapper();
        return dataMapper.loadPlanningApplications(database);
    }

    private void packRequest(HttpServletRequest request, List<PlanningApplication> planningApplications) {
        request.setAttribute(Keys.PLANNING_APPLICATION_KEY, planningApplications);
        request.setAttribute(Keys.PLANNING_APPLICATION_JSON, Util.toJSONArray(planningApplications));
        request.setAttribute(Keys.GOOGLE_API_KEY, getServletContext().getAttribute(Keys.GOOGLE_API_KEY));
    }

    private Database getDatabase() {
        return (Database)getServletContext().getAttribute(Keys.DATABASE_KEY);
    }

    private DataMapper<PlanningApplication> getDataMapper() {
        return (DataMapper<PlanningApplication>)getServletContext().getAttribute(Keys.PLANNING_DATA_MAPPER);
    }

    private PlanningApplication getPlanningApplicationFrom(HttpServletRequest request) {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        PlanningApplication app = new PlanningApplication(name, new Date(), address, "", type, status, "", "");

        return app;
    }
}
