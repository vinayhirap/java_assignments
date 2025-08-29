package com.aurionpro.service;

import com.aurionpro.dao.TransactionDAO;
import com.aurionpro.model.Transaction;
import java.util.List;

public class TransactionService {
	private TransactionDAO transactionDAO;

	public TransactionService() {
		this.transactionDAO = new TransactionDAO();
	}

	public List<Transaction> getAccountTransactions(int accountId) {
		return transactionDAO.getTransactionsByAccountId(accountId);
	}

	public List<Transaction> getAllTransactions() {
		return transactionDAO.getAllTransactions();
	}

	public Transaction getTransactionByReference(String referenceNumber) {
		return transactionDAO.getTransactionByReferenceNumber(referenceNumber);
	}

	public List<Transaction> getTransactionHistory(int accountId, int limit) {
		List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(accountId);
		if (transactions.size() > limit) {
			return transactions.subList(0, limit);
		}
		return transactions;
	}
}