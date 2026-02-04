/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class EmployeeEditDialog extends JDialog {
    
    //encapsulations
    
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField sssNumberField;
    private JTextField philHealthNumberField;
    private JTextField tinField;
    private JTextField pagIbigNumberField;
    // A flag to indicate whether the user clicked "Save" (true) or "Cancel" (false)
    private boolean confirmed = false;
    
public EmployeeEditDialog(Frame parent, String employeeID, String lastName, String firstName,
                              String sssNumber, String philHealthNumber, String tin, String pagIbigNumber) {
    
    super(parent, "Edit Employee: "+ employeeID, true);
    
    // Layout and preferred size for the dialog window
    setLayout (new BorderLayout (10,10));
    setPreferredSize (new Dimension (400,300));
    
    // Panel for the input fields, arranged in a grid layout
    JPanel fieldsPanel = new JPanel (new GridLayout(6,2,10,10));
    fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Add label and text field for last name
    fieldsPanel.add(new JLabel ("Last Name: "));
    lastNameField = new JTextField (lastName);
    fieldsPanel.add (lastNameField);
    
    // Add label and text field for first name
    fieldsPanel.add(new JLabel("First Name:"));
    firstNameField = new JTextField(firstName);
    fieldsPanel.add (firstNameField);
    
    // Add label and text field for Government Details
    fieldsPanel.add(new JLabel("SSS Number:"));
    sssNumberField = new JTextField(sssNumber);
    fieldsPanel.add(sssNumberField);
    
    fieldsPanel.add(new JLabel("PhilHealth Number:"));
    philHealthNumberField = new JTextField(philHealthNumber);
    fieldsPanel.add(philHealthNumberField);

    fieldsPanel.add(new JLabel("TIN:"));
    tinField = new JTextField(tin);
    fieldsPanel.add(tinField);

    fieldsPanel.add(new JLabel("Pag-IBIG Number:"));
    pagIbigNumberField = new JTextField(pagIbigNumber);
    fieldsPanel.add(pagIbigNumberField);
    
     // Add the field panel to the center of the dialog
    add(fieldsPanel, BorderLayout.CENTER);
    
    // Create a button panel for Save and Cancel buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Save button action: confirm the changes and close the dialog
        saveButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        
        // Cancel button action: discard changes and close the dialog
        cancelButton.addActionListener(e -> {
            confirmed = false;
            setVisible(false);
        });
        
        // Add buttons to the panel and add the panel to the dialog
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Adjust size and center the dialog on screen
        pack();
        setLocationRelativeTo(parent);
    }
    

    //Returns true if the user clicked "Save", false otherwise.
    public boolean isConfirmed() {
        return confirmed;
    }
    
    //Getters
    //Returns the updated employee data in an array.
    public String[] getUpdatedData(String employeeID) {
        return new String[]{
                employeeID,
                lastNameField.getText().trim(),
                firstNameField.getText().trim(),
                sssNumberField.getText().trim(),
                philHealthNumberField.getText().trim(),
                tinField.getText().trim(),
                pagIbigNumberField.getText().trim()
        };
    }
    
    
}
