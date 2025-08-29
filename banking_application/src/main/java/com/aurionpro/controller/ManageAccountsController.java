package com.aurionpro.controller;

import com.aurionpro.model.Account;
import com.aurionpro.service.AccountService;
import com.aurionpro.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-accounts")
public class ManageAccountsController extends HttpServlet {

	private AccountService accountService;

	@Override
	public void init() throws ServletException {
		accountService = new AccountService();
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
			List<Account> accounts = accountService.getAllAccounts();
			request.setAttribute("accounts", accounts);
		} catch (Exception e) {
			request.setAttribute("error", "Failed to load accounts: " + e.getMessage());
			e.printStackTrace();
		}

		request.getRequestDispatcher("../manage-accounts.jsp").forward(request, response);
	}
}
