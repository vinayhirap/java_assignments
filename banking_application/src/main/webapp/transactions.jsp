<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transaction History - Aurionpro Bank</title>
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

.transaction-card {
	transition: transform 0.2s ease;
}

.transaction-card:hover {
	transform: translateY(-2px);
}

.credit {
	border-left: 4px solid #28a745;
}

.debit {
	border-left: 4px solid #dc3545;
}

.transfer {
	border-left: 4px solid #007bff;
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

	List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
	String accountFilter = request.getParameter("account");
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
			<li class="nav-item"><a class="nav-link active"
				href="transactions"> <i class="fas fa-list-alt me-2"></i>Transactions
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
		<!-- Header -->
		<div class="card mb-4">
			<div class="card-body">
				<div class="row align-items-center">
					<div class="col-md-6">
						<h4>
							<i class="fas fa-history me-2"></i>Transaction History
						</h4>
						<p class="text-muted mb-0">View all your transaction details</p>
					</div>
					<div class="col-md-6 text-end">
						<button class="btn btn-outline-primary me-2"
							onclick="exportTransactions()">
							<i class="fas fa-download me-2"></i>Export PDF
						</button>
						<button class="btn btn-primary" onclick="printStatement()">
							<i class="fas fa-print me-2"></i>Print Statement
						</button>
					</div>
				</div>
			</div>
		</div>

		<!-- Filters -->
		<div class="card mb-4">
			<div class="card-header">
				<h6>
					<i class="fas fa-filter me-2"></i>Filter Transactions
				</h6>
			</div>
			<div class="card-body">
				<form method="get" action="transactions">
					<div class="row">
						<div class="col-md-3 mb-3">
							<label class="form-label">Date From</label> <input type="date"
								class="form-control" name="dateFrom"
								value="<%=request.getParameter("dateFrom") != null ? request.getParameter("dateFrom") : ""%>">
						</div>
						<div class="col-md-3 mb-3">
							<label class="form-label">Date To</label> <input type="date"
								class="form-control" name="dateTo"
								value="<%=request.getParameter("dateTo") != null ? request.getParameter("dateTo") : ""%>">
						</div>
						<div class="col-md-3 mb-3">
							<label class="form-label">Transaction Type</label> <select
								class="form-select" name="type">
								<option value="">All Types</option>
								<option value="DEPOSIT"
									<%="DEPOSIT".equals(request.getParameter("type")) ? "selected" : ""%>>Deposit</option>
								<option value="WITHDRAWAL"
									<%="WITHDRAWAL".equals(request.getParameter("type")) ? "selected" : ""%>>Withdrawal</option>
								<option value="TRANSFER"
									<%="TRANSFER".equals(request.getParameter("type")) ? "selected" : ""%>>Transfer</option>
							</select>
						</div>
						<div class="col-md-3 mb-3">
							<label class="form-label">&nbsp;</label>
							<div>
								<button type="submit" class="btn btn-primary">
									<i class="fas fa-search me-2"></i>Filter
								</button>
								<a href="transactions" class="btn btn-outline-secondary ms-2">Clear</a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>

		<!-- Transactions List -->
		<div class="row">
			<%
			if (transactions != null && !transactions.isEmpty()) {
			%>
			<%
			for (Transaction transaction : transactions) {
				String cardClass = "";
				String iconClass = "";
				String amountClass = "";

				switch (transaction.getTransactionType().name()) {
					case "DEPOSIT" :
				cardClass = "credit";
				iconClass = "fas fa-plus-circle text-success";
				amountClass = "text-success";
				break;
					case "WITHDRAWAL" :
				cardClass = "debit";
				iconClass = "fas fa-minus-circle text-danger";
				amountClass = "text-danger";
				break;
					case "TRANSFER" :
				cardClass = "transfer";
				iconClass = "fas fa-exchange-alt text-primary";
				amountClass = "text-primary";
				break;
					default :
				cardClass = "transfer";
				iconClass = "fas fa-circle text-info";
				amountClass = "text-info";
				}
			%>
			<div class="col-12 mb-3">
				<div class="card transaction-card <%=cardClass%>">
					<div class="card-body">
						<div class="row align-items-center">
							<div class="col-md-1 text-center">
								<i class="<%=iconClass%> fa-2x"></i>
							</div>
							<div class="col-md-3">
								<h6 class="mb-1"><%=transaction.getTransactionType()%></h6>
								<small class="text-muted"> <%=java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
		.format(transaction.getTransactionDate().toLocalDateTime())%>
								</small>
							</div>
							<div class="col-md-4">
								<p class="mb-1"><%=transaction.getDescription()%></p>
								<small class="text-muted">Ref: <%=transaction.getReferenceNumber()%></small>
							</div>
							<div class="col-md-2 text-center">
								<h5 class="mb-0 <%=amountClass%>">
									<%=transaction.getTransactionType().name().equals("WITHDRAWAL") ? "-" : "+"%>₹<%=transaction.getAmount()%>
								</h5>
							</div>
							<div class="col-md-2 text-end">
								<p class="mb-0 fw-bold">
									Balance: ₹<%=transaction.getBalanceAfter()%></p>
								<span
									class="badge bg-<%=transaction.getStatus().name().equals("COMPLETED") ? "success" : "warning"%>">
									<%=transaction.getStatus()%>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>

			<!-- Pagination -->
			<div class="col-12">
				<nav>
					<ul class="pagination justify-content-center">
						<li class="page-item"><a class="page-link" href="#">Previous</a>
						</li>
						<li class="page-item active"><a class="page-link" href="#">1</a>
						</li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">Next</a>
						</li>
					</ul>
				</nav>
			</div>
			<%
			} else {
			%>
			<div class="col-12">
				<div class="card">
					<div class="card-body text-center py-5">
						<i class="fas fa-receipt fa-5x text-muted mb-4"></i>
						<h4 class="text-muted">No Transactions Found</h4>
						<p class="text-muted">You don't have any transactions yet or
							no transactions match your filter criteria.</p>
						<a href="dashboard" class="btn btn-primary"> <i
							class="fas fa-arrow-left me-2"></i>Back to Dashboard
						</a>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		function exportTransactions() {
			alert('Export feature will be implemented soon!');
		}

		function printStatement() {
			window.print();
		}
	</script>
</body>
</html>