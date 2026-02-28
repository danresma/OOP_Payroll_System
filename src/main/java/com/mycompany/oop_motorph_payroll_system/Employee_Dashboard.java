/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oop_motorph_payroll_system;

import Model.Employee;
import Model.PayrollGenerator;
import dao.CSVHandler;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import service.PayrollService;

/**
 *
 * @author Administrator
 */
public class Employee_Dashboard extends DashboardFrame{
    
    
    public static void showDashboard(String username) {

        JFrame frame = new JFrame("Employee Dashboard");

        frame.setSize(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel(
                "Welcome Employee: " + username,
                SwingConstants.CENTER
        );

        frame.add(label);

        frame.setVisible(true);
    }

    public Employee_Dashboard(Employee employee) {
        super(employee);
    }
}