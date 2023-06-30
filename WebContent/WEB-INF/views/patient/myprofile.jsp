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
			<form action="./saveFamilyMember" method="post">
			
			    <div class="form-group" style="display:none;">
					<label for="patId" class="form-label">Patient Name</label> <input
						type="text" name="pfmbPatnId" id="pfmbPatnId" class="form-control"
						value="${family.pfmbPatnId}">
				</div>
			    

				<div class="form-group">
					<label for="patnName" class="form-label">Patient Name</label> <input
						type="text" name="pfmbName" id="pfmbName" class="form-control"
						value="${family.pfmbName}">
				</div>

				<div class="form-group">
					<label for="patnAge" class="form-label">Age</label> <input
						type="number" name="pfmbAge" id="pfmbAge" class="form-control"
						value="${family.pfmbAge}">
				</div>

				<div class="form-group">
					<label for="patnGender" class="form-label">Gender</label> <select
						name="pfmbGender" id="pfmbGender" class="form-control">
						<option value="Male"
							${family.pfmbGender.toString() == 'Male' ? 'selected' : ''}>Male</option>
						<option value="Female"
							${family.pfmbGender.toString() == 'Female' ? 'selected' : ''}>Female</option>
					</select>
				</div>
				
				<div class="form-group">
					<label for="patnBrelation" class="form-label">Relation</label> <input
						type="text" name="pfmbRelation" id="pfmbRelation" class="form-control"
						value="${family.pfmbRelation}">
				</div>



				<div class="form-group">
					<label for="patnBgroup" class="form-label">Blood Group</label> <input
						type="text" name="pfmbbgroup" id="pfmbbgroup" class="form-control"
						value="${family.pfmbbgroup}">
				</div>


				<button type="submit" class="btn btn-primary btn-block">Save</button>
			</form>
		</div>
	</div>
</body>
</html>
