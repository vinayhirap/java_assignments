package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.model.Customer;
import com.aurionpro.service.UserService;
import com.aurionpro.dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-users")
public class AdminUserManagementController extends HttpServlet {
	private UserService userService;
	private CustomerDAO customerDAO;

	@Override
	public void init() throws ServletException {
		userService = new UserService();
		customerDAO = new CustomerDAO();
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
			String action = request.getParameter("action");
			String userIdStr = request.getParameter("userId");

			if (action != null && userIdStr != null) {
				int userId = Integer.parseInt(userIdStr);
				boolean success = false;

				switch (action) {
				case "approve":
					success = userService.approveUser(userId);
					break;
				case "reject":
					success = userService.rejectUser(userId);
					break;
				case "deactivate":
					success = userService.deactivateUser(userId);
					break;
				case "activate":
					success = userService.approveUser(userId);
					break;
				default:
					// unknown action
					break;
				}

				// Store success or error message in session to show after redirect
				if (success) {
					session.setAttribute("success", "Action " + action + " performed successfully.");
				} else {
					session.setAttribute("error", "Failed to perform action: " + action);
				}

				// Redirect to self to avoid form re-submission and refresh data
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
				return;
			}

			// Load fresh data after any potential action
			List<Customer> customers = customerDAO.getAllCustomers();
			List<User> users = userService.getAllUsers();

			request.setAttribute("customers", customers);
			request.setAttribute("users", users);

			// Forward to JSP
			request.getRequestDispatcher("/admin_manage_users.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.getRequestDispatcher("/admin_manage_users.jsp").forward(request, response);
		}
	}
}
