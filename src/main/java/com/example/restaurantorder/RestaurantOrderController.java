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

/**
 * The `RestaurantOrderController` class serves as the controller for the graphical user interface (GUI)
 * of the restaurant order management system. It handles user interactions, order processing, and user input validation.
 */
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

    /**
     * Initializes the controller when the corresponding FXML layout is loaded.
     * This method sets up the initial state of the GUI, loads the menu, and populates the menu items.
     */
    public void initialize() {

        initializeItemComponents();
        Menu menu = new Menu("menu.txt");
        insertHeadingsToMenu();
        Label itemType[] = new Label[]{new Label("Starter"), new Label("Main Dish"), new Label("Last Dish"), new Label("Drink")};
        dishNameDesign(itemType);
        insertItemsByTypeToMenu(menu, itemType);
    }

    /**
     * Inserts headings (labels) for the menu columns: "Dish Name," "Price," and "Quantity" into the GUI.
     * The headings are styled with bold and italic text at a larger font size.
     */
    private void insertHeadingsToMenu() {
        Label title[] = new Label[]{new Label("Dish Name"), new Label("Price"), new Label("Quantity")};
        for (int k = 0; k < title.length; k++) {
            title[k].setStyle("-fx-font-weight: bold italic; -fx-font-size: 12pt;");
        }
        grid.addRow(i, title[0], title[1], title[2]);
        i++;
    }

    /**
     * Inserts menu items categorized by type (e.g., starters, main dishes, etc.) into the GUI.
     * It arranges the items in rows, one category at a time.
     *
     * @param menu     The `Menu` object containing menu items.
     * @param itemType An array of labels representing the categories (e.g., "Starter," "Main Dish").
     */
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

    /**
     * Applies a specific style (bold and increased font size) to the category labels in the GUI.
     *
     * @param itemType An array of labels representing the categories (e.g., "Starter," "Main Dish").
     */

    private void dishNameDesign(Label itemType[]) {
        for (int k = 0; k < itemType.length; k++) {
            itemType[k].setStyle("-fx-font-weight: bold; -fx-font-size: 11pt;");
        }
    }

    /**
     * Initializes various lists (e.g., select, quantity, type, description, price) used to manage menu items
     * and their corresponding UI components in the GUI.
     */
    private void initializeItemComponents() {
        select = new ArrayList<CheckBox>();
        quantity = new ArrayList<ComboBox>();
        type = new ArrayList<TextField>();
        description = new ArrayList<TextField>();
        price = new ArrayList<TextField>();
    }

    /**
     * Populates the GUI with menu items for a specific category, such as starters or main dishes.
     *
     * @param itemType A list of menu items belonging to a particular category.
     */
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

    /**
     * Adds a description (name) of a menu item to the GUI along with the specified style.
     *
     * @param item  The menu item to be displayed.
     * @param style The style applied to the description text field.
     */

    private void addItemDescription(Item item, String style) {
        description.add(new TextField());
        description.get(j).setText(item.getDescription());
        description.get(j).setEditable(false);
        description.get(j).setStyle(style);
    }

    /**
     * Adds the type (category) of a menu item to the GUI.
     *
     * @param item The menu item to be displayed.
     */
    private void addItemType(Item item) {
        type.add(new TextField());
        type.get(j).setText(item.getType());
    }

    /**
     * Adds the price of a menu item to the GUI along with the specified style.
     *
     * @param item  The menu item to be displayed.
     * @param style The style applied to the price text field.
     */
    private void addItemPrice(Item item, String style) {
        price.add(new TextField());
        price.get(j).setText(item.getPrice() + "");
        price.get(j).setEditable(false);
        price.get(j).setStyle(style);
    }

    /**
     * Adds a quantity selection ComboBox for a menu item to the GUI.
     *
     * @param item The menu item to be displayed.
     */
    private void addItemQuantity(Item item) {
        quantity.add(new ComboBox());

        for (int m = 1; m <= MAX_Item_Choice; m++) {
            quantity.get(j).getItems().add(m + "");
            quantity.get(j).setValue("1");
        }
    }

    /**
     * Handles the event when the "Order" button is pressed in the GUI. It processes the user's order,
     * calculates the total payment, and provides options to save, update, or cancel the order.
     *
     * @param event The ActionEvent triggered by the "Order" button press.
     */
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

    /**
     * Adds selected menu items to the provided order. It checks if a CheckBox associated with a menu item is selected
     * and, if so, adds the item to the order with the specified quantity.
     *
     * @param order        The `Order` object to which selected menu items should be added.
     * @param child        The GUI component (CheckBox) representing a menu item.
     * @param equalisation An integer used to calculate the correct menu item index based on the grid layout.
     */
    private void addSelectedItemsToOrder(Order order, Node child, int equalisation) {
        if (((CheckBox) child).isSelected()) {
            int rowIndex = GridPane.getRowIndex(child);
            int currentIndex = rowIndex - equalisation;
            order.addItem(new Item(description.get(currentIndex).getText(), type.get(currentIndex).getText(),
                    Double.parseDouble(price.get(currentIndex).getText()), Integer.parseInt(quantity.get(currentIndex).getValue().toString())));
        }
    }

    /**
     * Displays order summary options in a dialog box and performs actions based on the user's choice.
     * The options include confirming, updating, or canceling the order.
     *
     * @param order The `Order` object representing the customer's order.
     */
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

    /**
     * Initializes the GUI by resetting the state of checkboxes and quantity selection ComboBoxes.
     * This method is called after an order has been confirmed, updated, or canceled.
     */
    private void boardInitialization() {
        for (int k = 0; k < j; k++) {
            select.get(k).setSelected(false);
            quantity.get(k).setValue("1");
        }
    }

    /**
     * Saves the customer's order details to a text file with a user-provided file name.
     *
     * @param order The `Order` object representing the customer's order to be saved.
     * @return True if the order was successfully saved, false otherwise.
     */
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


    /**
     * Validates the provided file name for saving the order. It checks if the file name starts with a capital letter,
     * followed by lowercase letters, and ends with an ID number.
     *
     * @param fileName The user-provided file name for saving the order.
     * @param message  The message to be displayed if the file name is invalid.
     * @return True if the file name is valid, false otherwise.
     */
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

    /**
     * Displays an order summary dialog box with options for confirming, updating, or canceling the order.
     *
     * @param order The `Order` object representing the customer's order.
     * @return An integer representing the user's choice (0 for Confirm, 1 for Update, 2 for Cancel).
     */
    private int orderSummary(Order order) {
        Object options[] = {"Confirm Order", "Update Order", "Cancel Order"};
        JOptionPane JOptionPane = null;
        return JOptionPane.showOptionDialog(null, "Your Order:\n" + order.getOrderDetails(), "Order Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    /**
     * Checks whether the order is empty (contains no items) and displays an error message if it is.
     *
     * @param order The order to be checked for emptiness.
     * @return True if the order is empty, false otherwise.
     */
    private boolean isOrderEmpty(Order order) {
        if (order.getPayment() == 0) {
            makeErrorAlert("Invalid Order:", "Your Order Is Empty.");
            return true;
        }
        return false;
    }

    /**
     * Displays an error alert with a specified header and message.
     *
     * @param header The header text for the error alert.
     * @param text   The message text for the error alert.
     */
    private void makeErrorAlert(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(text);
        Optional<ButtonType> option = alert.showAndWait();
    }
}
