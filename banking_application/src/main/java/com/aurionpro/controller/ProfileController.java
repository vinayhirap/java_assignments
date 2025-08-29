//package com.aurionpro.controller;
//
//import com.aurionpro.model.*;
//import com.aurionpro.service.UserService;
//import com.aurionpro.dao.CustomerDAO;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.Date;
//
//@WebServlet("/customer/profile")
//public class ProfileController extends HttpServlet {
//	private UserService userService;
//	private CustomerDAO customerDAO;
//
//	@Override
//	public void init() throws ServletException {
//		userService = new UserService();
//		customerDAO = new CustomerDAO();
//	}
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		HttpSession session = request.getSession(false);
//		if (session == null || session.getAttribute("customer") == null) {
//			response.sendRedirect("../login");
//			return;
//		}
//
//		request.getRequestDispatcher("../profile.jsp").forward(request, response);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		HttpSession session = request.getSession(false);
//		if (session == null || session.getAttribute("customer") == null) {
//			response.sendRedirect("../login");
//			return;
//		}
//
//		try {
//			Customer customer = (Customer) session.getAttribute("customer");
//
//			// Get form parameters
//			String firstName = request.getParameter("firstName");
//			String lastName = request.getParameter("lastName");
//			String email = request.getParameter("email");
//			String phone = request.getParameter("phone");
//			String address = request.getParameter("address");
//			String dateOfBirthStr = request.getParameter("dateOfBirth");
//			String panNumber = request.getParameter("panNumber");
//			String aadharNumber = request.getParameter("aadharNumber");
//
//			// Validation
//			if (firstName == null || lastName == null || email == null || phone == null || address == null
//					|| firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty()
//					|| phone.trim().isEmpty() || address.trim().isEmpty()) {
//				request.setAttribute("error", "All required fields must be filled");
//				request.getRequestDispatcher("../profile.jsp").forward(request, response);
//				return;
//			}
//
//			// Update customer object
//			customer.setFirstName(firstName.trim());
//			customer.setLastName(lastName.trim());
//			customer.setEmail(email.trim());
//			customer.setPhone(phone.trim());
//			customer.setAddress(address.trim());
//
//			if (dateOfBirthStr != null && !dateOfBirthStr.trim().isEmpty()) {
//				customer.setDateOfBirth(Date.valueOf(dateOfBirthStr));
//			}
//
//			if (panNumber != null && !panNumber.trim().isEmpty()) {
//				customer.setPanNumber(panNumber.trim().toUpperCase());
//			}
//
//			if (aadharNumber != null && !aadharNumber.trim().isEmpty()) {
//				customer.setAadharNumber(aadharNumber.trim());
//			}
//
//			// Update in database
//			if (customerDAO.updateCustomer(customer)) {
//				// Update session
//				session.setAttribute("customer", customer);
//				request.setAttribute("success", "Profile updated successfully!");
//			} else {
//				request.setAttribute("error", "Failed to update profile. Please try again.");
//			}
//
//		} catch (Exception e) {
//			request.setAttribute("error", "An error occurred: " + e.getMessage());
//			e.printStackTrace();
//		}
//
//		request.getRequestDispatcher("../profile.jsp").forward(request, response);
//	}
//}