package com.aurionpro.service;

import com.aurionpro.dao.ComplaintDAO;
import com.aurionpro.model.Complaint;

import java.util.List;

public class ComplaintService {

	private ComplaintDAO complaintDAO;

	public ComplaintService() {
		complaintDAO = new ComplaintDAO();
	}

	public List<Complaint> getAllComplaints() throws Exception {
		return complaintDAO.getAllComplaints();
	}

	public boolean updateComplaintStatus(int complaintId, Complaint.ComplaintStatus newStatus) throws Exception {
		return complaintDAO.updateComplaintStatus(complaintId, newStatus);
	}
}
