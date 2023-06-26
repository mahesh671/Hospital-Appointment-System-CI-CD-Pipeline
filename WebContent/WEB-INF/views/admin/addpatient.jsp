<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
   
<jsp:include page="./js/addpatientjs.jsp"></jsp:include>
<jsp:include page="./css/addpatientcss.jsp"></jsp:include>
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

</body>
</html>
