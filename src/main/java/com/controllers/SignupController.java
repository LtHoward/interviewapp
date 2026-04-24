package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.UserManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML private Label errorLabel;
    @FXML private Button signinbutton;
    @FXML private Button loginbutton;


    private UserManager userManager = UserManager.getInstance();

    @FXML 
    private void handlesignup() throws IOException {
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
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

}
