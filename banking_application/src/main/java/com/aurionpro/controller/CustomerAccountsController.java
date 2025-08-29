package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.model.Customer;
import com.aurionpro.model.Account;
import com.aurionpro.service.UserService;
import com.aurionpro.dao.CustomerDAO;
import com.aurionpro.dao.AccountDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/accounts")
public class CustomerAccountsController extends HttpServlet {
	private UserService userService;
	private CustomerDAO customerDAO;
	private AccountDAO accountDAO;

	@Override
	public void init() throws ServletException {
		userService = new UserService();
		customerDAO = new CustomerDAO();
		accountDAO = new AccountDAO();
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
			List<Account> accounts = accountDAO.getAccountsByCustomerId(customer.getCustomerId());

			request.setAttribute("customer", customer);
			request.setAttribute("accounts", accounts);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load account data");
		}

		request.getRequestDispatcher("../customer_accounts.jsp").forward(request, response);
	}
}
