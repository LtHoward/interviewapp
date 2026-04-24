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

    @FXML private TextField searchField;
    @FXML private VBox resultsContainer;
    @FXML private Button easyButton;
    @FXML private Button mediumButton;
    @FXML private Button hardButton;
    @FXML private Button clearDifficultyButton;
    @FXML private TextField tagInputField;
    @FXML private Button clearTagsButton;
    @FXML private FlowPane selectedTagsContainer;

    private InterviewApp app;
    private User currentUser;
    private String selectedDifficulty;
    private final ArrayList<String> selectedTags = new ArrayList<>();

    @FXML
    public void initialize() {
        app = new InterviewApp();

        searchField.textProperty().addListener((obs, oldVal, newVal) ->
            loadQuestions(newVal.trim())
        );

        if (tagInputField != null) {
            tagInputField.setOnAction(event -> addTag(null));
        }

        updateDiffButtons();
        refreshTagChips();
        loadQuestions("");
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    // ── Navigation ───────────────────────────────────────────────────────────

    @FXML
    private void pressBack(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = App.setRootWithLoader("studentDashboard");
            StudentDashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Search ───────────────────────────────────────────────────────────────

    @FXML
    private void searchQuestion(ActionEvent event) {
        loadQuestions(searchField.getText().trim());
    }

    // ── Difficulty filters ───────────────────────────────────────────────────

    @FXML
    private void selectEasy(ActionEvent event) {
        setDifficulty("easy");
    }

    @FXML
    private void selectMedium(ActionEvent event) {
        setDifficulty("medium");
    }

    @FXML
    private void selectHard(ActionEvent event) {
        setDifficulty("hard");
    }

    @FXML
    private void clearDiff(ActionEvent event) {
        selectedDifficulty = null;
        updateDiffButtons();
        loadQuestions(searchField.getText().trim());
    }

    // ── Tag filters ──────────────────────────────────────────────────────────

    @FXML
    private void addTag(ActionEvent event) {
        if (tagInputField == null) return;

        String tag = normalizeTag(tagInputField.getText());
        if (tag.isEmpty()) return;

        if (!selectedTags.contains(tag)) {
            selectedTags.add(tag);
        }

        tagInputField.clear();
        refreshTagChips();
        loadQuestions(searchField.getText().trim());
    }

    @FXML
    private void clearTags(ActionEvent event) {
        selectedTags.clear();
        refreshTagChips();
        loadQuestions(searchField.getText().trim());
    }

    // ── Question selection ───────────────────────────────────────────────────

    @FXML
    private void selectedQuestion(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        QuestionPost question = (QuestionPost) clicked.getUserData();
        if (question == null) return;

        try {
            javafx.fxml.FXMLLoader loader = App.setRootWithLoader("questionDetail");
            // TODO: swap in your actual question detail controller
            // QuestionDetailController controller = loader.getController();
            // controller.setQuestion(question);
            // controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Core loading & filtering ─────────────────────────────────────────────

    private void loadQuestions(String keyword) {
        resultsContainer.getChildren().clear();

        ArrayList<QuestionPost> questions = keyword.isEmpty()
            ? app.getAllQuestions()
            : app.searchQuestions(keyword);

        questions = filterQuestions(questions);

        if (questions == null || questions.isEmpty()) {
            Button empty = new Button(buildNoResultsMessage(keyword));
            empty.getStyleClass().add("search-result-empty");
            empty.setMaxWidth(Double.MAX_VALUE);
            empty.setDisable(true);
            resultsContainer.getChildren().add(empty);
            return;
        }

        for (QuestionPost q : questions) {
            String label = formatDifficulty(q.getDifficulty()) + ": " + q.getTitle();
            Button btn = new Button(label);
            btn.getStyleClass().add("search-result-button");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(70);
            btn.setUserData(q); // store question reference for navigation
            btn.setOnAction(this::selectedQuestion);
            resultsContainer.getChildren().add(btn);
        }
    }

    private ArrayList<QuestionPost> filterQuestions(ArrayList<QuestionPost> questions) {
        if (questions == null || questions.isEmpty()) return questions;

        ArrayList<QuestionPost> filtered = new ArrayList<>();
        for (QuestionPost q : questions) {
            if (matchesDifficulty(q) && matchesTags(q)) {
                filtered.add(q);
            }
        }
        return filtered;
    }

    private boolean matchesDifficulty(QuestionPost question) {
        if (selectedDifficulty == null || selectedDifficulty.isEmpty()) return true;
        if (question == null || question.getDifficulty() == null) return false;
        return question.getDifficulty().toString().equalsIgnoreCase(selectedDifficulty);
    }

    private boolean matchesTags(QuestionPost question) {
        if (selectedTags.isEmpty()) return true;
        if (question == null || question.getTags() == null || question.getTags().isEmpty()) return false;

        for (String selectedTag : selectedTags) {
            boolean found = false;
            for (String questionTag : question.getTags()) {
                if (questionTag != null && questionTag.equalsIgnoreCase(selectedTag)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    // ── UI helpers ───────────────────────────────────────────────────────────

    private void setDifficulty(String difficulty) {
        if (difficulty == null || difficulty.isEmpty()) return;
        selectedDifficulty = difficulty.equalsIgnoreCase(selectedDifficulty) ? null : difficulty;
        updateDiffButtons();
        loadQuestions(searchField.getText().trim());
    }

    private void updateDiffButtons() {
        updateDiffButton(easyButton, "easy");
        updateDiffButton(mediumButton, "medium");
        updateDiffButton(hardButton, "hard");
        updateDiffButton(clearDifficultyButton, null);
    }

    private void updateDiffButton(Button button, String difficulty) {
        if (button == null) return;
        button.getStyleClass().removeAll("search-filter-button", "search-filter-button-selected");
        boolean isSelected = difficulty != null && difficulty.equalsIgnoreCase(selectedDifficulty);
        button.getStyleClass().add(isSelected ? "search-filter-button-selected" : "search-filter-button");
    }

    private void refreshTagChips() {
        if (selectedTagsContainer == null) return;
        selectedTagsContainer.getChildren().clear();

        for (String tag : selectedTags) {
            Button chip = new Button("#" + tag + "  ×");
            chip.getStyleClass().add("search-tag-chip");
            chip.setOnAction(event -> removeTag(tag));
            selectedTagsContainer.getChildren().add(chip);
        }
    }

    private void removeTag(String tag) {
        selectedTags.remove(tag);
        refreshTagChips();
        loadQuestions(searchField.getText().trim());
    }

    // ── Utilities ────────────────────────────────────────────────────────────

    private String normalizeTag(String tag) {
        if (tag == null) return "";
        String normalized = tag.trim().toLowerCase();
        return normalized.startsWith("#") ? normalized.substring(1) : normalized;
    }

    private String formatDifficulty(Object difficulty) {
        if (difficulty == null) return "Unknown";
        String value = difficulty.toString().toLowerCase();
        if (value.isEmpty()) return "Unknown";
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    private String buildNoResultsMessage(String keyword) {
        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        boolean hasDifficulty = selectedDifficulty != null && !selectedDifficulty.isEmpty();
        boolean hasTags = !selectedTags.isEmpty();

        if (hasKeyword && hasDifficulty && hasTags) {
            return "No results for \"" + keyword + "\" with difficulty and tag filters";
        } else if (hasKeyword && hasDifficulty) {
            return "No results for \"" + keyword + "\" with difficulty: " + formatDifficulty(selectedDifficulty);
        } else if (hasKeyword && hasTags) {
            return "No results for \"" + keyword + "\" with selected tags";
        } else if (hasDifficulty && hasTags) {
            return "No results for selected difficulty and tags";
        } else if (hasDifficulty) {
            return "No results for difficulty: " + formatDifficulty(selectedDifficulty);
        } else if (hasTags) {
            return "No results for selected tags";
        } else if (hasKeyword) {
            return "No results for \"" + keyword + "\"";
        }
        return "No questions found";
    }
}