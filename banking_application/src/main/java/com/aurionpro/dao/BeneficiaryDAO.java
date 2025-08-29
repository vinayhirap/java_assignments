package com.aurionpro.dao;

import com.aurionpro.model.Beneficiary;
import com.aurionpro.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryDAO {

	public boolean addBeneficiary(Beneficiary beneficiary) {
		String sql = "INSERT INTO beneficiaries (customer_id, beneficiary_account_number, beneficiary_name, nickname, bank_name, ifsc_code, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, beneficiary.getCustomerId());
			stmt.setString(2, beneficiary.getBeneficiaryAccountNumber());
			stmt.setString(3, beneficiary.getBeneficiaryName());
			stmt.setString(4, beneficiary.getNickname());
			stmt.setString(5, beneficiary.getBankName());
			stmt.setString(6, beneficiary.getIfscCode());
			stmt.setString(7, beneficiary.getStatus().name());

			int result = stmt.executeUpdate();

			if (result > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					beneficiary.setBeneficiaryId(rs.getInt(1));
				}
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Beneficiary> getBeneficiariesByCustomerId(int customerId) {
		String sql = "SELECT * FROM beneficiaries WHERE customer_id = ? AND status = 'ACTIVE'";
		List<Beneficiary> beneficiaries = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, customerId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Beneficiary beneficiary = new Beneficiary();
				beneficiary.setBeneficiaryId(rs.getInt("beneficiary_id"));
				beneficiary.setCustomerId(rs.getInt("customer_id"));
				beneficiary.setBeneficiaryAccountNumber(rs.getString("beneficiary_account_number"));
				beneficiary.setBeneficiaryName(rs.getString("beneficiary_name"));
				beneficiary.setNickname(rs.getString("nickname"));
				beneficiary.setBankName(rs.getString("bank_name"));
				beneficiary.setIfscCode(rs.getString("ifsc_code"));
				beneficiary.setStatus(Beneficiary.BeneficiaryStatus.valueOf(rs.getString("status")));
				beneficiaries.add(beneficiary);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beneficiaries;
	}

	public Beneficiary getBeneficiaryById(int beneficiaryId) {
		String sql = "SELECT * FROM beneficiaries WHERE beneficiary_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, beneficiaryId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Beneficiary beneficiary = new Beneficiary();
				beneficiary.setBeneficiaryId(rs.getInt("beneficiary_id"));
				beneficiary.setCustomerId(rs.getInt("customer_id"));
				beneficiary.setBeneficiaryAccountNumber(rs.getString("beneficiary_account_number"));
				beneficiary.setBeneficiaryName(rs.getString("beneficiary_name"));
				beneficiary.setNickname(rs.getString("nickname"));
				beneficiary.setBankName(rs.getString("bank_name"));
				beneficiary.setIfscCode(rs.getString("ifsc_code"));
				beneficiary.setStatus(Beneficiary.BeneficiaryStatus.valueOf(rs.getString("status")));
				return beneficiary;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteBeneficiary(int beneficiaryId) {
		String sql = "UPDATE beneficiaries SET status = 'INACTIVE' WHERE beneficiary_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, beneficiaryId);
			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}