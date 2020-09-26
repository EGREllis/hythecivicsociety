package net.hythe.servlets.listener;

import net.hythe.projects.database.Database;
import net.hythe.projects.database.source.JarFileSqlSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
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

        Database database = new Database(new JarFileSqlSource(jarFilePath));
        if (database.isDatabaseCreated()) {
            database.dropDatabase();
        }
        database.createDatabase();
    }
}