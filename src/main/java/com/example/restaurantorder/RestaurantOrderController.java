package com.example.restaurantorder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

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

    private final int CHECKBOX_LOCATION = 3;

    public void initialize() {

        initializeItemComponents();
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

    private void dishNameDesign(Label itemType[]) {
        for (int k = 0; k < itemType.length; k++) {
            itemType[k].setStyle("-fx-font-weight: bold; -fx-font-size: 11pt;");
        }
    }

    private void initializeItemComponents() {
        select = new ArrayList<CheckBox>();
        quantity = new ArrayList<ComboBox>();
        type = new ArrayList<TextField>();
        description = new ArrayList<TextField>();
        price = new ArrayList<TextField>();
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

    @FXML
    void orderPressed(ActionEvent event) {
        Order order = new Order();
        int equalisation = 0;
        for (Node child : grid.getChildren()) {
            if (GridPane.getColumnIndex(child) == CHECKBOX_LOCATION && (child instanceof CheckBox)) {
                addSelectedItemsToOrder(order, child, equalisation);
            } else if (GridPane.getColumnIndex(child) == 0 && (child instanceof Label)) {
                equalisation++;
            }
        }

        if (!isOrderEmpty(order)) {
            orderSummaryOptions(order);
        }

    }

    private void addSelectedItemsToOrder(Order order, Node child, int equalisation) {
        if (((CheckBox) child).isSelected()) {
            int rowIndex = GridPane.getRowIndex(child);
            int currentIndex = rowIndex - equalisation;
            order.addItem(new Item(description.get(currentIndex).getText(), type.get(currentIndex).getText(),
                    Double.parseDouble(price.get(currentIndex).getText()), Integer.parseInt(quantity.get(currentIndex).getValue().toString())));
        }
    }

    private void orderSummaryOptions(Order order) {
        int choice = orderSummary(order);
        switch (choice) {
            case 0:
                if (saveOrder(order)) {
                    boardInitialization();
                }
                break;
            case 1:
                break;
            case 2:
                boardInitialization();
                break;
        }
    }

    private void boardInitialization() {
        for (int k = 0; k < j; k++) {
            select.get(k).setSelected(false);
            quantity.get(k).setValue("1");
        }
    }

    private Boolean saveOrder(Order order) {
        String message = "Please enter your name \nand next to it your ID number:";
        String fileName = JOptionPane.showInputDialog(message);
        if (isValidFileName(fileName, message)) {
            try {
                FileOutputStream fo = new FileOutputStream(fileName + ".txt");
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fo));
                out.write(order.getOrderDetails());
                out.close();
                fo.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "The order was not saved.");
                return false;
            }
            return true;
        }
        return false;
    }


    private boolean isValidFileName(String fileName, String message) {
        String name = "";
        int k;
        try {
            if (!(fileName.charAt(0) >= 'A' && fileName.charAt(0) <= 'Z'))
                throw new Error();

            for (k = 1; fileName.charAt(k) >= 'a' && fileName.charAt(k) <= 'z'; k++)
                name += fileName.charAt(k);
            if (name == "")
                throw new Error();
            String checkId = fileName.substring(k);
            Integer.parseInt(checkId);
        } catch (Throwable t) {
            makeErrorAlert("The order was not saved:", "The order name is invalid.");
            return false;
        }
        return true;
    }

    private int orderSummary(Order order) {
        Object options[] = {"Confirm Order", "Update Order", "Cancel Order"};
        JOptionPane JOptionPane = null;
        return JOptionPane.showOptionDialog(null, "Your Order:\n" + order.getOrderDetails(), "Order Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    private boolean isOrderEmpty(Order order) {
        if (order.getPayment() == 0) {
            makeErrorAlert("Invalid Order:", "Your Order Is Empty.");
            return true;
        }
        return false;
    }

    private void makeErrorAlert(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(text);
        Optional<ButtonType> option = alert.showAndWait();
    }
}
