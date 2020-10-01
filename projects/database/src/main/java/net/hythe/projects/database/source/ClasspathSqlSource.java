package net.hythe.projects.database.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClasspathSqlSource implements SqlSource {
    @Override
    public Properties getSqlFromSource(String resource) {
        Properties properties = new Properties();
        System.out.println(String.format("Reading Properties SQL %1$s from classpath", resource));
        try (InputStream input = ClassLoader.getSystemResourceAsStream(resource)) {
            properties.load(input);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        System.out.println(String.format("Read SQL %1$s from classpath", resource));
        return properties;
    }
}
