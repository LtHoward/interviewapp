package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.InterviewApp;
import com.model.User;
import com.model.UserManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class ResetPasswordController {

    private final InterviewApp app = new InterviewApp();

    private User currentUser;

    private final UserManager userManager = UserManager.getInstance();

    @FXML 
    private Button switchtologin;

    @FXML
    private Button switchtosuccess;

    @FXML
    private Button bakctologin;

    @FXML 
    private PasswordField newPasswordField;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Button switchtoconfirm;

    @FXML
    private TextField emailField;

      @FXML
    private Button backtologin;

    @FXML
    private Button switchtonextstep;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        errorLabel.setText("");
    }

    @FXML
    void handlecontinue(ActionEvent event) throws IOException{
        String email = emailField.getText().trim();
        InterviewApp app = new InterviewApp();
        User user = userManager.getUser(email);
        for (User u : userManager.getUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                user = u;
                App.setRoot("createnewpassword");
            }
        }
        
        if (user == null) {
            errorLabel.setText("No account found with that email.");
        }
        
    }

    @FXML
    void switchtoconfirm(ActionEvent event) throws IOException {
        String newPassword = this.newPassword.getText();

        if(currentUser.getPassword().equals(newPassword)) {
            errorLabel.setText("New password must be different from the old password.");
            return;
        }
        
        if (userManager.isPasswordValid(newPassword)) {
            App.setRoot("confirmnewpassword");
        }
    }

    @FXML 
    void switchtosuccess(ActionEvent event) throws IOException {
        String newPassword = this.newPassword.getText();
        String confirmPassword = newPasswordField.getText();
        if (!newPassword.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            App.setRoot("createnewpassword");
            return;
        }

        boolean resetSuccess = userManager.resetPassword(currentUser.getUsername(), newPassword);
        if (resetSuccess) {
            App.setRoot("passwordresetsuccess");
        } else {
            errorLabel.setText("Failed to reset password. Please try again.");
        }
    }

    @FXML
    void switchbacktologin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void switchtologin(ActionEvent event) throws IOException{
        App.setRoot("login");
    }

    
}
