package com.aurionpro.controller;

import com.aurionpro.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer/beneficiaries")
public class BeneficiariesController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Load beneficiaries data here if implemented
		request.getRequestDispatcher("../customer_beneficiaries.jsp").forward(request, response);
	}
}