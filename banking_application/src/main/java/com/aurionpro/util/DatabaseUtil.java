package com.aurionpro.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	// Update these values according to your MySQL setup
	private static final String DB_URL = "jdbc:mysql://localhost:3306/aurionpro_bank";
	private static final String DB_USER = "root"; 
	private static final String DB_PASSWORD = "vinay.jain"; 

	static {
		try {
			// Load MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MySQL JDBC Driver loaded successfully!");
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL JDBC Driver not found!");
			System.err.println("Please add mysql-connector-java-8.0.33.jar to WEB-INF/lib folder");
			throw new RuntimeException("MySQL JDBC Driver not found", e);
		}
	}

	public static Connection getConnection() throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("Database connection established successfully!");
			return conn;
		} catch (SQLException e) {
			System.err.println("Failed to connect to database!");
			System.err.println("URL: " + DB_URL);
			System.err.println("User: " + DB_USER);
			System.err.println("Error: " + e.getMessage());
			throw e;
		}
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Database connection closed successfully!");
			} catch (SQLException e) {
				System.err.println("Error closing database connection: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// Test database connection
	public static boolean testConnection() {
		try (Connection conn = getConnection()) {
			return conn != null && !conn.isClosed();
		} catch (SQLException e) {
			System.err.println("Database connection test failed: " + e.getMessage());
			return false;
		}
	}
}