package com.aurionpro.controller;

import com.aurionpro.model.Complaint;
import com.aurionpro.model.User;
import com.aurionpro.service.ComplaintService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-complaints")
public class ManageComplaintsController extends HttpServlet {

	private ComplaintService complaintService;

	@Override
	public void init() throws ServletException {
		complaintService = new ComplaintService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
			List<Complaint> complaints = complaintService.getAllComplaints();
			request.setAttribute("complaints", complaints);
		} catch (Exception e) {
			request.setAttribute("error", "Error loading complaints: " + e.getMessage());
			e.printStackTrace();
		}

		request.getRequestDispatcher("../manage-complaints.jsp").forward(request, response);
	}
}
