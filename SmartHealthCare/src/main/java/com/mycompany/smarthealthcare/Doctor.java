package com.mycompany.smarthealthcare;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String contactNumber;
    private String email;
    private List<String> schedule;
    
    public Doctor(int doctorId, String name, String specialization, String contactNumber, String email) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.email = email;
        this.schedule = new ArrayList<>();
    }
     
    // Getters and Setters
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public List<String> getSchedule() { return schedule; }
    public void addSchedule(String appointment) {
        this.schedule.add(appointment);
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
