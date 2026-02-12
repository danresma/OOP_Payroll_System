/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */


// trying to convert into encapsulation yung EmployeeData
public class Employee {
        
        private String empNum;
        private String firstName;
        private String lastName;
        private String birthDate;
        private String sssNumber;
        private String philhealthNumber;
        private String Tin;
        private String pagibigNumber;
        private String status;
        private String position;
        private float basicPay;
        private float riceSub;
        private float phoneAl;
        private float clothAl;
        private float hourlyRate;

        //constractor

    public Employee(String empNum, String firstName, String lastName, String birthDate, String sssNumber, String philhealthNumber, String Tin, String pagibigNumber, String status, String position, float basicPay, float riceSub, float phoneAl, float clothAl, float hourlyRate) {
        this.empNum = empNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.Tin = Tin;
        this.pagibigNumber = pagibigNumber;
        this.status = status;
        this.position = position;
        this.basicPay = basicPay;
        this.riceSub = riceSub;
        this.phoneAl = phoneAl;
        this.clothAl = clothAl;
        this.hourlyRate = hourlyRate;
    }
    
    //Getters
    public String getEmpNum() {
        return empNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getSssNumber() {
        return sssNumber;
    }

    public String getPhilhealthNumber() {
        return philhealthNumber;
    }

    public String getTin() {
        return Tin;
    }

    public String getPagibigNumber() {
        return pagibigNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getPosition() {
        return position;
    }

    public float getBasicPay() {
        return basicPay;
    }

    public float getRiceSub() {
        return riceSub;
    }

    public float getPhoneAl() {
        return phoneAl;
    }

    public float getClothAl() {
        return clothAl;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }
    
    //setters

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setSssNumber(String sssNumber) {
        this.sssNumber = sssNumber;
    }

    public void setPhilhealthNumber(String philhealthNumber) {
        this.philhealthNumber = philhealthNumber;
    }

    public void setTin(String Tin) {
        this.Tin = Tin;
    }

    public void setPagibigNumber(String pagibigNumber) {
        this.pagibigNumber = pagibigNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setBasicPay(float basicPay) {
        this.basicPay = basicPay;
    }

    public void setRiceSub(float riceSub) {
        this.riceSub = riceSub;
    }

    public void setPhoneAl(float phoneAl) {
        this.phoneAl = phoneAl;
    }

    public void setClothAl(float clothAl) {
        this.clothAl = clothAl;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
        
   

   
        
     public static Employee fromCSV (String[]data){
     return new Employee(
            data[0],  // Employee #
            data[2],  // First Name
            data[1],  // Last Name
            data[3],  // Birthday
            data[6],  // SSS #
            data[7],  // Philhealth #
            data[8],  // TIN #
            data[9],  // Pag-ibig #
            data[10], // Status
            data[11], // Position
            Float.parseFloat(data[13]), // Basic Salary 
            Float.parseFloat(data[14]), // Rice Subsidy
            Float.parseFloat(data[15]), // Phone Allowance
            Float.parseFloat(data[16]), // Clothing Allowance
            Float.parseFloat(data[18])  // Hourly Rate
             );
     
     }
     
    public String getSummary(){
        return "Employee ID:" + empNum + "\n"
                + "Name: " + firstName + " " + lastName + "\n"
                + "Birth Date: " + birthDate + "\n"
                +"SSS: " + sssNumber + "PhilHealth : " + philhealthNumber + "\n"
                + "TIN: " + Tin + "Pag-IBIG: " + pagibigNumber + "\n"
                + "Status: " + status + ", Position: " + position +"\n"
                + "Basic Pay: " + basicPay + "Hourly Rate: " + hourlyRate;
                 }


}
        
        
    
    
    
    

