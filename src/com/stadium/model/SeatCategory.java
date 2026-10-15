package com.stadium.model;

public class SeatCategory {
    private int categoryId;
    private String categoryName;
    private double price;

    public SeatCategory(int categoryId, String categoryName, double price) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.price = price;
    }

    public int getCategoryId(){

        return categoryId;
    }

    public String getCategoryName(){

        return categoryName;
    }

    public double getPrice(){
        return price;
    }
}
