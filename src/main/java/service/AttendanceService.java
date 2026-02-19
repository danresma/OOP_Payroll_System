/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

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

/**
 *
 * @author Administrator
 */
public class AttendanceService {
    private final DataService dataService;

    public AttendanceService(DataService dataService) {
        this.dataService = dataService;
    }
    
    
    
    
    public boolean hasAttendance(String EmpNum, int month, int year){
        List <String[]> records = dataService.readData("attendance");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (String [] record : records){
            if(!record[0].equals (EmpNum))continue;
            LocalDate date = LocalDate.parse(record[3], dateFormatter);
            
            if (date.getMonthValue() == month && date.getYear() == year)
                return true;}
                return false;
        }
    
    public List<Map<String, Object>> calculateWeeklyHours(String EmpNum, int month, int year, float hourlyRate) {
        List<String[]> records = dataService.readData("attendance");
        List<Map<String, Object>> weeklySummaries = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        WeekFields weekFields = WeekFields.ISO;
        
         Map<Integer, Map<String, Object>> weekData = new TreeMap<>();

        for (String[] record : records) {
            if (!record[0].equals(EmpNum)) continue;
            LocalDate date = LocalDate.parse(record[3], dateFormatter);
            if (date.getMonthValue() != month || date.getYear() != year) continue;

            int weekNum = date.get(weekFields.weekOfMonth());
            LocalTime logIn = LocalTime.parse(record[4], timeFormatter);
            LocalTime logOut = LocalTime.parse(record[5], timeFormatter);
            LocalTime startTime = LocalTime.of(8, 30);
            LocalTime endTime = LocalTime.of(17, 30);
            LocalTime gracePeriod = startTime.plusMinutes(10);

            float hoursWorked = 8.0f;
            float lateMinutes = 0f;
            float overtime = 0f;

            if (logIn.isAfter(startTime)) {
                float lateHours = Duration.between(startTime, logIn).toMinutes() / 60.0f;
                hoursWorked -= lateHours;
            }

            if (logIn.isAfter(gracePeriod)) {
                lateMinutes = Duration.between(gracePeriod, logIn).toMinutes();
                hoursWorked = Math.max(0f, hoursWorked);
            }

            if (logOut.isAfter(endTime)) {
                overtime = Duration.between(endTime, logOut).toMinutes() / 60.0f;
            }

            weekData.putIfAbsent(weekNum, new HashMap<>(Map.of(
                "workHours", 0f,
                "lateMinutes", 0f,
                "salary", 0f
            )));

            Map<String, Object> summary = weekData.get(weekNum);
            summary.put("workHours", (float) summary.get("workHours") + hoursWorked);
            summary.put("lateMinutes", (float) summary.get("lateMinutes") + lateMinutes);
            float weeklySalary = (hoursWorked * hourlyRate) + (!logIn.isAfter(gracePeriod) ? overtime * hourlyRate * 1.25f : 0f);
            summary.put("salary", (float) summary.get("salary") + weeklySalary);
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





}
        
    
    
    
    

