<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<script>


	function dashboard() {
		console.log("click");
		$.ajax({
			url : "/AppointmentForm/admin/dashboard",
			success : function(result) {
				$("#disp").html(result);
			}
		});
	}
	
	function getpatientprofile()
	{
		$.ajax({
			url : "/AppointmentForm/admin/getpatient",
			success : function(result) {
				$("#disp").html(result);
			}
		});
	}
	function bookedapp()
	{
		$.ajax({
			url : "/AppointmentForm/admin/dashboard",
			success : function(result) {
				$("#disp").html(result);
			}
		});
	}
	function doctor() {
		console.log("hello");
		$.ajax({
			url : "../admin/doctors",
			success : function(result) {
				$("#disp").html(result);
			}
		});
	}
	function specialization() {
		console.log("click");
		$.ajax({
			url : "/AppointmentForm/admin/specialization",
			success : function(result) {
				$("#disp").html(result);
			}
		});

	}
	
	function getbookedapp() 
	{
		$.ajax({
			url : "/AppointmentForm/admin/getbookapp",
			success : function(result) {
				$("#disp").html(result);
			}
		});
	}
	function getnewapp() 
	{
		$.ajax({
			url : "/AppointmentForm/admin/getnewbookapp",
			success : function(result) {
				$("#disp").html(result);
			}
		});
		
	}
	
	function getpayments() 
	{
		$.ajax({
			url : "/AppointmentForm/admin/getpaymets",
			success : function(result) {
				$("#disp").html(result);
			}
		});
	}
	
	function getreports() 
	{
		$.ajax({
			url : "/AppointmentForm/admin/getreports",
			success : function(result) {
				$("#disp").html(result);
			}
		});
	}
</script>
<style>
a {
	color: #000000;
	text-decoration: none;
	background-color: transparent;
}

.nav a:hover {
	color: #078344;
	text-decoration: none;
	border-style: solid;
	background-color: transparent;
}

.container {
	padding: 0px;
	margin: 0px;
}

.table {
	border: 1px solid #ccc;
	margin-top: 3px;
	margin-bottom: 3px;
}

.table th, .table td {
	border: 1px solid #ccc;
	padding: 2px;
}

.nav-link dropdown-toggle {
	color: #078344;
	text-decoration: none;
	border-style: solid;
	background-color: transparent;
}

.table thead th {
	background-color: #f0f0f0;
}

.card-container {
	white-space: nowrap;
	width: auto;
}

.card {
	display: inline-block;
	width: 300px;
	margin-right: 10px;
	margin-top: 10px;
}
</style>
</head>

<body>


<jsp:include page="nav.jsp" />
	<div>

		<div class="container">
			
				<div id="disp"></div>
			</div>
		</div>


	

	

</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link  href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">



</html>