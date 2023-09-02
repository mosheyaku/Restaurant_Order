package com.example.restaurantorder;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * The `Menu` class represents a restaurant menu containing various categories of items,
 * such as starters, main dishes, last dishes, and drinks. It loads menu items from a file
 * and organizes them into different categories.
 */
public class Menu {

    // ArrayLists to store menu items in different categories
    private ArrayList<Item> starters = new ArrayList<>();
    private ArrayList<Item> mainDishes = new ArrayList<>();
    private ArrayList<Item> lastDishes = new ArrayList<>();
    private ArrayList<Item> drinks = new ArrayList<>();

    /**
     * Constructs a `Menu` object by loading menu items from a file and organizing them
     * into different categories based on their type.
     *
     * @param fileName The name of the file containing menu item information.
     */
    public Menu(String fileName) {
        try {
            // Read menu items from the specified file and categorize them
            Scanner input = new Scanner((new File(fileName)));
            while (input.hasNext()) {
                String description = input.nextLine();
                String type = input.nextLine();
                double price = Double.parseDouble(input.nextLine());
                Item item = new Item(description, type, price, 0);
                matchingItemToType(input, item, type);
            }
            input.close();
        } catch (FileNotFoundException e) {
            invalidMenu("Error: file not found");
        } catch (NumberFormatException e) {
            invalidMenu("Error: invalid format in file");
        }
    }

    /**
     * Retrieves the list of starter items in the menu.
     *
     * @return The list of starter items.
     */
    public ArrayList<Item> getStarters() {
        return starters;
    }

    /**
     * Retrieves the list of main dish items in the menu.
     *
     * @return The list of main dish items.
     */
    public ArrayList<Item> getMainDishes() {
        return mainDishes;
    }

    /**
     * Retrieves the list of last dish items in the menu.
     *
     * @return The list of last dish items.
     */
    public ArrayList<Item> getLastDishes() {
        return lastDishes;
    }

    /**
     * Retrieves the list of drink items in the menu.
     *
     * @return The list of drink items.
     */
    public ArrayList<Item> getDrinks() {
        return drinks;
    }

    /**
     * Helper method to categorize and add an item to the appropriate category.
     *
     * @param input The Scanner for reading menu item information from the file.
     * @param item  The item to be categorized and added.
     * @param type  The type or category of the item.
     */
    private void matchingItemToType(Scanner input, Item item, String type) {
        switch (type) {
            case "Starter":
                starters.add(item);
                break;
            case "Main Dish":
                mainDishes.add(item);
                break;
            case "Last Dish":
                lastDishes.add(item);
                break;
            case "Drink":
                drinks.add(item);
                break;
            default:
                input.close();
                invalidMenu("Invalid type: " + type);
        }
    }

    /**
     * Helper method to display an error message and exit the application.
     *
     * @param errorMessage The error message to be displayed.
     */
    private void invalidMenu(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Menu:");
        alert.setContentText(errorMessage);
        Optional<ButtonType> option = alert.showAndWait();
        exit(0);
    }
}
