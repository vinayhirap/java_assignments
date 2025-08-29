<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard - Aurionpro Bank</title>
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
	background: linear-gradient(135deg, #dc3545 0%, #fd7e14 100%);
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

.stat-card {
	background: linear-gradient(135deg, #dc3545 0%, #fd7e14 100%);
	color: white;
}

.header-admin {
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
	User user = (User) session.getAttribute("user");
	if (user == null || user.getUserType() != User.UserType.ADMIN) {
		response.sendRedirect("../login");
		return;
	}

	Integer totalCustomers = (Integer) request.getAttribute("totalCustomers");
	Integer totalAccounts = (Integer) request.getAttribute("totalAccounts");
	Integer totalTransactions = (Integer) request.getAttribute("totalTransactions");
	Integer pendingApprovals = (Integer) request.getAttribute("pendingApprovals");
	List<Transaction> recentTransactions = (List<Transaction>) request.getAttribute("recentTransactions");
	%>

	<!-- Sidebar -->
	<nav class="sidebar">
		<div class="p-4">
			<div class="text-center text-white mb-4">
				<i class="fas fa-shield-alt fa-2x mb-3"></i>
				<h5>Aurionpro Bank</h5>
				<small>Admin Portal</small>
			</div>

			<ul class="nav nav-pills flex-column">
				<li class="nav-item"><a class="nav-link active"
					href="dashboard"> <i class="fas fa-tachometer-alt me-2"></i>Dashboard
				</a></li>
				<li class="nav-item"><a class="nav-link" href="manage-users">
						<i class="fas fa-users-cog me-2"></i>Manage Users
				</a></li>
				<li class="nav-item"><a class="nav-link" href="approve-users">
						<i class="fas fa-user-check me-2"></i>Approve Users
				</a></li>
				<li class="nav-item"><a class="nav-link" href="manage-accounts">
						<i class="fas fa-piggy-bank me-2"></i>Manage Accounts
				</a></li>
				<li class="nav-item"><a class="nav-link"
					href="view-transactions"> <i class="fas fa-exchange-alt me-2"></i>All
						Transactions
				</a></li>
				<li class="nav-item"><a class="nav-link" href="manage-loans">
						<i class="fas fa-handshake me-2"></i>Manage Loans
				</a></li>
				<li class="nav-item"><a class="nav-link"
					href="manage-complaints"> <i class="fas fa-comments me-2"></i>Complaints
				</a></li>
				<li class="nav-item"><a class="nav-link" href="reports"> <i
						class="fas fa-chart-bar me-2"></i>Reports
				</a></li>
				<li class="nav-item mt-4"><a class="nav-link"
					href="<%=request.getContextPath()%>/logout"> <i
						class="fas fa-sign-out-alt me-2"></i>Logout
				</a></li>
			</ul>
		</div>
	</nav>

	<!-- Main Content -->
	<div class="main-content">
		<!-- Header -->
		<div class="header-admin">
			<div class="row align-items-center">
				<div class="col-md-8">
					<h3 class="mb-0">Admin Dashboard</h3>
					<p class="text-muted mb-0">
						Welcome,
						<%=user.getUsername()%>! Manage your banking system
					</p>
				</div>
				<div class="col-md-4 text-end">
					<div class="d-flex align-items-center justify-content-end">
						<div class="me-3">
							<small class="text-muted">Role: Administrator</small><br> <small>Email:
								admin@aurionpro.com</small>
						</div>
						<div class="bg-danger rounded-circle p-3">
							<i class="fas fa-user-shield text-white"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Statistics Cards -->
		<div class="row mb-4">
			<div class="col-md-3 mb-3">
				<div class="card stat-card">
					<div class="card-body text-center">
						<i class="fas fa-users fa-3x mb-3"></i>
						<h2><%=totalCustomers != null ? totalCustomers : 0%></h2>
						<p class="mb-0">Total Customers</p>
					</div>
				</div>
			</div>
			<div class="col-md-3 mb-3">
				<div class="card bg-success text-white">
					<div class="card-body text-center">
						<i class="fas fa-university fa-3x mb-3"></i>
						<h2><%=totalAccounts != null ? totalAccounts : 0%></h2>
						<p class="mb-0">Total Accounts</p>
					</div>
				</div>
			</div>
			<div class="col-md-3 mb-3">
				<div class="card bg-info text-white">
					<div class="card-body text-center">
						<i class="fas fa-exchange-alt fa-3x mb-3"></i>
						<h2><%=totalTransactions != null ? totalTransactions : 0%></h2>
						<p class="mb-0">Total Transactions</p>
					</div>
				</div>
			</div>
			<div class="col-md-3 mb-3">
				<div class="card bg-warning text-white">
					<div class="card-body text-center">
						<i class="fas fa-clock fa-3x mb-3"></i>
						<h2><%=pendingApprovals != null ? pendingApprovals : 0%></h2>
						<p class="mb-0">Pending Approvals</p>
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
						<div class="row">
							<div class="col-md-3 mb-3">
								<a href="approve-users"
									class="btn btn-outline-primary w-100 p-3"> <i
									class="fas fa-user-check fa-2x d-block mb-2"></i> <span>Approve
										Users</span> <%
 if (pendingApprovals != null && pendingApprovals > 0) {
 %> <span class="badge bg-danger ms-2"><%=pendingApprovals%></span> <%
 }
 %>
								</a>
							</div>
							<div class="col-md-3 mb-3">
								<a href="manage-accounts"
									class="btn btn-outline-success w-100 p-3"> <i
									class="fas fa-cog fa-2x d-block mb-2"></i> <span>Manage
										Accounts</span>
								</a>
							</div>
							<div class="col-md-3 mb-3">
								<a href="view-transactions"
									class="btn btn-outline-info w-100 p-3"> <i
									class="fas fa-list fa-2x d-block mb-2"></i> <span>View
										Transactions</span>
								</a>
							</div>
							<div class="col-md-3 mb-3">
								<a href="reports" class="btn btn-outline-warning w-100 p-3">
									<i class="fas fa-chart-bar fa-2x d-block mb-2"></i> <span>Generate
										Reports</span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Recent Activity -->
		<div class="row">
			<div class="col-md-8">
				<div class="card">
					<div
						class="card-header d-flex justify-content-between align-items-center">
						<h5>
							<i class="fas fa-history me-2"></i>Recent Transactions
						</h5>
						<a href="view-transactions" class="btn btn-sm btn-primary">View
							All</a>
					</div>
					<div class="card-body">
						<%
						if (recentTransactions != null && !recentTransactions.isEmpty()) {
						%>
						<div class="table-responsive">
							<table class="table table-hover mb-0">
								<thead class="table-dark">
									<tr>
										<th>ID</th>
										<th>Date</th>
										<th>Type</th>
										<th>Amount</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (int i = 0; i < Math.min(10, recentTransactions.size()); i++) {
										Transaction transaction = recentTransactions.get(i);
									%>
									<tr>
										<td>#<%=transaction.getTransactionId()%></td>
										<td><%=transaction.getTransactionDate()%></td>
										<td><span
											class="badge bg-<%=transaction.getTransactionType().name().equals("DEPOSIT") ? "success"
		: transaction.getTransactionType().name().equals("WITHDRAWAL") ? "danger" : "primary"%>">
												<%=transaction.getTransactionType()%>
										</span></td>
										<td>₹<%=transaction.getAmount()%></td>
										<td><span
											class="badge bg-<%=transaction.getStatus().name().equals("COMPLETED") ? "success" : "warning"%>">
												<%=transaction.getStatus()%>
										</span></td>
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
							<i class="fas fa-inbox fa-3x text-muted mb-3"></i>
							<p class="text-muted">No recent transactions</p>
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
							<i class="fas fa-chart-pie me-2"></i>Quick Stats
						</h5>
					</div>
					<div class="card-body">
						<div class="row text-center">
							<div class="col-6 border-end">
								<h4 class="text-success"><%=totalAccounts != null ? totalAccounts : 0%></h4>
								<small class="text-muted">Active Accounts</small>
							</div>
							<div class="col-6">
								<h4 class="text-primary"><%=totalCustomers != null ? totalCustomers : 0%></h4>
								<small class="text-muted">Total Users</small>
							</div>
						</div>
						<hr>
						<div class="row text-center">
							<div class="col-6 border-end">
								<h4 class="text-info"><%=totalTransactions != null ? totalTransactions : 0%></h4>
								<small class="text-muted">Transactions</small>
							</div>
							<div class="col-6">
								<h4 class="text-warning"><%=pendingApprovals != null ? pendingApprovals : 0%></h4>
								<small class="text-muted">Pending</small>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
