package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.model.Customer;
import com.aurionpro.service.UserService;
import com.aurionpro.dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer/dashboard")
public class CustomerDashboardController extends HttpServlet {
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

		User customer = (User) session.getAttribute("user");
		if (customer.getUserType() != User.UserType.CUSTOMER) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			// Get customer details
			Customer customerDetails = customerDAO.getCustomerByUserId(customer.getUserId());
			request.setAttribute("customer", customerDetails);

			// You can add more dashboard data here like:
			// - Recent transactions
			// - Account balance
			// - Notifications etc.

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load dashboard data");
		}

		request.getRequestDispatcher("../customer_dashboard.jsp").forward(request, response);
	}
}
