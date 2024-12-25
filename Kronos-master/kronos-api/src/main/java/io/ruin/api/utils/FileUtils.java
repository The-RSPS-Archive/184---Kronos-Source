package io.ruin.api.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static boolean writeLines(File file, List<String> lines) {
        return writeLines(file, lines.toArray(new String[lines.size()]));
    }

    public static boolean writeLines(File file, String... lines) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for(int i = 0; i < lines.length; i++) {
                if(i != 0)
                    bw.newLine();
                bw.write(lines[i]);
            }
        } catch(Exception e) {
            ServerWrapper.logError("", e);
            return false;
        }
        return true;
    }

    public static String[] readLines(File file) {
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null)
                lines.add(line);
        } catch(Exception e) {
            ServerWrapper.logError("", e);
            return null;
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static File get(String path) {
        if(File.separator.equals("/"))
            path = path.replace("\\", File.separator);
        else
            path = path.replace("/", File.separator);
        path = path.replace("%HOME%", System.getProperty("user.home"));
        try {
            return new File(new File(path).getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(path);
    }

}
