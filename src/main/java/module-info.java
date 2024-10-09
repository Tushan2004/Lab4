module com.lab4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lab4 to javafx.fxml;
    opens view to javafx.fxml;
    exports com.lab4;
    exports view;
}