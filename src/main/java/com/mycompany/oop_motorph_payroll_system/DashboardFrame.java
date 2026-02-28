/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oop_motorph_payroll_system;

import Model.Employee;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public abstract class DashboardFrame extends JFrame {
     protected Employee loggedInEmployee;

    public DashboardFrame(Employee employee) {

        this.loggedInEmployee = employee;

        setTitle("MotorPH Payroll System");

        setSize(800,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}