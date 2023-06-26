<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*, spring.orm.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="scripts.jsp" />
<title>Home</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="row mt-4">
		<div class="col-md-6 offset-md-3">
			<div class="text-center">
				<h5>Doctor Wise</h5>
				<select class="form-control" id="docFilter"
					onchange="getdocwisepay()">
					<option value=0>All Doctors</option>
					<c:forEach var="d" items="${docs}">
						<option value="${d.id}" id="${d.id}">${d.name}</option>

						<!-- Your other HTML code within the iteration -->

					</c:forEach>


					<!-- Add more test options as needed -->
				</select>
			</div>
		</div>
	</div>

	<div class="row mt-4">
		<div class="col-md-6 offset-md-3">
			<div class="text-center">
				<h5>Specialization Wise</h5>
				<select class="form-control" id="specFilter"
					onchange="RevenueSplit()">
					<option value="null">All Specializations</option>
					<c:forEach var="s" items="${specs}">
						<option value="${s.id}" id="${d.id}">${s.title}</option>

						<!-- Your other HTML code within the iteration -->

					</c:forEach>


					<!-- Add more test options as needed -->
				</select>
			</div>
		</div>
	</div>
	<div class="row mt-4">
		<div class="col-md-6 offset-md-3">
			<h5>Between Dates</h5>
			<div class="row">
				<div class="col-md-6">
					<input type="date" class="form-control" id="startDateFilter">
				</div>
				<div class="col-md-6">
					<input type="date" class="form-control" id="endDateFilter"
						onchange="getdatewisepay()">
				</div>
			</div>
		</div>
	</div>
	<div class="row mt-4">
		<div class="col-md-12">
			<table class="table table-bordered" id="patientTable">
				<thead>
					<tr>
						<th>Doctor/Specialization</th>

						<th>Doctor Pay</th>
						<th>Profit</th>
					</tr>
				</thead>
				<tbody id="patientTableBody">


				</tbody>
			</table>
		</div>
	</div>
	</div>


	</script>

	<script>
		function RevenueSplit() {
			// Make an AJAX request to call the controller method
			console.log("in spec");
			var spec = document.getElementById("specFilter").value;
			// var speci=document.getElementById(spec).textContent;

			var d1 = document.getElementById("startDateFilter").value;
			var d2 = document.getElementById("endDateFilter").value;
			var amount = 0;
			var count = 1;
			var profit = 0;
			var tamount = 0;
			if (d1 && d2) {
				getspecdatewisepay();
			} else {
				console.log(spec);
				console.log("inside revenue");
				$
						.ajax({
							url : "./revenueSplit", // Specify the URL of the controller method
							type : "POST", // Use POST or GET depending on your server-side implementation
							data : {
								spec : spec
							}, // Pass the patientId as data to the server
							success : function(response) {
								var data = response;

								// Iterate over the data and create table rows
								data
										.forEach(function(appointment) {
											tamount = amount
													+ (appointment.appn_payamount);
											var totalam = tamount * count;

											amount = amount
													+ (appointment.appn_payamount / 10);
											console.log(amount);
											count = count + 1;

											docpay = amount;
											profit = totalam - docpay;

										});
								var data = response;
								var tableBody = $('#patientTableBody');

								// Clear any existing table rows
								tableBody.empty();
								var newRow = $('<tr>');
								newRow.append($('<td>').text(spec));
								newRow.append($('<td>').text("null"));
								newRow.append($('<td>').text(profit));
								tableBody.append(newRow);

							},
							error : function(xhr, status, error) {
								console.error("Error: " + error);
							}
						});
			}
		}
		function getdocwisepay() {
			console.log("doc wise");
			var doc = document.getElementById("docFilter").value;
			var doci = document.getElementById("docFilter").value;
			console.log(doci);
			var d1 = document.getElementById("startDateFilter").value;
			var d2 = document.getElementById("endDateFilter").value;
			if (d1 && d2) {
				getdocdatewisepay();
			} else {
				console.log("sppec");
				$
						.ajax({

							url : "./getdocwisepay",
							method : "POST",
							data : {
								doc : doc
							},
							success : function(response) {
								var data = response;
								console.log(data);
								var tableBody = $('#patientTableBody');
								var amount = 0;
								var count = 1;
								var docpay = 0;
								var profit = 0;
								var tamount = 0;
								// Clear any existing table rows
								tableBody.empty();

								// Iterate over the data and create table rows
								
										data.forEach(function(appointment) {
											tamount = amount
													+ (appointment.appn_payamount);
											var totalam = tamount * count;

											amount = amount
													+ (appointment.appn_payamount / 10);
											console.log(amount);
											count = count + 1;

											docpay = amount;
											profit = totalam - docpay;

										});
								var newRow = $('<tr>');

								newRow.append($('<td>').text(doci));
								newRow.append($('<td>').text(docpay));
								newRow.append($('<td>').text(profit));
								tableBody.append(newRow);

								// Create the button element

							},
							error : function(xhr, status, error) {
								console.error("Error: " + error);
							}
						});
			}
		}

		function getspecdatewisepay() {
			var spec = document.getElementById("specFilter").value;
			var speci = document.getElementById("specFilter").value;
			var d1 = document.getElementById("startDateFilter").value;
			var d2 = document.getElementById("endDateFilter").value;
			var amount = 0;
			var count = 1;
			var profit = 0;
			var tamount = 0;
			console.log("sppec");
			$.ajax({

				url : "./getspecdatewisepay",
				method : "POST",
				data : {
					spec : spec,
					dat : d1,
					date : d2
				},
				success : function(response) {
					var data = response;
					var tableBody = $('#patientTableBody');

					// Clear any existing table rows
					tableBody.empty();

					// Iterate over the data and create table rows
					data.forEach(function(appointment) {
						tamount = amount + (appointment.appn_payamount);
						var totalam = tamount * count;

						amount = amount + (appointment.appn_payamount / 10);
						console.log(amount);
						count = count + 1;

						docpay = amount;
						profit = totalam - docpay;

					});
					var newRow = $('<tr>');
					newRow.append($('<td>').text(speci));
					newRow.append($('<td>').text("null"));
					newRow.append($('<td>').text(profit));
					tableBody.append(newRow);
				},
				error : function(xhr, status, error) {
					console.error("Error: " + error);
				}
			});
		}
		function getdatewisepay() {
			var doc = document.getElementById("docFilter").value;
			var d1 = document.getElementById("startDateFilter").value;
			var d2 = document.getElementById("endDateFilter").value;
			var spec = document.getElementById("specFilter").value;
			console.log(d1);

			console.log(d1);
			if (doc > 0) {
				getdocdatewisepay();
			} else if (spec != "null") {
				console.log(spec);
				getspecdatewisepay();
			} else {
				console.log("sppec");
				$
						.ajax({

							url : "./getdatewisepay",
							method : "POST",
							data : {
								dat : d1,
								date : d2
							},
							success : function(response) {
								console.log(response);
								var data = response;
								console.log(data);
								var tableBody = $('#patientTableBody');
								var amount = 0;
								var profit = 0;
								var count = 1;
								var tamount = 0;
								// Clear any existing table rows
								tableBody.empty();

								// Iterate over the data and create table rows
								data
										.forEach(function(appointment) {
											tamount = amount
													+ (appointment.appn_payamount);
											var totalam = tamount * count;

											amount = amount
													+ (appointment.appn_payamount / 10);
											console.log(amount);
											count = count + 1;

											docpay = amount;
											profit = totalam - docpay;

										});
								var newRow = $('<tr>');

								newRow.append($('<td>').text("All Doctors"));
								newRow.append($('<td>').text("null"));
								newRow.append($('<td>').text(profit));
								tableBody.append(newRow);

							},
							error : function(xhr, status, error) {
								console.error("Error: " + error);
							}
						});
			}
		}
		function getdocdatewisepay() {
			var doc = document.getElementById("docFilter").value;
			var d1 = document.getElementById("startDateFilter").value;
			var d2 = document.getElementById("endDateFilter").value;
			var amount = 0;
			var count = 1;
			var docpay = 0;
			var profit = 0;
			var tamount = 0;
			console.log("indocdate");
			$.ajax({

				url : "./getdocdatewisepay",
				method : "POST",
				data : {
					doc : doc,
					dat : d1,
					date : d2
				},
				success : function(response) {
					var data = response;
					var tableBody = $('#patientTableBody');

					// Clear any existing table rows
					tableBody.empty();

					// Iterate over the data and create table rows
					data.forEach(function(appointment) {
						tamount = amount + (appointment.appn_payamount);
						var totalam = tamount * count;

						amount = amount + (appointment.appn_payamount / 10);
						console.log(amount);
						count = count + 1;

						docpay = amount;
						profit = totalam - docpay;

					});
					var newRow = $('<tr>');
					newRow.append($('<td>').text(doc));
					newRow.append($('<td>').text(docpay));
					newRow.append($('<td>').text(profit));

					tableBody.append(newRow);

				},
				error : function(xhr, status, error) {
					console.error("Error: " + error);
				}
			});
		}
	</script>

</body>
</html>