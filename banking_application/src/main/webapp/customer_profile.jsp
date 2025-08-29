<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.User"%>
<%@ page import="com.aurionpro.model.Customer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Profile - Aurionpro Bank</title>
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
	margin: 0;
}

.sidebar {
	background: linear-gradient(180deg, #28a745 0%, #20c997 100%);
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
	color: #e6f7f1;
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
	margin: 2px 8px;
}

.sidebar ul li a.active, .sidebar ul li a:hover {
	color: #28a745;
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

.card {
	border: none;
	border-radius: 15px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<%
	User user = (User) session.getAttribute("user");
	Customer customer = (Customer) request.getAttribute("customer");

	String successMessage = (String) session.getAttribute("success");
	String errorMessage = (String) session.getAttribute("error");
	if (successMessage != null)
		session.removeAttribute("success");
	if (errorMessage != null)
		session.removeAttribute("error");
	%>

	<!-- Customer Sidebar -->
	<nav class="sidebar">
		<div class="icon">
			<i class="fas fa-university"></i>
		</div>
		<div class="title">Aurionpro Bank</div>
		<div class="subtitle">Customer Portal</div>
		<ul>
			<li><a href="dashboard"><i class="fas fa-tachometer-alt"></i>
					Dashboard</a></li>
			<li><a href="profile" class="active"><i class="fas fa-user"></i>
					My Profile</a></li>
			<li><a href="accounts"><i class="fas fa-piggy-bank"></i> My
					Accounts</a></li>
			<li><a href="transactions"><i class="fas fa-exchange-alt"></i>
					Transactions</a></li>
			<li><a href="beneficiaries"><i class="fas fa-users"></i>
					Beneficiaries</a></li>
			<li><a href="deposit"><i class="fas fa-plus-circle"></i>
					Deposit</a></li>
			<li><a href="withdraw"><i class="fas fa-minus-circle"></i>
					Withdraw</a></li>
			<li><a href="apply-loan"><i class="fas fa-handshake"></i>
					Apply for Loan</a></li>
			<li><a href="loans"><i class="fas fa-file-contract"></i> My
					Loans</a></li>
			<li><a href="<%=request.getContextPath()%>/logout"><i
					class="fas fa-sign-out-alt"></i> Logout</a></li>
		</ul>
	</nav>

	<!-- Main Content -->
	<div class="main-content">
		<!-- Header -->
		<div class="card">
			<div class="card-body">
				<h2>
					<i class="fas fa-user me-2 text-primary"></i>My Profile
				</h2>
				<p class="text-muted mb-0">Manage your personal information</p>
			</div>
		</div>

		<!-- Alerts -->
		<%
		if (successMessage != null) {
		%>
		<div class="alert alert-success"><%=successMessage%></div>
		<%
		}
		%>
		<%
		if (errorMessage != null) {
		%>
		<div class="alert alert-danger"><%=errorMessage%></div>
		<%
		}
		%>

		<!-- Profile Form -->
		<div class="card">
			<div class="card-header">
				<h5>
					<i class="fas fa-edit me-2"></i>Personal Information
				</h5>
			</div>
			<div class="card-body">
				<form method="post">
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="firstName" class="form-label">First Name</label> <input
								type="text" class="form-control" id="firstName" name="firstName"
								value="<%=customer != null ? customer.getFirstName() : ""%>"
								required>
						</div>
						<div class="col-md-6 mb-3">
							<label for="lastName" class="form-label">Last Name</label> <input
								type="text" class="form-control" id="lastName" name="lastName"
								value="<%=customer != null ? customer.getLastName() : ""%>"
								required>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="email" class="form-label">Email</label> <input
								type="email" class="form-control" id="email" name="email"
								value="<%=customer != null ? customer.getEmail() : ""%>"
								required>
						</div>
						<div class="col-md-6 mb-3">
							<label for="phone" class="form-label">Phone</label> <input
								type="tel" class="form-control" id="phone" name="phone"
								value="<%=customer != null ? customer.getPhone() : ""%>"
								required>
						</div>
					</div>
					<div class="mb-3">
						<label for="address" class="form-label">Address</label>
						<textarea class="form-control" id="address" name="address"
							rows="3" required><%=customer != null ? customer.getAddress() : ""%></textarea>
					</div>
					<div class="mb-3">
						<label class="form-label">Username</label> <input type="text"
							class="form-control"
							value="<%=user != null ? user.getUsername() : ""%>" readonly>
						<small class="text-muted">Username cannot be changed</small>
					</div>
					<button type="submit" class="btn btn-primary">
						<i class="fas fa-save me-2"></i>Update Profile
					</button>
					<a href="dashboard" class="btn btn-secondary ms-2"> <i
						class="fas fa-times me-2"></i>Cancel
					</a>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
