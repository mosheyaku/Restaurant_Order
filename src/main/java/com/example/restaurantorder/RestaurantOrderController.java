package com.example.restaurantorder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RestaurantOrderController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}