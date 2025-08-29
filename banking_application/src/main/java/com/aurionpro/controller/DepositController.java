package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/customer/deposit")
public class DepositController extends HttpServlet {
	private AccountService accountService;

	@Override
	public void init() {
		accountService = new AccountService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Show deposit form
		request.getRequestDispatcher("/customer/deposit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Process deposit form
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		if (user == null || user.getUserType() != User.UserType.CUSTOMER) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		int accountId = Integer.parseInt(request.getParameter("accountId"));
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		String description = request.getParameter("description");

		boolean success = accountService.deposit(accountId, amount, description);

		if (success) {
			session.setAttribute("success", "Deposit successful!");
		} else {
			session.setAttribute("error", "Deposit failed. Check details and try again.");
		}

		response.sendRedirect(request.getContextPath() + "../customer_deposit");
	}
}
