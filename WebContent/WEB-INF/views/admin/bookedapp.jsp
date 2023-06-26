<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*, spring.orm.model.entity.*,spring.orm.model.*,java.lang.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointments</title>
<jsp:include page="scripts.jsp" />
</head>
<body>



	<jsp:include page="nav.jsp" />
	<div align="center">
		<div class="container" align="center">
			<div class="row-md-12">
				<div class="col-md-9">
					<h3 align="center">Booked Appointments</h3>
					<form id="myform">
					        <div class="row">
						          <div class="col-md-2">
							   <h5>From Date:</h5>
							  <input type="date" class="form-control" id="from" name="from" >
						    </div>
					    	<div class="col-md-2">
						    	<h5>To Date:</h5>
							  <input type="date" class="form-control" id="to" name="to" onchange="jspPage('./fetchBookData')">
						   </div> 
						<div class="col-md-2">
							<h5>Specialization:</h5>
							<select class="form-control" id="spec" name="spec" onchange="jspPage('./fetchBookData')">
								<option value="select">select</option>
								<c:forEach items="${specdata}" var="spec">
									<option value="${spec.title}">${spec.title}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2">
							<h5>Status:</h5>
							<select class="form-control" id="status" name="status" onchange="jspPage('./fetchBookData')">
								<option value="select">select</option>
								<option value="YETO">YET TO</option>
								<option value="CMPL">Completed</option>
								<option value="CNL">Canceled</option>
							</select>
						</div>
						<div class="col-md-2">
							<h5>Doctor:</h5>
							<select class="form-control" id="doctor" name="doctor" onchange="jspPage('./fetchBookData')">
								<option value="select">select</option>
								<c:forEach items="${docdata}" var="doc">
									<option value="${doc.doctName}">${doc.doctName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					</form>
				</div>
				</br></br></br>
				<div id="content">

				</div>
			</div>
		</div>
	</div>
	<script>
	
	  function jspPage(path){
		$(document).ready(function() {
			console.log("data")
			var formdata ={
				spec :document.getElementById("spec").value,
				status : document.getElementById("status").value,
				doctor : document.getElementById("doctor").value,
				from : document.getElementById("from").value,
				to :document.getElementById("to").value
			}
			
			console.log(formdata.doctor)
			
		  $.ajax({
			url :path,
			type : 'GET',
			data: formdata,
			success : function(data) {
				// Insert the fetched JSP content into the desired <div> element
				$('#content').html(data);
			},
			error : function(xhr, status, error) {
				// Handle error if the AJAX request fails
				console.log('Error: ' + error);
			}
		  });
		})
		}
	  $(document).ready(function()
				{
			jspPage('./fetchBookData');
				});
	  
	</script>
</body>
</html>