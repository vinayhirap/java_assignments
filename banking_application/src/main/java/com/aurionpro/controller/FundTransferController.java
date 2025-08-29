package com.aurionpro.controller;

import com.aurionpro.model.*;
import com.aurionpro.service.AccountService;
import com.aurionpro.dao.CustomerDAO;
import com.aurionpro.dao.BeneficiaryDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/customer/transfer")
public class FundTransferController extends HttpServlet {
	private AccountService accountService;
	private CustomerDAO customerDAO;
	private BeneficiaryDAO beneficiaryDAO;

	@Override
	public void init() throws ServletException {
		accountService = new AccountService();
		customerDAO = new CustomerDAO();
		beneficiaryDAO = new BeneficiaryDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("customer") == null) {
			response.sendRedirect("../login");
			return;
		}

		Customer customer = (Customer) session.getAttribute("customer");

		// Get customer accounts
		List<Account> accounts = accountService.getCustomerAccounts(customer.getCustomerId());
		request.setAttribute("accounts", accounts);

		// Get customer beneficiaries
		List<Beneficiary> beneficiaries = beneficiaryDAO.getBeneficiariesByCustomerId(customer.getCustomerId());
		request.setAttribute("beneficiaries", beneficiaries);

		request.getRequestDispatcher("../fund_transfer.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("customer") == null) {
			response.sendRedirect("../login");
			return;
		}

		try {
			Customer customer = (Customer) session.getAttribute("customer");

			// Get form parameters
			String fromAccountStr = request.getParameter("fromAccount");
			String transferType = request.getParameter("transferType");
			String amountStr = request.getParameter("amount");
			String description = request.getParameter("description");

			// Validation
			if (fromAccountStr == null || transferType == null || amountStr == null || fromAccountStr.trim().isEmpty()
					|| transferType.trim().isEmpty() || amountStr.trim().isEmpty()) {
				request.setAttribute("error", "All required fields must be filled");
				doGet(request, response);
				return;
			}

			int fromAccountId = Integer.parseInt(fromAccountStr);
			BigDecimal amount = new BigDecimal(amountStr);

			if (amount.compareTo(BigDecimal.ZERO) <= 0) {
				request.setAttribute("error", "Amount must be greater than zero");
				doGet(request, response);
				return;
			}

			boolean success = false;

			switch (transferType) {
			case "beneficiary":
				String beneficiaryIdStr = request.getParameter("beneficiaryId");
				if (beneficiaryIdStr != null && !beneficiaryIdStr.trim().isEmpty()) {
					int beneficiaryId = Integer.parseInt(beneficiaryIdStr);
					Beneficiary beneficiary = beneficiaryDAO.getBeneficiaryById(beneficiaryId);
					if (beneficiary != null) {
						// Get beneficiary account
						Account toAccount = accountService
								.getAccountByNumber(beneficiary.getBeneficiaryAccountNumber());
						if (toAccount != null) {
							success = accountService.transfer(fromAccountId, toAccount.getAccountId(), amount,
									"Transfer to " + beneficiary.getNickname() + " - "
											+ (description != null ? description : ""));
						}
					}
				}
				break;

			case "account":
				String toAccountNumber = request.getParameter("toAccount");
				if (toAccountNumber != null && !toAccountNumber.trim().isEmpty()) {
					Account toAccount = accountService.getAccountByNumber(toAccountNumber);
					if (toAccount != null) {
						success = accountService.transfer(fromAccountId, toAccount.getAccountId(), amount,
								"Transfer to " + toAccountNumber + " - " + (description != null ? description : ""));
					} else {
						request.setAttribute("error", "Invalid account number");
						doGet(request, response);
						return;
					}
				}
				break;

			case "own":
				String toOwnAccountStr = request.getParameter("toOwnAccount");
				if (toOwnAccountStr != null && !toOwnAccountStr.trim().isEmpty()) {
					int toAccountId = Integer.parseInt(toOwnAccountStr);
					success = accountService.transfer(fromAccountId, toAccountId, amount,
							"Transfer between own accounts - " + (description != null ? description : ""));
				}
				break;
			}

			if (success) {
				request.setAttribute("success", "Transfer completed successfully!");
			} else {
				request.setAttribute("error", "Transfer failed. Please check your details and try again.");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("error", "Invalid amount or account details");
		} catch (Exception e) {
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			e.printStackTrace();
		}

		doGet(request, response);
	}
}