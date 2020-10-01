package net.hythe.projects.database.source;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static net.hythe.projects.database.Util.logException;

public class JarFileSqlSource implements SqlSource {
    private final String jarFilePath;

    public JarFileSqlSource(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    @Override
    public Properties getSqlFromSource(String resource) {
        File jarFile = new File(jarFilePath);
        System.out.println(String.format("Reading %1$s from jar file at %2$s, which exists? %3$s", resource, jarFile.getAbsolutePath(), Boolean.toString(jarFile.exists())));
        Properties properties = new Properties();
        try (ZipInputStream input = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarFilePath)))) {
            // Skip to the entry
            ZipEntry entry = input.getNextEntry();
            while (entry != null && !entry.getName().equals(resource)) {
                entry = input.getNextEntry();
            }

            if (entry == null) {
                throw new IllegalStateException(String.format("Could not fine entry %1$s in zip file %2$s", resource, jarFile.getAbsolutePath()));
            }

            properties.load(input);
        } catch (IOException ioe) {
            logException(ioe);
        }
        return properties;
    }
}
