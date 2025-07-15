package com.aurionpro.foodmanager.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	private final List<Food> foodList;
	private String name;

	// Default constructor
	public Menu() {
		this.foodList = new ArrayList<>();
	}

	public Menu(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addFood(Food food) {
		this.foodList.add(food);
	}

	public List<Food> getFoodList() {
		return foodList;
	}

	public Food getFoodById(int id) {
		for (Food food : foodList) {
			if (food.getId() == id) {
				return food;
			}
		}
		return null; // Not found
	}

	public void displayMenu() {
		System.out.println("+----------------------------------------------------------------------------+");
		if (name != null) {
			System.out.printf("| %-74s |\n", "=== " + name.toUpperCase() + " ===");
			System.out.println("+----------------------------------------------------------------------------+");
		} else {
			System.out.printf("| %-74s |\n", "=== MENU ===");
			System.out.println("+----------------------------------------------------------------------------+");
		}
		System.out.printf("| %-6s | %-5s | %-25s | %-10s | %-10s | %-10s |\n", "S.No", "ID", "Name", "Type", "Cuisine",
				"Price");
		System.out.println("+----------------------------------------------------------------------------+");

		int count = 1;
		for (Food food : foodList) {
			System.out.printf("| %-6d | %-5d | %-25s | %-10s | %-10s | Rs. %-6.2f |\n", count++, food.getId(),
					food.getName(), food.getType(), food.getCuisineType(), food.getPrice());
		}
		System.out.println("+----------------------------------------------------------------------------+");
	}
}
