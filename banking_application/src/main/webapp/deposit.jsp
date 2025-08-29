<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Deposit Money - Aurionpro Bank</title>
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

.card {
	border: none;
	border-radius: 15px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
}

.deposit-card {
	background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
	color: white;
}
</style>
</head>
<body>
	<%
	Customer customer = (Customer) session.getAttribute("customer");
	if (customer == null) {
		response.sendRedirect("../login");
		return;
	}
	List<Account> accounts = (List<Account>) request.getAttribute("accounts");
	%>

	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card">
					<div class="card-header deposit-card">
						<h4>
							<i class="fas fa-plus-circle me-2"></i>Deposit Money
						</h4>
						<p class="mb-0">Add money to your account</p>
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

						<form method="post" action="deposit">
							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="accountId" class="form-label"> <i
										class="fas fa-university me-2"></i>Deposit To Account *
									</label> <select class="form-select" id="accountId" name="accountId"
										required>
										<option value="">Select Account</option>
										<%
										if (accounts != null) {
											for (Account account : accounts) {
										%>
										<option value="<%=account.getAccountId()%>">
											<%=account.getAccountType()%> -
											<%=account.getMaskedAccountNumber()%> (₹<%=account.getBalance()%>)
										</option>
										<%
										}
										}
										%>
									</select>
								</div>

								<div class="col-md-6 mb-3">
									<label for="amount" class="form-label"> <i
										class="fas fa-rupee-sign me-2"></i>Amount *
									</label> <input type="number" class="form-control" id="amount"
										name="amount" placeholder="Enter amount" min="1" step="0.01"
										required>
								</div>
							</div>

							<div class="mb-3">
								<label for="description" class="form-label"> <i
									class="fas fa-comment me-2"></i>Description (Optional)
								</label>
								<textarea class="form-control" id="description"
									name="description" rows="3"
									placeholder="Enter deposit purpose or remarks"></textarea>
							</div>

							<div class="d-grid gap-2">
								<button type="submit" class="btn btn-success btn-lg">
									<i class="fas fa-plus-circle me-2"></i>Deposit Money
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

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>