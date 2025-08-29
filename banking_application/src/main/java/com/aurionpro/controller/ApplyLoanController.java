package com.aurionpro.controller;

import com.aurionpro.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer/apply-loan")
public class ApplyLoanController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Show loan application form
		request.getRequestDispatcher("../apply-loan.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Process loan application form here (extend logic as needed)
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		// For now, just simulate submission success
		session.setAttribute("success", "Loan application submitted successfully!");

		response.sendRedirect(request.getContextPath() + "../apply-loan");
	}
}
