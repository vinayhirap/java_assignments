<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Profile Management - Aurionpro Bank</title>
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
}

.profile-avatar {
	width: 120px;
	height: 120px;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	font-size: 3rem;
}
</style>
</head>
<body>
	<%
	Customer customer = (Customer) session.getAttribute("customer");
	User user = (User) session.getAttribute("user");
	if (customer == null || user == null) {
		response.sendRedirect("../login");
		return;
	}
	%>

	<!-- Sidebar (same as dashboard) -->
	<nav class="sidebar">
		<div class="p-4">
			<div class="text-center text-white mb-4">
				<i class="fas fa-university fa-2x mb-3"></i>
				<h5>Aurionpro Bank</h5>
				<small>Customer Portal</small>
			</div>
		</div>

		<ul class="nav nav-pills flex-column">
			<li class="nav-item"><a class="nav-link" href="dashboard"> <i
					class="fas fa-tachometer-alt me-2"></i>Dashboard
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
			<li class="nav-item"><a class="nav-link active" href="profile">
					<i class="fas fa-user-edit me-2"></i>Profile
			</a></li>
			<li class="nav-item mt-4"><a class="nav-link"
				href="<%=request.getContextPath()%>/logout"> <i
					class="fas fa-sign-out-alt me-2"></i>Logout
			</a></li>
		</ul>
	</nav>

	<!-- Main Content -->
	<div class="main-content">
		<div class="row">
			<!-- Profile Overview -->
			<div class="col-md-4 mb-4">
				<div class="card">
					<div class="card-body text-center">
						<div class="profile-avatar mx-auto mb-3">
							<i class="fas fa-user"></i>
						</div>
						<h5><%=customer.getFullName()%></h5>
						<p class="text-muted">
							Customer ID:
							<%=customer.getCustomerId()%></p>
						<p class="text-muted">
							Username:
							<%=user.getUsername()%></p>
						<span class="badge bg-success">Active Account</span>
					</div>
				</div>

				<div class="card mt-3">
					<div class="card-header">
						<h6>
							<i class="fas fa-shield-alt me-2"></i>Security Settings
						</h6>
					</div>
					<div class="card-body">
						<div class="d-grid gap-2">
							<button class="btn btn-outline-primary"
								onclick="changePassword()">
								<i class="fas fa-key me-2"></i>Change Password
							</button>
							<button class="btn btn-outline-warning">
								<i class="fas fa-mobile-alt me-2"></i>Update Mobile
							</button>
							<button class="btn btn-outline-info">
								<i class="fas fa-envelope me-2"></i>Update Email
							</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Profile Edit Form -->
			<div class="col-md-8">
				<div class="card">
					<div class="card-header">
						<h5>
							<i class="fas fa-edit me-2"></i>Edit Profile Information
						</h5>
					</div>
					<div class="card-body">
						<%
						if (request.getAttribute("error") != null) {
						%>
						<div class="alert alert-danger">
							<i class="fas fa-exclamation-triangle me-2"></i>
							<%=request.getAttribute("error")%>
						</div>
						<%
						}
						%>

						<%
						if (request.getAttribute("success") != null) {
						%>
						<div class="alert alert-success">
							<i class="fas fa-check-circle me-2"></i>
							<%=request.getAttribute("success")%>
						</div>
						<%
						}
						%>

						<form method="post" action="profile">
							<div class="row">
								<div class="col-md-6 mb-3">
									<label class="form-label"> <i class="fas fa-user me-2"></i>First
										Name *
									</label> <input type="text" class="form-control" name="firstName"
										value="<%=customer.getFirstName()%>" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label"> <i class="fas fa-user me-2"></i>Last
										Name *
									</label> <input type="text" class="form-control" name="lastName"
										value="<%=customer.getLastName()%>" required>
								</div>
							</div>

							<div class="row">
								<div class="col-md-6 mb-3">
									<label class="form-label"> <i
										class="fas fa-envelope me-2"></i>Email Address *
									</label> <input type="email" class="form-control" name="email"
										value="<%=customer.getEmail()%>" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label"> <i class="fas fa-phone me-2"></i>Phone
										Number *
									</label> <input type="tel" class="form-control" name="phone"
										value="<%=customer.getPhone()%>" required>
								</div>
							</div>

							<div class="mb-3">
								<label class="form-label"> <i
									class="fas fa-map-marker-alt me-2"></i>Address *
								</label>
								<textarea class="form-control" name="address" rows="3" required><%=customer.getAddress()%></textarea>
							</div>

							<div class="row">
								<div class="col-md-6 mb-3">
									<label class="form-label"> <i
										class="fas fa-calendar me-2"></i>Date of Birth
									</label> <input type="date" class="form-control" name="dateOfBirth"
										value="<%=customer.getDateOfBirth() != null ? customer.getDateOfBirth().toString() : ""%>">
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label"> <i
										class="fas fa-id-card me-2"></i>PAN Number
									</label> <input type="text" class="form-control" name="panNumber"
										value="<%=customer.getPanNumber() != null ? customer.getPanNumber() : ""%>"
										placeholder="ABCDE1234F" maxlength="10">
								</div>
							</div>

							<div class="mb-4">
								<label class="form-label"> <i
									class="fas fa-id-badge me-2"></i>Aadhar Number
								</label> <input type="text" class="form-control" name="aadharNumber"
									value="<%=customer.getAadharNumber() != null ? customer.getAadharNumber() : ""%>"
									placeholder="1234 5678 9012" maxlength="12">
							</div>

							<div class="d-grid gap-2">
								<button type="submit" class="btn btn-primary btn-lg">
									<i class="fas fa-save me-2"></i>Update Profile
								</button>
								<a href="dashboard" class="btn btn-outline-secondary"> <i
									class="fas fa-arrow-left me-2"></i>Back to Dashboard
								</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Change Password Modal -->
	<div class="modal fade" id="changePasswordModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Change Password</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<form method="post" action="change-password">
					<div class="modal-body">
						<div class="mb-3">
							<label class="form-label">Current Password</label> <input
								type="password" class="form-control" name="currentPassword"
								required>
						</div>
						<div class="mb-3">
							<label class="form-label">New Password</label> <input
								type="password" class="form-control" name="newPassword" required
								minlength="6">
						</div>
						<div class="mb-3">
							<label class="form-label">Confirm New Password</label> <input
								type="password" class="form-control" name="confirmPassword"
								required minlength="6">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancel</button>
						<button type="submit" class="btn btn-primary">Change
							Password</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		function changePassword() {
			const modal = new bootstrap.Modal(document
					.getElementById('changePasswordModal'));
			modal.show();
		}
	</script>
</body>
</html>