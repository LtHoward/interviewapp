package com.controllers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    private Button clearDifficultyButton;

    /** Text field where the user enters tags for filtering. */
    @FXML
    private TextField tagInputField;

    /** Button that clears all selected tags. */
    @FXML
    private Button clearTagsButton;

    /** Container that visually displays selected tags. */
    @FXML
    private FlowPane selectedTagsContainer;

    /** Reference to the application’s backend logic. */
    private InterviewApp app;

    /** The currently logged-in user. */
    private User currentUser;

    private String selectedDifficulty;

    /** Set of selected tags used for filtering results. */
    private final Set<String> selectedTags = new LinkedHashSet<>();

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

        if (tagInputField != null) {
            tagInputField.setOnAction(event -> handleAddTag(null));
        }

        updateDifficultyStyles();
        refreshSelectedTags();
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

    @FXML
    private void handleEasyClick(ActionEvent event) {
        toggleDifficulty("easy");
    }

    @FXML
    private void handleMediumClick(ActionEvent event) {
        toggleDifficulty("medium");
    }

    @FXML
    private void handleHardClick(ActionEvent event) {
        toggleDifficulty("hard");
    }

    @FXML
    private void handleClearDifficulty(ActionEvent event) {
        selectedDifficulty = null;
        updateDifficultyStyles();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Adds a tag to the selected tag list and updates the UI.
     * <p>
     * Prevents duplicate or empty tags. Clicking a tag removes it.
     *
     * @param event the action event triggered by the "Add Tag" button
     */
    @FXML
    private void handleAddTag(ActionEvent event) {
        String tag = normalizeTag(tagInputField.getText());

        if (tag.isEmpty() || selectedTags.contains(tag)) {
            return;
        }

        selectedTags.add(tag);
        tagInputField.clear();
        refreshSelectedTags();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Clears all selected tags and refreshes the results.
     *
     * @param event the action event triggered by the "Clear Tags" button
     */
    @FXML
    private void handleClearTags(ActionEvent event) {
        selectedTags.clear();
        refreshSelectedTags();
        loadQuestions(searchField.getText().trim());
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
            Button empty = new Button(buildEmptyMessage(keyword));
            empty.getStyleClass().add("search-result-empty");
            empty.setMaxWidth(Double.MAX_VALUE);
            empty.setDisable(true);
            resultsContainer.getChildren().add(empty);
            return;
        }

        for (QuestionPost q : questions) {
            if (!matchesDifficulty(q)) {
                continue;
            }

            if (!matchesTags(q)) {
                continue;
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

        if (resultsContainer.getChildren().isEmpty()) {
            Button empty = new Button(buildEmptyMessage(keyword));
            empty.getStyleClass().add("search-result-empty");
            empty.setMaxWidth(Double.MAX_VALUE);
            empty.setDisable(true);
            resultsContainer.getChildren().add(empty);
        }
    }

    /**
     * Checks if a question matches the currently selected difficulty filter.
     * @param question the question to check against the selected difficulty
     * @return true if the question matches the selected difficulty or if no difficulty is selected; false otherwise
     */
    private boolean matchesDifficulty(QuestionPost question) 
    {
        if (selectedDifficulty == null || selectedDifficulty.isEmpty()) return true;

        if (question == null || question.getDifficulty() == null) return false;

        return question.getDifficulty().toString().equalsIgnoreCase(selectedDifficulty);
    }

    /**
     * Checks if a question matches the currently selected tags.
     * @param question the question to check against the selected tags
     * @return true if the question contains all selected tags or if no tags are selected; false otherwise
     */
    private boolean matchesTags(QuestionPost question) 
    {
        if (selectedTags.isEmpty()) return true;
        
        if (question == null || question.getTags() == null || question.getTags().isEmpty()) return false;

        for (String selectedTag : selectedTags) 
        {
            boolean foundMatch = false;

            for (String questionTag : question.getTags()) 
            {
                if (questionTag != null && questionTag.equalsIgnoreCase(selectedTag)) 
                {
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) return false;
        }

        return true;
    }

    /**
     * Toggles the selected difficulty filter. If the same difficulty is clicked again, it clears the filter.
     * @param difficulty the difficulty level to toggle (e.g., "easy", "medium", "hard")
     */
    private void toggleDifficulty(String difficulty) 
    {
        if (difficulty == null || difficulty.isEmpty()) return;

        if (difficulty.equalsIgnoreCase(selectedDifficulty)) 
        {
            selectedDifficulty = null;
        } 
        else 
        {
            selectedDifficulty = difficulty;
        }

        updateDifficultyStyles();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Updates the visual styles of the difficulty filter buttons 
     * based on the currently selected difficulty.
     */
    private void updateDifficultyStyles() 
    {
        updateDifficultyButtonStyle(easyButton, "easy");
        updateDifficultyButtonStyle(mediumButton, "medium");
        updateDifficultyButtonStyle(hardButton, "hard");
        updateClearDifficultyButtonStyle();
    }

    /**
     * Updates the style of a difficulty button to indicate whether it is selected or not.
     * @param button the button to update the style for
     * @param difficulty the difficulty level associated with the button (e.g., "easy", "medium", "hard")
     */
    private void updateDifficultyButtonStyle(Button button, String difficulty) 
    {
        if (button == null) return;
        
        button.getStyleClass().removeAll("search-filter-button", "search-filter-button-selected");

        if (difficulty != null && difficulty.equalsIgnoreCase(selectedDifficulty)) 
        {
            button.getStyleClass().add("search-filter-button-selected");
        } 
        else 
        {
            button.getStyleClass().add("search-filter-button");
        }
    }

    /**
     * Updates the style of the "Clear Difficulty" button.
     */
    private void updateClearDifficultyButtonStyle() 
    {
        if (clearDifficultyButton == null) return;
        clearDifficultyButton.getStyleClass().removeAll("search-filter-button", "search-filter-button-selected");
        clearDifficultyButton.getStyleClass().add("search-filter-button");
    }

    /**
     * refreshes the display of selected tags in the UI.
     */
    private void refreshSelectedTags() 
    {
        if (selectedTagsContainer == null) {
            return;
        }

        selectedTagsContainer.getChildren().clear();

        for (String tag : selectedTags) {
            Button tagButton = new Button("#" + tag + "  x");
            tagButton.getStyleClass().add("search-tag-chip");

            tagButton.setOnAction(e -> {
                selectedTags.remove(tag);
                refreshSelectedTags();
                loadQuestions(searchField.getText().trim());
            });

            selectedTagsContainer.getChildren().add(tagButton);
        }
    }

    /**
     * Normalizes a tag string by trimming whitespace, converting to lowercase,
     *  and removing any leading '#' character.
     * @param tag the tag string to normalize
     * @return the normalized tag string
     */
    private String normalizeTag(String tag) 
    {
        if (tag == null) return " ";
        
        String normalizedTag = tag.trim().toLowerCase();

        if (normalizedTag.startsWith("#")) 
        {
            normalizedTag = normalizedTag.substring(1);
        }
        return normalizedTag;
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

    /**
     * Adds a tag to the search filter directly from the dashboard.
     * @param tag the tag to add to the search filter
     */
    public void addTagFromDashboard(String tag) 
    {
        if (tag == null || tag.trim().isEmpty()) return;
        tagInputField.setText(tag);
        handleAddTag(null);
    }

    /**
     * Builds an appropriate message to display when no search results are found.
     * @param keyword the search keyword that was used to find results
     * @return a message indicating that no questions were found.
     */
    private String buildEmptyMessage(String keyword) 
    {
        if (!selectedTags.isEmpty()) 
        {
            return "No questions found for \"" + keyword + "\" with selected tags";
        }

        if (selectedDifficulty != null && !selectedDifficulty.isEmpty()) 
        {
            return "No questions found for \"" + keyword + "\" with selected difficulty";
        }
        return "No questions found for \"" + keyword + "\"";
    }
}