<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="./css/forgetPage.css">
    
  
</head>
<body>
<div class="container text-center">
    <h4 class="mb-4">Forgot Password</h4>
    <form action="forgetVal" method="post">

        <div class="form-group">
            <label for="uname" class="form-label">Username</label>
            <input type="text" name="uname" id="uname" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Send OTP</button>

    </form>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
