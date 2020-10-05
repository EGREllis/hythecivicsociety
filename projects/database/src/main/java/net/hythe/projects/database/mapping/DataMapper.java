package net.hythe.projects.database.mapping;

import net.hythe.projects.database.Database;

import java.util.List;

public interface DataMapper<T> {
    List<T> loadPlanningApplications(Database database);
    int insertPlanningApplication(Database database, T item);
}
