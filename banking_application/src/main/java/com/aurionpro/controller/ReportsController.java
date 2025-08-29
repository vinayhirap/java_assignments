package com.aurionpro.controller;

import com.aurionpro.model.Report;
import com.aurionpro.model.User;
import com.aurionpro.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/reports")
public class ReportsController extends HttpServlet {

	private ReportService reportService;

	@Override
	public void init() throws ServletException {
		reportService = new ReportService();
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
			List<Report> reports = reportService.getAllReports();
			request.setAttribute("reports", reports);
		} catch (Exception e) {
			request.setAttribute("error", "Failed to load reports: " + e.getMessage());
			e.printStackTrace();
		}

		request.getRequestDispatcher("../reports.jsp").forward(request, response);
	}
}
