/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.oop_motorph_payroll_system.OOP_MotorPh_Payroll_System;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import service.CSVHandler;
import service.DataService;

/**
 *
 * @author Administrator
 */
public class LoginManager {
    
    private static final DataService dataService = new CSVHandler();
    
     public static void showLoginScreen() {
        JFrame frame = new JFrame("Login");
        JLabel userLabel = new JLabel("Username");
        JLabel passLabel = new JLabel("Password");
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        userLabel.setBounds(30, 30, 80, 25);
        userField.setBounds(120, 30, 150, 25);
        passLabel.setBounds(30, 70, 80, 25);
        passField.setBounds(120, 70, 150, 25);
        loginButton.setBounds(120, 110, 70, 25);
        cancelButton.setBounds(200, 110, 75, 25);

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginButton);
        frame.add(cancelButton);

        frame.setSize(350, 200);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                    return;
                }

                if (checkCredentials(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    frame.dispose(); // Close login window
                    OOP_MotorPh_Payroll_System.showEmployeeDetailsFrame(); // Show main app
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Credentials");
                }
            }
        });

        // Cancel button action
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the program
            }
        });
    }

    private static boolean checkCredentials(String username, String password) {
        List<String[]> credentialsList = dataService.readData("useraccount");
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
