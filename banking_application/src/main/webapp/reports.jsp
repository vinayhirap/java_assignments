<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.Report"%>
<%@ page import="com.aurionpro.model.User"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Reports - Aurionpro Bank Admin</title>
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

	List<Report> reports = (List<Report>) request.getAttribute("reports");
	%>

	<nav class="sidebar">
		<div class="p-4">
			<div class="text-center text-white mb-4">
				<i class="fas fa-shield-alt fa-2x mb-3"></i>
				<h5>Aurionpro Bank</h5>
				<small>Admin Portal</small>
			</div>
			<ul class="nav nav-pills flex-column">
				<li class="nav-item"><a class="nav-link" href="dashboard"><i
						class="fas fa-tachometer-alt me-2"></i>Dashboard</a></li>
				<li class="nav-item"><a class="nav-link" href="manage-users"><i
						class="fas fa-users-cog me-2"></i>Manage Users</a></li>
				<li class="nav-item"><a class="nav-link" href="approve-users"><i
						class="fas fa-user-check me-2"></i>Approve Users</a></li>
				<li class="nav-item"><a class="nav-link" href="manage-accounts"><i
						class="fas fa-piggy-bank me-2"></i>Manage Accounts</a></li>
				<li class="nav-item"><a class="nav-link"
					href="view-transactions"><i class="fas fa-exchange-alt me-2"></i>All
						Transactions</a></li>
				<li class="nav-item"><a class="nav-link" href="manage-loans"><i
						class="fas fa-handshake me-2"></i>Manage Loans</a></li>
				<li class="nav-item"><a class="nav-link"
					href="manage-complaints"><i class="fas fa-comments me-2"></i>Complaints</a></li>
				<li class="nav-item"><a class="nav-link active" href="reports"><i
						class="fas fa-chart-bar me-2"></i>Reports</a></li>
				<li class="nav-item mt-4"><a class="nav-link"
					href="<%=request.getContextPath()%>/logout"><i
						class="fas fa-sign-out-alt me-2"></i>Logout</a></li>
			</ul>
		</div>
	</nav>

	<div class="main-content">
		<h3>Reports Overview</h3>
		<hr />
		<%
		if (reports == null || reports.isEmpty()) {
		%>
		<div class="alert alert-info">No reports available.</div>
		<%
		} else {
		%>
		<div class="table-responsive">
			<table class="table table-bordered table-hover">
				<thead class="table-dark">
					<tr>
						<th>Report ID</th>
						<th>Title</th>
						<th>Description</th>
						<th>Created At</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (Report report : reports) {
					%>
					<tr>
						<td><%=report.getReportId()%></td>
						<td><%=report.getTitle()%></td>
						<td><%=report.getDescription()%></td>
						<td><%=report.getCreatedAt()%></td>
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
