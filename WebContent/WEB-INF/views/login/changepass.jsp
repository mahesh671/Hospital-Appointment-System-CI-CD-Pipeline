<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.4/jquery-confirm.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../css/changepass.css">

</head>
<body>
<div class="container text-center">
    <h4 class="mb-4">Set Password</h4>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">
            <p>${errorMessage}</p>
        </div>
    </c:if>
    <form action="passwordchange" method="post">
    <div class="form-group">
    
            <label for="lnpass" class="form-label">User Name</label>
            <input type="text" name="userName" id="uname" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="lnpass" class="form-label">Old Password</label>
            <input type="password" name="oldPassword" id="opass" class="form-control" required>
        </div>
        
        <div class="form-group">
            <label for="lcpass" class="form-label">New Password</label>
            <input type="password" name="newPassword" id="lcpass" class="form-control" required>
        </div>
        
        

        <button type="submit" class="btn btn-primary btn-block">Set Password</button>

    </form>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
