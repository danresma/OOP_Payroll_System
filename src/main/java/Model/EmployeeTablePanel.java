/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import service.CSVHandler;

/**
 *
 * @author Administrator
 */
public class EmployeeTablePanel extends JPanel {
    
    private DefaultTableModel model;
    private JButton viewButton;
    private JButton deleteButton;
    private JButton updateButton;

    public EmployeeTablePanel() {
        setLayout(new BorderLayout());
        String[] columnNames = {
                "Employee Number", "Last Name", "First Name",
                "SSS Number", "PhilHealth Number", "TIN", "Pag-IBIG Number"
        };
         model = new DefaultTableModel(columnNames, 0) {
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // disables editing
        }
        };
        loadData();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        table.getTableHeader().setReorderingAllowed(false);

        // Add New Employee Button and Action
        JButton addButton = new JButton("Add New Employee");
        addButton.setPreferredSize(new Dimension(160, 30));
        addButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "addEmployee");
        });

        //Update Button and Action
        updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setEnabled(false); // Initially disabled

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Please select an employee.",
                        "No Selection", JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Get the Data
            String employeeID = (String) model.getValueAt(selectedRow, 0);
            String lastName = (String) model.getValueAt(selectedRow, 1);
            String firstName = (String) model.getValueAt(selectedRow, 2);
            String sssNumber = (String) model.getValueAt(selectedRow, 3);
            String philHealthNumber = (String) model.getValueAt(selectedRow, 4);
            String tin = (String) model.getValueAt(selectedRow, 5);
            String pagIbigNumber = (String) model.getValueAt(selectedRow, 6);

            // Launch the Edit Employee Dialog
            EmployeeEditDialog dialog = new EmployeeEditDialog(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    employeeID, lastName, firstName, sssNumber, philHealthNumber, tin, pagIbigNumber
            );
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                String[] updatedData = dialog.getUpdatedData(employeeID);

                // Update the table
                for (int i = 1; i < updatedData.length; i++) {
                    model.setValueAt(updatedData[i], selectedRow, i);
                }

                // Update CSV file
                boolean success = CSVHandler.updateEmployeeByID("employee", employeeID, updatedData);
                if (success) {
                    JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(this),
                            "Employee updated successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(this),
                            "Failed to update employee in CSV file.",
                            "Error", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        //Delete button
        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setEnabled(false); // Initially disabled

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1){
                int confirm = JOptionPane.showConfirmDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Are you sure you want to delete the selected employee?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION){
                    String employeeID = (String) model.getValueAt(selectedRow, 0);

                    //Deleted data from the CSV File
                    CSVHandler.deleteEmployeeByID("employee", employeeID);

                    //Delete Data on the Table
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog( SwingUtilities.getWindowAncestor(this),
                            "Employee deleted successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE
                    );

                }}

            else {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Please select an employee to delete",
                        "No Selection", JOptionPane.WARNING_MESSAGE
                );
            }
        });

        // Back button to return to main card
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> {

            // Reset button states before going back
            viewButton.setEnabled(false);
            deleteButton.setEnabled(false);
            updateButton.setEnabled(false);

            //CLear the table selection
            table.clearSelection();

            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "home");
        });

        // View Selected Employee Button and Action
        viewButton = new JButton("View Selected Employee");
        viewButton.setPreferredSize(new Dimension(180, 30));
        viewButton.setEnabled(false); // Initially disabled

        viewButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String employeeID = (String) table.getValueAt(selectedRow, 0);
                showPayrollGenerationDialog(employeeID);
            } else {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Please select an employee first."
                );
            }
        });

        // Add selection listener to the table
        table.addMouseListener(new MouseAdapter() {
           
            @Override
            public void mouseClicked(MouseEvent e) {
                updateButtonStates(table);
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates(table);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initial button state update
        updateButtonStates(table);
    }

    private void updateButtonStates(JTable table) {
        boolean hasSelection = table.getSelectedRow() != -1;
        boolean hasData = model.getRowCount() > 0;

        viewButton.setEnabled(hasSelection && hasData);
        deleteButton.setEnabled(hasSelection && hasData);
        updateButton.setEnabled(hasSelection && hasData);
    }

    // fetch all employee records from the csv file
    private void loadData() {
        model.setRowCount(0); // Clear existing rows
        List<String[]> employeeData = CSVHandler.readCSV("employee");
        for (String[] data : employeeData) {
            if (data.length >= 10) {
                String[] rowData = new String[7];
                rowData[0] = data[0]; // Employee Number
                rowData[1] = data[1]; // Last Name
                rowData[2] = data[2]; // First Name
                rowData[3] = data[6]; // SSS Number
                rowData[4] = data[7]; // PhilHealth Number
                rowData[5] = data[8]; // TIN Number
                rowData[6] = data[9]; // Pag-IBIG Number
                model.addRow(rowData);
            }
        }
    }

    // Public method to refresh the table after adding a new employee
    public void refreshTable() {
        loadData();
    }

    private void showPayrollGenerationDialog(String employeeID) {
        // Get employee details
        String[] employeeData = EmployeeData.getEmployeeDataByID(employeeID);
        if (employeeData == null) {
            JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(this),
                    "Employee details not found for ID: " + employeeID
            );
            return;
        }

        // Create dialog
        JDialog payrollDialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Generate Payroll for Employee: " + employeeID,
                true
        );
        payrollDialog.setLayout(new BorderLayout(10, 10));
        payrollDialog.setPreferredSize(new Dimension(650, 800));

        // Employee details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Employee Information"));
        JTextArea detailsArea = new JTextArea(EmployeeData.getEmployeeDetailsByID(employeeID));
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        // Date selection panel
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        datePanel.setBorder(BorderFactory.createTitledBorder("Select Payroll Period"));

        // Month selection
        JLabel monthLabel = new JLabel("Month:");
        datePanel.add(monthLabel);
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        JComboBox<String> monthDropdown = new JComboBox<>(months);
        datePanel.add(monthDropdown);

        // Year selection
        JLabel yearLabel = new JLabel("Year:");
        datePanel.add(yearLabel);
        String[] years = {"2020", "2021", "2022", "2023", "2024", "2025"};
        JComboBox<String> yearDropdown = new JComboBox<>(years);
        datePanel.add(yearDropdown);

        // Generate button
        JButton generateButton = new JButton("Compute");
        generateButton.setPreferredSize(new Dimension(150, 30));

        // Result area
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setBorder(BorderFactory.createTitledBorder("Payroll Details"));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(generateButton);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(detailsPanel, BorderLayout.NORTH);
        contentPanel.add(datePanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to dialog
        payrollDialog.add(contentPanel, BorderLayout.NORTH);
        payrollDialog.add(resultScrollPane, BorderLayout.CENTER);

        // Action listener for generate button
        generateButton.addActionListener((ActionEvent e) -> {
            String selectedMonth = (String) monthDropdown.getSelectedItem();
            String selectedYear = (String) yearDropdown.getSelectedItem();

            String payroll = PayrollGenerator.generatePayroll(employeeData, selectedMonth, selectedYear);
            resultArea.setText(payroll);
        });

        payrollDialog.pack();
        payrollDialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        payrollDialog.setVisible(true);
    }
}
    

