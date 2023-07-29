package com.example.restaurantorder;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private ArrayList<Item> items;
    private double payment;

    public Order() {
        items = new ArrayList<>();
        payment = 0;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getPayment() {
        return payment;
    }

    public void addItem(Item item) {
        items.add(item);
        payment += item.getTotalPrice();
    }
}

