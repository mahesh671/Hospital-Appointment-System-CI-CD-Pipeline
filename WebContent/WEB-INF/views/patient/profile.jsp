<%@ page import="java.lang.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DAS</title>
<jsp:include page="scripts.jsp" />
</head>
<body>
	<div>
		<jsp:include page="nav.jsp" />
		<div class="shadow p-3  bg-white rounded">
			<h4 class="">Patient Settings</h4>
			<form action="./saveSettings" method="post">

				<div class="form-group">
					<label for="patnName" class="form-label">Patient Name</label> <input
						type="text" name="patnName" id="patnName" class="form-control"
						value="${patient.patn_name}">
				</div>

				<div class="form-group">
					<label for="patnAge" class="form-label">Age</label> <input
						type="number" name="patnAge" id="patnAge" class="form-control"
						value="${patient.patn_age}">
				</div>

				<div class="form-group">
					<label for="patnGender" class="form-label">Gender</label> <select
						name="patnGender" id="patnGender" class="form-control">
						<option value="Male"
							${patient.patn_gender.toString() == 'Male' ? 'selected' : ''}>Male</option>
						<option value="Female"
							${patient.patn_gender.toString() == 'Female' ? 'selected' : ''}>Female</option>
					</select>
				</div>



				<div class="form-group">
					<label for="patnBgroup" class="form-label">Blood Group</label> <input
						type="text" name="patnBgroup" id="patnBgroup" class="form-control"
						value="${patient.patn_bgroup}">
				</div>

				<div class="form-group">
					<label for="patnRdate" class="form-label">Registration Date</label>
					<input type="date" name="patnRdate" id="patnRdate"
						class="form-control"
						value="${patient.patn_rdate}" readonly>
				</div>

				<div class="form-group">
					<label for="patnLastVisit" class="form-label">Last Visit
						Date</label> <input type="date" name="patnLastVisit" id="patnLastVisit"
						class="form-control"
						value="${patient.patn_lastvisit}" readonly>
				</div>

				<div class="form-group">
					<label for="patnLastAppId" class="form-label">Last
						Appointment ID</label> <input type="text" name="patnLastAppId"
						id="patnLastAppId" class="form-control"
						value="${patient.patn_lastapp_id}" readonly>
				</div>

				<button type="submit" class="btn btn-primary btn-block">Save</button>
			</form>
		</div>
	</div>
</body>
</html>
