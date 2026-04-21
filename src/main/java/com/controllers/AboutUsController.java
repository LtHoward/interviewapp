package com.controllers;

import com.interviewapp.App;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class AboutUsController {

    @FXML
    public void switchToLogin(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}