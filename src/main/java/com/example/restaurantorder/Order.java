package com.example.restaurantorder;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private final int DIGIT_AFTER_POINT = 2;

    private ArrayList<Item> items;
    private double payment;

    public Order() {
        items = new ArrayList<>();
        payment = 0;
    }

    public double getPayment() {
        return payment;
    }

    public void addItem(Item item) {
        items.add(item);
        payment += item.getTotalPrice();
    }

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

