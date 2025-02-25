module com.example.salonprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.salonprojekt to javafx.fxml;
    exports com.example.salonprojekt;
}