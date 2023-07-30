module com.example.restaurantorder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.restaurantorder to javafx.fxml;
    exports com.example.restaurantorder;
}