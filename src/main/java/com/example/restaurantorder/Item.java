package com.example.restaurantorder;

/**
 * The `Item` class represents a menu item in a restaurant order.
 * It encapsulates information such as description, type, price, and quantity.
 */
public class Item {

    // Fields to store item information
    private String description;
    private String type;
    private double price;
    private int quantity;

    /**
     * Constructs an `Item` object with the specified parameters.
     *
     * @param description The description or name of the item.
     * @param type        The type or category of the item (e.g., Starter, Main Dish, etc.).
     * @param price       The price of a single unit of the item.
     * @param quantity    The quantity of this item in the order.
     */
    public Item(String description, String type, double price, int quantity) {
        this.description = description;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Retrieves the description of the item.
     *
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the type or category of the item.
     *
     * @return The type of the item.
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the price of a single unit of the item.
     *
     * @return The price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the quantity of this item in the order.
     *
     * @return The quantity of the item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates and retrieves the total price for the quantity of this item.
     *
     * @return The total price for the quantity of the item.
     */
    public double getTotalPrice() {
        return price * quantity;
    }
}
