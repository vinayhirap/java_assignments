package com.aurionpro.foodmanager.test;

import com.aurionpro.foodmanager.exception.*;
import com.aurionpro.foodmanager.model.*;
import com.aurionpro.foodmanager.service.*;

import java.util.*;
import java.util.concurrent.*;

public class FoodManagerAppMain {
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Order> allOrders = new ArrayList<>();
    private static final Map<Customer, Order> lastCompletedOrders = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = PreloadedMenuFactory.getPreloadedMenu();
        AdminService adminService = new AdminService();
        DeliveryService deliveryService = new DeliveryService();
        PaymentService paymentService = new PaymentService();
        InvoiceService invoiceService = new InvoiceService();
        List<DeliveryAgent> agents = Arrays.asList(new DeliveryAgent("Zomato"), new DeliveryAgent("Swiggy"));

        while (true) {
            System.out.println("\n=== FOOD MANAGER APP ===");
            System.out.println("1. Admin");
            System.out.println("2. New Customer Registration");
            System.out.println("3. Existing Customer Login");
            System.out.println("0. Exit");

            int choice = getValidIntInput(sc, "Enter your choice: ");

            switch (choice) {
                case 1 -> adminLogin(sc, menu, adminService, allOrders, agents);
                case 2 -> registerNewCustomer(sc);
                case 3 -> existingCustomerLogin(sc, menu, deliveryService, paymentService, invoiceService, agents);
                case 0 -> {
                    System.out.println("Thank you for using the Food Manager App!");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void adminLogin(Scanner sc, Menu menu, AdminService adminService,
                                   List<Order> allOrders, List<DeliveryAgent> agents) {
        System.out.print("Enter Admin ID: ");
        String adminId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (adminId.equalsIgnoreCase("admin") && password.equals("admin")) {
            boolean adminLoop = true;
            while (adminLoop) {
                System.out.println("\n=== ADMIN PANEL ===");
                System.out.println("1. Add Food");
                System.out.println("2. View Menu");
                System.out.println("3. View All Orders");
                System.out.println("4. View All Delivery Agents");
                System.out.println("5. View All Payments");
                System.out.println("0. Exit Admin Panel");

                int adminChoice = getValidIntInput(sc, "Choice: ");
                switch (adminChoice) {
                    case 1 -> adminService.addFoodToMenu(menu, sc);
                    case 2 -> menu.displayMenuWithNumbers();
                    case 3 -> adminService.viewAllOrders(allOrders);
                    case 4 -> adminService.viewAllAgents(agents);
                    case 5 -> adminService.viewAllPayments(allOrders);
                    case 0 -> adminLoop = false;
                    default -> System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private static void registerNewCustomer(Scanner sc) {
        System.out.println("\n=== NEW CUSTOMER REGISTRATION ===");
        String name = getValidStringInput(sc, "Enter your name: ");
        String email = getValidEmailInput(sc, "Enter your email: ");
        String password = getValidPasswordInput(sc, "Set your password: ");
        System.out.print("Enter your address: ");
        String address = sc.nextLine().trim(); // ✅ No validation now
        String phone = getValidPhoneInput(sc, "Enter your 10-digit phone number: ");

        Customer newCustomer = new Customer(name, email, password, address, phone);
        customers.add(newCustomer);
        System.out.println("Registration successful! Please login as existing customer.");
    }

    private static void existingCustomerLogin(Scanner sc, Menu menu, DeliveryService deliveryService,
                                              PaymentService paymentService, InvoiceService invoiceService,
                                              List<DeliveryAgent> agents) {
        System.out.println("\n=== CUSTOMER LOGIN ===");
        String email = getValidEmailInput(sc, "Enter your email: ");
        System.out.print("Enter your password: ");
        String password = sc.nextLine().trim();

        Customer loggedInCustomer = null;
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                loggedInCustomer = c;
                break;
            }
        }

        if (loggedInCustomer != null) {
            System.out.println("Login successful. Welcome, " + loggedInCustomer.getName() + "!");
            customerPanel(sc, loggedInCustomer, menu, deliveryService, paymentService, invoiceService, agents);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void customerPanel(Scanner sc, Customer customer, Menu menu,
                                      DeliveryService deliveryService, PaymentService paymentService,
                                      InvoiceService invoiceService, List<DeliveryAgent> agents) {
        Order order = new Order();
        order.setCustomer(customer);

        while (true) {
            System.out.println("\n=== CUSTOMER PANEL ===");
            System.out.println("1. View Menu and Add to Cart");
            System.out.println("2. View Order Status");
            System.out.println("3. View Order Details");
            System.out.println("0. Exit to Main Menu");

            int choice = getValidIntInput(sc, "Choice: ");

            switch (choice) {
                case 1 -> {
                    while (true) {
                        menu.displayMenuWithNumbers();
                        int itemNumber = getValidIntInput(sc, "Enter food number to add to cart (0 to stop): ");
                        if (itemNumber == 0) break;

                        List<Food> foodList = menu.getFoodList();
                        if (itemNumber < 1 || itemNumber > foodList.size()) {
                            System.out.println("Invalid selection.");
                            continue;
                        }

                        Food selectedFood = foodList.get(itemNumber - 1);
                        int qty = getValidIntInput(sc, "Enter quantity: ");
                        order.addItem(selectedFood, qty);
                        System.out.println("Added to cart successfully.");
                    }

                    if (order.getItemsOrdered().isEmpty()) {
                        System.out.println("Your cart is empty.");
                        break;
                    }

                    System.out.println("\n=== CART SUMMARY ===");
                    showCartSummary(order);

                    double cartTotal = order.calculateTotalWithoutDiscount();
                    if (cartTotal > 1000 && cartTotal <= 3000) {
                        System.out.println("Eligible for '10PERCENT' coupon. Enter to apply or press Enter to skip:");
                        String coupon = sc.nextLine().trim();
                        if (coupon.equalsIgnoreCase("10PERCENT")) {
                            order.calculateTotal(DiscountType.FLAT_10_PERCENT);
                            System.out.println("Coupon applied.");
                        } else {
                            order.calculateTotal(DiscountType.NONE);
                        }
                    } else if (cartTotal > 3000) {
                        System.out.println("Eligible for '20PERCENT' coupon. Enter to apply or press Enter to skip:");
                        String coupon = sc.nextLine().trim();
                        if (coupon.equalsIgnoreCase("20PERCENT")) {
                            order.calculateTotal(DiscountType.FLAT_20_PERCENT);
                            System.out.println("Coupon applied.");
                        } else {
                            order.calculateTotal(DiscountType.NONE);
                        }
                    } else {
                        order.calculateTotal(DiscountType.NONE);
                    }

                    System.out.println("Total payable: Rs. " + order.getTotalPayable());
                    System.out.println("Proceeding to payment...");

                    System.out.println("1. CASH\n2. UPI\n3. CREDIT CARD\n4. DEBIT CARD");
                    int payment = getValidIntInput(sc, "Choice: ");
                    switch (payment) {
                        case 1 -> order.setPaymentType(PaymentType.CASH);
                        case 2 -> order.setPaymentType(PaymentType.UPI);
                        case 3 -> order.setPaymentType(PaymentType.CREDIT_CARD);
                        case 4 -> order.setPaymentType(PaymentType.DEBIT_CARD);
                        default -> order.setPaymentType(PaymentType.CASH);
                    }

                    try {
                        DeliveryAgent agent = deliveryService.assignAgent(agents);
                        paymentService.processPayment(order);
                        invoiceService.printInvoice(order, agent);
                        allOrders.add(order);
                        lastCompletedOrders.put(customer, order);
                        simulateOrderStatus(order);
                        order = new Order();
                        order.setCustomer(customer);
                    } catch (InvalidPaymentException e) {
                        System.out.println("Payment failed: " + e.getMessage());
                    }
                }
                case 2 -> {
                    Order lastOrder = lastCompletedOrders.get(customer);
                    if (lastOrder != null) {
                        System.out.println("Current Order Status: " + lastOrder.getStatus());
                    } else {
                        System.out.println("No active orders found.");
                    }
                }
                case 3 -> {
                    Order lastOrder = lastCompletedOrders.get(customer);
                    if (lastOrder != null) {
                        System.out.println("\n=== LAST ORDER DETAILS ===");
                        System.out.println("Order ID: " + lastOrder.getOrderId());
                        System.out.println("Status: " + lastOrder.getStatus());
                        System.out.println("Date & Time: " + lastOrder.getOrderTime());
                        showCartSummary(lastOrder);
                        System.out.println("Total Payable: Rs. " + lastOrder.getTotalPayable());
                        System.out.println("Payment Method: " + lastOrder.getPaymentType());
                    } else {
                        System.out.println("No order details available.");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void showCartSummary(Order order) {
        System.out.printf("%-20s %-10s %-10s\n", "Item", "Qty", "Price");
        for (Map.Entry<Food, Integer> entry : order.getItemsOrdered().entrySet()) {
            Food food = entry.getKey();
            int qty = entry.getValue();
            double price = food.getPrice() * qty;
            System.out.printf("%-20s %-10d %-10.2f\n", food.getName(), qty, price);
        }
        System.out.println("Subtotal: Rs. " + order.getTotalAmount());
        System.out.println("Discount: Rs. " + order.getDiscountAmount());
        System.out.println("Delivery Fee: Rs. " + order.getDeliveryFee());
    }

    private static void simulateOrderStatus(Order order) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            order.setStatus(OrderStatus.PREPARING);
            System.out.println("Order Status Updated: PREPARING");
        }, 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
            System.out.println("Order Status Updated: OUT FOR DELIVERY");
        }, 15, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            order.setStatus(OrderStatus.DELIVERED);
            System.out.println("Order Status Updated: DELIVERED");
            scheduler.shutdown();
        }, 25, TimeUnit.SECONDS);
    }

    public static int getValidIntInput(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    public static String getValidStringInput(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            else System.out.println("Input cannot be empty.");
        }
    }

    public static String getValidEmailInput(Scanner sc, String prompt) {
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

    public static String getValidPasswordInput(Scanner sc, String prompt) {
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

    public static String getValidPhoneInput(Scanner sc, String prompt) {
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
}
