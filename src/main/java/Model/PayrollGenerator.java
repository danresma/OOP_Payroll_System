/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.oop_motorph_payroll_system.OOP_MotorPh_Payroll_System;
import service.PayrollService;

import dao.CSVHandler;
import java.util.List;
import java.util.Map;
import service.AttendanceService;
import service.DataService;
import service.DeductionService;
import service.EmployeeService;

/**
 *
 * @author Administrator
 */
public class PayrollGenerator implements PayrollService {
    
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final DeductionService deductionService;
    
    // Constractor
    public PayrollGenerator(DataService dataService) {
        this.employeeService = new EmployeeService(dataService);
        this.attendanceService = new AttendanceService(dataService);
        this.deductionService = new DeductionService();
    }
    
    //Implements interface
    
    @Override
    public String generatePayroll(Employee emp, String monthName, String selectedYear) {
        try {
            int month = OOP_MotorPh_Payroll_System.monthNameToNumber(monthName);
            
            if (emp == null) return "Employee not found.";

            if (!attendanceService.hasAttendance(emp.getEmpNum(), month, Integer.parseInt(selectedYear)))
                return "No attendance records found for " + monthName + " " + selectedYear + ".";

            List<Map<String, Object>> weeklySummaries = attendanceService.calculateWeeklyHours(
                    emp.getEmpNum(), month, Integer.parseInt(selectedYear), emp.getHourlyRate()
            );

            float grossSalary = 0, totalLate = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("Payroll Summary for ").append(emp.getFirstName()).append(" ").append(emp.getLastName()).append("\n");

            for (Map<String, Object> week : weeklySummaries) {
                grossSalary += (float) week.get("salary");
                totalLate += ((float) week.get("lateMinutes") / 60f) * emp.getHourlyRate();
            }

            double sssDeduction = deductionService.calculateSSS(grossSalary);
            double philHealth = deductionService.calculatePhilHealth(emp.getBasicPay());
            double pagIbig = deductionService.calculatePagIbig();
            double tax = deductionService.calculateTax(grossSalary);
            double totalAllowance = emp.getRiceSub() + emp.getPhoneAl() + emp.getClothAl();
            double totalDeductions = sssDeduction + philHealth + pagIbig + tax + totalLate;
            double netPay = grossSalary + totalAllowance - totalDeductions;

            sb.append(String.format("Gross Salary: %.2f\n", grossSalary));
            sb.append(String.format("Total Deductions: %.2f\n", totalDeductions));
            sb.append(String.format("Net Pay: %.2f\n", netPay));

            return sb.toString();

        } catch (Exception e) {
            return "Error generating payroll: " + e.getMessage();
        }
    }
}

