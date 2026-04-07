package com.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.interviewapp.App;

public class HomeController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}
