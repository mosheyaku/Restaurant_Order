package com.example.restaurantorder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    private int i = 0;
    private int j = 0;
    private final int MAX_Item_Choice = 10;


    @FXML
    public void orderPressed(ActionEvent actionEvent) {
    }

    public void initialize() {
        select = new ArrayList<CheckBox>();
        quantity = new ArrayList<ComboBox>();
        type = new ArrayList<TextField>();
        description = new ArrayList<TextField>();
        price = new ArrayList<TextField>();
        Menu menu = new Menu("menu.txt");
        insertHeadingsToMenu();
        Label itemType[] = new Label[]{new Label("Starter"), new Label("Main Dish"), new Label("Last Dish"), new Label("Drink")};
        dishNameDesign(itemType);
        insertItemsByTypeToMenu(menu, itemType);
    }

    private void insertHeadingsToMenu() {
        Label title[] = new Label[]{new Label("Dish Name"), new Label("Price"), new Label("Quantity")};
        for (int k = 0; k < title.length; k++) {
            title[k].setStyle("-fx-font-weight: bold italic; -fx-font-size: 12pt;");
        }
        grid.addRow(i, title[0], title[1], title[2]);
        i++;
    }

    private void dishNameDesign(Label itemType[]) {
        for (int k = 0; k < itemType.length; k++) {
            itemType[k].setStyle("-fx-font-weight: bold; -fx-font-size: 11pt;");
        }
    }

    private void insertItemsByTypeToMenu(Menu menu, Label itemType[]) {
        grid.addRow(i, itemType[0]);
        i++;
        setItems(menu.getStarters());

        grid.addRow(i, itemType[1]);
        i++;
        setItems(menu.getMainDishes());

        grid.addRow(i, itemType[2]);
        i++;
        setItems(menu.getLastDishes());

        grid.addRow(i, itemType[3]);
        i++;
        setItems(menu.getDrinks());
    }

    private void setItems(ArrayList<Item> itemType) {
        String style = "-fx-background-color: #e2e4e6; -fx-border-color: white;";
        for (Item item : itemType) {
            addItemDescription(item, style);
            addItemType(item);
            addItemPrice(item, style);
            addItemQuantity(item);
            select.add(new CheckBox());
            grid.addRow(i, description.get(j), price.get(j), quantity.get(j), select.get(j));
            i++;
            j++;
        }
    }

    private void addItemDescription(Item item, String style) {
        description.add(new TextField());
        description.get(j).setText(item.getDescription());
        description.get(j).setEditable(false);
        description.get(j).setStyle(style);
    }

    private void addItemType(Item item) {
        type.add(new TextField());
        type.get(j).setText(item.getType());
    }

    private void addItemPrice(Item item, String style) {
        price.add(new TextField());
        price.get(j).setText(item.getPrice() + "");
        price.get(j).setEditable(false);
        price.get(j).setStyle(style);
    }

    private void addItemQuantity(Item item) {
        quantity.add(new ComboBox());

        for (int m = 1; m <= MAX_Item_Choice; m++) {
            quantity.get(j).getItems().add(m + "");
            quantity.get(j).setValue("1");
        }
    }

}
