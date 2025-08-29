package com.aurionpro.model;

import java.sql.Timestamp;

public class Beneficiary {
	private int beneficiaryId;
	private int customerId;
	private String beneficiaryAccountNumber;
	private String beneficiaryName;
	private String nickname;
	private String bankName;
	private String ifscCode;
	private BeneficiaryStatus status;
	private Timestamp createdAt;

	public enum BeneficiaryStatus {
		ACTIVE, INACTIVE
	}

	// Constructors
	public Beneficiary() {
	}

	public Beneficiary(int customerId, String beneficiaryAccountNumber, String beneficiaryName, String nickname) {
		this.customerId = customerId;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		this.beneficiaryName = beneficiaryName;
		this.nickname = nickname;
		this.status = BeneficiaryStatus.ACTIVE;
	}

	// Getters and Setters
	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public BeneficiaryStatus getStatus() {
		return status;
	}

	public void setStatus(BeneficiaryStatus status) {
		this.status = status;
	}

	public String getMaskedAccountNumber() {
		if (beneficiaryAccountNumber != null && beneficiaryAccountNumber.length() > 4) {
			return "****" + beneficiaryAccountNumber.substring(beneficiaryAccountNumber.length() - 4);
		}
		return beneficiaryAccountNumber;
	}
}