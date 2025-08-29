package com.aurionpro.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class FixedDeposit {
	private int fdId;
	private int customerId;
	private int accountId;
	private String fdNumber;
	private BigDecimal principalAmount;
	private BigDecimal interestRate;
	private int tenureMonths;
	private BigDecimal maturityAmount;
	private Date startDate;
	private Date maturityDate;
	private FDStatus status;
	private Timestamp createdAt;

	public enum FDStatus {
		ACTIVE, MATURED, CLOSED
	}

	// Constructors
	public FixedDeposit() {
	}

	public FixedDeposit(int customerId, int accountId, String fdNumber, BigDecimal principalAmount,
			BigDecimal interestRate, int tenureMonths, Date startDate, Date maturityDate, BigDecimal maturityAmount) {
		this.customerId = customerId;
		this.accountId = accountId;
		this.fdNumber = fdNumber;
		this.principalAmount = principalAmount;
		this.interestRate = interestRate;
		this.tenureMonths = tenureMonths;
		this.startDate = startDate;
		this.maturityDate = maturityDate;
		this.maturityAmount = maturityAmount;
		this.status = FDStatus.ACTIVE;
	}

	// Getters and Setters
	public int getFdId() {
		return fdId;
	}

	public void setFdId(int fdId) {
		this.fdId = fdId;
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

	public String getFdNumber() {
		return fdNumber;
	}

	public void setFdNumber(String fdNumber) {
		this.fdNumber = fdNumber;
	}

	public BigDecimal getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(BigDecimal principalAmount) {
		this.principalAmount = principalAmount;
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

	public BigDecimal getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(BigDecimal maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public FDStatus getStatus() {
		return status;
	}

	public void setStatus(FDStatus status) {
		this.status = status;
	}
}