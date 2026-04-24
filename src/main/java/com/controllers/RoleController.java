package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;




public class RoleController {
    @FXML
    private Button continuestep;

    @FXML
    private Button handlecontinue;

    @FXML
    private ComboBox<String> majorComboBox;

    @FXML
    private StackPane overlaypane;

    @FXML
    private ComboBox<String> roleoption;

    @FXML 
    private ComboBox<String> yearComboBox;

    @FXML private ComboBox<String> roleComboBox;

    @FXML 
    private TextField majorField;

    @FXML 
    private TextField roleField;

    @FXML 
    private TextField yearField;

    @FXML 
    private void intialize() {
        roleComboBox.getItems().addAll("Student", "Contributor", "Administrator");
    }

    @FXML
    void handleComboBoxAction(ActionEvent event) {
        String selectedRole = roleComboBox.getValue();
        if (selectedRole != null) {
            roleField.setText(selectedRole);
        }

        if (selectedRole.equals("Student")) {
            majorField.setVisible(true);
            yearField.setVisible(true);
        } else {
            majorField.setVisible(false);
            yearField.setVisible(false);
        }
        
        if (selectedRole.equals("Contributor") || selectedRole.equals("Administrator")) {
            majorField.setVisible(false);
            yearField.setVisible(false);
        }
    }

    @FXML
    void handleMajor(ActionEvent event) {

    }

    @FXML
    void handleYear(ActionEvent event) {

    }

    @FXML
    void switchtonext(ActionEvent event) {

    }

    @FXML
    void switchtosignup(ActionEvent event) {

    }
}
