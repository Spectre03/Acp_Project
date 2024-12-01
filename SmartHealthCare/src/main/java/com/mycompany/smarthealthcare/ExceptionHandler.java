/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smarthealthcare;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class ExceptionHandler {
    public static int getValidAge() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter patient age: ");
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid age.");
            return getValidAge();  // Recursive call for retry
        }
    }
}
