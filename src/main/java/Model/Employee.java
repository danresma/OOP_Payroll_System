/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;
import service.CSVHandler;

/**
 *
 * @author Administrator
 */
public class Employee {
    
     private String empID;
    private String firstName;
    private String lastName;
    private String birthDate;
    private double sss;
    private double philHealth;
    private double tin;
    private double pagIbig;
    private String status;
    private String position;
    private double riceSub;
    private double phoneAl;
    private double clothAl;
    private double hourlyRate;

    // Constructor
    public Employee(String[] data) {
        this.empID = data[0];
        this.firstName = data[1];
        this.lastName = data[2];
        this.birthDate = data[3];
        this.sss = Double.parseDouble(data[6]);
        this.philHealth = Double.parseDouble(data[7]);
        this.tin = Double.parseDouble(data[8]);
        this.pagIbig = Double.parseDouble(data[9]);
        this.status = data[10];
        this.position = data[11];
        this.riceSub = Double.parseDouble(data[14]);
        this.phoneAl = Double.parseDouble(data[15]);
        this.clothAl = Double.parseDouble(data[16]);
        this.hourlyRate = Double.parseDouble(data[18]);
    }

    // Getter 
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public double getTotalAllowance() {
        return riceSub + phoneAl + clothAl;
    }

    // method to create an Employee by ID
    public static Employee getEmployeeByID(String empID) {
        List<String[]> employeeRecords = CSVHandler.readCSV("employee");
        for (String[] data : employeeRecords) {
            if (data[0].trim().equals(empID)) {
                return new Employee(data);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Employee ID: " + empID + "\n" +
               "Name: " + getFullName() + "\n" +
               "Birthday: " + birthDate + "\n" +
               "SSS: " + sss + "\n" +
               "PhilHealth: " + philHealth + "\n" +
               "TIN: " + tin + "\n" +
               "Pag-Ibig: " + pagIbig + "\n" +
               "Status: " + status + "\n" +
               "Position: " + position + "\n" +
               "Rice Subsidy: " + riceSub + "\n" +
               "Phone Allowance: " + phoneAl + "\n" +
               "Clothing Allowance: " + clothAl + "\n" +
               "Hourly Rate: " + hourlyRate + "\n";
    }
    
}
