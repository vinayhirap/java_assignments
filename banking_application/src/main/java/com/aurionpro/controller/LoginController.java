package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.model.Customer;
import com.aurionpro.service.UserService;
import com.aurionpro.dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
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
		// Updated JSP path for Dynamic Web Project
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
			request.setAttribute("error", "Please enter both username and password");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		User user = userService.authenticateUser(username, password);

		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("userId", user.getUserId());
			session.setAttribute("userType", user.getUserType().name());

			if (user.getUserType() == User.UserType.ADMIN) {
				response.sendRedirect("admin/dashboard");
			} else {
				// Get customer details for customer users
				Customer customer = null;
				try {
					customer = customerDAO.getCustomerByUserId(user.getUserId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (customer != null) {
					session.setAttribute("customer", customer);
					session.setAttribute("customerId", customer.getCustomerId());
				}
				response.sendRedirect("customer/dashboard");
			}
		} else {
			request.setAttribute("error", "Invalid username or password, or account not approved");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}