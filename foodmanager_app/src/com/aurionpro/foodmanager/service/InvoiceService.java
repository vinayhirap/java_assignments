package com.aurionpro.foodmanager.service;

import com.aurionpro.foodmanager.model.*;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class InvoiceService {

	public void printInvoice(Order order, DeliveryAgent agent) {
		System.out.println("\n=========== INVOICE ===========");
		System.out.println("Order ID: " + order.getOrderId());
		System.out.println("Order Status: " + order.getStatus());
		System.out.println("Order Date & Time: "
				+ order.getOrderTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println("Customer: " + order.getCustomer().getName());
		System.out.println("Address: " + order.getCustomer().getAddress());
		System.out.println("Phone: " + order.getCustomer().getPhone());
		System.out.println();

		System.out.printf("%-25s %-5s %-10s%n", "Item", "Qty", "Price");
		System.out.println("---------------------------------------------");

		for (Map.Entry<Food, Integer> entry : order.getItemsOrdered().entrySet()) {
			Food food = entry.getKey();
			int qty = entry.getValue();
			double price = food.getPrice() * qty;
			System.out.printf("%-25s %-5d %-10.2f%n", food.getName(), qty, price);
		}

		System.out.println("---------------------------------------------");
		System.out.printf("%-30s %10.2f%n", "Subtotal:", order.getTotalAmount());
		System.out.printf("%-30s %10.2f%n", "Discount Applied:", order.getDiscountAmount());
		System.out.printf("%-30s %10.2f%n", "Delivery Fee:", order.getDeliveryFee());
		System.out.printf("%-30s %10.2f%n", "Total Payable:", order.getTotalPayable());
		System.out.printf("%-30s %10s%n", "Payment Mode:", order.getPaymentType());
		System.out.printf("%-30s %10s%n", "Delivery Agent:", agent.getName());
		System.out.println("=========== THANK YOU ===========");
	}
}
