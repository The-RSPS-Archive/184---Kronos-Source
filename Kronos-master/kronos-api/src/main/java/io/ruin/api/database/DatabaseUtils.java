package io.ruin.api.database;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

public class DatabaseUtils {

    public static void connect(Database[] dbs, Consumer<ArrayList<Throwable>> errorsConsumer) {
        PrintStream err = System.err;
        err.flush();
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                /* skip output for now */
            }
        }));
        ArrayList<Throwable> errors = new ArrayList<>();
        for(Database d : dbs) {
            try {
                d.connect();
            } catch(Throwable t) {
                String message = t.getCause().getMessage();
                int i = message.indexOf("\n");
                if(i != -1)
                    message = message.substring(0, i);
                errors.add(new Throwable("Failed to connect to database '" + d.database + "' : " + message));
            }
        }
        System.setErr(err);
        errorsConsumer.accept(errors);
    }

    public static void close(AutoCloseable... closeables) {
        for(AutoCloseable closeable : closeables) {
            if(closeable == null)
                continue;
            try {
                closeable.close();
            } catch(Exception e) {
                /* cannot close */
            }
        }
    }

    public static String insertQuery(String table, String... columnNames) {
        String[][] pairs = new String[columnNames.length][];
        for(int i = 0; i < columnNames.length; i++)
            pairs[i] = pair(columnNames[i], "?");
        String columns = "";
        String values = "";
        for(int i = 0; i < pairs.length; i++) {
            if(i != 0) {
                columns += ",";
                values += ",";
            }
            columns += columnNames[i];
            values += "?";
        }
        return "INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");";
    }

    public static String insertQuery(String table, String[]... pairs) {
        String columns = "", values = "";
        for(int i = 0; i < pairs.length; i++) {
            if(i != 0) {
                columns += ",";
                values += ",";
            }
            columns += pairs[i][0];
            values += "`" + pairs[i][1] + "`";
        }
        return "INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");";
    }

    public static String[] pair(String column, int value) {
        return pair(column, String.valueOf(value));
    }

    public static String[] pair(String column, long value) {
        return pair(column, String.valueOf(value));
    }

    public static String[] pair(String column, Date date) {
        return pair(column, date.toString());
    }

    public static String[] pair(String column, String value) {
        return new String[]{column, value};
    }

}
