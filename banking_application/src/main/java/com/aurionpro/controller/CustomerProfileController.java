package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.model.Customer;
import com.aurionpro.service.UserService;
import com.aurionpro.dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer/profile")
public class CustomerProfileController extends HttpServlet {
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

		User user = (User) session.getAttribute("user");
		if (user.getUserType() != User.UserType.CUSTOMER) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			Customer customer = customerDAO.getCustomerByUserId(user.getUserId());
			request.setAttribute("customer", customer);
			request.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load profile data");
		}

		// Forward to JSP directly under webapp folder
		request.getRequestDispatcher("/customer_profile.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		try {
			Customer customer = customerDAO.getCustomerByUserId(user.getUserId());
			customer.setFirstName(request.getParameter("firstName"));
			customer.setLastName(request.getParameter("lastName"));
			customer.setEmail(request.getParameter("email"));
			customer.setPhone(request.getParameter("phone"));
			customer.setAddress(request.getParameter("address"));

			if (customerDAO.updateCustomer(customer)) {
				session.setAttribute("success", "Profile updated successfully!");
			} else {
				session.setAttribute("error", "Failed to update profile");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Error updating profile");
		}

		// Redirect using absolute context path; no '..' usage
		response.sendRedirect(request.getContextPath() + "/customer/profile");
	}
}
