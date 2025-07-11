package com.aurionpro.foodmanager.model;

public class PreloadedMenuFactory {

	public static Menu getPreloadedMenu() {
		Menu menu = new Menu("Main Menu");

		// SPICY
		menu.addFood(new Food("Chicken Biryani", 300, TasteType.SPICY, FoodType.NON_VEG));
		menu.addFood(new Food("Spicy Paneer Tikka", 220, TasteType.SPICY, FoodType.VEG));
		menu.addFood(new Food("Chili Chicken", 280, TasteType.SPICY, FoodType.NON_VEG));
		menu.addFood(new Food("Spicy Veg Noodles", 180, TasteType.SPICY, FoodType.VEG));
		menu.addFood(new Food("Schezwan Rice", 200, TasteType.SPICY, FoodType.VEG));
		menu.addFood(new Food("Pepper Chicken", 250, TasteType.SPICY, FoodType.NON_VEG));
		menu.addFood(new Food("Spicy Mutton Curry", 350, TasteType.SPICY, FoodType.NON_VEG));
		menu.addFood(new Food("Spicy Potato Wedges", 120, TasteType.SPICY, FoodType.VEG));
		menu.addFood(new Food("Hot Wings", 230, TasteType.SPICY, FoodType.NON_VEG));
		menu.addFood(new Food("Spicy Fish Fry", 300, TasteType.SPICY, FoodType.NON_VEG));

		// MEDIUM
		menu.addFood(new Food("Paneer Butter Masala", 250, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Veg Pulao", 160, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Dal Makhani", 180, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Butter Chicken", 320, TasteType.MEDIUM, FoodType.NON_VEG));
		menu.addFood(new Food("Kadhai Paneer", 240, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Mushroom Masala", 210, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Jeera Rice", 130, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Aloo Gobi", 150, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Mixed Veg Curry", 170, TasteType.MEDIUM, FoodType.VEG));
		menu.addFood(new Food("Chicken Curry", 290, TasteType.MEDIUM, FoodType.NON_VEG));

		// SWEET
		menu.addFood(new Food("Gulab Jamun", 60, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Rasgulla", 50, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Jalebi", 40, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Kheer", 70, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Ice Cream", 80, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Rasmalai", 90, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Ladoo", 30, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Halwa", 55, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Barfi", 60, TasteType.SWEET, FoodType.VEG));
		menu.addFood(new Food("Chocolate Brownie", 100, TasteType.SWEET, FoodType.VEG));

		return menu;
	}
}
