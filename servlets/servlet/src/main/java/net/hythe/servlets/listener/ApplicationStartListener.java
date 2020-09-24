package net.hythe.servlets.listener;

import net.hythe.projects.database.Database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ApplicationStartListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        File pwd = new File(".");
        StringBuilder message = new StringBuilder();
        boolean first = true;
        for (String entry : pwd.list()) {
            if (first) {
                first = false;
            } else {
                message.append(",");
            }
            message.append(entry);
        }
        System.out.println(String.format("Present working directory %1$s, contains %2$s", pwd.getAbsolutePath(), message.toString()));
        Database database = new Database();
        Connection connection = database.getConnection();
        System.out.println("Connection obtained!");
        try {
            URL url = ClassLoader.getSystemResource("data/planning_stock_data.sql");
            System.out.println(String.format("Url: %1$s", url != null ? url.toExternalForm() : "null"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
        final String jarFilePath = sce.getServletContext().getInitParameter("database_jar_path");
        System.out.println("Servlet Context param: database_jar_path = "+jarFilePath);
        try {
            StringBuilder text = new StringBuilder();
            ZipFile jarFile = new ZipFile(jarFilePath);
            Enumeration<? extends ZipEntry> contents = jarFile.entries();
            while (contents.hasMoreElements()) {
                ZipEntry entry = contents.nextElement();
                text.append(entry.getName()).append("\n");
            }
            System.out.println(text.toString());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        Database.main(new String[]{});
    }
}