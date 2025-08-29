<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Apply for Loan</title>
</head>
<body>
	<h2>Loan Application</h2>
	<form method="post">
		<label>Loan Amount: <input type="number" step="0.01"
			name="amount" required></label><br> <label>Loan Term
			(months): <input type="number" name="term" required>
		</label><br> <label>Purpose: <textarea name="purpose" required></textarea></label><br>
		<button type="submit">Apply</button>
	</form>
	<c:if test="${not empty success}">
		<div style="color: green">${success}</div>
	</c:if>
	<c:if test="${not empty error}">
		<div style="color: red">${error}</div>
	</c:if>
	<a href="dashboard">Back to Dashboard</a>
</body>
</html>
