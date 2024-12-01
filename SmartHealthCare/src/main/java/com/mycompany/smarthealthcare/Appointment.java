package com.mycompany.smarthealthcare;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private String status; // Scheduled, Completed, Cancelled
    private String description;
    
    public Appointment(int appointmentId, Patient patient, Doctor doctor, LocalDateTime dateTime, String description) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = "Scheduled";
        this.description = description;
    }
    
    // Getters and Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; } 
    
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public void completeAppointment() {
        this.status = "Completed";
    }
    
    public void cancelAppointment() {
        this.status = "Cancelled";
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patient=" + patient.getName() +
                ", doctor=" + doctor.getName() +
                ", dateTime=" + dateTime +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
