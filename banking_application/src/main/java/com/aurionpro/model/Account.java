package com.aurionpro.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
	private int accountId;
	private int customerId;
	private String accountNumber;

	public enum AccountType {
		SAVINGS, CURRENT
	}

	public enum AccountStatus {
		ACTIVE, INACTIVE, CLOSED
	}

	private AccountType accountType;
	private BigDecimal balance;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private AccountStatus status;

	public Account() {
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public String getMaskedAccountNumber() {
		if (accountNumber == null || accountNumber.length() < 4) {
			return "****";
		}
		// Show last 4 digits, mask the rest with '*'
		int length = accountNumber.length();
		String masked = "*".repeat(length - 4) + accountNumber.substring(length - 4);
		return masked;
	}

}
