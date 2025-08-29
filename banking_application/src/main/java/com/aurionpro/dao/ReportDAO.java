package com.aurionpro.dao;

import com.aurionpro.model.Report;
import com.aurionpro.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

	public List<Report> getAllReports() throws Exception {
		List<Report> reports = new ArrayList<>();

		String sql = "SELECT report_id, title, description, created_at FROM reports";

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Report report = new Report();
				report.setReportId(rs.getInt("report_id"));
				report.setTitle(rs.getString("title"));
				report.setDescription(rs.getString("description"));
				report.setCreatedAt(rs.getTimestamp("created_at"));
				reports.add(report);
			}
		}

		return reports;
	}
}
