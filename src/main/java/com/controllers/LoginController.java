package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.InterviewApp;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final InterviewApp app = new InterviewApp();

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        User user = app.getUser(username, password);

        if (user != null) {
            try {
                FXMLLoader loader = App.setRootWithLoader("studentDashboard");
                StudentDashboardController controller = loader.getController();
                controller.setUser(user);

                System.out.println("Logged in as: " + user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid username or password");
        }
    }

    @FXML
    void switchToIntro(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToAboutUs(ActionEvent event) {
        try {
            App.setRoot("aboutUs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchtoforgotpassword(ActionEvent event) throws IOException {
        App.setRoot("resetpassword");
    }

    @FXML
    void switchtorole(ActionEvent event) throws IOException {
        App.setRoot("role");
    }
}