package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.InterviewApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ResetPasswordController {

    @FXML
    private TextField emailField;

      @FXML
    private Button backtologin;

    @FXML
    private Button switchtonextstep;

    @FXML
    void handlecontinue(ActionEvent event) {
        String email = emailField.getText().trim();
        InterviewApp app = new InterviewApp();
        
    }

    @FXML
    void switchtologin(ActionEvent event) throws IOException{
        App.setRoot("login");
    }

    
}
