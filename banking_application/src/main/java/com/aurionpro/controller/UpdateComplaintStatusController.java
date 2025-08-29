package com.aurionpro.controller;

import com.aurionpro.model.Complaint;
import com.aurionpro.model.User;
import com.aurionpro.service.ComplaintService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/update-complaint-status")
public class UpdateComplaintStatusController extends HttpServlet {

	private ComplaintService complaintService;

	@Override
	public void init() throws ServletException {
		complaintService = new ComplaintService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		User admin = (User) session.getAttribute("user");
		if (admin.getUserType() != User.UserType.ADMIN) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			int complaintId = Integer.parseInt(request.getParameter("complaintId"));
			String statusStr = request.getParameter("status");
			Complaint.ComplaintStatus newStatus = Complaint.ComplaintStatus.valueOf(statusStr.toUpperCase());

			boolean updated = complaintService.updateComplaintStatus(complaintId, newStatus);
			if (updated) {
				request.getSession().setAttribute("success", "Complaint status updated successfully.");
			} else {
				request.getSession().setAttribute("error", "Failed to update complaint status.");
			}
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Error updating complaint status: " + e.getMessage());
		}

		response.sendRedirect(request.getContextPath() + "../manage-complaints");
	}
}
