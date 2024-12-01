/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.java;
import java.util.Scanner;
/**
 *
 * @author PC
 */
public class Java {

    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
      
     try {   
         System.out.println("Enter 1st integers: ");        
        int num1 = sc.nextInt();
        System.out.println("Enter 2nd integers: ");
        int num2 = sc.nextInt();
     int result = num1/num2;
     System.out.println("Result of divison :" + result);
     }
     catch (ArithmeticException e) {
         System.out.println("Error: not divisible");
            
      } catch (Exception e) { 
          System.out.println("Error: wrong integers");
      } finally {
         System.out.println("Program executed");
     }
    }
}