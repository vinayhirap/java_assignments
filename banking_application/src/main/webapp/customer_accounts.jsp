<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.User"%>
<%@ page import="com.aurionpro.model.Customer"%>
<%@ page import="com.aurionpro.model.Account"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Accounts - Aurionpro Bank</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<!-- Include same sidebar CSS as profile.jsp -->
</head>
<body>
	<%
	User user = (User) session.getAttribute("user");
	Customer customer = (Customer) request.getAttribute("customer");
	List<Account> accounts = (List<Account>) request.getAttribute("accounts");
	%>

	<!-- Include same sidebar as profile.jsp with accounts as active -->

	<!-- Main Content -->
	<div class="main-content">
		<div class="card">
			<div class="card-body">
				<h2>
					<i class="fas fa-piggy-bank me-2 text-primary"></i>My Accounts
				</h2>
				<p class="text-muted mb-0">View and manage your bank accounts</p>
			</div>
		</div>

		<c:choose>
			<c:when test="${not empty accounts}">
				<div class="row">
					<c:forEach var="account" items="${accounts}">
						<div class="col-md-6 mb-4">
							<div class="card">
								<div class="card-body">
									<div class="d-flex justify-content-between align-items-start">
										<div>
											<h5 class="card-title">${account.accountType}</h5>
											<p class="text-muted">Account No:
												${account.accountNumber}</p>
											<h3 class="text-success">₹${account.balance}</h3>
										</div>
										<div class="dropdown">
											<button class="btn btn-outline-primary dropdown-toggle"
												type="button" data-bs-toggle="dropdown">Actions</button>
											<ul class="dropdown-menu">
												<li><a class="dropdown-item"
													href="deposit?accountId=${account.accountId}">Deposit</a></li>
												<li><a class="dropdown-item"
													href="withdraw?accountId=${account.accountId}">Withdraw</a></li>
												<li><a class="dropdown-item"
													href="transactions?accountId=${account.accountId}">View
														Transactions</a></li>
											</ul>
										</div>
									</div>
									<p class="card-text mt-3">
										<small class="text-muted">Created:
											${account.createdAt}</small><br> <span class="badge bg-success">Active</span>
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<div class="text-center py-5">
					<i class="fas fa-piggy-bank fa-5x text-muted"></i>
					<h4 class="text-muted">No Accounts Found</h4>
					<p class="text-muted">You don't have any bank accounts yet.</p>
					<a href="apply-account" class="btn btn-primary">Open New
						Account</a>

				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
