package com.aurionpro.service;

import com.aurionpro.dao.LoanDAO;
import com.aurionpro.model.Loan;

import java.util.List;

/**
 * Service layer class to handle Loan-related business logic.
 */
public class LoanService {

	private LoanDAO loanDAO;

	/**
	 * Constructor initializes LoanDAO.
	 */
	public LoanService() {
		loanDAO = new LoanDAO();
	}

	/**
	 * Fetches all loans from the data source.
	 * 
	 * @return list of Loan objects
	 * @throws Exception if loading fails
	 */

	public boolean addLoan(Loan loan) throws Exception {
		return loanDAO.addLoan(loan);
	}

	public boolean updateLoan(Loan loan) throws Exception {
		return loanDAO.updateLoan(loan);
	}

	public boolean deleteLoan(int loanId) throws Exception {
		return loanDAO.deleteLoan(loanId);
	}

	public List<Loan> getAllLoans() throws Exception {
		return loanDAO.getAllLoans();
	}

	// You can add additional business logic methods here, such as:
	// public Loan getLoanById(int loanId), applyForLoan(Loan loan), etc.

}
