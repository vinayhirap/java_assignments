package com.aurionpro.foodmanager.model;

public class Discount {
	private DiscountType discountType;

	public Discount(DiscountType discountType) {
		this.discountType = discountType;
	}

	public double applyDiscount(double total) {
		switch (discountType) {
		case FLAT_10_PERCENT:
			return total * 0.9;
		case FLAT_20_PERCENT:
			return total * 0.8;
		default:
			return total;
		}
	}
}
