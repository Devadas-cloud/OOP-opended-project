import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class carreg extends JFrame {

  
    private JTextField customerNameField, phoneField, emailField;
    private JComboBox<String> vehicleModelComboBox;
    private JRadioButton petrolRadioButton, dieselRadioButton, electricRadioButton;
    private JCheckBox voterIdCheckBox, adhaarCheckBox, licenseCheckBox;
    private JButton submitButton, clearButton;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public carreg() {
 
        setTitle("Porsche Car Registration Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);  
   
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel vehicleModelLabel = new JLabel("Vehicle Model:");
        vehicleModelComboBox = new JComboBox<>(new String[]{"Porsche 911", "Porsche Cayenne", "Porsche Macan","porsche panamera","porsche Taycan"});
        JLabel vehicleTypeLabel = new JLabel("Vehicle Type:");
        petrolRadioButton = new JRadioButton("Petrol");
        dieselRadioButton = new JRadioButton("Diesel");
        electricRadioButton = new JRadioButton("Electric");

        ButtonGroup vehicleTypeGroup = new ButtonGroup();
        vehicleTypeGroup.add(petrolRadioButton);
        vehicleTypeGroup.add(dieselRadioButton);
        vehicleTypeGroup.add(electricRadioButton);

        JLabel idProofLabel = new JLabel("ID Proof:");
        voterIdCheckBox = new JCheckBox("Voter ID");
        adhaarCheckBox = new JCheckBox("Aadhar");
        licenseCheckBox = new JCheckBox("License");

        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");

        
        customerNameLabel.setBounds(30, 20, 120, 25);
        customerNameField.setBounds(150, 20, 200, 25);
        phoneLabel.setBounds(30, 60, 120, 25);
        phoneField.setBounds(150, 60, 200, 25);
        emailLabel.setBounds(30, 100, 120, 25);
        emailField.setBounds(150, 100, 200, 25);
        vehicleModelLabel.setBounds(30, 140, 120, 25);
        vehicleModelComboBox.setBounds(150, 140, 200, 25);
        vehicleTypeLabel.setBounds(30, 180, 120, 25);
        petrolRadioButton.setBounds(150, 180, 80, 25);
        dieselRadioButton.setBounds(230, 180, 80, 25);
        electricRadioButton.setBounds(310, 180, 80, 25);
        idProofLabel.setBounds(30, 220, 120, 25);
        voterIdCheckBox.setBounds(140, 220, 100, 25);
        adhaarCheckBox.setBounds(240, 220, 80, 25);
        licenseCheckBox.setBounds(320, 220, 80, 25);
        submitButton.setBounds(50, 280, 120, 30);
        clearButton.setBounds(200, 280, 120, 30);

       
        add(customerNameLabel);
        add(customerNameField);
        add(phoneLabel);
        add(phoneField);
        add(emailLabel);
        add(emailField);
        add(vehicleModelLabel);
        add(vehicleModelComboBox);
        add(vehicleTypeLabel);
        add(petrolRadioButton);
        add(dieselRadioButton);
        add(electricRadioButton);
        add(idProofLabel);
        add(voterIdCheckBox);
        add(adhaarCheckBox);
        add(licenseCheckBox);
        add(submitButton);
        add(clearButton);

       
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String customerName = customerNameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String vehicleModel = (String) vehicleModelComboBox.getSelectedItem();
                String vehicleType = "";
                if (petrolRadioButton.isSelected()) {
                    vehicleType = "Petrol";
                } else if (dieselRadioButton.isSelected()) {
                    vehicleType = "Diesel";
                } else if (electricRadioButton.isSelected()) {
                    vehicleType = "Electric";
                }
                String idProof = "";
                if (voterIdCheckBox.isSelected()) {
                    idProof += "Voter ID, ";
                }
                if (adhaarCheckBox.isSelected()) {
                    idProof += "Aadhar, ";
                }
                if (licenseCheckBox.isSelected()) {
                    idProof += "License, ";
                }

                if (!idProof.isEmpty()) {
                    idProof = idProof.substring(0, idProof.length() - 2);
                }


                saveRegistrationDetails(customerName, phone, email, vehicleModel, vehicleType, idProof);
            }
        });


        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                customerNameField.setText("");
                phoneField.setText("");
                emailField.setText("");
                vehicleModelComboBox.setSelectedIndex(0);
                vehicleTypeGroup.clearSelection();
                voterIdCheckBox.setSelected(false);
                adhaarCheckBox.setSelected(false);
                licenseCheckBox.setSelected(false);
            }
        });
    }

    private void saveRegistrationDetails(String customerName, String phone, String email, String vehicleModel, String vehicleType, String idProof) {
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/porshe", "root", "password");
            String query = "INSERT INTO RegistrationDetails (customerName, phone, email, vehicleModel, vehicleType, idProof) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, vehicleModel);
            preparedStatement.setString(5, vehicleType);
            preparedStatement.setString(6, idProof);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }


            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new carreg().setVisible(true);
            }
        });
    }
}
