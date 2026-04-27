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

public class CreateNewPasswordController {

    private final InterviewApp app = new InterviewApp();

    private User currentUser;

    private final UserManager userManager = UserManager.getInstance();

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField newpassword;

    @FXML
    private Button switchtoconfirm;

    @FXML
    private Button switchtologinpage;


    @FXML
    void switchtoconfirm(ActionEvent event) throws IOException {
        String newPassword = newpassword.getText();

        if(currentUser.getPassword().equals(newPassword)) {
            errorLabel.setText("New password must be different from the old password.");
            return;
        }
        
        if (userManager.isPasswordValid(newPassword)) {
            App.setRoot("confirmnewpassword");
        }
    }

    @FXML
    void switchtologin(ActionEvent event) throws IOException{
        App.setRoot("login");

    }
}
