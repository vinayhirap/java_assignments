<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Fund Transfer - Aurionpro Bank</title>
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

.form-control, .form-select {
	border-radius: 10px;
	border: 2px solid #e0e6ed;
	padding: 12px 15px;
}

.form-control:focus, .form-select:focus {
	border-color: #667eea;
	box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
}

.btn-transfer {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	border: none;
	border-radius: 25px;
	padding: 12px 30px;
	color: white;
	font-weight: 600;
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
	List<Beneficiary> beneficiaries = (List<Beneficiary>) request.getAttribute("beneficiaries");
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
			<li class="nav-item"><a class="nav-link active" href="transfer">
					<i class="fas fa-exchange-alt me-2"></i>Fund Transfer
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
			<li class="nav-item"><a class="nav-link" href="profile"> <i
					class="fas fa-user-edit me-2"></i>Profile
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
			<!-- Transfer Form -->
			<div class="col-md-8">
				<div class="card">
					<div class="card-header bg-primary text-white">
						<h5>
							<i class="fas fa-exchange-alt me-2"></i>Fund Transfer
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

						<form method="post" action="transfer" id="transferForm">
							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="fromAccount" class="form-label"> <i
										class="fas fa-university me-2"></i>From Account *
									</label> <select class="form-select" id="fromAccount"
										name="fromAccount" required>
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
									<label for="transferType" class="form-label"> <i
										class="fas fa-route me-2"></i>Transfer Type *
									</label> <select class="form-select" id="transferType"
										name="transferType" required onchange="toggleTransferFields()">
										<option value="">Select Type</option>
										<option value="beneficiary">To Beneficiary</option>
										<option value="account">To Account Number</option>
										<option value="own">Between My Accounts</option>
									</select>
								</div>
							</div>

							<!-- Beneficiary Selection -->
							<div class="row" id="beneficiarySection" style="display: none;">
								<div class="col-12 mb-3">
									<label for="beneficiaryId" class="form-label"> <i
										class="fas fa-user me-2"></i>Select Beneficiary
									</label> <select class="form-select" id="beneficiaryId"
										name="beneficiaryId">
										<option value="">Choose Beneficiary</option>
										<%
										if (beneficiaries != null) {
											for (Beneficiary beneficiary : beneficiaries) {
										%>
										<option value="<%=beneficiary.getBeneficiaryId()%>">
											<%=beneficiary.getNickname()%> -
											<%=beneficiary.getMaskedAccountNumber()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
							</div>

							<!-- Account Number Input -->
							<div class="row" id="accountSection" style="display: none;">
								<div class="col-md-6 mb-3">
									<label for="toAccount" class="form-label"> <i
										class="fas fa-credit-card me-2"></i>To Account Number
									</label> <input type="text" class="form-control" id="toAccount"
										name="toAccount" placeholder="Enter account number">
								</div>
								<div class="col-md-6 mb-3">
									<label for="beneficiaryName" class="form-label"> <i
										class="fas fa-user me-2"></i>Beneficiary Name
									</label> <input type="text" class="form-control" id="beneficiaryName"
										name="beneficiaryName" placeholder="Enter beneficiary name">
								</div>
							</div>

							<!-- Own Accounts -->
							<div class="row" id="ownAccountSection" style="display: none;">
								<div class="col-12 mb-3">
									<label for="toOwnAccount" class="form-label"> <i
										class="fas fa-university me-2"></i>To My Account
									</label> <select class="form-select" id="toOwnAccount"
										name="toOwnAccount">
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
							</div>

							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="amount" class="form-label"> <i
										class="fas fa-rupee-sign me-2"></i>Amount *
									</label> <input type="number" class="form-control" id="amount"
										name="amount" placeholder="Enter amount" min="1" step="0.01"
										required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="transferMode" class="form-label"> <i
										class="fas fa-cogs me-2"></i>Transfer Mode
									</label> <select class="form-select" id="transferMode"
										name="transferMode">
										<option value="IMPS">IMPS - Immediate</option>
										<option value="NEFT">NEFT - 2 Hours</option>
										<option value="RTGS">RTGS - Real Time</option>
									</select>
								</div>
							</div>

							<div class="mb-4">
								<label for="description" class="form-label"> <i
									class="fas fa-comment me-2"></i>Transfer Description (Optional)
								</label>
								<textarea class="form-control" id="description"
									name="description" rows="3"
									placeholder="Enter transfer purpose or remarks"></textarea>
							</div>

							<div class="d-grid gap-2">
								<button type="button" class="btn btn-transfer btn-lg"
									onclick="confirmTransfer()">
									<i class="fas fa-paper-plane me-2"></i>Transfer Money
								</button>
								<a href="dashboard" class="btn btn-outline-secondary"> <i
									class="fas fa-arrow-left me-2"></i>Back to Dashboard
								</a>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- Transfer Info -->
			<div class="col-md-4">
				<div class="card">
					<div class="card-header">
						<h6>
							<i class="fas fa-info-circle me-2"></i>Transfer Information
						</h6>
					</div>
					<div class="card-body">
						<div class="alert alert-info">
							<small><strong>IMPS:</strong> Instant transfer (24x7)</small>
						</div>
						<div class="alert alert-warning">
							<small><strong>NEFT:</strong> Takes up to 2 hours</small>
						</div>
						<div class="alert alert-success">
							<small><strong>RTGS:</strong> Real-time (Min ₹2 Lakh)</small>
						</div>
					</div>
				</div>

				<div class="card mt-3">
					<div class="card-header">
						<h6>
							<i class="fas fa-shield-alt me-2"></i>Security Tips
						</h6>
					</div>
					<div class="card-body">
						<ul class="list-unstyled">
							<li><i class="fas fa-check text-success me-2"></i>Verify
								account details</li>
							<li><i class="fas fa-check text-success me-2"></i>Check
								transfer amount</li>
							<li><i class="fas fa-check text-success me-2"></i>Save
								transaction receipt</li>
							<li><i class="fas fa-check text-success me-2"></i>Never
								share OTP/PIN</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Confirmation Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title">Confirm Transfer</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12 mb-3">
							<h6>Transfer Details:</h6>
							<div id="transferSummary"></div>
						</div>
					</div>
					<div class="alert alert-warning">
						<i class="fas fa-exclamation-triangle me-2"></i> Please verify all
						details before proceeding. This action cannot be undone.
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary"
						onclick="submitTransfer()">Confirm Transfer</button>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		function toggleTransferFields() {
			const transferType = document.getElementById('transferType').value;

			document.getElementById('beneficiarySection').style.display = 'none';
			document.getElementById('accountSection').style.display = 'none';
			document.getElementById('ownAccountSection').style.display = 'none';

			if (transferType === 'beneficiary') {
				document.getElementById('beneficiarySection').style.display = 'block';
			} else if (transferType === 'account') {
				document.getElementById('accountSection').style.display = 'block';
			} else if (transferType === 'own') {
				document.getElementById('ownAccountSection').style.display = 'block';
			}
		}

		function confirmTransfer() {
			// Basic validation
			const form = document.getElementById('transferForm');
			if (!form.checkValidity()) {
				form.reportValidity();
				return;
			}

			// Show confirmation modal
			const modal = new bootstrap.Modal(document
					.getElementById('confirmModal'));

			// Populate summary
			const fromAccount = document.getElementById('fromAccount').selectedOptions[0].text;
			const amount = document.getElementById('amount').value;
			const transferType = document.getElementById('transferType').value;

			let summary = '<p><strong>From:</strong> ' + fromAccount + '</p>';
			summary += '<p><strong>Amount:</strong> ₹' + amount + '</p>';
			summary += '<p><strong>Type:</strong> ' + transferType + '</p>';

			document.getElementById('transferSummary').innerHTML = summary;
			modal.show();
		}

		function submitTransfer() {
			document.getElementById('transferForm').submit();
		}
	</script>
</body>
</html>