/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smarthealthcare;

/**
 *
 * @author PC
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler3 {
    private static final Map<Integer, Patient> patients = new HashMap<>();
    private static final Map<Integer, Doctor> doctors = new HashMap<>();
    private static final Map<Integer, Appointment> appointments = new HashMap<>();

    // Patient operations
    public static void savePatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
    }

    public static Patient getPatient(int patientId) {
        return patients.get(patientId);
    }

    public static List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    // Doctor operations
    public static void saveDoctor(Doctor doctor) {
        doctors.put(doctor.getDoctorId(), doctor);
    }

    public static Doctor getDoctor(int doctorId) {
        return doctors.get(doctorId);
    }

    public static List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors.values());
    }

    public static List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> specializedDoctors = new ArrayList<>();
        for (Doctor doctor : doctors.values()) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                specializedDoctors.add(doctor);
            }
        }
        return specializedDoctors;
    }

    // Appointment operations
    public static void saveAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
    }

    public static Appointment getAppointment(int appointmentId) {
        return appointments.get(appointmentId);
    }

    public static List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    public static List<Appointment> getPatientAppointments(int patientId) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getPatient().getPatientId() == patientId) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

    public static List<Appointment> getDoctorAppointments(int doctorId) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getDoctor().getDoctorId() == doctorId) {
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments;
    }

    // Clear all data (for testing purposes)
    public static void clearAll() {
        patients.clear();
        doctors.clear();
        appointments.clear();
    }
}
