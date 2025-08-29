<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Withdraw Money</title>
</head>
<body>
	<h2>Withdraw Funds</h2>
	<form method="post">
		<label>Account ID: <input type="number" name="accountId"
			required></label><br> <label>Amount: <input
			type="number" step="0.01" name="amount" required></label><br> <label>Description:
			<textarea name="description"></textarea>
		</label><br>
		<button type="submit">Withdraw</button>
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
