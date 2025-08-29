package com.aurionpro.dao;

import com.aurionpro.model.User;
import com.aurionpro.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	public boolean createUser(User user) {
		if (isUsernameExists(user.getUsername())) {
			System.err.println("Username already exists: " + user.getUsername());
			return false;
		}

		String sql = "INSERT INTO users (username, password, user_type, status) VALUES (?, ?, ?, ?)";

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getUserType().name());
			stmt.setString(4, user.getStatus().name());

			int result = stmt.executeUpdate();

			if (result > 0) {
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						user.setUserId(rs.getInt(1));
					}
				}
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isUsernameExists(String username) {
		String sql = "SELECT 1 FROM users WHERE username = ?";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User getUserByCredentials(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, username);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt("user_id"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
					user.setStatus(User.UserStatus.valueOf(rs.getString("status")));
					return user;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateUserStatus(int userId, User.UserStatus status) {
		String sql = "UPDATE users SET status = ? WHERE user_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, status.name());
			stmt.setInt(2, userId);

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<User> getAllPendingUsers() {
		String sql = "SELECT * FROM users WHERE status = 'PENDING' AND user_type = 'CUSTOMER'";
		List<User> users = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
				user.setStatus(User.UserStatus.valueOf(rs.getString("status")));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<User> getAllUsers() throws Exception {
		String sql = "SELECT user_id, username, first_name, last_name, email, phone, address, user_type, status, created_at FROM users WHERE user_type = 'CUSTOMER'";
		List<User> users = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
				user.setStatus(User.UserStatus.valueOf(rs.getString("status")));
				users.add(user);
			}
		}

		return users;
	}

	public User getUserById(int userId) throws Exception {
		String sql = "SELECT user_id, username, first_name, last_name, email, phone, address, user_type, status FROM users WHERE user_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt("user_id"));
					user.setUsername(rs.getString("username"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setEmail(rs.getString("email"));
					user.setPhone(rs.getString("phone"));
					user.setAddress(rs.getString("address"));
					user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
					user.setStatus(User.UserStatus.valueOf(rs.getString("status")));
					return user;
				} else {
					return null;
				}
			}
		}
	}

	public boolean updateUser(User user) {
		String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ?, email = ?, phone = ?, address = ? WHERE user_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getAddress());
			ps.setInt(7, user.getUserId());

			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
