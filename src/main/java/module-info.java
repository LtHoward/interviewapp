module com.interviewapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires junit;
    
    opens com.interviewapp to javafx.fxml;
    exports com.interviewapp;

    opens com.model to javafx.fxml;
    exports com.model;
}
