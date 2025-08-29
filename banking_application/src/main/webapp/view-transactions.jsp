<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.Transaction"%>
<%@ page import="com.aurionpro.model.User"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>View Transactions - Aurionpro Bank Admin</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet" />
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
}

.table thead {
	background: #dc3545;
	color: white;
}
</style>
</head>
<body>
	<%
	User admin = (User) session.getAttribute("user");
	if (admin == null || admin.getUserType() != User.UserType.ADMIN) {
		response.sendRedirect("../login");
		return;
	}

	List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
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
				<li class="nav-item"><a class="nav-link" href="dashboard">
						<i class="fas fa-tachometer-alt me-2"></i>Dashboard
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
				<li class="nav-item"><a class="nav-link active"
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

	<div class="main-content">
		<h3>All Transactions</h3>
		<hr>
		<%
		if (transactions == null || transactions.isEmpty()) {
		%>
		<div class="alert alert-info">No transactions found.</div>
		<%
		} else {
		%>
		<div class="table-responsive">
			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th>Transaction ID</th>
						<th>From Account</th>
						<th>To Account</th>
						<th>Type</th>
						<th>Amount</th>
						<th>Balance After</th>
						<th>Description</th>
						<th>Status</th>
						<th>Date</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (Transaction txn : transactions) {
					%>
					<tr>
						<td>#<%=txn.getTransactionId()%></td>
						<td><%=txn.getFromAccountId() != null ? txn.getFromAccountId() : "-"%></td>
						<td><%=txn.getToAccountId() != null ? txn.getToAccountId() : "-"%></td>
						<td><%=txn.getTransactionType()%></td>
						<td>₹<%=txn.getAmount()%></td>
						<td>₹<%=txn.getBalanceAfter()%></td>
						<td><%=txn.getDescription() != null ? txn.getDescription() : "-"%></td>
						<td><%=txn.getStatus()%></td>
						<td><%=txn.getTransactionDate()%></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
		<%
		}
		%>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
