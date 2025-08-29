package com.aurionpro.service;

import com.aurionpro.dao.AccountDAO;
import com.aurionpro.dao.TransactionDAO;
import com.aurionpro.model.Account;
import com.aurionpro.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
	private AccountDAO accountDAO;
	private TransactionDAO transactionDAO;

	public AccountService() {
		this.accountDAO = new AccountDAO();
		this.transactionDAO = new TransactionDAO();
	}

	/**
	 * Creates a new account. Account number generation is handled inside DAO for
	 * uniqueness.
	 */
	public boolean createAccount(Account account) {
		return accountDAO.createAccount(account);
	}

	public Account getAccountByNumber(String accountNumber) {
		return accountDAO.getAccountByNumber(accountNumber);
	}

	public List<Account> getCustomerAccounts(int customerId) {
		return accountDAO.getAccountsByCustomerId(customerId);
	}

	/**
	 * Deposits a specified amount into the account if active.
	 */
	public boolean deposit(int accountId, BigDecimal amount, String description) {
		try {
			Account account = accountDAO.getAccountById(accountId);
			if (account == null || account.getStatus() != Account.AccountStatus.ACTIVE) {
				return false;
			}

			BigDecimal newBalance = account.getBalance().add(amount);

			// Update account balance
			if (accountDAO.updateBalance(accountId, newBalance)) {
				// Create transaction record
				Transaction transaction = new Transaction();
				transaction.setToAccountId(accountId);
				transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
				transaction.setAmount(amount);
				transaction.setBalanceAfter(newBalance);
				transaction.setDescription(description);
				transaction.setStatus(Transaction.TransactionStatus.COMPLETED);

				return transactionDAO.createTransaction(transaction);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Withdraws a specified amount from the account if active and sufficient
	 * balance.
	 */
	public boolean withdraw(int accountId, BigDecimal amount, String description) {
		try {
			Account account = accountDAO.getAccountById(accountId);
			if (account == null || account.getStatus() != Account.AccountStatus.ACTIVE) {
				return false;
			}

			if (account.getBalance().compareTo(amount) < 0) {
				return false; // Insufficient balance
			}

			BigDecimal newBalance = account.getBalance().subtract(amount);

			// Update account balance
			if (accountDAO.updateBalance(accountId, newBalance)) {
				// Create transaction record
				Transaction transaction = new Transaction();
				transaction.setFromAccountId(accountId);
				transaction.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
				transaction.setAmount(amount);
				transaction.setBalanceAfter(newBalance);
				transaction.setDescription(description);
				transaction.setStatus(Transaction.TransactionStatus.COMPLETED);

				return transactionDAO.createTransaction(transaction);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Transfers amount from one account to another if both accounts are active and
	 * fromAccount has sufficient balance.
	 */
	public boolean transfer(int fromAccountId, int toAccountId, BigDecimal amount, String description) {
		try {
			Account fromAccount = accountDAO.getAccountById(fromAccountId);
			Account toAccount = accountDAO.getAccountById(toAccountId);

			if (fromAccount == null || toAccount == null || fromAccount.getStatus() != Account.AccountStatus.ACTIVE
					|| toAccount.getStatus() != Account.AccountStatus.ACTIVE) {
				return false;
			}

			if (fromAccount.getBalance().compareTo(amount) < 0) {
				return false; // Insufficient balance
			}

			BigDecimal fromNewBalance = fromAccount.getBalance().subtract(amount);
			BigDecimal toNewBalance = toAccount.getBalance().add(amount);

			// Update both account balances atomically (consider transaction in real app)
			if (accountDAO.updateBalance(fromAccountId, fromNewBalance)
					&& accountDAO.updateBalance(toAccountId, toNewBalance)) {
				// Create transaction record
				Transaction transaction = new Transaction();
				transaction.setFromAccountId(fromAccountId);
				transaction.setToAccountId(toAccountId);
				transaction.setTransactionType(Transaction.TransactionType.TRANSFER);
				transaction.setAmount(amount);
				transaction.setBalanceAfter(fromNewBalance);
				transaction.setDescription(description);
				transaction.setStatus(Transaction.TransactionStatus.COMPLETED);

				return transactionDAO.createTransaction(transaction);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Account> getAllAccounts() {
		return accountDAO.getAllAccounts();
	}

	public BigDecimal getAccountBalance(int accountId) {
		Account account = accountDAO.getAccountById(accountId);
		return account != null ? account.getBalance() : BigDecimal.ZERO;
	}
}
