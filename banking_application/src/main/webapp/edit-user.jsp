<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Edit User - Aurionpro Bank Admin</title>
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
	max-width: 600px;
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

.form-label {
	font-weight: 600;
}
</style>
</head>
<body>
	<%
	User editUser = (User) request.getAttribute("editUser");
	%>
	<div class="container">
		<h2>Edit User Details</h2>

		<%
		if (request.getAttribute("error") != null) {
		%>
		<div class="alert alert-danger"><%=request.getAttribute("error")%></div>
		<%
		}
		%>

		<form method="post" action="edit-user">
			<input type="hidden" name="userId"
				value="<%=editUser != null ? editUser.getUserId() : ""%>" />

			<div class="mb-3">
				<label for="username" class="form-label">Username</label> <input
					type="text" id="username" name="username" class="form-control"
					value="<%=editUser != null ? editUser.getUsername() : ""%>"
					required />
			</div>

			<div class="mb-3">
				<label for="firstName" class="form-label">First Name</label> <input
					type="text" id="firstName" name="firstName" class="form-control"
					value="<%=editUser != null ? editUser.getFirstName() : ""%>"
					required />
			</div>

			<div class="mb-3">
				<label for="lastName" class="form-label">Last Name</label> <input
					type="text" id="lastName" name="lastName" class="form-control"
					value="<%=editUser != null ? editUser.getLastName() : ""%>"
					required />
			</div>

			<div class="mb-3">
				<label for="email" class="form-label">Email</label> <input
					type="email" id="email" name="email" class="form-control"
					value="<%=editUser != null ? editUser.getEmail() : ""%>" required />
			</div>

			<div class="mb-3">
				<label for="phone" class="form-label">Phone</label> <input
					type="text" id="phone" name="phone" class="form-control"
					value="<%=editUser != null ? editUser.getPhone() : ""%>" />
			</div>

			<div class="mb-3">
				<label for="address" class="form-label">Address</label>
				<textarea id="address" name="address" class="form-control" rows="3"><%=editUser != null ? editUser.getAddress() : ""%></textarea>
			</div>

			<button type="submit" class="btn btn-primary w-100">Update
				User</button>
			<a href="manage-users" class="btn btn-secondary w-100 mt-2">Cancel</a>
		</form>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
