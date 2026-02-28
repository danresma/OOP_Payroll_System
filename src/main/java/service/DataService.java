/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;


/**
 *
 * @author Administrator
 */
public interface DataService {
    
    
    
    List <String[]> readData(String key);
    List <String []> readDatawithHeader(String key);
    
    void writeData(String key, List<String []> data);
    void appendData(String key, String[] newData);
    boolean updateData(String key, String id, String[]updateData);
    void deleteData(String key, String id);
    
    //for User Validation
    
    default boolean validateUser(String key, String username, String password) {
        List<String[]> records = readData(key); // reads your CSV
        for (String[] row : records) {
            if (row.length >= 2) {
                String csvUser = row[0].trim();
                String csvPass = row[1].trim();
                if (csvUser.equals(username) && csvPass.equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    
}
