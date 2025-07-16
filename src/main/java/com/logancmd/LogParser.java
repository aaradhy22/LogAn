// src/main/java/com/logancmd/LogParser.java
package com.logancmd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    // Regex to match our log format: [TIMESTAMP] [LEVEL] [SOURCE] - MESSAGE
    private static final String LOG_PATTERN = "^\\[(.*?)\\] \\[(.*?)\\] \\[(.*?)\\] - (.*)$";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<LogEntry> parseLogFile(String filePath) {
        List<LogEntry> logEntries = new ArrayList<>();
        Pattern pattern = Pattern.compile(LOG_PATTERN);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER);
                    String level = matcher.group(2);
                    String source = matcher.group(3);
                    String message = matcher.group(4);
                    logEntries.add(new LogEntry(timestamp, level, source, message));
                } else {
                    System.err.println("Could not parse line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logEntries;
    }
}