package net.hythe.servlets.listener;

import net.hythe.projects.database.Database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.sql.Connection;

public class ApplicationStartListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        File pwd = new File(".");
        System.out.println(String.format("Present working directory %1$s", pwd.getAbsolutePath()));
        Connection connection = Database.getConnection();
        System.out.println("Connection obtained!");
    }
}