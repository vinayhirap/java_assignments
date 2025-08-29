<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Manage Users - Aurionpro Bank Admin</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet" />
<style>
body {
	background: linear-gradient(135deg, #f5f7fa 0%, #c3f3e2 100%);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	margin: 0;
}

.sidebar {
	background: linear-gradient(180deg, #dc3545 0%, #fd7e14 100%);
	min-height: 100vh;
	position: fixed;
	width: 250px;
	top: 0;
	left: 0;
	z-index: 1000;
	color: #fff;
	padding-top: 36px;
	display: flex;
	flex-direction: column;
	align-items: center;
}

.sidebar .title {
	font-size: 1.6rem;
	font-weight: 700;
	margin-bottom: 4px;
}

.sidebar .subtitle {
	font-size: 1rem;
	margin-bottom: 26px;
	color: #ffe6e0;
}

.sidebar .icon {
	font-size: 3rem;
	margin-bottom: 12px;
}

.sidebar ul {
	width: 100%;
	padding-left: 0;
	list-style: none;
}

.sidebar ul li {
	width: 100%;
}

.sidebar ul li a {
	display: flex;
	align-items: center;
	gap: 13px;
	color: rgba(255, 255, 255, 0.96);
	padding: 12px 18px;
	text-decoration: none;
	font-weight: 500;
	border-radius: 10px;
	transition: all 0.2s ease;
}

.sidebar ul li a.active, .sidebar ul li a:hover {
	color: #dc3545;
	background: #fff;
}

.sidebar ul li a i {
	min-width: 22px;
	font-size: 1.2rem;
}

.main-content {
	margin-left: 250px;
	padding: 20px;
	min-height: 100vh;
}

.status-badge {
	font-size: 0.85rem;
}

.action-btn {
	padding: 4px 8px;
	font-size: 0.85rem;
	border-radius: 5px;
	margin: 2px;
}

@media ( max-width : 768px) {
	.sidebar {
		position: relative;
		width: 100vw;
		min-height: auto;
		padding-top: 16px;
	}
	.main-content {
		margin-left: 0;
	}
}
</style>
</head>
<body>
	<%
	User admin = (User) session.getAttribute("user");
	if (admin == null || admin.getUserType() != User.UserType.ADMIN) {
		response.sendRedirect(request.getContextPath() + "/login");
		return;
	}
	%>
	

	<!-- Sidebar -->
	<nav class="sidebar">
		<div class="icon">
			<i class="fas fa-shield-alt"></i>
		</div>
		<div class="title">Aurionpro Bank</div>
		<div class="subtitle">Admin Portal</div>
		<ul>
			<li><a href="dashboard" class=""><i class="fas fa-chart-pie"></i>
					Dashboard</a></li>
			<li><a href="manage-users" class="active"><i
					class="fas fa-users-cog"></i> Manage Users</a></li>
			<li><a href="approve-users"><i class="fas fa-user-check"></i>
					Approve Users</a></li>
			<li><a href="manage-accounts"><i class="fas fa-piggy-bank"></i>
					Manage Accounts</a></li>
			<li><a href="view-transactions"><i
					class="fas fa-exchange-alt"></i> All Transactions</a></li>
			<li><a href="manage-loans"><i class="fas fa-handshake"></i>
					Manage Loans</a></li>
			<li><a href="manage-complaints"><i class="fas fa-comments"></i>
					Complaints</a></li>
			<li><a href="reports"><i class="fas fa-chart-bar"></i>
					Reports</a></li>
			<li><a href="<%=request.getContextPath()%>/logout"><i
					class="fas fa-sign-out-alt"></i> Logout</a></li>
		</ul>
	</nav>

	<!-- Main Content -->
	<div class="main-content">
		<div class="card mb-4">
			<div
				class="card-body d-flex justify-content-between align-items-center">
				<div>
					<h4>
						<i class="fas fa-users-cog me-2"></i>Manage Users
					</h4>
					<p class="text-muted mb-0">View and manage all customer
						accounts</p>
				</div>
				<div>
					<a href="export-users" class="btn btn-outline-primary"><i
						class="fas fa-download me-2"></i>Export</a>
					<button class="btn btn-primary" onclick="location.reload();">
						<i class="fas fa-sync me-2"></i>Refresh
					</button>
				</div>
			</div>
		</div>

		<c:if test="${not empty success}">
			<div class="alert alert-success">${success}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<c:choose>
			<c:when test="${not empty users}">
				<div class="card">
					<div
						class="card-header d-flex justify-content-between align-items-center">
						<h6>
							<i class="fas fa-table me-2"></i>All Users
						</h6>
						<span class="badge bg-primary">${fn:length(users)} users</span>
					</div>
					<div class="card-body p-0">
						<table class="table table-hover" id="usersTable">
							<thead class="table-dark">
								<tr>
									<th>ID</th>
									<th>Name</th>
									<th>Email</th>
									<th>Phone</th>
									<th>Username</th>
									<th>Status</th>
									<th>Registration Date</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="user" items="${users}">
									<tr>
										<td>${user.userId}</td>
										<td>
											<div class="d-flex align-items-center">
												<div
													class="bg-primary rounded-circle text-white d-flex align-items-center justify-content-center me-3"
													style="width: 40px; height: 40px;">
													<i class="fas fa-user"></i>
												</div>
												<strong>${user.firstName} ${user.lastName}</strong>
											</div>
										</td>
										<td>${user.email}</td>
										<td>${user.phone}</td>
										<td>${user.username}</td>
										<td><span
											class="badge
											${user.status == 'APPROVED' ? 'bg-success' : user.status == 'PENDING' ? 'bg-warning' : user.status == 'REJECTED' ? 'bg-danger' : 'bg-secondary'}">
												${user.status} </span></td>
										<td><c:choose>
												<c:when test="${not empty user.createdAt}">
												${fn:substring(user.createdAt, 0, 10)}
											</c:when>
												<c:otherwise>N/A</c:otherwise>
											</c:choose></td>
										<td>
											<div class="btn-group">
												<a href="view-user?userId=${user.userId}"
													class="btn btn-info btn-sm" title="View"><i
													class="fas fa-eye"></i></a> <a
													href="edit-user?userId=${user.userId}"
													class="btn btn-secondary btn-sm" title="Edit"><i
													class="fas fa-edit"></i></a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="text-center py-5">
					<i class="fas fa-users fa-5x text-muted"></i>
					<h4 class="text-muted">No Users Found</h4>
					<p class="text-muted">No customer records available.</p>
				</div>
			</c:otherwise>
		</c:choose>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

	<script>
		function refresh() {
			location.reload();
		}
	</script>

</body>
</html>
