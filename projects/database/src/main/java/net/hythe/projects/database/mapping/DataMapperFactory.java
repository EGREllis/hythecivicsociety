package net.hythe.projects.database.mapping;

import net.hythe.projects.database.Database;

public interface DataMapperFactory<T> {
    DataMapper<T> newDataMapper(Database database);
}
