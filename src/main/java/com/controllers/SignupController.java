package com.controllers;

import com.model.UserManager;

import javafx.fxml.FXML;
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


    private UserManager userManager = UserManager.getInstance();

    

}
