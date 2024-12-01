/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smarthealthcare;

/**
 *
 * @author PC
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/healthcare";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error connecting to database.");
            e.printStackTrace();
            return null;
        }
    }
}
