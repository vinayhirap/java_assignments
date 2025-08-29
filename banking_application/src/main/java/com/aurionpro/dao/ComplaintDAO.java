package com.aurionpro.dao;

import com.aurionpro.model.Complaint;
import com.aurionpro.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

	public List<Complaint> getAllComplaints() throws Exception {
		List<Complaint> complaints = new ArrayList<>();
		String sql = "SELECT complaint_id, customer_id, subject, description, status, created_at FROM complaints";

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Complaint complaint = new Complaint();
				complaint.setComplaintId(rs.getInt("complaint_id"));
				complaint.setCustomerId(rs.getInt("customer_id"));
				complaint.setSubject(rs.getString("subject"));
				complaint.setDescription(rs.getString("description"));
				complaint.setStatus(Complaint.ComplaintStatus.valueOf(rs.getString("status").toUpperCase()));
				complaint.setCreatedAt(rs.getTimestamp("created_at"));
				complaints.add(complaint);
			}
		}
		return complaints;
	}

	/**
	 * Updates the status of a complaint.
	 *
	 * @param complaintId - the complaint ID
	 * @param newStatus   - new status value
	 * @return true if updated successfully, false otherwise
	 * @throws Exception on database errors
	 */
	public boolean updateComplaintStatus(int complaintId, Complaint.ComplaintStatus newStatus) throws Exception {
		String sql = "UPDATE complaints SET status = ? WHERE complaint_id = ?";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, newStatus.name());
			ps.setInt(2, complaintId);

			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		}
	}
}
