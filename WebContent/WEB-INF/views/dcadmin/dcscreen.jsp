<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<jsp:include page="scripts.jsp" />

</head>

<body onload="jspPage('./testprofitdata')">
<center>
	<!-- Retrieving model objects -->
	<div>
		<jsp:include page="nav.jsp" />
		<div class="col-md-9">
			<form id="filter">
				<div class="row mt-4">
					<div class="col-md-6 offset-md-3">
						<h5>Between Dates</h5>
						<div class="row">
							<div class="col-md-6">
								<input type="date" class="form-control" id="from" name="from">
							</div>
							<div class="col-md-6">
								<input type="date" class="form-control" id="to" name="to" onchange="jspPage('./testDateWiseProfitdata')">
							</div>
						</div>
					</div>
				</div>		
				</form>
				</div>
				</br></br>

		<div id="content"></div>
</center>
	</div>
	<script>
	function jspPage(path){
		
		var from=$('#from').val();
		var to=$('#to').val();
	$(document).ready(function() {
		console.log("data")
	  $.ajax({
		url :path,
		type : 'GET',
		data:{
			from:from,
		},
		
		success : function(data) {
			// Insert the fetched JSP content into the desired <div> element
			$('#content').html(data);
		},
		error : function(xhr, status, error) {
			// Handle error if the AJAX request fails
			console.log('Error: ' + error);
		}
	  });
	});
	}
	</script>

</body>
</html>