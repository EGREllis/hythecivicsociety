package net.hythe.servlets;

import net.hythe.projects.database.model.JSONCodeable;

import java.util.List;

public class Util {
    public static String toJSONArray(List<? extends JSONCodeable> records) {
        StringBuilder message = new StringBuilder();
        message.append("[");
        boolean isFirst = true;
        for (JSONCodeable record : records) {
            if (isFirst) {
                isFirst = false;
            } else {
                message.append(",");
            }
            message.append(record.toJSON());
        }
        message.append("]");
        return message.toString();
    }
}
