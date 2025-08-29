package com.aurionpro.dao;

import com.aurionpro.model.Account;
import com.aurionpro.model.Account.AccountType;
import com.aurionpro.model.Account.AccountStatus;
import com.aurionpro.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Random;

public class AccountDAO {

	private static final Random random = new Random();

	public List<Account> getAllAccounts() {
		String sql = "SELECT * FROM accounts";
		List<Account> accounts = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				accounts.add(mapRowToAccount(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error fetching all accounts:");
			e.printStackTrace();
		}
		return accounts;
	}

	/**
	 * Generate a random account number string.
	 */
	private String generateAccountNumber() {
		long timestamp = System.currentTimeMillis();
		int randomDigits = random.nextInt(9000) + 1000; // 4-digit random number
		return "AC" + timestamp + randomDigits;
	}

	/**
	 * Generate a unique account number by checking database existence.
	 */
	public String generateUniqueAccountNumber() throws SQLException {
		String accountNumber;
		int tries = 0;

		do {
			accountNumber = generateAccountNumber();
			tries++;
			if (tries > 5) {
				throw new RuntimeException("Failed to generate unique account number after 5 attempts");
			}
		} while (getAccountByNumber(accountNumber) != null);

		return accountNumber;
	}

	public List<Account> getAccountsByCustomerId(int customerId) {
		String sql = "SELECT * FROM accounts WHERE customer_id = ? AND status = 'ACTIVE'";
		List<Account> accounts = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, customerId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					accounts.add(mapRowToAccount(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching accounts for customerId " + customerId);
			e.printStackTrace();
		}
		return accounts;
	}

	public Account getAccountById(int accountId) {
		String sql = "SELECT * FROM accounts WHERE account_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, accountId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToAccount(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching account by id: " + accountId);
			e.printStackTrace();
		}
		return null;
	}

	public Account getAccountByNumber(String accountNumber) {
		String sql = "SELECT * FROM accounts WHERE account_number = ?";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, accountNumber);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToAccount(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching account by number: " + accountNumber);
			e.printStackTrace();
		}
		return null;
	}

	public boolean createAccount(Account account) {
		String sql = "INSERT INTO accounts (customer_id, account_number, account_type, balance, status) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			// Generate and set unique account number
			String uniqueAccountNumber = generateUniqueAccountNumber();
			account.setAccountNumber(uniqueAccountNumber);

			ps.setInt(1, account.getCustomerId());
			ps.setString(2, account.getAccountNumber());
			ps.setString(3, account.getAccountType().name());
			ps.setBigDecimal(4, account.getBalance());
			ps.setString(5, account.getStatus().name());

			int result = ps.executeUpdate();

			if (result > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						account.setAccountId(rs.getInt(1));
					}
				}
				System.out.println("Account created successfully with ID: " + account.getAccountId());
				return true;
			} else {
				System.err.println("Failed to insert account into database.");
			}
		} catch (SQLException e) {
			System.err.println("Error creating account in database:");
			e.printStackTrace();
		} catch (RuntimeException re) {
			System.err.println("Account creation aborted: " + re.getMessage());
		}
		return false;
	}

	public boolean updateBalance(int accountId, BigDecimal newBalance) {
		String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setBigDecimal(1, newBalance);
			ps.setInt(2, accountId);

			int updated = ps.executeUpdate();
			if (updated > 0) {
				System.out.println("Balance updated for account ID: " + accountId);
				return true;
			} else {
				System.err.println("No account found with account ID: " + accountId);
			}
		} catch (SQLException e) {
			System.err.println("Error updating balance for account ID: " + accountId);
			e.printStackTrace();
		}
		return false;
	}

	private Account mapRowToAccount(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setAccountId(rs.getInt("account_id"));
		account.setCustomerId(rs.getInt("customer_id"));
		account.setAccountNumber(rs.getString("account_number"));
		account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
		account.setBalance(rs.getBigDecimal("balance"));
		account.setCreatedAt(rs.getTimestamp("created_at"));
		account.setUpdatedAt(rs.getTimestamp("updated_at"));
		account.setStatus(AccountStatus.valueOf(rs.getString("status")));
		return account;
	}
}
