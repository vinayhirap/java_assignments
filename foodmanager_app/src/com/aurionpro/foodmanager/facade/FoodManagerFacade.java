package com.aurionpro.foodmanager.facade;

import com.aurionpro.foodmanager.exception.*;
import com.aurionpro.foodmanager.model.*;
import com.aurionpro.foodmanager.service.*;

import java.util.*;
import java.util.concurrent.*;

public class FoodManagerFacade {

	private final List<Customer> customers = new ArrayList<>();
	private final List<Order> allOrders = new ArrayList<>();
	private final Map<Customer, Order> lastCompletedOrders = new HashMap<>();
	private final Menu menu = PreloadedMenuFactory.getPreloadedMenu();
	private final AdminService adminService = new AdminService();
	private final DeliveryService deliveryService = new DeliveryService();
	private final PaymentService paymentService = new PaymentService();
	private final InvoiceService invoiceService = new InvoiceService();
	private final List<DeliveryAgent> agents = Arrays.asList(new DeliveryAgent("Zomato"), new DeliveryAgent("Swiggy"));
	private final Scanner sc = new Scanner(System.in);

	public void start() {
		while (true) {
			printLine();
			System.out.printf("| %-40s |\n", "=== FOOD MANAGER APP ===");
			printLine();
			System.out.printf("| %-3s | %-30s |\n", "1", "Admin");
			System.out.printf("| %-3s | %-30s |\n", "2", "Customer");
			System.out.printf("| %-3s | %-30s |\n", "0", "Exit");
			printLine();

			int choice = getValidIntInput("Enter your choice: ");

			switch (choice) {
			case 1 -> handleAdmin();
			case 2 -> handleCustomerOptions();
			case 0 -> {
				System.out.println("Thank you for using the Food Manager App!");
				return;
			}
			default -> System.out.println("Invalid choice, please try again.");
			}
		}
	}

	private void handleAdmin() {
		System.out.print("Enter Admin ID: ");
		String adminId = sc.nextLine();
		System.out.print("Enter Password: ");
		String password = sc.nextLine();

		if (adminId.equalsIgnoreCase("admin") && password.equals("admin")) {
			adminService.adminPanel(sc, menu, allOrders, agents);
		} else {
			System.out.println("Invalid admin credentials.");
		}
	}

	private void handleCustomerOptions() {
		while (true) {
			System.out.println();
			printLine();
			System.out.printf("| %-35s |\n", "=== CUSTOMER OPTIONS ===");
			printLine();
			System.out.printf("| %-3s | %-30s |\n", "1", "New Customer Registration");
			System.out.printf("| %-3s | %-30s |\n", "2", "Existing Customer Login");
			System.out.printf("| %-3s | %-30s |\n", "0", "Back to Main Menu");
			printLine();

			int choice = getValidIntInput("Enter your choice: ");

			switch (choice) {
			case 1 -> handleCustomerRegistration();
			case 2 -> handleCustomerLogin();
			case 0 -> {
				return;
			}
			default -> System.out.println("Invalid choice, please try again.");
			}
		}
	}

	private void handleCustomerRegistration() {
		System.out.println("\n=== NEW CUSTOMER REGISTRATION ===");
		String name = getValidStringInput("Enter your name: ");
		String email = getValidEmailInput("Enter your email: ");
		String password = getValidPasswordInput("Set your password: ");
		System.out.print("Enter your address: ");
		String address = sc.nextLine().trim();
		String phone = getValidPhoneInput("Enter your 10-digit phone number: ");

		Customer newCustomer = new Customer(name, email, password, address, phone);
		customers.add(newCustomer);
		System.out.println("Registration successful! Please login as existing customer.");
	}

	private void handleCustomerLogin() {
		System.out.println("\n=== CUSTOMER LOGIN ===");
		String email = getValidEmailInput("Enter your email: ");
		System.out.print("Enter your password: ");
		String password = sc.nextLine().trim();

		Customer loggedInCustomer = customers.stream()
				.filter(c -> c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)).findFirst()
				.orElse(null);

		if (loggedInCustomer != null) {
			System.out.println("Login successful. Welcome, " + loggedInCustomer.getName() + "!");
			CustomerFacade customerFacade = new CustomerFacade(sc, loggedInCustomer, menu, deliveryService,
					paymentService, invoiceService, agents, allOrders, lastCompletedOrders);
			customerFacade.start();
		} else {
			System.out.println("Invalid email or password.");
		}
	}

	// Helper Input Utilities
	private int getValidIntInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Integer.parseInt(sc.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a numeric value.");
			}
		}
	}

	private String getValidStringInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = sc.nextLine().trim();
			if (!input.isEmpty())
				return input;
			else
				System.out.println("Input cannot be empty.");
		}
	}

	private String getValidEmailInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = sc.nextLine().trim();
			if (input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
				return input;
			} else {
				System.out.println("Invalid email format.");
			}
		}
	}

	private String getValidPasswordInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = sc.nextLine().trim();
			if (input.length() >= 4) {
				return input;
			} else {
				System.out.println("Password too short. Must be at least 4 characters.");
			}
		}
	}

	private String getValidPhoneInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = sc.nextLine().trim();
			if (input.matches("\\d{10}")) {
				return input;
			} else {
				System.out.println("Invalid phone number. Enter exactly 10 digits.");
			}
		}
	}

	private void printLine() {
		System.out.println("+------------------------------------------+");
	}
}
