/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.oop_motorph_payroll_system.EmployeeTablePanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import dao.CSVHandler;
import service.DataService;

/**
 *
 * @author Administrator
 */
public class AddNewEmployeePanel extends JPanel {
    private JTextField[] fields;
    private String[] fieldLabels = {
            "Employee Number", "Last Name", "First Name",
            "Birthday", "Address", "Phone Number",
            "SSS Number", "PhilHealth Number", "TIN", "Pag-IBIG Number", "Status",
            "Position", "Immediate Supervisor", "Basic Salary",
            "Rice Subsidy", "Phone Allowance", "Clothing Allowance",
            "Gross Semi-monthly Rate", "Hourly Rate"
    };
    private JPanel cardPanel;
    private EmployeeTablePanel employeePanel;
    
    //Data Abstraction
    private final DataService dataService = new CSVHandler();

    public AddNewEmployeePanel(JPanel cardPanel, EmployeeTablePanel employeePanel) {
        this.cardPanel = cardPanel;
        this.employeePanel = employeePanel;

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        fields = new JTextField[fieldLabels.length];

        JLabel requiredNote = new JLabel("<html><font color='red'>*</font> Required field</html>");
        add(requiredNote, BorderLayout.NORTH);

        String nextEmployeeNumber = getNextEmployeeNumber();

        for (int i = 0; i < fieldLabels.length; i++) {
            fields[i] = new JTextField(); // Initialize all to avoid nulls

            boolean showField = switch (fieldLabels[i]) {
                case "Employee Number", "Last Name", "First Name",
                     "SSS Number", "PhilHealth Number", "TIN", "Pag-IBIG Number" -> true;
                default -> false;
            };

            if (!showField) continue;

            String labelText = fieldLabels[i];
            if (labelText.equals("Employee Number") || labelText.equals("Last Name") || labelText.equals("First Name")) {
                labelText += " <font color='red'>*</font>";
            }

            JLabel label = new JLabel("<html>" + labelText + ":</html>");

            if (fieldLabels[i].equals("Last Name") || fieldLabels[i].equals("First Name")) {
                label.setToolTipText("This field is required");
            }

            formPanel.add(label);

            if (fieldLabels[i].equals("Employee Number")) {
                fields[i].setText(nextEmployeeNumber);
                fields[i].setEditable(false);
            }

            formPanel.add(fields[i]);
        }

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String[] newEmployeeData = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                newEmployeeData[i] = fields[i].getText().trim();
            }

            if (fields[1].getText().trim().isEmpty() || fields[2].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Please fill in Last Name and First Name.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            dataService.appendData("employee", newEmployeeData);

            JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(this),
                    "New employee added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            employeePanel.refreshTable();
            clearForm();
            fields[0].setText(getNextEmployeeNumber());

            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, "employees");
        });

        cancelButton.addActionListener(e -> {
            clearForm();
            fields[0].setText(getNextEmployeeNumber());

            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, "employees");
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void clearForm() {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] != null && fields[i].isEditable()) {
                fields[i].setText("");
            }
        }
    }

    private String getNextEmployeeNumber() {
        try {
            List<String[]> employees = dataService.readData("employee");

            if (employees == null || employees.isEmpty()) {
                return "10001";
            }

            String lastNumber = "";
            for (int i = employees.size() - 1; i >= 0; i--) {
                String[] employee = employees.get(i);
                if (employee != null && employee.length > 0 && employee[0] != null) {
                    lastNumber = employee[0].trim();
                    if (!lastNumber.isEmpty() && lastNumber.matches("\\d+")) {
                        break;
                    }
                }
            }

            if (lastNumber.isEmpty() || !lastNumber.matches("\\d+")) {
                return "10001";
            }

            int nextNum = Integer.parseInt(lastNumber) + 1;
            return String.format("%05d", nextNum);

        } catch (Exception e) {
            e.printStackTrace();
            return "10001";
        }
    }
}
    

