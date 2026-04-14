package com.controllers;

import com.model.User;
import com.model.Student;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class DashboardController {

        private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        initializeData();
    }

    private void initializeData() {
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getUsername());
        }
    }

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label statsLabel;

    @FXML
    private ProgressBar xpBar;

    @FXML
    public void initialize() {
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getUsername());

            if (currentUser instanceof Student) {
                Student s = (Student) currentUser;

                statsLabel.setText(
                    "Level: " + s.getProgression().getLevel() +
                    "   Streak: " + s.getCurrentStreak() +
                    "   Title: " + s.getProgression().getEquippedTitle()
                );

                double progress = (double) s.getProgression().getPoints()
                        / (s.getProgression().getLevel() * 100);

                xpBar.setProgress(progress);
            }
        }
    }
}