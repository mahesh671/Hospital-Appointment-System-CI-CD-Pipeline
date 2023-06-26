<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cancel Appointment</title>
	<jsp:include page="scripts.jsp" />
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div align="center">
		<h1>Cancel Appointment</h1>

		<form action="cancelAppointment.jsp" method="post">
			<div>
				<label for="appointmentId">Appointment ID:</label>
				<input type="text" id="appointmentId" name="appointmentId" required>
			</div>

			<div>
				<label for="reason">Reason for Cancellation:</label>
				<textarea id="reason" name="reason" required></textarea>
			</div>

			<button type="submit">Cancel Appointment</button>
		</form>
	</div>
</body>
</html>
