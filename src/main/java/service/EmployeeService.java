/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import service.DataService;
import Model.Employee;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Administrator
 */
public class EmployeeService {
    
    //Abstraction: DataService for CSV handling
    private final DataService dataService;

    //Constructor
    public EmployeeService(DataService dataService) {
        this.dataService = dataService;
    }
    
    public Employee getEmployeeById (String employeeID){
        List<String []> employeeRecords = dataService.readData("employee");
        
        for (String [] data: employeeRecords){
         if(data[0].trim ().equals(employeeID)){
         return Employee.fromCSV(data);
         }
        }
        return null; // if not found
    }
        
        //Get employee details as string for display
        
      public String getEmployeeDetailsById(String employeeID) {
    Employee emp = getEmployeeById(employeeID);
    if (emp == null) {
        return "Employee details not found for ID: " + employeeID;
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Employee ID: ").append(emp.getEmpNum()).append("\n");
    sb.append("Name: ").append(emp.getFirstName()).append(" ").append(emp.getLastName()).append("\n");
    sb.append("Birthday: ").append(emp.getBirthDate()).append("\n");
    sb.append("SSS: ").append(emp.getSssNumber()).append("\n");
    sb.append("PhilHealth: ").append(emp.getPhilhealthNumber()).append("\n");
    sb.append("TIN: ").append(emp.getTin()).append("\n");
    sb.append("Pag-IBIG: ").append(emp.getPagibigNumber()).append("\n");
    sb.append("Status: ").append(emp.getStatus()).append("\n");
    sb.append("Position: ").append(emp.getPosition()).append("\n");
    sb.append("Basic Pay: ").append(emp.getBasicPay()).append("\n");
    sb.append("Rice Subsidy: ").append(emp.getRiceSub()).append("\n");
    sb.append("Phone Allowance: ").append(emp.getPhoneAl()).append("\n");
    sb.append("Clothing Allowance: ").append(emp.getClothAl()).append("\n");
    sb.append("Hourly Rate: ").append(emp.getHourlyRate()).append("\n");

    return sb.toString();
}
      
      
      // Get all employees as a list of Employee objects
    public List<Employee> getAllEmployees() {
        List<String[]> records = dataService.readData("employee");
        return records.stream()
                .map(Employee::fromCSV)
                .collect(Collectors.toList());
    }

    // Add a new employee
    public void addEmployee(Employee employee) {
        dataService.appendData("employee", employeeToCSV(employee));
    }

    // Update an existing employee
    public boolean updateEmployee(Employee employee) {
        return dataService.updateData("employee", employee.getEmpNum(), employeeToCSV(employee));
    }

    // Delete an employee by ID
    public void deleteEmployee(String employeeID) {
        dataService.deleteData("employee", employeeID);
    }

    // Convert Employee object to CSV row
    private String[] employeeToCSV(Employee emp) {
        return new String[]{
                emp.getEmpNum(),
                emp.getLastName(),
                emp.getFirstName(),
                emp.getBirthDate(),
                "", "", // Placeholder for unused columns if any
                emp.getSssNumber(),
                emp.getPhilhealthNumber(),
                emp.getTin(),
                emp.getPagibigNumber(),
                emp.getStatus(),
                emp.getPosition(),
                "", "", "", "", // Placeholder for other unused columns if needed
                String.valueOf(emp.getBasicPay()),
                String.valueOf(emp.getRiceSub()),
                String.valueOf(emp.getPhoneAl()),
                String.valueOf(emp.getClothAl()),
                String.valueOf(emp.getHourlyRate())
        };
    }
}
