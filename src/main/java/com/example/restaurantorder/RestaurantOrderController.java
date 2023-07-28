package com.example.restaurantorder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class RestaurantOrderController {

    @FXML
    private GridPane grid;

    private ArrayList<TextField> description;

    private ArrayList<TextField> type;

    private ArrayList<ComboBox> quantity;

    private ArrayList<TextField> price;

    private ArrayList<CheckBox> select;

    @FXML
    public void orderPressed(ActionEvent actionEvent) {
    }

    public void initialize() {
        select = new ArrayList<CheckBox>();
        quantity = new ArrayList<ComboBox>();
        type = new ArrayList<TextField>();
        description = new ArrayList<TextField>();
        price = new ArrayList<TextField>();
    }
}
