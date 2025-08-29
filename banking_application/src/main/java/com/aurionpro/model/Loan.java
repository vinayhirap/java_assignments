package com.aurionpro.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Loan {
	private int loanId;
	private int customerId;
	private int accountId;
	private LoanType loanType;
	private BigDecimal loanAmount;
	private BigDecimal interestRate;
	private int tenureMonths;
	private BigDecimal emiAmount;
	private BigDecimal outstandingAmount;
	private LoanStatus status;
	private Date disbursementDate;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Enums for Loan Type and Status
	public enum LoanType {
		PERSONAL, HOME, CAR, EDUCATION, BUSINESS
	}

	public enum LoanStatus {
		PENDING, APPROVED, REJECTED, ACTIVE, CLOSED
	}

	// Constructors
	public Loan() {
	}

	public Loan(int customerId, int accountId, LoanType loanType, BigDecimal loanAmount, BigDecimal interestRate,
			int tenureMonths) {
		this.customerId = customerId;
		this.accountId = accountId;
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.interestRate = interestRate;
		this.tenureMonths = tenureMonths;
		this.outstandingAmount = loanAmount;
		this.status = LoanStatus.PENDING;
	}

	// Getters and Setters

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public int getTenureMonths() {
		return tenureMonths;
	}

	public void setTenureMonths(int tenureMonths) {
		this.tenureMonths = tenureMonths;
	}

	public BigDecimal getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(BigDecimal emiAmount) {
		this.emiAmount = emiAmount;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public Date getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
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
}
