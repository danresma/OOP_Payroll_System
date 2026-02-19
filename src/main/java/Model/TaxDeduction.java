/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */
public class TaxDeduction extends DeductionService {

    @Override
    public double Calculate(Employee employee, double grossSalary) {
        if (grossSalary <= 20832) return 0;
        else if (grossSalary < 33333) return (grossSalary - 20833) * 0.2;
        else if (grossSalary < 66667) return 2500 + (grossSalary - 33333) * 0.25;
        else if (grossSalary < 166667) return 10833 + (grossSalary - 66667) * 0.3;
        else if (grossSalary < 666667) return 40833.33 + (grossSalary - 166667) * 0.32;
        else return 200833.33 + (grossSalary - 666667) * 0.35;
    }
    
}
