/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */
public class PhilhealthDeduction extends DeductionService {

    @Override
    public double Calculate(Employee employee, double grossSalary) {
         return employee.getBasicPay()* 0.03;
        
    }
    
}
