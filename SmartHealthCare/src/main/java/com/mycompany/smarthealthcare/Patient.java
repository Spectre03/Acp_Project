package com.mycompany.smarthealthcare;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private String address;
    private List<String> medicalHistory;
    
    public Patient(int patientId, String name, int age, String gender, String contactNumber, String address) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender; 
        this.contactNumber = contactNumber;
        this.address = address;
        this.medicalHistory = new ArrayList<>();
    }
    
    // Getters and Setters
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public List<String> getMedicalHistory() { return medicalHistory; }
    public void addMedicalHistory(String record) { 
        this.medicalHistory.add(record);
    }
    
    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
