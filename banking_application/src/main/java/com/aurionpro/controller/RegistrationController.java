package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.model.Customer;
import com.aurionpro.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationController extends HttpServlet {

	private UserService userService;

	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward to registration JSP page
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get form parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		// Validation
		if (username == null || password == null || firstName == null || lastName == null || email == null
				|| phone == null || address == null || username.trim().isEmpty() || password.trim().isEmpty()
				|| firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty()
				|| phone.trim().isEmpty() || address.trim().isEmpty()) {

			request.setAttribute("error", "All fields are required");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		// Check if username already exists
		if (userService.isUsernameExists(username)) {
			request.setAttribute("error", "Username already exists");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		// Create User and Customer objects
		User user = new User(username, password, User.UserType.CUSTOMER);
		Customer customer = new Customer(0, firstName, lastName, email, phone, address);

		// Register user
		if (userService.registerUser(user, customer)) {
			request.setAttribute("success", "Registration successful! Please wait for admin approval.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Registration failed: Duplicate username or invalid data. Please try again.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
}
