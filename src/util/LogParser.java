package util;

import model.LogEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    private static final Pattern ORACLE_PATTERN = Pattern
            .compile("^\\[(.*?)\\]\\s+(DEBUG|INFO|WARN|ERROR|FATAL)\\s+-\\s+\\[[^]]+\\]\\s+(.*)$");

    private static final Pattern SIMPLE_PATTERN = Pattern.compile("^(DEBUG|INFO|WARN|ERROR|FATAL)\\s+(.*)$");

    public static LogEntry parse(String rawLine) {

        Matcher oracleMatcher = ORACLE_PATTERN.matcher(rawLine);
        if (oracleMatcher.matches()) {
            String timestamp = oracleMatcher.group(1);
            String level = oracleMatcher.group(2);
            String message = oracleMatcher.group(3);
            return new LogEntry(level, message, timestamp);
        }

        Matcher simpleMatcher = SIMPLE_PATTERN.matcher(rawLine);
        if (simpleMatcher.matches()) {
            String level = simpleMatcher.group(1);
            String message = simpleMatcher.group(2);
            return new LogEntry(level, message, "N/A");
        }

        return null;
    }
}
