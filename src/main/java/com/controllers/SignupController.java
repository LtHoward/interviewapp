package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.Major;
import com.model.Role;
import com.model.UserManager;
import com.model.Year;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;



public class SignupController {
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<Major> majorComboBox;
    @FXML private ComboBox<Role> roleComboBox;
    @FXML private ComboBox<Year> yearComboBox;
    @FXML private Label errorLabel;
    @FXML private Button signinbutton;
    @FXML private Button loginbutton;
    @FXML private Button continuebutton;
    @FXML private StackPane overlaypane;
    @FXML private Button handlecontinue;
    @FXML private Button continuestep;


    private UserManager userManager = UserManager.getInstance();

    
    @FXML 
    private void initialize() {
        roleComboBox.getItems().addAll(Role.values());
        majorComboBox.getItems().addAll(Major.values());
        yearComboBox.getItems().addAll(Year.values());

        overlaypane.setVisible(false);
    }
    
     @FXML
    void handleComboBoxAction(ActionEvent event) {
        Role selectedRole = roleComboBox.getValue();
    }

    @FXML
    void handleMajor(ActionEvent event) throws IOException{
        Major selectedMajor = majorComboBox.getValue();
    }

    @FXML
    void handleYear(ActionEvent event) throws IOException{
        Year selectedYear = yearComboBox.getValue();
    }

    @FXML
    void switchtonext(ActionEvent event) throws IOException{
        Role role = roleComboBox.getValue();
        Major major = majorComboBox.getValue();
        Year year = yearComboBox.getValue();
        if (role == null) {
            return;
        }
        if(role == Role.STUDENT) {
            overlaypane.setVisible(true);
            overlaypane.setMouseTransparent(false);
        } else {
        overlaypane.setVisible(false);
        overlaypane.setMouseTransparent(true);
        }

        if (role == Role.CONTRIBUTOR || role == Role.ADMINISTRATOR) {
            App.setRoot("signup");
        }
    }

    @FXML
    void switchtosignup(ActionEvent event) throws IOException{
        App.setRoot("signup");
    }

    @FXML 
    private void handlesignup(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        
        Role role = roleComboBox.getValue();
        Major major = majorComboBox.getValue();
        Year year = yearComboBox.getValue();

        
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || role ==null ){
            return;
        } 

        if (role == Role.STUDENT && (major == null || year == null)) {
            return;
        }

        if (userManager.getUser(username) != null) {
            return;
        } 

        boolean success = userManager.addUser(username,email,password,firstname,lastname,role,major,year);


        if (!success) {
            return;
        } 

        if (role == Role.STUDENT) {
            App.setRoot("studentDashboard");
        } else {
            App.setRoot("dashboard");
        }
        
    }

    @FXML 
    private void switchToLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

}
