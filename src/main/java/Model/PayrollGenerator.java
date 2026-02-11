/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import service.PayrollService;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import dao.CSVHandler;
import service.DataService;

/**
 *
 * @author Administrator
 */
public class PayrollGenerator implements PayrollService {
    
    private static final DataService dataService = new CSVHandler();
    
    // SSS deduction 
    public static double sss(double sssContri) {
        if (sssContri < 3250) return 135.00;
        else if (sssContri < 3750) return 157.50;
        else if (sssContri < 4250) return 180.00;
        else if (sssContri < 4750) return 202.50;
        else if (sssContri < 5250) return 225.00;
        else if (sssContri < 5750) return 247.50;
        else if (sssContri < 6250) return 270.00;
        else if (sssContri < 6750) return 292.50;
        else if (sssContri < 7250) return 315.00;
        else if (sssContri < 7750) return 337.50;
        else if (sssContri < 8250) return 360.00;
        else if (sssContri < 8750) return 382.50;
        else if (sssContri < 9250) return 405.00;
        else if (sssContri < 9750) return 427.50;
        else if (sssContri < 10250) return 450.00;
        else return 472.50;
    }
    // tax deduction
    public static double tax(double compensation) {
        if (compensation <= 20832.00) return 0;
        else if (compensation < 33333.00) return (compensation - 20833.00) * 0.20;
        else if (compensation < 66667.00) return 2500.00 + (compensation - 33333.00) * 0.25;
        else if (compensation < 166667.00) return 10833.00 + (compensation - 66667.00) * 0.30;
        else if (compensation < 666667.00) return 40833.33 + (compensation - 166667.00) * 0.32;
        else return 200833.33 + (compensation - 666667.00) * 0.35;
    }

    

    
    
    public static Employee findEmployeeByNumber(String empNum) {
        List<String[]> employees = dataService.readData("employee");
        for (String[] values : employees) {
            if (values[0].trim().equals(empNum.trim())) {
                
                Employee emp = new Employee(
                values[0],                  // empNum
                values[1],                  // firstName
                values[2],                  // lastName
                values[3],                  // birthDate
                values[6],                  // sssNumber
                values[7],                  // philhealthNumber
                values[8],                  // TIN
                values[9],                  // pagibigNumber
                values[10],                 // status
                values[11],                 // position
                Float.parseFloat(values[12]), // basicPay
                Float.parseFloat(values[13]), // riceSub
                Float.parseFloat(values[14]), // phoneAl
                Float.parseFloat(values[15]), // clothAl
                Float.parseFloat(values[16])  // hourlyRate
                );
                
                return emp;
            }
        }
        return null;
    }
    
    // check the record if my csv has the selected month and year
    public static boolean hasAttendanceRecords(String empNum, String month, String year) {
    List<String[]> attendanceRecords = dataService.readData("attendance");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    for (String[] values : attendanceRecords) {
        if (values[0].trim().equals(empNum.trim())) {
            LocalDate recordDate = LocalDate.parse(values[3], dateFormatter);
            String recordMonth = String.format("%02d", recordDate.getMonthValue());
            String recordYear = String.valueOf(recordDate.getYear());

            if (recordMonth.equals(month) && recordYear.equals(year)) {
                return true;
            }
        }
    }
    return false;
}

    public static List<Map<String, Object>> calculateWeeklyPayroll(
            String empNum, String month, String year, float hourlyRate) {

        List<String[]> attendanceRecords = dataService.readData("attendance");
        List<Map<String, Object>> weeklySummaries = new ArrayList<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        WeekFields weekFields = WeekFields.ISO;

        Map<Integer, Map<String, Object>> weekData = new TreeMap<>();

        for (String[] values : attendanceRecords) {
            if (!values[0].equals(empNum.trim())) continue;

            LocalDate recordDate = LocalDate.parse(values[3], dateFormatter);
            if (!String.format("%02d", recordDate.getMonthValue()).equals(month) ||
                !String.valueOf(recordDate.getYear()).equals(year)) continue;

            int weekNum = recordDate.get(weekFields.weekOfMonth());
            LocalTime logIn = LocalTime.parse(values[4], timeFormatter);
            LocalTime logOut = LocalTime.parse(values[5], timeFormatter);
            LocalTime startTime = LocalTime.of(8, 30);
            LocalTime endTime = LocalTime.of(17, 30);
            LocalTime gracePeriod = startTime.plusMinutes(10);

            float hoursWorked = 8.0f;
            float overtime = 0.0f;
            float lateMinutes = 0.0f;
            
            //Late Computation
            //
            if (logIn.isAfter(startTime)) {
                 float lateHours = Duration.between(startTime, logIn).toMinutes() / 60.0f;
                hoursWorked -= lateHours;
            }

            if (logIn.isAfter(gracePeriod)) {
                lateMinutes = Duration.between(gracePeriod, logIn).toMinutes();
                
                //prevent negative  hours
            hoursWorked = Math.max(0.0f, hoursWorked);
            }

            if (logOut.isAfter(endTime)) {
                overtime = Duration.between(endTime, logOut).toMinutes() / 60.0f;
            }

            weekData.putIfAbsent(weekNum, new HashMap<>(Map.of(
                "workHours", 0.0f,
                "lateMinutes", 0.0f,
                "salary", 0.0f
            )));

            Map<String, Object> summary = weekData.get(weekNum);
            summary.put("workHours", (float) summary.get("workHours") + hoursWorked);
            summary.put("lateMinutes", (float) summary.get("lateMinutes") + lateMinutes);
            
            boolean eligibleForOvertime = !logIn.isAfter(gracePeriod);
            
            float overtimePay = eligibleForOvertime
            ? overtime * (hourlyRate * 1.25f)
            : 0.0f;
            
            float salary = (hoursWorked * hourlyRate) + overtimePay;
            summary.put("salary", (float) summary.get("salary") + salary);
        }

        for (Map.Entry<Integer, Map<String, Object>> entry : weekData.entrySet()) {
            weeklySummaries.add(Map.of(
                "week", entry.getKey(),
                "workHours", entry.getValue().get("workHours"),
                "lateMinutes", entry.getValue().get("lateMinutes"),
                "salary", entry.getValue().get("salary")
            ));
        }

        return weeklySummaries;
    }
    
    @Override   
    public String generatePayroll(Employee emp, String monthName, String selectedYear) {
        try {
           Map<String, String> monthMap = Map.ofEntries(
            Map.entry("January", "01"),
            Map.entry("February", "02"),
            Map.entry("March", "03"),
            Map.entry("April", "04"),
            Map.entry("May", "05"),
            Map.entry("June", "06"),
            Map.entry("July", "07"),
            Map.entry("August", "08"),
            Map.entry("September", "09"),
            Map.entry("October", "10"),
            Map.entry("November", "11"),
            Map.entry("December", "12")
            );
           
            String monthNum = monthMap.getOrDefault(monthName, "01");
            
            if (!hasAttendanceRecords(emp.getEmpNum(), monthNum, selectedYear)) {
             return "No attendance records found for " + monthName + " " + selectedYear + ".";
            }

            List<Map<String, Object>> weeklySummaries = calculateWeeklyPayroll(emp.getEmpNum(), monthNum, selectedYear, emp.getHourlyRate());

            float grossSalary = 0, totalLate = 0;

            StringBuilder sb = new StringBuilder();
            sb.append("======= Payroll Summary =======\n");
            sb.append("Employee No.: ").append(emp.getEmpNum()).append("\n");
            sb.append("Name: ").append(emp.getFirstName()).append(" ").append(emp.getLastName()).append("\n");
            sb.append("Birthdate: ").append(emp.getBirthDate()).append("\n");
            sb.append("Month: ").append(monthName).append("\n\n");

            sb.append("------- Weekly Breakdown -------\n");
            for (Map<String, Object> week : weeklySummaries) {
                int weekNum = (int) week.get("week");
                float hours = (float) week.get("workHours");
                float late = (float) week.get("lateMinutes");
                float weekSalary = (float) week.get("salary");

                grossSalary += weekSalary;
                totalLate += (late / 60.0f) * emp.getHourlyRate();

                sb.append(String.format("Week %d:\n", weekNum));
                sb.append(String.format(" - Hours Worked: %.2f\n", hours));
                sb.append(String.format(" - Late (minutes): %.2f\n", late));
                sb.append(String.format(" - Weekly Salary: %.2f\n", weekSalary));
            }
            sb.append("\n");

            double sssDeduction = sss(grossSalary);
            double philHealth = emp.getBasicPay() * 0.03;
            double pagIbig = 100;
            double taxTotal = tax(grossSalary);
            double totalAllowance = emp.getRiceSub() + emp.getPhoneAl() + emp.getClothAl();
            double totalDeductions = sssDeduction + philHealth + pagIbig + taxTotal + totalLate;
            double netPay = grossSalary + totalAllowance - totalDeductions;

            sb.append(String.format("Gross Salary (Monthly): %.2f\n", grossSalary));
            sb.append(String.format("Late Deduction: %.2f\n\n", totalLate));
            sb.append("Allowances:\n");
            sb.append(String.format("Rice Subsidy: %.2f\n", emp.getRiceSub()));
            sb.append(String.format("Phone Allowance: %.2f\n", emp.getPhoneAl()));
            sb.append(String.format("Clothing Allowance: %.2f\n", emp.getClothAl()));
            sb.append(String.format("Total Allowance: %.2f\n\n", totalAllowance));
            sb.append("Deductions:\n");
            sb.append(String.format("SSS: %.2f\n", sssDeduction));
            sb.append(String.format("PhilHealth: %.2f\n", philHealth));
            sb.append(String.format("Pag-Ibig: %.2f\n", pagIbig));
            sb.append(String.format("Tax: %.2f\n", taxTotal));
            sb.append(String.format("Late Deduction: %.2f\n", totalLate));
            sb.append(String.format("Total Deductions: %.2f\n\n", totalDeductions));
            sb.append(String.format("Net Pay: %.2f\n", netPay));
            sb.append("===============================\n");

            return sb.toString();

        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred while generating payroll.";
        }
    }
    
}
