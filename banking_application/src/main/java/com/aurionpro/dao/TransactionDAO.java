package com.aurionpro.dao;

import com.aurionpro.model.Transaction;
import com.aurionpro.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDAO {

	public boolean createTransaction(Transaction transaction) {
		String sql = "INSERT INTO transactions (from_account_id, to_account_id, transaction_type, amount, balance_after, description, reference_number, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setObject(1, transaction.getFromAccountId());
			stmt.setObject(2, transaction.getToAccountId());
			stmt.setString(3, transaction.getTransactionType().name());
			stmt.setBigDecimal(4, transaction.getAmount());
			stmt.setBigDecimal(5, transaction.getBalanceAfter());
			stmt.setString(6, transaction.getDescription());
			stmt.setString(7, generateReferenceNumber());
			stmt.setString(8, transaction.getStatus().name());

			int result = stmt.executeUpdate();

			if (result > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					transaction.setTransactionId(rs.getInt(1));
				}
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Transaction> getTransactionsByAccountId(int accountId) {
		String sql = "SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id = ? ORDER BY transaction_date DESC";
		List<Transaction> transactions = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, accountId);
			stmt.setInt(2, accountId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(rs.getInt("transaction_id"));
				transaction.setFromAccountId((Integer) rs.getObject("from_account_id"));
				transaction.setToAccountId((Integer) rs.getObject("to_account_id"));
				transaction.setTransactionType(Transaction.TransactionType.valueOf(rs.getString("transaction_type")));
				transaction.setAmount(rs.getBigDecimal("amount"));
				transaction.setBalanceAfter(rs.getBigDecimal("balance_after"));
				transaction.setDescription(rs.getString("description"));
				transaction.setReferenceNumber(rs.getString("reference_number"));
				transaction.setStatus(Transaction.TransactionStatus.valueOf(rs.getString("status")));
				transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
				transactions.add(transaction);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public List<Transaction> getAllTransactions() {
		String sql = "SELECT t.*, a1.account_number as from_account, a2.account_number as to_account FROM transactions t LEFT JOIN accounts a1 ON t.from_account_id = a1.account_id LEFT JOIN accounts a2 ON t.to_account_id = a2.account_id ORDER BY transaction_date DESC";
		List<Transaction> transactions = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(rs.getInt("transaction_id"));
				transaction.setFromAccountId((Integer) rs.getObject("from_account_id"));
				transaction.setToAccountId((Integer) rs.getObject("to_account_id"));
				transaction.setTransactionType(Transaction.TransactionType.valueOf(rs.getString("transaction_type")));
				transaction.setAmount(rs.getBigDecimal("amount"));
				transaction.setBalanceAfter(rs.getBigDecimal("balance_after"));
				transaction.setDescription(rs.getString("description"));
				transaction.setReferenceNumber(rs.getString("reference_number"));
				transaction.setStatus(Transaction.TransactionStatus.valueOf(rs.getString("status")));
				transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
				transactions.add(transaction);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public Transaction getTransactionByReferenceNumber(String referenceNumber) {
		String sql = "SELECT * FROM transactions WHERE reference_number = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, referenceNumber);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(rs.getInt("transaction_id"));
				transaction.setFromAccountId((Integer) rs.getObject("from_account_id"));
				transaction.setToAccountId((Integer) rs.getObject("to_account_id"));
				transaction.setTransactionType(Transaction.TransactionType.valueOf(rs.getString("transaction_type")));
				transaction.setAmount(rs.getBigDecimal("amount"));
				transaction.setBalanceAfter(rs.getBigDecimal("balance_after"));
				transaction.setDescription(rs.getString("description"));
				transaction.setReferenceNumber(rs.getString("reference_number"));
				transaction.setStatus(Transaction.TransactionStatus.valueOf(rs.getString("status")));
				transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
				return transaction;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String generateReferenceNumber() {
		return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}
}