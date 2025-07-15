package com.aurionpro.foodmanager.model;

public class Food {
	private int id;
	private String name;
	private double price;
	private FoodType type;
	private CuisineType cuisine;

	public Food(int id, String name, double price, FoodType type, CuisineType cuisine) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
		this.cuisine = cuisine;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public FoodType getType() {
		return type;
	}

	public CuisineType getCuisine() {
		return cuisine;
	}

	public CuisineType getCuisineType() {
		return cuisine;
	}
}
