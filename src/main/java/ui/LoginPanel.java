/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;

/**
 * 
 * @author franzielle
 */

public class LoginPanel extends JPanel {
    
   public LoginPanel() {
        // set dimensions for the panel
        Dimension fixedSize = new Dimension(500, 500);
        this.setPreferredSize(fixedSize);
        this.setMinimumSize(fixedSize);
        this.setMaximumSize(fixedSize);
        
        this.setLayout(null); 
        this.setBackground(new Color(248, 249, 250)); // light modern gray

        // greeting subtitle
        JLabel greeting = new JLabel("Welcome to", SwingConstants.CENTER);
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 15));
        greeting.setBounds(0, 45, 500, 45);
        add(greeting);
                
        // header title
        JLabel title = new JLabel("MotorPH Payroll System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        title.setBounds(0, 65, 500, 45);
        add(title);
        
        // role selction
        JLabel roleLabel = new JLabel("Select Role");
        roleLabel.setBounds(100, 115, 300, 20);
        add(roleLabel);

        // role dropdown (JComboBox)
        String[] roles = {"Employee", "IT", "Manager", "Finance", "HR"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBounds(100, 140, 300, 35);
        roleBox.setBackground(Color.WHITE);
        roleBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(roleBox);

        // username label & field
        JLabel userLabel = new JLabel("Username");
       userLabel.setBounds(100, 190, 300, 20);
       add(userLabel);

       JTextField userField = new JTextField();
       userField.setBounds(100, 215, 300, 40);
       userField.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
       add(userField);

        // password label and field
        JLabel passLabel = new JLabel("Password");
       passLabel.setBounds(100, 270, 300, 20);
       add(passLabel);

       JPasswordField passField = new JPasswordField();
       passField.setBounds(100, 295, 300, 40);
       passField.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
       add(passField);

        // login button 
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 350, 300, 45);
        loginButton.setBackground(new Color(0x333f4f));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(loginButton);
   }

    /*
     * for test run only
     * transfer to main controller ( kapag meron na ^^ ) 
     * @param args
     */
   
    public static void main(String[] args) {
        JFrame frame = new JFrame("MotorPH Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false); 
        
        frame.add(new LoginPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

