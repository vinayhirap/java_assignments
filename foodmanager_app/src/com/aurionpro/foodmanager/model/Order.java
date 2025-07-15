package com.aurionpro.foodmanager.model;

import com.aurionpro.foodmanager.exception.InvalidInputException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
	private static int orderIdCounter = 500;
	private int orderId;
	private Customer customer;
	private Map<Food, Integer> itemsOrdered;
	private double totalPrice;
	private double discountAmount;
	private double totalAmount;
	private PaymentType paymentType;
	private LocalDateTime orderTime;
	private OrderStatus status;
	private static final double DELIVERY_FEE = 50.0;

	public Order() {
		this.orderId = orderIdCounter++;
		this.orderTime = LocalDateTime.now();
		this.status = OrderStatus.PLACED;
		this.itemsOrdered = new LinkedHashMap<>();
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getOrderId() {
		return orderId;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Map<Food, Integer> getItemsOrdered() {
		return itemsOrdered;
	}

	public void addItem(Food food, int quantity) {
		if (quantity <= 0)
			throw new InvalidInputException("Quantity must be greater than 0.");
		itemsOrdered.put(food, itemsOrdered.getOrDefault(food, 0) + quantity);
	}

	public void calculateTotal(DiscountType discountType) {
		totalAmount = 0;
		for (Map.Entry<Food, Integer> entry : itemsOrdered.entrySet()) {
			totalAmount += entry.getKey().getPrice() * entry.getValue();
		}

		if (discountType == DiscountType.FLAT_10_PERCENT) {
			discountAmount = totalAmount * 0.10;
		} else if (discountType == DiscountType.FLAT_20_PERCENT) {
			discountAmount = totalAmount * 0.20;
		} else {
			discountAmount = 0;
		}

		totalPrice = totalAmount - discountAmount + DELIVERY_FEE;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public double getTotalPayable() {
		return totalPrice;
	}

	public double getDeliveryFee() {
		return DELIVERY_FEE;
	}

	public double calculateTotalWithoutDiscount() {
		double total = 0;
		for (Map.Entry<Food, Integer> entry : itemsOrdered.entrySet()) {
			total += entry.getKey().getPrice() * entry.getValue();
		}
		return total;
	}

	public void clearCart() {
		itemsOrdered.clear();
	}
}
