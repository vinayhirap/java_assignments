package com.aurionpro.foodmanager.model;

public class PreloadedMenuFactory {

	public static Menu getPreloadedMenu() {
		Menu menu = new Menu("Main Menu");
		int idCounter = 100;

		// INDIAN
		menu.addFood(new Food(idCounter++, "Paneer Butter Masala", 250, FoodType.VEG, CuisineType.INDIAN));
		menu.addFood(new Food(idCounter++, "Chicken Curry", 300, FoodType.NON_VEG, CuisineType.INDIAN));
		menu.addFood(new Food(idCounter++, "Dal Tadka", 180, FoodType.VEG, CuisineType.INDIAN));
		menu.addFood(new Food(idCounter++, "Mutton Rogan Josh", 350, FoodType.NON_VEG, CuisineType.INDIAN));
		menu.addFood(new Food(idCounter++, "Veg Biryani", 220, FoodType.VEG, CuisineType.INDIAN));

		// ITALIAN
		menu.addFood(new Food(idCounter++, "Margherita Pizza", 320, FoodType.VEG, CuisineType.ITALIAN));
		menu.addFood(new Food(idCounter++, "Pepperoni Pizza", 380, FoodType.NON_VEG, CuisineType.ITALIAN));
		menu.addFood(new Food(idCounter++, "Pasta Alfredo", 280, FoodType.VEG, CuisineType.ITALIAN));
		menu.addFood(new Food(idCounter++, "Chicken Lasagna", 400, FoodType.NON_VEG, CuisineType.ITALIAN));
		menu.addFood(new Food(idCounter++, "Bruschetta", 150, FoodType.VEG, CuisineType.ITALIAN));

		// MEXICAN
		menu.addFood(new Food(idCounter++, "Veg Quesadilla", 260, FoodType.VEG, CuisineType.MEXICAN));
		menu.addFood(new Food(idCounter++, "Chicken Tacos", 300, FoodType.NON_VEG, CuisineType.MEXICAN));
		menu.addFood(new Food(idCounter++, "Burrito Bowl", 280, FoodType.VEG, CuisineType.MEXICAN));
		menu.addFood(new Food(idCounter++, "Beef Nachos", 350, FoodType.NON_VEG, CuisineType.MEXICAN));
		menu.addFood(new Food(idCounter++, "Mexican Rice", 200, FoodType.VEG, CuisineType.MEXICAN));

		// CHINESE
		menu.addFood(new Food(idCounter++, "Veg Manchurian", 240, FoodType.VEG, CuisineType.CHINESE));
		menu.addFood(new Food(idCounter++, "Chicken Fried Rice", 270, FoodType.NON_VEG, CuisineType.CHINESE));
		menu.addFood(new Food(idCounter++, "Spring Rolls", 180, FoodType.VEG, CuisineType.CHINESE));
		menu.addFood(new Food(idCounter++, "Schezwan Noodles", 260, FoodType.VEG, CuisineType.CHINESE));
		menu.addFood(new Food(idCounter++, "Chili Chicken", 300, FoodType.NON_VEG, CuisineType.CHINESE));

		// AMERICAN
		menu.addFood(new Food(idCounter++, "Veg Burger", 180, FoodType.VEG, CuisineType.AMERICAN));
		menu.addFood(new Food(idCounter++, "Chicken Burger", 220, FoodType.NON_VEG, CuisineType.AMERICAN));
		menu.addFood(new Food(idCounter++, "French Fries", 120, FoodType.VEG, CuisineType.AMERICAN));
		menu.addFood(new Food(idCounter++, "BBQ Wings", 280, FoodType.NON_VEG, CuisineType.AMERICAN));
		menu.addFood(new Food(idCounter++, "Grilled Sandwich", 150, FoodType.VEG, CuisineType.AMERICAN));

		return menu;
	}
}
