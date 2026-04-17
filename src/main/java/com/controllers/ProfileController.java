package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfileController {

    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("Back clicked");
    }

    @FXML
    private void handleEditProfile(ActionEvent event) {
        System.out.println("Edit Profile clicked");
    }

    @FXML
    private void handleEditAbout(ActionEvent event) {
        System.out.println("Edit About clicked");
    }

    @FXML
    private void handleEditAchievements(ActionEvent event) {
        System.out.println("Edit Achievements clicked");
    }

    @FXML
    private void handleEditSkills(ActionEvent event) {
        System.out.println("Edit Skills clicked");
    }

    @FXML
    private void handleEditStatistics(ActionEvent event) {
        System.out.println("Edit Statistics clicked");
    }
}