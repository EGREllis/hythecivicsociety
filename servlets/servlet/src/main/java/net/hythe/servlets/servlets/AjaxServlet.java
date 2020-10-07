package net.hythe.servlets.servlets;

import net.hythe.projects.database.Database;
import net.hythe.projects.database.mapping.DataMapper;
import net.hythe.projects.database.model.PlanningApplication;
import net.hythe.servlets.Keys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class AjaxServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StringBuilder message = new StringBuilder();
        Enumeration<String> paramsNames = request.getParameterNames();
        boolean first = true;
        message.append("{ ");
        while (paramsNames.hasMoreElements()) {
            if (first) {
                first = false;
            } else {
                message.append(",");
            }
            String name = paramsNames.nextElement();
            message.append(name).append(" : ").append("\"").append(request.getParameter(name)).append("\"");
        }
        message.append(" }");
        System.out.println(String.format("Ajax called with params: "+message));

        PlanningApplication planningApplication = PlanningServlet.getPlanningApplicationFrom(request);
        Database database = (Database)getServletContext().getAttribute(Keys.DATABASE_KEY);
        DataMapper<PlanningApplication> dataMapper = (DataMapper<PlanningApplication>)getServletContext().getAttribute(Keys.PLANNING_DATA_MAPPER);
        dataMapper.insertPlanningApplication(database, planningApplication);
        System.out.println(String.format("Persisted Ajax Planning request: %1$s", message));

        response.getWriter().println(message);
    }
}
