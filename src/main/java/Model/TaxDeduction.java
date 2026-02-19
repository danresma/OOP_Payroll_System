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
        if (compensation <= 20832) return 0;
        else if (compensation < 33333) return (compensation - 20833) * 0.2;
        else if (compensation < 66667) return 2500 + (compensation - 33333) * 0.25;
        else if (compensation < 166667) return 10833 + (compensation - 66667) * 0.3;
        else if (compensation < 666667) return 40833.33 + (compensation - 166667) * 0.32;
        else return 200833.33 + (compensation - 666667) * 0.35;
    }
    
}
