package com.example.lab2_5jan_rinku;
import java.io.Serializable;
import java.util.Date;

public class PurchaseHistory implements Serializable {
    private String name;
    private int quantity;
    private double totalPrice;
    private Date purchaseDate;

    public PurchaseHistory(String name, int quantity, double totalPrice, Date purchaseDate) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }
}

