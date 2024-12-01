package com.mycompany.smarthealthcare;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SmartHealthCare {
    private int nextPatientId = 1;
    private int nextDoctorId = 1;
    private int nextAppointmentId = 1;
    private Scanner scanner;
    
    public SmartHealthCare() {
        scanner = new Scanner(System.in);
    }
    
    // Patient Management
    public Patient registerPatient(String name, int age, String gender, String contactNumber, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (age <= 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age");
        }
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number cannot be empty");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        Patient patient = new Patient(nextPatientId++, name, age, gender, contactNumber, address);
        FileHandler3.savePatient(patient);
        return patient;
    }
    
    // Doctor Management
    public Doctor registerDoctor(String name, String specialization, String contactNumber, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        Doctor doctor = new Doctor(nextDoctorId++, name, specialization, contactNumber, email);
        FileHandler3.saveDoctor(doctor);
        return doctor;
    }
    
    // Appointment Management
    public Appointment scheduleAppointment(int patientId, int doctorId, LocalDateTime dateTime, String description) {
        Patient patient = FileHandler3.getPatient(patientId);
        Doctor doctor = FileHandler3.getDoctor(doctorId);
        
        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Patient or Doctor not found");
        }
        
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment time cannot be in the past");
        }

        // Check for time conflicts
        List<Appointment> doctorAppointments = FileHandler3.getDoctorAppointments(doctorId);
        for (Appointment existing : doctorAppointments) {
            if (existing.getStatus().equals("Scheduled")) {
                LocalDateTime existingTime = existing.getDateTime();
                // Check if appointments overlap (within 1 hour)
                if (Math.abs(existingTime.getHour() - dateTime.getHour()) < 1 &&
                    existingTime.toLocalDate().equals(dateTime.toLocalDate())) {
                    throw new IllegalArgumentException("Doctor has another appointment at this time");
                }
            }
        }
        
        Appointment appointment = new Appointment(nextAppointmentId++, patient, doctor, dateTime, description);
        FileHandler3.saveAppointment(appointment);
        doctor.addSchedule("Appointment with " + patient.getName() + " at " + dateTime);
        return appointment;
    }
    
    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Smart Healthcare System ===");
            System.out.println("1. Register New Patient");
            System.out.println("2. Register New Doctor");
            System.out.println("3. Schedule Appointment");
            System.out.println("4. View All Patients");
            System.out.println("5. View All Doctors");
            System.out.println("6. View All Appointments");
            System.out.println("7. Search Doctors by Specialization");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        registerNewPatient();
                        break;
                    case 2:
                        registerNewDoctor();
                        break;
                    case 3:
                        scheduleNewAppointment();
                        break;
                    case 4:
                        viewAllPatients();
                        break;
                    case 5:
                        viewAllDoctors();
                        break;
                    case 6:
                        viewAllAppointments();
                        break;
                    case 7:
                        searchDoctorsBySpecialization();
                        break;
                    case 8:
                        System.out.println("Thank you for using Smart Healthcare System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    private void registerNewPatient() {
        System.out.println("\n=== Register New Patient ===");
        try {
            String name = ExceptionHandler.getValidName();
            int age = ExceptionHandler.getValidAge();
            System.out.print("Enter gender (M/F): ");
            String gender = scanner.nextLine().trim().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                throw new IllegalArgumentException("Invalid gender. Please enter M or F.");
            }
            String contactNumber = ExceptionHandler.getValidPhoneNumber();
            System.out.print("Enter address: ");
            String address = scanner.nextLine().trim();
            
            Patient patient = registerPatient(name, age, gender, contactNumber, address);
            System.out.println("Patient registered successfully! Patient ID: " + patient.getPatientId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void registerNewDoctor() {
        System.out.println("\n=== Register New Doctor ===");
        try {
            String name = ExceptionHandler.getValidName();
            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine().trim();
            String contactNumber = ExceptionHandler.getValidPhoneNumber();
            String email = ExceptionHandler.getValidEmail();
            
            Doctor doctor = registerDoctor(name, specialization, contactNumber, email);
            System.out.println("Doctor registered successfully! Doctor ID: " + doctor.getDoctorId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void scheduleNewAppointment() {
        System.out.println("\n=== Schedule New Appointment ===");
        try {
            System.out.print("Enter patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter doctor ID: ");
            int doctorId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            LocalDateTime dateTime = ExceptionHandler.getValidDateTime();
            
            System.out.print("Enter appointment description: ");
            String description = scanner.nextLine().trim();
            
            Appointment appointment = scheduleAppointment(patientId, doctorId, dateTime, description);
            System.out.println("Appointment scheduled successfully! Appointment ID: " + appointment.getAppointmentId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
            scanner.nextLine(); // Clear the invalid input
        }
    }
    
    private void viewAllPatients() {
        System.out.println("\n=== All Patients ===");
        List<Patient> patientList = FileHandler3.getAllPatients();
        if (patientList.isEmpty()) {
            System.out.println("No patients registered yet.");
            return;
        }
        for (Patient patient : patientList) {
            System.out.println(patient);
        }
    }
    
    private void viewAllDoctors() {
        System.out.println("\n=== All Doctors ===");
        List<Doctor> doctorList = FileHandler3.getAllDoctors();
        if (doctorList.isEmpty()) {
            System.out.println("No doctors registered yet.");
            return;
        }
        for (Doctor doctor : doctorList) {
            System.out.println(doctor);
        }
    }
    
    private void viewAllAppointments() {
        System.out.println("\n=== All Appointments ===");
        List<Appointment> appointmentList = FileHandler3.getAllAppointments();
        if (appointmentList.isEmpty()) {
            System.out.println("No appointments scheduled yet.");
            return;
        }
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment);
        }
    }
    
    private void searchDoctorsBySpecialization() {
        System.out.println("\n=== Search Doctors by Specialization ===");
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine().trim();
        
        List<Doctor> specializedDoctors = FileHandler3.getDoctorsBySpecialization(specialization);
        if (specializedDoctors.isEmpty()) {
            System.out.println("No doctors found with specialization: " + specialization);
            return;
        }
        
        System.out.println("Doctors with specialization '" + specialization + "':");
        for (Doctor doctor : specializedDoctors) {
            System.out.println(doctor);
        }
    }
    
    public static void main(String[] args) {
        SmartHealthCare system = new SmartHealthCare();
        system.displayMenu();
    }
}