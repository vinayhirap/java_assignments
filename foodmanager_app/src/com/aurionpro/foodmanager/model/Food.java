package com.aurionpro.foodmanager.model;

public class Food {
    private static int idCounter = 100;
    private int id;
    private String name;
    private double price;
    private TasteType tasteType;
    private FoodType foodType;

    public Food(String name, double price, TasteType tasteType, FoodType foodType) {
        this.id = idCounter++;
        this.name = name;
        this.price = price;
        this.tasteType = tasteType;
        this.foodType = foodType;
    }

    public Food(String name2, double price2, String category) {
	}

	public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public TasteType getTasteType() { return tasteType; }
    public FoodType getFoodType() { return foodType; }

    @Override
    public String toString() {
        return String.format("%-3d %-25s %-10s %-10s Rs. %.2f", id, name, foodType, tasteType, price);
    }
}
