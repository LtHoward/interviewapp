package com.controllers;
import java.io.IOException;

import com.interviewapp.App;
import com.model.InterviewApp;
import com.model.UserManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
;

public class ConfirmNewPasswordController {
    private final NewPasswordState state = NewPasswordState.getInstance();

    private final InterviewApp app = new InterviewApp();


    private final UserManager userManager = UserManager.getInstance();
    @FXML
    private Button backtologin;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField newpasswordfield;

    @FXML
    private Button switchtosuccess;

    @FXML
    void switchtologin(ActionEvent event) throws IOException {
        App.setRoot("login");
    
    }

    @FXML
    void switchtosuccess(ActionEvent event) throws IOException {
        NewPasswordState state = NewPasswordState.getInstance();

        String confirmPassword = newpasswordfield.getText();
            if (!state.getNewPassword().equals(confirmPassword)) {
                errorLabel.setText("Passwords do not match.");
                App.setRoot("createnewpassword");
                return;
            }
       

        boolean resetSuccess = userManager.resetPassword(state.getUsername(), confirmPassword);
        if (resetSuccess) {
            App.setRoot("passwordresetsuccess");
        } else {
            errorLabel.setText("Failed to reset password. Please try again.");
        }

    }
}
