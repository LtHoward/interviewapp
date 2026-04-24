package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.UserManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class SignupController {
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField majorField;
    @FXML private TextField roleField;
    @FXML private TextField yearField;
    @FXML private Label errorLabel;
    @FXML private Button signinbutton;
    @FXML private Button loginbutton;
    @FXML private Button continuebutton;
    @FXML private ComboBox<String> roleComboBox;


    private UserManager userManager = UserManager.getInstance();

    
    @FXML 
    private void intialize() {
        errorLabel.setText("");
        roleComboBox.getItems().addAll("Student", "Contributor", "Administrator");
    }

    @FXML void handleComboBoxAction(ActionEvent event) {
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
    private void handlecontinue(ActionEvent event) throws IOException {
        String role = roleField.getText();
        String major = majorField.getText();
        if (role.equals("Student") || role.equals("Contributor") || role.equals("Administrator")) {
            App.setRoot("signup");
        } else {
            errorLabel.setText("Please select a vali role.");
        }
    }


    @FXML 
    private void handlesignup(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String major = majorField.getText();
        String role = roleField.getText();

        
        if (username.equals("") || email.equals("") || password.equals("") || firstname.equals("") || lastname.equals("") ) {
            errorLabel.setText("Sorry, You cannot leave blank fields.");
            return;
        }

        if (userManager.getUser(username) != null) {
            errorLabel.setText("Sorry, username is taken.");
            return;
        }


        if (!userManager.addUser(username, email, password, firstname, lastname, null, null, null)) {
            errorLabel.setText("Sorry, this user couldn't be created.");
            return;
        }
    }

    @FXML 
    private void switchToLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

}
