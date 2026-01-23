/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.oop_motorph_payroll_system;

import Model.AddNewEmployeePanel;
import Model.EmployeeData;
import Model.EmployeeTablePanel;
import Model.LoginManager;
import Model.PayrollGenerator;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Administrator
 */
public class OOP_MotorPh_Payroll_System {

     public static void main(String[] args) {
           SwingUtilities.invokeLater(LoginManager::showLoginScreen);     
    }
    
    // Convert Month into number 
    private static int monthNameToNumber(String monthName) {
        switch (monthName) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 0;
        }
    }

    //Main Frame Layout
    public static void showEmployeeDetailsFrame() {
    JFrame empFrame = new JFrame("MotorPH Payroll App");
    empFrame.setSize(800, 600);
    empFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Card layout for other panels
    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);

    // Home Panel (using its own null layout for manual placement)
    JPanel homePanel = new JPanel(null);

    JLabel titleLabel = new JLabel("MotorPh");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setBounds(50, 50, 200, 30);
    homePanel.add(titleLabel);

    JLabel tagLabel = new JLabel("Payroll System");
    tagLabel.setBounds(55, 70, 200, 30);
    homePanel.add(tagLabel);

    //For ID Number Input
    JLabel idLabel = new JLabel("Enter ID No.: ");
    idLabel.setBounds(20, 350, 150, 25);
    homePanel.add(idLabel);

    JTextField idField = new JTextField();
    idField.setBounds(120, 350, 80, 25);
    homePanel.add(idField);

    // Payroll Button
    JButton payrollButton = new JButton("Generate Payroll");
    payrollButton.setBounds(20, 450, 200, 25);
    homePanel.add(payrollButton);
    
    // View All Employees button
    JButton viewEmployeesButton = new JButton("View All Employees");
    viewEmployeesButton.setBounds(20, 520, 200, 25);
    homePanel.add(viewEmployeesButton);

    JTextArea empDetails = new JTextArea();
    empDetails.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(empDetails);
    scrollPane.setBounds(240, 80, 500, 425);
    homePanel.add(scrollPane);

    JLabel monthLabel = new JLabel("Select Month:");
    monthLabel.setBounds(20, 380, 150, 25);
    homePanel.add(monthLabel);

    String[] months = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    JComboBox<String> monthDropdown = new JComboBox<>(months);
    monthDropdown.setBounds(120, 380, 100, 25);
    homePanel.add(monthDropdown);

    JLabel yearLabel = new JLabel("Select Year:");
    yearLabel.setBounds(20, 410, 150, 25);
    homePanel.add(yearLabel);

    String[] years = {"2020", "2021", "2022", "2023", "2024", "2025"};
    JComboBox<String> yearDropdown = new JComboBox<>(years);
    yearDropdown.setBounds(120, 410, 100, 25);
    homePanel.add(yearDropdown);

    JButton resetButton = new JButton("Reset");
    resetButton.setBounds(20, 490, 100, 25);
    homePanel.add(resetButton);

    // Employee Table Panel
    EmployeeTablePanel employeePanel = new EmployeeTablePanel();
    AddNewEmployeePanel addEmployeePanel = new AddNewEmployeePanel(cardPanel, employeePanel);
   

    // Add panels to cardPanel
    cardPanel.add(homePanel, "home");
    cardPanel.add(employeePanel, "employees");
    cardPanel.add(addEmployeePanel, "addEmployee");

    // Add cardPanel to empFrame (no null layout)
    empFrame.add(cardPanel);

    // Payroll Button Action
    payrollButton.addActionListener(e -> {
        String employeeID = idField.getText().trim();
        String selectedMonth = (String) monthDropdown.getSelectedItem();
        String selectedYear = (String) yearDropdown.getSelectedItem();

        if (!employeeID.isEmpty()) {
            String[] employeeData = EmployeeData.getEmployeeDataByID(employeeID);
            if (employeeData != null) {
                String payrollSummary = PayrollGenerator.generatePayroll(employeeData, selectedMonth, selectedYear);
                empDetails.setText(payrollSummary);
            } else {
                JOptionPane.showMessageDialog(empFrame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(empFrame, "Please enter a valid ID.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    });

    // Reset Button Action
    resetButton.addActionListener(e -> {
        empDetails.setText("");
        idField.setText("");
        idField.setEnabled(true);
        monthDropdown.setEnabled(true);
        yearDropdown.setEnabled(true);
        payrollButton.setEnabled(true);
        resetButton.setEnabled(true);
        idField.requestFocus();
    });

    // View Employee Button Action
    viewEmployeesButton.addActionListener(e -> cardLayout.show(cardPanel, "employees"));
    

    empFrame.setVisible(true);
}}
