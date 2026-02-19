/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */
public class SSSDeduction extends DeductionService {

    @Override
    public double Calculate(Employee employee, double grossSalary) {
    if (grossSalary < 3250) return 135;
        else if (grossSalary < 3750) return 157.5;
        else if (grossSalary < 4250) return 180;        
        else if (grossSalary < 4750) return 202.5;
        else if (grossSalary < 5250) return 225;
        else if (grossSalary < 5750) return 247.5;
        else if (grossSalary < 6250) return 270;
        else if (grossSalary < 6750) return 292.5;
        else if (grossSalary < 7250) return 315;
        else if (grossSalary < 7750) return 337.5;
        else if (grossSalary < 8250) return 360;
        else if (grossSalary < 8750) return 382.5;
        else if (grossSalary < 9250) return 405;
        else if (grossSalary < 9750) return 427.5;
        else if (grossSalary < 10250) return 450;
        else return 472.5;
    
        
    }
    
    
    
    
}
