package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/view-user")
public class ViewUserDetailsController extends HttpServlet {

	private UserService userService;

	@Override
	public void init() throws ServletException {
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

		String userIdParam = request.getParameter("userId");
		if (userIdParam == null || userIdParam.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			return;
		}

		try {
			int userId = Integer.parseInt(userIdParam);
			User user = userService.getUserById(userId);
			if (user == null) {
				request.setAttribute("error", "User not found");
				request.getRequestDispatcher("/admin/manage-users").forward(request, response);
				return;
			}
			request.setAttribute("viewUser", user);
			request.getRequestDispatcher("/admin/view-user-details.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
		} catch (Exception e) {
			request.setAttribute("error", "Error fetching user details: " + e.getMessage());
			request.getRequestDispatcher("/admin/manage-users").forward(request, response);
		}
	}
}
