<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
		
		<div class="row mt-4">
							<div class="col-md-6 offset-md-3">
								<div class="text-center">
									<h8>Patient select</h8>
									<input type="text" class="form-control" id="searchInput"
										oninput="filterOptions()" placeholder="Search..."> <select
										class="form-control" id="patient" onchange="fillPatientName()">
										<option value="main" >All Patients</option>
										<!-- Add more category options as needed -->
									</select>
								</div>
								<div class="form-group">
							<label for="pname" class="form-label">Patient Name</label> <input
								type="text" name="pname" id="pname" class="form-control"
								required>
						</div>

						

						<div class="form-group">
							<label class="form-label">Gender</label>
							<div>
								<input type="radio" name="gender" id="gender" value="male"
									required> <label for="male">Male</label>
							</div>
							<div>
								<input type="radio" name="gender" id="gender" value="female"
									required> <label for="female">Female</label>
							</div>
						</div>

						<div class="form-group">
    <label for="contact" class="form-label">Contact Number</label>
    <input type="text" name="contact" id="contact" value="" class="form-control" required>
    <div id="contactError" class="error-msg"></div>
</div>

<div class="form-group">
    <label for="email" class="form-label">Email</label>
    <input type="email" name="email" id="email" value="" class="form-control" required>
    <div id="emailError" class="error-msg"></div>
</div>
							</div>
							
						</div>
						<center>
						<button onclick="allTests()">Booked Tests</button>
						<br>
						<br>
						
					</center>
		
<br>
						<br>		
<div id="tableContainer" style="display: none;">
	<table class="table">
		<thead>
			<tr>
				<th>Test Name</th>
				<th>Price</th>
				<th>Booked Date</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody id="tableBody">
			<!-- Table rows will be dynamically populated here -->
		</tbody>
		
		
	</table>
</div>

				<div class="modal fade" id="previewModal1" tabindex="-1"
					role="dialog" aria-labelledby="previewModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="previewModalLabel">Booked
									Details</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="bookingDetails1">
								<!-- Dynamically populated with booking details -->
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Cancel</button>
								<button type="button" class="btn btn-primary"
									onclick="payment()" data-dismiss="modal">Make Payment</button>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" id="previewModal3" tabindex="-1"
					role="dialog" aria-labelledby="previewModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="previewModalLabel">Receipt</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="bookingDetails3">
								<!-- Dynamically populated with booking details -->
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal" onclick="window.print()">Print</button>
								<button type="button" class="btn btn-primary"
									onclick="downloadReceipt()">Download</button>
								<button type="button" class="btn btn-primary"
									onclick="sendReceiptByEmail()">Send Receipt via Email</button>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" id="previewModal4" tabindex="-1"
					role="dialog" aria-labelledby="previewModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="previewModalLabel">Payment Failed</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="bookingDetails4">
								<!-- Dynamically populated with booking details -->
							</div>
							
						</div>
					</div>
				</div>
				<div class="modal fade" id="previewModal7" tabindex="-1"
					role="dialog" aria-labelledby="previewModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="previewModalLabel">Failed to Send Mail</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="bookingDetails7">
								<!-- Dynamically populated with booking details -->
							</div>
							
						</div>
					</div>
				</div>
</div>
</div>
<script src="js/booktest.js">
		
	</script>
</body>
</html>