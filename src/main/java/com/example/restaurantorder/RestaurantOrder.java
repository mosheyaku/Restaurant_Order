package com.example.restaurantorder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantOrder extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RestaurantOrder.class.getResource("restaurant-order.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 580);
        stage.setTitle("Restaurant Order");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}