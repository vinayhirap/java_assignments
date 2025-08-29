package com.aurionpro.model;

import java.sql.Timestamp;

public class Complaint {
	private int complaintId;
	private int customerId;
	private ComplaintType complaintType;
	private String subject;
	private String description;
	private ComplaintStatus status;
	private ComplaintPriority priority;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Timestamp resolvedAt;
	private String adminResponse;

	public enum ComplaintType {
		ACCOUNT, TRANSACTION, CARD, LOAN, FD, OTHER
	}

	public enum ComplaintStatus {
		OPEN, IN_PROGRESS, RESOLVED, CLOSED
	}

	public enum ComplaintPriority {
		LOW, MEDIUM, HIGH, URGENT
	}

	// Constructors
	public Complaint() {
	}

	public Complaint(int customerId, ComplaintType complaintType, String subject, String description) {
		this.customerId = customerId;
		this.complaintType = complaintType;
		this.subject = subject;
		this.description = description;
		this.status = ComplaintStatus.OPEN;
		this.priority = ComplaintPriority.MEDIUM;
	}

	// Getters and Setters

	public int getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public ComplaintType getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(ComplaintType complaintType) {
		this.complaintType = complaintType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ComplaintStatus getStatus() {
		return status;
	}

	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}

	public ComplaintPriority getPriority() {
		return priority;
	}

	public void setPriority(ComplaintPriority priority) {
		this.priority = priority;
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

	public Timestamp getResolvedAt() {
		return resolvedAt;
	}

	public void setResolvedAt(Timestamp resolvedAt) {
		this.resolvedAt = resolvedAt;
	}

	public String getAdminResponse() {
		return adminResponse;
	}

	public void setAdminResponse(String adminResponse) {
		this.adminResponse = adminResponse;
	}
}
