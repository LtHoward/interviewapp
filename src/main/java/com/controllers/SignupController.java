package com.controllers;

import java.io.IOException;

import com.model.UserManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;



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
        String 
        
    }

}
