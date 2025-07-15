package com.aurionpro.foodmanager.service;

import com.aurionpro.foodmanager.exception.InvalidPaymentException;
import com.aurionpro.foodmanager.model.Order;
import com.aurionpro.foodmanager.model.PaymentType;

import java.util.Scanner;

public class PaymentService {

	public void processPayment(Order order) throws InvalidPaymentException {
		Scanner sc = new Scanner(System.in);

		switch (order.getPaymentType()) {
		case CASH:
			System.out.println("Payment received in cash.");
			break;

		case UPI:
			while (true) {
				System.out.print("Enter UPI ID: ");
				String upi = sc.nextLine().trim();

				if (upi.contains("@")) {
					String[] parts = upi.split("@");
					if (parts.length == 2 && !parts[0].isEmpty() && parts[1].matches("[a-zA-Z]+")) {
						System.out.println("Payment received via UPI: " + upi);
						break;
					} else {
						System.out.println(
								"Invalid UPI ID format. It should have text or numbers before '@' and alphabets after '@'.");
					}
				} else {
					System.out.println("Invalid UPI ID format. It should contain '@'.");
				}
			}
			break;

		case CREDIT_CARD:
		case DEBIT_CARD:
			String cardNumber;
			while (true) {
				System.out.print("Enter card number (16 digits): ");
				cardNumber = sc.nextLine().replaceAll("\\s", "");
				if (cardNumber.matches("\\d{16}")) {
					break;
				} else {
					System.out.println("Invalid card number. Please enter exactly 16 digits.");
				}
			}

			String expiryDate;
			while (true) {
				System.out.print("Enter card expiry date (MM/YY): ");
				expiryDate = sc.nextLine().trim();
				if (expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
					break;
				} else {
					System.out.println("Invalid expiry date format. Please enter in MM/YY format.");
				}
			}

			String cardHolderName;
			while (true) {
				System.out.print("Enter cardholder name: ");
				cardHolderName = sc.nextLine().trim();
				if (cardHolderName.matches("[a-zA-Z ]+")) {
					break;
				} else {
					System.out.println("Invalid name. Please enter alphabetic characters only.");
				}
			}

			System.out.println(
					"Payment received via " + order.getPaymentType() + " ending with " + cardNumber.substring(12));
			break;

		default:
			throw new InvalidPaymentException("Unsupported payment type.");
		}
	}
}
