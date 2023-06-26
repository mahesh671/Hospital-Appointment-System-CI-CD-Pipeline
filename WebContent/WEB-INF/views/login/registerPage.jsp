<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<jsp:include page="scripts.jsp" />
</head>
<body>

	<div class="container text-center">
		<h4 class="mb-4">Register</h4>
		<form action="saveregister" method="post"
			onsubmit="return validateForm()">
			<div class="form-group">
				<input type="hidden" name="role" value="Patient"> <input
					type="text" class="form-control" value="Patient" readonly hidden>
			</div>
			<div class="form-group">
				<label for="uname" class="form-label">Username</label> <input
					type="text" name="uname" id="uname" class="form-control" required>
				<span id="usernameValidationMsg"></span>
			</div>

			<div class="form-group">
				<label for="name" class="form-label">Name</label> <input type="text"
					name="name" id="name" class="form-control" required>
			</div>
			<div class="form-group">
				<label for="age" class="form-label">Age</label> <input type="number"
					name="age" id="age" class="form-control" required>
			</div>
			<div class="form-group">
				<label for="gmail" class="form-label">Email</label> <input
					type="email" name="gmail" id="gmail" class="form-control" required>
			</div>
			<div class="form-group">
				<label for="gender" class="form-label">Gender</label> <select
					name="gender" id="gender" class="form-control" required>
					<option value="">Select Gender</option>
					<option value="Male">Male</option>
					<option value="Female">Female</option>
					<option value="Other">Other</option>
				</select>
			</div>
			<div class="form-group">
				<label for="pass" class="form-label">Password</label> <input
					type="password" name="pass" id="pass" class="form-control" required>
			</div>

			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>

</body>
<script>
$(document).ready(function() {
    $('#uname').on('blur', function() {
        var username = $(this).val();
        if(username==""||username==" "){
        	$('#usernameValidationMsg').text('Please Enter valid Username.');
        }
        else{
        $.ajax({
            url: './checkUsernameAvailability',
            type: 'GET',
            data: { username: username },
            success: function(response) {
                if (response === 'available') {
                    $('#usernameValidationMsg').text('Username is available.');
                } else {
                    $('#usernameValidationMsg').text('Username is already taken.');
                }
            },
            error: function() {
                $('#usernameValidationMsg').text('Error occurred while checking username availability.');
            }
        });
        }
    });
});
function validateForm() {
    var uname = $('#uname').val();
    var name = $('#name').val();
    var age = $('#age').val();
    var gmail = $('#gmail').val();
    var gender = $('#gender').val();
    var pass = $('#pass').val();

    // Perform your validation checks here
    if (uname.trim() == "") {
        alert('Please enter a valid Username.');
        return false;
    }
    if (name.trim() == "") {
        alert('Please enter a valid Name.');
        return false;
    }
    if (age.trim() == "" || isNaN(age)) {
        alert('Please enter a valid Age.');
        return false;
    }
    if (gmail.trim() == "") {
        alert('Please enter a valid Email.');
        return false;
    }
    if (gender == "") {
        alert('Please select a valid Gender.');
        return false;
    }
    if (pass.trim() == "") {
        alert('Please enter a valid Password.');
        return false;
    }

    return true;
}
</script>
</html>