<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>User Details - Aurionpro Bank Admin</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	padding: 30px;
}

.container {
	max-width: 700px;
	background: white;
	border-radius: 15px;
	padding: 30px;
	box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

h2 {
	color: #dc3545;
	margin-bottom: 25px;
	font-weight: 700;
	text-align: center;
}

.data-row {
	margin-bottom: 15px;
}

.label {
	font-weight: 600;
	color: #555;
}

.value {
	color: #222;
}

.btn-back {
	margin-top: 30px;
	display: block;
	width: 100%;
}
</style>
</head>
<body>
	<%
	User viewUser = (User) request.getAttribute("viewUser");
	if (viewUser == null) {
	%>
	<div class="alert alert-danger text-center">No user details
		available.</div>
	<%
	} else {
	%>
	<div class="container">
		<h2>User Details</h2>
		<div class="data-row">
			<span class="label">User ID: </span> <span class="value"><%=viewUser.getUserId()%></span>
		</div>
		<div class="data-row">
			<span class="label">Username: </span> <span class="value"><%=viewUser.getUsername()%></span>
		</div>
		<div class="data-row">
			<span class="label">First Name: </span> <span class="value"><%=viewUser.getFirstName()%></span>
		</div>
		<div class="data-row">
			<span class="label">Last Name: </span> <span class="value"><%=viewUser.getLastName()%></span>
		</div>
		<div class="data-row">
			<span class="label">Email: </span> <span class="value"><%=viewUser.getEmail()%></span>
		</div>
		<div class="data-row">
			<span class="label">Phone: </span> <span class="value"><%=viewUser.getPhone()%></span>
		</div>
		<div class="data-row">
			<span class="label">Address: </span> <span class="value"><%=viewUser.getAddress()%></span>
		</div>
		<div class="data-row">
			<span class="label">User Type: </span> <span class="value"><%=viewUser.getUserType()%></span>
		</div>

		<a href="manage-users" class="btn btn-primary btn-back">Back to
			Manage Users</a>
	</div>
	<%
	}
	%>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
