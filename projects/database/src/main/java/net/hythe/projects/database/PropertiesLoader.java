package net.hythe.projects.database;

import net.hythe.projects.database.source.SqlSource;

public class PropertiesLoader {
    private final SqlSource sqlSource;

    public PropertiesLoader(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public String loadGoogleMapKey() {
        return sqlSource.getSqlFromSource("config/secret.properties").getProperty(Keys.GOOGLE_MAP_KEY);
    }
}
