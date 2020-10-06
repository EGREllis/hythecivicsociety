package net.hythe.servlets.servlets;

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
        response.getWriter().println(message);
    }
}
