<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Appointment Details</title>
<!-- Add Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body style="background-color: #f4f4f4;">
	<div class="container"
		style="background-color: #fff; padding: 20px; border-radius: 5px;">
		<h1 style="color: #007bff; text-align: center;">Appointment
			Details</h1>
		<table class="table table-bordered">

			<tbody>
				<tr>
					<th>Appointment ID</th>
					<td>${appointment.appn_id}</td>
				</tr>
				<tr>
					<th>Doctor</th>
					<td>${appointment.doc_name}</td>
				</tr>
				<tr>
					<th>Patient</th>
					<td>${appointment.pat_name}</td>
				</tr>
				<tr>
					<th>Booked Date</th>
					<td>${appointment.appn_booked_Date}</td>
				</tr>
				<tr>
					<th>Scheduled Date</th>
					<td>${appointment.appn_sch_date}</td>
				</tr>
				<tr>
					<th>Payment Mode</th>
					<td>${appointment.appn_paymode}</td>
				</tr>
				<tr>
					<th>Payment Reference</th>
					<td>${appointment.appn_payreference}</td>
				</tr>
				<tr>
					<th>Payment Amount</th>
					<td>${appointment.appn_payamount}</td>
				</tr>
				
			</tbody>


		</table>
	</div>
	<!-- Add Bootstrap JS (optional) -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
