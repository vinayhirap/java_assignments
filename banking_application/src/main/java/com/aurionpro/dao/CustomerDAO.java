package com.aurionpro.dao;

import com.aurionpro.model.Customer;
import com.aurionpro.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

	public boolean createCustomer(Customer customer) {
		String sql = "INSERT INTO customers (user_id, first_name, last_name, email, phone, address, date_of_birth, pan_number, aadhar_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, customer.getUserId());
			stmt.setString(2, customer.getFirstName());
			stmt.setString(3, customer.getLastName());
			stmt.setString(4, customer.getEmail());
			stmt.setString(5, customer.getPhone());
			stmt.setString(6, customer.getAddress());
			stmt.setDate(7, customer.getDateOfBirth());
			stmt.setString(8, customer.getPanNumber());
			stmt.setString(9, customer.getAadharNumber());

			int result = stmt.executeUpdate();

			if (result > 0) {
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						customer.setCustomerId(rs.getInt(1));
					}
				}
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Customer getCustomerByUserId(int userId) throws Exception {
		String sql = "SELECT * FROM customers WHERE user_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Customer customer = new Customer();
					customer.setCustomerId(rs.getInt("customer_id"));
					customer.setUserId(rs.getInt("user_id"));
					customer.setFirstName(rs.getString("first_name"));
					customer.setLastName(rs.getString("last_name"));
					customer.setEmail(rs.getString("email"));
					customer.setPhone(rs.getString("phone"));
					customer.setAddress(rs.getString("address"));
					customer.setDateOfBirth(rs.getDate("date_of_birth"));
					customer.setPanNumber(rs.getString("pan_number"));
					customer.setAadharNumber(rs.getString("aadhar_number"));
					return customer;
				}
			}
		}
		return null;
	}

	public boolean updateCustomer(Customer customer) {
		String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, date_of_birth = ?, pan_number = ?, aadhar_number = ? WHERE customer_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setString(3, customer.getEmail());
			stmt.setString(4, customer.getPhone());
			stmt.setString(5, customer.getAddress());
			stmt.setDate(6, customer.getDateOfBirth());
			stmt.setString(7, customer.getPanNumber());
			stmt.setString(8, customer.getAadharNumber());
			stmt.setInt(9, customer.getCustomerId());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Customer> getAllCustomers() {
		String sql = "SELECT c.*, u.username, u.status FROM customers c JOIN users u ON c.user_id = u.user_id";
		List<Customer> customers = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setUserId(rs.getInt("user_id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPhone(rs.getString("phone"));
				customer.setAddress(rs.getString("address"));
				customer.setDateOfBirth(rs.getDate("date_of_birth"));
				customer.setPanNumber(rs.getString("pan_number"));
				customer.setAadharNumber(rs.getString("aadhar_number"));
				customers.add(customer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public Customer getCustomerById(int customerId) {
		String sql = "SELECT * FROM customers WHERE customer_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, customerId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Customer customer = new Customer();
					customer.setCustomerId(rs.getInt("customer_id"));
					customer.setUserId(rs.getInt("user_id"));
					customer.setFirstName(rs.getString("first_name"));
					customer.setLastName(rs.getString("last_name"));
					customer.setEmail(rs.getString("email"));
					customer.setPhone(rs.getString("phone"));
					customer.setAddress(rs.getString("address"));
					customer.setDateOfBirth(rs.getDate("date_of_birth"));
					customer.setPanNumber(rs.getString("pan_number"));
					customer.setAadharNumber(rs.getString("aadhar_number"));
					return customer;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteCustomer(int customerId) {
		String sql = "DELETE FROM customers WHERE customer_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, customerId);
			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Customer> getCustomersByStatus(String status) {
		String sql = "SELECT c.*, u.username, u.status FROM customers c JOIN users u ON c.user_id = u.user_id WHERE u.status = ?";
		List<Customer> customers = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, status);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Customer customer = new Customer();
					customer.setCustomerId(rs.getInt("customer_id"));
					customer.setUserId(rs.getInt("user_id"));
					customer.setFirstName(rs.getString("first_name"));
					customer.setLastName(rs.getString("last_name"));
					customer.setEmail(rs.getString("email"));
					customer.setPhone(rs.getString("phone"));
					customer.setAddress(rs.getString("address"));
					customer.setDateOfBirth(rs.getDate("date_of_birth"));
					customer.setPanNumber(rs.getString("pan_number"));
					customer.setAadharNumber(rs.getString("aadhar_number"));
					customers.add(customer);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
}
