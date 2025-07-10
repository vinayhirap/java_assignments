package com.aurionpro.bankapp;

import com.aurionpro.exception.MinBalanceViolationException;
import com.aurionpro.exception.NegativeAmountException;

public abstract class Account {
	int accno;
	String name;
	double balance;

	public Account(int accno, String name, double balance) {
		if (balance < 500) {
			throw new MinBalanceViolationException(balance);
		}
		this.accno = accno;
		this.name = name;
		this.balance = balance;
	}

	public void display() {
		System.out.println("\n Account Details:");
		System.out.println("Account No: " + accno);
		System.out.println("Name: " + name);
		System.out.println("Balance: ₹" + balance);
	}

	public void validateAmount(double amount) {
		if (amount <= 0) {
			throw new NegativeAmountException(amount);
		}
	}

	public abstract void credit(double amount);

	public abstract void debit(double amount);
}
