package com.aurionpro.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer/complaints")
public class ComplaintsController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward to JSP to display complaints
		request.getRequestDispatcher("/customer_complaints.jsp").forward(request, response);
	}
}
