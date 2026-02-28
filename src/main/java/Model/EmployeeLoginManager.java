/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.oop_motorph_payroll_system.EmployeeDashboard;
import com.mycompany.oop_motorph_payroll_system.Employee_Dashboard;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import service.DataService;

/**
 *
 * @author Administrator
 */
public class EmployeeLoginManager {
     public static void showLoginScreen(DataService dataService) {

        JFrame frame = new JFrame("Employee Login");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Employee ID:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {

            String username = userField.getText();
            String password = new String(passField.getPassword());

            boolean valid = dataService.validateUser(
                    "employee.csv",
                    username,
                    password
            );

            if (valid) {

                frame.dispose();

                Employee_Dashboard.showDashboard(username);

            } else {

                JOptionPane.showMessageDialog(frame,"Invalid Employee Login");
            }

        });

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));

        panel.add(userLabel);
        panel.add(userField);

        panel.add(passLabel);
        panel.add(passField);

        panel.add(new JLabel());
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
    

