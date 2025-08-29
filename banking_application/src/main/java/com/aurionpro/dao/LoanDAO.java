package com.aurionpro.dao;

import com.aurionpro.model.Loan;
import com.aurionpro.model.Loan.LoanStatus;
import com.aurionpro.model.Loan.LoanType;
import com.aurionpro.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class LoanDAO {

	/**
	 * Retrieves all loans from the database.
	 * 
	 * @return List of Loan objects
	 * @throws Exception if database operation fails
	 */
	public List<Loan> getAllLoans() throws Exception {
		List<Loan> loans = new ArrayList<>();
		String sql = "SELECT loan_id, customer_id, account_id, loan_type, loan_amount, interest_rate, tenure_months, emi_amount, "
				+ "outstanding_amount, status, disbursement_date, created_at, updated_at FROM loans";

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Loan loan = mapRowToLoan(rs);
				loans.add(loan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error fetching loans from database", e);
		}
		return loans;
	}

	/**
	 * Adds a new Loan to the database.
	 * 
	 * @param loan Loan object to add
	 * @return true if insertion is successful
	 * @throws Exception if insertion fails
	 */
	public boolean addLoan(Loan loan) throws Exception {
		String sql = "INSERT INTO loans (customer_id, account_id, loan_type, loan_amount, interest_rate, tenure_months, emi_amount, "
				+ "outstanding_amount, status, disbursement_date, created_at, updated_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, loan.getCustomerId());
			ps.setInt(2, loan.getAccountId());
			ps.setString(3, loan.getLoanType().name());
			ps.setBigDecimal(4, loan.getLoanAmount());
			ps.setBigDecimal(5, loan.getInterestRate());
			ps.setInt(6, loan.getTenureMonths());
			ps.setBigDecimal(7, loan.getEmiAmount());
			ps.setBigDecimal(8, loan.getOutstandingAmount());
			ps.setString(9, loan.getStatus().name());
			if (loan.getDisbursementDate() != null) {
				ps.setDate(10, loan.getDisbursementDate());
			} else {
				ps.setNull(10, Types.DATE);
			}

			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				throw new Exception("Creating loan failed, no rows affected.");
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					loan.setLoanId(generatedKeys.getInt(1));
				} else {
					throw new Exception("Creating loan failed, no ID obtained.");
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error inserting loan into database", e);
		}
	}

	/**
	 * Updates an existing Loan in the database.
	 * 
	 * @param loan Loan object with updated data
	 * @return true if update is successful
	 * @throws Exception if update fails
	 */
	public boolean updateLoan(Loan loan) throws Exception {
		String sql = "UPDATE loans SET account_id = ?, loan_type = ?, loan_amount = ?, interest_rate = ?, tenure_months = ?, "
				+ "emi_amount = ?, outstanding_amount = ?, status = ?, disbursement_date = ?, updated_at = CURRENT_TIMESTAMP "
				+ "WHERE loan_id = ?";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, loan.getAccountId());
			ps.setString(2, loan.getLoanType().name());
			ps.setBigDecimal(3, loan.getLoanAmount());
			ps.setBigDecimal(4, loan.getInterestRate());
			ps.setInt(5, loan.getTenureMonths());
			ps.setBigDecimal(6, loan.getEmiAmount());
			ps.setBigDecimal(7, loan.getOutstandingAmount());
			ps.setString(8, loan.getStatus().name());
			if (loan.getDisbursementDate() != null) {
				ps.setDate(9, loan.getDisbursementDate());
			} else {
				ps.setNull(9, Types.DATE);
			}
			ps.setInt(10, loan.getLoanId());

			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error updating loan in database", e);
		}
	}

	/**
	 * Deletes a Loan from the database (soft delete by status or hard delete).
	 * 
	 * @param loanId ID of loan to delete
	 * @return true if delete succeeded
	 * @throws Exception if deletion fails
	 */
	public boolean deleteLoan(int loanId) throws Exception {
		// Example: soft delete by setting status to CLOSED
		String sql = "UPDATE loans SET status = ? WHERE loan_id = ?";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, Loan.LoanStatus.CLOSED.name());
			ps.setInt(2, loanId);

			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error deleting loan in database", e);
		}
	}

	/**
	 * Helper method to map a ResultSet row to a Loan object.
	 * 
	 * @param rs ResultSet positioned at current row
	 * @return Loan object
	 * @throws SQLException on DB errors
	 */
	private Loan mapRowToLoan(ResultSet rs) throws SQLException {
		Loan loan = new Loan();
		loan.setLoanId(rs.getInt("loan_id"));
		loan.setCustomerId(rs.getInt("customer_id"));
		loan.setAccountId(rs.getInt("account_id"));

		String loanTypeStr = rs.getString("loan_type");
		if (loanTypeStr != null) {
			loan.setLoanType(LoanType.valueOf(loanTypeStr.toUpperCase()));
		}

		loan.setLoanAmount(rs.getBigDecimal("loan_amount"));
		loan.setInterestRate(rs.getBigDecimal("interest_rate"));
		loan.setTenureMonths(rs.getInt("tenure_months"));
		loan.setEmiAmount(rs.getBigDecimal("emi_amount"));
		loan.setOutstandingAmount(rs.getBigDecimal("outstanding_amount"));

		String statusStr = rs.getString("status");
		if (statusStr != null) {
			loan.setStatus(LoanStatus.valueOf(statusStr.toUpperCase()));
		}

		loan.setDisbursementDate(rs.getDate("disbursement_date"));
		loan.setCreatedAt(rs.getTimestamp("created_at"));
		loan.setUpdatedAt(rs.getTimestamp("updated_at"));

		return loan;
	}
}
