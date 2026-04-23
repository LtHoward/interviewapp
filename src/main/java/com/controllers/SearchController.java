package com.controllers;

import com.interviewapp.App;
import com.model.InterviewApp;
import com.model.QuestionPost;
import com.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class SearchController {

    @FXML
    private TextField searchField;

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

    @FXML
    private TextField tagInputField;

    @FXML
    private Button clearTagsButton;

    @FXML
    private FlowPane selectedTagsContainer;

    private InterviewApp app;

    private User currentUser;

    private String selectedDifficulty;

    private final ArrayList<String> selectedTags = new ArrayList<>();

    /**
     * Initialization method called by JavaFX after the FXML is loaded.
     * Sets up listeners and loads initial data.
     */
    @FXML
    public void initialize() 
    {
        app = new InterviewApp();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadQuestions(newValue.trim());
        });

        if (tagInputField != null) 
        {
            tagInputField.setOnAction(event -> AddTag(null));
        }

        updateDiff();
        clearTags();
        loadQuestions("");
    }

    /**
     * Sets the current user for this controller
     * @param user the user to set as the current user for this controller
     */
    public void setUser(User user) 
    {
        this.currentUser = user;
    }

    /**
     * Handles the action of clicking the "Back" button. Navigates the user back to the dashboard.
     * @param event the action event triggered by clicking the back button
     */
    @FXML
    private void pressBack(ActionEvent event) 
    {
        try 
        {
            javafx.fxml.FXMLLoader loader = App.setRootWithLoader("studentDashboard");
            StudentDashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of clicking the "Search" button. 
     * Loads questions based on the search field input.
     * @param event the action event triggered by clicking the search button
     */
    @FXML
    private void SearchQuestion(ActionEvent event) 
    {
        loadQuestions(searchField.getText().trim());
    }
    
    /**
     * Selects the "Easy" difficulty filter when the corresponding button is clicked.
     * @param event the action event triggered by clicking the easy difficulty filter button
     */
    @FXML
    private void selectEasy(ActionEvent event) 
    {
        selectedDiff("easy");
    }

    /**
     * Selects the "Medium" difficulty filter when the corresponding button is clicked.
     * @param event the action event triggered by clicking the medium difficulty filter button
     */
    @FXML
    private void selectMedium(ActionEvent event) 
    {
        selectedDiff("medium");
    }

   /**
    * Selects the "Hard" difficulty filter when the corresponding button is clicked.
    * @param event the action event triggered by clicking the hard difficulty filter button
    */
    @FXML
    private void selectHard(ActionEvent event) 
    {
        selectedDiff("hard");
    }

    /**
     * Clears the selected difficulty filter when the corresponding button is clicked,
     * allowing all difficulties to be shown in the search results.
     * @param event the action event triggered by clicking the clear difficulty button
     */
    @FXML
    private void ClearDiff(ActionEvent event) 
    {
        selectedDifficulty = null;
        updateDiff();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Adds the tag entered in the tag input field to the selected tags list 
     * and updates the search results accordingly when the "Add" button is clicked.
     * @param event the action event triggered by clicking the add tag button
     */
    @FXML
    private void AddTag(ActionEvent event) 
    {
        if (tagInputField == null) return;
        
        String tag = normTag(tagInputField.getText());

        if (tag.isEmpty()) return;
        
        if (!selectedTags.contains(tag)) 
        {
            selectedTags.add(tag);
        }

        tagInputField.clear();
        clearTags();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * clears all selected tags from the filter and updates the 
     * search results accordingly when the "Clear Tags" button is clicked.
     * @param event the action event triggered by clicking the clear tags button
     */
    @FXML
    private void ClearTags(ActionEvent event) 
    {
        selectedTags.clear();
        clearTags();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * selectedQuestion is called when a question button in the search results is clicked.
     * @param event the action event triggered by clicking a question button in the search results
     */
    @FXML
    private void selectedQuestion(ActionEvent event) 
    {
        Button clicked = (Button) event.getSource();
        System.out.println("Selected question: " + clicked.getText());
        // TODO: navigate to question detail page
    }

    /**
     * Loads questions based on the provided keyword and updates the results container.
     * @param keyword the search keyword to filter questions
     */
    private void loadQuestions(String keyword) 
    {
        resultsContainer.getChildren().clear();

        ArrayList<QuestionPost> questions;

        if (keyword.isEmpty()) 
        {
            questions = app.getAllQuestions();
        } 
        else 
        {
            questions = app.searchQuestions(keyword);
        }

        questions = filterQuestions(questions);

        if (questions == null || questions.isEmpty()) 
        {
            Button empty = new Button(noResults(keyword));
            empty.getStyleClass().add("search-result-empty");
            empty.setMaxWidth(Double.MAX_VALUE);
            empty.setDisable(true);
            resultsContainer.getChildren().add(empty);
            return;
        }

        for (QuestionPost q : questions) 
        {
            String label = formatDifficulty(q.getDifficulty()) + ": " + q.getTitle();
            Button btn = new Button(label);
            btn.getStyleClass().add("search-result-button");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(70);
            btn.setOnAction(this::selectedQuestion);
            resultsContainer.getChildren().add(btn);
        }
    }

    /**
     * Filters the provided list of questions based on the currently selected difficulty and tags.
     * @param questions the list of questions to filter
     * @return a new list of questions that match the selected difficulty and tags criteria
     */
    private ArrayList<QuestionPost> filterQuestions(ArrayList<QuestionPost> questions) 
    {
        if (questions == null || questions.isEmpty()) return questions;
        
        ArrayList<QuestionPost> filteredQuestions = new ArrayList<>();

        for (QuestionPost question : questions) 
        {
            if (!Difficulty(question)) continue;
            
            if (!Tags(question))continue;
        
            filteredQuestions.add(question);
        }

        return filteredQuestions;
    }

    /**
     * Checks if a given question matches the currently selected difficulty filter.
     * @param question the question to check against the selected difficulty
     * @return true if the question matches the selected difficulty or if no difficulty is selected; false otherwise
     */
    private boolean Difficulty(QuestionPost question) 
    {
        if (selectedDifficulty == null || selectedDifficulty.isEmpty()) return true;
        
        if (question == null || question.getDifficulty() == null) return false;
        
        return question.getDifficulty().toString().equalsIgnoreCase(selectedDifficulty);
    }

    /**
     * Checks if a given question matches the currently selected tags filter.
     * @param question the question to check against the selected tags
     * @return true if the question matches all selected tags or if no tags are selected; false otherwise
     */
    private boolean Tags(QuestionPost question) 
    {
        if (selectedTags.isEmpty()) return true;
        
        if (question == null || question.getTags() == null || question.getTags().isEmpty()) 
        {
            return false;
        }
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
     * Method for handling the selection of a difficulty filter. 
     * Toggles the selected difficulty based on user input and updates the question list accordingly.
     * @param difficulty the difficulty level to select (e.g., "easy", "medium", "hard")
     */
    private void selectedDiff(String difficulty) 
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
        updateDiff();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * Updates the styles of all difficulty filter buttons 
     * based on the currently selected difficulty
     */
    private void updateDiff() {
        updateDiff(easyButton, "easy");
        updateDiff(mediumButton, "medium");
        updateDiff(hardButton, "hard");
        updateDiff(clearDifficultyButton, null);
    }

    /**
     * Updates the style of a given difficulty button based on 
     * whether it matches the currently selected difficulty.
     * @param button the difficulty button to update the style for
     * @param difficulty the difficulty level associated 
     * with the button (e.g., "easy", "medium", "hard")
     */
    private void updateDiff(Button button, String difficulty) 
    {
        if (button == null) return;
        
        button.getStyleClass().removeAll("search-filter-button","search-filter-button-selected");

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
    * Clears the currently displayed selected tags in the UI 
    * and refreshes the display to show the current list of selected tags.
    */
    private void clearTags() 
    {
        if (selectedTagsContainer == null) return;

        selectedTagsContainer.getChildren().clear();

        for (String tag : selectedTags) 
        {
            Button tagButton = new Button("#" + tag + "  x");
            tagButton.getStyleClass().add("search-tag-chip");
            tagButton.setOnAction(event -> removeTag(tag));
            selectedTagsContainer.getChildren().add(tagButton);
        }
    }

    /**
     * Removes a tag from the selected tags list and refreshes the display and question list accordingly.
     * @param tag the tag to be removed from the selected tags list
     */
    private void removeTag(String tag) 
    {
        selectedTags.remove(tag);
        clearTags();
        loadQuestions(searchField.getText().trim());
    }

    /**
     * method to normalize a tag input by trimming whitespace, 
     * converting to lowercase, and removing any leading '#' character.
     * @param tag the input tag string to be normalized
     * @return the normalized tag string, or an empty string
     * if the input was null or empty after normalization
     */
    private String normTag(String tag) 
    {
        if (tag == null) return " ";
        String newTag = tag.trim().toLowerCase();

        if (newTag.startsWith("#")) 
        {
            newTag = newTag.substring(1);
        }
        return newTag;
    }
    
    /**
     * Formats a difficulty value into a user-friendly string
     * @param difficulty the difficulty value to format (e.g., "easy", "medium", "hard")
     * @return a formatted string representing the difficulty, 
     * with the first letter capitalized and the rest in lowercase; or "Unknown" if the input is null or empty
     */ 
    private String formatDifficulty(Object difficulty) 
    {
        if (difficulty == null) return "Unknown";
        
        String value = difficulty.toString().toLowerCase();

        if (value.isEmpty()) return "Unknown";
        
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    /**
     * Displays if no results were found for the given search keyword
     * @param keyword the searched keyword
     * @return a message indicating that no questions were found for the given keyword
     */
    private String noResults(String keyword) 
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