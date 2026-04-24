package com.controllers;

import com.interviewapp.App;
import com.model.Student;
import com.model.Title;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class ProfileController {

    private User currentUser;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label aboutLabel;

    @FXML
    private Label achievementsLabel;

    @FXML
    private Label skillsLabel;

    @FXML
    private Label statisticsLabel;

    public void setUser(User user) {
        this.currentUser = user;
        populateProfile();
    }

    private void populateProfile() {
        if (currentUser == null) {
            return;
        }

        if (usernameLabel != null) {
            usernameLabel.setText(currentUser.getUsername());
        }

        if (roleLabel != null) {
            roleLabel.setText(formatEnum(currentUser.getStatus()));
        }

        String first = currentUser.getFirstName() != null ? currentUser.getFirstName() : "";
        String last = currentUser.getLastName() != null ? currentUser.getLastName() : "";

        if (nameLabel != null) {
            nameLabel.setText((first + " " + last).trim());
        }

        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;

            if (aboutLabel != null) {
                aboutLabel.setText(
                    "A " + formatEnum(student.getSkillLevel()) + " " +
                    formatEnum(student.getMajor()) + " student."
                );
            }

            if (achievementsLabel != null) {
                Title equipped = student.getProgression().getEquippedTitle();
                achievementsLabel.setText(
                    "Equipped Title: " + formatEnum(equipped) +
                    "\nUnlocked Titles: " + student.getProgression().getUnlockedTitles().size()
                );
            }

            if (skillsLabel != null) {
                skillsLabel.setText(
                    "Skill Level: " + formatEnum(student.getSkillLevel()) +
                    "\nMajor: " + formatEnum(student.getMajor())
                );
            }

            if (statisticsLabel != null) {
                statisticsLabel.setText(
                    "Solved Questions: " + student.getSolvedQuestions() +
                    "\nCurrent Streak: " + student.getCurrentStreak() +
                    "\nLevel: " + student.getProgression().getLevel() +
                    "\nXP: " + student.getProgression().getPoints()
                );
            }
        } else {
            if (aboutLabel != null) {
                aboutLabel.setText("User profile");
            }
            if (achievementsLabel != null) {
                achievementsLabel.setText("No student achievements available.");
            }
            if (skillsLabel != null) {
                skillsLabel.setText("Role: " + formatEnum(currentUser.getStatus()));
            }
            if (statisticsLabel != null) {
                statisticsLabel.setText("No student statistics available.");
            }
        }
    }

    private String formatEnum(Enum<?> value) {
        if (value == null) {
            return "None";
        }

        String[] words = value.name().toLowerCase().split("_");
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (words[i].isEmpty()) continue;

            formatted.append(Character.toUpperCase(words[i].charAt(0)))
                     .append(words[i].substring(1));

            if (i < words.length - 1) {
                formatted.append(" ");
            }
        }

        return formatted.toString();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("studentDashboard");
            StudentDashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
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