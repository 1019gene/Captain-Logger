package com.example.jho.hackillinois;

public class Item {
    private double price;
    private String name;

    Item(String name, double price){
        this.price = price;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }
}
