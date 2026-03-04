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

public class AdminDashboard extends BaseDashboard {
    
    public AdminDashboard() {
        super("Admin Portal"); 
        
        initializeAdminButtons();
    }

    private void initializeAdminButtons() {
        // 1. profile button
        JButton profileButton = new JButton("My Profile");
        profileButton.setBounds(25, 120, 200, 40);
        profileButton.setFocusPainted(false);
        sidebar.add(profileButton);

        // 2. manage employees button
        JButton manageEmpButton = new JButton("Manage Employees");
        manageEmpButton .setBounds(25, 180, 200, 40);
        manageEmpButton .setFocusPainted(false);
        sidebar.add(manageEmpButton );

        // 3. run payroll button
        JButton payrollButton = new JButton("Run Payroll");
        payrollButton.setBounds(25, 240, 200, 40);
        payrollButton.setFocusPainted(false);
        sidebar.add(payrollButton);

        // 4. leave approval button
        JButton leaveAppButton = new JButton("Leave Approvals");
        leaveAppButton.setBounds(25, 300, 200, 40);
        leaveAppButton.setFocusPainted(false);
        sidebar.add(leaveAppButton);

        // 5. logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(25, 500, 200, 40);
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        sidebar.add(logoutButton);
    }

    public static void main(String[] args) {
        // test run only (remove after use)
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard().setVisible(true);
        });
    }
}