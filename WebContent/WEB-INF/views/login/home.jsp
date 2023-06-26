<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Doctor Appointment System</title>
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700"
	rel="stylesheet">
<style type="text/css">
body {
	color: #000;
	background: #f8f9fa;
	font-family: 'Roboto', sans-serif;
}

.form-control {
	height: 41px;
	border: 1px solid #ccc;
}

.form-control, .btn {
	border-radius: 3px;
}

.signup-form {
	width: 390px;
	margin: 30px auto;
}

.signup-form form {
	color: #333;
	border-radius: 3px;
	margin-bottom: 15px;
	background: #fff;
	box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
	padding: 30px;
}

.signup-form h2 {
	color: #333;
	font-weight: bold;
	margin-top: 0;
}

.signup-form hr {
	margin: 0 -30px 20px;
}

.signup-form input[type="checkbox"] {
	margin-top: 3px;
}

.signup-form .row div:first-child {
	padding-right: 10px;
}

.signup-form .row div:last-child {
	padding-left: 10px;
}

.signup-form .btn {
	font-size: 16px;
	font-weight: bold;
	border: none;
	min-width: 140px;
}

.signup-form .btn:hover, .signup-form .btn:focus {
	outline: none;
}

.signup-form a {
	color: #000;
	text-decoration: underline;
}

.signup-form a:hover {
	text-decoration: none;
}
form {
  flex-direction: column;
  justify-content: center;
}

.signup-form form a {
	color: #3598dc;
	text-decoration: none;
}

.signup-form form a:hover {
	text-decoration: underline;
}

.signup-form .hint-text {
	padding-bottom: 15px;
	text-align: center;
}

.news-container {
	margin: 50px auto;
	width: 600px;
	color: #000;
	text-align: center;
}

.news-container h2 {
	margin-bottom: 30px;
}

.about-container {
	margin: 50px auto;
	width: 600px;
	color: #000;
	text-align: center;
}

.about-container h2 {
	margin-bottom: 30px;
}
</style>
</head>
<body>

	<div class="container">
		<!-- Login Section -->
		<div class="signup-form">
			<c:if test="${not empty error}">
				<div class="alert alert-danger">${error}</div>
			</c:if>
			<form action="./login" method="post">
				<h2>Sign In</h2>
				<div class="form-group">
					<label for="username">Username</label> <input type="text"
						name="uname" class="form-control" id="uname"
						placeholder="Username" required="required">
				</div>
				<div class="form-group">
					<label for="password">Password</label> <input type="password"
						name="pass" class="form-control" id="pass" placeholder="Password"
						required="required">
				</div>
				<div class="form-group">
					<span id="login-error" style="color: red;"></span>
				</div>
				<div class="row">
					<div class="col-md-6">
						<button type="submit" class="btn btn-primary" id="btn">Login</button>
					</div>
					<div class="col-md-6">
						<button type="reset" class="btn btn-danger">Clear</button>
					</div>
				</div>
				<div style="margin-top: 20px; text-align: center;">
					<a href="forget" class="text-body">Forgot password?</a>
				</div>
				<div class="text-center text-lg-start mt-4 pt-2">
					<p class="small fw-bold mt-2 pt-1 mb-0">
						Don't have an account? <a href="register" class="link-danger">Register</a>
					</p>
				</div>
			</form>
		</div>
	</div>

</body>
</html>