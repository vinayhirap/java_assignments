package com.aurionpro.model;

public class Book implements Comparable<Book> {
	private static int totalBookCount = 0;
	private final int bookId;
	private final String authorName;
	private final String title;
	private final double price;
	private boolean isIssued;

	public Book(String authorName, String title, double price) {
		this.bookId = ++totalBookCount;
		this.authorName = authorName;
		this.title = title;
		this.price = price;
		this.isIssued = false;
	}

	public int getBookId() {
		return bookId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	public boolean isIssued() {
		return isIssued;
	}

	public void setIssued(boolean issued) {
		isIssued = issued;
	}

	public void displayDetails() {
		System.out.println("Book ID: " + bookId);
		System.out.println("Author: " + authorName);
		System.out.println("Title: " + title);
		System.out.println("Price: ₹" + price);
		System.out.println("Issued: " + (isIssued ? "Yes" : "No"));
		System.out.println("--------------------------");
	}

	@Override
	public int compareTo(Book other) {
		return this.authorName.compareToIgnoreCase(other.authorName);
	}
}
