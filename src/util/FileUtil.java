package util;

import model.LogEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<LogEntry> readLogFile(String path) {
        List<LogEntry> logs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry entry = LogParser.parse(line);
                if (entry != null) {
                    logs.add(entry);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading log file");
        }

        return logs;
    }
}
