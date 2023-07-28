package com.example.restaurantorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<Item> starters = new ArrayList<>();
    private ArrayList<Item> mainDishes = new ArrayList<>();
    private ArrayList<Item> lastDishes = new ArrayList<>();
    private ArrayList<Item> drinks = new ArrayList<>();

    public Menu(String fileName) {
        try {
            Scanner input = new Scanner((new File(fileName)));
            while (input.hasNext()) {
                String description = input.nextLine();
                String type = input.nextLine();
                double price = Double.parseDouble(input.nextLine());
                Item item = new Item(description, type, price, 0);
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
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
        } catch (NumberFormatException e) {
        }
    }

    public ArrayList<Item> getStarters() {
        return starters;
    }

    public ArrayList<Item> getMainDishes() {
        return mainDishes;
    }

    public ArrayList<Item> getLastDishes() {
        return lastDishes;
    }

    public ArrayList<Item> getDrinks() {
        return drinks;
    }
}
