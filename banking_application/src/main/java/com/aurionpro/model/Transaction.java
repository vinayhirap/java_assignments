package com.aurionpro.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
	private int transactionId;
	private Integer fromAccountId;
	private Integer toAccountId;
	private TransactionType transactionType;
	private BigDecimal amount;
	private BigDecimal balanceAfter;
	private String description;
	private String referenceNumber;
	private TransactionStatus status;
	private Timestamp transactionDate;

	public enum TransactionType {
		DEPOSIT, WITHDRAWAL, TRANSFER, FD_CREATION, FD_MATURITY, LOAN_DISBURSEMENT, LOAN_REPAYMENT
	}

	public enum TransactionStatus {
		PENDING, COMPLETED, FAILED, CANCELLED
	}

	// Constructors
	public Transaction() {
	}

	public Transaction(Integer fromAccountId, Integer toAccountId, TransactionType transactionType, BigDecimal amount,
			String description) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
		this.status = TransactionStatus.COMPLETED;
	}

	// Getters and Setters
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Integer fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public Integer getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Integer toAccountId) {
		this.toAccountId = toAccountId;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}
}