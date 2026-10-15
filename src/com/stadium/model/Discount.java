package com.stadium.model;

public class Discount {
    private int discountId;
    private String discountName;
    private double percentage;

    public Discount(int discountId, String discountName, double percentage) {
        this.discountId = discountId;
        this.discountName = discountName;
        this.percentage = percentage;
    }

    public int getDiscountId() {
        return discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public double getPercentage() {
        return percentage;
    }

    public double calculateDiscount(double subtotal) {
        return subtotal * (percentage / 100);
    }
}
