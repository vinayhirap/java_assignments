package com.aurionpro.controller;

import com.aurionpro.model.Account;
import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/customer/create-account")
public class CreateAccountController extends HttpServlet {

	private AccountService accountService;

	@Override
	public void init() {
		accountService = new AccountService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward to JSP page for account creation form
		request.getRequestDispatcher("/customer_create_account.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/customer/create-account");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) (session != null ? session.getAttribute("user") : null);

		if (user == null || user.getUserType() != User.UserType.CUSTOMER) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String accountTypeStr = request.getParameter("accountType");
		String depositStr = request.getParameter("initialDeposit");

		// Validate input
		if (accountTypeStr == null || (!accountTypeStr.equals("SAVINGS") && !accountTypeStr.equals("CURRENT"))) {
			session.setAttribute("error", "Please select a valid Account Type.");
			response.sendRedirect(request.getContextPath() + "/customer/create-account");
			return;
		}

		BigDecimal initialDeposit;
		try {
			initialDeposit = new BigDecimal(depositStr);
			if (initialDeposit.compareTo(BigDecimal.ZERO) < 0) {
				session.setAttribute("error", "Initial Deposit cannot be negative.");
				response.sendRedirect(request.getContextPath() + "/customer/create-account");
				return;
			}
		} catch (NumberFormatException e) {
			session.setAttribute("error", "Invalid Initial Deposit amount.");
			response.sendRedirect(request.getContextPath() + "/customer/create-account");
			return;
		}

		try {
			Account account = new Account();
			account.setCustomerId(user.getUserId());
			account.setAccountType(Account.AccountType.valueOf(accountTypeStr));
			account.setBalance(initialDeposit);
			account.setStatus(Account.AccountStatus.ACTIVE);

			boolean created = accountService.createAccount(account);

			if (created) {
				session.setAttribute("success", "Your bank account has been created successfully.");
			} else {
				session.setAttribute("error", "Failed to create bank account. Try again.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "An internal error occurred. Please try later.");
		}

		response.sendRedirect(request.getContextPath() + "/customer/create-account");
	}
}
