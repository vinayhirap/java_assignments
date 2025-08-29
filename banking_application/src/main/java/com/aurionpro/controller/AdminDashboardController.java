package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.model.Customer;
import com.aurionpro.model.Account;
import com.aurionpro.model.Transaction;
import com.aurionpro.service.UserService;
import com.aurionpro.service.AccountService;
import com.aurionpro.service.TransactionService;
import com.aurionpro.dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardController extends HttpServlet {
	private UserService userService;
	private AccountService accountService;
	private TransactionService transactionService;
	private CustomerDAO customerDAO;

	@Override
	public void init() throws ServletException {
		userService = new UserService();
		accountService = new AccountService();
		transactionService = new TransactionService();
		customerDAO = new CustomerDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("../login");
			return;
		}

		User user = (User) session.getAttribute("user");
		if (user.getUserType() != User.UserType.ADMIN) {
			response.sendRedirect("../login");
			return;
		}

		// Get statistics for dashboard
		List<Customer> allCustomers = customerDAO.getAllCustomers();
		List<Account> allAccounts = accountService.getAllAccounts();
		List<Transaction> allTransactions = transactionService.getAllTransactions();
		List<User> pendingUsers = userService.getPendingUsers();

		request.setAttribute("totalCustomers", allCustomers.size());
		request.setAttribute("totalAccounts", allAccounts.size());
		request.setAttribute("totalTransactions", allTransactions.size());
		request.setAttribute("pendingApprovals", pendingUsers.size());

		request.setAttribute("recentTransactions",
				allTransactions.size() > 10 ? allTransactions.subList(0, 10) : allTransactions);

		request.getRequestDispatcher("../admin_dashboard.jsp").forward(request, response);
	}
}
