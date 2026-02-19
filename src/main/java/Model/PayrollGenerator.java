/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.oop_motorph_payroll_system.OOP_MotorPh_Payroll_System;
import service.PayrollService;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import service.AttendanceService;
import service.DataService;
import service.EmployeeService;

/**
 *
 * @author Administrator
 */
public class PayrollGenerator implements PayrollService {
    
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final List<DeductionService> deductions;
    
    // Constractors
    public PayrollGenerator(DataService dataService) {
        this.employeeService = new EmployeeService(dataService);
        this.attendanceService = new AttendanceService(dataService);
        
        deductions = new ArrayList<> ();
        deductions.add(new SSSDeduction());
        deductions.add(new PhilhealthDeduction());
        deductions.add(new PagibigDeduction());
        deductions.add(new TaxDeduction());
        
        
        
    }
    
    //Implements interface
    
    @Override
    public String generatePayroll(Employee emp, String monthName, String selectedYear) {
         try {
        int month = OOP_MotorPh_Payroll_System.monthNameToNumber(monthName);

        if (emp == null) return "Employee not found.";

        // Check attendance
        if (!attendanceService.hasAttendance(emp.getEmpNum(), month, Integer.parseInt(selectedYear))) {
            return "No attendance records found for " + monthName + " " + selectedYear + ".";
        }

        // Get weekly summaries
        List<Map<String, Object>> weeklySummaries = attendanceService.calculateWeeklyHours(
            emp.getEmpNum(), month, Integer.parseInt(selectedYear), emp.getHourlyRate()
        );

        float grossSalary = 0, totalLate = 0;
        StringBuilder sb = new StringBuilder();

        // ------------------ Weekly Breakdown ------------------
        sb.append("Payroll Summary for ").append(emp.getFirstName()).append(" ").append(emp.getLastName()).append("\n");
        sb.append("Month: ").append(monthName).append(" ").append(selectedYear).append("\n\n");
        sb.append("------- Weekly Breakdown -------\n");

        for (Map<String, Object> week : weeklySummaries) {
            int weekNum = (int) week.get("week");
            float hoursWorked = (float) week.get("workHours");
            float lateMinutes = (float) week.get("lateMinutes");
            float weekSalary = (float) week.get("salary");

            grossSalary += weekSalary;
            totalLate += (lateMinutes / 60f) * emp.getHourlyRate();

            sb.append(String.format("Week %d:\n", weekNum));
            sb.append(String.format(" - Hours Worked: %.2f\n", hoursWorked));
            sb.append(String.format(" - Late Minutes: %.2f\n", lateMinutes));
            sb.append(String.format(" - Weekly Salary: %.2f\n", weekSalary));
        }
        sb.append("\n");
        // ---
            
        // Deductions & Allowances
        // OLD CODE
        // double sssDeduction = deductionService.calculateSSS(grossSalary);
        // double philHealth = deductionService.calculatePhilHealth(emp.getBasicPay());
        // double pagIbig = deductionService.calculatePagIbig();
        // double tax = deductionService.calculateTax(grossSalary);
        // double totalAllowance = emp.getRiceSub() + emp.getPhoneAl() + emp.getClothAl();
        // double totalDeductions = sssDeduction + philHealth + pagIbig + tax + totalLate;
        // double netPay = grossSalary + totalAllowance - totalDeductions;
        
        //New Code Upadte
        // Using Polymorphic 
        
        double totalDeductions =  0;
        for (DeductionService deduction : deductions){
            totalDeductions += deduction.Calculate(emp, grossSalary);
        }
        
        //Late
        totalDeductions += totalLate;
        
        //Allowances
        double totalAllowances = 
                emp.getRiceSub()+
                emp.getPhoneAl()+
                emp.getClothAl();
        
        

        // Totals
        sb.append(String.format("Gross Salary: %.2f\n", grossSalary));
        sb.append(String.format("Total Deductions: %.2f\n", totalDeductions));
        
        double netPay = grossSalary + totalAllowances - totalDeductions;
        
        sb.append(String.format("Net Pay: %.2f\n", netPay));

        return sb.toString();

    } catch (Exception e) {
        return "Error generating payroll: " + e.getMessage();
    }
    }}

