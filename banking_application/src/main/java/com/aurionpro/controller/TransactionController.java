package com.aurionpro.controller;

import com.aurionpro.model.*;
import com.aurionpro.service.TransactionService;
import com.aurionpro.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/customer/transactions")
public class TransactionController extends HttpServlet {
	private TransactionService transactionService;
	private AccountService accountService;

	@Override
	public void init() throws ServletException {
		transactionService = new TransactionService();
		accountService = new AccountService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("customer") == null) {
			response.sendRedirect("../login");
			return;
		}

		Customer customer = (Customer) session.getAttribute("customer");

		try {
			// Get customer accounts
			List<Account> accounts = accountService.getCustomerAccounts(customer.getCustomerId());

			// Get all transactions for customer accounts
			List<Transaction> allTransactions = new ArrayList<>();
			for (Account account : accounts) {
				List<Transaction> accountTransactions = transactionService
						.getAccountTransactions(account.getAccountId());
				allTransactions.addAll(accountTransactions);
			}

			// Apply filters if provided
			String typeFilter = request.getParameter("type");
			String dateFromFilter = request.getParameter("dateFrom");
			String dateToFilter = request.getParameter("dateTo");

			List<Transaction> filteredTransactions = allTransactions;

			if (typeFilter != null && !typeFilter.trim().isEmpty()) {
				filteredTransactions = filteredTransactions.stream()
						.filter(t -> t.getTransactionType().name().equals(typeFilter))
						.collect(java.util.stream.Collectors.toList());
			}

			// Sort by date (newest first)
			filteredTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

			request.setAttribute("transactions", filteredTransactions);
			request.setAttribute("accounts", accounts);

		} catch (Exception e) {
			request.setAttribute("error", "Error loading transactions: " + e.getMessage());
			e.printStackTrace();
		}

		request.getRequestDispatcher("../transactions.jsp").forward(request, response);
	}
}