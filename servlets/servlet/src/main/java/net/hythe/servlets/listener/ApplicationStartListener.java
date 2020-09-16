package net.hythe.servlets.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationStartListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application Start Listener has been initialised.");
    }
}