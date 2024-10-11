module com.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.lab4 to javafx.fxml;
    opens view to javafx.fxml;
    opens model to javafx.fxml;
    exports model;
    exports com.lab4;
    exports view;
}