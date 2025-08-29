<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Aurionpro Bank - Welcome</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet" />
<style>
body, html {
	margin: 0;
	padding: 0;
	height: 100%;
	background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

main {
	padding: 60px 20px;
	max-width: 1100px;
	margin: auto;
}

.hero-section {
	text-align: center;
	padding: 40px 20px;
}

.hero-title {
	font-size: 3rem;
	color: #dc3545;
	font-weight: 700;
}

.hero-subtitle {
	font-size: 1.3rem;
	color: #444;
	max-width: 700px;
	margin: 20px auto 40px;
}

.btn-primary {
	background: #007bff;
	border: none;
	font-size: 1.1rem;
	padding: 12px 35px;
	border-radius: 50px;
	font-weight: 600;
	box-shadow: 0 4px 12px rgba(0, 123, 255, 0.4);
	transition: background 0.3s ease;
}

.btn-primary:hover {
	background: #0056b3;
}

.btn-secondary {
	background: #28a745;
	border: none;
	font-size: 1.1rem;
	padding: 12px 35px;
	border-radius: 50px;
	font-weight: 600;
	box-shadow: 0 4px 12px rgba(40, 167, 69, 0.4);
	transition: background 0.3s ease;
}

.btn-secondary:hover {
	background: #1e7e34;
}

.features {
	display: flex;
	justify-content: space-around;
	margin-top: 60px;
	gap: 25px;
	flex-wrap: wrap;
}

.feature-card {
	background: white;
	border-radius: 15px;
	padding: 30px 20px;
	max-width: 320px;
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
	transition: transform 0.3s ease;
	cursor: default;
}

.feature-card:hover {
	transform: translateY(-10px);
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.feature-icon {
	font-size: 3.5rem;
	color: #dc3545;
	margin-bottom: 25px;
}

.feature-title {
	font-size: 1.5rem;
	margin-bottom: 15px;
	font-weight: 700;
}

.feature-desc {
	color: #666;
	font-size: 1rem;
	line-height: 1.5;
}
</style>
</head>
<body>

	<main>
		<section class="hero-section">
			<h1 class="hero-title">Welcome to Aurionpro Bank</h1>
			<p class="hero-subtitle">Your trusted partner for secure and
				effortless banking. Explore our services designed to empower your
				financial life.</p>
			<a href="register.jsp" class="btn btn-primary me-3">Register</a> <a
				href="login.jsp" class="btn btn-secondary">Login</a>
		</section>

		<section class="features">
			<div class="feature-card">
				<i class="fas fa-shield-alt feature-icon"></i>
				<h3 class="feature-title">Security Guaranteed</h3>
				<p class="feature-desc">World-class security protocols to
					safeguard your accounts and personal data.</p>
			</div>

			<div class="feature-card">
				<i class="fas fa-university feature-icon"></i>
				<h3 class="feature-title">Comprehensive Account Services</h3>
				<p class="feature-desc">Manage your savings, current accounts,
					fixed deposits efficiently with full online support.</p>
			</div>

			<div class="feature-card">
				<i class="fas fa-handshake feature-icon"></i>
				<h3 class="feature-title">Loan Management</h3>
				<p class="feature-desc">Apply, track, and manage your personal,
					home, car, and business loans seamlessly.</p>
			</div>
		</section>
	</main>

	<%@ include file="footer.jsp"%>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
