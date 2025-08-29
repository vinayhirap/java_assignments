package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/export-users")
public class ExportUsersController extends HttpServlet {

	private UserService userService;

	@Override
	public void init() {
		userService = new UserService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// Check if logged in user is admin
		User admin = (User) session.getAttribute("user");
		if (admin.getUserType() != User.UserType.ADMIN) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			// Get all users (or filtered subset)
			List<User> users = userService.getPendingUsers(); // or another method to fetch all users

			// Set response headers for CSV file download
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=\"users_export.csv\"");

			// Write CSV content
			PrintWriter writer = response.getWriter();
			// Write CSV header
			writer.println("UserID,Username,FirstName,LastName,Email,Phone,Address,UserType,Status");

			// Write data rows
			for (User user : users) {
				writer.printf("%d,%s,%s,%s,%s,%s,%s,%s,%s%n", user.getUserId(), user.getUsername(),
						safeCSV(user.getFirstName()), safeCSV(user.getLastName()), safeCSV(user.getEmail()),
						safeCSV(user.getPhone()), safeCSV(user.getAddress()), user.getUserType().name(),
						user.getStatus().name());
			}
			writer.flush();

		} catch (Exception e) {
			throw new ServletException("Failed to export user data", e);
		}
	}

	// Helper to escape commas and quotes in CSV output
	private String safeCSV(String value) {
		if (value == null)
			return "";
		String escaped = value.replace("\"", "\"\"");
		if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
			return "\"" + escaped + "\"";
		}
		return escaped;
	}
}
