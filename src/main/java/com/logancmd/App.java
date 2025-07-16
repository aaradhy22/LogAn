package com.logancmd;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // --- Load Configuration from XML ---
        ConfigParser.loadConfig("config.xml");
        String logFilePath = ConfigParser.logFilePath;
        String dbUrl = ConfigParser.dbUrl;
        String dbUser = ConfigParser.dbUser;
        String dbPassword = ConfigParser.dbPassword;

        System.out.println("Starting LogAn...");

        // 1. Parse the log file
        System.out.println("Parsing log file: " + logFilePath);
        LogParser parser = new LogParser();
        List<LogEntry> logEntries = parser.parseLogFile(logFilePath); // <-- Simple version
        System.out.println("Found " + logEntries.size() + " log entries.");

        if (logEntries.isEmpty()) {
            System.out.println("No new log entries to process. Exiting.");
            return;
        }

        // 2. Insert into the database
        System.out.println("Connecting to database and inserting logs...");
        LogDAO logDAO = new LogDAO(dbUrl, dbUser, dbPassword);
        try {
            logDAO.insertLogEntries(logEntries);
            System.out.println("Successfully inserted log entries into the database.");
        } catch (Exception e) {
            System.err.println("Error inserting logs into the database.");
            e.printStackTrace();
        }

        // 3. Generate a daily report
        System.out.println("\n--- Daily Report ---");
        try {
            int errorCount = logDAO.getDailyErrorCount();
            System.out.println("Total ERROR logs for the latest day in the database: " + errorCount);
        } catch (Exception e) {
            System.err.println("Error generating daily report.");
            e.printStackTrace();
        }
        System.out.println("LogAn process finished.");
    }
}