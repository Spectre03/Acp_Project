package com.mycompany.smarthealthcare;

import java.sql.*;
import javax.swing.JOptionPane;

public class Database {
    // MySQL database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/healthcare";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Connection connection = null;
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Create a new database connection
                System.out.println("Attempting to connect to MySQL database...");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Database connection established successfully.");
                
                // Create tables if they don't exist
                createTables();
                
                return connection;
            } catch (SQLException e) {
                System.err.println("Database Connection Error: " + e.getMessage());
                e.printStackTrace();
                
                // If database doesn't exist, create it and try again
                if (e.getMessage().contains("Unknown database")) {
                    try {
                        createDatabase();
                        connection = DriverManager.getConnection(DB_URL, USER, PASS);
                        createTables();
                        return connection;
                    } catch (SQLException ex) {
                        System.err.println("Failed to create database: " + ex.getMessage());
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to create database. Please ensure MySQL is running and credentials are correct.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage() + "\nPlease ensure MySQL is running and credentials are correct.");
                }
                return null;
            }
        }
        return connection;
    }
    
    private static void createDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        try (Connection tempConn = DriverManager.getConnection(url, USER, PASS);
             Statement stmt = tempConn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS healthcare");
            System.out.println("Database 'healthcare' created successfully.");
        }
    }
    
    private static void createTables() throws SQLException {
        Statement stmt = connection.createStatement();
        
        // Patients table
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS patients (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                age INT NOT NULL,
                gender VARCHAR(20) NOT NULL,
                contact_number VARCHAR(20) NOT NULL,
                address TEXT NOT NULL
            )
        """);
        System.out.println("Patients table created/verified.");
        
        // Doctors table
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS doctors (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                specialization VARCHAR(100) NOT NULL,
                contact_number VARCHAR(20) NOT NULL,
                email VARCHAR(100) NOT NULL
            )
        """);
        System.out.println("Doctors table created/verified.");
        
        // Appointments table
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS appointments (
                id INT AUTO_INCREMENT PRIMARY KEY,
                patient_id INT NOT NULL,
                doctor_id INT NOT NULL,
                date_time DATETIME NOT NULL,
                description TEXT NOT NULL,
                status VARCHAR(50) NOT NULL,
                FOREIGN KEY (patient_id) REFERENCES patients(id),
                FOREIGN KEY (doctor_id) REFERENCES doctors(id)
            )
        """);
        System.out.println("Appointments table created/verified.");
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    // Helper method to execute INSERT, UPDATE, DELETE queries
    public static int executeUpdate(String query, Object... parameters) throws SQLException {
        if (connection == null) {
            throw new SQLException("No database connection available");
        }
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            return pstmt.executeUpdate();
        }
    }
    
    // Helper method to execute SELECT queries
    public static ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        if (connection == null) {
            throw new SQLException("No database connection available");
        }
        
        PreparedStatement pstmt = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            pstmt.setObject(i + 1, parameters[i]);
        }
        return pstmt.executeQuery();
    }
}
