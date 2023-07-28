package com.example.restaurantorder;

public class Item {

    private String description;

    private String type;

    private double price;

    private int quantity;

    public Item(String description, String type, double price, int quantity) {
        this.description = description;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
