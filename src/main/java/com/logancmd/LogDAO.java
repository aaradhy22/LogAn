// src/main/java/com/logancmd/LogDAO.java
package com.logancmd;

import java.sql.*;
import java.util.List;

public class LogDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection connection;

	public LogDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	public void connect() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	public void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	public void insertLogEntries(List<LogEntry> logEntries) throws SQLException {
		String sql = "INSERT INTO logs (log_timestamp, log_level, source, message) VALUES (?, ?, ?, ?)";
		connect();

		// Use a PreparedStatement for security and performance
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			for (LogEntry entry : logEntries) {
				statement.setTimestamp(1, Timestamp.valueOf(entry.getTimestamp()));
				statement.setString(2, entry.getLevel());
				statement.setString(3, entry.getSource());
				statement.setString(4, entry.getMessage());
				statement.addBatch(); // Add to a batch for efficient insertion
			}
			statement.executeBatch(); // Execute the batch
		}
		disconnect();
	}

	// Add this method to your existing LogDAO.java class
	// In LogDAO.java

	public int getDailyErrorCount() throws SQLException {
	    // THIS IS THE LINE TO CHANGE
	    String sql = "SELECT COUNT(*) FROM logs WHERE log_level = 'ERROR' AND DATE(log_timestamp) = (SELECT MAX(DATE(log_timestamp)) FROM logs)";

	    connect();
	    int errorCount = 0;
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(sql)) {
	        if (resultSet.next()) {
	            errorCount = resultSet.getInt(1);
	        }
	    }
	    disconnect();
	    return errorCount;
	}
}