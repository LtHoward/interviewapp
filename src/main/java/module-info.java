module com.interviewapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.interviewapp to javafx.fxml;
    exports com.interviewapp;
}
