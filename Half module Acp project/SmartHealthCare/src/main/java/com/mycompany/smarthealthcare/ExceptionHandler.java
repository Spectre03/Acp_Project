/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smarthealthcare;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class ExceptionHandler {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static int getValidAge() {
        while (true) {
            try {
                System.out.print("Enter patient age: ");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (age <= 0 || age > 150) {
                    System.out.println("Invalid age! Age must be between 1 and 150.");
                    continue;
                }
                return age;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    public static String getValidPhoneNumber() {
        while (true) {
            System.out.print("Enter contact number: ");
            String phone = scanner.nextLine().trim();
            
            if (phone.matches("\\d{10}")) {
                return phone;
            }
            System.out.println("Invalid phone number! Please enter a 10-digit number.");
        }
    }
    
    public static String getValidEmail() {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            
            if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return email;
            }
            System.out.println("Invalid email format! Please enter a valid email address.");
        }
    }
    
    public static LocalDateTime getValidDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            try {
                System.out.println("Enter date and time (format: yyyy-MM-dd HH:mm): ");
                String dateTimeStr = scanner.nextLine().trim();
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                
                if (dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Error: Appointment time cannot be in the past!");
                    continue;
                }
                return dateTime;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use format: yyyy-MM-dd HH:mm");
            }
        }
    }
    
    public static String getValidName() {
        while (true) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();
            
            if (!name.isEmpty() && name.matches("^[a-zA-Z\\s]+$")) {
                return name;
            }
            System.out.println("Invalid name! Name should contain only letters and spaces.");
        }
    }
}
