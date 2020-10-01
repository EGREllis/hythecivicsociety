package net.hythe.projects.database.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Test {
    public static void main(String[] args) throws IOException {
        File dot = new File(".");
        System.out.println(dot.getAbsolutePath());

        Properties properties = getPropertiesFileFromJarFile("./projects/database/target/database-1.0-SNAPSHOT.jar");

        System.out.println(properties.getProperty("create"));
        System.out.println(properties.getProperty("stock"));
        System.out.println(properties.getProperty("select"));
        System.out.println(properties.getProperty("drop"));
    }

    private static Properties getPropertiesFileFromJarFile(String jarFilePath) {
        Properties properties = new Properties();
        try {
            System.out.println(String.format("Extracting properties from %1$s (which exists? %2$s)", jarFilePath, new File(jarFilePath).exists()));
            ZipInputStream inputStream = new ZipInputStream(new FileInputStream(jarFilePath));
            ZipEntry entry;
            while ((entry = inputStream.getNextEntry()) != null && !entry.getName().equals("config/planning.properties")) {
                // Skip
            }
            System.out.println(String.format("Reading %1$s", entry != null ? entry.getName() : "null"));
            if (entry != null && entry.getName().equals("config/planning.properties")) {
                System.out.println(String.format("Loading property files %1$s", entry.getName()));
                properties.load(inputStream);
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return properties;
    }
}
