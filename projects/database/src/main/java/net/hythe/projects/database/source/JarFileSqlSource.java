package net.hythe.projects.database.source;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static net.hythe.projects.database.Util.logException;

public class JarFileSqlSource implements SqlSource {
    private final String jarFilePath;

    public JarFileSqlSource(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    @Override
    public String getSqlFromSource(String resource) {
        System.out.println(String.format("Reading %1$s from jar file at %2$s", resource, jarFilePath));
        StringBuilder sqlStatements = new StringBuilder();
        try (ZipInputStream input = new ZipInputStream(new FileInputStream(jarFilePath))) {
            ZipEntry entry = input.getNextEntry();
            byte[] buffer = new byte[(int)entry.getSize()];
            if (entry.getName().equals(resource)) {
                // Unzip and add to sqlStatements
                int read = input.read(buffer, 0, (int)entry.getSize());
                char chars[] = new char[buffer.length/2];
                for (int i = 0; i < chars.length; i++) {
                    chars[i] = (char)buffer[i*2];
                    chars[i] = (char)(chars[i] << 8);
                    chars[i] = (char)(chars[i] | buffer[i*2 + 1]);
                }
                String text = new String(chars);
                sqlStatements.append(text);
            }
        } catch (IOException ioe) {
            logException(ioe);
        }
        return sqlStatements.toString();
    }
}
