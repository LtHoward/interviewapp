module com.interviewapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.logging; //backup library
    requires junit;
    
    opens com.controllers to javafx.fxml;
    exports com.interviewapp;

    opens com.model to javafx.fxml;
    exports com.model;
}
