/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author franzielle
 */

public abstract class BaseDashboard extends JFrame {
    
    protected JPanel sidebar;
    protected JPanel contentArea;

    public BaseDashboard(String role) {
        
        // 1. setup window (frame)
        setTitle("MotorPH - " + role);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        // 2. setup sidebar (panel)
        sidebar = new JPanel();
        sidebar.setBounds(0, 0, 250, 600);
        sidebar.setBackground(new Color(0x333f4f));
        sidebar.setLayout(null);
        add(sidebar);

        // sidebar brand
        JLabel logo = new JLabel("MotorPH", SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBounds(0, 30, 250, 40);
        sidebar.add(logo);

        // 3. setup content area (panel)
        contentArea = new JPanel();
        contentArea.setBounds(250, 0, 750, 600);
        contentArea.setBackground(Color.WHITE);
        contentArea.setLayout(null);
        add(contentArea);
    }
}
