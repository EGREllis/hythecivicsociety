package net.hythe.projects.database;

public class Util {
    public static void logException(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace(System.err);
        System.err.flush();
    }

    public static void closeSafely(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logException(e);
            }
        }
    }
}
