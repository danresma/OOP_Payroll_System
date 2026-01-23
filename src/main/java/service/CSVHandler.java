/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

/**
 *
 * @author Administrator
 */
public class CSVHandler {
    
    
     
      private static final Map<String, String> filePaths = new HashMap<>();
    static {
        filePaths.put("employee", "src/main/resources/EmployeeDetails.csv");
        filePaths.put("attendance", "src/main/resources/AttendanceRecord.csv");
        filePaths.put("payroll", "src/main/resources/PayrollReport.csv");
        filePaths.put("useraccount", "src/main/resources/USERACCOUNT.csv");
    }

    // Read CSV without header (for display purposes)
    public static List<String[]> readCSV(String key) {
        String filePath = filePaths.get(key);
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            boolean isHeader = true;
            while ((nextLine = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header for display
                }
                records.add(nextLine);
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return records;
    }

    // Read CSV including header (for editing: delete/update)
    public static List<String[]> readCSVWithHeader(String key) {
        String filePath = filePaths.get(key);
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                records.add(nextLine);  // keep header included
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return records;
    }

    // Write CSV using OpenCSV (write all rows as is)
    public static void writeCSV(String filePath, List<String[]> data) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                writer.writeNext(row);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath);
            e.printStackTrace();
        }
    }

    // Append a new row to CSV when adding a new employee data
    public static void appendCSV(String key, String[] newData) {
        String filePath = filePaths.get(key);
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {
            writer.writeNext(newData);
        } catch (IOException e) {
            System.err.println("Error appending to file: " + filePath);
            e.printStackTrace();
        }
    }

    // Delete an employee row by employeeID
    public static void deleteEmployeeByID(String fileName, String employeeID) {
        List<String[]> allData = readCSVWithHeader(fileName);
        if (allData.isEmpty()) {
            return; // empty file, nothing to delete
        }

        String[] header = allData.get(0);
        List<String[]> dataRows = new ArrayList<>(allData.subList(1, allData.size()));

        dataRows.removeIf(row -> row.length > 0 && row[0].equals(employeeID));
        // Adds back the header then the remaining data, rows
        List<String[]> updatedData = new ArrayList<>();
        updatedData.add(header);
        updatedData.addAll(dataRows);

        String filePath = filePaths.get(fileName);
        writeCSV(filePath, updatedData);
    }

    // Update employee by ID, replace row with updatedData
    public static boolean updateEmployeeByID(String fileName, String employeeID, String[] updatedData) {
        List<String[]> allData = readCSVWithHeader(fileName);
        if (allData.isEmpty()) {
            return false; // empty file, cannot update
        }

        String[] header = allData.get(0);
        List<String[]> dataRows = new ArrayList<>(allData.subList(1, allData.size()));
        boolean updated = false;

        for (int i = 0; i < dataRows.size(); i++) {
            String[] row = dataRows.get(i);
            if (row.length > 0 && row[0].equals(employeeID)) {
                dataRows.set(i, updatedData);
                updated = true;
                break;
            }
        }

        if (updated) {
            List<String[]> updatedDataList = new ArrayList<>();
            updatedDataList.add(header);
            updatedDataList.addAll(dataRows);

            String filePath = filePaths.get(fileName);
            writeCSV(filePath, updatedDataList);
        }

        return updated;
    }
    
}
