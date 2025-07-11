package com.aurionpro.foodmanager.service;

import com.aurionpro.foodmanager.model.*;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    private static final String ADMIN_USER_ID = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public boolean login(Scanner sc) {
        System.out.print("Enter Admin User ID: ");
        String userId = sc.nextLine().trim();
        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine().trim();

        if (ADMIN_USER_ID.equals(userId) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Admin login successful. Welcome, Vinay!");
            return true;
        } else {
            System.out.println("Invalid Admin credentials. Returning to main menu.");
            return false;
        }
    }

    public void addFoodToMenu(Menu menu, Scanner sc) {
        System.out.print("Enter food name: ");
        String name = sc.nextLine().trim();
        double price = 0;
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
                System.out.println("Invalid price, please enter numeric value.");
            }
        }

        System.out.print("Enter category: ");
        String category = sc.nextLine().trim();

        Food food = new Food(name, price, category);
        menu.addFood(food);
        System.out.println("Food item added successfully to the menu.");
    }

    public void viewAllOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        System.out.printf("%-8s %-15s %-10s %-15s %-15s %-10s%n",
                "OrderID", "Customer", "Status", "Payment", "Order Time", "Amount");
        for (Order order : orders) {
            System.out.printf("%-8d %-15s %-10s %-15s %-15s Rs. %-10.2f%n",
                    order.getOrderId(),
                    order.getCustomer().getName(),
                    order.getStatus(),
                    order.getPaymentType(),
                    order.getOrderTime().toLocalTime().withNano(0),
                    order.getTotalPayable());
        }
    }

    public void viewAllAgents(List<DeliveryAgent> agents) {
        System.out.printf("%-5s %-20s%n", "ID", "Agent Name");
        int id = 1;
        for (DeliveryAgent agent : agents) {
            System.out.printf("%-5d %-20s%n", id++, agent.getName());
        }
    }

    public void viewAllPayments(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No payments recorded yet.");
            return;
        }
        System.out.printf("%-8s %-15s %-15s %-10s%n", "OrderID", "Payment Mode", "Customer", "Amount");
        for (Order order : orders) {
            System.out.printf("%-8d %-15s %-15s Rs. %-10.2f%n",
                    order.getOrderId(),
                    order.getPaymentType(),
                    order.getCustomer().getName(),
                    order.getTotalPayable());
        }
    }
}
