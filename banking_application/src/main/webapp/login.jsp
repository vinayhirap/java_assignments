<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Login - Aurionpro Bank</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.form-container {
	max-width: 400px;
	margin: 60px auto 80px auto;
	background: white;
	padding: 30px 25px;
	border-radius: 15px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

h2 {
	margin-bottom: 25px;
	color: #dc3545;
	font-weight: 700;
	text-align: center;
}

.btn-submit {
	background: #28a745;
	border: none;
	width: 100%;
	padding: 12px;
	font-weight: 600;
	border-radius: 30px;
	transition: background-color 0.3s ease;
}

.btn-submit:hover {
	background: #1e7e34;
	color: white;
}

.security-note {
	text-align: center;
	margin-top: 20px;
	color: rgba(0, 0, 0, 0.6);
	font-size: 0.9rem;
}
</style>
</head>
<body>

	<div class="form-container">
		<h2>Login to Your Account</h2>

		<%
		if (request.getAttribute("error") != null) {
		%>
		<div class="alert alert-danger alert-dismissible fade show"
			role="alert">
			<i class="fas fa-exclamation-triangle me-2"></i>
			<%=request.getAttribute("error")%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
		</div>
		<%
		}
		if (request.getAttribute("success") != null) {
		%>
		<div class="alert alert-success alert-dismissible fade show"
			role="alert">
			<i class="fas fa-check-circle me-2"></i>
			<%=request.getAttribute("success")%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
		</div>
		<%
		}
		%>

		<form action="login" method="post">
			<div class="mb-3">
				<label for="username" class="form-label">Username or Email</label> <input
					type="text" class="form-control" id="username" name="username"
					placeholder="Enter your username or email" required />
			</div>
			<div class="mb-4">
				<label for="password" class="form-label">Password</label> <input
					type="password" class="form-control" id="password" name="password"
					placeholder="Enter your password" required />
			</div>
			<button type="submit" class="btn btn-submit">Login</button>
		</form>

		<div class="text-center mt-4">
			<p class="text-muted mb-2">Don't have an account?</p>
			<a href="register.jsp" class="btn btn-outline-primary btn-sm"><i
				class="fas fa-user-plus me-2"></i>Create Account</a>
		</div>

		<div class="security-note">
			<i class="fas fa-shield-alt me-1"></i> Your data is protected with
			bank-level security
		</div>
	</div>

	<%@ include file="footer.jsp"%>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
