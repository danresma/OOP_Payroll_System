/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.oop_motorph_payroll_system.AdminDashboard;
import com.mycompany.oop_motorph_payroll_system.OOP_MotorPh_Payroll_System;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mycompany.oop_motorph_payroll_system.Dashboard_Panel_A;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import service.DataService;
import service.EmployeeService;

/**
 *
 * @author Administrator
 */
public class AdminLoginManager {
      private static DataService dataService;

    public static void showLoginScreen(DataService dS) {
        dataService = dS;

        JFrame frame = new JFrame("Admin Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        userLabel.setBounds(30, 30, 80, 25);
        userField.setBounds(120, 30, 150, 25);
        passLabel.setBounds(30, 70, 80, 25);
        passField.setBounds(120, 70, 150, 25);
        loginButton.setBounds(120, 110, 70, 25);
        cancelButton.setBounds(200, 110, 75, 25);

        frame.setLayout(null);
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginButton);
        frame.add(cancelButton);

        frame.setVisible(true);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                return;
            }

            if (checkCredentials(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login Successful");
                frame.dispose(); // close login
            
            
                // Open Admin Dashboard
                //SwingUtilities.invokeLater(() -> new AdminDashboard(dataService, username)); other AdminDashboard
                Dashboard_Panel_A dashboard = new Dashboard_Panel_A();  // show Dashboard A
                dashboard.setVisible(true);  
                

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Admin Credentials");
            }
        });

        // Cancel button
        cancelButton.addActionListener(e -> frame.dispose());
    }

    // Check credentials against adminUser CSV
    private static boolean checkCredentials(String username, String password) {
        List<String[]> credentialsList = dataService.readData("adminUser"); // only adminUser CSV file
        for (String[] credentials : credentialsList) {
            if (credentials.length >= 2) {
                String storedUsername = credentials[0].trim();
                String storedPassword = credentials[1].trim();
                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}