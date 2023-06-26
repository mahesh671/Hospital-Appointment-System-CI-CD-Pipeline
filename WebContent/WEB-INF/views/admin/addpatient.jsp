<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
   
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-image: url('https://images.shiksha.com/mediadata/images/articles/1567401387phpphiMNK.jpeg');
             background-repeat: no-repeat;
            background-size: cover;
            background-position: center center;
            height: 100vh;
        }

        .container {
            max-width: 500px;
            margin: auto;
            padding: 20px;
            background-color: white;
            border-radius: 5px;
            margin-top: 100px;
        }
        h1 {
            font-family: Arial, sans-serif;
            font-size: 32px;
            font-weight: bold;
            text-align: center;
            color: #333;
        }
    </style>
</head>
<body>
    <div class="container">
        <form action="addpat" method="post">
         <center>
  
    <h1>Details for Appointment Booking</h1></center>
            <div class="form-group">
                <label for="patient_name" class="form-label"> Name</label>
                <input type="text" name="pname" id="pname" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="patient_age" class="form-label">Age</label>
                <input type="number" name="page" id="page" class="form-control" required>
            </div>
            <div class="form-group">
    <label for="patient_gender" class="form-label">Gender</label>
    <div>
        <input type="radio" name="gender" id="male" value="male" required>
        <label for="male">Male</label>
    </div>
    <div>
        <input type="radio" name="gender" id="female" value="female" required>
        <label for="female">Female</label>
    </div>
</div>

<div class="form-group">
    <label for="patient_blood" class="form-label">Blood Group</label>
    <div>
        <input type="radio" name="bloodGroup" id="groupA" value="A+" required>
        <label for="groupA">A+</label>
    
        <input type="radio" name="bloodGroup" id="groupB" value="B+" required>
        <label for="groupB">B+</label>
   
        <input type="radio" name="bloodGroup" id="groupAB" value="AB+" required>
        <label for="groupAB">AB+</label>
    
        <input type="radio" name="bloodGroup" id="groupO" value="O+" required>
        <label for="groupO">O+</label>
  
        <input type="radio" name="bloodGroup" id="groupA" value="A-" required>
        <label for="groupA">A-</label>
    
        <input type="radio" name="bloodGroup" id="groupB" value="B-" required>
        <label for="groupB">B-</label>
   
        <input type="radio" name="bloodGroup" id="groupAB" value="AB-" required>
        <label for="groupAB">AB-</label>
    
        <input type="radio" name="bloodGroup" id="groupO" value="O-" required>
        <label for="groupO">O-</label>
    </div>
</div>
<div class="form-group">
    <label for="patient_date" class="form-label">Registered Date</label>
    <input type="date" id="patient_date" name="rod" class="form-control" required>
</div>
<div class="form-group">
                <label for="specialization" class="form-label">Specialization</label>
                <select name="specialization" id="specialization" class="form-control" required>
                    <option value="">Select Specialization</option>
                    <option value="1">Specialization 1</option>
                    <option value="2">Specialization 2</option>
                    <option value="3">Specialization 3</option>
                    <!-- Add more options for different specializations -->
                </select>
            </div>

            <div class="form-group">
                <label for="doctor" class="form-label">Doctor</label>
                <select name="doctor" id="doctor" class="form-control" required>
                    <option value="">Select Doctor</option>
                    <!-- Add options dynamically using JavaScript based on selected specialization -->
                </select>
            </div>



           

            <button type="submit" class="btn btn-primary btn-block">Book </button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script>
function fun1(){
	
	$.ajax({
	//	url:'FromToTrainServlet',
		url:'MailController',
		method:'POST',
		data:{
		status:status
		},
		success: function(resultText){
			$('#results').html(resultText);
			$('#resultss').html(resultText);
			
		},
		error:function(jqXHR,exception){
			console.log('Error Occured!');
		}
	})
}</script>
</body>
</html>
