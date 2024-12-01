package com.mycompany.smarthealthcare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SmartHealthCare {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public SmartHealthCare() {
        // Initialize database connection
        if (Database.getConnection() == null) {
            System.exit(1);
        }
        initializeGUI();
    }
    
    private void initializeGUI() {
        mainFrame = new JFrame("Smart Healthcare System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        addButton(menuPanel, "Register Patient", () -> showPatientRegistration());
        addButton(menuPanel, "Register Doctor", () -> showDoctorRegistration());
        addButton(menuPanel, "Schedule Appointment", () -> showAppointmentScheduling());
        addButton(menuPanel, "View Patients", () -> showPatients());
        addButton(menuPanel, "View Doctors", () -> showDoctors());
        addButton(menuPanel, "View Appointments", () -> showAppointments());
        
        mainPanel.add(menuPanel, "menu");
        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    private void addButton(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        panel.add(button);
    }
    
    private void showPatientRegistration() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField addressField = new JTextField();
        
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        
        JButton submitButton = new JButton("Register");
        submitButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty() || 
                    ageField.getText().trim().isEmpty() || 
                    genderField.getText().trim().isEmpty() || 
                    contactField.getText().trim().isEmpty() || 
                    addressField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "All fields are required!");
                    return;
                }
                
                String query = "INSERT INTO patients (name, age, gender, contact_number, address) VALUES (?, ?, ?, ?, ?)";
                int result = Database.executeUpdate(query,
                    nameField.getText().trim(),
                    Integer.parseInt(ageField.getText().trim()),
                    genderField.getText().trim(),
                    contactField.getText().trim(),
                    addressField.getText().trim()
                );
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Patient registered successfully!");
                    cardLayout.show(mainPanel, "menu");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Failed to register patient!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter a valid age!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Database Error: " + ex.getMessage());
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel);
        
        mainPanel.add(panel, "patient_registration");
        cardLayout.show(mainPanel, "patient_registration");
    }
    
    private void showDoctorRegistration() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField nameField = new JTextField();
        JTextField specializationField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField emailField = new JTextField();
        
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Specialization:"));
        panel.add(specializationField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        
        JButton submitButton = new JButton("Register");
        submitButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty() || 
                    specializationField.getText().trim().isEmpty() || 
                    contactField.getText().trim().isEmpty() || 
                    emailField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "All fields are required!");
                    return;
                }
                
                String query = "INSERT INTO doctors (name, specialization, contact_number, email) VALUES (?, ?, ?, ?)";
                int result = Database.executeUpdate(query,
                    nameField.getText().trim(),
                    specializationField.getText().trim(),
                    contactField.getText().trim(),
                    emailField.getText().trim()
                );
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Doctor registered successfully!");
                    cardLayout.show(mainPanel, "menu");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Failed to register doctor!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Database Error: " + ex.getMessage());
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel);
        
        mainPanel.add(panel, "doctor_registration");
        cardLayout.show(mainPanel, "doctor_registration");
    }
    
    private void showAppointmentScheduling() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JComboBox<String> patientCombo = new JComboBox<>();
        JComboBox<String> doctorCombo = new JComboBox<>();
        JTextField dateTimeField = new JTextField("YYYY-MM-DD HH:MM");
        JTextField descriptionField = new JTextField();
        
        // Load patients and doctors
        try {
            ResultSet rs = Database.executeQuery("SELECT id, name FROM patients");
            while (rs.next()) {
                patientCombo.addItem(rs.getInt("id") + " - " + rs.getString("name"));
            }
            
            rs = Database.executeQuery("SELECT id, name FROM doctors");
            while (rs.next()) {
                doctorCombo.addItem(rs.getInt("id") + " - " + rs.getString("name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error loading data: " + ex.getMessage());
        }
        
        panel.add(new JLabel("Patient:"));
        panel.add(patientCombo);
        panel.add(new JLabel("Doctor:"));
        panel.add(doctorCombo);
        panel.add(new JLabel("Date/Time:"));
        panel.add(dateTimeField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        
        JButton submitButton = new JButton("Schedule");
        submitButton.addActionListener(e -> {
            try {
                if (patientCombo.getSelectedItem() == null || 
                    doctorCombo.getSelectedItem() == null || 
                    dateTimeField.getText().trim().isEmpty() || 
                    descriptionField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "All fields are required!");
                    return;
                }
                
                String patientId = ((String)patientCombo.getSelectedItem()).split(" - ")[0];
                String doctorId = ((String)doctorCombo.getSelectedItem()).split(" - ")[0];
                
                String query = "INSERT INTO appointments (patient_id, doctor_id, date_time, description, status) VALUES (?, ?, ?, ?, ?)";
                int result = Database.executeUpdate(query,
                    Integer.parseInt(patientId),
                    Integer.parseInt(doctorId),
                    dateTimeField.getText().trim(),
                    descriptionField.getText().trim(),
                    "Scheduled"
                );
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Appointment scheduled successfully!");
                    cardLayout.show(mainPanel, "menu");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Failed to schedule appointment!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Database Error: " + ex.getMessage());
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel);
        
        mainPanel.add(panel, "appointment_scheduling");
        cardLayout.show(mainPanel, "appointment_scheduling");
    }
    
    private void showPatients() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"ID", "Name", "Age", "Gender", "Contact", "Address"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM patients");
            int rowCount = 0;
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("contact_number"),
                    rs.getString("address")
                };
                model.addRow(row);
                rowCount++;
            }
            
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(mainFrame, "No patients found in the database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error loading patients: " + ex.getMessage());
        }
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(panel, "view_patients");
        cardLayout.show(mainPanel, "view_patients");
    }
    
    private void showDoctors() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"ID", "Name", "Specialization", "Contact", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("contact_number"),
                    rs.getString("email")
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error: " + ex.getMessage());
        }
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(panel, "view_doctors");
        cardLayout.show(mainPanel, "view_doctors");
    }
    
    private void showAppointments() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"ID", "Patient", "Doctor", "Date/Time", "Description", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        try {
            ResultSet rs = Database.executeQuery("""
                SELECT a.id, p.name as patient_name, d.name as doctor_name,
                       a.date_time, a.description, a.status
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors d ON a.doctor_id = d.id
            """);
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("patient_name"),
                    rs.getString("doctor_name"),
                    rs.getString("date_time"),
                    rs.getString("description"),
                    rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error: " + ex.getMessage());
        }
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(panel, "view_appointments");
        cardLayout.show(mainPanel, "view_appointments");
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(() -> {
                try {
                    new SmartHealthCare();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error starting application: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error initializing application: " + e.getMessage());
        }
    }
}