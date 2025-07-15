package com.aurionpro.foodmanager.facade;

import com.aurionpro.foodmanager.exception.InvalidPaymentException;
import com.aurionpro.foodmanager.model.*;
import com.aurionpro.foodmanager.service.*;

import java.util.*;
import java.util.concurrent.*;

public class CustomerFacade {

	private final Scanner sc;
	private final Customer customer;
	private final Menu menu;
	private final DeliveryService deliveryService;
	private final PaymentService paymentService;
	private final InvoiceService invoiceService;
	private final List<DeliveryAgent> agents;
	private final List<Order> allOrders;
	private final Map<Customer, Order> lastCompletedOrders;

	public CustomerFacade(Scanner sc, Customer customer, Menu menu, DeliveryService deliveryService,
			PaymentService paymentService, InvoiceService invoiceService, List<DeliveryAgent> agents,
			List<Order> allOrders, Map<Customer, Order> lastCompletedOrders) {

		this.sc = sc;
		this.customer = customer;
		this.menu = menu;
		this.deliveryService = deliveryService;
		this.paymentService = paymentService;
		this.invoiceService = invoiceService;
		this.agents = agents;
		this.allOrders = allOrders;
		this.lastCompletedOrders = lastCompletedOrders;
	}

	public void start() {
		Order order = new Order();
		order.setCustomer(customer);

		while (true) {
			System.out.println("\n=== CUSTOMER PANEL ===");
			System.out.println("1. View Menu and Add to Cart");
			System.out.println("2. View Order Status");
			System.out.println("3. View Order Details");
			System.out.println("0. Exit to Main Menu");

			System.out.print("Choice: ");
			String input = sc.nextLine().trim();

			switch (input) {
			case "1" -> handleCart(order);
			case "2" -> showOrderStatus();
			case "3" -> showOrderDetails();
			case "0" -> {
				return;
			}
			default -> System.out.println("Invalid choice.");
			}
		}
	}

	private void handleCart(Order order) {
		while (true) {
			menu.displayMenu();

			int itemId = getValidIntInput("Enter food ID to add to cart (0 to stop): ");
			if (itemId == 0)
				break;

			Food selectedFood = menu.getFoodById(itemId);
			if (selectedFood == null) {
				System.out.println("Invalid ID. Please select a valid Food ID from the menu.");
				continue;
			}

			int qty = getValidIntInput("Enter quantity: ");
			if (qty <= 0) {
				System.out.println("Quantity must be positive.");
				continue;
			}

			order.addItem(selectedFood, qty);
			System.out.println("Added to cart successfully: " + selectedFood.getName() + " x " + qty);
		}

		if (order.getItemsOrdered().isEmpty()) {
			System.out.println("Your cart is empty.");
			return;
		}

		order.calculateTotal(DiscountType.NONE);

		System.out.println("\n=== CART SUMMARY ===");
		showCartSummary(order);

		double cartTotal = order.calculateTotalWithoutDiscount();
		DiscountType discountType = DiscountType.NONE;

		if (cartTotal > 1000 && cartTotal <= 3000) {
			System.out.println("Eligible for '10PERCENT' coupon. Enter '10PERCENT' to apply or press Enter to skip:");
			String coupon = sc.nextLine().trim();
			if (coupon.equalsIgnoreCase("10PERCENT")) {
				discountType = DiscountType.FLAT_10_PERCENT;
				System.out.println("Coupon applied.");
			}
		} else if (cartTotal > 3000) {
			System.out.println("Eligible for '20PERCENT' coupon. Enter '20PERCENT' to apply or press Enter to skip:");
			String coupon = sc.nextLine().trim();
			if (coupon.equalsIgnoreCase("20PERCENT")) {
				discountType = DiscountType.FLAT_20_PERCENT;
				System.out.println("Coupon applied.");
			}
		}

		order.calculateTotal(discountType);
		System.out.printf("Total payable: Rs. %.2f%n", order.getTotalPayable());
		System.out.println("Proceeding to payment...");

		System.out.println("1. CASH\n2. UPI\n3. CREDIT CARD\n4. DEBIT CARD");
		int payment = getValidIntInput("Choice: ");
		switch (payment) {
		case 1 -> order.setPaymentType(PaymentType.CASH);
		case 2 -> order.setPaymentType(PaymentType.UPI);
		case 3 -> order.setPaymentType(PaymentType.CREDIT_CARD);
		case 4 -> order.setPaymentType(PaymentType.DEBIT_CARD);
		default -> {
			System.out.println("Invalid choice. Defaulting to CASH.");
			order.setPaymentType(PaymentType.CASH);
		}
		}

		try {
			DeliveryAgent agent = deliveryService.assignAgent(agents);
			paymentService.processPayment(order);
			invoiceService.printInvoice(order, agent);
			allOrders.add(order);
			lastCompletedOrders.put(customer, order);
			simulateOrderStatus(order);
			System.out.println("Order placed successfully!");
		} catch (InvalidPaymentException e) {
			System.out.println("Payment failed: " + e.getMessage());
		}
	}

	private void showOrderStatus() {
		Order lastOrder = lastCompletedOrders.get(customer);
		if (lastOrder != null) {
			System.out.println("Current Order Status: " + lastOrder.getStatus());
		} else {
			System.out.println("No active orders found.");
		}
	}

	private void showOrderDetails() {
		Order lastOrder = lastCompletedOrders.get(customer);
		if (lastOrder != null) {
			System.out.println("\n=== LAST ORDER DETAILS ===");
			System.out.println("Order ID: " + lastOrder.getOrderId());
			System.out.println("Status: " + lastOrder.getStatus());
			System.out.println("Date & Time: " + lastOrder.getOrderTime());
			showCartSummary(lastOrder);
			System.out.printf("Total Payable: Rs. %.2f%n", lastOrder.getTotalPayable());
			System.out.println("Payment Method: " + lastOrder.getPaymentType());
		} else {
			System.out.println("No order details available.");
		}
	}

	private void showCartSummary(Order order) {
		String line = "+-------------------------------------------------------+";
		System.out.println(line);
		System.out.printf("| %-30s | %-5s | %-10s |\n", "Item", "Qty", "Price");
		System.out.println(line);

		for (Map.Entry<Food, Integer> entry : order.getItemsOrdered().entrySet()) {
			Food food = entry.getKey();
			int qty = entry.getValue();
			double price = food.getPrice() * qty;
			System.out.printf("| %-30s | %-5d | Rs. %-7.2f |\n", food.getName(), qty, price);
		}
		System.out.println(line);
		System.out.printf("| %-30s | %-5s | Rs. %-7.2f |\n", "Subtotal", "", order.getTotalAmount());
		System.out.printf("| %-30s | %-5s | Rs. %-7.2f |\n", "Discount", "", order.getDiscountAmount());
		System.out.printf("| %-30s | %-5s | Rs. %-7.2f |\n", "Delivery Fee", "", order.getDeliveryFee());
		System.out.println(line);
	}

	private void simulateOrderStatus(Order order) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.schedule(() -> {
			order.setStatus(OrderStatus.PREPARING);
			System.out.println("Order Status Updated: PREPARING");
		}, 5, TimeUnit.SECONDS);

		scheduler.schedule(() -> {
			order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
			System.out.println("Order Status Updated: OUT FOR DELIVERY");
		}, 10, TimeUnit.SECONDS);

		scheduler.schedule(() -> {
			order.setStatus(OrderStatus.DELIVERED);
			System.out.println("Order Status Updated: DELIVERED");
			scheduler.shutdown();
		}, 15, TimeUnit.SECONDS);
	}

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
}
