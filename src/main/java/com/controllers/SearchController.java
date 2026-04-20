package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("Back clicked");
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText();
        System.out.println("Search clicked: " + keyword);
    }

    @FXML
    private void handleFilter(ActionEvent event) {
        System.out.println("Filter clicked");
    }

    @FXML
    private void handleQuestionClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        System.out.println("Selected question: " + clickedButton.getText());
    }
}