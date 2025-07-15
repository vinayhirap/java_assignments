package com.aurionpro.foodmanager.service;

import com.aurionpro.foodmanager.model.*;
import java.util.List;
import java.util.Scanner;

public class AdminService {

	public void adminPanel(Scanner sc, Menu menu, List<Order> allOrders, List<DeliveryAgent> agents) {
		int lastId = menu.getFoodList().stream().mapToInt(Food::getId).max().orElse(100);

		boolean running = true;
		while (running) {
			System.out.println("\n=== ADMIN PANEL ===");
			System.out.println("1. View Menu");
			System.out.println("2. Add Food Item to Menu");
			System.out.println("3. View Orders");
			System.out.println("4. View Payment History");
			System.out.println("5. Exit");
			System.out.print("Choice: ");
			String choice = sc.nextLine().trim();

			switch (choice) {
			case "1" -> menu.displayMenu();
			case "2" -> lastId = addFoodToMenu(menu, sc, lastId);
			case "3" -> viewOrders(allOrders);
			case "4" -> viewPayments(allOrders);
			case "5" -> running = false;
			default -> System.out.println("Invalid choice.");
			}
		}
	}

	private int addFoodToMenu(Menu menu, Scanner sc, int currentId) {
		System.out.print("Enter food name: ");
		String name = sc.nextLine().trim();

		double price;
		while (true) {
			System.out.print("Enter price: ");
			try {
				price = Double.parseDouble(sc.nextLine().trim());
				if (price <= 0) {
					System.out.println("Price must be positive.");
					continue;
				}
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid price. Enter a numeric value.");
			}
		}

		FoodType type;
		while (true) {
			System.out.print("Enter type (VEG/NON_VEG): ");
			String typeInput = sc.nextLine().trim().toUpperCase();
			try {
				type = FoodType.valueOf(typeInput);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid type. Enter VEG or NON_VEG.");
			}
		}

		CuisineType cuisine;
		while (true) {
			System.out.print("Enter cuisine (INDIAN/ITALIAN/MEXICAN/CHINESE/CONTINENTAL): ");
			String cuisineInput = sc.nextLine().trim().toUpperCase();
			try {
				cuisine = CuisineType.valueOf(cuisineInput);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid cuisine. Enter a valid option.");
			}
		}

		Food food = new Food(++currentId, name, price, type, cuisine);
		menu.addFood(food);
		System.out.println("Food item added successfully with ID: " + food.getId());
		return currentId;
	}

	private void viewOrders(List<Order> orders) {
		if (orders.isEmpty()) {
			System.out.println("No orders placed yet.");
			return;
		}
		System.out.printf("%-8s %-15s %-10s %-15s %-15s %-10s%n", "OrderID", "Customer", "Status", "Payment",
				"Order Time", "Amount");
		for (Order order : orders) {
			System.out.printf("%-8d %-15s %-10s %-15s %-15s Rs. %-10.2f%n", order.getOrderId(),
					order.getCustomer().getName(), order.getStatus(), order.getPaymentType(),
					order.getOrderTime().toLocalTime().withNano(0), order.getTotalPayable());
		}
	}

	private void viewPayments(List<Order> orders) {
		if (orders.isEmpty()) {
			System.out.println("No payments recorded yet.");
			return;
		}
		System.out.printf("%-8s %-15s %-15s %-10s%n", "OrderID", "Payment Mode", "Customer", "Amount");
		for (Order order : orders) {
			System.out.printf("%-8d %-15s %-15s Rs. %-10.2f%n", order.getOrderId(), order.getPaymentType(),
					order.getCustomer().getName(), order.getTotalPayable());
		}
	}
}
