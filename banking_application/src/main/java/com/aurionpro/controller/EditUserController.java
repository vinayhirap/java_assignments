package com.aurionpro.controller;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/edit-user")
public class EditUserController extends HttpServlet {

	private UserService userService;

	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}

	// Show user details on form
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
			request.setAttribute("editUser", user);
			request.getRequestDispatcher("/admin/edit-user.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
		} catch (Exception e) {
			request.setAttribute("error", "Error fetching user details: " + e.getMessage());
			request.getRequestDispatcher("/admin/manage-users").forward(request, response);
		}
	}

	// Handle form submission to update user
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
			int userId = Integer.parseInt(request.getParameter("userId"));
			String username = request.getParameter("username");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");

			User user = userService.getUserById(userId);
			if (user == null) {
				request.setAttribute("error", "User not found");
				request.getRequestDispatcher("/admin/manage-users").forward(request, response);
				return;
			}

			user.setUsername(username);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPhone(phone);
			user.setAddress(address);

			boolean updated = userService.updateUser(user);

			if (updated) {
				request.getSession().setAttribute("success", "User details updated successfully");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			} else {
				request.setAttribute("editUser", user);
				request.setAttribute("error", "Failed to update user details");
				request.getRequestDispatcher("/admin/edit-user.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("error", "Error updating user details: " + e.getMessage());
			request.getRequestDispatcher("/admin/edit-user.jsp").forward(request, response);
		}
	}
}
