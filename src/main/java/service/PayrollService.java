/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Model.Employee;

/**
 *
 * @author Administrator
 */
public interface PayrollService {
    String generatePayroll(Employee employee, String monthName, String selectedYear);
    
    
    //for Abstraction
}
    