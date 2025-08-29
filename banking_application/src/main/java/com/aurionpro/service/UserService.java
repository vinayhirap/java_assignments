package com.aurionpro.service;

import com.aurionpro.dao.UserDAO;
import com.aurionpro.dao.CustomerDAO;
import com.aurionpro.model.User;
import com.aurionpro.model.Customer;

import java.util.List;
import java.util.ArrayList;

/**
 * Service layer to manage User and Customer related business logic. Acts as the
 * bridge between controllers and database access layer (DAO).
 */
public class UserService {

	private final UserDAO userDAO;
	private final CustomerDAO customerDAO;

	public UserService() {
		this.userDAO = new UserDAO();
		this.customerDAO = new CustomerDAO();
	}

	/**
	 * Registers a new user and corresponding customer record.
	 * 
	 * @param user     User login details
	 * @param customer Customer personal info
	 * @return true if registration is successful, false otherwise
	 */
	public boolean registerUser(User user, Customer customer) {
		try {
			if (userDAO.createUser(user)) {
				customer.setUserId(user.getUserId());
				return customerDAO.createCustomer(customer);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace(); // Prefer logging in production
			return false;
		}
	}

	/**
	 * Authenticate by username and password only if user is approved.
	 */
	public User authenticateUser(String username, String password) {
		User user = userDAO.getUserByCredentials(username, password);
		if (user != null && user.getStatus() == User.UserStatus.APPROVED) {
			return user;
		}
		return null;
	}

	public boolean approveUser(int userId) {
		return userDAO.updateUserStatus(userId, User.UserStatus.APPROVED);
	}

	public boolean rejectUser(int userId) {
		return userDAO.updateUserStatus(userId, User.UserStatus.REJECTED);
	}

	public boolean deactivateUser(int userId) {
		return userDAO.updateUserStatus(userId, User.UserStatus.DEACTIVATED);
	}

	public List<User> getPendingUsers() {
		return userDAO.getAllPendingUsers();
	}

	public User getUserById(int userId) {
		try {
			return userDAO.getUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> getAllUsers() {
		try {
			return userDAO.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public boolean updateUser(User user) {
		try {
			return userDAO.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isUsernameExists(String username) {
		// This relies on a DAO method that accepts empty password to check username
		// existence.
		// Consider adding a dedicated DAO method for username existence check.
		return userDAO.getUserByCredentials(username, "") != null;
	}
}
