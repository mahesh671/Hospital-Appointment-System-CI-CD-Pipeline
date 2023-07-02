<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Set Password</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/reset.css">


</head>
<body>
<div class="container text-center">
    <h4 class="mb-4">Set Password</h4>
    <form action="passwordset" method="post">
    <div class="form-group">
            
            <input type="text" name="userName" id="uname" class="form-control" hidden value="${user.username }">
        </div>

        <div class="form-group">
            <label for="lnpass" class="form-label">New Password</label>
            <input type="password" name="newPassword" id="lnpass" class="form-control" required>
        </div>
        
        <div class="form-group">
            <label for="lcpass" class="form-label">Confirm Password</label>
            <input type="password" name="lcpass" id="lcpass" class="form-control" required>
        </div>
        
        <div class="form-group">
            <label for="lotp" class="form-label">Enter OTP:</label>
            <input type="text" name="enteredOTP" id="lotp" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Set Password</button>

    </form>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>