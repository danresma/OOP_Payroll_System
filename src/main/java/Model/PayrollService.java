/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */
public interface PayrollService {
    String generatePayroll(String [] employeeData, String monthName, String selectedYear);
    
    
    //for Abstraction
}
    