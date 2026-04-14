package com.controllers;

import com.interviewapp.App;
import com.model.InterviewApp;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private InterviewApp app = new InterviewApp();

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = app.getUser(username, password);

        if (user != null) {
            try {
                FXMLLoader loader = App.setRootWithLoader("dashboard");

                DashboardController controller = loader.getController();
                controller.setUser(user); // 🔥 CLEAN DATA PASS

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void switchToIntro(ActionEvent event) {
        try {
            App.setRoot("intro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}