package net.hythe.projects.database.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static net.hythe.projects.database.Util.logException;

public class ClasspathSqlSource implements SqlSource {
    @Override
    public String getSqlFromSource(String resource) {
        StringBuilder sqlStatements = new StringBuilder();
        System.out.println(String.format("Reading SQL %1$s from classpath", resource));
        System.out.flush();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(resource)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlStatements.append(line);
            }
        } catch (IOException ioe) {
            logException(ioe);
        }
        System.out.println(String.format("Read SQL %1$s from classpath", resource));
        return sqlStatements.toString();
    }
}
