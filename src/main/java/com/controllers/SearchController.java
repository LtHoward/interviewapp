package com.controllers;

import com.interviewapp.App;
import com.model.InterviewApp;
import com.model.User;
import com.model.QuestionPost;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SearchController {

    @FXML 
    private TextField searchField;
    @FXML 
    private VBox resultsContainer;

    private InterviewApp app;
    private User currentUser;

    /**
     * Initialization method called by JavaFX after the FXML is loaded.
     * Sets up listeners and loads initial data.
     */
    @FXML
    public void initialize() {
        app = new InterviewApp();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadQuestions(newValue.trim());
        });

        loadQuestions("");
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = App.setRootWithLoader("studentDashboard");
            StudentDashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        loadQuestions(searchField.getText().trim());
    }

    @FXML
    private void handleFilter(ActionEvent event) {
        System.out.println("Filter clicked");
        // TODO: implement filter functionality
    }

    @FXML
    private void handleQuestionClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        System.out.println("Selected question: " + clicked.getText());
        // TODO: navigate to question detail page
    }

    /**
     * Loads questions based on the provided keyword and updates the results container.
     * @param keyword the search keyword to filter questions
     */
    private void loadQuestions(String keyword) {
        resultsContainer.getChildren().clear();

        ArrayList<QuestionPost> questions;

        if (keyword.isEmpty()) {
            questions = app.getAllQuestions();
        } else {
            questions = app.searchQuestions(keyword);
        }

        if (questions == null || questions.isEmpty()) {
            Button empty = new Button("No questions found for \"" + keyword + "\"");
            empty.setMaxWidth(Double.MAX_VALUE);
            empty.setDisable(true);
            resultsContainer.getChildren().add(empty);
            return;
        }

        for (QuestionPost q : questions) {
            String label = q.getDifficulty() + ": " + q.getTitle();
            Button btn = new Button(label);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(70);
            btn.setOnAction(this::handleQuestionClick);
            resultsContainer.getChildren().add(btn);
        }
    }
}