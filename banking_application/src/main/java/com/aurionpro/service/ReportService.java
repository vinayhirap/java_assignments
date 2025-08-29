package com.aurionpro.service;

import com.aurionpro.dao.ReportDAO;
import com.aurionpro.model.Report;

import java.util.List;

public class ReportService {

	private ReportDAO reportDAO;

	public ReportService() {
		this.reportDAO = new ReportDAO();
	}

	public List<Report> getAllReports() throws Exception {
		return reportDAO.getAllReports();
	}
}
