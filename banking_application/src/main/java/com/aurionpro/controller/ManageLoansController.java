package com.aurionpro.controller;

import com.aurionpro.model.Loan;
import com.aurionpro.service.LoanService;
import com.aurionpro.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-loans")
public class ManageLoansController extends HttpServlet {

	private LoanService loanService;

	@Override
	public void init() throws ServletException {
		loanService = new LoanService();
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
			List<Loan> loans = loanService.getAllLoans();
			request.setAttribute("loans", loans);
		} catch (Exception e) {
			request.setAttribute("error", "Failed to load loans: " + e.getMessage());
			e.printStackTrace();
		}

		request.getRequestDispatcher("../manage-loans.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Implement logic to add/update/delete loans based on request parameters
		// (action)
		// Parse parameters, call LoanService methods and then redirect/forward with
		// success/error messages
	}

}
