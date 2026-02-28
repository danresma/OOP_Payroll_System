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
import service.DataService;
import service.EmployeeService;
import service.PayrollService;

/**
 *
 * @author Administrator
 */
public class AdminDashboard extends JFrame{
     private final DataService dataService;
     private final String adminUsername;

    public AdminDashboard(DataService dataService, String adminUsername) {
        this.dataService = dataService;
        this.adminUsername = adminUsername;

        setTitle("Admin Dashboard - " + adminUsername);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        EmployeeService employeeService = new EmployeeService(dataService);

        // EmployeeTablePanel for admin
        EmployeeTablePanel mainPanel = new EmployeeTablePanel(dataService, employeeService);
        add(mainPanel);

        setVisible(true);
    }
}