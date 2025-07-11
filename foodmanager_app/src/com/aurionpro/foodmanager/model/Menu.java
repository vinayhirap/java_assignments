package com.aurionpro.foodmanager.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String name;
    private List<Food> foodList;

    public Menu(String name) {
        this.name = name;
        this.foodList = new ArrayList<>();
    }

    public void addFood(Food food) {
        foodList.add(food);
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void displayMenuWithNumbers() {
        System.out.println("=== " + name + " ===");
        System.out.printf("%-5s %-5s %-20s %-10s %-10s %-10s%n", "No.", "ID", "Name", "Type", "Taste", "Price");
        int i = 1;
        for (Food food : foodList) {
            System.out.printf("%-5d %-5d %-20s %-10s %-10s Rs. %-10.2f%n",
                    i++,
                    food.getId(),
                    food.getName(),
                    food.getFoodType(),
                    food.getTasteType(),
                    food.getPrice());
        }
    }

    public String getName() {
        return name;
    }
}
