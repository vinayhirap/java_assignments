<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Create New Bank Account</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body>
	<div class="container mt-5">
		<h2>Create New Bank Account</h2>

		<!-- Success and error messages -->
		<c:if test="${not empty success}">
			<div class="alert alert-success">${success}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<!-- Account creation form -->
		<form method="post" action="create-account">
			<div class="mb-3">
				<label for="accountType" class="form-label">Account Type</label> <select
					name="accountType" id="accountType" class="form-select" required>
					<option value="" disabled selected>Select Account Type</option>
					<option value="SAVINGS">Savings</option>
					<option value="CURRENT">Current</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="initialDeposit" class="form-label">Initial
					Deposit Amount</label> <input type="number" step="0.01" min="0"
					name="initialDeposit" id="initialDeposit" class="form-control"
					required />
			</div>
			<button type="submit" class="btn btn-primary">Create Account</button>
			<a href="dashboard" class="btn btn-secondary ms-2">Cancel</a>
		</form>
	</div>
</body>
</html>
