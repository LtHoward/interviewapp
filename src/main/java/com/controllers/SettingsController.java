package com.controllers;

import com.interviewapp.App;
import com.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SettingsController {

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("dashboard");
            DashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}