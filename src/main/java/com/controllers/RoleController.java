package com.controllers;

import java.io.IOException;

import com.interviewapp.App;

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
    private ComboBox<String> yearComboBox;

    @FXML 
    private ComboBox<String> roleComboBox;

    @FXML 
    private TextField majorField;

    @FXML 
    private TextField roleField;

    @FXML 
    private TextField yearField;

    @FXML 
    private void initialize() {
        roleComboBox.getItems().addAll("Student", "Contributor", "Administrator");
        majorComboBox.getItems().addAll("Computer Science", "Computer Engineering", "Computer Information Systems");
        yearComboBox.getItems().addAll("Freshman", "Sophomore", "Junior", "Senior", "Graduate");

        overlaypane.setVisible(false);

    }

    @FXML
    void handleComboBoxAction(ActionEvent event) {
        String selectedRole = roleComboBox.getValue();
        if (selectedRole != null) {
            roleField.setText(selectedRole);
        }
    }

    @FXML
    void handleMajor(ActionEvent event) throws IOException{
        String selectedMajor = majorComboBox.getValue();
        if (selectedMajor != null) {
            majorField.setText(selectedMajor);

        }
    }

    @FXML
    void handleYear(ActionEvent event) throws IOException{
        String selectedYear = yearComboBox.getValue();
        if (selectedYear != null) {
            yearField.setText(selectedYear);
        }
    }

    @FXML
    void switchtonext(ActionEvent event) throws IOException{
        String selectedRole = roleComboBox.getValue();
        String selectedMajor = majorComboBox.getValue();
        String selectedYear = yearComboBox.getValue();
        if (selectedRole == null) {
            return;
        }
        if("Student".equals(selectedRole)) {
            overlaypane.setVisible(true);
            overlaypane.setMouseTransparent(false);
        } else {
        overlaypane.setVisible(false);
        overlaypane.setMouseTransparent(true);
        }

        if ("Contributor".equals(selectedRole) || "Administrator".equals(selectedRole)) {
            App.setRoot("signup");
        }
    }

    @FXML
    void switchtosignup(ActionEvent event) throws IOException{
        App.setRoot("signup");
    }
}
