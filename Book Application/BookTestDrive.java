package com.aurionpro.test;

import com.aurionpro.model.Book;
import com.aurionpro.model.AuthorComparator;
import com.aurionpro.model.BookDescendingTitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BookTestDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Book> availableBooks = new ArrayList<>();
		ArrayList<Book> issuedBooks = new ArrayList<>();
		boolean running = true;

		while (running) {
			System.out.println("\n--- Book Management System ---");
			System.out.println("1. Add Book");
			System.out.println("2. Issue Book");
			System.out.println("3. Display Available Books");
			System.out.println("4. Display Issued Books");
			System.out.println("5. Return Book");
			System.out.println("6. Sort Books");
			System.out.println("7. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1 -> {
				System.out.print("Enter author name: ");
				String author = scanner.nextLine();
				System.out.print("Enter book title: ");
				String title = scanner.nextLine();
				System.out.print("Enter price: ");
				double price = scanner.nextDouble();
				scanner.nextLine();
				availableBooks.add(new Book(author, title, price));
				System.out.println("Book added successfully!");
			}
			case 2 -> {
				System.out.print("Enter book ID to issue: ");
				int issueId = scanner.nextInt();
				scanner.nextLine();
				boolean found = false;
				for (int i = 0; i < availableBooks.size(); i++) {
					if (availableBooks.get(i).getBookId() == issueId) {
						Book book = availableBooks.remove(i);
						book.setIssued(true);
						issuedBooks.add(book);
						System.out.println("Book issued successfully!");
						found = true;
						break;
					}
				}
				if (!found)
					System.out.println("Book not found.");
			}
			case 3 -> {
				if (availableBooks.isEmpty()) {
					System.out.println("No available books.");
				} else {
					System.out.println("--- Available Books ---");
					for (Book book : availableBooks) {
						book.displayDetails();
					}
				}
			}
			case 4 -> {
				if (issuedBooks.isEmpty()) {
					System.out.println("No issued books.");
				} else {
					System.out.println("--- Issued Books ---");
					for (Book book : issuedBooks) {
						book.displayDetails();
					}
				}
			}
			case 5 -> {
				System.out.print("Enter book ID to return: ");
				int returnId = scanner.nextInt();
				scanner.nextLine();
				boolean found = false;
				for (int i = 0; i < issuedBooks.size(); i++) {
					if (issuedBooks.get(i).getBookId() == returnId) {
						Book book = issuedBooks.remove(i);
						book.setIssued(false);
						availableBooks.add(book);
						System.out.println("Book returned successfully!");
						found = true;
						break;
					}
				}
				if (!found)
					System.out.println("Book not found.");
			}
			case 6 -> {
				System.out.println(
						"Sort by:\n1. Author (Ascending using Comparable)\n2. Title (Descending)\n3. Author (Ascending using Comparator)");
				int sortChoice = scanner.nextInt();
				scanner.nextLine();
				if (sortChoice == 1) {
					Collections.sort(availableBooks);
					Collections.sort(issuedBooks);
					System.out.println("Sorted by author name (ascending using Comparable).");
				} else if (sortChoice == 2) {
					BookDescendingTitleComparator titleComparator = new BookDescendingTitleComparator();
					Collections.sort(availableBooks, titleComparator);
					Collections.sort(issuedBooks, titleComparator);
					System.out.println("Sorted by title (descending).");
				} else if (sortChoice == 3) {
					AuthorComparator authorComparator = new AuthorComparator();
					Collections.sort(availableBooks, authorComparator);
					Collections.sort(issuedBooks, authorComparator);
					System.out.println("Sorted by author name (ascending using Comparator).");
				} else {
					System.out.println("Invalid sort choice.");
				}
			}
			case 7 -> {
				System.out.println("Thank you for using AurionPro Book Management System!");
				running = false;
			}
			default -> System.out.println("Invalid choice. Please try again.");
			}
		}
		scanner.close();
	}
}
