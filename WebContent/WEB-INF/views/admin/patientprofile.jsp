<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient Profile</title>

<jsp:include page="../admin/scripts.jsp" />
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 550px;
	margin: 0 auto;
	padding: 10px;
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}

h3 {
	text-align: center;
	margin-bottom: 16px;
}

label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
}

input[type="text"], input[type="tel"], textarea {
	width: 100%;
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 2px;
	box-sizing: border-box;
	margin-bottom: 5px;
}

textarea {
	resize: vertical;
}

button[type="submit"] {
	background-color: #4CAF50;
	color: #fff;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

button[type="submit"]:hover {
	background-color: #45a049;
}
</style>

</head>
<body>

	<jsp:include page="nav.jsp" />

	<div class="container">
		<h3>Patient Profile Update Form</h3>
		

		<form id="profileUpdateForm" enctype="multipart/form-data" action = "./getpatientprofile" method="post"  modelAttribute="ProfileUpdateForm">
			<div class="form-group">
				
					
					Patient ID: <select class="form-control" id="patientId" name="patientId" onchange="getappn()">
                                <option value="main">All Patients Id</option>
                                <!-- Add more category options as needed -->
                            </select>
			</div>
			<div class="form-group">
			Appointment ID: <select class="form-control" id="appnId" name="Appnid">
                                <option value="main">All Appointments</option>
                                <!-- Add more category options as needed -->
                            </select>
			</div>
			<div class="form-group">
				<label for=" parameter">Parameter:</label> <input type="text"
					id=" parameter" name=" parameter" required>
			</div>
			<div class="form-group">
				<label for="patgroup">Parameter Group:</label> <input type="text"
					id="patgroup" name="Patgroup"  required>
			</div>
			<div class="form-group">
				<label for="value">Value:</label> <input type="text"
					id="value" name="value" required>
			</div>
	
			
			<div class="form-group">
				<label for="reportsInput">Upload Prescriptions:</label> <input type="file"
					id="reportsInput" name="reportsInput" accept="image/*">
			</div>
			<div class="form-group">
				<button type="submit">Update Profile</button>
			</div>
		</form>
	</div>
<script type="text/javascript">

$(document).ready(function() {
	
		
		
		$.ajax({
			url : './getpatientid',
			type : 'GET',
			success : function(response) {
				var data = response;
				console.log(data);
				  var dropdown = $('#patientId');
	                dropdown.empty(); // Clear existing options
	                
	                // Add new options based on the data
	                $.each(data, function(index, value) {
	                    dropdown.append($('<option></option>').attr('value', value).text(value));
	                }); 
			}
		});

	});



function getappn() {
    var patientId = document.getElementById("patientId").value;
console.log(patientId);
    $.ajax({
        url: "./getpatientbyid",
        type: "POST",
        data: {
        	patientId : patientId
        },
        success: function(response) {
        	var data = response;
			console.log(data);
			  var dropdown = $('#appnId');
                dropdown.empty(); // Clear existing options
                
                // Add new options based on the data
                $.each(data, function(index, value) {
                    dropdown.append($('<option></option>').attr('value', value).text(value));
                }); 
            
        },
        error: function(xhr, status, error) {
            console.log("Error: " + error);
        }
    });
}
</script>
</body>
</html>
