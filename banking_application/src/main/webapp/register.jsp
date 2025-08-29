<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Register - Aurionpro Bank</title>
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

.form-container {
	max-width: 600px;
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
	background: #007bff;
	border: none;
	width: 100%;
	padding: 12px;
	font-weight: 600;
	border-radius: 30px;
	transition: background-color 0.3s ease;
}

.btn-submit:hover {
	background: #0056b3;
	color: white;
}

.info-note {
	text-align: center;
	margin-top: 20px;
	color: rgba(0, 0, 0, 0.6);
	font-size: 0.9rem;
}

label {
	font-weight: 600;
}

.input-group-text {
	cursor: pointer;
}

.form-text {
	font-size: 0.92rem;
}
</style>
</head>
<body>

	<div class="form-container">
		<h2>Create an Account</h2>

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

		<form action="register" method="post"
			onsubmit="return validateForm();">
			<div class="row g-3">
				<div class="col-md-6">
					<label for="firstName" class="form-label"><i
						class="fas fa-user me-2"></i>First Name</label> <input type="text"
						class="form-control" id="firstName" name="firstName"
						placeholder="Enter first name" required />
				</div>
				<div class="col-md-6">
					<label for="lastName" class="form-label"><i
						class="fas fa-user me-2"></i>Last Name</label> <input type="text"
						class="form-control" id="lastName" name="lastName"
						placeholder="Enter last name" required />
				</div>
				<div class="col-md-6">
					<label for="username" class="form-label"><i
						class="fas fa-at me-2"></i>Username</label> <input type="text"
						class="form-control" id="username" name="username"
						placeholder="Choose a username" required />
				</div>
				<div class="col-md-6">
					<label for="email" class="form-label"><i
						class="fas fa-envelope me-2"></i>Email</label> <input type="email"
						class="form-control" id="email" name="email"
						placeholder="Enter email address" required /> <small
						id="emailHelp" class="form-text text-danger d-none">Please
						enter a valid email (must have '.' after '@')</small>
				</div>
				<div class="col-md-6">
					<label for="phone" class="form-label"><i
						class="fas fa-phone me-2"></i>Phone Number</label> <input type="tel"
						class="form-control" id="phone" name="phone"
						placeholder="Enter phone number" required maxlength="10" /> <small
						id="phoneHelp" class="form-text text-danger d-none">Please
						enter a valid 10-digit mobile number</small>
				</div>
				<div class="col-md-6">
					<label for="address" class="form-label"><i
						class="fas fa-map-marker-alt me-2"></i>Address</label>
					<textarea class="form-control" id="address" name="address" rows="3"
						placeholder="Enter complete address" required></textarea>
				</div>
				<div class="col-md-6">
					<label for="password" class="form-label"><i
						class="fas fa-lock me-2"></i>Password</label>
					<div class="input-group">
						<input type="password" class="form-control" id="password"
							name="password" placeholder="Create a password" required /> <span
							class="input-group-text" onclick="togglePassword('password');"><i
							class="fas fa-eye" id="showPass1"></i></span>
					</div>
				</div>
				<div class="col-md-6">
					<label for="retypePassword" class="form-label">Retype
						Password</label>
					<div class="input-group">
						<input type="password" class="form-control" id="retypePassword"
							name="retypePassword" placeholder="Retype password" required />
						<span class="input-group-text"
							onclick="togglePassword('retypePassword');"><i
							class="fas fa-eye" id="showPass2"></i></span>
					</div>
					<small id="passwordHelp" class="form-text text-danger d-none">Passwords
						do not match</small>
				</div>
			</div>

			<button type="submit" class="btn btn-submit mt-4">
				<i class="fas fa-user-plus me-2"></i>Create Account
			</button>
		</form>

		<div class="text-center mt-4">
			<p class="text-muted mb-2">Already have an account?</p>
			<a href="login.jsp" class="btn btn-outline-primary btn-sm"><i
				class="fas fa-sign-in-alt me-2"></i>Login Here</a>
		</div>

		<div class="info-note">
			<i class="fas fa-info-circle me-1"></i>Your account will be activated
			after admin approval.
		</div>
	</div>

	<%@ include file="footer.jsp"%>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		function togglePassword(fieldId) {
			const field = document.getElementById(fieldId);
			const icon = field.nextElementSibling.querySelector('i');
			if (field.type === "password") {
				field.type = "text";
				icon.classList.remove("fa-eye");
				icon.classList.add("fa-eye-slash");
			} else {
				field.type = "password";
				icon.classList.remove("fa-eye-slash");
				icon.classList.add("fa-eye");
			}
		}

		function validateEmail(email) {
			const atIndex = email.indexOf('@');
			const dotIndex = email.indexOf('.', atIndex + 2); // must be at least two chars after '@'
			return atIndex > 0 && dotIndex > atIndex;
		}

		function validatePhone(phone) {
			const re = /^[6-9]\d{9}$/; // for Indian mobile numbers
			return re.test(phone);
		}

		function validateForm() {
			let valid = true;

			// Email validation
			const email = document.getElementById("email").value.trim();
			if (!validateEmail(email)) {
				document.getElementById("emailHelp").classList.remove("d-none");
				valid = false;
			} else {
				document.getElementById("emailHelp").classList.add("d-none");
			}

			// Phone validation
			const phone = document.getElementById("phone").value.trim();
			if (!validatePhone(phone)) {
				document.getElementById("phoneHelp").classList.remove("d-none");
				valid = false;
			} else {
				document.getElementById("phoneHelp").classList.add("d-none");
			}

			// Password matching validation
			const password = document.getElementById("password").value;
			const retypePassword = document.getElementById("retypePassword").value;
			if (password !== retypePassword) {
				document.getElementById("passwordHelp").classList
						.remove("d-none");
				valid = false;
			} else {
				document.getElementById("passwordHelp").classList.add("d-none");
			}

			return valid;
		}
	</script>
</body>
</html>
