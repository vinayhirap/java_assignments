package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/approve-users")
public class ApproveUsersController extends HttpServlet {

	private UserService userService;

	@Override
	public void init() throws ServletException {
		super.init();
		userService = new UserService();
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

		// Fetch users with status PENDING
		List<User> pendingUsers = userService.getPendingUsers();

		// Set attribute to pass data to JSP
		request.setAttribute("pendingUsers", pendingUsers);

		// Forward to approve-users.jsp
		request.getRequestDispatcher("../approve-users.jsp").forward(request, response);
	}
}
