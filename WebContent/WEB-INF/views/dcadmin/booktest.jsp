<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<%@ include file="scripts.jsp"%>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<link rel="stylesheet" type="text/css" href="./css/booktest.css">
	<div align="center">
		<div class="container">
			<div class="col-md-9">
				<center>
					<form class="shadow-form">
						<h1>Book Test</h1>


						<div class="row mt-4">
							<div class="col-md-6 offset-md-3">
								<div class="text-center">
									<h8>Categories select</h8>
									<select class="form-control" id="category" onchange="getTest()">

										<option value="main">All Categories</option>
										<!-- Add more category options as needed -->
									</select>
								</div>
							</div>
						</div>

						<div class="row mt-4">
							<div class="col-md-6 offset-md-3">
								<div class="text-center">
									<h8>Tests select</h8>
									<select class="form-control" id="test" onchange="updatePrice()">
										<option value="main">All tests</option>
										<!-- Add more test options as needed -->
									</select>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="tprice" class="form-label">Test Price</label> <input
								type="text" name="tprice" id="tprice" class="form-control"
								required readonly>
						</div>
						

						<div class="row mt-4">
							<div class="col-md-6 offset-md-3">
								<div class="text-center">
									<h8>Patient select</h8>
									<input type="text" class="form-control" id="searchInput"
										oninput="filterOptions()" placeholder="Search..."> <select
										class="form-control" id="patient">
										<option value="main">All Patients</option>
										<!-- Add more category options as needed -->
									</select>
								</div>
			<div class="form-group">
    <label for="page" class="form-label">Patient Age</label>
    <input type="text" name="page" id="page" value="" class="form-control" oninput="validateAge()" required>
    <div id="ageError" class="error-msg"></div>
</div>

							</div>
							
						</div>

						<br>
						


					</form>
					<center>
						<button onclick="preview()">Book</button>
					</center>
					
					

				</center>
				<div class="modal fade" id="previewModal" tabindex="-1"
					role="dialog" aria-labelledby="previewModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="previewModalLabel">Preview
									Booking</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="bookingDetails">
								<!-- Dynamically populated with booking details -->
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Cancel</button>
								<button type="button" class="btn btn-primary"
									onclick="booktest()" data-dismiss="modal">Confirm
									Booking</button>
							</div>
						</div>
					</div>
				</div>
				
				
					
					<div class="modal fade" id="previewModal8" tabindex="-1"
					role="dialog" aria-labelledby="previewModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="previewModalLabel">Duplicate Test</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="bookingDetails8">
								<!-- Dynamically populated with booking details -->
							</div>
							
						</div>
					</div>
				</div>
				
				

				<div class="modal fade" id="previewModal2" tabindex="-1"
					role="dialog" aria-labelledby="previewModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="previewModalLabel">Invalid</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="bookingDetails2">
								<!-- Dynamically populated with booking details -->
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Ok</button>

							</div>
						</div>
					</div>
				</div>
				<!-- Add this code inside the <div class="container"> ... </div> block -->





	 			
			</div>

		</div>
	</div>

	<script src="js/booktest.js">
		
	</script>

</body>
</html>