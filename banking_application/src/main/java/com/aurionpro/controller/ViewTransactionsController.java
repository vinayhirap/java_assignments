package com.aurionpro.controller;

import com.aurionpro.model.Transaction;
import com.aurionpro.service.TransactionService;
import com.aurionpro.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/view-transactions")
public class ViewTransactionsController extends HttpServlet {
	private TransactionService transactionService;

	@Override
	public void init() throws ServletException {
		transactionService = new TransactionService();
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
			List<Transaction> transactions = transactionService.getAllTransactions();
			request.setAttribute("transactions", transactions);
		} catch (Exception e) {
			request.setAttribute("error", "Failed to load transactions: " + e.getMessage());
			e.printStackTrace();
		}

		request.getRequestDispatcher("../view-transactions.jsp").forward(request, response);
	}
}
