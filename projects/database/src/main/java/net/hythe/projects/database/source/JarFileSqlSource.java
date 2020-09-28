package net.hythe.projects.database.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static net.hythe.projects.database.Util.logException;

public class JarFileSqlSource implements SqlSource {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final String jarFilePath;

    public JarFileSqlSource(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    @Override
    public String getSqlFromSource(String resource) {
        File jarFile = new File(jarFilePath);
        System.out.println(String.format("Reading %1$s from jar file at %2$s, which exists? %3$s", resource, jarFile.getAbsolutePath(), Boolean.toString(jarFile.exists())));
        StringBuilder sqlStatements = new StringBuilder();
        try (ZipInputStream input = new ZipInputStream(new FileInputStream(jarFilePath))) {
            // Skip to the entry
            ZipEntry entry = input.getNextEntry();
            while (entry != null && !entry.getName().equals(resource)) {
                entry = input.getNextEntry();
            }

            if (entry == null) {
                throw new IllegalStateException(String.format("Could not fine entry %1$s in zip file %2$s", resource, jarFile.getAbsolutePath()));
            }

            sqlStatements.append(sqlFromZipEntry(input, entry));
        } catch (IOException ioe) {
            logException(ioe);
        }
        return sqlStatements.toString();
    }

    private String sqlFromZipEntry(ZipInputStream input, ZipEntry entry) throws IOException {
        String sql;
        int bufferSize;
        byte []buffer;
        char []chars;

        if (entry.getSize() > 0) {
            System.out.println(String.format("Reading sql from zip entry %1$s using known size zip file strategy (size %2$d)", entry.getName(), entry.getSize()));
            bufferSize = (int)entry.getSize();
            buffer = new byte[bufferSize];
            chars = new char[bufferSize/2];
            int read = input.read(buffer, 0, bufferSize);
            for (int i = 0; i < chars.length && i < read; i++) {
                chars[i] = (char)buffer[i*2];
                chars[i] = (char)(chars[i] << 8);
                chars[i] = (char)(chars[i] | buffer[i*2 + 1]);
            }
            sql = new String(chars);
        } else if (entry.getSize() < 0) {
            System.out.println(String.format("Reading sql from zip entry %1$s using unknown size zip file strategy", entry.getName()));
            StringBuilder message = new StringBuilder();
            bufferSize = 2;
            buffer = new byte[bufferSize];
            chars = new char[bufferSize/2];
            while (input.read(buffer, 0, bufferSize) > 0) {
                chars[0] = (char)buffer[0];
                chars[0] = (char)(chars[0] << 8);
                chars[0] = (char)(chars[0] | buffer[1]);
                message.append(chars[0]);
                buffer[0] = 0;
                buffer[1] = 0;
            }
            sql = message.toString();
        } else {
            sql = "";
        }
        return sql;
    }
}
