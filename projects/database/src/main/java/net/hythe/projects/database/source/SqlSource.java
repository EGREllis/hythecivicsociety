package net.hythe.projects.database.source;

import java.util.Properties;

public interface SqlSource {
    Properties getSqlFromSource(String resource);
}
