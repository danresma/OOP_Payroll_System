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
public class EmployeeData {
    public static String getEmployeeDetailsByID(String employeeID) {
            List<String[]> employeeRecords = CSVHandler.readCSV("employee");

            for (String[] data : employeeRecords) {
                if (data[0].trim().equals(employeeID)) {
                    return "Employee ID: " + data[0] + "\n"
                            + "Name: " + data[1] + ", " + data[2] + "\n"
                            + "Birthday: " + data[3] + "\n"
                            + "SSS :" + data [6] + "\n"
                            + "Philhealth:" + data [7] + "\n"
                            + "Tin:" + data [8] + "\n"
                            + "Pag-Ibig:" + data [9] + "\n"
                            + "Status: " + data[10] + "\n"
                            + "Position: " + data[11] + "\n"
                            + "Rice Subsidy: " + data[14] + "\n"
                            + "Phone Allowance: " + data[15] + "\n"
                            + "Clothing Allowance: " + data[16] + "\n"
                            + "Hourly Rate: " + data[18] + "\n";

                }
            }

            return "Employee details not found for ID: " + employeeID;
        }

        public static String[] getEmployeeDataByID(String employeeID) {
        List<String[]> employeeRecords = CSVHandler.readCSV("employee");

        for (String[] data : employeeRecords) {
            if (data[0].trim().equals(employeeID)) {
                return data; // Return raw data array
            }
        }
        return null; // Not found
    }

    }
    

