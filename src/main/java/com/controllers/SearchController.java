package com.controllers;

import com.interviewapp.App;
import com.model.InterviewApp;
import com.model.QuestionPost;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Controller for the Search page of the Interview App.
 * <p>
 * This controller is responsible for:
 * <ul>
 *     <li>Handling keyword-based question searches</li>
 *     <li>Displaying a dynamic list of search results</li>
 *     <li>Filtering questions using user-selected tags</li>
 *     <li>Allowing users to add/remove/clear tags</li>
 *     <li>Navigating between pages (dashboard and question view)</li>
 * </ul>
 * It communicates with the {@link InterviewApp} backend to retrieve and filter
 * {@link QuestionPost} data.
 */
public class SearchController {

    /** Text field where the user types their search query. */
    @FXML
    private TextField searchField;

    /** Container that holds dynamically generated search result buttons. */
    @FXML
    private VBox resultsContainer;

    /** Text field where the user enters tags for filtering. */
    @FXML
    private TextField tagInputField;

    /** Container that visually displays selected tags. */
    @FXML
    private FlowPane selectedTagsContainer;

    /** Reference to the application’s backend logic. */
    private InterviewApp app;

    /** The currently logged-in user. */
    private User currentUser;

    /** Set of selected tags used for filtering results. */
    private Set<String> selectedTags = new HashSet<>();

    /**
     * Initializes the controller after the FXML has been loaded.
     * <p>
     * Sets up the backend instance and attaches a listener to the search field
     * so results update dynamically as the user types.
     */
    @FXML
    public void initialize() {
        app = new InterviewApp();

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            loadQuestions(newVal.trim());
        });

        loadQuestions("");
    }

    /**
     * Sets the current logged-in user.
     *
     * @param user the user who is currently logged in
     */
    public void setUser(User user) {
        this.currentUser = user;
    }

    /**
     * Handles navigation back to the dashboard page.
     *
     * @param event the action event triggered by the back button
     */
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

    /**
     * Handles the search button press.
     * <p>
     * Reloads questions using the current search field input.
     *
     * @param event the action event triggered by the search button
     */
    @FXML
    private void handleSearch(ActionEvent event) {
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Loads and displays questions based on a keyword and selected tags.
     * <p>
     * If no keyword is provided, all questions are shown.
     * If tags are selected, only questions containing at least one matching tag
     * are displayed.
     *
     * @param keyword the search term entered by the user
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

            // Apply tag filtering
            if (!selectedTags.isEmpty()) {
                if (q.getTags() == null) continue;

                boolean matches = false;

                for (String tag : q.getTags()) {
                    if (selectedTags.contains(tag.toLowerCase())) {
                        matches = true;
                        break;
                    }
                }

                if (!matches) continue;
            }

            String label = formatEnum(q.getDifficulty()) + ": " + q.getTitle();

            Button btn = new Button(label);
            btn.getStyleClass().add("search-result-button");
            btn.setUserData(q);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(70);
            btn.setOnAction(this::handleQuestionClick);

            resultsContainer.getChildren().add(btn);
        }
    }

    /**
     * Handles when a user clicks a question result.
     * <p>
     * Navigates to the question view page and passes the selected question
     * and current user.
     *
     * @param event the action event triggered by clicking a question
     */
    @FXML
    private void handleQuestionClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        QuestionPost selectedQuestion = (QuestionPost) clicked.getUserData();

        try {
            FXMLLoader loader = App.setRootWithLoader("questionPost");
            QuestionsController controller = loader.getController();
            controller.setData(currentUser, selectedQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a tag to the selected tag list and updates the UI.
     * <p>
     * Prevents duplicate or empty tags. Clicking a tag removes it.
     *
     * @param event the action event triggered by the "Add Tag" button
     */
    @FXML
    private void AddTag(ActionEvent event) {
        String tag = tagInputField.getText().trim().toLowerCase();

        if (tag.isEmpty() || selectedTags.contains(tag)) {
            return;
        }

        selectedTags.add(tag);

        Button tagButton = new Button(tag);
        tagButton.getStyleClass().add("tag-pill");

        tagButton.setOnAction(e -> {
            selectedTags.remove(tag);
            selectedTagsContainer.getChildren().remove(tagButton);
            loadQuestions(searchField.getText().trim());
        });

        selectedTagsContainer.getChildren().add(tagButton);

        tagInputField.clear();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Clears all selected tags and refreshes the results.
     *
     * @param event the action event triggered by the "Clear Tags" button
     */
    @FXML
    private void ClearTags(ActionEvent event) {
        selectedTags.clear();
        selectedTagsContainer.getChildren().clear();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Converts an enum value into a user-friendly string.
     * <p>
     * Example:
     * <pre>
     * SYNTAX_SCOUT → Syntax Scout
     * </pre>
     *
     * @param value the enum value to format
     * @return a human-readable string representation
     */
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

    public void addTagFromDashboard(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            return;
        }

        tagInputField.setText(tag);
        AddTag(null);
    }
}