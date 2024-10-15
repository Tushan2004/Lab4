module com.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.lab4 to javafx.fxml; // Lägg till detta om du använder FXML
    opens view to javafx.fxml;
    opens model to javafx.fxml;

    exports model;
    exports com.lab4; // Lägg till detta om du vill exportera huvudklassen
    exports view;
}
