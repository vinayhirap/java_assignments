<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.math.BigDecimal"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Dashboard - Aurionpro Bank</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
body {
	background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.sidebar {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	min-height: 100vh;
	padding: 0;
	position: fixed;
	width: 250px;
	z-index: 1000;
}

.main-content {
	margin-left: 250px;
	padding: 20px;
}

.nav-link {
	color: rgba(255, 255, 255, 0.8) !important;
	border-radius: 10px;
	margin: 5px 15px;
	transition: all 0.3s ease;
}

.nav-link:hover, .nav-link.active {
	background: rgba(255, 255, 255, 0.2);
	color: white !important;
	transform: translateX(5px);
}

.card {
	border: none;
	border-radius: 15px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
	transition: transform 0.3s ease;
}

.card:hover {
	transform: translateY(-5px);
}

.balance-card {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: white;
}

.quick-action-btn {
	border-radius: 10px;
	padding: 15px;
	border: none;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: white;
	transition: all 0.3s ease;
}

.quick-action-btn:hover {
	transform: translateY(-3px);
	box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
	color: white;
}

.transaction-table {
	border-radius: 10px;
	overflow: hidden;
}

.header-profile {
	background: white;
	border-radius: 15px;
	padding: 20px;
	margin-bottom: 20px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>
</head>
<body>
	<%
	Customer customer = (Customer) session.getAttribute("customer");
	User user = (User) session.getAttribute("user");
	List<Account> accounts = (List<Account>) request.getAttribute("accounts");
	BigDecimal totalBalance = (BigDecimal) request.getAttribute("totalBalance");
	List<Transaction> recentTransactions = (List<Transaction>) request.getAttribute("recentTransactions");

	if (customer == null || user == null) {
		response.sendRedirect("../login");
		return;
	}
	%>

	<!-- Sidebar -->
	<nav class="sidebar">
		<div class="p-4">
			<div class="text-center text-white mb-4">
				<i class="fas fa-university fa-2x mb-3"></i>
				<h5>Aurionpro Bank</h5>
				<small>Customer Portal</small>
			</div>
		</div>

		<ul class="nav nav-pills flex-column">
			<li class="nav-item"><a class="nav-link active" href="dashboard">
					<i class="fas fa-tachometer-alt me-2"></i>Dashboard
			</a></li>
			<li class="nav-item"><a class="nav-link" href="accounts"> <i
					class="fas fa-piggy-bank me-2"></i>My Accounts
			</a></li>
			<li class="nav-item"><a class="nav-link" href="transfer"> <i
					class="fas fa-exchange-alt me-2"></i>Fund Transfer
			</a></li>
			<li class="nav-item"><a class="nav-link" href="transactions">
					<i class="fas fa-list-alt me-2"></i>Transactions
			</a></li>
			<li class="nav-item"><a class="nav-link" href="beneficiaries">
					<i class="fas fa-users me-2"></i>Beneficiaries
			</a></li>
			<li class="nav-item"><a class="nav-link" href="loans"> <i
					class="fas fa-handshake me-2"></i>Loans
			</a></li>
			<li class="nav-item"><a class="nav-link" href="fixed-deposits">
					<i class="fas fa-chart-line me-2"></i>Fixed Deposits
			</a></li>
			<li class="nav-item"><a class="nav-link" href="complaints">
					<i class="fas fa-comment-alt me-2"></i>Complaints
			</a></li>
			<li class="nav-item"><a class="nav-link" href="profile"> <i
					class="fas fa-user-edit me-2"></i>Profile
			</a></li>
			<li class="nav-item mt-4"><a class="nav-link"
				href="<%=request.getContextPath()%>/logout"> <i
					class="fas fa-sign-out-alt me-2"></i>Logout
			</a></li>
		</ul>
	</nav>

	<!-- Main Content -->
	<div class="main-content">
		<!-- Header -->
		<div class="header-profile">
			<div class="row align-items-center">
				<div class="col-md-8">
					<h3 class="mb-0">
						Welcome back,
						<%=customer.getFirstName()%>!
					</h3>
					<p class="text-muted mb-0">Here's your banking overview</p>
				</div>
				<div class="col-md-4 text-end">
					<div class="d-flex align-items-center justify-content-end">
						<div class="me-3">
							<small class="text-muted">Last Login</small><br> <small><%=new java.util.Date()%></small>
						</div>
						<div class="bg-primary rounded-circle p-3">
							<i class="fas fa-user text-white"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Balance Cards -->
		<div class="row mb-4">
			<div class="col-md-4 mb-3">
				<div class="card balance-card">
					<div class="card-body text-center">
						<i class="fas fa-wallet fa-3x mb-3"></i>
						<h2>
							₹<%=totalBalance != null ? totalBalance.toString() : "0.00"%></h2>
						<p class="mb-0">Total Balance</p>
					</div>
				</div>
			</div>
			<div class="col-md-4 mb-3">
				<div class="card bg-success text-white">
					<div class="card-body text-center">
						<i class="fas fa-credit-card fa-3x mb-3"></i>
						<h2><%=accounts != null ? accounts.size() : 0%></h2>
						<p class="mb-0">Active Accounts</p>
					</div>
				</div>
			</div>
			<div class="col-md-4 mb-3">
				<div class="card bg-info text-white">
					<div class="card-body text-center">
						<i class="fas fa-chart-bar fa-3x mb-3"></i>
						<h2><%=recentTransactions != null ? recentTransactions.size() : 0%></h2>
						<p class="mb-0">Recent Transactions</p>
					</div>
				</div>
			</div>
		</div>

		<!-- Quick Actions -->
		<div class="row mb-4">
			<div class="col-12">
				<div class="card">
					<div class="card-header">
						<h5>
							<i class="fas fa-bolt me-2"></i>Quick Actions
						</h5>
					</div>
					<div class="card-body">
						<div class="row text-center">
							<div class="col-md-3 mb-3">
								<a href="deposit" class="btn quick-action-btn w-100"> <i
									class="fas fa-plus-circle fa-2x d-block mb-2"></i> <span>Deposit
										Money</span>
								</a>
							</div>
							<div class="col-md-3 mb-3">
								<a href="withdraw" class="btn quick-action-btn w-100"> <i
									class="fas fa-minus-circle fa-2x d-block mb-2"></i> <span>Withdraw
										Money</span>
								</a>
							</div>
							<div class="col-md-3 mb-3">
								<a href="transfer" class="btn quick-action-btn w-100"> <i
									class="fas fa-exchange-alt fa-2x d-block mb-2"></i> <span>Transfer
										Funds</span>
								</a>
							</div>
							<div class="col-md-3 mb-3">
								<a href="apply-loan" class="btn quick-action-btn w-100"> <i
									class="fas fa-handshake fa-2x d-block mb-2"></i> <span>Apply
										Loan</span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Account Overview -->
		<div class="row mb-4">
			<div class="col-md-8">
				<div class="card">
					<div class="card-header">
						<h5>
							<i class="fas fa-piggy-bank me-2"></i>Your Accounts
						</h5>
					</div>
					<div class="card-body">
						<%
						if (accounts != null && !accounts.isEmpty()) {
						%>
						<div class="row">
							<%
							for (Account account : accounts) {
							%>
							<div class="col-md-6 mb-3">
								<div class="border rounded p-3 bg-light">
									<div class="d-flex justify-content-between align-items-center">
										<div>
											<h6 class="mb-1"><%=account.getAccountType()%>
												Account
											</h6>
											<p class="mb-1 text-muted">
												Account:
												<%=account.getMaskedAccountNumber()%></p>
											<h5 class="mb-0 text-success">
												₹<%=account.getBalance()%></h5>
										</div>
										<div>
											<i class="fas fa-university fa-2x text-primary"></i>
										</div>
									</div>
								</div>
							</div>
							<%
							}
							%>
						</div>
						<%
						} else {
						%>
						<div class="text-center py-4">
							<i class="fas fa-inbox fa-3x text-muted mb-3"></i>
							<p class="text-muted">No accounts found</p>
							<a href="create-account" class="btn btn-primary">Create
								Account</a>
						</div>
						<%
						}
						%>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-header">
						<h5>
							<i class="fas fa-bell me-2"></i>Notifications
						</h5>
					</div>
					<div class="card-body">
						<div class="alert alert-info">
							<i class="fas fa-info-circle me-2"></i> <small>Welcome to
								Aurionpro Bank! Your account is active.</small>
						</div>
						<div class="alert alert-warning">
							<i class="fas fa-shield-alt me-2"></i> <small>Remember to
								keep your login credentials secure.</small>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Recent Transactions -->
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div
						class="card-header d-flex justify-content-between align-items-center">
						<h5>
							<i class="fas fa-history me-2"></i>Recent Transactions
						</h5>
						<a href="transactions" class="btn btn-sm btn-primary">View All</a>
					</div>
					<div class="card-body">
						<%
						if (recentTransactions != null && !recentTransactions.isEmpty()) {
						%>
						<div class="table-responsive transaction-table">
							<table class="table table-hover mb-0">
								<thead class="table-dark">
									<tr>
										<th>Date</th>
										<th>Type</th>
										<th>Description</th>
										<th>Amount</th>
										<th>Balance</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (Transaction transaction : recentTransactions) {
									%>
									<tr>
										<td><%=transaction.getTransactionDate()%></td>
										<td><span
											class="badge bg-<%=transaction.getTransactionType().name().equals("DEPOSIT") ? "success"
		: transaction.getTransactionType().name().equals("WITHDRAWAL") ? "danger" : "primary"%>">
												<%=transaction.getTransactionType()%>
										</span></td>
										<td><%=transaction.getDescription()%></td>
										<td
											class="<%=transaction.getTransactionType().name().equals("DEPOSIT") ? "text-success" : "text-danger"%>">
											<%=transaction.getTransactionType().name().equals("DEPOSIT") ? "+" : "-"%>₹<%=transaction.getAmount()%>
										</td>
										<td>₹<%=transaction.getBalanceAfter()%></td>
									</tr>
									<%
									}
									%>
								</tbody>
							</table>
						</div>
						<%
						} else {
						%>
						<div class="text-center py-4">
							<i class="fas fa-receipt fa-3x text-muted mb-3"></i>
							<p class="text-muted">No recent transactions</p>
						</div>
						<%
						}
						%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>