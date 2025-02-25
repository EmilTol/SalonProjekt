module com.example.salonprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.salonprojekt to javafx.fxml;
    exports com.example.salonprojekt;
}