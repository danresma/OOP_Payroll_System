/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author franzielle
 */

public class EmployeeDashboard extends BaseDashboard {
    
    public EmployeeDashboard() {
        super("Employee Portal"); 
        

        initializeEmployeeButtons();
    }

    private void initializeEmployeeButtons() {
        // 1. profile button
        JButton profileButton = new JButton("My Profile");
        profileButton.setBounds(25, 120, 200, 40);
        profileButton.setFocusPainted(false);
        sidebar.add(profileButton);

        // 2. attendance button
        JButton attendanceButton = new JButton("View Attendance");
        attendanceButton.setBounds(25, 180, 200, 40);
        attendanceButton.setFocusPainted(false);
        sidebar.add(attendanceButton);

        // 3. salary button
        JButton payslipButton = new JButton("Payslip");
        payslipButton.setBounds(25, 240, 200, 40);
        payslipButton.setFocusPainted(false);
        sidebar.add(payslipButton);
        
        // 3. leave request button
        JButton leaveReqButton = new JButton("Leave Requests");
        leaveReqButton.setBounds(25, 300, 200, 40);
        leaveReqButton .setFocusPainted(false);
        sidebar.add(leaveReqButton);

        // 4. logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(25, 500, 200, 40);
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        sidebar.add(logoutButton);
    }

    public static void main(String[] args) {
        // test run only (remove after use)
        SwingUtilities.invokeLater(() -> {
            new EmployeeDashboard().setVisible(true);
        });
    }
}
