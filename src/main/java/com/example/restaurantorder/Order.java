package com.example.restaurantorder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The `Order` class represents a customer's order in a restaurant. It contains
 * a list of selected items and calculates the total payment for the order.
 */
public class Order implements Serializable {

    // The number of digits to display after the decimal point for prices
    private final int DIGIT_AFTER_POINT = 2;

    // List to store ordered items
    private ArrayList<Item> items;

    // Total payment for the order
    private double payment;

    /**
     * Constructs an empty `Order` object with no items and an initial payment of zero.
     */
    public Order() {
        items = new ArrayList<>();
        payment = 0;
    }

    /**
     * Retrieves the total payment for the order.
     *
     * @return The total payment for the order.
     */
    public double getPayment() {
        return payment;
    }

    /**
     * Adds an item to the order and updates the total payment.
     *
     * @param item The item to be added to the order.
     */
    public void addItem(Item item) {
        items.add(item);
        payment += item.getTotalPrice();
    }

    /**
     * Generates a formatted string containing the details of the order, including
     * item descriptions, quantities, and total prices.
     *
     * @return A string representing the order details.
     */
    public String getOrderDetails() {
        StringBuilder orderDetails = new StringBuilder();
        for (Item item : items) {
            orderDetails.append(item.getDescription()).append(" - $");
            orderDetails.append(convertPriceToString(item.getPrice())).append(" x ");
            orderDetails.append(item.getQuantity()).append(" = $");
            orderDetails.append(convertPriceToString(item.getTotalPrice())).append("\n");
        }
        orderDetails.append("Total: $").append(convertPriceToString(payment));
        return orderDetails.toString();
    }

    /**
     * Converts a double amount to a string with a specified number of digits
     * after the decimal point.
     *
     * @param amount The double amount to be converted to a string.
     * @return A formatted string representation of the amount.
     */
    private String convertPriceToString(double amount) {
        String cash = amount + "";
        int k, count = 0;
        boolean gotToPoint = false;
        for (k = 0; k < cash.length() && count != DIGIT_AFTER_POINT; k++) {
            if (gotToPoint)
                count++;
            if (cash.charAt(k) == '.')
                gotToPoint = true;
        }
        return cash.substring(0, k);
    }
}
